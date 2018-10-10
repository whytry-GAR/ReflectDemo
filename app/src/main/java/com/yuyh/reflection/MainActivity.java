package com.yuyh.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyh.reflection.annotation.Inject;
import com.yuyh.reflection.annotation.InjectView;
import com.yuyh.reflection.annotation.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.hello)
    public TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Inject.inject(this);
//        Test.getDeclaredMethods();
//        Monkey monkey = new Monkey();
//        Object proxy =  Proxy.newProxyInstance(Monkey.class.getClassLoader(),Monkey.class.getInterfaces(),new MonkeyInvocationHandler(monkey));


    }

    @OnClick(R.id.hello)
    public void tvHelloClick(View v){
        Toast.makeText(this, "OnClick", Toast.LENGTH_LONG).show();
    }

}
