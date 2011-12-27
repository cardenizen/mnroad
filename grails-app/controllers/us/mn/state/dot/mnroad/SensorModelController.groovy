package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.SensorModel

class SensorModelController extends ControllerBase {
    
    def index = { redirect(action:list,params:params) }

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        params.sort = "model"
        def sml = SensorModel.list( params )
        [ sensorModelInstanceList: sml, sensorModelInstanceTotal: SensorModel.count() ]
    }

    def show = {
        def sensorModelInstance = SensorModel.get( params.id )

        if(!sensorModelInstance) {
            flash.message = "SensorModel not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          def slc = Sensor.createCriteria()
          def sl = slc.list(){
            eq('sensorModel',sensorModelInstance)
            }
          return [ sensorModelInstance : sensorModelInstance, sensors : sl ] 
        }
    }
    def queryService
    def delete = {
        def sensorModelInstance = SensorModel.get( params.id )
        if(sensorModelInstance) {
            try {
              def nSensors = sensorModelInstance?.sensors?.size()
              if (params.confirmDelete=="true") {
                if (nSensors) {
                  try {
                  SensorModel.withTransaction { status ->
                    sensorModelInstance.sensors.each { aSensor ->
                      aSensor.delete()
                    }
                    def tnfm = queryService.sensorTableNamesViaUserTables(grailsApplication.config, 0)
                    if (!tnfm.keySet().contains(sensorModelInstance.model+"_VALUES"))
                      sensorModelInstance.delete(flush:true)
                  }
                  flash.message = "SensorModel ${params.id}  and ${nSensors} ${sensorModelInstance.model} sensors deleted"
                  redirect(action:list)
                  return
                  } catch (Exception e) {
                    log.info "Delete failed: ${sensorModelInstance}, ${e.message}"
                    if (e.message.startsWith("not-null property")) {
                      flash.message = "Delete failed:"
                      flash.message += ": Update ${e.message.substring(e.message.lastIndexOf("."))}"
                      redirect(action:show,id:params.id)
                      return
                    }
                  }
                } else {
                  flash.message = "SensorModel ${params.id} deleted"
                  sensorModelInstance.delete(flush:true)
                }
              } else {
                flash.message = "Delete of SensorModel ${sensorModelInstance.model}(ID ${params.id}) and ${nSensors} sensors not confirmed."
                redirect(action:show,id:params.id)
                return
              }
              redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
              log.info "Delete failed: ${sensorModelInstance}, ${e.message}"
              sensorModelInstance = SensorModel.get( params.id )
              if (sensorModelInstance) {
                flash.message = "SensorModel ${sensorModelInstance.model}(ID ${params.id}) could not be deleted."
                if (sensorModelInstance.sensors.size()) {
                  if (e.message.startsWith("not-null property"))
                    flash.message += ": Update ${e.message.substring(e.message.lastIndexOf("."))}"
                  else
                    flash.message += "  ${sensorModelInstance?.sensors?.size()} ${sensorModelInstance.model} sensors must first be deleted."
                }
              }
              else {
                flash.message = "SensorModel ${params.id} could not be deleted"
                if (e.message.startsWith("not-null property"))
                  flash.message += ": Update ${e.message.substring(e.message.lastIndexOf("."))}"
              }
              redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "SensorModel not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def sensorModelInstance = SensorModel.get( params.id )

        if(!sensorModelInstance) {
            flash.message = "SensorModel not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          def slc = Sensor.createCriteria()
          def sl = slc.list(){
            eq('sensorModel',sensorModelInstance)
            }
          return [ sensorModelInstance : sensorModelInstance, sensors : sl ]
//            return [ sensorModelInstance : sensorModelInstance ]
        }
    }

    def update = {
        def sensorModelInstance = SensorModel.get( params.id )
        if(sensorModelInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(sensorModelInstance.version > version) {
                    
                    sensorModelInstance.errors.rejectValue("version", "sensorModel.optimistic.locking.failure", "Another user has updated this SensorModel while you were editing.")
                    render(view:'edit',model:[sensorModelInstance:sensorModelInstance])
                    return
                }
            }
            sensorModelInstance.properties = params
            if(!sensorModelInstance.hasErrors() && sensorModelInstance.save()) {
                flash.message = "SensorModel ${params.id} updated"
                redirect(action:show,id:sensorModelInstance.id)
            }
            else {
                render(view:'edit',model:[sensorModelInstance:sensorModelInstance])
            }
        }
        else {
            flash.message = "SensorModel not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def sensorModelInstance = new SensorModel()
        sensorModelInstance.properties = params
        return ['sensorModelInstance':sensorModelInstance]
    }

    def save = {
        def sensorModelInstance = new SensorModel(params)
        if(!sensorModelInstance.hasErrors() && sensorModelInstance.save()) {
            flash.message = "SensorModel ${sensorModelInstance.id} created"
            redirect(action:show,id:sensorModelInstance.id)
        }
        else {
            render(view:'create',model:[sensorModelInstance:sensorModelInstance])
        }
    }
}
