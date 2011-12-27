package us.mn.state.dot.mnroad.distress
// Field permeability measurements using a falling head permeameter
class DistressWaterPermeability extends Distress {

   Integer    cell                 // test cell number
   Date       day                  // date of permeability measurement
   String     laneDescr            // time of day of test
   String     time                 // road station
   Double     station              // offset from centerline, feet
   Double     offsetFt             // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double     flowTimeS            // flow time, seconds
   Double     initialHeadCm        // initial head, centimeters
   Double     finalHeadCm          // final head, centimeters
   Double     pavementThicknessCm  // pavement thickness, centimeters
   Double     crossSectionAreaCm2  // cross section area of permeameter, square centimeters
   String     comments             // any comments on test results

    static mapping = {
      table 'DISTRESS_WATER_PERMEABILITY'
      columns {
        laneDescr column:'lane'
        flowTimeS column:'FLOW_TIME_S'
      }
    }
}
