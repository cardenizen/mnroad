/**
 * The MatProctorCurves entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatProctorCurves implements Serializable {
    static mapping = {
      table 'MAT_PROCTOR_CURVES'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['proctorCurve']
    }
    String id
    String proctorCurve
    java.math.BigDecimal optimumMcPctDryWt
    java.math.BigDecimal maximumDryDensityPcf

    static constraints = {
        proctorCurve(maxSize: 14, nullable: false, blank: false)
        optimumMcPctDryWt(scale: 1, nullable: false)
        maximumDryDensityPcf(scale: 1, nullable: false)
    }
    String toString() {
        return "${proctorCurve}"
    }
}
