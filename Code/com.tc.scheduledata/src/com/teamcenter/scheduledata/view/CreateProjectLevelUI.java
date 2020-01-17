package com.teamcenter.scheduledata.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
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

public class CreateProjectLevelUI {
	
	private static Table table_ProjectReportTable;
	private static TCSession session;
	private static ModelObject[] AllActiveProjList;
	static LinkedHashMap<String, ModelObject> ProjectList = new LinkedHashMap<String, ModelObject>();
	static LinkedHashMap<String, LinkedList<String>> ProjectStatusData = new LinkedHashMap<String, LinkedList<String>>();
	private static ProgressBar progressBar;
	static ArrayList<TCComponent> mileStoneObjList=new ArrayList<TCComponent>();
	private static String taskType;
	private static ArrayList<String> milestonesList;
	private static int perComlStatusProgress;
	static TCDataFunctionality obj = new TCDataFunctionality(session);
	private static Tree tableTree;
	private static Combo combo_AllActiveProjs;
	private Composite graphComposite;
	private Composite milestoneComposite;
	/**
	 * @wbp.parser.entryPoint
	 */
	public	CreateProjectLevelUI(){
		//this.session = session;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	/*public static void main(String[] args) {
		CreateProjectUI();
	}*/
	
	/**
	 * @wbp.parser.entryPoint
	 */
  public void CreateProjectUI()
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
			
			 combo_AllActiveProjs = new Combo(composite_1, SWT.NONE);
			//combo_AllActiveProjs.setToolTipText("Select Project");
			combo_AllActiveProjs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			
			Composite composite_2 = new Composite(composite, SWT.NONE);
			composite_2.setLayout(new GridLayout(1, false));
			GridData gd_composite_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_composite_2.heightHint = 69;
			composite_2.setLayoutData(gd_composite_2);

			 milestoneComposite = new Composite(composite_2, SWT.NONE);
			 milestoneComposite.setLayout(new GridLayout(2, false));
			 GridData gd_composite_mile = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
			 gd_composite_mile.heightHint = 57;
			 milestoneComposite.setLayoutData(gd_composite_mile);
			
				/*for(int i=0;i<2;i++)
				{
					Button btnNewButton12 = new Button(milestoneComposite, SWT.NONE);
					btnNewButton12.setImage(SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
				}*/
			
			 graphComposite = new Composite(composite_2, SWT.NONE);
			graphComposite.setLayout(new GridLayout(2, false));
			GridData gd_composite_5 = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
			gd_composite_5.heightHint = 57;
			graphComposite.setLayoutData(gd_composite_5);
			
			
			
			
			progressBar = new ProgressBar(graphComposite, SWT.NONE);
		    progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		  //  progressBar.setSelection(perComlStatusProgress);
		   // progressBar.setSelection(50);
					
			Composite composite_3 = new Composite(composite, SWT.NONE);
			composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
			composite_3.setLayout(new GridLayout(1, false));
			
			createTable(composite_3);
			
			Composite composite_4 = new Composite(composite_3, SWT.NONE);
			GridData gd_composite_4 = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
			gd_composite_4.widthHint = 501;
			gd_composite_4.heightHint = 35;
			composite_4.setLayoutData(gd_composite_4);
			
			// combobox projects
			
			TCDataFunctionality obj = new TCDataFunctionality(session);
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
			     
