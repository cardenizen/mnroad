package us.mn.state.dot.mnroad.materials

class MatConcModPoissonResults implements Serializable {

  static mapping = {
       table 'MAT_CONC_MOD_POISSON_RESULTS'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','testAgeDays']
  }
  String id   // required for the composite key to avoid "Fail to convert to internal representation"
  String                mnroadId
  Date                  dateTested
  Integer               testAgeDays
  Integer               staticModulusElasticityPsi
  java.math.BigDecimal  poissonRatio
  Integer               avgStaticModElasticityPsi
  java.math.BigDecimal  averagePoissonRatio
  java.math.BigDecimal  diameterIn
  java.math.BigDecimal  cappedLengthIn
  String                comments
  Integer               dynamicModulusElasticityPsi

  static constraints = {
      mnroadId                    (maxSize: 11, blank: false)
      dateTested                  (nullable: true)
      testAgeDays                 (nullable: false, maxSize: 4)
      staticModulusElasticityPsi  (nullable: true, maxSize: 8)
      poissonRatio                (nullable: true, scale: 2)
      avgStaticModElasticityPsi   (nullable: true, maxSize: 8)
      averagePoissonRatio         (nullable: true, scale: 2)
      diameterIn                  (nullable: true, scale: 3)
      cappedLengthIn              (nullable: true, scale: 3)
      comments                    (nullable: true, maxSize: 80)
      dynamicModulusElasticityPsi (nullable: true)
  }
  String toString() {
      return "${mnroadId},${testAgeDays}" 
  }
}
