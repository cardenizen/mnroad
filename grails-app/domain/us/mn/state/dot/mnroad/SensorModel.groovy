package us.mn.state.dot.mnroad

class SensorModel implements Comparable {

    String model
    String description
    String measurementType
    String dataValuesTable
    String measurementUnits
    Double minPossibleValue
    Double maxPossibleValue
    Double minExpectedValue
    Double maxExpectedValue
    String dynamicStatic

    static contraints = {
/*
alter table SENSOR_MODEL modify (model not null);
alter table SENSOR_MODEL modify (description not null);
      model             (nullable:false,maxSize:3)
      description       (nullable:false,maxSize:200)
      dynamicStatic     (nullable:false)
      measurementType   (nullable:false,maxSize:40)
      dataValuesTable   (nullable:false)
      measurementUnits  (nullable:false,maxSize:30)
      minPossibleValue  (nullable:false)
      maxPossibleValue  (nullable:false)
      minExpectedValue  (nullable:false)
      maxExpectedValue  (nullable:false)
*/
      model             (nullable:true,maxSize:3)
      description       (nullable:true,maxSize:200)
      dynamicStatic     (nullable:true)
      measurementType   (nullable:true,maxSize:40)
      dataValuesTable   (nullable:true)
      measurementUnits  (nullable:true,maxSize:30)
      minPossibleValue  (nullable:true)
      maxPossibleValue  (nullable:true)
      minExpectedValue  (nullable:true)
      maxExpectedValue  (nullable:true)
    }

    String toString() {
      model
    }

    Boolean confirmDelete
    static transients = [ 'confirmDelete' ]

    static hasMany = [ sensors : Sensor ]
    static mapping = {
      sensors cascade: 'delete', lazy: true, inverse: true
    }

    int compareTo(obj) {
      model.compareTo(obj.model)
    }
}
