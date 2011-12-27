import java.util.*
import groovy.sql.Sql
import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.*
import java.sql.SQLException

class LaneService {

  boolean transactional = true
  static def datePartNames = ["month","day","year"]
  static def laneAttrNames = ["material","thickness","constructStartDate","constructEndDate"]
  static def mmddyyyyDateFormat = "mmddyyyy"

  def dataSource
  def unitConvertService

    def isDistressLaneOutOfDate(tableName) {
      def cntOutOfDate = 0
//    table names cannot be passed as parameters to a prepared statement
      def q = "SELECT (SELECT COUNT(*) FROM MNR.${tableName} DV)-(SELECT COUNT(*) FROM MNR.${tableName} DV JOIN DISTRESS D ON DV.ID=D.ID)  OUT_OF_DATE_COUNT FROM DUAL"
      log.info "isDistressLaneOutOfDate query: ${q}"
      def application = AH.application
      groovy.util.ConfigObject ds = application.config.dataSource
//      String cd = "<hibernate-configuration><session-factory><property name=\"connection.driver_class\">oracle.jdbc.OracleDriver</property><property name=\"connection.url\">jdbc:oracle:thin:@localhost:1521:XE</property><property name=\"connection.username\">mnr</property><property name=\"connection.password\">mnr</property><property name=\"dialect\">org.hibernate.dialect.Oracle10gDialect</property></session-factory></hibernate-configuration>"

      if (dataSource) {
        Sql sql = new groovy.sql.Sql(dataSource)
        try {
          sql.eachRow(q.toString()) { row ->
             cntOutOfDate = row.OUT_OF_DATE_COUNT
          }
        } catch (java.sql.SQLSyntaxErrorException sqlse) {
          log.error sqlse.message
        } catch (java.sql.SQLException sqle) {
          log.error sqle.message
        }
      }
      println "${cntOutOfDate} returned from ${q}"
      return cntOutOfDate
    }

  def dbMetadataService

    def updateDistressLanes(def lane_id, def tableName, def userName, LinkedHashMap am, def defaultDateFormat) {
      def rm = [:]
      Sql sql = new groovy.sql.Sql(dataSource)
      def lane = Lane.get(lane_id)
      def id = sql.firstRow("select mnr.distress_seq.nextval from dual")[0]
      def dt = new java.sql.Date((new Date()).getTime())
      def args = [id,1,userName,userName,dt,dt,lane_id]
      def upq = "INSERT INTO MNR.DISTRESS (ID,VERSION,CREATED_BY,LAST_UPDATED_BY,LAST_UPDATED,DATE_CREATED,LANE_ID) VALUES (?,?,?,?,?,?,?)"
      boolean b
      try {
        println upq
        println args
        b = sql.execute(upq.toString(),args)
        if (!b && sql.updateCount == 1) {
          rm.put('result1', "${sql.updateCount} row inserted into DISTRESS for lane (${lane}).".toString())
          def wc = dbMetadataService.whereClause(tableName, am, defaultDateFormat)
          upq = "UPDATE MNR.${tableName} SET ID=? ${wc}"
          b = sql.execute(upq.toString(),[id])
          if (!b && sql.updateCount == 1) {
            rm.put('result2', "${sql.updateCount} ID in table ${tableName} updated with ID ${id}.".toString())
          }
        }
      } catch(SQLException sqle) {
        log.error "Unable to create a Distress table entry for ${tableName} and ${lane}."
      }

      return rm
    }

  // Find the lane which has been built over layer.lane
  Lane subsequentLane(Layer layer) {
    Lane newLane = null
    if (layer) {
      Cell c = layer.lane?.cell
      if (c && c.coveredBy) {
        def cellsAbove = c.coveredBy
        def acell
        int s = cellsAbove.size()
        if (s && s == 1) {
          acell = cellsAbove.first()
          Cell ac = Cell.get(acell.id)
          if (ac)
            ac.lanes.each {
            if (it.name == layer.lane.name) {
              newLane = it
            }
          }
        }
      }
    }
    return newLane
  }

