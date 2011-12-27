package us.mn.state.dot.mnroad

import java.text.SimpleDateFormat
import us.mn.state.dot.mnroad.Layer
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.Sensor

class LayerController extends ControllerBase {

    def queryService
    def laneService
    def dbMetadataService
    def sessionFactory

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
      prepareListParams() // max, order, and sort
      [layerInstanceList: Layer.list(params), layerInstanceTotal: Layer.count()]
    }

    def create = {
      def layerInstance = new Layer()
      layerInstance.properties = params
      def lanenum = params["laneid"]
      if (lanenum != null) {
        Long laneid = Long.parseLong(lanenum)
        def ln = Lane.get(laneid)
        layerInstance.lane = ln
        // Find the date of the most recent layer
        Date lastWorkDate = null
        ln?.layers.each {
          def thisDate = (it.constructEndDate == null?it.constructStartDate:it.constructEndDate)
          if (lastWorkDate == null) {
            lastWorkDate = thisDate
          }
          else if (lastWorkDate <= thisDate)   {
            lastWorkDate = thisDate
          }
        }
        if (lastWorkDate == null) {
          layerInstance.constructEndDate = layerInstance.constructStartDate = ln.cell.constructionEndedDate
        }
        else {
          layerInstance.constructStartDate = layerInstance.constructEndDate = lastWorkDate
        }
      }
      layerInstance.createdBy = getUserName()
      layerInstance.lastUpdatedBy = getUserName()
      return ['layerInstance':layerInstance]
    }

    def save = {
      def parentLaneId = params["laneid"]
      Long plid = Long.parseLong(parentLaneId).toLong()
      def theLane = Lane.get(parentLaneId)
      params.put('lane',theLane)
      def layerInstance = new Layer(params)
      if (layerInstance.constructEndDate == null) {
        if (layerInstance.constructStartDate == null) {
          layerInstance.constructEndDate = layerInstance.constructStartDate = layerInstance.lane.cell.constructionEndedDate
        }
        else {
          layerInstance.constructEndDate = layerInstance.constructStartDate
        }
      }
      if (params["engToMetric"]=="on") {
        layerInstance.toMms()
      }
      int layersCreated = 0
      layerInstance.lane = theLane
      layerInstance.layerNum = theLane.nextLayerNum()
      layerInstance.createdBy = getUserName()
      def newlayer = layerInstance.save(flush:true)
      if(!layerInstance.hasErrors() && newlayer) {
        theLane.layers += newlayer
        layersCreated++
        if (params["addToOtherRoadwayLanes"]=="on" || params["addToOtherShoulderLane"]=="on") {
            layerInstance.lane.cell.lanes.each { aLane ->
            if (aLane.id != plid) { // do not add to the same layerInstance
              if ((params["addToOtherRoadwayLanes"]=="on") || (params["addToOtherShoulderLane"]=="on") ) {
                if ((params["addToOtherRoadwayLanes"]=="on") && aLane.isRoadwayLane()) {
                  def anotherLayer = addLayer(params, aLane)
                  if (anotherLayer) {
                    layersCreated++
                    }
                  }
                else if ((params["addToOtherShoulderLane"]=="on") && !aLane.isRoadwayLane()) {
                  def anotherLayer = addLayer(params, aLane)
                  if (anotherLayer) {
                    layersCreated++
                    }
                  }
                }
              }
            }
            flash.message = "${layersCreated} Layers created"
            redirect(controller:'lane',action:show,id:layerInstance?.lane?.id)
          }
          else {
          flash.message = "Layer ${layerInstance.id} created"
          redirect(controller:layerInstance?.lane?.cell?.controller(),action:show,id:layerInstance?.lane?.cell?.id)
          }
      }
      else {
          render(view:'create',model:[layer:layerInstance, laneid:layerInstance.lane.id])
      }
    }

    def mat = {
      //println "${params}"
      def layerInstance = Layer.get(getId(params.id))
      if (params.up) {
        def id = Layer.get(params.up).under()
        if (id)
          layerInstance = Layer.get(id)
        else
          layerInstance = Layer.get(params.up)
      }
      else if (params.down) {
        def id = Layer.get(params.down).over()
        if (id)
          layerInstance = Layer.get(id)
        else
          layerInstance = Layer.get(params.down)
      }

      def result = ""
      def layersText = layerInstance.lane.layersToText()
      layersText.each {
        result += "$it"
      }

      def msc = MaterialSample.createCriteria()
      def linkedSamples = []
      def linkedToSomelayer= msc.list(){
        eq ("cell",layerInstance.lane.cell.cellNumber)
        isNotNull('layer')
        order ('mnroadId')
      }
      linkedToSomelayer.each {
        if (it.layer.id == layerInstance.id)
          linkedSamples << it
      }

      def msc1 = MaterialSample.createCriteria()
      def availableSamples
      if (layerInstance.lane.offsetRef == "-") {
        availableSamples= msc1.list(){
          eq ("cell",layerInstance.lane.cell.cellNumber)
          isNull('layer')
          le ("offset", new Double(0.0))
          order ('mnroadId')
        }
      } else {
        availableSamples= msc1.list(){
          eq ("cell",layerInstance.lane.cell.cellNumber)
          isNull('layer')
          gt ("offset", new Double(0.0))
          order ('mnroadId')
        }
      }
      return [ layerInstance : layerInstance
              , result:result
              , availableSamples:availableSamples
              , linkedSamples:linkedSamples
              ]
    }

    def updateSamples = {
//      println "${params}"
      def layerInstance = Layer.get(getId(params.id))

      def linksToMake = []
      if (params.linkedSamples) {
        if (params.linkedSamples instanceof java.lang.String) {
          linksToMake[0]=MaterialSample.get(params.linkedSamples.toInteger())
        } else {
          linksToMake=MaterialSample.getAll(params.linkedSamples*.toInteger())
        }
      }
      def msc = MaterialSample.createCriteria()
      def linkedSamples = msc.list(){
        eq ("cell",layerInstance.lane.cell.cellNumber)
        isNotNull('layer')
      }
      def addUpdts = 0
      def msgs = []
      linksToMake.each { matSample ->
        //def msid = matSample.id
        if (!linkedSamples.contains(matSample)) {
          matSample.layer = layerInstance
          layerInstance.materialSamples.add(matSample)
// Only the "higher level" class need be validated since
// validation "cascades"
          def lv = layerInstance.validate()
          if (!lv) {
            layerInstance.errors.allErrors.each {
              msgs << "MatSample ${matSample.mnroadId} update failed! Field [${it.getField()}] with value [${it.getRejectedValue()}] is invalid."
            }
          }
            matSample.layer.save(flush:true)
            layerInstance.save(flush:true)
            addUpdts++
        }
        def li = Layer.get(layerInstance.id)
      }

      def removeUpdts = 0
      def linksToBreak = []
      if (params.availableSamples) {
        if (params.availableSamples instanceof java.lang.String) {
          linksToBreak[0]=MaterialSample.get(params.availableSamples.toInteger())
        } else {
          linksToBreak=MaterialSample.getAll(params.availableSamples*.toInteger())
        }
      }
      linksToBreak.each {
        if (it.layer) {
          it.layer = null
          it.save()
          removeUpdts++          
        }
      }
      def valmsgs = (msgs.size()>0)?msgs.join("\n"):""
      flash.message = "${valmsgs} ${addUpdts} linked, ${removeUpdts} un-linked."
      redirect(action: "mat",id:layerInstance.id)
    }
