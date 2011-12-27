package us.mn.state.mn.mnroad

import grails.test.*
import us.mn.state.dot.mnroad.MrUtils
import us.mn.state.dot.mnroad.DbColumnAttr

class DbMetadataServiceTests extends GroovyTestCase {

  def dbMetadataService

  protected void setUp() {
      super.setUp()
/*
      SELECT 'assert dbMetadataService.cellType(' || CELL || ', MrUtils.getDate(''' || TO_CHAR(FROM_DATE + 1,'yyyy') ||',' || TO_CHAR(FROM_DATE + 1,'mm') ||','|| TO_CHAR(FROM_DATE + 1,'dd') ||',0,0,0''))==''' || cell_type ||''';' test
      FROM MNR.CELL_TYPES ORDER BY START_STATION, CELL
*/
  }

  protected void tearDown() {
      super.tearDown()
  }

  void testCellType() {
    assert dbMetadataService.cellType(64, MrUtils.getDate('2005,09,21,0,0,0'))=='PccCell';
    assert dbMetadataService.cellType(33, MrUtils.getDate('1990,10,06,0,0,0'))=='AggCell';
    assert dbMetadataService.cellType(33, MrUtils.getDate('1999,08,12,0,0,0'))=='HmaCell';
    assert dbMetadataService.cellType(34, MrUtils.getDate('1991,08,15,0,0,0'))=='AggCell';
    assert dbMetadataService.cellType(34, MrUtils.getDate('1999,08,12,0,0,0'))=='HmaCell';
  }

  void testAllTabColumns() {
    def atc = dbMetadataService.allTabColumns('mnr','facility')
    assert atc.size() == 6
    assert atc[DbColumnAttr.NAME] != null
    assert atc[DbColumnAttr.TYPE] != null
    assert atc[DbColumnAttr.LENGTH] != null
    assert atc[DbColumnAttr.NULLABLE] != null
  }

  void testClassForTable() {
    def artefact = dbMetadataService.classForTable('facility')
    assert artefact.size() > 0
    assert artefact[0].name == 'Facility'

    artefact = dbMetadataService.classForTable('cell')
    // Artefact > AggCell, Artefact > Cell, Artefact > CompositeCell, Artefact > HmaCell, Artefact > PccCell
    assert artefact.size() == 5
  }
}
