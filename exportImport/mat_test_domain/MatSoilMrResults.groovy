/**
 * The MatSoilMrResults entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatSoilMrResults {
    static mapping = {
         table 'MAT_SOIL_MR_RESULTS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
