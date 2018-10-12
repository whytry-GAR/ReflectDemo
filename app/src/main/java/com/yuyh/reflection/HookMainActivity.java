package com.yuyh.reflection;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuyh.reflection.hook.ClipboardHook;
import com.yuyh.reflection.hook.EvilInstrumentation;

import java.lang.reflect.Field;


public class HookMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtInput;
    private Button mBtnCopy;
    private Button mBtnShowPaste;
    private Button mBtnStartActivity;
    private LinearLayout mActivityMain;
    ClipboardManager clipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        initView();
        ClipboardHook.hookService(this);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    private void initView() {
        mEtInput = (EditText) findViewById(R.id.et_input);
        mBtnCopy = (Button) findViewById(R.id.btn_copy);
        mBtnShowPaste = (Button) findViewById(R.id.btn_show_paste);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mBtnStartActivity = (Button) findViewById(R.id.btn_start_activity);
        mBtnCopy.setOnClickListener(this);
        mBtnShowPaste.setOnClickListener(this);
        mBtnStartActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy:
                String input = mEtInput.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(this, "input不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //复制
                ClipData clip = ClipData.newPlainText("simple text", mEtInput.getText().toString());
                clipboard.setPrimaryClip(clip);
                break;
            case R.id.btn_show_paste:
                //黏贴
                clip = clipboard.getPrimaryClip();
                if (clip != null && clip.getItemCount() > 0) {
                    Toast.makeText(this, clip.getItemAt(0).getText(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_start_activity:
                try {
//                    attactContext();
//                    getApplicationContext().startActivity(new Intent(this, MainActivity.class));

                    attactActivity(this);
                    startActivity(new Intent(this, MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    public static void attactContext() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);
        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }

    public static void attactActivity(Activity activity) throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);
        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = Activity.class.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(activity);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(activity, evilInstrumentation);
    }
}

