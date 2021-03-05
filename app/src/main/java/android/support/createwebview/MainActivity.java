package android.support.createwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView wView;      // 웹뷰
    ProgressBar pBar;   // 로딩바
    EditText urlEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wView = findViewById(R.id.wView);   // 웹뷰
        pBar =  findViewById(R.id.pBar);    // 로딩바
        pBar.setVisibility(View.GONE);      // 로딩바 가리기 (로딩때만 보여야 함)

        initWebView();                      // 웹뷰 초기화

        // +추가> 주소 입력창 (주소 입력 -> 키보드 엔터 -> 해당 웹사이트 접속)
        urlEt = findViewById(R.id.urlEt);
        urlEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){                  // 키보드의 엔터키를 눌러서
                    wView.loadUrl("https://"+urlEt.getText().toString()+""); // 입력한 주소 접속
                }
                return false;
            }
        });
    }

    public void initWebView(){
        // 1. 웹뷰클라이언트 연결 (로딩 시작/끝 받아오기)
        wView.setWebViewClient(new WebViewClient(){
            @Override                                   // 1) 로딩 시작
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pBar.setVisibility(View.VISIBLE);       // 로딩이 시작되면 로딩바 보이기
            }
            @Override                                   // 2) 로딩 끝
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("TEST", url);
                pBar.setVisibility(View.GONE);          // 로딩이 끝나면 로딩바 없애기
            }
            @Override                                   // 3) 외부 브라우저가 아닌 웹뷰 자체에서 url 호출
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 2. WebSettings: 웹뷰의 각종 설정을 정할 수 있다.
        WebSettings ws = wView.getSettings();
        ws.setJavaScriptEnabled(true); // 자바스크립트 사용 허가
        // 3. 웹페이지 호출
        wView.loadUrl("https://coupang.com/");
//        wView.loadUrl("https://coupang.com/");

//        wView.loadUrl("https://login.coupang.com/login/login.pang?rtnUrl=https%3A%2F%2Fmc.coupang.com%2Fssr%2Fmobile%2Fthank-you%3ForderId%3D3000094659168%26price%3D3950%26retailPrice%3D0%26payType%3DVCNT%26checkoutId%3D1613664346640%26hasCoupangGlobal%3Dfalse%26agentType%3DW_Android%26isDirectOrder%3Dtrue%26checkoutType%3DDEFAULT%26success%3Dtrue%26message%3D%25EC%25A0%2595%25EC%2583%2581%25EC%25B2%2598%25EB%25A6%25AC%26returnUrl%3Dhttps%253A%252F%252Fmc.coupang.com%252Fm%252ForderResult.pang%253ForderId%253D3000094659168%2526price%253D3950%2526retailPrice%253D0%2526payType%253DROCKET_BANK%2526checkoutId%253D1613664346640%2526hasCoupangGlobal%253Dfalse%2526agentType%253DW_Android%2526isDirectOrder%253Dtrue%2526checkoutType%253DDEFAULT%26passed%3DY");


    }
    @Override
    public void onBackPressed() {
        if(wView.canGoBack()){      // 이전 페이지가 존재하면
            wView.goBack();         // 이전 페이지로 돌아가고
        }else{
            super.onBackPressed();  // 없으면 앱 종료
        }
    }

}