package com.example.logic.weibo.ui;



import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import com.example.R;
import com.example.logic.IWeiboActivity;
import com.example.logic.MainService;
import com.example.logic.Task;
import com.example.logic.weibo.ui.WriteWeibo;
import com.example.ui.Comment;
import com.example.ui.Status;
import com.example.ui.adapter.WeiboAdapter;
import com.example.ui.adapter.commentAdapter;
import com.example.ui.logic.imaCache.Anseylodar;
import com.example.util.Utility;
import com.example.util.WeiboUtil;
import com.weibo.sdk.android.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class weiboContent extends Activity implements IWeiboActivity, Runnable {

	public static final int REFRESH_WEIBO = 1;
	public int nowPage = 1; // 当前第几页
	public int pageSize = 10;  // 每页条数
	public static ListView weibolist; // 微博信息显示	
//	private loginprogress; // 开始页进度条
	public static View loginprogress; // 开始页进度条
	public static LinearLayout moreweibo; // 底部更多项
	public static ProgressBar progressBar; // 底部更多项的进度条
	public static ProgressBar titleprogressBar; // 顶部进度条
	public static commentAdapter adapter; // 微博信息的适配器
	private ImageView btrefaush; // 刷新微博的按钮
	
	// 设置信息常量
	public static final int SETTING = 1; //  设置
	public static final int ACCOUNT = 2; // 账号
	public static final int OFICEAWEIBO = 3; //   官方微博
 	public static final int COMMONT = 4; // 意见
	public static final int ABOUTWEIBO = 5; // 关于
	public static final int EXIT = 6; // 退出
	
	
	
	ImageView tweet_profile_preview;//发微博人的头像
	TextView tweet_profile_name;//发微博的人
	TextView tweet_message;//微博内容
	ImageView tweet_upload_pic;
	TextView tweet_oriTxt;//转发内容
	ImageView tweet_upload_pic2;//转发内容图片
	public Status status;//返回的微博内容
	public Status tweetstatus;//转发内容
	LinearLayout tweetstatusview;//转发微博内容页面
	ImageView back;//返回
	View progress;//圆形进度条
	Button comment,redirect;//评论和转发按钮
	TextView comment_num,redirect_num;//条数
	TextView tvtitle;
	Anseylodar anseylodar;
	RelativeLayout tweet_profile;//
	TextView tweet_updated;
	TextView tweet_via;
	//List<PostParameter> params;
	//下面的5个按钮 刷新 评论 转发 收藏 更多
	TextView tvReload,tvComment,tvForward,tvFav,tvMore;

	
	private  Handler mHandler=new Handler()
	{  
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			refreshview();//刷新页面内容
			//如果使用又转发 则显示又转发页面
		    if (tweetstatus!=null){
		    	tweetstatusview.setVisibility(View.VISIBLE);
			    refeshtweestatus();
			}
		}
	};
	
	public void refreshview(){
		try {
			//发表微博人的名字
			tweet_profile_name.setText(status.getUser().getScreenName());
			//微博内容
			tweet_message.setText(status.getText().toString());
			tweet_via.setText("From:" + status.getSource().getName());
			tweet_updated.setText(Utility.showTime(status.getCreatedAt()) + " ");
			progress.setVisibility(View.GONE);
			//这里把人头像的图片转换成了180*180尺寸的大图了
			URL url=WeiboUtil.getString(status.getUser().getProfileImageURL());
			//异步加载头像图片
			anseylodar.showimgAnsy(tweet_profile_preview, url.toString());
			if (status.getOriginalPic()!=null) {
				//一步加载内容图片
				anseylodar.showimgAnsy(tweet_upload_pic, status.getThumbnailPic());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//如果是又转发 则加载又转发内容
	public void refeshtweestatus(){
			//获取转发内容和图片
			try {
				//又转发内容
				String text = "@" + tweetstatus.getUser().getScreenName() + ":";
				SpannableString ss = new SpannableString(text + tweetstatus.getText());
				ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, text.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				tweet_oriTxt.setText(ss);
             //异步加载图片
				anseylodar.showimgAnsy(tweet_upload_pic2, tweetstatus.getThumbnailPic());
			} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// 获取又转发内容
		tweetstatus = status.getRetweetedStatus();
		mHandler.sendEmptyMessage(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.weiboxx);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// 获取HomeActivity发来的数据
		Intent data = getIntent();
		System.out.println( data.getExtras().get("status") );
		try {
			status = new Status( new JSONObject( (String) data.getExtras().get("status") ) );
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		initView(); // 初始化布局
    	MainService.allActivity.add(this);
    	init();
    	
        anseylodar=new Anseylodar();
        Thread statusT=new Thread(this);
        statusT.start();
	}
	// 初始化页面的一些基本布局
	private void initView() {
		
		weibolist = (ListView) this.findViewById(R.id.commentlist2);
		
		
		View head = LayoutInflater.from(this).inflate(R.layout.detailweibo2, null);
		
		tweet_profile_preview = (ImageView) head.findViewById(R.id.tweet_profile_preview);
		tweet_profile_name = (TextView) head.findViewById(R.id.tweet_profile_name);
		tweet_message = (TextView) head.findViewById(R.id.tweet_message);
	    tweet_upload_pic = (ImageView) head.findViewById(R.id.tweet_upload_pic);
		tweet_oriTxt = (TextView) head.findViewById(R.id.tweet_oriTxt);
		tweet_upload_pic2 = (ImageView) head.findViewById(R.id.tweet_upload_pic2);
	//	comment_num = (TextView) head.findViewById(R.)
	//	redirect_num = (TextView) head.findViewById(R.)
//		TextView comment_num,redirect_num;//条数
		tweet_updated = (TextView) head.findViewById(R.id.tweet_updated);
		tweet_via = (TextView) head.findViewById(R.id.tweet_via);
//		tvtitle = (TextView) head.findViewById(R.id.tvinfo);
//		tvtitle.setText(R.string.weiboinfo);
//			tvReload=(TextView) view.findViewById(R.id.tvReload);
//			tvComment=(TextView) this.findViewById(R.id.tvComment);
//			tvForward=(TextView) this.findViewById(R.id.tvForward);
//			tvFav=(TextView) this.findViewById(R.id.tvFav);
//			tvMore=(TextView) this.findViewById(R.id.tvMore);
		tweet_profile = (RelativeLayout) head.findViewById(R.id.tweet_profile);
      
		tweetstatusview=(LinearLayout) head.findViewById(R.id.src_text_block);
	
			//底部菜单点击事件
			//tvReload.setOnClickListener(new textclick());
			//tvComment.setOnClickListener(new textclick());
			//tvForward.setOnClickListener(new textclick());
			//tvFav.setOnClickListener(new textclick());
			//tvMore.setOnClickListener(new textclick());
			
//			COMMENT=(BUTTON) THIS.FINDVIEWBYID(R.ID.DETAIL_COMMENT);
//			REDIRECT=(BUTTON) THIS.FINDVIEWBYID(R.ID.DETAIL_REDIRECT);
		
		
		View bottom = LayoutInflater.from(this).inflate(R.layout.itembottom, null);
		// 标题头的布局
		View title = this.findViewById(R.id.freelook_title_home);
		titleprogressBar = (ProgressBar) title.findViewById(R.id.titleprogressBar);
		// 中间显示信息View
		TextView tvname=(TextView) title.findViewById(R.id.tvinfo);
		// 设置登陆用户名称
		tvname.setText(MainService.nowuser.getScreenName());
		// 写微博按钮
		ImageView backWeibo = (ImageView) title.findViewById(R.id.title_bt_left);
		backWeibo.setImageResource(R.drawable.title_back);
		// 刷新按钮
		btrefaush = (ImageView) title.findViewById(R.id.title_bt_right);
		btrefaush.setImageResource(R.drawable.ic_btn_refresh);
		// 底部进度条
		progressBar = (ProgressBar) bottom.findViewById(R.id.progressBar);
		// 将bottom 添加到ListView中的底部
		weibolist.addFooterView(bottom);
		weibolist.addHeaderView(head);
		
		adapter = new commentAdapter(this, (List<Comment>) (new ArrayList<Comment>()) );
		weibolist.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		moreweibo = (LinearLayout) bottom.findViewById(R.id.moreweibo);
		loginprogress = this.findViewById(R.id.loginprogres);
		// 点击底部更多布局
		moreweibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 请求页面++
				nowPage++;
				init(); // 再次请求
				// 设置进度条可见
				progressBar.setVisibility(View.VISIBLE);
				// 设置刷新按钮不可见 此时进度条可见
				btrefaush.setVisibility(View.GONE);
			}
		});
	
		backWeibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				weiboContent.this.finish();
			}
		});
		
		btrefaush.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		
