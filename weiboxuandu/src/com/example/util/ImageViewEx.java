package com.example.util;

import android.content.Context;
import android.widget.ImageView;

public class ImageViewEx extends ImageView {
	public String url = "";
	public ImageViewEx(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
}
