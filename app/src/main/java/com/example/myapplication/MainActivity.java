package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	String imgPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, LoadingActivity.class));
		setContentView(R.layout.activity_main);
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
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 1);

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

	@Override
	public void onActivityResult(int req, int res, Intent data){
		super.onActivityResult(req, res, data);
		if(req == 1 && res == RESULT_OK && null != data){
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			imgPath = cursor.getString(columnIndex);

			cursor.close();

		}
		Intent mainBtn3Intent = new Intent(getApplicationContext(), SocialActivity.class);
		mainBtn3Intent.putExtra("imgPath", imgPath);
		startActivity(mainBtn3Intent);
	}
}


