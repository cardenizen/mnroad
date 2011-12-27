package us.mn.state.dot.mnroad

class Sensor {

  Integer  seq
  String   description
  Date     dateInstalled
  Date     dateRemoved
  String   cabinet
  Double   stationFt
  Double   offsetFt
  Double   sensorDepthIn
  String   orientation
  String   collectionType
  String   dynamicStatic
  SortedSet evaluations
  static hasMany = [evaluations:SensorEvaluation]
  SensorModel sensorModel
  String   sensorId
  String   currentStatus
  Layer layer
  static belongsTo = [Layer, SensorModel]

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  String toString() {
    cellNum() + "${sensorModel.model}-${seq}, D:${sensorDepthIn}"
  }

  String cellNum() {
    (layer?.lane?.cell)?"Cell ${layer?.lane?.cell.cellNumber} ":""
  }

  String formattedDate(Date dt, String fmt) {
    return MrUtils.formatDate(dt, fmt)
  }

  String offsetRef() {
    offsetFt<0?'-':'+'
  }

  static mapping = {
    table 'SENSOR'
    columns {
      id column:'id'
    }
    id generator:'sequence', params:[sequence:'SENSOR_SEQ']
  }

    static constraints = {
    seq(max:999,    size:3)
    sensorId(maxSize:20,          nullable:true)
    description(maxSize:255,      nullable:true)
    dateInstalled(                nullable:true)
    dateRemoved(                  nullable:true)
    cabinet(maxSize:4,            nullable:true)
    dynamicStatic(maxSize:3,      nullable:true)
    stationFt(size:8,scale:2,     nullable:true)
    offsetFt(size:8,scale:2,      nullable:true)
    sensorDepthIn(size:8,scale:3, nullable:true)
    orientation(maxSize:15,       nullable:true)
    collectionType(               nullable:true)
    currentStatus(                nullable:true)
    dateCreated(                  nullable:true)
    lastUpdated(                  nullable:true)
    createdBy                    (nullable:true)
    lastUpdatedBy                (nullable:true)
    layer(                        nullable:true)
    }

    int compareTo(obj) {
      if (obj.sensorModel == sensorModel)
        seq.compareTo(obj.seq)
      else
        sensorDepthIn.compareTo(obj.sensorDepthIn)
    }

    def modelSeq() {
      "${sensorModel.model}-${seq}"
    }
}
