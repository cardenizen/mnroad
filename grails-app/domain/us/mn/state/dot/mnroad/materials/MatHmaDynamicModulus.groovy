/**
 * The MatHmaDynamicModulus entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaDynamicModulus implements Serializable {
    static mapping = {
      table 'MAT_HMA_DYNAMIC_MODULUS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleNo','tempC','frequencyHz']
      columns {
       tempC column:'temp_c'
      }
    }
    String id
    String  mnroadId
    String  testingLab
    Date    testDate
    Integer sampleNo
    Double  airVoidsPercent
    Double  heightMm
    Double  diameterMm
    Double  tempC
    Double  frequencyHz
    Double  dynamicModulusGpa
    Double  phaseAngleDeg
    Date    dateUpdated

    static constraints = {
      mnroadId          (maxSize:11, blank: false)
      testingLab        (maxSize:36, blank: true)
      testDate          (nullable: true)
      sampleNo          (nullable: false)
      airVoidsPercent   (nullable: false, scale: 1)
      heightMm          (nullable: false, scale: 2)
      diameterMm        (nullable: false, scale: 2)
      tempC             (nullable: false, scale: 2)
      frequencyHz       (nullable: false, scale: 3)
      dynamicModulusGpa (nullable: false, scale: 1)
      phaseAngleDeg     (nullable: false, scale: 1)
      dateUpdated       (nullable: true)
    }

    String toString() {
        return "${mnroadId},${sampleNo},${tempC},${frequencyHz}"
    }
}
