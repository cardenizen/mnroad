package us.mn.state.dot.mnroad.distress
// Concrete strength field data measured by schmidt hammer
class DistressSchmidtHammer extends Distress {

   Integer    cell            // test cell number                                                  
   Date       day             // date of measurement                                               
   Double     station         // road station                                                      
   Double     offsetFt        // offset from centerline, feet                                      
   Integer    panelNo         // concrete panel number                                             
   String     laneDescr       // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Double     reboundNo       // rebound number                                                    
   Integer    compStrengthPsi // compressive strength, pounds per square inch                      
   Double     stdevPsi        // standard deviation of compressive strength, pounds per square inch
   String     comments        // any comments on test results                                      
                                                                                                   
    static mapping = {
      table 'DISTRESS_SCHMIDT_HAMMER'
      columns {
        laneDescr column:'lane'
      }
    }
}
