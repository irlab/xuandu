package com.example.logic.weibo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WriteWeibo extends Activity {
	
	ImageView back; // 返回键
	Button send; // 发送键
	EditText etblogEditText; // 微博信息
	LinearLayout updatelay; // 状态布局
	TextView tvnowtite; //提示信息
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
