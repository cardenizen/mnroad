package us.mn.state.dot.mnroad
// Inventory of all material samples and storage locations
class MaterialSample {
  String  mnroadId
  Integer cell
  Double  station
  Double  offset
  String  materialGroup
  String  containerType
  String  sampleTime
  Date    sampleDate
  Double  sampleDepthTop
  String  depthCode
  Double  sampleDepthBottom
  String  storageLocation
  Double  sampleCureTime
  String  comments
  Integer liftNumber
  String  course
  String  spec
  String  contactPerson
  String  fieldId
  Date    lastUpdated
  Date    dateCreated
  String  createdBy
  String  lastUpdatedBy

  public String toString() {
    def rc = "${mnroadId}"
    rc += (offset != null)?", ${offset}":", offset?"
    rc += sampleDate?", ${MrUtils.formatDate(sampleDate,'MM/dd/yy')}":", sampleDate?"
    rc += materialGroup?", ${materialGroup}":", materialGroup?"
    rc += liftNumber?", ${liftNumber}":", liftNumber?"
    rc += comments?", ${comments}":", comments?"
  }

    static mapping = {
      table 'mat_samples'
      columns {
        id column:'id'
      }
      id generator:'sequence', params:[sequence:'MAT_SAMPLES_ID_SEQ']
    }

  //  TODO make mnroad_id,cell,material,sample_date not null.  All but mnroad_id now have some nulls
  //  TODO replace cell with FK to SAMPLE_SOURCE

  Layer layer
  static def belongsTo = [Layer]
  static constraints = {
    layer                (nullable:true)
    mnroadId             (maxSize:11)
    cell                 (nullable:true, max:999)
    station              (nullable:true, max:1000000 as Double, scale:2)
    offset               (nullable:true, max:1000 as Double, scale:2)
    sampleDate           (nullable:true)
    materialGroup        (nullable:true, maxSize:40)
    containerType        (nullable:true, maxSize:30)
    storageLocation      (nullable:true, maxSize:50)
    comments             (nullable:true, maxSize:250)
    fieldId              (nullable:true, maxSize:25)
    contactPerson        (nullable:true, maxSize:24)
    course               (nullable:true, maxSize:8)
    liftNumber           (nullable:true)
    depthCode            (nullable:true, maxSize:40)
    sampleDepthTop       (nullable:true, max:1000 as Double, scale:2)
    sampleDepthBottom    (nullable:true, max:1000 as Double, scale:2)
    sampleTime           (nullable:true, maxSize:30)
    sampleCureTime       (nullable:true, max:1000 as Double, scale:2)
    spec                 (nullable:true, maxSize:15)
    lastUpdated          ()
    dateCreated          ()
    createdBy            (nullable:true)
    lastUpdatedBy        (nullable:true)
   }

}
