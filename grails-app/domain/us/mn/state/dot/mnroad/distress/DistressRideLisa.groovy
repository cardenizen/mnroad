package us.mn.state.dot.mnroad.distress
// Ride quality data measured with the lightweight surface analyzer
class DistressRideLisa extends Distress {

   Integer    cell              // test cell number
   Date       day               // date of ride measurement
   String     laneDescr         // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   String     wheelpath         // wheelpath
   String     collectionMethod  // person 1 conducting the test
   Integer    runNo             // person 2 conducting the test
   String     operator1         // data collection method
   String     operator2         // test trial number
   Double     iriRunMKm         // International Roughness Index with regular laser, meters per kilometer
   String     comments          // any comments on test results
   Double     rolineIriMKm      // International Roughness Index with Roline laser, meters per kilometer

    static mapping = {
      table 'DISTRESS_RIDE_LISA'
      columns {
        laneDescr    column:'lane'
        iriRunMKm    column:'IRI_RUN_M_KM'
        rolineIriMKm column:'ROLINE_IRI_M_KM'
      }
    }
}
