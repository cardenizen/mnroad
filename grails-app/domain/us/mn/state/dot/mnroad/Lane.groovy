package us.mn.state.dot.mnroad

import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.distress.Distress

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

public class Lane implements Comparable {

    String name
    Integer width
    Integer laneNum
    String offsetRef // + South or East side of centerline, - North or West side of centerline

// Movel to PccPanel
    Double panelLength
    Double panelWidth

    Date dateCreated
    Date lastUpdated
    String createdBy
    String lastUpdatedBy

    Cell cell
    static belongsTo = Cell
    SortedSet layers
    static hasMany = [layers:Layer, distresses:Distress]


    static mapping = {
      columns {
        id column:'id'
      }
      id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
      layers lazy:true      
// Some distress tables are large (e.g. DISTRESS_ALPS_DATA)
// but lazy loading is the default
      //distresses lazy:false
    }

    static constraints = {
        laneNum                 (size:2)
        panelLength             (nullable:true)
        panelWidth              (nullable:true)
        offsetRef               ()//(nullable:true)
        createdBy               (nullable:true)
        lastUpdatedBy           (nullable:true)
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")

    int compareTo(obj) {
        obj.laneNum.compareTo(laneNum) // orders decending LaneNum
        //laneNum.compareTo(obj.laneNum)   // orders ascending LaneNum
    }

// Transients (not stored in the DB)
    Double totalThickness
    Double totalThicknessIn
    String laneDescription
    Integer surfaceLayerNum
    static transients = [
              "totalThickness"
            , "totalThicknessIn"
            , "laneDescription"
            , "surfaceLayerNum"
            , "afterLoad"
    ]
    def unitConvertService
    def afterLoad = {
      surfaceLayerNum = 0
      totalThickness = 0.0
      layers.each {
        if (it.layerNum) {
          totalThickness += it.thickness
          if (it.layerNum > surfaceLayerNum) {
              surfaceLayerNum = it.layerNum
          }

          if (it.layerNum > 1) {
            it.heightAboveSubgrade = MrUtils.roundTwo(totalThickness - it.thickness)
          }
          else {
            it.heightAboveSubgrade = 0.0
          }
        }
      }
      if (unitConvertService != null  && totalThickness != null) {
        totalThicknessIn = unitConvertService.mmToInches(totalThickness)
        totalThickness = unitConvertService.roundTwo(totalThickness)
      }

      laneDescription = "$width' $name"
    }

    def isRoadwayLane() {
       return name.indexOf("Shldr") < 0
    }

    def nextLayerNum() {
      Integer rc = 0
      layers.each {
        if (it.layerNum > rc)
          rc = it.layerNum
      }
      return rc+1
    }

    String toString() {
      "Cell ${cell.cellNumber} $width' $name"
    }

    /*
     *
     */
    double remainingThickness(double thicknessRemoved) {
      double r = thicknessRemoved
      for (Layer l in layers.toArray().reverse()) {
        r += l.thickness
        if (l.id == id) {
          if (r > l.thickness)
            r = l.thickness
          break
        }
      }
      r = (r < 0)?0.0:r
      return r
    }

    List remainingLayers(double thicknessRemoved) {
      return remainingLayers(thicknessRemoved, this.layers as List)
    }

    /*
    Returns a list of those layers which remained in lane "ln" after
    "thickness" of material was removed.
    Each item is a Map with attributes: "thicknessRemoved","layerId","thicknessRemaining"
     */
    List remainingLayers(double tr, List layerList) {
      def rc = []
      double removed = tr
      if (cell.covers) {
        def cell = Cell.get(cell.covers.first().id)
        if (cell) {
          Lane lane = cell.getLane(name)
          if (removed < 0) {
            for (Layer l in lane.layers.toArray().reverse()) {
              l.adjustedThickness = l.thickness
              removed += l.thickness
              if (removed > 0) {
                if (l.heightAboveSubgrade == 0.0 && l.thickness == 0.0) // Subgrade has no thickness
                  l.adjustedThickness = 0.0
                else
                  l.adjustedThickness = removed<l.thickness?removed:l.thickness
                rc << l
              } else {
                l.adjustedThickness = 0.0
              }
            }
          } else if (removed == 0.0) {
            lane.layers.toArray().reverse().each {
              it.adjustedThickness = it.thickness
              rc << it
            }
          }
        }
      } else { // this lanes cell does not cover another
        TreeSet ts = new TreeSet()
        for (Layer l in layers.toArray().reverse()) {
          l.adjustedThickness = l.thickness
          removed += l.thickness
          if (removed > 0) {
            if (l.heightAboveSubgrade == 0.0 && l.thickness == 0.0) // Subgrade has no thickness
              l.adjustedThickness = 0.0
            else
              l.adjustedThickness = removed<l.thickness?removed:l.thickness
            ts << l
          } else {
            l.adjustedThickness = removed
          }
        }
        rc = new ArrayList(ts.descendingSet())
      }
      return rc
    }

    List layersBelow(Layer l) {
      def lyrs = []
      def started = false
      for (Layer theLayer in layers.toArray().reverse()) {
        if (started) {
          if (theLayer.removal()) {
            if (theLayer == layers.first()) { // if this is the first (bottom) layer in the cell, then
              if (cell.covers)  { // there may be a Cell below, if there is, then
                def cb = Cell.get(cell.covers.first().id)
                if (cb) {
                  Lane lb = cb.getLane(name)
                  def rl = lb.remainingLayers(theLayer.thickness)
                  rl.each {
                    if (it.remainingThickness(theLayer.thickness) > 0.0)
                      lyrs << it
                  }
                } else {
                  println "Can't find Cell ${cell.covers}"}
              }  else {
                println "There should be a cell below ${cell} but there is not."}
            } else { // if this is not the first (bottom) layer, then
              def lb = this.layersBelow(theLayer)
              def ll = remainingLayers(theLayer.thickness, lb)
              ll.each {
                lyrs << it
              }

              def larr = []
              lyrs.each {
                Layer dlyr = Layer.get(it.id)
                if (dlyr.thickness >= 0.0)
                  larr << dlyr
              }
              double top = 0.0
              TreeSet ts = new TreeSet()
              larr.each {
                def r = [:]
                r.put('top',top)
                def thickness = MrUtils.roundTwo((it.adjustedThickness && it.adjustedThickness<it.thickness  && it.adjustedThickness>0.0)?it.adjustedThickness:it.thickness)
                r.put('bottom',MrUtils.roundTwo(top+thickness))
                it.depthRange = r
                ts << it
                top += thickness
              }
              lyrs = new ArrayList(ts.descendingSet())

            }
          } else // (!theLayer.removal())
          {
            if (theLayer.remainingThickness() > 0.0 || theLayer.subgradeLayer())
              lyrs << theLayer
          }
        }
        started = started || theLayer.id == l.id
      }
      // Next line added  for cells 105,205,305,405 where no thickness is removed, just layer(s) added
      if (!hasSubgrade(lyrs))
        lyrs += remainingLayers(0.0,lyrs)
      return lyrs
    }

    List layers() {
      def larr = []
      TreeSet ts = new TreeSet()
      for (Layer lyr in layers.toArray().reverse()) {
        if (lyr.removal())  {
          if (lyr.thickness > 0.0)
            ts << lyr
        } else {
          if (lyr.thickness > 0.0)
            larr << lyr
          def lyrsBelow = layersBelow(lyr)
          if (lyrsBelow.size() >0) {
            lyrsBelow.each {
              Layer dlyr = Layer.get(it.id)
              if (dlyr.thickness >= 0.0 && !larr.contains(dlyr))
                larr << dlyr
            }
            break;
          }
        }
      }
      def rc = []
      double top = 0.0
      larr.each {
        def r = [:]
        r.put('top',top)
        def thickness = MrUtils.roundTwo((it.adjustedThickness && it.adjustedThickness<it.thickness  && it.adjustedThickness>0.0)?it.adjustedThickness:it.thickness)
        r.put('bottom', MrUtils.roundTwo(top+thickness))
        it.depthRange = r
        def arraySize = ts.size()
        ts << it
        if (ts.size()==arraySize+1)
          top += MrUtils.roundTwo(thickness)
      }
      rc  = new ArrayList(ts.descendingSet())
      return rc
    }

    List layersToText() {
      def rc = []
      def lyrs = layers()
      if (lyrs.size() > 0) {
        lyrs.each {
          rc  << "\n${it}"
        }
      }
      return rc
    }

    Map offsetRange() {
      def rc = [:]
      if (isRoadwayLane()) {
        if (offsetRef == "+"){
          rc.put("from", 0)
          rc.put("to", width)
        }
        else {
          rc.put("from",0.0)
          rc.put("to", -width)
        }
      }
      else {
        Lane rl = cell.getRoadwayLane(offsetRef)
        if (offsetRef == "+"){
          rc.put("from", rl.width)
          rc.put("to", rl.width+width)
        }
        else {
          rc.put("from", -rl.width)
          rc.put("to", -rl.width-width)
        }
      }
      return rc
    }

    Double fromOffset() {
      Double rc = 0.0
      Map orng = offsetRange()
      r = orng.from.toDouble()
      return rc
    }

    Double toOffset() {
      Double rc = 0.0
      Map orng = offsetRange()
      r = orng.to.toDouble()
      return rc
    }

    Map layerDescriptions(Layer target) {
      def descriptionMap = [:]
      Double totalThickness = 0.0

      def lyrs = []
      layers.each {
        Layer l = Layer.get(it.id)
        totalThickness += l.thickness
        lyrs << it
      }
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")
      lyrs.reverse().each {
        Layer lin = Layer.get(it.id)
        def fromDepth = 0.0
        def toDepth = 0.0
        def fdmm = lin.depthRange?.get("top")
        def tdmm = lin.depthRange?.get("bottom")
        if (it.id == target.id) {
          fdmm = target.depthRange?.get("top")
          tdmm = target.depthRange?.get("bottom")
        }
        fromDepth = fdmm?lin.mmToInches(fdmm):lin.mmToInches(totalThickness - lin.heightAboveSubgrade - lin.thickness)
        toDepth = tdmm?lin.mmToInches(tdmm):lin.mmToInches(totalThickness - lin.heightAboveSubgrade)
        def tdDescr = (fromDepth == toDepth)?"":" to ${toDepth}"
        def constructEndDate = sdf.format(lin.constructEndDate)
        def descr = "Cell ${cell.cellNumber}, ${name}, Layer ${lin.layerNum}(${lin.id}), depth: ${fromDepth}$tdDescr inches, ${lin.material}, ${constructEndDate}"
        descriptionMap.put (it.id, descr)
      }
      return descriptionMap
    }

    boolean hasLayers() {
      return !(layers.size() == 0)
    }

    Date earliestLayerDate() {
      Date rc = new Date()
      layers.each { layer ->
        if (layer.constructStartDate < rc)
          rc = layer.constructStartDate
      }
      return rc
    }

    boolean hasSubgrade() {
      return hasSubgrade(layers as List)
    }

    boolean hasSubgrade(def layerList) {
      def rc = false
      layerList.each {
        if (it.subgradeLayer()) {
          rc = true
        }
      }
      return rc
    }

    boolean hasMilling() {
      def rc = false
      layers.each {
        if (it.thickness < 0) {
          rc = true
        }
      }
      return rc
    }
}
