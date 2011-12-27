package us.mn.state.dot.mnroad
class TrackedVehicleController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ trackedVehicleInstanceList: TrackedVehicle.list( params ) ]
    }

    def show = {
        def trackedVehicleInstance = TrackedVehicle.get( params.id )

        if(!trackedVehicleInstance) {
            flash.message = "TrackedVehicle not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ trackedVehicleInstance : trackedVehicleInstance ] }
    }

    def delete = {
        def trackedVehicleInstance = TrackedVehicle.get( params.id )
        if(trackedVehicleInstance) {
            trackedVehicleInstance.delete()
            flash.message = "TrackedVehicle ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "TrackedVehicle not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def trackedVehicleInstance = TrackedVehicle.get( params.id )

        if(!trackedVehicleInstance) {
            flash.message = "TrackedVehicle not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ trackedVehicleInstance : trackedVehicleInstance ]
        }
    }

    def update = {
        def trackedVehicleInstance = TrackedVehicle.get( params.id )
        if(trackedVehicleInstance) {
            trackedVehicleInstance.properties = params
            if(!trackedVehicleInstance.hasErrors() && trackedVehicleInstance.save()) {
                flash.message = "TrackedVehicle ${params.id} updated"
                redirect(action:show,id:trackedVehicleInstance.id)
            }
            else {
                render(view:'edit',model:[trackedVehicleInstance:trackedVehicleInstance])
            }
        }
        else {
            flash.message = "TrackedVehicle not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def trackedVehicleInstance = new TrackedVehicle()
        trackedVehicleInstance.properties = params
        return ['trackedVehicleInstance':trackedVehicleInstance]
    }

    def save = {
        def trackedVehicleInstance = new TrackedVehicle(params)
        trackedVehicleInstance.createdBy = request.getRemoteUser()
        if(!trackedVehicleInstance.hasErrors() && trackedVehicleInstance.save()) {
            flash.message = "TrackedVehicle ${trackedVehicleInstance.id} created"
            redirect(action:show,id:trackedVehicleInstance.id)
        }
        else {
            render(view:'create',model:[trackedVehicleInstance:trackedVehicleInstance])
        }
    }
}
