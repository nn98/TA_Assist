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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView showResult;
    EditText ID, PN, DL, AL;
    Button next, TXT, Test, Reset, Execute, Change, Major, Cp, Seminar;
    LinearLayout select;

    JsoupAsyncTask JT;
    static boolean running = true;
    ProgressBar ongoing;
    private static boolean mAsyncTaskExecute = false;
    WaitNotify waitNotify;

    // #1 _ 백준 채점1: 전탐세 백준 아이디 목록
    // #1 _ 백준 채점4: 아이디 목록 추가, 수정. 2차원배열로 변환, 행 번호 isCase 변수 선언.
    //0 전탐세 1 C프 2 대생세
    //--- 0 전탐세(No Use) 1 python 2 대생세
    static int idIndex = 0, sCount = 1, isCase = 2, AddLine;
    public static final String[] TASK_LIST = {"MAJOR","SEMINAR1","SEMINAR5"};
    public static final String[][] ID_LIST = {

            /**1207 - #1 _ 전공탐색세미나 ID_LIST 변경 - 2학기 C프로그래밍**/
//            {
//                    "es2qorgus", "sumin00j", "201811006", "yjs06070", "rabonim",
//                    "asfg15", "ironhak1106", "201814034", "bmb1416", "gustn8523",
//                    "gkdus023", "a3920679", "kuntek1953", "hjk0385", "cjs1399",
//                    "doseon1226", "jiyeongstar", "leemoon63", "qwe916", "ggs4029",
//                    "06zzkimzz06", "kj980926", "twoddal2", "hj980608", "zoeyourlife",
//                    "7608guswns", "shc3113", "jiwoo60", "shmoon12", "201814128",
//                    "dlaxodud1217", "201814135", "s9430939", "980lje"
//            },
            {
                    "ejy6802", "yunie2004", "hxxn1995", "khk7759", "201622027",
                    "znsjdhk", "jhye225", "sesil2001", "lhj6399", "wodnjs429",
                    "um0711", "asbb1004", "ikhoi22", "hjchae11", "201812153",
                    "201813043", "chlals9930", "woh21", "201911019", "201911025",
                    //25번 김정인 X
                    "ahndaesung77", "yehyun5466", "manta", "julia1149", "szdf564zsdf89sdf489zsdf48sd89",
                    "kmskms5388", "201912161", "xenu98", "bym9446", "yunsik29",
                    //32번 이건엽 X
                    "tjgus0210", "szdf564zsdf89sdf489zsdf48sd89", "whyp1028", "201913028", "gjeka4554",
                    "kahm28", "nay06", "dayun0802", "kimys001111", "siwon806",
                    "wavemouse", "hanju10250"
            },

            // 1103 - #1 _ C프로그래밍 ID LIST 변경 - 2학기 Python
//            {
//                    "yelin", "vamos", "ujin00", "-", "didekwls0104",
//                    "ruddl0519", "-", "201914008", "sss4920", "tjdeoduf1228",
//                    "yeachan0724", "ymreueo", "ksk78030", "minjiii00", "Chelry0",
//                    "-", "-", "201914018", "nahyunho1030", "kll4400",
//                    "ekdms3868", "gjwldud0719", "pyr981125", "gpwl0773", "0928bh",
//                    "201914081", "wndud5750", "epselcks1", "nada2121", "bsm3737",
//                    "leehy321", "o0o0o557", "apple2701", "isf1999", "eunseo5355",
//                    "choijudy0405", "jhhgms"
//            },

//            2020-04-25 19_2학기 Python ID LIST 변경 - 20_1학기 대생세 1반 백준 채점
//            {
//                    "youthearp", "h012486", "gusalsdl5119", "eleste324", "201634015",
//                    "qpp9946", "yelin", "ruddl0519", "sss4920", "tjdeoduf1228",
//                    "ymreueo", "minjiii00", "chelry0", "201914018", "rlayjean",
//                    "wjdalstn0622", "nomaltree", "serah29", "chomom015", "gmltjs418",
//                    "fr35wo", "rhrlqnf", "cion6339", "nahyunho1030", "kll4400",
//                    "ekdms3868", "gpwl0773", "eunseo5355", "john2318", "gaeun0800",
//                    "evol1531", "minjiii00", "yoou9028", "euirim", "cbj4069",
//                    "mbs324", "lsh32832,8", "gkrry2597", "kdc9050", "fatal0311",
//                    "88yhtserof", "spdhtm21",
//            },

            {
                    "wodnjs429","dmsgur139","yj3180","partmant","chooh2000",
                    "blueblue08","popanda","mini227","sejong0107","uuu0526",
                    "tlsssm1212","angela2265","treaty0321","wh230819","hyk4238",
                    "chk99445","br2030","baekjun1234","sooho3242"
            },

            // 0911 - #1 _ 대학생활세미나 ID LIST 변경 - 2학기 통합반
//            {
//                    "0928bh", "phw0204", "yelin", "vamos", "ujin00"
//                    , "-", "didekwls0104", "ruddl0519", "cindy1078", "201914008"
//                    , "sss4920", "tjdeoduf1228", "yeachan0724", "ymreueo", "ksk78030"
//                    , "minjiii00", "-", "-", "-", "201914018"
//                    , "nahyunho1030", "kll4400", "ekdms3868", "gjwldud0719", "pyr981125"
//                    , "gpwl0773", "201914081", "wndud5750", "epselcks1", "nada2121"
//                    , "bsm3737", "leehy321", "chorong557@naver.com", "o0o0o557", "apple2701"
//                    , "isf1999", "eunseo5355", "choijudy0405", "jhhgms"
//            }

//            2020-04-25 19_2학기 Python ID LIST 변경 - 20_1학기 대생세 5반 백준 채점
//            {
//                    "wodnjs429", "yelin", "ujin00", "ruddl0519", "cindy1078",
//                    "201914008", "sss4920", "tjdeoduf1228", "yeachan0724",
//                    "ymreueo", "ksk78030", "minjiii00", "chelry0", "201914018",
//                    "nahyunho1030", "kll4400", "ekdms3868", "gjwldud0719", "gpwl0773",
//                    "0928bh", "201914081", "wndud5570", "eselcks1", "bsm3737",
//                    "leehy321", "o0o0o557", "isf1999", "eunseo5355", "choijudy0405"
//            }
            {
                    "kshyun419","asas6614","kwj9294","skhu1024","rladnr128"
                    ,"yebinac","idotu","neck392","qmffmzpdl","skl0519"
                    ,"lsy1210","jwnamid","ssj2012sms","haeunkim0807","parkjh6275"
                    ,"hpsd417","djwls0843","dlak0011","ssb1870"

            }
    };
    // 2: 백준 채점현황 기본 주소
    private String score1 = "https://www.acmicpc.net/status?problem_id=";

    /**0920 이런 병신 걍 맞았습니다만 찾으면되는뎈ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ 개똘빡새낔ㅋㅋㅋㅋ**/
    private String score2 = "&language_id=-1&result_id=4";
    private String target = "";
    protected static String DeadLine = "201903181825";
    protected static String DateResult = "";
    private static StringBuffer score = new StringBuffer();
    /**1126 틀린거까지도 확인**/
    static String submition="";
    private Button wrong;

    // #6 _ 레이아웃1: 사이드바 레이아웃 구현. --기능 구현
    private String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    private ViewGroup mainLayout;          //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;          //전체 감싸는 영역
    private ViewGroup sideLayout;          //사이드바만 감싸는 영역
    private Boolean isMenuShow = false;    //사이드바 표시 상태
    private Boolean isExitFlag = false;    //뒤로 버튼 입력 상태

    /**0920 update**/
    private String deadLine[],checkResult="";
    private boolean c=false;
    Button check;

    /**1006 update**/
