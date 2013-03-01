package com.example.logic;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.example.R;
import com.example.db.WeiboDbAdapter;
import com.example.ui.HomeActivity;
import com.example.ui.HotWordsActivity;
import com.example.ui.Login;
import com.example.ui.User;
import com.example.ui.logic.main.TaskGetCloseFriends;
import com.example.ui.logic.main.TaskGetComment;
import com.example.ui.logic.main.TaskGetFreshWeibo;
import com.example.ui.logic.main.TaskGetSimilarTastes;
import com.example.ui.logic.main.TaskGetUserHomeTimeLine;
import com.example.ui.logic.main.TaskGetUserHotWords;
import com.example.ui.logic.main.TaskUserLogin;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboException;


public class MainService extends Service implements Runnable
{
	public static Weibo weibo;
	public static User nowuser;
	public static long min_time_id = -1;
	public static long max_time_id = -1;
	public static WeiboDbAdapter mWeiboDbAdapter = null;
	
	// 将当前的activity加到Service中方便管理和调用
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();
	
	// 将所有任务放到任务集合中
	public static ArrayList<Task> allTask = new ArrayList<Task>();
	
	// 遍历所有activity 根据名称在allActivity 中找到需要的activity
	public static Activity getActivityByName(String name) {
		for(Activity ac : allActivity) {
			System.out.println(name + "         " + ac.getClass().getName().indexOf(name) + " " + ac.getClass().getName());
			if (ac.getClass().getName().indexOf(name) >= 0) {
				Log.i("status", ACTIVITY_SERVICE.getClass().getName()
						.toString());
				return ac;
			}
		}
		return null;
	}
	
	// 将当前任务加到任务集合中
	public static void newTask(Task task) {
		allTask.add(task);	
	}
	private void startDb() {
		if(mWeiboDbAdapter == null){
		    mWeiboDbAdapter = new WeiboDbAdapter(this);
			mWeiboDbAdapter.open();		
		}
	}
		
	
	
	// 线程开关
	public boolean isrun=false;
	
