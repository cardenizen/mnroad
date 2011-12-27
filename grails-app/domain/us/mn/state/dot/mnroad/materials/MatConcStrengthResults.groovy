package us.mn.state.dot.mnroad.materials

class MatConcStrengthResults implements Serializable {

  static mapping = {
       table 'MAT_CONC_STRENGTH_RESULTS'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','labId','testAgeDays']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  String                testType
  Date                  dateTested
  String                labId
  java.math.BigDecimal  testAgeDays
  Integer               strength
  Integer               aveStrength
  java.math.BigDecimal  diameterIn
  java.math.BigDecimal  lengthIn
  java.math.BigDecimal  cappedLengthIn
  String                comments
  java.math.BigDecimal  weightLbs

  static constraints = {
      mnroadId      (maxSize: 11, blank: false)
      testType      (maxSize: 4, blank: false)
      dateTested    (nullable: true)
      labId         (nullable: false, maxSize: 30)
      testAgeDays   (nullable: false, scale: 2)
      strength      (nullable: true, maxSize: 4)
      aveStrength   (nullable: true, maxSize: 4)
      diameterIn    (nullable: true, scale: 2)
      lengthIn      (nullable: true, scale: 2)
      cappedLengthIn(nullable: true, scale: 2)
      comments      (nullable: true, maxSize: 80)
      weightLbs     (nullable: true, scale: 2)
  }
  String toString() {
      return "${mnroadId},${labId},${testAgeDays}"
  }
}
