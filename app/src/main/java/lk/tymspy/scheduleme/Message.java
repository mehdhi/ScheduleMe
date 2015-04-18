package lk.tymspy.scheduleme;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Mahdhi on 4/18/2015.
 */
public class Message {

    private String title;
    private String message;
    private Dialog dialog;
    private Context context;

    public Message(Context context, String title, String message) {
        this.title = title;
        this.message = message;
        this.dialog = new Dialog(context);
    }

    public Message(Context context, String title) {
        this.title = title;
        this.dialog = new Dialog(context);
    }

    public Message() { }

    public String getTitle() {
        return title;
    }

    public void show(){
        if ( title!= null && dialog != null && context != null ){
            dialog.setTitle(title);
            //dialog.
            dialog.show();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
