

在开发过程中都有 App 记住密码的功能，下面简单的实现一下记住密码的功能以及如何修改 CheckBox 的样式，测试效果参考如下：

![image](http://opvs5q5qo.bkt.clouddn.com/rememberPass.gif)

### 修改 CheckBox 的样式

#### 默认样式
直接使用 CheckBox ,其选中的颜色效果为 colorAccent 表示的颜色，参考如下：

``` xml
<CheckBox
    android:id="@+id/cb1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="记住密码"/>
```
#### 自定义样式一
在 values/styles.xml 中定义 MyCheckStyle ,并在 CheckBox 中引用，colorControlActivated 表示选中后的颜色，colorControlNormal 表示正常状态下也就是未选中时的颜色，参考如下：

``` xml
<style name="MyCheckStyle" parent="@android:style/Widget.CompoundButton.CheckBox">
    <item name="android:colorControlActivated">#1d24e9</item>
    <item name="android:colorControlNormal">#cfa4a4</item>
</style>
```
这样就可以随意修改 CheckBox 的样式了，在 CheckBox 的 android:theme 属性中引入自定义的 style:

``` xml
<CheckBox
    android:id="@+id/cb3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="记住密码"
    android:textSize="16sp"
    android:theme="@style/MyCheckStyle"/>
```
#### 自定义样式二
通过在 drawable 下定义 selector 资源，设置 CheckBox 的 android:button 属性，参考如下：

``` xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/remember_normal" android:state_checked="false"/>
    <item android:drawable="@drawable/remember_selected" android:state_checked="true"/>
</selector>

```
### 实现记住密码的功能
这里记住密码使用 CheckBox 来表示，下面是主要步骤，参考如下：

#### 第一步：
监听记录 CheckBox 的选中状态，并将其状态存储在   SharedPreferences 中，参考如下：

``` java
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()){
        case R.id.cb4:
            //保存 CheckBpx 的状态
            editor.putBoolean("remember",isChecked);
            editor.commit();
            break;
    }
}
```
#### 第二步：
2. 每次登陆获取保存的记住密码的状态，根据其状态判断是否需要自动填写密码以及初始化 CheckBox 的状态，参考如下：

```
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
```
#### 第三步：
3. 登录成功获取保存的记住密码的状态，根据其状态判断是否需要保存用户名和密码，参考如下：

``` java

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
```
### 参考代码
#### 布局文件

``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_login"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.manu.logodemo.MainActivity">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="20dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="默认样式"/>
        <CheckBox
            android:id="@+id/cb1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="记住密码"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="20dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="自定义样式一"/>
        <CheckBox
            android:id="@+id/cb3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="记住密码"
            android:textSize="16sp"
            android:theme="@style/MyCheckStyle"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="20dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="自定义样式二"/>
        <CheckBox
            android:id="@+id/cb2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="记住密码"
            android:button="@drawable/pass_selected"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="40dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:background="#e9d5d5"
        android:paddingLeft="30dp">
        <EditText
            android:id="@+id/et_username"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="请输入用户名"
            android:textSize="16sp"
            android:background="@null"/>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:paddingTop="10dp"
        android:paddingBottom="10dp"
        android:background="#e9d5d5"
        android:paddingLeft="30dp">
        <EditText
            android:id="@+id/et_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="请输入密码"
            android:inputType="textPassword"
            android:textSize="16sp"
            android:background="@null"/>
    </LinearLayout>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:paddingLeft="14dp">

        <CheckBox
            android:id="@+id/cb4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="记住密码"
            android:textSize="12sp"
            android:layout_alignParentRight="true"
            android:theme="@style/MyCheckStyle"/>
    </RelativeLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp">
        <Button
            android:id="@+id/btn_logo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="登录"
            android:textSize="20sp"
            android:textColor="#ffffff"
            android:background="#303ac4"/>
    </LinearLayout>
</LinearLayout>

```
#### MainActicity

``` java

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

```

<完>