/*

select cell, count(*) from mnr.mat_samples
group by cell
order by cell

select cell cell_number, count(*) mat_samples_cnt from mnr.mat_samples
where cell is not null and cell > 0
group by cell
order by cell

select count(*) from mnr.MAT_SAMPLES where
SAMPLE_DATE is null
and (cell is null or cell = 0)

SELECT unique msc.cell_number FROM (
select cell cell_number, count(*) mat_samples_cnt from mnr.mat_samples
where cell is not null and cell > 0
group by cell
order by cell
) msc join mnr.cell c on c.cell_number=msc.cell_number
 */
    def show = {
        def layerInstance = Layer.get(getId(params.id))
        if (params.up) {
          def id = Layer.get(params.up).under()
          if (id)
            layerInstance = Layer.get(id)
          else
            layerInstance = Layer.get(params.up)
        }
        else if (params.down) {
          def id = Layer.get(params.down).over()
          if (id)
            layerInstance = Layer.get(id)
          else
            layerInstance = Layer.get(params.down)
        }
        if (!layerInstance) {
            flash.message = "Layer not found with id ${params.id}"
            redirect(action: "list")
        }
        else {
          def availableSensorsInLane = laneService.sensorsInLane(layerInstance.lane)
          def sill = Sensor.createCriteria()
          def sensorsInLayerList = sill.list(){
            eq ("layer",layerInstance)
            order ('sensorModel')
            order ('seq')
            order ('sensorDepthIn')
          }
          def sensorsInLayer = sensorsInLayerList?:[]
          def result = ""
          def layersText = layerInstance.lane.layersToText()
          layersText.each {
            result += "$it"
          }
          def ts = laneService.sqlSensorsInLane_string(layerInstance.lane)
          def cs = laneService.hqlSensorsInLane_string(layerInstance.lane)
          if (ts != cs) {
            flash.message = "Sensor cell/model ${ts} disagrees with Cell/SensorModel ${cs}"
          }

          return [ layerInstance : layerInstance, 'selectedLayer':params.id
                  ,'availableSensors':availableSensorsInLane - sensorsInLayer
                  ,'layerSensors':sensorsInLayer
                  , members:"In ${layerInstance.lane.name} layer ${layerInstance.layerNum}"
                  , nonMembers:"In Other Layers of ${layerInstance.lane.name}"
                  , amountRemaining:laneService.amountRemaining(layerInstance)
                  , result:result
                  ]
        }
    }

    def edit = {
        def layerInstance = Layer.get(params.id)
        if (!layerInstance) {
            flash.message = "Layer not found with id ${params.id}"
            redirect(action: "list")
        }
        else {
            return [layerInstance: layerInstance]
        }
    }

    def update = {
      def layerInstance = Layer.get(params.id)
      if (layerInstance) {
        if (params.version) {
            def version = params.version.toLong()
            if (layerInstance.version > version) {
              flash.message = "Another user has updated this Layer while you were editing"
                render(view: "edit", model: [layerInstance: layerInstance])
                return
            }
        }
        double thickness = layerInstance.thickness
        layerInstance.properties = params
        if (params["engToMetric"]=="on") {
          layerInstance.toMms()
        }
        int layersUpdated = 0
        layerInstance.lastUpdatedBy = getUserName()
        if (layerInstance.validate()) {
        def theLayer = layerInstance.save(flush:true)
        if(!layerInstance.hasErrors() && theLayer) {
          layersUpdated++
          if (params["modifyOtherRoadwayLanes"]=="on"
              || params["modifyOtherShoulderLane"]=="on") {
            Cell c = layerInstance.lane.getProperty("cell")
            for (aLane in c.lanes) {
              if ((params["modifyOtherRoadwayLanes"]=="on")
                || (params["modifyOtherShoulderLane"]=="on") ) {
                if ((params["modifyOtherRoadwayLanes"]=="on")
                    && aLane.isRoadwayLane()) {
                  for (aLayer in aLane.layers) {
                    if (aLayer.id != layerInstance.id && sameMaterialThicknessDates(aLayer,layerInstance,thickness)) {
                      Layer layerToUpdate = Layer.get(aLayer.id)
                      layerToUpdate.properties = params
                      if (params["engToMetric"]=="on") {
                        layerToUpdate.toMms()
                      }
                      layerToUpdate.lastUpdatedBy = getUserName()
                      if (layerToUpdate.save(flush:true) != null)
                        layersUpdated++;
                    }
                  }
                }
                else if ((params["modifyOtherShoulderLane"]=="on")
                    && !aLane.isRoadwayLane()) {
                  for (aLayer in aLane.layers) {
                    if (aLayer.id != layerInstance.id && sameMaterialThicknessDates(aLayer,layerInstance,thickness)) {
                      Layer layerToUpdate = Layer.get(aLayer.id)
                      layerToUpdate.properties = params
                      if (params["engToMetric"]=="on") {
                        layerToUpdate.toMms()
                      }
                      layerToUpdate.lastUpdatedBy = getUserName()
                      if (layerToUpdate.save(flush:true) != null)
                        layersUpdated++;
                    }
                  }
                }
              }
            }
            flash.message = "${layersUpdated} Layers updated"
              redirect(action:show,id:layerInstance.id)
            }
            else {
              flash.message = "Layer ${layerInstance.id} updated"
              redirect(controller:layerInstance?.lane?.cell?.controller(),action:show,id:layerInstance?.lane?.cell?.id)
            }
          }
          else {
            render(view:'edit',model:[layerInstance:layerInstance])
          }
        }
        else {
          flash.message = ""
          render(view:'edit',model:[layerInstance:layerInstance])
        }
       }
      else {
        flash.message = "Layer not found with id ${params.id}"
        redirect(action: "list")
      }
    }

    def delete = {
        def layerInstance = Layer.get(params.id)
        if (layerInstance) {
//          Long cellId = layerInstance.lane.cell.id
//          layerInstance.lane.layers -= layerInstance
//          layerInstance.delete()
//          flash.message = "Layer ${params.id} deleted"
//          response.sendRedirect(createLink(controller: "lane", action: 'edit', id:params.laneid))


          try {
              Long cellId = layerInstance.lane.cell.id
              layerInstance.lane.layers -= layerInstance
              layerInstance.delete(flush: true)
              flash.message = "Layer ${params.id} deleted"
              redirect(controller: "${layerInstance.lane.cell.controller()}", action: "show", params:["id":layerInstance.lane.cell.id])
          }
          catch (org.springframework.dao.DataIntegrityViolationException e) {
              flash.message = "Layer ${params.id} not deleted."
              redirect(action: "show", id: params.id)
          }



        }
        else {
            flash.message = "Layer not found with id ${params.id}"
            redirect(action:list)
        }
    }

    boolean sameMaterialThicknessDates(Layer lx, Layer ly, def t) {
      SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yy" )
      (lx.material == ly.material
        && lx.thickness == t
      && sdf.format(lx.constructStartDate) == sdf.format(ly.constructStartDate)
      && sdf.format(lx.constructEndDate) == sdf.format(ly.constructEndDate)
      )
    }

    private Object addLayer(Map params, aLane) {
      params.remove('lane')
      params.put('lane', aLane)
      def anotherLayer = new Layer(params)
      if (anotherLayer.constructEndDate == null) {
        if (anotherLayer.constructStartDate == null) {
          anotherLayer.constructEndDate = anotherLayer.constructStartDate = anotherLayer.lane.cell.constructionEndedDate
        }
        else {
          anotherLayer.constructEndDate = anotherLayer.constructStartDate
        }
      }
      if (params["engToMetric"] == "on") {
        anotherLayer.toMms()
      }
      anotherLayer.lane = aLane
      anotherLayer.layerNum = aLane.nextLayerNum()
      anotherLayer.createdBy = getUserName()
      aLane.layers += anotherLayer
      if (!anotherLayer.hasErrors() && anotherLayer.save(flush:true)) {
        aLane.save(flush:true)
        return anotherLayer
      }
      return null
    }


    def updateSensors = {
      println params
      String msg = ""
      Long lid = getId(params.id)
      def ps = []
      if (params['Group.sensors'] instanceof String)
        ps << params['Group.sensors']
      else
        ps = params['Group.sensors']
      ps.each {
        Sensor s = Sensor.get(getId(it))
        if (s) {
          if (!s.layer || s.layer.id != lid) {
            msg = laneService.assignSensor(s, lid)
          }
        }
      }
      def avs = []
      if (params['availableSensors'] instanceof String)
        avs << params['availableSensors']
      else
        avs = params['availableSensors']
      avs.each {
        Sensor s = Sensor.get(getId(it))
        if (s) {
          if (s.layer && s.layer.id == lid) {
            msg = laneService.removeSensor(s, lid)
          }
        }
      }
      flash.message = msg
      redirect(action:show,id:params.id)
    }

}
