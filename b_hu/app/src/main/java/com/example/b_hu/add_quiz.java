package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class add_quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        MainActivity.setState(0);
        final EditText Quiz = (EditText)findViewById(R.id.add_quiz_quiz_text);
        final EditText Quiz_describe = (EditText)findViewById(R.id.add_quiz_quiz_describe_text);
        final Button submit = (Button)findViewById(R.id.add_quiz_submit);
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.account_add_quiz_progress);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quiz=Quiz.getText().toString();
                String quiz_describe=Quiz_describe.getText().toString();
                if (quiz.length()>0&&quiz.length()<=100){
                    if (quiz_describe.length()>0&&quiz_describe.length()<=20000){
                        String address="http://49.233.136.74:2975/quiz/"+ MainActivity.getAccount_name() +"/"+quiz+"/"+quiz_describe;
                        sendRequestWithHttpURLconnection(address);
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (MainActivity.getState() ==1){
                                    Toast.makeText(add_quiz.this,"提问成功",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(add_quiz.this,home_page.class);
                                    startActivity(intent);
                                }else if (MainActivity.getState() ==2){
                                    Toast.makeText(add_quiz.this,"提问异常，请重试",Toast.LENGTH_LONG).show();
                                }else if (MainActivity.getState() ==0){
                                    Toast.makeText(add_quiz.this,"提问失败，请重试",Toast.LENGTH_LONG).show();
                                }
                            }
                        }, 3000);
                    }else if (quiz_describe.length()==0){
                        Toast.makeText(add_quiz.this,"请输入题目描述",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(add_quiz.this,"题目描述过长，请删减",Toast.LENGTH_LONG).show();
                    }
                }else if (quiz.length()==0){
                    Toast.makeText(add_quiz.this,"请输入题目",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(add_quiz.this,"题目过长，请删减",Toast.LENGTH_LONG).show();
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
                MainActivity.setState(jsonObject.getInt("account"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
