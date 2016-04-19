package intellical.kross.com.intellicalender;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;


public class TaskCreate extends Activity {

    EditText Name;
    EditText loc;
    EditText StartDate;
    EditText startTime;
    EditText endDate;
    EditText desc;
    EditText endTime;
    Calendar StartCal;
    Calendar EndCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        StartCal = Calendar.getInstance(TimeZone.getDefault());
        EndCal = Calendar.getInstance(TimeZone.getDefault());
        Name = (EditText) findViewById(R.id.editText);
        loc = (EditText) findViewById(R.id.loc);
        desc = (EditText) findViewById(R.id.desc);
        StartDate = (EditText) findViewById(R.id.sDate);
        startTime = (EditText) findViewById(R.id.sTime);
        endDate = (EditText) findViewById(R.id.eDate);
        endTime = (EditText) findViewById(R.id.eTime);

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                StartCal.set(selectedYear, selectedMonth, selectedDay);

                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                StartDate.setText(day1 + "-" + month1 + "-" + year1);

            }
        };

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
                EndCal.set(selectedYear, selectedMonth, selectedDay);

                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                endDate.setText(day1 + "-" + month1 + "-" + year1);

            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                        R.style.AppTheme, datePickerListener1,
                        EndCal.get(Calendar.YEAR),
                        EndCal.get(Calendar.MONTH),
                        EndCal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(true);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    DatePickerDialog datePicker = new DatePickerDialog(TaskCreate.this,
                            R.style.AppTheme, datePickerListener1,
                            EndCal.get(Calendar.YEAR),
                            EndCal.get(Calendar.MONTH),
                            EndCal.get(Calendar.DAY_OF_MONTH));
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
        ref.createRow("false", Name.getText().toString(), loc.getText().toString(), desc.getText().toString(),StartCal.getTimeInMillis(), EndCal.getTimeInMillis() );
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
