package us.mn.state.dot.mnroad

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.util.GrailsUtil
import java.text.SimpleDateFormat
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.jsecurity.SecurityUtils

/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Jul 30, 2009
 * Time: 7:55:36 AM
 * To change this template use File | Settings | File Templates.
 */

public class MrUtils {
  static Logger log = Logger.getLogger(MrUtils.class.name)
  static List attrsList(String cls, String attrName) {
    def ml = []
    String name = attrName
    String al = AppConfig.findAllByNameAndParameter( cls, attrName).val
    if (al.startsWith("[") && al.endsWith("]"))
      al = al.substring(1, al.size()-1) // strip off the "[" and "]" from the ends
    al.split(",").each {
      ml.add(it)
    }
    return ml
  }

  static String decimalToStation(BigDecimal bd) {
    String rc = ""
    String offset = "+" + (""+(Integer)bd%100).padLeft(2,'0')
    String fraction = "${roundTwo(bd-(Integer)bd)}".substring(1)
    rc = (Integer)(bd/100) + offset + fraction
    return rc
  }

  static def plotClasses() {
    def m = [] as Set
    m.add("AnnotatedTimeLine")
    m.add("ScatterPlot")
    return m
  }

  static def pickListClasses() {
    def m = [] as Set
    def r = AppConfig.findAll()
    r.each   {
      m.add(it.name)
    }
    return m
  }

  static String grailsEnvironment() {
    String env = "envUnknown"
//check if production
    if ( GrailsUtil.getEnvironment().equals(GrailsApplication.ENV_PRODUCTION))
      {env="production"}
//check if development
    else if ( GrailsUtil.getEnvironment().equals(GrailsApplication.ENV_DEVELOPMENT))
      {env="development"}
//check if testing
    else if ( GrailsUtil.getEnvironment().equals(GrailsApplication.ENV_TEST) )
      {env="test"}
    return env
  }

/*
  * Given a date string in yyyy,mm,dd,hh format
  * return an SqlDate
   */
  static java.util.Date getDate(String ds)  {
    def dts = []
    dts = ds.trim().split(",")
    def yr = Integer.parseInt(dts[0])-1900
    def mo = Integer.parseInt(dts[1])-1
    def dy = Integer.parseInt(dts[2])
    def hr = Integer.parseInt(dts[3])-1
    return new Date(yr,mo,dy,hr,0,0)
  }

  static def getFormattedDate(String dateTimeString, String format) {
    def sdf = new SimpleDateFormat(format)
    Date dt
    if (!dateTimeString)
      return null
    try {
      dt = sdf.parse(dateTimeString)
    } catch (Exception iae) {
      def msg = "Unable to parse ${dateTimeString} as a Date: ${iae.message}"
      log.warn(msg)
      println msg
    }
    return dt
  }

  static String formatDate(java.util.Date dateTime, String format) {
    def sdf = new SimpleDateFormat(format)
    String dt = ""
    if (!dateTime)
      return dt
    try {

      dt = sdf.format(dateTime)
    } catch (Exception iae) {
      def msg = "Unable to format ${dateTime} of type ${dateTime.class.name} as a Date: ${iae.message}"
      log.warn(msg)
      println msg
    }
    return dt
  }

  static def formatTimestamp(java.sql.Timestamp dateTime, String format) {
    return formatDate(dateTime, format)
  }

  static Long toLong(String s) {
    Long rc = 0
    try {
      rc = Long.parseLong(s)
    } catch (NumberFormatException nfe) {
      log.warn("${nfe.message} - ${s}")
    }
    return rc
  }

  static Double roundTwo(Double d) {
    if (!d)
      return 0.0
    long l = (int)Math.round(d * 100); // truncates
    return l / 100.0;
  }

  static List concreteLayersInLane(Lane l) {
  def rc = []
  l.layers.each() {
   if (it.isConcrete()){
     rc << it
   }
  }
  return rc
  }

  static List concreteLayersInCell(Cell c) {
   def rc = []
   c.lanes.each { ln ->
      ln.layers.each() {
       if (it.isConcrete()){
         rc << it
       }
     }
   }
   return rc
  }

  static List concreteLayers() {
    def rc = []
    def ll = Layer.list()
    ll.each {
      if (it.isConcrete()){
        rc << it
      }
    }
    return rc
  }

  static String fqfn(base, dir) {
    (!base.endsWith(File.separator))?(base + File.separator + dir):(base + dir)
  }


  static String mkdir(String base,String dir) {
    String rc = ""
    def nds = (!base.endsWith(File.separator))?(base + File.separator + dir):(base + dir)
    def ndf = new File(nds)
    if (ndf.exists()) {
      rc = nds
    } else {
      //println "Creating: '${nds}'."
      if (ndf.mkdir()) {
        //println "Created: '${nds}'."
        rc = nds
      } else  {
        //println "Unable to create: '${nds}'."
        log.info "Unable to create: '${nds}'."
        rc = base
      }
    }
    return rc
  }

