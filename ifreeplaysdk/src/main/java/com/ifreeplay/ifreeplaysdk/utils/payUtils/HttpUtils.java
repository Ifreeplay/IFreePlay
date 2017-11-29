package com.ifreeplay.ifreeplaysdk.utils.payUtils;

import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;


/**
 * Created by lenovo on 2017/9/28.
 */

public class HttpUtils {

    public enum HttpMethod {

        GET,
        POST,
        PUT
    }

    public static void getDateFromServicer(String url, Map<String,String> params, HttpMethod method, final HttpCallBackListener listener){
        if (method== HttpMethod.GET){
            OkHttpUtils.get().url(url).params(params).build().execute(new StringCallback(){
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(String response, int id) {
                    listener.onFinish(response);
                }
            });

        }else if (method== HttpMethod.POST){
            OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback(){
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(String response, int id) {
                    listener.onFinish(response);
                }
            });
        }
    }

    public static void postString(String url, String json, final HttpCallBackListener listener){
        OkHttpUtils.postString().url(url).content(json).mediaType(MediaType.parse("application/json; charset=utf-8")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onError(e);
            }

            @Override
            public void onResponse(String response, int id) {
                listener.onFinish(response);
            }
        });
    }
}
