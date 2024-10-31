package com.application.furry_track.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.application.furry_track.R;

import dmax.dialog.SpotsDialog;
import im.delight.android.webview.AdvancedWebView;


public class Webview_Activity extends Base_Activity implements AdvancedWebView.Listener {

    AlertDialog dialog;
    // LinearLayout lin_main;
    private static String TEST_PAGE_URL = "";
    private AdvancedWebView mWebView;
    WebView dss;
    //, txt_day, txt_week, txt_month, txt_year;
    //View view1, view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getColor(R.color.dashboard_text));
        toolbar.setSubtitleTextColor(getColor(R.color.dashboard_text));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TEST_PAGE_URL = extras.getString("page_url");
            getSupportActionBar().setTitle(extras.getString("title"));
        }
        dialog = new SpotsDialog(Webview_Activity.this, getString(R.string.plzwait));
        dialog.setCancelable(false);
        dialog.show();
        mWebView = findViewById(R.id.webview);

        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(true);
        mWebView.setMixedContentAllowed(true);
        mWebView.setDesktopMode(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.setCookiesEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //         Toast.makeText(Webview_Activity.this, "Finished loading", Toast.LENGTH_SHORT).show();
                // lin_main.setVisibility(View.GONE);
                //dialog.dismiss();
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //       	Toast.makeText(Webview_Activity.this, title, Toast.LENGTH_SHORT).show();
            }

        });
        mWebView.addHttpHeader("X-Requested-With", "");
        mWebView.loadUrl(TEST_PAGE_URL);

        // WebView myWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("000000", message + " -- From line "
                        + lineNumber + " of "
                        + sourceID);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        //   mWebView.setVisibility(View.VISIBLE);
        //lin_main.setVisibility(View.VISIBLE);

       /* toast("jkhnlknhi");

        mWebView.evaluateJavascript("javascript:getLocalStorageValue()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                // Handle the value received from local storage
                // 'value' contains the result returned by the JavaScript function
                // You can parse and use the value as needed
                if (value != null && !value.isEmpty()) {
                    // Do something with the local storage value
                    // For example, you can log it or display it in your app
                    System.out.println("Local Storage Value: " + value);
                    toast(value + "sdsdfsdf");
                }


                String cookies = CookieManager.getInstance().getCookie(url);

                // Handle the cookies as needed
                if (cookies != null && !cookies.isEmpty()) {
                    // Do something with the cookies
                    // For example, you can log them or display them in your app
                    System.out.println("jaimin: " + cookies);
                }
            }
        });*/


    }

    @Override
    public void onPageFinished(String url) {
        //      mWebView.setVisibility(View.VISIBLE);
        // lin_main.setVisibility(View.GONE);
        if (dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

        Toast.makeText(Webview_Activity.this, "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

       // toast("sdfsd");
        //	Toast.makeText(MainActivity_sankalan.this, "onDownloadRequested(url = "+url+",  suggestedFilename = "+suggestedFilename+",  mimeType = "+mimeType+",  contentLength = "+contentLength+",  contentDisposition = "+contentDisposition+",  userAgent = "+userAgent+")", Toast.LENGTH_LONG).show();

	/*	if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
			// download successfully handled

		}
		else {
			// download couldn't be handled because user has disabled download manager app on the device
		}*/
    }

    @Override
    public void onExternalPageRequest(String url) {
        //	Toast.makeText(MainActivity_sankalan.this, "onExternalPageRequest(url = "+url+")", Toast.LENGTH_SHORT).show();
    }

}
