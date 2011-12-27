/**
 * The MatPccCoefThermExp entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatPccCoefThermExp implements Serializable {
    static mapping = {
         table 'MAT_PCC_COEF_THERM_EXP'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String mnroadId
    String testType
    Date dateTested
    String umId
    Integer testAge
    java.math.BigDecimal coefThermalExpansion
    java.math.BigDecimal avgCoefThermalExpansion
    String units
    String comments

    static constraints = {
        mnroadId(size: 0..20)
        testType(size: 0..4)
        dateTested(nullable: true)
        umId(size: 0..10)
        testAge(nullable: true)
        coefThermalExpansion(nullable: true, scale: 2)
        avgCoefThermalExpansion(nullable: true, scale: 2)
        units(size: 0..20)
        comments(size: 0..30)
    }
    String toString() {
        return "${}" 
    }
}
