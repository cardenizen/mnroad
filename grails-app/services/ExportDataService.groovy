import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import groovy.sql.Sql
import us.mn.state.dot.SqlDataFormat
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.MrUtils
import org.apache.log4j.Logger


class ExportDataService {

  static Logger log = Logger.getLogger(ExportDataService.class.name)
  boolean transactional = true
  def sessionFactory
  def grailsApplication
  def dbMetadataService
  def dataSource

  def queryToCsvList(String q, List arglist) {
    def dm = [:]
    def sql = Sql.newInstance(dataSource)
    def typeMap = [:]
    def rows = []
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
    rows.each { row ->
       def vals = [:]
       row.keySet().eachWithIndex { key, i ->
         vals.put(key,MrUtils.formatSqlValueForCsv(row[key], typeMap[key]))
       }
      lines << vals
    }
    dm.put("hdr",colNames.join(","))
    dm.put("lines",lines)
    return dm
  }

  def queryToCsvFile(String q, List arglist, String fqfn) {
    File iddf = new File(fqfn)
    if (iddf.exists()) {
      log.info "Unable to write ${fqfn}, it already exists."
      return
    }
    FileWriter iddfw = new FileWriter(iddf, false)
    BufferedWriter bw = new BufferedWriter(iddfw)
    def sql = Sql.newInstance(dataSource)
    def rowCnt = 0
    def typeMap = [:]
    def onRow = {
      def vals = []
      it.toRowResult().keySet().eachWithIndex { key, i ->
        vals  << MrUtils.formatSqlValueForCsv(it.toRowResult()[key], typeMap[key])
      }
      bw.writeLine(vals.join(",").toString())
      rowCnt++
      if ((rowCnt%100) == 0) {
        bw.flush()
      }
    }
    def onFirstRow = {meta ->
      def colNames = []
      (1..meta.columnCount).each {
        colNames << meta.getColumnLabel(it)
        typeMap.put(meta.getColumnLabel(it),meta.getColumnTypeName(it))
      }
      bw.writeLine(colNames.join(",").toString())
    }
    sql.eachRow(q,arglist,onFirstRow,onRow)
      bw.flush()
    if (rowCnt) {
      bw.close()
    }
    else {
      bw.close()
      iddf.delete()
    }
    return rowCnt
  }

    private String exportLayers (def target, def typeFolder, def shortName) {
      def status = "${shortName}Query not found"
      def fn = "${shortName} All Cells.csv"
      def q = grailsApplication.config["${shortName}Query"]
      if (q) {
        def aq = "${q} WHERE SUBSTR(C.CLASS,24)=? ORDER BY CELL,FROM_DATE".toString()
        def argList = [shortName]
        if (grailsApplication.config["overwriteLayerFiles"]) {
          def csvList = queryToCsvList(aq, argList)
          status = writeItems(csvList, "${target} Details", typeFolder, fn)
          def dnMap = designNumMap()
          Sql sql = new groovy.sql.Sql(dataSource)
          def cq = grailsApplication.config["CellsQuery"].toString()
          sql.eachRow(cq, [shortName]) { cellidrow ->
            def lq = "${q} WHERE SUBSTR(C.CLASS,24)=? AND CELL_ID=? ORDER BY CELL,FROM_DATE".toString()
            def dn = dnMap.get(cellidrow.ID.longValue())
            fn = "${shortName} ${cellidrow.CELL} Design ${dn}.csv"
            csvList = queryToCsvList(lq, [shortName, new BigDecimal(cellidrow.ID)])
            status = writeItems(csvList, "${target} Details", typeFolder, fn)
            log.info "${status} from  cell ${cellidrow.CELL}."
          }
        } else {
          status = "${shortName} layer export suppressed by grailsApplication.config.overwriteLayerFiles: ${grailsApplication.config.overwriteLayerFiles}"
          log.info status
        }
      } else {
        log.info status
      }
      return status
    }

    private String exportSensors (def target, def typeFolder, def shortName) {
      def status = "sensorQuery not found"
      def fn = "${shortName} All Sensors.csv"
      def q = grailsApplication.config["sensorQuery"]
      if (q) {
        def aq = "${q} WHERE SUBSTR(C.CLASS,24)=? ORDER BY CELL,MODEL,SEQ".toString()
        def argList = [shortName]
        if (grailsApplication.config["overwriteSensorFiles"]) {
          def csvList = queryToCsvList(aq, argList)
          status = writeItems(csvList, "${target} Details", typeFolder, fn)
          def dnMap = designNumMap()
          Sql sql = new groovy.sql.Sql(dataSource)
          def cq = grailsApplication.config["CellsQuery"].toString()
          sql.eachRow(cq, [shortName]) { cellidrow ->
            def lq = "${q} WHERE SUBSTR(C.CLASS,24)=? AND CELL_ID=? ORDER BY CELL,MODEL,SEQ".toString()
            def dn = dnMap.get(cellidrow.ID.longValue())
            fn = "${shortName} ${cellidrow.CELL} Design ${dn} Sensors.csv"
            csvList = queryToCsvList(lq, [shortName, new BigDecimal(cellidrow.ID)])
            status = writeItems(csvList, "${target} Details", typeFolder, fn)
            log.info "${status} from  cell ${cellidrow.CELL}."
          }
        } else {
          status = "${shortName} sensors suppressed by grailsApplication.config[overwriteSensorFiles]: ${grailsApplication.config["overwriteSensorFiles"]}"
          log.info status
        }
      } else {
        log.info status
      }
    return status
    }

    private String exportTransverseJoints () {
      def status = "trjQuery not found"
      def folder = grailsApplication.config["jointDowelFolder"]
      def fqfn = "${folder}\\transverse joints.csv"
      def q = grailsApplication.config["trjQuery"]
      if (q) {
        def aq = "${q} ORDER BY CELL_ID, LANE_ID, LAYER_ID".toString()
        if (grailsApplication.config["overwriteTransverseJointsFiles"]) {
          def csvList = queryToCsvList(aq,[])
          status = writeItems(csvList, fqfn)
        } else {
          status = "${shortName} transverse joints suppressed by grailsApplication.config.overwriteTransverseJointsFiles: ${grailsApplication.config["overwriteTransverseJointsFiles"]}"
          log.info status
        }
      }
      return status
    }

  private String exportFwd(def target,def typeFolder, def shortName) {
    def status = "fwdQuery not found"
    def q = grailsApplication.config["fwdQuery"]
    def dnMap = designNumMap()

    Sql sql = new groovy.sql.Sql(dataSource)
    def cq = grailsApplication.config["CellsQuery"].toString()
    try {
    sql.eachRow(cq, [shortName]) { cellidrow ->
      def lq = "${q}  WHERE MC.TYPE=? AND MC.CELL=? AND S.SESSION_DATE BETWEEN ? AND ? ORDER BY CELL,SESSION_DATE".toString()
      def dn = dnMap.get(cellidrow.ID.longValue())
      def fileName = "${shortName} ${cellidrow.CELL} Design ${dn} Fwd Drops.csv".toString()
      def sqlDateFrom = new java.sql.Date(cellidrow.from_date.getTime())
      def sqlDateTo = new java.sql.Date(cellidrow.to_date.getTime())
      def ddf = MrUtils.mkBranch(typeFolder, grailsApplication.config.loadFolders)
      def fqfn = MrUtils.fqfn(ddf,fileName)
      // queryToCsvFile streams data to a file.  Used for large data exports.
      def rowsExported = queryToCsvFile(lq, [shortName, new BigDecimal(cellidrow.CELL), sqlDateFrom, sqlDateTo], fqfn)
      log.info "${rowsExported} rows exported to ${fileName}."
     }
   } catch (java.sql.SQLSyntaxErrorException ses) {
     status = "${shortName} FWD failsed: ${ses.message}"      
   }
  return status
  }

  Map cellTypeProps(String typeName, def excluded) {
    def rc = [:]
    def tp = getCellProps(excluded)
    tp.keySet().each { shortName ->
      if (shortName == typeName) //  && !excluded.contains(shortName.toLowerCase()))
        rc = tp[shortName]
    }
    return rc
  }

  def writeFile = {ol, ddfw ->
    ol.each { arow ->
      ddfw.append("${arow}\n")
    }
  }

  def String writeItems(def csvList, def exportTarget, def typeFolder, def fileName) {
    def ddf = MrUtils.mkdir(typeFolder, "${exportTarget}")
    def fqfn = MrUtils.fqfn(ddf,fileName)
    return writeItems(csvList, fqfn)
  }

