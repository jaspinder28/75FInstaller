package com.x75f.installer.DB_Local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLliteAdapter {

    SQLlite sqLlite;

    public SQLliteAdapter(Context c) {
        sqLlite = new SQLlite(c);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = sqLlite.getWritableDatabase();
        String count = "SELECT count(*) FROM "+ SQLlite.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount == 0){
            return true;
        }else {
            return false;
        }
    }

    public long insertdata(String ccuid, int verified) {
        long x = 0;
        try {
            SQLiteDatabase db = sqLlite.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLlite.CCU_ID, ccuid);
            contentValues.put(SQLlite.VERIFIED, verified);
            x = db.insert(SQLlite.TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public int getdata(String ccuid) {
        int verified = 0;
        try {
            String[] columns = {SQLlite.VERIFIED};
            String where = SQLlite.CCU_ID + "= ?";
            String[] args = {ccuid};
            SQLiteDatabase db = sqLlite.getWritableDatabase();
            Cursor cursor = db.query(SQLlite.TABLE_NAME, columns, where, args, null, null, null, null);
            while (cursor.moveToNext()) {
                int index1 = cursor.getColumnIndex(SQLlite.VERIFIED);
                verified = cursor.getInt(index1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verified;
    }

    public int update(String ccuid, int value) {
        int i = 0;
        try {
            SQLiteDatabase db = sqLlite.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(SQLlite.VERIFIED, value);
            String where = SQLlite.CCU_ID + " = ?";
            String[] args = {ccuid};
            i = db.update(SQLlite.TABLE_NAME, cv, where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    static class SQLlite extends SQLiteOpenHelper {
        private Context c;
        private static final String DATABASE_NAME = "LOCAL75F";
        private static final String TABLE_NAME = "OTPCHECK";
        private static final int VERSION = 1;
        private static final String CCU_ID = "_ccuid";
        private static final String VERIFIED = "Verified";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CCU_ID + " VARCHAR(255) PRIMARY KEY ," + VERIFIED + " INTEGER DEFAULT 0 );";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public SQLlite(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
            this.c = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
