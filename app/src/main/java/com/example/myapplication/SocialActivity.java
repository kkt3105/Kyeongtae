package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class SocialActivity extends AppCompatActivity {
	CallbackManager callbackManager;
	ShareDialog shareDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);

		setContentView(R.layout.activity_social);

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

		if(ShareDialog.canShow(ShareLinkContent.class)){

			ShareLinkContent content = new ShareLinkContent.Builder()
					.setContentTitle("Hello Facebook")
					.setContentDescription("The 'Hello Facebook' sample showcases simple Facebook integration")
					.setContentUrl(Uri.parse("https://developers.facebook.com/android"))
					.build();

			shareDialog.show(content);
		}

	}
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode,resultCode,data);
	}
}
