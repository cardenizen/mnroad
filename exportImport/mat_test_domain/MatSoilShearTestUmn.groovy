/**
 * The MatSoilShearTestUmn entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatSoilShearTestUmn {
    static mapping = {
         table 'MAT_SOIL_SHEAR_TEST_UMN'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
