/**
 * The MatUnboundGradations entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatUnboundGradations implements Serializable {
    static mapping = {
      table 'MAT_UNBOUND_GRADATIONS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','labSampleId']
      columns {
        passing2In             column: 'passing_2_in'
        passing1AndHalfIn      column: 'passing_1_and_half_in'
        passing1AndQuarterIn   column: 'passing_1_and_quarter_in'
        passing1In             column: 'passing_1_in'
        passing3QuartersIn     column: 'passing_3_quarters_in'
        passing5EighthsIn      column: 'passing_5_eighths_in'
        passingHalfIn          column: 'passing_half_in'
        passing3EightsIn       column: 'passing_3_eights_in'
        passingNum4            column: 'passing_num_4'
        passingNum8            column: 'passing_num_8'
        passingNum10           column: 'passing_num_10'
        passingNum16           column: 'passing_num_16'
        passingNum20           column: 'passing_num_20'
        passingNum30           column: 'passing_num_30'
        passingNum40           column: 'passing_num_40'
        passingNum50           column: 'passing_num_50'
        passingNum60           column: 'passing_num_60'
        passingNum100          column: 'passing_num_100'
        passingNum200          column: 'passing_num_200'
        passing2Min            column: 'passing_2_min'
        passing5Min            column: 'passing_5_min'
        passing15Min           column: 'passing_15_min'
        passing30Min           column: 'passing_30_min'
        passing60Min           column: 'passing_60_min'
        passing250Min          column: 'passing_250_min'
        passing24Hr            column: 'passing_24_hr'
        mmDiam2Min             column: 'mm_diam_2_min'
        mmDiam5Min             column: 'mm_diam_5_min'
        mmDiam15Min            column: 'mm_diam_15_min'
        mmDiam30Min            column: 'mm_diam_30_min'
        mmDiam60Min            column: 'mm_diam_60_min'
        mmDiam250Min           column: 'mm_diam_250_min'
        mmDiam24Hr             column: 'mm_diam_24_hr'
      }
    }
    String                mnroadId
    Date                  testDate
    String                lab
    String                labSampleId
    java.math.BigDecimal  passing2In
    java.math.BigDecimal  passing1AndHalfIn
    java.math.BigDecimal  passing1AndQuarterIn
    java.math.BigDecimal  passing1In
    java.math.BigDecimal  passing3QuartersIn
    java.math.BigDecimal  passing5EighthsIn
    java.math.BigDecimal  passingHalfIn
    java.math.BigDecimal  passing3EightsIn
    java.math.BigDecimal  passingNum4
    java.math.BigDecimal  passingNum8
    java.math.BigDecimal  passingNum10
    java.math.BigDecimal  passingNum16
    java.math.BigDecimal  passingNum20
    java.math.BigDecimal  passingNum30
    java.math.BigDecimal  passingNum40
    java.math.BigDecimal  passingNum50
    java.math.BigDecimal  passingNum60
    java.math.BigDecimal  passingNum100
    java.math.BigDecimal  passingNum200
    java.math.BigDecimal  passing2Min
    java.math.BigDecimal  passing5Min
    java.math.BigDecimal  passing15Min
    java.math.BigDecimal  passing30Min
    java.math.BigDecimal  passing60Min
    java.math.BigDecimal  passing250Min
    java.math.BigDecimal  passing24Hr
    java.math.BigDecimal  mmDiam2Min
    java.math.BigDecimal  mmDiam5Min
    java.math.BigDecimal  mmDiam15Min
    java.math.BigDecimal  mmDiam30Min
    java.math.BigDecimal  mmDiam60Min
    java.math.BigDecimal  mmDiam250Min
    java.math.BigDecimal  mmDiam24Hr
    Date dateUpdated

    static constraints = {
        mnroadId              (maxSize: 12)
        testDate              (nullable: true)
        lab                   (size: 0..40)
        labSampleId           (size: 0..15)
        passing2In            (nullable: true, scale: 1)
        passing1AndHalfIn     (nullable: true, scale: 1)
        passing1AndQuarterIn  (nullable: true, scale: 1)
        passing1In            (nullable: true, scale: 1)
        passing3QuartersIn    (nullable: true, scale: 1)
        passing5EighthsIn     (nullable: true, scale: 1)
        passingHalfIn         (nullable: true, scale: 1)
        passing3EightsIn      (nullable: true, scale: 1)
        passingNum4           (nullable: true, scale: 1)
        passingNum8           (nullable: true, scale: 1)
        passingNum10          (nullable: true, scale: 1)
        passingNum16          (nullable: true, scale: 1)
        passingNum20          (nullable: true, scale: 1)
        passingNum30          (nullable: true, scale: 1)
        passingNum40          (nullable: true, scale: 1)
        passingNum50          (nullable: true, scale: 1)
        passingNum60          (nullable: true, scale: 1)
        passingNum100         (nullable: true, scale: 1)
        passingNum200         (nullable: true, scale: 1)
        passing2Min           (nullable: true, scale: 1)
        passing5Min           (nullable: true, scale: 1)
        passing15Min          (nullable: true, scale: 1)
        passing30Min          (nullable: true, scale: 1)
        passing60Min          (nullable: true, scale: 1)
        passing250Min         (nullable: true, scale: 1)
        passing24Hr           (nullable: true, scale: 1)
        mmDiam2Min            (nullable: true, scale: 4)
        mmDiam5Min            (nullable: true, scale: 4)
        mmDiam15Min           (nullable: true, scale: 4)
        mmDiam30Min           (nullable: true, scale: 4)
        mmDiam60Min           (nullable: true, scale: 4)
        mmDiam250Min          (nullable: true, scale: 4)
        mmDiam24Hr            (nullable: true, scale: 4)
        dateUpdated           (nullable: true)
    }

    String toString() {
        return "${mnroadId},${labSampleId}"
    }

}