  static String mkBranch(String base, String branch) {
    def dir = base
    def fns = branch.split("\\\\")
    if (fns && fns.length > 0) {
       for (fn in fns) {
          dir = MrUtils.mkdir(dir, fn)
       }
    }
    return dir.toString()
  }

  static quotedString(String val) {
    def nvl = val
    if (val == null)
      return ""
    if (val.contains("\'")) {
      def s = val
      nvl = s.replace("\'","\'\'")
    }
    if (val.contains("\"")) {
      def nv = (val.toString().indexOf('"')>-1)?val.replace('"','""'):"$val"
      nvl = ((nv.toString().indexOf('\r\n')>-1))?nv.replace("\r\n"," "):"$nv"
    }
    return '"' + nvl +'"'
  }

  static String formatSqlValueForCsv(Object val, String typ) {
    String rc = ""
    if (typ.equals("DATE") || (typ.startsWith("TIMESTAMP"))) {
      if (val == null)
        rc = ""
      else if ("${val}".endsWith("00:00:00.0")){
        rc="${val}".split(" ")[0]
      }
      else if (typ == "TIMESTAMP(6)") {
          def ov = val.stringValue()
          try {
          rc = ov[0..ov.lastIndexOf('.')+3] // truncate to thousandths of a second
          } catch (StringIndexOutOfBoundsException siob) {
            rc = ov //println "${ov} ${siob.message}"
          }
      }
      else
        rc = "${val}"
    }
    else if (typ=="CHAR" || typ=="VARCHAR2" ) {
      if (val == null)
        return "\"\""
      rc = "\"${val.trim()}\""
      if (val.contains("\'")) {
        def s = val
        def n = s.replace("\'","\'\'")
        rc = "\"$n\""
      }
      if (val.contains("\"'")) {
        def nv = (val.toString().indexOf('"')>-1)?val.replace('"','""'):"$val"
        def nv1 = ((nv.toString().indexOf('\r\n')>-1))?nv.replace("\r\n"," "):"$nv"
        rc =  '"' + nv1 +'"'
      }
    }
    else if (typ.equals("NUMBER")) {
      rc = val == null?"":val
    }
    else {
        rc = val?.toString()
        println "Unknown type: $typ"
    }
    return rc
  }

  /*
      subMap returns a subset of a source Map.
      Members are selected by spliting a comma separated list of strings into an array
      and searching the source map for keys that match members of the array  
   */
  static Map subMap(LinkedHashMap params, String keysCsv) {
    def rc = [:]
    def keys=[]
    def values=[]
    params.keySet().each { key ->
      keys << key
      values << params.get(key)
    }
    String [] ka = keys.toArray()
    String [] va = values.toArray()
    if (keysCsv) {
      def columnNames = keysCsv.split(',')
      if (columnNames.length > 0) {
        for (name in columnNames) {
          int i = 0
          String ns = name.toString()
          for (String s in  ka) {
            if (s.trim().equals(ns.trim())) {
              def v = ''
              if (i < va.size()) {
                v = va[i]
                rc.put(name,v)
                break
              }
            }
            i++
          }
        }
      }
    }

    return rc
  }

  static String getUserName() {
    def userName = ""
    def subject = SecurityUtils.getSubject();
    if (subject?.authenticated) {
         userName = subject.principal
    }
    return userName
  }

//
//  public static Map getCellProps() {
//    def rc = [:]
//    def dol = ApplicationHolder.application.getArtefacts("Domain") as List
//    dol.each { domObj ->
//      if (domObj.shortName != "Cell" && domObj.shortName.endsWith("Cell")) {
//        def tableProps = [:]
//        def classMetaData = sessionFactory.getClassMetadata(ApplicationHolder.application.getClassForName(domObj.clazz.name))
//        tableProps.put("tableName",classMetaData.getTableName())
//        tableProps.put("colProps",domObj.getProperties())
//        rc.put(domObj.shortName, tableProps)
//        }
//      }
//    return rc
//    }
//
//  Map pavementTypes() {
//    def rc = [:]
//    def domainObjList = ApplicationHolder.application.getArtefacts("Domain") as List
//    domainObjList.each { domObj ->
//      if (domObj.shortName != "Cell" && domObj.shortName.endsWith("Cell")) {
//        def hdrCols = []
//        println "${domObj.getName()} getProperties: ${domObj.getProperties()}"
//        domObj.getProperties().each {
//           hdrCols << it.name
//        }
//        rc.put(domObj.getName(),hdrCols)
//      }
//    }
//    return rc
//  }
//
}