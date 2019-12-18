package com.tc.writeexcel;



import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;






import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;*/
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class ReadExcelDemo
{
    public static void main(String[] args)
    {
        try
        {
        	LinkedHashMap<String, Integer[]> list=new LinkedHashMap<String, Integer[]>();
        	list.put("Task1",new Integer[]{1,2,3});
        	list.put("Task2",new Integer[]{22,23,24});
        	list.put("Task3",new Integer[]{43,44,45,46});
        	list.put("Task4",new Integer[]{55,56,57,58,59,60});
        	File filePath=new File("C:\\SETUP\\Schedule_Report.XLSX");
        	
            FileInputStream file = new FileInputStream(filePath);
           // POIFSFileSystem fs = new POIFSFileSystem(file);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            //Create Workbook instance holding reference to .xlsx file
           // HSSFWorkbook workbook = new HSSFWorkbook(file);
 
            CellStyle style = workbook.createCellStyle();
		       style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		       style.setFillPattern(CellStyle.THICK_BACKWARD_DIAG);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            
           // int cnt=13;
            
           
            XSSFRow secondRow = sheet.getRow(2);
            for(int i=12;i<sheet.getLastRowNum();i++)
            {
            	XSSFRow row = sheet.getRow(i);
                 	
            	for(String keyTask:list.keySet())
            	{
            		//Get task data
            		XSSFCell tasknmCEll = row.getCell(1);//Task Names;
            		int type = tasknmCEll.getCellType(); //type
            		if(type==Cell.CELL_TYPE_STRING )
                    {
                    	if(tasknmCEll.getStringCellValue()!=null && tasknmCEll.getStringCellValue().length()>0)
                    	 {
                    		String strValue = tasknmCEll.getStringCellValue();
                    		if(keyTask.equals(strValue))
                    		{
                    			System.out.println("strValue=="+strValue);
                    			tasknmCEll.setCellStyle(style);
                    		}
                    	 }
                    	
                    }
            		
            		//Get week data and color it
            		//For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = secondRow.cellIterator();
                    int cellCnt=0;
                    while (cellIterator.hasNext())
                    {
                        Cell seconRowcell = cellIterator.next();//Week cell
                        
                        if(type==Cell.CELL_TYPE_NUMERIC )
                        {
                        	if(seconRowcell.getStringCellValue()!=null && seconRowcell.getStringCellValue().length()>0)
                        	 {
                        		int weekValue =(int) seconRowcell.getNumericCellValue();
                        		//Match weeks and color
                        		for(int weekInt:list.get(keyTask))
                        		{
                        			if(weekInt==weekValue)
                        			{
                        				XSSFCell rowCell = row.getCell(cellCnt);
                        				rowCell.setCellValue(5555);
                        			}
                        		}
                        		                        		
                        	 }
                        	
                        }
                       
                        cellCnt++;
                    }
                	
            	}
            	
            	//System.out.println("cell val="+cell.getStringCellValue());
                
            }
            
 
           
            try(FileOutputStream fileOut = new FileOutputStream(filePath))
			{
				
				workbook.write(fileOut);
			//	workbook.close();
				// JFrame parent = new JFrame();
	            //    JOptionPane.showMessageDialog(parent, "Report is generated at "+filepath);
				
				org.eclipse.swt.widgets.MessageBox messageBox;
	       		messageBox = new org.eclipse.swt.widgets.MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION| SWT.OK);
	            messageBox.setMessage("Schedule Report is generated successfully.");
	            messageBox.setText("Excel Report");
	            int rc = messageBox.open();
	            if(rc == SWT.OK)
	            {
	            	///open on create
	            	Desktop.getDesktop().open(filePath);
	            }
				
	       
				
			}
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}