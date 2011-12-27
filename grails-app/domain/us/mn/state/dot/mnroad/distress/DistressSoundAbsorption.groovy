package us.mn.state.dot.mnroad.distress
// Pavement sound absorption measured by impedance tube
class DistressSoundAbsorption extends Distress {

   Integer    cell                  // test cell number                                                  
   String     station               // road station                                                      
   String     laneDescr             // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   String     wheelpath             // wheelpath                                                         
   Date       day                   // test trial number                                                 
   String     trial                 // person conducting the test                                        
   String     operator              // date of sound absorption test                                     
   String     time                  // time of day of test                                               
   Double     tempC                 // approximate air temperature, degrees C                            
   Double     atmosPressurePascals  // atmospheric pressure, pascals                                     
   String     condition             // weather condition                                                 
   Double     frequencyHz           // test frequency, hertz                                             
   Double     soundAbsorption       // sound absorption                                                  
   String     comments              // any comments on test results                                      
   Double     offsetFt              // offset from centerline, feet                                      
                                                                                                   
    static mapping = {
      table 'DISTRESS_SOUND_ABSORPTION'
      columns {
        laneDescr column:'lane'
        tempC column:'TEMP_C'
      }
    }
}
