package us.mn.state.mn.mnroad

/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Oct 4, 2011
 * Time: 8:23:19 AM
 * To change this template use File | Settings | File Templates.
 */
class QueryServiceTests extends GroovyTestCase {

  def queryService

  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testCellDateOverlap() {
    def msgs = queryService.cellDateOverlap(633)
    assert msgs.size()==2
  }
}
