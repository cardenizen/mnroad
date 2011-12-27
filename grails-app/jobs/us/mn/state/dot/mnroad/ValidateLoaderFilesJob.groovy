package us.mn.state.dot.mnroad
/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Feb 9, 2011
 * Time: 9:59:28 AM
 * To change this template use File | Settings | File Templates.
 */
import groovy.sql.Sql
import groovy.io.FileType
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.sql.ResultSet

public class ValidateLoaderFilesJob {

  def timeout = 5000l // execute job once in 5 seconds
  def dataSource  // injected by the quartz plugin

  static triggers = {
//    simple name: 'testingTrigger', startDelay: 1000, repeatInterval: 300000    
    // Run at 7AM every day
//    cron name:'countReadingsDailyCronTrigger', startDelay:6000, cronExpression: '0 0 7 * * ? *'
  }

  def execute(context) {
    Sql sql
    try {
      sql = new groovy.sql.Sql(dataSource)
//      readLoaderFiles(ConfigurationHolder.config.loaderFiles, sql)
      readLoaderFiles(ConfigurationHolder.config.loaderSampleFiles, sql)
    } catch (Exception e) {
      println e.message
    }
  }

  private def readLoaderFiles(def unc, def sql) {
    def gc = new GregorianCalendar()
    def yr = gc.get(Calendar.YEAR)
    def mo = gc.get(Calendar.MONTH)
    def day= gc.get(Calendar.DAY_OF_MONTH)
    println "Start ValidateLoaderFilesJob:readLoaderFiles ${mo}/${day}/${yr}"
    
//    def dir
//    def list = [:]
//    def nFiles = 0
//    def validModels = ['BV']
//    sql.query("SELECT model FROM mnr.sensor_model order by model") { ResultSet rs ->
//         while (rs.next())
//           validModels << rs.toRowResult().model
//     }
//    try {
//      dir = new File(unc)
//      dir.eachFile (FileType.FILES) { file ->
//        if ((file.name.size() > 13) && file.name.startsWith("Cell") && file.name.endsWith(".dat")) {
////        Returns a map with these keys: "cell", "model", "fromSeq", "toSeq'
//          def rc = parseFileName(file.name)
//          if (rc) {
//            list.put(file.name, rc)
//            if (!validModels.contains(rc.model)/* && rc.model != 'BV'*/) {
//              println "${file.name} does not refer to a valid model."
//            } else {
//              if (!thisYearsData(file, yr)) {
//                println ("${file.name} does not have this years data.")
//              }
//            }
//          } else {
//            println "Filename map has no entries for file name ${file.name}!"
//          }
//        } else {
//          "${file.name} is not a Cell data file"
//        }
//        nFiles++
//      }
//    } catch (java.io.FileNotFoundException fnfe) {
//      println "FileNotFoundException: ${fnfe.message}"
//    }
//  def noSensorList = [:]
  def knownSensorCnt = 0
//  list.keySet().each { fileName ->
//    def cmsMap = list.get(fileName)
//    if (cmsMap.model!='CT' && cmsMap.model != 'BV') {
//      def sq = "SELECT count(*) SENSOR_COUNT FROM MNR.CELL C JOIN MNR.LANE LN ON LN.CELL_ID=C.ID JOIN MNR.LAYER L ON L.LANE_ID=LN.ID JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID where cell_number=? and model=? and seq between ? and ?"
//      def sc = sql.firstRow(sq,[cmsMap.cell,cmsMap.model,cmsMap.fromSeq,cmsMap.toSeq]).SENSOR_COUNT
//      if (sc == 0) {
//        noSensorList.put(fileName,"cell:${cmsMap.cell}, \tmodel:${cmsMap.model},\tseq:${spacepad(cmsMap.fromSeq)} - ${spacepad(cmsMap.toSeq)}")
//      } else {
//        knownSensorCnt++
//      }
//
//    }
//  }
    
  println "${knownSensorCnt} files with known sensors reporting data."
  if (noSensorList.size()) {
    println  "\n****************************************************************"
    println "${noSensorList.size()} files with sensors unknown to the SENSOR table reporting data:"
    println  "****************************************************************"
    noSensorList.keySet().each { key ->
      println "${noSensorList.get(key)}:\t${key}"
    }
  }
  println "End ReadLoaderFiles, ${list.size()} of ${nFiles} files have valid file names."
  }

  String spacepad(def s) {
    String rc = ""
    def st = "${s}"
    if (st.size() < 3) {
      rc = st.padLeft(3)
    } else {
      rc = st
    }
    return rc
  }
/*
  Returns true if the year on the first and last lines is this year
  Assumes year is alway the sencond token on a line.
 */
  private boolean thisYearsData(File f, def yr) {
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
              ,"Sequence numbers are assumed to be sequential."
      ]
      println "${requiredformat} ${wc.join(' \n\tand ')}"
    }
    return fileAttrs
  }

}