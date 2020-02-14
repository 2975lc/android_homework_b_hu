package com.example.b_hu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

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

public class home_page extends AppCompatActivity implements View.OnClickListener{
    private List<RecommentContent> recommentContents=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        to_search();
        to_bookrack();
        to_live();
        change_fragment();
    }
    //下标条
    private ImageView attention_strip;
    private ImageView recomment_strip;
    private ImageView host_strip;
    private ImageView nCoV_strip;
    private String[] test_data = {"apple","banana"};
    //手势识别器
    private GestureDetector mGestureDetector;
    //跳转至搜索界面
    private void to_search(){
        final Intent intent_search =new Intent(home_page.this,search_page.class);
        final ImageView search_icon =(ImageView) findViewById(R.id.home_page_serach_icon);
        final TextView search_text =(TextView) findViewById(R.id.home_page_search_text);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_search);
            }
        });
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_search);
            }
        });
    }
    //跳转至书架界面
    private void to_bookrack(){
        final Intent intent_bookrack =new Intent(home_page.this,bookrack_page.class);
        final ImageView bookrack =(ImageView) findViewById(R.id.home_page_bookrack_icon);
        bookrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_bookrack);
            }
        });
    }
    //跳转至直播界面
    private void to_live(){
        final Intent intent_live =new Intent(home_page.this,bookrack_page.class);
        final ImageView live =(ImageView) findViewById(R.id.home_page_live_icon);
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_live);
            }
        });
    }
    //碎片切换
    private void change_fragment(){
        //下标条
        attention_strip = (ImageView)findViewById(R.id.bottom_strip_attention);
        recomment_strip = (ImageView)findViewById(R.id.bottom_strip_recomment);
        host_strip = (ImageView)findViewById(R.id.bottom_strip_host);
        nCoV_strip = (ImageView)findViewById(R.id.bottom_strip_nCoV);
        //初始化内容界面
        replaceFragment(new nCoV(),nCoV_strip);
        //转换为关注界面
        final Button attention =(Button)findViewById(R.id.home_page_title_attention);
        attention.setOnClickListener(this);
        //转换为推荐界面
        final Button recomment =(Button)findViewById(R.id.home_page_title_recomment);
        recomment.setOnClickListener(this);
        //转换为热榜界面
        final Button host =(Button)findViewById(R.id.home_page_title_host);
        host.setOnClickListener(this);
        //转换为nCoV界面
        final Button nCoV =(Button)findViewById(R.id.home_page_title_nCoV);
        nCoV.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_page_title_attention:
                replaceFragment(new attention(),attention_strip);
                break;
            case R.id.home_page_title_recomment:
                replaceFragment(new recomment(),recomment_strip);
                break;
            case R.id.home_page_title_host:
                replaceFragment(new host(),host_strip);
                break;
            case R.id.home_page_title_nCoV:
                replaceFragment(new nCoV(),nCoV_strip);
                break;
            default:
                break;
        }
    }
    //更换内容碎片
    private void replaceFragment(Fragment fragment,ImageView strip){
        if (attention_strip.getVisibility()==View.VISIBLE)
            attention_strip.setVisibility(View.INVISIBLE);
        if (recomment_strip.getVisibility()==View.VISIBLE)
            recomment_strip.setVisibility(View.INVISIBLE);
        if (host_strip.getVisibility()==View.VISIBLE)
            host_strip.setVisibility(View.INVISIBLE);
        if (nCoV_strip.getVisibility()==View.VISIBLE)
            nCoV_strip.setVisibility(View.INVISIBLE);
        strip.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_page_content,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    //滑动翻页函数(无效)
    private void change_page(){
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
                // e1: 第一次按下的位置   e2   当手离开屏幕 时的位置  velocityX  沿x 轴的速度  velocityY： 沿Y轴方向的速度
                //判断竖直方向移动的大小
                if(Math.abs(e1.getRawY() - e2.getRawY())>100){//Toast.makeText(getApplicationContext(), 动作不合法, 0).show();
                    return true;
                }
                if(Math.abs(velocityX)<150){//Toast.makeText(getApplicationContext(), 移动的太慢, 0).show();
                    return true;
                }
                if((e1.getRawX() - e2.getRawX()) >200){//向右翻页
                    slide_page(1);
                    return true;
                }
                if((e2.getRawX() - e1.getRawX()) >200){//向左翻页
                    slide_page(-1);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    //滑动翻页(无效)
    private void slide_page(int slide){
        if (slide==1){
            if (attention_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new recomment(),recomment_strip);
            }else if (recomment_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new host(),host_strip);
            }else if (host_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new nCoV(),nCoV_strip);
            }else if (nCoV_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new attention(),attention_strip);
            }
        }else {
            if (attention_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new nCoV(),nCoV_strip);
            }else if (recomment_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new attention(),attention_strip);
            }else if (host_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new recomment(),recomment_strip);
            }else if (nCoV_strip.getVisibility()==View.VISIBLE){
                replaceFragment(new host(),host_strip);
            }
        }
    }
}
