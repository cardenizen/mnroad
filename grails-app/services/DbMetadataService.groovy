import groovy.sql.Sql

import us.mn.state.dot.mnroad.SimpleXlsSlurper
import org.apache.poi.ss.util.CellReference
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.DbColumnAttr
import us.mn.state.dot.mnroad.MrUtils

import us.mn.state.dot.mnroad.distress.Distress
import us.mn.state.dot.mnroad.Lane
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import us.mn.state.dot.SqlDataFormat
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter

class DbMetadataService {

  static transactional = true
  def dataSource
  def sessionFactory
  def grailsApplication
  def messageSource

  public static def colMetaNames = ['names','types','nullables','numDistinctValues','numNulls']

  /**
   * Finds the cell type (PccCell,AggCell,HmaCell or CompositeCell) from a cellNumber and date
   * @param cellNumber  The cell number
   * @param asOf        The date
   * @return String     PccCell,AggCell,HmaCell or CompositeCell 
   */
  String cellType(int cellNumber, Date asOf) {
    def rc = ""
    def q = "select CELL_TYPE FROM MNR.CELL_TYPES C WHERE CELL=? AND FROM_DATE-60 < ? AND ? < TO_DATE"
    try {
    Sql sql = new groovy.sql.Sql(dataSource)
    def dt1 = new java.sql.Date(asOf.getTime())
    def dt2 = new java.sql.Date(asOf.getTime())
    def fr = sql.firstRow(q.toString(), [new BigDecimal(cellNumber),dt1,dt2])
    if (fr) {
      rc = fr.CELL_TYPE
    }
    } catch (Exception e) {
      log.error e
    }
    return rc
  }

  /**
   * Find a list of table names
   * @param owner             The schema to which the table belongs
   * @param nameLikeFilter    Filter includes
   * @param nameNotLikeFilter Filter excludes
   * @return A map; key - Table name, value = Number of rows in the table
   */
  Map tableNames(String owner, String nameLikeFilter, String[] nameNotLikeFilter) {
    def rc = [:]
    def session = RequestContextHolder.currentRequestAttributes().getSession()
    if (!session.tableNames) {
      if (owner) {
        rc = getTableNames(owner, nameLikeFilter, nameNotLikeFilter)
      }
      session.tableNames = rc
    } else {
      rc = session.tableNames
      if (!rc.keySet().toArray()[0].startsWith(nameLikeFilter[0..-2])) {
        rc = getTableNames(owner, nameLikeFilter, nameNotLikeFilter)
        session.tableNames = rc
      }
      def hasFilter = false
      rc.keySet().each {
        if (it.toString().startsWith(nameLikeFilter))
        {
          hasFilter = true
        }
        def count = rc.get(it)
        println "${it}:${count}"
      }
    }
    return rc
  }

  protected boolean matchesFilter(def tnm, nameLikeFilter) {
    boolean rc = false
    
    return rc
  }
  protected Map getTableNames(Serializable owner, String nameLikeFilter, String[] nameNotLikeFilter) {
    def names = [:]
    Sql sql = new Sql(dataSource)
    def parms = [owner, nameLikeFilter]
        def q = "SELECT TABLE_NAME,TABLESPACE_NAME FROM ALL_TABLES WHERE OWNER=? AND TABLE_NAME LIKE ?"
        def s = " AND TABLE_NAME NOT LIKE ?"
        def orderBy = " ORDER BY TABLE_NAME"
        for (def nls in nameNotLikeFilter) {
           parms << nls
          q += s
        }
        q += orderBy
        sql.eachRow(q.toString(), parms) { rs ->
          def cls = classForTable(rs.TABLE_NAME)
      if (cls && cls.size() > 0 && cls.get(0).clazz)
        names.put(rs.TABLE_NAME, sql.firstRow("SELECT COUNT(*) NUMROWS FROM ${owner}.${rs.TABLE_NAME}".toString()).NUMROWS)
        }
    return names
  }

  /**
   * Create a map of the basic column attributes for a table
   * @param owner     The schema name
   * @param tableName The table name
   * @return A map; key - <code>us.mn.state.dot.mnroad.DbColumnAttr</code> enum
   *  A value - Array of appropriate types
   */
  Map allTabColumns(String owner, String tableName) {
    Sql sql = new groovy.sql.Sql(dataSource)
    def cid = []
    def name = []
    def type = []
    def length = []
    def nullable = []
    def numDistinct = []
    def numNull = []
    def mdq = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_PRECISION,DATA_SCALE,NULLABLE,COLUMN_ID,DEFAULT_LENGTH,NUM_DISTINCT,NUM_NULLS FROM ALL_TAB_COLUMNS WHERE OWNER=? AND TABLE_NAME=? ORDER BY COLUMN_ID"
    sql.eachRow(mdq.toString(), [owner.toUpperCase(),tableName.toUpperCase()]) { meta ->
      cid << "${meta.COLUMN_ID}".toString()
      name << "${meta.COLUMN_NAME}".toString()
      type << "${meta.DATA_TYPE}".toString()
      nullable << "${meta.NULLABLE}".toString()
      length << "${meta.DATA_LENGTH}".toString()
      numDistinct << "${meta.NUM_DISTINCT}".toString()
      numNull << "${meta.NUM_NULLS}".toString()
    }
    def attrs = [:]
    attrs.put(DbColumnAttr.NAME, name)
    attrs.put(DbColumnAttr.TYPE, type)
    attrs.put(DbColumnAttr.LENGTH, length)
    attrs.put(DbColumnAttr.NULLABLE, nullable)
    attrs.put(DbColumnAttr.NUMDISTINCT, numDistinct)
    attrs.put(DbColumnAttr.NUMNULL, numNull)
    return attrs
  }

  String selectStatement(String tableName, Map attributeMap, String defaultDateFormat) {
    def rc = "SELECT * FROM MNR.${tableName} "
    def whereClause = whereClause(tableName, attributeMap, defaultDateFormat)
    return "${rc} ${whereClause}".toString()
  }

