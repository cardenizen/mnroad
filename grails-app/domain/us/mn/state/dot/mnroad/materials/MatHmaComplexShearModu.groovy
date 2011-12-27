/**
 * The MatHmaComplexShearModu entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaComplexShearModu implements Serializable {
    static mapping = {
      table 'MAT_HMA_COMPLEX_SHEAR_MODU'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc','tempF','frequencyHz','microstrain']
      columns {
        tempF column:'TEMP_F'
      }
    }
    String id  
    String                mnroadId
    String                testingLab
    Date                  testDate
    String                sampleDesc
    java.math.BigDecimal  tempF
    java.math.BigDecimal  frequencyHz
    java.math.BigDecimal  microstrain
    Integer               gstarPsi
    java.math.BigDecimal  phaseAngleDeg
    String                comments
    String                createdBy
    Date                  dateCreated
    String                modifiedBy
    Date                  dateModified

    static constraints = {
        mnroadId        (maxSize: 11, blank: false)
        testingLab      (maxSize: 36)
        testDate        (nullable: true)
        sampleDesc      (maxSize: 40, blank: false)
        tempF           (nullable: false, scale: 1)
        frequencyHz     (nullable: false, scale: 3)
        microstrain     (nullable: true, scale: 1)
        gstarPsi        (nullable: true, maxSize: 7)
        phaseAngleDeg   (nullable: true, scale: 1)
        comments        (maxSize: 100)
        createdBy       (maxSize: 8, blank: false)
        dateCreated     ()
        modifiedBy      (maxSize: 8, blank: false)
        dateModified    ()
    }
    String toString() {
        return "${mnroadId},${sampleDesc},${tempF},${frequencyHz},${microstrain}"
    }
}
