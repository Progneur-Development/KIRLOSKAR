package com.tc.scheduledata.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AbstractAIFSession;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.scheduledata.view.CreateProjLevelUI_BG;
import com.teamcenter.scheduledata.view.CreateProjectLevelUI;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ProjectLevelHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ProjectLevelHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Scheduledata",
				"Hello, Eclipse world");*/
		
		
	AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	TCSession session = (TCSession) application.getSession();
	System.out.println("session  "+session);
	
/*	CreateProjectLevelUI_oldmain ui = new CreateProjectLevelUI_oldmain();
	ui.CreateProjectUI();*/
	
	/*CreateProjectLevelUI ui = new CreateProjectLevelUI();
	ui.CreateProjectUI();*/
	
	CreateProjLevelUI_BG ui = new CreateProjLevelUI_BG(session);
	ui.callUI();
	
		return null;
	}
}
