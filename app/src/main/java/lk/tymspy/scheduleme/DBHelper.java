package lk.tymspy.scheduleme;

/**
 * Created by Mehdhi Nawaz on 4/5/2015.
 * DBHelper
 */


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scheduleMeDB.db";
    public static final String APPOINMENT_TABLE_NAME = "appointments";
    public static final String APPOINMENT_COLUMN_ID = "id";
    public static final String APPOINMENT_COLUMN_DATE = "date";
    public static final String APPOINMENT_COLUMN_TIME = "time";
    public static final String APPOINMENT_COLUMN_TITLE = "title";
    public static final String APPOINMENT_COLUMN_DETAIL = "detail";
    public static final String APPOINMENT_COLUMN_VENUE = "venue";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + APPOINMENT_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAppointment(String title, String detail, String date, String time, String venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APPOINMENT_COLUMN_TITLE, title);
        contentValues.put(APPOINMENT_COLUMN_DETAIL, detail);
        contentValues.put(APPOINMENT_COLUMN_DATE, date);
        contentValues.put(APPOINMENT_COLUMN_TIME, time);
        contentValues.put(APPOINMENT_COLUMN_VENUE, venue);

        db.insert(APPOINMENT_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ APPOINMENT_TABLE_NAME +" where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, APPOINMENT_TABLE_NAME);
        return numRows;
    }

    public boolean updateAppointment(Integer id, String title, String detail, String date, String time, String venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APPOINMENT_COLUMN_TITLE, title);
        contentValues.put(APPOINMENT_COLUMN_DETAIL, detail);
        contentValues.put(APPOINMENT_COLUMN_DATE, date);
        contentValues.put(APPOINMENT_COLUMN_TIME, time);
        contentValues.put(APPOINMENT_COLUMN_VENUE, venue);
        db.update(APPOINMENT_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteAppointment(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(APPOINMENT_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllAppointments() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ APPOINMENT_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(APPOINMENT_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}
