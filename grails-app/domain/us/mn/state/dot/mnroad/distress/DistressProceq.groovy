package us.mn.state.dot.mnroad.distress
// Field permeability measurements using the direct permeability tester
class DistressProceq extends Distress {

   Integer    cell              // test cell number                                                  
   Date       day               // date of permeability measurement                                  
   String     laneDescr         // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}
   Integer    panelNo           // concrete panel number                                             
   String     time              // time of day of test                                               
   Double     station           // road station                                                      
   Double     offsetFt          // offset from centerline, feet                                      
   Integer    resistivityKohmCm // resistivity measured with the Wenner probe                        
   Double     paMbar            // applied vacuum pressure                                           
   Integer    tmaxS             // test time, seconds                                                
   Double     deltaPmaxMbar     // change in pressure over TMAX                                      
   Double     kt10E16M2         // coefficient of air permeability                                   
   Double     lMm               // depth of penetration of the vacuum, millimeters                   
   String     concreteQuality   // qualitative description of concrete quality                       
   String     comments          // any comments on test results                                      
                                                                                                     
    static mapping = {
      table 'DISTRESS_PERMEABILITY_DIRECT'
      version false
      columns {
        cell      column:'CELL'
        laneDescr column:'LANE'
        tmaxS     column:'TMAX_S'
        kt10E16M2 column:'KT_10E_16_M2'
        lMm       column:'L_MM'
      }
    }

    static constraints = {
      concreteQuality       (nullable:true)
    }

}
