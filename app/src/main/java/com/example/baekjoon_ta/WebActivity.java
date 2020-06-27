package com.example.baekjoon_ta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.baekjoon_ta.MainActivity.ID_LIST;
import static com.example.baekjoon_ta.MainActivity.idIndex;
import static com.example.baekjoon_ta.MainActivity.isCase;

public class WebActivity extends Activity {

    Button submit,next,B,sR,b0,b1;
    WebView Web;
    EditText ID, PN, bT;
    WebSettings mWebSettings;
    String resultString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        // #1 _ 백준 채점1: ID와 문제 번호ProblemNumber 합쳐서 WebView 에 실행.
        Web = findViewById(R.id.Web);       //WebView Web set
        ID = findViewById(R.id.ID);         //EditText ID set
        next = findViewById(R.id.next);     //Button next set
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //next - 아이디 목록에서 다음 참조.
                    ID.setText(ID_LIST[isCase][idIndex++ % ID_LIST[isCase].length]);    //크기까지 반복
            }
        });

        PN = findViewById(R.id.PN);         //EditText PN set
        submit = findViewById(R.id.browse); //Button submit set
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //submit - WebView 에 채점 현황 표시.
                Web.setWebViewClient(new WebViewClient());
                mWebSettings = Web.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                // 구버전 복구 중 버그 발생-디버깅
                String target="https://www.acmicpc.net/status?problem_id=" + PN.getText().toString() + "&user_id=" + ID.getText().toString() + "&language_id=-1&result_id=-1";
                Web.setWebChromeClient(new WebChromeClient());
                Web.setWebViewClient(new WebViewClient());
                System.out.println(target);
                Web.loadUrl(target);
            }
        });
        // #1 _ 2-1: rollback for presentation. 구버전 복구
        sR=findViewById(R.id.sR);
        B=findViewById(R.id.B);
        bT=findViewById(R.id.bT);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultString+=bT.getText();
            }
        });
        sR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("result",resultString);
            }
        });
        b0=new Button(this);
        b1=new Button(this);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultString+="0\n";
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultString+="1\n";
            }
        });
    }
}
