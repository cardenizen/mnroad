import java.text.SimpleDateFormat
import groovy.sql.Sql
import java.sql.SQLException
import java.sql.ResultSet
import groovy.sql.GroovyRowResult
import us.mn.state.dot.mnroad.BigFile
import us.mn.state.dot.mnroad.MrUtils
import us.mn.state.dot.mnroad.TimePeriod
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

class AnalyticsService {

  boolean transactional = true

    def dataSource
    def queryService
    def laneService

  List tcSensorSeqList(String where) {
    def rc = []

    Sql sql = new groovy.sql.Sql(dataSource)
    sql.eachRow("select distinct seq from mnr.tc_values"+where) {
      rc << it
    }

    return rc
  }

  Map getData(def model, def cellNumber, def startDate, def endDate){
    String table = "MNR.${model}_VALUES"
    def sdf = new SimpleDateFormat("yyyy-MM-dd")
    def todayStr = sdf.format(new Date())
    def fd = sdf.format(startDate)
    def td = sdf.format(endDate)
    if ((td == todayStr) || (endDate <= startDate) || ((startDate+450) < endDate)) {
      fd = sdf.format(endDate-450)
    }
    def srs = [:]
    def temperatures=[]
    Sql sql = new groovy.sql.Sql(dataSource)
    def tblYear = ""
    def query = "select seq, to_char(day,'yyyy,mm,dd') ||','||hour dtm,value from ${table} where cell = ${cellNumber} and day between to_date('${fd}','yyyy-mm-dd') and to_date('${td}','yyyy-mm-dd') and qhr=0 and value is not null order by cell,seq,day,hour"
//    println "\nData from DB query: $query"
//    println "startDate: ${fd}"
//    println "endDate: ${td}"
//    println ""
    try {
      String lastseqNum = ""
        sql.eachRow(query.toString(), { row ->
          String seqNum = "${row.seq}"
          if ((seqNum != lastseqNum) && (lastseqNum != "")) {
            srs.put(lastseqNum,temperatures)
            temperatures = []
          }
          Date dth = getSqlDate(row.dtm)
          Double v = row.value
          temperatures << [temperature: v, date: dth]
          lastseqNum = seqNum
          }
        )
      } catch (SQLException sqle) {
        if (sqle.message == "ORA-00942: table or view does not exist\n")
          println "Table ${table} not found."
        else
            println sqle.message  
      } catch (Exception e) {
        println e.message
      }
    if (srs.size() == 0){
      println "No SensorReadings found in table ${table} where cell = ${cellNumber} and day between $fd and $td."
      }
    return srs
    }

