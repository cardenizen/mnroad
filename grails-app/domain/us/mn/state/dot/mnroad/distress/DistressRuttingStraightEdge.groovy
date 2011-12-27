package us.mn.state.dot.mnroad.distress
// Asphalt rut depth measured with a 6 foot straight edge
class DistressRuttingStraightEdge extends Distress {

   Date       day                 // date of rutting measurement
   Integer    cell                // test cell number
   Double     station             // road station
   String     laneDescr           // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double     leftWpDepthIn       // rut depth in left wheelpath, inches
   Double     rightWpDepthIn      // rut depth in right wheelpath, inches
   String     comments            // any comments on measurements
   Date       dateUpdated         // date record was most recently updated

    static mapping = {
      table 'DISTRESS_RUTTING_STRAIGHT_EDGE'
      columns {
        laneDescr column:'lane'
      }
    }
}
