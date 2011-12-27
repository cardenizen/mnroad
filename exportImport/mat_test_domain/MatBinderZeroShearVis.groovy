/**
 * The MatBinderZeroShearVis entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderZeroShearVis {
    static mapping = {
         table 'MAT_BINDER_ZERO_SHEAR_VIS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String mnroadId
    String testingLab
    Date testDate
    String asphaltId
    String description
    String condition
    java.math.BigDecimal tempC
    Double zsvPaSCam
    Integer zsvPaSGstarDivFreq
    Integer zsvPaSGdoubleprimeDivFreq
    Double zsvPaSSlope
    Date dateUpdated

    static constraints = {
        mnroadId(size: 0..11)
        testingLab(size: 0..36)
        testDate(nullable: true)
        asphaltId(size: 0..24)
        description(size: 0..24)
        condition(size: 0..12)
        tempC(nullable: true, scale: 1)
        zsvPaSCam(nullable: true, scale: 6)
        zsvPaSGstarDivFreq(nullable: true)
        zsvPaSGdoubleprimeDivFreq(nullable: true)
        zsvPaSSlope(nullable: true, scale: 6)
        dateUpdated(nullable: true)
    }
    String toString() {
        return "${}" 
    }
}
