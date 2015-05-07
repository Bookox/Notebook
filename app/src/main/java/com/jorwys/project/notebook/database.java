package com.jorwys.project.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class database  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "notebook";
    // Contacts table name
    public static final String TABLE = "notas";
    // Contacts Table Columns names

  static final String titulo = "titulo";
     static final String texto = "texto";
    static  final String fecha= "fecha";
    static  final String id= "_id";


   public  database(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("+id+" INTEGER PRIMARY KEY AUTOINCREMENT," + ""
                + titulo + " TEXT ," + texto + " TEXT,"+fecha+" TEXT"
                 + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

}
