/**
 * The MatHmaDctTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatHmaDctTest {
    static mapping = {
         table 'MAT_HMA_DCT_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'MAT_HMA_DCT_TEST_PK'
    }
    java.math.BigDecimal matHmaDctTestPk
    String mnroadId
    Integer cell
    String testingLab
    Date testDate
    String sampleDesc
    java.math.BigDecimal tempC
    java.math.BigDecimal fractureEnergyJPerM2
    String comments
    String createUser
    Date createDate
    String modifyUser
    Date modifyDate

    static constraints = {
        matHmaDctTestPk(size: 0..22)
        mnroadId(size: 1..11, blank: false)
        cell(nullable: true)
        testingLab(size: 0..36)
        testDate(nullable: true)
        sampleDesc(size: 1..40, blank: false)
        tempC(nullable: true, scale: 1)
        fractureEnergyJPerM2(nullable: true, scale: 1)
        comments(size: 0..100)
        createUser(size: 1..8, blank: false)
        createDate()
        modifyUser(size: 1..8, blank: false)
        modifyDate()
    }
    String toString() {
        return "${matHmaDctTestPk}" 
    }
}
