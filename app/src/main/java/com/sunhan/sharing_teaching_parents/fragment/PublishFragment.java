package com.sunhan.sharing_teaching_parents.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.publish.PublishActivity;

/**
 * Created by 孙汉 on 2019-07-02/15/35
 */
public class PublishFragment extends Fragment implements View.OnClickListener {

    View view;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish, container, false);
        initView();
        return view;
    }

    private void initView() {
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
        }
    }
}
