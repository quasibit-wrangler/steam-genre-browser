package com.example.steambrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class GameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);

        getSupportActionBar().setTitle("Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get appid from Intent
        Intent intent = getIntent();
        int appid = intent.getIntExtra("appid", 0);

        final ProgressBar detailProgressBar = findViewById(R.id.detailProgressBar);

        WebView webView=findViewById(R.id.webView);
        // load the game detail website
        webView.loadUrl("https://store.steampowered.com/app/"+appid);

        // set ProgressBar invisible after loading website successfully
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                detailProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
