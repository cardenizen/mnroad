import groovy.sql.Sql
import java.text.SimpleDateFormat
import java.text.ParseException
import java.sql.Timestamp
import java.util.Properties

class VehicleImportService {
  static def config
  static def minLatitude
  static def maxLatitude
  static def minLongitude
  static def maxLongitude
  static def latDir
  static def longDir
  static def lineNumber = 0
  static def sdf
  static StringBuffer sb = new StringBuffer()
  
  boolean transactional = true
  
  private def processFile(sql, filesDir, userDir, name, Long vpid) {
    try {
      loadConfig("VtImport.properties")
      minLatitude = config["latMin"]
      maxLatitude = config["latMax"]
      minLongitude = config["longMin"]
      maxLongitude = config["longMax"]
      latDir = config["latDirection"]
      longDir = config["longDirection"]
      sdf = new SimpleDateFormat(config["dateTimeFormat"])
      def fileCounts = [read:0,added:0,dateErrors:0,nanErrors:0,latLongErrors:0,tokenCountErrors:0]
      def fileName = userDir.path+'/'+name
      out name
      processFile (sql, fileName, vpid, fileCounts)
      String errsFile = ""
      def rc = "${fileCounts['added']} of ${fileCounts['read']} vehicle_location rows added."
      if (fileCounts['added'] != fileCounts['read']) {
        def of = new File( userDir, name+".error")
        of.withWriter("UTF-8") { w ->
          w.write(sb.toString())
        }
        def errFileName = name + ".error"
        rc += "  See messages in "+ errFileName
        //rc += "See <a class='home' href=\"${createLinkTo(dir:'')}\">${errFileName}</a>"
      }
      return rc   
    }
    catch(Exception e){
      return "Exiting due to "+e.class.name.toString() + ", Message: " + e.message
    }
  }
  
  private def parseFile(filesDir, userDir, name) {
    try {
      loadConfig("VtImport.properties")
      minLatitude = Double.parseDouble(config["latMin"])
      maxLatitude = Double.parseDouble(config["latMax"])
      minLongitude = Double.parseDouble(config["longMin"])
      maxLongitude = Double.parseDouble(config["longMax"])
      latDir = config["latDirection"]
      longDir = config["longDirection"]
      sdf = new SimpleDateFormat(config["dateTimeFormat"])
      def fileCounts = [read:0,added:0,dateErrors:0,nanErrors:0,latLongErrors:0,tokenCountErrors:0]
      def fileName = userDir.path+'/'+name
      sb.setLength(0)
      out name
      def errorCount = parse (fileName, fileCounts)
      String errsFile = ""
      def rc = ""
      if (errorCount > 0) {
        def of = new File( userDir, name+".error")
        of.withWriter("UTF-8") { w ->
          w.write(sb.toString())
        }
        rc = "${errorCount} of ${fileCounts['read']} vehicle_location rows flagged with errors."
        rc += "  See messages in "+name+".error"
      }
      else  {
        rc = "${fileCounts['read']} vehicle_location rows passed validation."
      }
      return rc
    }
    catch(Exception e){
      println "Exiting due to "+e.class.name.toString() + ", Message: " + e.message
    }
  }

  private def parse(fileName, validationCounts) {
    lineNumber = 0
    def linesInError = 0
    new File(fileName).eachLine {line ->
      validationCounts['read']++
      lineNumber++
      if (!validate(line,validationCounts)) {
        linesInError++
        outt "Line: $lineNumber, Data: ${line}"
      }
    }
    out "Validation Summary for file ${fileName}"
    out "${validationCounts['tokenCountErrors']} rows with insufficient number of tokens."
    out "${validationCounts['latLongErrors']} Latitude/Longitude format errors."
    out "${validationCounts['dateErrors']} rows with date errors."
    out "${validationCounts['nanErrors']} 'NaN' rows ignored."
    out "${validationCounts['read']} rows read."
    out "Errors: ${validationCounts['tokenCountErrors']+validationCounts['latLongErrors']+validationCounts['dateErrors']+validationCounts['nanErrors']}"
    return linesInError
  }

    private def processFile(sql, fileName, vehicle_pass_id, counts) {
      def ds = sql.dataSet('vehicle_location')
      lineNumber = 0
      new File(fileName).eachLine {line ->
        lineNumber++
        counts['read']++
        if (line.equals("")) {
          outt "Blank line at ${counts['read']}"
          return
        }
        if (!validate(line,counts)) {
          outt "Line: $lineNumber, Data: ${line}"
          return
        }
        def id = sql.firstRow("Select "+config["oracleIdSequence"]+".nextval from dual")
        addToDb(id, ds, vehicle_pass_id, line,counts)
      }
      out "Load Summary for file ${fileName}"
      if (counts['tokenCountErrors'] > 0)
        out "${counts['tokenCountErrors']} rows with insufficient number of tokens."
      if (counts['latLongErrors'] > 0)
        out "${counts['latLongErrors']} latitude/longitude format errors."
      if (counts['dateErrors'] > 0)
        out "${counts['dateErrors']} rows with date errors."
      if (counts['nanErrors'] > 0)
        out "${counts['nanErrors']} 'NaN' rows ignored."
      out "${counts['added']} rows added."
      out "${counts['read']} rows read."
      out ""

    }

