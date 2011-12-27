/**
 * The MatHmaRepeatPermDeform entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatHmaRepeatPermDeform {
    static mapping = {
         table 'MAT_HMA_REPEAT_PERM_DEFORM'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'MAT_HMA_REPEAT_PERM_DEFORM_PK'
    }
    java.math.BigDecimal matHmaRepeatPermDeformPk
    String mnroadId
    Integer cell
    String testingLab
    Date testDate
    String sampleDesc
    java.math.BigDecimal acPercent
    java.math.BigDecimal airVoidsPercent
    java.math.BigDecimal tempF
    Integer confiningPressurePsi
    Integer verticalStressPsi
    java.math.BigDecimal interceptA
    java.math.BigDecimal slopeB
    java.math.BigDecimal resilientStrain200Percent
    Integer flowNumber
    Integer totalNoCycles
    String comments
    String createUser
    Date createDate
    String modifyUser
    Date modifyDate

    static constraints = {
        matHmaRepeatPermDeformPk(size: 0..22)
        mnroadId(size: 0..11)
        cell(nullable: true)
        testingLab(size: 0..36)
        testDate(nullable: true)
        sampleDesc(size: 0..40)
        acPercent(nullable: true, scale: 2)
        airVoidsPercent(nullable: true, scale: 2)
        tempF(nullable: true, scale: 1)
        confiningPressurePsi(nullable: true)
        verticalStressPsi(nullable: true)
        interceptA(nullable: true, scale: 5)
        slopeB(nullable: true, scale: 5)
        resilientStrain200Percent(nullable: true, scale: 5)
        flowNumber(nullable: true)
        totalNoCycles(nullable: true)
        comments(size: 0..100)
        createUser(size: 1..8, blank: false)
        createDate()
        modifyUser(size: 1..8, blank: false)
        modifyDate()
    }
    String toString() {
        return "${matHmaRepeatPermDeformPk}" 
    }
}
