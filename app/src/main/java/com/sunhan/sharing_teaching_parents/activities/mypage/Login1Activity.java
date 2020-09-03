package com.sunhan.sharing_teaching_parents.activities.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.server.ConnectServer;
import com.sunhan.sharing_teaching_parents.server.HandleResponse;
import com.sunhan.sharing_teaching_parents.util.Constant;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-17/14/35
 */
public class Login1Activity extends BaseActivity implements View.OnClickListener {

    ImageView back;
    TextView password_login;
    TextView my_login;
    EditText et_username;
    EditText et_identifying_code;
    String username;
    String identifying_code;
    int loginMode = 0;
    TextView tv_personalLogin, tv_line1;
    TextView tv_organizationLogin, tv_line2;
    private HandleResponse loginResponse;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_login1);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initFigure();
        initView();
    }

    private void initView() {
        password_login = findViewById(R.id.password_login);
        password_login.setOnClickListener(this);
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        my_login = findViewById(R.id.my_login);
        my_login.setOnClickListener(this);
        et_username = findViewById(R.id.username);
        et_username.setOnClickListener(this);
        et_identifying_code = findViewById(R.id.identifying_code);
        et_identifying_code.setOnClickListener(this);

        tv_personalLogin = findView(R.id.tv_personalLogin);
        tv_personalLogin.setOnClickListener(this);
        tv_organizationLogin = findView(R.id.tv_organizationLogin);
        tv_organizationLogin.setOnClickListener(this);
        tv_line1=findViewById(R.id.tv_line1);
        tv_line2=findViewById(R.id.tv_line2);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_login:
                Intent intent = new Intent(Login1Activity.this, Login2Activity.class);
                startActivity(intent);
                break;
            case R.id.location_back_service:
                finish();
                break;
            case R.id.my_login:
                username = et_username.getText().toString();
                identifying_code = et_identifying_code.getText().toString();
                if(username.equals("")||identifying_code.equals("")){
                    Toast.makeText(this,"用户名或密码未填写，请检查后重试",Toast.LENGTH_SHORT).show();
                    return;
                }
                //数据库验证逻辑在这里写
                String mode = "login";
                List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
                paramsList.add(new BasicNameValuePair("NAME", username));
                paramsList.add(new BasicNameValuePair("IDENTIFYING_CODE", identifying_code));
                paramsList.add(new BasicNameValuePair("MODE", mode));
                paramsList.add(new BasicNameValuePair("TOKEN", Constant.TOKEN));
                paramsList.add(new BasicNameValuePair("TYPE", String.valueOf(loginMode)));
                new ConnectServer(paramsList, Constant.LOGIN_URL, loginResponse).execute();
                Toast.makeText(Login1Activity.this, "正在登录", Toast.LENGTH_SHORT).show();
                //login_simulate();//模拟登录
                break;
            case R.id.tv_personalLogin:
                tv_personalLogin.setTextColor(getResources().getColor(R.color.black));
                tv_organizationLogin.setTextColor(getResources().getColor(R.color.text_gray));
                tv_line2.setVisibility(View.GONE);
                tv_line1.setVisibility(View.VISIBLE);
                loginMode = Constant.PERSONAL_LOGIN;
                et_username.setText("saic_user");
                et_identifying_code.setText("123456");
                et_username.setSelection(et_username.getText().length());
                //Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_organizationLogin:
                tv_personalLogin.setTextColor(getResources().getColor(R.color.text_gray));
                tv_organizationLogin.setTextColor(getResources().getColor(R.color.black));
                tv_line2.setVisibility(View.VISIBLE);
                tv_line1.setVisibility(View.GONE);
                loginMode = Constant.ORGANIZATION_LOGIN;
                et_username.setText("saic_seller");
                et_identifying_code.setText("123");
                et_username.setSelection(et_username.getText().length());
                //Toast.makeText(this,"456",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void login_simulate() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", "张三");
        editor.putString("password", "初二物理");
        editor.putInt("isLogin", 1);
        Constant.IS_LOGIN = 1;
        editor.commit();
        Toast.makeText(Login1Activity.this, "模拟登录成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initFigure() {
        //获取设备token
        //Constant.TOKEN = XGPushConfig.getToken(getApplicationContext());
        loginResponse = new HandleResponse() {
            @Override
            public void getResponse(String response) {
                String res = response.trim();
                if (res.equals("1")) {
                    Toast.makeText(Login1Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Constant.USER_NAME = username;
                    Intent loginIntent;
                    switch (loginMode) {
                        case Constant.PERSONAL_LOGIN://个人登录;
                            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_id", "张三");
                            editor.putString("password", "初二物理");
                            editor.putInt("isLogin", 1);
                            editor.commit();
                            Constant.IS_LOGIN = 1;
                            Toast.makeText(Login1Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Constant.ORGANIZATION_LOGIN:
                            //执行机构登录
                            break;
                    }
                }
            }
        };
    }

}