    void addToDb(id, ds, vp_id,String line,counts) {
      List fields = line.split(',')
      // Collected time is coordinated universal time (UTC)
      // Central Standard time is 6 hours behind UTC
      // MnRoad data is collected in CST
      def hour = ""
      try {
        hour = "" + (Integer.parseInt(fields[2]) - 6)
      } catch (NumberFormatException nfe) {
        println "NumberFormatException exception at line: ${line}"
      }
      def dt = fields[1] + " " + hour + ":" + fields[3] + ":" + fields[4] + '0'
      try {
        Date odt = sdf.parse(dt)
        Timestamp now = new Timestamp(System.currentTimeMillis())
        ds.add(
                ID: id[0]
                , OBS_DATETIME: new Timestamp(odt.getTime())
                , LATITUDE: Double.parseDouble(fields[5])
                , LATITUDE_DIRECTION: fields[6]
                , LONGITUDE: Double.parseDouble(fields[7])
                , LONGITUDE_DIRECTION: fields[8]
                , VEHICLE_PASS_ID: new Long(vp_id)
                , DATE_CREATED: now
                , LAST_UPDATED: now
                , CREATED_BY: 'CARR1DEN'
                )
        counts['added']++
      }
      catch(ArrayIndexOutOfBoundsException e){
          println "ArrayIndexOutOfBoundsException: "+ e.message
      }
    }

    boolean validate(String line,counts) {
      boolean rc = true
      if (line.indexOf(config["nanKey"]) > -1) {
        counts['nanErrors']++
        return false
      }
      List flds = line.split(',')
      if (flds[8] == null) {
        counts['tokenCountErrors']++
        return false
      }

      String slatitude = flds[5]
      String slongitude = flds[7]
      Double latitude, longitude
      try {
        latitude = Double.parseDouble(slatitude)
        if ((latitude < minLatitude)|| (latitude > maxLatitude))   {
          outt "Latitude out of range: ${slatitude}"
          counts['latLongErrors']++
          rc = false
        }
        longitude = Double.parseDouble(slongitude)
        if ((longitude < minLongitude)|| (longitude > maxLongitude))   {
          outt "Longitude out of range: ${slongitude}"
          counts['latLongErrors']++
          rc = false
        }
      } catch (NumberFormatException nfe) {
        outt "Failed to parse latitude/longitude - ${slatitude}/${slongitude}"
        counts['latLongErrors']++
        rc = false
      } catch (ClassCastException cce) {
        println cce.message
      }
      if (flds[6].toUpperCase() != latDir)    {
        outt "Latitude direction s/b '${latDir}' but was ${flds[6]}"
        counts['latLongErrors']++
        rc = false
      }
      if (flds[8].toUpperCase() != longDir)    {
        outt "Longitude direction s/b '${longDir}' but was ${flds[8]} "
        counts['latLongErrors']++
        rc = false
      }
      def dt = flds[1] + " " + flds[2] + ":" + flds[3] + ":" + flds[4] + '0'
      try {
        sdf.parse(dt)
      } catch (ParseException adde) {
          outt adde.message
          counts['dateErrors']++
        rc = false
      }
      return rc
    }

    def out(msg) {
      sb.append "${msg}\n"
    }

    def outt(msg) {
      sb.append "\t\t${msg}\n"
    }

  private loadConfig(String propFileName) {
    config = new ConfigSlurper().parse(new File(filesDir + '/' + propFileName).toURL())
  }

}

//Accessing Session Map in the Domain or Service Layer
//Thursday, July 16th, 2009
//
//The Session Map is available in Grails in the Views, TagLibs and the Controllers. 
//That is, it can be directly accessed by the name “session”. 
//If the Session Map is required to be accessed in the Service Layer 
//or the Domain layer, such a straightforward approach will not work. 
//In this case, a class which is a part of the Spring Framework can be 
//used which gives the current context, the request attributes and the 
//session. This class along with HttpSession have to be imported by 
//issuing the following statements.
//import org.springframework.web.context.request.RequestContextHolder
//Now, the session variable can be defined in the Service class or Domain method as:
//        def session = RequestContextHolder.currentRequestAttributes().getSession()
//The session attributes can now be accessed as
//            session.attribute
