package com.ifreeplay.ifreeplaysdk.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.ifreeplay.ifreeplaysdk.interfaces.FacebookShareListener;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/1.
 */

public class FacebookShare {

    private static Activity mActivity;
    private static CallbackManager callbackManager;
    private static FacebookShare facebookShare;


    public static FacebookShare initialize(Activity activity){
        mActivity =activity;
        callbackManager = CallbackManager.Factory.create();
        if (facebookShare==null){
            facebookShare = new FacebookShare();
        }
        return facebookShare;
    }

    public enum ShareType {
        NEWS_FEED, // 动态
        MESSAGE // 消息
    }
    //分享连接
    public void shareLinkUrl(String url, ShareType shareType, final FacebookShareListener facebookShareListener){
        //"https://play.google.com/store/apps/details?id=com.ifreeplay.airtravelers"
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build();
        setShareContent(linkContent,shareType,facebookShareListener);
    }

    /**
     * 分享图片和视频
     * @param bitmaps
     * @param uris
     * @param shareType
     * @param facebookShareListener
     */
    public void shareMedia(List<Bitmap> bitmaps,List<Uri> uris,ShareType shareType, final FacebookShareListener facebookShareListener){
        if (bitmaps.size()+uris.size()>30){
            AndroidUtils.shortToast(mActivity,"The number of photos and videos can't be more than 30!");
            return;
        }

        List<SharePhoto> sharePhotos = new ArrayList<>();
        for (int i=0;i<bitmaps.size();i++){
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmaps.get(i)).build();
            sharePhotos.add(sharePhoto);
        }

        ArrayList<ShareVideo> shareVideos = new ArrayList<>();
        for (int i=0;i<uris.size();i++){
            ShareVideo shareVideo = new ShareVideo.Builder()
                    .setLocalUrl(uris.get(i)).build();
            shareVideos.add(shareVideo);
        }

        ShareMediaContent.Builder builder = new ShareMediaContent.Builder();
        for (int i=0;i<sharePhotos.size();i++){
            builder.addMedium(sharePhotos.get(i));
        }
        for (int i=0;i<shareVideos.size();i++){
            builder.addMedium(shareVideos.get(i));
        }
        ShareMediaContent shareMediaContent = builder.build();

        setShareContent(shareMediaContent,shareType,facebookShareListener);
    }

    /**
     * 分享图片
     * @param bitmaps
     * @param shareType
     * @param facebookShareListener
     */
    public void shareImage(List<Bitmap> bitmaps, ShareType shareType, final FacebookShareListener facebookShareListener){
        if (bitmaps.size()>30){
            AndroidUtils.shortToast(mActivity,"The number of photos can't be more than 30!");
            return;
        }
        List<SharePhoto> sharePhotos = new ArrayList<>();
        for (int i=0;i<bitmaps.size();i++){
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmaps.get(i)).build();
            sharePhotos.add(sharePhoto);
        }

        SharePhotoContent.Builder builder = new SharePhotoContent.Builder();
        for (int i=0;i<sharePhotos.size();i++){
            builder.addPhoto(sharePhotos.get(i));
        }

        SharePhotoContent sharePhotoContent = builder.build();
        setShareContent(sharePhotoContent,shareType,facebookShareListener);
    }

    private void setShareContent(ShareContent shareContent, ShareType shareType, final FacebookShareListener facebookShareListener){
        if (ShareType.NEWS_FEED==shareType){
            ShareDialog shareDialog = new ShareDialog(mActivity);
            shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    facebookShareListener.OnShareSuccess(result);
                }

                @Override
                public void onCancel() {
                    facebookShareListener.onShareCancel();
                }

                @Override
                public void onError(FacebookException error) {
                    facebookShareListener.OnShareError(error);
                }
            });
        }else if (ShareType.MESSAGE==shareType){
            MessageDialog messageDialog = new MessageDialog(mActivity);
            messageDialog.show(shareContent);
            messageDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    facebookShareListener.OnShareSuccess(result);
                }

                @Override
                public void onCancel() {
                    facebookShareListener.onShareCancel();
                }

                @Override
                public void onError(FacebookException error) {
                    facebookShareListener.OnShareError(error);
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
