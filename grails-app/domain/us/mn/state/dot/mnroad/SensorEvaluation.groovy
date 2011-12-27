package us.mn.state.dot.mnroad

class SensorEvaluation implements Comparable {

  Date    dateComment
  String  commentBy
  String  evaluationMethod  // Unknown, Physical Inspection, Data Analysis
  String  result
  String  reason
  Sensor  sensor
  static belongsTo = Sensor  //DB column will be sensor_id

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static constraints = {
    dateComment           (nullable:false)
    commentBy             (maxSize:50, nullable:false)
    evaluationMethod      (maxSize:20, nullable:false)
    result                (maxSize:20, nullable:false)
    reason                (maxSize:200, nullable:true)
    dateCreated           (nullable:true)
    lastUpdated           (nullable:true)
    createdBy             (nullable:true)
    lastUpdatedBy         (nullable:true)
  }

  static mapping = {
    table 'SENSOR_EVALUATION'
    columns {
      id column:'id'
    }
    id generator:'sequence', params:[sequence:'SENSOR_SEQ']
  }

  int compareTo(se) {
    se.dateComment.compareTo(this.dateComment)
  }

}
