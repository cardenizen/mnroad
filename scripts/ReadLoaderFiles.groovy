import groovy.sql.Sql
import groovy.io.FileType
import java.sql.ResultSet
import us.mn.state.dot.mnroad.Sensor

//def dir = "LoaderDataFeb-3-2011"
def config = new ConfigSlurper("development").parse((new File("../grails-app/conf/Config.groovy")).toURL())
readLoaderFiles(config.loaderSampleFiles)//+dir)//, validModels)
//readLoaderFiles(config.loaderFiles)//, validModels, sql)

private def readLoaderFiles(def unc) {//, def validModels, def sql) {
  println "Start 'ReadLoaderFiles"
  def dir
  def list = [:]
  def nFiles = 0
//  def url="jdbc:oracle:thin:@MRL2K3dev.ad.dot.state.mn.us:1521:DEV11"
//  def username="mnr"
//  def password="pass"
  def url="jdbc:oracle:thin:@MRL2K3MRDB.ad.dot.state.mn.us:1521:mnrd"
  def username="mnru"
  def password="pass"
  def driverClassName = "oracle.jdbc.OracleDriver"
  def sql = Sql.newInstance(url as String, username as String, password as String, driverClassName)
  def validModels = ['BV']
  sql.query("SELECT model FROM mnr.sensor_model order by model") { ResultSet rs ->
       while (rs.next())
         validModels << rs.toRowResult().model
   }
  try {
    dir = new File(unc)
    dir.eachFile (FileType.FILES) { file ->
      if ((file.name.size() > 13) && file.name.startsWith("Cell") && file.name.endsWith(".dat")) {
//        Returns a map with these keys: "cell", "model", "fromSeq", "toSeq'
        def rc = parseFileName(file.name)
        if (rc) {
          list.put(file.name, rc)
          if (!validModels.contains(rc.model)/* && rc.model != 'BV'*/) {
            println "${file.name} does not refer to a valid model."
          } else {
            if (rc.model != 'BV') {
              if (!thisYearsData(file)) {
                println ("${file.name} does not have this years data.")
              }
              try {
                int n = Integer.parseInt(rc.cell)
                def sensors = [:]
                getSensors(sensors, sql, n, rc.model)
                if (sensors.size()) {
                  if (sensors.get(5))
                    println sensors.get(5)
                  println "${rc} - SENSOR_COUNT: ${sensors.size()}"
                  println sensors
                }
                else {
                  println "No Sensors found: ${rc}"
                }
              } catch (NumberFormatException nfe) {
                println "  ******* ${file.name} ********"
                println "${nfe.message}, unable to parse cell number to an Integer."
                println "  *****************************"
              }

            }
          }
        } else {
          println "Filename map has no entries for file name ${file.name}!"
        }
      } else {
        "${file.name} is not a Cell data file"
      }
      nFiles++
    }
  } catch (java.io.FileNotFoundException fnfe) {
    println "FileNotFoundException: ${fnfe.message}"
  }
println "End ReadLoaderFiles, ${list.size()} of ${nFiles} file have valid file names."
}

