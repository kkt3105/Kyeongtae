package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class UserExerciseActivity extends AppCompatActivity {
	Button createLineBtn;
	Button endBtn;
	LinearLayout mainLayout;
	Context context;
	ArrayList<ExerciseView> vList;
	DBManager dbm;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_exercise);

		dbm = new DBManager(this);
		vList = new ArrayList<ExerciseView>();
		createLineBtn = (Button) findViewById(R.id.createLineBtn);
		endBtn = (Button) findViewById(R.id.endBtn);
		mainLayout = (LinearLayout) findViewById(R.id.layoutUserExercise);
		context = this;

		endBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(UserExerciseActivity.this, "완료", Toast.LENGTH_SHORT).show();
			}
		});
		createLineBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ExerciseView exerciseView = new ExerciseView(context);

				vList.add(exerciseView);
				mainLayout.addView(exerciseView);
			}
		});
	}

	protected class ExerciseView extends LinearLayout {

		EditText editText_name;
		EditText editText_num;
		EditText editText_repetition;
		EditText editText_weight;
		EditText editText_time;

		public ExerciseView(Context context) {
			super(context);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.weight = 1.0f;

			editText_name = new EditText(context);
			editText_num = new EditText(context);
			editText_repetition = new EditText(context);
			editText_weight = new EditText(context);
			editText_time = new EditText(context);

			editText_name.setHint(" 운동이름 ");
			editText_num.setHint(" 세트 수 ");
			editText_repetition.setHint(" 반복횟수 ");
			editText_weight.setHint(" 무게 ");
			editText_time.setHint(" 휴식시간 ");

			editText_name.setLayoutParams(params);
			editText_num.setLayoutParams(params);
			editText_repetition.setLayoutParams(params);
			editText_weight.setLayoutParams(params);
			editText_time.setLayoutParams(params);

			editText_name.setId(View.generateViewId());
			editText_num.setId(View.generateViewId());
			editText_repetition.setId(View.generateViewId());
			editText_weight.setId(View.generateViewId());
			editText_time.setId(View.generateViewId());

			editText_name.setNextFocusDownId(editText_num.getId());
			editText_num.setNextFocusDownId(editText_repetition.getId());
			editText_repetition.setNextFocusDownId(editText_weight.getId());
			editText_weight.setNextFocusDownId(editText_time.getId());
			editText_time.setNextFocusDownId(-1);

			// DB에 추가.
			/*
			addBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					db = dbm.getWritableDatabase();
					Calendar today = Calendar.getInstance();
					int year = today.get(Calendar.YEAR);
					int month = today.get(Calendar.MONTH);
					int day = today.get(Calendar.DATE);
					int hour = today.get(Calendar.HOUR_OF_DAY);
					int minute = today.get(Calendar.MINUTE);
					int second = today.get(Calendar.SECOND);

					String date = year + "" + (month + 1) + "" + day;
					String time = hour +""+minute+""+second;

					Log.d("kktime", date + time);

					String name = editText_name.getText().toString();
					String num = editText_num.getText().toString();
					String repetition = editText_repetition.getText().toString();
					String weight = editText_weight.getText().toString();
					String restTime = editText_time.getText().toString();

					if (name.length() == 0 || num.length() == 0 || repetition.length() == 0 || weight.length() == 0 || restTime.length() == 0) {
						Toast.makeText(UserExerciseActivity.this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
					} else {
						db.execSQL("INSERT INTO RECORD values('" + date + "','" + time +"','"+ name + "','" + num + "','" + repetition + "','" + weight + "');");
						Toast.makeText(UserExerciseActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();
					}
				}
			});
			*/

			this.setOrientation(LinearLayout.HORIZONTAL);
			this.addView(editText_name);
			this.addView(editText_num);
			this.addView(editText_repetition);
			this.addView(editText_weight);
			this.addView(editText_time);

		}
	}
}