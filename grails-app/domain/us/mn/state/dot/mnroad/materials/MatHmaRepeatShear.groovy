/**
 * The MatHmaRepeatShear entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaRepeatShear implements Serializable {
    static mapping = {
      table 'MAT_HMA_REPEAT_SHEAR'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleDesc','cycleNo']
      columns {
        tempF         column:'temp_f'
        dateCreated   column:'create_date'
        lastUpdated   column:'modify_date'
        createdBy     column:'create_user'
        lastUpdatedBy column:'modify_user'
      }
    }
    String id
    java.math.BigDecimal  matHmaRepeatShearPk
    String                mnroadId
    Integer               cell
    String                testingLab
    Date                  testDate
    String                sampleDesc
    java.math.BigDecimal  airVoidsPercent
    java.math.BigDecimal  tempF
    Integer               shearStressPsi
    Integer               cycleNo
    java.math.BigDecimal  permShearStrainPercent
    String                comments
    Date                  dateCreated
    Date                  lastUpdated
    String                createdBy
    String                lastUpdatedBy

    static constraints = {
        matHmaRepeatShearPk   (maxSize: 22)
        mnroadId              (maxSize: 11, blank: false)
        cell                  (nullable: true)
        testingLab            (maxSize: 36, blank: true)
        testDate              (nullable: true)
        sampleDesc            (maxSize: 40, blank: false)
        airVoidsPercent       (nullable: true, scale: 2)
        tempF                 (nullable: true, scale: 1)
        shearStressPsi        (nullable: true, maxSize: 3)
        cycleNo               (nullable: false, maxSize: 5)
        permShearStrainPercent(nullable: true, scale: 4)
        comments              (maxSize: 100, blank: true)
        createUser            (maxSize: 8, blank: false)
        createDate            ()
        modifyUser            (maxSize: 8, blank: false)
        modifyDate            ()
    }
    String toString() {
        return "${mnroadId},${sampleDesc},${cycleNo}" 
    }
}
