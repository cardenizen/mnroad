/**
 * The MatBinderDsrTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderDsrTests {
    static mapping = {
         table 'MAT_BINDER_DSR_TESTS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String mnroadId
    String mnroadIdComment
    java.math.BigDecimal cell
    java.math.BigDecimal hmaLift
    String labId
    String groupNo
    String testedBy
    Date testDate
    String asphaltId
    String description
    String sampleType
    java.math.BigDecimal freqRadS
    java.math.BigDecimal deltaDegrees
    java.math.BigDecimal gprimePa
    java.math.BigDecimal gdoubleprimePa
    java.math.BigDecimal gstarPa
    java.math.BigDecimal gTimesSinDeltaKpa
    java.math.BigDecimal gDivSinDeltaKpa
    java.math.BigDecimal gapMicrometers
    java.math.BigDecimal viscosityPaS
    java.math.BigDecimal torqueMicronewtonM
    java.math.BigDecimal percentStrain
    java.math.BigDecimal tempC
    java.math.BigDecimal diameterMm
    java.math.BigDecimal oscStressPa
    java.math.BigDecimal failTempC
    java.math.BigDecimal highTempPgGradeC
    Date dateUpdated

    static constraints = {
        mnroadId(size: 0..11)
        mnroadIdComment(size: 0..60)
        cell(nullable: true, size: 0..22)
        hmaLift(nullable: true, size: 0..22)
        labId(size: 0..24)
        groupNo(size: 0..12)
        testedBy(size: 0..24)
        testDate(nullable: true)
        asphaltId(size: 0..24)
        description(size: 0..200)
        sampleType(size: 0..24)
        freqRadS(nullable: true, size: 0..22)
        deltaDegrees(nullable: true, size: 0..22)
        gprimePa(nullable: true, size: 0..22)
        gdoubleprimePa(nullable: true, size: 0..22)
        gstarPa(nullable: true, size: 0..22)
        gTimesSinDeltaKpa(nullable: true, size: 0..22)
        gDivSinDeltaKpa(nullable: true, size: 0..22)
        gapMicrometers(nullable: true, size: 0..22)
        viscosityPaS(nullable: true, size: 0..22)
        torqueMicronewtonM(nullable: true, size: 0..22)
        percentStrain(nullable: true, size: 0..22)
        tempC(nullable: true, size: 0..22)
        diameterMm(nullable: true, size: 0..22)
        oscStressPa(nullable: true, size: 0..22)
        failTempC(nullable: true, size: 0..22)
        highTempPgGradeC(nullable: true, size: 0..22)
        dateUpdated(nullable: true)
    }
    String toString() {
        return "${}" 
    }
}
