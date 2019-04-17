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

    private String TAG = "MainActivity";

    private Context mContext = MainActivity.this;

    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역

    private Boolean isMenuShow = false;
    private Boolean isExitFlag = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if (isMenuShow) {
            closeMenu();
        } else {

            if (isExitFlag) {
                finish();
            } else {

                isExitFlag = true;
                Toast.makeText(this, "뒤로가기를 한번더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExitFlag = false;
                    }
                }, 2000);
            }
        }
    }

    private void init() {

        findViewById(R.id.Menu).setOnClickListener(this);

        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);

    }

    private void addSideView() {
        SideBarView sidebar = new SideBarView(mContext);
        sideLayout.addView(sidebar);
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.Menu:

                showMenu();
                break;
        }
    }

    public void closeMenu() {

        isMenuShow = false;
        Animation slide = AnimationUtils.loadAnimation(mContext, R.anim.sidebar_hidden);
        sideLayout.startAnimation(slide);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        }, 450);
    }

    public void showMenu() {

        isMenuShow = true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
        Log.e(TAG, "메뉴버튼 클릭");
    }


    EditText ID, PN;
    Button submit, next;
    WebView Web;
    WebSettings mWebSettings;
    int idIndex = 0;
    //리스트뷰 제작 후 선택 기능 추가
    static final String[] ID_LIST = {
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
        init();
        addSideView();
        // #1 _ ID와 문제 번호ProblemNumber 합쳐서 WebView 에 실행.
        Web = findViewById(R.id.Web);       //WebView Web set
        ID = findViewById(R.id.ID);         //EditText ID set
        next = findViewById(R.id.next);     //Button next set
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //- 아이디 목록에서 다음 참조.
                ID.setText(ID_LIST[idIndex++ % ID_LIST.length]);    //크기까지 반복
            }
        });
        PN = findViewById(R.id.PN);         //EditText PN set
        submit = findViewById(R.id.submit); //Button submit set
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //- WebView 에 채점 현황 표시.
                Web.setWebViewClient(new WebViewClient());
                mWebSettings = Web.getSettings();
                mWebSettings.setJavaScriptEnabled(true);
                Web.loadUrl("https://www.acmicpc.net/status?problem_id=" + PN.getText().toString() + "&user_id=" + ID.getText().toString() + "&language_id=-1&result_id=-1");
            }
        });
    }
}