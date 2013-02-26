package com.example.ui.logic.main;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;

import com.example.logic.MainService;
import com.example.ui.Login;
import com.example.ui.User;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;

public class TaskGetUserProfile implements RequestListener {
	
	Message _mess;
	public TaskGetUserProfile(long id, Message mess) {
		
		// TODO Auto-generated constructor stub
        UsersAPI userApi = new UsersAPI(Login.accessToken);
        _mess = mess;
        userApi.show( id, this );
	}
	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			MainService.nowuser = new User(jsonObj);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainService.getHandler().sendMessage(_mess);
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
