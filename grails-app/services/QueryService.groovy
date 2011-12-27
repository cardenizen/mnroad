import groovy.sql.Sql
import java.sql.SQLException
import us.mn.state.dot.mnroad.MrUtils
import us.mn.state.dot.mnroad.Sensor
import us.mn.state.dot.mnroad.Cell
import org.apache.log4j.Logger
import java.text.SimpleDateFormat
import java.sql.Date

class QueryService {
  static Logger log = Logger.getLogger(QueryService.class.name)
    /*
    Query Note
    You have a List , say abc = ['a','v','f','w',...lots more]
    SELECT * FROM table WHERE code not in ('a','v','f','w','e',...lots more)
    use sql.findAll("SELECT * FROM table WHERE code not in ('" + abc.join("','") + "')"
     */
    boolean transactional = true
    def dataSource
    def sessionFactory

    static def baseSelectPhrase = """ FROM MNR.SENSOR S
 JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID=SM.ID
 JOIN MNR.LAYER L ON S.LAYER_ID=L.ID
 JOIN MNR.LANE LN ON L.LANE_ID=LN.ID
 JOIN MNR.CELL C ON LN.CELL_ID=C.ID
WHERE c.CELL_number=?
"""

    List sensorsForCellNumber(def cellNumber, Integer fromOffset, Integer batchSize) {
      def rc = []
//      def hibernateMetaClass = sessionFactory.getClassMetadata("us.mn.state.dot.mnroad.Sensor")
      def sensorSelectPhrase = """SELECT UNIQUE * FROM (
SELECT S.ID SENSOR_ID
,LN.ID LANE_ID
,L.ID LAYER_ID
,C.ID CELL_ID
,C.DEMOLISHED_DATE
,S.DATE_REMOVED
,NVL(TO_CHAR(c.CELL_number),'All Cells') CELL
,NVL(sm.MODEL,'All Models') MODEL
,S.SEQ
""" + baseSelectPhrase + """UNION ALL
select * from (
SELECT S.ID SENSOR_ID
,NULL LANE_ID
,S.LAYER_ID
,NULL CELL_ID
,NULL DEMOLISHED_DATE
,S.DATE_REMOVED
, case
when length(sensor_id)=7 then substr(sensor_id,1,2)
when length(sensor_id)=8 then substr(sensor_id,1,3)
end CELL
, case
when length(sensor_id)=7 then substr(sensor_id,3,2)
when length(sensor_id)=8 then substr(sensor_id,4,2)
end MODEL
,S.SEQ
FROM MNR.SENSOR S
WHERE LAYER_ID IS NULL
) where
CELL=?
)
ORDER BY CELL,MODEL,SEQ
"""
      def sqs = "SELECT * FROM ( SELECT A.*, ROWNUM RNUM FROM (${sensorSelectPhrase}) A WHERE ROWNUM <= ? ) WHERE RNUM  >= ?"
      groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
      sql.eachRow(sqs.toString(), [cellNumber,cellNumber,batchSize,fromOffset]) { row ->
        Sensor qs = Sensor.get(row.SENSOR_ID)
        rc.add(qs)
      }

      return rc
    }

    int distressesForCellNumberCount(def cellNumber) {
      def rc = 0

      def sensorCountPhrase = "select count(*) NUMROWS" + baseSelectPhrase
      groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
      rc = sql.firstRow(sensorCountPhrase.toString(),[cellNumber]).NUMROWS

      return rc
    }

    int sensorsForCellNumberCount(def cellNumber) {
      def rc = 0

      def sensorCountPhrase = "select count(*) NUMROWS" + baseSelectPhrase
      groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
      rc = sql.firstRow(sensorCountPhrase.toString(),[cellNumber]).NUMROWS

      return rc
    }

