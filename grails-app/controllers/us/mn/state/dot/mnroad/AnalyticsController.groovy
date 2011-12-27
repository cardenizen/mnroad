package us.mn.state.dot.mnroad

//import java.util.Arrays;
import grails.converters.JSON
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.commons.fileupload.disk.DiskFileItem
import us.mn.state.dot.mnroad.MrUtils
import java.sql.SQLException

class AnalyticsController {

  // Automatically injected by Grails.
    def analyticsService
    def queryService

    def index = {
      def cells = (Cell.createCriteria()).list(){
        order('cellNumber','asc')
        order('constructionEndedDate','asc')
      }
      def cellNumber = params.cell?:1
      def cellid = params.cellid?:15849
      def model = params.model?:''
      Date dt = new Date()
      def dt1 = new GregorianCalendar(1900+dt.getYear(), Calendar.JANUARY, 1)
      [selectedFile: params.fileNames
              , cells:cells
              , cell:cellNumber
              , cellid:cellid
              , sequenceList:[]
              , tableModelList:queryService.modelListFromTableNames(cellNumber.toString())
              , model:model
              , thisYear: 1900+dt.getYear()
              , fromDate:dt1.getTime()
              , toDate:dt
      ]
    }

    def renderReadingCounts = {
      def seqs = params.sequenceList.tokenize(",")
      def from = parseDate(params.from_year,params.from_month,params.from_day)
      def to = parseDate(params.to_year,params.to_month,params.to_day)
      def cid = params.cell
      Cell c = Cell.get(cid)
      def modelList = queryService.modelListFromCellId(cid)
      def tableModelList = queryService.modelListFromTableNames(""+c.cellNumber)
      def nbrDays = to-from
      render (template:"readingCounts"
              , model: [
                        dayCount: nbrDays*seqs.size()
                      , estimatedCount: nbrDays*seqs.size()*24*4
                      , modelList: modelList
                      , model: params.model
                      , tableModelList:tableModelList
                      , cellNumber:c.cellNumber
                      , cellid:cid
                      , sequenceList:params.sequencesSelected?:[]
                      ]
      )
    }

    def renderModels = {
println "renderModels params: ${params}"
      def cid = params.cell
      Cell c = Cell.get(cid)
      def modelList = queryService.modelListFromCellId(cid)
      def tableModelList = queryService.modelListFromTableNames(""+c.cellNumber)
      def fromDate = c.constructionEndedDate - 30
      def toDate = c.demolishedDate?:new Date()
//      java.util.Calendar cal = java.util.Calendar.getInstance()
//      if (fromDate)
//        cal.setTime(fromDate)
//      def sfdate = "${cal.get(Calendar.MONTH)}/${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.YEAR)}"
      render (template:"modelsForChosenCell"
              , model: [modelList: modelList
                      , tableModelList:tableModelList
                      , cellNumber:c.cellNumber
                      , cellid:cid
                      , sequenceList:params.sequenceList?:[]
                      , fromDate: fromDate
                      , toDate:   toDate 
                      ] )
      }

      def renderSequenceNumbers = {   // see _modelsForChosenCell.gsp
//            println "renderSequenceNumbers params: ${params}"
        def cellId = Long.parseLong(params.cell)
        Cell c = Cell.get(cellId)
        def table = tableName(params.model)
        java.sql.Date fdate
        java.sql.Date tdate
        def from
        def to
        def sfdate = "${params.from_month}/${params.from_day}/${params.from_year}"
        def stdate = "${params.to_month}/${params.to_day}/${params.to_year}"
        def msg = ""
        try {
          from = parseDate(params.from_year,params.from_month,params.from_day)
          to = parseDate(params.to_year,params.to_month,params.to_day)
          fdate = new java.sql.Date(from.getTime())
          tdate = new java.sql.Date(to.getTime())
        } catch (Exception e) {
          msg = "Exception, ${e}, in date ${params.from_year},${params.from_month},${params.from_day}, ${params.to_year},${params.to_month},${params.to_day}, conversion: $params"
          log.error (msg)
        }
        def seqs = []
        def lc = us.mn.state.dot.mnroad.Lane.createCriteria()
        def lanes = lc.list(){
          eq ("cell",c)
        }
        lanes.each { lane ->
          lane.layers.each { layer ->
            layer.sensors.each { sensor ->
              if (sensor.sensorModel.model == params.model && !seqs.contains(sensor.seq)) {
                seqs << sensor.seq
              }
            }
          }
        }
        if (seqs.size() == 0) {
          if (!msg)
            msg = "No ${params.model} sensors found in cell ${c.cellNumber} id(${params.cell}) between ${sfdate} and ${stdate}."
        } else {
          seqs.sort()
        }
        render (template:"sequencesForChosenCell"
          , model: [sequenceList:seqs
                , msg: msg
                , cellId: cellId
                , cellNumber: c.cellNumber
                , model: params.model
                , fromDate: sfdate
                , toDate: stdate
                , estimatedCount:0
                ]
        )
      }

