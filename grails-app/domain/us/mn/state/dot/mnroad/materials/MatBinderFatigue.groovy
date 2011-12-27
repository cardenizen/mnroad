/**
 * The MatBinderFatigue entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatBinderFatigue implements Serializable {
    static mapping = {
      table 'MAT_BINDER_FATIGUE'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','description','condition','cycleN']
      columns {
        cycleN column:'cycle_n'
        freqRadS column:'freq_rad_s'
        viscosityPaS column:'viscosity_pa_s'
        tempC column:'temp_c'
      }
    }
    String id
    String                mnroadId
    String                testingLab
    Date                  testDate
    String                description
    String                condition
    java.math.BigDecimal  cycleN
    java.math.BigDecimal  freqRadS
    java.math.BigDecimal  deltaDegrees
    Integer               gprimePa
    Integer               gdoubleprimePa
    Integer               gstarPa
    java.math.BigDecimal  gapMicrometers
    Integer               viscosityPaS
    Integer               stressPa
    java.math.BigDecimal  torqueMicronM
    Double                strainPercent
    java.math.BigDecimal  tempC
    java.math.BigDecimal  timeSec
    Double                dissipatedEnergyWn
    Double                dissipatedEnergyRatioDer
    Date                  dateUpdated

    static constraints = {
        mnroadId                (maxSize: 11, blank: false)
        testingLab              (maxSize: 36, blank: true)
        testDate                (nullable: true)
        description             (maxSize: 24, blank: false)
        condition               (maxSize: 12, blank: false)
        freqRadS                (nullable: true, maxSize: 22)
        deltaDegrees            (nullable: true, maxSize: 22)
        gprimePa                (nullable: true, maxSize: 10)
        gdoubleprimePa          (nullable: true, maxSize: 10)
        gstarPa                 (nullable: true, maxSize: 10)
        gapMicrometers          (nullable: true, maxSize: 22)
        viscosityPaS            (nullable: true, maxSize: 10)
        stressPa                (nullable: true, maxSize: 10)
        torqueMicronM           (nullable: true, maxSize: 22)
        strainPercent           (nullable: true, scale: 6)
        tempC                   (nullable: true, maxSize: 22)
        timeSec                 (nullable: true, maxSize: 22)
        cycleN                  (nullable: false, maxSize: 22)
        dissipatedEnergyWn      (nullable: true, scale: 6)
        dissipatedEnergyRatioDer(nullable: true, scale: 6)
        dateUpdated             (nullable: true)
    }
    String toString() {
        return "${mnroadId},${},${description},${condition},${cycleN}" 
    }
}
