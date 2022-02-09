import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyApp {

	MyApp(int userID){
		try {
			JobDetail job1 = JobBuilder.newJob(Job1.class)
					.withIdentity("job1", "group1").build();

			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
					.build();
			
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.getContext().put("userID", userID);
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);

			
			Thread.sleep(100000);
			
			scheduler1.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}