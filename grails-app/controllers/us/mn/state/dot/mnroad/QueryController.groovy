package us.mn.state.dot.mnroad

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.converters.deep.JSON
import groovy.sql.Sql
import us.mn.state.dot.mnroad.MrUtils
//import us.mn.state.dot.mnroad.SensorRecord
import us.mn.state.dot.mnroad.Query

class QueryController {

  def sessionFactory
  def grailsApplication
  def dataSource
  def queryService
    
  def index = { redirect(action:list,params:params) }

  Map getDataMap(aline)
     {
     def map = [:]
     def columnArray = aline.split(",")
     for (item in columnArray)
     {
         def nvpair = item.split(":")
         def value = nvpair[1]
         if (nvpair[0].trim() == "owner") {
             def owner = nvpair[1].split("|")
             def o = Facility.FindWhere(owner[1])
             value = o
         }
         map[nvpair[0]] = value
     }
     return map
   }

  def ddl = {
    try {
      String fileName = "mnroad_"+MrUtils.grailsEnvironment()+".ddl"
      File f = new File(fileName)
      groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
      def schema = "MNROAD"
      def ddl = ""
      println("Writing "+MrUtils.grailsEnvironment()+" schema to file ")
      (ApplicationHolder.application.getArtefacts("Domain") as List).each {
        def natName = it.naturalName.toUpperCase()
        def tbl_name = natName.replace(' ','_')
        if ( // if this class is not a subclass
          (props(it.name).discriminatorColumnName == null) ||
          // or if it is a superclass
          (it.subClasses.size() > 0)
        )
        {
          def tname = props(it.name).tableName?props(it.name).tableName.toUpperCase():tbl_name
          def s = "select to_char(dbms_metadata.get_ddl('TABLE', ${tname},${schema})) from dual"
          ddl = sql.firstRow(s)[0].toString() + ";"
          println(it)
          f.append(ddl)
        }
      }
      }
    catch(Exception e){
      println e.message
    }
  }

        def createInserts = {
          def queryInstance = new Query()
          queryInstance.properties = params
// Careful... this will delete all data
//      (ApplicationHolder.application.getArtefacts("Domain") as List).each {
//        list = it.newInstance().list().delete()
//      }
      def domainObjList = ApplicationHolder.application.getArtefacts("Domain") as List
      String classOfTable = ""
      def exportData = grailsApplication.config.exportOrder["data"]
      exportData.each {
          def inserts = []
          inserts = listInserts("us.mn.state.dot."+ it)
//          inserts = listCsv(it)
          def sz = inserts.size()
          println "$it: ${sz} rows"
          if (sz > 0) {
            File f = new File('new.txt')
            f.withWriterAppend{ file ->
              file.writeLine(it)
              inserts.each { line ->
                 file.writeLine(line)
              }
            }
            f.append("--\n")
          }
        }
        return ['queryInstance':queryInstance]
        }

      private List listCsv(String classOfTable) {
        def rc = []
        Class clazz = ApplicationHolder.application.getClassForName(classOfTable)
        if (clazz == null) {
          println "No class for Name: $classOfTable"
        }
        def cls = clazz.newInstance()
        if (!cls || (classOfTable.equals("Cell")))  {// class Cell is extended
          return rc
        }
        def meta = props(classOfTable)
        def columnArraySize = meta["columns"].size()
        def idx = 0
        def columnList = ""
        meta["columns"].each {
          def type = meta["types"].toArray()[idx].name
          if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet"))  {
            columnList += it
            if ((idx+1) < columnArraySize)
              columnList += ", "
          }
          ++idx
        }
        rc << columnList
        def c = cls.list()
        c.each { fc ->
          def valueList = ""
          if (meta["discriminatorColumnName"] != null) {
            valueList += "'${fc.getClass().name}',"
          }
          idx = 0
          meta["fields"].each {
            def type = meta["types"].toArray()[idx].name
            def val = fc[it]
            def s = ""
            if (type.equals("java.util.Date")) {
              s = val
            }
            else {
              s = formatCsvValue(val, type,!meta["fks"].toArray()[idx].equals(""))
            }
            if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
              valueList += s
              if ((idx+1) < columnArraySize)
                valueList += ", "
            }
            ++idx
          }
          rc <<  valueList

        }
        return rc
      }

    private List listInserts(String classOfTable) {
      def rc = []
      Class clazz = ApplicationHolder.application.getClassForName(classOfTable)
      if (clazz == null) {
        log.error "No class for Name: $classOfTable"
        return rc
      }
      def cls = clazz.newInstance()
      if (!cls || (classOfTable.equals("Cell"))) // class Cell is extended
        return rc

    def c = cls.list()
    if (c.size() > grailsApplication.config.maxExportRowsPerTable) {
      println "Data for class $classOfTable skipped. ${c.size()} rows."
      return rc
    }
    def vs = ") values ("
    def term = ");"
    def meta = props(classOfTable)
    def columnArraySize = meta["columns"].size()
    c.each {fc ->
      def columnList = ""
      def valueList = ""
      if (meta["discriminatorColumnName"] != null) {
        columnList += "class, "
        valueList += "'${fc.getClass().name}',"
      }
      def idx = 0
      meta["columns"].each {
        def type = meta["types"].toArray()[idx].name
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet"))  {
//          def val = fc[meta["fields"][idx]]
          columnList += it
          if ((idx+1) < columnArraySize)
            columnList += ", "
        }
        else {
          String xcls = "";
        }
        ++idx
      }
      def is = "insert into ${meta['tableName']} ($columnList"
      is += vs
      idx = 0
      meta["fields"].each {
        def type = meta["types"].toArray()[idx].name
        def val = fc[it]
        String s = formatSqlValue(val, type,!meta["fks"].toArray()[idx].equals(""))
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
          valueList += s
          if ((idx+1) < columnArraySize)
            valueList += ", "
        }
        ++idx
      }
      is += valueList
      is += term
      rc.add(is)
    }
    return rc
  }

