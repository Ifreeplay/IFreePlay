package com.ifreeplay.ifreeplaysdk.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogOutStateListener;
import com.ifreeplay.ifreeplaysdk.model.Credential;
import com.ifreeplay.ifreeplaysdk.model.User;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gaowe on 2017/7/6.
 */

public class FaceBookLogin {
    private  static FaceBookLogin mFaceBookLogin =null;
    private static Activity loginActivity=null;
    private static Context mContext;
    private static long mGameId;
    private List<String> permissions;
    private LogInStateListener mBookLoginStateChanged;
    private LogOutStateListener mBookLogOutStateListener;
    private CallbackManager callbackManager;
    private FaceBookCallBackListener callback=new FaceBookCallBackListener();
    private boolean isLogOut=false;

    public static FaceBookLogin getInstance(Context context, long gameId) {
        if(mFaceBookLogin ==null){
            mFaceBookLogin =new FaceBookLogin();
        }
        mContext =context;
        mGameId =gameId;
        //初始化Facebook
        FacebookSdk.sdkInitialize(context);
        return mFaceBookLogin;
    }
    public  void SetFaceBookLoginActivity(Activity activity){
        if(activity==null){
            throw new NullPointerException("login activity is null");
        }else{
            loginActivity=activity;
        }
    }

   /* public  void SetFaceBookReadPermission(String array){
        if(array==null){
            permissions= Arrays.asList("public_profile");
        }else{
            permissions= Arrays.asList(array);
        }
    }*/

    public  void SetOnFaceBookLoginStateListener(LogInStateListener LoginStateChanged){
        if(LoginStateChanged==null){
            throw new NullPointerException("LoginStateListener is null");
        }else{
            mBookLoginStateChanged=LoginStateChanged;
        }
    }

    public void SetOnFaceBookLogOutListener(LogOutStateListener logoutListener){
        mBookLogOutStateListener=logoutListener;
    }

    public void open() {
        FacebookSdk.sdkInitialize(loginActivity);
        callbackManager = CallbackManager.Factory.create();
        com.facebook.login.LoginManager.getInstance().registerCallback(callbackManager, callback);
        com.facebook.login.LoginManager.getInstance().logInWithReadPermissions(loginActivity,Arrays.asList("public_profile"));

        /*if(logOutView!=null){
            logOutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.facebook.login.LoginManager.getInstance().logOut();
                    isLogOut=true;
                    mBookLogOutStateListener.OnLogOutListener(isLogOut,"facebook");
                }
            });
        }*/
    }

    public void logOut(){
        com.facebook.login.LoginManager.getInstance().logOut();
        isLogOut=true;
        mBookLogOutStateListener.OnLogOutListener(isLogOut,"facebook");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    /*public void OnDestory(){
        //profileTracker.stopTracking();
    }*/

    private class FaceBookCallBackListener implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult result) {
            fetchUserInfo(result.getAccessToken());
        }
        @Override
        public void onCancel() {
            mBookLoginStateChanged.OnLoginError("user cancle login facebook!");
        }
        @Override
        public void onError(FacebookException error) {
            mBookLoginStateChanged.OnLoginError(error.getMessage());
        }

    }

    private void fetchUserInfo(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        try{
                            if(response.getError()!=null){
                                mBookLoginStateChanged.OnLoginError(response.getError().getErrorMessage());
                            }else if(response.getConnection().getResponseCode()==200){
                                User user=new User();
                                /*if (object.getString("email")!=null){
                                    user.setEmail(object.getString("email"));
                                }
                                if (object.getString("gender")!=null){
                                    user.setGender(object.getString("gender"));
                                }
                                if (object.getString("link")!=null){
                                    user.setLink(object.getString("link"));
                                }
                                if (object.getString("first_name")!=null){
                                    user.setFirstname(object.getString("first_name"));
                                }
                                if (object.getString("last_name")!=null){
                                    user.setLastname(object.getString("last_name"));
                                }
                                if (object.getString("locale")!=null){
                                    user.setLocale(object.getString("locale"));
                                }
                                if (object.getString("timezone")!=null){
                                    user.setTimezone(object.getString("timezone"));
                                }*/
                                if (object.getString("id")!=null){
                                    user.setUserId(object.getString("id"));
                                }
                                if (object.getString("name")!=null){
                                    user.setUserName(object.getString("name"));
                                }
                                /*if (object.getString("picture")!=null){
                                    user.setHeadPortraitUrl(object.getString("picture"));
                                }*/
                                //获取用户头像
                                /*JSONObject object_pic = object.optJSONObject( "picture" ) ;
                                JSONObject object_data = object_pic.optJSONObject( "data" ) ;
                                String photo = object_data.optString( "url" )  ;
                                user.setHeadPortraitUrl(photo);*/
                                //此时调接口生成player
                                Credential credential = new Credential();
                                credential.setGameId(mGameId);
                                credential.setName(user.getUserName());
                                credential.setType(Credential.Type.LINE);
                                credential.setFacebookId(user.getUserId());
                                credential.setHeadPortraitUrl(user.getHeadPortraitUrl());

                                HttpUtils.postString(UrlConstants.AUTHLOGIN, new Gson().toJson(credential), new HttpCallBackListener() {
                                    @Override
                                    public void onFinish(String response) {
                                        ViewPlayer viewPlayer = new Gson().fromJson(response, ViewPlayer.class);
                                        mBookLoginStateChanged.OnLoginSuccess(viewPlayer,"facebook");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        mBookLoginStateChanged.OnLoginError(e.getMessage());
                                    }
                                });
                            }
                        }catch(Exception e){
                            mBookLoginStateChanged.OnLoginError(e.getMessage());
                        }
                    }
                });
        request.executeAsync();
    }
}
