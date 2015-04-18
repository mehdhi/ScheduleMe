package lk.tymspy.scheduleme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class Viewing extends ActionBarActivity implements View.OnClickListener {

    public static final String MODE = "MODE";
    private int mode = 0;
    private String date = "";
    private ArrayList data = null;
    TextView tvDisplay;
    EditText tfId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        tvDisplay = (TextView)findViewById(R.id.tvDisplay);
        tvDisplay.setMovementMethod(new ScrollingMovementMethod());
        Intent i = getIntent();
        mode = i.getIntExtra(MODE,0);
        date = i.getStringExtra(Appointment.KEY_DATE);
        try
        {
            DBHelper sqlview = new DBHelper(this);
            data = sqlview.getAllAppointmentsByDate( date );
            if(data.size()>0){
                String rows = "";
                int x = 0;
                Iterator iterator = data.iterator();
                while (iterator.hasNext()) {
                    rows += (x+1) + ". "+ data.get(x)+ "\n";
                    x++;
                }
                Dialog a = new Dialog(this);
                a.setTitle(rows);
                a.show();;

            }
            sqlview.close();
        }
        catch(Exception e)
        {
            Dialog a = new Dialog(this);
            a.setTitle(e.getMessage());
            a.show();
        }
        tfId = (EditText)findViewById(R.id.tfId);

        View btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(this);

    }

    private String getID(String i){
        Iterator iterator = data.iterator();
        Integer x = 0;
        while (iterator.hasNext() && Integer.parseInt(i) == x ) {
            return (String) data.get(x);
        }
        return "";
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnView:
                try
                {
                    if(mode == 1)
                    {
                        Intent i = new Intent(this, EditAppointment.class);
                        i.putExtra(Appointment.KEY_ID,getID(tfId.getText().toString()));
                        i.putExtra(Appointment.KEY_DATE, date);
                        startActivity(i);
                    }
                    else if(mode == 2)
                    {
                        Intent i = new Intent(this, Move.class);
                        i.putExtra(Appointment.KEY_ID,getID(tfId.getText().toString()));
                        i.putExtra(Appointment.KEY_DATE, date);
                        startActivity(i);
                    }
                    else if(mode == 3)
                    {
                        Intent i = new Intent(this, Translate.class);
                        i.putExtra(Appointment.KEY_ID,getID(tfId.getText().toString()));
                        i.putExtra(Appointment.KEY_DATE, date);
                        startActivity(i);
                    }
                }
                catch (Exception e)
                {
                   throw e;
                }
                break;
        }
    }
}

