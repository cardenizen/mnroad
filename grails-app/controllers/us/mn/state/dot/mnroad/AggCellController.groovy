package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.AggCell

class AggCellController extends CellController {
    static String cellType = "us.mn.state.dot.mnroad.AggCell"

    def list = {
      prepareListParams()
        [ aggCellList: AggCell.list( params ) ]
    }

    def edit = {
        Long cellId
        if (params.id instanceof String) {
          cellId = getId(params.id)
        }
        else
          cellId = params.id
        def aggCell = AggCell.get( cellId )

        if(!aggCell) {
            flash.message = "AggCell not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ cell : aggCell ]
        }
    }

    def update = {
      def cell = AggCell.get( params.id )
      if(cell) {
  // paramsDateCheck - Workaround for http://jira.codehaus.org/browse/GRAILS-1793
  // enables saving a null date
        def dateError = paramsDateCheck(params)
        cell.properties = params
        def updateLanes = params['updateLanes']
        if (updateLanes) {
          cell.lanes.each {
            if (laneService) {
              laneService.saveLayersWithLane(params, it, getUserName())
            }
          }
        }

        def newClass = params['clazz'].toString()
        if (!cell.hasLayers() && newClass && !cell.class.name.endsWith(newClass)) {
          changeCellType(cell, newClass, params, getUserName())
          return
        }

        cell.lastUpdatedBy = getUserName()
        if(!cell.hasErrors() && cell.save(flush:true)) {
          def msg = "Cell ${cell?.cellNumber} updated${dateError}."
          flash.message = msg
          redirect(action:show,id:cell.id)
        }
        else {
              render(view:'edit',model:[cell:cell])
        }
      }
      else {
          flash.message = "Aggregate Cell not found with id ${params.id}"
          redirect(action:edit,id:params.id)
      }
    }

    def save = {
      def cell = new AggCell(params)
      cell.createdBy = getUserName()
      cell.lastUpdatedBy = getUserName()
      if(!cell.hasErrors() && cell.save(flush:true)) {
        flash.message = "Aggregate Cell ${cell?.cellNumber} created"
        redirect(action:show,id:cell.id)
      }
      else {
        render(view:'create',model:[cell:cell])
      }
    }

    def create = {
      def cellInstance = new AggCell()
      cellInstance.properties = params
      def roadSectionInstance = RoadSection.get(Long.parseLong(params['rsId']))
      cellInstance.roadSection = roadSectionInstance
      return ['cell':cellInstance]
    }

}
