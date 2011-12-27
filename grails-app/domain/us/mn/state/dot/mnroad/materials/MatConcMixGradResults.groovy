package us.mn.state.dot.mnroad.materials

class MatConcMixGradResults implements Serializable {

  static mapping = {
       table 'MAT_CONC_MIX_GRAD_RESULTS'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','labSampleId']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  String                labSampleId
  String                aggregateType
  String                pitNumber
  String                pitName
  String                testingLab
  java.math.BigDecimal  passingNum4
  java.math.BigDecimal  passingNum8
  java.math.BigDecimal  passingNum16
  java.math.BigDecimal  passingNum30
  java.math.BigDecimal  passingNum50
  java.math.BigDecimal  passingNum100
  java.math.BigDecimal  passingNum200
  java.math.BigDecimal  passing1In
  java.math.BigDecimal  passing112In
  java.math.BigDecimal  passing2In
  java.math.BigDecimal  passing34In
  java.math.BigDecimal  passing12In
  java.math.BigDecimal  passing38In
  java.math.BigDecimal  finenessModulus

  static constraints = {
      mnroadId        (maxSize: 15, blank: false)
      labSampleId     (maxSize: 30, blank: false)
      aggregateType   (maxSize: 15)
      pitNumber       (maxSize: 15)
      pitName         (maxSize: 50)
      testingLab      (maxSize: 20)
      passingNum4     (nullable: true, scale: 2)
      passingNum8     (nullable: true, scale: 2)
      passingNum16    (nullable: true, scale: 2)
      passingNum30    (nullable: true, scale: 2)
      passingNum50    (nullable: true, scale: 2)
      passingNum100   (nullable: true, scale: 2)
      passingNum200   (nullable: true, scale: 2)
      passing1In      (nullable: true, scale: 2)
      passing112In    (nullable: true, scale: 2)
      passing2In      (nullable: true, scale: 2)
      passing34In     (nullable: true, scale: 2)
      passing12In     (nullable: true, scale: 2)
      passing38In     (nullable: true, scale: 2)
      finenessModulus (nullable: true, scale: 2)
  }
  String toString() {
      return "${mnroadId},${labSampleId}"
  }
}
