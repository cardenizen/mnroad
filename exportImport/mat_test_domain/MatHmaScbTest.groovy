/**
 * The MatHmaScbTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaScbTest implements Serializable {
    static mapping = {
      table 'MAT_HMA_SCB_TEST'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id:composite:['mnroadId','sampleDesc','cycleNo']
      columns {
        tempC         column: 'temp_c'
        dateCreated   column:'create_date'
        lastUpdated   column:'date_updated'
        createdBy     column:'create_user'
        lastUpdatedBy column:'modify_user'
      }
    }
    String                id
    String                mnroadId
    Integer               cell
    String                testingLab
    Date                  testDate
    String                sampleName
    Integer               tempC
    java.math.BigDecimal  thicknessMm
    java.math.BigDecimal  notchLengthMm
    java.math.BigDecimal  radiusMm
    java.math.BigDecimal  fractureEnergy
    java.math.BigDecimal  fractureToughness
    java.math.BigDecimal  stiffness
    Date                  dateCreated
    Date                  lastUpdated
    String                createdBy
    String                lastUpdatedBy  

    static constraints = {
        mnroadId          (maxSize: 11, blank: false)
        cell              (nullable: true)
        testingLab        (maxSize: 36, blank: true)
        testDate          (nullable: true)
        sampleName        (maxSize: 24, blank: false)
        tempC             (nullable: true)
        thicknessMm       (nullable: true, scale: 2)
        notchLengthMm     (nullable: true, scale: 2)
        radiusMm          (nullable: true, scale: 2)
        fractureEnergy    (nullable: true, scale: 2)
        fractureToughness (nullable: true, scale: 2)
        stiffness         (nullable: true, scale: 2)
    }

    String toString() {
        return "${}" 
    }
}
