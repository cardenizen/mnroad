package us.mn.state.dot.mnroad.distress
// Surface distress of aggregate surfaced test sections
class DistressAggSurveySemi  extends Distress {
  Date    day                  // date of survey                     
  Integer cell                 // test cell number                   
  String  laneDescr           // lane {Inside, Outside}
  Double  rutStart             // start station of rutting           
  Double  rutEnd               // end station of rutting             
  Double  washboardStart       // start station of washboarding      
  Double  washboardEnd         // end station of washboarding        
  Double  dustStart            // start station of dust              
  Double  dustEnd              // end station of dust                
  Double  distressComparison   // distress comparison                
  Double  dustComparison       // dust comparison                    
  Double  avgSpeedMph          // average speed, miles per hour      
  String  surveyComment        // any comments on the distress survey
                                                                     
    static mapping = {
      table 'DISTRESS_AGG_SURVEY_SEMI'
      columns {
        laneDescr column:'lane'
      }
    }
}
