package com.sunhan.sharing_teaching_parents.activities.firstpage;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.adapter.UniversityListAdapter;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;
import com.sunhan.sharing_teaching_parents.entity.UniversityInfo;
import com.sunhan.sharing_teaching_parents.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class UniversitysListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    //列表
    private ListView university_listview;
    private UniversityListAdapter universityListAdapter;
    private List<UniversityInfo> universityInfoslist = new ArrayList<>();

    @Override
    protected View getContentView() {
        return inflateView(R.layout.fragment_universitylist);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);

        university_listview = findViewById(R.id.university_listview);
        String[] university_name = getResources().getStringArray(R.array.university_name);
        String[] university_describe = getResources().getStringArray(R.array.university_describe);
        TypedArray university_photo = getResources().obtainTypedArray(R.array.university_photo);
        for (int i = 0; i < university_name.length; i++) {
            universityInfoslist.add(new UniversityInfo(university_photo.getResourceId(i,0), university_name[i], university_describe[i]));
        }
        universityListAdapter = new UniversityListAdapter(this, universityInfoslist);
        university_listview.setAdapter(universityListAdapter);

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
        }
    }
}
