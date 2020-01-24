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
	
	
	private ModelObject[] schedule_object;
	
	TCSession session = null;

	private ArrayList<String> allUserUniqueList;

	public TCDataFunctionality(TCSession session){
	
		this.session=session;
		schedule_object = scheduleSearchQuery();
		//session.getSession(getAllActiveProjects());
		//queryService = SavedQueryService.getService(session.getSoaConnection());
		 // schMngService = ScheduleManagementService.getService(session) ;
	}

	public ModelObject[] scheduleSearchQuery() {
		ModelObject[] Schedule_object1 = null;
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		AbstractAIFSession session = (TCSession) application.getSession();
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
			int schCount = result.nFound;
			Schedule_object1 = result.objects;

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Schedule_object"+schedule_object);
		
		return Schedule_object1;	
	}
	//Get all active schedule list
	//Schedule whose status is in progress
	public ArrayList<ModelObject> getAllActiveProjects() {
		
		ArrayList<ModelObject> allActiveProjList=new ArrayList<ModelObject>();
		for (int k = 0; k < schedule_object.length; k++) 
		{
			try {
				
				String status = schedule_object[k]
						.getPropertyDisplayableValue("fnd0SSTStatus");

				if (status.equals("In Progress")) {
					allActiveProjList.add(schedule_object[k]);
					}
				
			} catch (NotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return allActiveProjList;
	}
public ArrayList<ModelObject> getAllProjectsResources() {
		
		ArrayList<ModelObject> allActiveProjList=new ArrayList<ModelObject>();
		for (int k = 0; k < schedule_object.length; k++) 
		{
			try {
				
				String status = schedule_object[k]
						.getPropertyDisplayableValue("fnd0SSTStatus");

				if (status.equals("In Progress")) {
					allActiveProjList.add(schedule_object[k]);
					}
				
			} catch (NotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return allActiveProjList;
	}
	/*public ModelObject[] getSchedule_object() {
		return Schedule_object;
	}*/
	
	LinkedHashMap<String, LinkedHashMap<String, String>> getTableData(String seleProject) {
		LinkedHashMap<String, LinkedHashMap<String, String>> mainlist = new LinkedHashMap<String, LinkedHashMap<String,String>>();
				
		try {
			
			TCComponentSchedule scheObj = null;
			//Get string project into model object type
			ArrayList<ModelObject> projList = getAllActiveProjects();
			for(int i=0;i<projList.size();i++)
			{
				if(projList.get(i).toString().equals(seleProject))
				{
					scheObj=(TCComponentSchedule)projList.get(i);
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
					String assignee = schTask.getPropertyDisplayableValues("ResourceAssignment").toString();
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
			ArrayList<ModelObject> projList = getAllActiveProjects();
			for(int i=0;i<projList.size();i++)
			{
				if(projList.get(i).toString().equals(seleProject))
				{
					scheObj=(TCComponentSchedule)projList.get(i);
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

    public ArrayList<TCComponent> getSchAllTaskData(TCComponentSchedule sche)
    {
    	ArrayList<TCComponent> schallTaskList = new ArrayList<TCComponent>();
		try 
		{
			Property prop = sche.getPropertyObject("fnd0SummaryTask");
			//System.out.println("prop values" + prop);
			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) prop.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
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
	
	//Get all user Unique list
	//Bhavana -1-23-2019
	public ArrayList<String> getAllUserList()
	{
		allUserUniqueList=new ArrayList<String>();
		boolean flagChk=false;
		try {

			//loop on object all schedule 
			for (int schCnt = 0; schCnt < schedule_object.length; schCnt++) 
			{
				//Get all tasks of schedule
				ArrayList<TCComponent> taskList = getSchAllTaskData((TCComponentSchedule)schedule_object[schCnt]);
				for(int m=0;m<taskList.size();m++)
				{
					TCComponentScheduleTask schTask=(TCComponentScheduleTask)taskList.get(m);
				     String assignee = schTask.getPropertyDisplayableValues("ResourceAssignment").toString();
				     
				     if(assignee!=null && assignee.length()>0)
				     {
				    	 //get unqiue user and add into list
				    	 getUniqueUser(assignee,allUserUniqueList);
				    	
				     }
				}
			 }
    		} catch (Exception e) {
				e.printStackTrace();
			}
			
			return allUserUniqueList;
	}
	
	//Add unique user into list
	//Bhavana : 1-24-2020
	private void getUniqueUser(String assignee, ArrayList<String> allUserList) {
		try {
	     	 String assName="";
	    	// System.out.println("assignee=="+assignee);
	    	 //If multiple resources are available for one task 
	    	  if(assignee.contains(","))
	    	  {
	    		  String users[] = assignee.split(",");
	    		  for(String tempUser:users)
	    		  {
	    			  assName=strValidation(tempUser.trim());
	    			
	    			  if(!allUserList.contains(assName))
			    			 allUserList.add(assName); 
	    		  }
	    	  }else
	    	  {
	    		  assName=strValidation(assignee.trim());
	    		  if(!assignee.contains("[]"))
			    	 {
			    		/* if(flagChk)
				    	 {
			    			 allUserList.add(assName);
			    			 flagChk=false;
				    	 }else*/
				    	 {
				    		 if(!allUserList.contains(assName))
				    			 allUserList.add(assName); 
				    	 }
			    	 }
	    	  }
	     
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	String strValidation(String assignee)
	{
		
		if(assignee.contains("("))
		  {
			assignee = assignee.split("\\(")[0];
			assignee = assignee.replaceAll("[^a-zA-Z0-9]", " ").trim();  //allUserList.add(uu.split("\\(")[0].trim());
		  
		  }
					  
		return assignee;
	}
	
	LinkedHashMap<String, LinkedHashMap<String, String>> getAllUsersData()
	{
		LinkedHashMap<String, LinkedHashMap<String,String>> AllUsersData = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		ArrayList<String> allUserList=new ArrayList<String>();
		try {

			LinkedHashMap<String,String> data = new LinkedHashMap<String,String>();
			for (int k = 0; k < schedule_object.length; k++) 
			{
			try {
				data = new LinkedHashMap<String,String>();
				String status = schedule_object[k].getPropertyDisplayableValue("fnd0SSTStatus");
						if (status.equals("In Progress")) {
							String sche = schedule_object[k].toString();
							String startDate = schedule_object[k].getPropertyDisplayableValue("start_date").toString();
							String endDate = schedule_object[k].getPropertyDisplayableValue("finish_date").toString();
							String duration = schedule_object[k].getPropertyDisplayableValue("duration").toString();
							String work_estimate = schedule_object[k].getPropertyDisplayableValue("work_estimate").toString();
							String assignee = schedule_object[k].getPropertyDisplayableValue("ResourceAssignment").toString();
							
							if (sche != null)
								data.put("SCH_NAME",sche);
							else
								data.put("SCH_NAME","");

							if (startDate != null)
								data.put("START_DATE",startDate);
							else
								data.put("START_DATE","");

							if (endDate != null)
								data.put("END_DATE",endDate);
							else
								data.put("END_DATE","");

							if (duration != null)
								data.put("DURATION",duration);
							else
								data.put("DURATION","");
							
							if (work_estimate != null)
								data.put("WORK_ESTIMATE",work_estimate);
							else
								data.put("WORK_ESTIMATE","");
							
							if (assignee != null)
								data.put("USER",assignee);
							else
								data.put("USER","");

							AllUsersData.put(k + "", data);
						}
					} catch (NotLoadedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return AllUsersData;
	}

}