  String whereClause(String tableName, Map attributeMap, String defaultDateFormat) {
    def md = allTabColumns('MNR',tableName)
    def wc = []
    attributeMap.keySet().eachWithIndex { key, i ->
      def name = ""
      def v = "${attributeMap.get(key)}"
      def val = ""
      def type = md[DbColumnAttr.TYPE][i]
      if (type.startsWith("DATE") || type.startsWith("TIMESTAMP")) {
        name = "TO_CHAR(${key},'${defaultDateFormat}')"
        val = v?"='${v}' ":" IS NULL "
      }
      else if (type.startsWith("NUMBER")) {
        name=key
        val = v?"=${v}":" IS NULL "
      }
      else if (type.startsWith("VARCHAR2")) {
        name=key
        val = v?"='${v}'":" IS NULL "
      } else {
        name=key
        val=v
      }
      wc << "${name}${val}"
    }
    def whereClause = " WHERE ${wc.join(' and ')}"

    return "${whereClause}".toString()
  }

  Map expectedNullables(Object dom) {
    def nm = [:]
    def classMetaData = sessionFactory.getClassMetadata(dom.class)
    def constraints = dom.getProperties().get("constraints")
    for (constraint in constraints) {
       constraint.getValue().each {c ->
         def cn = classMetaData.getPropertyColumnNames(c.getPropertyName())[0]
          nm.put(cn.toUpperCase(),c.isNullable())
       }
    }
    return nm
  }
  /**
   * Given a domain class, returns a field name to column name map
   */
  Map fieldToColumnKeyMap(Class clazz) {
    def md = sessionFactory.getClassMetadata(clazz)
    def columnToFieldnameMap = columnToFieldNames(clazz)
    def keymap = [:]
    for (kc in md.keyColumnNames) {
      def fieldName = columnToFieldnameMap.find{it.key.toUpperCase()==kc.toUpperCase()}.value
      keymap.put(fieldName,kc.toString().toUpperCase())
    }
// This produces almost the same result.  It adds id->mnroad_id    
//    def fieldToColumnKeyMap = [:]
//    def fieldProps = getFieldProps(clazz)
//    fieldProps.keySet().each {
//      if (fieldProps.get(it).isKey) {
//        fieldToColumnKeyMap.put(it.toString(), fieldProps.get(it).columnName)
//      }
//    }
    return keymap
  }

  List naturalKeyColumnNames(clazz) {
    def hmd=sessionFactory.getClassMetadata(clazz)
    def tn = hmd.getTableName()[4..-1]
    Map nkm = grailsApplication.config.distressNaturalKeyMap
    def al = []
    if (clazz.getSimpleName().startsWith('Distress')) {
      nkm.get(tn).split(',').each {
        al << it
      }
    }
    return al
  }

  Map getFieldProps(clazz, colHeaders) {
    def fieldProps = [:]
    def domainProps=(new DefaultGrailsDomainClass(clazz)).getProperties()
    def hmd=sessionFactory.getClassMetadata(clazz)
    domainProps.eachWithIndex{prop, i ->
      def columnProps = null
      def isKey
      try {
        columnProps = hmd.getPropertyColumnNames(prop.getName())
        if (columnProps && columnProps.size() >  0)
        isKey = (hmd.keyColumnNames as List).contains(columnProps[0])
      }
      catch (org.hibernate.MappingException hme) {
        if (hme.message != 'unknown property: version')
          log.info "Table: ${hmd.getTableName()} Field: $prop.getName(), MappingException message: ${hme.message}."
        // caused by having closures in domain classes or a missing
        // assumed domain property like 'version'
      }
      def nks = naturalKeyColumnNames(clazz)
      if(columnProps && columnProps.length>0){
      //get the column name, which is stored into the first array
        def columnName=columnProps[0].toUpperCase()
        def fieldProp = [:]
        fieldProp.put('clazz',prop.type.name)
        fieldProp.put('columnName',columnName)
//        fieldProp.put('naturalName',prop.naturalName)
        fieldProp.put('isKey',isKey) // from composite keys
        fieldProp.put('isNumber',['java.lang.Integer','java.lang.Long','java.lang.Float','java.lang.BigInteger','java.math.BigDecimal','java.lang.Double'].contains(prop.type.name))
        // Cannot create columnIndex here because columnProps are object based and sorted by name
        // Column index refers to the columns in the spreadsheet
        fieldProp.put('columnIndex', 0)
        fieldProp.put('isNaturalKey', nks.contains(columnName))
        fieldProps.put(prop.name, fieldProp)
      }
      for (fprop in fieldProps) {
        int j = 0
        for (cname in colHeaders) {
          if (fprop.getValue()['columnName'].toUpperCase() == cname) {
            fprop.getValue().put('columnIndex',j+1)
          }
          j++
        }
      }

    }
  return fieldProps
  }

  List keyColumnNames(tableName) {
    def rc = []
    def artefact = classForTable(tableName)
    def hmd=sessionFactory.getClassMetadata(artefact.get(0).clazz)
    hmd.getKeyColumnNames().each { columnName ->
      rc << columnName.toUpperCase()
    }
    return rc
  }

