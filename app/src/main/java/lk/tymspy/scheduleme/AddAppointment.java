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
                boolean worked = true;
                try {
                    if (validate()) {
//                        DBHelper sql = new DBHelper(this);
//                        sql.open();
//                        if (sql.checkname(tfTitle.getText().toString())) {
//                            appointment.setDate(CW2Activity.Date1);
//                            appointment.setTime(tfTime.getText().toString());
//                            appointment.setTitle(tfTitle.getText().toString());
//                            appointment.setDetail(tfDetail.getText().toString());
//
//                            sql.createEntry();
//                        } else {
//                            Dialog k = new Dialog(this);
//                            k.setTitle("Already Exists");
//                            worked = false;
//                        }
//                        sql.close();
                    } else {
                        worked = false;
                    }
                } catch (Exception e) {
                    worked = false;
                } finally {
                    Dialog d = new Dialog(this);
                    TextView tv1 = new TextView(this);
                    if (worked) {
                        d.setTitle("Created");

                        d.show();
                        Intent i = new Intent(this, Main.class);
                        startActivity(i);
                        finish();
                    } else {
                        d.setTitle("Error");
                        d.show();
                    }
                }
        }
    }


    public boolean validate() {
        if ((tfTitle.getText().toString().length() == 0) || (tfTime.getText().toString().length() == 0) || (tfDetail.getText().toString().length() == 0)) {
            Dialog a = new Dialog(this);
            a.setTitle("Fill in all the details");
            return false;
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
                return false;
            } else {
                return true;
            }
        }

    }
}
