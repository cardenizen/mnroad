/**
 * The MatBinderBbrTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatBinderBbrTest {
    static mapping = {
         table 'MAT_BINDER_BBR_TEST'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'MNROAD_ID'
    }
    String mnroadId
    String mnroadIdComment
    Integer cell
    Integer hmaLift
    String labId
    String sampleType
    String specimen
    String description
    Date testDate
    String testedBy
    String fileName
    java.math.BigDecimal targetTempC
    java.math.BigDecimal widthMm
    java.math.BigDecimal thickMm
    Integer timeSec
    Integer pForceMn
    java.math.BigDecimal deflectionMm
    java.math.BigDecimal measuredStiffnessMpa
    java.math.BigDecimal estStiffnessMpa
    java.math.BigDecimal differencePercent
    String mValue
    java.math.BigDecimal ftempStiffness
    java.math.BigDecimal ftempMvalue
    java.math.BigDecimal lowTempPggrade
    Date dateUpdated

    static constraints = {
        mnroadId(size: 1..10, blank: false)
        mnroadIdComment(size: 0..80)
        cell(nullable: true)
        hmaLift(nullable: true)
        labId(size: 1..20, blank: false)
        sampleType(size: 1..50, blank: false)
        specimen(size: 0..50)
        description(size: 0..100)
        testDate(nullable: true)
        testedBy(size: 0..36)
        fileName(size: 0..12)
        targetTempC(nullable: true, scale: 1)
        widthMm(nullable: true, scale: 1)
        thickMm(nullable: true, scale: 2)
        timeSec(nullable: true)
        pForceMn(nullable: true)
        deflectionMm(nullable: true, scale: 3)
        measuredStiffnessMpa(nullable: true, scale: 1)
        estStiffnessMpa(nullable: true, scale: 1)
        differencePercent(nullable: true, scale: 3)
        mValue(size: 0..8)
        ftempStiffness(nullable: true, scale: 1)
        ftempMvalue(nullable: true, scale: 1)
        lowTempPggrade(nullable: true, scale: 1)
        dateUpdated(nullable: true)
    }
    String toString() {
        return "${mnroadId}" 
    }
}
