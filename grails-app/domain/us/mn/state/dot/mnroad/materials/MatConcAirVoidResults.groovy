package us.mn.state.dot.mnroad.materials

class MatConcAirVoidResults implements Serializable {
  static mapping = {
       table 'MAT_CONC_AIR_VOID_RESULTS'
       // version is set to false, because it isn't available in the table
       version false
       id composite:['mnroadId']
  }
  String id
  String                mnroadId
  Date                  dateTested
  java.math.BigDecimal  percentAir
  Integer               specificSurface
  java.math.BigDecimal  spacingFactor
  java.math.BigDecimal  voidsPerInch
  java.math.BigDecimal  pasteToAir
  String comments

  static constraints = {
      mnroadId        (maxSize: 11, blank: false)
      dateTested      (nullable: true)
      percentAir      (nullable: true, scale: 2)
      specificSurface (nullable: true, maxSize: 4)
      spacingFactor   (nullable: true, scale: 4)
      voidsPerInch    (nullable: true, scale: 2)
      pasteToAir      (nullable: true, scale: 2)
      comments        (nullable: true, maxSize: 80)
  }
  String toString() {
      return "${mnroadId}"
  }
}
