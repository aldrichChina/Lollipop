package com.jia.lollipop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jia.lollipop.callback.DialogCallback;
import com.jia.lollipop.utils.Constant;
import com.jia.lollipop.Bean.RequestInfo;
import com.jia.lollipop.utils.Urls;
import com.jia.okhttputils.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class CustomRequestActivity extends BaseActivity {

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(com.jia.lollipop.R.layout.activity_custom_request);
        ButterKnife.bind(this);
        actionBar.setTitle(Constant.getData().get(1)[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @OnClick(com.jia.lollipop.R.id.requestJson)
    public void requestJson(View view) {
        OkHttpUtils.get(Urls.URL_JSONOBJECT)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CustomCallBack<>(this,RequestInfo.class));
    }

    @OnClick(com.jia.lollipop.R.id.requestJsonArray)
    public void requestJsonArray(View view) {
        OkHttpUtils.get(Urls.URL_JSONARRAY)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new CustomCallBack<List<RequestInfo>>(this, new TypeToken<List<RequestInfo>>(){}.getType()));
    }

    private class CustomCallBack<T> extends DialogCallback<T> {
        public CustomCallBack(Activity activity, Class<T> clazz) {
            super(activity, clazz);
        }

        public CustomCallBack(Activity activity, Type type) {
            super(activity, type);
        }

        @Override
        public void onResponse(boolean isFromCache, T data, Request request, Response response) {
            handleResponse(isFromCache, data, request, response);
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            handleError(isFromCache, call, response);
        }
    }
}
