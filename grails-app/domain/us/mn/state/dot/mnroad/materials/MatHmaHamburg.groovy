/**
 * The MatHmaHamburg entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaHamburg implements Serializable {
    static mapping = {
      table 'MAT_HMA_HAMBURG'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDescription']
      columns {
        tempC column:'temp_c'
      }
    }

    String id
    String  mnroadId
    String  testingLab
    Date    testDate
    String  sampleDescription
    String  condition
    Double	tempC
    Integer	noPasses
    Double  rutDepthMm

    static constraints = {
      mnroadId            (maxSize:11, blank: false)
      testingLab          (maxSize:36, blank: true)
      testDate            (nullable: false)
      sampleDescription   (maxSize: 50, blank: false)
      condition           (maxSize: 50, blank: true)
      tempC               (nullable: true, scale: 1)
      rutDepthMm          (nullable: true, scale: 1)
    }
    String toString() {
        return "${mnroadId},${sampleDescription}"
    }
}
