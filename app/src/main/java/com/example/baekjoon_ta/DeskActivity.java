package com.example.baekjoon_ta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//  #3: 지정자리-6202 기준. 1~42번 PC 각 자리 이름 입력, Java 에서 xml 레이아웃 하드코딩, Submit-결과 출력. 엑셀 파일로 붙여넣기

public class DeskActivity extends AppCompatActivity {

    LinearLayout LL1;
    EditText[] input;
    boolean c=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        LL1=findViewById(R.id.LL1);
        input=new EditText[42];
        for(int i=1;i<43;i++) {
            LinearLayout l=new LinearLayout(this);
            LinearLayout.LayoutParams lP=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            l.setOrientation(LinearLayout.HORIZONTAL);
            l.setWeightSum(3);
            TextView t=new TextView(this);
            t.setText("PC-"+i);
            t.setTextColor(Color.rgb(255,255,255));
            lP.weight=2;
            lP.setMargins(10,0,0,0);
            t.setLayoutParams(lP);
            input[i-1]=new EditText(this);
            input[i-1].setHint("Input Name");
            input[i-1].setHintTextColor(Color.rgb(200,200,200));
            input[i-1].setTextColor(Color.rgb(255,255,255));
            lP.weight=1;
            input[i-1].setLayoutParams(lP);
            l.addView(t);
            l.addView(input[i-1]);
            if(c) {
                l.setBackgroundColor(Color.rgb(120,120,120));
                c=false;
            } else {
                l.setBackgroundColor(Color.rgb(60,60,60));
                c=true;
            }
            LL1.addView(l);
        }
        Button b=new Button(this);
        b.setText("Submit");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r="";
                for(int i=0;i<input.length;i++) r+=input[i].getText()+"\n";
                Log.i("result", r);  //result 결과값 출력. System.out 시 초과 문자열 압축 기능 실행
            }
        });
        LL1.addView(b);
    }
}
