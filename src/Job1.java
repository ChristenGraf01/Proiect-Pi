
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;


public class Job1 implements Job{ 

	public void execute(JobExecutionContext context) throws JobExecutionException {
        int userID = -1;
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            userID = (int) schedulerContext.get("userID");
            System.out.println(userID);
          } catch (SchedulerException e1) {
            e1.printStackTrace();
          }
        DbService database = new DbService(userID);
        String email;
        email = database.getEmailAddress();
        if (email == null) {
            System.out.println("Couldn't find email address.\n");
        }
        else{
            System.out.println(email);
            System.out.println("Job1 --->>> Time is " + new Date());
            EmailUtil.sendEmail(email, "Monthly reminder", "This is a reminder for you to pay the remaining rates.\n");
        }
    } 
}