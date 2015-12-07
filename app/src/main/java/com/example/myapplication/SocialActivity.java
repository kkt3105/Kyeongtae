package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

public class SocialActivity extends AppCompatActivity {
	CallbackManager callbackManager;
	ShareDialog shareDialog;
	String imgPath;
	Bitmap img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());

		Intent intent = getIntent();
		imgPath = intent.getStringExtra("imgPath");
		img = BitmapFactory.decodeFile(imgPath);

		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
		Log.d("dddd","1");

		setContentView(R.layout.activity_social);
		Log.d("dddd", "2");

		shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
			@Override
			public void onSuccess(Sharer.Result result) {
				Log.d("kk", "success");
				//Toast.makeText(SocialActivity.this, "",Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onCancel() {
				Log.d("kk", "cancel");
				finish();
			}

			@Override
			public void onError(FacebookException error) {
				Log.d("kk", "error");
				finish();
			}
		});
		Log.d("dddd", "3");

		if(ShareDialog.canShow(SharePhotoContent.class)){
			SharePhoto photo = new SharePhoto.Builder().setBitmap(img).build();
			SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
			shareDialog.show(content);
		}
		Log.d("dddd", "4");

//		if(ShareDialog.canShow(ShareLinkContent.class)){
//			ShareLinkContent content = new ShareLinkContent.Builder()
//					.setContentTitle("Hello Facebook")
//					.setContentDescription("The 'Hello Facebook' sample showcases simple Facebook integration")
//					.setContentUrl(Uri.parse("https://developers.facebook.com/android"))
//					.build();
//			shareDialog.show(content);
//		}
	}
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode,resultCode,data);
	}

}
