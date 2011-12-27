import groovy.sql.Sql
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.SensorModel
import us.mn.state.dot.mnroad.Sensor
import us.mn.state.dot.mnroad.Layer
/*
 Use command line "grails test shell" to start Grails with a
 Groovy Shell UI (see http://groovy.codehaus.org/Groovy+Shell).
 At the groovy:000> prompt run "load scripts/SensorImports.groovy"
 */
includeTargets << grailsScript("Init")

target(main: "Import Sensor Data from a spreadsheet.") {
  def nRows = 0
  def nRowsInserted = 0
  def b4Mnroad = new Date(90,0,1)
  def now = new Date()
  def parms = []
  def types = ["i","c","d","d","c","n","n","n","c","c","c","d","d","l","i","c","c","l","c","c"]
  def names = ["seq","description","dateInstalled","dateRemoved","cabinet","stationFt","offsetFt","sensorDepthIn","orientation","collectionType","dynamicStatic","dateCreated","lastUpdated","sensorModelId","version","sensorId","currentStatus","layerId","createdBy","lastUpdatedBy"]
  def attrs = [:]
  String uploadFilename = "SENSOR Cells 70-72.xlsx"
  if (uploadFilename) {
    // get the full path name of the file from the temp directory
    def fn = "C:/grailsApps/1.3.5/mnroad"+"/"+uploadFilename
    def wb = new XSSFWorkbook(fn)
    def sheet = wb.getSheetAt(0)
    // Process the data from the sheet and save it in Grails domain objects
    nRows = sheet.getPhysicalNumberOfRows()
    println "Sheet has ${sheet.getPhysicalNumberOfRows()} rows."
    for (int i = 1; i < nRows; i++ ) {
      def row = sheet.getRow(i)
      int j = 0
      parms.clear()
      for (Iterator ait = row.cellIterator(); ait.hasNext(); ) {
        Cell cell = ait.next();
        def s = cell.getRawValue()
        def ct = cell.getCellType()
        if (s == null) {
          if ((types[j] == "d") || (types[j] == "n"))
            parms << null
          else
            parms << ""
        }
        else if (ct == Cell.CELL_TYPE_NUMERIC)     {
          def dv = cell.getDateCellValue()
          if ((dv instanceof java.util.Date)
                  && dv.before(now)
                  && dv.after(b4Mnroad)
            ) {
            // the cell is a date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy")
            java.util.Date dt = sdf.parse(cell.toString())
            def sqldt = new java.sql.Date(dt.getTime())
            parms << sqldt
          }
          else {
            if (types[j] == "n") {
              parms << Double.parseDouble(cell.toString())
            }
            else if (types[j] == "l") {
              parms << Long.parseLong(s)
            }
            else if (types[j] == "i") {
              parms << Integer.parseInt(s)
            }
          }
        }
        else if (ct == Cell.CELL_TYPE_STRING) {
          def cv = cell.toString()
          parms << "${cv}".toString()
          }
        attrs.put(names[j],parms[j])
        j++
       }
      def fkattrs = [:]
      fkattrs.put("sensorModelId",attrs.sensorModelId)
      fkattrs.put("layerId",attrs.layerId)
      SensorModel sm = SensorModel.get(attrs.sensorModelId)
      if (!attrs.layerId) {
         println "row ${i+1}: ${sm}, ${attrs.seq}, ${attrs.description}, ${attrs.stationFt}, ${attrs.offsetFt}, ${attrs.sensorDepthIn}"
      }
      else {
        Layer layer = Layer.get(attrs.layerId)
        if (!layer) {
           println "Layer not found for ID: ${attrs.layerId}"
        } else {
          Sensor sensor = new Sensor(attrs-fkattrs)
          sensor.sensorModel = sm
          sensor.layer = layer
        if (!sensor.validate()) {
          def errorMessages = sensor.errors.allErrors.collect { it }
          println "Sensor: ${sensor} failed validate: " + errorMessages
        }
        else {
          sensor.save(flush:true)
          nRowsInserted++
       }

        }
      }
    }
    println "${nRows} rows read."
    println "${nRowsInserted} rows inserted."
  }
}

setDefaultTarget(main)

//delete from mnr.sensor where id >= 14670;
//drop SEQUENCE SENSOR_SEQ;
//CREATE SEQUENCE SENSOR_SEQ MINVALUE 1 START WITH 14670 INCREMENT BY 1 CACHE 20;