  def String writeItems(def csvList, def fqfn) {
    File iddf = new File(fqfn)
    def all = []
    all << csvList["hdr"]
    csvList["lines"].each{ row ->
      def cvals = []
      row.keySet().each { colName ->
        cvals << row.get(colName)
      }
      all << cvals.join(",")
    }
    if (all.size()>1) {
      FileWriter iddfw = new FileWriter(iddf, false)
      writeFile(all, iddfw)
      iddfw.close()
    }
    int nExported = all.size()?all.size()-1:0
    "${nExported}\t\t items exported to file '${iddf.getName()}'"
  }

  def exportMatSamples(def typeFolders) {
    Sql sql = new groovy.sql.Sql(dataSource)
    Map columnTypeMap = [:]
    Map dataMap = [:]

    def writeRows={ row ->
      def rr = row.toRowResult()
      def r = ""
      def a = []
      def nl = rr.keySet().toList()
      rr.eachWithIndex { it,i ->
        if (nl[i]!="ID") {
          Object o = rr[i]
          def typ = columnTypeMap.get(i+1)
          a << SqlDataFormat.formatSqlValue(o, typ, true, false)
        }
      }
      dataMap["rows"] << "${a.join(',')}"
      dataMap["nrows"]++
    }

    def writeHeader = { meta ->
      def cn = [:]
      columnTypeMap.clear()
      (1..meta.columnCount).each {
        def colName = meta.getColumnLabel(it)
        if (colName != "ID") {
          cn.put(it,colName)
          columnTypeMap.put(it,meta.getColumnTypeName(it))
          }
        }
      def fileHdr = "${cn.keySet().collect {cn.get(it)}.join(",")}\n"
      dataMap["hdr"] = fileHdr
      dataMap["rows"] = []
      dataMap["nrows"] = 0
     }

    dataMap.put("rowsWritten", 0)
    dataMap.put("nrows", 0)
    dataMap.put("hdr", "")
    dataMap.put("rows", [])

    def drive = grailsApplication.config.rdrive
    def dir = MrUtils.mkBranch(drive, grailsApplication.config["dataProductDataFolder"])
    dir = MrUtils.mkBranch(dir, grailsApplication.config["materialSamplesDataFolder"])
    def fqfn = "${dir}\\MATERIAL_SAMPLES.CSV"
    def q = "SELECT * FROM MNR.MAT_SAMPLES ORDER BY MNROAD_ID"
    def csvList = queryToCsvList(q, [])
    if (csvList.size()>0) {
      println "Writing material samples to ${fqfn}."
      def status = writeItems(csvList, fqfn)
    }

    typeFolders.keySet().each { cellType ->
      dir = MrUtils.mkdir(typeFolders.get(cellType),grailsApplication.config["matTestsFolder"])
      def fileMap = [:]
      def fileWriterMap = [:]
      def matTableList = grailsApplication.config.matTableFolderMap.keySet() as List
      for (tableName in matTableList){
        try {
          q = grailsApplication.config["CellsQuery"]
          def rs = sql.rows("${q} ORDER BY CELL,FROM_DATE".toString(),[cellType])
          rs.each { row ->
            q = "${grailsApplication.config.matSamplesQuery} JOIN MNR.${tableName} MST ON MST.MNROAD_ID=MS.MNROAD_ID WHERE MS.CELL=? AND SAMPLE_DATE BETWEEN ? AND ? ORDER BY MS.CELL,SAMPLE_DATE"
            def sqlDateFrom = new java.sql.Date(row.from_date.getTime())
            def sqlDateTo = new java.sql.Date(row.to_date.getTime())
            sql.eachRow(q.toString(),[row.cell, sqlDateFrom, sqlDateTo],writeHeader,writeRows)
            if (dataMap["nrows"]) {
              def subdir = grailsApplication.config.matTableFolderMap[tableName]
              def folder = subdir?MrUtils.mkBranch(dir,subdir):dir
              def fileName = "${tableName}.csv"
              def fn = "${folder}\\${fileName}"
              FileWriter fw = fileWriterMap.get(fn)
              if (!fw) {
                def f = new File(fn)
                if (!f.exists() || (f.exists()&& grailsApplication.config["overwriteDcpFiles"])) {
                  fw = new FileWriter(f, false)
                  fileMap.put(fn,f)
                  fileWriterMap.put(fn,fw)
                  dataMap.put("rowsWritten", 0)
                }
              }
              if (fw && grailsApplication.config["overwriteMatSamplesFiles"]) {
                if (dataMap["rowsWritten"]==0) {
                  def f = fileMap.get(fn)
                  if (f.length() > 0) {
                    def aFileWriter = new FileWriter(f, false)
                    fileWriterMap.put(fn,aFileWriter)
                    fw = aFileWriter
                  }
                  fw.append dataMap["hdr"]
                }
                writeFile(dataMap["rows"],fw)
                dataMap["rowsWritten"]=dataMap["rows"].size()
                log.info "${cellType}: ${tableName} rows from cell ${row.cell}: ${dataMap["rowsWritten"]}"
              }
            }
          }
        } catch (Exception e) {
          log.error "${e.message} retrieving ${q}."
        }
      }
      if (fileWriterMap) {
        fileWriterMap.keySet().each { aFileName ->
         //println "Closing ${aFileName}."
         fileWriterMap.get(aFileName).close()
        }
        fileWriterMap.clear()
      }
    }
  }

  def exportDcp(def typeFolders) {
    Sql sql = new groovy.sql.Sql(dataSource)
    Map columnTypeMap = [:]
    Map dataMap = [:]

    def writeRows={ row ->
      def rr = row.toRowResult()
      def r = ""
      def a = []
      def nl = rr.keySet().toList()
      rr.eachWithIndex { it,i ->
        if (nl[i]!="ID") {
          Object o = rr[i]
          def typ = columnTypeMap.get(i+1)
          a << SqlDataFormat.formatSqlValue(o, typ, true, false)
        }
      }
      dataMap["rows"] << "${a.join(',')}"
      dataMap["nrows"]++
    }

    def writeHeader = { meta ->
      def cn = [:]
      columnTypeMap.clear()
      (1..meta.columnCount).each {
        def colName = meta.getColumnLabel(it)
        if (colName != "ID") {
          cn.put(it,colName)
          columnTypeMap.put(it,meta.getColumnTypeName(it))
          }
        }
      def fileHdr = "${cn.keySet().collect {cn.get(it)}.join(",")}\n"
      dataMap["hdr"] = fileHdr
      dataMap["rows"] = []
      dataMap["nrows"] = 0
     }

    dataMap.put("rowsWritten", 0)
    dataMap.put("nrows", 0)
    dataMap.put("hdr", "")
    dataMap.put("rows", [])
    typeFolders.keySet().each { cellType ->
      def dir = typeFolders.get(cellType)
      dir = MrUtils.mkdir(dir,grailsApplication.config.physMeasureFolder)
      def q = ""
      def fileMap = [:]
      def fileWriterMap = [:]
      try {
        q = grailsApplication.config["CellsQuery"]
        def rs = sql.rows("${q} ORDER BY CELL,FROM_DATE".toString(),[cellType])
        rs.each { row ->
          q = "${grailsApplication.config.dcpQuery} where cell=? and day between ? and ? order by cell,day"
          def sqlDateFrom = new java.sql.Date(row.from_date.getTime())
          def sqlDateTo = new java.sql.Date(row.to_date.getTime())
          sql.eachRow(q.toString(),[row.cell, sqlDateFrom, sqlDateTo],writeHeader,writeRows)
          if (dataMap["nrows"]) {
            def subdir = grailsApplication.config.distressTableFolderMap["DCP_LOCATION"]
            def dcpFolder = MrUtils.mkBranch(dir,subdir)
            def dcpFileName = "DCP.csv"
            def fn = "${dcpFolder}\\${dcpFileName}"
            FileWriter fw = fileWriterMap.get(fn)
            if (!fw) {
              def f = new File(fn)
              if (!f.exists() || (f.exists()&& grailsApplication.config.overwriteDcpFiles)) {
                fw = new FileWriter(f, false)
                fileMap.put(fn,f)
                fileWriterMap.put(fn,fw)
                dataMap.put("rowsWritten", 0)
              }
            }
            if (fw && grailsApplication.config.overwriteDcpFiles) {
              if (dataMap["rowsWritten"]==0) {
                def f = fileMap.get(fn)
                if (f.length() > 0) {
                  // If file exists but no rows have been written it must be an old file.  So overwrite it.
                  def aFileWriter = new FileWriter(f, false)
                  fileWriterMap.put(fn,aFileWriter)
                  fw = aFileWriter
                }
                fw.append dataMap["hdr"]
              }
              writeFile(dataMap["rows"],fw)
              dataMap["rowsWritten"]=dataMap["rows"].size()
              log.info "${cellType}: DCP rows from cell ${row.cell}: ${dataMap["rowsWritten"]}"
            }
          }
        }
      } catch (Exception e) {
        log.error "${e.message} retrieving ${q}."
      }
      if (fileWriterMap) {
        fileWriterMap.keySet().each { aFileName ->
         //println "Closing ${aFileName}."
         fileWriterMap.get(aFileName).close()
        }
        fileWriterMap.clear()
      }
    }
  }

