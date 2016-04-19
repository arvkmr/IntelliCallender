package intellical.kross.com.intellicalender;

/**
 * Created by Arv on 09-11-2014.
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class dbHelper {


    private final static String KEY_ROWID = "_id";
    private final static String KEY_NAME = "name";
    private final static String KEY_BOOL = "bool";
    private final static String KEY_START_TIME = "stime";
    private final static String KEY_END_TIME = "etime";
    private final static String KEY_DESC = "desc";
    private final static String KEY_LOC = "loc";

    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS TaskData(" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_BOOL + " text not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_DESC + " text not null, "
                    + KEY_LOC + " text not null, "
                    + KEY_START_TIME + " integer default 0, "
                    + KEY_END_TIME + " integer default 0);";

    private static final String DATABASE_NAME = "CalDb";

    private static final String DATABASE_TABLE = "TaskData";

    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public dbHelper(Context ctx) {
        try {
            db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
            db.execSQL(DATABASE_CREATE);
        } catch (Exception e) {
            db = null;
        }

    }

    public void close() {
        db.close();
    }

    public void createRow(String code, String name, String loc, String desc, long sTime, long eTime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BOOL, code);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DESC, desc);
        initialValues.put(KEY_LOC, loc);
        initialValues.put(KEY_START_TIME, sTime);
        initialValues.put(KEY_END_TIME, eTime);

        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteRow(long rowId) {
        db.delete(DATABASE_TABLE, KEY_ROWID + " = " + rowId, null);
    }

    public ArrayList<Row> fetchRows(long sTime) {
        ArrayList<Row> ret = new ArrayList<Row>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE, new String[]{
                            KEY_ROWID, KEY_BOOL, KEY_NAME, KEY_DESC, KEY_LOC, KEY_START_TIME, KEY_END_TIME}, KEY_START_TIME + " >= " + sTime, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                row._Id = c.getLong(0);
                row.mReschedule = c.getString(1).contains("true");
                row.name = c.getString(2);
                row.desc = c.getString(3);
                row.loc = c.getString(4);
                row.sTime = c.getLong(5);
                row.eTime = c.getLong(6);
                ret.add(row);
                c.moveToNext();
            }
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public Row fetchRow(long rowId) {
        Row row = new Row();
        Cursor c =
                db.query(true, DATABASE_TABLE, new String[]{
                                KEY_ROWID, KEY_BOOL, KEY_NAME, KEY_DESC, KEY_LOC, KEY_START_TIME, KEY_END_TIME}, KEY_ROWID + " = " + rowId, null, null,
                        null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            row._Id = c.getLong(0);
            row.mReschedule = c.getString(1).contains("true");
            row.name = c.getString(2);
            row.desc = c.getString(3);
            row.loc = c.getString(4);
            row.sTime = c.getLong(5);
            row.eTime = c.getLong(6);
            return row;
        } else {
            row._Id = -1;
            row.name = null;
        }
        return row;
    }

    public void updateRow(long rowId, String code, String name, String loc, String desc, long sTime, long eTime) {
        ContentValues args = new ContentValues();
        args.put(KEY_BOOL, code);
        args.put(KEY_NAME, name);
        args.put(KEY_DESC, desc);
        args.put(KEY_LOC, loc);
        args.put(KEY_START_TIME, sTime);
        args.put(KEY_END_TIME, eTime);
        db.update(DATABASE_TABLE, args, "_id=" + rowId, null);
    }

    public ArrayList<Row> GetAllRows() {
        try {
            Cursor c = db.query(DATABASE_TABLE, new String[]{
                    KEY_ROWID, KEY_BOOL, KEY_NAME, KEY_DESC, KEY_LOC, KEY_START_TIME, KEY_END_TIME}, null, null, null, null, null);
            ArrayList<Row> ret = new ArrayList<Row>();
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                row._Id = c.getLong(0);
                row.mReschedule = c.getString(1).contains("true");
                row.name = c.getString(2);
                row.desc = c.getString(3);
                row.loc = c.getString(4);
                row.sTime = c.getLong(5);
                row.eTime = c.getLong(6);
                ret.add(row);
                c.moveToNext();
            }
            return ret;

        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }

}
