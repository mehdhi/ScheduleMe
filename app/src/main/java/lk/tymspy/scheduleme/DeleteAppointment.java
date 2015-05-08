package lk.tymspy.scheduleme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class DeleteAppointment extends ActionBarActivity implements View.OnClickListener {

    private Appointment appointment;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);
        Intent g = getIntent();
        appointment = new Appointment();
        appointment = Utility.convertAppointmentStringToModel( g.getStringArrayExtra( Appointment.KEY_APPOINTMENT) );
        initializeComponents();
    }

    private void initializeComponents() {
        d = new Dialog(this);
        View btnDelSel = findViewById(R.id.btnDeleteSel);
        btnDelSel.setOnClickListener(this);

        View btnDelAll = findViewById(R.id.btnDeleteAll);
        btnDelAll.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeleteSel:

                try {
                    DBHelper sql = new DBHelper(this);
                    d.setTitle("Deleted item" + sql.deleteAppointment(appointment.getId()) );
                    d.show();
                    sql.close();

                } catch (Exception e ) {
                    d.setTitle("Error : "+ e);
                    d.show();
                } finally {

                    Intent i = new Intent(this, Main.class);
                    startActivity(i);
                    finish();

                }
                break;
            case R.id.btnDeleteAll:
                try {
                    DBHelper sql = new DBHelper(this);
                    d.setTitle("Deleted " + sql.deleteAppointmentByDate( appointment.getDate()) +" items" );
                    d.show();
                    sql.close();

                } catch (Exception e ) {
                    d.setTitle("Error : "+ e);
                    d.show();
                } finally {

                    Intent i = new Intent(this, Main.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }

}
