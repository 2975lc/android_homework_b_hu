package com.example.b_hu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class issue extends AppCompatActivity {
    private List<SolutionContent> solution=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        final RecommentContent get_issue = (RecommentContent)getIntent().getSerializableExtra("issue");
        TextView issue_quiz = (TextView)findViewById(R.id.issue_quiz);
        TextView issue_quiz_describe = (TextView)findViewById(R.id.issue_quiz_describe);
        issue_quiz.setText(get_issue.getIssue_quiz());
        issue_quiz_describe.setText(get_issue.getIssue_quiz_describe());
        String address="http://49.233.136.74:2975/show_resolve/"+get_issue.getIssue_sequence();
        sendRequestWithHttpURLconnection(address);
        SolutionAdapter solutionAdapter=new SolutionAdapter(issue.this,R.layout.issue_listview,solution);
        ListView listView=(ListView)findViewById(R.id.issue_listview);
        listView.setAdapter(solutionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SolutionContent solutionConnet=solution.get(position);
                Intent solution = new Intent(issue.this,comment.class);
                solution.putExtra("comment",solutionConnet);
                startActivity(solution);
                Intent intent =new Intent(issue.this,comment.class);
                startActivity(intent);
            }
        });
        FloatingActionButton add_quiz = (FloatingActionButton)findViewById(R.id.issue_add_solution);
        add_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.getAccount_name() !=""){
                    //跳转至回答界面
                    Intent intent1 = new Intent(issue.this,add_solution.class);
                    intent1.putExtra("add_solution",get_issue);
                    startActivity(intent1);
                    Intent intent =new Intent(issue.this,add_solution.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(issue.this,"请先登录",Toast.LENGTH_LONG).show();
                    //跳转至登录界面
                    Intent intent =new Intent(issue.this,account_register.class);
                    startActivity(intent);
                }
            }
        });
    }
    //获取网络明文
    public void sendRequestWithHttpURLconnection(final String address){
        SolutionContent t=new SolutionContent("test","test","1","1","1","1");
        solution.add(t);
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
                String solution_possessor = jsonObject.getString("solution_possessor");
                String solution_answer = jsonObject.getString("solution_answer");
                String solution_sequence = jsonObject.getString("solution_sequence");
                String solution_floor = jsonObject.getString("solution_floor");
                String solution_praise = jsonObject.getString("solution_praise");
                String solution_exist = jsonObject.getString("solution_exist");
                SolutionContent test=new SolutionContent(solution_possessor,solution_answer,solution_sequence,solution_floor,solution_praise,solution_exist);
                solution.add(test);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}