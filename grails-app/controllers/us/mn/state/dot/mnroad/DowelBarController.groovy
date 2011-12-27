package us.mn.state.dot.mnroad

class DowelBarController extends ControllerBase {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dowelBarInstanceList: DowelBar.list(params), dowelBarInstanceTotal: DowelBar.count()]
    }

    def create = {
        def dowelBarInstance = new DowelBar()
        dowelBarInstance.properties = params
        return [dowelBarInstance: dowelBarInstance]
    }

    def save = {
        def dowelBarInstance = new DowelBar(params)
        if (dowelBarInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), dowelBarInstance.id])}"
            redirect(action: "show", id: dowelBarInstance.id)
        }
        else {
            render(view: "create", model: [dowelBarInstance: dowelBarInstance])
        }
    }

    def show = {
        def dowelBarInstance = DowelBar.get(params.id)
        if (!dowelBarInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
            redirect(action: "list")
        }
        else {
            [dowelBarInstance: dowelBarInstance]
        }
    }

    def edit = {
        def dowelBarInstance = DowelBar.get(params.id)
        if (!dowelBarInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [dowelBarInstance: dowelBarInstance]
        }
    }

    def update = {
        def dowelBarInstance = DowelBar.get(params.id)
        if (dowelBarInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (dowelBarInstance.version > version) {
                    
                    dowelBarInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'dowelBar.label', default: 'DowelBar')] as Object[], "Another user has updated this DowelBar while you were editing")
                    render(view: "edit", model: [dowelBarInstance: dowelBarInstance])
                    return
                }
            }
            dowelBarInstance.properties = params
            if (!dowelBarInstance.hasErrors() && dowelBarInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), dowelBarInstance.id])}"
                redirect(action: "show", id: dowelBarInstance.id)
            }
            else {
                render(view: "edit", model: [dowelBarInstance: dowelBarInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def dowelBarInstance = DowelBar.get(params.id)
        if (dowelBarInstance) {
            try {
                dowelBarInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dowelBar.label', default: 'DowelBar'), params.id])}"
            redirect(action: "list")
        }
    }
}
