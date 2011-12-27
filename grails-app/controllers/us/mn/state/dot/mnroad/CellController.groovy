package us.mn.state.dot.mnroad

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.RoadSection
import us.mn.state.dot.mnroad.Lane
import groovy.sql.Sql
import java.text.SimpleDateFormat
import java.util.zip.ZipOutputStream
import java.util.zip.ZipException
import java.util.zip.ZipEntry
import java.sql.SQLException

class CellController extends ControllerBase {

  // see http://poi.apache.org/spreadsheet/quick-guide.html

  def cellUpdateService
  def queryService
  def laneService
  def sessionFactory

  def index = {
    redirect(action:dataRequest,params:params) 
  }

  def delete = {
    if (params.id == "") {
      flash.message = "Cell id was not provided."
      //println "Cannot Delete: "+params
      return
    }
    Cell cell = Cell.get(params.id)
    if (cell != null) {
      Long rid = cell.roadSection.id
      if (cellUpdateService.deleteCell(cell.id)) {
        if (session.cellId == cell.id) {
            session.cellId = null
        }
        flash.message = "Cell ${cell} deleted"
      } else {
        flash.message = "Unable to delete cell ${cell}."
      }
      redirect(controller: "roadSection", action: show, id: rid)
      }
    else {
      flash.message = "Cell ${params.id} was not found."
      redirect(action: show, id: params.id)
      }
    }

  def changeCellType = {  cell,newClass,params,userName ->
    def cn = cell?.cellNumber
    def ncell = cellUpdateService.changeCellType(cell, newClass, params, userName)
    flash.message = "${cell?.cellType()} Cell ${cn} deleted, ${ncell?.cellType()} Cell ${ncell?.cellNumber} created."
    if (session.cellId == params.id) {
        session.cellId = null
    }
    if (ncell) {
      redirect(controller: ncell.controller(), action:show,id:ncell.id)
      }
    else  {
      redirect(action:show,id:cell.id)
      }
    }

  def show = {
    // cellType must be defined in classes that extend us.mn.state.dot.mnroad.CellController
    Class clazz = ApplicationHolder.application.getClassForName(cellType)
    def cell = clazz.get( params.id )
    if (cell) {
      flash.cellid=cell.id
      Long cellBelow = null
      def cellsBelow = [:]
      if (cell.covers.size() > 0) {
        cell.covers.each { aCell ->
          cellsBelow = cellUpdateService.subCellsUnder(cell.id)
          cellBelow = aCell.id
        }
      }
      def rfs = queryService.retrofitSensors(cell.cellNumber, 'TC',grailsApplication.config.constrPeriodLenDays)
      return [ cell: cell
              , cellsBelow:cellsBelow
              , cellBelow: cellBelow
              , homelessSensors : queryService.sensorsInNoLayer(cell.cellNumber)
              , lanes: cell.lanes
              , tcSensors : rfs
      ]
    }
    else {
        flash.message = "$cellType not found with id ${params.id}"
        redirect(action:show,id:flash.cellid)
    }
  }

