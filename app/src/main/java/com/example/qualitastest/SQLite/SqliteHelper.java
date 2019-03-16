package com.example.qualitastest.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.qualitastest.model.CanadaDeatilsPojo;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CanadaMaster";
    private static final int DB_VERSION = 1;

    public SqliteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_TITLE_TABLE);
        db.execSQL(Constant.CREATE_ROW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertRowData(int title_id, String title, String description, String img_url) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.TITLE_ID, title_id);
        contentValues.put(Constant.TITLE, title);
        contentValues.put(Constant.DESCRIPTION, description);
        contentValues.put(Constant.URL, img_url);

        long id = sqLiteDatabase.insert(Constant.ROW_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return id;

    }


    public long inserTitle(String title) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.TITLE, title);

        long id = sqLiteDatabase.insert(Constant.TITLE_TABLE, null, contentValues);
        sqLiteDatabase.close();

        return id;

    }


    public CanadaDeatilsPojo getTitleAndId(long id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Constant.TITLE_TABLE, new String[]{Constant.COLUMN_ID, Constant.TITLE}, Constant.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        CanadaDeatilsPojo canadaDeatilsPojo = new CanadaDeatilsPojo();
        canadaDeatilsPojo.setTitle_id(cursor.getInt(cursor.getColumnIndex(Constant.COLUMN_ID)));
        canadaDeatilsPojo.setTitle(cursor.getString(cursor.getColumnIndex(Constant.TITLE)));

        cursor.close();
        sqLiteDatabase.close();
        return canadaDeatilsPojo;

    }

    public int isEmpty() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.TITLE_TABLE);
        return count;

    }

    public int rowTableSize(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, Constant.ROW_TABLE);
        return count;
    }

    public ArrayList<CanadaDeatilsPojo> getRowTableData(int id) {

        ArrayList<CanadaDeatilsPojo> rowdetailsList = new ArrayList<>();


        //String fetch_rowtable = "SELECT t." + Constant.COLUMN_ID + ",r.* FROM " + Constant.TITLE_TABLE + " t," + Constant.ROW_TABLE + " r" + " WHERE t." + Constant.COLUMN_ID + "=" + id + " AND r." + Constant.TITLE_ID + "=" + id;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(Constant.fetch_rowtable + id, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                CanadaDeatilsPojo canadaDeatilsPojo = new CanadaDeatilsPojo();
                if (cursor.getString(cursor.getColumnIndex(Constant.TITLE)) == null && cursor.getString(cursor.getColumnIndex(Constant.DESCRIPTION)) == null && cursor.getString(cursor.getColumnIndex(Constant.DESCRIPTION)) == null) {
                    Log.i("NullObject", ""+cursor.getString(cursor.getColumnIndex(Constant.URL)));
                } else {
                    canadaDeatilsPojo.setTitle(cursor.getString(cursor.getColumnIndex(Constant.TITLE)));
                    canadaDeatilsPojo.setDescription(cursor.getString(cursor.getColumnIndex(Constant.DESCRIPTION)));
                    canadaDeatilsPojo.setImg_url(cursor.getString(cursor.getColumnIndex(Constant.URL)));
                    rowdetailsList.add(canadaDeatilsPojo);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return rowdetailsList;
    }

}
