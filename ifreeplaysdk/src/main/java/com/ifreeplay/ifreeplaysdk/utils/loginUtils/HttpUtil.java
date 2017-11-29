package com.ifreeplay.ifreeplaysdk.utils.loginUtils;



import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {


    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL(address);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine())!=null){
                                response.append(line);
                            }
                            if (listener!= null){
                                listener.onFinish(response.toString());
                            }
                        } catch (MalformedURLException e) {
                            if (listener!=null){
                                listener.onError(e);
                            }
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

    }
}
