package com.example.ui.logic.main;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;

import com.example.ui.Login;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.net.RequestListener;

public class TaskUserLogin implements RequestListener{
	
	Message _mess;
	public TaskUserLogin(Message mess) {
		 AccountAPI accountApi=new AccountAPI(Login.accessToken);
        _mess = mess;
        accountApi.getUid(this);   
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			TaskGetUserProfile _obj = new TaskGetUserProfile(Long.parseLong(jsonObj.getString("uid")), _mess);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
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
