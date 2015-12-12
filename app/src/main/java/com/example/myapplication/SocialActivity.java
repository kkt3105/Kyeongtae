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

		setContentView(R.layout.activity_social);

		shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
			@Override
			public void onSuccess(Sharer.Result result) {
				Log.d("kk", "success");
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

		if(ShareDialog.canShow(SharePhotoContent.class)){
			SharePhoto photo = new SharePhoto.Builder().setBitmap(img).build();
			SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
			shareDialog.show(content);
		}

	}
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode,resultCode,data);
	}

}