private Map getSensors (Map sensors, def sql, def cellNum, def model) {
    def q = """
SELECT * FROM (
SELECT
S.ID,C.CELL_NUMBER CELL,SM.MODEL,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.DATE_INSTALLED,S.DATE_REMOVED
FROM MNR.SENSOR S JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID=SM.ID JOIN MNR.LAYER L ON S.LAYER_ID=L.ID JOIN MNR.LANE LN ON L.LANE_ID=LN.ID JOIN MNR.CELL C ON LN.CELL_ID=C.ID JOIN MNR.MATERIAL M ON M.ID = L.MATERIAL_ID
UNION ALL
SELECT S_ID ID, CELL, MODEL, SEQ, STATION, OFFSET, DATE_INSTALLED, DATE_REMOVED FROM (
    SELECT
    CCC.CELL_OVER CELL,SUBSTR(CCC.CLASS,24) TYPE,CCC.CELL_UNDER,SUBSTR(CCC.CLASS_UNDER,24)
    ,CCC.FROM_STATION_UNDER,CCC.TO_STATION_UNDER,CCC.FROM_DATE_UNDER,CCC.TO_DATE_UNDER
    ,LN.NAME LANE,M.BASIC_MATERIAL,CCC.FROM_DATE
    ,SM.MODEL,S.ID S_ID,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.SENSOR_DEPTH_IN DEPTH,S.DATE_INSTALLED,S.DATE_REMOVED
    , CCC.START_STATION,CCC.END_STATION
    FROM
    (
    SELECT
    CC.CELL_ID ID_UNDER
    ,(SELECT CELL_NUMBER FROM MNR.CELL WHERE ID = CC.CELL_ID) CELL_UNDER
    ,(SELECT CLASS FROM MNR.CELL WHERE ID = CC.CELL_ID) CLASS_UNDER
    ,(SELECT START_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_STATION_UNDER
    ,(SELECT END_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_STATION_UNDER
    ,(SELECT CONSTRUCTION_ENDED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_DATE_UNDER
    ,(SELECT DEMOLISHED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_DATE_UNDER
    ,C.CONSTRUCTION_ENDED_DATE FROM_DATE,C.ID ID_OVER,C.CELL_NUMBER CELL_OVER,C.CLASS
    ,C.START_STATION,C.END_STATION
    FROM MNR.CELL C JOIN MNR.CELL_CELL CC ON CC.CELL_COVERS_ID=C.ID
    ) CCC
    JOIN MNR.LANE LN ON LN.CELL_ID=CCC.ID_UNDER
    JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
    JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
    JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
    JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
    WHERE DATE_INSTALLED > FROM_DATE-63
     AND S.STATION_FT BETWEEN START_STATION AND END_STATION
  )
)
WHERE CELL=? AND MODEL=?
ORDER BY CELL, MODEL,SEQ
"""
  try {
    sql.eachRow(q.toString(),[cellNum, model]) { row ->
      def rowData = []
      rowData << row.CELL?:""
      rowData << row.MODEL?:""
      rowData << row.SEQ?:""
      rowData << row.STATION?:""
      rowData << row.OFFSET?:""
      rowData << row.DATE_INSTALLED?:""
      if (!row.DATE_REMOVED) {
        rowData << "Active"
      } else {
        rowData << "Error - removed sensor reporting data"
      }
      sensors.put(row.ID, rowData)
    }
    } catch( Exception e) {
      println "cellNum ${cellNum}, model: ${model}. ${e.message}"
    }
  }

/*
  Returns true if the year on the first and last lines is this year
  Assumes year is alway the sencond token on a line.
 */
private boolean thisYearsData(File f) {
  def yr = (new GregorianCalendar()).get(Calendar.YEAR)
  def lines = f.readLines()
  return !((lines.first().split(",")[1].toInteger() != yr) ||
      (lines.last().split(",")[1].toInteger() != yr))
}

/*
Returns a map with these keys: "cell", "model", "fromSeq", "toSeq'
 */
private def parseFileName(fn) {
  def fileAttrs = [:]
  def bvct = ['BV', 'CT']
  def arr = fn.split(" ")
  if (arr.size() == 2) {
    def cms = arr[1][0..-5]
    arr = cms.split("_")
    if (arr.size() == 2) {
      def cell = arr[0]
      def model = arr[1][0..1]
      def seqRange = arr[1].substring(2)
      arr = seqRange.split("-")
      if (arr.size() == 2) {
        def sStartSeq = arr[0]
        def sNSensors = arr[1]
        def startSeq = 0
        def nSensors = 0
        try {
          startSeq = Integer.parseInt(sStartSeq)
          nSensors = Integer.parseInt(sNSensors)
          fileAttrs.put('cell',cell)
          fileAttrs.put('model',model)
          fileAttrs.put('fromSeq',startSeq)
          fileAttrs.put('toSeq',startSeq + nSensors - 1)
//          rc.put(it,fileAttrs)
        } catch (NumberFormatException nfe) {
          println "Unable to parse $sStartSeq or $sNSensors"
        }
      } else {
        if (!bvct.contains(model)) {
          println "Unable to split '${seqRange}' in file name ${it} using '-'"
        } else {
          fileAttrs.put('cell',cell)
          fileAttrs.put('model',model)
          fileAttrs.put('fromSeq',Integer.parseInt(seqRange))
          fileAttrs.put('toSeq',0)
        }
      }
    } else { println "Unable to split '${cms}' in file name ${it} using '_'" }
  } else {
    println "Unable to split file name '${fn}' using ' '"
    def requiredformat="Required format is 'Cell nnn_mmbbb-ss'"
    def wc = [" where nnn = 2 or 3 digit cell number"
            ,"mm = 2 character model acronymn"
            ,"bbb = 2 or 3 digit beginning sequence number"
            ,"ss = number of readings on a line."
    ]
    println "${requiredformat} ${wc.join(' \n\tand ')}"
  }
  return fileAttrs
}
