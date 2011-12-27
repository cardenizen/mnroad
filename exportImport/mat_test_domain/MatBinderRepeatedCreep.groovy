/**
 * The MatBinderRepeatedCreep entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderRepeatedCreep {
    static mapping = {
         table 'MAT_BINDER_REPEATED_CREEP'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
