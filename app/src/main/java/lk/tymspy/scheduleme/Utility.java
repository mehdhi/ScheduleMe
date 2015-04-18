package lk.tymspy.scheduleme;

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
}
