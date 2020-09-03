package com.sunhan.sharing_teaching_parents.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;

/**
 * Created by 孙汉 on 2019-07-14/10/54
 */
public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private View item1;
    private View item2;
    private View item3;
    private View item4;
    private View item5;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_teacherdetail);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        item4 = findViewById(R.id.item4);
        item5 = findViewById(R.id.item5);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_back_service:
                finish();
                break;
            case R.id.item1:
                ImageView imageView = v.findViewById(R.id.img1);
                TextView textView = v.findViewById(R.id.tv1);
                switch (textView.getText().toString()) {
                    case "已预约":
                        imageView.setImageResource(R.drawable.teacher_bespeak_nor);
                        textView.setText("预约");
                        textView.setTextColor(0x8A000000);
                        break;
                    case "预约":
                        imageView.setImageResource(R.drawable.teacher_bespeak_sel);
                        textView.setText("已预约");
                        textView.setTextColor(0xff09BA07);
                        break;
                }
                break;
            case R.id.item5:
                ImageView imageView5 = v.findViewById(R.id.img5);
                TextView textView5 = v.findViewById(R.id.tv5);
                switch (textView5.getText().toString()) {
                    case "已关注":
                        imageView5.setImageResource(R.drawable.teacher_attention_nor);
                        textView5.setText("关注");
                        textView5.setTextColor(0x8A000000);
                        break;
                    case "关注":
                        imageView5.setImageResource(R.drawable.teacher_attention_sel);
                        textView5.setText("已关注");
                        textView5.setTextColor(0xffd81e06);
                        break;
                }
                break;
        }
    }
}
