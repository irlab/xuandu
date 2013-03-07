package com.example.ui.logic.main;

import java.io.IOException;
import java.util.List;

import android.os.Message;

import com.example.logic.MainService;
import com.example.ui.Comment;
import com.example.ui.Login;
import com.example.ui.Status;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.CommentsAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.AUTHOR_FILTER;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;
import com.weibo.sdk.android.net.RequestListener;

public class TaskGetComment implements RequestListener {
	
	Message _mess;
//	public TaskGetUserHomeTimeLine(Integer pageSize, Integer nowPage, Message mess) {
//        StatusesAPI statusApi=new StatusesAPI(Login.accessToken);
//        statusApi.friendsTimeline(0, 0, pageSize, nowPage, false, FEATURE.ALL, false, this);
//        _mess = mess;
//	}
	public TaskGetComment(long Id, int nowPage, Message mess) {
		CommentsAPI commentsApi=new CommentsAPI(Login.accessToken);
//      StatusesAPI statusApi=new StatusesAPI(Login.accessToken);
//      statusApi.friendsTimeline(0, 0, pageSize, nowPage, false, FEATURE.ALL, false, this);
        System.out.println("TaskGetComment..................         Id = " + Id);
//		long nowpage = 1;
	//	long pagesize = 50;
        commentsApi.show(Id, 0, 0, 5, nowPage, AUTHOR_FILTER.ALL, this);
     //   commentsApi.show(id, 0, max_id, count, page, filter_by_author, listener)
        
        System.out.println("hello world");
        _mess = mess;
	}
	
	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
        List<Comment> allcomment = null;
        try {
        	if (response == null) {
        		for(int i = 0; i < 10; i++) {
        			System.out.println("TaskGetUserHomeTimeLine/onComplete!!!!!!!!! response = null");
        		}
        	}
        	System.out.println("^---------------^");
        	allcomment = Comment.constructListComment(response);
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        if (allcomment == null) {
        	if (response == null) {
        		for(int i = 0; i < 10; i++) {
        			System.out.println("TaskGetUserHomeTimeLine/onComplete!!!!!!!!! response = null");
        		}
        	}
        	System.out.println("^---------------^");
        }
        else {
        	System.out.println("TaskGetComment OnComment!!!!!!!!!!!!!!!!!!!");
        	for(Comment ac:allcomment) {
        		System.out.println(ac.toString());
        	}
        }
        
		_mess.obj = allcomment;
		
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
