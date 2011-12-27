package us.mn.state.dot.mnroad.materials

class MatHmaApa implements Serializable {
  static mapping = {
    table 'MAT_HMA_APA'
    // version is set to false, because this isn't available by default for legacy databases
    version false
    id composite:['mnroadId','sampleName','position']
    columns {
      tempC   column:'TEMP_C'
    }
  }
  String                id
  String                mnroadId
  String                testingLab
  Date                  testDate
  String                sampleName
  String                sampleType
  String                position
  java.math.BigDecimal  airVoidsPercent
  Integer               tempC
  Integer               strokes
  java.math.BigDecimal  rutMm
  String                comments
  Date                  dateUpdated

  static constraints = {
      mnroadId        (maxSize: 11, blank: false)
      testingLab      (maxSize: 36, blank: true)
      testDate        (nullable: true)
      sampleName      (maxSize: 24, blank: false)
      sampleType      (maxSize: 24, blank: true)
      position        (maxSize: 24, blank: false)
      airVoidsPercent (nullable: true, scale: 2)
      tempC           (nullable: true, maxSize: 4)
      strokes         (nullable: true, maxSize: 6)
      rutMm           (nullable: true, scale: 2)
      comments        (maxSize: 250, blank: true)
      dateUpdated     (nullable: true)
  }
  String toString() {
      return "${mnroadId},${sampleName},${position}"
  }
}
