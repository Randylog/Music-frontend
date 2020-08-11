package com.example.muscinfinder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.muscinfinder.R;

public class NewMapActivity extends AppCompatActivity {
    private WebView mWebView;
    // static map
    String loadMap = "https://goo.gl/maps/E8bwi8p975E6b1aN8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);

        mWebView =  findViewById(R.id.mapView);
        mWebView.loadUrl(loadMap);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
    }
}

