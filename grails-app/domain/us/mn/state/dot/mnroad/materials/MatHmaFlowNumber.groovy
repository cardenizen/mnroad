package us.mn.state.dot.mnroad.materials

class MatHmaFlowNumber implements Serializable {
  static mapping = {
    table 'MAT_HMA_FLOW_NUMBER'
    // version is set to false, because this isn't available by default for legacy databases
    version false
    id composite:['mnroadId','specimen','tempC']
    columns {
      tempC   column:'TEMP_C'
    }
  }
  String id
  String mnroadId
  String testingLab
  Date testDate
  String sampleDescription
  String specimen
  java.math.BigDecimal tempC
  java.math.BigDecimal confiningPressureKpa
  Integer flowNumberNchrp
  Integer totalStrainNchrp
  Integer totalCyclesNchrp
  Integer flowNumberFranken
  Integer totalStrainFranken
  Integer totalCyclesFranken

  static constraints = {
      mnroadId            (maxSize: 11, blank: false)
      testingLab          (size: 0..50)
      testDate            (nullable: true)
      sampleDescription   (maxSize: 50, blank: true)
      specimen            (maxSize: 50, blank: false)
      tempC               (nullable: true, scale: 1)
      confiningPressureKpa(nullable: true, scale: 1)
      flowNumberNchrp     (nullable: true)
      totalStrainNchrp    (nullable: true)
      totalCyclesNchrp    (nullable: true)
      flowNumberFranken   (nullable: true)
      totalStrainFranken  (nullable: true)
      totalCyclesFranken  (nullable: true)
  }
  String toString() {
      return "${mnroadId},${specimen},${tempC}"
  }
}
