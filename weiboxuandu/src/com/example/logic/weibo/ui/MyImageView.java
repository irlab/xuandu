package com.example.logic.weibo.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	public int startTimes = 0;
	public int height;
	public int width;
	public picActivity pa;
	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
	}

	public void init(picActivity pa) {

		this.pa = pa;
	}
	
	@Override
	protected void onDraw(Canvas canvas) { // 这里就是重写的方法了，想画什么形状自己动手
		// TODO Auto-generated method stub		
		if(startTimes == 0){
			startTimes++;
			pa.init();
		}
		super.onDraw(canvas);
	}
}
