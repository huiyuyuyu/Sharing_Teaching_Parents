package com.sunhan.sharing_teaching_parents.activities.publish;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.PermissionSettingPage;
import com.hjq.permissions.XXPermissions;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.activities.MainActivity;

import java.io.File;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-28/19/23
 */
public class RealNameActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvPic;
    ImageView back;
    private View item1;
    private TextView tv_identify;
    private Button btn_fan;

    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_realname);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        mIvPic = findView(R.id.imageView23);
        item1 = findViewById(R.id.item1);
        item1.setOnClickListener(this);
        tv_identify = findViewById(R.id.tv_identify);
        btn_fan = findViewById(R.id.btn_fan);
        btn_fan.setOnClickListener(this);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.location_back_service:
                finish();
                break;
            case R.id.item1:
                PopWindow popWindow = new PopWindow.Builder(this)
                        .setStyle(PopWindow.PopWindowStyle.PopUp)
                        .setTitle("选择")
                        .addItemAction(new PopItemAction("身份证", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {
                                tv_identify.setText("身份证");
                            }
                        }))
                        .addItemAction(new PopItemAction("护照", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {
                                tv_identify.setText("护照");
                            }
                        }))
                        .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                        .create();
                popWindow.show();
                break;
            case R.id.btn_fan:
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it, 100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            Bundle bundle = data.getExtras();
            Bitmap bmp = (Bitmap) bundle.get("data");
            ImageView img_fan = findViewById(R.id.img_fan);
            img_fan.setImageBitmap(bmp);
        } else {
            //Toast.makeText(this, "没有拍到照片", Toast.LENGTH_LONG).show();
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            Bundle bundle = data.getExtras();
            Bitmap bmp = (Bitmap) bundle.get("data");
            mIvPic.setImageBitmap(bmp);
        } else {
            //Toast.makeText(this, "没有拍到照片", Toast.LENGTH_LONG).show();
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 300) {
            Uri sourceUri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(sourceUri, proj, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imgPath = cursor.getString(columnIndex);
            Uri mOutputUri = Uri.fromFile(new File(imgPath));
            Glide.with(this).load(mOutputUri).into(mIvPic);
        } else {
            //Toast.makeText(this, "没有拍到照片", Toast.LENGTH_LONG).show();
        }
    }

    public void click1(View view) {
        PopWindow popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setTitle("选择")
                .addItemAction(new PopItemAction("拍照", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        takePhoto();//拍照
                    }
                }))
                .addItemAction(new PopItemAction("相册", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        selectPhoto();
                    }
                }))
                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                .create();
        popWindow.show();
    }

    private void selectPhoto() {
        if (XXPermissions.isHasPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 300);
        } else {
            XXPermissions.with(this)
                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                Toast.makeText(RealNameActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                            }else {
                                //ToastUtils.show("获取权限成功，部分权限未正常授予");
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if(quick) {
                                //ToastUtils.show("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(RealNameActivity.this);
                            }else {
                                //ToastUtils.show("获取权限失败");
                            }
                        }
                    });
        }
    }

    private void takePhoto() {
        if (XXPermissions.isHasPermission(this, Permission.Group.CAMERA)) {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(it, 200);
        } else {
            //Toast.makeText(this, "没有拍照权限", Toast.LENGTH_SHORT).show();
            XXPermissions.with(this)
                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(new String[]{Manifest.permission.CAMERA}) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                Toast.makeText(RealNameActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                            }else {
                                //ToastUtils.show("获取权限成功，部分权限未正常授予");
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if(quick) {
                                //ToastUtils.show("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(RealNameActivity.this);
                            }else {
                                //ToastUtils.show("获取权限失败");
                            }
                        }
                    });
        }

    }
}
