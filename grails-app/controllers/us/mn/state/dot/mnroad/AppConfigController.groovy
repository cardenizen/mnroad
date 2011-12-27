package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.AppConfig

class AppConfigController extends ControllerBase {
    
    def index = { redirect(action:list,params:params) }

    def list = {
      prepareListParams()
      [ appConfigInstanceList: AppConfig.list(params) ]
    }

    def classSelected = {
      def pln = params.pickListName
      render(template:"/templates/common/menuListTemplate",
          model:[appConfigInstanceList:AppConfig.findAllByName(pln)])
    }

    def show = {
        def appConfigInstance = AppConfig.get( params.id )
        if(!appConfigInstance) {
            flash.message = "AppConfig not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ appConfigInstance : appConfigInstance ] }
    }

    def delete = {
        def appConfigInstance = AppConfig.get( params.id )
        if(appConfigInstance) {
            appConfigInstance.delete()
            flash.message = "AppConfig ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "AppConfig not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def appConfigInstance = AppConfig.get( params.id )

        if(!appConfigInstance) {
            flash.message = "AppConfig not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ appConfigInstance : appConfigInstance ]
        }
    }

    def update = {
        def appConfigInstance = AppConfig.get( params.id )
        if(appConfigInstance) {
            appConfigInstance.properties = params
            appConfigInstance.lastUpdatedBy = getUserName()
            if(!appConfigInstance.hasErrors() && appConfigInstance.save()) {
                flash.message = "AppConfig ${params.id} updated"
                redirect(action:show,id:appConfigInstance.id)
            }
            else {
                render(view:'edit',model:[appConfigInstance:appConfigInstance])
            }
        }
        else {
            flash.message = "AppConfig not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def appConfigInstance = new AppConfig()
        appConfigInstance.properties = params
        return ['appConfigInstance':appConfigInstance]
    }

    def save = {
        def appConfigInstance = new AppConfig(params)
        appConfigInstance.createdBy = getUserName()
        appConfigInstance.lastUpdatedBy = getUserName()
        if(!appConfigInstance.hasErrors() && appConfigInstance.save()) {
            flash.message = "AppConfig ${appConfigInstance.id} created"
            redirect(action:show,id:appConfigInstance.id)
        }
        else {
            render(view:'create',model:[appConfigInstance:appConfigInstance])
        }
    }
}
