package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RoutineExerciseActivity extends AppCompatActivity {
	DBManager dbm;
	SQLiteDatabase db;
	Button pushUpRoutineBtn;
	Button pullUpRoutineBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routine_exercise);
		dbm = new DBManager(this);

		pushUpRoutineBtn = (Button)findViewById(R.id.pushUpRoutineBtn);
		pullUpRoutineBtn = (Button)findViewById(R.id.pullUpRoutineBtn);

		pushUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(RoutineExerciseActivity.this, "푸시업 루틴", Toast.LENGTH_SHORT).show();

			}
		});
		pullUpRoutineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(RoutineExerciseActivity.this, "풀업 루틴", Toast.LENGTH_SHORT).show();
			}
		});
	}

//	public void routine_test_button(View view) {
//		db = dbm.getWritableDatabase();
//
//		Calendar today = Calendar.getInstance();
//		int year = today.get(Calendar.YEAR);
//		int month = today.get(Calendar.MONTH);
//		int date = today.get(Calendar.DATE);
//
//		String day = year + "" + (month+1) + "" +date;
//		String name = "Push Up";
//		String num = "5";
//		String repetition = "20";
//		String weight = "0";
//
//		db.execSQL("INSERT INTO RECORD values('" +day+ "','" +name+ "','" +num+ "','" +repetition+ "','" +weight+ "');");
//		Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();
//	}
}
