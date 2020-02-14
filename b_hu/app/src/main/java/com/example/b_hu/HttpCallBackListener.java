package com.example.b_hu;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError (Exception e);
}
