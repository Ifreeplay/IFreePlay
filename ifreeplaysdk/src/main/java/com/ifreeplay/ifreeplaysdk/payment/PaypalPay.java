package com.ifreeplay.ifreeplaysdk.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.PaymentCallBackListener;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/29.
 */

public class PaypalPay {

    private static long mOrderId;
    private static PaymentCallBackListener mCallbackListener;
    public static final int PAYPAL_REQUEST_CODE = 10101;//paypal请求码

    public enum Environment {
        ENVIRONMENT_SANDBOX, // 沙箱
        ENVIRONMENT_PRODUCTION//线上
    }

    public enum CurrencyTypes {
        CNY, // 人民币
        USD, // 美元
        HKD, // 港币
        JPY, // 日元
        GBP, // 英镑
        EUR // 欧元
    }

    private static PayPalConfiguration config;
    private static Context mContext;
    private static String mClientId;

    public static void init(Context context, String clientId, Environment environment){
        mContext = context;
        mClientId = clientId;
        if (environment== Environment.ENVIRONMENT_SANDBOX){
            config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(clientId);
        }else if (environment== Environment.ENVIRONMENT_PRODUCTION){
            config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION).clientId(clientId);
        }
        //启动PayPal服务
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);
    }

    /**
     * 调起PayPal支付
     * @param orderId
     * @param totalPrice
     * @param currencyTypes
     */
    public static void pay(long orderId, int totalPrice, CurrencyTypes currencyTypes, String productName, PaymentCallBackListener callBackListener) {
        mCallbackListener =callBackListener;
        mOrderId =orderId;
        if (currencyTypes== CurrencyTypes.CNY){
            AndroidUtils.shortToast(mContext,"抱歉，暂不支持人民币！");
            return;
        }
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(totalPrice)), currencyTypes.name(), productName,
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(mContext, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        ((Activity)mContext).startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    /**
     * 处理返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PAYPAL_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    JSONObject jsonObject = confirm.toJSONObject();
                    if (jsonObject != null) {
                        JSONObject response = jsonObject.optJSONObject("response");
                        if (response != null) {
                            String payment_id = response.optString("id");
                            // 这里直接跟服务器确认支付结果，支付结果确认后回调处理结果
                            checkPaypalResult(payment_id);
                        }
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                AndroidUtils.shortToast(mContext,"The user canceled.");
            }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                AndroidUtils.shortToast(mContext,"An invalid Payment or PayPalConfiguration was submitted.");
            }
        }

    }

    /**
     * 服务器校验
     * @param payment_id
     */
    private static void checkPaypalResult(String payment_id) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", String.valueOf(mOrderId));
        map.put("paymentId",payment_id);
        map.put("verifyEnvironment","");
        HttpUtils.getDateFromServicer(UrlConstants.CHECK_PAYPAL_RESULT, map, HttpUtils.HttpMethod.POST, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                if (mCallbackListener!=null){
                    mCallbackListener.onFinish(response);
                }
            }

            @Override
            public void onError(Exception e) {
                if (mCallbackListener!=null){
                    mCallbackListener.onError(e);
                }
            }
        });

    }

    /**
     * 关闭服务
     */
    public static void unbindService() {
        mContext.stopService(new Intent(mContext, PayPalService.class));
    }

}
