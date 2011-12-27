package us.mn.state.dot.mnroad

import us.mn.state.dot.mnroad.CompositeCell

class CompositeCellController extends CellController {

    static String cellType = "us.mn.state.dot.mnroad.CompositeCell"

    def list = {
      prepareListParams()
        [ compositeCellList: CompositeCell.list( params ) ]
    }

    def edit = {
        Long cellId = (params.id instanceof String)?getId(params.id):params.id
        def compositeCell = CompositeCell.get( cellId )

        if(!compositeCell) {
            flash.message = "CompositeCell not found with id ${params.id}"
            redirect(action:list)
        }
        else {
        def sl = Cell.activeCellsBetween(compositeCell.roadSection.id,compositeCell.startStation,compositeCell.endStation)
        sl -= compositeCell
          return [ cell : compositeCell, sl : sl ]
        }
    }

    def update = {
        def cell = CompositeCell.get( params.id )
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
            flash.message = "Composite Cell not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
      }

    def save = {
      def cell = new CompositeCell(params)
      cell.createdBy = getUserName()
      cell.lastUpdatedBy = getUserName()
      if(!cell.hasErrors() && cell.save(flush:true)) {
        flash.message = "Composite Cell ${cell?.cellNumber} created"
        redirect(action:show,id:cell.id)
      }
      else {
        render(view:'create',model:[cell:cell])
      }
    }

    def create = {
      def cellInstance = new CompositeCell()
      cellInstance.properties = params
      def roadSectionInstance = RoadSection.get(Long.parseLong(params['rsId']))
      cellInstance.roadSection = roadSectionInstance
      return ['cell':cellInstance]
    }

}
