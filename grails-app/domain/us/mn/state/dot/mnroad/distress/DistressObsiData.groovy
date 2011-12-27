package us.mn.state.dot.mnroad.distress
// Raw data from on board sound intensity (noise) tests
class DistressObsiData extends Distress {

   Integer   cell                   // test cell number
   String    laneDescr              // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Date      day                    // date of OBSI measurement
   String    time                   // time of day of test, HH:MM:SS
   String    operator               // person conducting the test
   String    testTire               // test tire (typically standard reference test tire, SRTT)
   Double    freqHz                 // test frequency, hertz
   Double    leadingIntensityLevel  // sound intensity at leading edge of tire
   Double    leadingPi              // pressure-intensity index at leading edge of tire
   Double    leadingCoh             // coherence at leading edge of tire
   Double    trailingIntensityLevel // sound intensity at trailing edge of tire
   Double    trailingPi             // pressure-intensity index at trailing edge of tire
   Double    trailingCoh            // coherence at trailing edge of tire
   Double    avgIntensityLevel      // average sound intensity level
   Integer   trial                  // test trial number
   String    grind                  // description of diamond grind pattern if Cell 27
   String    comments               // any comments on test results

    static mapping = {
      table 'DISTRESS_OBSI_DATA'
      columns {
        laneDescr column:'lane'
      }
    }
}
