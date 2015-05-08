package lk.tymspy.scheduleme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mahdhi on 4/18/2015.
 */
public class Utility {

    public static Appointment convertAppointmentStringToModel (String[] appointment ){
        Appointment app = new Appointment();
        app.setId(appointment[0]);
        app.setDate(appointment[1]);
        app.setTime(appointment[2]);
        app.setTitle(appointment[3]);
        app.setDetail(appointment[4]);
        return app;
    }

    public static String[] convertAppointmentModelToString (Appointment appointment ){
        return new String[] {
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getTitle(),
                appointment.getDetail()
        };
    }

    /**
     * This function will convert String date(yyyy/MM/dd) into Long
     * @param date
     * @return longDate
     * @throws ParseException
     */

    public static long convertStringDateToLong ( String date ) throws ParseException {
        if (date == null ){
            return -1;
        }
        String [] d = date.split("/");
        String time = d[0]+"-"+d[1]+"-"+d[2]+" 00-00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        Date dt = df.parse(time);
        Long longDate = dt.getTime();
        return longDate;
    }
}
