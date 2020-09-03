package com.sunhan.sharing_teaching_parents.activities.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.util.Constant;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.io.ByteArrayInputStream;

/**
 * Created by 孙汉 on 2019-07-19/18/46
 */
public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

    ImageView back;
    Button btn_logout;
    View item_head;
    ImageView imageView_head;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_userdetail);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        item_head = findViewById(R.id.item_head);
        item_head.setOnClickListener(this);
        imageView_head = findViewById(R.id.imageView_head);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_back_service:
                finish();
                break;
            case R.id.btn_logout:
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(this);
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("isLogin", 0);
                        Constant.IS_LOGIN = 0;
                        Toast.makeText(UserDetailActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                rxDialogSureCancel.show();
                break;
            case R.id.item_head:
                Intent intent = new Intent(this, HeadImageChangeActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("student", MODE_PRIVATE);
        byte[] imagByte = Base64.decode(sharedPreferences.getString("productImg",""), Base64.DEFAULT);
        ByteArrayInputStream bais2 = new ByteArrayInputStream(imagByte);
        imageView_head.setImageDrawable(Drawable.createFromStream(bais2,  "imagByte"));
    }
}
