package com.qfree.obo.report.rest.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import com.qfree.obo.report.domain.Configuration.ParamName;
import com.qfree.obo.report.rest.server.RestUtils.RestApiVersion;
import com.qfree.obo.report.scheduling.AnotherBean;
import com.qfree.obo.report.scheduling.MyBean;
import com.qfree.obo.report.scheduling.ScheduledJob;
import com.qfree.obo.report.service.BirtService;
import com.qfree.obo.report.service.ConfigurationService;

@Component
@Path("/test")
public class TestController extends AbstractBaseController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	private final ConfigurationService configurationService;
	private final BirtService birtService;

	private final SchedulerFactoryBean schedulerFactoryBean;
	private final MyBean myBean;
	private final AnotherBean anotherBean;

	@Autowired
	public TestController(
			ConfigurationService configurationService,
			BirtService birtService,
			SchedulerFactoryBean schedulerFactoryBean,
			MyBean myBean,
			AnotherBean anotherBean) {
		this.configurationService = configurationService;
		this.birtService = birtService;
		this.schedulerFactoryBean = schedulerFactoryBean;
		this.myBean = myBean;
		this.anotherBean = anotherBean;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test(@HeaderParam("Accept") String acceptHeader) {
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		switch (apiVersion) {
		case v1:
			/*
			 * Code for API v1:
			 */
			break;
		default:
			/*
			 * Code for default API version as well as unrecognized version from 
			 * "Accept" header:
			 */
		}
		return "/test endpoint: API version " + apiVersion.getVersion();
	}

	/*
	 * This endpoint can be tested with:
	 * 
	 *   $ mvn clean spring-boot:run
	 *   $ curl -i -H "Accept: text/plain;v=1" -X GET http://localhost:8080/rest/test/api_version
	 * 
	 * @Transactional is used to avoid org.hibernate.LazyInitializationException
	 * being thrown when evaluating ...
	 */
	/**
	 * ReST endpoint that can be used to confirm that the API version is being
	 * specified correctly by a client.
	 * 
	 * The response entity is the version number specified in the request.
	 *  
	 * @param acceptHeader
	 * @return
	 */
	@GET
	@Path("/api_version")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptHeaderApiVersionGet(@HeaderParam("Accept") String acceptHeader) {
		//		logger.info("acceptHeader = {}", acceptHeader);
		//		System.out.println("acceptHeaderApiVersionGet: acceptHeader = " + acceptHeader);
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		//		logger.info("apiVersion, apiVersion.getVersion() = {}, {}", apiVersion, apiVersion.getVersion());
		//		System.out.println("acceptHeaderApiVersionGet: apiVersion, apiVersion.getVersion() = "
		//				+ apiVersion + ", " + apiVersion.getVersion());
		return apiVersion.getVersion();
	}

	/*
	 * This endpoint can be tested with:
	 * 
	 *   $ mvn clean spring-boot:run
	 *   $ curl -i -H "Accept: text/plain;v=1" -X POST http://localhost:8080/rest/test/api_version
	 */
	@POST
	@Path("/api_version")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptHeaderApiVersionPost(@HeaderParam("Accept") String acceptHeader) {
		//		logger.info("acceptHeader = {}", acceptHeader);
		//		System.out.println("acceptHeaderApiVersionPost: acceptHeader = " + acceptHeader);
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v3);
		//		logger.info("apiVersion, apiVersion.getVersion() = {}, {}", apiVersion, apiVersion.getVersion());
		//		System.out.println("acceptHeaderApiVersionPost: apiVersion, apiVersion.getVersion() = "
		//				+ apiVersion + ", " + apiVersion.getVersion());
		return apiVersion.getVersion();
	}

	/*
	 * This endpoint can be tested with:
	 * 
	 *   $ mvn clean spring-boot:run
	 *   $ curl -i -H "Accept: text/plain;v=1" -X POST http://localhost:8080/rest/test/api_version
	 */
	@PUT
	@Path("/api_version")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptHeaderApiVersionPut(@HeaderParam("Accept") String acceptHeader) {
		//		logger.info("acceptHeader = {}", acceptHeader);
		//		System.out.println("acceptHeaderApiVersionPost: acceptHeader = " + acceptHeader);
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v4);
		//		logger.info("apiVersion, apiVersion.getVersion() = {}, {}", apiVersion, apiVersion.getVersion());
		//		System.out.println("acceptHeaderApiVersionPost: apiVersion, apiVersion.getVersion() = "
		//				+ apiVersion + ", " + apiVersion.getVersion());
		return apiVersion.getVersion();
	}

	@POST
	@Path("/form")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String formPostProduceText(
			@HeaderParam("Accept") String acceptHeader,
			@FormParam("param1") String param1,
			@FormParam("param2") String param2) {
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		return "(" + param1 + ", " + param2 + "): " + apiVersion;
	}

	@GET
	@Path("/string_param_default")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTestStringParamDefault(@HeaderParam("Accept") String acceptHeader) {
		//		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		//		Object stringValueDefaultObject = configurationService.get(ParamName.TEST_STRING);
		//		String stringParam = null;
		//		if (stringValueDefaultObject instanceof String) {
		//			stringParam = (String) stringValueDefaultObject;
		//		}
		//		return stringParam;
		return configurationService.get(ParamName.TEST_STRING, null, String.class);
	}

	@GET
	@Path("/string_param_default")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTestStringParamDefaultAsJson(@HeaderParam("Accept") String acceptHeader) {
		//		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		String stringValue = configurationService.get(ParamName.TEST_STRING, null, String.class);


		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


		return configurationService.get(ParamName.TEST_STRING, null, String.class);
	}

	@POST
	@Path("/string_param_default")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String postTestStringParamDefault(
			@HeaderParam("Accept") String acceptHeader,
			@FormParam("paramValue") String newParamValue) {
		//		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		/*
		 * Update parameter's default value.
		 */
		configurationService.set(ParamName.TEST_STRING, newParamValue);
		/*
		 * Return updated value.
		 */
		return configurationService.get(ParamName.TEST_STRING, null, String.class);
	}

	@PUT
	@Path("/string_param_default")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String putTestStringParamDefault(
			@HeaderParam("Accept") String acceptHeader,
			String newParamValue) {
		//		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v2);
		/*
		 * Update parameter's default value.
		 */
		configurationService.set(ParamName.TEST_STRING, newParamValue);
		/*
		 * Return updated value.
		 */
		return configurationService.get(ParamName.TEST_STRING, null, String.class);
	}

	//TODO USE @PUT TO RETURN A JSON object?????????????????

	//TODO USE @PUT TO accept a JSON object, e.g., a new Configuration and then later a new Role?
	//		Insert into DB and then RETURN A JSON object?????????????????

	@GET
	@Path("/parse_report_params")
	@Produces(MediaType.TEXT_PLAIN)
	public String parseReportParamsTest(
			@HeaderParam("Accept") final String acceptHeader,
			@Context final UriInfo uriInfo) {
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v1);

		try {

			/*
			 * Load rptdesign file into a String.
			 */
			//java.nio.file.Path rptdesignPath = Paths
			//		.get("/home/jeffreyz/git/obo-birt-reports/birt-reports/tests/400-TestReport04_v1.1.rptdesign");
			java.nio.file.Path rptdesignPath = Paths.get("/home/jeffreyz/Desktop/cascade_v3.2.23.rptdesign");
			//java.nio.file.Path rptdesignPath = Paths.get("/home/jeffreyz/Desktop/cascade_v3.2.6.rptdesign");
			List<String> rptdesignLines = null;
			try {
				rptdesignLines = Files.readAllLines(rptdesignPath);// assumes UTF-8 encoding
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String rptdesignXml = String.join("\n", rptdesignLines);
			//logger.info("rptdesignXml = \n{}", rptdesignXml);

			//ReportUtils.parseReportParams(rptdesignXml);
			birtService.parseReportParams(rptdesignXml);

		} catch (Exception e) {
			logger.error("Parsing the report parameters failed with the following exception:", e);
		}

		return "Please work!!!";
	}

	@GET
	@Path("/scheduleTask")
	@Produces(MediaType.TEXT_PLAIN)
	public String scheduleTask(
			@HeaderParam("Accept") final String acceptHeader,
			@Context final UriInfo uriInfo) {
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v1);

		/*
		 * Get the underlying Quartz Scheduler. According to the Javadoc for
		 * org.springframework.scheduling.quartz.SchedulerFactoryBean :
		 * 
		 *   For dynamic registration of jobs at runtime, use a bean reference 
		 *   to this SchedulerFactoryBean to get direct access to the Quartz 
		 *   Scheduler (org.quartz.Scheduler). This allows you to create new 
		 *   jobs and triggers, and also to control and monitor the entire 
		 *   Scheduler.
		 * 
		 * So it seems that in order to scedule jobs dynamically (which is what
		 * we are doing here), one *must* go use the Quartz Scheduler object
		 * obtained here.
		 */
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		String jobGroupName = "ReportSubscriptions";
		String jobName = "SubscriptionUUID";

		MethodInvokingJobDetailFactoryBean methodInvokingJobDetail = new MethodInvokingJobDetailFactoryBean();
		methodInvokingJobDetail.setTargetObject(myBean);
		methodInvokingJobDetail.setTargetMethod("printMessage");
		methodInvokingJobDetail.setGroup(jobGroupName);
		methodInvokingJobDetail.setName(jobName);
		methodInvokingJobDetail.setConcurrent(false);
		try {
			methodInvokingJobDetail.afterPropertiesSet();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		/*
		 * Create trigger for methodInvokingJobDetail.
		 */
		SimpleTriggerFactoryBean simpleTrigger = new SimpleTriggerFactoryBean();
		simpleTrigger.setJobDetail(methodInvokingJobDetail.getObject());
		simpleTrigger.setName("SomeTriggerNameChosenByJeff");
		simpleTrigger.setStartDelay(1000L);
		simpleTrigger.setRepeatCount(50);
		simpleTrigger.setRepeatInterval(2L * 1000L); // ms
		simpleTrigger.afterPropertiesSet();


		String complexJobGroupName = "ReportSubscriptions";
		String complexJobName = "complexSubscriptionUUID";

		UUID subscriptionUuid = UUID.randomUUID();

		Map<String, Object> jobDataAsMap = new HashMap<>();
		jobDataAsMap.put("anotherBean", anotherBean);
		jobDataAsMap.put("subscriptionUuid", subscriptionUuid);

		JobDetailFactoryBean complexJobDetail = new JobDetailFactoryBean();
		complexJobDetail.setJobClass(ScheduledJob.class);
		complexJobDetail.setJobDataAsMap(jobDataAsMap);
		complexJobDetail.setDurability(true); // ????????????????????????????????????? TRY REMOVING OR SET TO false???????????????????????????????????
		complexJobDetail.setGroup(complexJobGroupName);
		complexJobDetail.setName(complexJobName);
		//		try {
		complexJobDetail.afterPropertiesSet();
		//		} catch (ClassNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (NoSuchMethodException e) {
		//			e.printStackTrace();
		//		}

		/*
		 * Create trigger for methodInvokingJobDetail.
		 */
		CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
		cronTrigger.setJobDetail(complexJobDetail.getObject());
		cronTrigger.setName("cronTriggerCreatedByJeff");
		cronTrigger.setStartDelay(1000L);
		cronTrigger.setCronExpression("0/5 * * ? * MON-FRI"); // Run the job every 5 seconds only on weekdays
		try {
			cronTrigger.afterPropertiesSet();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			scheduler.scheduleJob(methodInvokingJobDetail.getObject(), simpleTrigger.getObject());
			scheduler.scheduleJob(complexJobDetail.getObject(), cronTrigger.getObject());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		try {
			logger.info("scheduler.getTriggerGroupNames() = {}", scheduler.getTriggerGroupNames());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		JobKey myJobKey = JobKey.jobKey(jobName, jobGroupName);
		logger.info("myJobKey = {}", myJobKey);

		try {
			Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.anyGroup());
			logger.debug("scheduler.getJobKeys(GroupMatcher.anyGroup()) = {}", jobKeySet);
			JobKey[] jobKeys = jobKeySet.toArray(new JobKey[] {});
			logger.info("{} JobKeys:", jobKeys.length);
			for (JobKey jobKey : jobKeys) {
				logger.info("    {}", jobKey);
			}
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}

		try {
			logger.info("scheduler.checkExists(myJobKey)) = {}", scheduler.checkExists(myJobKey));
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}

		logger.info("schedulerFactoryBean.isRunning() = {}", schedulerFactoryBean.isRunning());

		try {
			logger.info("scheduler.isStarted() = {}", scheduler.isStarted());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		return "Return something here after scheduling the task?";
	}

	@GET
	@Path("/unscheduleTask")
	@Produces(MediaType.TEXT_PLAIN)
	public String unscheduleTask(
			@HeaderParam("Accept") final String acceptHeader,
			@Context final UriInfo uriInfo) {
		RestApiVersion apiVersion = RestUtils.extractAPIVersion(acceptHeader, RestApiVersion.v1);

		logger.info("schedulerFactoryBean.stop()");
		schedulerFactoryBean.stop();

		/*
		 * This will start the scheduler again, so "stop()" is really like "pause()".
		 */
		//		logger.info("schedulerFactoryBean.start()");
		//		schedulerFactoryBean.start();

		return "Return something here after unscheduling the task";
	}

}
