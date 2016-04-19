package intellical.kross.com.intellicalender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CalActivity extends Activity {

	Calendar cal = null;
	TextView date = null;
	MyCustomAdapter adapter = null;
	dbHelper ref = null; 
	ListView lView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_list_year);
        
        lView =  (ListView) findViewById(R.id.list00);
        date = (TextView) findViewById(R.id.ListDateView);
        
        
        ref = new dbHelper(this);
        
        // Set todays day to the top textview 
        cal = Calendar.getInstance(TimeZone.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date.setText(getDate(cal.getTimeInMillis(), "dd MMM yyyy"));
        
        
        adapter = new MyCustomAdapter(LayoutInflater.from(this), ref.fetchRows(cal.getTimeInMillis()));
        
        lView.setAdapter(adapter);
        
        // OnClick listener that calls task details activity on click 
        
        lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Row row = adapter.getItem(arg2);
				Intent intent = new Intent(CalActivity.this, TaskDetails.class);
				
				intent.putExtra(Row.KEY_ROWID, row._Id);
				intent.putExtra(Row.KEY_NAME, row.name);
				intent.putExtra(Row.KEY_DESC, row.desc);
			    intent.putExtra(Row.KEY_LOC, row.loc);
			    intent.putExtra(Row.KEY_START_TIME, row.sTime);
			    intent.putExtra(Row.KEY_END_TIME, row.eTime);
			    
				startActivity(intent);
			}
		});
       
        // Start the service 
        
        Intent sIntent = new Intent(this, Brains.class);
        startService(sIntent);
        
        
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
        else if(id == R.id.action_add)
        {
        	// Add task
        	Intent intent = new Intent(this, TaskCreate.class);
            startActivity(intent);
        	return true;
        }
        else if(id == R.id.action_proindex)
        {
        	//View Index
        	Intent intent = new Intent(this, ProIndex.class);
            startActivity(intent);
        	return true;
        }
        
    
        return super.onOptionsItemSelected(item);
    }
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Refresh adapter
		adapter = new MyCustomAdapter(LayoutInflater.from(this), ref.fetchRows(cal.getTimeInMillis()));
        lView.setAdapter(adapter);
	}

    public void Previous(View view)
    {
    	// Go to previous day
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.getTime();
        date.setText(getDate(cal.getTimeInMillis(), "dd MMM yyyy"));
        adapter = new MyCustomAdapter(LayoutInflater.from(this), ref.fetchRows(cal.getTimeInMillis()));
        lView.setAdapter(adapter);
    }
    
    public void Next(View view)
    {
    	// Move to next day refresh adapter 
    	
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
        date.setText(getDate(cal.getTimeInMillis(), "dd MMM yyyy"));
        adapter = new MyCustomAdapter(LayoutInflater.from(this), ref.fetchRows(cal.getTimeInMillis()));
        lView.setAdapter(adapter);
    }
    
/**
 * A custom adapter that controls the list view's elements
 * @author Arv
 *
 */
    private class MyCustomAdapter extends BaseAdapter {

        ArrayList<Row> list = null;
        private LayoutInflater mInflater;

        public MyCustomAdapter(LayoutInflater Inflater, ArrayList<Row> arrayList)
        {
            list = arrayList;
            mInflater = Inflater;
        }

        @SuppressWarnings("unused")
		public void clear() {
            list.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Row getItem(int position) {
            Row item = null;
            try {
                item = list.get(position);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("unused")
		public void add(Row entry) {
            list.add(entry);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_layout, null);
                holder = new ViewHolder();
                holder.textView1= (TextView)convertView.findViewById(R.id.taskName);
                //holder.textView2= (TextView)convertView.findViewById(R.id.textView2);
                holder.textView3= (TextView)convertView.findViewById(R.id.tvWeek);
                holder.layout1= (RelativeLayout)convertView.findViewById(R.id.sRLTime);
                holder.layout2= (RelativeLayout)convertView.findViewById(R.id.eRLTime);
                holder.stvTime= (TextView)convertView.findViewById(R.id.stvTime);
                holder.etvTime= (TextView)convertView.findViewById(R.id.etvTime);
                convertView.setTag(holder);

            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }

            Row item = getItem(position);

            /**
             * Change Cell height. 
             */
            
            
            float a = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int)((item.eTime - item.sTime)/100000)*2, getResources().getDisplayMetrics());
            //holder.layout1.setMinimumHeight((int) a );
            if(a > 350)
                a = 350;
            if(a < 120)
                a = 120;
            ViewGroup.LayoutParams parms = holder.layout1.getLayoutParams();
            parms.height = (int)a;
            //Log.i("Val =", "  "+a);
            holder.layout1.invalidate();
            ViewGroup.LayoutParams parms1 = holder.layout2.getLayoutParams();
            parms1.height = (int)a;
            //Log.i("Val =", "  "+a);
            holder.layout2.invalidate();
            
            /**
             * Set params
             */
            
            if(item != null)
            {
                holder.textView1.setText(item.name);
//              holder.textView2.setText("02:08 PM");//String.valueOf(item.sTime));
                holder.textView3.setText(item.desc);//String.valueOf(item.eTime));
                holder.stvTime.setText(getDate(item.sTime, "HH:mm"));
                holder.etvTime.setText(getDate(item.eTime, "HH:mm"));
            }
            return convertView;
        }

    }
    public static class ViewHolder
    {
        public RelativeLayout layout1;
        public RelativeLayout layout2;
        public TextView textView1;
        public TextView textView3;
        public TextView stvTime;
        public TextView etvTime;

    }
    
    /**
     * A function to get a nicely formated date from unix time stamp and date pattern
     * @param milliSeconds
     * @param format
     * @return
     */
    
    public static String getDate(long milliSeconds, String format)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
