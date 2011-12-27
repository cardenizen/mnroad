package us.mn.state.dot.mnroad.distress
// Surface distress of concrete pavements from LTPP style crack survey
class DistressJpcc extends Distress {

  Integer   cell                   // test cell number                                                  
  String    laneDescr              // lane {Driving, Passing, Inside, Outside, Eastbound, Westbound, NA}
  Date      day                    // date survey was conducted                                         
  String    surveyor1              // person 1 conducting the survey                                    
  String    surveyor2              // person 2 conducting the survey                                    
  Integer   cornerBreaksNoL        // number of corner breaks low severity                              
  Integer   cornerBreaksNoM        // number of corner breaks medium severity                           
  Integer   cornerBreaksNoH        // number of corner breaks high severity                             
  Integer   durabCrackNoL          // number of slabs with D cracking low severity                      
  Integer   durabCrackNoM          // number of slabs with D cracking medium severity                   
  Integer   durabCrackNoH          // number of slabs with D cracking high severity                     
  Double    durabCrackAL           // total area of D cracking low severity, square feet                
  Double    durabCrackAM           // total area of D cracking medium severity, square feet             
  Double    durabCrackAH           // total area of D cracking high severity, square feet               
  Double    longCrackLL            // total length of longitudinal cracking low severity, feet          
  Double    longCrackLM            // total length of longitudinal cracking medium severity, feet       
  Double    longCrackLH            // total length of longitudinal cracking high severity, feet         
  Double    longCrackSealLL        // total length of sealed longitudinal cracking low severity, feet   
  Double    longCrackSealLM        // total length of sealed longitudinal cracking medium severity, feet
  Double    longCrackSealLH        // total length of sealed longitudinal cracking high severity, feet  
  Integer   transCrackNoL          // number of transverse cracks low severity                          
  Integer   transCrackNoM          // number of transverse cracks medium severity                       
  Integer   transCrackNoH          // number of transverse cracks high severity                         
  Double    transCrackLL           // total length of transverse cracks low severity, feet              
  Double    transCrackLM           // total length of transverse cracks medium severity, feet           
  Double    transCrackLH           // total length of transverse cracks high severity, feet             
  Double    transCrackSealLL       // total length of sealed transverse cracks low severity, feet       
  Double    transCrackSealLM       // total length of sealed transverse cracks medium severity, feet    
  Double    transCrackSealLH       // total length of sealed transverse cracks high severity, feet      
  String    jtSealed               // number of transverse joints sealed                                
  Integer   jointSealTransNoL      // number of transverse joints with low severity joint seal damage   
  Integer   jointSealTransNoM      // number of transverse joints with medium severity joint seal damage
  Integer   jointSealTransNoH      // number of transverse joints with high severity joint seal damage  
  Double    longJtSealNo           // number of longitudinal joints sealed                              
  Double    longJtSealDamL         // number of longitudinal joints with joint seal damage              
  Double    longSpallingLL         // total length of longitudinal spalling low severity, feet          
  Double    longSpallingLM         // total length of longitudinal spalling medium severity, feet       
  Double    longSpallingLH         // total length of longitudinal spalling high severity, feet         
  Integer   transSpallingNoL       // number of spalled transverse joints low severity                  
  Integer   transSpallingNoM       // number of spalled transverse joints medium severity               
  Integer   transSpallingNoH       // number of spalled transverse joints high severity                 
  Double    transSpallingLL        // total length of transverse spalling low severity, feet            
  Double    transSpallingLM        // total length of transverse spalling medium severity, feet         
  Double    transSpallingLH        // total length of transverse spalling high severity, feet           
  Integer   scalingNo              // number of scaling occurrences                                     
  Double    scalingA               // total area of scaling, square feet                                
  Double    polishAggA             // total area of polished aggregate, square feet                     
  Integer   popoutsNoArea          // total area of popouts, square feet                                
  Integer   blowupsNo              // number of blowups                                                 
  Integer   patchFlexNoL           // number of low severity flexible patches                           
  Integer   patchFlexNoM           // number of medium severity flexible patches                        
  Integer   patchFlexNoH           // number of high severity flexible patches                          
  Double    patchFlexAL            // total area of low severity flexible patches, square feet          
  Double    patchFlexAM            // total area of medium severity flexible patches, square feet       
  Double    patchFlexAH            // total area of high severity flexible patches, square feet         
  Integer   patchRigidNoL          // number of low severity rigid patches                              
  Integer   patchRigidNoM          // number of medium severity rigid patches                           
  Integer   patchRigidNoH          // number of high severity rigid patches                             
  Double    patchRigidAL           // total area of low severity rigid patches, square feet             
  Double    patchRigidAM           // total area of medium severity rigid patches, square feet          
  Double    patchRigidAH           // total area of high severity rigid patches, square feet            
  Integer   pumpingNo              // number of occurrences of pumping                                  
  Double    pumpingL               // total length of pumping, feet                                     
  Integer   mapCrackNo             // number of occurrences of map cracking        
  Double    mapCrackA              // total area of map cracking, square feet      
  Integer   longCrackNoL           // number of longitudinal cracks low severity   
  Integer   longCrackNoM           // number of longitudinal cracks medium severity
  Integer   longCrackNoH           // number of longitudinal cracks high severity  
  Date      dateUpdated            // date record was most recently updated        
                                                                                   
