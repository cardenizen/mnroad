package us.mn.state.dot.mnroad
/**
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Sep 23, 2010
 * Time: 7:27:04 AM
 * To change this template use File | Settings | File Templates.
 */
import groovy.sql.Sql
import java.sql.Date
import java.text.SimpleDateFormat

public class XvQuery {

  static def  sql
  static def config
  static String tnbase = "XV_VALUES"

  static main (args) {
    // Command line:
    // Oracle JDBC driver named ojdbc6.jar must be in the current directory
		// set JAVA_OPTS=-Xms128m -Xmx512m
		// groovy XvQuery.groovy -e prod
    Integer beginCell = 0
    Integer beginYear = 0
    int tot = 0

    def om = options(args)
    if (!om)
        return
    try {
      def cf = new File('XvQueryParams.groovy')
      config = new ConfigSlurper(om.env).parse(cf.toURL())
    } catch (java.io.FileNotFoundException fnfe) {
      println "Cannot find configuration file: TcQueryParams.groovy"
      return
    }
    XvQuery dbq = new XvQuery()
    dbq.initiate(config.db.server, config.db.user, config.db.pw)
    if (!sql) {
      println "Unable to connect to the database."
      System.exit(1)
    }
    def msg = "Connected to the ${om.env} environment."
    	beginCell = Integer.parseInt(om.cell)
    beginYear = Integer.parseInt(om.year)
    if (!beginCell)
      beginCell = config.beginCell
    tot = dbq.getData(beginCell,beginYear)
    println tot
  } // end main

  void initiate(String url, String user, String pw) {
    try {
      this.class.classLoader.rootLoader.addURL(new URL("file:../lib/ojdbc6.jar"))
      sql = Sql.newInstance( url, user, pw )
    } catch (Exception e) {
      println "Unable to connect to the database: ${e.message}."
    }
  } // end initiate

  int getData(int beginCell, int beginYear) {
    int tot=0
    def len = 0

    def cl = getCells()
    if (cl.keySet().size() == 0) {
      return tot
      }
    def tns = xvTableNames()
    if (tns.size() == 0) {
      return tot
    }

    String rds = "Raw_Data".toString()
    String sensorModel = tnbase.substring(0,2)
// Iterate over cells
    for (cellId in cl.keySet()) {
      def v = cl.get(cellId)
      int cellNumber = v['cell']
      if (cellNumber < beginCell) {
        continue
        }
      String ds = "${sensorModel}_${rds}/Cell${cellNumber}".toString()
      def dir = new File(ds)
      def status = dir.mkdirs()
      Date cellFromDate = v.get("from_date")
      Date cellToDate = v.get("to_date")
      println "\nCell ${cellNumber} (${cellFromDate} to ${cellToDate})"
      def fyear = yearOf(cellFromDate)
      def tyear = yearOf(cellToDate)
      def yearsWithNoDataTables = []
      def cellTotal = 0
      println ""
      println (fyear..tyear).toString()
      def loy = fyear..tyear
// Iterate over years
      for (Integer i=0; i < loy.size(); i++) {
      	Integer yr = loy[i]
      	if (beginYear <= yr) {
        def tableName = (yr==currentYear())?tnbase:tnbase+"_${yr}"
        if (hasTable(tableName)) {
          println "${yr}, ${tableName}"
          java.sql.Date fjDate = java.sql.Date.valueOf("$yr-01-01")
          java.sql.Date tjDate = java.sql.Date.valueOf("$yr-12-31")
          if ((cellFromDate.after(fjDate) && cellFromDate.before(tjDate))
                  || (cellToDate.after(fjDate) && cellToDate.before(tjDate))) {
            if (cellFromDate.getYear() == yr - 1900)
              fjDate = new java.sql.Date(cellFromDate.getTime())
            if (cellToDate.getYear() == yr - 1900)
              tjDate = new java.sql.Date(cellToDate.getTime())
          }
          println "${yr}, Composing queries..."
          def qm = queryMap(cellNumber, tableName, valueColumns(tableName), fjDate, tjDate)
          if (qm["dataQuery"]) {
            def fileName = "${ds}/${yr}Cell${cellNumber}_${cellId}_XV.csv"
            len += thermocoupleWrite(fileName.toString(),cellNumber, fjDate, tjDate, config.batchSize, qm, tableName)
          }
        } else {
          yearsWithNoDataTables << yr
        }
      }
      }
      if (yearsWithNoDataTables.size() > 0) {
        println "Tables for years ${yearsWithNoDataTables} not found."          
      }
      if (!cellTotal) {
        dir.delete()
      } else {
        tot += cellTotal
      }
    }
  return tot
  }