  /**
   * Constructs a fieldName/fieldValue map which can be used to find a DB row
   * @param clazz The Domain class name
   * @param row   The spreadsheet row that hold the key values.
   * @return      A fieldName -> fieldValue map
   */
  //private
  Map keyValueMap(clazz, row, slurper) {
    slurper.sheets(0) // set the current sheet
    def hdrs = slurper.headers(true)  // A list of column names including the first
    def keymap = fieldToColumnKeyMap(clazz)
    def keyValueMap = [:]
    keymap.keySet().each { // for each field in keymap
      def v = keymap.get(it) // get the column name (in a map entry)
      // get its datatype from the dbColumns sheet
      def type = slurper.type(v)//dbMeta().find {it.key == v}.value.get(0)
      if (type) {
        def colIdx = hdrs.indexOf(v.value.toString()) // get the cell index
        def sval = row.getCell(colIdx).toString()
        switch (type) {
          case 'NUMBER':
            keyValueMap.put(it, sval.asTypeSafeNumber(java.math.BigDecimal))
            break
          case 'VARCHAR2':
            keyValueMap.put(it, sval)
            break
          case 'DATE':
            keyValueMap.put(it, new java.sql.Date(sval.asTypeSafeDate('yyyy-MM-dd').getTime()))
          break
          default:
            keyValueMap.put(it, sval)
            break
        }
      }
    }
    return keyValueMap
  }

  Map updtCodeMap(def sheet) {
    def ir = [:] // Gather the row update codes and indices
    def nrows = sheet.getLastRowNum()
    for (def rowIdx = 1; rowIdx <= nrows; rowIdx++) { // skip the headers row (index 0)
      def row = sheet.getRow(rowIdx)
      def updtCode = row.getCell(0).toString().trim().toUpperCase()
      if (updtCode && !updtCode.equals("null")
               && updtCode != 'FIRST 20 ROWS. USE SQUIRREL + COPY/PASTE TO ADD MORE.'
      ){
        ir.put(rowIdx, updtCode)
      }
    }
    return ir
  }

  Lane findLane(cellNum, laneName, day) {
    def rc
    def ln = Lane.createCriteria().list(){
      eq ("name", laneName)
      cell {
        eq ("cellNumber", cellNum)
      }
    }
    if (ln) {
      if (ln.size()>1) {
        ln.each {aLane ->
          def fdt = (aLane.cell.earliestLayerDate() < aLane.cell.constructionEndedDate)?aLane.cell.earliestLayerDate():aLane.cell.constructionEndedDate
          def tdt = (aLane.cell.demolishedDate==null)?new Date():aLane.cell.demolishedDate
          if (day > fdt && day < tdt) {
            rc = aLane
          }
        }
      } else {
        rc = ln.toArray()[0]
      }
    }
  return rc
  }

  Boolean isNullable(Object dom, String columnName) {
    def rc = false
    def expectedNullables = expectedNullables(dom)
    if (expectedNullables.keySet().contains(columnName))
      rc = !expectedNullables.get(columnName)
    return rc
  }

