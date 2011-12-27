package us.mn.state.dot.mnroad

class DowelBar {
    String dowelNumber
    Double embedmentLengthIn
    Double transverseOffsetIn
    Double diameterWidth
    Integer length
    String type

    Date dateCreated
    Date lastUpdated
    String createdBy
    String lastUpdatedBy

    TrJoint trJoint
    Lane  lane
    //Layer layer
    static belongsTo=[TrJoint,Lane]//, Layer]

    static constraints = {
      embedmentLengthIn       (nullable:true)
      transverseOffsetIn      (nullable:true)
      diameterWidth           (nullable:true)
      length                  (nullable:true)
      type                    (nullable:true)
      dateCreated             (nullable:true)
      lastUpdated             (nullable:true)
      createdBy               (nullable:true)
      lastUpdatedBy           (nullable:true)
    }

    String toString() {
      "Dowel ${dowelNumber},  ${diameterWidth}\" ${type} in joint ${trJoint}, offset: ${transverseOffsetIn}, lane ${lane}"
    }

}