  def xvTableNames() {
    def rc = []
    def ans = sql.rows("select TABLE_NAME from user_tables where table_name like '${tnbase}%'".toString())
    ans.each { row ->
      rc << row.TABLE_NAME
    }

    return rc
  }

  def getCells() {
    def rc = [:]
    def q = "SELECT UNIQUE CELL,ID,FROM_DATE,TO_DATE FROM MNR.CELLS order by CELL,ID"

    def ans = sql.rows(q.toString())
    ans.each { row ->
      def d = [:]
      d.put('cell', row.CELL.intValue())
      d.put('from_date', new Date(row.FROM_DATE.getTime()))
      def to_date = row.TO_DATE
      def td = (to_date == null)?new Date(System.currentTimeMillis()):new Date(to_date.getTime())
      d.put('to_date', td)
      rc.put(row.ID, d)
    }

    return rc
  }

  int yearOf(Date dt) {
    def gc = new GregorianCalendar()
    gc.setTime(dt)
    gc.get(Calendar.YEAR)
  }

  int currentYear() {
    def gc = new GregorianCalendar()
    gc.setTimeInMillis(System.currentTimeMillis())
    gc.get(Calendar.YEAR)
  }

  boolean hasTable(String tableName) {
    boolean b = false
    def ans = sql.firstRow("select count(*) num from user_tables where table_name=?", [tableName])
    if (ans?.NUM.toString() == "1")  {
      b = true
    }
    return b
  }

  List valueColumns(def tableName) {
    def rc = []
    def t = config.valueColumnNames.get(tableName)
    if (t) {
      rc = t.split(",")
      }
      else
      rc << "VALUE"
    return rc
    }

    protected Map queryMap(int cell, String tableName, ArrayList valueColumnNames, java.sql.Date from_date, java.sql.Date to_date) {
      def rc = [:]
    def tn = tableName
    if (config.useLargetables || tableName.startsWith(tnbase))
      tn = tnbase + "_ALL"
    def psq = //"select unique seq from MNR.SENSOR_COUNTS where cell=? and table_name=? and ((from_day between ? and ?) or (to_day between ? and ?)) order by seq"
      "select unique seq from cell c join lane ln on ln.cell_id=c.id join layer l on l.lane_id=ln.id join sensor s on s.layer_id=l.id where model='VW' and cell=? order by seq"
      def q = ""
//    def ans = sql.rows(psq, [cell, tn, from_date,to_date, from_date,to_date])
    def ans = sql.rows(psq, [cell])
      if (ans) {
        def q1 = 'select cell \"Cell\", to_char(day,\'yyyy-mm-dd\') \"Day\", hour \"Hour\", qhr \"Qhr\", minute \"Minute\",'
        def q2 = ' from (select cell,day,hour,qhr,minute,'
        def q3 = " FROM mnr.$tableName WHERE cell = ? AND DAY BETWEEN ? and ? GROUP BY cell,day,hour,qhr,minute) order by cell,day,hour,qhr,minute".toString()
        def a1 = []
        def a2 = []
        ans.each {row ->
          if (valueColumnNames.size()==1) {
            a1 << "\"s_${row.SEQ}\""
            a2 << "min(decode(seq,${row.SEQ},value)) as \"s_${row.SEQ}\""
          }
          else {
            valueColumnNames.each { cn ->
              a1 << "${cn}_${row.SEQ}"
              a2 << "min(decode(seq,${row.SEQ},${cn})) as ${cn}_${row.SEQ}"
            }
          }
        }
        q = "${q1}${a1.join(',')}${q2}${a2.join(',')}${q3}"
      }
      if (q) {
        rc.put ("dataQuery",q.toString())
        rc.put ("countQuery",q[0..6] + "count(*) NUMROWS " + q.substring(q.indexOf("from")))
      }
      return rc
    }

