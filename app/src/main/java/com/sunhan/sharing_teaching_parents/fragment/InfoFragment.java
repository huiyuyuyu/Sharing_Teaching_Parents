package com.sunhan.sharing_teaching_parents.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.adapter.InformationListAdapter;
import com.sunhan.sharing_teaching_parents.entity.InformationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-02/15/35
 */
public class InfoFragment extends Fragment {

    View view;
    private NetStateReceiver mStateReceiver;

    //列表项
    private ListView _dynamic;
    private InformationListAdapter informationListAdapter;
    private List<InformationInfo> informationList = new ArrayList<InformationInfo>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        rigisterReceiver();
        initView();
        return view;
    }

    private void initView() {
        _dynamic = view.findViewById(R.id.information_list);
        informationList.clear();
        informationList.add(new InformationInfo(R.drawable.head_boy, "孙老师/数学", "您收到一条预约消息，请点击查看！！！", "2019-08-06"));
        informationList.add(new InformationInfo(R.drawable.head_boy, "孙老师/数学", "您收到一条预约消息，请点击查看！！！", "2019-08-06"));
        informationList.add(new InformationInfo(R.drawable.head_boy, "孙老师/数学", "您收到一条预约消息，请点击查看！！！", "2019-08-06"));
        informationList.add(new InformationInfo(R.drawable.head_boy, "孙老师/数学", "您收到一条预约消息，请点击查看！！！", "2019-08-06"));
        informationList.add(new InformationInfo(R.drawable.head_boy, "孙老师/数学", "您收到一条预约消息，请点击查看！！！", "2019-08-06"));
        informationListAdapter = new InformationListAdapter(getActivity(), informationList);
        _dynamic.setAdapter(informationListAdapter);
    }

    private void rigisterReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//创建意图过滤器
        mStateReceiver = new NetStateReceiver();//创建广播监听器
        getActivity().registerReceiver(mStateReceiver,filter);//注册广播
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mStateReceiver);//解除注册
    }

    //监听网络变化的广播
    class NetStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断网络连接连接还是没有连接
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(getActivity(), "网络已连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "网络断开", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
