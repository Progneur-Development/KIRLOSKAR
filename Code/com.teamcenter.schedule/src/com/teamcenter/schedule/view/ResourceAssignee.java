package com.teamcenter.schedule.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.ServiceData;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.schedule.ScheduleManagerService;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.AssignmentCreateContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.AttributeUpdateContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.CreatedObjectsContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.ObjectUpdateContainer;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;
import com.teamcenter.soa.exceptions.NotLoadedException;


public class ResourceAssignee {

	TCSession session = null;
	ModelObject[] Schedule_object = null;
	int Schedule_count = 0;
	private Combo name_combo;
	LinkedHashMap<TCComponent, String> resVector = null;
	private Table ReportTable;
	Composite composite1_table = null;
	static boolean firstEntryfalg = false;
	private Table dummyReportTable;
	private ArrayList<String> resList;
	TCdataOperations tcDataOperationObj=null;
	
	public ResourceAssignee(TCSession session) {
		// TODO Auto-generated constructor stub
		this.session = session;
		 tcDataOperationObj=new TCdataOperations(session); 
		 tcDataOperationObj.getScheduleData();
		 this.Schedule_count=tcDataOperationObj.getSchedule_count();
		 this.Schedule_object=tcDataOperationObj.getSchedule_object();		
	}

	public void resource_AssUI() {
		
		// TODO Auto-generated method stub
		Display display=Display.getDefault();
	  	Shell schshell = new Shell(display);
		schshell.setSize(655, 650);
		schshell.setText("Schedule Manager Report");

		// schshell.pack();
		schshell.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(schshell, SWT.NONE);
		GridLayout gl_composite = new GridLayout(6, false);
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.FILL, SWT.TOP, true, false, 1,
				1);
		gd_composite.heightHint = 71;
		gd_composite.widthHint = 387;
		composite.setLayoutData(gd_composite);
		composite.setBounds(0, 0, 64, 64);

		Text name_txt = new Text(composite, SWT.NONE);
		name_txt.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridData gd_name_txt = new GridData(SWT.LEFT, SWT.CENTER, false, true,
				1, 1);
		gd_name_txt.heightHint = 20;
		gd_name_txt.widthHint = 63;
		name_txt.setLayoutData(gd_name_txt);
		name_txt.setText("Name : ");

		
		name_combo = new Combo(composite, SWT.NONE);
		GridData gd_name_combo = new GridData(SWT.LEFT, SWT.FILL, false, false,
				2, 1);
		gd_name_combo.heightHint = 20;
		gd_name_combo.widthHint = 146;
		name_combo.setLayoutData(gd_name_combo);

		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Text txtFrom = new Text(composite, SWT.NONE);
		txtFrom.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtFrom.setText("From :");
		GridData gd_txtFrom = new GridData(SWT.LEFT, SWT.CENTER, false, true,
				1, 1);
		gd_txtFrom.widthHint = 63;
		gd_txtFrom.heightHint = 20;
		txtFrom.setLayoutData(gd_txtFrom);

		final DateTime from_date = new DateTime(composite, SWT.BORDER);
		GridData gd_frdate = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_frdate.widthHint = 92;
		gd_frdate.heightHint = 20;
		from_date.setLayoutData(gd_frdate);

		Text txtTo = new Text(composite, SWT.NONE);
		txtTo.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		txtTo.setText("    To :");
		GridData gd_txtTo = new GridData(SWT.CENTER, SWT.CENTER, false, true,
				1, 1);
		gd_txtTo.widthHint = 51;
		gd_txtTo.heightHint = 20;
		txtTo.setLayoutData(gd_txtTo);

		final DateTime to_date = new DateTime(composite, SWT.BORDER);
		GridData gd_to_date = new GridData(SWT.FILL, SWT.FILL, false, true, 1,
				1);
		gd_to_date.widthHint = 92;
		gd_to_date.heightHint = 20;
		to_date.setLayoutData(gd_to_date);
		new Label(composite, SWT.NONE);