  def createLanes = {
        def savedCell = Cell.get( params.id )
        if (savedCell) {
//          def laneNames = ["Left Shldr","Passing","Driving","Right Shldr"]
//          def lvrSouthNames = ["Inside Shldr","Inside","Outside","Outside Shldr"]
//          def farmNames = ["Left Shldr","Westbound","Eastbound","Right Shldr"]
          def laneNames = grailsApplication.config.mainlineLaneNames
          RoadSection r = RoadSection.get(savedCell.roadSection.id)
          if (r) {
            if (r.facility.name == 'Farm Road' ) {
              laneNames = grailsApplication.config.farmNames
            } else if (r.facility.name == 'Low Volume Road') {
              if (r.description.contains('South')) {
                laneNames = grailsApplication.config.lvrSouthNames
              }
              else if (r.description.contains('North')) {
                laneNames = grailsApplication.config.lvrSouthNames.reverse()
              }
            }
//MnRoad Parking Lot
//MnRoad Sidewalk
          }
          def created = true
          def fm = ""
          Lane ln1 = new Lane(laneNum: 1
                  , name: laneNames[0]
                  , width: 12
                  , offsetRef:'+'
                  , cell:savedCell)
          if (ln1.validate()) {
            savedCell.addToLanes(ln1)
          } else if (ln1) {
            ln1.errors.allErrors.each {
              fm += "Ooops!.  Field [${it.getField()}] with value [${it.getRejectedValue()}] is invalid."
            }
            flash.message = fm
            created = false
          }
          Lane ln2 = new Lane(laneNum: 2
                  , name: laneNames[1]
                  , width: 12
                  , offsetRef:'+'
                  , cell:savedCell)
          if (ln2.validate()) {
            savedCell.addToLanes(ln2)
          } else if (ln2) {
            ln2.errors.allErrors.each {
              fm += "Ooops!.  Field [${it.getField()}] with value [${it.getRejectedValue()}] is invalid."
            }
            flash.message = fm
            created = false
          }
          Lane ln3 = new Lane(laneNum: 3
                  , name: laneNames[2]
                  , width: 12
                  , offsetRef:'-'
                  , cell:savedCell)
          if (ln3.validate()) {
            savedCell.addToLanes(ln3)
          } else if (ln3) {
            ln3.errors.allErrors.each {
              fm += " Ooops!.  Field [${it.getField()}] with value [${it.getRejectedValue()}] is invalid."
            }
            flash.message = fm
            created = false
          }
          Lane ln4 = new Lane(laneNum: 4
                  , name: laneNames[3]
                  , width: 12
                  , offsetRef:'-'
                  , cell:savedCell)
          if (ln4 && ln4.validate()) {
            savedCell.addToLanes(ln4)
          } else if (ln4) {
            ln4.errors.allErrors.each {
              fm += "Ooops!.  Field [${it.getField()}] with value [${it.getRejectedValue()}] is invalid."
            }
            flash.message = fm
            created = false
          }
          if (created) {
            savedCell.save(flush:true)
            flash.message = "Lanes for cell ${savedCell} created"
          }
          redirect(action:show,id:params.id)
        }
      else {
          flash.message = "Cell not found with id ${params.id}"
          redirect(action:show,id:params.id)
      }
  }

  def renderTcOnly = {
    println "renderTcOnly params: ${params}"
    render (template:"thermocoupleOnly"
      , model: ["selectedCellId":params.selectedCellId,"tc": params.tc]
            )
  }

  def renderModels = {
    println "renderModels params: ${params}"
    def cell = us.mn.state.dot.mnroad.Cell.get(params.selectedCellId)
    def ml = queryService.modelListFromCellId(params.selectedCellId)
    def mlt = queryService.modelListFromTableNames(""+cell.cellNumber)
    def rc = getStructureText(params.selectedCellId)
//    writeCellData(cell)
    render (template:"modelsForChosenCell"
      , model: ["selectedCellId":params.selectedCellId,modelList: ml, tableModelList: mlt, result:rc, cellNumber: cell.cellNumber, yearList: getYearList(cell)]
    )
  }

  def yearList = { start ->
    def rc = []
    def end = Calendar.getInstance().get(Calendar.YEAR)

    for (int i=start;i<=end;i++) {
      rc << i
    }
    return rc
  }

  def dataRequest = {
    //println "dataRequest params:${params}"
    def cellList = (Cell.createCriteria()).list(){
      order('cellNumber','asc')
      order('constructionEndedDate','asc')
    }
    [cells:cellList, result:params.result?:"", yearList:yearList(grailsApplication.config.firstSensorReadingYear), year:params.year, id:params.id, selectedYear:params.selectedYear]
  }

  private def getStructureText(def cell) {
    def rc = "Got your request for Cell with ID ${cell}."
    def c = Cell.get(cell)
    if (c) {
      for (Lane l in c.lanes) {
        def layers = l.layers()
        rc += "\n\n${layers.size()} layers in lane ${l.id}.  "
        layers.each {
          rc += "\n$it"
        }
      }
    } else {
      rc += "\n but am unable to find the cell."
    }
    return rc
  }

  protected List getYearList(cell) {
    def yrlist = []
    if (!cell)
      return yrlist
    def gc = new GregorianCalendar()
    gc.setTime(cell.constructionEndedDate)
    def from_year = gc.get(Calendar.YEAR)
    if (cell.demolishedDate == null)
      gc = Calendar.getInstance()
    else {
      gc = new GregorianCalendar()
      gc.setTime(cell.demolishedDate)
    }
    def to_year = gc.get(Calendar.YEAR)

    def start = grailsApplication.config.firstSensorReadingYear
    for (int i = Math.max(from_year, start); i <= to_year; i++) {
      yrlist << i
    }
    return yrlist
  }

