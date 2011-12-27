package us.mn.state.dot.mnroad.materials

class MatConcFlexStrength implements Serializable {

  static mapping = {
       table 'MAT_CONC_FLEX_STRENGTH'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  String                sampleId
  String                testType
  Date                  dateTested
  Integer               testAgeDays
  Integer               flexStrengthPsi
  java.math.BigDecimal  depthIn
  java.math.BigDecimal  lengthIn
  java.math.BigDecimal  weightLbs
  String comments

  static constraints = {
      mnroadId        (maxSize: 30, blank: false)
      sampleId        (maxSize: 30, blank: true)
      testType        (maxSize: 10, blank: true)
      dateTested      (nullable: true)
      testAgeDays     (nullable: true, maxSize: 4)
      flexStrengthPsi (nullable: true, maxSize: 6)
      depthIn         (nullable: true, scale: 2)
      lengthIn        (nullable: true, scale: 2)
      weightLbs       (nullable: true, scale: 2)
      comments        (maxSize: 100, blank: true)
  }
  String toString() {
      return "${mnroadId}"
  }
}