  /*
  * Returns expected number of rows
   */
    int parameterCheck(String model, int cell, Date dFrom, Date dTo) {
      int nRows = 0
      def q = ""
      try {
        List cells = Cell.findAllByCellNumber(cell)
        if (cells.size() < 1) {
          log.error("rowPerSensorPerDayByHour - Cell, (${cell}) not found.")
        }
        cells.each { rm ->
          boolean cellOk = true
          def rtsf = rm.constructionEndedDate
          def rtst = rm.demolishedDate?:new Date()
          if (dFrom.before(rtsf) || dTo.after(rtst)) {
            cellOk = false
          }
          if (cellOk) {
            int cellModelReadingCount = 0
            Sql sql = new groovy.sql.Sql(dataSource)
            q = "SELECT COUNT(*) FROM MNR.${model}_VALUES WHERE CELL = ? AND DAY BETWEEN ? AND ?"
            def sqlDateFrom = new java.sql.Date(dFrom.getTime())
            def sqlDateTo = new java.sql.Date(dTo.getTime())
            sql.eachRow(q,[cell, sqlDateFrom, sqlDateTo]) { row ->
              nRows += row[0]
              if (nRows == 0)
                log.info "${nRows} ROWS FROM MNR.${model}_VALUES CELL ${cell} BETWEEN ${dFrom} AND ${dTo}"
            }
          }
        }
      } catch (SQLException sqle) {
          log.info sqle.message.substring(0,sqle.message.size() - 1) + ": - ${model}_values"
      }

    return nRows
    }

    List cellDateOverlap(def roadSectionId) {
      def msgs = []
      Sql sql = new groovy.sql.Sql(dataSource)
      // ID,CELL,DESIGN,CELL_DEMOLISHED,NEXT_CONSTRUCTION_BEGAN,NEXT_CELL_ID,NEXT_CELL,NEXT_DESIGN
      def q = """
SELECT CE.ID,CE.CELL,CE.DESIGN,TO_CHAR(CE.CELL_DEMOLISHED,'mm/dd/yyyy') CELL_DEMOLISHED
,TO_CHAR(CE.NEXT_CONSTRUCTION_BEGAN,'mm/dd/yyyy') NEXT_CONSTRUCTION_BEGAN,CE.NEXT_CELL_ID,CE.NEXT_DESIGN FROM (
SELECT C.ROAD_SECTION_ID
,CD.ID
,CD.CELL,CD.DESIGN
,CELL_END_DATE CELL_DEMOLISHED
,LEAD (CONSTRUCTION_BEGAN_DATE,1,NULL) OVER (PARTITION BY CELL ORDER BY CD.ID) NEXT_CONSTRUCTION_BEGAN
,LEAD (CD.ID,1,NULL) OVER (PARTITION BY CELL ORDER BY CD.ID) NEXT_CELL_ID
,LEAD (DESIGN,1,NULL) OVER (PARTITION BY CELL ORDER BY CD.ID) NEXT_DESIGN
FROM MNR.CELL_DESIGN CD JOIN MNR.CELL C ON C.ID=CD.ID
) CE JOIN MNR.ROAD_SECTION R ON CE.ROAD_SECTION_ID=R.ID
WHERE NEXT_CONSTRUCTION_BEGAN IS NOT NULL AND NEXT_CONSTRUCTION_BEGAN < CELL_DEMOLISHED
AND ROAD_SECTION_ID=?
      """
      sql.eachRow(q.toString(), [new BigDecimal(roadSectionId)]) { row ->
        msgs << "First layer of cell ${row.CELL} design ${row.NEXT_DESIGN} was built on ${row.NEXT_CONSTRUCTION_BEGAN} before previous cell (design ${row.DESIGN}) was demolished on ${row.CELL_DEMOLISHED}"
      }
      return msgs
    }

    boolean hasTable(String tableName) {
      boolean b = false
      Sql sql = new groovy.sql.Sql(dataSource)
      //def q = "SELECT COUNT(*) NUM FROM USER_TABLES WHERE TABLE_NAME="
      //def ans = sql.firstRow(q.toString(), [tableName])
      def q = "SELECT COUNT(*) NUM FROM all_tables where owner = 'MNR' and table_name like '"+tableName+"%'"
      def ans = sql.firstRow(q.toString())
      if (ans?.NUM >= 1)  {
        b = true
      } else {
        def logmsg = "Table ${tableName} not found. SQL: ${q}'${tableName}'"
        log.info logmsg        
      }
      return b
    }

