package us.mn.state.dot.mnroad.distress
// Ride quality data from Pavetech pavement management van
class DistressPavetechValues extends Distress {

   Date      day           // date of Pavetech measurement
   Integer   cell          // test cell number
   String    laneDescr     // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double    iriValue      // average International Roughness Index, meters per kilometer
   Double    ruttingValue  // average rut depth, inches

   static mapping = {
      table 'DISTRESS_RIDE_PAVETECH'
      columns {
        laneDescr    column:'lane'
        iriValue     column:'IRI_M_PER_KM'
        ruttingValue column:'RUT_IN'
      }
    }
}