  def processSensorDataRequest = {
    println "processSensorDataRequest params: ${params}"
    def yl = yearList(grailsApplication.config.firstSensorReadingYear)
    def rc = "Available years: ${yl}.\n\tSelected year: ${params.year?:'no year selected'}"
    redirect(action:downloadSensorData,
            params:["yearlist":yl,"result":rc,"model":params.model,"selectedYear":new Integer(params.year?:0),"cellId":params.cell])
  }
/*
TODO Imporve performance using GPars.  See scripts/readTc.groovy
 */
  def downloadSensorData = {
    println "downloadSensorData params: ${params}"
    def year = 0
    def yearList = []
    if (params.selectedYear) {
      year = Integer.parseInt(params.selectedYear)
      if (year <= 0) {
        flash.message = "Please Select a Cell and then one year."
        redirect(action:dataRequest,id:params.cellId)
        return
      }
    }
    Date fjDate
    Date tjDate
    // getCellMap query: SELECT UNIQUE CELL,FROM_DATE,TO_DATE FROM CELLS where ID=?
    def cm = queryService.getCellMap(params.cellId)
    Date cellFromDate = cm.get("fromDate")
    Date cellToDate = cm.get("toDate")
    fjDate = new java.sql.Date(cellFromDate.getTime())
    tjDate = new java.sql.Date(cellToDate.getTime())
    if (year > 0) {
      fjDate = java.sql.Date.valueOf("$year-01-01")
      tjDate = java.sql.Date.valueOf("$year-12-31")
      if ((cellFromDate.after(fjDate) && cellFromDate.before(tjDate))
              || (cellToDate.after(fjDate) && cellToDate.before(tjDate))) {
        if (cellFromDate.getYear() == year - 1900)
          fjDate = new java.sql.Date(cellFromDate.getTime())
        if (cellToDate.getYear() == year - 1900)
          tjDate = new java.sql.Date(cellToDate.getTime())
      }
    }

    List tableNames = []
    if (params.model)
      tableNames = getTableNames(params.cellId, params.model, year)
    else
      tableNames = getTableNames(params.cellId, year)

    // Find out if any data is available before the response is committed.
    def c = Cell.get(params.cellId)
    def rowCounts = getRowCounts(tableNames, c.cellNumber, tjDate, fjDate)
    if (!rowCounts || rowCounts.values().sum() == 0.0){
      flash.message = "No Data Available for cell ${c}."
      redirect(action:dataRequest,id:params.cellId)
      return
    }
    def zipFileName = "Cell_${c.cellNumber}-${params.cellId}year${year}" + grailsApplication.config.zipFileNameBase
    if (params.model)
      zipFileName = params.model+zipFileName
    def len = 0
    response.setContentType('application/zip')
    response.setHeader("Content-disposition", "attachment; filename=${zipFileName}")
//    response.setHeader("Content-Disposition","inline; filename=\""+zipFileName+"\"".toString());
    ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream));
    for (tn in tableNames) {  // tn format:   table_name:value_column_1,value_column_2,value_column_3
      def tns = tn.split(":")
      def qm = queryMap(c.cellNumber, tns[0], columnNames(tns), fjDate, tjDate)
      if (qm["dataQuery"]) {
        String fjDateStr = MrUtils.formatDate(fjDate, grailsApplication.config.oracleDefaultQueryFormat)
        String tjDateStr = MrUtils.formatDate(tjDate, grailsApplication.config.oracleDefaultQueryFormat)
        def zipEntryName = "${tns[0].substring(0, 2)}_Cell-${c.cellNumber}_${fjDateStr}_${tjDateStr}.csv"
        len += sensorValuesByQhr(zos, zipEntryName.toString(), c.cellNumber, tns[0], fjDate, tjDate, grailsApplication.config.batchSize, qm)
      }
    }
    zos.flush()
    boolean hasEntries = false
    try {
      zos.close()
      hasEntries = true
    } catch (ZipException ze) {
      if (ze.message == "ZIP file must have at least one entry") {
        log.info "No entries for ${zipFileName}"
      } else {
        log.info ze.message
      }
    }
    response.setContentLength(len)

  }

  private def getTableNames(def cellId, def model, int year) {
    def tableNames = []
    getTableNames(cellId, year).each {
      if (it.startsWith(model)) {
        tableNames << it
        if (it[0..1] == "EC") {
          tableNames << "ET_VALUES:VALUE"
          tableNames << "EW_VALUES:VALUE"
        }
      }
    }
    return tableNames
  }

  private def getTableNames(def cellId, int year) {
    def tableNames = []
    def ml = queryService.modelListFromCellId(cellId)
    def tnfm = queryService.sensorTableNamesViaUserTables(grailsApplication.config, year)
    tnfm.keySet().each {
      def ss = it[0..1]
      if (ml.contains(ss)) {
        tableNames << "${it}:${tnfm.get(it)}"
        if (ss == "EC") {
          tableNames << "ET_VALUES:VALUE"
          tableNames << "EW_VALUES:VALUE"
        }
      }
    }
    return tableNames
  }

  protected def getRowCounts(List tableNames, Integer cellNumber, Date tjDate, Date fjDate) {
    Sql sql = new Sql(dataSource)
    def rowCounts = [:]
    for (tn in tableNames) {  // tn format:   table_name:value_column_1,value_column_2,value_column_3
      def tns = tn.split(":")
      def qm = queryMap(cellNumber, tns[0], columnNames(tns), fjDate, tjDate)
      if (qm["countQuery"]) {
        try {
          def numrows = sql.firstRow(qm["countQuery"].toString(), [cellNumber, fjDate, tjDate]).NUMROWS
          rowCounts.put(tns[0], numrows)
        } catch (SQLException sqle) {
          log.info ("SQLException: ${sqle.message}")
          log.info("Database query error - table name: ${tns[0]}.")
        }
      }
    }
    return rowCounts
  }

    protected int childRowCount(def childTableName, def parentTableName, def parentId) {
      int rc = 0
      Sql sql = new Sql(dataSource)
      try {
        def numrows = sql.firstRow("select count(*) NUMROWS from ${childTableName} where ${parentTableName}_ID=?".toString(), [parentId]).NUMROWS
        rc = numrows
      } catch (SQLException sqle) {
        println ("SQLException: ${sqle.message}")
      }
      return rc
    }

  private List columnNames(tns) {
    def valueColumnNames = []
    if (tns.size() > 1) {
      tns[1].split(",").each {
        valueColumnNames << it
      }
    } else {
      valueColumnNames << "VALUE"
    }
    return valueColumnNames
  }

  protected Map queryMap(int cell, String tableName, ArrayList valueColumnNames, java.sql.Date from_date, java.sql.Date to_date) {
    def rc = [:]
    if (grailsApplication.config.largeTables.contains(tableName[0..1])) {
      tableName=tableName[0..8]+"_ALL"
    }

    def psq = "select unique seq from MNR.SENSOR_COUNTS where cell=? and table_name=? and ((from_day between ? and ?) or (to_day between ? and ?)) order by seq"
    def q = ""
    Sql sql = new groovy.sql.Sql(dataSource)
    // Temporary fix until SensorDevice can be implemented. A "EC" implies an "ET" and and "EW" because all
    // three sensors are part of the same device.
//    def ctn = tableName
//    if (['ET_VALUES','EW_VALUES'].contains(tableName))
//      ctn = 'EC_VALUES'
    // Temporary
//    def ans = sql.rows(psq, [cell, ctn, from_date,to_date, from_date,to_date])
    def ans = sql.rows(psq, [cell, tableName, from_date,to_date, from_date,to_date])
    if (ans) {
      def q1 = 'select cell \"Cell\", to_char(day,\'yyyy-mm-dd\') \"Day\", hour \"Hour\", qhr \"Qhr\", minute \"Minute\",'
      def q2 = ' from (select cell,day,hour,qhr,minute,'
      def q3 = " FROM MNR.$tableName WHERE cell = ? AND DAY BETWEEN ? and ? GROUP BY cell,day,hour,qhr,minute) order by cell,day,hour,qhr,minute".toString()
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

  int sensorValuesByQhr(ZipOutputStream zpos, String zipEntryName, int cell, String tableName, java.sql.Date fjDate, java.sql.Date tjDate, int sqlBatchSize, Map qm) {
    int len = 0
    int numBatches = 0
    int batchNum = 0
    int rowsRead = 0
    boolean includeHeader = true
    boolean includeRownum = false
    Sql sql = new groovy.sql.Sql(dataSource)
    if (sql) {
      try {
        def numrows = sql.firstRow(qm["countQuery"].toString(),[cell, fjDate, tjDate]).NUMROWS
        if (numrows==0) {
          return 0
        }
        numBatches = (numrows/sqlBatchSize) + 1
        int sqlBeginOffset = 0
        log.info "Cell ${cell}. Reading sensor data from table $tableName from ${fjDate} to ${tjDate}."
        zpos.putNextEntry(new ZipEntry(zipEntryName));
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
                zpos.write(line.getBytes(),0,line.size())
              }
              line = (getContent(row,sqs.toLowerCase().contains("decode"),includeRownum)+"\n").toString()
              len += line.size()
              zpos.write(line.getBytes(),0,line.size())
              rowsRead++
            }
//            print "${rowsRead} rows (${len} bytes) written.\r"
          }
          sqlBeginOffset += sqlBatchSize
          batchNum++
//          log.info "Batch ${batchNum} of ${numBatches} processed, ${rowsRead} rows."
        }
      } catch (IOException ioe) {
        log.error "${ioe.message}."
        ioe.printStackTrace();
      } catch (java.sql.SQLException sqle) {
        log.error "${sqle.message}."
        sqle.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
      log.info "${len} bytes in ${rowsRead} rows for cell ${cell} written to entry ${zipEntryName}."
    }
    return len
  }

  String getContent(Map row, boolean multiSensor, boolean includeRownum) {
    String rc = ""
    if (multiSensor) {
      def s = row.values().join(",")
      def withNull =  includeRownum?s.toString():s.substring(0,s.lastIndexOf(','))
      rc = withNull.replaceAll("null","")
    } else {
      def s = "${row.CELL},${row.SEQ},${row.DAY.toString().substring(0,10)},${row.HOUR},${row.MINUTE},${row.value}"
      rc =  includeRownum?(s+row.rnum).toString():s.toString()
    }
    return rc
  }

  def sensorSummary = {
    //http://localhost:9090/mnroad/cell/sensorSummary
    log.info "CellController:sensorSummary params ${params}"
    String tableName = "sensor"
    String colList = "CELL,MODEL,STATION,OFFSET,SEQBEG,SEQEND,NUMSENSORS\n"
    def q = "SELECT CELL, MODEL, STATION_FT STATION, OFFSET_FT OFFSET, MIN(SEQ) SEQBEG, MAX(SEQ) SEQEND, COUNT(SEQ) NUMSENSORS FROM MNR.${tableName} WHERE (MODEL='TC' OR MODEL='WM' OR MODEL='XV') GROUP BY CELL, MODEL, STATION_FT, OFFSET_FT ORDER BY CELL, MODEL, SEQEND"
    response.setContentType("application/zip");
    response.setHeader("Content-Disposition","inline; filename=\""+tableName+".zip\";");
    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
    ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.outputStream));
    int len = 0
    try {
      Sql sql = new groovy.sql.Sql(dataSource)
      String b = ""
      def ans = sql.rows(q.toString())
      if (ans.size() > 0) {
        zip.putNextEntry(new ZipEntry("${tableName}.csv".toString()));
        len += colList.size()
        zip.write(colList.getBytes(),0,colList.size())
      }
      ans.each {
        b = "${it.CELL},${it.MODEL},${it.STATION},${it.OFFSET},${it.SEQBEG},${it.SEQEND},${it.NUMSENSORS}\n".toString()
        len += b.size()
        zip.write(b.getBytes(),0,b.size())
      }
    } catch(java.sql.SQLException sqle) {
      log.error "${sqle.message} - Query with string argument: ${sensorQuery}"
    }
    log.info "Zip File len: ${len} bytes."
    response.setContentLength(len)
    response.setHeader("${len}","content-len")
    zip.flush()
    zip.close()
  }

