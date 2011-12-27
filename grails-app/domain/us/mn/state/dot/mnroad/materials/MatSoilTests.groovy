/**
 * The MatSoilTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatSoilTests implements Serializable {
    static mapping = {
      table 'MAT_SOIL_TESTS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','labSampleId','subtestName']
      columns {
        lastUpdated   column: 'date_updated'
      }
    }
    String id
    String  mnroadId
    String  labSampleId
    String  testType
    String  subtestName
    Date    testDate
    String  testLabName
    String  proctorCurve
    Double  testResult
    String  testResultChar
    Date    lastUpdated

    static constraints = {
        mnroadId        (maxSize: 11, nullable: false, blank: false)
        labSampleId     (maxSize: 15, nullable: false)
        testType        (maxSize: 4, nullable: true)
        subtestName     (maxSize: 40, nullable: false)
        testDate        (nullable: true)
        testLabName     (maxSize: 40, nullable: true)
        proctorCurve    (maxSize: 3, nullable: true)
        testResult      (nullable: true, scale: 6)
        testResultChar  (size: 0..12)
    }
    String toString() {
        return "${mnroadId},${labSampleId},${subtestName}"
    }
}
