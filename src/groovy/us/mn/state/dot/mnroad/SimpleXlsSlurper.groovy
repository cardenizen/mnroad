package us.mn.state.dot.mnroad
/*
Copyright 2009 Yellow Snow 

Licensed under the Apache License, Version 2.0 (the "License"); you may not 
use this file except in compliance with the License. You may obtain a copy of 
the License at 

	http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
License for the specific language governing permissions and limitations under 
the License. 
*/
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.hssf.util.CellReference
import org.apache.poi.hssf.usermodel.HSSFDateUtil

/**
 *  To iterate through row by row and cell by cell:
 *
 *  def slurper = new SimpleXlsSlurper(ufn)
 *  def si = slurper.iterator()
 *  while (si.hasNext()) {
 *    def r = si.next()
 *    while (r.hasNext()) {
 *        def c = r.next()
 *        println "${c}"
 *      }
 *  }
 */
public class SimpleXlsSlurper implements Iterable {
	static {
	}

	boolean showFormulas = false
	private def workbook
	private selection
	private sheets
	
	SimpleXlsSlurper(InputStream inputStream) {
		if (inputStream) {
			workbook = WorkbookFactory.create(inputStream)
			populate()
		} else {
			throw new IllegalArgumentException("Input Stream")
		}
	}

	SimpleXlsSlurper(String fileName) {
		if (fileName) {
			workbook = WorkbookFactory.create(new FileInputStream(fileName))
			populate()
		} else {
			throw new IllegalArgumentException("File Name")
		}
	}

    List headers(boolean includeFirstColumn) {
      def ra = []
      rows(0)
      def s = iterator()
      int i = 0
      while (s.hasNext()) {
        def n = s.next()
        if ((!i && includeFirstColumn) || i > 0)
          ra << "${n}".toString()
        i++
      }
      return ra
    }

    String downloadDate() {
      def rc = ""
      sheets('dbColumns')
      def s = iterator()
      while (s.hasNext()) {
        def row = s.next()
        while (row.hasNext()) {
          def r = row.next()
          if ("${r}".toString() == "DOWNLOAD_DATE") {
            rc = row.next()
            break;
          }
        }
      }
      rc
    }
  /**
   * Construct a map of database attributes in the 'dbColumns' sheet.
   * The map key is the column name.  Access each entry like so:
   * <code>dbMeta.find {it.key=='MNROAD_ID'}.value</code>
   * "value" is an array of TYPE, LENGTH, and NULLABLE
   * @return
   */
    Map dbMeta() {
      def rc = [:]
      sheets("dbColumns")
      def s = iterator()
      int ridx = 0
      while (s.hasNext()) {
        def r = s.next()
        def ra = []
        def colName = ""
        while (r.hasNext()) {
          def n = r.next()
          if (!colName) {
            colName = "${n}"
          }
          ra << "${n}"
        }
        if (ridx && colName != 'DOWNLOAD_DATE'){
          def l = ra[1..-1]
          rc.put(colName.toString(),l)
        }
        ridx++
      }
      rc
    }

    String type(colName) {
      def rc
      def c = dbMeta().find {it.key == colName}
      if (c) {
        rc = c.value.get(0)
      }
      return rc
    }

    Integer length(colName) {
      def rc
      def c = dbMeta().find {it.key == colName}
      if (c) {
        rc = c.value.get(1).toString().asTypeSafeNumber(Integer)
      }
      return rc
    }

    Boolean nullable(colName) {
      def rc
      def c = dbMeta().find {it.key == colName}
      if (c) {
        rc = c.value.get(2) == 'Y'?true:false
      }
      return rc
    }

    List nulls() {
      return dbMeta().keySet().collect {dbMeta().get(it)[2] == 'Y'}
    }

	protected populate(){
		sheets = new ArrayList<HSSFSheet>(workbook.numberOfSheets)
		(0..(workbook.numberOfSheets-1)).each{sheets << workbook.getSheetAt(it)}
		selection = workbook
	} 

	Iterator iterator(){
		if (selection instanceof HSSFSheet) {
			return selection.collect{row->rowIterator(row)}.iterator()
		} else if (selection == workbook || selection == sheets) {
			return sheets.collect{sheet->sheetIterator(sheet)}.iterator()
		} else if (selection instanceof HSSFRow) {
			return selection.collect{cell->getCellValue(cell)}.iterator()
		}
	}
	private sheetIterator(sheet){
		def iterator = sheet.collect{row->rowIterator(row)}.iterator()
		iterator.metaClass.toString={sheet.sheetName}
		iterator.metaClass.getName={sheet.sheetName}
		return iterator
	}
	private rowIterator(row){
		def iterator = row.collect{cell->getCellValue(cell)}.iterator()
		iterator.metaClass.toString{String.valueOf(row.rowNum)}
		iterator.metaClass.getNum{row.rowNum}
		return iterator
	}
		
	def cells(Integer index){
		if (selection instanceof HSSFRow){
			selection = getCell(selection.rowNum,index)
		}
		if (!selection) {
			throw new IllegalArgumentException("Bad cell column index [${index}]")
		}
		return getCellValue(selection)
	}
	def rows(Integer index){
		def sheet,row
		if (selection instanceof HSSFSheet){
			sheet = selection
		} else {
			if (!sheets.empty) {
				sheet = sheets[0]
			}
		}
		if (sheet) {
			selection = sheet.getRow(index)
		}
		if (!selection) {
			throw new IllegalArgumentException("Bad row index [${index}]")
		}
		return this
	}
	def sheets(){
		selection = sheets
		return this
	}
	def sheets(Integer index){
		if (!index in (0..(sheets.size()))){
			throw new IllegalArgumentException("Bad sheet index [${index}]")
		}
		selection = sheets[index]
		if (!selection){
			throw new IllegalArgumentException("Bad sheet index [${index}]")
		}
        //return this
        return selection
	}
	def sheets(String name){
		selection = workbook.getSheet(name)
		if (!selection) {
			throw new IllegalArgumentException("Bad sheet name [${name}]")
		}
		//return this
        return selection
	}
	def getValue(){
		if (selection instanceof HSSFCell){
			return getCellValue(selection)
		} else {
			throw new IllegalArgumentException("No cell is selected")
		}
	}
	def valueAt(String ref){
		def cellRef = new CellReference(ref)
		def rowNum = cellRef.row
		def cellNum = cellRef.col
		def sheetName = cellRef.sheetName
		def cell = getCell(rowNum,cellNum,sheetName)
		return getCellValue(cell)
	}
	private getCell(rowNum,cellNum,sheetName=null){
		def aSheet,row,cell
		if (sheetName) {
			sheets(sheetName)
			aSheet = selection
		} else if (selection instanceof HSSFSheet){
			aSheet = selection
		} else {
			if (!sheets.empty) {
				aSheet = sheets[0]
			}
		}
		if (aSheet){
			if (aSheet.physicalNumberOfRows > rowNum){
				row  = aSheet.getRow(rowNum)
			}
			cell = row?.getCell(cellNum)
		}
		return cell
	}
	private getCellValue(cell){
		selection = workbook
		return getCellValueByType(cell,cell?cell.cellType:HSSFCell.CELL_TYPE_STRING)
	}

	private getCellValueByType(cell, cellType) {
		def result
        if (!cell)
          return result
		switch(cellType) {
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.richStringCellValue.string;
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
				  result = cell.dateCellValue
				} else {
				  result = cell.numericCellValue
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.booleanCellValue
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = showFormulas ? cell.cellFormula : getCellValueByType(cell, cell.cachedFormulaResultType)
				break
		}
		return result
	}
}