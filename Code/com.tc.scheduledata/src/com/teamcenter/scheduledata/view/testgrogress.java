package com.teamcenter.scheduledata.view;

import java.lang.reflect.Array;
import java.util.ArrayList;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class testgrogress {
	private static Table table;
	private static Label label4;
	private static ProgressBar bar;
	private static ProgressBar progressBar;

	public static void main(String[] args) { Display display = new Display();
    Shell shell = new Shell(SWT.SHELL_TRIM);
    shell.setText("StackOverflow");
    shell.setLayout(new GridLayout(1, false));


  
    shell.setSize(523, 200);
    Composite container = new Composite(shell, SWT.NONE);
	GridLayout gl = new GridLayout(2, false);
	gl.marginWidth = 5;
	gl.marginHeight = 3;
	container.setLayout(gl);

	progressBar = new ProgressBar(container, SWT.SMOOTH);
	GridData gdPprogressBar = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	gdPprogressBar.heightHint = 16;
	gdPprogressBar.widthHint = 130;
	progressBar.setLayoutData(gdPprogressBar);
	progressBar.setMinimum(0); 
	progressBar.setMaximum(100);
	progressBar.setSelection(50);
	//progressBar.
	//progressBar.setToolTipText("htt");
	

	Label label = new Label(container, SWT.None);
	label.setText(50 + "%");

	
   
    
    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));
    GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    gd_composite.widthHint = 493;
    composite.setLayoutData(gd_composite);
    
    table = new Table(composite, SWT.BORDER | SWT.SINGLE);
    table.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    //milestonelist
    TableItem items = new TableItem(table,SWT.NONE);
    TableItem items1 = new TableItem(table,SWT.NONE);
    
    TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
    tblclmnNewColumn_1.setWidth(100);
    tblclmnNewColumn_1.setText("New Column");
    
    Label lblTest = new Label(composite, SWT.NONE);
    lblTest.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
    lblTest.setText("test");
    
    for(int i = 0; i<5 ;i++)
    {
    	TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn.setWidth(100);
        //tblclmnNewColumn.setText("New Column");
    }
    
    for(int i = 0; i<5 ;i++)
    {
    
   	items.setImage(i,SWTResourceManager.getImage(CreateProjectLevelUI.class,"icons/diaBlueComplete.png"));
   	items1.setText(i,"tt");
   	if(i<3)
   	items1.setBackground(i,SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
   	ProgressBar progressBar = new ProgressBar(table, SWT.NONE);
	progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	progressBar.setSelection(0);
   
    }
   
	table.addListener(SWT.Selection, new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			TableItem[] item = table.getSelection();
			 for (int i = 0; i < item.length; i++){
				 TableItem selectedItem = item[i];
				 String selectedUniqueJobId = selectedItem.getText();
		      }
		}
	});
   
    
    ArrayList<String> list=new ArrayList<String>();
    list.add("task1");
    list.add("task2");
    list.add("task3");
    list.add("task4");
    list.add("task5");
    
    /*Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new GridLayout(list.size(), false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    for(int i=0;i<list.size();i++)
    {
    	 Button btnNewButton = new Button(composite, SWT.NONE);
    	    btnNewButton.setText(list.get(i));
    }

    ProgressBar progressBar = new ProgressBar(composite, SWT.NONE);
    progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, list.size(), 1));
	//progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
	progressBar.setSelection(60);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);*/
    
    
    shell.open();

    while (!shell.isDisposed())
    {
        if (!display.readAndDispatch())
        {
            display.sleep();
        }
    }
    display.dispose();}

}