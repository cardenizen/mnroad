import java.util.zip.ZipOutputStream
import org.apache.tools.zip.ZipEntry
import groovy.sql.Sql
import java.sql.SQLException

import org.apache.poi.hssf.usermodel.HSSFWorkbook
private HSSFWorkbook wb;
def ext = "xls"

//import org.apache.poi.xssf.usermodel.XSSFWorkbook
//private XSSFWorkbook wb;
//def ext = "xlsx"

// Assumes 1) working directory is project base
// and 2) this is a script in a Grails application
def cfu = new File("grails-app/conf/DataSource.groovy").toURL()
def config = new ConfigSlurper("development").parse(cfu)
def sql = Sql.newInstance(config.dataSource.url as String,
            config.dataSource.username as String,
            config.dataSource.password as String,
            config.dataSource.driverClassName as String)

private File f;
private FileOutputStream fos;
boolean okToWrite = false

def md = [:]
FileWriter fw
def schema="MNR"
def dtbls = ["DISTRESS_AC","DISTRESS_AGG_SURVEY_SEMI","DISTRESS_ALPS_DATA","DISTRESS_ALPS_RESULTS_RUT","DISTRESS_CIRCULR_TEXTR_METER","DISTRESS_CUPPING","DISTRESS_FRICTION_DFT","DISTRESS_FRICTION_GRIP","DISTRESS_FRICTION_TRAILER","DISTRESS_JPCC","DISTRESS_LANE_SHOULDER_DROPOFF","DISTRESS_LIGHTWEIGHT_DEFLECT","DISTRESS_NUCLEAR_DENSITY","DISTRESS_OBSI_DATA","DISTRESS_OBSI_SUMMARY","DISTRESS_PCC_FAULTS","DISTRESS_PROCEQ","DISTRESS_RIDE_LISA","DISTRESS_RIDE_PATHWAYS","DISTRESS_RIDE_PAVETECH","DISTRESS_RUTTING_DIPSTICK","DISTRESS_RUTTING_DIPSTICK_PINS","DISTRESS_RUTTING_DIPSTICK_RAW","DISTRESS_RUTTING_STRAIGHT_EDGE","DISTRESS_SAND_PATCH","DISTRESS_SCHMIDT_HAMMER","DISTRESS_SOUND_ABSORPTION","DISTRESS_WATER_PERMEABILITY"]
def hdr = ""
def cn = [:]
def ct = [:]
int nrows

def writeRow={ row ->
  def rr = row.toRowResult()
  def r = ""
  def a = []
  rr.eachWithIndex { it,i ->
    String v = ""
    String o = rr[i]
    if (o && ct.get(i+1) == "DATE") {
      v = o.substring(0,10)
    } else if (o && o.class.name == "java.lang.String") {
      int idx = o.indexOf("\"")
      if (idx>=0) {
        String s1 = o.substring(0,idx)
        String s2 = o.substring(idx)
        char [] na = new char[o.size()+3]
        na[0] = '"'[0]
        s1.eachWithIndex{c,j->
          na[j+1]= c
        }
        na[idx+1] = '"'[0]
        s2.eachWithIndex{c,ii->
          na[idx+2+ii]= c
        }
        na[o.size()+2] = '"'[0]
        v = String.valueOf(na)
      } else {
        v = "\"${o}\""
      }
    } else {
      v = "${o}"
    }
    if (i<rr.size()-1) {
      a.add(v)
    }
    r += v+(i<rr.size()-2)?",":""
  }
  fw.append "${a.join(',')}\n"
  nrows++
}

def writeHeader = { meta ->
  hdr = ""
  cn.clear()
  ct.clear()
  (1..meta.columnCount-1).each {
      hdr += "${meta.getColumnLabel(it)}".padRight(meta.getPrecision(it))
      cn.put(it,meta.getColumnLabel(it))
      ct.put(it,meta.getColumnTypeName(it))
     }
  fw.append("${cn.keySet().collect {cn.get(it)}.join(",")}\n")
 }

