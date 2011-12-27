package us.mn.state.dot.mnroad.materials

class MatConcThermalExpansion implements Serializable {
  static mapping = {
    table 'MAT_CONC_THERMAL_EXPANSION'
    // version is set to false, because this isn't available by default for legacy databases
    version false
    id composite:['mnroadId']
    columns {
      ctePerDegC column:'CTE_PER_DEGC'
    }
  }
  String                id
  String                mnroadId
  String                sampleId
  java.math.BigDecimal  ctePerDegC
  String                comments
  Integer               diameterIn
  Integer               lengthIn
  Date                  dateCast
  Date                  dateTested
  java.math.BigDecimal  testAgeDays

  static constraints = {
      mnroadId    (maxSize: 11, blank: false)
      sampleId    (nullable: true, size: 0..30)
      ctePerDegC  (nullable: true, scale: 1)
      comments    (nullable: true, size: 0..50)
      diameterIn  (nullable: true, maxSize: 3)
      lengthIn    (nullable: true, maxSize: 3)
      dateCast    (nullable: true)
      dateTested  (nullable: true)
      testAgeDays (nullable: true, maxSize: 22)
  }
  String toString() {
      return "${mnroadId}"
  }
}