    String mat_samplesHelpBalloonData (String column) {
      def rc =   ""
      if (column) {
          Sql sql = new groovy.sql.Sql(dataSource)
          def query = "SELECT ${column} VAL, COUNT(*) COUNT FROM MNR.MAT_SAMPLES GROUP BY ${column} ORDER BY ${column}"
          boolean first = true
          sql.eachRow(query.toString(), { row ->
            def d = "${row.val} (${row.count})"
          if (!first) {rc += "<br/>"} else {first = false}
            rc += d
            }
            )
        }
      return rc
    }

    def sensorsByCellAndModelAndDates(Cell cell, List models) {
      def s = Sensor.createCriteria()
      def fdt = (cell.earliestLayerDate() < cell.constructionEndedDate)?cell.earliestLayerDate():cell.constructionEndedDate
      def tdt = (cell.demolishedDate==null)?new Date():cell.demolishedDate
      def sensors = s.list(){
        'in' ("model", models)
        eq ("cell",cell.cellNumber)
        between ("dateInstalled",fdt,tdt)
        order ('stationFt','asc')
        order ('offsetFt','asc')
        order ('sensorDepthIn','asc')
      }
      return sensors
    }

    def sensorsInLayer = { layer ->
      def s = Sensor.createCriteria()
      def sensors = s.list(){
        eq ('layer',layer)
        isNull('dateRemoved')
        order ('model','asc')
        order ('sensorDepthIn','asc')
      }
      return sensors
    }
/*
 * Find sensors not yet assciated with a layer
 */
    def sensorsInNoLayer = { cell_number ->
      def sm = [:]
      def q = "SELECT ID, S.SENSOR_ID FROM MNR.SENSOR S where S.LAYER_ID IS NULL ORDER BY SENSOR_ID"
      groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
      sql.eachRow(q.toString()) { row ->
        def sid = row.SENSOR_ID
        def id = 0
        def cellstr = cell_number.toString()
        if (sid?.size() == 6)  {
          if (sid[0] == cellstr)
            sm.put(row.ID, Sensor.get(row.ID))
        }
        if (sid?.size() == 7)  {
          if (sid[0..1] == cellstr)
            sm.put(row.ID, Sensor.get(row.ID))
        }
        if (sid?.size() == 8)  {
          if (sid[0..2] == cellstr)
            sm.put(row.ID, Sensor.get(row.ID))          
        }
      }
      return sm
    }
/*
 * Find sensors not yet assciated with a layer
 */
  def sensorsInNoCell = {
    def cm = [:]
//    def sm = [:]
    def layerlessSensors = []
    groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
    def q = "SELECT ID, S.SENSOR_ID FROM MNR.SENSOR S where S.LAYER_ID IS NULL and S.SENSOR_ID is not null ORDER BY SENSOR_ID"
    sql.eachRow(q.toString()) { row ->
      def sid = row.SENSOR_ID
      def id = 0
      def cellstr = ""
      if (sid?.size() == 6)  {
        cellstr = sid[0]
      }
      if (sid?.size() == 7)  {
        cellstr = sid[0..1]
      }
      if (sid?.size() == 8)  {
        cellstr = sid[0..2]
      }
      if (cellstr) {
        def cn = Integer.parseInt(cellstr)
        if (Cell.countByCellNumber(cn) == 0) {
          if (cm.keySet().contains(cn)) {
            cm.get(cn).put (row.ID, Sensor.get(row.ID))
          } else {
            def sm = [:]
            sm.put (row.ID, Sensor.get(row.ID))
            cm.put(cn, sm)
          }
//          sm.put(row.ID, Sensor.get(row.ID))
        }
      }
    }
//    return sm
    return cm
  }

/*
        eq ("cell",cell.cellNumber)

   */
    def sensorsInCell = { cell ->
      def s = Sensor.createCriteria()
      def fdt = (cell.earliestLayerDate() < cell.constructionEndedDate)?cell.earliestLayerDate():cell.constructionEndedDate
      def tdt = (cell.demolishedDate==null)?new Date():cell.demolishedDate
      def sensors = s.list(){
        eq ('layer.lane.cell.cellNumber',cell_number)
        ne ('sensorModel.model','CT')
        between ("dateInstalled",fdt,tdt)
        order ('offsetFt','asc')
        order ('sensorDepthIn','desc')
        order ('sensorModel','asc')
        order ('seq','asc')
        order ('stationFt','asc')
      }
      return sensors
    }
/*
    def getLaneId = {
      def query = ""
      try {
        Sql sql = new groovy.sql.Sql(dataSource)
//          -- Selects the id of the lane given a cell_number, the lane description, and a date.
      query = """
select id from (
select l.id, rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1
and l.name='Driving'
and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
)
where age_rank=(
select max(age_rank) from
(
select rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1 and l.name='Driving'
and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
))
"""
        def result = sql.firstRow(query,[sensor.cell, sensor.seq])
        log.info result
      } catch(java.sql.SQLException sqle) {
        log.info "${sqle.message} - Query with string argument: ${query}"
      }
    }

  private List getDbData(String query, Integer cell, Date from, Date to) {
    def rc = []
    try {
      Sql sql = new groovy.sql.Sql(dataSource)
      def q = query
      def sqlDateFrom = new java.sql.Date(from.getTime())
      def sqlDateTo = new java.sql.Date(to.getTime())
      def lastDay
      def lastSeq = ""
      def ans = sql.rows(q,[cell, sqlDateFrom, sqlDateTo])
      ans.each{ row ->
        rc.add(row)
      }
    } catch (SQLException sqle) {
        log.info ("SQLException: ${sqle.message}")
        lineData.put('error', 'Database query error - see log for explaination.')
    }
    return rc
  }
*
  private def formatLineData(List rows, List types) {
    def lineData = [:]
    def labels = ['Date']
    def dates = []
    def points = [:]
    def lastDay
    def lastSeq = ""
    rows.each{ row ->
      def dayString = gaDateFormat(row,types.contains('datetime')?true:false)
      String seq = "${row.SEQ}".toString()
      def val = row.VALUE
      if (!dates.contains(dayString))   {
        dates << dayString
      }
      if (seq != lastSeq) {
        if (points.keySet().contains(seq)) {
          def sa = points.get(seq)
          sa.put(dayString, val)
        } else {
          def seqPoints = [:]
          seqPoints.put(dayString, val)
          points.put(seq, seqPoints)
          types << 'number'
          labels << seq
        }
      }
      else if (points.keySet().contains(lastSeq)) {
        def sa = points.get(lastSeq)
        sa.put(dayString, val)
      }
      lastSeq = seq
      lastDay = row.DAY
    }
    lineData.put('labels',labels)
    lineData.put('types',types)
//  This section ensures that each group of observations (a "sequence") has one for each date
//  If a "sequence" has no observation for a date, a null is inserted
    def data = [:]
    if (dates) {
      dates.sort()
      dates.each { aDate ->
        //println aDate
        points.keySet().each { pointsKey ->
          def dateMap = points.get(pointsKey)
          def dateVal = dateMap.get(aDate)
          if (dateVal) {
            if (!data.keySet().contains(pointsKey)) {
              def v = []
              v << dateVal
              data.put(pointsKey,v)
            } else {
              data.get(pointsKey) << dateVal
            }
          } else {
            if (!data.keySet().contains(pointsKey)) {
              def v = []
              v << null
              data.put(pointsKey,v)
            } else {
              data.get(pointsKey) << null
            }

          }
        }
      }
    }
    lineData.put('dates',dates)
    lineData.put('data', data)
//    log.info "lineData.data size: ${data.size()}"
    return lineData
  }
*/
   /*
    Formats a date for use by google analytics  \
    google.visualization.AnnotatedTimeLine
   */
  private String gaDateFormat(Map row, Boolean hasHour) {
    def rc = ""
    if (row.DAY) {
      def day = row.DAY
      Calendar c = Calendar.getInstance()
      c.setTimeInMillis(day.getTime())
      def yr = c.get(Calendar.YEAR)
      def month = "${c.get(Calendar.MONTH)}".padLeft(2,'0')
      def dayOfMonth = "${c.get(Calendar.DAY_OF_MONTH)}".padLeft(2,'0')
      if (hasHour) {
        def hourOfDay = "${row.HOUR}".padLeft(2,'0')
        def minute = "${row.MINUTE}".padLeft(2,'0')
        rc = "Date(${yr},${month},${dayOfMonth},${hourOfDay},${minute})"
      }
      else
        rc = "Date(${yr},${month},${dayOfMonth})"
    }
    return rc
  }
  
