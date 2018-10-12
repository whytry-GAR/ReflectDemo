package com.yuyh.reflection;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();

    }
}
