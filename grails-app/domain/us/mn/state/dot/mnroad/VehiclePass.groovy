package us.mn.state.dot.mnroad

/**
 * A VehiclePass represents the use of one TrackedVehicle navigating within a Lane and
 * passing near enough to one or more dynamic load test sensors to trigger a response.
 */

class VehiclePass {

  TrackedVehicle trackedVehicle
  String laneDescription
  Double speed
  Double grossWeight
  Date dateCollected
  Date dateCreated
  Date lastUpdated
  String createdBy
  SortedSet vehicleLocations
  static hasMany = [vehicleLocations:VehicleLocation]
  Facility facility
  static belongsTo = Facility


      static mapping = {
        columns { id column:'id' }
        id generator:'sequence', params:[sequence:'VT_ID_SEQ']
        vehicleLocations lazy:false
      }


}
