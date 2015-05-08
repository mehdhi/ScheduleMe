package lk.tymspy.scheduleme;

/**
 * Created by Mahdhi on 4/18/2015.
 */

import java.sql.Date;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Appointment {

    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_APPOINTMENT = "KEY_APPOINTMENT";


    //Items of Appointment
    private String id;
    private String date;
    private String time;
    private String title;
    private String detail;

    //Setter & Getter for Items of Appointment
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}

