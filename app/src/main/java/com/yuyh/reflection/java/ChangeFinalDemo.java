package com.yuyh.reflection.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

public class ChangeFinalDemo {

    static void ChangeFinalDemo(Field field, Object newValue) throws Exception

    {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    static void ChangeFinalDemo(Method method) throws Exception

    {
        System.out.println("inmethod before method.getModifiers()="+method.getModifiers());
        method.setAccessible(true);
        Field field = Method.class.getDeclaredField("modifiers");
        field.setAccessible(true);
        field.setInt(method, method.getModifiers() & ~Modifier.FINAL);
        System.out.println("inmethod after method.getModifiers()="+method.getModifiers());
//        field.set(null, newValue);
    }

    public static void main(String args[]) throws Exception {

        System.out.println(Bean.INT_VALUE);
        ChangeFinalDemo(Bean.class.getField("INT_VALUE"), 200);
        System.out.println(Bean.INT_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.STRING_VALUE);
        ChangeFinalDemo(Bean.class.getField("STRING_VALUE"), "String_2");
        System.out.println(Bean.STRING_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.BOOLEAN_VALUE);
        ChangeFinalDemo(Bean.class.getField("BOOLEAN_VALUE"), true);
        System.out.println(Bean.BOOLEAN_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.OBJECT_VALUE);
        ChangeFinalDemo(Bean.class.getField("OBJECT_VALUE"), new Date());
        System.out.println(Bean.OBJECT_VALUE);

        System.out.println("----method---test()-----------");
        System.out.println("before method.getModifiers()="+Bean.class.getMethod("test").getModifiers());
        ChangeFinalDemo(Bean.class.getMethod("test"));
        System.out.println("after method.getModifiers()="+Bean.class.getMethod("test").getModifiers());

    }
}

class Bean {
    public static final int INT_VALUE = 100;
    public static final Boolean BOOLEAN_VALUE = false;
    public static final String STRING_VALUE = "String_1";
    public static final Object OBJECT_VALUE = "234";
    public static final void test(){

    }
}




