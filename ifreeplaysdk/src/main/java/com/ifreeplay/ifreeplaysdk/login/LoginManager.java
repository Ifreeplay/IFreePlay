package com.ifreeplay.ifreeplaysdk.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogOutStateListener;

/**
 * Created by gaowe on 2017/7/6.
 */

public  class LoginManager {
    private static FaceBookLogin fbUtil=null;
    private static LineLogin lineUtil=null;
    private static WeChatLogin wechatUtil=null;
    public static final  int LineRequestCode=101;
    public static final  int FaceBookRequestCode=64206;
    private static Context mContext;
    private static long mGameId;
    private static LoginManager loginManager;

    /**
     * 登陆工具类，
     *
     */
    public static LoginManager initialize(Context context, long gameId){
        mContext =context;
        mGameId =gameId;
        if (loginManager==null){
            loginManager = new LoginManager();
        }
        return loginManager;
    }

    public void initFaceBookLogin(){
        fbUtil= FaceBookLogin.getInstance(mContext, mGameId);
    }

    public void initLineLogin(){
        lineUtil = LineLogin.getInstance(mContext, mGameId);
    }

    public void initWechatLogin(){
        wechatUtil = WeChatLogin.getInstance(mContext, mGameId);
    }

    /**
     * 注销微信注册的广播
     */
    public void unRegister(){
        wechatUtil.unRegister();
    }

    /**
     * 如果要使用facebook登录需要调用这个方法
     * @param activity
     * @param arrarPermission
     * @param loginstateListener
     */
    public void setFaceBookLoginParams(Activity activity, String arrarPermission, LogInStateListener loginstateListener){
        fbUtil.SetFaceBookLoginActivity(activity);
        fbUtil.SetFaceBookReadPermission(arrarPermission);
        fbUtil.SetOnFaceBookLoginStateListener(loginstateListener);
        fbUtil.open();
    }

    /**
     * 如果要使用Line登录时需调用这个方法
     * @param activity
     * @param loginstateListener
     */
    public void setLineLoginParams(Activity activity,String channelId, LogInStateListener loginstateListener){
        lineUtil.SetLineLoginActivity(activity);
        lineUtil.SetOnLineLoginStateListener(loginstateListener);
        lineUtil.SetLineLoginChinnalId(channelId);
        lineUtil.open();
    }

    /**
     * 如果要使用Wechat登录时需调用这个方法
     * @param activity
     * @param loginstateListener
     */
    public void setWechatLoginParams(Activity activity, String appId, String appSecret, String packageName, LogInStateListener loginstateListener){
        wechatUtil.SetWeChatLoginActivity(activity);
        wechatUtil.SetWeChatLoginAppId(appId,appSecret,packageName);
        wechatUtil.register();
        wechatUtil.SetOnWeChatLoginStateListener(loginstateListener);
        wechatUtil.open();
    }
    /**
     * 退出Facebook登录时调用
     * @param logOutStateListener
     */
    public void setFaceBookLogOutParams( LogOutStateListener logOutStateListener){
        fbUtil.SetOnFaceBookLogOutListener(logOutStateListener );
        fbUtil.logOut();
    };

    /**
     * 退出line登录时调用
     * @param logOutStateListener
     */
    public void setLineLogOutParams(LogOutStateListener logOutStateListener, Context LogOutContext){
        lineUtil.SetOnLineLogOutListener(logOutStateListener);
        lineUtil.setLineLogOutContext(LogOutContext);
        lineUtil.logOut();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==64206&&fbUtil!=null){
            fbUtil.onActivityResult(requestCode, resultCode, data);
        }else if (requestCode==101&&lineUtil!=null){
            lineUtil.onActivityResult(requestCode, resultCode, data);
        }
    }
}
