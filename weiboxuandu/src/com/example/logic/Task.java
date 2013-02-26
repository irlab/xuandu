package com.example.logic;

import java.util.Map;

public class Task {

	private int taskID;// 任务ID
	@SuppressWarnings("rawtypes")
	private Map taskParam;// 内容参数
	public static final int TASK_USER_LOGIN=0;//  用户登录任务 
	public static final int TASK_GET_USER_HOMETIMEINLINE=1;//  获取用户首页信息
	public static final int TASK_SEARCH_WEIBO=2;//  搜索微博
	public static final int TASK_GET_HOT_WORD = 3; // 圈中热语
	public static final int TASK_GET_CloseFriends_WORD = 4; // 密友寻踪
	public static final int TASK_SimilarTastes = 5; // 相似口味
	public static final int TASK_GET_COMMENT = 6;
	
	
	@SuppressWarnings("rawtypes")
	public Task(int id,Map param)
	{   
	   this.taskID=id;
	   this.taskParam=param;
	}
	 
	public int getTaskID() {
		// TODO Auto-generated method stub
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	} 
	@SuppressWarnings("rawtypes")
	public Map getTaskParam() {
		return taskParam;
	}
	@SuppressWarnings("rawtypes")
	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}
}
