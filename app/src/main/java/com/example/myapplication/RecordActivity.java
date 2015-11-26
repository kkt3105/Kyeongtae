package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordActivity extends AppCompatActivity {
	DBManager dbm;
	SQLiteDatabase db;
	ListView lv;
	CalendarView cal;
	String today_date, date, time, name, num, repetition, weight;
	int order;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbm = new DBManager(this);

		setContentView(R.layout.activity_record);

		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH);
		int day = today.get(Calendar.DATE);
		today_date = year + "" + (month + 1) + "" + day;

		cal = (CalendarView)findViewById(R.id.Calendar);

		lv = (ListView)findViewById(R.id.RecordList);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(RecordActivity.this, "제거하려면 길게 누르세요.", Toast.LENGTH_SHORT).show();

			}
		});

		cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

				date = year + "" + (month + 1) + "" + dayOfMonth;
				updateList(date);
			}
		});

		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Exercise selectedItem = (Exercise)lv.getAdapter().getItem(position);
				String time = selectedItem.time;
				int order = selectedItem.order;

				db = dbm.getWritableDatabase();
				db.execSQL("DELETE FROM RECORD WHERE (date = " + date + " AND time = " + time + " AND exerorder = " + order +");");
				Toast.makeText(RecordActivity.this, "제거하였습니다.", Toast.LENGTH_SHORT).show();
				updateList(date);
				return true;
			}
		});

		updateList(today_date);

	}
	public void updateList(String key){
		ArrayList<Exercise> list = new ArrayList<>();

		db = dbm.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("SELECT * FROM RECORD WHERE (date = " + key + ") ORDER BY time;", null);

		if (cursor.moveToFirst()) {
			do {
				date = cursor.getString(0);
				time = cursor.getString(1);
				order = cursor.getInt(2);
				name = cursor.getString(3);
				num = cursor.getString(4);
				repetition = cursor.getString(5);
				weight = cursor.getString(6);

				Exercise exer = new Exercise(date, time, order, name, num, repetition, weight);
				list.add(exer);
				Log.d("kk", time);

			} while (cursor.moveToNext());
		}
		ExerciseAdapter adapter = new ExerciseAdapter(RecordActivity.this, R.layout.exercise, list);
		lv.setAdapter(adapter);

		cursor.close();
		db.close();
	}
}

class Exercise{
	String date, time, name, num, repetition, weight;
	int order;
	Exercise(String date, String time, int order, String name, String num, String repetition, String weight){
		this.date = date;
		this.time = time;
		this.order = order;
		this.name = name;
		this.num = num;
		this.repetition = repetition;
		this.weight = weight;
	}
}

class ExerciseAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	ArrayList<Exercise> exerList;
	int layout;

	public ExerciseAdapter(Context context, int layout, ArrayList<Exercise> exerList){
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.exerList = exerList;
		this.layout = layout;
	}

	@Override
	public int getCount(){
		return exerList.size();
	}
	@Override
	public Object getItem(int position){
		return exerList.get(position);
	}
	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = inflater.inflate(layout, parent, false);
		}
		TextView ExerName = (TextView)convertView.findViewById(R.id.ExerName);
		TextView ExerNum = (TextView)convertView.findViewById(R.id.ExerNum);
		TextView ExerRepetition = (TextView)convertView.findViewById(R.id.ExerRepetition);
		TextView ExerWeight = (TextView)convertView.findViewById(R.id.ExerWeight);

		ExerName.setText(exerList.get(position).name);
		ExerNum.setText(exerList.get(position).num);
		ExerRepetition.setText(exerList.get(position).repetition);
		ExerWeight.setText(exerList.get(position).weight);

		return convertView;
	}
}
