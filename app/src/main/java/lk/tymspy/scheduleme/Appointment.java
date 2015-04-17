package lk.tymspy.scheduleme;

/**
 * Created by Mahdhi on 4/18/2015.
 */

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Appointment {

    //Items of Appointment
    private String date;
    private String time;
    private String title;
    private String detail;

    //Setter & Getter for Items of Appointment
    public void setDate(String date)
    {
        this.date = date;
        DateFormat formatter = new SimpleDateFormat("YYYYMMDDThhmm");
        Date fileDate = null;

        try
        {
            fileDate = (Date)formatter.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        this.date = fileDate.toString();
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

