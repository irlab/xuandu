package com.example.util;

import android.app.Application;

public class MyContext extends Application {
    private static MyContext context;

    public static MyContext getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = this;
    }
}