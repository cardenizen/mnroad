package us.mn.state.dot.mnroad.distress
// Processed (calculated via Excel macro) rutting data from Automated Laser Profile System
class DistressAlpsResultsRut extends Distress {
  Integer      cell              // test cell number
  Integer      station           // road station
  String       laneDescr         // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
  String       measurementType   // type of measurement
  Date         day               // date of rut measurement
  String       hourMinSec        // time, hour:minute:second
  String       wheelpath         // wheelpath
  Double       rutIn             // calculated rut depth, inches
  Double       waterIn           // calculated water depth, inches
  Double       rutVolCfpf        // calculated rut volume, cubic feet per foot
  Double       waterVolCfpf      // calculated water volume, cubic feet per foot
  Double       xRutFt            // horizontal distance to rut, feet
  Double       xWaterFt          // horizontal distance to water depth measurement, feet
  Date         dateUpdated       // date record was most recently updated
  String       comments          // any comments on the test result

    static mapping = {
      table 'DISTRESS_ALPS_RESULTS_RUT'
      columns {
        laneDescr column:'lane'
      }
    }

}