  def exportDistress(def typeFolders) {
    Sql sql = new groovy.sql.Sql(dataSource)
    Map columnTypeMap = [:]
    Map dataMap = [:]

    def writeRows={ row ->
      def rr = row.toRowResult()
      def r = ""
      def a = []
      def nl = rr.keySet().toList()
      rr.eachWithIndex { it,i ->
        if (nl[i]!="ID") {
          Object o = rr[i]
          def typ = columnTypeMap.get(i+1)
          a << SqlDataFormat.formatSqlValue(o, typ, true, false)
        }
      }
      dataMap["rows"] << "${a.join(',')}"
      dataMap["nrows"]++
    }

    def writeHeader = { meta ->
      def cn = [:]
      columnTypeMap.clear()
      (1..meta.columnCount).each {
        def colName = meta.getColumnLabel(it)
        if (colName != "ID") {
          cn.put(it,colName)
          columnTypeMap.put(it,meta.getColumnTypeName(it))
          }
        }
      def fileHdr = "${cn.keySet().collect {cn.get(it)}.join(",")}\n"
      dataMap["hdr"] = fileHdr
      dataMap["rows"] = []
      dataMap["nrows"] = 0
     }

    dataMap.put("rowsWritten", 0)
    dataMap.put("nrows", 0)
    dataMap.put("hdr", "")
    dataMap.put("rows", [])

    typeFolders.keySet().each { cellType ->
      def dir = typeFolders.get(cellType)
      dir = MrUtils.mkdir(dir,grailsApplication.config.physMeasureFolder)
      def q = ""
      def fileMap = [:]
      def fileWriterMap = [:]
      try {
        q = "SELECT * FROM MNR.DISTRESS_CELL WHERE TYPE=?"
        def rs = sql.rows(q.toString(),[cellType])
        rs.each { row ->
          q = "select * from ${grailsApplication.config.currentSchema}.${row.data_table} where cell=? and day between ? and ? order by cell,day"//,lane,day"
          def sqlDateFrom = new java.sql.Date(row.from_date.getTime())
          def sqlDateTo = new java.sql.Date(row.to_date.getTime())
          sql.eachRow(q.toString(),[row.cell, sqlDateFrom, sqlDateTo],writeHeader,writeRows)
          if (dataMap["nrows"]) {
            def subdir = grailsApplication.config.distressTableFolderMap[row.data_table]
            def distressFolder = MrUtils.mkBranch(dir,subdir)
            def distressFileName = "${row.data_table.toString().substring(9)}.csv"
            if (grailsApplication.config.distressFileNameMap[row.data_table])
              distressFileName = "${grailsApplication.config.distressFileNameMap[row.data_table]}.csv"
            def fn = "${distressFolder}\\${distressFileName}"
            FileWriter fw = fileWriterMap.get(fn)
            if (!fw) {
              def f = new File(fn)
              if (!f.exists() || (f.exists()&& grailsApplication.config.overwriteDistressFiles)) {
                fw = new FileWriter(f, false)
                fileMap.put(fn,f)
                fileWriterMap.put(fn,fw)
                dataMap.put("rowsWritten", 0)
              }
            }
            if (fw && grailsApplication.config.overwriteDistressFiles) {
              if (dataMap["rowsWritten"]==0) {
                def f = fileMap.get(fn)
                if (f.length() > 0) {
                  // If file exists but no rows have been written it must be an old file.  So overwrite it.
                  def aFileWriter = new FileWriter(f, false)
                  fileWriterMap.put(fn,aFileWriter)
                  fw = aFileWriter
                }
                fw.append dataMap["hdr"]
              }
              writeFile(dataMap["rows"],fw)
              dataMap["rowsWritten"]=dataMap["rows"].size()
              log.info "${cellType}: ${row.data_table} rows from cell ${row.cell}: ${dataMap["rowsWritten"]}"
            }
          }
        }
      } catch (Exception e) {
        log.error "${e.message} retrieving ${q}."
      }
      if (fileWriterMap) {
        fileWriterMap.keySet().each { aFileName ->
         //println "Closing ${aFileName}."
         fileWriterMap.get(aFileName).close()
        }
        fileWriterMap.clear()
      }
    }
  }

  def createFoldersByType(def baseFolder) {
    def rc = [:]
    try {
      rc = makeTypeFolders(baseFolder)
    } catch (Exception e) {
      println e.toString()
      log.error "createTypeFolders: ${e.message}"
    }
    return rc
  }

  Map makeTypeFolders(def branch) {
    def rc = [:]
    def drive = grailsApplication.config.rdrive
    def dir = MrUtils.mkBranch(drive, grailsApplication.config.dataProductDataFolder)
    dir = MrUtils.mkBranch(dir, branch)
    grailsApplication.config.pavementTypes.keySet().each { type ->
      rc.put(type, MrUtils.mkdir(dir,grailsApplication.config.pavementTypes.get(type)))
    }
  return rc
  }

/*
GrailsDomainClass Interface
GrailsDomainClass         Component:             ${prop.getComponent()}
GrailsDomainClass         DomainClass:           ${prop.getDomainClass()}
int                       FetchMode:             ${prop.getFetchMode()}
java.lang.String          FieldName:             ${prop.getFieldName()}
java.lang.String          Name:                  ${prop.getName()}
java.lang.String          NaturalName:           ${prop.getNaturalName()}
GrailsDomainClassProperty OtherSide:             ${prop.getOtherSide()}
GrailsDomainClass         ReferencedDomainClass: ${prop.getReferencedDomainClass:
java.lang.String          ReferencedPropertyName:${prop.getReferencedPropertyName()}
java.lang.Class           ReferencedPropertyType:${prop.getReferencedPropertyType()}
java.lang.Class           Type:                  ${prop.getType()}
java.lang.String          TypePropertyName:      ${prop.getTypePropertyName()}
boolean                   isAssociation:         ${prop.isAssociation()}
boolean                   isBasicCollectionType: ${prop.isBasicCollectionType()
boolean                   isBidirectional:       ${prop.isBidirectional()}
boolean                   isCircular:            ${prop.isCircular()}
boolean                   isEmbedded:            ${prop.isEmbedded()}
boolean                   isEnum:                ${prop.isEnum()}
boolean                   isHasOne:              ${prop.isHasOne()}
boolean                   isIdentity:            ${prop.isIdentity()}
boolean                   isInherited:           ${prop.isInherited()}
boolean                   isManyToMany:          ${prop.isManyToMany()}
boolean                   isManyToOne:           ${prop.isManyToOne()}
boolean                   isOneToMany:           ${prop.isOneToMany()}
boolean                   isOneToOne:            ${prop.isOneToOne()}
boolean                   isOptional:            ${prop.isOptional()}
boolean                   isOwningSide:          ${prop.isOwningSide()}
 */
  Map getCellProps(def excluded) {
    def rc = [:]
    def dol = grailsApplication.getArtefacts("Domain") as List
    dol.each { domObj ->
      if (domObj.shortName != "Cell"
              && domObj.shortName.endsWith("Cell")) {
        def tableProps = [:]
        Class clazz = grailsApplication.getClassForName(domObj.clazz.name)
        def classMetaData = sessionFactory.getClassMetadata(clazz)
        tableProps.put("class",clazz)
        tableProps.put("tableName",classMetaData.getTableName())
        //tableProps.put("colProps",domObj.getProperties())
        def colNames = [:]
        def colProps = [:]
        domObj.getProperties().each {
          def cn = classMetaData.getPropertyColumnNames(it.name)[0]
          if (cn == 'id'
                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && it.referencedPropertyType
                  && it.referencedPropertyType.name.toLowerCase().endsWith("cell") ) {
            cn = "${it.name}_${it.referencedPropertyType.name.substring(it.referencedPropertyType.name.lastIndexOf(".")+1)}_${cn}"
          }
          if (!it.identity && !it.oneToMany && !excluded.contains(cn.toLowerCase())) {
            colNames.put(it.name,cn)
            colProps.put(it.name, it)
          }
          if (it.identity && it.name=='id') {
            colNames.put(it.name, "cell_id")
            colProps.put(it.name, it)
          }
        }
        tableProps.put("colNames",colNames)
        tableProps.put("colProps",colProps)
        rc.put(domObj.shortName, tableProps)
        }
      }
    return rc
  }

