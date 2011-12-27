package us.mn.state.dot.mnroad.materials

class MatConcFieldResults implements Serializable {

  static mapping = {
       table 'MAT_CONC_FIELD_RESULTS'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  Date                  dateTested
  java.math.BigDecimal  slumpInch
  java.math.BigDecimal  percentAir
  java.math.BigDecimal  unitWeightPcf
  String comments

  static constraints = {
      mnroadId      (maxSize: 11, blank: false)
      dateTested    (nullable: true)
      slumpInch     (nullable: true, scale: 2)
      percentAir    (nullable: true, scale: 2)
      unitWeightPcf (nullable: true, scale: 2)
      comments      (maxSize: 80, blank: true)
  }
  String toString() {
      return "${mnroadId}"
  }
}
