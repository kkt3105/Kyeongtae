package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	public DBManager(Context context){
		super(context, "AppDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		String createSQL = "CREATE TABLE RECORD (date char(8), time char(6), name varchar(30), num varchar(2), repetition varchar(3), weight varchar(3), PRIMARY KEY(date, time));";
		db.execSQL(createSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS RECORD");
		onCreate(db);
	}
}