  Map getTypeProps(def sn, def excluded) {
    def rc = [:]
    def dol = grailsApplication.getArtefacts("Domain") as List
    dol.each { domObj ->
      if (domObj.shortName != sn
              && domObj.shortName.endsWith(sn)) {
        def tableProps = [:]
        Class clazz = grailsApplication.getClassForName(domObj.clazz.name)
        def classMetaData = sessionFactory.getClassMetadata(clazz)
        tableProps.put("class",clazz)
        tableProps.put("tableName",classMetaData.getTableName())
        //tableProps.put("colProps",domObj.getProperties())
        def colNames = [:]
        def colProps = [:]
        domObj.getProperties().each {
          def cn = classMetaData.getPropertyColumnNames(it.name)[0]
          if (cn == 'id'
                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && it.referencedPropertyType
                  && it.referencedPropertyType.name.toLowerCase().endsWith("cell") ) {
            cn = "${it.name}_${it.referencedPropertyType.name.substring(it.referencedPropertyType.name.lastIndexOf(".")+1)}_${cn}"
          }
          if (!it.identity && !it.oneToMany && !excluded.contains(cn.toLowerCase())) {
            colNames.put(it.name,cn)
            colProps.put(it.name, it)
          }
          if (it.identity && it.name=='id') {
            colNames.put(it.name, "cell_id")
            colProps.put(it.name, it)
          }
        }
        tableProps.put("colNames",colNames)
        tableProps.put("colProps",colProps)
        rc.put(domObj.shortName, tableProps)
        }
      }
    return rc
  }

  Map getObjProps(def shortType, def excluded) {
    def rc = [:]
    def dol = grailsApplication.getArtefacts("Domain") as List
    dol.each { domObj ->
      if (domObj.shortName.startsWith(shortType)||domObj.shortName.endsWith(shortType)) {
        def tableProps = [:]
        Class clazz = grailsApplication.getClassForName(domObj.clazz.name)
        def classMetaData = sessionFactory.getClassMetadata(clazz)
        tableProps.put("class",clazz)
        tableProps.put("tableName",classMetaData.getTableName())
        def colNames = [:]
        def colProps = [:]
        domObj.getProperties().each {
          def cn = ""
          try {
            cn = classMetaData.getPropertyColumnNames(it.name)[0]
          }
          catch (org.hibernate.MappingException hme) {
            if (hme.message != 'unknown property: version')
              log.info "${hme.message}"
            //println "Table: ${classMetaData.getTableName()}, MappingException message: ${hme.message}."
            // caused by having closures in domain classes  e.g. unknown property: depthRange
          }
          if (cn == 'id'
  //                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && it.referencedPropertyType
                  && it.referencedPropertyType.name.toLowerCase().endsWith("cell") ) {
            cn = "${it.name}_${it.referencedPropertyType.name.substring(it.referencedPropertyType.name.lastIndexOf(".")+1)}_${cn}"
          }
          if (it.persistant
  //                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && cn
                  && !excluded.contains(cn.toLowerCase())) {
            if (it.identity)
              cn = it.domainClass.name+"_id"
            colNames.put(it.name, cn)
            colProps.put(it.name, it)
          }
        }
        tableProps.put("colNames",colNames)
        tableProps.put("colProps",colProps)
        rc.put(domObj.shortName, tableProps)
        }
      }
    return rc
  }

    def saveData(def fileName) {
      String classOfTable = "Facility"
      def exportData = grailsApplication.config.exportOrder["data"]
      exportData.each {
        def inserts = []
        inserts = listInserts(it)
        log.info "Writing $it: ${inserts.size()} rows"
        if (inserts.size() > 0) {
          File f = new File(fileName)
            f.withWriterAppend {file ->
            inserts.each {line ->
            file.writeLine(line)
            }
          }
          f.append("--\n")
        }
      }
    }