  def sensorData(String query, model, Integer cell, java.util.Date from, java.util.Date to) {
//    println "queryService.sensorData\n\t$from\t$to"
    def results = [:]
    def map = [:]
    map.version = 0.6
    map.reqId = '0'
    map.status = 'warning'

    def ld = readData(query, cell, from, to)
    // variable "ld"
    // contains 4 maps with keySets ["dates","labels","types","data"]
    // "dates, types, and "labels" contain column metadata
    // and "data contains the line plot data

    def readingCount = 0
    if (ld.data) {
      def columns = []
      def rows = []
      // iterate columns
      ld.labels.eachWithIndex {obj, i ->
        columns << [id: '', label: obj, pattern: '', type: ld.types.get(i)]
      }
      if (ld.data.keySet().size() > 0) {
        // iterate rows
        ld.dates.eachWithIndex {obj, i ->
          def cells = []
          cells << ['v': obj];
          ld.data.keySet().each { key ->
            if (ld.data.get(key).size() == ld.dates.size()) {
              cells << ['v': ld.data.get(key).get(i)];
            }
            else {
              log.info "index $i, key $key, ${ld.data.get(key).size()} != ${ld.dates.size()}"
            }
          }
          rows << ['c':cells]
        }
      } else {
        log.info  "ld data keySet is empty"
      }
      def table = [cols: columns, rows: rows]
      map.table = table
      map.status = 'ok'
      readingCount = rows.size()*(columns.size()-1)
    }
    // see http://code.google.com/apis/visualization/documentation/dev/implementing_data_source.html
    results.put('googleVisualizationMap', map)
    println "googleVisualizationMap data.entrySet.size: ${ld.data.entrySet().size()}  $readingCount observations in ${to-from} days"
    return results
  }

