package com.ifreeplay.ifreeplaysdk.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.PaymentCallBackListener;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.IabHelper;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.IabResult;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.Inventory;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.IabHelper.RESPONSE_INAPP_PURCHASE_DATA;
import static com.ifreeplay.ifreeplaysdk.utils.payUtils.googleUtils.IabHelper.RESPONSE_INAPP_SIGNATURE;


/**
 * Created by lenovo on 2017/9/29.
 */

public class GooglePay {

    private static Context mContext;
    //private static String mGooglePublicKey;
    private static IabHelper helper;
    private static IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
    private static long mOrderId;
    private static PaymentCallBackListener mCallBackListener;
    public static final int GOOGLE_REQUEST_CODE = 10001;//Google请求码

    public static void init(Context context, String googlePublicKey) {
        mContext = context;
        helper = new IabHelper(context, googlePublicKey);
    }

    public static void pay(final String productId, final long orderId, PaymentCallBackListener callBackListener) {
        mOrderId =orderId;
        mCallBackListener =callBackListener;
        //绑定服务
        helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    AndroidUtils.shortToast(mContext, "Google payment service binding failed!");
                    return;
                }
                // 服务绑定成功,查询商品
                ArrayList<String> productList = new ArrayList<>();
                productList.add(productId);
                helper.queryInventoryAsync(true, productList, new IabHelper.QueryInventoryFinishedListener() {
                    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                        if (result.isFailure()) {
                            AndroidUtils.shortToast(mContext, "Commodity query failed!");
                            return;
                        }
                        Purchase purchase = inventory.getPurchase(productId);
                        if (purchase != null) {
                            //消耗商品
                            helper.consumeAsync(purchase, mConsumeFinishedListener);
                            return;
                        }
                        //发起购买商品
                        helper.launchPurchaseFlow((Activity) mContext, productId, GOOGLE_REQUEST_CODE,
                                new IabHelper.OnIabPurchaseFinishedListener() {
                                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                                        if (result.isFailure()) {
                                            AndroidUtils.shortToast(mContext, "Failure to buy goods!");
                                            return;
                                        }
                                        //购买成功，消耗商品
                                        helper.consumeAsync(purchase, mConsumeFinishedListener);
                                    }
                                }, String.valueOf(orderId));
                    }
                });
            }
        });

        // 消耗商品的监听
        mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (helper == null) return;
                if (result.isSuccess()) {
                    //AndroidUtils.shortToast(mContext,"消耗成功！");
                } else {
                    AndroidUtils.shortToast(mContext,"Error while consuming: " + result);
                }
            }
        };
    }

    /**
     * 解绑谷歌支付服务
     */
    public static void unbindService() {
        if (helper != null) helper.dispose();
        helper = null;
    }

    /**
     * 获取支付返回数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GOOGLE_REQUEST_CODE) {
            if (helper == null) return;
            String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
            String dataSignature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);
            checkGooglePay(dataSignature,purchaseData);
        }
    }

    /**
     * 服务端校验
     * @param dataSignature
     * @param purchaseData
     */
    private static void checkGooglePay(String dataSignature, String purchaseData) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNumber", String.valueOf(mOrderId));
        map.put("signtureData",purchaseData);
        map.put("signture",dataSignature);
        HttpUtils.getDateFromServicer(UrlConstants.CHECK_GOOGLE_RESULT, map, HttpUtils.HttpMethod.POST, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                if (mCallBackListener!=null){
                    mCallBackListener.onFinish(response);
                }
            }

            @Override
            public void onError(Exception e) {
                if (mCallBackListener!=null){
                    mCallBackListener.onError(e);
                }
            }
        });

    }
}
