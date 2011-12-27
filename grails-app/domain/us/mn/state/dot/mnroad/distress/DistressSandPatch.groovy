package us.mn.state.dot.mnroad.distress
// Surface texture data measured with sand patch test
class DistressSandPatch extends Distress {

   Integer    cell        // test cell number
   String     laneDescr        // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Date       day         // date of texture measurement
   String     time        // time of day of measurement
   Integer    offsetFt    // offset from centerline, feet
   Double     station     // road station
   String     measuredBy  // person conducting the test
   String     method      // ASTM test method
   String     fieldId     // field ID of test location
   Double     x1Mm        // diameter 1, millimeters
   Double     x2Mm        // diameter 2, millimeters
   Double     x3Mm        // diameter 3, millimeters
   Double     x4Mm        // diameter 4, millimeters
   Double     xAvgMm      // average sand patch diameter, millimeters
   Double     volumeMm3   // volume of sand, cubic millimeters
   Double     textureMm   // pavement surface texture depth, millimeters
   String     comments    // any comments on test results

    static mapping = {
      table 'DISTRESS_SAND_PATCH'
      columns {
        laneDescr column:'lane'
        x1Mm      column:'X1_MM'
        x2Mm      column:'X2_MM'
        x3Mm      column:'X3_MM'
        x4Mm      column:'X4_MM'
        volumeMm3 column:'VOLUME_MM3'
      }
    }
}
