package com.dd.ddapplication;

import android.app.Application;

/**
 * Created by Administrator on 2017/9/16.
 */
public class MainApplication extends Application {
    private  static MainApplication INSTANCE ;
    public static MainApplication getInstance(){
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
