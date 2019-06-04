package com.example.baekjoon_ta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    Button submit,next;
    WebView Web;
    EditText ID, PN;
    WebSettings mWebSettings;

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
                    ID.setText(ID_LIST[isCase][idIndex++ % ID_LIST.length]);    //크기까지 반복
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
                Web.loadUrl("https://www.acmicpc.net/status?problem_id=" + PN.getText().toString() + "&user_id=" + ID.getText().toString() + "&language_id=-1&result_id=-1");
            }
        });

    }
}
