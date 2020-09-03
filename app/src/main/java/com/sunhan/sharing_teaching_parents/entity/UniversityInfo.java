package com.sunhan.sharing_teaching_parents.entity;

/**
 * Created by 孙汉 on 2019-07-15/16/15
 */
public class UniversityInfo {
    public int university_photo;
    public String university_name;
    public String university_describe;

    public UniversityInfo(int university_photo, String university_name, String university_describe) {
        this.university_photo = university_photo;
        this.university_name = university_name;
        this.university_describe = university_describe;
    }

    public int getUniversity_photo() {
        return university_photo;
    }

    public void setUniversity_photo(int university_photo) {
        this.university_photo = university_photo;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getUniversity_describe() {
        return university_describe;
    }

    public void setUniversity_describe(String university_describe) {
        this.university_describe = university_describe;
    }
}
