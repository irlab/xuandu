package com.example.util;

import com.example.ui.Login;
import com.weibo.sdk.android.Oauth2AccessToken;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;
/**
 * 该类用于保存Oauth2AccessToken到sharepreference，并提供读取功能
 * @author xiaowei6@staff.sina.com.cn
 *
 */
public class AccessTokenKeeper {
	private static final String PREFERENCES_NAME = "weiboxuandu";
	public static int exist_flag = 0;
	/**
	 * 保存accesstoken到SharedPreferences
	 * @param context Activity 上下文环�?
	 * @param token Oauth2AccessToken
	 */
	public static void keepAccessToken(Context context, Oauth2AccessToken token) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("token", token.getToken());
		editor.putLong("expiresTime", token.getExpiresTime());
		editor.commit();
		exist_flag = 1;
	}
	/**
	 * 清空sharepreference
	 * @param context
	 */
	public static void clear(Context context){
	    SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
	    pref.edit().clear().commit();
	    exist_flag = 0;
	}

	/**
	 * 从SharedPreferences读取accessstoken
	 * @param context
	 * @return Oauth2AccessToken
	 */
	public static Oauth2AccessToken readAccessToken(Context context){
		Oauth2AccessToken token = new Oauth2AccessToken();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		token.setToken(pref.getString("token", ""));
		token.setExpiresTime(pref.getLong("expiresTime", 0));
		return token;
	}
	public static void savaautoLogin( Context context,   boolean isauto){
		SharedPreferences spuserID=context.getSharedPreferences("isauto",  Context.MODE_APPEND);
		spuserID.edit().putBoolean("isauto", isauto).commit();
	}
	
	public static boolean getauto(Context context){
		SharedPreferences spuserisFirst=context.getSharedPreferences("isauto", Context.MODE_APPEND);
		boolean isauto=spuserisFirst.getBoolean("isauto", false);
		return isauto;
	}
}
