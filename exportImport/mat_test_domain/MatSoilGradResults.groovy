/**
 * The MatSoilGradResults entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatSoilGradResults {
    static mapping = {
         table 'MAT_SOIL_GRAD_RESULTS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String mnroadId
    String labSampleId
    java.math.BigDecimal passingNum4
    java.math.BigDecimal passingNum10
    java.math.BigDecimal passingNum20
    java.math.BigDecimal passingNum40
    java.math.BigDecimal passingNum60
    java.math.BigDecimal passingNum100
    java.math.BigDecimal passingNum200
    java.math.BigDecimal passing1In
    java.math.BigDecimal passing2In
    java.math.BigDecimal passing34In
    java.math.BigDecimal passing38In
    java.math.BigDecimal passing2Min
    java.math.BigDecimal passing5Min
    java.math.BigDecimal passing15Min
    java.math.BigDecimal passing30Min
    java.math.BigDecimal passing60Min
    java.math.BigDecimal passing250Min
    java.math.BigDecimal passing24Hr
    java.math.BigDecimal mmDiam2Min
    java.math.BigDecimal mmDiam5Min
    java.math.BigDecimal mmDiam15Min
    java.math.BigDecimal mmDiam30Min
    java.math.BigDecimal mmDiam60Min
    java.math.BigDecimal mmDiam250Min
    java.math.BigDecimal mmDiam24Hr
    Date dateUpdated

    static constraints = {
        mnroadId(size: 1..11, blank: false)
        labSampleId(size: 1..15, blank: false)
        passingNum4(nullable: true, scale: 2)
        passingNum10(nullable: true, scale: 2)
        passingNum20(nullable: true, scale: 2)
        passingNum40(nullable: true, scale: 2)
        passingNum60(nullable: true, scale: 2)
        passingNum100(nullable: true, scale: 2)
        passingNum200(nullable: true, scale: 2)
        passing1In(nullable: true, scale: 2)
        passing2In(nullable: true, scale: 2)
        passing34In(nullable: true, scale: 2)
        passing38In(nullable: true, scale: 2)
        passing2Min(nullable: true, scale: 2)
        passing5Min(nullable: true, scale: 2)
        passing15Min(nullable: true, scale: 2)
        passing30Min(nullable: true, scale: 2)
        passing60Min(nullable: true, scale: 2)
        passing250Min(nullable: true, scale: 2)
        passing24Hr(nullable: true, scale: 2)
        mmDiam2Min(nullable: true, scale: 4)
        mmDiam5Min(nullable: true, scale: 4)
        mmDiam15Min(nullable: true, scale: 4)
        mmDiam30Min(nullable: true, scale: 4)
        mmDiam60Min(nullable: true, scale: 4)
        mmDiam250Min(nullable: true, scale: 4)
        mmDiam24Hr(nullable: true, scale: 4)
        dateUpdated(nullable: true)
    }
    String toString() {
        return "${}" 
    }
}
