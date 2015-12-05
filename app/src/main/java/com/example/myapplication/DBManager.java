package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

	private static String DB_PATH="";
	private static String DB_NAME = "AppDB";

	public DBManager(Context context){
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		Log.d("kkk", "asdsadsad");
		String createRecord = "CREATE TABLE RECORD (date char(8), time char(6), exerorder int, name varchar(30), num int, repetition int, weight int, PRIMARY KEY(date, time, exerorder));";
		db.execSQL(createRecord);

		String createRoutine = "CREATE TABLE ROUTINE (type int, week int, exerorder int, repetition int, PRIMARY KEY(type, week, exerorder));";
		db.execSQL(createRoutine);

		// pull up routine sample 10 weeks
		db.execSQL("INSERT INTO ROUTINE values(0,1,0,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,1,1,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,1,2,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,1,3,4);");
		db.execSQL("INSERT INTO ROUTINE values(0,1,4,3);");

		db.execSQL("INSERT INTO ROUTINE values(0,2,0,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,2,1,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,2,2,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,2,3,4);");
		db.execSQL("INSERT INTO ROUTINE values(0,2,4,4);");

		db.execSQL("INSERT INTO ROUTINE values(0,3,0,8);");
		db.execSQL("INSERT INTO ROUTINE values(0,3,1,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,3,2,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,3,3,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,3,4,4);");

		db.execSQL("INSERT INTO ROUTINE values(0,4,0,8);");
		db.execSQL("INSERT INTO ROUTINE values(0,4,1,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,4,2,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,4,3,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,4,4,5);");

		db.execSQL("INSERT INTO ROUTINE values(0,5,0,9);");
		db.execSQL("INSERT INTO ROUTINE values(0,5,1,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,5,2,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,5,3,5);");
		db.execSQL("INSERT INTO ROUTINE values(0,5,4,5);");

		db.execSQL("INSERT INTO ROUTINE values(0,6,0,10);");
		db.execSQL("INSERT INTO ROUTINE values(0,6,1,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,6,2,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,6,3,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,6,4,5);");

		db.execSQL("INSERT INTO ROUTINE values(0,7,0,10);");
		db.execSQL("INSERT INTO ROUTINE values(0,7,1,8);");
		db.execSQL("INSERT INTO ROUTINE values(0,7,2,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,7,3,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,7,4,6);");

		db.execSQL("INSERT INTO ROUTINE values(0,8,0,11);");
		db.execSQL("INSERT INTO ROUTINE values(0,8,1,8);");
		db.execSQL("INSERT INTO ROUTINE values(0,8,2,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,8,3,6);");
		db.execSQL("INSERT INTO ROUTINE values(0,8,4,6);");

		db.execSQL("INSERT INTO ROUTINE values(0,9,0,12);");
		db.execSQL("INSERT INTO ROUTINE values(0,9,1,8);");
		db.execSQL("INSERT INTO ROUTINE values(0,9,2,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,9,3,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,9,4,6);");

		db.execSQL("INSERT INTO ROUTINE values(0,10,0,12);");
		db.execSQL("INSERT INTO ROUTINE values(0,10,1,9);");
		db.execSQL("INSERT INTO ROUTINE values(0,10,2,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,10,3,7);");
		db.execSQL("INSERT INTO ROUTINE values(0,10,4,7);");

		// push up routine sample 7 days
		db.execSQL("INSERT INTO ROUTINE values(1,1,0,2);");
		db.execSQL("INSERT INTO ROUTINE values(1,1,1,2);");
		db.execSQL("INSERT INTO ROUTINE values(1,1,2,4);");
		db.execSQL("INSERT INTO ROUTINE values(1,1,3,4);");

		db.execSQL("INSERT INTO ROUTINE values(1,2,0,3);");
		db.execSQL("INSERT INTO ROUTINE values(1,2,1,3);");
		db.execSQL("INSERT INTO ROUTINE values(1,2,2,6);");
		db.execSQL("INSERT INTO ROUTINE values(1,2,3,6);");

		db.execSQL("INSERT INTO ROUTINE values(1,3,0,4);");
		db.execSQL("INSERT INTO ROUTINE values(1,3,1,6);");
		db.execSQL("INSERT INTO ROUTINE values(1,3,2,8);");
		db.execSQL("INSERT INTO ROUTINE values(1,3,3,6);");

		db.execSQL("INSERT INTO ROUTINE values(1,4,0,8);");
		db.execSQL("INSERT INTO ROUTINE values(1,4,1,6);");
		db.execSQL("INSERT INTO ROUTINE values(1,4,2,4);");
		db.execSQL("INSERT INTO ROUTINE values(1,4,3,6);");

		db.execSQL("INSERT INTO ROUTINE values(1,5,0,10);");
		db.execSQL("INSERT INTO ROUTINE values(1,5,1,8);");
		db.execSQL("INSERT INTO ROUTINE values(1,5,2,6);");
		db.execSQL("INSERT INTO ROUTINE values(1,5,3,4);");

		db.execSQL("INSERT INTO ROUTINE values(1,6,0,5);");
		db.execSQL("INSERT INTO ROUTINE values(1,6,1,5);");
		db.execSQL("INSERT INTO ROUTINE values(1,6,2,2);");
		db.execSQL("INSERT INTO ROUTINE values(1,6,3,4);");

		db.execSQL("INSERT INTO ROUTINE values(1,7,0,2);");
		db.execSQL("INSERT INTO ROUTINE values(1,7,1,2);");
		db.execSQL("INSERT INTO ROUTINE values(1,7,2,4);");
		db.execSQL("INSERT INTO ROUTINE values(1,7,3,2);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS RECORD");
		onCreate(db);
	}
}
