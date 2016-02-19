package com.example.rsrivatsan.webfill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends Activity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("NETACCESS", MODE_PRIVATE);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");
        final String radio = (prefs.getInt("radio",-1)-1)+"";
        if(username.equals("") || password.equals("") || radio.equals("-2")) {
            startActivity(new Intent(MainActivity.this, Settings.class));
        }
        else {
            WebView wv = new WebView(this);
            //wv.getSettings().setAllowFileAccess(true);
            //wv.getSettings().setAllowFileAccessFromFileURLs(true);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebChromeClient(new WebChromeClient());
            wv.getSettings().setDomStorageEnabled(true);
        /*CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(false);
        WebSettings ws = wv.getSettings();
        ws.setSaveFormData(false);
        ws.setSavePassword(false);*/
            wv.loadUrl("https://netaccess.iitm.ac.in/account/login");
            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);

            wv.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView v, String url) {
                    Log.d("main", url);
                    //v.loadUrl(url);
                /*if(url.equals("https://netaccess.iitm.ac.in/") || url.equals("https://netaccess.iitm.ac.in/account/index")) {
                    v.loadUrl(url);

                }
                else*/
                    final String prevUrl=url;
                    v.loadUrl("javascript:(function(){" +
                            "var%20name=document.getElementById('username');if(name){name.value='" + username + "';}" +
                            "var%20pass=document.getElementById('password');if(pass){pass.value='" + password + "';}" +
                            "var%20b=document.getElementById('submit');if(b){b.click();}})();");
                    Log.d("url",url);
                    v.setWebViewClient(new WebViewClient() {


                        @Override
                        public void onPageFinished(WebView v, String url) {
                            Log.d("logged", url);
                            if(!prevUrl.equals(url)) {
                                v.loadUrl("javascript:(function(){" +
                                        "var%20app=document.getElementsByTagName('a')[3];if(app){app.click();}})();");
                                v.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onPageFinished(WebView v, String url) {
                                        Log.d("approve", url);
                                        v.loadUrl("javascript:(function(){" +
                                                "var%20rad=document.getElementById('radios-" + radio + "');if(rad){rad.click();}" +
                                                "var%20bu=document.getElementById('approveBtn');if(bu){bu.click();}})();");
                                        v.setWebViewClient(new WebViewClient() {


                                            @Override
                                            public void onPageFinished(WebView v, String url) {

                                                /*v.loadUrl("javascript:(function(){" +
                                                        "var%20l=document.getElementsByTagName('a')[1];if(l){l.click();}})();");*/
                                                Toast.makeText(getApplicationContext(), "Successfully Approved", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                                finish();
                                            }

                                            @Override
                                            public void onReceivedError(WebView v, int errorCode, String description, String failingUrl) {
                                                Toast.makeText(getApplicationContext(), "Error : Check Your Internet Connections", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onReceivedError(WebView v, int errorCode, String description, String failingUrl) {
                                        Toast.makeText(getApplicationContext(), "Error : Check Your Internet Connections", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password is incorrect",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this,Settings.class));
                            }
                        }

                        @Override
                        public void onReceivedError(WebView v, int errorCode, String description, String failingUrl) {
                            Toast.makeText(getApplicationContext(), "Error : Check Your Internet Connections", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();
                        }
                    });
                }

                @Override
                public void onReceivedError(WebView v, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getApplicationContext(), "Error : Check Your Internet Connections", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }
            });
            //setContentView(wv);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
