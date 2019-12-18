package com.teamcenter.schedule.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//public class ImportFromExcel {

	/*public static void main(String[] args) {
		
		getExcelData("Z:\\E\\Projectdata\\CreateSchedule.xlsx");
	}*/

	/*private static int rowCnt;
	private static LinkedHashMap<String, LinkedList<String>> outtermap;
	
	 public static LinkedHashMap<String, LinkedList<String>> getExcelData(String sFileLocation )
	 {
		 try {
			 FileInputStream ExcelFileToRead = new FileInputStream(sFileLocation);
		        HSSFWorkbook  wb = new HSSFWorkbook(ExcelFileToRead);
		        HSSFWorkbook test = new HSSFWorkbook(); 
		        HSSFSheet sheet = wb.getSheetAt(0);
		        DataFormatter dataFormatter = new DataFormatter();
		        Iterator<Row> rowIterator = sheet.rowIterator();
		        LinkedHashMap<String, LinkedList<String>> outtermap = new LinkedHashMap<String, LinkedList<String>>();
		        LinkedList<String> innerList = new LinkedList<String>();
		        while (rowIterator.hasNext()) {
				 
				 String scheduleName = "Schedule Name";
				 String customerName = "Customer Name";
				 String customerNumber = "Customer Number";
				 String description = "Description";
				 String status = "Status";
				 
				   Row row = rowIterator.next();
				   Iterator<Cell> cellIterator = row.cellIterator();
		            int cnt=0;
		            LinkedHashMap<String, String> innerMap=new LinkedHashMap<String, String>();
		            if(rowCnt==0)
		            {
		            	System.out.println("row count is 0");
		            	rowCnt++;
		            	
		            }
		  
		        }
		} catch (Exception e) {
			
		}
		 return outtermap;
	 }

}
*/