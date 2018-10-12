package com.yuyh.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyh.reflection.annotation.Inject;
import com.yuyh.reflection.annotation.InjectView;
import com.yuyh.reflection.annotation.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.hello)
    public TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Inject.inject(this);
//        Test.getDeclaredMethods();
//        Monkey monkey = new Monkey();
//        Object proxy =  Proxy.newProxyInstance(Monkey.class.getClassLoader(),Monkey.class.getInterfaces(),new MonkeyInvocationHandler(monkey));


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TestEvent event) {
        Toast.makeText(this, "onEvent(TestEvent event)", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.hello)
    public void tvHelloClick(View v){
        Toast.makeText(this, "OnClick", Toast.LENGTH_LONG).show();

    }
    @OnClick(R.id.send_event)
    public void tvSendEventClick(View v){
        EventBus.getDefault().post(new TestEvent());
    }




    public class TestEvent{

    }

}
