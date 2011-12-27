package us.mn.state.dot.mnroad.distress
// Surface distress of asphalt pavements from LTPP style crack survey
class DistressAc extends Distress {
  Integer cell                  // test cell number
  String  laneDescr             // lane {Driving, Passing, Inside, Outside, Eastbound, Westbound, NA}
  Date    day                   // date survey was conducted
  String  surveyor1             // person conducting the survey
  Double  fatigueAL             // fatigue cracking area low severity, square feet
  Double  fatigueAM             // fatigue cracking area medium severity, square feet
  Double  fatigueAH             // fatigue cracking area high severity, square feet
  Double  blockAL               // block cracking area low severity, square feet
  Double  blockAM               // block cracking area medium severity, square feet
  Double  blockAH               // block cracking area high severity, square feet
  Double  edgeLL                // edge cracking length low severity, feet
  Double  edgeLM                // edge cracking length medium severity, feet
  Double  edgeLH                // edge cracking length high severity, feet
  Double  longWpLL              // longitudinal wheelpath cracking length low severity, feet
  Double  longWpLM              // longitudinal wheelpath cracking length medium severity, feet
  Double  longWpLH              // longitudinal wheelpath cracking length high severity, feet
  Double  longWpSealLL          // sealed longitudinal wheelpath cracking length low severity, feet
  Double  longWpSealLM          // sealed longitudinal wheelpath cracking length medium severity, feet
  Double  longWpSealLH          // sealed longitudinal wheelpath cracking length high severity, feet
  Double  longNwpLL             // longitudinal non-wheelpath cracking length low severity, feet
  Double  longNwpLM             // longitudinal non-wheelpath cracking length medium severity, feet
  Double  longNwpLH             // longitudinal non-wheelpath cracking length high severity, feet
  Double  longNwpSealLL         // sealed longitudinal non-wheelpath cracking length low severity, feet
  Double  longNwpSealLM         // sealed longitudinal non-wheelpath cracking length medium severity, feet
  Double  longNwpSealLH         // sealed longitudinal non-wheelpath cracking length high severity, feet
  Integer transverseNoL         // number of transverse cracks low severity
  Integer transverseLL          // length of transverse cracks low severity, feet
  Integer transverseNoM         // number of transverse cracks medium severity
  Integer transverseLM          // length of transverse cracks medium severity, feet
  Integer transverseNoH         // number of transverse cracks high severity
  Integer transverseLH          // length of transverse cracks high severity, feet
  Integer transverseSealNoL     // number of sealed transverse cracks low severity
  Integer transverseSealLL      // length of sealed transverse cracks low severity, feet
  Integer transverseSealNoM     // number of sealed transverse cracks medium severity
  Integer transverseSealLM      // length of sealed transverse cracks medium severity, feet
  Integer transverseSealNoH     // number of sealed transverse cracks high severity
  Integer transverseSealLH      // length of sealed transverse cracks high severity, feet
  Integer patchNoL              // number of patches low severity
  Double  patchAL               // area of low severity patches, square feet
  Integer patchNoM              // number of patches medium severity
  Double  patchAM               // area of medium severity patches, square feet
  Integer patchNoH              // number of patches high severity
  Double  patchAH               // area of high severity patches, square feet
  Integer potholesNoL           // number of potholes low severity
  Double  potholesAL            // area of low severity potholes, square feet
  Integer potholesNoM           // number of potholes medium severity
  Double  potholesAM            // area of medium severity potholes, square feet
  Integer potholesNoH           // number of potholes high severity
  Double  potholesAH            // area of high severity potholes, square feet
  Integer shovingNo             // number of shoving occurrences
  Double  shovingA              // total area of shoving, square feet
  Double  bleedingAL            // area of bleeding low severity, square feet
  Double  bleedingAM            // area of bleeding medium severity, square feet
  Double  bleedingAH            // area of bleeding high severity, square feet
  Double  polishAggA            // area of polished aggregates, square feet
  Double  ravelingAL            // area of raveling low severity, square feet
  Double  ravelingAM            // area of raveling medium severity, square feet
  Double  ravelingAH            // area of raveling high severity, square feet
  Integer pumpingNo             // number of occurrences of water pumping
  Double  pumpingL              // total length of pumping, feet
  Integer constShldJntSealL     // length of distress along sealed edge joint low severity, feet
  Integer constShldJntSealM     // length of distress along sealed edge joint medium severity, feet
  Integer constShldJntSealH     // length of distress along sealed edge joint high severity, feet
  Integer constShldJntL         // length of distress along edge joint low severity, feet
  Integer constShldJntM         // length of distress along edge joint medium severity, feet
  Integer constShldJntH         // length of distress along edge joint high severity, feet
  Integer constClJntSealL       // length of distress along sealed centerline joint low severity, feet
  Integer constClJntSealM       // length of distress along sealed centerline joint medium severity, feet
  Integer constClJntSealH       // length of distress along sealed centerline joint high severity, feet
  Integer constClJntL           // length of distress along centerline  joint low severity, feet
  Integer constClJntM           // length of distress along centerline joint medium severity, feet
  Integer constClJntH           // length of distress along centerline joint high severity, feet
  String  comments              // any comments on the distress survey
  Date    dateUpdated           // date record was most recently updated
  Double  topdownLL             // length of topdown cracking low severity, feet
  Double  topdownLM             // length of topdown cracking medium severity, feet
  Double  topdownL              // length of topdown cracking high severity, feet

