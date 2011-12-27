package us.mn.state.dot.mnroad
class VehicleLocationController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ vehicleLocationInstanceList: VehicleLocation.list( params ) ]
    }

    def show = {
        def vehicleLocationInstance = VehicleLocation.get( params.id )

        if(!vehicleLocationInstance) {
            flash.message = "VehicleLocation not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ vehicleLocationInstance : vehicleLocationInstance ] }
    }

    def delete = {
        def vehicleLocationInstance = VehicleLocation.get( params.id )
        if(vehicleLocationInstance) {
            vehicleLocationInstance.delete()
            flash.message = "VehicleLocation ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "VehicleLocation not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def vehicleLocationInstance = VehicleLocation.get( params.id )

        if(!vehicleLocationInstance) {
            flash.message = "VehicleLocation not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ vehicleLocationInstance : vehicleLocationInstance ]
        }
    }

    def update = {
        def vehicleLocationInstance = VehicleLocation.get( params.id )
        if(vehicleLocationInstance) {
            vehicleLocationInstance.properties = params
            if(!vehicleLocationInstance.hasErrors() && vehicleLocationInstance.save()) {
                flash.message = "VehicleLocation ${params.id} updated"
                redirect(action:show,id:vehicleLocationInstance.id)
            }
            else {
                render(view:'edit',model:[vehicleLocationInstance:vehicleLocationInstance])
            }
        }
        else {
            flash.message = "VehicleLocation not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def vehicleLocationInstance = new VehicleLocation()
        vehicleLocationInstance.properties = params
        return ['vehicleLocationInstance':vehicleLocationInstance]
    }

    def save = {
        def vehicleLocationInstance = new VehicleLocation(params)
        if(!vehicleLocationInstance.hasErrors() && vehicleLocationInstance.save()) {
            flash.message = "VehicleLocation ${vehicleLocationInstance.id} created"
            redirect(action:show,id:vehicleLocationInstance.id)
        }
        else {
            render(view:'create',model:[vehicleLocationInstance:vehicleLocationInstance])
        }
    }
}
