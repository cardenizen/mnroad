/**
 * The MatHmaTtiOverlay entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaTtiOverlay implements Serializable {
    static mapping = {
      table 'MAT_HMA_TTI_OVERLAY'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc']
      columns {
        tempC column:'temp_c'
      }
    }
    String  id
    String  mnroadId
    String  testingLab
    Date    testDate
    String  sampleDesc
    Double  tempC
    Integer airVoidsPercent
    Integer startLoadLbs
    Integer finalLoadLbs
    Integer percentLoadDecline
    Integer noCycles
    String  comments

    static constraints = {
      mnroadId           (maxSize: 11, blank: false)
      testingLab         (maxSize: 36, blank: true)
      testDate           (nullable: true)
      sampleDesc         (maxSize: 60, blank: true)
      tempC              (nullable: true, scale: 1)
      airVoidsPercent    (nullable: true, maxSize: 4)
      startLoadLbs       (nullable: true, maxSize: 5)
      finalLoadLbs       (nullable: true, maxSize: 4)
      percentLoadDecline (nullable: true, maxSize: 4)
      noCycles           (nullable: true, maxSize: 5)
      comments           (nullable: true, maxSize: 200)
    }

    String toString() {
        return "${mnroadId},${sampleDesc}"
    }
}
