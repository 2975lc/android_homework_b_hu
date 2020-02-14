package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class comment extends AppCompatActivity {
    private List<CommentContent> comment=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final SolutionContent get_solution = (SolutionContent) getIntent().getSerializableExtra("comment");
        String address="http://49.233.136.74:2975/show_review/"+get_solution.getSolution_sequence()+"/"+get_solution.getSolution_floor();
        sendRequestWithHttpURLconnection(address);
        CommentAdapter commentAdapter=new CommentAdapter(comment.this,R.layout.solution_listview,comment);
        ListView listView=(ListView)findViewById(R.id.solution_listview);
        listView.setAdapter(commentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainActivity.getAccount_name() !=""){
                    Intent intent1 = new Intent(comment.this,add_comment.class);
                    intent1.putExtra("add_comment",get_solution);
                    startActivity(intent1);
                    //跳转至评论界面
                    Intent intent =new Intent(comment.this,add_comment.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(comment.this,"请先登录",Toast.LENGTH_LONG).show();
                    //跳转至登录界面
                    Intent intent =new Intent(comment.this,account_register.class);
                    startActivity(intent);
                }
            }
        });
    }
    //获取网络明文
    public void sendRequestWithHttpURLconnection(final String address){
        CommentContent t=new CommentContent("test","test","1","1","1","1","1","1","1");
        comment.add(t);
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
                String comment_possessor = jsonObject.getString("comment_possessor");
                String comment_discuss = jsonObject.getString("comment_discuss");
                String comment_sequence = jsonObject.getString("comment_sequence");
                String comment_floor = jsonObject.getString("comment_floor");
                String comment_tier = jsonObject.getString("comment_tier");
                String comment_little_tier = jsonObject.getString("comment_little_tier");
                String comment_star = jsonObject.getString("comment_star");
                String comment_praise = jsonObject.getString("comment_praise");
                String comment_exist = jsonObject.getString("comment_exist");
                CommentContent test=new CommentContent(comment_possessor,comment_discuss,comment_sequence,comment_floor,comment_tier,comment_little_tier,comment_star,comment_praise,comment_exist);
                comment.add(test);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
