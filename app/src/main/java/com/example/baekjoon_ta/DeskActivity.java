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

/**
 * 0907
 * 이름 + \t + 번호 식으로 입력
 * 강의실 선택 후 제출
 * Log 콘솔에 해당 강의실 좌석 배치도에 맞춰 좌석번호, 학생성명 양식 출력
 */

// 짜잔 뻘짓이였습니다~

public class DeskActivity extends AppCompatActivity {

    LinearLayout LL1, LL[], lectureRoom;
    //    EditText[] name;
    EditText input;
    String[] result;
    Button[] lectureRooms;

    static int lectureRoomNumber = -1, i = 0;
    static boolean order = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);

        LL1 = findViewById(R.id.LL1);
        /*
        LL = new LinearLayout[2];
        LinearLayout.LayoutParams backLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        backLP.weight = 1;
        LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        nameLP.weight = 2;
        nameLP.setMargins(30, 0, 0, 10);
        LinearLayout.LayoutParams numLP = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        numLP.weight = 1;
        numLP.setMargins(80, 0, 0, 10);
        name = new EditText[61];
//        number = new EditText[61];
        for (int i = 1; i < name.length; i += 2) {
            LL[0] = new LinearLayout(this);
            LL[0].setWeightSum(2);
            for (int j = i; j < i + 2; j++) {
                LL[1] = new LinearLayout(this);
                LL[1].setLayoutParams(backLP);
                LL[1].setOrientation(LinearLayout.HORIZONTAL);
                LL[1].setWeightSum(2);
//                number[j] = new EditText(this);
//                number[j].setTextColor(Color.rgb(255, 255, 255));
//                number[j].setLayoutParams(numLP);
                name[j] = new EditText(this);
                name[j].setHint("Name");
                name[j].setHintTextColor(Color.rgb(200, 200, 200));
                name[j].setTextColor(Color.rgb(255, 255, 255));
                name[j].setLayoutParams(nameLP);
                LL[1].addView(name[j]);
//                LL[1].addView(number[j]);
                if (j % 2 == 1) LL[1].setBackgroundColor(Color.rgb(120, 120, 120));
                else LL[1].setBackgroundColor(Color.rgb(60, 60, 60));
                LL[0].addView(LL[1]);
            }
            LL1.addView(LL[0]);
        }
        */

//        result = new String[61];
        Button b = new Button(this);
        b.setText("Submit");

        /***레이아웃 대폭 수정***/
        input = new EditText(this);
        LinearLayout.LayoutParams inLP = new LinearLayout.LayoutParams(-1, -2);
        input.setLayoutParams(inLP);
        LL1.addView(input);

        /*********************강의실별 양식 작성*************************/

        //강의실 선택 버튼 관련 코드 추가
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = "------------------------\n";  //결과 출력, 복사용
                String[] in = input.getText().toString().split("\n");
                result = new String[50];
                Arrays.fill(result, "");
                switch (lectureRoomNumber) {    //강의실 판별
                    case 0: //6202 강의실
                        // 좌석 좌 - > 우 증가
                        for (int i = 0; i < in.length; i++) {
//                            if (!name[i].getText().toString().equals("")) {
//                                String[] sp=name[i].getText().toString().split("\t");
//                                result[Integer.parseInt(sp[1])] = sp[0];
//                        }
                            String[] sp = in[i].split("\t");
                            result[Integer.parseInt(sp[1])] = sp[0];

                        }
//                System.out.println(Arrays.toString(result));

                        // 좌석 번호 입력
                        for (int i = 37; i > 0; i -= 6) {
                            r += "\t";
                            for (int j = 0; j < 6; j++) {
                                r += "" + (i + j);
                                if (j % 2 == 0)
                                    r += "\t";
                                else if (j == 5)
                                    break;
                                else
                                    r += "\t\t";
                            }
                            r += "\n";

                            // 학생 이름 입력
                            r += "\t";
                            for (int j = 0; j < 6; j++) {
                                if (!result[i + j].equals("")) r += result[i + j];
                                if (j % 2 == 0)
                                    r += "\t";
                                else if (j == 5)
                                    break;
                                else
                                    r += "\t\t";
                            }
                            r += "\n";
                        }
                        break;
                    case 1: //6405 강의실
                        // 좌석 좌 - > 우 감소
                        for (int i = 1; i < in.length; i++) {
//                            if (!name[i].getText().toString().equals("")) {
//                                String[] sp = name[i].getText().toString().split("\t");
//                                result[Integer.parseInt(sp[1])] = sp[0];
//                            }
                            String[] sp = in[i].split("\t");
                            result[Integer.parseInt(sp[1])] = sp[0];
                        }
//                System.out.println(Arrays.toString(result));

                        // 좌석 번호 입력
                        for (int i = 30; i > 0; i -= 6) {
                            r += "\t";
                            for (int j = 0; j < 6; j++) {
                                r += "" + (i - j);
                                if (j == 2)
                                    r += "\t\t";
                                else if (j == 5)
                                    break;
                                else
                                    r += "\t";
                            }
                            r += "\n";

                            // 학생 이름 입력
                            r += "\t";
                            for (int j = 0; j < 6; j++) {
                                if (!result[i - j].equals("")) r += result[i - j];
                                if (j == 2)
                                    r += "\t\t";
                                else if (j == 5)
                                    break;
                                else
                                    r += "\t";
                            }
                            r += "\n";
                        }
                        break;
                    case 2:
                        // 좌석 좌 - > 우 감소
                        for (int i = 1; i < in.length; i++) {
//                            if (!name[i].getText().toString().equals("")) {
//                                String[] sp = name[i].getText().toString().split("\t");
//                                result[Integer.parseInt(sp[1])] = sp[0];
                            String[] sp = in[i].split("\t");
                            result[Integer.parseInt(sp[1])] = sp[0];
                        }

//                System.out.println(Arrays.toString(result));

                        // 좌석 번호 입력
                        for (int i = 47; i > 0; i -= 7) {
                            r += "\t";
                            for (int j = 0; j < 7; j++) {
                                if ((i - j == 13 | i - j == 7) && j == 6) {
                                    i++;
                                    break;
                                }
                                r += "" + (i - j);
                                if (j == 2)
                                    r += "\t\t";
                                else if (j == 6)
                                    break;
                                else
                                    r += "\t";
                            }
                            r += "\n";

                            // 학생 이름 입력
                            r += "\t";
                            for (int j = 0; j < 6; j++) {
                                if (!result[i - j].equals("")) r += result[i - j];
                                if (j == 2)
                                    r += "\t\t";
                                else if (j == 6)
                                    break;
                                else
                                    r += "\t";
                            }
                            r += "\n";
                        }
                        break;
                }

                Log.i("result", r);  //result 결과값 출력. System.out 시 초과 문자열 압축 기능 실행
