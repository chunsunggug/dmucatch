package com.example.dmu.dmucatch.utility;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.StringUrl;

public class DaumAddressApi extends AppCompatActivity {
    private WebView daum_webView;
    public TextView daum_result;
    private Handler handler;
    private Intent adsIntent;

    StringUrl url = new StringUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_address_daum);
        daum_result = (TextView) findViewById(R.id.daum_result);
        // WebView 초기화
        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }

    public void init_webView() {
        // WebView 설정
        daum_webView = (WebView) findViewById(R.id.daum_webview);
        // JavaScript 허용
        daum_webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        daum_webView.addJavascriptInterface(new AndroidBridge(), "dmuCatch");
        // web client 를 chrome 으로 설정
        daum_webView.setWebChromeClient(new WebChromeClient());
        // webview url load. php 파일 주소
        daum_webView.loadUrl(url.AddressApi);
    }


        private class AndroidBridge {
            @JavascriptInterface
            public void setAddress(final String zCode, final String address, final String buildNm) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        daum_result.setText(String.format("(%s) %s %s", zCode, address, buildNm));

                        DaumAddressVo tempAddress = new DaumAddressVo();

                        tempAddress.setZipCode(zCode);
                        tempAddress.setAddress(address);
                        tempAddress.setBuidName(buildNm);

                        // 상위 클래스로부터 intent 받음
                        adsIntent = getIntent();

                        adsIntent.putExtra("zipCode", zCode);
                        adsIntent.putExtra("address", address);
                        adsIntent.putExtra("buidName", buildNm);

                        setResult(RESULT_OK, adsIntent);
                        // 상위 클래스로 보낼 intent extra 세팅

                        // WebView를 초기화 하지않으면 재사용할 수 없음
                        init_webView();
                        finish();
                    }
                });
            }
        }
    }





