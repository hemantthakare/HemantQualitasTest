package com.example.qualitastest.SQLite;

public class Constant {

    public static final String TITLE_TABLE = "title";
    public static final String ROW_TABLE = "row";

    public static final String COLUMN_ID = "id";
    public static final String TITLE = "title";
    public static final String TITLE_ID = "title_id";
    public static final String DESCRIPTION = "description";
    public static final String URL = "imgurl";


    public static final String CREATE_TITLE_TABLE = "CREATE TABLE " + TITLE_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE + " TEXT"
            + ")";

    public static final String CREATE_ROW_TABLE = "CREATE TABLE " + ROW_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE_ID + " INTEGER,"
            + TITLE + " TEXT,"
            + DESCRIPTION + " TEXT,"
            + URL + " TEXT"
            + ")";


    public static final String fetch_title = "SELECT * FROM "+TITLE_TABLE;
    public static final String fetch_rowtable = "SELECT * FROM " + ROW_TABLE + " WHERE " + TITLE_ID + "=";
    //String fetch_rowtable = "SELECT t." + COLUMN_ID + ",r.* FROM " +TITLE_TABLE + " t," + Constant.ROW_TABLE + " r" + " WHERE t." + Constant.COLUMN_ID + "=" + id + " AND r." + Constant.TITLE_ID + "=" + id;

}
