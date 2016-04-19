package intellical.kross.com.intellicalender;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskDetails extends Activity  {

	protected Calendar StartCal = null;
	protected Calendar EndCal = null;
	Row row;
	EditText name;
	EditText decs;
	EditText loc;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.fragment_task_details);
	
	StartCal = Calendar.getInstance(TimeZone.getDefault());
	EndCal = Calendar.getInstance(TimeZone.getDefault());
	
	name =  (EditText) findViewById(R.id.taskinfoname);
	decs =  (EditText) findViewById(R.id.taskinfodesc);
	loc =  (EditText) findViewById(R.id.taskinfoloc);
	final EditText date = (EditText) findViewById(R.id.detailsdate);
	final EditText etime =  (EditText) findViewById(R.id.taskinfoetime);
	final EditText stime =  (EditText) findViewById(R.id.taskinfostime);
	final TextView title =  (TextView) findViewById(R.id.NameDetailsView);
	Intent c = getIntent(); 
    
	row = new Row();
	row._Id = c.getLongExtra(Row.KEY_ROWID, 0);
    //row.mReschedule = c.getString(1).contains("true");
    row.name = c.getStringExtra(Row.KEY_NAME);
    row.desc = c.getStringExtra(Row.KEY_DESC);
    row.loc = c.getStringExtra(Row.KEY_LOC);
    
    row.sTime = c.getLongExtra(Row.KEY_START_TIME, 0);
    row.eTime = c.getLongExtra(Row.KEY_END_TIME, 0);
	
    title.setText(row.name);
    name.setText(row.name);
    decs.setText(row.desc);
    loc.setText(row.loc);
    StartCal.setTimeInMillis(row.sTime);
    EndCal.setTimeInMillis(row.eTime);
	 final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

         // when dialog box is closed, below method will be called.
         public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
             StartCal.set(selectedYear, selectedMonth, selectedDay);
             date.setText(CalActivity.getDate(StartCal.getTimeInMillis(), "dd MMM yyyy"));

         }
     };

     date.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             DatePickerDialog datePicker = new DatePickerDialog(TaskDetails.this,
                     R.style.AppTheme, datePickerListener,
                     StartCal.get(Calendar.YEAR),
                     StartCal.get(Calendar.MONTH),
                     StartCal.get(Calendar.DAY_OF_MONTH));
             
             datePicker.setCancelable(true);
             datePicker.setTitle("Select the date");
             datePicker.show();
         }
     });
     date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             if (hasFocus) {

                 DatePickerDialog datePicker = new DatePickerDialog(TaskDetails.this,
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
     
     final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

         // when dialog box is closed, below method will be called.
         public void onTimeSet(TimePicker view, int a, int b) {
             StartCal.set(Calendar.HOUR_OF_DAY, a);
             StartCal.set(Calendar.MINUTE, b);

             String minute = String.valueOf(b);
             String hour = String.valueOf(a);
             stime.setText( hour + ": " + minute);

         }
     };
     stime.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             TimePickerDialog timePicker = new TimePickerDialog(TaskDetails.this,
                     R.style.AppTheme, timePickerListener,
                     StartCal.get(Calendar.HOUR_OF_DAY),
                     StartCal.get(Calendar.MINUTE),
                     true);
             timePicker.setCancelable(true);
             timePicker.setTitle("Select the Time");
             timePicker.show();
         }
     });
     stime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             if(hasFocus)
             {

                 TimePickerDialog timePicker = new TimePickerDialog(TaskDetails.this,
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

             etime.setText(CalActivity.getDate(EndCal.getTimeInMillis(), "HH:mm")); //hour + ": " + minute);

         }
     };
     etime.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             TimePickerDialog timePicker = new TimePickerDialog(TaskDetails.this,
                     R.style.AppTheme, timePickerListener1,
                     EndCal.get(Calendar.HOUR_OF_DAY),
                     EndCal.get(Calendar.MINUTE),
                     true);
             timePicker.setCancelable(true);
             timePicker.setTitle("Select the date");
             timePicker.show();
         }
     });
     etime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             if(hasFocus)
             {

                 TimePickerDialog timePicker = new TimePickerDialog(TaskDetails.this,
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
     
	
    etime.setText(CalActivity.getDate(row.eTime, "HH:mm"));
    stime.setText(CalActivity.getDate(row.sTime, "HH:mm"));
	 }
	 
	 public void cancelTask(View view)
	 {
		 dbHelper ref = new dbHelper(this);
		 ref.deleteRow(row._Id);
		 finish();
		 Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_LONG).show();
	 }
	 
	 public void backTask(View view)
	 {
		 finish();
	 }
	 
	 public void editTask(View view)
	 {
		 dbHelper ref = new dbHelper(TaskDetails.this);
		 ref.updateRow(row._Id, "false" , name.getText().toString(), loc.getText().toString(), decs.getText().toString(), StartCal.getTimeInMillis(), EndCal.getTimeInMillis() );
		 ref.close();
		 Toast.makeText(getApplicationContext(), "Details Saved", Toast.LENGTH_LONG).show();
		 finish();
	 }
}