  private def readData(String query, int cell, java.util.Date from, java.util.Date to) {
// variable "ld"
    // contains 4 maps with keySets ["dates","labels","types","data"]
    // "dates, types, and "labels" contain column metadata
    // and "data contains the line plot data
    def ld = [:]
    ld.put('types', [])
    if (query.toString().toUpperCase().contains('GROUP BY'))
      ld.types << 'date'
    else
      ld.types << 'datetime'
    ld.put('labels', ['Date'])
    ld.put('dates', [])

    // datapts is the map for the data
    // where each keyset member is a SEQ and
    // each entryset member is an array of sensor readings
    // that corresponds to the dates array
    def datapts = [:]
//    def points = [:]
    def lastDay
    def lastSeq = ""
    try {
      Sql sql = new Sql(dataSource)
      def ans = sql.rows(query, [cell, new Date(from.getTime()), new Date(to.getTime())])
      ans.each {row ->
        def dayString = gaDateFormat(row, ld.types.contains('datetime') ? true : false)
        String seq = "${row.SEQ}".toString()
        def val = row.VALUE
        if (!ld.dates.contains(dayString)) {
          ld.dates << dayString
        }

        if (seq != lastSeq) {
          def va = []
          va << val
          datapts.put(seq,va)
          ld.types << 'number'
          ld.labels << seq
        }
        else {
          datapts.get(seq) << val
        }

//        if (seq != lastSeq) {
//          if (points.keySet().contains(seq)) {
//            def sa = points.get(seq)
//            sa.put(dayString, val)
//          } else {
//            def seqPoints = [:]
//            seqPoints.put(dayString, val)
//            points.put(seq, seqPoints)
//            ld.types << 'number'
//            ld.labels << seq
//          }
//        }
//        else if (points.keySet().contains(lastSeq)) {
//          def sa = points.get(lastSeq)
//          sa.put(dayString, val)
//        }

        lastSeq = seq
        lastDay = row.DAY
      }
    } catch (SQLException sqle) {
      log.info("SQLException: ${sqle.message}")
      ld.put('error', 'Database query error - see log for explaination.')
    }
//  This section ensures that each group of observations (a "sequence") has one observation for each date
//  If a "sequence" has no observation for a date, a null is inserted
//    def ap = dataForAllDays(ld, points)
//    ld.put('data', dataForAllDays(ld, points))
    ld.put('data', datapts)
    return ld
  }

//  This section ensures that each group of observations (a "sequence") has one observation for each date
//  If a "sequence" has no observation for a date, a null is inserted
  private def dataForAllDays(Map ld, def points) {
    def data = [:]
    if (ld.dates) {
      ld.dates.sort()
      ld.dates.each {aDate ->
        points.keySet().each {pointsKey ->
          def dateMap = points.get(pointsKey)
          def dateVal = dateMap.get(aDate)
          if (dateVal) {
            if (!data.keySet().contains(pointsKey)) {
              def v = []
              v << dateVal
              data.put(pointsKey, v)
            } else {
              data.get(pointsKey) << dateVal
            }
          }
//          else {    //only if missing points will be added
//            if (!data.keySet().contains(pointsKey)) {
//              def v = []
//              v << null
//              data.put(pointsKey, v)
//            } else {
//              data.get(pointsKey) << null
//            }
//          }
        }
      }
    }
    return data
  }

