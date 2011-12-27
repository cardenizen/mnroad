package us.mn.state.dot.mnroad.materials

class MatHmaAging implements Serializable {
  static mapping = {
       table 'MAT_HMA_AGING'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       id composite:['mnroadId','samId']
       columns {
         sieveNum4   column:'SIEVE_NUM_4'
         sieveNum8   column:'SIEVE_NUM_8'
         sieveNum10  column:'SIEVE_NUM_10'
         sieveNum16  column:'SIEVE_NUM_16'
         sieveNum40  column:'SIEVE_NUM_40'
         sieveNum50  column:'SIEVE_NUM_50'
         sieveNum100 column:'SIEVE_NUM_100'
         sieveNum200 column:'SIEVE_NUM_200'
         sieve58inch column:'SIEVE_5_8INCH'
         sieve12inch column:'SIEVE_1_2INCH'
         sieve38inch column:'SIEVE_3_8INCH'
         highPgTempC column:'HIGH_PG_TEMP_C'
       }
  }
  String id
  String                mnroadId
  String                testingLab
  Date                  testDate
  String                samId
  String                fieldId
  String                hmaLift
  java.math.BigDecimal  depthFromTopIn
  java.math.BigDecimal  sampleThicknessIn
  java.math.BigDecimal  highPgTempC
  java.math.BigDecimal  acPercent
  String                comments
  java.math.BigDecimal  sieve58inch
  java.math.BigDecimal  sieve12inch
  java.math.BigDecimal  sieve38inch
  java.math.BigDecimal  sieveNum4
  java.math.BigDecimal  sieveNum8
  java.math.BigDecimal  sieveNum10
  java.math.BigDecimal  sieveNum16
  java.math.BigDecimal  sieveNum40
  java.math.BigDecimal  sieveNum50
  java.math.BigDecimal  sieveNum100
  java.math.BigDecimal  sieveNum200
  Date                  dateUpdated

  static constraints = {
      mnroadId          (maxSize: 11, blank: false)
      testingLab        (maxSize: 36, blank: true)
      testDate          (nullable: true)
      samId             (maxSize: 16, blank: false)
      fieldId           (maxSize: 60, blank: true)
      hmaLift           (maxSize: 12, blank: true)
      depthFromTopIn    (nullable: true, scale: 3)
      sampleThicknessIn (nullable: true, scale: 3)
      highPgTempC       (nullable: true, scale: 2)
      acPercent         (nullable: true, scale: 2)
      comments          (maxSize: 60, blank: true)
      sieve58inch       (nullable: true, scale: 2)
      sieve12inch       (nullable: true, scale: 2)
      sieve38inch       (nullable: true, scale: 2)
      sieveNum4         (nullable: true, scale: 2)
      sieveNum8         (nullable: true, scale: 2)
      sieveNum10        (nullable: true, scale: 2)
      sieveNum16        (nullable: true, scale: 2)
      sieveNum40        (nullable: true, scale: 2)
      sieveNum50        (nullable: true, scale: 2)
      sieveNum100       (nullable: true, scale: 2)
      sieveNum200       (nullable: true, scale: 2)
      dateUpdated       (nullable: true)
  }
  String toString() {
      return "${mnroadId},${samId}"
  }
}
