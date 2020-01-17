package com.teamcenter.scheduledata.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.nebula.widgets.grid.GridCellRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.soa.client.model.ModelObject;

public class CreateProjLevelUI_BG {

	private static ProgressBar progressBar;
	private static Tree tableTree;
	private static Combo combo_allSchedulelist;
	private static Composite composite_miles;
	private static Composite composite_progress;
	private static Table milestoneTabl;
	static LinkedHashMap<String, ModelObject> ProjectList = new LinkedHashMap<String, ModelObject>();
	static TCDataFunctionality obj;
    static TCSession session;
	private static ModelObject[] AllActiveProjList;
	private static String ProjPerComplete="0%";
	private static Label lblFinishDate;
	private static Label lblDayCompletion;
	private static Label lblStartDate;
	/*public static void main(String[] args) {
		callUI();

	}
*/
	public CreateProjLevelUI_BG(TCSession session)
	{
		this.session=session;
		obj = new TCDataFunctionality(session);
	}
	public static void callUI() {
		try {
			ProjectList = new LinkedHashMap<String, ModelObject>();
			// Display display = new Display();
			    Shell shell = new Shell(SWT.SHELL_TRIM);
			    shell.setText("Project View");
			    shell.setSize(750, 387);
			    shell.setLayout(new GridLayout(1, false));
			    
			    Composite composite = new Composite(shell, SWT.NONE);
			    composite.setLayout(new GridLayout(2, false));
			    composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			    
			    Label lblNewLabel = new Label(composite, SWT.NONE);
			    lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			    lblNewLabel.setText("Active Project");
			    
			    combo_allSchedulelist = new Combo(composite, SWT.NONE);
			    GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			    gd_combo.widthHint = 116;
			    combo_allSchedulelist.setLayoutData(gd_combo);
			    
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
					combo_allSchedulelist.setItems(projArr);
				
				System.out.println("ProjectList"+ProjectList);
			    
			    combo_allSchedulelist.addSelectionListener(new  SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						String selection = combo_allSchedulelist.getText();
						setProgress_tableData(selection,e);
						 displayReport(selection);
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			    
			    Composite composite_1 = new Composite(shell, SWT.NONE);
			    composite_1.setLayout(new GridLayout(1, false));
			    composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			    
			    composite_miles = new Composite(composite_1, SWT.NONE);
			    composite_miles.setLayout(new GridLayout(1, false));
			    GridData gd_composite_miles = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			    gd_composite_miles.heightHint = 30;
			    composite_miles.setLayoutData(gd_composite_miles);
			    
			    milestoneTabl = new Table(composite_miles,SWT.Selection);
			    GridData gd_table_1 = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
			    gd_table_1.heightHint = 30;
			    milestoneTabl.setLayoutData(gd_table_1);
			    milestoneTabl.setHeaderVisible(false);
			    milestoneTabl.setLinesVisible(false);
			    milestoneTabl.setVisible(true);
			    milestoneTabl.setBackground(composite_miles.getBackground());
			    
			 /*   Button btnNewButton = new Button(composite_miles, SWT.NONE);
			    btnNewButton.setText("New Button1");*/
			    
			   /* for(int i=0;i<3;i++)
			    {
			    	TableColumn col=new TableColumn(milestoneTabl, SWT.NONE);
				    col.setWidth(100);
				    col.setText("hello");
			    }*/
			  /*  TableItem item=new TableItem(milestoneTabl, SWT.NONE);
			    //item.setText("hi");
			    for(int ii=0;ii<3;ii++)
			    {
			    	item.setImage(ii,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
			    }*/
			    
			    
			    composite_progress = new Composite(composite_1, SWT.NONE);
			    composite_progress.setLayout(new GridLayout(14, false));
			    composite_progress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			    
				progressBar = new ProgressBar(composite_progress, SWT.NONE);
				progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 14, 1));
				
				lblStartDate = new Label(composite_progress, SWT.NONE);
				GridData gd_lblStartDate = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
				gd_lblStartDate.minimumWidth = 200;
				lblStartDate.setLayoutData(gd_lblStartDate);
				lblStartDate.setText("Start Dateyyyyyyyyyy");
				lblStartDate.setVisible(false);
				
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				new Label(composite_progress, SWT.NONE);
				
				lblDayCompletion = new Label(composite_progress, SWT.NONE);
				lblDayCompletion.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
				lblDayCompletion.setText("days");
				lblDayCompletion.setVisible(false);
				
				lblFinishDate = new Label(composite_progress, SWT.NONE);
				//GridData gd_lblEndDate = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				GridData gd_lblEndDate = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
				gd_lblEndDate.minimumWidth = 200;
				lblFinishDate.setLayoutData(gd_lblEndDate);
				lblFinishDate.setText("End Date:tttttttttttttttt");
				lblFinishDate.setVisible(false);

			    
			    Composite composite_2 = new Composite(shell, SWT.NONE);
			    composite_2.setLayout(new GridLayout(1, false));
			    composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
			    
			    /*table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
			    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			    table.setHeaderVisible(true);
			    table.setLinesVisible(true);*/
				 tableTree = new Tree(composite_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL);
				 tableTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				 tableTree.setHeaderVisible(true);
				 tableTree.setLinesVisible(true);
			     create_tableCol();
			    
			    shell.open();
			    

			    /*while (!shell.isDisposed())
			    {
			        if (!display.readAndDispatch())
			        {
			            display.sleep();
			        }
			    }
			    display.dispose();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private static void create_tableCol() {

		
   TreeColumn column1 = new TreeColumn(tableTree, SWT.NONE);
   column1.setText("Name");
   column1.setWidth(150);
   
   TreeColumn column2 = new TreeColumn(tableTree, SWT.NONE);
   column2.setText("Start Date");
   column2.setWidth(150);
   TreeColumn column3 = new TreeColumn(tableTree, SWT.NONE);
   column3.setText("End date");
   column3.setWidth(150);
   TreeColumn perComplete = new TreeColumn(tableTree, SWT.NONE);
   perComplete.setText("Per Complete");
   perComplete.setWidth(50);
   TreeColumn status = new TreeColumn(tableTree, SWT.NONE);
   status.setText("Status");
   status.setWidth(50);
   TreeColumn assignee = new TreeColumn(tableTree, SWT.NONE);
   assignee.setText("Assignee");
   assignee.setWidth(100);
   TreeColumn desc = new TreeColumn(tableTree, SWT.NONE);
   desc.setText("Major milestone");
   desc.setWidth(150);
   

	}
	protected static void setProgress_tableData(String selection, final SelectionEvent e) {
		try {
		 	ArrayList<String> milestoneComplete = new ArrayList<String>();
			ArrayList<String> milestoneDelayed = new ArrayList<String>();
			ArrayList<String> milestoneInProgress = new ArrayList<String>();
			ArrayList<String> milestoneOther = new ArrayList<String>();
			
			composite_miles.setLayout(new GridLayout(1, false));
		    composite_miles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		    milestoneTabl.setVisible(true);
		    
		    /*Button btnNewButton = new Button(composite_miles, SWT.NONE);
		    btnNewButton.setText("New Button");*/
		    
		    TableColumn[] tabCols = milestoneTabl.getColumns();
		    if(tabCols.length>0)
	    	{
	    	  for(int i=0;i<tabCols.length;i++)
	    	  {
	    		  tabCols[i].dispose();
	    	  }
	    	}
		    
			 LinkedHashMap<String, String> milestonelist = obj.getMileshtoneList(selection);
		    for(int i=0;i<milestonelist.size();i++)
		    {
		    	TableColumn col=new TableColumn(milestoneTabl, SWT.NONE);
		    	col.setWidth(40);
		    }
		    TableItem[] tabItem = milestoneTabl.getItems();
		    if(tabItem.length>0)
		    	{
		    	  for(int i=0;i<tabItem.length;i++)
		    	  {
		    		  tabItem[i].dispose();
		    	  }
		    	}
		    for(String mileName:milestonelist.keySet())
			{
			String milestoneStatus = milestonelist.get(mileName);
				
				if(milestoneStatus.equalsIgnoreCase("Complete") || milestoneStatus.equalsIgnoreCase("complete") )
					milestoneComplete.add(mileName);
				else if(milestoneStatus.equalsIgnoreCase("Late") || milestoneStatus.equalsIgnoreCase("late"))
					milestoneDelayed.add(mileName);
				else if(milestoneStatus.equalsIgnoreCase("in_progress") || milestoneStatus.equalsIgnoreCase("In Progress"))
					milestoneInProgress.add(mileName);
				else
					milestoneOther.add(mileName);
				
			}
		    
		    TableItem item=new TableItem(milestoneTabl, SWT.NONE);
		    int cnt=0;
		    
		    for(String key:milestoneComplete)
		    {
		    	item.setImage(cnt,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/greenDiamond.gif"));
		    	cnt++;
		    }
		    for(String key:milestoneDelayed)
		    {
		    	item.setImage(cnt,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/redDiamond.gif"));
		    	cnt++;
		    }
		    for(String key:milestoneInProgress)
		    {
		    	item.setImage(cnt,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
		    	cnt++;
		    }
		    for(String key:milestoneOther)
		    {
		    	item.setImage(cnt,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/whiteDiamond.gif"));
		    	cnt++;
		    }
		    /*for(String keys: milestonelist.keySet())
		    {
		    	
		    	item.setImage(cnt,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/redDiamond.gif"));
		    	cnt++;
		    }*/
		    ModelObject getProjModelObj = ProjectList.get(selection);
		    TCComponentSchedule sche=(TCComponentSchedule)getProjModelObj;
		       try {
					ProjPerComplete = sche.getProperty("fnd0SSTCompletePercent");
					String ProjStartdate=sche.getProperty("start_date");
					String projEnddate=sche.getProperty("finish_date");
					String Proj_duration=sche.getProperty("fnd0DurationString");
					lblStartDate.setText(ProjStartdate);
					lblFinishDate.setText(projEnddate);
					lblDayCompletion.setText(Proj_duration);
					lblStartDate.setVisible(true);
					lblFinishDate.setVisible(true);
					lblDayCompletion.setVisible(true);
					
					String perComplete_temp="0";
					if(ProjPerComplete!=null && ProjPerComplete.length()>0)
					{
						System.out.println("status====="+ProjPerComplete);
						perComplete_temp = ProjPerComplete.replaceAll("[^\\d.]", "");
					}else
						perComplete_temp="0";
				
					  int perComlStatusProgress1 = Integer.parseInt(perComplete_temp);
					System.out.println("perComlStatusProgress1=="+perComlStatusProgress1);
					progressBar.setSelection(perComlStatusProgress1);
					 progressBar.setToolTipText(ProjPerComplete);
					 
					 
					 final GridCellRenderer Col12Renderer = new GridCellRenderer() { //For Status column
							
							@Override
							public void paint(GC gc, Object arg1) 
							{
								Point point = progressBar.getSize();
								  
								  FontMetrics fontMetrics = gc.getFontMetrics();
								  int width = fontMetrics.getAverageCharWidth() * ProjPerComplete.length();
								  int height = fontMetrics.getHeight();
								  gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								  gc.drawString("", (point.x-width)/2 , (point.y-height)/2, true);
								  gc.drawString(ProjPerComplete, (point.x-width)/2 , (point.y-height)/2, true);
								
							}

							@Override
							public boolean notify(int arg0, Point arg1,
									Object arg2) {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public Point computeSize(GC arg0, int arg1,
									int arg2, Object arg3) {
								int x = 0;
								int y = 0;
								// TODO Auto-generated method stub
								return new Point(x, y);
							}
					 };
					 
				
				} catch (Exception ex) {
				
					ex.printStackTrace();
				}
		    	
		       
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void displayReport_Original(String selection) {
		try {
			System.out.println("inside displayReport method");
			//clean table
			 TreeItem[] tabItem = tableTree.getItems();
			    if(tabItem.length>0)
			    	{
			    	  for(int i=0;i<tabItem.length;i++)
			    	  {
			    		  tabItem[i].dispose();
			    	  }
			    	}
			 LinkedHashMap<String, LinkedHashMap<String, String>> tableAlldataList = obj.getTableData(selection);
			 
			 Set<String> keys = tableAlldataList.keySet();
			 for(String key:keys)
			 {
				  LinkedHashMap<String, String> taskData = tableAlldataList.get(key);
				 // TreeItem mileStoneItem = null; //= new TreeItem(tableTree, SWT.NONE);// item =new TableItem(table_ProjectReportTable, SWT.NONE);
				  //TreeItem taskItem;
				  TreeItem item = new TreeItem(tableTree, SWT.LEFT);
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
						String resourceAssignee = "";
						if(taskData.get("TASK_ASSIGNEE")!=null && taskData.get("TASK_ASSIGNEE").length()>0)
						{
							resourceAssignee=taskData.get("TASK_ASSIGNEE");
							if(resourceAssignee.contains("("))
							   resourceAssignee=resourceAssignee.split("\\(")[0];
									
						}
						item.setText(5,resourceAssignee);
						
				    }
					  
				   
					
			 }
			 
			 
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	public static void displayReport(String selection) {
		try {
			System.out.println("inside displayReport method");
			//clean table
			 TreeItem[] tabItem = tableTree.getItems();
			    if(tabItem.length>0)
			    	{
			    	  for(int i=0;i<tabItem.length;i++)
			    	  {
			    		  tabItem[i].dispose();
			    	  }
			    	}
			    
			    LinkedHashMap<String, String> getMilestones = obj.getMileshtoneList(selection);
			   for(String mile:getMilestones.keySet()) 
			   {
				   TreeItem item = new TreeItem(tableTree, SWT.LEFT);
				   item.setText(mile);
			   }
			 LinkedHashMap<String, LinkedHashMap<String, String>> tableAlldataList = obj.getTableData(selection);
			 
			 TreeItem[] mileitems = tableTree.getItems();
			  TreeItem cildItem = null;
			  TreeItem parentItem = null;
			  if(mileitems.length>0 )
			  {
				  for(TreeItem iiiii:mileitems)
				  {
					  boolean flag=false;
					  boolean break_falg=false;
					  for(String key:tableAlldataList.keySet())
					  {
						  LinkedHashMap<String, String> taskData = tableAlldataList.get(key);
						   break_falg=false;
						  if(key.equals(iiiii.getText()) )
						  {
							  flag=true;
							  parentItem=iiiii;
							  setMilestoneTableData(parentItem, taskData);
							  
						  }else
						  {
							  String task_type=taskData.get("TASK_TYPE");
							  if(task_type.equals("M") )
							  {
								  flag=false;
								  break_falg=true;
								
							  }else
							  {
								  if(flag && !break_falg)
								  {
									  setTableTaskData(parentItem,cildItem,taskData);
								
								  }
											  
							   }
							  
						  }
					  }
				  }
			  }
			 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
   static void setTableTaskData(TreeItem ParentItem, TreeItem cildItem, LinkedHashMap<String, String> taskData)
   {
		  cildItem = new TreeItem(ParentItem, SWT.LEFT);
		  cildItem.setText(0,taskData.get("TASK_NAME"));
	    	cildItem.setText(1,taskData.get("TASK_START_DATE"));
			cildItem.setText(2,taskData.get("TASK_END_DATE"));
			cildItem.setText(3,taskData.get("TASK_PER_COMPLETE"));
			//item.setText(4,taskData.get("TASK_STATUS"));
			if(taskData.get("TASK_TYPE").equals("M"))
			{
				String status=taskData.get("TASK_STATUS");
				if(status.equalsIgnoreCase("in_progress") || status.equalsIgnoreCase("In Progress"))
				   cildItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
				else if(status.equalsIgnoreCase("Complete") || status.equalsIgnoreCase("complete") )
					cildItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/greenDiamond.gif"));
				else if(status.equalsIgnoreCase("Late") || status.equalsIgnoreCase("late"))
					cildItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/redDiamond.gif"));
				else
					cildItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/whiteDiamond.gif"));
			}else
			{
				cildItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/task.png"));
			}
			String resourceAssignee = "";
			if(taskData.get("TASK_ASSIGNEE")!=null && taskData.get("TASK_ASSIGNEE").length()>0)
			{
				resourceAssignee=taskData.get("TASK_ASSIGNEE");
				if(resourceAssignee.contains("("))
				   resourceAssignee=resourceAssignee.split("\\(")[0];
						
			}
			cildItem.setText(5,resourceAssignee);
		
   }
   static void setMilestoneTableData(TreeItem ParentItem,LinkedHashMap<String, String> taskData)
   {
		  
		  ParentItem.setText(0,taskData.get("TASK_NAME"));
	    	ParentItem.setText(1,taskData.get("TASK_START_DATE"));
			ParentItem.setText(2,taskData.get("TASK_END_DATE"));
			ParentItem.setText(3,taskData.get("TASK_PER_COMPLETE"));
			//item.setText(4,taskData.get("TASK_STATUS"));
			if(taskData.get("TASK_TYPE").equals("M"))
			{
				String status=taskData.get("TASK_STATUS");
				if(status.equalsIgnoreCase("in_progress") || status.equalsIgnoreCase("In Progress"))
					ParentItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
				else if(status.equalsIgnoreCase("Complete") || status.equalsIgnoreCase("complete") )
					ParentItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/greenDiamond.gif"));
				else if(status.equalsIgnoreCase("Late") || status.equalsIgnoreCase("late"))
					ParentItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/redDiamond.gif"));
				else
					ParentItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/whiteDiamond.gif"));
			}else
			{
				ParentItem.setImage(4,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/task.png"));
			}
			String resourceAssignee = "";
			if(taskData.get("TASK_ASSIGNEE")!=null && taskData.get("TASK_ASSIGNEE").length()>0)
			{
				resourceAssignee=taskData.get("TASK_ASSIGNEE");
				if(resourceAssignee.contains("("))
				   resourceAssignee=resourceAssignee.split("\\(")[0];
						
			}
			ParentItem.setText(5,resourceAssignee);
			if(taskData.get("TASK_TYPE").equals("M"))
			{
				ParentItem.setText(6,taskData.get("TASK_DESC"));
			}
   }
}