  /*
 This list of sensor seq numbers, SENSOR_COUNTS, comes from a select distinct seq
 on all _VALUES tables.
  */
  List sensorSeqFromSensorCounts(String cellNumber, String model, String table,
                         java.sql.Date fdate, java.sql.Date tdate) {
      def rc = []
      Sql sql = new groovy.sql.Sql(dataSource)
      Integer cn = 0
      try {
        cn = Integer.parseInt(cellNumber)
        def q = "SELECT UNIQUE SEQ FROM MNR.SENSOR_COUNTS WHERE CELL=? AND TABLE_NAME=? AND ((FROM_DAY BETWEEN ? AND ?) OR (TO_DAY BETWEEN ? AND ?)) ORDER BY SEQ"
        def ans = sql.rows(q,[cn, table, fdate, tdate, fdate, tdate])
        ans.each{ row ->
          rc << row.SEQ
        }
        if (rc.size()==0) {
          println q
          println "${[cn, table, fdate, tdate, fdate, tdate]}"
        }
      }
      catch (NumberFormatException nfe) {
        log.info "NumberFormatException on $cellNumber in QueryService"
      }

      return rc //rc.sort()
    }
  
      List modelListFromCellId(String cellId) {
        def rc = []

        Sql sql = new groovy.sql.Sql(dataSource)
        Integer cid = Integer.parseInt(cellId)
        def q = """SELECT C.CELL_NUMBER, SM.MODEL
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID=L.ID
JOIN MNR.SENSOR_MODEL SM ON SM.ID=S.SENSOR_MODEL_ID
WHERE
SUBSTR(SM.DATA_VALUES_TABLE,3,7)='_VALUES' and C.ID=?
GROUP BY C.CELL_NUMBER,SM.MODEL
ORDER BY MODEL"""
        def ans = sql.rows(q.toString(),[cid])
        ans.each { row ->
          rc << row.MODEL
        }
        return rc
      }

