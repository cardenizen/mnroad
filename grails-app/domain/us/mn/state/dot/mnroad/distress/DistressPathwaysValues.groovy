package us.mn.state.dot.mnroad.distress
// Ride quality data from Pathways pavement management van
class DistressPathwaysValues extends Distress {

   Integer   cell          // test cell number
   String    laneDescr     // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double    testLengthFt  // length of test, feet
   Date      day           // date of Pathways measurement
   Integer   time          // time of day of test
   Integer   run           // test trial number
   Double    iriLwp        // International Roughness Index in left wheel path, meters per kilometer
   Double    iriRwp        // International Roughness Index in right wheel path, meters per kilometer
   Double    iriAvg        // average International Roughness Index, meters per kilometer
   Double    rutLwp        // rut depth in left wheel path, inches
   Double    rutCen        // rut depth measured with center laser, inches
   Double    rutRwp        // rut depth in right wheel path, inches
   Double    rutAvg        // average rut depth, inches
   Double    psr           // Present Serviceability Rating
   Double    sr            // Surface Rating
   Date      dateUpdated   // date record was most recently updated
   String    comments      // any comments on test results
   Double    rqi           // Ride Quality Index

    static mapping = {
      table 'DISTRESS_RIDE_PATHWAYS'
      columns {
        laneDescr column:'lane'
        iriLwp column:'IRI_LWP_M_KM'
        iriRwp column:'IRI_RWP_M_KM'
        iriAvg column:'IRI_AVG_M_KM'
        rutLwp column:'RUT_LWP_IN'
        rutCen column:'RUT_CEN_IN'
        rutRwp column:'RUT_RWP_IN'
        rutAvg column:'RUT_AVG_IN'
      }
    }
}
