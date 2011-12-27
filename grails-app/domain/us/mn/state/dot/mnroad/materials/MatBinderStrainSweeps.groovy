/**
 * The MatBinderStrainSweeps entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatBinderStrainSweeps implements Serializable {
    static mapping = {
      table 'MAT_BINDER_STRAIN_SWEEPS'
         // version is set to false, because this isn't available by default for legacy databases
      version false
	  id composite:['mnroadId','description','condition','strainPercent']
      columns {
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
    java.math.BigDecimal  freqRadS
    java.math.BigDecimal  deltaDegrees
    Integer               gprimePa
    Integer               gdoubleprimePa
    Integer               gstarPa
    Double                gTimesSinDeltaKpa
    Double                gDivSinDeltaKpa
    java.math.BigDecimal  gapMicrometers
    Double                viscosityPaS
    java.math.BigDecimal  torqueMicronM
    Double                strainPercent
    java.math.BigDecimal  tempC
    java.math.BigDecimal  timeSec
    Double                diameterMm
    Date                  dateUpdated

    static constraints = {
        mnroadId          (maxSize: 11, blank: false)
        testingLab        (maxSize: 36, blank: true)
        testDate          (nullable: true)
        description       (maxSize: 24, blank: false)
        condition         (maxSize: 12, blank: false)
        freqRadS          (nullable: true, maxSize: 22)
        deltaDegrees      (nullable: true, maxSize: 22)
        gprimePa          (nullable: true, maxSize: 22)
        gdoubleprimePa    (nullable: true, maxSize: 22)
        gstarPa           (nullable: true, maxSize: 22)
        gTimesSinDeltaKpa (nullable: true, maxSize: 22)
        gDivSinDeltaKpa   (nullable: true, maxSize: 22)
        gapMicrometers    (nullable: true, maxSize: 22)
        viscosityPaS      (nullable: true, maxSize: 22)
        torqueMicronM     (nullable: true, maxSize: 22)
        strainPercent     (nullable: false, maxSize: 22)
        tempC             (nullable: true, maxSize: 22)
        timeSec           (nullable: true, maxSize: 22)
        diameterMm        (nullable: true, maxSize: 22)
        dateUpdated       (nullable: true)
    }
    String toString() {
        return "${mnroadId},${description},${condition},${strainPercent}"
    }
}