//		weibolist.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, 
//					int position, long id) {
//			
//				Status nowstu = (Status) parent.getAdapter().getItem(position);
//				Intent intent = new Intent(HotWordsActivity.this, WeiboInfo.class);
//				// 发送到weiboInfo
//				intent.putExtra("status", nowstu.getJson());
//				HotWordsActivity.this.startActivity(intent);	
//			}
//		});
	}
	@Override
	public void init() {
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("ID", status.getId());
		
		Task task = new Task(Task.TASK_GET_COMMENT, param);
		
	    MainService.allTask.add(task);
		
		btrefaush.setVisibility(View.GONE);
		titleprogressBar.setVisibility(View.VISIBLE);
		
	}
	// 刷新主页信息
	public void refresh() {
		nowPage = 1;
		init();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*此处是菜单项的设置*/
		menu.add(1, SETTING, 0, R.string.menu_setting).setIcon(
				R.drawable.setting);
		menu.add(1, ACCOUNT, 1, R.string.menu_switchuser).setIcon(
				R.drawable.switchuser);
		menu.add(1, OFICEAWEIBO, 2, R.string.menu_officialweibo).setIcon(
				R.drawable.officialweibo);
		menu.add(1, COMMONT, 0, R.string.menu_comment).setIcon(
				R.drawable.comment);
		menu.add(1, ABOUTWEIBO, 1, R.string.menu_aboutweibo).setIcon(
				R.drawable.aboutweibo);
		menu.add(1, EXIT, 2, R.string.exit_app).setIcon(R.drawable.menu_exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		int flag = ((Integer) param[0]).intValue();
		switch(flag) {
		case REFRESH_WEIBO:
			btrefaush.setVisibility(View.GONE);
			titleprogressBar.setVisibility(View.GONE);
			if (nowPage == 1) {
				loginprogress.setVisibility(View.GONE);

				List<Comment> nowStatus = (List<Comment>) param[1];
				
				adapter.addmoreData(nowStatus);
				adapter.notifyDataSetChanged();
			}
			else {
				progressBar.setVisibility(View.GONE);
				adapter.addmoreData((List<Comment>) param[1]);
				adapter.notifyDataSetChanged();
			}
		}
	}
	
}
