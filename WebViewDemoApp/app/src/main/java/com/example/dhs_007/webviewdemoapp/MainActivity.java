package com.example.dhs_007.webviewdemoapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button b1;
    EditText ed1;

    private WebView wv1;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);

        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        wv1.setInitialScale(1);
        wv1.getSettings().setUseWideViewPort(true);

        progressDialog=new ProgressDialog(wv1.getContext());
        progressDialog.setMessage("Loading...");
        wv1.setWebChromeClient(new WebChromeClient()
        {

            public void onProgressChanged(WebView view, int progress)
            {

                if(!progressDialog.isShowing())
                    progressDialog.show();

/*                MainActivity.this.setTitle("Loading...");*/
                MainActivity.this.setProgress(progress * 100);

                if(progress == 100)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    //MainActivity.this.setTitle(R.string.app_name);
                }
            }
        });

        wv1.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && wv1.canGoBack()) {
                    wv1.goBack();
                    return true;
                }
                return false;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();

                if(!url.startsWith("http"))
                    url="http://"+url;

                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                CookieManager.getInstance().setAcceptCookie(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