      java.util.Date parseDate(String yr, String mo, String day) {
        int y = Integer.parseInt(yr)
        int m = Integer.parseInt(mo)
        int d = Integer.parseInt(day)
        java.util.Calendar c = java.util.Calendar.getInstance()
        c.set(y,m-1,d)
        return c.getTime()
      }

      // temporary until queryService.sensorSeqFromSensorCounts can get its data from Cell/Lane/Layer/Sensor
      def sensorSeqList = { table ->
        grailsApplication.config.largeTables.contains(valueTableSeqs).get(table)
      }

      def tableName = { model ->
        def table = "MNR.${model}_VALUES"
        if (grailsApplication.config.largeTables.contains(model))
          table += "_ALL"
        return table
      }

      def dailyMeanValueLineCharts = {
        if (!params.model) { //table name issue
          flash.message = "Model must be selected."
          redirect(action:index, params:[cell:params.cell.id])
          return
        }

        if (!params.sequence) { //table name issue
          flash.message = "One or more sensors must be selected."
          redirect(action:index, params:[cell:params.cell.id, model:params.model])
          return
        }
//          use(groovy.time.TimeCategory) {
//              def duration = params.toDate - params.fromDate
//              println "${duration.days} days, ${duration.hours} hours."
//              if (duration.months > 12) {
//                flash.message = "Please choose a duration of 12 months or less."
//                redirect(action:index, params:[cell:params.cell, model:params.model, toDate:params.toDate, fromDate:params.fromDate])
//                return
//              }
//          }
//            println  "dailyMeanValueLineCharts \n\tfromDate: ${params.fromDate_month}/${params.fromDate_day}/${params.fromDate_year}\n\ttoDate: ${params.toDate_month}/${params.toDate_day}/${params.toDate_year}"


        Cell c = Cell.get(Long.parseLong(params.cell.id))
        [ cell:c.cellNumber
        , sequences:params.sequence
        , model: params.model
        , fromDate: "${params.fromDate_month}/${params.fromDate_day}/${params.fromDate_year}".toString()
        , toDate: "${params.toDate_month}/${params.toDate_day}/${params.toDate_year}".toString()
        , description: params.description
        ]
      }

      def dailyValueLineCharts = {
        if (!params.model) { //table name issue
          flash.message = "Model must be selected."
          redirect(action:index, params:[cell:params.cell.id])
          return
        }

        if (!params.sequence) { //table name issue
          flash.message = "One or more sensors must be selected."
          redirect(action:index, params:[cell:params.cell.id, model:params.model])
          return
        }
//          use(groovy.time.TimeCategory) {
//              def duration = params.toDate - params.fromDate
//              println "${duration.days} days, ${duration.hours} hours."
//              if (duration.months > 12) {
//                flash.message = "Please choose a duration of 12 months or less."
//                redirect(action:index, params:[cell:params.cell, model:params.model, toDate:params.toDate, fromDate:params.fromDate])
//                return
//              }
//          }  //expected # points: (params.toDate-params.fromDate)*params.sequence.size()*24*4
//            println  "dailyValueLineCharts \n\t$params}"

        def nbrDays = params.toDate-params.fromDate
        Cell c = Cell.get(Long.parseLong(params.cell.id))
        [ cell:c.cellNumber
        , sequences:params.sequence
        , model: params.model
        , fromDate: "${params.fromDate_month}/${params.fromDate_day}/${params.fromDate_year}".toString()
        , toDate: "${params.toDate_month}/${params.toDate_day}/${params.toDate_year}".toString()
        , description: params.description
        , dayCount:(nbrDays)*params.sequence.size()
        , estimatedCount:(nbrDays)*params.sequence.size()*24*4
        ]
      }

