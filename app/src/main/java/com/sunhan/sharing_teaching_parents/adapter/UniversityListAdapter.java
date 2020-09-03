package com.sunhan.sharing_teaching_parents.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;
import com.sunhan.sharing_teaching_parents.entity.UniversityInfo;

import java.util.List;

/**
 * Created by 孙汉 on 2019-07-15/16/35
 */
public class UniversityListAdapter extends BaseAdapter {

    private Context mContext;
    private List<UniversityInfo> mUniversitylist;

    public UniversityListAdapter(Context mContext, List<UniversityInfo> mUniversitylist){
        this.mContext = mContext;
        this.mUniversitylist = mUniversitylist;
    }

    @Override
    public int getCount() {
        return mUniversitylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mUniversitylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_university_list, null);
            holder.university_photo = convertView.findViewById(R.id.university_photo);
            holder.university_name = convertView.findViewById(R.id.university_name);
            holder.university_describe = convertView.findViewById(R.id.university_describe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.university_photo.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),mUniversitylist.get(position).getUniversity_photo()));
        holder.university_name.setText(mUniversitylist.get(position).getUniversity_name());
        holder.university_describe.setText(mUniversitylist.get(position).getUniversity_describe());
        return convertView;
    }

    static class ViewHolder{
        public ImageView university_photo;
        public TextView university_name;
        public TextView university_describe;
    }
}
