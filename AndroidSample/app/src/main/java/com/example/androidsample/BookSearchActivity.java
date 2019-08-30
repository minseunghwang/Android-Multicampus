
package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        String url = "http://70.12.115.61:8080/bookSearch/searchTitle?USER_KEYWORD="+keyword;


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
                Log.i("ms", input);
            }
//            Log.i("DATA", sb.toString());
            // 얻어온결과 JSON 문자열을 Jackson library를 이용해서 Java 객채 형태로 변형
            // Java 객체형태(String[])로 변형

            ObjectMapper mapper = new ObjectMapper();
            //Jackson library를 이용하여 JSON문자열을 String[] 형태로 변환
            String[] resultArr = mapper.readValue(sb.toString(), String[].class);


            Bundle bundle = new Bundle();
            bundle.putStringArray("BOOKARRAY", resultArr);    // Key, value 쌍으로 담는거
                                                                // bundle이 바구니역할( bundle에 담는거~!)
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);


        } catch (Exception e){
            Log.i("DATAError",e.toString());

        }

    }
}

public class BookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        Button searchBtn = (Button)findViewById(R.id.searchBookBtn);
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
                String[] result = bundle.getStringArray("BOOKARRAY");

//                 adapter라는 객체는 데이터를 가져다가 view에 그리는 역할을 담당

                ArrayAdapter adapter = new ArrayAdapter(BookSearchActivity.this, android.R.layout.simple_list_item_1, result);
                        // 배열에 있는 데이터를 가져와서 화면에 그리겠다는 얘기지 (앞의 인자는 context를 표현하는데 activity객체를 인자로 넣어주면되고,
                        // 두번째는 레이아웃 (android.R.layout.simple_list_item_1) 이형태대로 딲딲딲 찍겟다
                lv.setAdapter(adapter);
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
