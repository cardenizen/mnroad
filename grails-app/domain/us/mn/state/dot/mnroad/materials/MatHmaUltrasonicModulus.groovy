/**
 * The MatHmaUltrasonicModulus entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaUltrasonicModulus implements Serializable {
    static mapping = {
      table 'MAT_HMA_ULTRASONIC_MODULUS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc','tempF']
      columns {
        tempF         column: 'temp_f'
        dateCreated   column: 'create_date'
        lastUpdated   column: 'modify_date'
        createdBy     column: 'create_user'
        lastUpdatedBy column: 'modify_user'
      }
    }
    String  id
    Long    matHmaIndirectTensFatiPk
    String  mnroadId
    String  testingLab
    Date    testDate
    String  sampleDesc
    Double  heightMm
    Double  densityKgM3
    Double  airVoidsPercent
    Integer elasticModulusPsi
    Double  tempF
    String  comments
    String  createdBy
    Date    dateCreated
    String  lastUpdatedBy
    Date    lastUpdated

    static constraints = {
      matHmaIndirectTensFatiPk (nullable:true)
      mnroadId                 (maxSize: 11, blank: false)
      testingLab               (maxSize: 36, blank: true)
      testDate                 (nullable: true)
      sampleDesc               (maxSize: 60, blank: true)
      heightMm                 (scale: 2, nullable: true)
      densityKgM3              (scale: 2, nullable: true)
      airVoidsPercent          (scale: 2, nullable: true)
      elasticModulusPsi        (maxSize: 9, nullable: true)
      tempF                    (nullable: true, scale: 1)
      comments                 (nullable: true, maxSize: 100)
    }

    String toString() {
        return "${mnroadId},${sampleDesc},${tempF}" 
    }
}
