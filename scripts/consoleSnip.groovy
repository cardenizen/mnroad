// Groovy Code here

// Implicit variables include:
//     ctx: the Spring application context
//     grailsApplication: the Grails application
//     config: the Grails configuration
//     request: the HTTP request
//     session: the HTTP session

import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.Lane
import us.mn.state.dot.mnroad.Layer

def cellList = (Cell.createCriteria()).list(){
   order('cellNumber','asc')
   order('constructionEndedDate','asc')
}
def selectedLane = '%Shldr'
cellList.each { aCell ->
   def lnList = Lane.createCriteria().list(){
      not {
         like ("name", selectedLane )
      }
      cell {
        eq ("cellNumber", aCell.cellNumber)
      }
      order('name','asc')
   }
   lnList.each { aLane ->
      aLane.layers.each { aLayer ->
      println "$aCell.cellNumber $aLane, $aLayer"
      }
   }
} 
   
""

import us.mn.state.dot.mnroad.MaterialSample
def dbMetadataService =  ctx.getBean("dbMetadataService")  // used in the console
def matSample = MaterialSample.get(2588)
dbMetadataService.checkNullable(matSample).each {
   println it
}

import us.mn.state.dot.mnroad.MrUtils

    def fqfn = { base, dir ->
      (!base.endsWith(File.separator))?(base + File.separator + dir):(base + dir)
    }
    def writeFile = {ol, fn ->
      println "Writing ${ol.size()} lines to ${fn}."
      File ddf = new File(fn)
      FileWriter ddfw = new FileWriter(ddf, true)
      ol.each {
        ddfw.append("${it}\n")
      }
      ddfw.close()
    }

def exportDataService =  ctx.getBean("exportDataService")  // used in the console
def dnMap = exportDataService.designNumMap()
def typeFolders = exportDataService.createDesignDetailsByTypeFolders(grailsApplication.config)
if (typeFolders)    {
  grailsApplication.config.pavementTypes.keySet().each { shortName ->
  def data = exportDataService.dataProductSensors(shortName)
        def cols = data["cols"]
        long lastid = 0
        int cell = 0
        def ol = []
        def nExported = 0
        data.rows.each { row ->
          def line = []
          row.keySet().each { key ->
            line << MrUtils.formatSqlValueForCsv(row.get(key), cols.get(key))
          }
          if (row.CELL_ID && row.CELL_ID.longValue() != lastid) {
            if (ol.size() > 0) {
              def fn = fqfn(MrUtils.mkdir(typeFolders.get(shortName), "Design Details")
                      ,"${shortName} ${cell} Design ${dnMap.get(lastid)}.csv")
              writeFile(ol, fn)
              nExported++
              ol.clear()
            }
            ol << cols.keySet().join(",")
          }
          ol << line.join(",")
          if (row.CELL) {
             cell = row.CELL.intValue()
          }
          if (row.CELL_ID) {
            lastid = row.CELL_ID.longValue()
          }
        }
        if (ol.size() > 0) {
          def fn = fqfn(MrUtils.mkdir(typeFolders.get(shortName), "Design Details")
                  ,"${shortName} ${cell} Design ${dnMap.get(lastid)}.csv")
          writeFile(ol, fn)
          nExported++
        }

        println "${nExported} ${shortName} cells exported."
        nExported = 0
  }
}
/*
import groovy.sql.Sql
import us.mn.state.dot.mnroad.MrUtils

def dataSource = ctx.dataSource
def sql = Sql.newInstance(dataSource)
def typeMap = [:]
def rows = []
def q = """
SELECT MC.CELL CELL_NUMBER
 ,C.NAME CELL_NAME
 ,NVL(TO_CHAR(C.DESIGN_LIFE),'') DESIGN_LIFE_YRS
 ,F.NAME FACILITY
 ,R.DESCRIPTION ROAD_SECTION
 ,TO_CHAR(MC.FROM_DATE,'yyyy-mm-dd') BUILT_DATE
 ,TO_CHAR(MC.TO_DATE,'yyyy-mm-dd') DEMOLISHED_DATE
 ,C.START_STATION START_STATION_FT
 ,C.END_STATION END_STATION_FT
 ,C.DRAINAGE_SYSTEM DRAINAGE_SYSTEM
 ,C.SHOULDER_TYPE SHOULDER_TYPE
 ,LN.NAME LANE_NAME
 ,LN.WIDTH LANE_WIDTH_FT
 ,LN.OFFSET_REF LANE_OFFSET_REF
 ,L.LAYER_NUM LAYER_NUM
 ,TO_CHAR(L.CONSTRUCT_START_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_START_DATE
 ,TO_CHAR(L.CONSTRUCT_END_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_END_DATE
 ,L.THICKNESS/25.4 LAYER_THICKNESS_FT
 ,NVL(M.MN_DOT_SPEC_NUMBER,'n/a') SPEC_NUMBER
 ,M.BASIC_MATERIAL || ' - ' || M.DESCRIPTION  MATERIAL
 ,MC.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
 ,M.ID MATERIAL_ID
 FROM MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
 JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
 WHERE SUBSTR(C.CLASS,24)='AggCell'
 ORDER BY CELL,FROM_DATE
"""
def arglist = []
def onRow = {
   rows << it.toRowResult()
   }
def onFirstRow = {meta ->
   (1..meta.columnCount).each {
     typeMap.put(meta.getColumnLabel(it),meta.getColumnTypeName(it))
   }
}
sql.eachRow(q,arglist,onFirstRow,onRow)
def colNames = typeMap.keySet().toList()
def lines = []
lines << colNames.join(",")
rows.each { row ->
   def vals = []
   row.keySet().eachWithIndex { key, i ->
      vals << MrUtils.formatSqlValueForCsv(row[key], typeMap[key]).trim()
   }
   lines << "${vals.join(",")}"
}
*/
/*
// Groovy Code here

// Implicit variables include:
//     ctx: the Spring application context
//     grailsApplication: the Grails application
//     config: the Grails configuration
//     request: the HTTP request
//     session: the HTTP session
import groovy.sql.Sql

def dataSource = ctx.dataSource
def sql = Sql.newInstance(dataSource)

def pref = ""
def q = """
SELECT id,CELL,LANE,FROM_DATE,TO_DATE,LANE_ID,
'DISTRESS_AC' DATA_TABLE FROM (SELECT d.id,LD.CELL_ID,LD.LANE_ID,LD.FROM_DATE,LD.TO_DATE,D.CELL,D.LANE FROM
MNR.DISTRESS_AC D JOIN MNR.LANE_DESIGN LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND TO_CHAR(D.DAY,'yyyy-mm-dd') BETWEEN FROM_DATE AND TO_DATE
) A
"""
def onRow = {
   def iq = pref + "$it.ID,1,'carr1den',null,sysdate,null,$it.LANE_ID);"
   sql.execute iq
   assert sql.updateCount == 1
   }
def onFirstRow = {meta ->
   pref = "insert into mnr.distress (ID,VERSION,CREATED_BY,LAST_UPDATED_BY,LAST_UPDATED,DATE_CREATED,LANE_ID) values ("
}
sql.eachRow(q,[],onFirstRow,onRow)
*/