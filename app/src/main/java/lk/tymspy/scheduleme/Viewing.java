package lk.tymspy.scheduleme;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class Viewing extends ActionBarActivity implements View.OnClickListener {

    public static final String MODE = "MODE";
    public static final String MODE_EDIT = "MODE_EDIT";
    public static final String MODE_MOVE = "MODE_MOVE";
    public static final String MODE_DELETE = "MODE_DELETE";
    public static final String MODE_TRANSLATE = "MODE_TRANSLATE";

    private String mode = MODE_EDIT;
    private String date = "";
    private int selection;
    private ArrayList<Appointment> data = null;
    TextView tvDisplay;
    EditText tfId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        tvDisplay = (TextView)findViewById(R.id.tvDisplay);
        tvDisplay.setMovementMethod(new ScrollingMovementMethod());
        tfId = (EditText)findViewById(R.id.tfId);
        Button btnView = (Button)findViewById(R.id.btnView);
        btnView.setOnClickListener(this);

        Intent i = getIntent();
        mode = i.getStringExtra(MODE);
        date = i.getStringExtra(Appointment.KEY_DATE);

        if ( mode == MODE_EDIT ){
            btnView.setText("View / Edit");
        }
        if ( mode == MODE_MOVE ){
            btnView.setText("Move Appointment");
        }
        if ( mode == MODE_TRANSLATE ){
            btnView.setText("Translate Appointment");
        }
        if ( mode == MODE_DELETE ){
            btnView.setText("Delete");
        }
        try {
            DBHelper sql = new DBHelper(this);
            data = sql.getAllAppointmentsByDate( date );
            if(data.size()>0){
                String result = "";

                int x = 0;
                for ( Appointment app : data ){
                    Appointment row = data.get(x);
                    result += ( x+1 ) +". " + row.getTitle() +"\n";
                    x++;
                }
                tvDisplay.setText(result);

            }
            sql.close();
        }
        catch(Exception e) {
            Dialog a = new Dialog(this);
            a.setTitle(e.getMessage());
            a.show();
        }


    }

    /**
     * This function retrieves all data on a particular given date
     * @param selection - user selection(int)
     * @return String[] = {"ID","DATE","TIME","TITLE","DETAIL"}
     */
    private String[] getSelectedAppointment(int selection){
        if (selection<1 && selection < data.size() ) return null;
        Iterator iterator = data.iterator();
        Integer x = 0;
        while (iterator.hasNext() && x != selection ){
            return Utility.convertAppointmentModelToString(data.get(selection));
        }
        return null;
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnView:
                try
                {

                    if ( validate() ){
                        if(mode == MODE_EDIT)
                        {
                            Intent i = new Intent(this, EditAppointment.class);
                            i.putExtra( Appointment.KEY_APPOINMENT, getSelectedAppointment( selection ) );
                            startActivity(i);
                        }
                        else if(mode == MODE_MOVE)
                        {
                            Intent i = new Intent(this, MoveAppointment.class);
                            i.putExtra( Appointment.KEY_APPOINMENT, getSelectedAppointment( selection ) );
                            startActivity(i);
                        }
                        else if(mode == MODE_TRANSLATE)
                        {
                            Intent i = new Intent(this, Translate.class);
                            i.putExtra( Appointment.KEY_APPOINMENT, getSelectedAppointment( selection ) );
                            startActivity(i);
                        } else if (mode == MODE_DELETE ){
                            Intent i = new Intent(this, DeleteAppointment.class);
                            i.putExtra( Appointment.KEY_APPOINMENT, getSelectedAppointment( selection ) );
                            startActivity(i);
                        }
                    } else  {
                        Dialog dialog = new Dialog(this);
                        dialog.setTitle("Invalid Selection");
                        dialog.show();
                    }

                }
                catch (Exception e)
                {
                   throw e;
                }
                break;
        }
    }

    private boolean validate(){
        try {
            selection = Integer.parseInt(tfId.getText().toString()) - 1;
            if ( selection >= 0 && selection < data.size() ) {
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }
}

