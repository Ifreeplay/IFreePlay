package com.ifreeplay.ifreeplaysdk.interfaces;


import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;

/**
 * Created by gaowe on 2017/7/6.
 */

public interface LogInStateListener {
    public  void OnLoginSuccess(ViewPlayer player, String logType);
    public  void OnLoginError(String error);
}
