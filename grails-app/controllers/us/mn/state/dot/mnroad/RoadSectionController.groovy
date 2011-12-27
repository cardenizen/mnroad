package us.mn.state.dot.mnroad

import java.text.SimpleDateFormat
import org.springframework.dao.DataIntegrityViolationException
import us.mn.state.dot.mnroad.HmaCell
import us.mn.state.dot.mnroad.PccCell
import us.mn.state.dot.mnroad.CompositeCell
import us.mn.state.dot.mnroad.AggCell
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.RoadSection

class RoadSectionController extends ControllerBase {
    
    def queryService

    def index = { redirect(action:list,params:params) }

    def delete = {
        def roadSectionInstance = RoadSection.get( params.id )
        if(roadSectionInstance) {
            Long fid = roadSectionInstance.facility.id
            roadSectionInstance.facility.roadSections -= roadSectionInstance
            roadSectionInstance.delete()
            flash.message = "RoadSection ${params.id} deleted"
            redirect(controller: "facility", action: show, id: fid)
        }
        else {
            flash.message = "RoadSection not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def roadSectionInstance = RoadSection.get( params.id )

        if(!roadSectionInstance) {
            flash.message = "RoadSection not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ roadSectionInstance : roadSectionInstance ]
        }
    }

    def update = {
        if (params.id.class.name.endsWith("String;") || params['nNewCells'] != null)
          redirect(action:createCells,params:params)
        def roadSectionInstance = RoadSection.get( params.id )
        if(roadSectionInstance) {
            roadSectionInstance.properties = params
            roadSectionInstance.lastUpdatedBy = getUserName()
            if(!roadSectionInstance.hasErrors() && roadSectionInstance.save(flush:true)) {
                flash.message = "RoadSection ${params.id} updated"
                redirect(action:show,id:roadSectionInstance.id)
            }
            else {
                render(view:'edit',model:[roadSectionInstance:roadSectionInstance])
            }
        }
        else {
            flash.message = "RoadSection not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

//    def cellUpdateService

    def createCells = {
      def userName = getUserName()
      def sdf = new SimpleDateFormat('MM/dd/yy',Locale.US);
      def roadSectionInstance = RoadSection.get( Long.parseLong(params.id[0]) )

      Integer n = Integer.parseInt(params['nNewCells'])
      Double last = Double.parseDouble((String)params['minStation'])
      Double max = Double.parseDouble((String)params['maxStation'])
      String sc = params['subsumeCells']
      Boolean subsumeCells = sc?(sc=="on"?true:false):false

      List oldCells = roadSectionInstance.cellsBetweenList(last, max)

      def newCells = []

      // Process list of new cells created by the user.
      String cellList = ""
      for (i in 1..n) {
        String ct = (String)params["ct"+(i==n?"_last":""+i)]
        String cell_last = (String)params["cell"+(i==n?"_last":""+i)]
        if (cell_last && !"".equals(cell_last)) {
          cellList += cell_last
          if (i!=n) {
            cellList += ","
          }
        }
        String dt_day = (String)params["dt"+(i==n?"_last":""+i)+"_day"]
        String dt_month = (String)params["dt"+(i==n?"_last":""+i)+"_month"]
        String dt_year = (String)params["dt"+(i==n?"_last":""+i)+"_year"]
        if (dt_day == null || dt_day.equals(""))
          dt_day = "01"
        Date dt = sdf.parse(dt_month+"/"+dt_day+"/"+dt_year)
        String number = (String)params["number"+(i==n?"_last":""+i)]
        // because "number_last" is disabled
        Double endStation = (number==null || number.equals(""))?max:Double.parseDouble(number)
        def cell = new HmaCell()
        if (ct == "PCC") {
          cell = new PccCell(roadSection:roadSectionInstance, name:"Default Portland Concrete Cement Cell")
        }
        else if (ct == "HMA") {
          cell = new HmaCell(roadSection:roadSectionInstance, name:"Default Hot Mix Asphalt Cell")
        }
        else if (ct == "Composite") {
            cell = new CompositeCell(roadSection:roadSectionInstance, name:"Default Composite (PCC & HMA) Cell")
        }
        else if (ct == "Aggregate") {
              cell = new AggCell(roadSection:roadSectionInstance, name:"Default Aggregate Cell")
        }
        if (cell) {
          cell.cellNumber =             Integer.parseInt(cell_last)
          cell.constructionEndedDate =  dt
          cell.drainageSystem =         "None"
          cell.designLife =             5
          cell.startStation =           last
          cell.endStation =             endStation
          cell.shoulderType =           "HMA"
          newCells << cell
        }
        last = endStation
      }

/*
 * TODO Check cell numbers and stations to see if old and new cell termini are coincident
 * and adjust subsumedBy accordingly.
 */
      try {
        Cell.withTransaction { status ->
          def sba = []
          for (acell in newCells) {
            acell?.createdBy = userName
            acell?.lastUpdatedBy = userName
            acell?.save()
            if (acell?.id != null) {
              sba << acell.id
            }
          }
          def subsumedBy = subsumeCells?cellList:""
          if (subsumedBy && !"".equals(subsumedBy)) {
            oldCells.each {
              // get the old cell
              Cell c = Cell.get(it.id)
              if (!c.demolishedDate) {
                sba.each { cellId ->
                  def newCell = Cell.get(cellId)
                  c.coveredBy.add(newCell)
                  c.lastUpdatedBy = userName
                  c.save(flush:true)
                  if (!newCell.covers) {
                    def hs = new HashSet()
                    hs.add(c)
                    newCell.covers = new TreeSet(hs);
                  }
                  else {
                    newCell.covers.add(c)
                  }
                  newCell.lastUpdatedBy = userName
                  newCell.save(flush:true)
                }
              }
            }
          }
        }
      }
      catch(Exception e){
        flash.message = "Insert Failed, unknown cause"
        def msg = e.message.toString()
        if (e.cause && e.cause.cause)
          msg = e.cause.cause.toString()
        if (msg.contains("cannot insert NULL")) {
          flash.message = e.cause.cause.toString().split(":")[2] + ". Use the <- back button and try again."
        }
        else {
          flaxh.message = msg
        }
        render(view:'show')
        return
      }

      redirect(controller: newCells.getAt(0).controller(), action: "show", id: newCells.getAt(0).id)
    }

    def createFirstCell = {
      def cellType = params['cellType']   // from RoadSection/edit.gsp
      Long rsid = 0
      Object pid = params['id']
      if (!pid) {
        flash.message = "Unable to create new cell. Param 'id' is null."
        redirect(action:show,id:roadSectionInstance.id)
        return
      }
      def roadSectionInstance
      if (pid instanceof Long) {
        rsid = pid
        roadSectionInstance = RoadSection.get(rsid)
      } else if (pid instanceof String) {
        roadSectionInstance = RoadSection.get(MrUtils.toLong(pid))
      } else if (pid instanceof Object[]) {
        roadSectionInstance = RoadSection.get(MrUtils.toLong(pid[0]))        
      }
      if (!roadSectionInstance) {
        flash.message = "Unable to create new cell. No RoadSection for ${pid}."
        redirect(action:show,id:roadSectionInstance.id)
        return
      }
//      def roadSectionInstance = RoadSection.get(Long.parseLong(params['id']))
      String ctrlr = ""
      switch (cellType) {
        case "HMA":
            ctrlr = "hmaCell"
          break
        case "PCC":
          ctrlr = "pccCell"
          break
        case "Aggregate":
          ctrlr = "aggCell"
          break
        case "Composite":
          ctrlr = "compositeCell"
          break
        default:
          flash.message = "Unable to create new cell."
          redirect(action:show,id:roadSectionInstance.id)
      }
      redirect (controller: ctrlr, action:create,
              params:['rsId' : roadSectionInstance.id
                , startStation : roadSectionInstance.startStation
                , endStation : roadSectionInstance.endStation
              ])
    }

    def create = {
        def roadSectionInstance = new RoadSection()
        roadSectionInstance.properties = params
        return ['roadSectionInstance':roadSectionInstance]
    }

    def save = {
        def roadSectionInstance = new RoadSection()
        roadSectionInstance.properties = params

        try {
        roadSectionInstance.createdBy = getUserName()
        roadSectionInstance.lastUpdatedBy = getUserName()
        if(!roadSectionInstance.hasErrors() && roadSectionInstance.save(flush:true)) {
            flash.message = "RoadSection ${roadSectionInstance.id} created"
            redirect(action:show,id:roadSectionInstance.id)
        }
        else {
            render(view:'create',model:[roadSectionInstance:roadSectionInstance])
        }

//        } catch (java.sqlConnection.BatchUpdateException be) {
        } catch (DataIntegrityViolationException be) {
          flash.message = "Invalid Data: ${be.cause?.sqle?.detailMessage?:"Unknown cause"}."
          render(view:'create',model:[roadSectionInstance:roadSectionInstance])
        }
    }

    def cellDisplayList(Long rsid) {//, Boolean includeHistory) {
      def sl = []
      def rs = RoadSection.get(rsid)
      if (rs.cells != null) {
          sl = rs.cells
      }
      return sl.sort()
    }

    // The list, in /views/roadSection/edit.gsp, a javascript
    //  global selectedCells is passed to here, |sitesSelected| closure,
    // by remoteFunction
    def sitesSelected = {
      def includeHistory = params['includeHistory']
      def rsid = params['rsid']

      def cellsSelected = []
      if (includeHistory != null && rsid != null) {
        cellsSelected = cellDisplayList(Long.parseLong(rsid) )
        render(template:"/templates/common/cellListTemplate",
            model:[
              includeHistory:includeHistory
              ,cellList:cellsSelected
            ])
      }
    }

      def cellsSelected = {
        def cellId = params['cellIds']
        def ids = []
        def minStation = 999999.0
        def maxStation = 0.0
        def roadSectionId = null
        def cellSites = []
        if (cellId.contains(",")) {
            ids = cellId.split(",")
            ids.each {
                def cs = Cell.get(Long.parseLong(it))
                roadSectionId = cs.roadSection.id
                cellSites.add(cs)
                minStation = minStation.min(cs.startStation)
                maxStation = maxStation.max(cs.endStation)
            }
        }
        else {
            if  (cellId && !cellId.equals("")) {
            def cell = Cell.get(Long.parseLong(cellId))
            minStation = cell?.startStation
            maxStation = cell?.endStation
            }
        }
        render(template:"cellBoundsTemplate",
          model:[minStation:minStation
            ,maxStation:maxStation
            ,segmentLength:maxStation-minStation])
      }

      def show = {
        def roadSectionInstance = RoadSection.get( params.id )
//        def msgs = queryService.cellDateOverlap(params.id)
//        if (msgs){
//            flash.errors = msgs
//        }

        if(!roadSectionInstance) {
            flash.message = "RoadSection not found with id ${params.id}"
            redirect(controller:facility, action:list)
        }
        else {
//          def cl = cellDisplayList(Long.parseLong(params.id))//, params['includeHistory']?:true)
//          return [ roadSectionInstance : roadSectionInstance, cellList:cl]
          return [ roadSectionInstance : roadSectionInstance]
        }
      }
}
