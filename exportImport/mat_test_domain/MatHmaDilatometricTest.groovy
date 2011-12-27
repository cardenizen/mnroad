/**
 * The MatHmaDilatometricTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatHmaDilatometricTest {
    static mapping = {
         table 'MAT_HMA_DILATOMETRIC_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'MAT_HMA_DILATOMETRIC_TEST_PK'
    }
    java.math.BigDecimal matHmaDilatometricTestPk
    String mnroadId
    Integer cell
    String testingLab
    Date testDate
    String sampleDesc
    String thermalCycle
    java.math.BigDecimal glassTransTempC
    Double coeffBelowTg
    Double coeffAboveTg
    String comments
    String createUser
    Date createDate
    String modifyUser
    Date modifyDate

    static constraints = {
        matHmaDilatometricTestPk(size: 0..22)
        mnroadId(size: 1..11, blank: false)
        cell(nullable: true)
        testingLab(size: 0..36)
        testDate(nullable: true)
        sampleDesc(size: 1..40, blank: false)
        thermalCycle(size: 0..25)
        glassTransTempC(nullable: true, scale: 1)
        coeffBelowTg(nullable: true, scale: 8)
        coeffAboveTg(nullable: true, scale: 8)
        comments(size: 0..100)
        createUser(size: 1..8, blank: false)
        createDate()
        modifyUser(size: 1..8, blank: false)
        modifyDate()
    }
    String toString() {
        return "${matHmaDilatometricTestPk}" 
    }
}
