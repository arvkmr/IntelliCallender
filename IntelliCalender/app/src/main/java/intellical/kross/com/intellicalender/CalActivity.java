package intellical.kross.com.intellicalender;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class CalActivity extends Activity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_activity);
        setTitle("");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        context = this;
        Intent intent = new Intent(this, TaskCreate.class);
        startActivity(intent);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_year, container, false);
            ListView lView =  (ListView) rootView.findViewById(R.id.list00);
            //lView.setDivider(null);
            //lView.setDividerHeight(0);
            dbHelper ref = new dbHelper(getActivity());
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            MyCustomAdapter adapter = new MyCustomAdapter(inflater, ref.fetchRows(cal.getTimeInMillis()));
            lView.setAdapter(adapter);
            return rootView;
        }


        private class MyCustomAdapter extends BaseAdapter {

            ArrayList<Row> list = null;
            private LayoutInflater mInflater;

            public MyCustomAdapter(LayoutInflater Inflater, ArrayList<Row> arrayList)
            {
                list = arrayList;
                mInflater = Inflater;
            }

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
                    holder.textView3= (TextView)convertView.findViewById(R.id.textView3);
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

                float a = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int)((item.eTime - item.sTime)/100000)*2, getResources().getDisplayMetrics());
                //holder.layout1.setMinimumHeight((int) a );
                if(a > 300)
                    a = 300;
                if(a < 120)
                    a = 120;
                ViewGroup.LayoutParams parms = holder.layout1.getLayoutParams();
                parms.height = (int)a;
                Log.i("Val =", "  "+a);
                holder.layout1.invalidate();
                ViewGroup.LayoutParams parms1 = holder.layout2.getLayoutParams();
                parms1.height = (int)a;
                Log.i("Val =", "  "+a);
                holder.layout2.invalidate();
                if(item != null)
                {
                    holder.textView1.setText(item.name);
//                    holder.textView2.setText("02:08 PM");//String.valueOf(item.sTime));
                    holder.textView3.setText(item.desc);//String.valueOf(item.eTime));
                    holder.stvTime.setText(getDate(item.sTime, "hh:mm"));
                    holder.etvTime.setText(getDate(item.eTime, "hh:mm"));
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
    }

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
