package intellical.kross.com.intellicalender;

/**
 * Created by Arv on 09-11-2014.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class dbHelper {



    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS TaskData(" + Row.KEY_ROWID + " integer primary key autoincrement, "
                    + Row.KEY_BOOL + " text not null, "
                    + Row.KEY_NAME + " text not null, "
                    + Row.KEY_DESC + " text not null, "
                    + Row.KEY_LOC + " text not null, "
                    + Row.KEY_START_TIME + " integer default 0, "
                    + Row.KEY_END_TIME + " integer default 0, " 
                    + Row.KEY_STATUS + " integer default 0 );";

    private static final String DATABASE_NAME = "CalDb";

    private static final String DATABASE_TABLE = "TaskData";

    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    /**
     * Constructer
     * @param ctx
     */
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
        initialValues.put(Row.KEY_BOOL, code);
        initialValues.put(Row.KEY_NAME, name);
        initialValues.put(Row.KEY_DESC, desc);
        initialValues.put(Row.KEY_LOC, loc);
        initialValues.put(Row.KEY_START_TIME, sTime);
        initialValues.put(Row.KEY_END_TIME, eTime);
        initialValues.put(Row.KEY_STATUS, Row.TASK_PENDING);
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteRow(long rowId) {
        db.delete(DATABASE_TABLE, Row.KEY_ROWID + " = " + rowId, null);
    }

    /**
     * Fetches tasks that lie between starting time and next 24 hours.
     * @param sTime
     * @return
     */
    public ArrayList<Row> fetchRows(long sTime) {
    	long eTime = sTime + 86400000;
        ArrayList<Row> ret = new ArrayList<Row>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE, new String[]{
                    		Row.KEY_ROWID, Row.KEY_BOOL, Row.KEY_NAME, Row.KEY_DESC, Row.KEY_LOC, Row.KEY_START_TIME, Row.KEY_END_TIME, Row.KEY_STATUS}, Row.KEY_START_TIME + " >= " + sTime +" AND " +Row.KEY_END_TIME + " <= " + eTime , null, null, null, Row.KEY_END_TIME+" ASC");
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
                row.lStatus = c.getLong(7);
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
                		Row.KEY_ROWID, Row.KEY_BOOL, Row.KEY_NAME, Row.KEY_DESC, Row.KEY_LOC, Row.KEY_START_TIME, Row.KEY_END_TIME, Row.KEY_STATUS}, Row.KEY_ROWID + " = " + rowId, null, null,
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
            row.lStatus = c.getLong(7);
            return row;
        } else {
            row._Id = -1;
            row.name = null;
        }
        return row;
    }
    
    /**
     * Updates the status of the task uning Row.TASK_ values
     * @param status
     * @param rowId
     */
    public void updateStatus(long status, long rowId)
    {
    	ContentValues args = new ContentValues();
        args.put(Row.KEY_STATUS, status);
        int temp  = db.update(DATABASE_TABLE, args, "_id=" + rowId, null);
        Log.e("DB", "Updated rows = " + temp + "status = "+ status);
    }
    
    
    public void updateRow(long rowId, String code, String name, String loc, String desc, long sTime, long eTime) {
        ContentValues args = new ContentValues();
        args.put(Row.KEY_BOOL, code);
        args.put(Row.KEY_NAME, name);
        args.put(Row.KEY_DESC, desc);
        args.put(Row.KEY_LOC, loc);
        args.put(Row.KEY_START_TIME, sTime);
        args.put(Row.KEY_END_TIME, eTime);
        db.update(DATABASE_TABLE, args, "_id=" + rowId, null);
    }

    /**
     * Get all rows between the given starting and ending time.
     * @param sTime
     * @param eTime
     * @return
     */
    public ArrayList<Row> GetAllRows(long sTime, long eTime) {
        try {
            Cursor c = db.query(DATABASE_TABLE, new String[]{
            		Row.KEY_ROWID, Row.KEY_BOOL, Row.KEY_NAME, Row.KEY_DESC, Row.KEY_LOC, Row.KEY_START_TIME, Row.KEY_END_TIME, Row.KEY_STATUS}, Row.KEY_START_TIME + " >= " + sTime +" AND " +Row.KEY_END_TIME + " <= " + eTime , null, null, null, Row.KEY_END_TIME+" ASC");
                        ArrayList<Row> ret = new ArrayList<Row>();
                        int numRows = c.getCount();
                        //Log.e("No of roes",  numRows+"");
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
                            row.lStatus = c.getLong(7);
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
