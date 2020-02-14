package com.example.b_hu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class recomment extends Fragment {
    private List<RecommentContent>recomment=new ArrayList<>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //获取存在延时
//        String address="http://49.233.136.74:2975/show_quiz";
//        sendRequestWithHttpURLconnection(address);
        tmp();
        FloatingActionButton add_quiz = (FloatingActionButton)getActivity().findViewById(R.id.recomment_add_quiz);
        add_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.getAccount_name() !=""){
                    //跳转至提问界面
                    Intent intent =new Intent(getContext(),add_quiz.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_LONG).show();
                    //跳转至登录界面
                    Intent intent =new Intent(getContext(),account_register.class);
                    startActivity(intent);
                }
            }
        });
        final RecommentAdapter recommentAdapter=new RecommentAdapter(getContext(),R.layout.recommnet_listview,recomment);
        ListView listView=(ListView)getActivity().findViewById(R.id.home_page_recomment_listview);
        listView.setAdapter(recommentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getContext(),issue.class);
                startActivity(intent);
                RecommentContent recommentContent=recomment.get(position);
                Intent issue = new Intent(getContext(),issue.class);
                issue.putExtra("issue",recommentContent);
                startActivity(issue);
            }
        });
    }
    private void tmp(){
        for (int i=0;i<3;i++){
            RecommentContent test=new RecommentContent("1","1","issue_quiz","issue_quizzer","issue_quiz_describe");
            recomment.add(test);
        }
    }
    //获取网络明文
    public void sendRequestWithHttpURLconnection(final String address){
        RecommentContent t=new RecommentContent("","","","","");
        recomment.add(t);
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
                String issue_quizzer = jsonObject.getString("issue_quizzer");
                String issue_quiz = jsonObject.getString("issue_quiz");
                String issue_quiz_describe = jsonObject.getString("issue_quiz_describe");
                String issue_sequence = jsonObject.getString("issue_sequence");
                String issue_attention = jsonObject.getString("issue_attention");
                String issue_exist = jsonObject.getString("issue_exist");
                RecommentContent test=new RecommentContent(issue_sequence,issue_exist,issue_quiz,issue_quizzer,issue_quiz_describe);
                recomment.add(test);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home_page_recomment,container,false);
    }
}