package us.mn.state.dot.mnroad.distress

import us.mn.state.dot.mnroad.Lane

class Distress {
  Lane lane
  Long id
  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static belongsTo = Lane
  static mapping = {
    // use table-per-subclass inheritance strategy
    // each 
      tablePerHierarchy false
    id generator:'sequence', params:[sequence:'DISTRESS_SEQ']
  }

  static constraints = {
  }
  
}