    private List listInserts(String classOfTable) {
      def rc = []
      Class clazz = grailsApplication.getClassForName(classOfTable)
      if (!clazz) {
        lof.error "No class for Name: $classOfTable"
        return rc
      }
      def cls = clazz.newInstance()
      if (!cls || (classOfTable.equals("Cell"))) // class Cell is extended
        return rc

      def c = cls.list()
      if (c.size() > grailsApplication.config.maxExportRowsPerTable) {
        log.info "Data for class $classOfTable skipped. ${c.size()} rows."
        return rc
      }
      def vs = ") values ("
      def term = ");"
      def meta = props(classOfTable)
      def columnArraySize = meta["columns"].size()
      c.each {fc ->
        def columnList = ""
        def valueList = ""
        if (meta["discriminatorColumnName"] != null) {
          columnList += "class, "
          valueList += "'${fc.getClass().name}',"
        }
        def idx = 0
        meta["columns"].each {
          def type = meta["types"].toArray()[idx].name
          if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet"))  {
            def val = fc[meta["fields"][idx]]
            columnList += it
            if ((idx+1) < columnArraySize)
              columnList += ", "
          }
          else {
            String xcls = "";
          }
          ++idx
        }
        def is = "insert into ${meta['tableName']} ($columnList"
        is += vs
        idx = 0
        meta["fields"].each {
          def type = meta["types"].toArray()[idx].name
          def val = fc[it]
          String s = SqlDataFormat.formatSqlValue(val, type,true,!meta["fks"].toArray()[idx].equals(""))
          if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
            valueList += s
            if ((idx+1) < columnArraySize)
              valueList += ", "
          }
          ++idx
        }
        is += valueList
        is += term
        rc.add(is)
      }
      return rc
    }

    public def props(String topLevelName) {
      def rc = [:]
      //get the domain class object named topLevelName
      def domainClass = grailsApplication.getClassForName(topLevelName)
      //get hibernate meta data object
      def hibernateMetaClass = sessionFactory.getClassMetadata(domainClass)
      //get the table name mapped to the DomainClass domain class
      def tableName = hibernateMetaClass.getTableName()
      rc.put("tableName",tableName)
      rc.put("discriminatorColumnName",hibernateMetaClass.discriminatorColumnName)
      //create a new GrailsDomainClass object for the DomainClass
      def grailsDomainClass = new DefaultGrailsDomainClass(domainClass)
      //get the domain properties defined in Domain Class
      //grailsDomainClass.getProperties() is returns GrailsDomainClassProperty[] objects
      def domainProps = grailsDomainClass.getProperties()
      def fields = []
      def types = []
      def columns = []
      def fks = []
      domainProps.each {prop ->
        //get the property's name
        String propName = prop.getName()
        def columnProps = null
        try {
          columnProps = hibernateMetaClass.getPropertyColumnNames(propName)
        }
        catch (org.hibernate.MappingException hme) {
          if (hme.message != 'unknown property: version')
            log.info "Table: ${tableName} Field: $propName, MappingException message: ${hme.message}."
          // caused by having closures in domain classes
        }
        if (columnProps && columnProps.length > 0) {
          //get the column name, which is stored into the first array
          def columnName = columnProps[0]
          String pn = propName.toString()
          fields.add(pn)
          types.add(prop.type)
          columns.add(columnName)
          if (!prop.isOwningSide() && prop.isManyToOne() && columnName.endsWith("_id"))   {
            fks.add(pn)
          }
          else if (prop.isOneToMany() && prop.referencedPropertyType != null)   {
            fks.add(prop.referencedPropertyType.name)
          }
          else if (prop.isOneToOne())   {
            fks.add(prop.type)
          }
          else {
            fks.add("")
          }
        }
      }
    rc.put("fields",fields)
    rc.put("types",types)
    rc.put("columns",columns)
    rc.put("fks",fks)
    return rc
    }

  Map getDistressProps(def excluded) {
    def rc = [:]
    def dol = grailsApplication.getArtefacts("Domain") as List
    dol.each { domObj ->
      if (domObj.shortName.startsWith("Distress")&&domObj.shortName != "Distress") {
        def tableProps = [:]
        Class clazz = grailsApplication.getClassForName(domObj.clazz.name)
        def classMetaData = sessionFactory.getClassMetadata(clazz)
        tableProps.put("class",clazz)
        tableProps.put("tableName",classMetaData.getTableName())
        def colNames = [:]
        def colProps = [:]
        domObj.getProperties().each {
          def cn = ""
          try {
            cn = classMetaData.getPropertyColumnNames(it.name)[0]
          }
          catch (org.hibernate.MappingException hme) {
            //log.info "${hme.message}"
            // "Table: $tableName, MappingException message: ${hme.message}."
            // caused by having closures in domain classes  e.g. unknown property: depthRange
          }
          if (cn == 'id'
    //                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && it.referencedPropertyType
                  && it.referencedPropertyType.name.toLowerCase().endsWith("cell") ) {
            cn = "${it.name}_${it.referencedPropertyType.name.substring(it.referencedPropertyType.name.lastIndexOf(".")+1)}_${cn}"
          }
          if (it.persistant
    //                  && !it.identity
                  && !it.oneToMany
                  && !it.manyToOne
                  && cn
                  && !excluded.contains(cn.toLowerCase())) {
            colNames.put(it.name,cn)
            colProps.put(it.name, it)
          }
        }
        tableProps.put("colNames",colNames)
        tableProps.put("colProps",colProps)
        rc.put(domObj.shortName, tableProps)
        }
      }
    return rc
    }

    private Map setupTypeFolders(String f, List types) {
      def typeFolders = [:]
      (new File(f)).mkdirs()
      types.each { type ->
        def sf = f + "${grailsApplication.config.pavementTypes.get(type)}"
        (new File(sf)).mkdir()
        typeFolders.put(type, sf)
      }
      return typeFolders
    }

      private def cellPropValues(ctp, Cell c) {
        def cd = []
        ctp.colProps.keySet().each {cp ->
          def fldProps = ctp.colProps.get(cp)
          def val = c[cp]
          def fk = foreignKey(fldProps)
          def sqlVal = SqlDataFormat.formatValue(val, fldProps, fk != "")
          if (fldProps.type.name == "java.util.SortedSet" && val.size() == 0)
            sqlVal=""
          cd << sqlVal ? "\"${sqlVal}\"" : ""
        }
        return cd
      }

      private def foreignKey(fldProps) {
        def fks = ""
        if (!fldProps.isOwningSide() && fldProps.isManyToOne() && fldProps.name.endsWith("_id")) {
          fks = fldProps.name
        }
        else if (fldProps.isOneToMany() || fldProps.referencedPropertyType != null) {
          fks = fldProps.referencedPropertyType.name
        }
        else if (fldProps.isOneToOne()) {
            fks = fldProps.type
          }
          else {
            fks = ""
          }
        return fks
      }

    private def objPropValues(def ctp, def c) {
      def cd = []
      ctp.colProps.keySet().each {cp ->
        def fldProps = ctp.colProps.get(cp)
        def fk = foreignKey(fldProps)
        def val = c[cp]
        def sqlVal
        if (fldProps.oneToOne) {
          sqlVal = val.toString()
        } else {
          sqlVal = SqlDataFormat.formatValue(val, fldProps, fk != "")
        }
        if (fldProps.type.name == "java.util.SortedSet" && val.size() == 0)
          sqlVal=""
        cd << sqlVal ? "\"${sqlVal}\"" : ""
      }
      return cd
    }

    Map designNumMap () {
      def rc = [:]
      def cellList = (Cell.createCriteria()).list(){
        order('cellNumber','asc')
        order('constructionEndedDate','asc')
      }
      def dn = 1
      us.mn.state.dot.mnroad.Cell c = null
      cellList.each() {
        if (c == null) {
          rc.put(it.id, dn++)
        } else {
          if (c.cellNumber == it.cellNumber) {
            rc.put(it.id, dn++)
          } else {
            dn = 1
            rc.put(it.id, dn++)
          }
        }
        c = it
      }
      return rc
    }
//
//      HSSFWorkbook tableAsSpreadsheet(def tableName, def metaOnly, def maxRows, def rowsInTable) {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        def tc = dbMetadataService.allTabColumns(grailsApplication.config.currentSchema,tableName)
//        if (tc) {
//          int rowNum = 0;
//          def sql = new Sql(sessionFactory.currentSession.connection())
//          def sheet = wb.createSheet(tableName);
//          def ssrow = sheet.createRow(rowNum++);
//          HSSFCellStyle lockedStyle = wb.createCellStyle();
//          lockedStyle.setLocked(true)
//          HSSFCell headerCell = ssrow.createCell(0)
//          headerCell.setCellStyle(lockedStyle)
//          // Write header row
//          headerCell.setCellValue("CREATE(c) UPDATE(u) or DELETE(d)") // Used to specify the C/U/D operation
//          def colsExcludedFromUpdate = ['DATE_CREATED','CREATED_BY','DATE_MODIFIED','MODIFIED_BY']
//          tc[DbColumnAttr.NAME].eachWithIndex { colName,namesIdx ->
//            if (!colsExcludedFromUpdate.contains(colName)) {
//              def colHeaderCell = ssrow.createCell(namesIdx+1)
//              colHeaderCell.setCellStyle(lockedStyle)
//              colHeaderCell.setCellValue(colName)
//            }
//          }
//          String sq = "select T.* FROM ${grailsApplication.config.currentSchema}.${tableName} T".toString()
//          String sqs = "SELECT * FROM ( SELECT C.*, ROWNUM RNUM FROM (${sq} ) C WHERE ROWNUM <= ${maxRows}) WHERE RNUM  >= 1".toString()
//          int maxRownum = 0
//          int rcnt = 1
//          sql.eachRow(metaOnly?sqs:sq) { row ->
//            def val = ""
//            ssrow = sheet.createRow(rowNum++);
//            int colIdx = 0
//            ssrow.createCell(colIdx).setCellValue(val)
//            sheet.autoSizeColumn(colIdx)
//
//            if (metaOnly) {
//              BigDecimal rnum = row["rnum"]
//              if (rnum) {
//                maxRownum = Math.max(maxRownum, rnum.toInteger().intValue())
//              }
//            }
//            for (def colName in tc[DbColumnAttr.NAME]) {
//              if (!colsExcludedFromUpdate.contains(colName)) {
//                def cellval = row[colName]
//                def fv = SqlDataFormat.formatSqlValue(row[colName], tc[DbColumnAttr.TYPE][colIdx], false, false)
//                if ('"'+fv+'"' == cellval) {
//                  ssrow.createCell(0).setCellValue('u')
//                }
//                HSSFCell dataCell = ssrow.createCell(colIdx+1);
//                dataCell.setCellValue(fv)
//                if ((rowNum - 1 == rowsInTable) || (metaOnly && (rowNum-1 == maxRows))) {
//                  sheet.autoSizeColumn(colIdx+1)
//                }
//              }
//              colIdx++
//            }
//          rcnt++
//          }
//          if (metaOnly) {
//            ssrow = sheet.createRow(rowNum++);
//            ssrow.createCell(0).setCellValue("First ${maxRows} rows. Use SQuirreL + copy/paste to add more.")
//            sheet.autoSizeColumn(0)
//          }
//        sheet.createFreezePane( 1, 1, 1, 1 );
//        def ds = sql.firstRow("SELECT TO_CHAR(SYSDATE, 'yyyy-MM-dd hh:MI:ss') DOWNLOAD_DATE FROM DUAL")
//        int rn = addDbColumns(wb, tc, ds.DOWNLOAD_DATE)
//        println "${rn} metadata rows."
//      }
//      return wb
//      }
//
//    protected def addDbColumns(HSSFWorkbook wb, def tc, def downloadDate) {
//      def rn = 0
//      // Create headings
//      def mdsheet = wb.createSheet(grailsApplication.config.downloadMetadataSheetname.toString());
//      def mdrow = mdsheet.createRow(rn++)
//      (DbColumnAttr.NAME..DbColumnAttr.NULLABLE).eachWithIndex { name, idx ->
//        mdrow.createCell(idx).setCellValue(name.toString())
//      }
//      def colRange = 0..((DbColumnAttr.NAME..DbColumnAttr.NULLABLE).size()-1)
//      def nRows = tc[DbColumnAttr.NAME].size()
//      // Create a row for each column
//      (0..nRows-1).each {
//        mdrow = mdsheet.createRow(rn++)
//        colRange.each { colIdx ->
//          def v = tc[(DbColumnAttr.list()[colIdx])][rn - 2]
//          mdrow.createCell(colIdx).setCellValue(v)
//        }
//        if (rn - 1 == nRows) {
//          colRange.each { colIdx ->
//            mdsheet.autoSizeColumn(colIdx)
//          }
//        }
//      }
//      mdrow = mdsheet.createRow(rn)
//      mdrow.createCell(0).setCellValue("DOWNLOAD_DATE")
//      mdrow.createCell(1).setCellValue(downloadDate)
//      return rn
//    }

