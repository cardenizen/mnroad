package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.SensorEvaluation
import us.mn.state.dot.mnroad.Sensor

class SensorEvaluationController extends ControllerBase {
    
    def index = { redirect(action:list,params:params) }

    def list = {
      prepareListParams()
      [ sensorEvaluationInstanceList: SensorEvaluation.list( params ) ]
    }

    def show = {
        def sensorEvaluationInstance = SensorEvaluation.get( params.id )

        if(!sensorEvaluationInstance) {
            flash.message = "SensorEvaluation not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ sensorEvaluationInstance : sensorEvaluationInstance ] }
    }

    def delete = {
        def sensorEvaluationInstance = SensorEvaluation.get( params.id )
        if(sensorEvaluationInstance) {
            sensorEvaluationInstance.delete()
            flash.message = "SensorEvaluation ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "SensorEvaluation not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def sensorEvaluationInstance = SensorEvaluation.get( params.id )

        if(!sensorEvaluationInstance) {
            flash.message = "SensorEvaluation not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            def sensors = []
            if (sensorEvaluationInstance.sensor.layer) {
              def s = Sensor.createCriteria()
              sensors = s.list(){
                eq ("layer",sensorEvaluationInstance.sensor.layer)
              }
            }
            return [ sensorEvaluationInstance : sensorEvaluationInstance
              , sensorList: (sensors.size()>0)?sensors:Sensor.list() ]
        }
    }

    def update = {
        def sensorEvaluationInstance = SensorEvaluation.get( params.id )
        if(sensorEvaluationInstance) {
            sensorEvaluationInstance.properties = params
            sensorEvaluationInstance.lastUpdatedBy = getUserName()
            if(!sensorEvaluationInstance.hasErrors() && sensorEvaluationInstance.save()) {
                flash.message = "SensorEvaluation ${params.id} updated"
                redirect(action:show,id:sensorEvaluationInstance.id)
            }
            else {
                render(view:'edit',model:[sensorEvaluationInstance:sensorEvaluationInstance])
            }
        }
        else {
            flash.message = "SensorEvaluation not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def sensorEvaluationInstance = new SensorEvaluation()
        sensorEvaluationInstance.properties = params
        Sensor sensor = Sensor.get(params.sensor.id)
        sensorEvaluationInstance.sensor = sensor
        return ['sensorEvaluationInstance':sensorEvaluationInstance]
    }

    def save = {
      def sid = getId(params['sensorId'])
      Sensor s = Sensor.get(sid)
      def sensorEvaluationInstance = new SensorEvaluation(params)
      sensorEvaluationInstance.commentBy = getUserName()
      sensorEvaluationInstance.evaluationMethod = params.evaluationMethod
      sensorEvaluationInstance.reason = params.reason
      sensorEvaluationInstance.result = params.result
      sensorEvaluationInstance.sensor = s
      s.addToEvaluations(sensorEvaluationInstance)
      //s.evaluations += sensorEvaluationInstance      
        if(!sensorEvaluationInstance.hasErrors() && sensorEvaluationInstance.save()) {
            flash.message = "SensorEvaluation ${sensorEvaluationInstance.id} created"
            redirect(action:show,id:sensorEvaluationInstance.id)
        }
        else {
            render(view:'create',model:[sensorEvaluationInstance:sensorEvaluationInstance])
        }
    }
}
