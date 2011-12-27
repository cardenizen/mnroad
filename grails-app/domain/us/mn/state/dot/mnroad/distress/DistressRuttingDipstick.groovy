package us.mn.state.dot.mnroad.distress
// Asphalt rut depth measured with a dipstick
class DistressRuttingDipstick extends Distress {
   Date           day                 // date of rutting measurement
   Integer        cell                // test cell number
   Double         station             // road station
   String         laneDescr           // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double         leftWpDepthIn       // rut depth in left wheelpath, inches
   Double         rightWpDepthIn      // rut depth in right wheelpath, inches

    static mapping = {
      table 'DISTRESS_RUTTING_DIPSTICK'
      columns {
        laneDescr    column:'lane'
      }
    }
    static constraints = {
    }
}
