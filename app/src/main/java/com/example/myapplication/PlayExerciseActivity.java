package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayExerciseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_exercise);

		Intent intent = getIntent();
		ArrayList<String> nameList = intent.getStringArrayListExtra("name");
		ArrayList<Integer> numList = intent.getIntegerArrayListExtra("num");
		ArrayList<Integer> repetitionList = intent.getIntegerArrayListExtra("repetition");
		ArrayList<Integer> weightList = intent.getIntegerArrayListExtra("weight");
		ArrayList<Integer> restTimeList = intent.getIntegerArrayListExtra("restTime");

		TextView tvName = (TextView)findViewById(R.id.PlayName);
		TextView tvNum = (TextView)findViewById(R.id.PlayNum);
		TextView tvRepetition = (TextView)findViewById(R.id.PlayRepetition);
		TextView tvWeight = (TextView)findViewById(R.id.PlayWeight);

		Log.d("kkk", nameList.size()+"");
	}
}