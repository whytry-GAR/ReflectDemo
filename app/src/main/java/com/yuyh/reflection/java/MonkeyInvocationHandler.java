package com.yuyh.reflection.java;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MonkeyInvocationHandler implements InvocationHandler {
    public Object object;

    public MonkeyInvocationHandler(Object object1) {
        this.object = object1;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("whh", "Invoke method Before22222! "+method.getName());
        Object returnObject = method.invoke(object, args);
        Log.i("whh", "Invoke method After222222!");
        return returnObject;
    }
}
