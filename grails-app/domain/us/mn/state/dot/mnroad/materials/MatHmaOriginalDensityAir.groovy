/**
 * The MatHmaCoreTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaOriginalDensityAir implements Serializable {
  static mapping = {
    table 'MAT_HMA_ORIGINAL_DENSITY_AIR'
    // version is set to false, because this isn't available by default for legacy databases
    version false
    id composite:['mnroadId','hmaLift']
    columns {
    }
  }
  String id
  String  mnroadId
  String  description
  Integer hmaLift
  String  liftDescription
  String  testingLab
  Double  liftThicknessInches
  Double  bulkSpecificGravity
  Double  theoMaxSpecificGravity
  Double  airVoidPercent
  String  resultsComment
  
  static constraints = {
    mnroadId               (maxSize: 15, blank: false)
    description            (maxSize: 50, blank: true)
    hmaLift                (nullable: false, maxSize: 1)
    liftDescription        (maxSize: 10, blank: true)
    testingLab             (maxSize: 30, blank: true)
    liftThicknessInches    (nullable: false, scale: 3)
    bulkSpecificGravity    (nullable: false, scale: 3)
    theoMaxSpecificGravity (nullable: false, scale: 3)
    airVoidPercent         (nullable: false, scale: 1)
    resultsComment         (maxSize: 75, blank: true)
    }
  
  String toString() {
      return "${mnroadId},${hmaLift}"
  }
}
