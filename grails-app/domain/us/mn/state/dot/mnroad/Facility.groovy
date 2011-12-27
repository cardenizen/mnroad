package us.mn.state.dot.mnroad

class Facility {
  String name
  String description
  SortedSet roadSections
  static hasMany = [roadSections:RoadSection]

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static constraints = {
    name                    (size:0..400)
    description             (size:0..400)
    createdBy               (nullable:true)
    lastUpdatedBy           (nullable:true)
    dateCreated             (nullable:true)
    lastUpdated             (nullable:true)
    createdBy               (nullable:true)
    lastUpdatedBy           (nullable:true)
  }

  String toString() {
      "${name}"
  }

  static mapping = {
    columns { id column:'id' }
    id natural:['name'], generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
    roadSections lazy:false
  }

}