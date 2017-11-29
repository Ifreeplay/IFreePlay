/*
package com.ifreeplay.ifreeplaysdk.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogOutStateListener;
import com.ifreeplay.ifreeplaysdk.model.Credential;
import com.ifreeplay.ifreeplaysdk.model.User;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;


*/
/**
 * Created by gaowe on 2017/7/19.
 *//*


public class LineLogin {

    private static LineLogin mLineLogin;
    private static long mGameId;
    private LogInStateListener mLineLoginStateChanged;
    private LogOutStateListener mLineLogOutStateListener;
    private Activity loginActivity;
    private String mChannelId1;
    private boolean isLogOut=false;
    private Context mLineLogOutContext;
    private LineApiClient lineApiClient;

    public static LineLogin getInstance(Context context, long gameId) {
        if(mLineLogin ==null){
            mLineLogin =new LineLogin();
        }
        mGameId =gameId;
        return mLineLogin;
    }

    public  void SetLineLoginActivity(Activity activity){
        if(activity==null){
            throw new NullPointerException("login activity is null");
        }else{
            loginActivity =activity;

        }
    }

    public void setLineLogOutContext(Context lineLogOutContext) {
        this.mLineLogOutContext = lineLogOutContext;
    }

    public  void SetOnLineLoginStateListener(LogInStateListener LoginStateChanged){
        if(LoginStateChanged==null){
            throw new NullPointerException("LoginStateListener is null");
        }else{
            mLineLoginStateChanged =LoginStateChanged;
        }
    }

    public void SetOnLineLogOutListener(LogOutStateListener logoutListener){
        mLineLogOutStateListener =logoutListener;
    }

    public void SetLineLoginChinnalId(String channelId) {
        mChannelId1 = channelId;
    }

    public void open() {

        try{
            Intent loginIntent = LineLoginApi.getLoginIntent(loginActivity, mChannelId1);
            loginActivity.startActivityForResult(loginIntent, LoginManager.LineRequestCode);
        }
        catch(Exception e) {
            Log.e("ERROR", e.toString());
        }

        */
/*if(logOutView!=null){
            logOutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //初始化lineApiClient
                    LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(mLineLogOutContext, mChannelId1);
                    lineApiClient = apiClientBuilder.build();
                    lineApiClient.logout();
                    isLogOut=true;
                    mLineLogOutStateListener.OnLogOutListener(isLogOut,"line");
                }
            });
        }*//*

    }

    public void logOut(){
        //初始化lineApiClient
        LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(mLineLogOutContext, mChannelId1);
        lineApiClient = apiClientBuilder.build();
        lineApiClient.logout();
        isLogOut=true;
        mLineLogOutStateListener.OnLogOutListener(isLogOut,"line");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
        switch (result.getResponseCode()) {
            case SUCCESS:
                fetchUserInfo(result);
                break;

            case CANCEL:
                // Login canceled by user
                mLineLoginStateChanged.OnLoginError("user cancle login facebook!");
                break;

            default:
                // Login canceled due to other error
                mLineLoginStateChanged.OnLoginError(result.getErrorData().toString());
        }
    }

    private void fetchUserInfo(LineLoginResult result) {
        User user = new User();
        try {
            */
/*if (result.getLineProfile()!=null){
            user.setLineProfile(result.getLineProfile().toString());
            }*//*

            if (result.getLineProfile().getDisplayName()!=null){
                user.setUserName(result.getLineProfile().getDisplayName());
            }
            if (result.getLineProfile().getUserId()!=null){
                user.setUserId(result.getLineProfile().getUserId());
            }
            if (result.getLineProfile().getPictureUrl()!=null){
                user.setHeadPortraitUrl(result.getLineProfile().getPictureUrl().toString());
            }
            */
/*if (result.getLineCredential().getAccessToken().getAccessToken()!=null){
                user.setAccessToken(result.getLineCredential().getAccessToken().getAccessToken());
            }*//*



            //此时调接口生成player
            Credential credential = new Credential();
            credential.setGameId(mGameId);
            credential.setName(user.getUserName());
            credential.setType(Credential.Type.LINE);
            credential.setLineId(user.getUserId());
            credential.setHeadPortraitUrl(user.getHeadPortraitUrl());
            HttpUtils.postString(UrlConstants.AUTHLOGIN, new Gson().toJson(credential), new HttpCallBackListener() {
                @Override
                public void onFinish(String response) {
                    ViewPlayer viewPlayer = new Gson().fromJson(response, ViewPlayer.class);
                    mLineLoginStateChanged.OnLoginSuccess(viewPlayer, "line");
                }

                @Override
                public void onError(Exception e) {
                    mLineLoginStateChanged.OnLoginError(e.getMessage());
                }
            });
        }catch (Exception e){
            mLineLoginStateChanged.OnLoginError(e.getMessage());
        }

    }


}
*/
