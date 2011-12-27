/**
 * The MatTestTypes entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatTestTypes implements Serializable {
    static mapping = {
      table 'MAT_TEST_TYPES'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['testType','testTypeDesc']
    }
    String testType
    String testTypeDesc
    String subtestName
    String aashtoDesignation
    String resultsUnits
    String comments

    static constraints = {
        testType          (maxSize: 4, nullable:false, blank: false)
        testTypeDesc      (maxSize: 80, nullable:false, blank: false)
        subtestName       (maxSize: 40, nullable:true, blank: true)
        aashtoDesignation (maxSize: 60, nullable:true, blank: true)
        resultsUnits      (maxSize: 60, nullable:true, blank: true)
        comments          (maxSize: 500, nullable:true, blank: true)
    }
    String toString() {
        return "${testType},${testTypeDesc}"
    }
}