      def dailyMeanValueLineData = {
        /*
        see <g:actionSubmit class="upload" value="Graph Mean Daily Value" action="dailyMeanValueLineCharts"/> in index.gsp
        Parameters get here via circuitous path:
        index.gsp form -> g:actionSubmit action="dailyMeanValueLineCharts" -> dailyMeanValueLineCharts closure
          -> dailyMeanValueLineCharts.gsp -> google.visualization.Query("${createLink(action: 'dailyMeanValueLineData' params
         */
            println "dailyMeanValueLineData\n\t${params}"
        def model = params.model
        def map = [:]
        String q = ""
        if (queryService.hasTable(model + '_VALUES')) {
          try {
            q = getDailyMeanValueQuery(params, model)
            map = queryService.sensorData(q, model
              , Integer.parseInt(params.cellnumber)
              , MrUtils.getFormattedDate(params.fromDate,"MM/dd/yyyy")
              , MrUtils.getFormattedDate(params.toDate,"MM/dd/yyyy")
            )
          } catch (SQLException sqle) {
            flash.message = "Query: $q, ${sqle.message}."
            println "query: $q"
            println "query params: ${params}"
            redirect(action:index)
          }
        } else { //table name issue
          flash.message = "Table for sensor model ${model} not found"
          redirect(action:index)
        }
        render "google.visualization.Query.setResponse(" + (map.googleVisualizationMap as JSON) + ")"
      }

      def dailyValueLineData = {
        /*
        see <g:actionSubmit class="upload" value="Graph Mean Daily Value" action="dailyMeanValueLineCharts"/> in index.gsp
        Parameters get here via circuitous path:
        index.gsp form -> g:actionSubmit action="dailyMeanValueLineCharts" -> dailyMeanValueLineCharts closure
          -> dailyMeanValueLineCharts.gsp -> google.visualization.Query("${createLink(action: 'dailyMeanValueLineData' params
         */
        println "dailyValueLineData\n\t${params}"
        def model = params.model
        def map = [:]
        if (queryService.hasTable(model + '_VALUES')) {
          try {
          String q = getValueQuery(params, model)
          println "query: $q"
          println "query params: ${params}"
          map = queryService.sensorData(q, model
            , Integer.parseInt(params.cellnumber)
            , MrUtils.getFormattedDate(params.fromDate,"MM/dd/yyyy")
            , MrUtils.getFormattedDate(params.toDate,"MM/dd/yyyy")
          )
          } catch (SQLException sqle) {
            flash.message = "Query: $q, ${sqle.message}."
            redirect(action:index)
          }
        } else { //table name issue
          flash.message = "Table for sensor model ${model} not found"
          redirect(action:index)
        }
        render "google.visualization.Query.setResponse(" + (map.googleVisualizationMap as JSON) + ")"
      }

      private String getDailyMeanValueQuery(Map params, model) {
        def schema = 'MNR' // Why is owner other than 'MNR'? e.g. EC_VALUES owner is CHAD1BRU
        def q = "SELECT DAY, SEQ, AVG(VALUE) VALUE FROM "//${schema}."
        q += tableName(model)
        q += " WHERE CELL = ? and DAY BETWEEN ? AND ? "
        if (params.sequences) {
          if (params.sequences instanceof String)
            q += "and seq in (" + params.sequences + ")"
          else
            q += "and seq in (" + params.sequences.join(',') + ")"
        }
        q += " group by day, seq order by seq, day"
        return q.toUpperCase()
      }

      private String getValueQuery(Map params, model) {
        def schema = 'MNR' // Why is owner other than 'MNR'? e.g. EC_VALUES owner is CHAD1BRU
        def q = "SELECT DAY, HOUR, QHR, MINUTE, SEQ, VALUE FROM "//${schema}."
        q += tableName(model)
        q += " WHERE CELL = ? and DAY BETWEEN ? AND ? "
        if (params.sequences) {
          if (params.sequences instanceof String)
            q += "and seq in (" + params.sequences + ")"
          else
            q += "and seq in (" + params.sequences.join(',') + ")"
        }
        q += "ORDER by SEQ, DAY, HOUR, QHR, MINUTE, SEQ,VALUE"
        return q.toUpperCase()
      }
}
