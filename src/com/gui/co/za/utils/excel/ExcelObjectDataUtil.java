package com.gui.co.za.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelObjectDataUtil {
	private static Workbook excelWBook;
	
	public static void setWorkBook(String wbPath) throws FileNotFoundException, IOException {
		if(wbPath.endsWith(".xls")) {
			ExcelObjectDataUtil.excelWBook = new HSSFWorkbook(new FileInputStream(wbPath));
		}else {
			ExcelObjectDataUtil.excelWBook = new XSSFWorkbook(new FileInputStream(wbPath));
		}
	}
	
	public static Workbook getWorkBook() {
		return excelWBook;
	}
	
	
	/*
	 * this returns a row in an excel to be used to get cell contents using index 0 to the last cell index
	 * 
	 * */
	public Row getRowData(String excelPath, int iterationRowNum, String sheetName) {
		Row currentRow = null;
		try {
			/* setWorkbook */	
			ExcelObjectDataUtil edu = new ExcelObjectDataUtil();
			
			this.setWorkBook(excelPath);
			/* getTheWorkbook */			
			Workbook excelWorkbook = this.getWorkBook();
			/* get sheet for test  */
			Sheet sheetToUse = excelWorkbook.getSheet(sheetName);
			
			/* get the row you need using the iteration number */
			currentRow = sheetToUse.getRow(iterationRowNum);
			
			/* slot the four items for the row into a getter and setter */
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return currentRow;
		
	}
	
	
	/*
	 * this method gets the object data for the PageObject excel based on the page object specified
	 * 
	 * */
	public ExcelObjectFactory getObjectDataForRuntime(String excelPath, String PageObject, String sheetName) {
		Row currentRow = null;
		int iterationRowNum = 0;
		ExcelObjectFactory of = new ExcelObjectFactory();

		System.out.println("The page object that came in is ::: "+ PageObject);

		try {
			/* setWorkbook */			
			this.setWorkBook(excelPath);
			/* getTheWorkbook */			
			Workbook excelWorkbook = this.getWorkBook();
			/* get sheet for test  */
			Sheet sheetToUse = excelWorkbook.getSheet(sheetName);
			
			/* find the row number using the Page object */
			
			for(Row row :  sheetToUse) {
				
				if(row.getCell(0).getStringCellValue().equalsIgnoreCase(PageObject)) {
					iterationRowNum = row.getRowNum();
					break;
				}
				else {
					//System.out.println("Page object "+PageObject+" not Found.");
				}
			}
			
			
			
			/* get the row you need using the iteration number */
			currentRow = sheetToUse.getRow(iterationRowNum);
			
			String page = currentRow.getCell(0).getStringCellValue();
			String selectorType = currentRow.getCell(1).getStringCellValue();
			String selectorContent = currentRow.getCell(2).getStringCellValue();
			String valueToUse = currentRow.getCell(3).getStringCellValue();
			String expectedResult = currentRow.getCell(4).getStringCellValue();
			String objectType = currentRow.getCell(5).getStringCellValue();
			String maxLength = currentRow.getCell(6).getStringCellValue();
			String ritItem = currentRow.getCell(7).getStringCellValue();
			//System.out.println("This row "+ currentRow+" +"+page);
			
			/* slot the four items for the row into an object so that you can set it in your code then continue*/
			of.setPage(page);
			of.setSelectorType(selectorType);
			of.setSelectorContent(selectorContent);
			of.setValueToUse(valueToUse);
			of.setExpectedResult(expectedResult);
			of.setObjectType(objectType);
			of.setMaxLen(maxLength);
			of.setRitTestItem(ritItem);
			
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("Extract Excel Error 1 : "+e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.out.println("Extract Excel Error 2 : "+e.getMessage());
			e.printStackTrace();
		}
		
		return of;
	}

    public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
        try{
    		Sheet excelWSheet = excelWBook.getSheet(SheetName);
            Cell cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = cell.getStringCellValue();
            return CellData;
         }catch (Exception e){
             return"";
         }
     }
    
    public static void closeWorkBook() {
    	if ( excelWBook != null )
			try {
				excelWBook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public static int getRowCount(String sheetName) {
    	return excelWBook.getSheet(sheetName).getLastRowNum();
    }

	public static int getNumberOfSheets() {
		return excelWBook.getNumberOfSheets();
	}


	
	public static ArrayList<String> getSheetNames(){
		ArrayList<String> returnList = new ArrayList<>();
		Iterator<Sheet> sit = excelWBook.sheetIterator();
		while(sit.hasNext()) {
			returnList.add(sit.next().getSheetName());
		}
		return returnList;
	}
	
	public static Sheet getSheetByName(String name) {
		return excelWBook.getSheet(name);
	}
	
	public static Sheet getSheetByIndex(int index) {
		return excelWBook.getSheetAt(index);
	}
	
	public static Row getRowFromSheet(int index, String sheetName) {
		return excelWBook.getSheet(sheetName).getRow(index);
	}
	
}
