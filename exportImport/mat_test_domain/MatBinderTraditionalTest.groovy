/**
 * The MatBinderTraditionalTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderTraditionalTest {
    static mapping = {
         table 'MAT_BINDER_TRADITIONAL_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }

    static constraints = {
    }
    String toString() {
        return "${}" 
    }
}
