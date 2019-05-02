package com.example.baekjoon_ta;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTask extends AsyncTask<String, String, String> {
    String r = "0\n", target;

    public JsoupTask(String target) {
        this.target=target;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            Document doc = Jsoup.connect(this.target).get();

            //HTML 크롤링 확인-성공.
            //System.out.println(doc.html());

            //필요한 항목: 테이블 내부 문제 번호, 해결 여부, 날짜
            //테스트1
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
    protected void onPostExecute(String result) {
        System.out.println(r);
    }

    public String getR() {
        return r;
    }
}