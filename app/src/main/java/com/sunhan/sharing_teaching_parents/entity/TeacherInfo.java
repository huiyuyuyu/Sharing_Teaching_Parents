package com.sunhan.sharing_teaching_parents.entity;

import android.widget.ImageView;
import android.widget.TextView;

public class TeacherInfo {
    public int teacher_photo;
    public String teacher_name;
    public String teacher_number;
    public String teacher_university;
    public String teacher_level;
    public String teacher_latestlogintime;
    public String teacher_selfdescribe;
    public String teacher_salaryrequest;

    public TeacherInfo(int teacher_photo, String teacher_name, String teacher_number, String teacher_university, String teacher_level, String teacher_latestlogintime, String teacher_selfdescribe, String teacher_salaryrequest) {
        this.teacher_photo = teacher_photo;
        this.teacher_name = teacher_name;
        this.teacher_number = teacher_number;
        this.teacher_university = teacher_university;
        this.teacher_level = teacher_level;
        this.teacher_latestlogintime = teacher_latestlogintime;
        this.teacher_selfdescribe = teacher_selfdescribe;
        this.teacher_salaryrequest = teacher_salaryrequest;
    }

    public int getTeacher_photo() {
        return teacher_photo;
    }

    public void setTeacher_photo(int teacher_photo) {
        this.teacher_photo = teacher_photo;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_number() {
        return teacher_number;
    }

    public void setTeacher_number(String teacher_number) {
        this.teacher_number = teacher_number;
    }

    public String getTeacher_university() {
        return teacher_university;
    }

    public void setTeacher_university(String teacher_university) {
        this.teacher_university = teacher_university;
    }

    public String getTeacher_level() {
        return teacher_level;
    }

    public void setTeacher_level(String teacher_level) {
        this.teacher_level = teacher_level;
    }

    public String getTeacher_latestlogintime() {
        return teacher_latestlogintime;
    }

    public void setTeacher_latestlogintime(String teacher_latestlogintime) {
        this.teacher_latestlogintime = teacher_latestlogintime;
    }

    public String getTeacher_selfdescribe() {
        return teacher_selfdescribe;
    }

    public void setTeacher_selfdescribe(String teacher_selfdescribe) {
        this.teacher_selfdescribe = teacher_selfdescribe;
    }

    public String getTeacher_salaryrequest() {
        return teacher_salaryrequest;
    }

    public void setTeacher_salaryrequest(String teacher_salaryrequest) {
        this.teacher_salaryrequest = teacher_salaryrequest;
    }
}
