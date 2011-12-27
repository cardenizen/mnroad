/**
 * The MatUnboundTubeSuction entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatUnboundTubeSuction implements Serializable {
    static mapping = {
      table 'MAT_UNBOUND_TUBE_SUCTION'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','sampleNo','day','hours']
    }
    String                mnroadId
    String                sampleNo
    Date                  day
    java.math.BigDecimal  hours
    java.math.BigDecimal  dielectricValue
    Integer               conductivity
    java.math.BigDecimal  moistureContentPercent
    java.math.BigDecimal  actWetDensityPcf
    java.math.BigDecimal  actDryDensityPcf
    java.math.BigDecimal  relativeDensity
    String                comments

    static constraints = {
        mnroadId                (maxSize: 20, nullable: false, blank: false)
        sampleNo                (maxSize: 30, nullable: false, blank: false)
        day                     (nullable: false)
        hours                   (nullable: false, scale: 1)
        dielectricValue         (nullable: true, scale: 1)
        conductivity            (nullable: true, maxSize: 6)
        moistureContentPercent  (nullable: true, scale: 1)
        actWetDensityPcf        (nullable: true, scale: 1)
        actDryDensityPcf        (nullable: true, scale: 1)
        relativeDensity         (nullable: true, scale: 1)
        comments                (nullable: true, maxSize: 100)
    }
    String toString() {
        return "${mnroadId},${sampleNo},${day},${hours}"
    }
}
