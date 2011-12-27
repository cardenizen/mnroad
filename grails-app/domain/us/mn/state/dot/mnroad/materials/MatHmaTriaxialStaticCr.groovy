/**
 * The MatHmaTriaxialStaticCr entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaTriaxialStaticCr implements Serializable {
    static mapping = {
      table 'MAT_HMA_TRIAXIAL_STATIC_CR'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc']
      columns {
        tempF       column: 'temp_f'
        interceptA  column: 'intercept_a'
        slopeB      column: 'slope_b'
        dateCreated   column:'create_date'
        lastUpdated   column:'modify_date'
        createdBy     column:'create_user'
        lastUpdatedBy column:'modify_user'
      }
    }
    String id
    Long     matHmaTriaxialStaticCrPk
    String   mnroadId
    String   testingLab
    Date     testDate
    String   sampleDesc
    Double   acPercent
    Double   airVoidsPercent
    Double   tempF
    Integer  confiningPressurePsi
    Integer  verticalStressPsi
    Double   interceptA
    Double   complianceAt1sec
    Double   slopeB
    Integer  flowTimeSec
    String   comments
    String   createdBy
    Date     dateCreated
    String   lastUpdatedBy
    Date     lastUpdated

    static constraints = {
      matHmaTriaxialStaticCrPk  (nullable: false)
      mnroadId                  (maxSize: 11, blank: false)
      testingLab                (maxSize: 36, blank: true)
      testDate                  (nullable:true)
      sampleDesc                (maxSize: 40, blank:false)
      acPercent                 (nullable:true, scale: 2)
      airVoidsPercent           (nullable:true, scale: 2)
      tempF                     (nullable:true, scale: 1)
      confiningPressurePsi      (nullable:true, maxSize: 3)
      verticalStressPsi         (nullable:true, maxSize: 3)
      interceptA                (nullable:true, scale: 5)
      complianceAt1sec          (nullable:true, scale: 6)
      slopeB                    (nullable:true, scale: 5)
      flowTimeSec               (nullable:true, maxSize: 5)
      comments                  (maxSize: 100, blank: true)
    }

    String toString() {
        return "${mnroadId},${sampleDesc}"
    }
}