					public void widgetSelected(SelectionEvent e) {
						String selection = combo_AllActiveProjs.getText();
						
						loadGraphAndTable(selection);
					}
				
				    
				public void widgetDefaultSelected(SelectionEvent e) {
			       
			      }
			      
			    });
			
			schshell.open();
			schshell.setVisible(true);
			/*while (!schshell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
			schshell.setVisible(true);
			// schshell.close();
			 }
			
		//	schshell.open();
		//	}
		}*/ 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void loadGraphAndTable(String selProject) {
		try
		{

		  com.teamcenter.soa.client.model.Property prop;
		  System.out.println("selection====="+selProject);
	      ModelObject getProjModelObj = ProjectList.get(selProject);
	       TCComponentSchedule sche=(TCComponentSchedule)getProjModelObj;
	       
	       setProgressBar(sche);
	       displayReport(selProject);
	       
	        int size=20;
			for(int i=0;i<2;i++)
			{
				Button btnNewButton12 = new Button(milestoneComposite, SWT.NONE);
				btnNewButton12.setImage(SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
			}
	       
	      
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	 public ArrayList<String> setProgressBar(TCComponentSchedule sche)
	    {
	    	 milestonesList = new ArrayList<String>();
	    	ArrayList<String> milestoneComplete = new ArrayList<String>();
			ArrayList<String> milestoneDelayed = new ArrayList<String>();
			ArrayList<String> milestoneInProgress = new ArrayList<String>();
			
	    	  try {
				String perComplete = sche.getProperty("fnd0SSTCompletePercent");
				String perComplete_temp="0";
				if(perComplete!=null && perComplete.length()>0)
				{
					System.out.println("status====="+perComplete);
					perComplete_temp = perComplete.replaceAll("[^\\d.]", "");
				}else
					perComplete_temp="0";
			
				 int perComlStatusProgress1 = Integer.parseInt(perComplete_temp);
				System.out.println("perComlStatusProgress1=="+perComlStatusProgress1);
				progressBar.setSelection(perComlStatusProgress1);
				 progressBar.setToolTipText(perComplete);
				ArrayList<TCComponent> milestone = getmilestons(sche);
			
				/*System.out.println("milestone size ===="+milestone.size());
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
				}*/
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
	    	  return milestonesList;
		}
	    

 
 public ArrayList<TCComponent> getmilestons(TCComponentSchedule sche){
 	ArrayList<TCComponent> milestones = new ArrayList<TCComponent>();
		try {
			
			 com.teamcenter.soa.client.model.Property prop = sche.getPropertyObject("fnd0SummaryTask");
			System.out.println("prop values" + prop);
			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) prop.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);	
			for (int j = 0; j <SchTaskArr.length; j++) 
			{
			TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
			System.out.println("myTaskActivity"+myTaskActivity);	
			taskType = myTaskActivity.getProperty("fnd0TaskTypeString");
			//Task = myTaskActivity.getProperty("complete_percent"); 
			if(taskType.equalsIgnoreCase("M"))
			{
				mileStoneObjList.add(myTaskActivity);
				System.out.println("Milestone **"+taskType);
				
			}/*else if (taskType.equalsIgnoreCase("T"))
			{
				mileStoneObjList.add(myTaskActivity);
				System.out.println("Task **"+taskType);
			}*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return milestones;
	}
 

	
 


	public static void setimagetotablecoloum(String status)
	{
	
		if(status.equalsIgnoreCase("In Progress"))
		{
				status="in_progress";
				
				TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			
			    item.setImage(6,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
			   
				
		}else if(status.equalsIgnoreCase("Complete"))
		{
			status = "complete";
			TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			item.setImage(6, SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/greenDiamond1.png"));
			
		}else if(status.equalsIgnoreCase("Not Started"))
		{
			status = "Not Started";
			TableItem item = new TableItem(table_ProjectReportTable, SWT.NONE);
			item.setImage(6, SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/yellowdiamond1.png"));
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void displayReport(String selection) {
		try {
			System.out.println("inside displayReport method");

			 LinkedHashMap<String, LinkedHashMap<String, String>> tableAlldataList = obj.getTableData(selection);
			 
			 Set<String> keys = tableAlldataList.keySet();
			 for(String key:keys)
			 {
				  LinkedHashMap<String, String> taskData = tableAlldataList.get(key);
				 // TreeItem mileStoneItem = null; //= new TreeItem(tableTree, SWT.NONE);// item =new TableItem(table_ProjectReportTable, SWT.NONE);
				  //TreeItem taskItem;
				  TreeItem item = new TreeItem(tableTree, SWT.NONE);
				    String task_name = taskData.get("TASK_NAME");
				    String task_type = taskData.get("TASK_TYPE");
				   System.out.println("task_type=="+task_type);
				   // if(task_type.equals("M"))
				    {
				    	item.setText(0,taskData.get("TASK_NAME"));
				    	item.setText(1,taskData.get("TASK_START_DATE"));
						item.setText(2,taskData.get("TASK_END_DATE"));
						item.setText(3,taskData.get("TASK_PER_COMPLETE"));
						//item.setText(4,taskData.get("TASK_STATUS"));
						if(task_type.equals("M"))
						{
							String status=taskData.get("TASK_STATUS");
							//if(status.equalsIgnoreCase("in_progress") || status.equalsIgnoreCase("In Progress"))
								
							if(status.equalsIgnoreCase("Complete") || status.equalsIgnoreCase("complete") )
								item.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/greenDiamond.gif"));
							else if(status.equalsIgnoreCase("Late") || status.equalsIgnoreCase("late"))
								item.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/redDiamond.gif"));
							else
								item.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/whiteDiamond.gif"));
						}else
						{
							item.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/task.png"));
						}
							
						item.setText(5,taskData.get("TASK_ASSIGNEE"));
				    }
					  
				    /*ArrayList<String> mileStonelist = obj.getMileshtoneList(selection);
				    for(String milestone:mileStonelist)
				    {
				    	if(milestone.equals(task_name))
				    	{
				    		if()
				    		mileStoneItem = new TreeItem(tableTree, SWT.NONE);
				    		mileStoneItem.setText(0,taskData.get("TASK_NAME"));
					    	mileStoneItem.setText(1,taskData.get("TASK_START_DATE"));
							mileStoneItem.setText(2,taskData.get("TASK_END_DATE"));
							mileStoneItem.setText(3,taskData.get("TASK_STATUS"));
							mileStoneItem.setText(4,taskData.get("TASK_PER_COMPLETE"));
							mileStoneItem.setText(5,taskData.get("TASK_ASSIGNEE"));
				    		
				    	}else
				    	{
				    		taskItem = new TreeItem(mileStoneItem, SWT.NONE);
				    		taskItem.setText(0,taskData.get("TASK_NAME"));
				    		taskItem.setText(1,taskData.get("TASK_START_DATE"));
					    	taskItem.setText(2,taskData.get("TASK_END_DATE"));
					    	taskItem.setText(3,taskData.get("TASK_STATUS"));
					    	taskItem.setText(4,taskData.get("TASK_PER_COMPLETE"));
					    	taskItem.setText(5,taskData.get("TASK_ASSIGNEE"));	
				    	}
				    }*/
					
			 }
			 
			 
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private static void createTable(Composite composite_3) {
		
		
		 tableTree = new Tree(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table_ProjectReportTable = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_table_ProjectReportTable.heightHint = 124;
		tableTree.setLayoutData(gd_table_ProjectReportTable);
		tableTree.setHeaderVisible(true);
		tableTree.setLinesVisible(true);
		
		
    TreeColumn column1 = new TreeColumn(tableTree, SWT.LEFT);
    column1.setText("Name");
    column1.setWidth(200);
    TreeColumn column2 = new TreeColumn(tableTree, SWT.CENTER);
    column2.setText("Start Date");
    column2.setWidth(100);
    TreeColumn column3 = new TreeColumn(tableTree, SWT.RIGHT);
    column3.setText("End date");
    column3.setWidth(100);
    TreeColumn perComplete = new TreeColumn(tableTree, SWT.RIGHT);
    perComplete.setText("Per Complete");
    perComplete.setWidth(100);
    TreeColumn status = new TreeColumn(tableTree, SWT.RIGHT);
    status.setText("Status");
    status.setWidth(100);
    TreeColumn assignee = new TreeColumn(tableTree, SWT.RIGHT);
    assignee.setText("Assignee");
    assignee.setWidth(100);
		
    /*	table_ProjectReportTable = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table_ProjectReportTable = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_table_ProjectReportTable.heightHint = 124;
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
		
		TableColumn tblclmnTaskStatus = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnTaskStatus.setWidth(100);
		tblclmnTaskStatus.setText("Task Status");
		
		TableColumn tblclmnResourceAssigned = new TableColumn(table_ProjectReportTable, SWT.NONE);
		tblclmnResourceAssigned.setWidth(100);
		tblclmnResourceAssigned.setText("Resource Assigned");
	*/}

	//IncreaseProgress();
	
}

	
	/*ArrayList<String> getMilestone()
	{
		try {

			for(int i= 0 ; i <Schedule_object.length; i++)
			{
			Property t = Schedule_object[i].getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);
		
			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);
			
			for (int j = 0; j <SchTaskArr.length; j++) 
			{
			TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
			System.out.println("myTaskActivity"+myTaskActivity);	
			taskType = myTaskActivity.getProperty("fnd0TaskTypeString");
			//Task = myTaskActivity.getProperty("complete_percent"); 
			if(taskType.equalsIgnoreCase("M"))
			{
				
				data1.add(j, myTaskActivity.toString());
				
				TableItem tableItem= new TableItem(ReportTable, SWT.NONE);
				tableItem.setText(5, data1.toString());
				System.out.println("tableItem***"+tableItem);
				//mileStoneObjList1.add(j, myTaskActivity);
				System.out.println("Milestone **"+taskType);
				
			}else if (taskType.equalsIgnoreCase("T"))
			{
				data1.add(j, myTaskActivity.toString());
				//mileStoneObjList.add(myTaskActivity.toString());
				System.out.println("Task **"+taskType);
			}
			}
			}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		//	LinkedList<String> linklist;
			return data1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}*/
	


