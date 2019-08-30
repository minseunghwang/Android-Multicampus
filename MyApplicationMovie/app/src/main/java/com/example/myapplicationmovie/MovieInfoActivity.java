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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Intent i = getIntent();
        String movieCd = i.getExtras().getString("mcd");
        Log.i("ms","000");
        final ListView lv2 = (ListView)findViewById(R.id.lv2);
        final Handler handler = new Handler(){
            // handler에게 message가 전달되면 아래의 method가 callback되요!
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String[] result = new String[6];
                Log.i("ms","666");

                result[0] = bundle.getString("a");
                result[1] = bundle.getString("b");
                result[2] = bundle.getString("c");
                result[3] = bundle.getString("d");
                result[4] = bundle.getString("e");
                result[5] = bundle.getString("f");



//                 adapter라는 객체는 데이터를 가져다가 view에 그리는 역할을 담당
                ArrayAdapter adapter = new ArrayAdapter(MovieInfoActivity.this, android.R.layout.simple_list_item_1, result);
                // 배열에 있는 데이터를 가져와서 화면에 그리겠다는 얘기지 (앞의 인자는 context를 표현하는데 activity객체를 인자로 넣어주면되고,
                // 두번째는 레이아웃 (android.R.layout.simple_list_item_1) 이형태대로 딲딲딲 찍겟다
                lv2.setAdapter(adapter);
            }
        };

        MovieInfoRunnable movieInfoRunnable = new MovieInfoRunnable(handler, movieCd);
        Thread t = new Thread(movieInfoRunnable);


        t.start();
    }
}

class MovieInfoRunnable implements Runnable{
    private String mcd;
    private Handler handler;

    MovieInfoRunnable(Handler handler, String mcd){
        this.handler = handler;
        this.mcd = mcd;
    }

    @Override
    public void run() {

        String url ="http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=7f618281f1657ca2d879ff4150f838a5&movieCd="+mcd;

        try {
            URL urlobj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlobj.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); // bufferedreader의 일반적인 사용인듯
            String input = "";
            StringBuffer sb = new StringBuffer();       // 결과를 저장할 스트링 버퍼
            Log.i("ms","111");

            while((input = br.readLine()) != null){         // null이 아닐때까지 계속읽어라~ (서버가 보내준거 다 읽어와라)
                sb.append(input);
            }
            Log.i("ms","222");


// []어레이고 {}오브젝트고


            JSONObject jsonobject = new JSONObject(sb.toString());
            String st = jsonobject.getString("movieInfoResult");
            jsonobject = new JSONObject(st);
            st = jsonobject.getString("movieInfo");
            jsonobject = new JSONObject(st);



            String a,b,c,d,e,f;
            a =jsonobject.getString("movieNm");
            Log.i("ms",a);
            b =jsonobject.getString("movieNmEn");
            Log.i("ms",b);
            c =jsonobject.getString("showTm");
            d =jsonobject.getString("prdtYear");
            e =jsonobject.getString("openDt");
            f =jsonobject.getString("prdtStatNm");

            Bundle bundle = new Bundle();
            bundle.putString("a",a);
            bundle.putString("b",b);
            bundle.putString("c",c);
            bundle.putString("d",d);
            bundle.putString("e",e);
            bundle.putString("f",f);
            Log.i("ms","444");

            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("ms","555");

        } catch (Exception e){
            Log.i("DATAError",e.toString());

        }
    }
}