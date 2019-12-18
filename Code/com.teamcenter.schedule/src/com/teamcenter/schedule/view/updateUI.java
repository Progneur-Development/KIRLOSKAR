package com.teamcenter.schedule.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import com.teamcenter.rac.kernel.TCSession;

public class updateUI {
	private static LinkedHashMap<String, LinkedHashMap<String, String>> exceldata;
	private static Text text;
	static TCSession session = null;
	TCdataOperations tcDataOperationObj=null;
	//static LinkedHashMap<String, LinkedHashMap<String, String>> dataexcel = null;
	
	public updateUI(TCSession session)
	{
		this.session =session;
	}
	public static void updatepropToTC(){
		Display display=Display.getDefault();
	  	final Shell schshell = new Shell(display);
		schshell.setSize(416, 184);
		schshell.setText("UpdatePropertyValues");
		schshell.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(schshell, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1);
		gd_composite.heightHint = 162;
		gd_composite.widthHint = 633;
		composite.setLayoutData(gd_composite);
		
		text = new Text(composite, SWT.BORDER);
		GridData gd_text = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_text.heightHint = 21;
		gd_text.widthHint = 182;
		text.setLayoutData(gd_text);
	//	text.setText("C:\\TEMP\\ScheduleReport");
		new Label(composite, SWT.NONE);
		
		Button BrowseButton = new Button(composite, SWT.NONE);
		GridData gd_BrowseButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_BrowseButton.widthHint = 101;
		BrowseButton.setLayoutData(gd_BrowseButton);
		BrowseButton.setText("Browse");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button UpdateToTcButton = new Button(composite, SWT.NONE);
		GridData gd_UpdateToTcButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_UpdateToTcButton.widthHint = 122;
		UpdateToTcButton.setLayoutData(gd_UpdateToTcButton);
		UpdateToTcButton.setText("UpdateToTC");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
				
		BrowseButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				FileDialog fd = new FileDialog(schshell, SWT.SAVE);
		       // fd.setText("UpdateVlaues");
		      //  fd.setFilterPath("C:\\TEMP\\ScheduleReport");
		        String[] filterExt = { "*.*","*.txt", "*.doc", ".rtf"};
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        System.out.println(selected);
		        fd.setFilterPath(selected);
		        
				if (selected != null)
					text.setText(selected);
		        try {
					exceldata = new ResAssigneeExportToExcel().readXLSFile(selected);
					System.out.println("exceldata========"+exceldata);
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			
		});
		
		UpdateToTcButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				TCdataOperations obj = new TCdataOperations(session); 
				
				  obj.updateSchedulePropvalue(exceldata);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		schshell.open();
		/*while (!schshell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
		 schshell.close();
		 }
		 display.dispose ();*/

	

	}
}
