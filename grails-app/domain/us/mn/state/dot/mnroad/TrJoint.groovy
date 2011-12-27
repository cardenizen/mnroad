package us.mn.state.dot.mnroad

class TrJoint implements Comparable {

    Double station
    Integer jointNumber
    String sealantType
    Date dateCreated
    Date lastUpdated
    String createdBy
    String lastUpdatedBy
    static hasMany = [dowelBars:DowelBar]
//    PccPanel ahead
//    PccPanel behind
    Layer layer
    static belongsTo = Layer
    static mapping = {
      table 'transverse_joint'
      id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
    }
    static constraints = {
      station         ()
      jointNumber     ()
      sealantType     (nullable:true)
      createdBy       (nullable:true)
      lastUpdatedBy   (nullable:true)             
      dateCreated     (nullable:true)
      lastUpdated     (nullable:true)
    }

    String toString() {
      "Joint ${jointNumber} (id $id) at station ${MrUtils.decimalToStation(station)} offset: ${layer?.lane?.offsetRef}"
    }

    int compareTo(obj) {
        jointNumber.compareTo(obj.jointNumber)
    }

}
/*
   CELL_ID NUMBER(22),
   CELL NUMBER(3),
   STATION NUMBER(12,2),
   JOINT_NUMBER NUMBER(5) not null,
   SEALANT_TYPE char(20),
   ID NUMBER(19) PRIMARY KEY NOT NULL,
   VERSION NUMBER(22) NOT NULL,
   DATE_UPDATED timestamp,
   DATE_CREATED timestamp,
   CREATED_BY varchar2(10),
   UPDATED_BY varchar2(10)
*/