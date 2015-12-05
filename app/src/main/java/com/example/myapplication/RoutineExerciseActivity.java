package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class RoutineExerciseActivity extends AppCompatActivity {
	DBManager dbm;
	SQLiteDatabase db;
	Button pushUpRoutineBtn;
	Button pullUpRoutineBtn;
	Context context;
	ListView lv;
	ArrayAdapter<String> adapter;

	int type;
	int listnum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routine_exercise);
		dbm = new DBManager(this);
		context=this;

		pushUpRoutineBtn = (Button)findViewById(R.id.pushUpRoutineBtn);
		pullUpRoutineBtn = (Button)findViewById(R.id.pullUpRoutineBtn);
		lv = (ListView)findViewById(R.id.routineListView);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				db = dbm.getReadableDatabase();

				Log.d("kk", position+"");
				Cursor cursor;
				cursor = db.rawQuery("SELECT * FROM ROUTINE WHERE (type = " + type +" and week = "+ (position+1) + ");", null);
				String contents="";
				if (cursor.moveToFirst()) {
					do {
						int type = cursor.getInt(0);
						int week = cursor.getInt(1);
						int exerorder = cursor.getInt(2);
						int repetition = cursor.getInt(3);

						contents += "Set: "+ (exerorder+1) +" Repetition: "+repetition+"\n";
					} while (cursor.moveToNext());
				}
				contents += "추가하시려면 길게 눌러주세요.";
				Toast.makeText(RoutineExerciseActivity.this, contents, Toast.LENGTH_SHORT).show();
			}
		});

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				db = dbm.getReadableDatabase();

				Calendar today = Calendar.getInstance();
				int year = today.get(Calendar.YEAR);
				int month = today.get(Calendar.MONTH);
				int day = today.get(Calendar.DATE);
				int hour = today.get(Calendar.HOUR_OF_DAY);
				int minute = today.get(Calendar.MINUTE);
				int second = today.get(Calendar.SECOND);

				String date = year + "" + (month + 1) + "" + day;
				String time = hour +""+minute+""+second;

				Log.d("kk", position+"");
				Cursor cursor;
				cursor = db.rawQuery("SELECT * FROM ROUTINE WHERE (type = " + type + " and week = " + (position + 1) + ");", null);
				String contents="";
				String name="";
				int type=0, week=0, exerorder=0, repetition=0;

				if (cursor.moveToFirst()) {
					do {
						type = cursor.getInt(0);
						week = cursor.getInt(1);
						exerorder = cursor.getInt(2);
						repetition = cursor.getInt(3);

						if(type == 0){
							name="Pull up";
						}else if(type == 1){
							name="Push up";
						}

						db.execSQL("INSERT INTO RECORD values('" + date + "','" + time + "', " + exerorder + ",'" + name + "', " + 1 + ", " + repetition + ", " + 0 + ");");

					} while (cursor.moveToNext());
				}

				Toast.makeText(RoutineExerciseActivity.this, "추가하였습니다.", Toast.LENGTH_SHORT).show();

				return true;
			}
		});

		pushUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = 1;
				ArrayList<String> list = new ArrayList<String>();
				adapter = new ArrayAdapter<String>(RoutineExerciseActivity.this, android.R.layout.simple_expandable_list_item_1, list);
				adapter.clear();
				db = dbm.getReadableDatabase();

				Cursor cursor;
				cursor = db.rawQuery("SELECT max(week) FROM ROUTINE WHERE ( type ="+type+");", null);
				listnum = 0;

				if (cursor.moveToFirst()) {
					do {
						listnum = cursor.getInt(0);
					} while (cursor.moveToNext());
				}
				for (int i = 1; i <= listnum; i++) {
					list.add(i+"일차");
				}

				adapter = new ArrayAdapter<String>(RoutineExerciseActivity.this, android.R.layout.simple_expandable_list_item_1, list);

				lv.setAdapter(adapter);

				cursor.close();
				db.close();
			}
		});

		pullUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = 0;
				ArrayList<String> list = new ArrayList<String>();
				adapter = new ArrayAdapter<String>(RoutineExerciseActivity.this, android.R.layout.simple_expandable_list_item_1, list);
				adapter.clear();
				db = dbm.getReadableDatabase();

				Cursor cursor;
				cursor = db.rawQuery("SELECT max(week) FROM ROUTINE WHERE ( type ="+type+");", null);
				listnum = 0;

				if (cursor.moveToFirst()) {
					do {
						listnum = cursor.getInt(0);
					} while (cursor.moveToNext());
				}
				for (int i = 1; i <= listnum; i++) {
					list.add(i+"주차");
				}

				adapter = new ArrayAdapter<String>(RoutineExerciseActivity.this, android.R.layout.simple_expandable_list_item_1, list);

				lv.setAdapter(adapter);

				cursor.close();
				db.close();
			}
		});
	}
}
