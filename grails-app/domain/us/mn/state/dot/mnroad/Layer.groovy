package us.mn.state.dot.mnroad


class Layer implements Comparable {

  Lane lane
  static belongsTo = Lane

  Integer layerNum
  Date constructStartDate
  Date constructEndDate
  Material material
  Double thickness
  Boolean transverseSteel

  SortedSet joints
  Set sensors
  Set materialSamples
  static hasMany = [sensors:Sensor, joints:TrJoint, materialSamples:MaterialSample]//, panels:PccPanel]

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

// Transients (not stored in the DB)
  Double heightAboveSubgrade // (calculated) height of the layer bottom above subgrade
  Double adjustedThickness   // (calculated) Thickness of layer after milling
  Map depthRange             // (calculated)
  static transients = [
          "heightAboveSubgrade"
          ,"adjustedThickness"
          ,"depthRange"
  ]

  static constraints = {
/*
    constructEndDate (nullable:true, validator: { val, obj ->
        if (val == null)  // because null is allowed
          return true
        if (!(val.after(obj.constructStartDate) ||  val == obj.constructStartDate))
          return ['beforeStartDate.error', val, obj.constructStartDate, obj.lane.cell.demolishedDate]
        if (!(obj.lane.cell.demolishedDate && val.before(obj.lane.cell.demolishedDate))) {
          return ['beforeDemolition.error', val, obj.lane.cell.demolishedDate]
        }
        else
          return true
        }
    )
    constructStartDate (nullable:false, validator: { val, obj ->
        if (!(val.before(obj.constructEndDate) ||  val == obj.constructEndDate))
          return ['afterEndDate.error', val, obj.constructEndDate, obj.lane.cell.demolishedDate]
        if (!(obj.lane.cell.demolishedDate && val.before(obj.lane.cell.demolishedDate))) {
          return ['beforeDemolition.error', val, obj.lane.cell.demolishedDate]
        }
        else
          return true
        }
      )
*/
    constructEndDate (nullable:true)
    constructStartDate (nullable:false)
    lane             (nullable:true)
    layerNum         (size:2)
    transverseSteel  (nullable:true)
    createdBy        (nullable:true)
    lastUpdatedBy    (nullable:true)
  }

  static mapping = {
    columns { id column:'id' }
    id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
    sensors lazy:true
  }

  int compareTo(obj) {
    if (id == obj.id)
      return 0
    else if (constructEndDate < obj.constructEndDate) {
      return -1
    }
    else if (layerNum < obj.layerNum) {
      return -1
    }
    else {
      return 1
    }
  }

  String toStringBold() {
    def rc = this.toString()
    def ns = ""
    rc.split(',').each { it ->
      ns += ns==""?"":", "
      if (it.trim().startsWith('depth'))
        ns += "<b>"+it +"</b>"
      else
        ns += it
      }
    return ns
  }

  String toString() {
    Map layerDescriptions = lane?.layerDescriptions(this)
    return layerDescriptions.get(id)
  }

  Boolean isAsphalt() {
    material.basicMaterial.toLowerCase().contains("asphalt")
  }

  Boolean isConcrete() {
    material.basicMaterial.toLowerCase().contains("concrete")
  }

  Double mmToInches(Double d) {
      return unitConvertService.mmToInches(d)
  }

  def unitConvertService

  Layer toInches() {
      thickness = unitConvertService.mmToInches(thickness)
      heightAboveSubgrade = unitConvertService.mmToInches(heightAboveSubgrade)
      return this
      }

  Layer toMms() {
      thickness = unitConvertService.inchToMms(thickness)
      heightAboveSubgrade = unitConvertService.inchToMms(heightAboveSubgrade)
      return this
      }

  def materialName() {
      return material?.display()
    }

    Long under() {
      def i = -1
      def tidx = -1
      Long rc = id
      def layers = lane.layers
      layers.each {
        tidx++
        if (this.id == it.id) {
          i = tidx
        }
      }
      def targetIdx = (i<layers.size()-1)?i+1:i
      i = 0
      layers.each {
        if (targetIdx == i++) {
          rc = it.id
        }
      }
      return rc
    }

    Long over() {
      def i = -1
      def tidx = -1
      Long rc = id
      def layers = lane.layers
      layers.each {
        tidx++
        if (this.id == it.id) {
          i = tidx
        }
      }
      def targetIdx = (i > 0)?i-1:i
      i = 0
      layers.each {
        if (targetIdx == i++) {
          rc = it.id
        }
      }
      return rc
    }

    boolean removal() {
      if (thickness < 0)
        return true
    }

    double remainingThickness() {
      double rc = thickness
      def la = nextRemovalLayer()
      if (la) {
        def amountRemoved = unitConvertService.roundTwo(la.thickness)
        if (amountRemoved < 0) {
           for (Layer lyr in la.layersBelow().toArray().reverse()) {
             amountRemoved += unitConvertService.roundTwo(lyr.thickness)
             if (lyr.id == id) {
               rc = amountRemoved>0?amountRemoved:0.0
               break;
             }
           }
        }
      }
      return unitConvertService.roundTwo(rc)
    }

    double remainingThickness(double thicknessMilledOffLane) {
      double rc = thickness  // defaults to return the current thickness
      if (thicknessMilledOffLane < 0.0) {
        def amountRemoved = unitConvertService.roundTwo(thicknessMilledOffLane)
        if (amountRemoved < 0) {
           for (Layer lyr in lane.layers.toArray().reverse()) {
             amountRemoved += unitConvertService.roundTwo(lyr.thickness)
             if (lyr.id == id) {
               rc = amountRemoved>0?amountRemoved:0.0
               break;
             }
           }
        }
      }
      return unitConvertService.roundTwo(rc)
    }

    Layer nextRemovalLayer() {
      def rc = null
      for (Layer lyr in layersAbove()) {
        if (lyr.removal()) {
          rc = lyr
          break
        }
      }
      return rc
    }

    SortedSet layersAbove() {  // returns this layer and those with a greater Layer.compareTo(otherLayer)
      return lane.layers.tailSet(this)
    }

    SortedSet layersBelow() {
      return lane.layers.headSet(this)
    }

    boolean subgradeLayer() {
      return material.course?.equals("Subgrade")
    }

/**
* Returns a field value escaped for special characters
* @param input A String to be evaluated
* @return A properly formatted String
*/
String escape(String input) {
if (input.contains(',') || input.contains('\n') || input.contains('\'') || (!input.trim().equals(input))) {
'"' + input.replaceAll("'", "\'\'") + '"'
} else {
input
}
}
private StringBuffer content = new StringBuffer()  
/**
* Appends a row of values to the output
* @param values A list of values
* @return this CsvBuffer instance
*/
StringBuffer append(List values) {
List escapedValues = values.collect { o -> escape(o as String) }
content.append escapedValues.join(',')
content.append '\r\n'
this
}

}
