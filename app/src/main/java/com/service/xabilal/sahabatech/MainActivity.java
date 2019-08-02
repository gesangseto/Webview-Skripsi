package com.service.xabilal.sahabatech;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webview;
    public MainActivity() {
    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid of the android title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the XML layout
        setContentView(R.layout.activity_main);
        // Bundle objectbundle = this.getIntent().getExtras();
        webview = (WebView) findViewById(R.id.webView);
        final Activity activity = this;
        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Silahkan tunggu...", true);
        // Enable JavaScript and lets the browser go back
        webview.getSettings().setJavaScriptEnabled(true);
       // webview.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; rv:13.0) Gecko/20100101 Firefox/12");
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webview.canGoBack();


        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("about:blank");
                Toast.makeText(getApplicationContext(),"Anda tidak terkoneksi dengan server",Toast.LENGTH_LONG).show();// Set your own toast  message
                view.loadUrl("file:///android_asset/Login.html");

            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String webUrl = webview.getUrl();
                pd.dismiss();
            }

        });
        // The URL that webview is loading
        webview.loadUrl("http://xabilal.xyz/user/");
        //webview.loadUrl("http://192.168.1.200/user_new/");
    }
}
