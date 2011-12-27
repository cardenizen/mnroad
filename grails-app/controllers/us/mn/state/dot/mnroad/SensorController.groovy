package us.mn.state.dot.mnroad

import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.MrUtils
import us.mn.state.dot.mnroad.Sensor
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.SensorEvaluation
import groovy.sql.Sql
import org.hibernate.exception.ConstraintViolationException

class SensorController extends ControllerBase {
    
  def dataSource
  def queryService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = { // invoked via list or list sort
//      println "'SensorController.list': $params"
      def cell
      def cellId
      params.max = Math.min( params.max ? params.max.toInteger() : 24,  100)

      def cl = Cell.list(sort:'cellNumber')
      if (params.maxrows) {
        params.max = params.maxrows
      } else {
        cellId = params.cellId
        if (!cellId)
          cellId = params.filterId?params.filterId:cl.toArray()[0].id
        cell = Cell.get(cellId)
      }
      def total = 0
      def sl = []
      // cell should never be null as it is taken from a list of cells!
        def offset = params.offset?:0
        sl = queryService.sensorsForCellNumber(cell.cellNumber,offset.toInteger()+1,offset.toInteger()+params.max.toInteger())
        total = queryService.sensorsForCellNumberCount(cell.cellNumber)
      [ cellNumber:cell.cellNumber
      , sensorInstanceList: sl
      , max:params.max
      , sensorInstanceTotal:total
      , filterId:cellId 
      , celllessSensors : queryService.sensorsInNoCell()
      , cells:cl]
    }

    def orphanedSensors = {
      [celllessSensors : queryService.sensorsInNoCell()]      
    }

    def show = {
//      println "'SensorController.show': $params"
        def sensorInstance = Sensor.get(params.id)
        if (!sensorInstance) {
            flash.message = "Sensor not found with id ${params.id}"
            redirect(action: "list")
        }
        else {
          def parms = [:]
          parms.put('sensorInstance', sensorInstance)
          parms.put('cellId', params.cellId)
          parms.put('cellController', params.cellController)
          return parms
        }
    }

    def edit = {
//      println "'SensorController.edit': $params"
        def sensorInstance = Sensor.get( params.id )
        if(!sensorInstance) {
            flash.message = "Sensor not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          def sList = []
          def cn = 0
          if (!sensorInstance.layer) {
            def scn = sensorInstance.sensorId.size()==7?sensorInstance.sensorId[0..1]:sensorInstance.sensorId[0..2]
            cn = Integer.parseInt(scn)
          } else {
            cn = sensorInstance.layer.lane.cell.cellNumber
          }
          def cells
          if (sensorInstance.stationFt) {
            cells = Cell.withCriteria {
          or {
            and {
              ge('startStation', sensorInstance.stationFt)
              le('endStation', sensorInstance.stationFt)
            }
            and {
              ge('endStation', sensorInstance.stationFt)
              le('startStation', sensorInstance.stationFt)
            }
          }
        }
          } else {
            cells = Cell.findAllByCellNumber(cn)
          }
        def targetOffsetRef = sensorInstance.offsetFt < 0.0 ? '-' : '+'
        cells.collect {
          it.lanes.collect {
            if (it.offsetRef == targetOffsetRef)
            it.layers.collect{
              sList<<it
            }
          }
        }
        return [ sensorInstance : sensorInstance, layersInLane: sList ]
        }
    }

