package com.ifreeplay.ifreeplaysdk.interfaces;


import com.facebook.FacebookException;
import com.facebook.share.Sharer;

/**
 * Created by gaowe on 2017/7/6.
 */

public interface FacebookShareListener {
    void OnShareSuccess(Sharer.Result result);
    void onShareCancel();
    void OnShareError(FacebookException error);
}
