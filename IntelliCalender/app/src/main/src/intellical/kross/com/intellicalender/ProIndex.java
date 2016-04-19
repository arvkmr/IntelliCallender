package intellical.kross.com.intellicalender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProIndex extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proindex);
		TextView weekIndex  = (TextView) findViewById(R.id.tvWeek);
		TextView MonthIndex  = (TextView) findViewById(R.id.tvMonth);
		TextView totalIndex  = (TextView) findViewById(R.id.tvIndex);
		dbHelper ref = new dbHelper(this);
		
		//Calculate weekly index first
		
		// Get all tasks for this week 
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(Calendar.DAY_OF_WEEK, 1);
	    Log.e("ProIndex",  cal.getTime().toString());
	    ArrayList<Row> list = ref.GetAllRows(cal.getTimeInMillis(), System.currentTimeMillis());
	    // calculate % of completed tasks
	    int incomp = 0, comp = 0;
	    for(int i = 0; i < list.size(); i++)
  		{
			Row row = list.get(i);
			if(row.lStatus == Row.TASK_INCOMPLETE)
				incomp++;
			if(row.lStatus == Row.TASK_COMPLETED)
				comp++;
  		}
	    
	    Log.e("ProIndex", incomp+" , "+comp);
	    int Weekindex = 0;
	    
	    if(comp!=0)
	    {

	    	double temp = (double)incomp/comp;
	    	Weekindex = (int) (100 - (temp * 100));
	    }
	    
	    
		// Get all tasks for this Month 
	    cal = Calendar.getInstance(TimeZone.getDefault());
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    Log.e("ProIndex",  cal.getTime().toString());
	    list = ref.GetAllRows(cal.getTimeInMillis(), System.currentTimeMillis());
	    
	    // calculate % of completed tasks
	    incomp = 0;
	    comp = 0;
	    for(int i = 0; i < list.size(); i++)
  		{
			Row row = list.get(i);
			if(row.lStatus == Row.TASK_INCOMPLETE)
				incomp++;
			if(row.lStatus == Row.TASK_COMPLETED)
				comp++;
  		}
	    int Monthindex = 0;
	    if(comp!=0)
	    {
	    	double temp = (double)incomp/comp;
	    	
	    	Monthindex = (int) (100 - (temp * 100));
	    }

	    Log.e("ProIndex", incomp+" , "+comp);
	 // Get all tasks from this year onwards 
	    cal = Calendar.getInstance(TimeZone.getDefault());
	    cal.set(2014, 1, 1);
	    list = ref.GetAllRows(cal.getTimeInMillis(), System.currentTimeMillis());
	    
	    // calculate % of completed tasks
	    incomp = 0;
	    comp = 0;
	    for(int i = 0; i < list.size(); i++)
  		{
			Row row = list.get(i);
			if(row.lStatus == Row.TASK_INCOMPLETE)
				incomp++;
			if(row.lStatus == Row.TASK_COMPLETED)
				comp++;
  		}
	    int tIndex = 0;
	    
	    if(comp!=0)
	    {
			double temp = (double)incomp/comp;
				    	
			tIndex = (int) (100 - (temp * 100));
	    }
	    
	    Log.e("ProIndex", incomp+" , "+comp);
	    weekIndex.setText(GetIndexRating(Weekindex));
	    MonthIndex.setText(GetIndexRating(Monthindex));
	    totalIndex.setText(GetIndexRating(tIndex));
	    
	}

	public String GetIndexRating(int index)
	{
		String temp = "";
		if(index > 80)
		{
			temp = index+" : Good";
		}
		else if (index > 70)
		{
			temp = index+" : Average";
		}
		else if (index > 50)
		{
			temp = index+" : Bad";
		}
		else 
		{
			temp = index+" : Really bad";
		}
		return temp;
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
	}

}
