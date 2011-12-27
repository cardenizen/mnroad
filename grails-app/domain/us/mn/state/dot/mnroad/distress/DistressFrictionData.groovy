package us.mn.state.dot.mnroad.distress
// Pavement friction data from the skid trailer
class DistressFrictionData extends Distress {

    Integer   cell            // test cell number
    String    laneDescr       // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
    Date      day             // date of texture measurement
    String    time            // time of day of test                                               
    Double    frictionNumber  // friction number
    Double    peak            // peak friction number                                              
    Double    speedMph        // vehicle speed, miles per hour                                     
    Double    airTempF        // air temperature, degrees F                                        
    Double    pvmtTempF       // pavement surface temperature, degrees F                           
    String    tireType        // tire type {Ribbed, Smooth}                                        
    String    equipment       // equipment used for test                                           
    Date      lastUpdated  // date record was most recently updated
//    Date      dateUpdated     // date record was most recently updated
    String    latitude        // latitude of test position from GPS                                
    String    longitude       // longitude of test position from GPS                               
    Integer   minfn           // minimum friction number                                           
    Integer   maxfn           // maximum friction number                                           
    Integer   slipPercent     // percent slip
    String    comments        // any comments on test results                                      
                                                                                                 
    static constraints = {
      cell            (blank: false)
      laneDescr       (blank: false)
      day             (nullable: false)
      time            (blank: false)
      frictionNumber  (nullable: false, scale: 1)
      peak            (nullable: true, scale: 2)
      speedMph        (nullable: true, scale: 1)
      airTempF        (nullable: true, scale: 2)
      pvmtTempF       (nullable: true, scale: 2)
      tireType        (maxSize: 10, blank: false)
      equipment       (maxSize: 50, nullable: true, blank: true)
      latitude        (maxSize: 14, nullable: true, blank: true)
      longitude       (maxSize: 14, nullable: true, blank: true)
      minfn           (nullable: true, maxSize: 2)
      maxfn           (nullable: true, maxSize: 2)
      slipPercent     (nullable: true, maxSize: 2)
      comments        (maxSize: 80, nullable: true, blank: true)
    }

    static mapping = {
      table 'DISTRESS_FRICTION_TRAILER'
      columns {
        laneDescr     column: 'lane'
        airTempF      column: 'air_temp_f'
        pvmtTempF     column: 'pvmt_temp_f'
        slipPercent   column: 'slip'
        lastUpdated   column: 'date_updated'
      }
    }
}
