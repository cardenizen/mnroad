package us.mn.state.dot.mnroad.distress
// Processed (calculated) cupping data from Automated Laser Profile System
class DistressCupping extends Distress {

    Integer  cell                    // test cell number                                                  
    Date     day                     // date of test                                                      
    Double   station                 // road station                                                      
    String   wheelPath               // wheel path location within lane                                   
    String   crackNo                 // crack number (cell-crack number-wheel path)                       
    Double   cuppingXDistanceInch    // longitudinal distance across cupped crack, inches                 
    Double   cupdepthYInch           // distance from laser to pavement surface, inches                   
    String   comments                // any comments on the test result                                   
    String   weather                 // description of weather                                            
    String   laneDescr               // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}

    static constraints = {            
    }                                                                                                     

    static mapping = {
      table 'DISTRESS_CUPPING'
      columns {
        laneDescr column:'lane'
        cuppingXDistanceInch column:'CUPPING_X_DISTANCE_INCH'
        cupdepthYInch column:'CUPDEPTH_Y_INCH'
      }
    }
}
