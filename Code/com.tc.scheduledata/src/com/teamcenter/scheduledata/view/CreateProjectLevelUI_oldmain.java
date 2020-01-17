/*package com.teamcenter.scheduledata.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class CreateProjectLevelUI_oldmain {
	
	private static Table table_ProjectReportTable;
	private static TCSession session;
	private static ModelObject[] AllActiveProjList;
	static LinkedHashMap<String, ModelObject> ProjectList = new LinkedHashMap<String, ModelObject>();
	static LinkedHashMap<String, LinkedList<String>> ProjectStatusData = new LinkedHashMap<String, LinkedList<String>>();
	private static ProgressBar progressBar;
	static ArrayList<TCComponent> mileStoneObjList=new ArrayList<TCComponent>();
	private static String taskType;
	static TCDataFunctionality obj = new TCDataFunctionality(session);
	private static TableColumn tblclmnTaskStatus;
	private static TableColumn tblclmnResourceAssigned;

	public	CreateProjectLevelUI_oldmain(){
		this.session = session;
	}
	public static void main(String[] args) {
		CreateProjectUI();
	}
	
	public static void CreateProjectUI()
	{
		try {
			//Display display=Display.getDefault();
			//Shell schshell = new Shell(display);
			Shell schshell = new Shell();
			schshell.setSize(781, 462);
			schshell.setText("Project View");
			schshell.setLayout(new GridLayout(1, false));
		
			Composite composite = new Composite(schshell, SWT.NONE);
			composite.setLayout(new GridLayout(1, false));
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			
			Composite composite_1 = new Composite(composite, SWT.NONE);
			composite_1.setLayout(new GridLayout(2, false));
			GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_composite_1.heightHint = 32;
			composite_1.setLayoutData(gd_composite_1);
			
			Label lblNewLabel_AllActiveProjs = new Label(composite_1, SWT.NONE);
			GridData gd_lblNewLabel_AllActiveProjs = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
			gd_lblNewLabel_AllActiveProjs.widthHint = 111;
			lblNewLabel_AllActiveProjs.setLayoutData(gd_lblNewLabel_AllActiveProjs);
			lblNewLabel_AllActiveProjs.setText("Active Projects");
			
			final Combo combo_AllActiveProjs = new Combo(composite_1, SWT.NONE);
		
			combo_AllActiveProjs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Composite composite_2 = new Composite(composite, SWT.NONE);
			composite_2.setLayout(new GridLayout(1, false));
			GridData gd_composite_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_composite_2.heightHint = 69;
			composite_2.setLayoutData(gd_composite_2);
		
			final Composite composite_5 = new Composite(composite_2, SWT.NONE);
			composite_5.setLayout(new GridLayout(1, false));
			GridData gd_composite_5 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_composite_5.heightHint = 60;
			composite_5.setLayoutData(gd_composite_5);
			
			Button btnNewButton = new Button(composite_5, SWT.NONE);
			btnNewButton.setText("New Button");
			btnNewButton.setVisible(false);
			if(milestonesList!=null && milestonesList.size()>0)
			{
				for(int i=0;i<milestonesList.size();i++)
				{
					Button btnNewButton12 = new Button(composite_5, SWT.NONE);
					btnNewButton12.setImage(SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
				}
				
			}
			
			//progressBar = new ProgressBar(composite_5, SWT.NONE);
			progressBar = new ProgressBar(composite_5, SWT.NONE);
			
			GridData gd_progressBar = new GridData(SWT.FILL, SWT.CENTER, false, false,1, 1);
			gd_progressBar.heightHint = 11;
			progressBar.setLayoutData(gd_progressBar);
			progressBar.setSelection(perComlStatusProgress);
			progressBar.setVisible(true);
	
		    
		  //progressBar.setSelection(milestonesList.size());
			
			Composite composite_3 = new Composite(composite, SWT.NONE);
			composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			composite_3.setLayout(new GridLayout(1, false));
			
			createTable(composite_3);
			
			
			Composite composite_4 = new Composite(composite_3, SWT.NONE);
			GridData gd_composite_4 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
			gd_composite_4.widthHint = 501;
			gd_composite_4.heightHint = 35;
			composite_4.setLayoutData(gd_composite_4);
			
			// combobox projects
			
			
			AllActiveProjList = obj.getAllActiveProjects();
			String[] projArr=new String[AllActiveProjList.length];
			
			for(int i=0;i<AllActiveProjList.length;i++)
			{
				projArr[i]=AllActiveProjList[i].toString();
				ProjectList.put(AllActiveProjList[i].toString(), AllActiveProjList[i]);
			}
			
			if(projArr!=null && projArr.length>0)
				combo_AllActiveProjs.setItems(projArr);
			
			System.out.println("ProjectList"+ProjectList);
			
			
			
			combo_AllActiveProjs.addSelectionListener(new SelectionListener() {
			    private com.teamcenter.soa.client.model.Property prop;
				private String taskTypeStatus;
				private TCComponent myTaskActivity;
				private int perComlStatusProgress;
			
				public void widgetSelected(SelectionEvent e) {
			    String selection = combo_AllActiveProjs.getText();
			   
			   System.out.println("selection====="+selection);
			     //  progressBar.setSelection(50);
			       ModelObject getProjModelObj = ProjectList.get(selection);
			       TCComponentSchedule sche=(TCComponentSchedule)getProjModelObj;
			       
			       ArrayList<String> milestonesList = setProgressBar(sche);
			       
			       if(milestonesList!=null && milestonesList.size()>0)
					{
					        composite_5.setLayout(new GridLayout(milestonesList.size(), false));
							GridData gd_composite_5 = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
							gd_composite_5.heightHint = 57;
							composite_5.setLayoutData(gd_composite_5);
							
								for(int i=0;i<milestonesList.size();i++)
								{
									Button btnNewButton12 = new Button(composite_5, SWT.NONE);
									btnNewButton12.setText("dfdsf");
									//btnNewButton12.setImage(SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
								}
								progressBar = new ProgressBar(composite_5, SWT.NONE);							
								progressBar.setSelection(perComlStatusProgress);
								progressBar.setVisible(true);
					}
			    
			       
			     
			       displayReport();
			       
			       
			       TCComponentSchedule mySch = (TCComponentSchedule) getSchedule_object()[0];
					System.out.println("***mySch====" + mySch.toDisplayString());     
					TCComponentUser user=session.getUser();
					System.out.println("***User====>"+user);	
					Property t = mySch.getPropertyObject("fnd0SummaryTask");
			       
			      }
			    public ArrayList<String> setProgressBar(TCComponentSchedule sche)
			    {
			    	ArrayList<String> milestonesList = new ArrayList<String>();
			    	ArrayList<String> milestoneComplete = new ArrayList<String>();
					ArrayList<String> milestoneDelayed = new ArrayList<String>();
					ArrayList<String> milestoneInProgress = new ArrayList<String>();
					
			    	  try {
						String perComplete = sche.getProperty("fnd0SSTCompletePercent");
						if(perComplete!=null && perComplete.length()>0)
						{
							System.out.println("status====="+perComplete);
							perComplete = perComplete.replaceAll("[^\\d.]", "");
						}else
							perComplete="0";
					
						 perComlStatusProgress = Integer.parseInt(perComplete);
						System.out.println("perComlStatusProgress=="+perComlStatusProgress);
						
						
						ArrayList<TCComponent> milestone = getmilestons(sche);
					
						System.out.println("milestone size ===="+milestone.size());
						for(int i = 0; i<milestone.size(); i++)
						{
							
							String mile = milestone.get(i).toString();
							milestonesList.add(mile);
							System.out.println("###milestone====="+milestone.get(i).toString());	
							
							String milestoneStatus = milestone.get(i).getPropertyDisplayableValue("fnd0status");
							if(milestoneStatus.equals("Complete"))
							{
								milestoneComplete.add(mile);
							}
							else if(milestoneStatus.equals("In Progress"))
							{
								milestoneDelayed.add(mile);
							}
							else if(milestoneStatus.equals("In Progress"))
							{
								milestoneInProgress.add(mile);
							}
						}
						
					} catch (TCException | NotLoadedException e) {
					
						e.printStackTrace();
					}
			    	  return milestonesList;
				}
			    
		
			    
			    
			    public ArrayList<TCComponent> getmilestons(TCComponentSchedule sche){
			    	mileStoneObjList = new ArrayList<TCComponent>();
					try {
						
						 prop = sche.getPropertyObject("fnd0SummaryTask");
						System.out.println("prop values" + prop);
						TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) prop.getModelObjectValue();
						AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
						System.out.println("SchTaskArr size is " + SchTaskArr.length);	
						for (int j = 0; j <SchTaskArr.length; j++) 
						{
						myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
						System.out.println("myTaskActivity"+myTaskActivity);	
						taskType = myTaskActivity.getProperty("fnd0TaskTypeString");
						 
						
						//Task = myTaskActivity.getProperty("complete_percent"); 
						if(taskType.equalsIgnoreCase("M"))
						{
							
							 mileStoneObjList.add(myTaskActivity);
							 
							System.out.println("Milestone **"+taskType);
							System.out.println("mileStoneObjList"+mileStoneObjList);
							
						}else if (taskType.equalsIgnoreCase("T"))
						{
							mileStoneObjList.add(myTaskActivity);
							System.out.println("Task **"+taskType);
						}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					return mileStoneObjList;
				}
				public void widgetDefaultSelected(SelectionEvent e) {
			        System.out.println(combo_AllActiveProjs.getText());
			      }
			      
			    });
			
			schshell.open();
			schshell.setVisible(true);
			while (!schshell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
			schshell.setVisible(true);
			// schshell.close();
			 }
			
		//	schshell.open();
		//	}
		} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createTable(Composite composite_3) {
		
		table_ProjectReportTable = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table_ProjectReportTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table_ProjectReportTable.heightHint = 177;
		table_ProjectReportTable.setLayoutData(gd_table_ProjectReportTable);
		table_ProjectReportTable.setHeaderVisible(true);
		table_ProjectReportTable.setLinesVisible(true);
		
		TableColumn tblclmnTaskName = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnTaskName.setWidth(100);
		tblclmnTaskName.setText("Task Name");
		
		TableColumn tblclmnStarDate = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnStarDate.setWidth(100);
		tblclmnStarDate.setText("Start Date");
		
		TableColumn tblclmnEndDate = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnEndDate.setWidth(100);
		tblclmnEndDate.setText("End Date");
		
		TableColumn tblclmnPercentageCompletion = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnPercentageCompletion.setWidth(100);
		tblclmnPercentageCompletion.setText("Percentage Completation");
		
		TableColumn tblclmnTaskStartDate = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnTaskStartDate.setWidth(100);
		tblclmnTaskStartDate.setText("Task Start Date");
		
		TableColumn tblclmnTaskEndDate = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnTaskEndDate.setWidth(100);
		tblclmnTaskEndDate.setText("Task End Date");
		
		tblclmnTaskStatus = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnTaskStatus.setWidth(100);
		tblclmnTaskStatus.setText("Task Status");
		
		 tblclmnResourceAssigned = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnResourceAssigned.setWidth(100);
		tblclmnResourceAssigned.setText("Resource Assigned");
		
	}

	//IncreaseProgress();
	public static void setimagetotablecoloum(String status)
	{
	
		if(status.equalsIgnoreCase("In Progress"))
		{
				status="in_progress";
				
				TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			
			    item.setImage(6,SWTResourceManager.getImage(CreateProjectLevelUI_oldmain.class,"icons/diaBlueComplete.png"));
			   
				
		}else if(status.equalsIgnoreCase("Complete"))
		{
			status = "complete";
			TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			item.setImage(6, SWTResourceManager.getImage(CreateProjectLevelUI_oldmain.class,"icons/greenDiamond1.png"));
			
		}else if(status.equalsIgnoreCase("Not Started"))
		{
			status = "Not Started";
			TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			item.setImage(6, SWTResourceManager.getImage(CreateProjectLevelUI_oldmain.class,"icons/yellowdiamond1.png"));
		}
	}

public static void displayReport() {
	try {
		System.out.println("inside displayReport method");

		LinkedHashMap<String, ArrayList<String>> dataList = obj.getTableData();
		Set<String> set = dataList.keySet();
		for (String key : set) {
			TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			ArrayList<String> dd = dataList.get(key);
			for (int i = 0; i < dd.size(); i++) {
				item.setText(i, dd.get(i));
				item.setData(key);
			}
		}

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

}


}




*/