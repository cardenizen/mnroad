/**
 * The MatMrSoilsLabData entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatMrSoilsLabData implements Serializable {
    static mapping = {
         table 'MAT_MR_SOILS_LAB_DATA'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
