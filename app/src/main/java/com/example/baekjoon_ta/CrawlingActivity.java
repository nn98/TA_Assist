package com.example.baekjoon_ta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlingActivity extends AppCompatActivity {
    //
    private String htmlPageUrl = "https://www.acmicpc.net/status?problem_id=" + "1085" + "&user_id=" + "gustn8523" + "&language_id=-1&result_id=-1"; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling);


        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();

                //HTML 크롤링 확인-성공.
                System.out.println(doc.html());

                //필요한 항목: 테이블 내부 문제 번호, 해결 여부, 날짜 
                //테스트1
                Elements titles= doc.select("td[class=result]");

                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

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

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }


}