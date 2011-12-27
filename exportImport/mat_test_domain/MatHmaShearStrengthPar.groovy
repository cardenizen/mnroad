/**
 * The MatHmaShearStrengthPar entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaShearStrengthPar implements Serializable {
    static mapping = {
         table 'MAT_HMA_SHEAR_STRENGTH_PAR'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'MAT_HMA_SHEAR_STRENGTH_PAR_PK'
    }
    java.math.BigDecimal matHmaShearStrengthParPk
    String mnroadId
    Integer cell
    String testingLab
    Date testDate
    java.math.BigDecimal cohesion100fPsi
    java.math.BigDecimal cohesion130fPsi
    java.math.BigDecimal friction100fDeg
    java.math.BigDecimal friction130fDeg
    java.math.BigDecimal shearStrength100fPsi
    java.math.BigDecimal shearStrength130fPsi
    String comments
    String createUser
    Date createDate
    String modifyUser
    Date modifyDate

    static constraints = {
        matHmaShearStrengthParPk(size: 0..22)
        mnroadId(size: 0..11)
        cell(nullable: true)
        testingLab(size: 0..36)
        testDate(nullable: true)
        cohesion100fPsi(nullable: true, scale: 1)
        cohesion130fPsi(nullable: true, scale: 1)
        friction100fDeg(nullable: true, scale: 1)
        friction130fDeg(nullable: true, scale: 1)
        shearStrength100fPsi(nullable: true, scale: 1)
        shearStrength130fPsi(nullable: true, scale: 1)
        comments(size: 0..100)
        createUser(size: 1..8, blank: false)
        createDate()
        modifyUser(size: 1..8, blank: false)
        modifyDate()
    }
    String toString() {
        return "${matHmaShearStrengthParPk}" 
    }
}