/**
    This groovy script will take a CSV file that represents the data
    in a database. The first row should be the name of the columns and
    all other rows should represent the data you wish to insert.
*/

def csvToInserts(def tn, def fn) {
  // Setup basic information  from http://gist.github.com/284147
  numColumns = 6
  tableName = "TABLE_NAME"
  fileName = "C:\\file\\path"

  i = 0
  new File(fileName).splitEachLine(',') { fields ->
      if(i++ == 0) {
          columns = "(${fields.join(',')})"
      } else {
          values = fields.collect { it.equals("sysdate") ? it : ("'" + it + "'") }
          println "INSERT INTO ${tableName}${columns} VALUES (${values.join(',')});"
      }
  }
}

  private String formatSqlValue(Object val, String typ, Boolean useId) {
    String rc = ""
    if (typ.equals("java.util.Date")) {
      rc = val == null?"null":"to_timestamp('" + val + "','yyyy-mm-dd hh24:mi:ss.ff3')"
    }
    else if (typ.equals("java.lang.String")) {
      if (val == null)
        return "''"
      if (val.contains("\'")) {
        def s = val
        def n = s.replace("\'","\'\'")
        rc = "'$n'"
      }
      else
        rc = "'$val'"
    }
    else if (typ.equals("java.lang.Long")) {
      rc = val == null?"":val
    }
    else if (typ.equals("java.lang.Double")) {
      rc = val == null?"":val
    }
    else if (typ.equals("java.lang.Boolean")) {
      int b = (val?1:0)
      rc = val == null?"":"$b"
    }
    else if (typ.equals("java.lang.Integer")) {
      rc = val == null?"":val
    }
    else {
      if (useId)
        rc = ""+val?.id
      else
        println "Unknown type: $typ"
    }
    return rc
  }

    private String formatCsvValue(Object val, String typ, Boolean useId) {
      String rc = ""
      if (typ.equals("java.util.Date")) {
        rc = val == null?"null":"to_timestamp('" + val + "','yyyy-mm-dd hh24:mi:ss.ff3')"
      }
      else if (typ.equals("java.lang.String")) {
        if (val == null)
          return ""
        if (val.contains("\'")) {
          def s = val
          def n = s.replace("\'","\'\'")
          rc = "'$n'"
        }
        else
          rc = "'$val'"
      }
      else if (typ.equals("java.lang.Long")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Double")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Boolean")) {
        int b = (val?1:0)
        rc = val == null?"":"$b"
      }
      else if (typ.equals("java.lang.Integer")) {
        rc = val == null?"":val
      }
      else {
        if (useId)
          rc = ""+val?.id
        else
          println "Unknown type: $typ"
      }
      return rc
    }

  private def props(topLevelName) {
    def rc = [:]
    //get the domain class object named topLevelName
    def domainClass = grailsApplication.getClassForName(topLevelName)
    //get hibernage meta data object
    def hibernateMetaClass = sessionFactory.getClassMetadata(domainClass)
    //get the table name mapped to the DomainClass domain class
    def tableName = hibernateMetaClass.getTableName()
    rc.put("tableName",tableName)
    rc.put("discriminatorColumnName",hibernateMetaClass.discriminatorColumnName)
    //create a new GrailsDomainClass object for the DomainClass
    def grailsDomainClass = new DefaultGrailsDomainClass(domainClass)
    //get the domain properties defined in Domain Class
    //grailsDomainClass.getProperties() is returns GrailsDomainClassProperty[] objects
    def domainProps = grailsDomainClass.getProperties()
    def fields = []
    def types = []
    def columns = []
    def fks = []
    domainProps.each {prop ->
      //get the property's name
      String propName = prop.getName()
      def columnProps = null
      try {
        columnProps = hibernateMetaClass.getPropertyColumnNames(propName)
      }
      catch (org.hibernate.MappingException hme) {
        //println "Table: $tableName, MappingException message: ${hme.message}."
        // caused by having closures in domain classes
      }
      if (columnProps && columnProps.length > 0) {
        //get the column name, which is stored into the first array
        def columnName = columnProps[0]
        String pn = propName.toString()
        fields.add(pn)
        types.add(prop.type)
        columns.add(columnName)
        if (!prop.isOwningSide() && prop.isManyToOne() && columnName.endsWith("_id"))   {
          fks.add(pn)
        }
        else if (prop.isOneToMany() && prop.referencedPropertyType != null)   {
          fks.add(prop.referencedPropertyType.name)
        }
        else if (prop.isOneToOne())   {
          fks.add(prop.type)
    }
    else {
          fks.add("")
        }
      }
    }
  rc.put("fields",fields)
  rc.put("types",types)
  rc.put("columns",columns)
  rc.put("fks",fks)
  return rc
    }

  def save = {
    def queryInstance = new Query(params)
    if(!queryInstance.hasErrors() && queryInstance.save()) {
        flash.message = "Query ${queryInstance.id} created"
        redirect(action:show,id:queryInstance.id)
    }
    else {
        render(view:'create',model:[queryInstance:queryInstance])
    }
    }
}

