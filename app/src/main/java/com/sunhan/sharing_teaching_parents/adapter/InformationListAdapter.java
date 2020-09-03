package com.sunhan.sharing_teaching_parents.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.TeacherDetailActivity;
import com.sunhan.sharing_teaching_parents.entity.InformationInfo;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;

import java.util.List;

public class InformationListAdapter extends BaseAdapter {

    private Context mContext;
    private List<InformationInfo> mInformationlist;

    public InformationListAdapter(Context mContext, List<InformationInfo> mInformationlist){
        this.mContext = mContext;
        this.mInformationlist = mInformationlist;
    }

    @Override
    public int getCount() {
        return mInformationlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mInformationlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_information_list, null);
            viewHolder.photo = view.findViewById(R.id.photo);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.photo.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),mInformationlist.get(i).getPhoto()));
            viewHolder.name.setText(mInformationlist.get(i).getName());
            viewHolder.content.setText(mInformationlist.get(i).getContent());
            viewHolder.time.setText(mInformationlist.get(i).getTime());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TeacherDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        return view;
    }

    private class ViewHolder {
        private ImageView photo;
        private TextView name;
        private TextView content;
        private TextView time;
    }
}
