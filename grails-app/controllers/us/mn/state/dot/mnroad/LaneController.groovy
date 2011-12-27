package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.Lane
import us.mn.state.dot.mnroad.Cell

class LaneController extends ControllerBase {

    def list = {
      prepareListParams()
      [laneInstanceList: Lane.list(params)]
    }

    def create = {
        def laneInstance = new Lane()
        laneInstance.properties = params
        Cell acell = Cell.get(params.id)
        laneInstance.cell = acell
        return ['laneInstance':laneInstance]
    }

    def laneService

    def show = {
        def laneInstance = Lane.get(params.id)
        if (!laneInstance) {
            flash.message = "Lane not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          def bottomLayer
          if (laneInstance.layers.size()>0) {
            bottomLayer = laneInstance.layers.toArray()[0]
          }
          def sensorsInLayer = []
          if (bottomLayer) {
            sensorsInLayer = bottomLayer.sensors?bottomLayer.sensors:[]
          }
          def sensors = laneService.sensorsInLane(laneInstance)
/* Omit until DISTRESS_* table are converted
          def tableNames = []
          def tableNamesAndCounts = ""
          def distressTableNames = grailsApplication.config.distressTables
          distressTableNames.each { tableName   ->
            def dids = laneService.getDistressIdsForLane(laneInstance, tableName)
            if (dids.size() > 0) {
              //distresses.put(tableName, dids)
              tableNames << tableName
              tableNamesAndCounts += "${tableName} (${dids.size()}), "
            }
          }
          // chop off the last two characters - ", " if necessary
          def tnc = tableNamesAndCounts.endsWith(", ")?tableNamesAndCounts[0..-3]:""
*/
          return [ laneInstance : laneInstance
                  , cellId:params.cellId
                  , sensorList:sensors
                  , selectedLayer:bottomLayer?.id
                  , availableSensors:sensors - sensorsInLayer
                  , layerSensors:sensorsInLayer
//                  , tableNames: tableNames, tableNamesAndCounts: tnc
          ]
        }
    }

    def edit = {
        def laneInstance = Lane.get(params.id)
        if (!laneInstance) {
            flash.message = "Lane not found with id ${params.id}"
            redirect(action:"show", id:params.id)
        }
        else {
          // Added cellId for use in delete.  due to bug (see http://jira.codehaus.org/browse/GRAILS-4386)
            return [ laneInstance : laneInstance, cellId:params.cellId ]
        }
    }

    def update = {
      def laneInstance = Lane.get(params.id)
      if (laneInstance) {
        laneInstance.properties = params
        laneInstance.lastUpdatedBy = getUserName()
        boolean result = false
        Lane.withTransaction { status ->
          result = laneInstance.save(flush:true)
        }
        if (result) {
          flash.message = "Lane ${params.id} updated"
          redirect(action:show,id:laneInstance.id)
        }
      }
      else {
        flash.message = "Lane not found with id ${params.id}"
        redirect(action: "list")
      }
    }

    def delete = {
        def laneInstance = Lane.get( params.id )
        if(laneInstance) {
          String ctrlr = laneInstance.cell.controller()
          Integer cellId = laneInstance.cell.id
          def cellNumber = laneInstance.cell.cellNumber

          if (cellNumber) {
            laneInstance.cell.lanes -= laneInstance
            laneInstance.delete(flush:true)
          }
          if (laneInstance.cell.lanes.size() > 0) {
              flash.message = "No lanes found for Cell ${cellNumber}"
              redirect(controller:ctrlr, action:"edit", id:cellId)
          }
          else {
            flash.message = "Lane ${params.id} deleted"
            redirect(controller:ctrlr, action:"show", id:cellId)
          }
        }
        else {
            flash.message = "Lane not found with id ${params.id}"
            redirect(action: "list")
        }
    }

    def save = {
        def laneInstance = new Lane(params)
        Cell acell = Cell.get(params.cellid)
        laneInstance.laneNum = acell.lanes.size() + 1
        laneInstance.cell = acell
        laneInstance.createdBy = getUserName()
        laneInstance.lastUpdatedBy = getUserName()
        if(!laneInstance.hasErrors() && laneInstance.save(flush:true)) {
            flash.message = "Lane ${laneInstance.id} created"
            redirect(action:show,id:laneInstance.id)
        }
        else {
            render(view:'create',model:[laneInstance:laneInstance])
        }
    }

/* Omit until DISTRESS_* table are converted
    def dataSource

    def attachDistress = {
      println "attachDistress: ${params}"
      def ln = Lane.get( params.id )
      if(!ln) {
          flash.message = "Lane not found with id ${params.id}"
          redirect(action:list)
      }
      def distressTableNames = grailsApplication.config.distressTables
      def totalRowsAttached = 0
      def msg = ""
      distressTableNames.each { tableName ->
        def rowsAttached = 0
        def ids = laneService.getDistressIdsForLane(ln, tableName)
        if (ids.size() > 0) {
          Sql sql = new groovy.sql.Sql(dataSource)
          ids.keySet().each { key ->
            String insertIntoTable = 'insert into distress (ID,VERSION,CREATED_BY,LAST_UPDATED_BY,LAST_UPDATED,DATE_CREATED,LANE_ID) values (?,?,?,?,?,?,?)'
            java.sql.Date sysdate = new java.sql.Date((new Date()).getTime())
            boolean b = sql.execute( insertIntoTable ,[key, 1, 'carr1den', 'carr1den', sysdate, sysdate, ids.get(key)] )
            if (!b) {
              rowsAttached++
            }
          }
        }
        if (rowsAttached>0) {
          msg += "${tableName}(${rowsAttached}), "
          totalRowsAttached += rowsAttached
        }
      }
      msg = "Total added: ${totalRowsAttached} - " + msg
      flash.message = msg[0..-3]
      redirect(action:show, params:["id":params.id])
    }


    def attachAllDistresses = {
      def distressTableNames = grailsApplication.config.distressTables
      def msg = laneService.attachDistresses(distressTableNames)
      flash.message = msg
      redirect(action:show, params:["id":params.id])
    }
*/

}
