package com.teamcenter.schedule.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ResAssigneeExportToExcel {
	HSSFWorkbook wb ;
	private ArrayList<String> alExcelCell1;

	   public void generateReport(Table ReportTable) throws IOException {
			 try  (HSSFWorkbook wb = new HSSFWorkbook()){
				HSSFSheet sheet = wb.createSheet("ExportData");
				// HSSFRow row = sheet.addMergedRegion();	
				// HSSFCellStyle cellStyle= setCellBorder();

				HSSFRow row = sheet.createRow(1);
				HSSFCell cell_row1 = row.createCell(1);
				cell_row1.setCellValue("Schedule Report");
				HSSFHeader header = sheet.getHeader();

				String columns[] = { "Task name","Schedule name", "Status", "Start Date",
						"End Date", "User" };
				HSSFRow columnRow = sheet.createRow(2);
				for (int i = 0; i < columns.length; i++) {
					HSSFCell R2_C1 = columnRow.createCell(i);
				//	R2_C1.setCellStyle(cellStyle);
					R2_C1.setCellValue(columns[i]);
				}	
				TableColumn[] tabColumns= ReportTable.getColumns();
				TableItem[] items = ReportTable.getItems();
				
				int rowCnt=3;
				for(int tabi=0;tabi<items.length;tabi++)
				{
					HSSFRow rowC = sheet.createRow(rowCnt);
					TableItem tableItem = items[tabi];
					
					for(int tabc=0;tabc<tabColumns.length;tabc++)
					{
						String tabITemStr = tableItem.getText(tabc);
						HSSFCell colCell = rowC.createCell(tabc);

						 // Create the style
				       
				       
				      /*  if(tabITemStr.equals("In Progress"))
				           cellStyle.setFillBackgroundColor(HSSFColor.RED.index);
				        else if(tabITemStr.equals("Not Started"))
					           cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
				        else if(tabITemStr.equals("Completed"))
					           cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
				        else cellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
						     
				        colCell.setCellStyle(cellStyle);*/
						colCell.setCellValue(tabITemStr);
					}
					rowCnt++;
				}
				
				DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
				Date date = new Date();
				String dateStr = dateFormat.format(date);
					
				// sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

				   File file = new File("C:\\temp\\ScheduleReport");
			        if (!file.exists()) {
			            if (file.mkdir()) {
			              //  System.out.println("Directory is created!");
			            } else {
			               // System.out.println("Failed to create directory!");
			            }
			        }
				
				// Write the output to a file
				String filepath=file.getAbsolutePath()+"\\ScheduleTakMAnagerReport_"+dateStr+".xls";
				try(FileOutputStream fileOut = new FileOutputStream(filepath))
				{
					
					wb.write(fileOut);
					org.eclipse.swt.widgets.MessageBox messageBox;
		       		messageBox = new org.eclipse.swt.widgets.MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION| SWT.OK);
		            messageBox.setMessage("Resource Assignee Schedule Report is generated successfully.");
		            messageBox.setText("Excel Report");
		            int rc = messageBox.open();
		            if(rc == SWT.OK)
		            {
		            	///open on create
		            	Desktop.getDesktop().open(new File(filepath));
		            }
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	
	   HSSFCellStyle setCellBorder( )
	   {
		   HSSFCellStyle cellStyle= wb.createCellStyle();
		   cellStyle.setBorderTop(BorderStyle.THIN);
	        cellStyle.setBorderBottom(BorderStyle.THIN);
	        cellStyle.setBorderLeft(BorderStyle.THIN);
	        cellStyle.setBorderRight(BorderStyle.THIN);
	     
	        return cellStyle;
	   }
	   
	   
	   public  LinkedHashMap<String, LinkedHashMap<String, String>> getExcelScheduleData(String sFileLocation) throws IOException
	    {
		   try {
			   FileInputStream ExcelFileToRead = new FileInputStream(sFileLocation);
		        HSSFWorkbook  wb = new HSSFWorkbook(ExcelFileToRead);
		        HSSFWorkbook test = new HSSFWorkbook(); 
		        HSSFSheet sheet = wb.getSheetAt(0);
		        DataFormatter dataFormatter = new DataFormatter();
		        int rowCnt = 0;
		        Iterator<Row> rowIterator = sheet.rowIterator();
		        LinkedHashMap<String, LinkedList<String>> outtermap = new LinkedHashMap<String, LinkedList<String>>();
		        LinkedList<String> innerList = new LinkedList<String>();
		        ArrayList<String> colList=new ArrayList<String>();
		        while (rowIterator.hasNext()) { 
				 String scheduleName = "Schedule Name";
				 String customerName = "Customer Name";
				 String customerNumber = "Customer Number";
				 String description = "Description";
				 String status = "Status";
				 
				   Row row = rowIterator.next();
				   Iterator<Cell> cellIterator = row.cellIterator();
		           
		            LinkedHashMap<String, String> innerMap=new LinkedHashMap<String, String>();
		            
					if(rowCnt==0)
		            {
						while ( cellIterator.hasNext()) {   
	  	                Cell cell = cellIterator.next();
	  	                String cellValue = dataFormatter.formatCellValue(cell);
	  	            	colList.add(cellValue);
	  	            	System.out.println("RowCount0 =="+colList);
						}
		            }
					else
		            if(rowCnt==1 || rowCnt>1)
		            {
		            	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(); 	
		        }    
		        } 
		   }catch (Exception e) {
			e.printStackTrace();
			
		}  
			return null;	    
}
	   
	   
	 //*********************************Read Excel data ************************
	   
	   public  LinkedHashMap<String, LinkedHashMap<String, String>> readXLSFile(String sFileLocation) throws IOException
	    {
		   FileInputStream ExcelFileToRead = new FileInputStream(sFileLocation);
	        HSSFWorkbook  wb = new HSSFWorkbook(ExcelFileToRead);
	        HSSFWorkbook test = new HSSFWorkbook(); 
	        HSSFSheet sheet = wb.getSheetAt(0);
	        DataFormatter dataFormatter = new DataFormatter();
	     
	        Iterator<Row> rowIterator = sheet.rowIterator();
	        
	        int rowCnt = 0;
	        LinkedHashMap<String, LinkedHashMap<String, String>> outerMap=new LinkedHashMap<String, LinkedHashMap<String, String>>();
	       
	        while (rowIterator.hasNext()) {
	        	String taskName = "Task name";
        		String schedulename = "Schedule name";
        		String status = "Status";
        		String startDate = "Start Date";
        		String enddate = "End Date";
        		String user = "User";
	            Row row = rowIterator.next();
	        	
	            // Now let's iterate over the columns of the current row
	            Iterator<Cell> cellIterator = row.cellIterator();
	            int cnt=0;
	            LinkedHashMap<String, String> innerMap=new LinkedHashMap<String, String>();
	            if(rowCnt==0)
	            {
	            	System.out.println("row count is 0");
	            	rowCnt++;
	            	
	            }
	            else
	            {
	            	  while ( cellIterator.hasNext()) {
	  	            	//LinkedHashMap<String, String> innerMap=new LinkedHashMap<String, String>();
	  	                Cell cell = cellIterator.next();
	  	                String cellValue = dataFormatter.formatCellValue(cell);
	  	                
	  	              if(rowCnt==1)
		  	           {
		  	            	ArrayList<String> colList=new ArrayList<String>();
		  	            	colList.add(cellValue);
		  	            	System.out.println("RowCount1 =="+colList);
		  	            	
		  	           }else
		  	           {
		  	        	 if(cnt==0)
		  	                {
		  	                	taskName=cellValue;
		  	                	ArrayList<String> coloum = new ArrayList<String>();
		  	                	coloum.add(cellValue);
			            	}
		  	              innerMap.put(""+cnt, cellValue);
		  	             cnt++;
		  	           }	             
	  	            } 
	            	  if(rowCnt!=0 && rowCnt!=1)
	            	    outerMap.put(taskName, innerMap);
	            	  rowCnt++;
	            }
	            System.out.println("innerMap=="+innerMap);
	            //cnt++;
	        }
	        System.out.println("outerMap=="+outerMap);
			return outerMap;	
	}
}