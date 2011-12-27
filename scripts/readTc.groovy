/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Jan 12, 2011
 * Time: 7:54:25 AM
 *
 * An example of using GPars (requires jars gpars-0.11.jar and jsr166y-070108.jar).
 */
import groovy.sql.Sql
//import org.codehaus.groovy.grails.commons.ConfigurationHolder
//import groovyx.gpars.GParsPool
//import us.mn.state.dot.mnroad.values.TcValues
import java.text.DecimalFormat

/*
  def sql = Sql.newInstance(ConfigurationHolder.config.dataSource.url as String,
            ConfigurationHolder.config.dataSource.username as String,
            ConfigurationHolder.config.dataSource.password as String,
            ConfigurationHolder.config.dataSource.driverClassName)
*/
def readTc = {
  def url="jdbc:oracle:thin:@MRL2K3dev.ad.dot.state.mn.us:1521:DEV11"
  def username="mnr"
  def password="pass"
  def driverClassName = "oracle.jdbc.OracleDriver"
  def sql = Sql.newInstance(url as String,
            username as String,
            password as String,
            driverClassName)

  def nrows = 0
  println "Started 'readTc'"
//  def rm = [:]
  TreeSet rm = new TreeSet()
  def then = new Date()
  def out = new File('/temp/temps.csv')
  def fmt = '0000000.00'
  try {
//    def years = (1993..2010)
    def years = (1993..1994)
//    GParsPool.withPool {
      println "Processing ${years.size()} years."
      years.eachParallel { year ->
        def yr = year <2010?"_${year}":""
        def yq = "SELECT CELL, SEQ, TO_CHAR(MIN(DAY),'YYYY-MM-DD') FR_DATE, TO_CHAR(MAX(DAY),'YYYY-MM-DD') TO_DATE, MIN(VALUE) MIN_TEMP, MAX(VALUE) MAX_TEMP FROM MNR.TC_VALUES${yr} GROUP BY CELL,SEQ ORDER BY CELL,SEQ"
        sql.rows(yq.toString()).each { f ->
          def cs = "${f.CELL}".padLeft(3,'0')
          def ss = "${f.SEQ}".padLeft(3,'0')
          def key = "${year}C${cs}S${ss}"
          def fmin = roundTwo(f.MIN_TEMP, fmt)
          def fmax = roundTwo(f.MAX_TEMP, fmt)
          if (!fmin || !fmax)
          	println " where cell=${f.CELL} and seq=${f.SEQ} and to_char(day,'yyyy')='${year}'" 
          rm << key+",${fmin},${fmax},${f.FR_DATE},${f.TO_DATE}\n"
          nrows++
        }
      }
//    }
  } catch (Exception e) {
      println e.message
  }
def now = new Date()
out.append "CELLSEQ,MIN_TEMP,MAX_TEMP,FR_DATE,TO_DATE\n"
rm.each {
  out << it
}
"${nrows} in ${(now.getTime()-then.getTime())/1000.0} seconds elapsed time."
}.call()

/*
Via SQL
SELECT TO_CHAR(DAY,'YYYY')||'C'||LPAD(CELL,3,'0')||'S'||LPAD(SEQ,3,'0') CELLSEQ
, TO_CHAR(ROUND(MIN(VALUE),2),'0999999.90') MIN_TEMP
, TO_CHAR(ROUND(MAX(VALUE),2),'0999999.90') MAX_TEMP
, TO_CHAR(MIN(DAY),'YYYY-MM-DD') FR_DATE
, TO_CHAR(MAX(DAY),'YYYY-MM-DD') TO_DATE
 FROM MNR.TC_VALUES_ALL
 GROUP BY TO_CHAR(DAY,'YYYY'),CELL,SEQ ORDER BY TO_CHAR(DAY,'YYYY'),CELL,SEQ
Rows read: 18,261, Elapsed time (seconds) - SQL query: 898.622

Via this script:
Processing 18 years.
===> 16929 in 496.557 seconds elapsed time.

SELECT TO_CHAR(DAY,'YYYY')||'C'||LPAD(CELL,3,'0')||'S'||LPAD(SEQ,3,'0') CELLSEQ
, TO_CHAR(ROUND(MIN(VALUE),2),'0999999.90') MIN_TEMP
, TO_CHAR(ROUND(MAX(VALUE),2),'0999999.90') MAX_TEMP
, TO_CHAR(MIN(DAY),'YYYY-MM-DD') FR_DATE
, TO_CHAR(MAX(DAY),'YYYY-MM-DD') TO_DATE
 FROM MNR.TC_VALUES_ALL
-- MNR.TC_VALUES_2001 WHERE CELL=32 AND SEQ=116
 GROUP BY TO_CHAR(DAY,'YYYY'),CELL,SEQ ORDER BY TO_CHAR(DAY,'YYYY'),CELL,SEQ
 */


def String roundTwo(BigDecimal d, String fmt) {
    if (d == null)
        return ""
    Double dbl = ((int)Math.round(d * 100)) / 100.0 // truncates
    def pmone = (dbl < 0.0)?-1.0:1.0
    def mn = new DecimalFormat(fmt).format(dbl * pmone)
    return (pmone < 0.0)?"-"+mn:mn
    }
