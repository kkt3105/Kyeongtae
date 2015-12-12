package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class RoutineExerciseActivity extends AppCompatActivity {
	DBManager dbm;
	SQLiteDatabase db;
	ImageButton pushUpRoutineBtn;
	ImageButton pullUpRoutineBtn;
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

		pushUpRoutineBtn = (ImageButton)findViewById(R.id.pushUpRoutineBtn);
		pullUpRoutineBtn = (ImageButton)findViewById(R.id.pullUpRoutineBtn);
		lv = (ListView)findViewById(R.id.routineListView);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				db = dbm.getReadableDatabase();

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

				ArrayList<String> nameList = new ArrayList<>();
				ArrayList<Integer> numList = new ArrayList<>();
				ArrayList<Integer> repetitionList = new ArrayList<>();
				ArrayList<Integer> weightList = new ArrayList<>();
				ArrayList<Integer> restTimeList = new ArrayList<>();

				Log.d("kk", position+"");
				Cursor cursor;
				cursor = db.rawQuery("SELECT * FROM ROUTINE WHERE (type = " + type + " and week = " + (position + 1) + ");", null);
				String contents="";
				String name="";
				int type=0, week=0, exerorder=0, repetition=0, num=1, weight=0, restTime=1;

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
						nameList.add(name);
						numList.add(num);
						repetitionList.add(repetition);
						weightList.add(weight);
						restTimeList.add(restTime);

						db.execSQL("INSERT INTO RECORD values('" + date + "','" + time + "', " + exerorder + ",'" + name + "', " + num + ", " + repetition + ", " + 0 + ");");

					} while (cursor.moveToNext());
				}

				Toast.makeText(RoutineExerciseActivity.this, "추가완료", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), PlayExerciseActivity.class);

				intent.putStringArrayListExtra("name", nameList);
				intent.putIntegerArrayListExtra("num", numList);
				intent.putIntegerArrayListExtra("repetition", repetitionList);
				intent.putIntegerArrayListExtra("weight", weightList);
				intent.putIntegerArrayListExtra("restTime", restTimeList);

				startActivity(intent);

				return true;
			}
		});

		pushUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = 1;
				updateList(type);
			}
		});

		pullUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = 0;
				updateList(type);
			}
		});

	}
	public void updateList(int type){
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
			if(type==1){
				list.add(i+"일차");
			}else if(type==0){
				list.add(i+"주차");
			}
		}

		adapter = new ArrayAdapter<String>(RoutineExerciseActivity.this, android.R.layout.simple_expandable_list_item_1, list);
		lv.setAdapter(adapter);

		cursor.close();
		db.close();
	}

}
