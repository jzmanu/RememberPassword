package com.manu.logodemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 记住密码的功能的简单实现
 */
public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener{
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private EditText et_username;
    private EditText et_password;
    private Button btn_logo;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isRemember;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_logo = (Button) findViewById(R.id.btn_logo);

        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);
        cb4.setOnCheckedChangeListener(this);
        btn_logo.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("remember",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkRememberState();
        setEditViewHintTextSize(et_username,"请输入用户名",16);
        setEditViewHintTextSize(et_password,"请输入密码",16);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb1:
                Toast.makeText(this, "默认样式："+isChecked, Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb3:
                Toast.makeText(this, "自定义样式一："+isChecked, Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb2:
                Toast.makeText(this, "自定义样式二："+isChecked, Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb4:
                editor.putBoolean("remember",isChecked);
                editor.commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logo:
                login();
                break;
        }
    }

    /**
     * 模拟登陆
     */
    private void login(){
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "用户名与密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("admin".equals(username) && "admin".equals(password)){
            saveLogoState();
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 检查记住密码的状态
     */
    private void checkRememberState(){
        isRemember = sharedPreferences.getBoolean("remember",false);
        if (isRemember){
            cb4.setChecked(true);
            username = sharedPreferences.getString("username","");
            password = sharedPreferences.getString("password","");
            et_username.setText(username);
            et_password.setText(password);
        }else{
            cb4.setChecked(false);
        }
    }

    /**
     * 保存登录状态
     */
    private void saveLogoState(){
        isRemember = sharedPreferences.getBoolean("remember",false);
        if (isRemember){
            editor.putString("username",username);
            editor.putString("password",password);
        }else{
            editor.putString("username","");
            editor.putString("password","");
        }
        editor.commit();
    }

    /**
     * 设置EditText提示文本的字体大小
     * @param editText
     */
    private void setEditViewHintTextSize(EditText editText, String hintText, int hintSize){
        //创建一个可以添加属性的文本对象
        SpannableString string = new SpannableString(hintText);
        //创建一个属性对象，修改文本字体大小
        //参数（字体大小，true）,true表示字体单位为dip
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(hintSize,true);
        //附加属性到文本
        string.setSpan(sizeSpan,0,string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文本字体（monospace、default、default-bold、serif、sans-serif）
        string.setSpan(new TypefaceSpan("default"), 0, hintText.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置hint
        editText.setHint(new SpannedString(string));
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            //点击空白位置隐藏软键盘
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
}