    def pointsForSensor(String fileName, String sensor, String chartType, Integer windowsize, Double minvariance) {
      def rowsAndColumns = []
      def columns = []
      def rows = []
      if (chartType == "AnnotatedTimeLine") {
// When producing an AnnotatedTimeLine, google analytics expects the first variable, the ordinate,
// to be a date type
        columns << [id:'', label: 'Date', pattern: '', type: 'date']
      } else if (chartType == "ScatterChart") {
// When producing a ScatterChart google analytics expects the first variable to be numeric
// Needs to change here and in AnalyticsService where the data is generated
        columns << [id:'', label: 'Elapsed Time', pattern: '', type: 'number']
      }
      if (!fileName)
        return rowsAndColumns
      def sids = []
      def idxs = []
      def i = 0
      TimePeriod tp = new TimePeriod()
      BigFile file = new BigFile(fileName)
      Iterator iter = file.iterator()
      DescriptiveStatistics stats = DescriptiveStatistics.newInstance();
//      stats.setWindowSize(120);
      stats.setWindowSize(windowsize.intValue());
      long nLines = 0;
      Double periodVariance = 0.0
      while (iter.hasNext()) {
        String line = iter.next()
        if (i++ == 2) {
          def models = line.split(",")
          models -= models[0]
          int k = 0
          models.each {
            // Create the indices of all sensors of a given model
//            if (it[2..3] == sensor[2..3]) {
              if (it == sensor) {
              sids << it
              columns << [id:'', label: it, pattern: '', type: 'number']
              idxs << k
            }
            k++
          }
        }
        if ((i < 4))// || (i%2 == 1))
          continue;
        def parts = line.split(",")
        def cells = []
        if (i == 4) {
// When producing a ScatterChart google analytics expects the first variable to be numeric
// This call sets the initial time so elapsed time can be computed for subsequent lines        
          tp.initialize(parts[0])
        }

//        if (periodVariance > 0.05) {
          if (periodVariance > minvariance.doubleValue()) {
        // use the first column - parts[0] to get the abscissa
        if (chartType == "ScatterChart") {
            // When producing a ScatterChart google analytics expects the first variable to be numeric
            // Needs to change here and in us.mn.state.dot.mnroad.AnalyticsController where the column label is generated
         Double elapsed = tp.elapsedSeconds(parts[0])
          cells << [v : elapsed]
        } else if (chartType == "AnnotatedTimeLine") {
            // When producing an AnnotatedTimeLine, google analytics expects the first variable, the ordinate,
            // to be a date type
        // chop off the last 3 places to get milleseconds rather than microseconds
        def dateTime = parts[0].substring(0,parts[0].size()-3)
        Date dtm = MrUtils.getFormattedDate(dateTime, "M/dd/yyyy  hh:mm:ss.SSS")
        cells << [v : dtm]
        }
        else // unknown chartType
        {
          return rows
        }
        }
        // remove the first column leaving the reading values
        parts -= parts[0]
        // insert the readings
        def m = 0
        parts.each {
          if (idxs.contains(m)) {
//            if (periodVariance > 0.05) {
              if (periodVariance > minvariance.doubleValue()) {
            BigDecimal bd = new BigDecimal(it)
            cells << [v : bd]
            }
            Double d = Double.parseDouble(it)
            stats.addValue(d);
//            if (nLines++ == 120) {
            if (nLines++ == windowsize.intValue()) {
              nLines = 0;
              periodVariance = stats.getVariance()
            }
          }
          m++
        }
        rows << ['c': cells]
      }
      file.Close()
      rowsAndColumns << columns
      rowsAndColumns << rows
      return rowsAndColumns
    }

/* Used to plot all sensors of a given model
      def pointList(String fileName, String model) {
        def rows = []

        if (!fileName)
          return rows
        def sids = []
        def idxs = []
        BigFile file = new BigFile(fileName)
        def i = 0
        Iterator iter = file.iterator()
        while (iter.hasNext()) {
          String line = iter.next()
          // ********* process the first 3 lines
          if (i++ == 2) {
            def models = line.split(",")
            models -= models[0]
            int k = 0
            models.each {
              if (it.contains(model)) {
                sids << it
                idxs << k
              }
              k++
            }
          }
          if ((i < 4) || (i%2 == 1))
            continue;
          // *********
          def cells = []
          def parts = line.split(",")
          // use only the first column - parts[0]
          def dateTime = parts[0].substring(0,parts[0].size()-3)
          Date dtm = MrUtils.getFormattedDate(dateTime, "M/dd/yyyy  hh:mm:ss.SSS")
          cells << [v : dtm]
          // remove the first column leaving the reading values
          parts -= parts[0]
          // insert the readings
          def m = 0
          parts.each {
            // select the right sensor
            if (idxs.contains(m)) {
              BigDecimal bd = new BigDecimal(it)
              cells << [v : bd]
            }
            m++
          }
          rows << ['c': cells]
        }
        file.Close()
        return rows
      }
*/
/*
    def points(String rawData_fn, idx) {
    def rows = []
    def linesRead = 0
    if (!rawData_fn)
      return rows
    File raw = new File(rawData_fn)
//    FileReader raw = new FileReader(rawData_fn)
    Double rmin=99999.9
    Double rmax=0.0
    Double cmin=99999.9
    Double cmax=0.0
    Double et = 0.0
    Date startDate
    Integer msStart
    Date endDate
    Integer msEnd
    try {
      println "Reading raw traffic data from ${rawData_fn}... "
        raw.splitEachLine(',') { aline ->
        String s = ""
        linesRead++
        if (linesRead > 3) {
          s = aline[0]
          if (linesRead%1000 == 0)
            print '.'
          if (!startDate) {
            startDate = MrUtils.getFormattedDate(s,"M/dd/yyyy  hh:mm:ss")
            msStart = Integer.parseInt(s.substring(s.indexOf('.')+1))
          }
          endDate = MrUtils.getFormattedDate(s,"M/dd/yyyy  hh:mm:ss")
          msEnd = Integer.parseInt(s.substring(s.indexOf('.')+1))

          int secnds = (endDate.getTime() - startDate.getTime())/1000
          Double elapsedTime = new Double(secnds) + (msEnd + (1000000-msStart))/1000000.0

          aline -= aline[0]
          Double seq1 = Double.parseDouble(aline[idx])
          cmin = Math.min(cmin,elapsedTime)
          rmin = Math.min(rmin,seq1)
          cmax = Math.max(cmin,elapsedTime)
          rmax = Math.max(rmin,seq1)
          def arow = [value: seq1, seconds: elapsedTime]
          rows << arow
        }
      }
      Double elt = new Double((endDate.getTime() - startDate.getTime())/1000) + (msEnd + (1000000-msStart))/1000000.0
    } catch (Exception e) {
      println "Exception:  ${e.message}"
      println ""
    }
    return rows
  }
*/
}
