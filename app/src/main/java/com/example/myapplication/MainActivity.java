package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startActivity(new Intent(this, LoadingActivity.class));
	}

	public void onClick_main_1(View view) {
		Intent mainBtn1Intent = new Intent(getApplicationContext(), ExerciseActivity.class);
		startActivity(mainBtn1Intent);
	}
	public void onClick_main_2(View view) {
		Intent mainBtn2Intent = new Intent(getApplicationContext(), RecordActivity.class);
		startActivity(mainBtn2Intent);
	}
	public void onClick_main_3(View view) {
		Toast.makeText(MainActivity.this, "기능 미구현", Toast.LENGTH_SHORT).show();
		//Calendar now = Calendar.getInstance();
		//Log.d("kktime", "" + now.get(Calendar.HOUR_OF_DAY) + " " + now.get(Calendar.MINUTE) + " " + now.get(Calendar.SECOND));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("확인").setMessage("정말로 종료하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick( DialogInterface dialog, int which )
				{
					finish();
				}
			}).setNegativeButton( "아니요", null ).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}


