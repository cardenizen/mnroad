/**
 * The MatHmaTsrstTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaTsrstTest implements Serializable {
    static mapping = {
         table 'MAT_HMA_TSRST_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
