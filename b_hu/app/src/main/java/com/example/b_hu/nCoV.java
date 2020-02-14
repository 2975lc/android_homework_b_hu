package com.example.b_hu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
public class nCoV extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final WebView nCoV= (WebView) getActivity().findViewById(R.id.nCoV_real_time_spidemic);
        //摘自网上用以解决webview闪烁后白屏（暂时未知其原理）
        nCoV.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        nCoV.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        nCoV.getSettings().setDomStorageEnabled(true);
        nCoV.getSettings().setDatabaseEnabled(true);
        nCoV.getSettings().setAppCacheEnabled(true);
        nCoV.getSettings().setAllowFileAccess(true);
        nCoV.getSettings().setSavePassword(true);
        nCoV.getSettings().setSupportZoom(true);
        nCoV.getSettings().setBuiltInZoomControls(true);
        nCoV.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        nCoV.getSettings().setJavaScriptEnabled(true);
        nCoV.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        nCoV.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        nCoV.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        nCoV.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        nCoV.setWebViewClient(new WebViewClient() {
            @Override
            //重写此方法使连接跳转在该WebView内进行
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        nCoV.loadUrl("https://3g.dxy.cn/newh5/view/pneumonia");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home_page_ncov,container,false);
    }
}