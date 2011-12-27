import groovy.sql.Sql
import java.sql.SQLException

import org.apache.poi.hssf.usermodel.HSSFWorkbook

class DistressProduct {
static def configDatasource = new File("grails-app/conf/DataSource.groovy").toURL()
static def configDev = new ConfigSlurper("development").parse(configDatasource)
static def sql = Sql.newInstance(configDev.dataSource.url as String,
            configDev.dataSource.username as String,
            configDev.dataSource.password as String,
            configDev.dataSource.driverClassName as String)
static def configConfig = new File("grails-app/conf/Config.groovy").toURL()
static def config = new ConfigSlurper("development").parse(configConfig)

static Map dnMap = [:]
static Map typeFolders = [:]
static Map columnTypeMap = [:]
static Map dataMap = [:]

  static main (args) {
    def mc = new DistressProduct()
    dataMap.put("nrows", 0)
    dataMap.put("hdr", "")
    dataMap.put("rows", [])
    mc.exportDistress(sql,config)
  }

  def createTypeFolders (config) {
    def rc = [:]
    try {
      File f = new File(config.rdrive+config.dataProductDataFolder)
      if (!f.exists())
        f.mkdir()
      def fn = config.rdrive+config.dataProductDataFolder+config.byPavementTypeFolder
      f = new File(fn)
      if (!f.exists())
        f.mkdir()
      def types = config.pavementTypes.keySet()
      types.each { type ->
        def typeFolder = "${fn}\\${config.pavementTypes.get(type)}"
        (new File(typeFolder)).mkdir()
        rc.put(type, typeFolder)
      }
    } catch (Exception e) {
      println "${createDesignDetailsByTypeFolders}: ${e.message}"
    }
    return rc
  }

  def writeRows={ row ->
    def rr = row.toRowResult()
    def r = ""
    def a = []
    rr.eachWithIndex { it,i ->
      String v = ""
      String o = rr[i]
      if (o && columnTypeMap.get(i+1) == "DATE") {
        v = o.substring(0,10)
      } else if (o && o.class.name == "java.lang.String") {
        int idx = o.indexOf("\"")
        if (idx>=0) {
          String s1 = o.substring(0,idx)
          String s2 = o.substring(idx)
          char [] na = new char[o.size()+3]
          na[0] = '"'[0]
          s1.eachWithIndex{c,j->
            na[j+1]= c
          }
          na[idx+1] = '"'[0]
          s2.eachWithIndex{c,ii->
            na[idx+2+ii]= c
          }
          na[o.size()+2] = '"'[0]
          v = String.valueOf(na)
        } else {
          v = "\"${o}\""
        }
      } else {
        v = "${o}"
      }
      if (i<rr.size()-1) {
        a.add(v)
      }
      r += v+(i<rr.size()-2)?",":""
    }
    dataMap["rows"] << "${a.join(',')}\n"
    dataMap["nrows"]++
  }

  def writeHeader = { meta ->
    def cn = [:]
    columnTypeMap.clear()
    cn.put(0,"CELL_ID")
    columnTypeMap.put(0, "NUMBER")
    (1..meta.columnCount-1).each {
        cn.put(it,meta.getColumnLabel(it))
        columnTypeMap.put(it,meta.getColumnTypeName(it))
      }
    dataMap["hdr"] = "${cn.keySet().collect {cn.get(it)}.join(",")}\n"
    dataMap["rows"] = []
    dataMap["nrows"] = 0
   }

  def exportDistress(def sql, def config) {
    dnMap = mc.designNumMap(sql)
    typeFolders = mc.createDesignDetailsByTypeFolders(config)
    def cb = cellBasics(sql)

    cb.keySet().each { cellId ->
      def c = cb.get(cellId)
      def cellDesign = "${c.file}"
      def tfs = "${typeFolders.get(c.type)}\\${cellDesign}\\"
      def tf = new File(tfs)
      if (!tf.exists())
        tf.mkdir()
      config.distressTableNames.each { tableName ->
        def dd1 = "IV. Physical Measurements\\"
        def dd2 = "Surface Characteristics\\"
        def dd3 = "Visual Distress\\"
        def nd = mkdir(tfs,dd1)
        if (nd) {
          nd = mkdir(nd, dd2)
        }
        if (nd) {
          nd = mkdir(nd, dd3)
        }
        def q = "select * from ${config.currentSchema}.${tableName} where cell=? and day between ? and ?"
        def sqlDateFrom = new java.sql.Date(c.fromDate.getTime())
        def sqlDateTo = new java.sql.Date((c.toDate?:new Date()).getTime())
        sql.eachRow(q.toString(),[c.cell,sqlDateFrom,sqlDateTo],writeHeader,writeRows)
        println "${dataMap["nrows"]} ${tableName} rows exported for ${cellDesign}"
        if (dataMap["nrows"]) {
          File df = new File("${nd}${tableName}.csv")
          FileWriter fw = new FileWriter(df)
          if (fw) {
            fw.append dataMap["hdr"]
            for (row in dataMap["rows"]) {
              fw.append("${cellId},${row}")
            }
            fw.close()
            fw=null
          }
        }
      }
    }
  }

  String mkdir(String base,String dir) {
    String rc = ""
    def nds = base + dir
    def ndf = new File(nds)
    if (ndf.exists()) {
      rc = nds
    } else if (ndf.mkdir()) {
      rc = nds
    }
    return rc
  }

  Map designNumMap (def sql) {
    def rc = [:]
    def q = "SELECT ID,CELL_NUMBER,CONSTRUCTION_ENDED_DATE FROM MNR.CELL order by CELL_NUMBER,CONSTRUCTION_ENDED_DATE"
    def dn = 1
    def lc = 0
    def ans = sql.rows(q.toString())
    ans.each { row ->
      if (lc == 0) {
        rc.put(row.ID, dn++)
      } else {
        if (lc == row.CELL_NUMBER) {
          rc.put(row.ID, dn++)
        } else {
          dn = 1
          rc.put(row.ID, dn++)
        }
      }
      lc = row.CELL_NUMBER
    }
    return rc
  }

  Map cellBasics(def sql) {
    def rc = [:]
    def q = """SELECT SUBSTR(C.CLASS,24) TYPE,CELL,CS.ID,FROM_DATE,TO_DATE,FROM_STATION,TO_STATION
  FROM MNR.CELL_STATIONS  CS
  JOIN MNR.CELL C ON C.ID = CS .ID
  ORDER BY CELL,FROM_DATE,FROM_STATION
  """
    sql.eachRow(q.toString()) { row ->
      def rowMap = [:]
      rowMap.put("cell"       ,row.CELL        )
      rowMap.put("type"       ,row.TYPE        )
      rowMap.put("fromDate"   ,new Date(row.FROM_DATE.getTime())   )
      rowMap.put("toDate"     ,row.TO_DATE?(new Date(row.TO_DATE.getTime())):null   )
      rowMap.put("fromStation",row.FROM_STATION)
      rowMap.put("toStation"  ,row.TO_STATION  )
      def cellDesign = "${row.TYPE} Cell ${row.CELL} Design ${dnMap.get(row.ID)}"
      rowMap.put("file"  ,cellDesign  )
      rc.put(row.ID, rowMap)
    }
    return rc
  }

}