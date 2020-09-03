package com.sunhan.sharing_teaching_parents.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sunhan.sharing_teaching_parents.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-02/15/34
 */
public class ServiceFragment extends Fragment {

    View view;

    ListView mlist;
    private List<String> list;
    //private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service, container, false);
            //initView();
        }


        return view;
    }

    /**
    private void initView() {
        mlist = view.findViewById(R.id.secondlist);
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("PullToRefreshLayout"+i);
        }
        adapter = new ListAdapter(getActivity(), list);
        mlist.setAdapter(adapter);
    }*/
}
