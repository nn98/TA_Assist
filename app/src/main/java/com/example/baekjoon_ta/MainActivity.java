package com.example.baekjoon_ta;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText ID,PN;
    Button submit,next;
    WebView Web;
    WebSettings mWebSettings;
    int idIndex=0;
    //리스트뷰 제작 후 선택 기능 추가
    static final String[] ID_LIST= {
            "es2qorgus",
            "sumin00j",
            "201811006",
            "yjs06070",
            "rabonim",
            "asfg15",
            "ironhak1106",
            "201814034",
            "bmb1416",
            "gustn8523",
            "gkdus023",
            "a3920679",
            "kuntek1953",
            "hjk0385",
            "cjs1399",
            "doseon1226",
            "jiyeongstar",
            "leemoon63",
            "qwe916",
            "ggs4029",
            "06zzkimzz06",
            "kj980926",
            "twoddal2",
            "hj980608",
            "zoeyourlife",
            "7608guswns",
            "shc3113",
            "jiwoo60",
            "shmoon12",
            "201814128",
            "dlaxodud1217",
            "201814135",
            "s9430939",
            "980lje"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,ID_LIST);
        //ListView IDL=findViewById(R.id.Idlist);
        //IDL.setAdapter(adapter);
        ID=findViewById(R.id.ID);
        PN=findViewById(R.id.PN);
        submit=findViewById(R.id.submit);
        Web=findViewById(R.id.Web);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID.setText(ID_LIST[idIndex++%ID_LIST.length]);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.setWebViewClient(new WebViewClient());
                mWebSettings = Web.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                Web.loadUrl("https://www.acmicpc.net/status?problem_id="+PN.getText().toString()+"&user_id="+ID.getText().toString()+"&language_id=-1&result_id=-1");
                /*
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri u=Uri.parse("https://www.acmicpc.net/status?problem_id="+PN.getText().toString()+"&user_id="+ID.getText().toString()+"&language_id=-1&result_id=-1");
                intent.setData(u);
                startActivity(intent);
                 */
            }
        });
    }
}