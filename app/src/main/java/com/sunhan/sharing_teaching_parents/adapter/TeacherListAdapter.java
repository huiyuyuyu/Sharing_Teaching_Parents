package com.sunhan.sharing_teaching_parents.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.TeacherDetailActivity;
import com.sunhan.sharing_teaching_parents.activities.TeachersListActivity;
import com.sunhan.sharing_teaching_parents.entity.TeacherInfo;

import java.util.List;

public class TeacherListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TeacherInfo> mTeacherlist;

    public TeacherListAdapter(Context mContext, List<TeacherInfo> mTeacherlist){
        this.mContext = mContext;
        this.mTeacherlist = mTeacherlist;
    }

    @Override
    public int getCount() {
        return mTeacherlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mTeacherlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_teacher_list, null);
            holder.teacher_photo = view.findViewById(R.id.teacher_photo);
            holder.teacher_name = view.findViewById(R.id.teacher_name);
            holder.teacher_number = view.findViewById(R.id.teacher_number);
            holder.teacher_university = view.findViewById(R.id.teacher_university);
            holder.teacher_level = view.findViewById(R.id.teacher_level);
            holder.teacher_latestlogintime = view.findViewById(R.id.teacher_latestlogintime);
            holder.teacher_selfdescribe = view.findViewById(R.id.teacher_selfdescribe);
            holder.teacher_salaryrequest = view.findViewById(R.id.teacher_salaryrequest);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.teacher_photo.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),mTeacherlist.get(i).getTeacher_photo()));
        holder.teacher_name.setText(mTeacherlist.get(i).getTeacher_name());
        holder.teacher_number.setText(mTeacherlist.get(i).getTeacher_number());
        holder.teacher_university.setText(mTeacherlist.get(i).getTeacher_university());
        holder.teacher_level.setText(mTeacherlist.get(i).getTeacher_level());
        holder.teacher_latestlogintime.setText(mTeacherlist.get(i).getTeacher_latestlogintime());
        holder.teacher_selfdescribe.setText(mTeacherlist.get(i).getTeacher_selfdescribe());
        holder.teacher_salaryrequest.setText(mTeacherlist.get(i).getTeacher_salaryrequest());

        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TeacherInfo teacherInfo = mTeacherlist.get(i);
                Intent intent = new Intent(mContext, TeacherDetailActivity.class);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    static class ViewHolder{
        public ImageView teacher_photo;
        public TextView teacher_name;
        public TextView teacher_number;
        public TextView teacher_university;
        public TextView teacher_level;
        public TextView teacher_latestlogintime;
        public TextView teacher_selfdescribe;
        public TextView teacher_salaryrequest;
    }
}
