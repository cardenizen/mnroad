/**
 * The MatMrTestingLog entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatMrTestingLog implements Serializable {
    static mapping = {
         table 'MAT_MR_TESTING_LOG'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String xlsFileName
    String formerXlsFileName
    String mrFileName
    String loadFileName
    String unloadFileName
    String pretestSoilsLabId1
    String pretestSoilsFieldId1
    String pretestSoilsLabId2
    String pretestSoilsFieldId2
    String pretestSoilsLabId3
    String pretestSoilsFieldId3
    String posttestSoilsLabId
    String posttestSoilsFieldId
    String pretestAggLabId1
    String pretestAggFieldId1
    String pretestAggLabId2
    String pretestAggFieldId2
    String pretestAggLabId3
    String pretestAggFieldId3
    String posttestAggLabId
    String posttestAggFieldId
    Date testDate
    String testType
    String researchObjectives
    String mndotSpecifications
    String materialType
    String spnum
    String location
    String route
    String rpBegin
    String rpEnd
    String staBegin
    String staEnd
    String offset
    String sampleDepthIn
    String targetGradation
    String sampleType
    String shearSampleType
    java.math.BigDecimal moisturePct
    java.math.BigDecimal dryDensityPcf
    java.math.BigDecimal diameterIn
    java.math.BigDecimal mrSampleHeightIn
    java.math.BigDecimal shearSampleHeightIn
    java.math.BigDecimal loadingRateInSec
    java.math.BigDecimal confiningPressurePsi
    String operator
    Double lvdtSpacingIn
    String comments
    String protocol
    Date dateUpdated
    String testSite
    Integer lvdtCount
    String lvdtSkipped
    String compaction
    String pit
    String samIdExt

    static constraints = {
        xlsFileName(size: 1..40, blank: false)
        formerXlsFileName(size: 0..15)
        mrFileName(size: 0..15)
        loadFileName(size: 0..40)
        unloadFileName(size: 0..15)
        pretestSoilsLabId1(size: 0..15)
        pretestSoilsFieldId1(size: 0..50)
        pretestSoilsLabId2(size: 0..15)
        pretestSoilsFieldId2(size: 0..50)
        pretestSoilsLabId3(size: 0..15)
        pretestSoilsFieldId3(size: 0..50)
        posttestSoilsLabId(size: 0..15)
        posttestSoilsFieldId(size: 0..50)
        pretestAggLabId1(size: 0..15)
        pretestAggFieldId1(size: 0..50)
        pretestAggLabId2(size: 0..15)
        pretestAggFieldId2(size: 0..50)
        pretestAggLabId3(size: 0..15)
        pretestAggFieldId3(size: 0..50)
        posttestAggLabId(size: 0..15)
        posttestAggFieldId(size: 0..50)
        testDate(nullable: true)
        testType(size: 0..20)
        researchObjectives(size: 0..50)
        mndotSpecifications(size: 0..30)
        materialType(size: 0..40)
        spnum(size: 0..20)
        location(size: 0..50)
        route(size: 0..15)
        rpBegin(size: 0..15)
        rpEnd(size: 0..15)
        staBegin(size: 0..15)
        staEnd(size: 0..15)
        offset(size: 0..15)
        sampleDepthIn(size: 0..40)
        targetGradation(size: 0..15)
        sampleType(size: 0..30)
        shearSampleType(size: 0..30)
        moisturePct(nullable: true, scale: 2)
        dryDensityPcf(nullable: true, scale: 2)
        diameterIn(nullable: true, scale: 3)
        mrSampleHeightIn(nullable: true, scale: 3)
        shearSampleHeightIn(nullable: true, scale: 3)
        loadingRateInSec(nullable: true, scale: 2)
        confiningPressurePsi(nullable: true, scale: 1)
        operator(size: 0..30)
        lvdtSpacingIn(nullable: true, scale: 6)
        comments(size: 0..160)
        protocol(size: 0..40)
        dateUpdated(nullable: true)
        testSite(size: 0..50)
        lvdtCount(nullable: true)
        lvdtSkipped(size: 0..30)
        compaction(size: 0..20)
        pit(size: 0..50)
        samIdExt(size: 0..20)
    }
    String toString() {
        return "${}" 
    }
}