//    private String addLine[]; useless

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

        /**1126**/
        wrong=findViewById(R.id.wrong);
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("wrong people",submition);
            }
        });

        // #1 _ 백준 채점1: 이후 구현 변화에 따라 개별 액티비티로 분리.
        ID = findViewById(R.id.ID);         //EditText ID set
        next = findViewById(R.id.next);     //Button next set
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //next - 아이디 목록에서 다음 참조.
                // #1 _ 1 -> 4
                // #1 _ 백준 채점4: 채점 케이스 수정. 대생세 추가, 다수 분기용 스위치문.
                // -> 2차원 배열로 변경, 행 번호로 분기.
                ID.setText(ID_LIST[isCase][idIndex++ % ID_LIST[isCase].length]);    //크기까지 반복
            }
        });
        // #1 _ 4
        select = findViewById(R.id.select);
        PN = findViewById(R.id.PN);         //EditText PN set

        /********************0920********************/
        check=findViewById(R.id.rCheck);

        Change = findViewById(R.id.change);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // #1 _ 4: 케이스 변경용 리스너
                if(c) {
                    select.setVisibility(View.INVISIBLE);
                    check.setVisibility(View.VISIBLE);
                    c=false;
                } else {
                    select.setVisibility(View.VISIBLE);
                    check.setVisibility(View.INVISIBLE);
                    c=true;
                }
            }
        });
        Major = findViewById(R.id.Major);
        Major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCase = 0;
                select.setVisibility(View.INVISIBLE);
                check.setVisibility(View.VISIBLE);
                c=false;
            }
        });
        Cp = findViewById(R.id.Cp);
        Cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCase = 1;
                select.setVisibility(View.INVISIBLE);
                check.setVisibility(View.VISIBLE);
                c=false;
            }
        });
        Seminar = findViewById(R.id.Seminar);
        Seminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCase = 2;
                select.setVisibility(View.INVISIBLE);
                check.setVisibility(View.VISIBLE);
                c=false;
            }
        });

        // #1 _ 백준 채점2: WebView 실행 대신 데이터 크롤링 후 결과값 score 에 저장.
        waitNotify = new WaitNotify();
        TXT = findViewById(R.id.TXT);       //TXT - 아이디 , 문제번호 EditText 에서 값 추출, JsoupTask 실행.
        TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TXT RUN");
                running = true;
                JT = new JsoupAsyncTask(score1 + PN.getText().toString() + "&user_id=" + ID.getText().toString() + score2, waitNotify);
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
                //System.out.println("TEST RUN");
                //score += JT.getR();               //결과값 get, score 에 문자열 형식으로 결합.
                Log.i("result", score.toString());  //score 결과값 출력. System.out 시 초과 문자열 압축 기능 실행
                // System.out.println("ResultSet:"+score);
                showResult.setText(score);      //가시적으로 표기.
                //next.performClick();            //자동으로 다음아이디. 기능구별 위해 next 잔여.
            }
        });
        Reset = findViewById(R.id.Reset);         //Reset - 다음 문제 채점을 위해 score 초기화.
        // if(idIndex / ID_LIST == 1) Reset.performClick(); 편의성 증가를 위해 추가? --- 보류
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idIndex = 0;
                sCount = 0;
                System.out.println("Reset");
                score = new StringBuffer();                       //score 초기화.
            }
        });
        //#1 _ 백준 채점3: 기존 기능들 활용해 원클릭 채점기능 구현-0521
        //synchronized wait/notify 활용. ---http://m.blog.daum.net/jhmoon77/17456070?tp_nil_a=1
        ongoing = findViewById(R.id.ongoing);
        Execute = findViewById(R.id.Execute);
        Execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get adLine
                AddLine=Integer.parseInt((AL=findViewById(R.id.AL)).getText().toString());
                System.out.println("--------------------------------------------------EXECUTE RUN");
                score.append("----------TASK : "+TASK_LIST[isCase]+" --- "+PN.getText()+"\n");
                //ProgressBar set, Toast massage popup.
                ongoing.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Execute running", Toast.LENGTH_SHORT).show();
                deadLine=DL.getText().toString().split(" ");
