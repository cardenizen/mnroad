package us.mn.state.dot.mnroad

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.util.CellReference
import org.jsecurity.SecurityUtils

class MaterialSampleController extends ControllerBase {

  def sessionFactory
  def dbMetadataService
  def exportDataService
  def queryService

    def index = { redirect(action:list,params:params) }

    def list = { // invoked via list or list sort
      def cell
      def cellId
      params.max = Math.min( params.max ? params.max.toInteger() : 12,  100)

      if (params.maxrows) {
        params.max = params.maxrows
      } else {
        cellId = params.filterId?params.filterId:Cell.list(sort:'cellNumber').getAt(0).id
        cell = Cell.get(cellId)
      }
      def cl = Cell.list(sort:'cellNumber')
      def total = 0
      def sl = []
      if (!cell) {
        def ms = MaterialSample.createCriteria()
        sl = ms.list(){
          isNull ("cell")
        }
        total = sl.size()
      } else {

      sl = MaterialSample.findAllByCell(cell.cellNumber,params)
      total = MaterialSample.countByCell(cell.cellNumber)
      }
      [ materialSampleInstanceList: sl ,
        max:params.max ,
        materialSampleInstanceTotal:total ,
        filterId:cellId ,
        cellList:cl]
    }

    def filterSelected = {
      params.max = Math.min( params.max ? params.max.toInteger() : 12,  100)
      if (params.maxrows)
        params.max = params.maxrows
      def cid = params.cellId
      if (!cid) {
          cid = params.filterId
      }
      def cell = Cell.get(cid)
      def total = 0
      def sl = []
      if (!cell) {
        def ms = MaterialSample.createCriteria()
        sl = ms.list(){
          isNull ("cell")
        }
        total = sl.size()
      } else {
        params["cellNumber"] = cell.cellNumber
        params["filterId"] = cid
        sl = MaterialSample.findAllByCell(cell.cellNumber,params)
        total = MaterialSample.countByCell(cell.cellNumber)
      }
      if ((!cell || params.cellId) && !params.sort){
        // Arrived here by selecting from the drop-down named 'filterId'
        // which passes the id of the selected item using param name 'cellId' (same as filterId)
        render(template:"/templates/common/selectedMaterialSamplesTemplate",
          model:[materialSampleInstanceList:sl
            , max:params.max, offset:0, materialSampleInstanceTotal:total
                  , filterId:params.cellId
            ]
        )
      }
      else {
        // Arrived here via column sort after first selecting from drop-down list
        //
        def cl = Cell.list(sort:'cellNumber')
        render(view:"list",
          model:[materialSampleInstanceList:sl
            , max:params.max, offset:0, materialSampleInstanceTotal:total
            , filterId:params.filterId
            , cellList:cl
          ]
        )
      }
    }

    Map getHelpBalloons(def columnList) {
      Map m = [:]
      columnList.each {
        String s = queryService.mat_samplesHelpBalloonData(it)
        m.put(it,s)
      }
      return m
    }

    def show = {
      def materialSampleInstance = MaterialSample.get( params.id )

      if(!materialSampleInstance) {
        flash.message = "MaterialSample not found with id ${params.id}"
        redirect(action:list)
      }
      else { return [ materialSampleInstance : materialSampleInstance ] }
    }

    def delete = {
      def materialSampleInstance = MaterialSample.get( params.id )
      if(materialSampleInstance) {
        materialSampleInstance.delete()
        flash.message = "MaterialSample ${params.id} deleted"
        redirect(action:list)
      }
      else {
        flash.message = "MaterialSample not found with id ${params.id}"
        redirect(action:list)
      }
    }

    def edit = {
      def materialSampleInstance = MaterialSample.get( params.id )

      if(!materialSampleInstance) {
        flash.message = "MaterialSample not found with id ${params.id}"
        redirect(action:list)
      }
      else {
        Map m = getHelpBalloons(["container_type","material_group","storage_location","course","sample_time","spec"])
        return [ materialSampleInstance : materialSampleInstance, 'helpmap':m]
      }
    }

    def update = {
      def materialSampleInstance = MaterialSample.get( params.id )
      if(materialSampleInstance) {
        materialSampleInstance.properties = params
        materialSampleInstance.lastUpdatedBy = getUserName()
        if(!materialSampleInstance.hasErrors() && materialSampleInstance.save()) {
          flash.message = "MaterialSample ${params.id} updated"
          redirect(action:show,id:materialSampleInstance.id)
        }
        else {
          Map m = getHelpBalloons(["container_type","material_group","storage_location","course","sample_time","spec"])
          render(view:'edit',model:[materialSampleInstance:materialSampleInstance, 'helpmap':m])
        }
      }
      else {
        flash.message = "MaterialSample not found with id ${params.id}"
        redirect(action:edit,id:params.id)
      }
    }

    def create = {
      def materialSampleInstance = new MaterialSample()
      materialSampleInstance.properties = params
      Map m = getHelpBalloons(["container_type","material_group","storage_location","course","sample_time","spec"])
      return [ materialSampleInstance : materialSampleInstance, 'helpmap':m]
    }

    def save = {
      def materialSampleInstance = new MaterialSample(params)
      materialSampleInstance.createdBy = getUserName()
      materialSampleInstance.lastUpdatedBy = getUserName()
      if(!materialSampleInstance.hasErrors() && materialSampleInstance.save()) {
        flash.message = "MaterialSample ${materialSampleInstance.id} created"
        redirect(action:show,id:materialSampleInstance.id)
      }
      else {
        Map m = getHelpBalloons(["container_type","material_group","storage_location","course","sample_time","spec"])
        render(view:'create',model:[materialSampleInstance:materialSampleInstance, 'helpmap':m])
      }
    }

    def selectTable = {
//      println "selectTable: ${params}"
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
//        HSSFWorkbook wb = exportDataService.tableAsSpreadsheet(tableName, params.metaOnly=='on'?true:false, 20, nrows)
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
      String[] excludes = ["BIN%","MATERIAL","MAT_SAMPLES"]
      def tmd = dbMetadataService?.tableNames('MNR','MAT_%', excludes)
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
        if (params.messages) {
          flash.messages.addAll(params.messages)
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
      msgs.put(-1,"No rows selected.".toString())
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
    if (nUpdates+nCreates+nDeletes < updts.size()) {
      updts << "${nUpdates+nCreates+nDeletes-updts.size()} ... more.".toString()
    }
    updts << "${nUpdates} updated, ${nCreates} created, ${nDeletes} deleted.".toString()
    redirect (action:'upload',params:[filename:params.filename, 'updates':updts, 'messages':msgs])
  }

  def validate = {
    def rc = dbMetadataService.validateForUpdate(params.filename)
    if ((rc.messages) || (rc.updates)) {
      if ((rc.messages)){
        flash.errors = rc.messages
      }
      if (rc.updates) {
        println rc.updates
        flash.messages = rc.updates//.values()
      }
    } else {
      flash.message = "File uploaded but has no updates."
    }
    redirect (action:'upload',params:[filename:params.filename, numUpdates:rc.updates?.size(),updtsTodo:rc.updates?"yes":"no"])
  }

}
