package us.mn.state.dot.mnroad

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import groovy.sql.Sql
import us.mn.state.dot.mnroad.distress.Distress
import us.mn.state.dot.mnroad.distress.DistressAc
import java.text.SimpleDateFormat
import org.apache.poi.ss.util.CellReference

class DistressController extends ControllerBase {

  def dataSource
  def dbMetadataService
  def exportDataService
  def laneService

  def edit = {
    def am = MrUtils.subMap((LinkedHashMap)params,params.columnNames)
    [lane_id:params.lane_id, dr:am, laneDescr: params.lane, table:params.table]
  }

  def update = {
    println "Update params: ${params}"
    def defaultDateFormat="dd-MON-yyyy"
    if (params.lane_id && params.table) {
      def am = MrUtils.subMap((LinkedHashMap)params,params.columnNames)
      def rm = laneService.updateDistressLanes(params.lane_id
              ,params.table.toString()
              ,getUserName().toString()
              ,am, defaultDateFormat.toString())
      flash.message = "Tables MNR.DISTRESS and MNR.${params.table} updated. ${rm.result1} ${rm.result2}"
    }
    redirect(action:"show")
  }

  def show = {
    def defaultDateFormat="dd-MON-yyyy"
    groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
    def nullRows = [:]
    def q = "select orphan_row_count, table_name from mnr.ORPHAN_DISTRESSES_COUNTS where orphan_row_count > ?"
    sql.eachRow(q, [0]) { row ->
      try {
        nullRows.put("$row.table_name".toString().trim(),row.orphan_row_count)
      } catch (Exception e) {
        println "${e.message}"
      }
    }
    def lid = 0
    def laneDescr
    def rr
    def tablename
    nullRows.keySet().each { tableName ->
      def idq = """SELECT max(ln.id) lane_id FROM mnr.cell c
        join mnr.lane ln on ln.cell_id=c.id
        join MNR.${tableName} d on d.cell=c.cell_number and d.lane=ln.name and d.day > c.construction_ended_date
        where c.cell_number=? and ln.NAME=? and d.id is null
        """
      def md = dbMetadataService.allTabColumns('MNR',tableName)
      def cols = []
      int i = 0
      for (name in md[DbColumnAttr.NAME]) {
        def n = name
        if (md[DbColumnAttr.TYPE][i] == "DATE" || md[DbColumnAttr.TYPE].toString().startsWith("TIMESTAMP")) {
          n = "TO_CHAR(${n},'${defaultDateFormat}') ${n}"
        }
        cols << n
        i++
      }
      String s = "select ${cols.join(',')} from mnr.${tableName} where id is null".toString()
      println "Looking for ${tableName} data with missing ID ... "
      tablename = tableName
      rr = sql.firstRow(s)
      def args = [rr.cell,rr.lane]
      def laneid = sql.firstRow(idq.toString(),args)
      if (laneid) {
        lid = laneid[0]
        laneDescr = sql.firstRow("select ln.name||' lane of '||substr(c.class,24)||' '||c.cell_number||' '||c.name description from mnr.cell c join mnr.lane ln on ln.cell_id=c.id where ln.id = ?".toString()
                ,[lid])[0]
        }
      }
    if (!lid)
      flash.message = "All LaneID's are populated."
    [dr:rr
      , lane_id:lid
      , laneDescr: laneDescr
      , table:tablename
    ]
  }

  def selectTable = {
      println "selectTable: ${params}"
        def tableName = params.table
        def nrows = 0
        if (params.table) {
          if (params.table.indexOf(':')>=0) {
            def a = params.table.split(':')
            tableName = a[0]
            nrows = Integer.parseInt(a[1])
          }
          response.setContentType("application/vnd.ms-excel");
          response.setHeader("Content-Disposition","inline; filename=\""+tableName+".xls\";");
          HSSFWorkbook wb = dbMetadataService.tableAsSpreadsheet(tableName, params.metaOnly=='on'?true:false, 20, nrows)
          try {
            wb.write(response.outputStream);
          }catch(IOException ioe ){
              ioe.printStackTrace();
          }
          def len = wb.getBytes().length
          response.setContentLength(len)
          response.setHeader("${len}","content-len")
          }
//    refreshDistress()
        String[] excludes = ["BIN%"]
        def tmd = dbMetadataService?.tableNames('MNR','DISTRESS_%', excludes)
        def tableNames = []
        tmd.keySet().each {name ->
          tableNames << "${name}:${tmd.get(name)}".toString()
        }
        [tables:tableNames, selectedTable:tableName]
      }

    def upload = {
      def folderFile
      if (params.fileToUpload) {
        flash.errors = []
        flash.message=""
        def fn = ""
        def fe = false
        def f = request.getFile('fileToUpload')
        if(!f.empty) {
          def uploadedFilesFolder = "${grailsApplication.config.fileUploadFolder}".toString()
          def userFolder = "${uploadedFilesFolder}/${getUserName()}"
          folderFile = new File( userFolder )
          fn = userFolder + File.separatorChar + f.getOriginalFilename()

          def fl = folderFile.exists()
          if (!fl) {
            fl = folderFile.mkdir()
            }
          if (fl) {
            flash.message = "File uploaded."// and validated."
            f.transferTo( new File( fn ) )
            fe=true
          } else {
            flash.message = "Unable to find or create ${fn}."
          }
        }
        else {
           flash.message = 'File cannot be empty'
        }
        redirect (action:fe?validate:upload, params:["filename":fn])//, "rfilename":driveLetterFileName])//, valid:"no"])
      } else {
        if (params.updates || params.messages) {
          flash.messages = flash.messages?:[]
          if (params.updates) {
            flash.messages.addAll(params.updates)
          }
          flash.errors = flash.errors?:[]
          if (params.messages) {
            flash.errors.addAll(params.messages)
          }
        }
        else {
          if (params.fileToUpload) {
            println "Unable to upload file ${params.fileToUpload}".toString()
          }
        }
      }
    }

