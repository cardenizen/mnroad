package us.mn.state.dot.mnroad

import org.jsecurity.SecurityUtils
import org.springframework.web.multipart.MultipartFile

class VehiclePassController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ vehiclePassInstanceList: VehiclePass.list( params ) ]
    }

    def show = {
      //Long id = Long.parseLong(params.id)
        def vehiclePassInstance = VehiclePass.get(params.id)//id )
        vehiclePassInstance.vehicleLocations.size()
        if(!vehiclePassInstance) {
            flash.message = "VehiclePass not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          return [ vehiclePassInstance : vehiclePassInstance]//, 'vr':params.vr ]
        }
    }

    def delete = {
        def vehiclePassInstance = VehiclePass.get( params.id )
        if(vehiclePassInstance) {
            vehiclePassInstance.delete()
            flash.message = "VehiclePass ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "VehiclePass not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def vehiclePassInstance = VehiclePass.get( params.id )

        if(!vehiclePassInstance) {
            flash.message = "VehiclePass not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ vehiclePassInstance : vehiclePassInstance ]
        }
    }

    def update = {
        def vehiclePassInstance = VehiclePass.get( params.id )
        if(vehiclePassInstance) {
            vehiclePassInstance.properties = params
            if(!vehiclePassInstance.hasErrors() && vehiclePassInstance.save()) {
                flash.message = "VehiclePass ${params.id} updated"
                redirect(action:show,id:vehiclePassInstance.id)
            }
            else {
                render(view:'edit',model:[vehiclePassInstance:vehiclePassInstance])
            }
        }
        else {
            flash.message = "VehiclePass not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
      def s = ['file_1':"",'file_2':"",'file_3':"",'file_4':"",'file_5':""]
        def vehiclePassInstance = new VehiclePass()
        vehiclePassInstance.properties = params
        return ['vehiclePassInstance':vehiclePassInstance,'vr':s]
    }
  
    def upload = {
        if(request.method == 'POST') {
          def vehiclePassInstance = new VehiclePass(params)
          vehiclePassInstance.createdBy = SecurityUtils.subject.principal
          def webRootDir = servletContext.getRealPath("/")
          def filesDir = webRootDir + "/files"
          def userDir = new File(filesDir, "/${SecurityUtils.subject.principal}")
          if (!userDir.exists())
            userDir.mkdirs()
          def sdf = new java.text.SimpleDateFormat('MM/dd/yy')
          def filenameMap = ['file_1':"",'file_2':"",'file_3':"",'file_4':"",'file_5':""]
          def resultMap = ['file_1':"",'file_2':"",'file_3':"",'file_4':"",'file_5':""]
          Iterator itr = request.getFileNames();
          while(itr.hasNext()) {
              MultipartFile file = request.getFile(itr.next());
              File destination = new File(userDir, file.getOriginalFilename())
              if (file && !file.isEmpty()) {
                try {
                  file.transferTo(destination)
                } catch (IllegalStateException ise) {
                    println ise.message
                }
                def line
                destination.withReader{r->
                  line = r.readLine();
                }
                Date d = sdf.parse(line.split(",")[1])
                if (d != null) {
                  vehiclePassInstance.dateCollected = d
                }
                def r = vehicleImportService.parseFile(filesDir, userDir, file.originalFilename)
                if (r) {
                resultMap[file.fileItem.fieldName] = r
                filenameMap[file.fileItem.fieldName] = filesDir + "\\" + file.originalFilename
                }
              }
              else // no content in file
              {
                  // failure
              }
            }

            // Trigger an Event.COMPLETE event
//            response.sendError(200,'Done');
          render(view:'validate',model:[vehiclePassInstance:vehiclePassInstance,'fn':filenameMap, 'vr':resultMap])
        }
    }

    def vehicleImportService
    def dataSource

    def save = {
        def vehiclePassInstance = new VehiclePass(params)
        vehiclePassInstance.createdBy = SecurityUtils.subject.principal
          
        def webRootDir = servletContext.getRealPath("/")
        def filesDir = webRootDir + "/files"
        def userDir = new File(filesDir, "/${SecurityUtils.subject.principal}")
        if (!userDir.exists())
          userDir.mkdirs()
        def sdf = new java.text.SimpleDateFormat('MM/dd/yy')
        //handle uploaded file
        def uploadedFile = request.getFile('file')
        if(uploadedFile && !uploadedFile.empty){
          println "Class: ${uploadedFile.class}"
          println "Name: ${uploadedFile.name}"
          println "OriginalFileName: ${uploadedFile.originalFilename}"
          println "Size: ${uploadedFile.size}"
          println "ContentType: ${uploadedFile.contentType}"

          def f = new File( userDir, uploadedFile.originalFilename)
          uploadedFile.transferTo(f)
          def line
          f.withReader{r->
            line = r.readLine();
//            println "first line: $line"
//            r.splitEachLine("\t"){fields->
//                    println "fields on line: $fields"
//            }
          }
          Date d = sdf.parse(line.split(",")[1])
          if (d != null) {
            vehiclePassInstance.dateCollected = d
          }
//          String scriptDir = "$webRootDir/files"
//          def cmd = "groovy $scriptDir/VtImport.groovy local $scriptDir $userDir ${uploadedFile.originalFilename}"
//          def output = cmd.execute().text
//          if (output && output != "") {
//            def of = new File( userDir, uploadedFile.originalFilename+".error")
//            of.withWriter("UTF-8") { w ->
//              w.write(output)
//            }
//          }
        }
//\\ad\mrl\SECTIONS\RESEARCH\MnROAD\Data - Collection\Vehicle Tracking System LVR_Laps\Data\FormattedGPSData

        if(!vehiclePassInstance.hasErrors() && vehiclePassInstance.save()) {
            flash.message = "VehiclePass ${vehiclePassInstance.id} created"

            groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
            String s = vehicleImportService.processFile(sql, filesDir, userDir, uploadedFile.originalFilename, vehiclePassInstance.id)
//            def of = new File( userDir, uploadedFile.originalFilename+".error")
//            of.withWriter("UTF-8") { w ->
//              w.write(s)
//            }

            redirect(action:show,id:vehiclePassInstance.id,params:['vr':s])
        }
        else {
            render(view:'create',model:[vehiclePassInstance:vehiclePassInstance])
        }
    }
}
