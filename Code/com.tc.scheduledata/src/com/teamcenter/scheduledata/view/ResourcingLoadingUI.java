package com.teamcenter.scheduledata.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridColumnGroup;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
//import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.nebula.*;

import com.teamcenter.rac.kernel.TCSession;

public class ResourcingLoadingUI {
	private static Table table;
	private static Grid grid_calender;
	TCSession session;
	public ResourcingLoadingUI(TCSession session) {
		this.session=session;
	}
/*public static void main(String[] args) {
		callUI();
	}*/
   public void callResourceLoadingUI()
   {

		try {
		//Display display = new Display ();
	    Shell shell = new Shell ();
	    shell.setLayout(new FillLayout());
	    shell.setText("Resource Loading");

	    grid_calender = new Grid(shell,SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    grid_calender.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	    grid_calender.setHeaderVisible(true);
	    grid_calender.setRowHeaderVisible(true);
	    GridColumn column = new GridColumn(grid_calender,SWT.LEFT );
	    column.setText("Users/Date");
	    column.setWidth(100);
	    
	    ArrayList<String> yearList=new ArrayList<String>();
	    yearList.add("2018");
	    yearList.add("2019");
	    yearList.add("2020");
	    yearList.add("2021");
	    yearList.add("2022");
	    
	    LinkedHashMap<String, Integer> monthList= new LinkedHashMap<String, Integer>();
	    monthList.put("Jan", 31);
	    monthList.put("Feb", 28);
	    monthList.put("Mar", 31);
	    monthList.put("Apr", 30);
	    monthList.put("May", 31);
	    monthList.put("Jun", 30);
	    monthList.put("Jul", 31);
	    monthList.put("Aug", 31);
	    monthList.put("Sep", 30);
	    monthList.put("Oct", 31);
	    monthList.put("Nov", 30);
	    monthList.put("Dec", 31);
	    
	    
	    //Set grid column data
	    for(int iYears=0;iYears<yearList.size();iYears++)
	    {
	    	 GridColumnGroup columnGroup = new GridColumnGroup(grid_calender,SWT.CENTER);
	 	     columnGroup.setText(yearList.get(iYears));
	 	    
	 
	 	     for(String month:monthList.keySet())
	 	     {
	 	    	for(int iDays=0;iDays<monthList.get(month);iDays++)
	 	    	{
		 	       	GridColumn column2 = new GridColumn(columnGroup,SWT.NONE);
			 	    column2.setText((iDays+1)+"-"+month+"-"+yearList.get(iYears));
			 	    column2.setWidth(60);
			 	    
	 	    	}
	 	
	 	     }
	    }
	    
		  /*  GC l_gc = new GC(grid_calender);
 	    columnGroup.setExpanded(false);
 	   final int l_colWidth = l_gc.stringExtent(iYears + " <<").x + 6;
        // listen expand event
        columnGroup.addListener(SWT.Expand, new Listener() {
        	@Override
            public void handleEvent(Event event) {
                int l_width = l_colWidth / 3;
                if (l_width < 20) {
                    l_width = 20;
                }
                for (GridColumn l_column : ((GridColumnGroup) event.widget).getColumns()) {
                    l_column.setWidth(l_width);
                }
            }

			
        });
        // listen collapse event
        columnGroup.addListener(SWT.Collapse, new Listener() {
        	@Override
            public void handleEvent(Event event) {
                int l_width = l_colWidth / 2;
                if (l_width < 20) {
                    l_width = 20;
                }
                for (GridColumn l_column : ((GridColumnGroup) event.widget).getColumns()) {
                    if (l_column.isSummary()) {
                        l_column.setWidth(l_width);
                    }
                }
            }
        });
 	     */
	    
	   
	    
	    setGridData();
	    getUserData();
	  
	    shell.setSize(594,342);
	    shell.open();
	    /*while (!shell.isDisposed()) {
	        if (!display.readAndDispatch ()) display.sleep ();
	    }
	    display.dispose ();} catch (Exception e) {
			e.printStackTrace();
		}*/

	
   }catch(Exception e)
	{
	   e.printStackTrace();
	}
   }
private void setGridData() {
	// TODO Auto-generated method stub
	try {
		
		LinkedHashMap<String, LinkedHashMap<String, String>> allDataList=new LinkedHashMap<String, LinkedHashMap<String,String>>();
		
		 LinkedHashMap<String, String> rowList=new LinkedHashMap<String, String>();
		 rowList.put("Task", "task1");
		 rowList.put("start_date", "01-Jan-2018");
		 rowList.put("end_date", "03-Jan-2018");
		 rowList.put("work_complete", "24h");
		 rowList.put("status", "complete");
		 allDataList.put("User1_1", rowList);
		 
		 LinkedHashMap<String, String> rowList1=new LinkedHashMap<String, String>();
		 rowList1.put("Task", "task2");
		 rowList1.put("start_date", "15-Jan-2018");
		 rowList1.put("end_date", "1-Feb-2018");
		 rowList1.put("work_complete", "24h");
		 rowList1.put("status", "complete");
		 allDataList.put("User1_2", rowList1);
		 
		 LinkedHashMap<String, String> rowList2=new LinkedHashMap<String, String>();
		 rowList2.put("Task", "task3");
		 rowList2.put("start_date", "01-Jan-2018");
		 rowList2.put("end_date", "05-Jan-2018");
		 rowList2.put("work_complete", "40h");
		 rowList2.put("status", "complete");
		 allDataList.put("User2_3", rowList2);
		 
		 LinkedHashMap<String, String> rowList3=new LinkedHashMap<String, String>();
		 rowList3.put("Task", "task3");
		 rowList3.put("start_date", "03-Jan-2018");
		 rowList3.put("end_date", "06-Jan-2018");
		 rowList3.put("work_complete", "32");
		 rowList3.put("status", "complete");
		 allDataList.put("User2_3", rowList3);
		 
		 for(String userKey:allDataList.keySet())
		 {
			LinkedHashMap<String, String> data = allDataList.get(userKey);
			 GridItem item=new GridItem(grid_calender, SWT.NONE) ;
			 item.setText(0,userKey.split("_")[0]);
			 //item.setBackground(0,GUIHelper.COLOR_GREEN);
			 
			 ArrayList<String> startDateList = getSplitedStr(data.get("start_date"));
			 ArrayList<String> endDateList = getSplitedStr(data.get("end_date"));
			 String work_complete = data.get("work_complete");
			 
			 ArrayList<Integer> outputColList = new ArrayList<Integer>();
			 AddColorTocell(startDateList,item,outputColList);
			 AddColorTocell(endDateList,item,outputColList);
			
			  System.out.println("columns="+outputColList);
			  int startCol=outputColList.get(0);
			  int endCol=outputColList.get(1);
			  
			  
			  for(int colg=1;colg<grid_calender.getColumns().length;colg++)
			  {
				  item.setBackground(colg, SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));  
			  }
			  for(int cosll=startCol;cosll<=endCol;cosll++)
			  {
				  item.setText(cosll,"8");
				  item.setBackground(cosll, SWTResourceManager.getColor(SWT.COLOR_RED));
			  }
			 
		 }
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
 private static void AddColorTocell(ArrayList<String> DateList, GridItem item,  ArrayList<Integer> outputColList) {
	
	
	 GridColumnGroup[] columnsGrp = grid_calender.getColumnGroups();
	 for(int colGrp=0;colGrp<columnsGrp.length;colGrp++)
	 {
		
		 if(columnsGrp[colGrp].getText().equals(DateList.get(2)))
		 {
			 GridColumn[] columns = columnsGrp[colGrp].getColumns();
			 for(int col=0;col<columns.length;col++)
			 {
				 int intday = Integer.parseInt(DateList.get(0));
				 String day=intday+"-"+DateList.get(1)+"-"+DateList.get(2);
				 if(columns[col].getText().equals(day))
				 {
					 //item.setBackground(col,SWTResourceManager.getColor(SWT.COLOR_BLACK));
					 item.setText((col+1),"8");
					 outputColList.add((col+1));
					// item.setBackground(col,SWT.);
					 break;
				 }
			 }
			 break;
		 }
		
	 }
}
static ArrayList<String> getSplitedStr(String date)
 {
	 ArrayList<String> list=new ArrayList<String>();
	 list.add(date.split("-")[0]);
	 list.add(date.split("-")[1]);
	 list.add(date.split("-")[2]);
	 return list;
 }
void getUserData() {
	try
	{
		TCDataFunctionality obj=new TCDataFunctionality(session);
		ArrayList<String> userList = obj.getAllUserList();
		System.out.println("userList=="+userList);
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
  }
}
