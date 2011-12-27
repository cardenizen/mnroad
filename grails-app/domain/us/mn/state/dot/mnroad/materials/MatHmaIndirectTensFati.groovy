/**
 * The MatHmaIndirectTensFati entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaIndirectTensFati implements Serializable {
    static mapping = {
      table 'MAT_HMA_INDIRECT_TENS_FATI'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc']
      columns {
       tempF column:'temp_f'
      }
    }
    String id
    String  mnroadId
    String  testingLab
    Date    testDate
    String  sampleDesc
    Double  heightInch
    Double  loadLbs
    Double  tensileStressPsi
    Double  stressStrengthRatio
    Integer initialMicrostrain
    Integer cyclesToFailure
    Integer cyclesToHalfMr
    Integer initialResilientModKsi
    Double  tempF
    String  comments

    static constraints = {
      mnroadId                    (maxSize:11, blank: false)
      testingLab                  (maxSize:36, blank: true)
      testDate                    (nullable: false)
      sampleDesc                  (maxSize:40, blank: true)
      heightInch                  (nullable: true, scale: 2)
      loadLbs                     (nullable: true, scale: 1)
      tensileStressPsi            (nullable: true, scale: 1)
      stressStrengthRatio         (nullable: true, scale: 3)
      initialMicrostrain          (nullable: true, scale: 1)
      cyclesToFailure             (nullable: true)
      cyclesToHalfMr              (nullable: true)
      initialResilientModKsi      (nullable: true)
      tempF                       (nullable: true)
      comments                    (maxSize:100, blank: false)
    }
  
    String toString() {
        return "${mnroadId},${sampleDesc}"
    }
}
