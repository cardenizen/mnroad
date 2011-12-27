package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.Facility
import us.mn.state.dot.mnroad.MrUtils

class FacilityController extends ControllerBase {

    def dataSource

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
      session["dbEnv"] = MrUtils.grailsEnvironment()//dbEnv()
      session["grailsVersion"] = grailsVersion()
      prepareListParams() // max, order, and sort
      def facilityList = Facility.list()
      return [ facilities: facilityList, paginateCount: facilityList.size()]//, 'dbenv':dbenv ,'grailsVersion':gv]
    }

// Create a new Facility
    def create = {
        def facility = new Facility()
        facility.properties = params
        [ facility: facility ]
    }

// Save a created Facility
    def save = {
		def facility = new Facility(params)
		if (!facility.hasErrors()) {
			boolean result = false
            facility.createdBy = getUserName()
            facility.lastUpdatedBy = getUserName()
			Facility.withTransaction { status ->
//				facility.save(flush:true)
				result = facility.save(flush:true)
			}
			if (result) {
				flash.message = "Facility ${facility.name} created"
				redirect(action: show, id: facility.id)
			}
			else {
				render(view: 'create', model: [ facility: facility ])
			}
        }
        else {
            flash.message = "Facility not created"
            redirect(action: list)
        }
    }
	
// Display a Facility
    def show = {
        def facility = Facility.get(params.id)
        if (!facility) {
            flash.message = "Facility not found"
            redirect(action: list)
        }
        else {
            return [ facility : facility ] 
        }
    }

//Edit a Facility
    def edit = {
        def facility = Facility.get(params.id)
        if (!facility) {
            flash.message = "Facility not found"
            redirect(action: list)
        }
        else {
            session.facilityId = facility.id
            return [ facility : facility ]
        }
    }

// Delete a Facility
    def delete = {
      def facility = Facility.get(params.id)
      Facility.withTransaction { status ->
          facility?.delete()
      }
      if (session.facilityId == facility?.id) {
          session.facilityId = null
      }
      facility = Facility.get(params.id)
      if (facility != null)
      {
        flash.message = "Facility ${params.name} was not deleted"
        redirect(action: edit, id: facility.id)
        return
      }
      flash.message = "Facility ${params.name} deleted"
      redirect(action: list)      
    }

    def update = {
        def facility = Facility.get( params.id )
        if(facility) {
          facility.properties = params
          facility.lastUpdatedBy = getUserName()
          try {
            def errors = facility.hasErrors()
            if(!errors && facility.save(flush:true)) {
                flash.message = "Facility ${params.id} updated"
                redirect(action:show,id:facility.id)
            }
            else {
                render(view:'edit',model:[facility:facility])
            }
          } catch(Exception e) {
            def msg = "Facility not saved."
            if (e.message.startsWith("immutable natural identifier"))
              flash.message = "${msg} - Cannot update the natural key."
            else
              flash.message = "${msg} - Exception: ${e.message}."
            redirect(action:edit,id:params.id)
          }
        }
        else {
            flash.message = "Facility not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }
}
