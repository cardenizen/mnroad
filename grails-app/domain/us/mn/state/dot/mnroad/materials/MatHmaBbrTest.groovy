/**
 * The MatHmaBbrTest entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaBbrTest implements Serializable {
    static mapping = {
      table 'MAT_HMA_BBR_TEST'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','targetTempC','specimen','timeSec']
      columns {
       targetTempC column:'target_temp_c'
      }
    }
    String id
    String                mnroadId
    String                testingLab
    String                specimen
    Date                  testDate
    java.math.BigDecimal  targetTempC
    Integer               soakTimeMinutes
    java.math.BigDecimal  lengthMm
    java.math.BigDecimal  widthMm
    java.math.BigDecimal  thickMm
    java.math.BigDecimal  timeSec
    java.math.BigDecimal  stiffnessMpa
    java.math.BigDecimal  mValue

    static constraints = {
        mnroadId        (maxSize: 11, blank: false)
        testingLab      (maxSize: 50, blank: true)
        specimen        (maxSize: 50, blank: false)
        testDate        (nullable: true)
        targetTempC     (nullable: false, scale: 1)
        soakTimeMinutes (nullable: true, maxSize: 6)
        lengthMm        (nullable: true, scale: 1)
        widthMm         (nullable: true, scale: 2)
        thickMm         (nullable: true, scale: 2)
        timeSec         (nullable: false, scale: 1)
        stiffnessMpa    (nullable: true, scale: 1)
        mValue          (nullable: true, scale: 3)
    }
    String toString() {
        return "${mnroadId},${targetTempC},${specimen},${timeSec}"
    }
}
