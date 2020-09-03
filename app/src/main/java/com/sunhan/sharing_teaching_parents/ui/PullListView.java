package com.sunhan.sharing_teaching_parents.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by 张海逢 on 2018/7/27.
 */


public class PullListView extends ListView {
    public PullListView(Context context) {
        super(context);
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(getChildAt(0)!=null){
            if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {//到头部了
                getParent().requestDisallowInterceptTouchEvent(false);//放行触摸
                getParent().getParent().requestDisallowInterceptTouchEvent(false);//放行触摸
            } else {//没有到头部
                getParent().requestDisallowInterceptTouchEvent(true);//拦截触摸
                getParent().getParent().requestDisallowInterceptTouchEvent(true);//放行触摸
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
