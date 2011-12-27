package us.mn.state.dot.mnroad.materials

class MatConcFreezeThawResults implements Serializable {

  static mapping = {
       table 'MAT_CONC_FREEZE_THAW_RESULTS'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','numberOfCycles']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  Integer               numberOfCycles
  Date                  dateTested
  Date                  dateCast
  java.math.BigDecimal  weightChange
  Integer               relativeDynamicMod
  Integer               totalNmbrCycles
  java.math.BigDecimal  totalWeightChange
  Integer               finalRelDynMod
  String                comments
  java.math.BigDecimal  length

  static constraints = {
      mnroadId          (maxSize: 11, blank: false)
      numberOfCycles    (nullable: false, maxSize: 4)
      dateTested        (nullable: true)
      dateCast          (nullable: true)
      weightChange      (nullable: true, scale: 2)
      relativeDynamicMod(nullable: true, maxSize: 3)
      totalNmbrCycles   (nullable: true, maxSize: 4)
      totalWeightChange (nullable: true, scale: 2)
      finalRelDynMod    (nullable: true, maxSize: 3)
      comments          (maxSize: 80)
      length            (nullable: true, scale: 2)
  }
  String toString() {
      return "${mnroadId},${numberOfCycles}"
  }
}
