package com.ifreeplay.ifreeplaysdk.payment;

import android.content.Context;

import com.google.gson.Gson;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.WechatPayCallBackListener;
import com.ifreeplay.ifreeplaysdk.model.UnifiedOrder;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/29.
 */

public class WechatPay {
    //调起统一下单接口
    public static void pay(long orderId, final Context context, final WechatPayCallBackListener wechatPayCallBackListener){
        Map<String, String> map = new HashMap<>();
        map.put("orderId",String.valueOf(orderId));
        HttpUtils.getDateFromServicer(UrlConstants.GETORDERPARAMETERS, map, HttpUtils.HttpMethod.POST, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                UnifiedOrder unifiedOrder = gson.fromJson(response, UnifiedOrder.class);
                if (unifiedOrder.getCode()==0){
                    //调起微信支付
                    IWXAPI wxapi = WXAPIFactory.createWXAPI(context, unifiedOrder.getData().getAppId());
                    wxapi.registerApp(unifiedOrder.getData().getAppId());
                    PayReq request = new PayReq();
                    request.appId = unifiedOrder.getData().getAppId();
                    request.partnerId = unifiedOrder.getData().getPartnerId();
                    request.prepayId= unifiedOrder.getData().getPrepayId();
                    request.packageValue = unifiedOrder.getData().getPackageValue();
                    request.nonceStr= unifiedOrder.getData().getNonceStr();
                    request.timeStamp= unifiedOrder.getData().getTimeStamp();
                    request.sign= unifiedOrder.getData().getSign();
                    wxapi.sendReq(request);
                }else {
                    wechatPayCallBackListener.onFinish(unifiedOrder.getMessage());
                }
            }
            @Override
            public void onError(Exception e) {
                wechatPayCallBackListener.onError(e);
            }
        });
    }
}
