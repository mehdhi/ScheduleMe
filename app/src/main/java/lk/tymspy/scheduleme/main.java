package lk.tymspy.scheduleme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Main extends ActionBarActivity implements View.OnClickListener {

    CalendarView calendar;
    private String selectedDate = null;
    private Button btnCreate, btnViewEdit, btnMove, btnDelete, btnSearch, btnTranslate, btnExit, btnAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.activity_main);

        //initializes the components
        initializeCalendar();
        initializeButtons();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                selectedDate = year + "/" + month + "/" + day ;

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCreateAppointment:
                createAppointment();
                break;
            case R.id.btnViewEdit:
                viewEditAppointment();
                break;
            case R.id.btnMove:
                moveAppointment();
                break;
            case R.id.btnDelete:
                deleteAppointment();
                break;
            case R.id.btnSearch:
                searchAppointment();
                break;
            case R.id.btnTranslate:
                translate();
                break;

        }

    }

    private void initializeButtons() {

        btnCreate = (Button) findViewById(R.id.btnCreateAppointment );
        btnCreate.setOnClickListener(this);

        btnViewEdit = (Button) findViewById(R.id.btnViewEdit);
        btnViewEdit.setOnClickListener(this);

        btnMove = (Button) findViewById(R.id.btnMove);
        btnMove.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnTranslate = (Button) findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(this);

    }

    private void createAppointment(){
        Intent intent = new Intent(this, AddAppointment.class);
        if ( selectedDate == null ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            selectedDate = df.format(c.getTime());
        }
        intent.putExtra(Appointment.KEY_DATE, selectedDate);
        startActivity(intent);

    }

    private void viewEditAppointment(){
        Intent intent = new Intent(this, Viewing.class);
        if ( selectedDate == null ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            selectedDate = df.format(c.getTime());
        }
        intent.putExtra(Appointment.KEY_DATE, selectedDate);
        intent.putExtra(Viewing.MODE, 1);
        startActivity(intent);
    }

    private void moveAppointment(){
        Intent intent = new Intent(this, Viewing.class);
        if ( selectedDate == null ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            selectedDate = df.format(c.getTime());
        }
        intent.putExtra(Appointment.KEY_DATE, selectedDate);
        intent.putExtra(Viewing.MODE, 2);
        startActivity(intent);
    }

    private void deleteAppointment(){
        Intent intent = new Intent(this, Viewing.class);
        if ( selectedDate == null ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            selectedDate = df.format(c.getTime());
        }
        intent.putExtra(Appointment.KEY_DATE, selectedDate);
        intent.putExtra(Viewing.MODE, 3);
        startActivity(intent);
    }

    private void searchAppointment(){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    private void translate(){
        Intent intent = new Intent(this, Translate.class);
        if ( selectedDate == null ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            selectedDate = df.format(c.getTime());
        }
        intent.putExtra(Appointment.KEY_DATE, selectedDate);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}