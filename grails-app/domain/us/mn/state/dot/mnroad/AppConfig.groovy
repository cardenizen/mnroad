package us.mn.state.dot.mnroad

class AppConfig {

  String name
  String parameter
  String val
  String status

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static mapping = {
    columns {
      id column:'id'
    }
  id generator:'sequence', params:[sequence:'PICKLIST_ID_SEQ']
  }

  static constraints = {
    name          (nullable:false, size:1..80)
    parameter     (nullable:false, size:1..25)
    val           (nullable:false, size:1..4000)
    status        (nullable:false, size:1..20)
    createdBy     (nullable:true)
    lastUpdatedBy (nullable:true)
  }
}
