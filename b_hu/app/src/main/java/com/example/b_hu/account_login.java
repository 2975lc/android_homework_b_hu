package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class account_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        MainActivity.setState(0);
        login();
    }
    public void login(){
        final EditText Account = (EditText)findViewById(R.id.account_login_account);
        final EditText Password = (EditText)findViewById(R.id.account_login_password);
        final EditText SecounPassword = (EditText)findViewById(R.id.account_login_second_password);
        final Button Login = (Button)findViewById(R.id.account_login);
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.account_login_progress);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account =Account.getText().toString();
                String password =Password.getText().toString();
                String secoud =SecounPassword.getText().toString();
                if (account.length()<=20&&account.length()>0){
                    if (password.equals(secoud)){
                        if (password.length()<=30&&password.length()>0){
                            String address="http://49.233.136.74:2975/login/"+account+"/"+password;
                            sendRequestWithHttpURLconnection(address);
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (MainActivity.getState() ==1|| MainActivity.getState() ==2){
                                        Toast.makeText(account_login.this,"该账号已注册",Toast.LENGTH_LONG).show();
                                    }else if (MainActivity.getState() ==3){
                                        Toast.makeText(account_login.this,"注册成功",Toast.LENGTH_LONG).show();
                                        finish();
                                    }else if (MainActivity.getState() ==4){
                                        Toast.makeText(account_login.this,"注册失败",Toast.LENGTH_LONG).show();
                                    }else if (MainActivity.getState() ==0){
                                        Toast.makeText(account_login.this,"注册故障",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 3000);
                        }else if (password.length()==0){
                            Toast.makeText(account_login.this,"请输入密码",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(account_login.this,"密码过长，请重试",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(account_login.this,"两次密码不同，请重试",Toast.LENGTH_SHORT).show();
                    }
                }else if (account.length()==0){
                    Toast.makeText(account_login.this,"请输入账号",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(account_login.this,"账号过长，请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //获取网络明文
    public void sendRequestWithHttpURLconnection(final String address){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    if (connection.getResponseCode()==200){
                        InputStream in = connection.getInputStream();
                        //读取输入流
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder respose = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            respose.append(line);
                        }
                        UIoperate(respose.toString());
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //对获取的明文进行UI操作
    private void UIoperate(String response){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                MainActivity.setState(jsonObject.getInt("state"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
