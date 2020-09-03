package com.sunhan.sharing_teaching_parents.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.TeachersListActivity;
import com.sunhan.sharing_teaching_parents.entity.SubjectGridInfo;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<SubjectGridInfo> mData;

    public GridAdapter(Context mContext, List<SubjectGridInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview,null);
        ImageView gridIcon = (ImageView) convertView.findViewById(R.id.grid_icon);
        TextView gridTitle = (TextView)convertView.findViewById(R.id.grid_title);

        gridIcon.setImageResource(mData.get(position).getGridIcon());
        gridTitle.setText(mData.get(position).getGridTitle());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SubjectGridInfo subjectGridInfo = mData.get(position);
                Intent intent = new Intent(mContext, TeachersListActivity.class);
                intent.putExtra(TeachersListActivity.SUBJECT_NAME, subjectGridInfo.getGridTitle());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


}
