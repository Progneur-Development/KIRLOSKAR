package com.teamcenter.scheduledata.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.AbstractAIFSession;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class TCDataFunctionality {
	
	
	private ModelObject[] Schedule_object;
	private int Schedule_count;
	TCSession session = null;
	private String status;
	private Property prop;

	public TCDataFunctionality(TCSession session){
	
		this.session=session;
		session.getSession(getAllActiveProjects());
		//queryService = SavedQueryService.getService(session.getSoaConnection());
		 // schMngService = ScheduleManagementService.getService(session) ;
	}
	public ModelObject[] getAllActiveProjects() {

		System.out.println("\n inside getScheduleData");
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		AbstractAIFSession session = (TCSession) application.getSession();
		System.out.println("session  "+session);
		System.out.println("session"+session);
		SavedQueryService service = SavedQueryService.getService(((TCSession) session).getSoaConnection());
		System.out.println("service"+service);
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
		System.out.println("Schedule_object"+Schedule_object);
		return Schedule_object;	
	}
	public ModelObject[] getSchedule_object() {
		return Schedule_object;
	}
	
	LinkedHashMap<String, LinkedHashMap<String, String>> getTableData(String seleProject) {
		LinkedHashMap<String, LinkedHashMap<String, String>> mainlist = new LinkedHashMap<String, LinkedHashMap<String,String>>();
				
		try {
			
			TCComponentSchedule scheObj = null;
			//Get string project into model object type
			ModelObject[] projList = getAllActiveProjects();
			for(int i=0;i<projList.length;i++)
			{
				if(projList[i].toString().equals(seleProject))
				{
					scheObj=(TCComponentSchedule)projList[i];
					break;
				}
			}
			
			//Get schedule task list
				ArrayList<TCComponent> taskList = getSchAllTaskData(scheObj);
				for(int m=0;m<taskList.size();m++)
				{
					LinkedHashMap<String,String> innerlist = new LinkedHashMap<String, String>();
					TCComponentScheduleTask schTask=(TCComponentScheduleTask)taskList.get(m);
					
					String task_name = schTask.toString();
					//String projStartDate = Schedule_object[k].getPropertyDisplayableValue("start_date");
					//String projEndDate = Schedule_object[k].getPropertyDisplayableValue("finish_date");
					String startDate = schTask.getPropertyDisplayableValue("start_date").toString();
					String endDate = schTask.getPropertyDisplayableValue("finish_date").toString();
					String percentage = schTask.getPropertyDisplayableValue("complete_percent").toString();
					String taskType = schTask.getPropertyDisplayableValue("fnd0TaskTypeString");
					String taskStatus = schTask.getPropertyDisplayableValue("fnd0status").toString();
					String assignee = schTask.getPropertyDisplayableValue("ResourceAssignment").toString();
					String desc = schTask.getPropertyDisplayableValue("object_desc").toString();
					
				//	if(taskType.)
					innerlist.put("TASK_NAME", task_name);
					innerlist.put("TASK_TYPE", taskType);
					innerlist.put("TASK_START_DATE", startDate);
					innerlist.put("TASK_END_DATE", endDate);
					
					if(percentage!=null && percentage.length()>0 && percentage.contains("."))
						percentage=percentage.split("\\.")[0];
					
					innerlist.put("TASK_PER_COMPLETE", percentage+"%");
					innerlist.put("TASK_STATUS", taskStatus);
					innerlist.put("TASK_ASSIGNEE", assignee);
					innerlist.put("TASK_DESC", desc);
					
					mainlist.put(task_name, innerlist);
				}
				 
			//}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mainlist;

	}
	LinkedHashMap<String,String>getMileshtoneList(String seleProject) {
		LinkedHashMap<String,String> mainlist = new LinkedHashMap<String,String>();
				
		try {
			
			TCComponentSchedule scheObj = null;
			//Get string project into model object type
			ModelObject[] projList = getAllActiveProjects();
			for(int i=0;i<projList.length;i++)
			{
				if(projList[i].toString().equals(seleProject))
				{
					scheObj=(TCComponentSchedule)projList[i];
					break;
				}
			}
			
			//Get schedule task list
				ArrayList<TCComponent> taskList = getSchAllTaskData(scheObj);
				LinkedHashMap<String,Object> taskMileStoneData = new LinkedHashMap<String, Object>();
				for(int m=0;m<taskList.size();m++)
				{
					TCComponentScheduleTask schTask=(TCComponentScheduleTask)taskList.get(m);
					
					String task_name = schTask.toString();
					String taskType = schTask.getPropertyDisplayableValue("fnd0TaskTypeString");
					String taskStatus = schTask.getPropertyDisplayableValue("fnd0status").toString();
					if(taskType.equals("M"))
					  mainlist.put(task_name,taskStatus);
					
					
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainlist;
	}

    public ArrayList<TCComponent> getSchAllTaskData(TCComponentSchedule sche){
    	ArrayList<TCComponent> schallTaskList = new ArrayList<TCComponent>();
		try {
			
			 prop = sche.getPropertyObject("fnd0SummaryTask");
			System.out.println("prop values" + prop);
			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) prop.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);	
			for (int j = 0; j <SchTaskArr.length; j++) 
			{
				TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
				schallTaskList.add(myTaskActivity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return schallTaskList;
	}
	public ArrayList<TCComponent> getScheduleTasks(TCComponentSchedule mySch)
	{
		ArrayList<TCComponent> TaskObjList = new ArrayList<TCComponent>();	
		try {
			
			TCComponentUser user=session.getUser();
			System.out.println("***User====>"+user);	
			Property t = mySch.getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);

			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);
		
			for (int j = 0; j <SchTaskArr.length; j++) 
			{
			TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
			System.out.println("myTaskActivity"+myTaskActivity);	
			String taskType = myTaskActivity.getProperty("fnd0TaskTypeString");
			//Task = myTaskActivity.getProperty("complete_percent"); 
			if(taskType.equalsIgnoreCase("T"))
			{
				TaskObjList.add(myTaskActivity);
				System.out.println("Tasks **"+taskType);
				
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaskObjList;
	}
}
