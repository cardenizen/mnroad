package us.mn.state.dot.mnroad

import java.text.DateFormat
import java.text.SimpleDateFormat

class Cell implements Comparable {

    String name
    String drainageSystem
    Integer cellNumber
    Double startStation
    Double endStation
    Double startSensorStation
    Double endSensorStation
    Date constructionEndedDate
    Date demolishedDate
    Integer designLife
    String shoulderType
    SortedSet coveredBy
    SortedSet covers
    RoadSection roadSection
    static belongsTo = RoadSection

//    SortedSet joints
    SortedSet lanes
    static hasMany = [covers:Cell, coveredBy:Cell, lanes:Lane]//, joints:TrJoint]
    Date dateCreated
    Date lastUpdated
    String createdBy
    String lastUpdatedBy

    static mapping = {
      columns {
        startSensorStation  column:'CELL_START_STATION'
        endSensorStation    column:'CELL_END_STATION'
        startStation        column:'START_STATION'
        endStation          column:'END_STATION'
        lanes               lazy:false
      }
      id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
    }

    static constraints = {
      cellNumber              ()
      name                    (blank:false) // For an Oracle DB, non-nullable character fields must be declared non-blank
      startStation            ()
      endStation              ()
      startSensorStation      (nullable:true)
      endSensorStation        (nullable:true)
      constructionEndedDate   ()
      drainageSystem          (blank:false)
      demolishedDate          (nullable:true, validator: { val, obj ->
  //    see hmaCell.demolishedDate.validator.invalid in i18n/messages.properties
  //          println "In constraints.demolishedDate.validator: ${val}"
            if (val == null)  // because null is allowed
              return true
            return (val.after(obj.constructionEndedDate))?true:false
          }
        )
      designLife              (nullable:true)
      shoulderType            (nullable:true)
      createdBy               (nullable:true)
      lastUpdatedBy           (nullable:true)
    }

    String shortName() {
      "${cellType()} ${cellNumber}-${id}"
    }

    Lane getLane(def laneName) {
      Lane rl = null
      for (Lane ln : lanes) {
        if (ln.name == laneName) {
          rl = ln
        }
      }
      return rl
    }

    Lane getRoadwayLane(def offsetRef) {
      Lane rl = null
      for (Lane ln : lanes) {
        if (ln.isRoadwayLane() && ln.offsetRef == offsetRef) {
          rl = ln
        }
      }
      return rl
    }

    boolean hasLayers() {
      boolean rc = false
      if (lanes.size() == 0)
        return rc
      lanes.each {
        if (it.hasLayers()) {
          rc = true
        }
      }
      return rc
    }

    Date earliestLayerDate() {
      Date rc = new Date()
      if (lanes.size() == 0)
        return rc
      lanes.each { lane ->
        lane.layers.each { layer ->
          if (layer.constructStartDate < rc)
            rc = layer.constructStartDate
          }
        }
      return rc
      }

    Cell findNext() {
      Cell.findByStartStation endStation
    }

    Cell findPrev() {
      Cell.findByEndStation startStation
    }

    double length() {
      return maxStation - minStation
    }

    void validateDrainageSystem() {
      if(!drainageSystem) {
        this.errors.rejectValue("drainageSystem", "default.null.message")
        }
    }

/*
  *
  * This closure returns a map of the cells which subsume (are built over or include) this one.
  * The map key is id and the value is cellNumber and is constructed out of the
  * coveredBy field.
 */
    Map subsumedCellIds() {
      def cellNumbers = [:]
      if (coveredBy.size() == 0)
        return cellNumbers
      coveredBy.each { cbCell ->
        cellNumbers.put(cbCell.id,cbCell.cellNumber)
        }
      return cellNumbers
    }

    String fromDate() {
        if (!constructionEndedDate)
            return "Null Date Error"
        def DateFormat df = new SimpleDateFormat( "MMM d, yyyy" );
        return df.format(constructionEndedDate)
    }

//    def cellDateService
    String toDate() {
      if (!demolishedDate)
          return "now"
      def DateFormat df = new SimpleDateFormat( "MMM d, yyyy" );
      return df.format(demolishedDate)
//      def rc = ""
//      def DateFormat df = new SimpleDateFormat( "MMM d, yyyy" )
//      if (!demolishedDate) {
//        def ed = cellDateService.getEndDate(id)
//        rc = ed?df.format(ed):"now"
//      } else {
//        rc = df.format(demolishedDate)
//      }
//      return rc
    }

    int compareTo(obj) {
      // need to have both of these for sets because compareTo is used
      // for both ordering and equality - set members must be unique
      if (startStation == obj.startStation) {
        return obj.constructionEndedDate.compareTo(constructionEndedDate)
      }
      return startStation.compareTo(obj.startStation)
    }

    String controller(){
      String n = this.class.name.substring(this.class.name.lastIndexOf('.')+1)
      String ctrlr = n.substring(0,1).toLowerCase()+n.substring(1)
      return ctrlr
    }

    Boolean hasConcrete() {
      return (cellType() == 'PCC' || cellType() == 'Composite')
    }

    Boolean hasAsphalt() {
      return (cellType() == 'HMA' || cellType() == 'Composite')
    }

    String cellType() {
      def rc = "Unknown Cell Type"
      def cell
      switch (controller()) {
        case "hmaCell":
          rc = "HMA"
          break
        case "pccCell":
          rc = "PCC"
          break
        case "aggCell":
          rc = "Aggregate"
          break
        case "compositeCell":
          rc = "Composite"
          break
        }
      return rc
    }

//
// find other cells which are active (demolishedDate is null)
// and where the startStation lies between the given station termini
//
    static List activeCellsBetween(Long rsid, Double from, Double to) {
      def cb = []
      def oneOneHundrethShy = to - 0.1
      RoadSection rs = RoadSection.get(rsid)
      cb = Cell.findAllByRoadSection(rs, [sort:'startStation',order:'asc'])
      return cb
    }

//    static List otherCellsInRoadSection(Long rsid) {
//      def cb = []
//      RoadSection rs = RoadSection.get(rsid)
//      def fr = Cell.findAllByRoadSection(rs, [sort:'startStation',order:'asc'])
//      fr.each {
//        if (it.id != id)
//          cb << it
//      }
//
//      return cb
//    }

