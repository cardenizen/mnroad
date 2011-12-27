package us.mn.state.dot.mnroad

class VehicleLocation implements Comparable {

  Date   obsDatetime
  BigDecimal latitude
  String latitudeDirection
  BigDecimal longitude
  String longitudeDirection
  Date   dateCreated
  String createdBy
  VehiclePass vehiclePass
  static belongsTo = VehiclePass

  static mapping = {
    columns {
      id           column:'id'
    }
    id generator:'sequence', params:[sequence:'VT_ID_SEQ']
  }

  int compareTo(obj) {
    id.compareTo(obj.id)
  }

  def adjLatitude = {latitude + 0.00035}
  def adjLongitude =  {longitude + 0.00015}
  static transients = ["adjLatitude","adjLongitude"]

  static constraints = {
    obsDatetime()
    latitude()
    latitudeDirection(size:1..1)
    longitude()
    longitudeDirection(size:1..1)
    dateCreated()
    createdBy()
  }

}
