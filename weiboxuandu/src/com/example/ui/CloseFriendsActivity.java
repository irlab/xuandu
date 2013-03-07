package com.example.ui;



import java.util.HashMap;
import java.util.List;


import com.example.R;
import com.example.logic.IWeiboActivity;
import com.example.logic.MainService;
import com.example.logic.Task;
import com.example.logic.weibo.ui.WeiboInfo;
import com.example.logic.weibo.ui.WriteWeibo;
import com.example.logic.weibo.ui.weiboContent;
import com.example.ui.adapter.WeiboAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CloseFriendsActivity extends Activity implements IWeiboActivity {

	public static final int REFRESH_WEIBO = 1;
	public int nowPage = 1; // 当前第几页
	public int pageSize = 10;  // 每页条数
	private ListView weibolist; // 微博信息显示
	private View loginprogress; // 开始页进度条
	private LinearLayout moreweibo; // 底部更多项
	private ProgressBar progressBar; // 底部更多项的进度条
	private ProgressBar titleprogressBar; // 顶部进度条
	private WeiboAdapter adapter; // 微博信息的适配器
	private ImageView btrefaush; // 刷新微博的按钮
	
	// 设置信息常量
	public static final int SETTING = 1; //  设置
	public static final int ACCOUNT = 2; // 账号
	public static final int OFICEAWEIBO = 3; //   官方微博
 	public static final int COMMONT = 4; // 意见
	public static final int ABOUTWEIBO = 5; // 关于
	public static final int EXIT = 6; // 退出
	

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initView(); // 初始化布局
    	MainService.allActivity.add(this);
    	init();
	}
	
	// 初始化页面的一些基本布局
	private void initView() {
		weibolist = (ListView) this.findViewById(R.id.weibolist);
		View bottom = LayoutInflater.from(this).inflate(R.layout.itembottom, null);
		// 标题头的布局
		View title = this.findViewById(R.id.freelook_title_home);
		titleprogressBar = (ProgressBar) title.findViewById(R.id.titleprogressBar);
		// 中间显示信息View
		TextView tvname=(TextView) title.findViewById(R.id.tvinfo);
		// 设置登陆用户名称
		tvname.setText(MainService.nowuser.getScreenName());
		// 写微博按钮
		ImageView btwriteWeibo = (ImageView) title.findViewById(R.id.title_bt_left);
		btwriteWeibo.setImageResource(R.drawable.ic_btn_post_text);
		// 刷新按钮
		btrefaush = (ImageView) title.findViewById(R.id.title_bt_right);
		btrefaush.setImageResource(R.drawable.ic_btn_refresh);
		// 底部进度条
		progressBar = (ProgressBar) bottom.findViewById(R.id.progressBar);
		// 将bottom 添加到ListView中的底部
		weibolist.addFooterView(bottom);
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
	
		btwriteWeibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CloseFriendsActivity.this, WriteWeibo.class);
				CloseFriendsActivity.this.startActivity(intent);
			}
		});
		
		btrefaush.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		
		weibolist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
			
				Status nowstu = (Status) parent.getAdapter().getItem(position);
				Intent intent = new Intent(CloseFriendsActivity.this, weiboContent.class);
				// 发送到weiboInfo
				intent.putExtra("status", nowstu.getJson());
				CloseFriendsActivity.this.startActivity(intent);	
			}
		});
	}
	@Override
	public void init() {
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		param.put("nowPage", new Integer(nowPage));
		param.put("pageSize", new Integer(pageSize));
		
		Task task = new Task(Task.TASK_GET_CloseFriends_WORD, param);
		
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
				System.out.println(param[1]);
				List<Status> nowStatus = (List<Status>) param[1];
				adapter = new WeiboAdapter(this, nowStatus);
				weibolist.setAdapter(adapter);
			}
			else {
				progressBar.setVisibility(View.GONE);
				adapter.addmoreDate((List<Status>) param[1]);
			}
		}
	}
	
}
