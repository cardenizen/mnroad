/**
 * The MatHmaSieveData entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaSieveData implements Serializable {
    static mapping = {
      table 'MAT_HMA_SIEVE_DATA'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id:composite:['mnroadId','lab','testDate','sampleType']
      columns {
        no4           column: 'no_4'
        no8           column: 'no_8'
        no10          column: 'no_10'
        no20          column: 'no_20'
        no40          column: 'no_40'
        no80          column: 'no_80'
        no200         column: 'no_200'
        no16          column: 'no_16'
        no30          column: 'no_30'
        no50          column: 'no_50'
        no100         column: 'no_100'
        dateCreated   column: 'create_date'
        lastUpdated   column: 'date_updated'
        createdBy     column: 'create_user'
        lastUpdatedBy column: 'modify_user'
      }
    }
    String                id
    String                mnroadId
    String                lab
    Date                  testDate
    String                sampleType
    Integer               threeFourths
    Integer               fiveEighths
    Integer               oneHalf
    Integer               threeEighths
    java.math.BigDecimal  no4
    java.math.BigDecimal  no8
    java.math.BigDecimal  no10
    java.math.BigDecimal  no20
    java.math.BigDecimal  no40
    java.math.BigDecimal  no80
    java.math.BigDecimal  no200
    java.math.BigDecimal  no16
    java.math.BigDecimal  no30
    java.math.BigDecimal  no50
    java.math.BigDecimal  no100
    Date                  dateCreated
    Date                  lastUpdated
    String                createdBy
    String                lastUpdatedBy

    static constraints = {
        mnroadId      (maxSize: 11, blank: false)
        lab           (maxSize: 40, blank: false)
        testDate      (nullable: false)
        sampleType    (maxSize: 30, blank: false)
        threeFourths  (nullable: true, maxSize: 3)
        fiveEighths   (nullable: true, maxSize: 3)
        oneHalf       (nullable: true, maxSize: 3)
        threeEighths  (nullable: true, maxSize: 3)
        no4           (nullable: true, scale: 1)
        no8           (nullable: true, scale: 1)
        no10          (nullable: true, scale: 1)
        no20          (nullable: true, scale: 1)
        no40          (nullable: true, scale: 1)
        no80          (nullable: true, scale: 1)
        no200         (nullable: true, scale: 1)
        no16          (nullable: true, scale: 1)
        no30          (nullable: true, scale: 1)
        no50          (nullable: true, scale: 1)
        no100         (nullable: true, scale: 1)
    }
    String toString() {
        return "${mnroadId},${lab},${testDate},${sampleType}" 
    }
}