  double amountRemaining(Layer layer) {
    double rem = 0.0
    Lane newLane = subsequentLane(layer)
    if (newLane && newLane.layers.size() > 0 && newLane.layers.first().thickness < 0) {
      // If material has been removed by subsequent work assumes the first layer
      // is material removal with a negative value for newLane.layers.first().thickness
      rem = remainingThickness(layer.lane.layers.toArray().reverse(), newLane.layers.first().thickness,layer.id)
    }
    else {
      rem = layer.thickness
    }
    return (rem < 0 )?0.0:unitConvertService.mmToInches(rem)
  }

  double remainingThickness(Object[] layers, double thicknessRemoved, Long targetLayerId) {
    double r = thicknessRemoved
    for (Layer l: layers) {
      r += l.thickness
      if (l.id == targetLayerId) {
        if (r > l.thickness)
          r = l.thickness
        break
      }
    }
    return (r < 0)?0.0:r
  }

  def layersAsOf (Lane ln, Date dt) {
    def rc = []

    ln.layers.each {
      if ((it.constructStartDate < dt) && (it.constructEndDate?:(new Date()) < dt)) {
        rc.add(it)
      }
    }

    return rc
  }

  def mostRecentLayerAsOf(Lane ln, Date dt) {
    def rc = null

    def layers = layersAsOf(ln, dt)
    if (layers.size() > 0) {
      rc = layers.reverse().toArray()[0]
    }

    return rc
  }

  String assignSensor(Sensor s, Long layerId) {
    String rc = ""
    Layer l = Layer.get(layerId)
    if (s.layer) {
      s.layer.sensors.remove(s)
      println "Moving sensor_id ${s.id} from layer_id ${s.layer.id} to layer_id ${l.id}"
    }
    else {
      println "Assigning sensor_id ${s.id} to layer_id ${l.id} "
    }
    s.layer = l
    l.sensors.add(s)

    def sv = s.validate()
    if (!sv) {
      s.errors.each {
        rc += "${it}\n"
      }
    }
    def lv = l.validate()
    if (!lv) {
      l.errors.each {
        rc += "${it}\n"
      }
    }
    if (sv && lv) {
      s.layer.save(flush:true)
      l.save(flush:true)
      rc = "Sensor ${s.id} assigned. Layer ${l.id} has ${l.sensors.size()} sensors."
    }
    return rc
  }

    String removeSensor(Sensor s, Long layerId) {
      String rc = ""
      Layer.withTransaction { status ->
        Layer l = Layer.get(layerId)
        boolean result = false
        if (s && l) {
          l.sensors.remove(s)
          result = l.save(flush:true)
          if (l.hasErrors()) {
            def fld = l.errors.getFieldError("layer")
            rc = "Field ${fld.field} cannot be ${fld.rejectedValue}"
          }
          s.layer = null
          result = s.save(flush:true)
          if (s.hasErrors()) {
            def fld = s.errors.getFieldError("layer")
            rc = "Field ${fld.field} cannot be ${fld.rejectedValue}"
          }
          else
            rc = "Sensor ${s.id} detached. Layer ${l.id} has ${l.sensors.size()} sensors."
        }
      }
      return rc
    }

  def /*Lane*/ saveLayersWithLane(Map params, Lane l, userName) {
      Map layerAttrParams = getAttributeParamMap(params)

      Lane oldLane = Lane.get(l.id)
      oldLane.layers.each {
          String val = getValue(it.id.toString(), "material", layerAttrParams)
          it.material = val
          val = getValue(it.id.toString(), "thickness", layerAttrParams)
          if (val) {
              it.thickness = Double.parseDouble(val)
              if (params["engToMetric"]=="on") {
                  it.toMms()
              }
          }
          val = getValue(it.id.toString(), "constructStartDate", layerAttrParams)
          if (val) {
              Date fdate = new SimpleDateFormat("MMddyyyy"/*mmddyyDateFormat*/).parse(val)
              it.constructStartDate = fdate
              }
          val = getValue(it.id.toString(), "constructEndDate", layerAttrParams)
          if (val) {
              Date fdate = new SimpleDateFormat("MMddyyyy"/*mmddyyDateFormat*/).parse(val)
              it.constructEndDate = fdate
              }
          it.lastUpdatedBy = userName
          it.save(flush:true)
      }
      oldLane.lastUpdatedBy = userName
      oldLane.save(flush:true)
      return oldLane
  }

