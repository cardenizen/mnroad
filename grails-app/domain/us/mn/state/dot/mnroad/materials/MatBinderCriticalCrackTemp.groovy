/**
 * The MatBinderCriticalCrackTemp entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatBinderCriticalCrackTemp implements Serializable {
    static mapping = {
         table 'MAT_BINDER_CRITICAL_CRACK_TEMP'
         // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','description','condition','s300Mpa']
      columns {
        s300Mpa column:'s_300_mpa'
        mPoint300 column:'m_point_300'
      }
    }
    String id
    String                mnroadId
    String                testingLab
    Date                  testDate
    String                asphaltId
    String                description
    String                condition
    Double                s300Mpa
    Double                mPoint300
    Double                strain1Percent
    java.math.BigDecimal  pc24
    java.math.BigDecimal  pc18
    Date                  dateUpdated
    java.math.BigDecimal  bbrPc18
    java.math.BigDecimal  bbrDentPc1

    static constraints = {
        mnroadId      (maxSize: 11, blank: false)
        testingLab    (maxSize: 36)
        testDate      (nullable: true)
        asphaltId     (maxSize: 24)
        description   (maxSize: 24, blank: false)
        condition     (maxSize: 12, blank: false)
        s300Mpa       (nullable: false, scale: 10)
        mPoint300     (nullable: true, scale: 10)
        strain1Percent(nullable: true, scale: 10)
        pc24          (nullable: true, maxSize: 22)
        pc18          (nullable: true, maxSize: 22)
        dateUpdated   (nullable: true)
        bbrPc18       (nullable: true, maxSize: 22)
        bbrDentPc1    (nullable: true, maxSize: 22)
    }
    String toString() {
        return "${mnroadId},${description},${condition},${s300Mpa}"
    }
}
