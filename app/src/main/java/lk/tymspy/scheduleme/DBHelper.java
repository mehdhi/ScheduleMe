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
    public static final String APPOINMENT_TABLE_NAME = "appointments";
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
                "create table " + APPOINMENT_TABLE_NAME +
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
        db.execSQL("DROP TABLE IF EXISTS " + APPOINMENT_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APPOINMENT_COLUMN_TITLE, appointment.getTitle());
        contentValues.put(APPOINMENT_COLUMN_DETAIL, appointment.getDetail());
        contentValues.put(APPOINMENT_COLUMN_DATE, appointment.getDate());
        contentValues.put(APPOINMENT_COLUMN_TIME, appointment.getTime());

        db.insert(APPOINMENT_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getDataByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + APPOINMENT_TABLE_NAME + " where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, APPOINMENT_TABLE_NAME);
        return numRows;
    }

    public boolean updateAppointment(Integer id, String title, String detail, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APPOINMENT_COLUMN_TITLE, title);
        contentValues.put(APPOINMENT_COLUMN_DETAIL, detail);
        contentValues.put(APPOINMENT_COLUMN_DATE, date);
        contentValues.put(APPOINMENT_COLUMN_TIME, time);
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
        Cursor res = db.rawQuery("select * from " + APPOINMENT_TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(APPOINMENT_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean checkName(String s) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(APPOINMENT_TABLE_NAME, null, APPOINMENT_COLUMN_TITLE+" like '%" + s + "%'", null, null, null, null);
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

    public Cursor getDataByTitle( String s ){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + APPOINMENT_TABLE_NAME + " where "+APPOINMENT_COLUMN_TITLE+"=" + s + "", null);
        return res;
    }

    public ArrayList getAllAppointmentsByDate(String s) {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + APPOINMENT_TABLE_NAME +" where "+APPOINMENT_COLUMN_DATE+"=" + s + "", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(APPOINMENT_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}