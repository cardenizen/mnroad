package us.mn.state.dot.mnroad.distress
// Summary data from on board sound intensity (noise) tests
class DistressObsiSummary extends Distress {

   Integer   cell          // test cell number
   String    laneDescr     // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Date      day           // date of OBSI measurement
   String    time          // time of day of test, HH:MM:SS
   String    operator      // person conducting the test
   String    testTire      // test tire (typically standard reference test tire, SRTT)
   Double    leadingObsi   // on board sound intensity level at leading edge of tire, dbA
   Double    trailingObsi  // on board sound intensity level at trailing edge of tire, dbA
   Integer   trial         // test trial number
   String    grind         // description of diamond grind pattern if Cell 27
   Double    obsiAverage   // average on board sound intensity level, dbA
   String    comments      // any comments on test results

    static mapping = {
      table 'DISTRESS_OBSI_SUMMARY'
      columns {
        laneDescr column:'lane'
      }
    }
}
