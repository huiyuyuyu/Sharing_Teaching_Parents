package com.sunhan.sharing_teaching_parents.activities.publish;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;

/**
 * Created by 孙汉 on 2019-07-28/14/56
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private View item1;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_publish);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        item1 = findViewById(R.id.item1);
        item1.setOnClickListener(this);
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
            case R.id.item1:
                Intent intent = new Intent(this, RealNameActivity.class);
                startActivity(intent);
                break;
        }
    }


}
