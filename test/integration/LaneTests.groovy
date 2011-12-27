import grails.test.*
import us.mn.state.dot.mnroad.MrUtils

class LaneTests extends GrailsUnitTestCase {

//    def argmap = [:]
  def laneService
    protected void setUp() {
      super.setUp()
      laneService = new LaneService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetLanes() {
      def l = laneService.getLanes(new Integer(1), "Driving")
      assert l.size() == 2
    }
/*
    void testGetLanesAsOf() {
      LaneService laneService = new LaneService();
      argmap.keySet().each { cell ->
        def data = argmap.get(cell)
        def cellNumber = data.get("cell")
        def arg_lane = data.get("lane")
        def dates = data.get("dates")
        def exps = data.get("exp")
        dates.eachWithIndex { it, i ->
          def date = it.value
          println "\nTesting cell ${cellNumber}, ${arg_lane}, ${MrUtils.formatDate(date,"dd-MMM-yyyy")}"
          def expkey = arg_lane+i
          def expVal = exps.get(expkey)
          def lanes = laneService.getLanesAsOf(cellNumber, arg_lane, date)
          if (lanes.size() != expVal) {
            println "${lanes.size()} lanes for cell ${cellNumber}, ${arg_lane}, ${MrUtils.formatDate(date,"dd-MMM-yyyy")}. "            
          }
//          assert lanes.size() == expVal
          else {
          println "Returned ${expVal} lanes."
          lanes.each { lane ->
            println "\t Lane - Id: ${lane.id}: ${lane}\t Cell - Id: ${lane.cell.id}: ${lane.cell}"
            println "\t Layers (top to bottom):"
            lane.layers.toList().reverse().each { lyr ->
              println "\t\t Id: ${lyr.id}, #: ${lyr.layerNum}, Built: ${MrUtils.formatDate(lyr.constructEndDate,"dd-MMM-yyyy")}}"
            }
          }
          }
        }
      }
    }

        Map cell_1D() {
          def data = [:]
          data.put("cell", new Integer(1))
          data.put("lane", "Driving")
          def dates = [:]
          dates.put("Driving0",new GregorianCalendar(2005, Calendar.JULY, 22).getTime())
          dates.put("Driving1",new GregorianCalendar(2007, Calendar.JULY, 22).getTime())
          def exp = [:]
          exp.put("Driving0",1)
          exp.put("Driving1",1)
          data.put("dates",dates)
          data.put("exp",exp)
          return data
        }

        Map cell_1P() {
          def data = [:]
          data.put("cell", new Integer(1))
          data.put("lane", "Passing")
          def dates = [:]
          dates.put("Passing0",new GregorianCalendar(2005, Calendar.JULY, 22).getTime())
          def exp = [:]
          exp.put("Passing0",1)
          data.put("dates",dates)
          data.put("exp",exp)
          return data
        }

      Map cell_33O() {
        def data = [:]
        data.put("cell", new Integer(33))
        data.put("lane", "Outside")
        def dates = [:]
        dates.put("Outside0",new GregorianCalendar(1995, Calendar.JULY, 22).getTime())
        dates.put("Outside1",new GregorianCalendar(2005, Calendar.JULY, 22).getTime())
        dates.put("Outside2",new GregorianCalendar(2008, Calendar.JULY, 22).getTime())
        def exp = [:]
        exp.put("Outside0",1)
        exp.put("Outside1",1)
        exp.put("Outside2",1)
        data.put("dates",dates)
        data.put("exp",exp)
        return data
      }

          Map cell_61D() {
            def data = [:]
            data.put("cell", new Integer(61))
            data.put("lane", "Driving")
            def dates = [:]
            dates.put("Driving0",new GregorianCalendar(2003, Calendar.JULY, 22).getTime())
            dates.put("Driving1",new GregorianCalendar(2007, Calendar.JULY, 22).getTime())
            def exp = [:]
            exp.put("Driving0",1)
            exp.put("Driving1",1)
            data.put("dates",dates)
            data.put("exp",exp)
            return data
          }
*/
}
