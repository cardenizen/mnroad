import groovy.sql.Sql
import us.mn.state.dot.mnroad.MrUtils
import us.mn.state.dot.mnroad.Cell
import us.mn.state.dot.mnroad.HmaCell
import us.mn.state.dot.mnroad.PccCell
import us.mn.state.dot.mnroad.AggCell
import us.mn.state.dot.mnroad.CompositeCell
import us.mn.state.dot.mnroad.Lane

class CellUpdateService {

  boolean transactional = true
  def dataSource

  def deleteCell (Long id) {
    def rc = false
    def cell = Cell.get(id)
    if ((cell.coveredBy.size()>0)   // if this cell has been subsumed
      || (cell.demolishedDate))  {   // or demolished
      return rc
    }
    Cell.withTransaction { status ->
      cell.lanes.each {lane ->
        def laneId = lane.id
        def dbLayers = Lane.findAll ("from Layer where lane_id="+laneId)
        def nDbLayers = dbLayers.size()
        def nlayers = lane.layers.toArray().size()
        lane.layers.each { layer ->
          if (nDbLayers == nlayers) {
            lane.layers -= layer
            layer.delete()
          }
          else {
            dbLayers.each{
              dbLayers -= layer
              lane.layers -= layer
              it.delete()
            }
          }
        }
        cell.lanes -= lane
        lane.delete()
      }
      // if this cell has overlayed others
      if (cell.covers.size()>0) {
        cell.covers.each { coveredCell ->
          coveredCell.coveredBy -= cell
          coveredCell.save()
          cell.covers -= coveredCell
          cell.save()
        }
      }
      cell.roadSection.cells -= cell;
      cell.delete(flush:true)
      rc = true
    }
  return rc
  }

  def changeCellType (Cell oldCell, String cellType, def params, def userName) {
    Long rid = oldCell.roadSection.id
    def varName
    def cell
    switch (cellType) {
      case "HmaCell":
        cell = new HmaCell()
        varName = 'hmaCell'
        break
      case "PccCell":
        cell = new PccCell()
        varName = 'pccCell'
        break
      case "AggCell":
        cell = new AggCell()
        varName = 'aggCell'
        break
      case "CompositeCell":
        cell = new CompositeCell()
        varName = 'compositeCell'
        break
      default:
        flash.message = "Unable to change cell type."
        redirect(action:show,id:roadSectionInstance.id)
      }
    cell.properties = params
    def savedCell = null
    Cell.withTransaction { status ->
      oldCell.lanes.each {lane ->
        lane.layers.each { layer ->
          lane.layers -= layer
          layer.delete(flush:true)
        }
        oldCell.lanes -= lane
        lane.delete(flush:true)
      }
      oldCell.delete(flush:true)
      def roadSectionInstance = RoadSection.get(rid)
      cell.roadSection = roadSectionInstance
      cell.createdBy = userName
      cell.lastUpdatedBy = userName
      savedCell = cell.save(flush:true)
    }
    return savedCell
  }
  /*
  Given the id of a cell,
  find the id and cell_number of the cell below it
  and the longitudinal bounds (from/to station)
  of the given cell.
   */
  Map subCellsUnder(Long cellId) {
    def rc = [:]
    //
    def q = """select 
 (select cell_number from MNR.cell where id=over_cell_id) CELL_NUMBER_UNDER
 ,over_cell_id CELL_ID
 ,us.FROM_STATION
 ,us.TO_STATION
from
(SELECT unique cc2.cell_id under_cell_id, cc1.cell_id over_cell_id
, case
 when (select start_station from MNR.cell where id=cc2.cell_id) > (select start_station from MNR.cell where id=cc1.cell_id)
 then (select start_station from MNR.cell where id=cc2.cell_id)
 else (select start_station from MNR.cell where id=cc1.cell_id)
 end
 FROM_STATION
, case
 when (select end_station from MNR.cell where id=cc1.cell_id)<(select end_station from MNR.cell where id=cc2.cell_id)
 then (select end_station from MNR.cell where id=cc1.cell_id)
 when (select end_station from MNR.cell where id=cc2.cell_id)<(select start_station from MNR.cell where id=cc1.cell_id)
 then (select start_station from MNR.cell where id=cc1.cell_id)
 else (select end_station from MNR.cell where id=cc2.cell_id)
 end
 TO_STATION
FROM MNR.cell_cell cc1 join MNR.cell_cell cc2 on cc1.cell_id=cc2.cell_covered_by_id) us
where under_cell_id=?
order by FROM_STATION
"""
    Sql sql = new groovy.sql.Sql(dataSource)
    sql.eachRow(q,[cellId]) { row ->
      def f = MrUtils.decimalToStation(row.FROM_STATION)
      def t = MrUtils.decimalToStation(row.TO_STATION)
      rc.put(row.CELL_ID, "${row.CELL_NUMBER_UNDER}:${f} - ${t}")
    }

    return rc
  }

  Map subCellsAbove(Long cellId) {
    def rc = [:]
    //
    def q = """select (select cell_number from MNR.cell where
id=under_cell_id) CELL_NUMBER_OVER, under_cell_id CELL_ID
, us.FROM_STATION
, us.TO_STATION
, (select demolished_date from MNR.cell where id=?) DEMOLISHED_DATE
from
(SELECT unique cc2.cell_id under_cell_id, cc1.cell_id over_cell_id
, case
 when (select start_station from MNR.cell where id=cc2.cell_id) > (select start_station from MNR.cell where id=cc1.cell_id)
 then (select start_station from MNR.cell where id=cc2.cell_id)
 else (select start_station from MNR.cell where id=cc1.cell_id)
 end
 FROM_STATION
, case
 when (select end_station from MNR.cell where id=cc1.cell_id)<(select end_station from MNR.cell where id=cc2.cell_id)
 then (select end_station from MNR.cell where id=cc1.cell_id)
 when (select end_station from MNR.cell where id=cc2.cell_id)<(select start_station from MNR.cell where id=cc1.cell_id)
 then (select start_station from MNR.cell where id=cc1.cell_id)
 else (select end_station from MNR.cell where id=cc2.cell_id)
 end
 TO_STATION
FROM MNR.cell_cell cc1 join MNR.cell_cell cc2 on cc1.cell_id=cc2.cell_covered_by_id) us
where over_cell_id=?
order by FROM_STATION
"""
    Sql sql = new groovy.sql.Sql(dataSource)
    sql.eachRow(q,[cellId,cellId]) { row ->
      def f = MrUtils.decimalToStation(row.FROM_STATION)
      def t = MrUtils.decimalToStation(row.TO_STATION)
      if (row.DEMOLISHED_DATE == null)
        rc.put(row.CELL_ID, "${row.CELL_NUMBER_OVER}:${f} - ${t}")
    }

    return rc
  }

}
