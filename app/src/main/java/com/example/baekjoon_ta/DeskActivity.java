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

import java.util.Arrays;

//  #3: 지정자리-6202 기준. 1~42번 PC 각 자리 이름 입력, Java 에서 xml 레이아웃 하드코딩, Submit-결과 출력. 엑셀 파일로 붙여넣기

public class DeskActivity extends AppCompatActivity {

    LinearLayout LL1,LL[];
    EditText[] name,number;
    String[] result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        LL1=findViewById(R.id.LL1);
        LL=new LinearLayout[2];
        LinearLayout.LayoutParams backLP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        backLP.weight=1;
        LinearLayout.LayoutParams nameLP=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        nameLP.weight=2;
        nameLP.setMargins(30,0,0,10);
        LinearLayout.LayoutParams numLP=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        numLP.weight=1;
        numLP.setMargins(80,0,0,10);
        name=new EditText[51];
        number=new EditText[51];
        for(int i=1;i<51;i+=2) {
            LL[0]=new LinearLayout(this);
            LL[0].setWeightSum(2);
            for(int j=i;j<i+2;j++) {
                LL[1] = new LinearLayout(this);
                LL[1].setLayoutParams(backLP);
                LL[1].setOrientation(LinearLayout.HORIZONTAL);
                LL[1].setWeightSum(3);
                number[j]=new EditText(this);
                number[j].setTextColor(Color.rgb(255,255,255));
                number[j].setLayoutParams(numLP);
                name[j]=new EditText(this);
                name[j].setHint("Name");
                name[j].setHintTextColor(Color.rgb(200,200,200));
                name[j].setTextColor(Color.rgb(255,255,255));
                name[j].setLayoutParams(nameLP);
                LL[1].addView(number[j]);
                LL[1].addView(name[j]);
                if(j%2==1) LL[1].setBackgroundColor(Color.rgb(120,120,120));
                else LL[1].setBackgroundColor(Color.rgb(60,60,60));
                LL[0].addView(LL[1]);
            }
            LL1.addView(LL[0]);
        }
        result=new String[51];
        Arrays.fill(result,"");
        Button b=new Button(this);
        b.setText("Submit");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r="";
                for(int i=1;i<number.length;i++) {
                    if(!number[i].getText().toString().equals(""))
                        result[Integer.parseInt(number[i].getText().toString())]=name[i].getText().toString().trim();
                }
                System.out.println(Arrays.toString(result));
                for(int i=1;i<result.length;i+=2) {
                    if(!result[i].equals(""))  r+=result[i];
                    r+="\t";
                    if(!result[i+1].equals("")) r+=result[i+1];
                    r+="\n";
                }
//                Log.i("result", r);  //result 결과값 출력. System.out 시 초과 문자열 압축 기능 실행
                System.out.print(r);
            }
        });
        LL1.addView(b);
    }
}
