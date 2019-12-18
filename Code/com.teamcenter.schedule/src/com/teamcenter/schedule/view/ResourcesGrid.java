package com.teamcenter.schedule.view;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
public class ResourcesGrid {

	Shell resshell;

	public void assignResource(ArrayList<String> resList) {

		String getSelectedStr = null;
		if(resshell != null)
		{
			resshell.dispose();
		}
			// TODO Auto-generated method stub
		Display display = Display.getDefault();
		
		 resshell = new Shell();
		resshell.setSize(377, 312);
		resshell=new Shell(display,SWT.CLOSE|SWT.ON_TOP|SWT.PRIMARY_MODAL);
		resshell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	
		resshell.setText("Resource Assignee");
		
	//	schshell.pack();
		resshell.setLayout(new GridLayout(1, false));
		
		
		Composite composite1_table = new Composite(resshell, SWT.NONE);
		composite1_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		//composite1_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite1_table.setLayout(new GridLayout(3, false));
		GridData gd_composite1_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite1_table.heightHint = 136;
		gd_composite1_table.widthHint = 387;
		composite1_table.setLayoutData(gd_composite1_table);
		composite1_table.setBounds(0, 0, 64, 64);
		

		// TODO Auto-generated method stub
		 Table tcResGrid1 = new Table(composite1_table, SWT.BORDER | SWT.FULL_SELECTION);
			GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_table.heightHint = 180;
			tcResGrid1.setLayoutData(gd_table);
			tcResGrid1.setHeaderVisible(true);
			tcResGrid1.setLinesVisible(true);
			
			TableColumn tcResGridcol = new TableColumn(tcResGrid1, SWT.NONE);
			tcResGridcol.setResizable(false);
			tcResGridcol.setWidth(150);
			tcResGridcol.setText("User");
			
			//Set data to grid
			
			for (int i = 0; i < resList.size(); i++) {
				TableItem item = new TableItem(tcResGrid1, SWT.NONE);
				item.setText(i, resList.get(i));
				item.setData(resList.get(i));
			}
				
			
			Composite composite1_arrow = new Composite(composite1_table, SWT.NONE);
			composite1_arrow.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
			//composite1_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			composite1_arrow.setLayout(new GridLayout(1, false));
			GridData gd_composite1_arrow = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_composite1_arrow.heightHint = 136;
			gd_composite1_arrow.widthHint = 72;
			composite1_arrow.setLayoutData(gd_composite1_arrow);
			composite1_arrow.setBounds(0, 0, 64, 64);
			new Label(composite1_arrow, SWT.NONE);
			new Label(composite1_arrow, SWT.NONE);
			
			Button goButton = new Button(composite1_arrow, SWT.NONE);
			GridData gd_goButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_goButton.widthHint = 36;
			goButton.setLayoutData(gd_goButton);
			goButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
				}
			});
			goButton.setText(">");
			new Label(composite1_arrow, SWT.NONE);
			
			Button backButton_1 = new Button(composite1_arrow, SWT.NONE);
			GridData gd_backButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_backButton_1.widthHint = 37;
			backButton_1.setLayoutData(gd_backButton_1);
			backButton_1.setText("<");
			
			// TODO Auto-generated method stub
			 Table resGrid2 = new Table(composite1_table, SWT.BORDER | SWT.FULL_SELECTION);
				gd_table.heightHint = 180;
				resGrid2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				resGrid2.setHeaderVisible(true);
				resGrid2.setLinesVisible(true);
				
				TableColumn rescol1 = new TableColumn(resGrid2, SWT.NONE);
				rescol1.setResizable(false);
				rescol1.setWidth(150);
				rescol1.setText("User");
		
		
		Composite ExReport_composite_1 = new Composite(resshell, SWT.NONE);
		ExReport_composite_1.setLayout(new GridLayout(1, false));
		GridData gd_ExReport_composite_1 = new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1);
		gd_ExReport_composite_1.heightHint = 35;
		ExReport_composite_1.setLayoutData(gd_ExReport_composite_1);
		
		Button OKButton = new Button(ExReport_composite_1, SWT.NONE);
		OKButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		OKButton.setBounds(0, 0, 75, 25);
		OKButton.setText("OK");
		
		new Label(ExReport_composite_1, SWT.NONE);
		new Label(ExReport_composite_1, SWT.NONE);
		resshell.pack();
		resshell.open ();
		//while (!schshell.isDisposed ()) {
			//if (!display.readAndDispatch ()) display.sleep ();
	//	schshell.close();	
	//		}
		//display.dispose ();

	}

	

}
