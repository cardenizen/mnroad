/**
 * The MatCoreLengths entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatCoreLengths {
    static mapping = {
         table 'MAT_CORE_LENGTHS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
