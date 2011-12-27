package us.mn.state.dot.mnroad

/**
 * A TrackedVehicle represents a vehicle equiped with a device that can record Lat/Long
 * location at intervals of 1/10th second (7.33 ft @ 50 MPH).
 */

class TrackedVehicle {

  String description
  Double gpsMountLongitudinalOffset
  Double gpsMountTransverseOffset
  Integer numberOfAxles
  String tireSpacingWidthNumberList
  Date dateCreated
  Date lastUpdated
  String createdBy

static constraints = {
  description()
  gpsMountLongitudinalOffset()
  gpsMountTransverseOffset()
  numberOfAxles(min:2, max:15, size:2)
  tireSpacingWidthNumberList()
  dateCreated()
  lastUpdated()
  createdBy()
}

//  DESCRIPTION VARCHAR(40) NOT NULL,
//  -- GPS_MOUNT_ OFFSETS are relative to the center of the front axle
//  GPS_MOUNT_LONGITUDINAL_OFFSET NUMBER(8,5),
//  GPS_MOUNT_TRANSVERSE_OFFSET NUMBER(8,5),
//  -- Axles are numbered from to back beginning with 1
//  -- Minimum 2, Maximum 20
//  NUMBER_OF_AXLES NUMBER(2),
//  -- This list contains a comma separated list of axleSpacing_tireWidth_tireSpacing_numberOfAxleTires
//  -- axleSpacing: distance (feet) from previous axle
//  -- tireWidth:  width (inches) of tire surface contact
//  -- tireSpacing: center to center distance (feet) between outside tires
//  -- number: number of tires on the axle
//  -- example: Two-Axle, Six-Tire, Single Unit Trucks
//  -- front axle: 0.0_8.5_8.2_2,12.3_8.5_10.2_4
//  TIRE_SPACING_WIDTH_NUMBER_LIST VARCHAR(400),

}