    def bupdate = {
//    println "bupdate: ${params}"
      def slurper = new SimpleXlsSlurper(params.filename)
      def sheet = slurper.sheets(0)
      def msgs = [:]
      def updts = []
      def ir = [:] // Gather the row update codes and indices
      def nrows = sheet.getLastRowNum()
      for (def rowIdx = 1; rowIdx <= nrows; rowIdx++) { // skip the headers row (index 0)
        def row = sheet.getRow(rowIdx)
        def updtCode = row.getCell(0).toString().trim()
        if (updtCode && !updtCode.equals("null") && !msgs.get(rowIdx)) {
          ir.put(rowIdx, updtCode)
        }
      }
      def nUpdates = 0
      def nDeletes = 0
      def nCreates = 0
      if (ir.size()==0) {
        msgs.put(-1,"No rows selected.")
        redirect (action:'upload',params:[filename:params.filename])
      }
      else  {
        def m = dbMetadataService.updateTable(params.filename)
        nUpdates = m.U
        nDeletes = m.D
        nCreates = m.C
        msgs = m.messages
        updts = m.updates
      }
      if ((nUpdates+nCreates+nDeletes-updts.size()) > 0 && nUpdates+nCreates+nDeletes < updts.size()) {
        updts << "${nUpdates+nCreates+nDeletes-updts.size()} ... more."
      }
      updts << "${nUpdates} updated, ${nCreates} created, ${nDeletes} deleted."
      redirect (action:'upload',params:[filename:params.filename, 'updates':updts, 'messages':msgs])
    }

    def validate = {
      def rc = dbMetadataService.validateForUpdate(params.filename)
      if ((rc.messages) || (rc.updates)) {
//        if ((rc.messages)){
//          flash.errors = rc.messages
//        }
//        if (rc.updates) {
//          println rc.updates
//          flash.messages = rc.updates
//        }
      } else {
        flash.message = "File uploaded but has no updates."
      }
      redirect (action:'upload',params:[messages:rc.messages, updates:rc.updates, filename:params.filename, numUpdates:rc.updates?.size(),updtsTodo:rc.updates?"yes":"no"])
    }

    def populateDistress = {
//      if (Distress.count() == 0) {
//        int cnt = refreshDistress()
//        println "$cnt rows added."
//      }

    }

    int refreshDistress () {
      def sql = Sql.newInstance(dataSource)
      def updtCnt = 0
      def q = """
SELECT * FROM (
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_AC                   ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_AC                    D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_AGG_SURVEY_SEMI      ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_AGG_SURVEY_SEMI       D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_ALPS_RESULTS_RUT     ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_ALPS_RESULTS_RUT      D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_CIRCULR_TEXTR_METER  ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_CIRCULR_TEXTR_METER   D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_CUPPING              ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_CUPPING               D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_FRICTION_DFT         ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_FRICTION_DFT          D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_FRICTION_TRAILER     ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_FRICTION_TRAILER      D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_JPCC                 ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_JPCC                  D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_LANE_SHOULDER_DROPOFF') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_LANE_SHOULDER_DROPOFF D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_NUCLEAR_DENSITY      ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_NUCLEAR_DENSITY       D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_OBSI_DATA            ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_OBSI_DATA             D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_OBSI_SUMMARY         ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_OBSI_SUMMARY          D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_PCC_FAULTS           ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_PCC_FAULTS            D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_PERMEABILITY_DIRECT  ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_PERMEABILITY_DIRECT   D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_RIDE_LISA            ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_RIDE_LISA             D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_RIDE_PATHWAYS        ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_RIDE_PATHWAYS         D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_RIDE_PAVETECH        ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_RIDE_PAVETECH         D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_RUTTING_DIPSTICK     ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_RUTTING_DIPSTICK      D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_RUTTING_STRAIGHT_EDGE') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_RUTTING_STRAIGHT_EDGE D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_SAND_PATCH           ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_SAND_PATCH            D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_SCHMIDT_HAMMER       ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_SCHMIDT_HAMMER        D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_SOUND_ABSORPTION     ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_SOUND_ABSORPTION      D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_WARP_CURL            ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_WARP_CURL             D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A UNION ALL
SELECT ID,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,TRIM('DISTRESS_WATER_PERMEABILITY   ') DATA_TABLE FROM (SELECT D.ID,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM MNR.DISTRESS_WATER_PERMEABILITY    D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE) A )
ORDER BY ID
      """
      def onRow = {
        def lv = it.LANE_ID.longValue()
        def ln = Lane.get(lv)
        def did = it.ID
        def tbl = it.DATA_TABLE
        if (!Class.forName('us.mn.state.dot.mnroad.distress.Distress',true,Thread.currentThread().contextClassLoader).get(did))
        Distress.withTransaction {
          def d = new Distress()
          d.lane = ln
          d.version=1
          d.createdBy='carr1den'
          d.lastUpdatedBy='carr1den'
          d.dateCreated = new java.sql.Date((new Date()).getTime())
          d.lastUpdated = new java.sql.Date((new Date()).getTime())
          ln.addToDistresses(d)
          def sd
          if (d.hasErrors()) {
            d.eachError {
              print "$it"
            }
          } else {
            sd = d.save(flush:true)
            updtCnt++
            }
          if (updtCnt%100 == 0) {
            println "$updtCnt rows saved. Table - ${tbl}, ID - ${did}, ${ln}}"
          }
        }
      }
      def onFirstRow = {meta ->
      }
      sql.eachRow(q,[],onFirstRow,onRow)
      return updtCnt
    }
}
