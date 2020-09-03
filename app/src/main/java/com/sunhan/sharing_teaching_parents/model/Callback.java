package com.sunhan.sharing_teaching_parents.model;

/**
 * Created by 孙汉 on 2019-07-26/10/35
 */
public interface Callback<T> {
    void onEvent(int code, String msg, T t);
}
