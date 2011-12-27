//import us.mn.state.dot.mnroad.EcValues
import java.text.SimpleDateFormat
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import groovy.sql.Sql

class FileResourceController {

    def dataSource
    def queryService

    def index = { redirect(action:list,params:params) }
	static transactional = true
    
    static def allowedMethods = []

    def list = {
		def fileResourceInstanceList = []
		def f = new File( grailsApplication.config.images.location.toString() )
		if( f.exists() ){
			f.eachFile(){ file->
			if( !file.isDirectory() )
				fileResourceInstanceList.add( file.name )
			}
		}
        [ fileResourceInstanceList: fileResourceInstanceList ]
    }

    def delete = {
		def filename = params.id.replace('###', '.')
		def file = new File( grailsApplication.config.images.location.toString() + File.separatorChar +   filename )
		file.delete()
		flash.message = "file ${filename} removed" 
		redirect( action:list )
    }

	def upload = {
		def f = request.getFile('fileUpload')
	    if(!f.empty) {
	      flash.message = 'Your file has been uploaded'
		  new File( grailsApplication.config.images.location.toString() ).mkdirs()
		  f.transferTo( new File( grailsApplication.config.images.location.toString() + File.separatorChar + f.getOriginalFilename() ) )								             			     	
		}    
	    else {
	       flash.message = 'file cannot be empty'
	    }
		redirect( action:list)
	}
/*
      def downloadEcValuesList = {
        response.setHeader("Content-disposition", "attachment; filename=ec_values.csv")
        response.contentType = "application/vnd.ms-excel"
        Writer outputStreamWriter = new OutputStreamWriter(response.outputStream)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
        outputStreamWriter.write("day,hour,qhr,minute,cell,seq,value\n")

        def ecValuesList = EcValues.list()
        ecValuesList.each() {
          outputStreamWriter.write("${sdf.format(it.day)},${it.hour},${it.qhr},${it.minute},${it.cell},${it.seq},${it.value}\n")
        }

        outputStreamWriter.flush()
        outputStreamWriter.close()
      }

      def downloadZippedEcValuesList = {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","inline; filename=ec_values.zip;");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")

        def ecValuesList = EcValues.list()

        ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.outputStream));
        zip.putNextEntry(new ZipEntry("ec_values.csv"));
        int len = 0
        String b = "day,hour,qhr,minute,cell,seq,value\n"
        len += b.size()
        zip.write(b.getBytes(),0,b.size())
        ecValuesList.each() {
          b = "${sdf.format(it.day)},${it.hour},${it.qhr},${it.minute},${it.cell},${it.seq},${it.value}\n".toString()
          len += b.size()
          zip.write(b.getBytes(),0,b.size())
        }
        response.setContentLength(len) 
        zip.flush()
        zip.close()
      }
*/
        def downloadZippedValuesList = {
          println params
          def sensorQuery = """select c.cell_number cell, s.model, s.station_ft station, s.offset_ft offset
, MIN(s.seq) seqbeg, MAX(s.seq) seqend, COUNT(s.seq) numsensors
from mnr.cell c
join mnr.lane l on l.cell_id=c.id
join mnr.layer ly on ly.lane_id=l.id
join mnr.sensor s on s.layer_id=ly.id
WHERE (s.model='TC' or s.model='WM' or s.model='XV')
   AND c.cell_number=?
GROUP BY c.cell_number, s.model, s.station_ft, s.offset_ft ORDER BY c.cell_number, s.model, seqend
"""
          /*
          [CELL:2, MODEL:TC, STATION:111011.25, OFFSET:-9.67, SEQBEG:1, SEQEND:11, NUMSENSORS:11]
          [CELL:2, MODEL:TC, STATION:111026.5, OFFSET:-5.93, SEQBEG:101, SEQEND:101, NUMSENSORS:1]
          [CELL:2, MODEL:TC, STATION:111026.52, OFFSET:-5.93, SEQBEG:102, SEQEND:116, NUMSENSORS:15]
          [CELL:2, MODEL:WM, STATION:111013.14, OFFSET:-9.62, SEQBEG:1, SEQEND:7, NUMSENSORS:7]
           */
          response.setContentType("application/zip");
          response.setHeader("Content-Disposition","inline; filename=tc_values.zip;");
          SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
          ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.outputStream));

          String colList = "day,hour,qhr,minute,cell,seq,value"
          String tableName = "tc_values"
          String q = "select ${colList} from ${tableName}".toString()
          zip.putNextEntry(new ZipEntry("${tableName}.csv".toString()));
          int len = 0
          try {
            Sql sql = new groovy.sql.Sql(dataSource)
            String b = colList+"\n"
            len += b.size()
            zip.write(b.getBytes(),0,b.size())
            def ans = sql.rows(q)
            ans.each {
              b = "${sdf.format(it.day)},${it.hour},${it.qhr},${it.minute},${it.cell},${it.seq},${it.value}\n".toString()
              len += b.size()
              zip.write(b.getBytes(),0,b.size())
            }
            ans = sql.rows(sensorQuery,[params.cell])
            ans.each {
              println "${it}"
            }
          } catch(java.sql.SQLException sqle) {
            println "${sqle.message} - Query with string argument: ${sensorQuery}"
          }
          response.setContentLength(len)
          zip.flush()
          zip.close()
        }

      def tcValues = {
        //http://localhost:9090/mnroad/fileResource/tcValues?cell=3
        println params
        String tableName = "tc_values"
        String colList = "DAY,HOUR,QHR,MINUTE,CELL,SEQ,VALUE\n"
        String q = "SELECT ${colList} FROM MNR.${tableName} WHERE CELL=?"
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","inline; filename=\""+tableName+".zip\";");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
        ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.outputStream));
        int len = 0
        try {
          Sql sql = new groovy.sql.Sql(dataSource)
          String b = ""
          if (params.cell) {
            def ans = sql.rows(q.toString(),[params.cell])
            if (ans.size() > 0) {
              zip.putNextEntry(new ZipEntry("${tableName}.csv".toString()));
              len += colList.size()
              zip.write(colList.getBytes(),0,colList.size())
            }
            ans.each {
              b = "${sdf.format(it.day)},${it.hour},${it.qhr},${it.minute},${it.cell},${it.seq},${it.value}\n".toString()
              len += b.size()
              zip.write(b.getBytes(),0,b.size())
            }
          }
        } catch(java.sql.SQLException sqle) {
          println "${sqle.message} - Query with string argument: ${sensorQuery}"
        }
        println "${len} bytes."
        response.setContentLength(len)
        response.setHeader("${len}","content-len")
        zip.flush()
        zip.close()
      }

      def sensorsInGroup = {
        //http://localhost:9090/mnroad/fileResource/sensorsInGroup?cell=1&model=TC&station=110640.68&offset=-9.76

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","inline; filename=\"sensors.zip\";");
        ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.outputStream));
        int len = 0
        try {
          //Items required by MATLAB code
          //"Cell","Model","Station","Offset","Seq","Location_group","Depth_ft"
          if (params.cell && params.model && params.station && params.offset) {
            def sensors = queryService.getSensorsInGroup(params.model, params.cell, params.station, params.offset)
            if (sensors.size() > 0) {
              zip.putNextEntry(new ZipEntry("sensorsInGroup.csv"));
              String header = "SEQ,LOCATION_GROUP,SENSOR_DEPTH_FT\n"
              len += header.size()
              zip.write(header.getBytes(),0,header.size())
              sensors.each {
                println it
                len += it.size()
                zip.write(it.getBytes(),0,it.size())
              }
            }
          }
        } catch(java.sql.SQLException sqle) {
          println "${sqle.message} - Query with string argument: ${q}"
        }
        response.setContentLength(len)
        response.setHeader("${len}","content-len")
        zip.flush()
        zip.close()
      }

      def sensorSummary = {
        //http://localhost:9090/mnroad/fileResource/sensorSummary
        println params
        String tableName = "sensor"
        String colList = "CELL,MODEL,STATION,OFFSET,SEQBEG,SEQEND,NUMSENSORS\n"
        def q = "SELECT CELL, MODEL, STATION_FT STATION, OFFSET_FT OFFSET, MIN(SEQ) SEQBEG, MAX(SEQ) SEQEND, COUNT(SEQ) NUMSENSORS FROM MNR.${tableName} WHERE (MODEL='TC' OR MODEL='WM' OR MODEL='XV') GROUP BY CELL, MODEL, STATION_FT, OFFSET_FT ORDER BY CELL, MODEL, SEQEND"
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition","inline; filename=\""+tableName+".zip\";");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
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
          println "${sqle.message} - Query with string argument: ${sensorQuery}"
        }
        println "${len} bytes."
        response.setContentLength(len)
        response.setHeader("${len}","content-len")
        zip.flush()
        zip.close()
        }

  List cellTemperatures(String device, String frequency, int year, int cell, boolean values) {

  }
}
