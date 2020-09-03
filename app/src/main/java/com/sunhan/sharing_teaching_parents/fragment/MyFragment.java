package com.sunhan.sharing_teaching_parents.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.mypage.Login1Activity;
import com.sunhan.sharing_teaching_parents.activities.mypage.PageRequestActivity;
import com.sunhan.sharing_teaching_parents.activities.mypage.UserDetailActivity;
import com.sunhan.sharing_teaching_parents.util.Constant;

/**
 * Created by 孙汉 on 2019-07-02/15/35
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    View view;

    //@BindView(R.id.button1)
    Button button1;
    //@BindView(R.id.button2)
    Button button2;
    //@BindView(R.id.button3)
    Button button3;
    //@BindView(R.id.my_order)
    View my_order;
    //@BindView(R.id.my_wallet)
    View my_wallet;
    //@BindView(R.id.my_publish)
    View my_publish;
    //@BindView(R.id.my_bespeak)
    View my_bespeak;
    //@BindView(R.id.my_attention)
    View my_attention;
    //@BindView(R.id.my_teacher)
    View my_teacher;
    //@BindView(R.id.item1)
    View item1;
    //@BindView(R.id.item2)
    View item2;
    //@BindView(R.id.item3)
    View item3;
    //@BindView(R.id.item4)
    View item4;
    //@BindView(R.id.item5)
    View item5;
    //@BindView(R.id.item6)
    View item6;
    //@BindView(R.id.item_login)
    LinearLayout item_login;

    //登录状态
    TextView my_login_tv1;
    TextView my_login_tv2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        //ButterKnife.bind(getActivity());
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(),"注销后执行此步骤", Toast.LENGTH_SHORT).show();
        initLoginStatus();
    }

    private void initLoginStatus() {
        my_login_tv1 = view.findViewById(R.id.my_login_tv1);
        my_login_tv2 = view.findViewById(R.id.my_login_tv2);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id", null);//(key,若无数据需要赋的值)
        String password = sharedPreferences.getString("password", null);
        //Constant.IS_LOGIN = sharedPreferences.getInt("isLogin", 0);
        if (Constant.IS_LOGIN == 1) {
            my_login_tv1.setText(user_id);
            my_login_tv2.setText(password);
        } else {
            my_login_tv1.setText("立即登录");
            my_login_tv2.setText("登录/注册后使用更多功能");
        }
    }

    private void initView() {
        item_login = view.findViewById(R.id.item_login);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        my_order = view.findViewById(R.id.my_order);
        my_wallet = view.findViewById(R.id.my_wallet);
        my_publish = view.findViewById(R.id.my_publish);
        my_bespeak = view.findViewById(R.id.my_bespeak);
        my_attention = view.findViewById(R.id.my_attention);
        my_teacher = view.findViewById(R.id.my_teacher);
        item1 = view.findViewById(R.id.my_item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        item5 = view.findViewById(R.id.item5);
        item6 = view.findViewById(R.id.item6);

        item_login.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        my_order.setOnClickListener(this);
        my_wallet.setOnClickListener(this);
        my_publish.setOnClickListener(this);
        my_bespeak.setOnClickListener(this);
        my_attention.setOnClickListener(this);
        my_teacher.setOnClickListener(this);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
        item6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Constant.IS_LOGIN == 0) {
            if (v.getId() == R.id.item_login) {
                //Toast.makeText(getActivity(), "马上进入登录界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Login1Activity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "请先登录后，才能查看相应功能", Toast.LENGTH_SHORT).show();
            }
        } else {
            switch (v.getId()) {
                case R.id.item_login:
                    Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button1:
                    Intent intent1 = new Intent(getActivity(), PageRequestActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.button2:
                    Intent intent2 = new Intent(getActivity(), PageRequestActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.button3:
                    Intent intent3 = new Intent(getActivity(), PageRequestActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    }
}
