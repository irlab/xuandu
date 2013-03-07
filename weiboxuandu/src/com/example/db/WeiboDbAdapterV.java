package com.example.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import com.example.ui.Status;
import com.weibo.sdk.android.WeiboException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WeiboDbAdapterV extends DbAdapterV {

	private static final String TAG = "WeiboDbAdapter";
	
	public static final String TABLE_NAME = "weibo";
	public static final String TABLE_NAME_HOTWORDS = "weibohotWords";
//	public static String TABLE_NAME= "weibo"

	public static final String id = "_id";
	public static final String text = "_text";
	public static final String create_at = "_create_at";
	public static final String source = "_source";
	public static final String favorited = "_favorited";
	public static final String truncated = "_truncated";
	public static final String in_reply_to_status_id = "_in_reply_to_status_id";
	public static final String in_reply_to_user_id = "_in_reply_to_user_id";
	public static final String in_reply_to_screen_name =  "_in_reply_to_screen_name";
    public static final String geo = "_geo";
    public static final String mid = "_mid";
    public static final String reposts_count = "_repost_count";
    public static final String comments_count = "_comments_count";
    public static final String user = "_user";
    public static final String idstr = "_idstr";
    public static final String thumbnail_pic = "_thumbnail_pic";
    public static final String bmiddle_pic = "_bmiddle_pic";
    public static final String original_pic = "_original_pic";
    public static final String retweeted_status = "_retweeted_status";
    public static final String jsonWeibo = "__json";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
    
    public WeiboDbAdapterV(Context ctx) {
    	this.mCtx = ctx;
    }
    
    public WeiboDbAdapterV open() throws SQLException {
    	mDbHelper = new DatabaseHelper(mCtx);
    	mDb = mDbHelper.getWritableDatabase();
    	return this;
    }
    
	public long createWeibo(Status status, String table_name) {
		Log.d(TAG, "createWeibo. " + table_name);
		System.out.println(status.toString());
		
//		Cursor it = mDb.query(TABLE_NAME, null, id + "=" + status.getId(), null, null, null, id + " DESC");
//		
//		if(it.moveToFirst() == it.moveToLast()) {
//			System.out.println("da createWeibo");
//			return 0;
//		}
		
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(id, status.getId());
		initialValues.put(create_at, status.getCreatedAt().toString());
		initialValues.put(user, status.getUser().toString());
		System.out.println("createWeibo...   " + status.getUser().toString());
		initialValues.put(text, status.getText());
		initialValues.put(mid, status.getMid());
		initialValues.put(idstr, status.getIdstr());
		initialValues.put(source, status.getSource().toString());
		initialValues.put(favorited, status.isFavorited());
		initialValues.put(truncated, status.isTruncated());
		initialValues.put(in_reply_to_status_id, status.getInReplyToStatusId());
		initialValues.put(in_reply_to_screen_name, status.getInReplyToScreenName());
		initialValues.put(in_reply_to_user_id, status.getInReplyToUserId());
		initialValues.put(jsonWeibo, status.toString());
		try {
		//	initialValues.put(geo, status.getGeo());
			initialValues.put(thumbnail_pic, status.getThumbnailPic());
		} catch(Exception e) {
		
			System.out.println("retweeted_status NULL");
		}
		try {
			initialValues.put(bmiddle_pic, status.getBmiddlePic());
			//initialValues.put(geo, status.getGeo());
		} catch(Exception e) {
			System.out.println("retweeted_status NULL");
		}
		try {
			initialValues.put(original_pic, status.getOriginalPic());
			//initialValues.put(geo, status.getGeo());
		} catch(Exception e) {
			System.out.println("retweeted_status NULL");
		}
		initialValues.put(geo, status.getGeo());
		try {
			initialValues.put(geo, status.getGeo());
		} catch(Exception e) {
			System.out.println("retweeted_status NULL");
		}
		try {
			initialValues.put(retweeted_status, status.getRetweetedStatus().toString());
		} catch(Exception e) {
			System.out.println("retweeted_status NULL");
		}
		initialValues.put(reposts_count, status.getRepostsCount());
		initialValues.put(comments_count, status.getCommentsCount());
		try {
			return mDb.insert(table_name, null, initialValues);
		} catch (Exception e) {
			System.out.println("mDb insert error!!!!!!!!!!!!!");
			return -1;
		}
	}
    
	public boolean deleteWeibo(long _id, String table_name) {
		return mDb.delete(table_name, id + "=" + _id, null) > 0;
	}
	
	public List<Status> getAllWeibos(String table_name) throws WeiboException, JSONException {
//		Cursor it = mDb.query(TABLE_NAME, new String[] {id, create_at, user, text, mid, idstr, source, favorited, 
//				truncated, in_reply_to_status_id, in_reply_to_screen_name, in_reply_to_user_id, 
//				thumbnail_pic, bmiddle_pic, original_pic, geo, retweeted_status, reposts_count, 
//				comments_count}, null, null, null, null, "_create_at desc");
		//Cursor it = mDb.query(TABLE_NAME, null, null, null, null, null, null);
		Cursor it = mDb.query(table_name, null, null, null, null, null, id + " DESC");
		List<Status> weiboList = new ArrayList<Status>();
		//it.move
		for(it.moveToFirst(); !it.isAfterLast(); it.moveToNext()) {
			String _status = it.getString(it.getColumnIndex(jsonWeibo));
			weiboList.add(new Status(_status));
		} 
		for(Status _s: weiboList) {
			System.out.println(_s.toString());
		}
		return weiboList;
	}
	
//	public Status getWeibo(Cursor it) {
////		Status weibo = new Status(it);
////		return weibo;
//		String[] _t = it.getColumnNames();
//		for(String s: _t) {
//			System.out.print(s + " " + it.getExtras().getString(s) + " " + it.getColumnIndex(s) );
//			int index = it.getColumnIndexOrThrow(s);
//			System.out.println(index);
//		//	if() {
//			if(it.getString(index) != null)
//				System.out.println(it.getString(index));
//		//	}
//		}
//		//it.getExtras().get(key)
//		System.out.println("");
//		return null;
//	}
    public void close() {
    	mDbHelper.close();
    }
    
    public void delete() {
//		mDbHelper.onUpgrade(mDb, 0, 0);
    	mDbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + WeiboDbAdapterV.TABLE_NAME + ";");
		mDbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + WeiboDbAdapterV.TABLE_NAME_HOTWORDS + ";");
		mDbHelper.onCreate(mDb);
    }
}
