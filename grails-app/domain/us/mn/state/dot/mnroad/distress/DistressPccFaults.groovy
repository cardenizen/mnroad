package us.mn.state.dot.mnroad.distress
// Concrete faulting data measured by the faultmeter
class DistressPccFaults extends Distress {

   Integer   cell                   // test cell number                                                  
   String    laneDescr              // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Date      day                    // date of faulting measurement                                      
   Integer   sampleTime             // time of day of test                                               
   Integer   jointNumber            // transverse joint number                                           
   Double    faultDepthMm           // fault depth, millimeters                                         
   Date      dateUpdated            // date record was most recently updated                                                                            
   Double    offsetFromCenterlineFt // offset from centerline, feet

    static mapping = {
      table 'DISTRESS_PCC_FAULTS'
      columns {
        laneDescr column:'lane'
      }
    }
  static constraints = {
      faultDepthMm                   (nullable: false, maxSize:6, scale: 3)
  }
}
