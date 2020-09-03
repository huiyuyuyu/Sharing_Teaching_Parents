package com.sunhan.sharing_teaching_parents.activities.mypage;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.activities.BaseActivity;
import com.sunhan.sharing_teaching_parents.util.IsInternet;

/**
 * Created by 孙汉 on 2019-07-20/20/42
 */
public class PageRequestActivity extends BaseActivity implements View.OnClickListener {

    ImageView back;
    private WebView webView;
    private ProgressBar progressBar;
    private View item1;
    private TextView mTvCenterBadnet;

    @Override
    protected View getContentView() {
        return inflateView(R.layout.activity_pagerequest);
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        initView();
    }

    private void initView() {
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        mTvCenterBadnet = findViewById(R.id.mTvCenterBadnet);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        webView.loadUrl("http://www.baidu.com/");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //正常网络流程
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //如果没有网络直接跳出方法
                if (!IsInternet.isNetworkAvalible(PageRequestActivity.this)) {
                    webView.setVisibility(View.GONE);
                    item1 = findViewById(R.id.item1);
                    item1.setVisibility(View.VISIBLE);
                    //点击重新连接网络
                    mTvCenterBadnet.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mTvCenterBadnet.setVisibility(View.INVISIBLE);
                            //重新加载网页
                            webView.reload();
                        }
                    });
                    return;
                }

                if (newProgress == 100){
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_back_service:
                finish();
                break;
        }
    }
}