  static mapping = {
    table 'DISTRESS_AC'
//    version false
    columns {
      laneDescr           column:'LANE'
      fatigueAL           column:'FATIGUE_A_L'
      fatigueAM           column:'FATIGUE_A_M'
      fatigueAH           column:'FATIGUE_A_H'
      blockAL             column:'BLOCK_A_L'
      blockAM             column:'BLOCK_A_M'
      blockAH             column:'BLOCK_A_H'
      edgeLL              column:'EDGE_L_L'
      edgeLM              column:'EDGE_L_M'
      edgeLH              column:'EDGE_L_H'
      longWpLL            column:'LONG_WP_L_L'
      longWpLM            column:'LONG_WP_L_M'
      longWpLH            column:'LONG_WP_L_H'
      longWpSealLL        column:'LONG_WP_SEAL_L_L'
      longWpSealLM        column:'LONG_WP_SEAL_L_M'
      longWpSealLH        column:'LONG_WP_SEAL_L_H'
      longNwpLL           column:'LONG_NWP_L_L'
      longNwpLM           column:'LONG_NWP_L_M'
      longNwpLH           column:'LONG_NWP_L_H'
      longNwpSealLL       column:'LONG_NWP_SEAL_L_L'
      longNwpSealLM       column:'LONG_NWP_SEAL_L_M'
      longNwpSealLH       column:'LONG_NWP_SEAL_L_H'
      transverseNoL       column:'TRANSVERSE_NO_L'
      transverseLL        column:'TRANSVERSE_L_L'
      transverseNoM       column:'TRANSVERSE_NO_M'
      transverseLM        column:'TRANSVERSE_L_M'
      transverseNoH       column:'TRANSVERSE_NO_H'
      transverseLH        column:'TRANSVERSE_L_H'
      transverseSealNoL   column:'TRANSVERSE_SEAL_NO_L'
      transverseSealLL    column:'TRANSVERSE_SEAL_L_L'
      transverseSealNoM   column:'TRANSVERSE_SEAL_NO_M'
      transverseSealLM    column:'TRANSVERSE_SEAL_L_M'
      transverseSealNoH   column:'TRANSVERSE_SEAL_NO_H'
      transverseSealLH    column:'TRANSVERSE_SEAL_L_H'
      patchNoL            column:'PATCH_NO_L'
      patchAL             column:'PATCH_A_L'
      patchNoM            column:'PATCH_NO_M'
      patchAM             column:'PATCH_A_M'
      patchNoH            column:'PATCH_NO_H'
      patchAH             column:'PATCH_A_H'
      potholesNoL         column:'POTHOLES_NO_L'
      potholesAL          column:'POTHOLES_A_L'
      potholesNoM         column:'POTHOLES_NO_M'
      potholesAM          column:'POTHOLES_A_M'
      potholesNoH         column:'POTHOLES_NO_H'
      potholesAH          column:'POTHOLES_A_H'
      shovingNo           column:'SHOVING_NO'
      shovingA            column:'SHOVING_A'
      bleedingAL          column:'BLEEDING_A_L'
      bleedingAM          column:'BLEEDING_A_M'
      bleedingAH          column:'BLEEDING_A_H'
      polishAggA          column:'POLISH_AGG_A'
      ravelingAL          column:'RAVELING_A_L'
      ravelingAM          column:'RAVELING_A_M'
      ravelingAH          column:'RAVELING_A_H'
      pumpingNo           column:'PUMPING_NO'
      pumpingL            column:'PUMPING_L'
      constShldJntSealL   column:'CONST_SHLD_JNT_SEAL_L'
      constShldJntSealM   column:'CONST_SHLD_JNT_SEAL_M'
      constShldJntSealH   column:'CONST_SHLD_JNT_SEAL_H'
      constShldJntL       column:'CONST_SHLD_JNT_L'
      constShldJntM       column:'CONST_SHLD_JNT_M'
      constShldJntH       column:'CONST_SHLD_JNT_H'
      constClJntSealL     column:'CONST_CL_JNT_SEAL_L'
      constClJntSealM     column:'CONST_CL_JNT_SEAL_M'
      constClJntSealH     column:'CONST_CL_JNT_SEAL_H'
      constClJntL         column:'CONST_CL_JNT_L'
      constClJntM         column:'CONST_CL_JNT_M'
      constClJntH         column:'CONST_CL_JNT_H'
      comments            column:'COMMENTS'
      dateUpdated         column:'DATE_UPDATED'
      topdownLL           column:'TOPDOWN_L_L'
      topdownLM           column:'TOPDOWN_L_M'
      topdownL            column:'TOPDOWN_L_H'
    }
  }

    static constraints = {
      comments          (nullable:true)
      dateUpdated       (nullable:true)
    }

  String  toString() {
    "$cell,$laneDescr, $day, $surveyor1, $lane".toString()    
  }
}
