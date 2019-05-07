package com.example.baekjoon_ta;
/* #1 _ 리스트뷰 제작 시도-보류
    ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,ID_LIST);
    ListView IDL=findViewById(R.id.Idlist);
    IDL.setAdapter(adapter);

        WebView 에 실행 아닌 직접 실행 코드
    Intent intent=new Intent(Intent.ACTION_VIEW);
    Uri u=Uri.parse("https://www.acmicpc.net/status?problem_id="+PN.getText().toString()+"&user_id="+ID.getText().toString()+"&language_id=-1&result_id=-1");
    intent.setData(u);
    startActivity(intent);

                 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView showResult;
    EditText ID, PN;
    Button submit, next, TXT, Test, Reset;
    WebView Web;
    WebSettings mWebSettings;
    JsoupTask JT;
    static int idIndex = 0;
    public static final String[] ID_LIST = {      // #1 _ 백준 채점1: 전탐세 백준 아이디 목록
            "es2qorgus", "sumin00j", "201811006", "yjs06070", "rabonim",
            "asfg15", "ironhak1106", "201814034", "bmb1416", "gustn8523",
            "gkdus023", "a3920679", "kuntek1953", "hjk0385", "cjs1399",
            "doseon1226", "jiyeongstar", "leemoon63", "qwe916", "ggs4029",
            "06zzkimzz06", "kj980926", "twoddal2", "hj980608", "zoeyourlife",
            "7608guswns", "shc3113", "jiwoo60", "shmoon12", "201814128",
            "dlaxodud1217", "201814135", "s9430939", "980lje"
    };
    public static final String[] C_ID_LIST = {
            "yelin", "vamos", "ujin00", "-", "didekwls0104",
            "ruddl0519", "-", "201914008", "sss4920", "tjdeoduf1228",
            "yeachan0724", "ymreueo", "ksk78030", "minjiii00", "Chelry0",
            "-", "-", "201914018", "nahyunho1030", "kll4400",
            "ekdms3868", "gjwldud0719", "pyr981125", "gpwl0773", "0928bh",
            "201914081", "wndud5750", "epselcks1", "nada2121", "bsm3737",
            "leehy321", "o0o0o557", "apple2701", "isf1999", "eunseo5355",
            "choijudy0405", "jhhgms"
    };
    boolean isC = false;
    // 2: 백준 채점현황 기본 주소
    private String score1 = "https://www.acmicpc.net/status?problem_id=";
    private String score2 = "&language_id=-1&result_id=-1";
    private String target = "";
    private static String score = "";

    // #6 _ 레이아웃1: 사이드바 레이아웃 구현. --기능 구현
    private String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    private ViewGroup mainLayout;          //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;          //전체 감싸는 영역
    private ViewGroup sideLayout;          //사이드바만 감싸는 영역
    private Boolean isMenuShow = false;    //사이드바 표시 상태
    private Boolean isExitFlag = false;    //뒤로 버튼 입력 상태

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }   //키 입력 확인 - 뒤로 버튼

    @Override
    public void onBackPressed() {
        if (isMenuShow) {               //사이드바 실행중일때
            closeMenu();                //사이드바 종료
        } else {                        //초기 화면일때
            if (isExitFlag) {           //두번 눌렀을 경우
                finish();               //종료
            } else {                    //처음 눌렀을 경우
                isExitFlag = true;      //플래그 설정 후 안내
                Toast.makeText(this, "뒤로가기를 한번더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                //플래그 설정 시 2초 지연시간
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExitFlag = false;
                    }
                }, 2000);
            }
        }
    }      //뒤로 버튼 입력된 경우 - 사이드바 종료 | 앱 종료

    private void addSideView() {
        SideBarView sidebar = new SideBarView(mContext);    //this 정의해놨던 콘텍스트로 사이드바뷰 정의
        sideLayout.addView(sidebar);                        //정의한 뷰 사이드레이아웃에 추가
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }); //전체 영역은 클릭해도 아무일 없게
        sidebar.setEventListener(new SideBarView.EventListener() {
            @Override
            public void btnCancel() {
                Log.e(TAG, "btnCancel");
                closeMenu();
            }

            @Override
            public void btnLevel1() {
                Log.e(TAG, "btnLevel1");
                closeMenu();
            }
        }); //클릭된 종료버튼에 따라 해당 버튼 콘솔에 출력 +TODO
    }       //사이드바 생성 - 실행 전제조건

    @Override
    public void onClick(View view) {
        switch (view.getId()) {         //클릭된 뷰가
            case R.id.Menu:             //메뉴버튼일 경우
                showMenu();             //사이드바 표시
                break;
        }
    }   //메인코드 온클릭 정의 - 버튼클릭시 addSideView

    private void init() {
        findViewById(R.id.Menu).setOnClickListener(this);
        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);
    }              //정의된 온클릭 적용 - 실행 전제조건

    public void closeMenu() {
        isMenuShow = false;     //사이드바 플래그 설정
        Animation slide = AnimationUtils.loadAnimation(mContext, R.anim.sidebar_hidden);    //애니메이션 적용
        sideLayout.startAnimation(slide);   //애니메이션 실행
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        }, 450);    //.45초동안 사이드바 페이드아웃
    }          //사이드바 종료

    public void showMenu() {
        isMenuShow = true;              //플래그 설정
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);  //closeMenu와 동일
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
        Log.e(TAG, "메뉴버튼 클릭");  //안내 콘솔에 출력
    }           //사이드바 실행
    // _ #6:1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addSideView();              // #6 _ 온클릭 적용, 사이드바 생성 - 사이드바 실행 전제조건

        // #1 _ 백준 채점1: ID와 문제 번호ProblemNumber 합쳐서 WebView 에 실행.
        Web = findViewById(R.id.Web);       //WebView Web set
        ID = findViewById(R.id.ID);         //EditText ID set
        next = findViewById(R.id.next);     //Button next set
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //next - 아이디 목록에서 다음 참조.
                if (isC)                     //C 채점
                    ID.setText(C_ID_LIST[idIndex++ % C_ID_LIST.length]);    //크기까지 반복
                else                        //전탐세 채점
                    ID.setText(ID_LIST[idIndex++ % ID_LIST.length]);    //크기까지 반복
            }
        });

        PN = findViewById(R.id.PN);         //EditText PN set
        submit = findViewById(R.id.submit); //Button submit set
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //submit - WebView 에 채점 현황 표시.
                Web.setWebViewClient(new WebViewClient());
                mWebSettings = Web.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                Web.loadUrl("https://www.acmicpc.net/status?problem_id=" + PN.getText().toString() + "&user_id=" + ID.getText().toString() + "&language_id=-1&result_id=-1");
            }
        });

        // #1 _ 백준 채점2: WebView 실행 대신 데이터 크롤링 후 결과값 score 에 저장.
        TXT = findViewById(R.id.TXT);       //TXT - 아이디 , 문제번호 EditText 에서 값 추출, JsoupTask 실행.
        TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JT = new JsoupTask(score1 + PN.getText().toString() + "&user_id=" + ID.getText().toString() + score2);
                JT.execute();
                /*
                File savefile = new File("C:\\Users\\user\\Documents\\Temp"+"/test.txt");
                String wString="0\n2\n1\n1\n1\n1\n8\n5\n8\n7\n6\n";
                try {

                    FileOutputStream fos = new FileOutputStream(savefile);
                    fos = openFileOutput(wString,0);
                    fos.write(wString.getBytes());
                    fos.close();
                    Log.v(null,"complete");
                } catch(IOException e) {e.printStackTrace();}
                 */
            }
        });
        showResult = findViewById(R.id.result); //showResult - 결과값 저장한 score 가시적으로 표기.
        Test = findViewById(R.id.Test);         //Test - JsoupTask 실행결과 저장된 결과값 score 에 문자열로 저장.
        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score+=JT.getR();               //결과값 get, score 에 문자열 형식으로 결합.
                Log.i("result", score);  //score 결과값 출력. System.out 시 초과 문자열 압축 기능 실행
                // System.out.println("ResultSet:"+score);
                showResult.setText(score);      //가시적으로 표기.
                next.performClick();            //자동으로 다음아이디. 기능구별 위해 next 잔여.
            }
        });
        Reset=findViewById(R.id.Reset);         //Reset - 다음 문제 채점을 위해 score 초기화.
        // if(idIndex / ID_LIST == 1) Reset.performClick(); 편의성 증가를 위해 추가? --- 보류
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score="";                       //score 초기화.
            }
        });
    }

    //데이터 크롤링을 위한 JsopAsyncTask 구현. 실질적 실행은 개별 클래스인 JsoupTask.
    private class JsoupAsyncTask extends AsyncTask<String, String, String> {
        String r = "0\n";                       //결과값 저장할 r. 기본값 0
        String target;                          //실행시 사용할 URL. 아이디와 문제번호에서 추출.
        private JsoupAsyncTask(String target) {
            this.target=target;                 //JsoupTask 생성자로 target 초기화.
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }   //Pre 없음.

        @Override
        protected String doInBackground(String... params) {       //작업
            try {

                Document doc = Jsoup.connect(this.target).get();  //target 에서 html 크롤링.

                //HTML 크롤링 확인-성공.
                //System.out.println(doc.html());

                //필요한 항목: 테이블 내부 문제 번호, 해결 여부, 날짜
                //-0507 문제번호와 해결여부 해결. 날짜 미결.
                //테스트1
                Elements results = doc.select("td[class=result]");  //결과값 컬럼 추출.
                System.out.println("-------------------------------------------------------------");
                System.out.println(target);                                   //실행된 URL 표시.
                for (Element e : results) {                                   //결과값 컬럼들 중
                    if (e.text().equals("맞았습니다!!")) {                    //맞았을 경우
                        r = "1\n";                                            //r 1로 변환, 이식 용이하도록 개행.
                        return null;                                          //dolnBackground 종료.
                    }
                    //System.out.println(target);
                    //System.out.println("title: " + e.text());
                }                                                             //맞은 컬럼 없을 경우 r 그대로 0.
                /*
                제출일자 확인용 --- 제출일자 td 클래스가 없음. 내부 하이퍼링크로 존재.
                추출 방법은?
                Elements titles = doc.select("td[class=result]");
                System.out.println("-------------------------------------------------------------");
                System.out.println(target);
                for (Element e : titles) {
                    if (e.text().equals("맞았습니다!!")) {
                        r = "1\n";
                        return null;
                    }
                    //System.out.println(target);
                    //System.out.println("title: " + e.text());
                }
                 */
                /*
                    //테스트2
                    titles= doc.select("div.news-con h2.tit-news");

                    System.out.println("-------------------------------------------------------------");
                    for(Element e: titles){
                        System.out.println("title: " + e.text());
                        htmlContentInStringFormat += e.text().trim() + "\n";
                    }

                    //테스트3
                    titles= doc.select("li.section02 div.con h2.news-tl");

                    System.out.println("-------------------------------------------------------------");
                    for(Element e: titles){
                        System.out.println("title: " + e.text());
                        htmlContentInStringFormat += e.text().trim() + "\n";
                    }
                    System.out.println("-------------------------------------------------------------");
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {   //doln 종료 후 결과값 r 확인용
            System.out.println(r);                      //r 출력
            score += r;                                 //score 에 r값 추가. 실제 사용시 오류. 비동기식_
        }

        private String getR() {                         //r getter.
            return r;
        }
    }

    public void mOnPopupClick(View v) {                 //사이드뷰에서 실행할 외부 액티비티 연결.
        Intent i = new Intent(this, CrawlingActivity.class);    //현재는 크롤링 테스트 액티비티.
        i.putExtra("URL", "https://www.acmicpc.net/status?problem_id=" + PN.getText().toString() + "&user_id=" + ID.getText().toString() + "&language_id=-1&result_id=-1");
        startActivityForResult(i, 1);
    }
}