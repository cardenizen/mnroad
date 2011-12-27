package us.mn.state.dot.mnroad.distress
// Pavement surface texture data from the circular texture meter
class DistressCirculrTextrMeter extends Distress {

    Integer   cell                 // test cell number                                                     
    String    laneDescr            // lane {Driving, Passing, Inside, Outside, Westbound, Eastbound, NA}   
    String    wheelPath            // wheelpath                                                            
    Date      day                  // date of texture measurement                                          
    String    time                 // time, hour:minute:second                                             
    String    operator             // person conducting the test                                           
    String    fieldId              // location ID in the field                                             
    Double    station              // road station                                                         
    Integer   offsetFt             // offset from centerline, feet                                         
    String    trial                // trial run at each location                                           
    Double    meanProfileDepthMm   // mean profile depth, millimeters                                      
    Double    rootMeanSquaredTd    // root mean squared of 8 measurements around circumference, millimeters
                                                                                                           
    static constraints = {
    }
  
    static mapping = {
      table 'DISTRESS_CIRCULR_TEXTR_METER'
      columns {
        laneDescr column:'lane'
        rootMeanSquaredTd column:'root_mean_squared_td_mm'
      }
    }
}
