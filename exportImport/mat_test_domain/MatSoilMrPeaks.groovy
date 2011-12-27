/**
 * The MatSoilMrPeaks entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
package us.mn.state.dot.mnroad.materials

class MatSoilMrPeaks {
    static mapping = {
         table 'MAT_SOIL_MR_PEAKS'
         // version is set to false, because this isn't available by default for legacy databases
         version false
    }
    String xlsFileName
    java.math.BigDecimal confPressPsi
    Double internalAxialLoadLbs
    Double internalLvdt1Microinch
    Double internalLvdt2Microinch
    Double internalLvdt3Microinch
    Double deviatorStressPsi
    Double bulkStressPsi
    Double strain
    Double mrPsi
    Double deviatorStressCalc
    Double bulkStressCalc
    Double strainCalc
    Double mrPsiCalc
    Double lvdt12
    Double lvdt13
    Double lvdt23
    Double v
    Double vMaxomin
    Double vMaxominCorr

    static constraints = {
        xlsFileName(size: 0..12)
        confPressPsi(nullable: true, scale: 4)
        internalAxialLoadLbs(nullable: true, scale: 6)
        internalLvdt1Microinch(nullable: true, scale: 6)
        internalLvdt2Microinch(nullable: true, scale: 6)
        internalLvdt3Microinch(nullable: true, scale: 6)
        deviatorStressPsi(nullable: true, scale: 6)
        bulkStressPsi(nullable: true, scale: 6)
        strain(nullable: true, scale: 6)
        mrPsi(nullable: true, scale: 6)
        deviatorStressCalc(nullable: true, scale: 6)
        bulkStressCalc(nullable: true, scale: 6)
        strainCalc(nullable: true, scale: 6)
        mrPsiCalc(nullable: true, scale: 6)
        lvdt12(nullable: true, scale: 6)
        lvdt13(nullable: true, scale: 6)
        lvdt23(nullable: true, scale: 6)
        v(nullable: true, scale: 6)
        vMaxomin(nullable: true, scale: 6)
        vMaxominCorr(nullable: true, scale: 6)
    }
    String toString() {
        return "${}" 
    }
}
