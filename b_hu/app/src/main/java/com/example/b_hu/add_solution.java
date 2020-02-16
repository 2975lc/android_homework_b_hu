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

public class add_solution extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);
        MainActivity.setState(0);
        final RecommentContent get_issue = (RecommentContent)getIntent().getSerializableExtra("add_solution");
        final String name = (String)getIntent().getSerializableExtra("add_solution_name");
        final EditText Solution = (EditText)findViewById(R.id.add_solution_solution_text);
        final Button submit = (Button)findViewById(R.id.add_solution_submit);
        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.account_add_solution_progress);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String solution=Solution.getText().toString();
                if (solution.length()>0&&solution.length()<=20000){
                    String address="http://49.233.136.74:2975/resolve/"+name+"/"+solution+"/"+get_issue.getIssue_sequence();
                    sendRequestWithHttpURLconnection(address);
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (MainActivity.getState() ==1){
                                Toast.makeText(add_solution.this,"回答成功",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(add_solution.this,issue.class);
                                startActivity(intent);
                            }else if (MainActivity.getState() ==2){
                                Toast.makeText(add_solution.this,"回答异常，请重试",Toast.LENGTH_LONG).show();
                            }else if (MainActivity.getState() ==0){
                                Toast.makeText(add_solution.this,"回答失败，请重试",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 3000);
                }else if (solution.length()==0){
                    Toast.makeText(add_solution.this,"请输入您的答案",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(add_solution.this,"您的答案过长，请删减后再试",Toast.LENGTH_LONG).show();
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
