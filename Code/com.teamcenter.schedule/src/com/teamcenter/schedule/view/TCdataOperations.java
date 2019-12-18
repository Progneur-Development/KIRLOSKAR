package com.teamcenter.schedule.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.ServiceData;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.schedule.project.common.gui.HoursFormatter;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.query.SavedQueryService;
import com.teamcenter.services.loose.query._2006_03.SavedQuery;
import com.teamcenter.services.loose.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesCriteriaInput;
import com.teamcenter.services.loose.query._2010_04.SavedQuery.FindSavedQueriesResponse;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2007_01.ScheduleManagement;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.AssignmentCreateContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.CreatedObjectsContainer;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.TaskCreateContainer;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Property;
import com.teamcenter.schemas.projectmanagement._2011_06.schedulemanagement.SchMgtOptions;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.SchMgtIntegerOption;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.ScheduleLoadResponse;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.LoadScheduleContainer;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.TaskExecUpdate;


public class TCdataOperations {
	
	private int Schedule_count;
	private ModelObject[] Schedule_object;
    TCSession session=null;
	private Object key;
	private LinkedHashMap<String, String> obj;
	private SavedQueryService queryService;
	private ModelObject schobj;
	ScheduleManagementService schMngService;
	public TCdataOperations(TCSession session) {
		this.session=session;
		 queryService = SavedQueryService.getService(session.getSoaConnection());
		  schMngService = ScheduleManagementService.getService(session) ;
	}
		ModelObject FindScheduleQueryObj() 
		{
		System.out.println("\n inside FindScheduleQueryObj");	
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
			FindSavedQueriesResponse res = queryService.findSavedQueries(queryObject);
			System.out.println("\n inside res");
			//response = queryService.findSavedQueries(queryObject);
			savequery = res.savedQueries;

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelObject save = savequery[0];
		return save;
	}
	
