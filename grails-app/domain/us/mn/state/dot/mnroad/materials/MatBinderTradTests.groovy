/**
 * The MatBinderTradTests entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatBinderTradTests implements Serializable {
    static mapping = {
         table 'MAT_BINDER_TRAD_TESTS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
      id composite:['mnroadId','labNo']
      columns {
        weightGallon60f column:'WEIGHT_GALLON_60F'
        pen77f          column:'PEN_77F'
        pen40f          column:'PEN_40F'
        pen55f          column:'PEN_55F'
        pen90f          column:'PEN_90F'
        visc140f        column:'VISC_140F'
        visc275f        column:'VISC_275F'
        ductility77f    column:'DUCTILITY_77F'
        tfoDuctRes77f   column:'TFO_DUCT_RES_77F'
        tfoDuctRes140f  column:'TFO_DUCT_RES_140F'
        tfoDuctRes275f  column:'TFO_DUCT_RES_275F'
      }
    }
    String id   // required for the composite key to avoid "Fail to convert to internal representation"
    String                mnroadId
    String                labNo
    String                description
    Date                  testDate
    String                testingLab
    java.math.BigDecimal  weightGallon60f
    Integer               pen77f
    Integer               pen40f
    Integer               pen55f
    Integer               pen90f
    Integer               visc140f
    java.math.BigDecimal  visc275f
    Integer               ductility77f
    Integer               softeningPoint
    java.math.BigDecimal  tfoTestPercentLoss
    java.math.BigDecimal  tfoPenResPercentOriginal
    Integer               tfoDuctRes77f
    Integer               tfoDuctRes140f
    Integer               tfoDuctRes275f
    Integer               tfoSofteningPoint
    Date                  dateUpdated

    static constraints = {
        mnroadId                (maxSize: 10, blank: false)
        labNo                   (maxSize: 12, blank: false)
        description             (maxSize: 50, blank: true)
        testDate                (nullable: true)
        testingLab              (maxSize: 20, blank: true)
        weightGallon60f         (nullable: true, scale: 3)
        pen77f                  (nullable: true, maxSize: 4)
        pen40f                  (nullable: true, maxSize: 4)
        pen55f                  (nullable: true, maxSize: 4)
        pen90f                  (nullable: true, maxSize: 4)
        visc140f                (nullable: true, maxSize: 5)
        visc275f                (nullable: true, scale: 1)
        ductility77f            (nullable: true, maxSize: 4)
        softeningPoint          (nullable: true, maxSize: 4)
        tfoTestPercentLoss      (nullable: true, scale: 2)
        tfoPenResPercentOriginal(nullable: true, scale: 2)
        tfoDuctRes77f           (nullable: true, maxSize: 4)
        tfoDuctRes140f          (nullable: true, maxSize: 6)
        tfoDuctRes275f          (nullable: true, maxSize: 6)
        tfoSofteningPoint       (nullable: true, maxSize: 4)
        dateUpdated             (nullable: true)
    }
    String toString() {
        return "${mnroadId},${labNo}"
    }
}
