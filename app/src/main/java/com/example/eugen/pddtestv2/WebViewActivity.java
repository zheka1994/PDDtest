package com.example.eugen.pddtestv2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        wb = (WebView)findViewById(R.id.wv);
        String url = getIntent().getStringExtra(MenuActivity.URL);
        wb.loadUrl(url);
    }
}