	ModelObject getSchData(ModelObject save,String name)
	{
		String[] id = { "Name" };
		String[] val = { name };
		
		ExecuteSavedQueryResponse result;
		try {
			//SavedQuery queryService = null;
			result = queryService.executeSavedQuery(save, id, val, 1000);
			
			System.out.println("queryService=="+queryService);
			System.out.println("result=="+result);
			ModelObject[] schobjs = result.objects;
			System.out.println("schobjslength======"+schobjs.length);
			if(schobjs.length>0)
			{
				schobj=schobjs[0];
			System.out.println("schobj=="+schobj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
         return schobj;
	}
	
	void getScheduleData() {
		System.out.println("\n inside getScheduleData");
		SavedQueryService service = SavedQueryService.getService(session.getSoaConnection());
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
			setSchedule_count(result.nFound);
			setSchedule_object(result.objects);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	void resourceAssUpdation()
	{
		try {
			
			ScheduleManagementService tt = ScheduleManagementService.getService(session) ;
			TCComponentSchedule mySch = (TCComponentSchedule) getSchedule_object()[0];
			System.out.println("***mySch====" + mySch.toDisplayString());     
			//TCComponentUser user=new TCComponentUser();
			TCComponentUser user=session.getUser();
			System.out.println("***User====>"+user);	
			Property t = mySch.getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);

			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("SchTaskArr size is " + SchTaskArr.length);
			if(SchTaskArr.length>0)
			{
				for (int j = 2; j < 4; j++) 
				{	
					TCComponent myTaskActivity=(TCComponent)SchTaskArr[j].getComponent();
					 AssignmentCreateContainer containerObj=new AssignmentCreateContainer();
			           containerObj.task=(TCComponentScheduleTask)myTaskActivity;
			           containerObj.user=user;
			           containerObj.assignedPercent=100;
						
			           AssignmentCreateContainer[] container=new AssignmentCreateContainer[1];
			           container[0]=containerObj;        
						try {
							CreatedObjectsContainer res=tt.assignResources(mySch,container);
							
							int error=res.serviceData.sizeOfPartialErrors();
							System.out.println("Error==="+error);
							if(error==0)
							{
								
							}
							else
						     {
						    	 int n1= res.serviceData.sizeOfPartialErrors();
						    	 String  n2= res.serviceData.toString();
						    	 String[]  n3= res.serviceData.getPartialError(0).getMessages();
						    	 com.teamcenter.rac.util.MessageBox.post("Failed to assign resource or It may be already assigned for "+SchTaskArr[j].toString(),"Alert", MessageBox.INFORMATION); 
						     }
							
							mySch.refresh();
							//return error;
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// Create Schedule And Task 15-11-2019
	public void CreateTaskInSchedule(TCComponentSchedule mySch, String taskName, String taskDesc,  Calendar startDate, Calendar finishDate, int workEstimate, String objType)
	{
		try {
			AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
			TCSession session = (TCSession) application.getSession();
			ScheduleManagementService ScheduleManagService = ScheduleManagementService.getService(session);		
			
			TaskCreateContainer TaskContainerObj =  new TaskCreateContainer();
			TaskContainerObj.name = taskName;
			TaskContainerObj.desc = taskDesc;
			TaskContainerObj.start = startDate;
			TaskContainerObj.finish = finishDate;
			TaskContainerObj.workEstimate=6;
			TaskContainerObj.objectType=objType;		
			TaskCreateContainer[] taskCreateContainer = new TaskCreateContainer[1]; 
			taskCreateContainer[0] =  TaskContainerObj;	
			System.out.println("TaskContainerObj=="+TaskContainerObj);
			CreatedObjectsContainer response = ScheduleManagService.createTasks(mySch, taskCreateContainer);
			System.out.println("createdObjectContainer.serviceData.sizeOfPartialErrors()=="+response.serviceData.sizeOfPartialErrors());
			if(response.serviceData.sizeOfPartialErrors()==0)
			{
				System.out.println("Task is Created");
			}else
			{
				System.out.println("Task is not Created");
				int n1= response.serviceData.sizeOfPartialErrors();
		    	String[] n2 = response.serviceData.getPartialError(0).getMessages();
		    	System.out.println("n2=="+n2[0]);
		    	String[]  n3= response.serviceData.getPartialError(0).getMessages();
		    	System.out.println("n3=="+n3);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CreateScheduleFromExcelToTC(String customerName1, String customerNumber1,Boolean datesLinked1, String description1,Calendar finishDate1,String name1,Calendar startDate1,String status1 )
	{		
		try {	
			AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
			TCSession session = (TCSession) application.getSession();
				
			ScheduleManagementService localScheduleManagementService = ScheduleManagementService.getService(session);
			ScheduleManagement.CreateScheduleContainer ScheduleContainer = new ScheduleManagement.CreateScheduleContainer();
		
			ScheduleContainer.customerName = customerName1;
			ScheduleContainer.customerNumber = customerNumber1;
			ScheduleContainer.datesLinked = datesLinked1;
			ScheduleContainer.description = description1;
			ScheduleContainer.finishDate = finishDate1;
	        ScheduleContainer.isPublic=false;
	        ScheduleContainer.isTemplate=false;
	        ScheduleContainer.linksAllowed=false;
	        ScheduleContainer.name=name1;
	        ScheduleContainer.percentLinked=false;
	        ScheduleContainer.priority=1;
	        ScheduleContainer.published=false;
	        ScheduleContainer.startDate=startDate1;
	        ScheduleContainer.status=status1;
	        ScheduleManagement.CreateScheduleContainer[] newScheduleOperation = new ScheduleManagement.CreateScheduleContainer[1];
	        newScheduleOperation[0]=ScheduleContainer;
	        
	        @SuppressWarnings("deprecation")
	        ServiceData ScheduleOperation = localScheduleManagementService.createSchedule(newScheduleOperation);
	      
	       // ReferenceIDContainer createScheduleContainer = ((ScheduleModel)localObject1).newSchedule((ScheduleCreateContainer)localObject2);
	        System.out.println("newScheduleOperation111=="+ScheduleOperation);
	       TCComponentSchedule schobj = (TCComponentSchedule)ScheduleOperation.getCreatedObject(0);
	       System.out.println("schobj=="+schobj);
	       CreateTaskInSchedule(schobj,"Requirement Gathering","Requirement Gathering",startDate1,finishDate1,7,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"Design Documentation Creation","Design Documentation Creation",startDate1,finishDate1,9,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"Implementation","Implementation",startDate1,finishDate1,4,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"Integration","Integration",startDate1,finishDate1,7,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"Development","Development",startDate1,finishDate1,9,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"Deployment","Deployment",startDate1,finishDate1,4,"ScheduleTask");  
	       
	       
	      /* CreateTaskInSchedule(schobj,"M1","Requirement Gathering",startDate1,finishDate1,7,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"M2","Design Documentation Creation",startDate1,finishDate1,9,"ScheduleTask");
	       CreateTaskInSchedule(schobj,"M3","Implementation",startDate1,finishDate1,4,"ScheduleTask");*/
	         
	       
		} catch (Exception e) {
			
		}	
	}	
	
	// old Working scheduleCreaion
	/*void   scheduleCreaion() {
		try {
			GregorianCalendar startDate=new GregorianCalendar( 2017, 01, 07 );
	  	   	GregorianCalendar finishDate=new GregorianCalendar( 2018, 01, 07 );	
	  	   	ScheduleManagementService localScheduleManagementService = ScheduleManagementService.getService(session);
		    ScheduleManagement.CreateScheduleContainer newScheduleOperation1 = new ScheduleManagement.CreateScheduleContainer();
	        System.out.println("newScheduleOperation1=="+newScheduleOperation1);
	        newScheduleOperation1.customerName="Prog TDPS";
	        newScheduleOperation1.customerNumber="1222";
	        newScheduleOperation1.datesLinked=false;
	        newScheduleOperation1.description="Progneur Test Schedule";
	        newScheduleOperation1.finishDate=finishDate;
	        newScheduleOperation1.isPublic=false;
	        newScheduleOperation1.isTemplate=false;
	        newScheduleOperation1.linksAllowed=false;
	        newScheduleOperation1.name="Prog_TestSch";
	        newScheduleOperation1.percentLinked=false;
	        newScheduleOperation1.priority=1;
	        newScheduleOperation1.published=false;
	        newScheduleOperation1.startDate=startDate;
	        newScheduleOperation1.status="complete";
		    
	        ScheduleManagement.CreateScheduleContainer[] newScheduleOperation = new ScheduleManagement.CreateScheduleContainer[1];
	        newScheduleOperation[0]=newScheduleOperation1;
	        
	        @SuppressWarnings("deprecation")
	        ServiceData newScheduleOperation111 = localScheduleManagementService.createSchedule(newScheduleOperation);
	       // ReferenceIDContainer createScheduleContainer = ((ScheduleModel)localObject1).newSchedule((ScheduleCreateContainer)localObject2);
	        System.out.println("newScheduleOperation111=="+newScheduleOperation111);
	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	*/
	public void updateSchedulePropvalue(LinkedHashMap<String, LinkedHashMap<String, String>> mExceldata)
	{
		try { 
			
			AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
			TCSession session = (TCSession) application.getSession();
			//ScheduleManagementService schMngServiceData = ScheduleManagementService.getService(session) ;
			System.out.println("schMngServiceData==");
			System.out.println("mExceldata=="+mExceldata);
			
			ModelObject queryObj = FindScheduleQueryObj();
			//ModelObject queryObj = get();
			System.out.println("queryObj=="+queryObj);
			if(mExceldata.size()>0)
			{
				for(String mainKEy:mExceldata.keySet())
				{
					LinkedHashMap<String, String> innerList = mExceldata.get(mainKEy);
					String taskname=innerList.get("0");
					String schName=innerList.get("1");
					String status=innerList.get("2");
					String startDAte=innerList.get("3");
					String endDate=innerList.get("4");
					String userName=innerList.get("5");
					String perComplete=innerList.get("6");
					String workComplete=innerList.get("7");
					
					System.out.println("taskname=="+taskname+"\t schName="+schName+"\t status=="+status+"\t startDAte=="+startDAte+
							"\t endDate=="+endDate+"\t userName=="+userName+"\t workComplete=="+workComplete+"\t perComplete=="+perComplete);
				
					ModelObject schobj = getSchData(queryObj,schName.trim());
					
					System.out.println("schobj====="+schobj);
					
					TCComponentSchedule tcComSchObj=(TCComponentSchedule)schobj;
					System.out.println("tcComSchObj=="+tcComSchObj.toDisplayString());
					
					
					TCComponentUser user=session.getUser();
					System.out.println("User====>"+user);
					
					Property prop = schobj.getPropertyObject("fnd0SummaryTask");
					System.out.println("prop values" + prop);

					TCComponentScheduleTask schobjTask = (TCComponentScheduleTask) prop.getModelObjectValue();
					AIFComponentContext[] SchTaskArr = schobjTask.getChildren();
					System.out.println("ttt size is " + SchTaskArr.length);
					
					for(int i = 0 ; i<SchTaskArr.length; i++)
					{
						String arrval=SchTaskArr[i].toString().trim();
						
						if(arrval.equals(taskname))
						{
							System.out.println("arrval=="+arrval);
							System.out.println("taskname=="+taskname);
							System.out.println("matchinf found");
							TCComponentScheduleTask	schTask=(TCComponentScheduleTask)SchTaskArr[i].getComponent();
							updateResource(schTask,user,tcComSchObj);
							updateRowToTC(tcComSchObj,schTask,taskname,startDAte,endDate,userName,status,perComplete,workComplete);
						}
					}
				}
			}	
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void updateResource(TCComponent myTaskActivity, TCComponentUser user,  TCComponentSchedule mySch )
	{
		try {	
			ScheduleManagementService smservice = ScheduleManagementService.getService(session) ;
			 AssignmentCreateContainer containerObj=new AssignmentCreateContainer();
	           containerObj.task=(TCComponentScheduleTask)myTaskActivity;
	           containerObj.user=user;
	           containerObj.assignedPercent=100;
				
	           AssignmentCreateContainer[] container=new AssignmentCreateContainer[1];
	           container[0]=containerObj;        
				try {
					CreatedObjectsContainer res=smservice.assignResources(mySch,container);
					System.out.println("res=="+res);
					
					int error=res.serviceData.sizeOfPartialErrors();
					System.out.println("Error==="+error);
					if(error==0)
					{
						
					}
					else
				     {
				    	 int n1= res.serviceData.sizeOfPartialErrors();
				    	 String  n2= res.serviceData.toString();
				    	 String[]  n3= res.serviceData.getPartialError(0).getMessages();
				    	 com.teamcenter.rac.util.MessageBox.post("Failed to assign resource or It may be already assigned for "+myTaskActivity.toString(),"Alert", MessageBox.INFORMATION); 
				     }
					mySch.refresh();
					//return error;
				} catch (Exception e) {
					
					e.printStackTrace();
				}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//*********import to tc from excel
	public void updateRowToTC(TCComponentSchedule tcComSchObj,TCComponentScheduleTask myTask, String taskname, String startDate, String endDate, String userName, String status, String perComplete, String workComplete) 
	{
		try {
				TCComponentUser user=session.getUser();
				System.out.println("User====>"+user);
				
				SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String dd=getFormattedDate(startDate);
				Date dateAS = sdf4.parse(dd);
				System.out.println("dateAS=="+dateAS);
				Calendar calASDate = Calendar.getInstance();
				calASDate.setTime(dateAS);
				
				String dd2=getFormattedDate(endDate);
				Date dateAF = sdf4.parse(dd2);
				System.out.println("dateAF=="+dateAF);
				Calendar calAFDate = Calendar.getInstance();
				calAFDate.setTime(dateAF);
				
				 double newPCVal=0.0;
				 newPCVal=Double.parseDouble(perComplete);	
				 System.out.println("perComplete=="+perComplete);
				 System.out.println("newPCVal=="+newPCVal);
			
				 Integer WC = Integer.valueOf(workComplete);
				 WC=WC*60;
				
				if(status.equalsIgnoreCase("In Progress"))
				{
						status="in_progress";
						
				}else if(status.equalsIgnoreCase("Complete"))
				{
					status = "complete";
				}
				
				 TaskExecUpdate taskupdate=new TaskExecUpdate();
				 taskupdate.task=(TCComponentScheduleTask) myTask;
				 taskupdate.updateAS =true;
				 taskupdate.newAS =calASDate;
				 taskupdate.updateAF =true;
				 taskupdate.newAF =calAFDate;
				 taskupdate.newStatus=status;
				 taskupdate.newWC=(int) WC;
				 taskupdate.newPC=newPCVal;
				//taskupdate.=userName;//(complete/in_progress)
				// taskupdate.newWC =(int) wc; //Work complete
								
	    		 TaskExecUpdate[] taskupdateArr=new TaskExecUpdate[1];
	     		 taskupdateArr[0]=taskupdate;
	     		 	 
				ServiceData schTaskUpdate = schMngService.updateTaskExecution(taskupdateArr);
	    		 if(schTaskUpdate.sizeOfPartialErrors() == 0)
	    		 {
	    			 System.out.println(" *****************Task has been updated successfully*******************" );
	    			 
	    		 }else
	    		 {
	    			 System.out.println(" ************************Task has ***NOT*** been updated successfully************************** " );
	    		 }
		}
				catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public  String getFormattedDate(String inDate) {
	    String outDate = "";
	    Date outDate1 = null ;
	    if (inDate != null) 
		  {
		 	try {
			  	inDate=inDate.replaceAll("/", "-");
				System.out.println("startDAte=="+inDate);
				
				String ExcelformateendDate = "MM-dd-yy hh:mm";
				String tcformateendDate ="dd-MM-yyyy HH:mm" ;
				
				SimpleDateFormat sdf3 = new SimpleDateFormat(ExcelformateendDate);
				SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				
				outDate = sdf4.format(sdf3.parse(inDate));
				//outDate = sdf3.parse(inDate);
				System.out.println("***Formated End date ****"+outDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	    return outDate;
	  }
	
	
	void   scheduleUpdate() {
		try {
			
			ScheduleManagementService schMngService = ScheduleManagementService.getService(session) ;
			
			TCComponentSchedule mySch = (TCComponentSchedule) getSchedule_object()[0];
			System.out.println("********mySch====" + mySch.toDisplayString());
              
			//TCComponentUser user=new TCComponentUser();
			TCComponentUser user=session.getUser();
			System.out.println("User====>"+user);
			
			Property t = mySch.getPropertyObject("fnd0SummaryTask");
			System.out.println("t is " + t);

			TCComponentScheduleTask mySchTask = (TCComponentScheduleTask) t.getModelObjectValue();
			AIFComponentContext[] SchTaskArr = mySchTask.getChildren();
			System.out.println("ttt size is " + SchTaskArr.length);
			for (int j = 2; j < 4; j++) 
			{
				InterfaceAIFComponent myTask=SchTaskArr[2].getComponent();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				Date dateAS = sdf.parse("7-01-2018 16:05");
				Calendar calASDate = Calendar.getInstance();
				
				calASDate.setTime(dateAS);
				
				Date dateAF = sdf.parse("7-01-2019 16:06");
				Calendar calAFDate = Calendar.getInstance();
				calAFDate.setTime(dateAF);
		
				 double newPCVal=0.0;
				 newPCVal=Double.parseDouble("40");	
				 double d = newPCVal / 1000.0D;
				 double wc=d*Double.parseDouble("112");
				  
				 ///new for start date and end date
				 
				/*Date startDate = sdf.parse("29-01-2017 17:10");
				Calendar calStartDate =  Calendar.getInstance();				
				calStartDate.setTime(startDate);
				
				Date finishDate = sdf.parse("30-01-2017 41:11");
				Calendar calfinishDate = Calendar.getInstance();				
				calfinishDate.setTime(finishDate);*/
					
				////
				
				 TaskExecUpdate taskupdate=new TaskExecUpdate();
				 taskupdate.task=(TCComponentScheduleTask) myTask;
				 taskupdate.updateAS =true;
				 taskupdate.newAS =calASDate;
				 taskupdate.updateAF =true;
				 taskupdate.newAF =calAFDate;
				 taskupdate.newStatus="in_progress";//(complete/in_progress)
				 taskupdate.newWC =(int) wc; //Work complete
								
	    		 TaskExecUpdate[] taskupdateArr=new TaskExecUpdate[1];
	     		 taskupdateArr[0]=taskupdate;
	     		 	 
				ServiceData schTaskUpdate = schMngService.updateTaskExecution(taskupdateArr);
	    		 if(schTaskUpdate.sizeOfPartialErrors() == 0)
	    		 {
	    			 System.out.println(" Task is updated " );
	    			 
	    		 }else
	    		 {
	    			 System.out.println(" Task is not updated " );
	    		 }
			}
			
			//Example for schedule update
			/*ObjectUpdateContainer[] obj=new ObjectUpdateContainer[1];
			obj[0]=new ObjectUpdateContainer();
			obj[0].object=mySch;
			
			AttributeUpdateContainer[] attributeObj=new AttributeUpdateContainer[1] ;
			attributeObj[0]=new AttributeUpdateContainer();
			attributeObj[0].attrName="object_desc";
			attributeObj[0].attrValue="My test schedule";
			obj[0].updates=attributeObj;
			com.teamcenter.rac.kernel.ServiceData res=tt.updateSchedules(obj);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public int getSchedule_count() {
		return Schedule_count;
	}
	public void setSchedule_count(int schedule_count) {
		Schedule_count = schedule_count;
	}
	public ModelObject[] getSchedule_object() {
		return Schedule_object;
	}
	public void setSchedule_object(ModelObject[] schedule_object) {
		Schedule_object = schedule_object;
	}
}
