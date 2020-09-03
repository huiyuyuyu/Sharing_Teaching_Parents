package com.sunhan.sharing_teaching_parents.activities;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.adapter.TeacherListAdapter;
import com.sunhan.sharing_teaching_parents.beans.DataBean;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;

import java.util.ArrayList;
import java.util.List;

import fj.dropdownmenu.lib.concat.DropdownI;
import fj.dropdownmenu.lib.ion.ViewInject;
import fj.dropdownmenu.lib.ion.ViewUtils;
import fj.dropdownmenu.lib.pojo.DropdownItemObject;
import fj.dropdownmenu.lib.utils.DropdownUtils;
import fj.dropdownmenu.lib.view.DropdownButton;
import fj.dropdownmenu.lib.view.DropdownColumnView;


public class TeachersListActivity extends BaseActivity implements View.OnClickListener, DropdownI.SingleRow, DropdownI.DoubleRow,
        DropdownI.ThreeRow, DropdownI.RandomView {

    public static final String SUBJECT_NAME = "subject_name";

    private ImageView back;

    @ViewInject(R.id.lvType)
    DropdownColumnView lvType;
    @ViewInject(R.id.lvSubject)
    DropdownColumnView lvSubject;
    @ViewInject(R.id.lvMultiple)
    DropdownColumnView lvMultiple;
    @ViewInject(R.id.lvCondition)
    DropdownColumnView lvCondition;
    @ViewInject(R.id.btnType)
    DropdownButton btnType;
    @ViewInject(R.id.btnSubject)
    DropdownButton btnSubject;
    @ViewInject(R.id.btnMultiple)
    DropdownButton btnMultiple;
    @ViewInject(R.id.btnCondition)
    DropdownButton btnCondition;
    View mask;

    //列表项
    ListView teacher_listview;
    private TeacherListAdapter teacherListAdapter;
    private List<TeacherInfo> teacherInfoslist = new ArrayList<>();

    @Override
    protected View getContentView() {
        return inflateView(R.layout.fragment_teacherlist);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();

        DropdownUtils.init(this, mask);
        ViewUtils.injectViews(this, mask);

        //高校分类
        lvType.setSingleRow(this)
                .setSingleRowList(DataBean.getType(), -1)  //单列数据
                .setButton(btnType) //按钮
                .show();
        //年级科目
        btnSubject.setText("年级科目");
        lvSubject.setRandom(this)
                .setRandomView(R.layout.subject_random_view)
                .setButton(btnSubject) //按钮
                .show();
        //综合排序
        lvMultiple.setSingleRow(this)
                .setSingleRowList(DataBean.getMultiple(), -1)  //单列数据
                .setButton(btnMultiple) //按钮
                .show();

        //条件筛选
        btnCondition.setText("条件筛选");
        lvCondition.setRandom(this)
                .setRandomView(R.layout.condition_random_view)
                .setButton(btnCondition) //按钮
                .show();

        Intent intent = getIntent();
        String subject_name = intent.getStringExtra(SUBJECT_NAME);
        if (subject_name != null) {btnSubject.setText(subject_name);}
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);

        lvType = findViewById(R.id.lvType);
        lvSubject = findViewById(R.id.lvSubject);
        lvMultiple = findViewById(R.id.lvMultiple);
        lvCondition = findViewById(R.id.lvCondition);
        btnType = findViewById(R.id.btnType);
        btnSubject = findViewById(R.id.btnSubject);
        btnMultiple = findViewById(R.id.btnMultiple);
        btnCondition = findViewById(R.id.btnCondition);
        mask = findViewById(R.id.mask);

        teacher_listview = findViewById(R.id.teacher_listview);
        final PullToRefreshLayout pullToRefreshLayout;
        pullToRefreshLayout = findViewById(R.id.pull_ToRefreshLayout);

        teacherInfoslist.add(new TeacherInfo(R.drawable.head_boy, "张鱼鱼建", "编号：17890",
                "重庆大学", "本科生", "2019-06-25", "勤奋好学",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_girl, "张教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_boy, "孙教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_boy, "雨教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherListAdapter = new TeacherListAdapter(this, teacherInfoslist);
        teacher_listview.setAdapter(teacherListAdapter);

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         for (int i = 0; i < 10; i++) {
                         teacherInfoslist.add(new TeacherInfo(R.mipmap.commodity_1, "孙教员" + i, "编号：17890",
                         "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                         "80元/小时"));
                         }
                         teacherListAdapter.notifyDataSetChanged();
                         setListViewHeightBasedOnChildren(fisrtpage_listview);*/

                        pullToRefreshLayout.finishRefresh();
                    }
                },2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            teacherInfoslist.add(new TeacherInfo(R.drawable.head_girl, "孙教员" + i, "编号：17890",
                                    "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                                    "80元/小时"));
                        }
                        teacherListAdapter.notifyDataSetChanged();
                        //setListViewHeightBasedOnChildren(fisrtpage_listview);
                        pullToRefreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_back_service:
                finish();
                break;
        }
    }

    /**
     * 单列表返回
     */
    @Override
    public void onSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("类型",singleRowObject.getValue());
    }

    /**
     * 双列表返回
     */
    @Override
    public void onDoubleSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("动物",singleRowObject.getValue());
    }

    @Override
    public void onDoubleChanged(DropdownItemObject doubleRowObject) {
        Log.d("动物子类",doubleRowObject.getValue());
    }

    /**
     * 三列表返回
     */
    @Override
    public void onThreeSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("省",singleRowObject.getValue());
    }

    @Override
    public void onThreeDoubleChanged(DropdownItemObject doubleRowObject) {
        Log.d("市",doubleRowObject.getValue());
    }

    @Override
    public void onThreeChanged(DropdownItemObject threeRowObject) {
        Log.d("区",threeRowObject.getValue());
    }

    @Override
    public void onRandom(View view) {
        switch (view.getId()){
            case R.layout.condition_random_view:
                Button btnRandom = (Button) view.findViewById(R.id.btnRandom);
                final EditText etRandom = (EditText) view.findViewById(R.id.etRandom);
                btnRandom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TeachersListActivity.this, etRandom.getText().toString(), Toast.LENGTH_SHORT).show();
                        DropdownUtils.hide();//点击后是否收起布局
                    }
                });
                break;

            case R.layout.subject_random_view:
                break;
        }

    }
}
