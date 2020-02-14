package com.example.b_hu;

import android.app.Application;

public class global_variable extends Application{
    private String account_name;
    private int state;
    public String getAccount_name(){
        return this.account_name;
    }
    public void setAccount_name(String account_name){
        this.account_name = account_name;
    }
    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state=state;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        account_name="";
        state=0;
    }
}