	private static Handler handler = new Handler() {
		@SuppressLint("UseValueOf")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.TASK_USER_LOGIN:
					IWeiboActivity login = (IWeiboActivity) MainService
						.getActivityByName("Login");
					login.refresh(new Integer(Login.REFRESH_LOGIN), msg.obj);
					break;
			case Task.TASK_GET_USER_HOMETIMEINLINE:
					IWeiboActivity ia = (IWeiboActivity) MainService
						.getActivityByName("HomeActivity");
					ia.refresh(HomeActivity.REFRESH_WEIBO, msg.obj);
					break;
			case Task.TASK_GET_HOT_WORD:
					IWeiboActivity hotWords = (IWeiboActivity) MainService
						.getActivityByName("HotWordsActivity");
					hotWords.refresh(HotWordsActivity.REFRESH_WEIBO, msg.obj);
					break;
			case Task.TASK_GET_CloseFriends_WORD:
					IWeiboActivity closeFriends = (IWeiboActivity) MainService
						.getActivityByName("CloseFriendsActivity");
					closeFriends.refresh(HotWordsActivity.REFRESH_WEIBO, msg.obj);
					break;
			case Task.TASK_SimilarTastes:
					IWeiboActivity similiarTastes = (IWeiboActivity) MainService
						.getActivityByName("SimilarTastesActivity");
					similiarTastes.refresh(HotWordsActivity.REFRESH_WEIBO, msg.obj);
					break;
			case Task.TASK_GET_COMMENT:
					IWeiboActivity tmp = (IWeiboActivity) MainService.getActivityByName("weiboContent");
					tmp.refresh(1, msg.obj);
					break;
			case Task.TASK_GET_FRESHWEIBO:
					IWeiboActivity new_weibo = (IWeiboActivity) MainService.getActivityByName("HomeActivity");
					new_weibo.refresh(2, msg.obj);
				break;
			/*
			case Task.TASK_SEARCH_WEIBO:
					IWeiboActivity ia2 = (IWeiboActivity) MainService	
						.getActivityByName("SearchUser");
					ia2.refresh(SearchUser.SEARCH_WEIBO, msg.obj);
					break;
			*/
			}
		}
	};
	
	public static Handler getHandler() {
		return handler;
	}
	private void doTask(Task task) throws WeiboException  {
		Message mess = handler.obtainMessage();
		mess.what = task.getTaskID(); // 将当前任务的ID 放到Message中
		//MyRequestListener requestListener_t = new MyRequestListener();
		switch (task.getTaskID()) {
			case Task.TASK_USER_LOGIN:// 得到登陆任务
				// 执行登录
				TaskUserLogin _obj = new TaskUserLogin(mess);
			    break;
			case Task.TASK_GET_USER_HOMETIMEINLINE:// 得到刷新主页面信息的任务		
				TaskGetUserHomeTimeLine _taskGetUserHomeTimeLine = new TaskGetUserHomeTimeLine( HomeActivity.pageSize, 
						(Long) task.getTaskParam().get("maxId"), mess);
				break;
			case Task.TASK_GET_FRESHWEIBO:
				System.out.println("TASK_GET_REFRESHWEIBO start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				TaskGetFreshWeibo _t = new TaskGetFreshWeibo((Integer) task.getTaskParam().get("pageSize"), 
						(Integer) task.getTaskParam().get("nowPage"), mess);
				break;
			case Task.TASK_GET_HOT_WORD:// 得到圈中热语任务
				
				TaskGetUserHotWords _taskGetUserHotWords = new TaskGetUserHotWords((Integer) task.getTaskParam().get("pageSize"), 
						(Integer) task.getTaskParam().get("nowPage"), mess);
				break;
			case Task.TASK_GET_CloseFriends_WORD:// 得到密友寻踪任务
				TaskGetCloseFriends _task3 = new TaskGetCloseFriends((Integer) task.getTaskParam().get("pageSize"), 
						(Integer) task.getTaskParam().get("nowPage"), mess);
				break;
			case Task.TASK_SimilarTastes:// 得到相似口味任务				
				TaskGetSimilarTastes _task4 = new TaskGetSimilarTastes((Integer) task.getTaskParam().get("pageSize"), 
						(Integer) task.getTaskParam().get("nowPage"), mess);
				break;
			case Task.TASK_GET_COMMENT:
				System.out.println("TASK_GET_COMMENT start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				TaskGetComment _taskComment = new TaskGetComment(Long.valueOf((String) task.getTaskParam().get("ID")), mess);
				break;
		/*
		 * 
		 *  case Task.TASK_SEARCH_WEIBO:
			Paging paging = new Paging(
					(Integer) task.getTaskParam().get("nowPage"), 
					(Integer) task.getTaskParam().get("pageSize"));
			String content = (String) task.getTaskParam().get("content");
			List<Status> searchweibo = List<Status>
			mess.obj = searchweibo;
			break;
		*/
			
		}
		//handler.sendMessage(mess);// 发送当前消息	
		allTask.remove(task);//  当前任务执行完毕把任务从任务集合中remove 不会重复执行 
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("token","service on created");
		isrun = true;// 启动线程
		startDb();
		Thread t = new Thread(this);
		t.start();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.stopSelf();  // 停止服务
		isrun = false;  // 关闭线程
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isrun) {
			Task lastTask = null;
			synchronized (allTask) {
				if (allTask.size() > 0) {
					lastTask = allTask.get(0);
					Log.i("yanzheng", "任务ID" + lastTask.getTaskID());
					try {
						doTask(lastTask);
					} catch (WeiboException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		// 每隔一秒钟检查是否有任务
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}		
	}
		
		
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 退出应用程序
	 * @param context
	 */
	public static void exitAPP(Context context) {
		Intent it = new Intent("com.example.logic.MainService");
		context.stopService(it);//ͣ 停止服务
		// 杀死进程   我感觉这种方式最直接了当
		android.os.Process.killProcess(android.os.Process.myPid());
		for (Activity activity : allActivity) {// 遍历所有activity 一个一个删除 
			activity.finish();
		}
	}
	
	/**
	 * 网络连接yichan
	 * @param context
	 */
	public static void AlertNetError(final Context  context) 
	{
		
		// TODO Auto-generated method stub
		AlertDialog.Builder alerError = new AlertDialog.Builder(context);
		alerError.setTitle(R.string.main_fetch_fail);
		alerError.setMessage(R.string.NoSignalException);
		alerError.setNegativeButton(R.string.apn_is_wrong1_exit,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						exitAPP(context);
					}
				});
		alerError.setPositiveButton(R.string.apn_is_wrong1_setnet,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						context.startActivity(new Intent(
								android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		alerError.create().show();
	}		
}

	
