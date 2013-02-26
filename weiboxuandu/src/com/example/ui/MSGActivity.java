package com.example.ui;

import com.example.R;
import com.example.util.Exit;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MSGActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Exit.btexit(MSGActivity.this);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
