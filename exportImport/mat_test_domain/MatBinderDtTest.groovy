/**
 * The MatBinderDtTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderDtTest {
    static mapping = {
         table 'MAT_BINDER_DT_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String mnroadId
    String mnroadIdComment
    Integer cell
    String sampleType
    String description
    Date testDate
    String testedBy
    Integer targetTempC
    Double directTensionStrain
    java.math.BigDecimal directTensionStressMpa

    static constraints = {
        mnroadId(size: 1..20, blank: false)
        mnroadIdComment(size: 0..20)
        cell(nullable: true)
        sampleType(size: 0..40)
        description(size: 0..60)
        testDate(nullable: true)
        testedBy(size: 0..30)
        targetTempC(nullable: true)
        directTensionStrain(nullable: true, scale: 6)
        directTensionStressMpa(nullable: true, scale: 3)
    }
    String toString() {
        return "${}" 
    }
}
