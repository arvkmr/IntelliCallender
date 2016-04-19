package intellical.kross.com.intellicalender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Brains extends Service { 


	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
/**
 * Handles action button press in feedback notification and update the status of the task in the db. 	
 */
		Log.e("Service", "Intent");
		int comp = intent.getIntExtra("Complete", -1);
		Log.e("Service", "Intent , complete = "+ comp);
		long id = intent.getLongExtra("id", -1);
		dbHelper ref = new dbHelper(this);
		NotificationManager notificationManager = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel((int) id);
		
		if(comp == 5)
		{
			ref.updateStatus(Row.TASK_COMPLETED, id);
		}
		else if(comp == 4)
		{
			ref.updateStatus(Row.TASK_INCOMPLETE, id);
		}
		return startId;
		
	}
	@Override
	public void onCreate() {

		super.onCreate();

		Log.e("Service", "Started");
		
		Intent intent = new Intent(this, CalActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		final Builder n  = new Notification.Builder(this);
		n.setContentTitle("Start");
		n.setContentText("Something to do.");
		n.setSmallIcon(android.R.drawable.ic_dialog_info);
		n.setContentIntent(pIntent);
		n.setOnlyAlertOnce(true);
		startForeground(011, n.build()); 
		//final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//notificationManager.notify(0, n)
		final dbHelper ref = new dbHelper(this);
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        
    	/*
    	  final ArrayList<Row> list = ref.fetchRows(cal.getTimeInMillis());
    	  for(int i = 0; i < list.size(); i++)
		{
			Log.e("Service", "List.Size = "+ list.size()+ " i = "+i);
			
				Row row = list.get(i);

        ref.updateStatus( Row.TASK_PENDING, row._Id);
		}
		*/
        
        
        /**
         * A forever running thread that update the notification at fixed intervals 
         * Also responsible for feedback
         */
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					
					while(true)
					{
						Log.e("Service", "Loop");
						
						final ArrayList<Row> list = ref.fetchRows(cal.getTimeInMillis());
						boolean currTask = false; 
						for(int i = 0; i < list.size(); i++)
						{
							Log.e("Service", "List.Size = "+ list.size()+ " i = "+i);
							
								Row row = list.get(i);

								Log.e("Service", "row.name ="+ row.name + "row.lStatus = "+ row.lStatus);
								
								if (((row.lStatus == Row.TASK_PENDING) || (row.lStatus == Row.TASK_PENDING)) && (System.currentTimeMillis() > row.eTime))
								{
									
									//ref.updateStatus(Row.TASK_COMPLETED, row._Id);
									feedback(row.name, row._Id);
									Log.e("Service", "Feedback");
								}
								Log.e("Service", "row.sTime ="+ row.sTime + "; row.eTime ="+ row.eTime + " ; current Time = "+ System.currentTimeMillis());
								
								if((row.sTime < System.currentTimeMillis()) && (System.currentTimeMillis() < row.eTime))
								{
									ref.updateStatus(Row.TASK_IN_PROGRESS, row._Id);
									n.setContentTitle("Current task"+ row.name);
									n.setContentText(row.desc);
									startForeground(011, n.build()); 
									Log.e("Service", "There is a Task");
									currTask = true;
								}
								else if (currTask == false)
								{
									n.setContentTitle("Nothing to do");
									n.setContentText("Chill out!");
									startForeground(011, n.build()); 
								}
							
						}
						
						Thread.sleep(50000);
					}
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Sends a feedback Notification with pending intent to itself 
	 * @param name
	 * @param _Id
	 */
	public void feedback(String name, long _Id)

	{
    
		
		Intent intent = new Intent(this, Brains.class);
		intent.putExtra("Complete", 5);
		intent.putExtra("id", _Id);
		PendingIntent pIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Intent intent1 = new Intent(this, Brains.class);
		intent1.putExtra("Complete", 4);
		intent1.putExtra("id", _Id);
		PendingIntent pIntent1 = PendingIntent.getService(this, 2, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

		// build notification
		// the addAction re-use the same intent to keep the example short
		
		Notification n  = new Notification.Builder(this)
		.setContentTitle(name)
		.setContentText("Was this task completed on time?")
		.setSmallIcon(R.drawable.ic_launcher)
		.setAutoCancel(false)
		.addAction(android.R.drawable.presence_online, "Yes", pIntent)
		.addAction(android.R.drawable.presence_busy, "No", pIntent1)
		.build();


		NotificationManager notificationManager = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify((int)_Id, n); 
	}
}