/*
    def ddl = {
      try {
        String fileName = "mnroad_"+MrUtils.grailsEnvironment()+".ddl"
        File f = new File(fileName)
        Sql sql = new groovy.sql.Sql(dataSource)
        def schema = "MNROAD"
        def ddl = ""
        log.info("Writing "+MrUtils.grailsEnvironment()+" schema to file ")
        (ApplicationHolder.application.getArtefacts("Domain") as List).each {
          def natName = it.naturalName.toUpperCase()
          def tbl_name = natName.replace(' ','_')
          if ( // if this class is not a subclass
            (props(it.name).discriminatorColumnName == null) ||
            // or if it is a superclass
            (it.subClasses.size() > 0)
          )
          {
            def tname = props(it.name).tableName?props(it.name).tableName.toUpperCase():tbl_name
            def s = "select to_char(dbms_metadata.get_ddl('TABLE', ${tname},${schema})) from dual"
            ddl = sql.firstRow(s)[0].toString() + ";"
            log.info(it)
            f.append(ddl)
          }
        }
        }
      catch(Exception e){
        log.error e.message
      }
    }

    private def writeCellCsvfiles(List excluded, shortName, typeFolders) {
      def ctp = cellTypeProps(shortName, excluded)
      def cellHdrs = ["FACILITY", "ROAD_SECTION"]
      cellHdrs.addAll(ctp["colNames"].values().collect {it.toUpperCase()})
      def exclProps = ["date_created", "created_by", "last_updated", "last_updated_by", "version"]

      def laneProps = getObjProps("Lane", exclProps)
      def laneHdrs = laneProps["Lane"]["colNames"].values().collect {it.toUpperCase()}
      cellHdrs.addAll(laneHdrs)

      def layerProps = getObjProps("Layer", exclProps)
      def layerHdrs = layerProps["Layer"]["colNames"].values().collect {it.toUpperCase()}
      cellHdrs.addAll(layerHdrs)

      def sensorProps = getObjProps("Sensor", exclProps)
      def sensorHdrs = []
      sensorHdrs << "LAYER_ID"
      sensorHdrs.addAll(sensorProps["Sensor"]["colNames"].values().collect {it.toUpperCase()})
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
      def l = (ctp["class"]).list().sort {p1, p2 ->
        def s1 = "${p1.cellNumber}".padLeft(3, '0')
        def s2 = "${p2.cellNumber}".padLeft(3, '0')
        def d1 = "${sdf.format(p1.constructionEndedDate)}"
        def d2 = "${sdf.format(p2.constructionEndedDate)}"
        (s1 + d1).compareTo(s2 + d2)
      }
      def dnMap = designNumMap()
      def nCellsExported = 0
      l.each {obj ->
        try {
          Cell cell = Cell.get(obj.id)
          def cells = ["\"${cell.roadSection.facility.name}\"", "\"${cell.roadSection.description}\""]
          cells.addAll(cellPropValues(ctp, cell))
          def layerRows = []
          def sensorRows = []
          cell.lanes.each {lane ->
            def lanes = []
            lanes.addAll(cells)
            lanes.addAll(objPropValues(laneProps["Lane"], lane))
            lane.layers.each { layer ->
              def layers = []
              layers.addAll(lanes)
              def vals = objPropValues(layerProps["Layer"], layer)
              layers.addAll(vals)
              layerRows << layers
              layer.sensors.each { sensor ->
                def sp = []
                sp << sensor.layer.id
                sp.addAll(objPropValues(sensorProps["Sensor"], sensor))
                sensorRows << sp
              }
            }
          }
          nCellsExported++
          def dn = dnMap.get(obj.id)
          def desn = "Design ${dn}"
          def cellDesignFileName = "${shortName} ${obj.cellNumber} ${desn}"
          def sensorDataFileName = "${cellDesignFileName} Sensors"
          //writeRowsToWorkbookSheet(sensorRows, sensorHdrs, wb, "Cell ${obj.cellNumber} Sensors")
          def tfn = typeFolders.get(shortName)
          if (!tfn) {
//            log.error "writeCellCsvfiles: No folder for ${shortName}"
            return
          }
          def cellFolder = ""
          try {
            def dataFolder = MrUtils.mkdir("${tfn}\\","${cellDesignFileName}\\")
            cellFolder = MrUtils.mkdir(dataFolder,"${grailsApplication.config.cellFolder}\\")
          } catch (FileNotFoundException fnfe) {
            if ((new File(cellFolder)).exists()) {
              log.debug "${cellFolder} not created. It already exists."
            } else
              log.info "Unable to create folders: ${fnfe.message}"
          }
          FileWriter fw
          if (cellFolder) {
//            log.info "Writing to ${cellFolder}"
            try {
              if (cellDesignFileName) {
                fw = new FileWriter("${cellFolder}${cellDesignFileName}.csv")
                if (fw) {
                  writeRowsToCsvFile(layerRows,cellHdrs, fw)
                  fw.close()
                  fw = null
                }
              }
              if (sensorDataFileName) {
                fw = new FileWriter("${cellFolder}${sensorDataFileName}.csv")
                if (fw) {
                  writeRowsToCsvFile(sensorRows, sensorHdrs, fw)
                  fw.close()
                  fw = null
                }
              }
            } catch (FileNotFoundException fnfe) {
              log.error fnfe.message
            } catch (Exception e) {
              log.error e
            } finally {
              if (fw) {
                fw.close()
              }
            }
          }
        } catch (IOException fioe) {
          log.error fioe.message
        }
      }
      return nCellsExported
    }

      protected def writeRowsToCsvFile(List rows, List colHdrs, FileWriter f) {
        // Write column headers
        f.append("${colHdrs.join(",")}\n")
        // Write data headers
        rows.each { row ->
          f.append("${row.join(",")}\n")
        }
      }
*/
/****************************************************************************
    protected def writeRowsToWorkbookSheet(List rows, List colHdrs, XSSFWorkbook wb, def sheetName) {
      def lncnt = 0
      Sheet sheet = wb.createSheet(sheetName)
      // Write column headers
      Row ssRow = sheet.createRow(lncnt++);
      colHdrs.eachWithIndex {hdr, i ->
        ssRow.createCell(i).setCellValue(hdr.toString().toUpperCase());
      }
      rows.each {arow ->
        ssRow = sheet.createRow(lncnt);
        arow.eachWithIndex {aVal, i ->
          ssRow.createCell(i).setCellValue(aVal);
        }
        lncnt++
      }
      int columnCount = 0;
      if (sheet) {
        Row firstRow = sheet.getRow(0);
        for (Cell c: firstRow) {
          sheet.autoSizeColumn(columnCount)
          columnCount++;
        }
      }
//    return columnCount
    }

    protected def writeLayerData (def lane, def sheet, def laneMeta, def layerMeta, def selectedLaneCols) {
      def rowidx = sheet.rows.size()
      int colidx = 0
      lane.layers.each { layer ->
        Row layerRow = sheet.createRow(rowidx++);
        laneMeta["columns"].eachWithIndex { cname, lnidx ->
          if (selectedLaneCols.contains(cname)) {
            def type = laneMeta["types"].toArray()[lnidx].name
            def val = lane[laneMeta["fields"].toArray()[lnidx]]
            if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
              String sqlVal = formatSqlValue(val, type,!laneMeta["fks"].toArray()[lnidx].equals(""))
              layerRow.createCell(colidx).setCellValue(sqlVal);
              sheet.autoSizeColumn(colidx)
              colidx++
            }
          }
        }
        layerMeta["fields"].eachWithIndex { obj, lyridx ->
          def type = layerMeta["types"].toArray()[lyridx].name
          def val = layer[obj]
          if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
            String sqlVal = formatSqlValue(val, type,!layerMeta["fks"].toArray()[lyridx].equals(""))
            layerRow.createCell(colidx+lyridx).setCellValue(sqlVal);
            sheet.autoSizeColumn(colidx+lyridx)
          }
        }
        colidx = 0
      }
    }

      def writeSheet (def wb, def domObj) {
      def lncnt = 0
      def cls = domObj.newInstance()
      if (cls && (!domObj.shortName.equals("Cell"))) { // class Cell is extended
        def sn = domObj.clazz.name.substring(domObj.clazz.name.lastIndexOf('.') + 1)
        def metaData = props(domObj.clazz.name)

        def objs = cls.list()
        lncnt = 0
        def sheetName = "mr${sn}Data"
        log.info "Writing ${objs.size()} rows to sheet ${sheetName}"
        Sheet sheet
        try {
          sheet = wb.createSheet(sheetName)
          // Write column headers
          Row row = sheet.createRow(lncnt++);
          metaData['columns'].eachWithIndex { obj, i ->
            row.createCell(i).setCellValue(obj.toString().toUpperCase());
            sheet.autoSizeColumn(i)
          }

          objs.each {arow ->
            // show progress
            if (lncnt % 25 == 0) {
              log.info "${sheetName} ${lncnt}"
            }
            row = sheet.createRow(lncnt);
            def flds = metaData["fields"]
            flds.eachWithIndex {fldName, i ->
              String sqlVal = ""
              def type = metaData["types"].toArray()[i].name
              if (!type.equals("java.util.Set") && !type.equals("java.util.SortedSet")) {
                def val = arow[fldName]
                sqlVal = formatSqlValue(val, type, !metaData["fks"].toArray()[i].equals(""))
              } // accessing embedded collections really chews up the time
              // Instead, use DB query to count the number of children
              else {
                def dc = metaData["fks"].toArray()[i]
                if (dc) {
                  def ctn = sessionFactory.getClassMetadata(dc).getTableName()
                  def tn = metaData["tableName"]
                  sqlVal = "${childRowCount(ctn, tn.substring(tn.indexOf(".") + 1), arow["id"])}"
                }
              }
              row.createCell(i).setCellValue(sqlVal);
//            sheet.autoSizeColumn(i)
            }
            lncnt++
          }
        } catch (FileNotFoundException fnfe) {
          if (fnfe.message.endsWith("(The process cannot access the file because it is being used by another process)")) {
            log.error fnfe.message
          }
        } catch (Exception e) {
          log.error e.message
        }
        if (sheet) {
          Row firstRow = sheet.getRow(0);
          int columnCount = 0;
          for (Cell cell : firstRow) {
            sheet.autoSizeColumn(columnCount)
              columnCount++;
          }
        }
      }
      return lncnt?lncnt-1:0
    }

      private def writeCells(List excluded, shortName, typeFolders) {
        def ctp = cellTypeProps(shortName, excluded)
        def colHdrs = ["FACILITY", "ROAD_SECTION"]//," + ctp["colNames"].values().join(",").toUpperCase()
        colHdrs.addAll(ctp["colNames"].values().collect {it.toUpperCase()})
        def exclProps = ["date_created", "created_by", "last_updated", "last_updated_by", "version"]
        def laneProps = getObjProps("Lane", exclProps)
        def laneHdrs = laneProps["Lane"]["colNames"].values().collect {it.toUpperCase()}
        colHdrs.addAll(laneHdrs)
        def layerProps = getObjProps("Layer", exclProps)
        def layerHdrs = layerProps["Layer"]["colNames"].values().collect {it.toUpperCase()}
        colHdrs.addAll(layerHdrs)
        def distressHdrs = [:]
        def distressProps = getDistressProps(exclProps)
        distressProps.keySet().each {dkey ->
          def props = distressProps.get(dkey)
          def colNames = props["colNames"].values().collect {it.toUpperCase()}
          distressHdrs.put(dkey, colNames)
        }
        SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd")
        def l = (cellTypeProps(shortName, excluded)["class"]).list().sort {p1, p2 ->
          def s1 = "${p1.cellNumber}".padLeft(3, '0')
          def s2 = "${p2.cellNumber}".padLeft(3, '0')
          def d1 = "${sdf_yyyyMMdd.format(p1.constructionEndedDate)}"
          def d2 = "${sdf_yyyyMMdd.format(p2.constructionEndedDate)}"
          (s1 + d1).compareTo(s2 + d2)
        }
        def dnMap = designNumMap()
        def nCellsExported = 0
        l.each {obj ->
          def dn = dnMap.get(obj.id)
          def desn = "Design ${dn}"
          def cellDesign = "${shortName} ${obj.cellNumber} ${desn}"
//          def fns = "${cellDesign} ${obj.name}"
          def fns = "${cellDesign}"
      try {
            def rows = []
            Cell cell = Cell.get(obj.id)
            def cells = ["${cell.roadSection.facility.name}", "${cell.roadSection.description}"]
            cells.addAll(cellPropValues(ctp, cell))
            cell.lanes.each {lane ->
              def lanes = []
              lanes.addAll(cells)
              lanes.addAll(objPropValues(laneProps["Lane"], lane))
              lane.layers.each { layer ->
                def layers = []
                layers.addAll(lanes)
                def vals = objPropValues(layerProps["Layer"], layer)
                layers.addAll(vals)
                rows << layers
              }
            }

            nCellsExported++
// Write to excel spreadwheet
            XSSFWorkbook wb = new XSSFWorkbook();
            writeLayersToWorkbook(rows, colHdrs, wb, cellDesign)
            FileOutputStream fileOut
            try {
              def cellFolder = typeFolders.get(shortName) + "\\${fns.replace("\"", " in. ").trim()}\\"
              def layersFolder = "${cellFolder}I. Test Cell Layers and Design Details\\"
              def sensorsFolder = "${cellFolder}V. Sensors\\"
              (new File(cellFolder)).mkdir()
              fileOut = new FileOutputStream("${layersFolder}mnroadData.xlsx");
              log.info "Writing workbook to ${layersFolder}mnroadData.xlsx."
              wb.write(fileOut);
              fileOut.close();
            } catch (FileNotFoundException fnfe) {
              if (fnfe.message.endsWith("(The process cannot access the file because it is being used by another process)")) {
                log.error fnfe.message
              }
            } catch (Exception e) {
              log.error e
            }

          } catch (IOException fioe) {
            log.error fioe.message
          }
        }
        return nCellsExported
      }

    protected def writeLayersToWorkbook(List rows, List colHdrs, XSSFWorkbook wb, cellDesign) {
      def sheetName = "${cellDesign}"
      def lncnt = 0
      Sheet sheet = wb.createSheet(sheetName)
      // Write column headers
      Row ssRow = sheet.createRow(lncnt++);
      colHdrs.eachWithIndex {hdr, i ->
        ssRow.createCell(i).setCellValue(hdr.toString().toUpperCase());
      }
      rows.each {arow ->
        ssRow = sheet.createRow(lncnt);
        arow.eachWithIndex {aVal, i ->
          ssRow.createCell(i).setCellValue(aVal);
        }
        lncnt++
      }
      int columnCount = 0;
      if (sheet) {
        Row firstRow = sheet.getRow(0);
        for (Cell c: firstRow) {
          sheet.autoSizeColumn(columnCount)
          columnCount++;
        }
      }
      return columnCount
    }

  private def writeCellSpreadsheets(List excluded, shortName, typeFolders) {
    def ctp = cellTypeProps(shortName, excluded)
    def cellHdrs = ["FACILITY", "ROAD_SECTION"]//," + ctp["colNames"].values().join(",").toUpperCase()
    cellHdrs.addAll(ctp["colNames"].values().collect {it.toUpperCase()})
    def exclProps = ["date_created", "created_by", "last_updated", "last_updated_by", "version"]

    def laneProps = getObjProps("Lane", exclProps)
    def laneHdrs = laneProps["Lane"]["colNames"].values().collect {it.toUpperCase()}
    cellHdrs.addAll(laneHdrs)

    def layerProps = getObjProps("Layer", exclProps)
    def layerHdrs = layerProps["Layer"]["colNames"].values().collect {it.toUpperCase()}
    cellHdrs.addAll(layerHdrs)

    def sensorProps = getObjProps("Sensor", exclProps)
    def sensorHdrs = []
    sensorHdrs << "LAYER_ID"
    sensorHdrs.addAll(sensorProps["Sensor"]["colNames"].values().collect {it.toUpperCase()})
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
    def l = (ctp["class"]).list().sort {p1, p2 ->
      def s1 = "${p1.cellNumber}".padLeft(3, '0')
      def s2 = "${p2.cellNumber}".padLeft(3, '0')
      def d1 = "${sdf.format(p1.constructionEndedDate)}"
      def d2 = "${sdf.format(p2.constructionEndedDate)}"
      (s1 + d1).compareTo(s2 + d2)
    }
    def dnMap = designNumMap()
    def nCellsExported = 0
    l.each {obj ->
      try {
        Cell cell = Cell.get(obj.id)
        def cells = ["${cell.roadSection.facility.name}", "${cell.roadSection.description}"]
        cells.addAll(cellPropValues(ctp, cell))
        def layerRows = []
        def sensorRows = []
        cell.lanes.each {lane ->
          def lanes = []
          lanes.addAll(cells)
          lanes.addAll(objPropValues(laneProps["Lane"], lane))
          lane.layers.each { layer ->
            def layers = []
            layers.addAll(lanes)
            def vals = objPropValues(layerProps["Layer"], layer)
            layers.addAll(vals)
            layerRows << layers
            layer.sensors.each { sensor ->
              def sp = []
              sp << sensor.layer.id
              sp.addAll(objPropValues(sensorProps["Sensor"], sensor))
              sensorRows << sp
            }

          }
        }
        nCellsExported++
        // Write to excel spreadwheet
        // see http://poi.apache.org/spreadsheet/quick-guide.html
        Workbook wb = new XSSFWorkbook();
        def dn = dnMap.get(obj.id)
        def desn = "Design ${dn}"
        def cellDesign = "${shortName} ${obj.cellNumber} ${desn}"
        writeRowsToWorkbookSheet(layerRows, cellHdrs, wb, cellDesign)
        writeRowsToWorkbookSheet(sensorRows, sensorHdrs, wb, "Cell ${obj.cellNumber} Sensors")
        def tfn = typeFolders.get(shortName)
        if (!tfn) {
          log.error "No folder for ${shortName}"
          return
        }
        def cellFolder = ""
        try {
          def dataFolder = MrUtils.mkdir("${tfn}\\","${cellDesign}\\")
          cellFolder = MrUtils.mkdir(dataFolder,"${grailsApplication.config.cellFolder}\\")
        } catch (FileNotFoundException fnfe) {
            log.info "Unable to create folders: ${fnfe.message}"
        }
        FileOutputStream fileOut
        if (cellFolder) {
          try {
            fileOut = new FileOutputStream("${cellFolder}mnroadData.xlsx");
            wb.write(fileOut);
            if (fileOut)
              fileOut.close();
          } catch (FileNotFoundException fnfe) {
            log.error fnfe.message
          } catch (Exception e) {
            log.error e
          } finally {
            if (fileOut) {
              fileOut.close()
            }
          }
        }
      } catch (IOException fioe) {
        log.error fioe.message
      }
    }
    return nCellsExported
    }
*/
} // end class ExportDataService

