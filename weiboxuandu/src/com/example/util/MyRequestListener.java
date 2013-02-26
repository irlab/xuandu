package com.example.util;

import java.io.IOException;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class MyRequestListener implements RequestListener {
	
    private String response;
	public MyRequestListener() { response = "test"; }
	@Override
	public void onComplete(String r) {
		// TODO Auto-generated method stub
	    this.response = r;
		
	}
	public void set(String s) {
		this.response = s;
	}
	public String get() {
		return this.response;
	}

	@Override
	public void onError(WeiboException e) {
		// TODO Auto-generated method stub
		 e.printStackTrace();
	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub
		 e.printStackTrace();
		
	}
}