  static mapping = {
    table 'DISTRESS_JPCC'
    version false
    columns {
      laneDescr             column:'LANE'
      cornerBreaksNoL       column:'CORNER_BREAKS_NO_L'
      cornerBreaksNoM       column:'CORNER_BREAKS_NO_M'
      cornerBreaksNoH       column:'CORNER_BREAKS_NO_H'
      durabCrackNoL         column:'DURAB_CRACK_NO_L'
      durabCrackNoM         column:'DURAB_CRACK_NO_M'
      durabCrackNoH         column:'DURAB_CRACK_NO_H'
      durabCrackAL          column:'DURAB_CRACK_A_L'
      durabCrackAM          column:'DURAB_CRACK_A_M'
      durabCrackAH          column:'DURAB_CRACK_A_H'
      longCrackLL           column:'LONG_CRACK_L_L'
      longCrackLM           column:'LONG_CRACK_L_M'
      longCrackLH           column:'LONG_CRACK_L_H'
      longCrackSealLL       column:'LONG_CRACK_SEAL_L_L'
      longCrackSealLM       column:'LONG_CRACK_SEAL_L_M'
      longCrackSealLH       column:'LONG_CRACK_SEAL_L_H'
      transCrackNoL         column:'TRANS_CRACK_NO_L'
      transCrackNoM         column:'TRANS_CRACK_NO_M'
      transCrackNoH         column:'TRANS_CRACK_NO_H'
      transCrackLL          column:'TRANS_CRACK_L_L'
      transCrackLM          column:'TRANS_CRACK_L_M'
      transCrackLH          column:'TRANS_CRACK_L_H'
      transCrackSealLL      column:'TRANS_CRACK_SEAL_L_L'
      transCrackSealLM      column:'TRANS_CRACK_SEAL_L_M'
      transCrackSealLH      column:'TRANS_CRACK_SEAL_L_H'
      jtSealed              column:'JT_SEALED'
      jointSealTransNoL     column:'JOINT_SEAL_TRANS_NO_L'
      jointSealTransNoM     column:'JOINT_SEAL_TRANS_NO_M'
      jointSealTransNoH     column:'JOINT_SEAL_TRANS_NO_H'
      longJtSealNo          column:'LONG_JT_SEAL_NO'
      longJtSealDamL        column:'LONG_JT_SEAL_DAM_L'
      longSpallingLL        column:'LONG_SPALLING_L_L'
      longSpallingLM        column:'LONG_SPALLING_L_M'
      longSpallingLH        column:'LONG_SPALLING_L_H'
      transSpallingNoL      column:'TRANS_SPALLING_NO_L'
      transSpallingNoM      column:'TRANS_SPALLING_NO_M'
      transSpallingNoH      column:'TRANS_SPALLING_NO_H'
      transSpallingLL       column:'TRANS_SPALLING_L_L'
      transSpallingLM       column:'TRANS_SPALLING_L_M'
      transSpallingLH       column:'TRANS_SPALLING_L_H'
      scalingNo             column:'SCALING_NO'
      scalingA              column:'SCALING_A'
      polishAggA            column:'POLISH_AGG_A'
      popoutsNoArea         column:'POPOUTS_NO_AREA'
      blowupsNo             column:'BLOWUPS_NO'
      patchFlexNoL          column:'PATCH_FLEX_NO_L'
      patchFlexNoM          column:'PATCH_FLEX_NO_M'
      patchFlexNoH          column:'PATCH_FLEX_NO_H'
      patchFlexAL           column:'PATCH_FLEX_A_L'
      patchFlexAM           column:'PATCH_FLEX_A_M'
      patchFlexAH           column:'PATCH_FLEX_A_H'
      patchRigidNoL         column:'PATCH_RIGID_NO_L'
      patchRigidNoM         column:'PATCH_RIGID_NO_M'
      patchRigidNoH         column:'PATCH_RIGID_NO_H'
      patchRigidAL          column:'PATCH_RIGID_A_L'
      patchRigidAM          column:'PATCH_RIGID_A_M'
      patchRigidAH          column:'PATCH_RIGID_A_H'
      pumpingNo             column:'PUMPING_NO'
      pumpingL              column:'PUMPING_L'
      mapCrackNo            column:'MAP_CRACK_NO'
      mapCrackA             column:'MAP_CRACK_A'
      longCrackNoL          column:'LONG_CRACK_NO_L'
      longCrackNoM          column:'LONG_CRACK_NO_M'
      longCrackNoH          column:'LONG_CRACK_NO_H'
    }
  }

    static constraints = {
//      dateUpdated          (nullable:true)
    }
}    