  Map updateTable(String filename) {
    def rc = [:]
    def msgs = []
    def updts = []
    def nUpdates = 0
    def nDeletes = 0
    def nCreates = 0
    rc.put('M',msgs)
    rc.put('U',nUpdates)
    rc.put('D',nDeletes)
    rc.put('C',nCreates)
    rc.put('updates',updts)
    def slurper = new SimpleXlsSlurper(filename)
    // Check for all null allowed
    if (slurper.dbMeta().keySet().every {slurper.dbMeta().get(it)[2] == 'Y'}) {
      updts << "Table ${slurper.sheets(0).sheetName} accepts rows with no data. Give someone a good talkin-to."
    }
    // For now use only two sheets: data on the first (0) and metadata on the second
    def sheet = slurper.sheets(0)
    def headers = slurper.headers(false)
    def artefact = classForTable(sheet.sheetName)
    if (!artefact || artefact.size()== 0 || artefact.size() > 1) {
      msgs << "Table not found or not a simple domain class.".toString()
      rc.put('messages',msgs)
      return rc
    }
    def clazz = artefact.get(0).clazz
    def fieldProps = getFieldProps(clazz,slurper.headers(false))
    def colToFldNameMap = columnToFieldNames(clazz)
    def ir = updtCodeMap(sheet)
    if (ir.size()==0) {
      msgs << "No rows selected for update.".toString()
      rc.put('messages',msgs)
      return rc
    } 
    def keyField2ColMap = fieldToColumnKeyMap(clazz)
    // for each row in the spreadsheet with an unpdate code
    for (def rowIdx in ir.keySet()) {
      def row = sheet.getRow(rowIdx)
      def updtCode = ir.get(rowIdx)
      if (['C','U','D'].contains(updtCode))  { // just in case ...
        // construct the map used to find the DB row
        def keyField2ValueMap = keyValueMap(clazz, row, slurper)
        def fieldTypedValMap = field2TypedValueMap(rowIdx,row,msgs,slurper,colToFldNameMap,fieldProps)

        // Read the row
        def matrow
        try {
          if (clazz.getSimpleName().startsWith('Distress')) {
            Long id = new Long(keyField2ValueMap.get(keyField2ValueMap.keySet().toArray()[0]).longValue())
            matrow = Distress.get(id)
          } else {
          matrow = clazz.get(clazz.newInstance(keyField2ValueMap))
          }
        } catch (org.springframework.dao.InvalidDataAccessResourceUsageException idae) {
            msgs << "Row ${rowIdx}, ${idae.message}".toString()
        } catch (Exception e) {
          if (updtCode != 'C')
            msgs << "Row ${rowIdx}, ${e.message}".toString()
        }
        if (matrow == null && updtCode != 'C') {
          msgs << "(${CellReference.convertNumToColString(0)}:${rowIdx+1}): Row with key ${keyField2ValueMap} cannot be found - operation not possible.".toString()
        } else {
          switch (updtCode) {
            case 'C':
                def ni = clazz.newInstance(fieldTypedValMap)
                if (matrow) { // If we have a distress already linked to a lane
                  ni.lane = matrow.lane
                }  else { // Otherwise find the lane via cell number, lane description, and date
                  ni.lane = findLane(ni.cell, ni.laneDescr, ni.day)
                }
                if (!ni.lane) {
                  msgs << "$ssCell: Lane not found for ${ni.cell} ${ni.laneDescr} ${ni.day}"
                }
                if (ni.metaClass.hasProperty(ni,'createdBy'))
                  ni['createdBy'] = MrUtils.getUserName()
                if (ni.metaClass.hasProperty(ni,'lastUpdatedBy'))
                  ni['lastUpdatedBy'] = MrUtils.getUserName()
                if (ni.metaClass.hasProperty(ni,'dateCreated'))
                  ni['dateCreated'] = new java.sql.Date((new Date()).getTime())
                if (ni.metaClass.hasProperty(ni,'lastUpdated'))
                  ni['lastUpdated'] = new java.sql.Date((new Date()).getTime())
                def vr = ni.validate()
                if (!vr) {
                  ni.errors.allErrors.each {
                    def ssCell = "${CellReference.convertNumToColString(fieldProps[it.field]['columnIndex'])}:${rowIdx+1}"
                    msgs << "Cell $ssCell, "+messageSource.getMessage(it,Locale.getDefault())
                  }
                }
                else {
                  def cr = ni.save(flush:true)
                  if (cr.id)
                  nCreates++

                  if (nUpdates+nCreates+nDeletes < 10)
                   updts << "Row ${rowIdx+1} created."
                }
              break
            case 'D':
              if (matrow) {
                try {
                  matrow.delete(flush:true)
                  nDeletes++
                  if (nUpdates+nCreates+nDeletes < 10)
                    updts << "Row ${rowIdx+1} deleted."                  
                }
                catch (org.springframework.dao.DataIntegrityViolationException dive) {
                  def msg = dive.getMessage()
                  if (msg.contains('not-null property references a null or transient value')) {
                    def propName = msg.lastIndexOf('.').with {it != -1 ? msg[it+1..-1] : msg}
                    def objName = msg.lastIndexOf('.').with {it != -1 ? msg[0..<it] : msg}
                    objName = objName.lastIndexOf('.').with {it != -1 ? objName[it+1..-1] : objName}
                    def columnName = fieldProps[propName]['columnName']
                    def umsg = "Delete failed, Property ${propName} of ${objName} must not be null."
                    if (isNullable(matrow,columnName))
                        umsg += "DB claims ${columnName} can be null.'"
                    msgs << umsg
                  } else {
                    msgs << msg
                  }
                }
              }
              break
            case 'U':
              def sa = []
              headers.eachWithIndex {colName, idx ->
                def fieldName = colToFldNameMap.get(colName)
                def dbval = (!matrow || !matrow[fieldName] || matrow[fieldName].toString()=='null')?null:matrow[fieldName].toString()
                def cval = row.getCell(idx+1)
                def ssval = cval?cval.toString():null
                def type = slurper.type(colName)
                if (type == "DATE" && dbval && dbval.endsWith("00:00:00.0")) {
                  dbval = dbval.split(' ')[0]
                  def dt = ssval.asTypeSafeDate('dd-MMM-yyyy')
                  if (!dt)
                    dt = ssval.asTypeSafeDate('yyyy-MM-dd')
                  if (dt) {
                    ssval = new java.sql.Date(dt.getTime()).toString()
                  } else {
                    log.info "Unable to parse ${colName} date: ${ssval}."
                  }
                }
                // check if excel added a '.0' to an integer value
                if (fieldProps[fieldName]['isNumber']) {
                  if (ssval?.endsWith('.0')) {
                    ssval = ssval[0..-3] // lop off the .0
                  }
                  if (dbval?.endsWith('.0')) {
                    dbval = dbval[0..-3] // lop off the .0
                  }
                }
                if ((ssval && !ssval.equals(dbval))
                        || (!ssval && dbval && slurper.nullable(colName))
                ) {
                  if (fieldProps[fieldName]['isNaturalKey'] && dbval) {
                    msgs << "Cell ${CellReference.convertNumToColString(idx+1)}:${rowIdx+1}, values of natural keys should not be changed.".toString()
                  }
                  if ((keyField2ColMap.values() as List).contains(colName)) {
                    msgs << "Cell ${CellReference.convertNumToColString(idx+1)}:${rowIdx+1}, values of keys should not be changed.".toString()
                  } else {
                    switch (type) {
                      case "VARCHAR2":
                        matrow[fieldName] = ssval?:''
                        break
                      case "DATE":
                        matrow[fieldName]= ssval.asTypeSafeDate('yyyy-MM-dd')
                        break
                      case "NUMBER":
                        matrow[fieldName] = ssval.asTypeSafeNumber(Class.forName('java.math.BigDecimal'))
                        break
                      default:
                        break
                    }
                    sa << "$colName:${dbval}->${ssval}"
                  }
                }
              }
              if (sa && (nUpdates+nCreates+nDeletes < 10))
                updts << sa.join(',')
              if (matrow.metaClass.hasProperty(matrow,'modifiedBy')
                && matrow.metaClass.hasProperty(matrow,'dateModified')) {
                matrow['modifiedBy'] = MrUtils.getUserName()
                matrow['dateModified'] = new java.sql.Date((new Date()).getTime())
              }
              def sv = matrow.save(flush:true)
              if (matrow.hasErrors()) {
                msgs = []
                matrow.errors.allErrors.each {
                  def amsg = messageSource.getMessage(it,Locale.getDefault())
                  msgs << amsg
                }
              } else {
                if (sv) {
                  nUpdates++
                } else {
                  println ("${keyField2ColMap.values()} Update failed: ${matrow}")
                }
              }
              break // End Updates
            default:
              break
          }
        }
      } // updtCode is valid
       else {
        if (updtCode != 'NULL')
          msgs << "Line ${rowIdx+1}, invalid update code - ${updtCode}. Must be C(c), U(u), or D(d).".toString()
      }
    } // each row with an update code
    rc.put('M',msgs)
    rc.put('U',nUpdates)
    rc.put('D',nDeletes)
    rc.put('C',nCreates)
    rc.put('messages',msgs)
    rc.put('updates',updts)
    return rc
  }

