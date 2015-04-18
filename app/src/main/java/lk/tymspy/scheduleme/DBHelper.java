package lk.tymspy.scheduleme;

/**
 * Created by Mehdhi Nawaz on 4/5/2015.
 * DBHelper
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "scheduleMeDB.db";
    public static final String TABLE_NAME = "appointments";
    public static final String APPOINMENT_COLUMN_ID = "id";
    public static final String APPOINMENT_COLUMN_DATE = "date";
    public static final String APPOINMENT_COLUMN_TIME = "time";
    public static final String APPOINMENT_COLUMN_TITLE = "title";
    public static final String APPOINMENT_COLUMN_DETAIL = "detail";

    //private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        " ("
                        + APPOINMENT_COLUMN_ID + " integer primary key autoincrement, "
                        + APPOINMENT_COLUMN_TITLE + " text not null, "
                        + APPOINMENT_COLUMN_DATE + " text not null, "
                        + APPOINMENT_COLUMN_TIME + " text not null, "
                        + APPOINMENT_COLUMN_DETAIL + " text not null " +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APPOINMENT_COLUMN_TITLE, appointment.getTitle());
        contentValues.put(APPOINMENT_COLUMN_DETAIL, appointment.getDetail());
        contentValues.put(APPOINMENT_COLUMN_DATE, appointment.getDate());
        contentValues.put(APPOINMENT_COLUMN_TIME, appointment.getTime());

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APPOINMENT_COLUMN_TITLE, appointment.getTitle());
        contentValues.put(APPOINMENT_COLUMN_DETAIL, appointment.getDetail());
        contentValues.put(APPOINMENT_COLUMN_DATE, appointment.getDate());
        contentValues.put(APPOINMENT_COLUMN_TIME, appointment.getTime());
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{appointment.getId()});
        return true;
    }

    public Integer deleteAppointment(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                APPOINMENT_COLUMN_ID +" = ? ",
                new String[]{id});
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        if(c!=null) {
            int iRow = c.getColumnIndex(APPOINMENT_COLUMN_ID);
            int iTime = c.getColumnIndex(APPOINMENT_COLUMN_TIME);
            int iDetail = c.getColumnIndex(APPOINMENT_COLUMN_DETAIL);
            int iTitle = c.getColumnIndex(APPOINMENT_COLUMN_TITLE);
            int iDate = c.getColumnIndex(APPOINMENT_COLUMN_DATE);

            for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext()) {
                Appointment row = new Appointment();
                row.setId(c.getString(iRow));
                row.setDate(c.getString(iDate));
                row.setTime(c.getString(iTime));
                row.setTitle(c.getString(iTitle));
                row.setDetail(c.getString(iDetail));
                list.add(row);
            }
        }

        return list;
    }

    public boolean checkName(String s) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, APPOINMENT_COLUMN_TITLE+" like '%" + s + "%'", null, null, null, null);
        if (c != null) {
            int x = 0;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                x++;
            }
            if (x < 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }

    }


    /**
     * This function retrieves all data on a particular given date
     * @param date
     * @return ArrayList<Appointment>
     */

    public ArrayList getAllAppointmentsByDate(String date) {
        ArrayList<Appointment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[]{APPOINMENT_COLUMN_ID,APPOINMENT_COLUMN_TIME,APPOINMENT_COLUMN_TITLE,APPOINMENT_COLUMN_DETAIL};

        Log.w("error", "Date = "+ date );
        Cursor c = db.query(TABLE_NAME, cols,APPOINMENT_COLUMN_DATE+" = \""+ date + "\"", null, null, null, null);

        if(c!=null) {
            int iRow = c.getColumnIndex(APPOINMENT_COLUMN_ID);
            int iTime = c.getColumnIndex(APPOINMENT_COLUMN_TIME);
            int iDetail = c.getColumnIndex(APPOINMENT_COLUMN_DETAIL);
            int iTitle = c.getColumnIndex(APPOINMENT_COLUMN_TITLE);

            for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext()) {
                Appointment row = new Appointment();
                row.setId(c.getString(iRow));
                row.setDate(date);
                row.setTime(c.getString(iTime));
                row.setTitle(c.getString(iTitle));
                row.setDetail(c.getString(iDetail));
                list.add(row);
            }
        }
        return list;

    }

    public Appointment getAppointmentByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[]{APPOINMENT_COLUMN_ID,APPOINMENT_COLUMN_DATE,APPOINMENT_COLUMN_TIME,APPOINMENT_COLUMN_TITLE,APPOINMENT_COLUMN_DETAIL};
        Appointment row = new Appointment();
        Log.w("error", "Title = "+ title );
        Cursor c = db.query(TABLE_NAME, cols,APPOINMENT_COLUMN_TITLE+" = \""+ title + "\"", null, null, null, null);

        if(c!=null) {

                row.setId(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_ID)));
                row.setDate(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_DATE)));
                row.setTime(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_TIME)));
                row.setTitle(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_TITLE)));
                row.setDetail(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_DETAIL)));

        }
        return row;

    }

    public Appointment getAppointmentByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[]{APPOINMENT_COLUMN_ID,APPOINMENT_COLUMN_DATE,APPOINMENT_COLUMN_TIME,APPOINMENT_COLUMN_TITLE,APPOINMENT_COLUMN_DETAIL};
        Appointment row = new Appointment();
        Log.w("error", "Title = "+ id );
        Cursor c = db.query(TABLE_NAME, cols,APPOINMENT_COLUMN_ID+" = \""+ id + "\"", null, null, null, null);

        if(c!=null) {

            row.setId(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_ID)));
            row.setDate(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_DATE)));
            row.setTime(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_TIME)));
            row.setTitle(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_TITLE)));
            row.setDetail(c.getString(c.getColumnIndex(APPOINMENT_COLUMN_DETAIL)));

        }
        return row;

    }


}