package com.example.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.logic.weibo.ui.WeiboInfo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WeiboUtil {
/**
 * 判断当前网络是否可用
 * @param act
 * @return
 */
	public static boolean checkNet(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static URL getString(URL str) {
		String ssString = str.toString();
		String[] aaStrings = ssString.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < aaStrings.length; i++) {
			if (i == 4) {
				aaStrings[i] = "180";
			}
			sb.append(aaStrings[i] + "/");
		}
		sb.deleteCharAt(sb.length() - 1);
		URL url = null;
		try {
			url = new URL(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static void addFav(WeiboInfo weiboInfo, String id) {
		// TODO Auto-generated method stub
	
	}

}