  Map validateForUpdate(String filename) {
    def rc = [:]
    def msgs = []
    def updts = [:]
    def slurper = new SimpleXlsSlurper(filename)
    // Check for all null allowed
    if (slurper.dbMeta().keySet().every {slurper.dbMeta().get(it)[2] == 'Y'}) {
      updts.put(0,"Warning - Table ${slurper.sheets(0).sheetName} accepts rows with no data. Give someone a good talkin-to.".toString())
    }
    // For now use only two sheets: data on the first (0) and metadata on the second
    def sheet = slurper.sheets(0)
    //def headers = slurper.headers(false)
    def artefact = classForTable(sheet.sheetName)
    if (!artefact || artefact.size()== 0 || artefact.size() > 1) {
      msgs << "Table not found or not a simple domain class.".toString()
      rc.put('messages',msgs)
      return rc
    }
    def clazz = artefact.get(0).clazz
    def natKeyColumns = naturalKeyColumnNames(clazz)
    def fieldProps = getFieldProps(clazz,slurper.headers(false))
    def colToFldNameMap = columnToFieldNames(clazz)
    def ir = updtCodeMap(sheet)
    if (ir.size()==0) {
      msgs << "No rows selected for update.".toString()
      rc.put('messages',msgs)
      return rc
    }

    // for each row in the spreadsheet with an unpdate code
    for (def rowIdx in ir.keySet()) {
      def row = sheet.getRow(rowIdx)
      def updtCode = ir.get(rowIdx)
//      if (['C','U','D'].contains(updtCode))  { // just in case ...
        // construct the map used to find the DB row
        def keyField2ValueMap = keyValueMap(clazz, row, slurper)
        def fieldCharValMap = field2CharValueMap(rowIdx,row,msgs,slurper,colToFldNameMap)
        def fieldTypedValMap = field2TypedValueMap(rowIdx,row,msgs,slurper,colToFldNameMap,fieldProps)
        def updtCol = []
        def dbrow
        try {
          if (clazz.getSimpleName().startsWith('Distress')) {
            Long id = new Long(keyField2ValueMap.get(keyField2ValueMap.keySet().toArray()[0]).longValue())
            dbrow = Distress.read(id)
          } else {
          dbrow = clazz.read(clazz.newInstance(keyField2ValueMap))
          }
        } catch (org.springframework.dao.InvalidDataAccessResourceUsageException idae) {
            msgs << "Row ${rowIdx}, ${idae.message}".toString()
        } catch (Exception e) {
          if (updtCode != 'C')
            msgs << "Row ${rowIdx}, ${e.message}".toString()
        }
        if (dbrow == null && updtCode != 'C') {
          msgs << "(${CellReference.convertNumToColString(0)}:${rowIdx+1}): Row with key ${keyField2ValueMap} cannot be found - operation not possible.".toString()
        } else {
          switch (updtCode) {
            case 'C':
              def ni = clazz.newInstance(fieldTypedValMap)
              if (dbrow) {
                ni.lane = dbrow.lane
              } else { // Search for lanes with cell number ni.cell and lane name ni.laneDescr
                ni.lane = findLane(ni.cell, ni.laneDescr, ni.day)
              }
              if (ni.metaClass.hasProperty(ni,'createdBy'))
                ni['createdBy'] = MrUtils.getUserName()
              if (ni.metaClass.hasProperty(ni,'lastUpdatedBy'))
                ni['lastUpdatedBy'] = MrUtils.getUserName()
              if (ni.metaClass.hasProperty(ni,'dateCreated'))
                ni['dateCreated'] = new java.sql.Date((new Date()).getTime())
              if (ni.metaClass.hasProperty(ni,'lastUpdated'))
                ni['lastUpdated'] = new java.sql.Date((new Date()).getTime())
              def vr = ni.validate()
              if (!vr) {
                ni.errors.allErrors.each {
                  def ssCell = "${CellReference.convertNumToColString(fieldProps[it.field]['columnIndex'])}:${rowIdx+1}"
                  msgs << "Cell $ssCell, "+messageSource.getMessage(it,Locale.getDefault())
                }
              }
              updts.put(rowIdx,"Cell ${CellReference.convertNumToColString(0)}:${rowIdx+1}: Row will be created.")// Key ${keyField2ValueMap} will be ignored.".toString())
              break
            case 'D':
              if (dbrow == null) {
                msgs << "(${CellReference.convertNumToColString(0)}:${rowIdx+1}): Row with key ${keyField2ValueMap} cannot be found - delete not possible.".toString()
              } else {
                updts.put(rowIdx, "Delete row: ${keyField2ValueMap}")
              }
              break
            case 'U':
              if (dbrow == null) {
                msgs << "(${CellReference.convertNumToColString(0)}:${rowIdx+1}): Key columns ${keyField2ValueMap} may not be updated.".toString()
              } else {
                def sdf = new SimpleDateFormat("yyyy-MM-dd")
                fieldCharValMap.keySet().each {fieldName ->
                  if (fieldName && !grailsApplication.config.fieldsExcludedFromUpdate.contains(fieldName.toString())) {
                    def dbval = dbrow[fieldName]
                    dbval = (dbval==null || dbval == 'null')?'':"$dbval"
                    if  (dbval.endsWith('00:00:00.0')) {
                      def dbdt = dbval.split(' ')[0].asTypeSafeDate('yyyy-MM-dd')
                      dbval = dbdt?sdf.format(dbdt):""
                    }
                    def val = fieldCharValMap.get(fieldName)
                    def fp = fieldProps.find {it.key==fieldName}
                    def fpv = fp?fp.value:''
                    def ssval = (fpv && fpv.clazz=='java.util.Date')?validateDate(val):val
                    // check if excel added a '.0' to an integer value
                    if (fieldProps[fieldName].isNumber) {
                      if (ssval?.endsWith('.0')) {
                      ssval = ssval[0..-3] // lop off the .0
                      }
                      if (dbval?.endsWith('.0')) {
                        dbval = dbval[0..-3] // lop off the .0
                      }
                    }
                    if (dbval != ssval) {
                      def cn = (fieldTypedValMap.keySet() as List).indexOf(fieldName)
                      if (fieldProps[fieldName]['isNaturalKey'] && dbval) { // Allow a null natural key value to be populated
                        msgs << "${fieldName}, Cell ${CellReference.convertNumToColString(cn)}:${rowIdx+1}, values of natural keys should not be changed.".toString()
                      }
                      if (fieldProps[fieldName].isKey) {
                        msgs << "${CellReference.convertNumToColString(cn)}:${rowIdx+1}, ${fieldName} ${dbval} cannot be changed to ${ssval} - values of natural keys should not be changed.".toString()
                      } else {
                        def colName = fieldProps[fieldName]['columnName'].toUpperCase()
                        switch (slurper.type(colName)) {
                          case "VARCHAR2":
                            dbrow[fieldName] = ssval?:''
                            updtCol << "${fieldName}: ${dbval} -> ${ssval}"
                            break
                          case "DATE":
                            dbrow[fieldName]= ssval.asTypeSafeDate('yyyy-MM-dd')
                            updtCol << "${fieldName}: ${dbval} -> ${ssval}"
                            break
                          case "NUMBER":
                            def tsn = 0.0
                            if (ssval != '0')
//                              tsn =  ssval.asTypeSafeNumber(Class.forName('java.math.BigDecimal'))
                              tsn =  ssval.asTypeSafeNumber(Class.forName('java.lang.Double'))
                            if (tsn != null) {
                              dbrow[fieldName] = tsn
                              updtCol << "${fieldName}: ${dbval?:'null'} -> ${ssval}"
                            } else {
                              msgs << "Cell ${CellReference.convertNumToColString(cn)}:${rowIdx+1}, column $colName - Conversion of ${ssval} to a number failed."
                            }
                            break
                          default:
                            break
                        }
                      }

                    }
                  }
                }
                def vr = dbrow.validate()
                if (!vr) {
                  dbrow.errors.allErrors.each {
                    def ssCell = "${CellReference.convertNumToColString(fieldProps[it.field]['columnIndex'])}:${rowIdx+1}"
                    msgs << "Cell $ssCell, "+messageSource.getMessage(it,Locale.getDefault())
                  }
                }
                if (updtCol) {
                  updts.put(rowIdx, "Update Row ${rowIdx+1}: ${updtCol.join(' | ')}")
                }
              }
              break
            default:
              break
          }
        }
//      } else {
//        if (updtCode != 'NULL')
//          msgs << "Line ${rowIdx+1}, invalid update code - ${updtCode}. Must be C(c), U(u), or D(d).".toString()
//      }
    }
    def updates = []
    updts.values().each {
      updates << it
    }
    rc.put('messages',msgs)
    rc.put('updates',updates)
    return rc
  }

