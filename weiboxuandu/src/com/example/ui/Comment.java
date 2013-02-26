package com.example.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import com.example.db.WeiboDbAdapter;
import com.example.util.WeiboResponse;
import com.weibo.sdk.android.WeiboException;


public class Comment extends WeiboResponse  implements Serializable  {
	
	private static final long serialVersionUID = -6310255385883066980L;
	private User user = null;                            //作者信息
	private Date createdAt;                              //status创建时间
	private String id;                                   //status id
	private String mid;                                  //微博MID
	private long idstr;                                  //保留字段，请勿使用                     
	private String text;                                 //微博内容
	private Source source;                               //微博来源
	private String __json;
	
	public Comment() {
	}
	public String getJson() {
		return __json;
	}

	@SuppressLint("NewApi")
	private void constructJson(JSONObject json) throws WeiboException {
		try {
			__json = json.toString();
			createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
			System.out.println(createdAt.toString());
			id = json.getString("id");
			if(json.has("mid")) {
				mid=json.getString("mid");
			}
			idstr = json.getLong("idstr");
			text = json.getString("text");
			
			if(json.has("source")){
				source = new Source(json.getString("source"));
			}
			
			if(!json.isNull("user")) {
				user = new User(json.getJSONObject("user"));
				System.out.println(json.getJSONObject("user").toString());
			}
			
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
		}
	}



	public Comment(JSONObject json)throws WeiboException, JSONException{
		constructJson(json);
	}
	public Comment(String str) throws WeiboException, JSONException {
		// StatusStream uses this constructor
		super();
		JSONObject json = new JSONObject(str);
		constructJson(json);
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getIdstr() {
		return idstr;
	}
	public void setIdstr(long idstr) {
		this.idstr = idstr;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}


	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}

	
	public static List<Comment> constructListComment(String res) throws WeiboException {
		
		JSONObject jsonStatus = null;
		try {
			jsonStatus = new JSONObject(res);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //asJSONArray();
		JSONArray statuses = null;
		try {
			if(!jsonStatus.isNull("comments")){				
				statuses = jsonStatus.getJSONArray("comments");
			}
//			if(!jsonStatus.isNull("reposts")){
//				statuses = jsonStatus.getJSONArray("reposts");
//			}
			
			System.out.println("_______________________________________________________________");
			System.out.println(statuses.toString());
			//Log.i("statuses", statuses.toString());
			int size = statuses.length();
			System.out.println("size = " + size);
			List<Comment> status = new ArrayList<Comment>(size);
			for (int i = 0; i < size; i++) {
				System.out.println(statuses.getJSONObject(i));
				status.add(new Comment(statuses.getJSONObject(i)));
			}
			return status;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}
	
	public static CommentWrapper constructWapperComment(String res) throws WeiboException {
		
		JSONObject jsonStatus = null;
		try {
			jsonStatus = new JSONObject(res);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //asJSONArray();
		JSONArray statuses = null;
		try {
			if(!jsonStatus.isNull("comments")){				
				statuses = jsonStatus.getJSONArray("comments");
			}
//			if(!jsonStatus.isNull("reposts")){
//				statuses = jsonStatus.getJSONArray("reposts");
//			}
			int size = statuses.length();
			List<Comment> status = new ArrayList<Comment>(size);
			for (int i = 0; i < size; i++) {
				status.add(new Comment(statuses.getJSONObject(i)));
			}
			long previousCursor = jsonStatus.getLong("previous_curosr");
			long nextCursor = jsonStatus.getLong("next_cursor");
			long totalNumber = jsonStatus.getLong("total_number");
			String hasvisible = jsonStatus.getString("hasvisible");
			return new CommentWrapper(status, previousCursor, nextCursor,totalNumber,hasvisible);
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return __json;
//		return "Status [user=" + user + ", idstr=" + idstr + ", createdAt="
//				+ createdAt + ", id=" + id + ", text=" + text + ", source="
//				+ source + ", favorited=" + favorited + ", truncated="
//				+ truncated + ", inReplyToStatusId=" + inReplyToStatusId
//				+ ", inReplyToUserId=" + inReplyToUserId
//				+ ", inReplyToScreenName=" + inReplyToScreenName
//				+ ", thumbnailPic=" + thumbnailPic + ", bmiddlePic="
//				+ bmiddlePic + ", originalPic=" + originalPic
//				+ ", retweetedStatus=" + retweetedStatus + ", geo=" + geo
//				+ ", latitude=" + latitude + ", longitude=" + longitude
//				+ ", repostsCount=" + repostsCount + ", commentsCount="
//				+ commentsCount + ", mid=" + mid //+ ", annotations="
////				+ annotations + ", mlevel=" + mlevel
////				+ ", visible=" + visible 
//				+ "]";
	}

}

