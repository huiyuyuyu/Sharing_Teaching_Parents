package com.sunhan.sharing_teaching_parents.entity;

import android.support.annotation.StringRes;

public class SubjectGridInfo {

    private @StringRes int gridIcon;
    private String gridTitle;

    public SubjectGridInfo(int gridIcon, String gridTitle) {
        this.gridIcon = gridIcon;
        this.gridTitle = gridTitle;
    }

    public int getGridIcon() {
        return gridIcon;
    }

    public String getGridTitle() {
        return gridTitle;
    }

    public void setGridIcon(int gridIcon) {
        this.gridIcon = gridIcon;
    }

    public void setGridTitle(String gridTitle) {
        this.gridTitle = gridTitle;
    }
}