//private Map dataProductSensors(def tm, def q, def argList) {
//  def rc = [:]
//  def nameList = tm.keySet() as List
//  def rowList = []
//  def n = 0
//  try {
//    Sql sql = new groovy.sql.Sql(dataSource)
//    def rows = sql.rows(q.toString(),argList)
//    rows.each { arow ->
//      def lineMap = [:]
//      arow.values().eachWithIndex { val, i ->
//        lineMap.put(nameList[i], val)
//      }
//     rowList << lineMap
//    n++
//    }
//    rc.put("rows",rowList)
//  } catch (Exception e) {
//    println e
//  }
//  rc.put("cols",tm)
//  return rc
//}
//
//private Map dataProductLayers(def tm, def q, def argList) {
//  def rc = [:]
//  def nameList = tm.keySet() as List
//  def rowList = []
//  def n = 0
//  try {
//     Sql sql = new groovy.sql.Sql(dataSource)
//     def rows = sql.rows(q.toString(),argList)
//     rows.each { arow ->
//       def lineMap = [:]
//       arow.values().eachWithIndex { val, i ->
//         lineMap.put(nameList[i], val)
//       }
//      rowList << lineMap
//     n++
//     }
//     rc.put("rows",rowList)
//  } catch (Exception e) {
//    println e
//  }
//  rc.put("cols",tm)
//  return rc
//}

