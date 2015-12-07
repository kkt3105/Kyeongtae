package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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
		vList = new ArrayList<>();
		createLineBtn = (Button) findViewById(R.id.createLineBtn);
		endBtn = (Button) findViewById(R.id.endBtn);
		mainLayout = (LinearLayout) findViewById(R.id.layoutUserExercise);
		context = this;

		endBtn.setOnClickListener(new View.OnClickListener() {
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
				String name;

				int order, num, repetition, weight, restTime;
				int isEmpty=0;

				ArrayList<String> nameList = new ArrayList<>();
				ArrayList<Integer> numList = new ArrayList<>();
				ArrayList<Integer> repetitionList = new ArrayList<>();
				ArrayList<Integer> weightList = new ArrayList<>();
				ArrayList<Integer> restTimeList = new ArrayList<>();

				// 비어있는지 체크
				if(vList.size()!=0){
					for (int i=0; i<vList.size();i++){
						if (vList.get(i).editText_name.getText().toString().length() == 0 ||
								vList.get(i).editText_num.getText().toString().length() == 0 ||
								vList.get(i).editText_repetition.getText().toString().length() == 0 ||
								vList.get(i).editText_weight.getText().toString().length() == 0 ||
								vList.get(i).editText_time.getText().toString().length() == 0) {
							isEmpty++;
						}
					}

					if(isEmpty ==0){
						for (int i=0; i<vList.size();i++){
							order=i;
							name = vList.get(i).editText_name.getText().toString();
							num = Integer.parseInt(vList.get(i).editText_num.getText().toString());
							repetition = Integer.parseInt(vList.get(i).editText_repetition.getText().toString());
							weight = Integer.parseInt(vList.get(i).editText_weight.getText().toString());
							restTime = Integer.parseInt(vList.get(i).editText_time.getText().toString());

							Log.d("kk", date + "/" + time + "/" + order + "/" + name + "/" + num + "/" + repetition + "/" + weight + "/" + restTime);

							nameList.add(name);
							numList.add(num);
							repetitionList.add(repetition);
							weightList.add(weight);
							restTimeList.add(restTime);

							db.execSQL("INSERT INTO RECORD values('" + date + "','" + time + "', " + order + ",'" + name + "', " + num + ", " + repetition + ", " + weight + ");");
						}

						Toast.makeText(UserExerciseActivity.this, "추가완료", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), PlayExerciseActivity.class);

						intent.putStringArrayListExtra("name", nameList);
						intent.putIntegerArrayListExtra("num", numList);
						intent.putIntegerArrayListExtra("repetition", repetitionList);
						intent.putIntegerArrayListExtra("weight", weightList);
						intent.putIntegerArrayListExtra("restTime", restTimeList);

						startActivity(intent);
					}else{
						Toast.makeText(UserExerciseActivity.this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(UserExerciseActivity.this, "운동을 추가해주세요.", Toast.LENGTH_SHORT).show();
				}


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

			this.setOrientation(LinearLayout.HORIZONTAL);
			this.addView(editText_name);
			this.addView(editText_num);
			this.addView(editText_repetition);
			this.addView(editText_weight);
			this.addView(editText_time);

		}
	}
}