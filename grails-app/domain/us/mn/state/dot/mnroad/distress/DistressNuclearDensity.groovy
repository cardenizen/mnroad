package us.mn.state.dot.mnroad.distress
// Unbound material density and moisture tests with nuclear gauge
class DistressNuclearDensity extends Distress {

  Integer   cell                      // test cell number
  Date      day                       // date of measurement
  String    wheelpath                 // road station
  Double    station                   // offset from centerline, feet
  Double    offsetFt                  // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
  String    laneDescr                 // wheelpath
  String    operator                  // person conducting the test
  String    equipment                 // test equipment used
  String    setup                     // test setup
  String    materialTested            // pavement material being tested  ** eventually this will link to LAYER_ID
  Double    runOneTransversePcf       // density 1 measured with gauge transverse, pounds per cubic foot
  Double    runTwoTransversePcf       // density 2 measured with gauge transverse, pounds per cubic foot
  Double    runThreeLongitudinalPcf   // density 3 measured with gauge longitudinal, pounds per cubic foot
  Double    runFourLongitudinalPcf    // density 4 measured with gauge longitudinal, pounds per cubic foot
  Double    stationAveragePcf         // average density at test location, pounds per cubic foot
  Double    moistureContentPct        // moisture content, percent
  String    comments                  // any comments on measurements
                                                                                                         
   static mapping = {
     table 'DISTRESS_NUCLEAR_DENSITY'
     columns {
       laneDescr column:'lane'
       runOneTransversePcf     column:'RUN1_TRANSVERSE_PCF'
       runTwoTransversePcf     column:'RUN2_TRANSVERSE_PCF'
       runThreeLongitudinalPcf column:'RUN3_LONGITUDINAL_PCF'
       runFourLongitudinalPcf  column:'RUN4_LONGITUDINAL_PCF'
     }
   }
}