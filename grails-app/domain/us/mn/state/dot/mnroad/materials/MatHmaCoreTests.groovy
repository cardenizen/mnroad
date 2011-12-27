/**
 * The MatHmaCoreTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaCoreTests implements Serializable {
    static mapping = {
      table 'MAT_HMA_CORE_TESTS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','liftNumber']
    }
    String id
    String                mnroadId
    Date                  testDate
    String                locationDesc
    String                lab
    Integer               liftNumber
    String                liftDesc
    java.math.BigDecimal  liftThicknessIn
    java.math.BigDecimal  maxSpgUsed
    String                maxSpgSource
    java.math.BigDecimal  inpGmb
    java.math.BigDecimal  inpVoids
    java.math.BigDecimal  inpVoidsStd
    String                comments
    Date                  dateUpdated

    static constraints = {
        mnroadId        (maxSize: 11, blank: false)
        testDate        (nullable: true)
        locationDesc    (maxSize: 15, blank: true)
        lab             (maxSize: 30, blank: true)
        liftNumber      (maxSize: 2, nullable: false)
        liftDesc        (maxSize: 25, blank: true)
        liftThicknessIn (nullable: true, scale: 3)
        maxSpgUsed      (nullable: true, scale: 3)
        maxSpgSource    (maxSize: 40, blank: true)
        inpGmb          (nullable: true, scale: 3)
        inpVoids        (nullable: true, scale: 2)
        inpVoidsStd     (nullable: true, scale: 2)
        comments        (maxSize: 250, blank: true)
        dateUpdated     (nullable: true)
    }
    String toString() {
        return "${mnroadId},${liftNumber}"
    }
}
