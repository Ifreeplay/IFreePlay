package com.ifreeplay.ifreeplaysdk.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by lenovo on 2017/12/1.
 */

public class LineShare {
    private static Activity mActivity;
    private static LineShare lineShare;

    public static LineShare initialize(Activity activity){
        mActivity =activity;
        if (lineShare==null){
            lineShare = new LineShare();
        }
        return lineShare;
    }

    /**
     * 分享文本
     * @param content
     */
    public void shareText(String content){
        ComponentName cn = new ComponentName("jp.naver.line.android"
                , "jp.naver.line.android.activity.selectchat.SelectChatActivity");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setComponent(cn);//跳到指定APP的Activity
        mActivity.startActivity(Intent.createChooser(shareIntent, "分享"));
    }

    public void shareImage(Bitmap bitmap){
        ComponentName cn = new ComponentName("jp.naver.line.android"
                , "jp.naver.line.android.activity.selectchat.SelectChatActivity");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), bitmap, null,null));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*"); //图片分享
        shareIntent.setComponent(cn);
        mActivity.startActivity(Intent.createChooser(shareIntent, "分享"));
    }

}
