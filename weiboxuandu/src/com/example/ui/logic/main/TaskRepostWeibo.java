package com.example.ui.logic.main;

import java.io.IOException;

import com.example.ui.Login;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI;
import com.weibo.sdk.android.net.RequestListener;

public class TaskRepostWeibo extends WeiboAPI implements RequestListener {
	public TaskRepostWeibo(Oauth2AccessToken oauth2AccessToken) {
		super(oauth2AccessToken);
	}
	public void Repostweibo(String status, long sid) {
		StatusesAPI statusApi = new StatusesAPI(Login.accessToken);
		statusApi.repost(sid, status,  COMMENTS_TYPE.NONE, TaskRepostWeibo.this);	
	}
	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		
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
