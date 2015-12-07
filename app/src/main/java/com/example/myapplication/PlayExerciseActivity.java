package com.example.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PlayExerciseActivity extends AppCompatActivity {
	private static Fragment1 fragment1;
	private static Fragment2 fragment2;
	private static Fragment3 fragment3;

	private static ArrayList<String> nameList;
	private static ArrayList<Integer> numList;
	private static ArrayList<Integer> repetitionList;
	private static ArrayList<Integer> weightList;
	private static ArrayList<Integer> restTimeList;

	private static int nameIndex;
	private static int numIndex;
	private static int temp;
	private static int restTime;
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_exercise);
		context = this;
		Intent intent = getIntent();
		nameList = intent.getStringArrayListExtra("name");
		numList = intent.getIntegerArrayListExtra("num");
		repetitionList = intent.getIntegerArrayListExtra("repetition");
		weightList = intent.getIntegerArrayListExtra("weight");
		restTimeList = intent.getIntegerArrayListExtra("restTime");

		if(savedInstanceState == null){
			fragment1 = new Fragment1();
			fragment2 = new Fragment2();
			fragment3 = new Fragment3();
			getFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();
		}

	}

	public static class Fragment1 extends Fragment{
		TextView tvName;
		TextView tvNum;
		TextView tvRepetition;
		TextView tvWeight;
		Button setEndButton;

		public Fragment1(){
			nameIndex=0;
			numIndex=0;
			temp=0;

		}
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

			View rootView = inflater.inflate(R.layout.fragment1, container, false);

			tvName = (TextView)rootView.findViewById(R.id.PlayName);
			tvNum = (TextView)rootView.findViewById(R.id.PlayNum);
			tvRepetition = (TextView)rootView.findViewById(R.id.PlayRepetition);
			tvWeight = (TextView)rootView.findViewById(R.id.PlayWeight);

			tvName.setText(nameList.get(nameIndex)+"");
			tvNum.setText(numList.get(nameIndex)+"");
			tvRepetition.setText(repetitionList.get(nameIndex)+"");
			tvWeight.setText(weightList.get(nameIndex)+"");

			setEndButton = (Button)rootView.findViewById(R.id.setEndBtn);
			setEndButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().getFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();


				}
			});

			return rootView;
		}
	}

	public static class Fragment2 extends Fragment{
		TextView tvRestTime;
		Button restEndBtn;
		boolean run;
		Timer timer;
		TimerTask timerTask;
		int i, temp;
		int color = 0xFF000000;
		Vibrator vibe;

		public Fragment2(){
			i=0;
			vibe = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

		}
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

			final View rootView = inflater.inflate(R.layout.fragment2, container, false);

			int delay = restTime; // delay for 5 sec.
			int period = 1000; // repeat every sec.
			run = true;

			restTime = restTimeList.get(nameIndex) * 60;
			tvRestTime = (TextView)rootView.findViewById(R.id.restTimeView);
			tvRestTime.setText(restTime+"");
			restEndBtn = (Button)rootView.findViewById(R.id.restEndBtn);
			restEndBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					run = false;
				}
			});
			final android.os.Handler handler = new android.os.Handler(){
				@Override
				public void handleMessage(Message msg){
					tvRestTime.setText(--restTime + "");

					color = 0xFF000000+ (((0xFF0000/(restTimeList.get(nameIndex)*60))*((restTimeList.get(nameIndex)*60) - restTime)) / 0x10000)*0x10000 ;
					tvRestTime.setTextColor(color);

					if (restTime == 0){
						run = false;
						vibe.vibrate(1000);
					}
				}
			};

			timer = new Timer();
			timerTask = new TimerTask() {
				@Override
				public void run() {
					if(run){
						Message msg = handler.obtainMessage();
						handler.sendMessage(msg);
						Log.d("ii",";");
					}else {
						timer.cancel();
						timer.purge();

						if( ++temp >= numList.get(nameIndex)){
							temp=0;
							nameIndex++;
							Log.d("Dbug", "set end");
						}

						if(nameIndex >= nameList.size()){
							Log.d("Dbug-list", "ALL EXERCISE FINISH!!");
							getActivity().getFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();

						}else{
							getActivity().getFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
						}

					}
				}
			};
			timer.schedule(timerTask, 1000, 1000);
			return rootView;
		}
	}
	public static class Fragment3 extends Fragment{
		public Fragment3(){}
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			final View rootView = inflater.inflate(R.layout.fragment3, container, false);

			ImageButton anotherExerciseButton = (ImageButton)rootView.findViewById(R.id.anotherExerciseBtn);
			final ImageButton cameraButton = (ImageButton)rootView.findViewById(R.id.cameraBtn);
			ImageButton exerciseEndButton = (ImageButton)rootView.findViewById(R.id.ExerciseEndBtn);

			anotherExerciseButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();

				}
			});

			cameraButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent cameraIntent = new Intent();
					cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivity(cameraIntent);
				}
			});
			exerciseEndButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent allStopIntent = new Intent(context, MainActivity.class);
					allStopIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(allStopIntent);
				}
			});
			return rootView;
		}
	}
}

