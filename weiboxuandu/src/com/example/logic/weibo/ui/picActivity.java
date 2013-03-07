package com.example.logic.weibo.ui;

import com.example.R;
import com.example.ui.logic.imaCache.Anseylodar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class picActivity extends Activity {

	MyImageView imageView;
	private Matrix m = new Matrix();
	float curScale = 1f;
	Bitmap bm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picactivity);

		Intent intent = this.getIntent();
		String url = (String) intent.getExtras().get("pic_path");

		imageView = (MyImageView) findViewById(R.id.img);
		imageView.init(this);
		imageView.setOnTouchListener(new TounchListener());
		
		Anseylodar anseylodar = new Anseylodar();
		anseylodar.showimgAnsy(imageView, url.toString());
		//imageView.setImageBitmap(bm);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		Button back = (Button) findViewById(R.id.buttonBack);
		back.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stu
				picActivity.this.finish();
			}
		});
	}

	public void init() {
		m.set(imageView.getImageMatrix());
	}

	private class TounchListener implements OnTouchListener {
		

		private PointF startPoint = new PointF();
		private Matrix matrix = m;
		private Matrix currentMatrix = new Matrix();

		private int mode = 0;
		private static final int DRAG = 1;
		private static final int ZOOM = 2;
		private float startDis = 0;
		private PointF midPoint;

		public boolean onTouch(View v, MotionEvent event) {
			m.set(imageView.getImageMatrix());
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mode = DRAG;
				currentMatrix.set(imageView.getImageMatrix());

				startPoint.set(event.getX(), event.getY());
				break;

			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					float dx = event.getX() - startPoint.x;
					float dy = event.getY() - startPoint.y;
					matrix.set(currentMatrix);
					matrix.postTranslate(dx, dy);

				} else if (mode == ZOOM) {
					float endDis = distance(event);
					if (endDis > 10f) {
						float scale = endDis / startDis;
						curScale = scale * curScale;

						matrix.set(currentMatrix);
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);

					}

				}

				break;

			case MotionEvent.ACTION_UP:
				mode = 0;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				mode = 0;
				float scale;
				Matrix m = imageView.getImageMatrix();
				float values[] = new float[9];
				m.getValues(values);
				float x = values[0];
				if (x < 0.2) {
					scale = 0.2f /x;
					matrix.postScale(scale, scale, midPoint.x, midPoint.y);
				} else if (x > 5) {
					scale = 5 / x;
					matrix.postScale(scale, scale, midPoint.x, midPoint.y);
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = ZOOM;
				startDis = distance(event);

				if (startDis > 10f) {
					midPoint = mid(event);
					currentMatrix.set(imageView.getImageMatrix());
				}

				break;

			}

			imageView.setImageMatrix(matrix);
			return true;
		}
	}

	private static float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		return FloatMath.sqrt(dx * dx + dy * dy);
	}

	private static PointF mid(MotionEvent event) {
		float midx = event.getX(1) + event.getX(0);
		float midy = event.getY(1) + event.getY(0);

		return new PointF(midx / 2, midy / 2);
	}
}
