/**
 * The MatBinderAbcdTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 * SELECT m.mnroad_id,m.sample_description,count(*) FROM mnr.MAT_BINDER_ABCD_TEST m
 * group by m.mnroad_id,m.sample_description
 * having count(*) > 1
 * should return 0 rows if m.mnroad_id,m.sample_description is the natural key
 */
package us.mn.state.dot.mnroad.materials

class MatBinderAbcdTest implements Serializable {
    static mapping = {
      table 'MAT_BINDER_ABCD_TEST'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDescription']
      columns {
        crackTempC column:'crack_temp_c'
      }
    }
    String id
    String                mnroadId
    String                testingLab
    Date                  testDate
    String                sampleDescription
    String                condition
    java.math.BigDecimal  crackTempC
    java.math.BigDecimal  strainJumpMicrostrain
    java.math.BigDecimal  coolingRateDegcHr

    static constraints = {
        mnroadId              (maxSize: 11, blank: false)
        testingLab            (maxSize: 50)
        testDate              (nullable: true)
        sampleDescription     (maxSize: 50, blank: false)
        condition             (maxSize: 50, blank: true)
        crackTempC            (nullable: true, scale: 1)
        strainJumpMicrostrain (nullable: true, scale: 1)
        coolingRateDegcHr     (nullable: true, scale: 1)
    }
    String toString() {
        return "${mnroadId},${sampleDescription}" 
    }
}
