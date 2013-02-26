package com.example.ui;

import java.io.IOException;

import com.example.R;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;
import com.weibo.sdk.android.net.RequestListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class timeLine extends Activity implements  RequestListener
{
	public static String timeline="not changed";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log);
		StatusesAPI api=new StatusesAPI(Login.accessToken);
		if(Login.accessToken==null)
		{
			Toast.makeText(timeLine.this,"the token is fucking null", Toast.LENGTH_SHORT).show();
		}
		api.friendsTimeline(0, 0, 5 , 1, false,  FEATURE.ALL,false,this);
		
		
	}
		public void onComplete(String response) {
			// TODO Auto-generated method stub
			
			timeline=response;
			Log.i("response",response);
			Toast.makeText(timeLine.this,response, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			
		}
	

}
