package com.sunhan.sharing_teaching_parents.entity;

public class InformationInfo {
    public int photo;
    public String name;
    public String content;
    public String time;

    public InformationInfo(int photo, String name, String content, String time) {
        this.photo = photo;
        this.name = name;
        this.content = content;
        this.time = time;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
