package lk.tymspy.scheduleme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;


public class MoveAppointment extends ActionBarActivity implements View.OnClickListener {

    private CalendarView calendar;
    private String selectedDate = null;
    private Appointment appointment;
    private TextView tvSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        Intent g = getIntent();
        appointment = new Appointment();
        appointment = Utility.convertAppointmentStringToModel( g.getStringArrayExtra( Appointment.KEY_APPOINTMENT) );

        initializeComponents();
        initializeCalendar();
    }

    private void initializeComponents() {
        tvSelectedDate = (TextView) findViewById(R.id.tvSelectedDate);

        tvSelectedDate.setText( "Current Date : "+ appointment.getDate());

        View btnMove = findViewById(R.id.btnMoveAppointment);
        btnMove.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMoveAppointment:
                Dialog d = new Dialog(this);
                if ( appointment.getDate() == selectedDate ){
                    d.setTitle("Please choose another date");
                    d.show();
                    break;
                }
                try{
                    DBHelper sql = new DBHelper(this);
                    appointment.setDate(selectedDate);
                    sql.updateAppointment(appointment);
                    sql.close();
                    d.setTitle("Successfully Moved!");
                    d.show();

                    Intent i = new Intent(this, Main.class);
                    startActivity(i);
                    finish();
                } catch ( Exception e ){
                    d.setTitle("Error : " + e);
                    d.show();
                }

                break;
        }
    }


    /**
     * This function initializes the Calendar
     */
    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendarMove);

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

        try {
            calendar.setDate( Utility.convertStringDateToLong( appointment.getDate() ) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                selectedDate = year + "/" + month + "/" + day ;
                tvSelectedDate.setText("Current date : " + appointment.getDate() + "\nMove to : "+ selectedDate );
            }
        });


    }
}
