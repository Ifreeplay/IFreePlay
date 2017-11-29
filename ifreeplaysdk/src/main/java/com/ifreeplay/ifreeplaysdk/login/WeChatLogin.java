package com.ifreeplay.ifreeplaysdk.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogOutStateListener;
import com.ifreeplay.ifreeplaysdk.model.Credential;
import com.ifreeplay.ifreeplaysdk.model.User;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.utils.loginUtils.HttpUtil;
import com.ifreeplay.ifreeplaysdk.utils.loginUtils.SPUtil;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaowe on 2017/7/21.
 */

public class WeChatLogin {

    private static WeChatLogin mWeChatLogin;
    private static Context mContext;
    private static long mGameId;
    private Activity loginActivity;
    private View logOutView;
    private LogInStateListener mWeChatLoginStateChanged;
    private LogOutStateListener mWeChatLogOutStateListener;
    private IWXAPI api;
    private String mAppId;
    private String mPackageName;
    private String mAppSecret;

    //接受微信登录返回code的广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("authlogin")){
                getAccessToken();
            }
        }
    };

    public String getAppId(){
        return mAppId;
    }
    public static WeChatLogin getInstance(Context context, long gameId) {
        if(mWeChatLogin ==null){
            mWeChatLogin =new WeChatLogin();
        }
        mContext =context;
        mGameId =gameId;
        return mWeChatLogin;
    }

    public  void SetWeChatLoginActivity(Activity activity){
        if(activity==null){
            throw new NullPointerException("login activity is null");
        }else{
            loginActivity =activity;
        }
    }

    public void register(){
        //注册微信登录广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("authlogin");
        loginActivity.registerReceiver(mBroadcastReceiver, filter);
    }

    public void unRegister(){
        if (mBroadcastReceiver!=null){
            loginActivity.unregisterReceiver(mBroadcastReceiver);
        }
    }

    public void SetWeChatLogOutButton(View view){
        logOutView =view;
    }

    public  void SetOnWeChatLoginStateListener(LogInStateListener LoginStateChanged){
        if(LoginStateChanged==null){
            throw new NullPointerException("LoginStateListener is null");
        }else{
            mWeChatLoginStateChanged =LoginStateChanged;
        }
    }

    public void SetOnWeChatLogOutListener(LogOutStateListener logoutListener){
        mWeChatLogOutStateListener =logoutListener;
    }

    public void SetWeChatLoginAppId(String AppId, String appSecret, String packageName) {
        mAppId = AppId;
        mPackageName =packageName;
        mAppSecret =appSecret;
        //注册绑定微信
        api = WXAPIFactory.createWXAPI(mContext, AppId, true);
        api.registerApp(AppId);
    }

    public void open() {
        try{
            if (api == null) {
                api = WXAPIFactory.createWXAPI(loginActivity,mAppId, true);
            }
            SendAuth.Req req = new SendAuth.Req();
            req.state = mPackageName;
            req.scope = "snsapi_userinfo";
            api.sendReq(req);
        }
        catch(Exception e) {
            Log.e("ERROR", e.toString());
        }


        if(logOutView!=null){
            logOutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //退出微信登录

                }
            });
        }
    }

    /**
     * 微信登录通过code获取AccessToken(放自己服务端请求并返回相应参数)
     */
    public void getAccessToken() {
        String code = SPUtil.getString(loginActivity, "WxCode", "");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + mAppId
                + "&secret="
                + mAppSecret
                + "&code="
                + code
                + "&grant_type=authorization_code";
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                //解析以及存储获取到的信息
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String access_token = jsonObject.getString("access_token");
                    String openid = jsonObject.getString("openid");
                    String refresh_token = jsonObject.getString("refresh_token");
                    if (!access_token.equals("")) {
                        SPUtil.setString(loginActivity,"ACCESS_TOKEN",access_token);
                    }
                    if (!refresh_token.equals("")) {
                        SPUtil.setString(loginActivity,"REFRESH_TOKEN",refresh_token);
                    }
                    if (!openid.equals("")) {
                        SPUtil.setString(loginActivity,"WXOPENID",openid);
                        getPersonMessage(access_token, openid);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(loginActivity, "通过code获取数据没有成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取微信用户信息
     */
    private void getPersonMessage(String access_token, String openid) {
        final String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    User user = new User();
                    if (jsonObject.getString("nickname")!=null){
                        user.setUserName(jsonObject.getString("nickname"));
                    }
                    if (jsonObject.getString("openid")!=null){
                        user.setUserId(jsonObject.getString("openid"));
                    }
                    if (jsonObject.getString("unionid")!=null){
                        user.setWxUnionId(jsonObject.getString("unionid"));
                    }
                    if (jsonObject.getString("headimgurl")!=null){
                        user.setHeadPortraitUrl(jsonObject.getString("headimgurl"));
                    }
                    //此时调接口生成player
                    Credential credential = new Credential();
                    credential.setGameId(mGameId);
                    credential.setName(user.getUserName());
                    credential.setType(Credential.Type.WECHAT);
                    credential.setWechatId(user.getUserId());
                    credential.setHeadPortraitUrl(user.getHeadPortraitUrl());
                    HttpUtils.postString(UrlConstants.AUTHLOGIN, new Gson().toJson(credential), new HttpCallBackListener() {
                        @Override
                        public void onFinish(String response) {
                            ViewPlayer viewPlayer = new Gson().fromJson(response, ViewPlayer.class);
                            mWeChatLoginStateChanged.OnLoginSuccess(viewPlayer,"wechat");
                        }

                        @Override
                        public void onError(Exception e) {
                            mWeChatLoginStateChanged.OnLoginError(e.getMessage());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    mWeChatLoginStateChanged.OnLoginError(e.getMessage());
                }
            }

            @Override
            public void onError(Exception e) {
                mWeChatLoginStateChanged.OnLoginError("通过openid获取数据没有成功");
            }
        });
    }

}
