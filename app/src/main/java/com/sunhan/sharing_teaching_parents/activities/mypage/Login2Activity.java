package com.sunhan.sharing_teaching_parents.activities.mypage;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.util.Constant;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-17/16/49
 */
public class Login2Activity extends BaseActivity implements View.OnClickListener {

    ImageView back;
    TextView identifying_code_login;

    TextView my_login;
    EditText et_username;
    EditText et_password;
    String username;
    String identifying_code;
    int loginMode = 0;
    TextView tv_personalLogin, tv_line1;
    TextView tv_organizationLogin, tv_line2;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_login2);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        identifying_code_login = findViewById(R.id.identifying_code_login);
        identifying_code_login.setOnClickListener(this);
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);

        my_login = findViewById(R.id.my_login);
        my_login.setOnClickListener(this);
        et_username = findViewById(R.id.username);
        et_username.setOnClickListener(this);
        et_password = findViewById(R.id.password);
        et_password.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.identifying_code_login:
                finish();
                break;
            case R.id.location_back_service:
                finish();
                break;
            case R.id.my_login:
                username = et_username.getText().toString();
                identifying_code = et_password.getText().toString();
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
                break;
            case R.id.tv_personalLogin:
                tv_personalLogin.setTextColor(getResources().getColor(R.color.black));
                tv_organizationLogin.setTextColor(getResources().getColor(R.color.text_gray));
                tv_line2.setVisibility(View.GONE);
                tv_line1.setVisibility(View.VISIBLE);
                loginMode = Constant.PERSONAL_LOGIN;
                et_username.setText("saic_user");
                et_password.setText("12345678");
                et_username.setSelection(et_username.getText().length());
                Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_organizationLogin:
                tv_personalLogin.setTextColor(getResources().getColor(R.color.text_gray));
                tv_organizationLogin.setTextColor(getResources().getColor(R.color.black));
                tv_line2.setVisibility(View.VISIBLE);
                tv_line1.setVisibility(View.GONE);
                loginMode = Constant.ORGANIZATION_LOGIN;
                et_username.setText("saic_seller");
                et_password.setText("123");
                et_username.setSelection(et_username.getText().length());
                Toast.makeText(this,"456",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
