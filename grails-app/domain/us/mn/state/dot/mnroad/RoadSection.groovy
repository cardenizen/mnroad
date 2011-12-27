package us.mn.state.dot.mnroad

class RoadSection implements Comparable  {

  Facility facility
  static belongsTo = Facility

  Double startStation
  Double endStation
  String description
  SortedSet cells
  static hasMany = [cells:Cell]
  String orientation
  // This is adopted from an FHWA road inventory route direction convention
  // orientation pick list is in table APP_CONFIG
  // 'East' if deltaX > deltaY
  // 'North' if deltaY > deltaX
  // 'None' if facility is non-linear

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static constraints = {
    description             (size:0..400)
    endStation              (max:(Double)125000.0, scale:2)
    startStation            (max:(Double)125000.0, scale:2)
    orientation             (nullable:true, size:4..25)
    dateCreated             (nullable:true)
    lastUpdated             (nullable:true)
    createdBy               (nullable:true)
    lastUpdatedBy           (nullable:true)
  }

  static mapping = {
    columns { id column:'id' }
    id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
    cells lazy:false
  }

  String toString() {
      "${description}"
  }

  int compareTo(obj) {
      obj.id.compareTo(id)
  }

  List cellsBetweenList(Double from, Double to) {
    return Cell.findAll(
      "from Cell as c where (c.startStation >= :ss and c.endStation <= :ts) and c.roadSection.id=:rsid"
        ,[rsid:id, ss:from, ts:to])
  }

    List activeCellList() {
      return Cell.findAll(
        "from Cell as c where c.demolishedDate is null and c.roadSection.id=:rsid"
          ,[rsid:id])
    }

    // Adding the propertyMissing method to an object allows code to
    // request a property value wihout getting a MissingPropertyException
    // If the property is not found the name of the requested
    // property is returned instead.
    def propertyMissing(String name)
    {
        name
    }
/*
    def demolishedCells = {
      def demolishedCells = []
      cells.each {
        def ccells = []
        if (it.demolishedDate != null) {
            demolishedCells += it
        }
      }
      return demolishedCells
    }

      def mostRecentDemolishedCells = {
        getUnique(demolishedCells())
      }

  Cell detectSiteDiscontinuity() {
      double lastEnd
      String rc = null
      Cell cs = null
      cells.each {
          if (lastEnd)  {
              if (it.startStation != lastEnd)  {
                  cs = it
              }
          }
          lastEnd = it.endStation;
      }
      return cs
  }
*/
    
  Set mostRecentCells() {
    Set ss = getUnique(cells).sort()
    return ss
  }

  //
  // Given a list of cells, returns a set of cells having only
  // the most recent for each group that have the same station termini
  //
  Set getUnique(SortedSet c) {
    def rc = [] as Set
    Cell last = null
    c.each {
      if (last != null) {
        if (it.startStation == last.startStation
          && it.endStation == last.endStation) {
          boolean b = it.constructionEndedDate > last.constructionEndedDate
          if (b) {
            rc.add(it)
          }
          else {
            addIfLater(rc, last)
          }
        }
        else {
          addIfLater(rc, last)
        }
        last = it
      }
      else {
        last = it
      }
    }
    addIfLater(rc, last)
    return rc.sort()
  }

  private void addIfLater(rc, Cell last) {
    def dup = rc.findAll {item -> item.startStation == last.startStation && item.endStation == last.endStation}
    if ((dup.size() == 0) && (last.coveredBy.size() == 0) &&(last.demolishedDate == null)) {
      rc.add(last)
    }
    else {
      dup.each {d ->
        if (last.constructionEndedDate > d.constructionEndedDate) {
          rc.add(last)
        }
      }
    }
  }

}
