package us.mn.state.dot.mnroad

class TrJointController extends ControllerBase {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
      params.max = Math.min(params.max ? params.int('max') : 10, 100)
      def list = []
      def cnt = 0
      if (params.layerId) {
        Long layerId = (params.layerId instanceof String)?getId(params.layerId):params.layerId
        def c = Layer.get(layerId)
        list = TrJoint.findAllByLayer(c,[sort:'station',order:'asc'])
        cnt = list.size()
      } else {
        list = TrJoint.list(params)
        cnt = TrJoint.count()
      }
      [trJointInstanceList: list, trJointInstanceTotal: cnt]
    }

    def create = {
        def trJointInstance = new TrJoint()
        trJointInstance.properties = params
        return [trJointInstance: trJointInstance]
    }

    def save = {
        def trJointInstance = new TrJoint(params)
        trJointInstance.createdBy = getUserName()
        if (trJointInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), trJointInstance.id])}"
            redirect(action: "show", id: trJointInstance.id)
        }
        else {
            render(view: "create", model: [trJointInstance: trJointInstance])
        }
    }

    def show = {
        def trJointInstance = TrJoint.get(params.id)
        if (!trJointInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
            redirect(action: "list")
        }
        else {
            [trJointInstance: trJointInstance]
        }
    }

    def edit = {
        def trJointInstance = TrJoint.get(params.id)
        if (!trJointInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [trJointInstance: trJointInstance]
        }
    }

    def update = {
        def trJointInstance = TrJoint.get(params.id)
        if (trJointInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (trJointInstance.version > version) {
                    
                    trJointInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'trJoint.label', default: 'TrJoint')] as Object[], "Another user has updated this TrJoint while you were editing")
                    render(view: "edit", model: [trJointInstance: trJointInstance])
                    return
                }
            }
            trJointInstance.properties = params
            trJointInstance.lastUpdatedBy = getUserName()
            if (!trJointInstance.hasErrors() && trJointInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), trJointInstance.id])}"
                redirect(action: "show", id: trJointInstance.id)
            }
            else {
                render(view: "edit", model: [trJointInstance: trJointInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def trJointInstance = TrJoint.get(params.id)
        if (trJointInstance) {
            try {
                trJointInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trJoint.label', default: 'TrJoint'), params.id])}"
            redirect(action: "list")
        }
    }
}
