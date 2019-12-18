package com.teamcenter.schedule.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class AllActiveSchedules {

	TCSession session = null;
	ModelObject[] Schedule_object = null;
	int Schedule_count = 0;
	String nameComboVal[] = null;
	ArrayList<String> resList = null;
	private Table ReportTable;
	Composite composite1_table = null;

	public AllActiveSchedules(TCSession session) {
		// TODO Auto-generated constructor stub
		this.session = session;
		getScheduleData();
	}

	public void allActiveSchUI()
	{
		// TODO Auto-generated method stub
	    //Display display = new Display ();
		Shell schshell = new Shell();
		schshell.setSize(645, 381);
		schshell.setText("All Active Scedules");
		//schshell.setImage(new Image(display,"C:\\SET_UP\\Eclipse_Workspace\\com.teamcenter.schedule\\icons\\tcImg.ico"));
		
	//	schshell.pack();
		schshell.setLayout(new GridLayout(1, false));
		
				
		composite1_table = new Composite(schshell, SWT.NONE);
		composite1_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite1_table.setLayout(new GridLayout(1, false));
		GridData gd_composite1_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite1_table.heightHint = 136;
		gd_composite1_table.widthHint = 387;
		composite1_table.setLayoutData(gd_composite1_table);
		composite1_table.setBounds(0, 0, 64, 64);
		
		//table create
		createTable(composite1_table);
	//	LinkedHashMap<String, ArrayList<String>> dataList = getTableData();
	//	new Nat_explore().nat_tableDisplay(composite1_table,dataList);
	   
		//Display table data
		 displayReport();
		
		Composite ExReport_composite_1 = new Composite(schshell, SWT.NONE);
		ExReport_composite_1.setLayout(new GridLayout(1, false));
		GridData gd_ExReport_composite_1 = new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1);
		gd_ExReport_composite_1.heightHint = 35;
		ExReport_composite_1.setLayoutData(gd_ExReport_composite_1);
		
		Button btnNewButton = new Button(ExReport_composite_1, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					new AllActiveScheExportToExcel().generateReport(ReportTable);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(0, 0, 75, 25);
		btnNewButton.setText("Export to excel");
		
		new Label(ExReport_composite_1, SWT.NONE);
		new Label(ExReport_composite_1, SWT.NONE);
		
		schshell.open ();
	//	while (!schshell.isDisposed ()) {
			//if (!display.readAndDispatch ()) display.sleep ();
			//schshell.close();	
	//		}
	//	display.dispose ();
		
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

		TableColumn tblclmnNewColumn = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Schedule Name");

		TableColumn tblclmnNewColumn_2 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Start Date");

		TableColumn tblclmnNewColumn_3 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("End Date");

		TableColumn tblclmnNewColumn_4 = new TableColumn(ReportTable, SWT.NONE);
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText("Percentage");

	}
// Using SavedQueryService we are searching created schedule from TC
	void getScheduleData() {

		System.out.println("\n inside getScheduleData");
		SavedQueryService service = SavedQueryService.getService(session
				.getSoaConnection());
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
		String[] val = { "*" };
		ModelObject save = savequery[0];
		ExecuteSavedQueryResponse result;

		try {
			result = service.executeSavedQuery(save, id, val, 1000);
			Schedule_count = result.nFound;
			Schedule_object = result.objects;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void displayReport() {
		try {
			System.out.println("inside displayReport method");

			LinkedHashMap<String, ArrayList<String>> dataList = getTableData();
			Set<String> set = dataList.keySet();
			for (String key : set) {
				TableItem item = new TableItem(ReportTable, SWT.NONE);
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

	LinkedHashMap<String, ArrayList<String>> getTableData() {
		LinkedHashMap<String, ArrayList<String>> scheduleData = new LinkedHashMap<String, ArrayList<String>>();
		try {

			ArrayList<String> data = new ArrayList<String>();
			for (int k = 0; k < Schedule_object.length; k++) {
				try {
					data = new ArrayList<String>();

					String status = Schedule_object[k]
							.getPropertyDisplayableValue("fnd0SSTStatus");

					if (status.equals("In Progress")) {
						String sche = Schedule_object[k].toString();
						String startDate = Schedule_object[k]
								.getPropertyDisplayableValue("start_date")
								.toString();
						String endDate = Schedule_object[k]
								.getPropertyDisplayableValue("finish_date")
								.toString();
						String percentage = Schedule_object[k]
								.getPropertyDisplayableValue(
										"fnd0SSTCompletePercent").toString();

						if (sche != null)
							data.add(sche);
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

						if (percentage != null)
							data.add(percentage);
						else
							data.add("");

						scheduleData.put(k + "", data);
					}
				} catch (NotLoadedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleData;

	}
}