    def update = {
//      println "'SensorController.update': $params"
      // paramsDateCheck - Workaround for http://jira.codehaus.org/browse/GRAILS-1793
      // enables saving a null date
        def dateError = paramsDateCheck(params)
        def sensorInstance = Sensor.get( params.id )
        if(sensorInstance) {
            sensorInstance.properties = params

          if (!sensorInstance.validate()) {
            def sList = []
              def cells = Cell.findAllByCellNumber(sensorInstance.layer.lane.cell.cellNumber)
              cells.collect {aCell ->
                aCell.lanes.collect {aLane ->
                  aLane.layers.collect {
                    sList<<it
                  }
                }
              }
            render(view:'edit',model:[sensorInstance:sensorInstance, layersInLane: sList ])
          }
          else {
            def layerId = params.layer.id
            if (layerId && layerId != "null") {
            def lyr = Layer.get(Long.parseLong(params.layer.id))
            lyr.sensors.add(sensorInstance)
            sensorInstance.layer = lyr

            sensorInstance.lastUpdatedBy = getUserName()
            if(!sensorInstance.hasErrors() && sensorInstance.save()) {
                flash.message = "Sensor ${params.id} updated"
                redirect(action:show,id:sensorInstance.id)
            }
            else {
              def sList = []
                def cells = Cell.findAllByCellNumber(sensorInstance.layer.lane.cell.cellNumber)
                cells.collect {aCell ->
                  aCell.lanes.collect {aLane ->
                    aLane.layers.collect {
                      sList<<it
                    }
                  }
                }
                render(view:'edit',model:[sensorInstance:sensorInstance, layersInLane: sList])
            }
          }
            else {
              def sList = []
                def cells = Cell.findAllByCellNumber(sensorInstance.cell)
              cells.collect {aCell ->
                aCell.lanes.collect {aLane ->
                  aLane.layers.collect {
                      sList<<it
                    }
                  }
                }
              flash.message = "Layer must be selected."
              render(view:'edit',model:[sensorInstance:sensorInstance, layersInLane: sList])
            }
          }
        }
        else {
            flash.message = "Sensor not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def delete = {
//      println "'SensorController.delete': $params"
        def sensorInstance = Sensor.get(params.id)
        def cell = Cell.get(params.cellId)
        if (sensorInstance) {
            try {
              sensorInstance.delete(flush: true)
              flash.message = "Sensor ${params.id} deleted."// ${params}"
              def parms = [:]
              parms.put('filterId', params.cellId)
              parms.put('action', 'list')
              redirect(parms)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
              if (e.getMessage().contains("not-null property references a null or transient value: us.mn.state.dot.mnroad.Sensor.layer")) {
                // layer is null in DB so go around hibernate and delete.
                if (forceDelete(sensorInstance)) {
                  flash.message = "Sensor ${params.id} deleted."// ${params}"
                  def parms = [:]
                  parms.put('id', params.cellId)
                  parms.put('action', 'show')
                  parms.put('controller', params.cellController)
                  redirect(parms)
//  will throw a     }catch (org.hibernate.StaleObjectStateException sose) {
                  return
                }
              }
              flash.message = "Sensor ${params.id} not deleted"
              redirect(action: "show", id: params.id)
			}
        }
        else {
            flash.message = "Sensor not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def forceDelete(def sensorInstance) {
      Sql sql = new groovy.sql.Sql(dataSource)
      def rc = false
      try {
        def nUpdated = 0
        sensorInstance.evaluations.each {
          def se_q = "delete from mnr.sensor_evaluation where id = $it.id"
          nUpdated = sql.executeUpdate(se_q)
          println "${nUpdated} deleted."
        }
        def delete_q = "delete from mnr.sensor where id = $sensorInstance.id"
        nUpdated = sql.executeUpdate(delete_q)
        println "${nUpdated} deleted."
      } catch (java.sql.SQLSyntaxErrorException sqlse) {
        log.error sqlse.message
        return rc
      } catch (java.sql.SQLException sqle) {
        log.error sqle.message
        return rc
      }
      return true
    }

    def create = {
        def sensorInstance = new Sensor()
        Cell c = Cell.get(Long.parseLong(params.id))
        def layers = []
        if (c) {
          c.lanes.collect {
            it.layers.collect{
              layers<<it
            }
          }
          if (c.demolishedDate) {
            flash.message = "Warning, ${c} has been removed."
          }
        }
        sensorInstance.properties = params
//        sensorInstance.cell = c.cellNumber
        return ['sensorInstance':sensorInstance, 'layersInCell':layers, 'cellId':c.id]
    }

    def save = {
      def sensorInstance = new Sensor()
// Workaround for http://jira.codehaus.org/browse/GRAILS-1793
      def excludes = []
      if ((!params.dateRemoved_month) ||(!params.dateRemoved_day)||(!params.dateRemoved_year))
        excludes << "dateRemoved"
      if ((!params.dateInstalled_month) ||(!params.dateInstalled_day)||(!params.dateInstalled_year))
        excludes << "dateInstalled"
      bindData(sensorInstance, params, excludes)
        sensorInstance.createdBy = getUserName()
        sensorInstance.lastUpdatedBy = getUserName()
//        sensorInstance.model = sensorInstance.sensorModel.model // until model can be removed
        if(!sensorInstance.hasErrors() && sensorInstance.save()) {
            flash.message = "Sensor ${sensorInstance.id} created"
            redirect(action:show,id:sensorInstance.id)
        }
        else {
          Cell c = Cell.get(Long.parseLong(params.cellId))
          def layers = []
          c.lanes.collect {
            it.layers.collect{
              layers<<it
            }
          }
//SEQ - java.sql.BatchUpdateException: ORA-01438: value larger than specified precision allowed for this column
            render(view:'create',model:[sensorInstance:sensorInstance,'layer.id':params.layer.id, 'layersInCell':layers, 'cellNumber':params.cell, 'cellId':params.cellId])
        }
    }
    
    def updateSensorEvaluations = {
//      println "Sensor updateSensorEvaluations: ${params}"
      def senIds = params["Group.sensors"]
      int numCreated = 0
      if (senIds && params.method) {
        //Date dt = MrUtils.dateStructToDate(params.evalDate_year, params.evalDate_month,params.evalDate_day)
        SimpleDateFormat sdf2 =  new SimpleDateFormat("yyyy/mm/dd")
        String ds = ""+params.evalDate_year +"/"+ params.evalDate_month+"/"+ params.evalDate_day
        Date dt = sdf2.parse(ds)
        List msl = []
        List args = []
        // prevent a single string from being evaluated as a list
        if (senIds instanceof String)
          args << senIds
        else
          args = senIds
        // Process the list
        args.each {
          Sensor s = Sensor.get(Long.parseLong(it))
          msl += "${s.sensorModel.model}-${s.seq.toString()}"
          SensorEvaluation se = new SensorEvaluation()
          se.dateComment = dt
          se.commentBy = getUserName()
          se.createdBy = getUserName()
          se.evaluationMethod = params.method
          se.reason = params.reason
          se.result = params.result
          se.sensor = s
          s.evaluations += se
          if (!s.validate()) {
            def errorMessages = s.errors.allErrors.collect { g.message(error:it) }
            flash.message = errorMessages
//            println "Failed validate: " + errorMessages
          }
          else {
            s.save(flush:true)
            numCreated++
          }
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MMM-yyyy" )
        if (numCreated > 0)
          flash.message = "${params.result} evaluation on ${sdf.format(dt)} added for sensors: ${msl}."
      }
      else {
        flash.message = "No sensors selected."
        if (!params.method) {
          flash.message = "Please choose an evaluation method."
        }
      }
      // send along the current Cell ID to redisplay sensors for that cell
      redirect(action:evaluate, id:params.id)
    }

    def evaluate = {
      List cellList = Cell.list([sort:'cellNumber'])
      def c
      def sensors = []
      def l = ""
      if (params.id) {
        c = Cell.get(params.id)
        if (c) {
          sensors = sensorsByCellId(c.id)
//          sensors = queryService.sensorsInCell(c)
          l = "Sensors in Cell ${c.cellNumber}"
//          def s = Sensor.createCriteria()
//          sensors = s.list(){
//            eq ("cell",c.cellNumber)
//            order ('cell','asc')
//            order ('model','asc')
//            order ('seq','asc')
//          }
        }
      }
      [cells:cellList, cell:c, legend:l, sensorList: sensors, id:params.id]
    }

    def filterSelected = {
//      println "'SensorController.filterSelected': $params"
      params.max = Math.min( params.max ? params.max.toInteger() : 12,  100)
      if (params.maxrows)
        params.max = params.maxrows
      def cid = params.cellId
      if (!cid) {
          cid = params.filterId
      }
      def cell = Cell.get(cid)
      def total = 0
      def sl = []
      if (!cell) {
        def ms = Sensor.createCriteria()
        sl = ms.list()
//          sl = ms.list(){
//          isNull ("cell")
//        }
        total = sl.size()
      } else {
        params["cellNumber"] = cell.cellNumber
        params["filterId"] = cid
        def offset = params.offset?:0
        sl = queryService.sensorsForCellNumber(cell.cellNumber,offset.toInteger()+1,offset.toInteger()+params.max.toInteger())
        total = queryService.sensorsForCellNumberCount(cell.cellNumber)
      }
      if ((!cell || params.cellId) && !params.sort){
        // Arrived here by selecting from the drop-down named 'filterId'
        // which passes the id of the selected item using param name 'cellId' (same as filterId)
        render(template:"/templates/common/selectedSensorsTemplate",
          model:[sensorInstanceList:sl
            , max:params.max, offset:0, sensorInstanceTotal:total
                  , cellNumber:params.cellNumber , filterId:params.cellId
            ]
        )
      }
      else {
        // Arrived here via column sort after first selecting from drop-down list
        //
        def cl = Cell.list(sort:'cellNumber')
        render(view:"list",
          model:[sensorInstanceList:sl
            , max:params.max, offset:0, sensorInstanceTotal:total
            , cellNumber:params.cellNumber ,filterId:params.filterId
            , cells:cl
          ]
        )
      }
    }

    List sensorsByCellId(Long cellid) {
      def sl = []
      def cell = Cell.get(cellid)
      cell.lanes.each { lane ->
        lane.layers.each { layer ->
          layer.sensors.each { sensor ->
            sl.add(sensor)
          }
        }
      }
      sl
        .sort(){a,b->a.sensorDepthIn.equals(b.sensorDepthIn)? 0: (a.sensorDepthIn)<(b.sensorDepthIn)? -1: 1}
        .sort(){a,b->a.sensorModel.equals(b.sensorModel)? 0: (a.sensorModel)<(b.sensorModel)? -1: 1}
    }

    def newSensor={
      List cellList = Cell.list([sort:'cellNumber'])
      def c
      def sensors = []
      def l = ""
      if (params.id) {
        c = Cell.get(params.id)
        if (c) {
          l = "Sensors in Cell ${c.cellNumber}"
          def s = Sensor.createCriteria()
          sensors = s.list(){
            eq ("cell",c.cellNumber)
            order ('cell','asc')
            order ('model','asc')
            order ('seq','asc')
          }
        }
      }
      [cells:cellList, cell:c, legend:l, sensorList: sensors, id:params.id]
    }
}