    List modelListFromTableNames(String cellNumber) {
      def rc = []
      def q = "SELECT SUBSTR(TABLE_NAME,1,2) MODEL FROM MNR.SENSOR_COUNTS WHERE CELL=? GROUP BY TABLE_NAME,CELL ORDER BY TABLE_NAME"
      Sql sql = new groovy.sql.Sql(dataSource)
      Integer cn = Integer.parseInt(cellNumber)
      def ans = sql.rows(q,[cn])
      ans.each { row ->
        rc << row.MODEL
      }
      return rc
    }

//    List modelListFromSensor(String cellNumber) {
//      def rc = []
//
//      Sql sql = new groovy.sql.Sql(dataSource)
//      Integer cn = Integer.parseInt(cellNumber)
//      def ans = sql.rows("select unique s.model from sensor s where s.cell=? order by model",[cn])
//      // queries each value table to see
//      // 1) if the database has a selectedModel_VALUES table
//      // and if so 2) if the tables has any data for a chosen cell number
//      // Commenting this out - takes too much time!
//      // anyway its only here because the current sensor table data is incomplete
//      ans.each{ row ->
//        // 1)
//        if (hasTable(row.MODEL+'_VALUES')) {
//        /*
//          // 2)
//          if (sensorSeqFromSensorCounts(cellNumber,row.MODEL).size() > 0) {
//            rc << row.MODEL
//          }
//        */
//        rc << row.MODEL
//        }
//      }
//      return rc
//    }
/*
    List getSensors(def layer) {
      def sensors = []
      String tableName = "sensor"
      String colList = "CELL,MODEL,SEQ,DESCRIPTION,DATE_INSTALLED,DATE_REMOVED,CABINET,STATION_FT,OFFSET_FT,NORTHING_FT,EASTING_FT,PAVE_ELEV_FT,SENSOR_ELEV_FT,SENSOR_DEPTH_IN,ORIENTATION,COLLECTION_TYPE,DYNAMIC_STATIC,LAST_UPDATED,DATE_CREATED,COMMENTS,SENSOR_MODEL_ID,ID,VERSION,SENSOR_ID,LOCATION_MATERIAL,LOCATION_GROUP,CURRENT_STATUS,LOCATION_LAYER,LAYER_ID,CREATED_BY,LAST_UPDATED_BY"
      Sql sql = new groovy.sql.Sql(dataSource)
      def q = "SELECT ${colList} FROM MNR.SENSOR WHERE CELL = ? AND OFFSET_FT between ? and ? AND SENSOR_DEPTH_IN between ? and ? ORDER BY CELL,MODEL,SEQ,STATION_FT,OFFSET_FT,SENSOR_DEPTH_IN"
      try {
        def cell = layer.lane.cell.cellNumber
        def fromOffset = layer.lane.offsetRange().get("from")
        def toOffset = layer.lane.offsetRange().get("to")
        def fromDepth = layer.depthRange?.get("top")
        def toDepth = layer.depthRange?.get("bottom")
        def ans = sql.rows(q.toString(),[cell,fromOffset,toOffset,fromDepth,toDepth])
        if (ans.size() > 0) {
          ans.each {
            log.info it
          }
        }
      } catch(java.sql.SQLException sqle) {
        log.info "${sqle.message} - Query with string argument: ${sensorGroupQuery}"
      }
      return sensors
    }

    def getCells(Integer from) {
      def rc = []
      def q = "SELECT UNIQUE CELL,ID,FROM_DATE,TO_DATE FROM MNR.CELLS order by CELL,ID"

      Sql sql = new groovy.sql.Sql(dataSource)
      def ans = sql.rows(q.toString())
      ans.each { row ->
        def cn = row.CELL.intValue()
        def id = row.ID.longValue()
        def from_date = row.FROM_DATE
        def to_date = row.TO_DATE
        def df = "yyyy-MM-dd"
        def td = MrUtils.formatDate((to_date == null)?new Date(System.currentTimeMillis()):new Date(to_date.getTime()),df)
        def fd = MrUtils.formatDate(new Date(from_date.getTime()),df)
        if ((from == 0) || (cn >= from))
          rc << "${cn},${id},${fd},${td}\n"

      }

      return rc
    }
*/
    def getCellMap(def id) {
      def rc = [:]
      def q = """
SELECT ID, CELL, FIRST_LAYER_DATE
, LAST_LAYER_DATE, CONSTRUCTION_ENDED_DATE
, TO_DATE, TYPE, DESIGN_NUMBER FROM MNR.CELLS where ID=?"""
      Sql sql = new groovy.sql.Sql(dataSource)
      def ans = sql.rows(q.toString(),[id])
      ans.each { row ->
        def cn = row.CELL.intValue()
        def from_date = row.FIRST_LAYER_DATE
        def to_date = row.TO_DATE
        def td = (to_date == null)?new Date(System.currentTimeMillis()):new Date(to_date.getTime())
        def fd = new Date(from_date.getTime())
        rc.put("id",id)
        rc.put("cellNumber", cn)
        rc.put("fromDate",fd)
        rc.put("toDate",td)
      }

      return rc
    }

