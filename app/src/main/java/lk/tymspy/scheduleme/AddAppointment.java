package lk.tymspy.scheduleme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Dialog;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;



public class AddAppointment extends ActionBarActivity implements View.OnClickListener {

    private Appointment appointment;
    private EditText tfTitle, tfTime, tfDetail;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        this.initializeComponents();
        Intent g = getIntent();
        appointment.setDate(g.getStringExtra(Appointment.KEY_DATE));
    }

    private void initializeComponents() {
        appointment = new Appointment();
        tfTitle = (EditText) findViewById(R.id.tfTitle);
        tfTime = (EditText) findViewById(R.id.tfTime);
        tfDetail = (EditText) findViewById(R.id.tfDetail);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        View save = findViewById(R.id.btnSave);
        save.setOnClickListener(this);
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
            case R.id.btnSave:
                int worked = 1;
                try {
                    if (validate() > 0 ) {
                        DBHelper sql = new DBHelper(this);
                        if (sql.checkName(tfTitle.getText().toString())) {
                            appointment.setTime(tfTime.getText().toString());
                            appointment.setTitle(tfTitle.getText().toString());
                            appointment.setDetail(tfDetail.getText().toString());
                            sql.insertAppointment(appointment);
                        } else {
                            worked = 0;
                        }
                        sql.close();
                    } else if (validate() == 0 ) {
                        worked = -1;
                    } else {
                        worked = -2;
                    }
                } catch (Exception e) {
                    worked = -3;
                } finally {
                    Dialog d = new Dialog(this);

                    if (worked > 0) {
                        d.setTitle("Successfully added!");

                        d.show();
                        Intent i = new Intent(this, Main.class);
                        startActivity(i);
                        finish();
                    } else if (worked == 0 ) {
                        d.setTitle("Duplicated Title!");
                        d.show();
                    } else if (worked == -1 ) {
                        d.setTitle("Fill all the fields!");
                        d.show();
                    } else if (worked == -2 ) {
                        d.setTitle("Invalid Time!");
                        d.show();
                    } else {
                        d.setTitle("Error!");
                        d.show();
                    }
                }
        }
    }


    public int validate() {
        if ((tfTitle.getText().toString().length() == 0) || (tfTime.getText().toString().length() == 0) || (tfDetail.getText().toString().length() == 0)) {
            return 0;
        } else {
            int a = 0;
            int p = 0;
            int m = 0;
            int number = 0;
            for (int i = 0; i < tfTime.getText().toString().length(); i++) {
                if (tfTime.getText().charAt(i) == 'a') {
                    a++;
                }
                if (tfTime.getText().charAt(i) == 'p') {
                    p++;
                }
                if (tfTime.getText().charAt(i) == 'm') {
                    m++;
                }
                if (tfTime.getText().charAt(i) >= '0' && tfTime.getText().charAt(i) <= '9') {
                    number++;
                }
            }
            if ((a > 1) || (p > 1) || (m > 1) || (a == 1 && p == 1)) {
                return -1;
            } else {
                return 1;
            }
        }

    }
}
