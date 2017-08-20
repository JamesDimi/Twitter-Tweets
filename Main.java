import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import twitter4j.*;

public class Main {
	
	public static void main(String [] args) throws InterruptedException{
		
		// Set a variable that has the date of 7days past(yyy-mm-dd)
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		String search_7_days = dateFormat.format(cal.getTime());

		Twitter twitter = TwitterFactory.getSingleton();
	    Query query = new Query("#Trump").count(100).since(search_7_days);
	    QueryResult result;
	    int counter = 0;
	    // Gia kathe status ksanarxizei apo current_time kai katevainei...
	    while(true)
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
		        System.out.println("["+status.getCreatedAt()+"]"+"@" + status.getUser().getScreenName() + "\n"+ status.getText()+"\n");
		        TimeUnit.SECONDS.sleep(1);
			}
			
	        //counter++;
	        
		} catch (TwitterException e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}

	}
}
