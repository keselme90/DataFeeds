package com.keselman.edward.feeder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.keselman.edward.feeder.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("TITLE"));
        setSupportActionBar(toolbar);

        mWebview = findViewById(R.id.webview);
//        mWebview.loadUrl(intent.getStringExtra("URL_TO_OPEN"));
        mWebview.loadUrl("https://www.google.com");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
