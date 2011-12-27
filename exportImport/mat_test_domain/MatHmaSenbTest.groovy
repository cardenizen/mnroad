/**
 * The MatHmaSenbTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaSenbTest implements Serializable {
    static mapping = {
      table 'MAT_HMA_SENB_TEST'
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
    String id
    java.math.BigDecimal matHmaSenbTestPk
    String mnroadId
    Integer cell
    String sampleDesc
    String testingLab
    Date testDate
    java.math.BigDecimal tempC
    java.math.BigDecimal fractureEnergyJPerM2
    String comments
    String createUser
    Date createDate
    String modifyUser
    Date modifyDate

    static constraints = {
        matHmaSenbTestPk(size: 0..22)
        mnroadId(size: 1..11, blank: false)
        cell(nullable: true)
        sampleDesc(size: 1..40, blank: false)
        testingLab(size: 0..36)
        testDate(nullable: true)
        tempC(nullable: true, scale: 1)
        fractureEnergyJPerM2(nullable: true, scale: 1)
        comments(size: 0..100)
        createUser(size: 1..8, blank: false)
        createDate()
        modifyUser(size: 1..8, blank: false)
        modifyDate()
    }
    String toString() {
        return "${matHmaSenbTestPk}" 
    }
}
