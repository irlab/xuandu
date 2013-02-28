package com.example.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import com.example.ui.Comment;
import com.example.ui.Status;
import com.weibo.sdk.android.WeiboException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentDbAdapter extends DbAdapter {

	private static final String TAG = "CommentDbAdapter";
	public static final String TABLE_NAME = "comment";

	public static final String id = "_id";
	public static final String statusid = "_statusid";
//	public static final String text = "_text";
//	public static final String create_at = "_create_at";
//	public static final String source = "_source";
//	public static final String favorited = "_favorited";
//	public static final String truncated = "_truncated";
//	public static final String in_reply_to_status_id = "_in_reply_to_status_id";
//	public static final String in_reply_to_user_id = "_in_reply_to_user_id";
//	public static final String in_reply_to_screen_name =  "_in_reply_to_screen_name";
//    public static final String geo = "_geo";
//    public static final String mid = "_mid";
//    public static final String reposts_count = "_repost_count";
//    public static final String comments_count = "_comments_count";
//    public static final String user = "_user";
//    public static final String idstr = "_idstr";
//    public static final String thumbnail_pic = "_thumbnail_pic";
//    public static final String bmiddle_pic = "_bmiddle_pic";
//    public static final String original_pic = "_original_pic";
//    public static final String retweeted_status = "_retweeted_status";
    public static final String jsonWeibo = "__json";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
    
    public CommentDbAdapter(Context ctx) {
    	this.mCtx = ctx;
    }
    
    public CommentDbAdapter open() throws SQLException {
    	mDbHelper = new DatabaseHelper(mCtx);
    	mDb = mDbHelper.getWritableDatabase();
    	return this;
    }
    
	public long createComment(Comment comment) {
		Log.d(TAG, "createWeibo.");
		ContentValues initialValues = new ContentValues();
		initialValues.put(id, comment.getId());
		initialValues.put(statusid, comment.getStatus().getId());
		initialValues.put(jsonWeibo, comment.toString());
		try {
			return mDb.insert(TABLE_NAME, null, initialValues);
		} catch (Exception e) {
			System.out.println("mDb insert error!!!!!!!!!!!!!");
			return -1;
		}
	}
    
	public boolean deleteComment(long _id) {
		return mDb.delete(TABLE_NAME, id + "=" + _id, null) > 0;
	}
	
	public boolean deleteCommentWithStatusId(long _statusId) {
		List<Long> commentIds = getAllCommentIds(_statusId);
		for(Long _id: commentIds) {
			if(!deleteComment(_id)) return false;
		}
		return true;
	}
/*	
 *
	public List<Status> getAllWeibos() throws WeiboException, JSONException {
//		Cursor it = mDb.query(TABLE_NAME, new String[] {id, create_at, user, text, mid, idstr, source, favorited, 
//				truncated, in_reply_to_status_id, in_reply_to_screen_name, in_reply_to_user_id, 
//				thumbnail_pic, bmiddle_pic, original_pic, geo, retweeted_status, reposts_count, 
//				comments_count}, null, null, null, null, "_create_at desc");
		Cursor it = mDb.query(TABLE_NAME, null, null, null, null, null, null);
		List<Status> weiboList = new ArrayList<Status>();
		//it.move
		for(it.moveToFirst(); !it.isAfterLast(); it.moveToNext()) {
			//getWeibo(it);
			//System.out.println(it.getCount() + " " + it.getPosition());
			String _status = it.getString(it.getColumnIndex(jsonWeibo));
			weiboList.add(new Status(_status));
		} 
		for(Status _s: weiboList) {
			System.out.println(_s.toString());
		}
		return weiboList;
	}
	
*/
	
	
	private List<Long> getAllCommentsId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Comment> getAllComments(long __statusid) throws JSONException, WeiboException {
		Cursor it = mDb.query(TABLE_NAME, null, statusid + "=" + __statusid, null, null, null, null);
		List<Comment> commentList = new ArrayList<Comment>();
		//it.move
		for(it.moveToFirst(); !it.isAfterLast(); it.moveToNext()) {
			String _comment = it.getString(it.getColumnIndex(jsonWeibo));
			commentList.add(new Comment(_comment));
		} 
		
		return commentList;
	}
	
	public List<Long> getAllCommentIds(long __statusId) {
		Cursor it = mDb.query(TABLE_NAME, new String[] {id}, statusid + "=" + __statusId, null, null, null, null);
		List<Long>  commentIds = new ArrayList<Long>();
		for(it.moveToFirst(); !it.isAfterLast(); it.moveToNext()) {
			commentIds.add( it.getLong(it.getColumnIndex(id)) );
		}
		return commentIds;
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
}