//      def String writeItems(def rows, def exportTarget, def tm, def q, def typeFolder, def shortName, def argList, def writeSummary) {
//        def dnMap = designNumMap()
//      def data
//      if (!grailsApplication.config.validExportTargets.contains(exportTarget.toLowerCase()))
//        return ("Unknown export target '${exportTarget}'.  Must be in ${grailsApplication.config.validExportTargets}.")
//      if (exportTarget == "Layer")
//        data = dataProductLayers(tm, q, argList)
//      else if (exportTarget == "Sensor")
//        data = dataProductSensors(tm, q, argList)
//      else // if (exportTarget == "Drop")
//        return ("Unknown export target '${exportTarget}'.  Must be in ${grailsApplication.config.validExportTargets}.")
//        long lastid = 0
//        int cell = 0
//        def ol = []
//        def nCells = 0
//        def all = []
//        all << data["cols"].keySet().join(",")
//        def ddf = MrUtils.mkdir(typeFolder, "${exportTarget} Details")
//        data.rows.each { row ->
//          def line = []
//          row.keySet().each { key ->
//            line << MrUtils.formatSqlValueForCsv(row.get(key), data["cols"].get(key))
//          }
//          if (writeSummary) {
//            all << line.join(",")
//          }
//          if (row.CELL_ID.longValue() != lastid) {
//            if (ol.size() > 0) {
//              def fn = MrUtils.fqfn(ddf,"${shortName} ${cell} Design ${dnMap.get(lastid)}.csv")
//              writeFile(ol, fn)
//              nCells++
//              ol.clear()
//            }
//            ol << data["cols"].keySet().join(",")
//          }
//          ol << line.join(",")
//          cell = row.CELL.intValue()
//          lastid = row.CELL_ID.longValue()
//        }
//        if (ol.size() > 0) {
//          def fn = MrUtils.fqfn(ddf,"${shortName} ${cell} Design ${dnMap.get(lastid)}.csv")
//          writeFile(ol, fn)
//          nCells++
//        }
//        if (writeSummary) {
//          def fn = MrUtils.fqfn(ddf,"${shortName} All Cells.csv")
//          writeFile(all, fn)
//        }
//
//        "${shortName}: ${all.size()-1} ${exportTarget} items in ${nCells} cells exported."
//      }

//      Map cellBasics() {
//        def rc = [:]
//        Sql sql = new groovy.sql.Sql(dataSource)
//        def dnMap = designNumMap()
//        def q = """SELECT SUBSTR(C.CLASS,24) TYPE,CELL,CS.ID,FROM_DATE,TO_DATE,FROM_STATION,TO_STATION
//      FROM MNR.CELL_STATIONS  CS
//      JOIN MNR.CELL C ON C.ID = CS .ID
//      ORDER BY CELL,FROM_DATE,FROM_STATION
//      """
//        sql.eachRow(q.toString()) { row ->
//          def rowMap = [:]
//          rowMap.put("cell"       ,row.CELL.intValue()        )
//          rowMap.put("type"       ,row.TYPE        )
//          rowMap.put("fromDate"   ,new Date(row.FROM_DATE.getTime())   )
//          rowMap.put("toDate"     ,row.TO_DATE?(new Date(row.TO_DATE.getTime())):null   )
//          rowMap.put("fromStation",row.FROM_STATION)
//          rowMap.put("toStation"  ,row.TO_STATION  )
//          def cellDesign = "${row.TYPE} ${row.CELL} Design ${dnMap.get(row.ID)}"
//          rowMap.put("cellDesign"  ,cellDesign  )
//          rc.put(row.ID, rowMap)
//        }
//        return rc
//      }