//                addLine=Arrays.copyOf(deadLine,deadLine.length);
//                addLine[1]+=2;
                /**1126**/
                submition="";
                for (int i = 0; i < (ID_LIST[isCase].length); i++) {
                    try {
                        next.performClick();
                        TXT.performClick();
                    } catch (Exception e) {
                        Log.i("E", e.getMessage());
                    }
                }
                //System.out.println("EXECUTE FINISH");
                //Toast.makeText(MainActivity.this,"Execute finish",Toast.LENGTH_SHORT).show();
                //ongoing.setVisibility(View.INVISIBLE);
            }
        });
        // 0521-기본 백준 채점 알고리즘 구현 완료. --TODO 날짜 비교
        // --TXT, TEXT invisible.

        /********************0920********************/
        DL=findViewById(R.id.DL);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("checkResult", checkResult);
            }
        });

    }

    public void mOnPopupClick1(View v) {
        //데이터 담아서 팝업(액티비티) 호출 데이터 전송에 사용되는 Intent 중요
        //인텐트 정의
        Intent intent = new Intent(this, TemplateActivity.class);
        //전송할 값들 인텐트에 삽입
        //아마 실행메소드
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick2(View v) {
        //데이터 담아서 팝업(액티비티) 호출 데이터 전송에 사용되는 Intent 중요
        //인텐트 정의
        Intent intent = new Intent(this, DepartureActivity.class);
        //전송할 값들 인텐트에 삽입
        //아마 실행메소드
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick3(View v) {
        //데이터 담아서 팝업(액티비티) 호출 데이터 전송에 사용되는 Intent 중요
        //인텐트 정의
        Intent intent = new Intent(this, CrawlingActivity.class);
        //전송할 값들 인텐트에 삽입
        //아마 실행메소드
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick4(View v) {
        //데이터 담아서 팝업(액티비티) 호출 데이터 전송에 사용되는 Intent 중요
        //인텐트 정의
        Intent intent = new Intent(this, DeskActivity.class);
        //전송할 값들 인텐트에 삽입
        //아마 실행메소드
        startActivityForResult(intent, 1);
    }

    public void mOnPopupClick5(View v) {
        //데이터 담아서 팝업(액티비티) 호출 데이터 전송에 사용되는 Intent 중요
        //인텐트 정의
        Intent intent = new Intent(this, WebActivity.class);
        //전송할 값들 인텐트에 삽입
        //아마 실행메소드
        startActivityForResult(intent, 1);
    }

    private class WaitNotify {
        synchronized public void mWait() {
            try {
                wait();
            } catch (Exception e) {
                Log.i("Debug", e.getMessage());
            }
        }

        synchronized public void mNotify() {
            try {
                notify();
            } catch (Exception e) {
                Log.i("Debug", e.getMessage());
            }
        }
    }

    //데이터 크롤링을 위한 JsopAsyncTask 구현. 실질적 실행은 개별 클래스인 JsoupTask.
    private class JsoupAsyncTask extends AsyncTask<String, String, String> {
        String target;
        StringBuffer r=new StringBuffer("0\n");
        private WaitNotify mWaitNotify = null;

        public JsoupAsyncTask(String target, WaitNotify aWaitNotify) {
            this.target = target;
            mWaitNotify = aWaitNotify;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // - 0920 !!! update - 시간 체크 구현 마무리 시도. 정규식 공부 안함. 병신.
        @Override
        protected String doInBackground(String... params) {
            try {
                if (mAsyncTaskExecute) {
                    mWaitNotify.mWait();
                }
                mAsyncTaskExecute = true;
                Document doc = Jsoup.connect(this.target).get();

                /**0920**/
                //문제 해결 판별, 제출 기한 판별
                boolean problem=false,inTime=false,adTime=false,isSubmit=false;
                //추가 제출 대비 - 가장 오래된 맞았습니다!! 탐색용 변수
                int counter=0,pNumber=0,dNumber=0;

                //HTML 크롤링 확인-성공.
                //System.out.println(doc.html());

                //필요한 항목: 테이블 내부 문제 번호, 해결 여부, 날짜
                Elements titles = doc.select("td[class=result]");   //제출 여부 확인
                System.out.println("-------------------------------------------------------------");
//                System.out.println(target);
                for (Element e : titles) {
                    if (e.text().equals("맞았습니다!!")) {                   //정오 판별
//                        r = "2\n";
                        problem=true;
                        pNumber=counter;
                        isSubmit=true;
                    }
                    counter++;
                    //System.out.println(target);
                    //System.out.println("title: " + e.text());
                }
                // #1 _ 백준 채점4: 시간 체크 추가 real-time-update , data-original-title
                titles = doc.select("a[class=real-time-update]");   //제출일자 확인
                for (Element e : titles) {
                    if(dNumber<pNumber) {
                        dNumber++;
                        continue;
                    }
//                    System.out.println(e.text());   //상대시간
//                    System.out.println(e.text("data-original-title"));          //절대시간
                    String match=e.text("data-original-title").toString();      //제출시간(절대) 추출
//                    System.out.println(match);  //확인
                    // 년 버리고 월 일 시 분 추출
                    String[] t=match.split("분"),date=new String[4];
                    date[3]=t[0].substring(t[0].length()-2).trim();
                    t=t[0].split("시");
                    date[2]=t[0].substring(t[0].length()-2).trim();
                    t=t[0].split("일");
                    date[1]=t[0].substring(t[0].length()-2).trim();
                    t=t[0].split("월");
                    date[0]=t[0].substring(t[0].length()-2).trim();
//                    System.out.println(Arrays.toString(date)+target);
                    System.out.println(Arrays.toString(date));
                    // 제출시간
//                    System.out.println("Submit\t: "+Arrays.toString(date));
                    // 제출기한
//                    System.out.println("DeadLine\t: "+Arrays.toString(deadLine));

                    //0920 11:10 제줄기한 추출 성공. 비교 후 정오 판별

                    for(int i=0;i<4;i++) {
//                        System.out.println(date[i]+" "+deadLine[i]+" "+deadLine[i].compareTo(date[i]));
//                        if(deadLine[i].compareTo(date[i])>0) {
//                            inTime=false;
//                            break;
//                        }
                        int s=Integer.parseInt(date[i]),d=Integer.parseInt(deadLine[i]);
                        if(s<d) {
//                            System.out.println(s+" "+d);
                            inTime=true;
                            adTime=true;
                            break;
                        }
                    }
                    if(!inTime) {
//                        System.out.println(Arrays.toString(addLine));
                        for (int i = 0; i < 4; i++) {
//                        System.out.println(date[i]+" "+deadLine[i]+" "+deadLine[i].compareTo(date[i]));
//                        if(deadLine[i].compareTo(date[i])>0) {
//                            inTime=false;
//                            break;
//                        }
                            int s = Integer.parseInt(date[i]), d = Integer.parseInt(deadLine[i]);
                            if(i==1)d+=AddLine;
                            if (s < d) {
//                            System.out.println(s+" "+d);
                                adTime = true;
                                break;
                            }
                        }
                    }
//                    System.out.println(target+"\n"+dNumber+" "+pNumber);

                    /*************************0920 제출기한 판별기능 구현 완료*************************/
//                    return null;
//                    Pattern pattern = Pattern.compile("[*0-9]");              //정규식 포맷
//                    StringBuilder date = new StringBuilder();                   //제출일자 저장용
//                    Matcher matcher = pattern.matcher(match);
//                    while (matcher.find()) {
//                        System.out.print(matcher.group());
//                        date.append(matcher.group());                                  //제출일자 빌드
//                        if(matcher.group().equals("\"")) if(cut) break;
//                        else cut=true;
//                    }
//                    date.delete(date.length()-10,date.length());                //제출시간(절대) 비교양식
                    //어메이징 백준사이트 09월이 아니라 9월
//                    System.out.println();
//                    System.out.println(date);
//                    if (isDate(date.toString())) {                                           //기한 판별
//                        DateResult += "1\n";
//                    } else {
//                        DateResult += "0\n";
//                    }
                    //System.out.println(target);
                    //System.out.println("title: " + e.text());
                }
                checkResult+=problem+" "+inTime+" "+adTime+"\n";
                if(!adTime) r=new StringBuffer("0\n");
                else if(problem&&inTime) r=new StringBuffer("2\n");
                else if(problem) r=new StringBuffer("1\n");

                /**1126**/
                if(!isSubmit){
                    String[]sp=target.split("&user_id=");
                    sp=sp[1].split("&");
                    submition+=sp[0]+"\n";
                }
                // 0604 - 제출 날짜 크롤링 성공, """""""2019429142929""1556515769""""" 형식. 스플릿과 제출기한 비교 구현 필요
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

        protected boolean isDate(String t) {
            return DeadLine.compareTo(t) >= 0;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(r);
            score.append(r);
            MainActivity.running = false;
            mWaitNotify.mNotify();
            mAsyncTaskExecute = false;
            sCount++;
            //ProgressBar set, Toast massage popup.
            if (sCount == (ID_LIST[isCase].length)) {
                sCount=0;
                ongoing.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Execute finish", Toast.LENGTH_SHORT).show();
            }
        }

        public String getR() {
            return r.toString();
        }
    }
}