/*

   */

  def cellRequest = {
    println "cellRequest params:${params}"

    def exportData = grailsApplication.config.exportOrder["data"]
    def domainObjList = ApplicationHolder.application.getArtefacts("Domain") as List
    def meta = ""
    exportData.each { cn ->
      domainObjList.each { domObj ->
        if (domObj.shortName.endsWith(cn)) {
          def md = props(domObj.clazz.name)
          def keys = md.keySet().toArray()
          meta += "${keys[0]}: ${md[keys[0]]}" + (md[keys[1]]?" (${keys[1]}: ${md[keys[1]]})":"") + "\n"
          def keyIdx = 2
          def nFields = md[keys[keyIdx]].size()
          for (i in 0..3) { // one for each fields, types, columns, and foreign keys
            def arr = md[keys[i+keyIdx]]
            if (keys[i+keyIdx] == "types")  {
              def cori = arr.collect {"${it.toString().split(" ")[0]}"}
              arr = arr.collect {"${it.toString().split(" ")[1]}"}
              meta += "\tcori:  ${cori}\n"
            }
            meta += "\t${keys[i+keyIdx]}:  ${arr}\n"
          }
          meta += "\n"
        }
      }
    }

    def cellList = (Cell.createCriteria()).list(){
      order('cellNumber','asc')
      order('constructionEndedDate','asc')
    }
    [cells:cellList,
            result:params.result?:meta,
            yearList:yearList(grailsApplication.config.firstSensorReadingYear),
            year:params.year,
            id:params.id,
            selectedYear:params.selectedYear]
    }

  private List listInserts(def domObj) {
      def rc = []
      def cls = domObj.newInstance()
      if (!cls || (domObj.shortName.equals("Cell"))) // class Cell is extended
        return rc

      def c = cls.list()
      if (c.size() > grailsApplication.config.maxExportRowsPerTable) {
        println "Data for class $domObj.clazz skipped. ${c.size()} rows."
        return rc
      }
      def vs = ") values ("
      def term = ");"
      def meta = props(domObj.clazz.name)
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
          if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
            String s = formatSqlValueForInserts(val, type,!meta["fks"].toArray()[idx].equals(""))
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
      //create a new GrailsDomainClass object for the DomainClass hibernateMetaClass.propertyMapping.typesByPropertyPath hibernateMetaClass.propertyMapping.columnsByPropertyPath
      def grailsDomainClass = new DefaultGrailsDomainClass(domainClass)
      //get the domain properties defined in Domain Class
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
          //println "${hme.message}"
          // "Table: $tableName, MappingException message: ${hme.message}."
          // caused by having closures in domain classes  e.g. unknown property: depthRange
        }
        if (columnProps && columnProps.length > 0) {
          //get the column name, which is stored into the first array
          def columnName = columnProps[0]
          String pn = propName.toString()
          fields.add(pn)
          types.add(prop.type)
          columns.add(prop.naturalName.toUpperCase().replace(' ','_'))
//          if (prop.isManyToMany()) {
//            columns.add(prop.naturalName.toUpperCase().replace(' ','_'))
//          }
//          else {
//            columns.add(columnName)
//          }
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

    def schema = {
      def rc = []
      try {
        groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
        def schema = grailsApplication.config.currentSchema //(MrUtils.grailsEnvironment()=='development')?'MNR':'MNROAD'
        def exportData = grailsApplication.config.exportOrder["data"]
        exportData.each { cn ->
          (ApplicationHolder.application.getArtefacts("Domain") as List).each{
            if (it.shortName.endsWith(cn)) {
              def meta = props(it.clazz.name)
              def natName = it.naturalName.toUpperCase()
              def tbl_name = natName.replace(' ','_')
              if ( // if this class is not a subclass
                (meta.discriminatorColumnName == null) ||
                // or if it is a superclass
                (it.subClasses.size() > 0)) {
                  def tname = meta.tableName.toUpperCase().startsWith(schema)?tbl_name:meta.tableName.toUpperCase()
                  def s = "select to_char(dbms_metadata.get_ddl('TABLE', ${tname},${schema})) from dual"
                  def ddl = sql.firstRow(s)[0].toString() + ";"
                  rc << ddl.replace("\"${schema}\".".toString(),'')
                }
              }
            }
          }
        }
      catch(Exception e){
        println e.message
      }
      return rc
    }

    protected def writeInserts = { File f ->
      def rc = 0
      if (!f)
        return rc
      def domainObjList = ApplicationHolder.application.getArtefacts("Domain") as List
      def exportData = grailsApplication.config.exportOrder["data"]
      exportData.each { cn ->
        domainObjList.each{
          if (it.shortName.endsWith(cn)) {
            def inserts = listInserts(it)
            f.withWriterAppend {file ->
              inserts.each {line ->
                file.writeLine(line)
                rc++
                }
              }
            if (inserts.size()==0)
              f.append("-- No ${it.shortName} data found.\n")
            f.append("--\n")
          }
        }
      }
      return rc
    }

    Map cellDates(def sql) {
      def rc = [:]
      def q = """select cell, min(FROM_DATE) FROM_DATE,max(nvl(TO_DATE,sysdate)) TO_DATE from mnr.cells group by cell
    """
      sql.eachRow(q.toString()) { row ->
        def rowMap = [:]
        rowMap.put("fromDate"   ,new Date(row.FROM_DATE.getTime())   )
        rowMap.put("toDate"     ,row.TO_DATE?(new Date(row.TO_DATE.getTime())):null   )
        rc.put(row.CELL.intValue(), rowMap)
      }
      return rc
    }

} // end classCellController
