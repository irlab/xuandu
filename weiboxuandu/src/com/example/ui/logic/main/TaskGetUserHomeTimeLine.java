package com.example.ui.logic.main;

import java.io.IOException;
import java.util.List;

import android.os.Message;

import com.example.logic.MainService;
import com.example.ui.Login;
import com.example.ui.Status;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;
import com.weibo.sdk.android.net.RequestListener;

public class TaskGetUserHomeTimeLine implements RequestListener {
	
	Message _mess;
//	public TaskGetUserHomeTimeLine(Integer pageSize, Integer nowPage, Message mess) {
//        StatusesAPI statusApi=new StatusesAPI(Login.accessToken);
//        statusApi.friendsTimeline(0, 0, pageSize, nowPage, false, FEATURE.ALL, false, this);
//        _mess = mess;
//	}
	public TaskGetUserHomeTimeLine(Integer pageSize, Long maxId, Message mess) {
        StatusesAPI statusApi=new StatusesAPI(Login.accessToken);
        System.out.println("TaskGetUserHomeTimeLine ..................         maxId = " + maxId);
        statusApi.friendsTimeline(0, maxId, pageSize, 1, false, FEATURE.ALL, false, this);
        _mess = mess;
	}
	
	
	public TaskGetUserHomeTimeLine(Long since_id, Long max_id, Integer pageSize, Integer nowPage, Message mess) {
        StatusesAPI statusApi=new StatusesAPI(Login.accessToken);
    //    System.out.println("TaskGetUserHomeTimeLine ..................         maxId = " + maxId);
        statusApi.friendsTimeline(since_id, max_id, pageSize, nowPage, false, FEATURE.ALL, false, this);
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
