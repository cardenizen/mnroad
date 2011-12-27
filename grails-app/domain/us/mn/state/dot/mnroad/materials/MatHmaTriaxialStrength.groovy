/**
 * The MatHmaTriaxialStrength entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaTriaxialStrength implements Serializable {
    static mapping = {
      table 'MAT_HMA_TRIAXIAL_STRENGTH'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc']
      columns {
        tempF         column:'temp_f'
        dateCreated   column:'create_date'
        lastUpdated   column:'modify_date'
        createdBy     column:'create_user'
        lastUpdatedBy column:'modify_user'
      }
    }
    String  id
    Long    matHmaTriaxialStrengthPk
    String  mnroadId
    String  testingLab
    Date    testDate
    String  sampleDesc
    Double  acPercent
    Double  airVoidsPercent
    Integer confiningPressurePsi
    Double  tempF
    Double  maxDeviatoricStressPsi
    Double  maxNormalStressPsi
    Double  percentStrain
    Double  strainRateInPerMin
    String  comments
    String  createdBy
    Date    dateCreated
    String  lastUpdatedBy
    Date    lastUpdated

    static constraints = {
      matHmaTriaxialStrengthPk  (nullable: false)
      mnroadId                  (maxSize: 11, blank: false)
      testingLab                (maxSize: 36, blank: true)
      testDate                  (nullable: true)
      sampleDesc                (maxSize: 40, blank: false)
      acPercent                 (nullable: true, scale: 2)
      airVoidsPercent           (nullable: true, scale: 2)
      confiningPressurePsi      (maxSize: 3)
      tempF                     (nullable:true, scale: 1)
      maxDeviatoricStressPsi    (nullable:true, scale: 1)
      maxNormalStressPsi        (nullable:true, scale: 1)
      percentStrain             (nullable:true, scale: 1)
      strainRateInPerMin        (nullable:true, scale: 2)
      comments                  (maxSize: 100, blank: true)
    }

    String toString() {
      return "${mnroadId},${sampleDesc}"
    }
}
