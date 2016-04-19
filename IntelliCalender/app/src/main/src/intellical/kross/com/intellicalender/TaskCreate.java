package intellical.kross.com.intellicalender;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;


public class TaskCreate extends Activity {

    EditText Name;
    EditText loc;
    EditText StartDate;
    EditText startTime;
    EditText desc;
    EditText endTime;
    Calendar StartCal;
    Calendar EndCal;
    Calendar UntilCal;

    EditText untilDate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        
        StartCal = Calendar.getInstance(TimeZone.getDefault());
        EndCal = Calendar.getInstance(TimeZone.getDefault());
        UntilCal = Calendar.getInstance(TimeZone.getDefault());
        Name = (EditText) findViewById(R.id.editText);
        loc = (EditText) findViewById(R.id.loc);
        desc = (EditText) findViewById(R.id.desc);
        StartDate = (EditText) findViewById(R.id.sDate);
        startTime = (EditText) findViewById(R.id.sTime);
        
        endTime = (EditText) findViewById(R.id.eTime);

        
        untilDate = (EditText) findViewById(R.id.eDate);
        
        // Set date and time picker listeners 
        
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                StartCal.set(selectedYear, selectedMonth, selectedDay);
                EndCal.set(selectedYear, selectedMonth, selectedDay);
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                StartDate.setText(day1 + "-" + month1 + "-" + year1);

            }
        };

        
        // Set focus change and On click listener to capture all changes. 
        
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                        R.style.AppTheme, datePickerListener,
                        StartCal.get(Calendar.YEAR),
                        StartCal.get(Calendar.MONTH),
                        StartCal.get(Calendar.DAY_OF_MONTH));
                
                datePicker.setCancelable(true);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });
        StartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                            R.style.AppTheme, datePickerListener,
                            StartCal.get(Calendar.YEAR),
                            StartCal.get(Calendar.MONTH),
                            StartCal.get(Calendar.DAY_OF_MONTH));
                    datePicker.setCancelable(true);
                    datePicker.setTitle("Select the date");
                    datePicker.show();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                UntilCal.set(selectedYear, selectedMonth, selectedDay);

                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                untilDate.setText(day1 + "-" + month1 + "-" + year1);

            }
        };
        untilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                        R.style.AppTheme, datePickerListener1,
                        UntilCal.get(Calendar.YEAR),
                        UntilCal.get(Calendar.MONTH),
                        UntilCal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(true);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });
        untilDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                            R.style.AppTheme, datePickerListener1,
                            UntilCal.get(Calendar.YEAR),
                            UntilCal.get(Calendar.MONTH),
                            UntilCal.get(Calendar.DAY_OF_MONTH));
                    datePicker.setCancelable(true);
                    datePicker.setTitle("Select the date");
                    datePicker.show();
                }
            }
        });

        final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

            // when dialog box is closed, below method will be called.
            public void onTimeSet(TimePicker view, int a, int b) {
                StartCal.set(Calendar.HOUR_OF_DAY, a);
                StartCal.set(Calendar.MINUTE, b);

                String minute = String.valueOf(b);
                String hour = String.valueOf(a);
                startTime.setText( hour + ": " + minute);

            }
        };
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(TaskCreate.this,
                        R.style.AppTheme, timePickerListener,
                        StartCal.get(Calendar.HOUR_OF_DAY),
                        StartCal.get(Calendar.MINUTE),
                        true);
                timePicker.setCancelable(true);
                timePicker.setTitle("Select the Time");
                timePicker.show();
            }
        });
        startTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    TimePickerDialog timePicker = new TimePickerDialog(TaskCreate.this,
                            R.style.AppTheme, timePickerListener,
                            StartCal.get(Calendar.HOUR_OF_DAY),
                            StartCal.get(Calendar.MINUTE),
                            true);
                    timePicker.setCancelable(true);
                    timePicker.setTitle("Select the Time");
                    timePicker.show();
                }
            }
        });

        final TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {

            // when dialog box is closed, below method will be called.
            public void onTimeSet(TimePicker view, int a, int b) {
                EndCal.set(Calendar.HOUR_OF_DAY, a);
                EndCal.set(Calendar.MINUTE, b);

                String minute = String.valueOf(b);
                String hour = String.valueOf(a);

                endTime.setText( hour + ": " + minute);

            }
        };
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(TaskCreate.this,
                        R.style.AppTheme, timePickerListener1,
                        EndCal.get(Calendar.HOUR_OF_DAY),
                        EndCal.get(Calendar.MINUTE),
                        true);
                timePicker.setCancelable(true);
                timePicker.setTitle("Select the date");
                timePicker.show();
            }
        });
        endTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    TimePickerDialog timePicker = new TimePickerDialog(TaskCreate.this,
                            R.style.AppTheme, timePickerListener1,
                            EndCal.get(Calendar.HOUR_OF_DAY),
                            EndCal.get(Calendar.MINUTE),
                            true);
                    timePicker.setCancelable(true);
                    timePicker.setTitle("Select the date");
                    timePicker.show();
                }
            }
        });

    }

    public void Add(View view)
    {

        dbHelper ref = new dbHelper(TaskCreate.this);
        CheckBox repeat = (CheckBox) findViewById(R.id.checkBox1);
    	
        Log.e("Create", "1");
        if(!repeat.isChecked())   // Single task
        {
        	Log.e("Create", "2");
        	ref.createRow("true", Name.getText().toString(), loc.getText().toString(), desc.getText().toString(), StartCal.getTimeInMillis(), EndCal.getTimeInMillis() );
        }
        else  // Repeating task
        {
        	
        	ToggleButton sun = (ToggleButton) findViewById(R.id.toggleButton1);
        	ToggleButton mon = (ToggleButton) findViewById(R.id.toggleButton2);
        	ToggleButton tue = (ToggleButton) findViewById(R.id.toggleButton3);
        	ToggleButton wed = (ToggleButton) findViewById(R.id.toggleButton4);
        	ToggleButton thu = (ToggleButton) findViewById(R.id.toggleButton5);
        	ToggleButton fri = (ToggleButton) findViewById(R.id.toggleButton6);
        	ToggleButton sat = (ToggleButton) findViewById(R.id.toggleButton7);
        	
        	boolean a[] = new boolean[7];;
        	for(int i =0; i < 7; i++)
        	{
        		a[i] = false;
        	}
        		if(sun.isChecked())
        			a[0] = true;
        		if(mon.isChecked())
        			a[1] = true;
        		if(tue.isChecked())
        			a[2] = true;
        		if(wed.isChecked())
        			a[3] = true;
        		if(thu.isChecked())
        			a[4] = true;
        		if(fri.isChecked())
        			a[5] = true;
        		if(sat.isChecked())
        			a[6] = true;
        		
        		
        	Calendar tempCal = (Calendar)StartCal.clone() ;
     
        	long time =  EndCal.getTimeInMillis() - StartCal.getTimeInMillis() ;
        	//tempCal.set(Calendar.DAY_OF_WEEK, 1);
        	boolean loop = true;
        	Log.e("Create", "loop");
        	
        	/**
        	 * Loops till the end date is greater than the temp date
        	 */
        	
        	while(loop)
        	{
        		//Log.e("Create", "loop");
        		
    			for(int i = 0; i < 7; i++)
    	    	{
    				if(a[i] == true)
					{
    					ref.createRow("false", Name.getText().toString(), loc.getText().toString(), desc.getText().toString(), tempCal.getTimeInMillis(), tempCal.getTimeInMillis() + time );
					}
    				
    				Log.e("Time 1", "" + tempCal.getTimeInMillis());
    				tempCal.add(Calendar.DAY_OF_YEAR, 1);
    				Log.e("Time 2", "" + tempCal.getTimeInMillis()); 
    				Log.e("Time Until", "" + UntilCal.getTimeInMillis());
    				
            		if(tempCal.getTimeInMillis() > UntilCal.getTimeInMillis())
            		{
            			Log.e("Create", "break");
            			loop = false;
            			break;
            		}
    			}
    			
        	}
        }
        ref.close();
        finish();
    }

    public void Cancel(View view)
    {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_create, menu);
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
}
