package us.mn.state.dot.mnroad.distress
// Pavement friction data from the dynamic friction tester
class DistressDynamicFriction extends Distress {
   Integer  cell              // test cell number 
   String   laneDescr         // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Date     day               // date of texture measurement
   Double   station           // road station
   Double   offsetFt          // offset from centerline, feet
   String   wheelpath         // wheelpath
   String   measuredBy        // person conducting the test
   Integer  runNo             // trial run at each location
   Double   speedKph          // speed, kilometers per hour
   Double   coeffFriction     // coefficient of friction
   String   comments          // any comments on test results
   Date     dateUpdated       // date record was most recently updated

   static constraints = {
     station           (nullable:true)
     offsetFt          (nullable:true)
     comments          (nullable:true)
   }

   static mapping = {
     table 'DISTRESS_FRICTION_DFT'
     columns {
       laneDescr column:'lane'
     }
   }
}
