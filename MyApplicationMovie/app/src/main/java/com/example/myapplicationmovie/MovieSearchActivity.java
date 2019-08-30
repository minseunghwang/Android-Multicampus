package com.example.myapplicationmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class SearchTitleRunnable implements Runnable{
    private String keyword;
    private Handler handler;

    SearchTitleRunnable(Handler handler, String keyword){
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        // keyword를 이용해서 web program에 접속한 후 결과를 받아와요!
        //결과로 받아온 JSON문자열을 이용해서 ListView에 출력해야 해요!
        // 그런데 여기서는 Listview를 제어할 수 없어요!

        // Handler를 이용해서 UI Thread에 Listview에 사용할 데이터를 넘겨요.
//        String url = "http://70.12.115.61:8080/bookSearch/searchTitle?USER_KEYWORD="+keyword;
        String url ="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=7f618281f1657ca2d879ff4150f838a5&targetDt="+keyword;


        //내가 실제 접속할 웹 서버의 ip 주소

        // Network code는 예외처리가 필요!
        try {
            // URL이라는 클래스가 잇어
            URL urlobj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlobj.openConnection();
            // network연결이 성공한 후 데이터를 읽어들이기 위한 데이터 연결 통로
            // Stream을 생성해요!

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); // bufferedreader의 일반적인 사용인듯
            // 스트림을 뽑아낸다음에 좀더 나은 스트림으로(inputstream)맨들고 거기에 더좋은 buffered까지 씌우는 너낌
            String input = "";
            StringBuffer sb = new StringBuffer();       // 결과를 저장할 스트링 버퍼

            while((input = br.readLine()) != null){         // null이 아닐때까지 계속읽어라~ (서버가 보내준거 다 읽어와라)
                sb.append(input);
            }

            JSONObject jsonobject = new JSONObject(sb.toString());
            String st = jsonobject.getString("boxOfficeResult");
            jsonobject = new JSONObject(st);
            st = jsonobject.getString("dailyBoxOfficeList");
            JSONArray movieInfo = new JSONArray(st);

            String [] arr1 = new String[10];
            String [] arr2 = new String[10];

            for(int i=0; i<movieInfo.length(); i++ ){
                jsonobject = movieInfo.getJSONObject(i);
                arr1[i] = jsonobject.getString("movieCd");
                arr2[i] = jsonobject.getString("movieNm");
            }


            Bundle bundle = new Bundle();
            bundle.putStringArray("mC", arr1);
            bundle.putStringArray("mN", arr2);
            // bundle이 바구니역할( bundle에 담는거~!)
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);


        } catch (Exception e){
            Log.i("DATAError",e.toString());

        }
    }
}

public class MovieSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Button searchBtn = (Button)findViewById(R.id.searchMovieBtn);
        final EditText keywordEt = (EditText)findViewById(R.id.keywordEt);
        final ListView lv = (ListView)findViewById(R.id.lv);

        // 웹 서버에 접속해서 데이터를 받아온 후 해당 데이터를 listview에 세팅
        // UI tHREAD(Activity Thread, Main Thread)에서는 Network연결을 할 수 없어요!!

        final Handler handler = new Handler(){
            // handler에게 message가 전달되면 아래의 method가 callback되요!
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                final String[] result1 = bundle.getStringArray("mC");
                final String[] result2 = bundle.getStringArray("mN");

//                 adapter라는 객체는 데이터를 가져다가 view에 그리는 역할을 담당
                ArrayAdapter adapter = new ArrayAdapter(MovieSearchActivity.this, android.R.layout.simple_list_item_1, result2);
                // 배열에 있는 데이터를 가져와서 화면에 그리겠다는 얘기지 (앞의 인자는 context를 표현하는데 activity객체를 인자로 넣어주면되고,
                // 두번째는 레이아웃 (android.R.layout.simple_list_item_1) 이형태대로 딲딲딲 찍겟다
                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent i = new Intent();
                        ComponentName cname = new ComponentName("com.example.myapplicationmovie",
                                "com.example.myapplicationmovie.MovieInfoActivity");
                        i.putExtra("mcd",result1[position]);
                        i.setComponent(cname);
                        startActivity(i);
                    }
                });


            }
        };

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 keyword를 가지고 Thread를 파생.
                SearchTitleRunnable runnable = new SearchTitleRunnable(handler, keywordEt.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });

    }
}