try {
 
  dtbls.each { tableName ->
    def sq = "SELECT count(*) ROW_COUNT FROM ${schema}.${tableName}"
    def numrows= sql.firstRow(sq,[]).ROW_COUNT
    nrows = 0
    println "${tableName} - ${numrows} rows."
    md.put(tableName,["rowCount":numrows])
    def fn = "data/${tableName}.csv"
    if (numrows< 5000000) {
      try {
        fw = new FileWriter(fn);
        def batchSize=10000
        long numBatches = ((int)(numrows/batchSize)) + 1
        int batchNum = 1
        int fromOffset = 1
        while (batchNum <= numBatches)  {
          println "Retrieving batch ${batchNum} of ${numBatches}."
          def sqs = "select * from ( select a.*, ROWNUM rnum from (select * from ${schema}.${tableName} ) a where ROWNUM <= ${fromOffset+batchSize} ) where rnum  >= ${fromOffset+1}"
          if (batchNum == 1)
            sql.eachRow(sqs.toString(),writeHeader,writeRow)
          else {
            sql.eachRow(sqs.toString()) {
              writeRow(it) 
            }
          }
          fromOffset += batchSize
          println "Batch ${batchNum} of ${numBatches} processed, ${nrows} rows written."
          batchNum++
        }
        if (fw) {
          fw.close()
          fw = null
        }
        println "${nrows} plus one header written to ${fn}."
      } catch (Exception e) {
         println "${sq}: ${e.message}"
      } finally {
        if (fw) {
          fw.close()
          fw=null
        }
      }
    }
  }

//  def schema = "MNR"
//  def tableFilter = "MAT_%"
//  def tmd = tableMetaData(sql,schema,tableFilter)
//  tmd.keySet().each { tableName ->
//    okToWrite = false
//    def sq = "SELECT count(*) ROW_COUNT FROM ${schema}.${tableName}"
//    def sc = sql.firstRow(sq,[]).ROW_COUNT
//    println "${tableName} - ${sc} rows."
//    if (sc > 0 && sc < 20000) {
//      def tn = "${tableName}.${ext}"
//      f = new File(tn);
//      println "Writing ${sc} rows to ${tn}"
////      wb = new XSSFWorkbook();
//      wb = new HSSFWorkbook();
//      def sheet = wb.createSheet(tableName);
//      int rowNum = 0;
//      def ssrow = sheet.createRow(rowNum++);
//      // Write header row
//      ssrow.createCell(0).setCellValue("OP") // Used to specify the C/U/D operation
//      tmd[tableName].eachWithIndex { col,i ->
//        ssrow.createCell(i+1).setCellValue(col.name)
//      }
//      sq = "select * from ${schema}.${tableName}"
//      // Write data rows
//      sql.eachRow(sq.toString()) { row ->
//        def val = ""
//        ssrow = sheet.createRow(rowNum++);
//        ssrow.createCell(0).setCellValue("")
//        tmd[tableName].eachWithIndex { col,i ->
//          def fv = SqlDataFormat.formatSqlValue(row[col.name], col.type_name, true)
//          ssrow.createCell(i+1).setCellValue(fv)
//          if (rowNum-1 == sc)
//            sheet.autoSizeColumn(i+1)
//        }
//      }
//      try {
//        fos = new FileOutputStream(f);
////
////        File zf = new File("${tableName}.zip");
////        zfos = new FileOutputStream(zf);
////        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(zfos));
////        zos.putNextEntry(new ZipEntry("${tableName}.${ext}"));
////        byte[] b = wb.getBytes()
////        zos.write(b,0,b.length)
////        zos.flush()
////        zos.close()
//
//        wb.write(fos);
//        fos.close();
//        okToWrite = true
//        println "${rowNum-1} rows written to ${f.name}."
//      }catch(IOException ioe ){
//          ioe.printStackTrace();
//      }
//    }
//  }
} catch (SQLException e) {
    e.printStackTrace();
}
//finally {
//  if (okToWrite)
//    writeBook(wb, fos);
//}

//def Map tableMetaData(def sql, String schema, def tableFilter) {
//  def tv = getTablesAndViews(sql, schema, tableFilter)
//  def rc = [:]
//  for (tableName in tv.tables) {
//    if (tableName == "MAT_BINDER_ABCD_TEST") {
//      rc.put(tableName, getTableColumns(sql, schema, tableName))
//    }
//  }
//  println rc
//  return rc
//}
//
//def Map getTablesAndViews(def sql, String schema, String nameFilter) {
//  def rc = [:]
//  def md = sql.getConnection().getMetaData()
//  def resultSet = md.getTables (null, schema, nameFilter, null)
//  def tables = []
//  def views = []
//  while(resultSet.next()) {
//     if (resultSet.getString ("TABLE_TYPE") == 'TABLE')
//        tables << resultSet.getString ("TABLE_NAME")
//     if (resultSet.getString ("TABLE_TYPE") == 'VIEW')
//        views << resultSet.getString ("TABLE_NAME")
//     }
//  resultSet.close()
//  rc.put('tables',tables)
//  rc.put('views',views)
//  return rc
//}
//
//def ArrayList getTableColumns(def sql, String schema, String tableName) {
//  def rc = []
//  def md = sql.getConnection().getMetaData()
//  def resultSet = md.getColumns (null, schema, tableName, null)
//  while(resultSet.next()) {
//      def cm = [:]
//      cm.put("name",      resultSet.getString ("COLUMN_NAME"))
//      cm.put("type_id",   resultSet.getInt ("DATA_TYPE"))
//      cm.put("type_name", resultSet.getString ("TYPE_NAME"))
//      cm.put("size",      resultSet.getInt ("COLUMN_SIZE"))
//      cm.put("digits",    resultSet.getInt ("DECIMAL_DIGITS"))
//      cm.put("size",      resultSet.getInt ("COLUMN_SIZE"))
//      rc << cm
//     }
//  resultSet.close()
//  return rc
//}
//
///**
// * Write the workbook out to the file system.
// * @param wb
// * @param fos
// */
//public void writeBook(def wb, FileOutputStream fos) {
//  if (fos) {
//    try {
////      if (wb.sheets.size() >0)
////        wb.write(fos);
//    }catch(IOException ioe ){
//      ioe.printStackTrace();
//    }finally {
//      try {
//        fos.close();
//      }catch(IOException ioe ){
//        ioe.printStackTrace();
//      }
//    }
//  }
//}
//
//
//      List batchOfRow(def cell, Integer fromOffset, Integer batchSize, def query, def classmd) {
//        def rc = []
////      def hibernateMetaClass = sessionFactory.getClassMetadata("us.mn.state.dot.mnroad.Sensor")
//        def sqs = "SELECT * FROM ( SELECT A.*, ROWNUM RNUM FROM (${query}) A WHERE ROWNUM <= ? ) WHERE RNUM  >= ?"
//        groovy.sql.Sql sql = new groovy.sql.Sql(dataSource)
//        sql.eachRow(sqs.toString(), [cell,cell,batchSize,fromOffset]) { row ->
//          def id = row.SENSOR_ID
//          rc.add(id)
//        }
//
//        return rc
//      }
//

