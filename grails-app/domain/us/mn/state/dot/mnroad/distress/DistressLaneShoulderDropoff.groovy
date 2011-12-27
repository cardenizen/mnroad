package us.mn.state.dot.mnroad.distress
// Lane shoulder dropoff data measured by the faultmeter
class DistressLaneShoulderDropoff extends Distress {
    Integer   cell            // test cell number
    String    laneDescr       // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
    Date      day             // date of texture measurement
    String    time            // time of day of test
    String    operator        // person conducting the test
    Double    station         // road station
    Double    offset          // offset from centerline, feet
    Double    lsDepthMm       // depth of lane shoulder dropoff
    String    comments        // any comments on test result

    static mapping = {
      table 'DISTRESS_LANE_SHOULDER_DROPOFF'
      columns {
        laneDescr column:'lane'
        offset    column:'offset_ft'
      }
    }
}