  public Map getAttributeParamMap(Map params) {
      def Map layerAttrParams = new HashMap()
      params.each {j ->
          if (j.key.startsWith("thickness")
                  || j.key.startsWith("material")
                  || j.key.startsWith("constructStartDate")
                  || j.key.startsWith("constructEndDate")
          ) {
              layerAttrParams.put(j.key, j.value)
          }
      }
      return layerAttrParams
  }

  private Object getValue(String id, String attrName, Map paramMap) {
  String rc
  paramMap.each {j ->
      if (j.key.startsWith(attrName)) {
          // g:datePicker dates are separated into 3 controls (CONTROLNAME_day, CONTROLNAME_month, and CONTROLNAME_year)
          // and so must have special handling
          // buildMmDdYyyyDate always returns a "mmddyyy" String or null
          if (j.key.contains("Date_")) {
              String[] nvp = j.key.split("_")
              if ((nvp.size() == 3) && (nvp[1] == id)) {
                  rc = j.value
                  rc = buildMmDdYyyyDate(j.key, paramMap)
                  return rc
              }
          }
          else {
              String[] nvp = j.key.split("_")
              if ((nvp.size() == 2) && (nvp[1] == id)) {
                  rc = j.value
                  return rc
              }
          }
      }
    }
    return rc
}

  private String buildMmDdYyyyDate(String attr,  Map aParamMap)  {
      HashMap dt = new HashMap()
      // dt gets filled with datePartNames keys and values from the aParamMap
      // The caller checks to see if attr contains the phrase "Date"
      String[] fldname = attr.split("_")
      // 'field' is the name given to the g:datePicker control
      // strip off "_day", "_month" or "_year" from 'attr'
      String field = fldname[0] + "_" + fldname[1]
      def dates = []
      aParamMap.each {parm ->
          String sparm = (String)parm
          if (sparm.startsWith(field)){
              String[] nvp = sparm.split("_")
              if (nvp.length > 2) {
                  String dateKey = nvp[0]+"_"+nvp[1]
                  String[] partNvp = nvp[2].split("=")   // day=nn or month=nn or year=nnnn
                  if (partNvp && partNvp.length==2 && (dateKey == field)) {
                      // If the value of the date part is a single digit, prepend a zero
                      String dmy = partNvp[1].size()==1?"0"+partNvp[1]:partNvp[1]
                      // By convention, the key of the control is its name appended with
                      // an underscore and the id of the object from which it came
                      def adate = dt.getAt(dateKey)
                      if (adate) {
                          adate.put(partNvp[0],dmy)
                      }
                      else   {
                          HashMap ht = new HashMap()
                          ht.put(partNvp[0],dmy)
                          dt.put(dateKey, ht)
                      }
                  }
              }
          }
      }
      def aDateMap = dt.getAt(field)
      def m = aDateMap?.getAt("month")
      def d = aDateMap?.getAt("day")
      def y = aDateMap?.getAt("year")
      return (m+d+y).toString()
  }

/*
LVR North Side  (Cells 33-40)
Left = - = Outside = Passing
Right = + = Inside = Driving

LVR South Side  (Cells 24-32, 52-54, 77-79, 85-89)
Left = - = Inside = Driving
Right = + = Outside = Passing

Mainline 
Left = - = Driving
Right = + = Passing
*/
  def sensorsInLane = { lane ->
    def fd = lane.offsetRange().from.toDouble()
    def td = lane.offsetRange().to.toDouble()
    def fbd = new BigDecimal(fd)
    def tbd = new BigDecimal(td)
    def q = """SELECT S.ID FROM MNR.SENSOR S
 JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID=SM.ID
 JOIN MNR.LAYER L ON S.LAYER_ID=L.ID
 JOIN MNR.LANE LN ON L.LANE_ID=LN.ID
 JOIN MNR.CELL C ON LN.CELL_ID=C.ID
WHERE
C.CELL_NUMBER=? AND
(
  (LN.OFFSET_REF='+' AND
    (LN.NAME LIKE '%Shldr%' AND S.OFFSET_FT > ?)
    OR
    (LN.NAME NOT LIKE '%Shldr%' AND S.OFFSET_FT >= ? AND S.OFFSET_FT <= ?)
  )
  OR
  (LN.OFFSET_REF='-' AND
    (LN.NAME LIKE '%Shldr%' AND S.OFFSET_FT < ?)
    OR
    (LN.NAME NOT LIKE '%Shldr%' and s.offset_ft <= ? and s.offset_ft >= ?)
  )
)
ORDER BY
SM.MODEL,S.SEQ
"""
    Sql sql = new groovy.sql.Sql(dataSource)
    List rl = sql.rows(q,[lane.cell.cellNumber,fbd,fbd,tbd,fbd,fbd,tbd])
    def sensors = []
    rl.each {
      sensors << Sensor.get(it.id)
    }
    return sensors
  }

