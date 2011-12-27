/**
 * The MatHmaRepeatPermDeform entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaRepeatPermDeform implements Serializable {
    static mapping = {
      table 'MAT_HMA_REPEAT_PERM_DEFORM'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc']
      columns {
        dateCreated   column:'create_date'
        lastUpdated   column:'modify_date'
        createdBy     column:'create_user'
        lastUpdatedBy column:'modify_user'
      }
    }
    String id
    java.math.BigDecimal  matHmaRepeatPermDeformPk
    String                mnroadId
    Integer               cell
    String                testingLab
    Date                  testDate
    String                sampleDesc
    java.math.BigDecimal  acPercent
    java.math.BigDecimal  airVoidsPercent
    java.math.BigDecimal  tempF
    Integer               confiningPressurePsi
    Integer               verticalStressPsi
    java.math.BigDecimal  interceptA
    java.math.BigDecimal  slopeB
    java.math.BigDecimal  resilientStrain200Percent
    Integer               flowNumber
    Integer               totalNoCycles
    String                comments
    Date                  dateCreated
    Date                  lastUpdated
    String                createdBy
    String                lastUpdatedBy

    static constraints = {
        matHmaRepeatPermDeformPk  (maxSize: 22)
        mnroadId                  (maxSize: 11, blank: false)
        cell                      (nullable: true)
        testingLab                (maxSize: 36, blank: true)
        testDate                  (nullable: true)
        sampleDesc                (maxSize: 40, blank: false)
        acPercent                 (nullable: true, scale: 2)
        airVoidsPercent           (nullable: true, scale: 2)
        tempF                     (nullable: true, scale: 1)
        confiningPressurePsi      (nullable: true)
        verticalStressPsi         (nullable: true)
        interceptA                (nullable: true, scale: 5)
        slopeB                    (nullable: true, scale: 5)
        resilientStrain200Percent (nullable: true, scale: 5)
        flowNumber                (nullable: true)
        totalNoCycles             (nullable: true)
        comments                  (maxSize: 100, blank: true)
        createUser                ()
        createDate                ()
        modifyUser                ()
        modifyDate                ()
    }
    String toString() {
        return "${mnroadId},${sampleDesc}" 
    }
}