//                System.out.print(r);
            }
        });

        //강의실 선택 버튼 레이아웃
        lectureRoom = new

                LinearLayout(this);
        lectureRoom.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        lectureRoom.setWeightSum(3);
        lectureRoom.setOrientation(LinearLayout.HORIZONTAL);

        //강의실 선택 버튼 레이아웃 파라메터
        LinearLayout.LayoutParams lR = new LinearLayout.LayoutParams(-2, -2);
        lR.weight = 1;

        //강의실 선택 버튼
        lectureRooms = new Button[3];
        for (i = 0; i < lectureRooms.length; i++) {
            String room = "";
            if (i == 0) room = "6202";
            else if (i == 1) room = "6405";
            else if (i == 2) room = "6109";
            lectureRooms[i] = new Button(this);
            lectureRooms[i].setText(room);
            lectureRooms[i].setBackgroundColor(Color.rgb(150, 150, 150));
            lectureRooms[i].setLayoutParams(lR);
            lectureRoom.addView(lectureRooms[i]);
        }

        //개같은 싱크
        lectureRooms[0].

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        order = true;
                        if (lectureRoomNumber == -1) ;
                        else {
                            lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(150, 150, 150));
                            lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(0, 0, 0));
                        }
                        lectureRoomNumber = 0;
                        lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(35, 35, 35));
                        lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(255, 255, 255));
                    }
                });

        lectureRooms[1].

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        order = false;
                        if (lectureRoomNumber == -1) ;
                        else {
                            lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(150, 150, 150));
                            lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(0, 0, 0));
                        }
                        lectureRoomNumber = 1;
                        lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(35, 35, 35));
                        lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(255, 255, 255));
                    }
                });

        lectureRooms[2].

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        order = false;
                        if (lectureRoomNumber == -1) ;
                        else {
                            lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(150, 150, 150));
                            lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(0, 0, 0));
                        }
                        lectureRoomNumber = 2;
                        lectureRooms[lectureRoomNumber].setBackgroundColor(Color.rgb(35, 35, 35));
                        lectureRooms[lectureRoomNumber].setTextColor(Color.rgb(255, 255, 255));
                    }
                });

        //강의실 선택 버튼 레이아웃 추가
        LL1.addView(lectureRoom);
        //제출버튼 추가
        LL1.addView(b);
    }
}
