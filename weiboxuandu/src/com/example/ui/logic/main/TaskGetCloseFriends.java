package com.example.ui.logic.main;


import java.io.IOException;
import java.util.List;

import xuandu.HotWordsAPI;

import android.os.Message;

import com.example.logic.MainService;
import com.example.ui.Login;
import com.example.ui.Status;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;
import com.weibo.sdk.android.net.RequestListener;

public class TaskGetCloseFriends implements RequestListener {
	
	Message _mess;
	public TaskGetCloseFriends(Integer pageSize, Integer nowPage, Message mess) {
        HotWordsAPI statusApi= new HotWordsAPI(Login.accessToken);
      //  (int now_page, int page_size, int order_type, RequestListener listener)
        statusApi.friendsTimeline(nowPage, pageSize, 1, this);
        _mess = mess;
	}
	
	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
        List<Status> allweibo = null;
        try {
        	if (response == null) {
        		for(int i = 0; i < 10; i++) {
        			System.out.println("TaskGetUserHomeTimeLine/onComplete!!!!!!!!! response = null");
        		}
        	}
        	System.out.println("^---------------^");
        	allweibo = Status.constructListStatus(response);
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        if (allweibo == null) {
        	if (response == null) {
        		for(int i = 0; i < 10; i++) {
        			System.out.println("TaskGetUserHomeTimeLine/onComplete!!!!!!!!! response = null");
        		}
        	}
        	System.out.println("^---------------^");
        }
		_mess.obj = allweibo;
		
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
