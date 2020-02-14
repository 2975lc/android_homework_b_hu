package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class account_register extends AppCompatActivity{
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        text=(TextView)findViewById(R.id.location2);
        register();
        to_login();
        to_forget();
    }
    //登录检测
    private void register(){
        MainActivity.setState(0);
        final EditText Account = (EditText)findViewById(R.id.account_register_account);
        final EditText Password = (EditText)findViewById(R.id.account_register_password);
        final Button Register = (Button)findViewById(R.id.account_register);
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.account_register_progress);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account =Account.getText().toString();
                String password =Password.getText().toString();
                String address = "http://49.233.136.74:2975/register/"+account+"/"+password;
                sendRequestWithHttpURLconnection(address,account);
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (MainActivity.getState() ==1) {
                            Toast.makeText(account_register.this, "登录成功", Toast.LENGTH_LONG).show();
                            finish();
                        }else if (MainActivity.getState() ==2){
                            Toast.makeText(account_register.this,"密码错误，请重试",Toast.LENGTH_LONG).show();
                        }else if (MainActivity.getState() ==3){
                            Toast.makeText(account_register.this,"该账号未注册或已注销",Toast.LENGTH_LONG).show();
                        }else if (MainActivity.getState() ==0){
                            Toast.makeText(account_register.this,"登录故障",Toast.LENGTH_LONG).show();
                        }
                    }
                }, 3000);
            }
        });
    }
    //注册账号
    private void to_login(){
        final Button to_login_button = (Button)findViewById(R.id.account_to_login);
        to_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent =new Intent(account_register.this,account_login.class);
                startActivity(intent);
            }
        });
    }
    //忘记密码
    private void to_forget(){
        final Button to_forget_button = (Button)findViewById(R.id.account_to_forget);
        to_forget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(account_register.this,"密码忘了我也没办法，还没写申诉",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //获取网络明文
    public void sendRequestWithHttpURLconnection(final String address,final String account){
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
                        UIoperate(respose.toString(),account);
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
    private void UIoperate(String response,String account){
        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                MainActivity.setState(jsonObject.getInt("state"));
                if (MainActivity.getState() ==1)
                    MainActivity.setAccount_name(account);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