		Button buttonOK = new Button(composite, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_button.widthHint = 69;
		gd_button.heightHint = 20;
		buttonOK.setLayoutData(gd_button);
		buttonOK.setText(" OK ");
		buttonOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {

				String userName;
				try {
					if (name_combo.getItem(name_combo.getSelectionIndex()) != null)
						userName = name_combo.getItem(name_combo
								.getSelectionIndex());
					else
						userName = "*";
				} catch (Exception exe) {
					userName = "*";
					exe.printStackTrace();
				}

				// From date
				int day = from_date.getDay();
				int month = from_date.getMonth() + 1;
				int year = from_date.getYear();

				String startDate = (day < 10) ? "0" + day + "/" : day + "/";
				startDate += (month < 10) ? "0" + month + "/" : month + "/";
				startDate += year;

				// To date
				int day1 = to_date.getDay();
				int month1 = to_date.getMonth() + 1;
				int year1 = to_date.getYear();

				String toDate = (day1 < 10) ? "0" + day1 + "/" : day1 + "/";
				toDate += (month1 < 10) ? "0" + month1 + "/" : month1 + "/";
				toDate += year1;

				// Display table data
				displayReport(userName, startDate, toDate);

			}
		});

		composite1_table = new Composite(schshell, SWT.NONE);
		composite1_table.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite1_table.setLayout(new GridLayout(1, false));
		GridData gd_composite1_table = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		gd_composite1_table.heightHint = 136;
		gd_composite1_table.widthHint = 387;
		composite1_table.setLayoutData(gd_composite1_table);
		composite1_table.setBounds(0, 0, 64, 64);

		// table create
		createTable(composite1_table);
		
		Composite ExReport_composite_1 = new Composite(schshell, SWT.NONE);
		ExReport_composite_1.setLayout(new GridLayout(5, false));
		GridData gd_ExReport_composite_1 = new GridData(SWT.RIGHT, SWT.BOTTOM,
				true, false, 1, 1);
		gd_ExReport_composite_1.heightHint = 35;
		ExReport_composite_1.setLayoutData(gd_ExReport_composite_1);
		
		/*Button btnImportButton = new Button(ExReport_composite_1, SWT.NONE);
		btnImportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {

					new ResAssigneeExportToExcel().readXLSXFile();
					new ResAssigneeExportToExcel().generateReport(ReportTable);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImportButton.setBounds(0, 0, 75, 25);
		btnImportButton.setText("Import");
*/

		Button resUpdateButton = new Button(ExReport_composite_1, SWT.NONE);
		resUpdateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					tcDataOperationObj.resourceAssUpdation();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		resUpdateButton.setBounds(0, 0, 75, 25);
		resUpdateButton.setText("Resource Assignee");
		resUpdateButton.setVisible(true);
		Button updateButton = new Button(ExReport_composite_1, SWT.NONE);
		updateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					//tcDataOperationObj.scheduleCreaion();	
					GregorianCalendar startDate=new GregorianCalendar( 2019, 05, 02 );
			  	   	GregorianCalendar finishDate=new GregorianCalendar( 2019, 06, 03 );
			  	  tcDataOperationObj.CreateScheduleFromExcelToTC("Kirloskar","1210",false,"Kirloskar",finishDate,"Kirloskar Pumps",startDate, "In progress");
			  	//  tcDataOperationObj.CreateScheduleFromExcelToTC("Kirloskar","1210",false,"Kirloskar",finishDate,"KirloskarMotors",startDate, "In progress");
			  	  /*tcDataOperationObj.CreateScheduleFromExcelToTC("MahindraAndMahindra","1211",false,"",finishDate,"MnMProject3",startDate, "In progress");
			  	  tcDataOperationObj.CreateScheduleFromExcelToTC("MahindraAndMahindra","1212",false,"FD",finishDate,"MnMProject4",startDate, "In progress");
*/
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updateButton.setBounds(0, 0, 75, 25);
		updateButton.setText("Create Schedule");
		updateButton.setVisible(true);
		
		Button updateButton1 = new Button(ExReport_composite_1, SWT.NONE);
		updateButton1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					tcDataOperationObj.scheduleUpdate();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updateButton1.setBounds(0, 0, 75, 25);
		updateButton1.setText("Update Task");
		updateButton1.setVisible(true);
		
		Button btnNewButton = new Button(ExReport_composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					//new ResAssigneeExportToExcel().readXLSXFile();
					new ResAssigneeExportToExcel().generateReport(ReportTable);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(0, 0, 75, 25);
		btnNewButton.setText("Export to excel");
		
		Button importExcelBtn = new Button(ExReport_composite_1, SWT.NONE);
		importExcelBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new updateUI(session).updatepropToTC();
				 //FileDialogExample();
				/*Display display=Display.getDefault();
				Shell schshell = new Shell(display);
				schshell.setSize(655,650 );
				schshell.setText("UpdateProperties");
				// schshell.pack();
				schshell.setLayout(new GridLayout(1, false));
				FileDialog fd = new FileDialog(schshell, SWT.SAVE);
				fd.setText("Save");
				fd.setFilterPath("C:\\TEMP\\ScheduleReport");
				String[] filterExt = { "*.*","*.txt", "*.doc", ".rtf"};
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				System.out.println(selected);
				*/
      // new ResAssigneeExportToExcel().readXLSFile("C:\\TEMP\\ScheduleReport\\");
				//schshell.open();
			}
		});
		importExcelBtn.setBounds(0, 0, 75, 25);
		importExcelBtn.setText("Import from excel");
			
		new Label(ExReport_composite_1, SWT.NONE);
		new Label(ExReport_composite_1, SWT.NONE);
		
		// Load combo and frid Data
		LoadWidgetData();
		//gridEvents();

		schshell.open();
		
		/*while (!schshell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
		 schshell.close();
		 }
		 display.dispose ();*/

	}
	
	
	private void gridEvents() {
		try {
			ReportTable.addListener(SWT.MouseDown, new Listener() {
		   
				@Override
				public void handleEvent(Event event) {
			        Point pt = new Point(event.x, event.y);
			        TableItem item = ReportTable.getItem(pt);
			        System.out.println("item==="+item.getText());
			        if (item == null)
			          return;
			        for (int i = 0; i < ReportTable.getColumnCount(); i++) {
			          Rectangle rect = item.getBounds(i);
			          if (rect.contains(pt)) {
			             String resource = item.getText(i);
			             ResourcesGrid resObj = new ResourcesGrid();
			             resObj.assignResource(resList);
			          }
			        }
			      }
			    });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// Load Data
	void LoadWidgetData() 
	{
		
		try {
			
			// first entry of table dispaly
			firstEntryfalg = true;
			// Display table data
			displayReport("*", "", "");
			
			// Assign combo values
			resList = getResourceComboData();
			// Add data to combo box
			if (resList != null && resList.size() > 0) {
				String[] nameComboVal = new String[resList.size()];
				for (int i = 0; i < resList.size(); i++)
					nameComboVal[i] = resList.get(i);

				name_combo.setItems(nameComboVal);

			}


						

		} catch (Exception e) {
			System.out.println("\n resource data error");
		}
	}
	
	private void createTable(Composite composite1_table) {
		// TODO Auto-generated method stub
		ReportTable = new Table(composite1_table, SWT.BORDER
				| SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 180;
		ReportTable.setLayoutData(gd_table);
		ReportTable.setHeaderVisible(true);
		ReportTable.setLinesVisible(true);

		TableColumn tblclmnNewColumn_0 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_0.setWidth(100);
		tblclmnNewColumn_0.setText("Task Name");

		TableColumn tblclmnNewColumn = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Schedule Name");

		TableColumn tblclmnNewColumn_1 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Status");

		TableColumn tblclmnNewColumn_2 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Start Date");

		TableColumn tblclmnNewColumn_3 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("End Date");

		TableColumn tblclmnNewColumn_5 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("User");
		

	}

	// Assign data to combo box
	ArrayList<String> getResourceComboData() 
	{
		ArrayList<String> resList = new ArrayList<String>();
		
		try {
			Vector<String> resVector11 = new Vector<String>();

			//for (int k = 0; k < resVector.size(); k++) {
			for(TCComponent k:resVector.keySet()){
				try {

					if(resVector.get(k)!=null && resVector.get(k).trim().length()>0)
					{
						String ele = resVector.get(k);
						System.out.println("**ele==" + ele);
						if (ele.contains(",")) {
							String str[] = ele.split(",");
							for (int j = 0; j < str.length; j++)
									resVector11.add(str[j].trim());

						} else {
							System.out.println("ele==" + ele);
							resVector11.add(ele.trim());

						}
					}	

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// Remove duplicates
			for (int i = 0; i < resVector11.size(); i++) {
				if (i == 0)
					resList.add(resVector11.get(i));

				if (!resList.contains(resVector11.get(i)))
					resList.add(resVector11.get(i));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;

	}

	void getScheduleData() {

		System.out.println("\n inside getScheduleData");
	   
	}

	private void displayReport(String searcStr, String froDate, String toDate) {
		try {
			System.out.println("inside displayReport method");
			TableItem[] items = ReportTable.getItems();

			LinkedHashMap<String, ArrayList<String>> dataList = getTableData();
			Set<String> set = dataList.keySet();
			if (firstEntryfalg) {

				for (String key : set) {
					TableItem item = new TableItem(ReportTable, SWT.NONE);
					ArrayList<String> dd = dataList.get(key);
					for (int i = 0; i < dd.size(); i++) {
						item.setText(i, dd.get(i));
						item.setData(key);
					}
				}
				firstEntryfalg = false;

			} else if (searcStr.equals("*") || searcStr.equals("")) {
				// Clear table data
				clearTableData(items);
				// Match search assignee for filter and set data to table
				int rowcnt = 0;
				for (String key : set) {
					ArrayList<String> dd = dataList.get(key);
					TableItem item = items[rowcnt];
					for (int i = 0; i < dd.size(); i++) {
						item.setText(i, dd.get(i));
						item.setData(dd.get(i));
					}
					rowcnt++;
				}

			} else {
				// Clear table data
				clearTableData(items);

				// Match search assignee for filter and set data to table
				int rowcnt = 0;
				for (String key : set) {
					if (key.contains(searcStr)) {
						ArrayList<String> dd = dataList.get(key);
						TableItem item = items[rowcnt];
						for (int i = 0; i < dd.size(); i++) {
							item.setText(i, dd.get(i));
							item.setData(dd.get(i));
						}
						rowcnt++;
					}

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void clearTableData(TableItem[] items) {
		try {
			for (int b = 0; b < items.length; b++) {
				TableItem tabitem = items[b];
				for (int a = 0; a < ReportTable.getColumnCount(); a++) {
					tabitem.setText(a, "");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	LinkedHashMap<String, ArrayList<String>> getTableData() {
		LinkedHashMap<String, ArrayList<String>> scheduleData = new LinkedHashMap<String, ArrayList<String>>();
		try {

			ArrayList<String> data = new ArrayList<String>();
			int cnt = 1;
			resVector = new LinkedHashMap<TCComponent, String>();

			AIFComponentContext[] SchTaskArr;
			for (int k = 0; k < Schedule_count; k++) {
				//if (k <= 6) 
				{
					TCComponentSchedule mySch = (TCComponentSchedule) Schedule_object[k];
					System.out.println("mySch====" + mySch.toDisplayString());

					Property t = mySch.getPropertyObject("fnd0SummaryTask");
					System.out.println("t is " + t);

					TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
					SchTaskArr = mySchTask.getChildren();
					System.out.println("ttt size is " + SchTaskArr.length);
					for (int j = 0; j < SchTaskArr.length; j++) 
					{
						try 
						{
							data = new ArrayList<String>();
							InterfaceAIFComponent InterfaceAIFComponent1 = SchTaskArr[j].getComponent();

							String ScheTaskname = InterfaceAIFComponent1.getProperty("object_name");
							String sche = InterfaceAIFComponent1.getProperty("schedule_tag");
							String status = InterfaceAIFComponent1.getProperty("fnd0status").toString();
							String startDate = InterfaceAIFComponent1.getProperty("start_date").toString();
							String endDate = InterfaceAIFComponent1.getProperty("finish_date").toString();
							String members = InterfaceAIFComponent1.getProperty("ResourceAssignment").toString();
							

							if(members.contains("("))
							{
								members=members.split("\\(")[0];
							}
							
							
							if (ScheTaskname != null)
								data.add(ScheTaskname);
							else
								data.add("");

							if (sche != null)
								data.add(sche);
							else
								data.add("");

							if (status != null)
								data.add(status);
							else
								data.add("");

							if (startDate != null)
								data.add(startDate);
							else
								data.add("");

							if (endDate != null)
								data.add(endDate);
							else
								data.add("");

							if (members != null)
								data.add(members);
							else
								data.add("");
							
							//data for grid
							scheduleData.put(cnt + "-," + members, data);
							cnt++;
							
							//Data for combo list
							TCComponent ii=(TCComponent) SchTaskArr[j].getComponent();
							 TCProperty prp = ii.getTCProperty("ResourceAssignment");
							// TCComponentUser tcComponentUser =(TCComponentUser) prp.getModelObjectValue();
							resVector.put(ii,members);
						
						} catch (NotLoadedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleData;
	}
	
}