  Map field2CharValueMap(def rowIdx, def row, def msgs, def slurper, def colToFldNameMap) {
    def fieldValMap = [:]
    def lastCellNum = row.getLastCellNum()
    DataFormatter formatter = new DataFormatter();
    for (def cn = 2; cn <= lastCellNum; cn++) {
      def colName = slurper.headers(false)[cn-2]
      def cell = row.getCell(cn-1)
      if ((cell ||(cell==null && slurper.nullable(colName)))) { // ignore rows with no c,u,or d action or null cell
//        def val = (cell.toString()=="null")?"":cell.toString()
        def val = (cell.toString()=="null")?"":"${formatter.formatCellValue(cell)}".toString()
        def fieldName = colToFldNameMap.get(colName)
        def type = slurper.type(colName)
        def len = slurper.length(colName)
        def nullable = slurper.nullable(colName)
        if (!nullable && val?.size()==0) {
          msgs << "Database requires non-null value for column ${colName}: cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
          } else {
            switch (type) {
              case 'NUMBER':
              def v = val.asTypeSafeNumber(Double)   // see Bootstrap.groovy for implementation
              if (!nullable && !v) {
                  msgs << "Number conversion format error in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
              break
              case 'VARCHAR2':
              if (val.size() > len.toString().asTypeSafeNumber(Integer)) {
                  msgs << "Character data length (${cell.toString().size()}) exceeds database maximum  in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString() //(${cols[cn-1]})
                }
              if (val.size()==0 && !nullable) {
                msgs << "Column ${colName} cannot be set to null in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
              break
              case 'DATE':
                if (val && validateDate(val)== "") {
                  msgs << "Date conversion format error in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
              break
              default:
                break
            }
            fieldValMap.put((fieldName), val)
          }
      }
    }
  return fieldValMap
  }

  Map field2TypedValueMap(def rowIdx, def row, def msgs, def slurper, def colToFldNameMap, def fieldProps) {
    def fieldValMap = [:]
    def lastCellNum = row.getLastCellNum()
    DataFormatter formatter = new DataFormatter();
    for (def cn = 2; cn <= lastCellNum; cn++) {
      def colName = slurper.headers(false)[cn-2]
      
//      if (!colToFldNameMap.keySet().contains(colName)) {
//        msgs << "Column name from spreadsheet (${colName}) not found in table metadata: ${colToFldNameMap.keySet()}"
//      }
      def cell = row.getCell(cn-1)
      if ((cell || (cell==null && slurper.nullable(colName)))) { // ignore rows with no c,u,or d action or null cell
        //CellReference ref = new CellReference(rowIdx,cn-1);
        //println "$cn The value of ${ref.formatAsString()} is ${formatter.formatCellValue(cell)}"
        def val = (cell.toString()=="null")?"":"${formatter.formatCellValue(cell)}".toString()
        def fieldName = colToFldNameMap.get(colName)
        def type = slurper.type(colName)
        def len = slurper.length(colName)
        def nullable = slurper.nullable(colName)
        if (!nullable && val?.size()==0) {
          msgs << "Database requires non-null value for column ${colName}: cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
          } else {
            def cval = val
            switch (type) {
              case 'NUMBER':
              def fieldType = fieldProps[fieldName]
              def v
              if (fieldType) {
                v = cval.asTypeSafeNumber(Class.forName(fieldType.clazz))
                }
              if (!nullable && !v) {
                  msgs << "Number conversion format error in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
              else {
                val = cval.asTypeSafeNumber(Double)
              }
              break
              case 'VARCHAR2':
              if (cval.size() > len.toString().asTypeSafeNumber(Integer)) {
                  msgs << "Character data length (${cell.toString().size()}) exceeds database maximum  in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString() //(${cols[cn-1]})
                }
              else if (cval.size()==0 && !nullable) {
                msgs << "Column ${colName} cannot be set to null in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
              break
              case 'DATE':
                if (cval && validateDate(cval)== "") {
                  msgs << "Date conversion format error in cell (${CellReference.convertNumToColString(cn)}:${rowIdx+1}).".toString()
                }
                else {
                  val = cval.asTypeSafeDate('yyyy-MM-dd')
                }
              break
              default:
                break
            }
            fieldValMap.put((fieldName), val)
          }
      }
    }
  return fieldValMap
  }

  String validateDate(String val) {
    String rval = ""
    def fmt = (val.size()>3 && val[2]=='-'&& val[6]=='-' && val.size()==11)?'dd-MMM-yyyy':'yyyy-MM-dd'
    def todateFmt = fmt
    if (val.size() > 10)
      todateFmt = fmt + ' hh:mm:ss.S'
    if (todateFmt) {
      Date ssDate = val.asTypeSafeDate(todateFmt)
      if (!ssDate)  {
        ssDate = val.asTypeSafeDate(fmt)
      }
      if (ssDate) {
        def ffmt = 'yyyy-MM-dd'
        if (val.size() > 11)
          ffmt = ffmt + ' hh:mm:ss.S'
        def sdf = new SimpleDateFormat(ffmt)
        rval = sdf.format(ssDate)
      }
    }
    return rval
  }

  def classForTable(String tableName) {
    def dol = grailsApplication.getArtefacts("Domain") as List
    def ddol = dol.findAll {
      sessionFactory.getClassMetadata(it.clazz).getTableName().substring(grailsApplication.config.currentSchema.size()+1).toUpperCase() == tableName.toUpperCase()
    }
    return ddol
  }

  Map columnToFieldNames(Class domainClass) {
    def columnToFieldname = [:]
    def grailsDomainClass = new DefaultGrailsDomainClass(domainClass)
    def hibernateMetaClass = sessionFactory.getClassMetadata(domainClass)
    def props = grailsDomainClass.getProperties()
    props.each { prop ->
      def propName = "${prop.getName()}"
      def columnProps = null
      try {
        columnProps = hibernateMetaClass.getPropertyColumnNames(propName)
      }
      catch (org.hibernate.MappingException hme) {
        if (hme.message != 'unknown property: version')
          log.info "Table: ${hibernateMetaClass.getTableName()} Field: $propName, MappingException message: ${hme.message}."
        // caused by having closures in domain classes or a missing
        // assumed domain property like 'version'
      }
      if (columnProps) {
        def cpa = columnProps.toList()
        def columnName = cpa.size()==1?columnProps[0].toUpperCase():"ID"
        columnToFieldname.put(columnName, propName)
      }
    }
    return columnToFieldname
  }

/*

  List checkNullable(Object dom) {
    def rc = []
    def expectedNullables = expectedNullables(dom)
    def tableName = sessionFactory.getClassMetadata(dom.class).getTableName()
    if (tableName) { // will be null for non-Domain objects
      def columnDefs = getColumnDefs(tableName)
      def atc = allTabColumns('MNR',tableName)
      columnDefs.get("name").eachWithIndex {name, i ->
         def attrIsNullable = expectedNullables.get(name.toLowerCase())
         def columnIsNullable = columnDefs.get("nullable")[i]
         if (columnIsNullable && !attrIsNullable) {
            rc << "${tableName} column ${name} (${columnDefs.get("type")[i]}) is ${columnIsNullable?'nullable':'Not nullable'} but the class ${dom.class.name} expects ${attrIsNullable?'nullable':'Not nullable'}."
         }
      }
    }
    return rc
  }

  Map columnDefs(String tableName) {
    Sql sql = new groovy.sql.Sql(dataSource)
    def name = []
    def type = []
    def nullable = []
    def rnum = "ROWNUM"
    def mdq = "SELECT C.*, ${rnum} FROM (SELECT * FROM ${tableName}) C WHERE ${rnum} < 2"
    sql.query(mdq.toString()) { rs ->
      def meta = rs.getMetaData()
      (1..meta.columnCount).each {
        def dtype = ""
        switch (meta.getColumnTypeName(it)) {
        case 'NUMBER':
          dtype = (meta.getScale(it)==0)?"NUMBER(${meta.getPrecision(it)})":"NUMBER(${meta.getPrecision(it)},${meta.getScale(it)})"
          break
        case 'VARCHAR2':
          dtype = "VARCHAR2(${meta.getPrecision(it)})"
          break
        case 'DATE':
          dtype = "DATE"
          break
        case 'TIMESTAMP':
          dtype = (meta.getScale(it)==0)?"TIMESTAMP":"TIMESTAMP(${meta.getScale(it)})"
          break
        default:
          println "Unknown ColumnTypeName: ${meta.getColumnTypeName(it)} for column ${it}"
        }
      if (meta.getColumnName(it) != rnum)
        name << meta.getColumnName(it)
        type << dtype
        nullable << meta.isNullable(it)
      }
    }
    def attrs = [:]
    attrs.put('name',name)
    attrs.put('type',type)
    attrs.put('nullable',nullable)
    return attrs
  }
*/

  //http://johnrellis.blogspot.com/2010/02/retrieve-grails-domain-errors-from.html
//  clazz.metaClass.retrieveErrors = {
//    def errorString = delegate?.errors?.allErrors?.collect{messageSource.getMessage(it,null)}?.join(' \n')
//
//    return errorString
//  }


  HSSFWorkbook tableAsSpreadsheet(def tableName, def metaOnly, def maxRows, def rowsInTable) {
    HSSFWorkbook wb = new HSSFWorkbook();
    def kf = keyColumnNames(tableName)
    def tc = allTabColumns(grailsApplication.config.currentSchema,tableName)
    if (tc) {
      int rowNum = 0;
      def sql = new Sql(sessionFactory.currentSession.connection())
      def sheet = wb.createSheet(tableName);
      def ssrow = sheet.createRow(rowNum++);
      HSSFCellStyle lockedStyle = wb.createCellStyle();
      lockedStyle.setLocked(true)
      HSSFCellStyle unlockedStyle = wb.createCellStyle();
      unlockedStyle.setLocked(false)
      HSSFCell headerCell = ssrow.createCell(0)
      headerCell.setCellStyle(lockedStyle)
      // Write header row
      headerCell.setCellValue("CREATE(c) UPDATE(u) or DELETE(d)") // Used to specify the C/U/D operation
      headerCell.getCellStyle().setLocked(true)
      def colsExcludedFromUpdate = ['DATE_CREATED','CREATED_BY','DATE_MODIFIED','MODIFIED_BY']
      tc[DbColumnAttr.NAME].eachWithIndex { colName,namesIdx ->
        if (!colsExcludedFromUpdate.contains(colName)) {
          def colHeaderCell = ssrow.createCell(namesIdx+1)
          colHeaderCell.setCellValue(colName)
          colHeaderCell.setCellStyle(lockedStyle)
        }
      }
      String sq = "select T.* FROM ${grailsApplication.config.currentSchema}.${tableName} T".toString()
      String sqs = "SELECT * FROM ( SELECT C.*, ROWNUM RNUM FROM (${sq} ) C WHERE ROWNUM <= ${maxRows}) WHERE RNUM  >= 1".toString()
      int maxRownum = 0
      int rcnt = 1
      sql.eachRow(metaOnly?sqs:sq) { row ->
        def val = ""
        ssrow = sheet.createRow(rowNum++);
        int colIdx = 0
        HSSFCell updtcodeCell = ssrow.createCell(colIdx)
        updtcodeCell.setCellValue(val)
        updtcodeCell.setCellStyle(unlockedStyle)
        sheet.autoSizeColumn(colIdx)

        if (metaOnly) {
          BigDecimal rnum = row["rnum"]
          if (rnum) {
            maxRownum = Math.max(maxRownum, rnum.toInteger().intValue())
          }
        }
        for (def colName in tc[DbColumnAttr.NAME]) {
          if (!colsExcludedFromUpdate.contains(colName)) {
            def cellval = row[colName]
            def fv = SqlDataFormat.formatSqlValue(row[colName], tc[DbColumnAttr.TYPE][colIdx], false, false)
//            if ('"'+fv+'"' == cellval) {
//              ssrow.createCell(0).setCellValue('u')
//            }
            HSSFCell dataCell = ssrow.createCell(colIdx+1);
            dataCell.setCellValue(fv)
            if (kf.contains(colName))
              dataCell.setCellStyle(lockedStyle)
            else
              dataCell.setCellStyle(unlockedStyle)
            if ((rowNum - 1 == rowsInTable) || (metaOnly && (rowNum-1 == maxRows))) {
              sheet.autoSizeColumn(colIdx+1)
            }
          }
          colIdx++
        }
      rcnt++
      }
      if (metaOnly) {
        ssrow = sheet.createRow(rowNum++);
        ssrow.createCell(0).setCellValue("First ${maxRows} rows. Use SQuirreL + copy/paste to add more.")
        sheet.autoSizeColumn(0)
      }
    sheet.createFreezePane( 1, 1, 1, 1 );
    sheet.protectSheet("")   // activates cell locking
    def ds = sql.firstRow("SELECT TO_CHAR(SYSDATE, 'yyyy-MM-dd hh:MI:ss') DOWNLOAD_DATE FROM DUAL")
    int rn = addDbColumns(wb, tc, ds.DOWNLOAD_DATE)
    println "${rn} metadata rows."
  }
  return wb
  }

protected def addDbColumns(HSSFWorkbook wb, def tc, def downloadDate) {
  def rn = 0
  // Create headings
  def mdsheet = wb.createSheet(grailsApplication.config.downloadMetadataSheetname.toString());
  def mdrow = mdsheet.createRow(rn++)
  (DbColumnAttr.NAME..DbColumnAttr.NULLABLE).eachWithIndex { name, idx ->
    mdrow.createCell(idx).setCellValue(name.toString())
  }
  def colRange = 0..((DbColumnAttr.NAME..DbColumnAttr.NULLABLE).size()-1)
  def nRows = tc[DbColumnAttr.NAME].size()
  // Create a row for each column
  (0..nRows-1).each {
    mdrow = mdsheet.createRow(rn++)
    colRange.each { colIdx ->
      def v = tc[(DbColumnAttr.list()[colIdx])][rn - 2]
      mdrow.createCell(colIdx).setCellValue(v)
    }
    if (rn - 1 == nRows) {
      colRange.each { colIdx ->
        mdsheet.autoSizeColumn(colIdx)
      }
    }
  }
  mdrow = mdsheet.createRow(rn++)
  mdrow.createCell(0).setCellValue("DOWNLOAD_DATE")
  mdrow.createCell(1).setCellValue(downloadDate)
  mdrow = mdsheet.createRow(rn++)
  mdrow.createCell(0).setCellValue("Date Format")
  mdrow.createCell(1).setCellValue('yyyy-mm-dd')
  mdsheet.autoSizeColumn(0)
  mdsheet.autoSizeColumn(1)
  return rn
}


}
