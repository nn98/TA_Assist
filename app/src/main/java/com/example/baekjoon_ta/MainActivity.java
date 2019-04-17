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

public class MainActivity extends Activity implements View.OnClickListener {

    EditText ID, PN;
    Button submit, next;
    WebView Web;
    WebSettings mWebSettings;
    int idIndex = 0;
    static final String[] ID_LIST = {      // #1 _ 백준 채점1: 전탐세 백준 아이디 목록
            "es2qorgus", "sumin00j", "201811006", "yjs06070", "rabonim",
            "asfg15", "ironhak1106", "201814034", "bmb1416", "gustn8523",
            "gkdus023", "a3920679", "kuntek1953", "hjk0385", "cjs1399",
            "doseon1226", "jiyeongstar", "leemoon63", "qwe916", "ggs4029",
            "06zzkimzz06", "kj980926", "twoddal2", "hj980608", "zoeyourlife",
            "7608guswns", "shc3113", "jiwoo60", "shmoon12", "201814128",
            "dlaxodud1217", "201814135", "s9430939", "980lje"
    };

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

        init(); addSideView();              // #6 _ 온클릭 적용, 사이드바 생성 - 사이드바 실행 전제조건

        // #1 _ 백준 채점1: ID와 문제 번호ProblemNumber 합쳐서 WebView 에 실행.
        Web = findViewById(R.id.Web);       //WebView Web set
        ID = findViewById(R.id.ID);         //EditText ID set
        next = findViewById(R.id.next);     //Button next set
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //next - 아이디 목록에서 다음 참조.
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
    }
}