    List sensorsInOffsetRange(String offsetRange) {
      def rc = []
      String sql = "from Sensor as s where s.cell = :cn " + offsetRange + " order by model, seq"
      rc = Sensor.findAll (sql, [cn:cellNumber])
      return rc
    }

    Double minStation() {
      return Math.min(startStation,endStation)
    }

    Double maxStation() {
      return Math.max(startStation,endStation)
    }

    

//  boolean terminiEqual(Cell c1, Cell c2) {
//    return c1.startStation == c2.startStation && c1.endStation == c2.endStation
//  }
//
//  boolean terminiOverlap(Cell c, Double start, Double end) {
//    if ((
//    (start <= c.startStation && end > c.startStation)
//      || (start < c.endStation && start >= c.endStation)
//      || (start < c.endStation &&  end > c.endStation)
//    ))
//      {return true;}
//    else
//      {return false;}
//  }
//
//  boolean terminiOverlap(Cell c1, Cell c2) {
//    return terminiOverlap(c1, c2.startStation, c2.endStation)
//  }
//
//  def findTerminiOverlaps = {
//    def ocs = []
////    (start <= c.startStation && end > c.startStation)
////      || (start < c.endStation && start >= c.endStation)
////      || (start < c.endStation &&  end > c.endStation)
//// Given cell 26 id - 578,start_station - 16367.5, and end_station - 16992.5, this returns 3 rows ():
////    CELL_NUMBER	START_STATION	END_STATION
////    85	16,367.5	16,593.5
////    86	16,593.5	16,819.5
////    87	16,819.5	17,045.5
////select * from Cell c where c.id!=578
//// and (
////   (c.start_Station>=16367.5 and c.start_Station<16992.5)
////   or (c.end_Station>16367.5 and c.end_Station<=16367.5)
////   or (c.end_Station>16367.5 and c.end_Station<=16992.5)
//// )
//    // Select all cells within the longitudinal range of this one
//    ocs = Cell.findAll("from Cell as c where c.id!=:cellId and ( (c.startStation>=:startStation and c.startStation<:endStation) or (c.endStation>:startStation and c.endStation<=:startStation) or (c.endStation>:startStation and c.endStation<=:endStation)  )"
//            ,[cellId:id, startStation:startStation, endStation:endStation])
//    return ocs
//  }
//  // "Existing" == demolished date is null
//  def findExistingThatOverlap = {
//    def ols = []
//    findTerminiOverlaps().each {
//      if (it.demolishedDate == null)
//        ols.add(it)
//    }
//    return ols
//  }
//
//  // "History" == demolished date is NOT null
//  def findHistoryThatOverlap = {
//    def ols = []
//    findTerminiOverlaps.each {
//      if (it.demolishedDate != null)
//        ols.add(it)
//    }
//    return ols
//  }

//
//  find OTHER cells where the termini are the same as for this one
//
//  def findOthersAtThisTermini = {
//    def ocs = []
//    ocs = Cell.findAll(
//      "from Cell as c where c.id!=:cellId and c.startStation=:startStation and c.endStation=:endStation"
//      ,[cellId:id, startStation:startStation, endStation:endStation])
//    return ocs
//  }
/*
//  find other cells where the termini overlap with the termini of this one.
//
  def otherCellsIntersectingThisTermini = {
    def ocs = []
    Cell c = this  // because referencing 'this' in the closure does not work
    roadSection.cells.each {
      boolean overlaps = terminiWhollyWithin(this, it)
      boolean sameObject = it.id.equals(c.id)
      if (overlaps && !sameObject) {
        ocs.add(it)
      }
    }
    return ocs
  }
  */
//
//  find all cells where the termini are the same as for this one but the date(s) are different
//
//  def allCellsAtThisTermini = {
//    def ocs = []
//    roadSection.cells.each {
//      boolean samePlace = terminiEqual(this, it)
//      if (samePlace) {
//        ocs.add(it)
//      }
//    }
//
//    def ocsn = []
//    ocsn = findOthersAtThisTermini()
//    ocsn += this
//    return ocs
//  }

//
//  find all cells where the termini overlap with the termini of this one.
//  
//  def allCellsIntersectingThisTermini = {
//    def ocs = []
//    roadSection.cells.each {
//      boolean overlaps = terminiWhollyWithin(this, it)
//      if (overlaps) {
//        ocs.add(it)
//      }
//    }
//    return ocs
//  }

//  boolean datesEqual(Cell c1, Cell c2) {
//    if (
//      (c1.constructionEndedDate == null && c2.constructionEndedDate != null)  ||
//      (c1.constructionEndedDate != null && c2.constructionEndedDate == null)
//    )
//    return false
//
//    return c1.constructionEndedDate == c2.constructionEndedDate && c1.demolishedDate == c2.demolishedDate
//  }
/*
Tom Burham 3/10/2009
Cells can experience the following:

Construction
Shortened in length
Demolished

Overlay application - PCC, HMA or Pervious PCC
Inlayed - Full width or one lane only
Cracked Sealed
Sealed coated
Microsurfaced - Full width or one lane only
Slurry sealed - Full width or one lane only
Retrofit doweled - All joints or only specific joints
Surface planed or diamond ground
Reclaimed - Full or partial depth
 */

}
