package com.example.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
	private static final String TAG = "DbAdapter";

	private static final String DATABASE_NAME = "iTracks.db";
	private static final int DATABASE_VERSION = 1;
	
	public class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
//			public static final String id = "_id";
//			public static final String text = "_text";
//			public static final String create_at = "_create_at";
//			public static final String source = "_source";
//			public static final String favorited = "_favorited";
//			public static final String truncated = "_truncated";
//			public static final String in_reply_to_status_id = "_in_reply_to_status_id";
//			public static final String in_reply_to_user_id = "_in_reply_to_user_id";
//			public static final String in_reply_to_screen_name =  "_in_reply_to_screen_name";
//		    public static final String geo = "_geo";
//		    public static final String mid = "_mid";
//		    public static final String reposts_count = "_repost_count";
//		    public static final String comments_count = "_comments_count";
//		    public static final String user = "_user";
//		    public static final String idstr = "_idstr";
//		    public static final String thumbnail_pic = "_thumbnail_pic";
//		    public static final String bmiddle_pic = "_bmiddle_pic";
//		    public static final String original_pic = "_original_pic";
//		    public static final String retweeted_status = "_retweeted_status";
			String weibo_sql = "CREATE TABLE " + WeiboDbAdapter.TABLE_NAME + " (" 
					+ WeiboDbAdapter.id + " INTEGER primary key autoincrement, "
					+ WeiboDbAdapter.mid + " INTEGER not null, "
					+ WeiboDbAdapter.comments_count + " INTEGER not null, "
					+ WeiboDbAdapter.create_at + " datatime, "
					+ WeiboDbAdapter.favorited + " bit, " 
					+ WeiboDbAdapter.geo + " text, "
					+ WeiboDbAdapter.source + " text, "
					+ WeiboDbAdapter.truncated + " bit, " 
					+ WeiboDbAdapter.in_reply_to_screen_name + " text, "
					+ WeiboDbAdapter.in_reply_to_status_id + " text, "
					+ WeiboDbAdapter.in_reply_to_user_id + " text, "
					+ WeiboDbAdapter.bmiddle_pic + " text, "
					+ WeiboDbAdapter.thumbnail_pic + " text, "
					+ WeiboDbAdapter.original_pic + " text, "
					+ WeiboDbAdapter.retweeted_status + " text, "
					+ WeiboDbAdapter.user + " text, "
					+ WeiboDbAdapter.text + " text, "
					+ WeiboDbAdapter.reposts_count + " INTEGER, "
					+ WeiboDbAdapter.idstr + " INTEGER, "
					+ WeiboDbAdapter.jsonWeibo + " text"
					+ ");";	
			Log.i(TAG, weibo_sql);
			db.execSQL(weibo_sql);
			
			String comment_sql = "CREATE TABLE " + CommentDbAdapter.TABLE_NAME + " (" 
					+ CommentDbAdapter.id + " INTEGER primary key autoincrement, "
					+ CommentDbAdapter.statusid + " INTEGER not null, "
					+ CommentDbAdapter.jsonWeibo + " text"
					+ ");";	
			Log.i(TAG, comment_sql);
			db.execSQL(comment_sql);
			/*
			
			String user_sql = "CREATE_TABLE " + UserDbAdapter.TABLE_NAME + " (" 
					+ UserDbAdapter.id + " INTEGER primary key autoincrement, "
					+ UserDbAdapter.screen_name + " text, " 
					+ UserDbAdapter.name + " text, "
					+ UserDbAdapter.province + " text, "
					+ UserDbAdapter.city + " text, "
					+ UserDbAdapter.location + " text, "
					+ UserDbAdapter.description + " text, "
					+ UserDbAdapter.url + " text, "
					+ UserDbAdapter.profile_image_url + " text, "
					+ UserDbAdapter.domain + " text, "
					+ UserDbAdapter.gender + " m, "
					+ UserDbAdapter.followers_count + " int, "
					+ UserDbAdapter.friends_count + " int, "
					+ UserDbAdapter.statuses_count + " int, "
					+ UserDbAdapter.favorites_count + " int, "
					+ UserDbAdapter.create_at + " datatime, "
					+ UserDbAdapter.following + " bit, "
					+ UserDbAdapter.allow_all_act_msg + " bit, "
					+ UserDbAdapter.remark + " text, "
					+ UserDbAdapter.geo_enabled + " bit, " 
					+ UserDbAdapter.verified + " bit, "
					+ UserDbAdapter.allow_all_comment + " bit, "
					+ UserDbAdapter.avatar_large + " text, "
					+ UserDbAdapter.verified_reason + " text, "
					+ UserDbAdapter.follow_me + " text, "
					+ UserDbAdapter.online_status + " text, "
					+ UserDbAdapter.bi_followers_count + " int, "
					+ ");";
			Log.i(TAG, user_sql);
			db.execSQL(user_sql);
			*/
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//db.execSQL("DROP TABLE IF EXISTS " + UserDbAdapter.TABLE_NAME + ";");
			db.execSQL("DROP TABLE IF EXISTS " + WeiboDbAdapter.TABLE_NAME + ";");
			db.execSQL("DROP TABLE IF EXISTS " + CommentDbAdapter.TABLE_NAME + ";");
			onCreate(db);
		}

	}

}
