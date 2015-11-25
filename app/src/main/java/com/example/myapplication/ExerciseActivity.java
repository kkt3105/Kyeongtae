package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ExerciseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
	}

	public void onClick1(View view) {
		Intent exerciseBtn1Intent = new Intent(getApplicationContext(), UserExerciseActivity.class);
		startActivity(exerciseBtn1Intent);
	}

	public void onClick2(View view) {
		Intent exerciseBtn2Intent = new Intent(getApplicationContext(), RoutineExerciseActivity.class);
		startActivity(exerciseBtn2Intent);
	}
}
