/**
 * The MatHmaMixTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatHmaMixTests implements Serializable {
    static mapping = {
      table 'MAT_HMA_MIX_TESTS'
      // version is set to false, because this isn't available by default for legacy databases
      version false
      id composite:['mnroadId','testDate','comments']
      columns {
        lastUpdated   column: 'date_updated'
      }
    }
    String      id
    String      mnroadId
    String      testingLab
    Date        testDate
    Integer     doubleBlows
    Double      bulkSpGravity
    Double      riceSpGravity
    Double      airVoidsPercent
    Double      vmaPercent
    Integer     stability
    Double      flow
    Double      extractedAcPercent
    Double      incineratorAcPercent
    Integer     tensileStrengthRatio
    String      fineAggAngularity
    String      coarseAggAngularity
    Double      mixMoisturePercent
    String      comments
    Date        lastUpdated


    static constraints = {
      mnroadId             (maxSize: 11, blank: false)
      testingLab           (maxSize: 40, blank: false)
      testDate             (nullable: false)
      doubleBlows          (nullable: true, maxSize:2)
      bulkSpGravity        (nullable: true, scale: 3)
      riceSpGravity        (nullable: true, scale: 3)
      airVoidsPercent      (nullable: true, scale: 1)
      vmaPercent           (nullable: true, scale: 1)
      stability            (nullable: true, maxSize:4)
      flow                 (nullable: true, scale: 1)
      extractedAcPercent   (nullable: true, scale: 1)
      incineratorAcPercent (nullable: true, scale: 1)
      tensileStrengthRatio (nullable: true, maxSize:3)
      fineAggAngularity    (maxSize:10, blank: true)
      coarseAggAngularity  (maxSize:10, blank: true)
      mixMoisturePercent   (nullable: true, scale: 2)
      comments             (maxSize:100, blank: false)
    }
    String toString() {
        return "${mnroadId},${testDate},${comments}"
    }
}
