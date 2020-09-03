package com.sunhan.sharing_teaching_parents.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.firstpage.SearchActivity;
import com.sunhan.sharing_teaching_parents.activities.TeachersListActivity;
import com.sunhan.sharing_teaching_parents.activities.firstpage.UniversitysListActivity;
import com.sunhan.sharing_teaching_parents.activities.firstpage.ChooseLocationPlace;
import com.sunhan.sharing_teaching_parents.adapter.BannerPagerAdapter;
import com.sunhan.sharing_teaching_parents.adapter.GridAdapter;
import com.sunhan.sharing_teaching_parents.adapter.TeacherListAdapter;
import com.sunhan.sharing_teaching_parents.adapter.ViewPageAdapter;
import com.sunhan.sharing_teaching_parents.entity.SubjectGridInfo;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;
import com.sunhan.sharing_teaching_parents.listener.ViewPagerListener;
import com.sunhan.sharing_teaching_parents.server.ConnectServer;
import com.sunhan.sharing_teaching_parents.server.HandleResponse;
import com.sunhan.sharing_teaching_parents.ui.Indicator;
import com.sunhan.sharing_teaching_parents.util.Constant;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-02/15/34
 */
public class FirstpageFragment extends Fragment implements View.OnClickListener {

    View view;
    private HandleResponse getTeacherListResponse;//从数据库获取新的老师列表

    //标题栏
    private View home_locationbar;
    private View home_searchbar;

    //广告轮播
    private ViewPager bannerPager;
    private Indicator bannerIndicator;
    private int[] imgRes = new int[]{R.drawable.banner01, R.drawable.banner02, R.drawable.banner03};
    private Handler mHandler = new Handler();

    //viewpager的科目选项
    private List<SubjectGridInfo> pageOneData = new ArrayList<>();
    private List<SubjectGridInfo> pageTwoData = new ArrayList<>();
    private List<SubjectGridInfo> pageThreeData = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();

    //三个按钮
    private Button button_daxuesheng;
    private Button button_mingxiao;
    private Button button_zhuanye;