  def hqlSensorsInLane_string = { lane ->
    def sensors = sensorsInLane(lane)

    def cms_s = []
    sensors.each {
      cms_s << "${it.layer.lane.cell.cellNumber},${it.sensorModel.model},${it.seq}"
//      def sensor = Sensor.get(it.id)
//      cms_s << "${sensor.layer.lane.cell.cellNumber},${sensor.sensorModel.model},${sensor.seq}"
    }

    return cms_s.toString()
  }

  def sqlSensorsInLane_string = { lane ->
    def fd = lane.offsetRange().from.toDouble()
    def td = lane.offsetRange().to.toDouble()
    def fbd = new BigDecimal(fd)
    def tbd = new BigDecimal(td)
    def q = """select s.id from mnr.sensor s join
mnr.sensor_model sm on s.sensor_model_id=sm.id join
mnr.layer l on s.layer_id=l.id join
mnr.lane ln on l.lane_id=ln.id join
mnr.cell c on ln.cell_id=c.id
where
c.cell_number=? and
(
  (ln.offset_ref='+' and
    (ln.name like '%Shldr%' and s.offset_ft > ?)
    or
    (ln.name not like '%Shldr%' and s.offset_ft >= ? and s.offset_ft <= ?)
  )
  or
  (ln.offset_ref='-' and
    (ln.name like '%Shldr%' and s.offset_ft < ?)
    or
    (ln.name not like '%Shldr%' and s.offset_ft <= ? and s.offset_ft >= ?)
  )
)
order by
sm.model,s.seq
"""
    Sql sql = new groovy.sql.Sql(dataSource)
    List rl = sql.rows(q,[lane.cell.cellNumber,fbd,fbd,tbd,fbd,fbd,tbd])
    def cms_r = []
    rl.each {
      def sensor = Sensor.get(it.id)
      cms_r << "${sensor.layer.lane.cell.cellNumber},${sensor.sensorModel.model},${sensor.seq}"
    }
    return cms_r.toString()
  }

  List cells(Integer cellNumber) {
    def c = Cell.createCriteria()
    def rc = c.list(){
      eq ("cellNumber",cellNumber)
    }
    return rc
  }

  List getLanes(Integer cellNumber, String laneDescription) {
    def rc = Lane.createCriteria().list(){
      eq ("name", laneDescription)
      cell {
        eq ("cellNumber", cellNumber)
      }
    }
    return rc
  }
}