  int thermocoupleWrite(String zipEntryName, int cell, java.sql.Date fjDate, java.sql.Date tjDate, int sqlBatchSize, Map qm, String tableName) {
      int len = 0
      int numBatches = 0
      int batchNum = 0
      int rowsRead = 0
      boolean includeHeader = true
      boolean includeRownum = false
//    Sql sql = new groovy.sql.Sql(dataSource)
      if (sql) {
      File tempFile
      BufferedWriter out
        try {
          def numrows = sql.firstRow(qm["countQuery"].toString(),[cell, fjDate, tjDate]).NUMROWS
          if (numrows==0) {
            return 0
          }
        tempFile = new File(zipEntryName);
        out = new BufferedWriter(new FileWriter(tempFile))
          numBatches = (numrows/sqlBatchSize) + 1
          int sqlBeginOffset = 0
        println "Cell ${cell}. Reading sensor data from table $tableName from ${fjDate} to ${tjDate}."
          while (batchNum < numBatches)  {
            def sqs = 'select * from ( select a.*, ROWNUM rnum from (' + qm["dataQuery"] + ') a where ROWNUM <= ? ) where rnum  >= ?'
            def data = sql.rows(sqs.toString(),[cell, fjDate, tjDate, sqlBeginOffset+sqlBatchSize, sqlBeginOffset+(rowsRead==0?0:1)])
            if (data.size() > 0) {
              data.each {row ->
                String line = ""
                if (rowsRead == 0 && includeHeader) {
                  def s = row.keySet().join(",")
                  line = "${includeRownum?s.toString():s.substring(0,s.lastIndexOf(','))}\n".toString()
                  len += line.size()
                out.write(line)
                }
              line = (getContent(row,includeRownum)+"\n").toString()
              len += line.size()
              out.write(line)
              rowsRead++
              }
            //print "${rowsRead} rows (${len} bytes) written.\r"
            }
            sqlBeginOffset += sqlBatchSize
          out.flush();
            batchNum++
          println "Batch ${batchNum} of ${numBatches} processed, ${rowsRead} rows."
          }
        } catch (IOException ioe) {
        println "${ioe.message}."
          ioe.printStackTrace();
        } catch (java.sql.SQLException sqle) {
        println "${sqle.message}."
          sqle.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      out.close();
      println "${len} bytes in ${rowsRead} rows for cell ${cell} written to entry ${zipEntryName}."
      }
      //return len
      return rowsRead
    }

  String getContent(Map row, boolean includeRownum) {
      String rc = ""
        def s = row.values().join(",")
        def withNull =  includeRownum?s.toString():s.substring(0,s.lastIndexOf(','))
        rc = withNull.replaceAll("null","")
      return rc
    }

  String formatDate(java.util.Date dateTime, String format) {
    def sdf = new SimpleDateFormat(format)
    String dt = ""
    if (!dateTime)
      return dt
    try {
      dt = sdf.format(dateTime)
    } catch (Exception iae) {
      def msg = "Unable to format ${dateTime} of type ${dateTime.class.name} as a Date: ${iae.message}"
      println msg
    }
    return dt
  }

  static def options (def args) {
      def om = [:]
      def validEnvs = ['production','test','development']
      def shortEnvs = ['prod','test','dev']
      def cli = new CliBuilder()
      cli.with
          {
          h(longOpt: 'help', 'Help - Usage Information')
          e(longOpt: 'name', '(production | test | development)', args: 1, type: String, required: true)
          c(longOpt: 'cell', 'Start at cell', args: 1, type: Integer, optionalArg: true)
          y(longOpt: 'year', 'Start at selected year in the selsected cell', args: 1, type: Integer, optionalArg: true)
          }
      def opt = cli.parse(args)
      if (!opt) 
          return
      if (opt.h) 
          cli.usage()
      def given = opt.e
      if (!validEnvs.contains(opt.e))     {
          if (!shortEnvs.contains(opt.e))     {
              cli.usage()
              return
          }
          if (opt.e == 'dev')
              given = 'development'
          if (opt.e == 'prod')
              given = 'production'
      }
      om.put('env', given)
      om.put('cell', (opt.c.class.toString().endsWith("String"))?opt.c:"0")
      om.put('year', (opt.y.class.toString().endsWith("String"))?opt.y:"0")
      return om
  }

}