    //列表项
    private ListView fisrtpage_listview;
    private List<String> list;
    private ListAdapter adapter;
    private TeacherListAdapter teacherListAdapter;
    private List<TeacherInfo> teacherInfoslist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_firstpage, container, false);
            initData();
            initFigure();
            initView();
            autoScroll();
        }
        return  view;
    }

    private void initData() {
        String[] gridTitles = getResources().getStringArray(R.array.firstpage_bar_labels);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.firstpage_bar_icon);
        for (int i = 0; i < gridTitles.length; i++) {
            if (i < 5) {
                pageOneData.add(new SubjectGridInfo(typedArray.getResourceId(i,0),gridTitles[i]));
            } else if (i < 10){
                pageTwoData.add(new SubjectGridInfo(typedArray.getResourceId(i,0),gridTitles[i]));
            } else {
                pageThreeData.add(new SubjectGridInfo(typedArray.getResourceId(i, 0), gridTitles[i]));
            }
        }
    }

    private void autoScroll() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerPager.getCurrentItem();
                bannerPager.setCurrentItem(currentItem+1, true);
                mHandler.postDelayed(this, 2000);
            }
        }, 2000);
    }

    private void initView() {
        ViewPager headPager = view.findViewById(R.id.secondpage_banner_pager);

        //新闻栏滚动
        bannerPager = view.findViewById(R.id.fisrtpage_banner_pager);
        bannerIndicator = view.findViewById(R.id.fisrtpage_banner_indicator);
        bannerPager.setAdapter(new BannerPagerAdapter(getChildFragmentManager(), imgRes));
        bannerPager.addOnPageChangeListener(new ViewPagerListener(bannerIndicator));

        home_locationbar = view.findViewById(R.id.home_titleBar_right_layout);
        home_searchbar = view.findViewById((R.id.home_titleBar_left_layout));
        button_daxuesheng = view.findViewById(R.id.daxuesheng);
        button_mingxiao = view.findViewById(R.id.mingxiao);
        button_zhuanye = view.findViewById(R.id.zhuanye);
        home_locationbar.setOnClickListener(this);
        home_searchbar.setOnClickListener(this);
        button_daxuesheng.setOnClickListener(this);
        button_mingxiao.setOnClickListener(this);
        button_zhuanye.setOnClickListener(this);

        //gridView实现功能方阵，第一页
        View pageOne = LayoutInflater.from(getActivity()).inflate(R.layout.fisrtpage_gridview,null);
        GridView gridView1 = pageOne.findViewById(R.id.firstpage_gridview);
        gridView1.setAdapter(new GridAdapter(getActivity(), pageOneData));
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        //gridView实现功能方阵，第二页
        View pageTwo = LayoutInflater.from(getActivity()).inflate(R.layout.fisrtpage_gridview,null);
        GridView gridView2 = pageTwo.findViewById(R.id.firstpage_gridview);
        gridView2.setAdapter(new GridAdapter(getActivity(),pageTwoData));
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        //gridView实现功能方阵，第三页
        View pageThree = LayoutInflater.from(getActivity()).inflate(R.layout.fisrtpage_gridview,null);
        GridView gridView3 = pageThree.findViewById(R.id.firstpage_gridview);
        gridView3.setAdapter(new GridAdapter(getActivity(),pageThreeData));
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        mViewList.add(gridView1);
        mViewList.add(gridView2);
        mViewList.add(pageThree);
        bannerIndicator = view.findViewById(R.id.secondpage_banner_indicator);
        headPager.addOnPageChangeListener(new ViewPagerListener(bannerIndicator));
        headPager.setAdapter(new ViewPageAdapter(mViewList));

        //下拉列表
        final PullToRefreshLayout pullToRefreshLayout;
        pullToRefreshLayout = view.findViewById(R.id.pull_ToRefreshLayout);
        fisrtpage_listview = view.findViewById(R.id.firstpage_listview);
        /**
        list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            list.add("PullToRefreshLayout"+i);
        }
        adapter = new ListAdapter(getActivity(), list);*/


        teacherInfoslist.add(new TeacherInfo(R.drawable.head_boy, "张鱼鱼建", "编号：17890",
                "重庆大学", "本科生", "2019-06-25", "勤奋好学",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_girl, "张教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_girl, "孙教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherInfoslist.add(new TeacherInfo(R.drawable.head_girl, "雨教员", "编号：17890",
                "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                "80元/小时"));
        teacherListAdapter = new TeacherListAdapter(getActivity(), teacherInfoslist);
        fisrtpage_listview.setAdapter(teacherListAdapter);
        setListViewHeightBasedOnChildren(fisrtpage_listview);

        //pullToRefreshLayout.autoRefresh();


        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获取新的老师列表
                        String mode = "getTeachers";
                        List<BasicNameValuePair> teacherlistParamList = new ArrayList<>();
                        teacherlistParamList.add(new BasicNameValuePair("MODE", mode));
                        teacherlistParamList.add(new BasicNameValuePair("TEACHER_LIST", "老师列表"));
                        new ConnectServer(teacherlistParamList, Constant.GET_TEACHER_LIST_URL, getTeacherListResponse).execute();

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
                            teacherInfoslist.add(new TeacherInfo(R.drawable.head_boy, "孙教员" + i, "编号：17890",
                                    "重庆师范大学", "在读研究生", "2019-07-10", "自我描述：xxxx飒飒xxxxx飒飒xxxxxx",
                                    "80元/小时"));
                        }
                        teacherListAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(fisrtpage_listview);
                        pullToRefreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        params.height += 5;// if without this statement,the listview will be a
        // little short
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_titleBar_left_layout:
                Intent intent_location = new Intent(getActivity(), ChooseLocationPlace.class);
                startActivity(intent_location);
                break;
            case R.id.home_titleBar_right_layout:
                Intent intent_search = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent_search);
                break;
            case R.id.daxuesheng:
                Intent intent_daxuesheng = new Intent(getActivity(), TeachersListActivity.class);
                startActivity(intent_daxuesheng);
                break;
            case R.id.mingxiao:
                Intent intent_mingxiao = new Intent(getActivity(), UniversitysListActivity.class);
                startActivity(intent_mingxiao);
                break;
            case R.id.zhuanye:
                Intent intent_zhuanye = new Intent(getActivity(), TeachersListActivity.class);
                startActivity(intent_zhuanye);
                break;
        }
    }

    private void initFigure() {
        getTeacherListResponse = new HandleResponse() {
            @Override
            public void getResponse(String response) {
                if (!response.equals("")) {
                    String [] line = response.split("@");
                    TeacherInfo teacherInfo;
                    for (String s : line) {
                        String [] teacherElements = s.split("%");
                        teacherInfo = new TeacherInfo(R.drawable.head_boy, teacherElements[0], teacherElements[1], teacherElements[2],
                                teacherElements[3], teacherElements[4], teacherElements[5], teacherElements[6]);
                        teacherInfoslist.add(teacherInfo);
                    }
                    teacherListAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(fisrtpage_listview);
                }
            }
        };
    }
}
