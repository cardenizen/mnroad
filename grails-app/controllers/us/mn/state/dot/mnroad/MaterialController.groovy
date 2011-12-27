package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.Material

class MaterialController extends ControllerBase {
    
    def index = { redirect(action:list,params:params) }

    def list = {
        if (!params.order) {
            params.order = "asc"
        }
        if (!params.sort) {
            params.sort = "basicMaterial"
        }
        def list = Material.list(params)

        return [ materialInstanceList: list ]
    }

    def show = {
        def material = Material.get( params.id )

        if(!material) {
            flash.message = "Material not found with id ${params.id}"
            redirect(action:list)
        }
        else {
          def ll = Layer.findAllByMaterial(material)
          def cellIds = ll.collect {Lane.get(it.lane.id).cell.id}
          def laneIds = ll.collect {it.lane.id}
          def layerIds = ll.collect {it.id}
          def cellNums = ll.collect {Lane.get(it.lane.id).cell.cellNumber}
          def laneNames = ll.collect {it.lane.name}
          def layerNums = ll.collect {it.layerNum}
          return [ materialInstance : material,
                  cellIds:cellIds, cellNums:cellNums,
                  laneIds:laneIds, laneNames:laneNames,
                  layerIds:layerIds,layerNums:layerNums]
        }
    }

    def delete = {
        def material = Material.get( params.id )
        if(material) {
            material.delete()
            flash.message = "Material ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Material not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def material = Material.get( params.id )

        if(!material) {
            flash.message = "Material not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ materialInstance : material ]
        }
    }

    def update = {
        def material = Material.get( params.id )
        if(material) {
            material.properties = params
            material.lastUpdatedBy = getUserName()
            if(!material.hasErrors() && material.save(flush:true)) {
                flash.message = "Material ${params.id} updated"
                redirect(action:show,id:material.id)
            }
            else {
                render(view:'edit',model:[materialInstance:material])
            }
        }
        else {
            flash.message = "Material not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def material = new Material()
        material.properties = params
        return ['materialInstance':material]
    }

    def save = {
        def material = new Material(params)
        material.createdBy = getUserName()
        material.lastUpdatedBy = getUserName()
        if(!material.hasErrors() && material.save(flush:true)) {
            flash.message = "Material ${material.id} created"
            redirect(action:show,id:material.id)
        }
        else {
            render(view:'create',model:[materialInstance:material])
        }
    }
}
