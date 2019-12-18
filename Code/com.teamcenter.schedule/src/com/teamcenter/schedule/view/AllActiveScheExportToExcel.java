package com.teamcenter.schedule.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.teamcenter.rac.schedule.commands.assigntotask.SearchCriteriaProviders.Row;
import com.teamcenter.rac.util.Header;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AllActiveScheExportToExcel {

   public void generateReport(Table ReportTable) throws IOException {
		try  {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("ExportData");
			// HSSFRow row = sheet.addMergedRegion();

			HSSFRow row = sheet.createRow(1);
			HSSFCell cell_row1 = row.createCell(1);
			cell_row1.setCellValue("All Active Schedule Report");
			HSSFHeader header = sheet.getHeader();

			String columns[] = { "Schedule Name", "Start Date",
					"End Date", "Percentage" };
			HSSFRow columnRow = sheet.createRow(2);
			for (int i = 0; i < columns.length; i++) {
				HSSFCell R2_C1 = columnRow.createCell(i);
				R2_C1.setCellValue(columns[i]);
			}
		//	change to NatTable 
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
					colCell.setCellValue(tabITemStr);
				}
				rowCnt++;
			}
			
			// sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

///
			DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
			Date date = new Date();
			String dateStr = dateFormat.format(date);
			
			
			   File file = new File("C:\\temp\\ScheduleReport");
		        if (!file.exists()) {
		            if (file.mkdir()) {
		              //  System.out.println("Directory is created!");
		            } else {
		               // System.out.println("Failed to create directory!");
		            }
		        }
			
			// Write the output to a file
			String filepath=file.getAbsolutePath()+"\\AllActiveScheduleExcel_"+dateStr+".xls";
			try(FileOutputStream fileOut = new FileOutputStream(filepath))
			{
				
				wb.write(fileOut);
				// JFrame parent = new JFrame();
	            //    JOptionPane.showMessageDialog(parent, "Report is generated at "+filepath);
				
				org.eclipse.swt.widgets.MessageBox messageBox;
	       		messageBox = new org.eclipse.swt.widgets.MessageBox(Display.getCurrent().getActiveShell(),SWT.ICON_INFORMATION| SWT.OK);
	            messageBox.setMessage("All Active Schedule Report is generated successfully.");
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
			e.printStackTrace();
		}
	}
}