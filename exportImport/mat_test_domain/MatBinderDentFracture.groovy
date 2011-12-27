/**
 * The MatBinderDentFracture entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatBinderDentFracture {
    static mapping = {
         table 'MAT_BINDER_DENT_FRACTURE'
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
    java.math.BigDecimal fractureToughness
    Date dateUpdated
    java.math.BigDecimal failStressMpa
    java.math.BigDecimal failStrain
    java.math.BigDecimal peakLoadKn

    static constraints = {
        mnroadId(size: 0..11)
        testingLab(size: 0..36)
        testDate(nullable: true)
        asphaltId(size: 0..24)
        description(size: 0..24)
        condition(size: 0..12)
        tempC(nullable: true, scale: 2)
        fractureToughness(nullable: true, size: 0..22)
        dateUpdated(nullable: true)
        failStressMpa(nullable: true, scale: 3)
        failStrain(nullable: true, scale: 3)
        peakLoadKn(nullable: true, scale: 4)
    }
    String toString() {
        return "${}" 
    }
}