  /*
   *
   * This method returns a list on table names in which sensor reading values are stored.  Most tables contain
   * only a single value column named 'VALUE'.  Those that do not follow this convention are identified as a member
   * of the "valueColumnNames" map in Config.groovy.  The map key is table name and the value is a comma separated
   * list of the names of columns which hold sensor reading values.
   *
   */
  def sensorTableNamesViaUserTables(def config, int year) {
    def cabinetTempModel='CT'
    def rc = [:]
    def q = "SELECT substr(table_name,1,2) model_from_table_names FROM all_tables where owner = 'MNR' and table_name like '%_VALUES' and substr(table_name,3,2)='_V' order by table_name"

    Sql sql = new groovy.sql.Sql(dataSource)
    def ans = sql.rows(q.toString())
    def modelsFromTableNames = []
    ans.each { row ->
      if (row.MODEL_FROM_TABLE_NAMES != cabinetTempModel)
        modelsFromTableNames << row.MODEL_FROM_TABLE_NAMES
    }
    def tns = []
    // Adjust names for the large table model types to use Views
    modelsFromTableNames.each {
      if (config.largeTables.contains(it)) {
        if (year>0)
          tns << it+'_VALUES_'+year   // if only one year is requested
        else
          tns << it+'_VALUES_ALL'  // or use the View
      }
      else {
        tns << it+'_VALUES'
      }
    }
    tns.each {
      if (config.valueColumnNames.keySet().contains(it)) {
        rc.put(it,"${config.valueColumnNames.get(it)}")
      }
      else
        rc.put(it,"VALUE")
    }
    return rc
  }

  def retrofitSensors = { cellNum, model,constrPeriodLenDays ->

    def q = """
SELECT * FROM (
SELECT
S.ID,C.CELL_NUMBER CELL,SM.MODEL,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.DATE_INSTALLED,S.DATE_REMOVED
FROM MNR.SENSOR S JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID=SM.ID JOIN MNR.LAYER L ON S.LAYER_ID=L.ID JOIN MNR.LANE LN ON L.LANE_ID=LN.ID JOIN MNR.CELL C ON LN.CELL_ID=C.ID JOIN MNR.MATERIAL M ON M.ID = L.MATERIAL_ID
UNION ALL
SELECT S_ID ID, CELL, MODEL, SEQ, STATION, OFFSET, DATE_INSTALLED, DATE_REMOVED FROM (
    SELECT
    CCC.CELL_OVER CELL,SUBSTR(CCC.CLASS,24) TYPE,CCC.CELL_UNDER,SUBSTR(CCC.CLASS_UNDER,24)
    ,CCC.FROM_STATION_UNDER,CCC.TO_STATION_UNDER,CCC.FROM_DATE_UNDER,CCC.TO_DATE_UNDER
    ,LN.NAME LANE,M.BASIC_MATERIAL,CCC.FROM_DATE
    ,SM.MODEL,S.ID S_ID,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.SENSOR_DEPTH_IN DEPTH,S.DATE_INSTALLED,S.DATE_REMOVED
    , CCC.START_STATION,CCC.END_STATION
    FROM
    (
    SELECT
    CC.CELL_ID ID_UNDER
    ,(SELECT CELL_NUMBER FROM MNR.CELL WHERE ID = CC.CELL_ID) CELL_UNDER
    ,(SELECT CLASS FROM MNR.CELL WHERE ID = CC.CELL_ID) CLASS_UNDER
    ,(SELECT START_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_STATION_UNDER
    ,(SELECT END_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_STATION_UNDER
    ,(SELECT CONSTRUCTION_ENDED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_DATE_UNDER
    ,(SELECT DEMOLISHED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_DATE_UNDER
    ,C.CONSTRUCTION_ENDED_DATE FROM_DATE,C.ID ID_OVER,C.CELL_NUMBER CELL_OVER,C.CLASS
    ,C.START_STATION,C.END_STATION
    FROM MNR.CELL C JOIN MNR.CELL_CELL CC ON CC.CELL_COVERS_ID=C.ID
    ) CCC
    JOIN MNR.LANE LN ON LN.CELL_ID=CCC.ID_UNDER
    JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
    JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
    JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
    JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
    WHERE DATE_INSTALLED > FROM_DATE-${constrPeriodLenDays}
     AND S.STATION_FT BETWEEN START_STATION AND END_STATION
  )
)
WHERE CELL=${cellNum} AND MODEL='${model}'
ORDER BY CELL, MODEL,SEQ
"""
//    ORDER BY STATION,OFFSET,DEPTH;
    def sensors = []
    Sql sql = new groovy.sql.Sql(dataSource)
    def ans = sql.rows(q.toString())
    ans.each { row ->
      Long id = row.ID
      Sensor s = Sensor.read(id)
      sensors << s
    }
    return sensors
  }
}