/*
      File f = new File('new.txt')
      def exportOrderData = grailsApplication.config.exportOrder["data"]
      exportOrderData.each {
          def inserts = listInserts(it)
          println "$it: ${inserts.size()} rows"
          if (inserts.size() > 0) {
            f.withWriterAppend{ file ->
              inserts.each { line ->
                 file.writeLine(line)
              }
            }
            f.append("--\n")
          }
        }


  private List listInserts(String classOfTable) {
    def rc = []
    Class clazz = ApplicationHolder.application.getClassForName(classOfTable)
    if (clazz == null) {
      println "No class for Name: $classOfTable"
    }
    def cls = clazz.newInstance()
    if (!cls || (classOfTable.equals("Cell"))) // class Cell is extended
      return rc

    def c = cls.list()
    if (c.size() > grailsApplication.config.maxExportRowsPerTable) {
      println "Data for class $classOfTable skipped. ${c.size()} rows."
      return rc
    }
    def vs = ") values ("
    def term = ");"
    def meta = props(classOfTable)
    def columnArraySize = meta["columns"].size()
    c.each {fc ->
      def columnList = ""
      def valueList = ""
      if (meta["discriminatorColumnName"] != null) {
        columnList += "class, "
        valueList += "'${fc.getClass().name}',"
      }
      def idx = 0
      meta["columns"].each {
        def type = meta["types"].toArray()[idx].name
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet"))  {
          def val = fc[meta["fields"][idx]]
          columnList += it
          if ((idx+1) < columnArraySize)
            columnList += ", "
        }
        else {
          String xcls = "";
        }
        ++idx
      }
      def is = "insert into ${meta['tableName']} ($columnList"
      is += vs
      idx = 0
      meta["fields"].each {
        def type = meta["types"].toArray()[idx].name
        def val = fc[it]
        String s = formatSqlValue(val, type,!meta["fks"].toArray()[idx].equals(""))
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
          valueList += s
          if ((idx+1) < columnArraySize)
            valueList += ", "
        }
        ++idx
      }
      is += valueList
      is += term
      rc.add(is)
    }
    return rc
    }
*/
/*
    def sensorReadings = {
      //def model = "HC"
      int cell = 39
      def from = "11-01-2008"
      def to = "01-22-2009"
      println "Searching for cell ${cell} from ${from} to ${to}"
      Date dFrom
      Date dTo
      try {
        dFrom = MrUtils.getFormattedDate(from, 'MM-dd-yyyy')
      } catch (Exception e) {
        log.error("rowPerSensorPerDayByHour - Invalid date; ${from}")
      }
      try {
        dTo = MrUtils.getFormattedDate(to, 'MM-dd-yyyy')
      } catch (Exception e) {
        log.error("rowPerSensorPerDayByHour - Invalid date; ${to}")
      }
      List cells = Cell.findAllByCellNumber(cell)
      if (cells.size() < 1) {
        log.error("rowPerSensorPerDayByHour - Cell, (${cell}) not found.")
      }
      Sql sql = new groovy.sql.Sql(dataSource)
      def q = "select distinct model from sensor where cell = ? order by cell"
      boolean fail = false
      sql.eachRow(q, [cell]) { row ->
        try {
          println "$row.model, $cell, $dFrom, $dTo"
          List days = queryService.rowPerSensorPerDayByHour(row.model, cell, dFrom, dTo)
//          days.each {
//            println it
//          }
          println "Result: $days.size rows."
        } catch (Exception e) {
          println "${e.message}"
        }
      }
      println "Search ended."
    }

    // the delete, save and update actions only accept POST requests
    static def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        //render Facility.get(1074) as JSON
       [ queryInstanceList: Query.list( params ) ]
    }

    def show = {
        def queryInstance = Query.get( params.id )

        if(!queryInstance) {
            flash.message = "Query not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ queryInstance : queryInstance ] }
    }

    def delete = {
        def queryInstance = Query.get( params.id )
        if(queryInstance) {
            queryInstance.delete()
            flash.message = "Query ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Query not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def queryInstance = Query.get( params.id )

        if(!queryInstance) {
            flash.message = "Query not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ queryInstance : queryInstance ]
        }
    }

    def update = {
        def queryInstance = Query.get( params.id )
        if(queryInstance) {
            queryInstance.properties = params
            if(!queryInstance.hasErrors() && queryInstance.save()) {
                flash.message = "Query ${params.id} updated"
                redirect(action:show,id:queryInstance.id)
            }
            else {
                render(view:'edit',model:[queryInstance:queryInstance])
            }
        }
        else {
            flash.message = "Query not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }
*/
/*
  static int level = 0
  private void listInserts(def inst, String classOfTable, List rc) {
    level ++
    if (level < 3)
      println "$inst, $classOfTable, ${rc.size()}"
    def clazz = ApplicationHolder.application.getClassForName(classOfTable)
    if (clazz == null) {
      println "No class for Name: $classOfTable"
    }
    def cls = clazz.newInstance()
    if (!cls || (classOfTable.equals("Cell"))) {// class Cell is extended
      println "$inst $classOfTable"
      return
    }
    def c
    if (inst != null) {
      c = inst
    }
    else {
      c = cls.list()
    }

    if (c.size() > grailsApplication.config.maxExportRowsPerTable) {
      println "Data for class $classOfTable skipped. ${c.size()} rows."
      return
    }
    def vs = ") values ("
    def term = ");"
    def meta = props(classOfTable)
    def columnArraySize = meta["columns"].size()
    int dups = 0
    c.each {fc ->
      def columnList = ""
      def valueList = ""
      if (meta["discriminatorColumnName"] != null) {
        columnList += "class, "
        valueList += "'${fc.getClass().name}',"
        meta = props(fc.getClass().name)
        columnArraySize = meta["columns"].size()
      }
      def idx = 0
      if (level < 4)
        println "$fc"
      meta["columns"].each {
        def type = meta["types"].toArray()[idx].name
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet"))  {
          def val = fc[meta["fields"][idx]]
          columnList += it
          if ((idx+1) < columnArraySize)
            columnList += ", "
        }
        else {
          String xcls = "";
        }
        ++idx
      }
      def is = "insert into ${meta['tableName']} ($columnList"
      is += vs
      idx = 0
      meta["fields"].each {
        def type = meta["types"].toArray()[idx].name
        def val = fc[it]
        String s = formatSqlValue(val, type,!meta["fks"].toArray()[idx].equals(""))
        if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
          valueList += s
          if ((idx+1) < columnArraySize)
            valueList += ", "
        }
        else {
          String xcls = "";
          val.each {
            xcls = it.metaClass.theClass.name
            listInserts(val, xcls,rc)
          }
        }
        ++idx
      }
      is += valueList
      is += term
      if (!rc.contains(is))
        rc.add(is)
      else {
        dups++
      }
        }

    if (level < 3)
      println "dups: $dups"
    level --
//    if (inst == null) {
    if (classOfTable.equals("RoadSection")){
      int sz = rc.size()
      if (sz > 0) {
        File f = new File('new.txt')
        f.withWriterAppend{ file ->
          rc.reverse().each { line ->
             file.writeLine(line)
          }
        }
        rc.clear()
        f.append("--\n")
        println "$sz lines appended for $inst"
      }

    }
    return
  }
*/
