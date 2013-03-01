package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    
	public static String showTime(Date t) {
		String time = "";
		
		long nowTimeMillis = System.currentTimeMillis();
		long oldTimeMillis = t.getTime();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		
		long timeOffsetOfSecond = (nowTimeMillis - oldTimeMillis)/1000;
		
		
		if (timeOffsetOfSecond < 60) {
			time = (timeOffsetOfSecond < 0 ? 1 : timeOffsetOfSecond) + "秒前";
		}
		else if (timeOffsetOfSecond < 3600){
			time = timeOffsetOfSecond / 60 + "分钟前";
		}
		else if (timeOffsetOfSecond < 86400){ // 3600*24
			time = "今天" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE); 
		}
		else {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			time = df.format(t);
		}
		return time;
	}
}