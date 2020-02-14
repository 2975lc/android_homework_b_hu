package com.example.b_hu;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static void sendRequestWithHttpURLconnection(final String address,final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    //读取输入流
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder respose = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        respose.append(line);
                    }
                    if (listener!=null)
                        listener.onFinish(respose.toString());
                } catch (Exception e) {
                    if (listener!=null)
                        listener.onError(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
