/**
 * The MatConcreteMixDesigns entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class MatConcreteMixDesigns {
    static mapping = {
         table 'MAT_CONCRETE_MIX_DESIGNS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    Integer cell
    String mixDesignations
    String portlandCement
    String flyAsh
    String otherCementitious
    String water
    java.math.BigDecimal waterToCementitiousRatio
    String fineAggregate
    String otherFineAggregate
    String coarseAggregate1AndHalfIn
    String coarseAgg3QuartersInPlus
    String coarseAgg3QuartersInMinus
    String otherCoarseAggregate
    String airContent
    String admixture1
    String admixture2

    static constraints = {
        cell()
        mixDesignations(size: 0..45)
        portlandCement(size: 0..45)
        flyAsh(size: 0..45)
        otherCementitious(size: 0..45)
        water(size: 0..15)
        waterToCementitiousRatio(nullable: true, scale: 2)
        fineAggregate(size: 0..45)
        otherFineAggregate(size: 0..50)
        coarseAggregate1AndHalfIn(size: 0..100)
        coarseAgg3QuartersInPlus(size: 0..100)
        coarseAgg3QuartersInMinus(size: 0..100)
        otherCoarseAggregate(size: 0..100)
        airContent(size: 0..15)
        admixture1(size: 0..100)
        admixture2(size: 0..100)
    }
    String toString() {
        return "${}" 
    }
}
