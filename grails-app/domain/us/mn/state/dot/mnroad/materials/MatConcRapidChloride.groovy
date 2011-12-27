package us.mn.state.dot.mnroad.materials

class MatConcRapidChloride implements Serializable {

  static mapping = {
       table 'MAT_CONC_RAPID_CHLORIDE'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','trial']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  String                sampleNo
  Integer               trial
  String                sampleDescription
  Integer               sampleDiameterIn
  Integer               sampleLengthIn
  Date                  dateCast
  Date                  dateTested
  Integer               ageDays
  Integer               coulombs
  java.math.BigDecimal  milliampsMax

  static constraints = {
      mnroadId          (maxSize: 30, blank: false)
      sampleNo          (maxSize: 30, blank: true)
      trial             (nullable: false, maxSize: 3)
      sampleDescription (maxSize: 50)
      sampleDiameterIn  (nullable: true, maxSize: 3)
      sampleLengthIn    (nullable: true, maxSize: 3)
      dateCast          (nullable: true)
      dateTested        (nullable: true)
      ageDays           (nullable: true, maxSize: 3)
      coulombs          (nullable: true, maxSize: 5)
      milliampsMax      (nullable: true, scale: 1)
  }
  String toString() {
      return "${mnroadId},${trial}"
  }

}
