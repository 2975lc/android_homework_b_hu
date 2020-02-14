package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static String account_name="";
    private static int state=0;
    public static String getAccount_name() {
        return account_name;
    }
    public static void setAccount_name(String account_name) {
        MainActivity.account_name = account_name;
    }
    public static int getState() {
        return state;
    }
    public static void setState(int state) {
        MainActivity.state = state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        to_home_page();
    }
    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }
    //延时后跳转至主页
    private void to_home_page(){
        final Intent intent =new Intent(MainActivity.this,home_page.class);
        Timer timer=new Timer();
        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(task,1*1000);
    }
}
