package com.tc.writeexcel.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
	//	IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	//	MessageDialog.openInformation(
		//		window.getShell(),
		//		"Writeexcel",
		//		"Hello, Eclipse world");
		
		
		LinkedHashMap<String, ArrayList<Object>> list=new LinkedHashMap<String, ArrayList<Object>>();
		//1.Export
		ExportDataset();
		
		//2. Fetch schedule and tasks
		ArrayList<String> TaskName=new ArrayList<String>();
		ArrayList<String> TaskStartDate=new ArrayList<String>();
		ArrayList<String> TaskEndDate=new ArrayList<String>();
		ArrayList<String> TaskActualStartDate=new ArrayList<String>();
		ArrayList<String> TaskActualEndDate=new ArrayList<String>();
		AbstractAIFUIApplication app= AIFUtility.getCurrentApplication();
		TCSession session = (TCSession) app.getSession();
		
		System.out.println("\n inside getScheduleData");
		SavedQueryService service = SavedQueryService.getService(session.getSoaConnection());
		FindSavedQueriesCriteriaInput queryObject[] = new FindSavedQueriesCriteriaInput[1];
		FindSavedQueriesCriteriaInput queryObject1 = new FindSavedQueriesCriteriaInput();
		String[] qryName = { "Schedule_search" };
		String[] qryDesc = { "" };
		queryObject1.queryNames = qryName;
		queryObject1.queryDescs = qryDesc;
		FindSavedQueriesResponse response;
		queryObject[0] = queryObject1;
		ModelObject[] savequery = null;
		try {
			response = service.findSavedQueries(queryObject);
			savequery = response.savedQueries;

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] id = { "ID" };
		String[] val = { "000798" }; // 000646 , 000638,
		ModelObject save = savequery[0];
		ExecuteSavedQueryResponse result;
		
			try {
				result = service.executeSavedQuery(save, id, val, 1000);
				int setSchedule_count= result.nFound;
				ModelObject[] setSchedule_object= result.objects;
	            System.out.println("Stage 2s");
	            
	            TCComponentSchedule mySch = (TCComponentSchedule) setSchedule_object[0];
				System.out.println("***mySch====" + mySch.toDisplayString());
	              
					//TCComponentUser user=new TCComponentUser();
				TCComponentUser user=session.getUser();
				System.out.println("***User====>"+user);	
				Property t = null;
				try {
					t = mySch.getPropertyObject("fnd0SummaryTask");
				} catch (Exception e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				
				System.out.println("t is " + t);
				
				TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
				AIFComponentContext[] SchTaskArr = null;
				try {
					SchTaskArr = mySchTask.getChildren();
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("SchTaskArr size is " + SchTaskArr.length);

				for(int ich=0;ich<SchTaskArr.length;ich++)
				{
					TCComponent myTaskActivity=(TCComponent)SchTaskArr[ich].getComponent();
					try {
						TaskName.add(myTaskActivity.getProperty("object_name").toString());
					} catch (TCException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					try {
						TaskStartDate.add(myTaskActivity.getProperty("start_date").toString());
					} catch (TCException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						TaskEndDate.add(myTaskActivity.getProperty("finish_date").toString());
					} catch (TCException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						TaskActualStartDate.add(myTaskActivity.getProperty("actual_start_date").toString());
					} catch (TCException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						TaskActualEndDate.add(myTaskActivity.getProperty("actual_finish_date").toString());
					} catch (TCException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AIFComponentContext[] SchTasSubtask = null;
					try {
						SchTasSubtask = myTaskActivity.getChildren();
					} catch (TCException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("SchTasSubtask size is " + SchTasSubtask.length);
					for(int ichsub=0;ichsub<SchTasSubtask.length;ichsub++)
					{
						TCComponent mysubTaskActivity=(TCComponent)SchTasSubtask[ichsub].getComponent();
						try {
							TaskName.add(mysubTaskActivity.getProperty("object_name").toString());
						} catch (TCException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							TaskStartDate.add(mysubTaskActivity.getProperty("start_date").toString());
						} catch (TCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							TaskEndDate.add(mysubTaskActivity.getProperty("finish_date").toString());
						} catch (TCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							TaskActualStartDate.add(mysubTaskActivity.getProperty("actual_start_date").toString());
						} catch (TCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							TaskActualEndDate.add(mysubTaskActivity.getProperty("actual_finish_date").toString());
						} catch (TCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Stage 3");
					}
				}
				System.out.println("Task Names as follows");
			} catch (ServiceException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
         for(int iEx=0;iEx <TaskName.size();iEx++)
 		{
        	  ArrayList<Object> alldateslist=new ArrayList<Object>();
        		String[] dates={TaskStartDate.get(iEx),TaskEndDate.get(iEx)};
        		ArrayList<Integer> NormalweeksArr=getWeekArr(dates);
        		alldateslist.add(NormalweeksArr);
        		
        		String[] Act_dates={TaskActualStartDate.get(iEx),TaskActualEndDate.get(iEx)};
        		ArrayList<Integer> Act_weeksArr=getWeekArr(Act_dates);
        		alldateslist.add(Act_weeksArr);
        		
        		list.put(TaskName.get(iEx),alldateslist);
 			}
         generateExcel(list);
//				try 
//				{
//					//Write the workbook in file system
//				    FileOutputStream out = new FileOutputStream(new File("C:\\Temp\\PPAF_Report_Final.XLSX"));
//				    workbook.write(out);
//				    out.close();
//				    
//				    System.out.println("Data written on file successful.");
//				     
//				} 
//				catch (Exception e) 
//				{
//				    e.printStackTrace();
//				}
		return null;
	}
	
	public void ExportDataset() {
	AbstractAIFUIApplication app=AIFUtility.getCurrentApplication();
	TCSession session=(TCSession) app.getSession();
	try 
	{
		TCComponentQueryType Qtype=(TCComponentQueryType) session.getTypeComponent("ImanQuery");
		TCComponentQuery query=(TCComponentQuery) Qtype.find("1_Search_DatasetName");	
		String [] Entryname={"PPAF_Report"};
		String [] Entryval={"PPAF_Report"};		
		TCComponent[] comp=query.execute(Entryname, Entryval);
				if(comp.length > 0)
				{
				System.out.println(comp[0].getStringProperty("object_name"));
				TCComponentDataset latesdataset=(((TCComponentDataset) comp[0]).latest());
				String namedRefFileName[]=latesdataset.getFileNames("excel");
				if(namedRefFileName.length >0)
				{
					File exported = latesdataset.getFile("excel", namedRefFileName[0],"C:\\TEMP\\ScheduleReport\\report");
					System.out.println("exported=="+exported);
				}
		}
	} 
	catch (TCException e) 
	{
		e.printStackTrace();
	}
	}
	
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 * @param list 
	 */
	public static  void generateExcel(LinkedHashMap<String, ArrayList<Object>> list){
			
		XSSFSheet sheet=null;
		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		
		 try {
			file = new FileInputStream(new File("C:\\TEMP\\ScheduleReport\\report\\PPAF_Report.XLSX"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
         sheet = workbook.getSheetAt(0);
         
         //set style
         CellStyle style = workbook.createCellStyle();
	       style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	       style.setFillPattern(CellStyle.THICK_BACKWARD_DIAG);
	       
	       //set style
	         CellStyle style1 = workbook.createCellStyle();
		       style1.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		       style1.setFillPattern(CellStyle.THICK_BACKWARD_DIAG);
	       //Cell cell15 = row.createCell(15);
		     
         
	           XSSFRow secondRow = sheet.getRow(2);
	           int cnt=12;          	
	            for(String keyTask:list.keySet())
	            {
	            	XSSFRow row = sheet.getRow(cnt);
	            	XSSFRow row1 = sheet.getRow(cnt+1);
		            XSSFCell firstCell = row.getCell(1);
		            firstCell.setCellValue(keyTask);	
	            	
	            	
	            	//For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = secondRow.cellIterator();
                    int cellCnt=0;
                    while (cellIterator.hasNext())
                    {
                        Cell seconRowcell = cellIterator.next();//Week cell
                         
                       
                        	//if(seconRowcell.getNumericCellValue()!=null)
                        	 {
                        		int weekValue =(int) seconRowcell.getNumericCellValue();
                        		//Match weeks and color
                        		int tempcnt=0;
                        		for(Object weekIntarr:list.get(keyTask))
                        		{
                        			ArrayList<Integer> intArr=(ArrayList<Integer>) weekIntarr;
                        			
                        			for(Integer weekInt: intArr)
                        			{
                        				if(weekInt==weekValue)
                            			{
                            				XSSFCell rowCell = row.getCell(cellCnt);
                            				XSSFCell Act_rowCell = row1.getCell(cellCnt);
                            				if(tempcnt==0)
                            				  rowCell.setCellStyle(style);
                            				else
                            					Act_rowCell.setCellStyle(style1);
                            				
                            			}
                        			}
                        			tempcnt++;
                        			
                        		}
                        		                        		
                        	 }
                     
                       
                        cellCnt++;
                    }
                    cnt=cnt+2;
	            	
	            }
	            	
				try 
				{
					//Write the workbook in file system
				    FileOutputStream out = new FileOutputStream(new File("C:\\TEMP\\ScheduleReport\\report\\PPAF_Report.XLSX"));
				    workbook.write(out);
				    out.close();
				    
				    System.out.println("Data written on file successful.");
				     
				} 
				catch (Exception e) 
				{
				    e.printStackTrace();
				}
	}

	//**************To get Week*********
	ArrayList<Integer> getWeekArr(String[] dates)
	{
		ArrayList<Integer> weeksArr=new ArrayList<Integer>();
		
		//String[] dates={TaskStartDate.get(iEx),TaskEndDate.get(iEx)};
			try {
				 
				for(int i=0;i<dates.length;i++)
				{
					Date date = new Date(dates[i]);
				    Calendar calendar = Calendar.getInstance();
				    calendar.setTime(date);
				    int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);// the day of the week in numerical format
				    weeksArr.add(weekOfYear);
				}
				 
				System.out.println("start end weeks="+weeksArr);
				if(weeksArr!=null && weeksArr.size()>0)
				{
					int startWeek=weeksArr.get(0);
					int endWeek=weeksArr.get(1);
					
					if(endWeek>startWeek)
					{
						weeksArr=new ArrayList<Integer>();
						for(int j=startWeek;j<=endWeek;j++)
						{
							weeksArr.add(j);
						}
					}else
					{
						int newWeek=endWeek+52;
						weeksArr=new ArrayList<Integer>();
						for(int j=startWeek;j<=newWeek;j++)
						{
							weeksArr.add(j);
						}
					}				
					System.out.println("All weeks="+weeksArr);					
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}
		 return weeksArr;
	}
}
