package com.example.ui;

import java.util.HashMap;
import com.example.R;
import com.example.logic.IWeiboActivity;
import com.example.logic.MainService;
import com.example.logic.Task;
import com.example.util.AccessTokenKeeper;
import com.example.util.MyContext;
import com.example.util.WeiboUtil;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("ShowToast")
public class Login extends Activity implements IWeiboActivity {

	private static final String CONSUMER_KEY = "172443491";
	private static final String REDIRECT_URL = "http://www.sina.com.cn";
	private  static Weibo mWeibo;
	public static Oauth2AccessToken accessToken;
	@SuppressWarnings("unused")
	private Button authBtn,loging;
	public static String timeline="not changed";
	public ProgressDialog pd;
	private String token;
	private long expirein;
	private CheckBox autologin;// 自动登录选择框
	boolean isautologin;
	public  Dialog	 dialog;
	public EditText editText;
    public static final int REFRESH_LOGIN=1;//  登录
    public static Context context;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("Login onCreate .....................");
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this); // 将当前的activity 添加到Service 的 acitivity集合中
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);

		context = (MyContext)  this.getApplication();
		
		setContentView(R.layout.login);
		
		
		CookieSyncManager.createInstance(Login.this);
		CookieManager.getInstance().removeAllCookie();
		
		Button btlogin=(Button)this.findViewById(R.id.login_Button);
		btlogin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("***********************()()()()()()()()()()()*************************************");
					System.out.println("exit_flag " + AccessTokenKeeper.exist_flag);
					if( ! accessToken.isSessionValid() )  {
						System.out.println("hello world   accessToken get Token");
						mWeibo.authorize(Login.this, new AuthDialogListener());
					}
					else  {
						goHome();
					}
				}
			});	
			
	}
	public void dialogshow(){
		Log.i("token","on dialogshow");
	    View dialogview=LayoutInflater.from(Login.this).inflate(R.layout.dialogshow, null);
	    dialog= new Dialog(Login.this,R.style.oauthdialog);
		dialog.setContentView(dialogview);
		Button btstart=(Button)dialogview.findViewById(R.id.btn_start);
		
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		btstart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWeibo.authorize(Login.this, new AuthDialogListener());
			}
		});
	}
	public class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			Login.accessToken = new Oauth2AccessToken(token, expires_in);
		//	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			AccessTokenKeeper.keepAccessToken(context, Login.accessToken);
			Toast.makeText(Login.this,"token is  saved", Toast.LENGTH_SHORT).show();
			
			dialog.dismiss();// 要先关闭dialog 否则窗体会泄露
			editText.setText("您已经验证过了");
		}

		@Override
		public void onWeiboException(WeiboException e) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void onError(WeiboDialogError e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("token","on resume");
		super.onResume();
		System.out.println("Login onResume");
		init(); // 
	}

	@SuppressLint("NewApi")
	@Override
	public void init() {
		// TODO Auto-generated method stub
		Log.i("token","on init");
		autologin=(CheckBox) this.findViewById(R.id.auto_login);
    	autologin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
  			@Override
  			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
  				AccessTokenKeeper.savaautoLogin(context, isChecked);
  			}
  		});
        isautologin = AccessTokenKeeper.getauto(context);
		editText = (EditText) this.findViewById(R.id.user);
		accessToken = AccessTokenKeeper.readAccessToken(context);
		
		if (WeiboUtil.checkNet(Login.this)) 
		{
			Log.i("token","startservice qian");
			Intent it=new Intent(this,MainService.class);
			this.startService(it);
			Log.i("token","startservice hoy");
			if (!accessToken.getToken().isEmpty())
			{
				editText.setText("username");
				Log.i("token",accessToken.getToken());
				token=accessToken.getToken();
				expirein=accessToken.getExpiresTime();
			}
			else
			{// 如果没有
				dialogshow();// 弹出认证Dialog
			}
			// 
		}
	    else {
		  	
			MainService.AlertNetError(this);
			
		}
	}

	@SuppressLint("ShowToast")
	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		int flag=((Integer)param[0]).intValue();// 获得第一个参数
		switch (flag) {
        case REFRESH_LOGIN:
    	   Toast.makeText(Login.this, "登录成功", 3000).show();
    		if (pd!=null) {
    			pd.dismiss(); 
			}
    		
    		Intent it=new Intent(this, MainActivity.class);
    		this.startActivity(it);
    		MainService.allActivity.remove(this);
    		finish();
    		break;
		}	
	}
	@SuppressWarnings("unchecked")
	public void goHome()	{
		if (pd==null) {
			pd = new ProgressDialog(Login.this);
		}
		pd.setMessage("正在登录中。。。。");
		pd.show();
		@SuppressWarnings("rawtypes")
		HashMap parm=new HashMap();
		parm.put("token",token);// 登陆请求参数token
		parm.put("expirein", expirein);//   登录请求参数secret  
		// 将map放到Task参数中传递到Service中
		Task loginTask=new Task(Task.TASK_USER_LOGIN, parm);
		MainService.newTask(loginTask);//  将当前任务发送到Service 的任务队列中
	}
}
