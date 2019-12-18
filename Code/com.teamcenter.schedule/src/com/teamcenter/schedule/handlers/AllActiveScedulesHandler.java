package com.teamcenter.schedule.handlers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.ServiceData;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.schedule.ScheduleManagerService;
import com.teamcenter.schedule.view.AllActiveSchedules;
import com.teamcenter.schedule.view.GanttDemo1;
import com.teamcenter.schedule.view.NewGanttChartPOC;
import com.teamcenter.schedule.view.ResAssigneeExportToExcel;
import com.teamcenter.schedule.view.ResourceAssignee;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2007_01.ScheduleManagement;
import com.teamcenter.services.rac.projectmanagement._2007_01.ScheduleManagement.TaskDeliverableData;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.CreatedObjectsContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.TaskCreateContainer;
import com.teamcenter.soa.client.model.ErrorStack;
import com.teamcenter.soa.client.model.ModelObject;

public class AllActiveScedulesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		TCSession session = (TCSession) application.getSession();
		
        /*RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);*/
	
		/*ResAssigneeExportToExcel excel = new ResAssigneeExportToExcel();
		try {
			excel.getExcelScheduleData("C:\\Users\\Administrator\\Desktop\\ScheduleReport\\Book1.xls");
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
				
		AllActiveSchedules res=new AllActiveSchedules(session);
		res.allActiveSchUI();
		return null;
	}
}
