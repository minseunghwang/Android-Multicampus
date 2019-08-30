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

class BookSearchRunnable implements Runnable {
    private Handler handler;
    private String keyword;

    BookSearchRunnable(Handler handler,String keyword){
        this.handler = handler;
        this.keyword = keyword;
    }
    @Override
    public void run() {
        String url = "http://70.12.115.61:8080/bookSearch/search?search_keyword=" + keyword;
        try{
            URL urlobj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlobj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = "";
            StringBuffer sb = new StringBuffer();
            while ((input = br.readLine()) != null) {
                sb.append(input);
            }
            // 서버에 대한 스트림은 종료
            br.close();
            // 결과적으로 우리가 얻어낸것은 JSON형태의 문자열
            // JSON문자열을 원래 객체 형태로 복원
            ObjectMapper mapper = new ObjectMapper();

            BookVO[] resultArr = mapper.readValue(sb.toString(),BookVO[].class);

            // UI Thread에게 데이터를 전달하기 전에
            // 화면에 표현할 데이터가 완전히

            for(BookVO vo : resultArr){
                vo.drawbleFromURL();
            }


            // 최종적으로 얻어낸 객체를 UI Thread(main Thread, Activity Thread) 에게 전달해야 해요!
            Bundle bundle = new Bundle();
            bundle.putSerializable("BOOKS",resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

            for(BookVO vo : resultArr){
                Log.i("customListView", vo.getBtitle());
            }

            Log.i("customListView",sb.toString());
        }catch (Exception e){
            Log.i("customListView",e.toString());
        }
    }
}

public class CustomBookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_book_search);

        // 필요한 reference 얻어오기
        // Button, EditText, ListView
        Button customBtn = (Button)findViewById(R.id.customBtn);
        final EditText customEt = (EditText)findViewById(R.id.customEt);
        final ListView customLv = (ListView)findViewById(R.id.customLv);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // 외부 Thread에서 message를 받으면 호출
                Bundle bundle = msg.getData();
                BookVO[] list = (BookVO[])bundle.getSerializable("BOOKS");

                // ListView에 adapter를 적용해서 listView를 그리는 작업
                // ListView : 화면에 리스트형태를 보여주는 widget
                // ListView에 데이터를 가져다가 실제 그려주는 작업 => adapter

                // 예전에는 ArrayAdapter 썻는데(기본으로제공된) 여기선 우리가 기능을좀 확장해서 그려주는 Adapter(클래스)를 하나 만들어야됨
                CustomListViewAdapter adapter = new CustomListViewAdapter();
                customLv.setAdapter(adapter);

                //adapter에 그려야하는 데이터를 세팅
                for(BookVO vo : list) {
                    adapter.addItem(vo);
                }
            }
        };
        customBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // 검색 버튼이 클릭되면 호출되요!
                // 사용자가 입력한 keyword를 이용해서 network 접속을 시도
                // 외부 servlet web program을 호출해서 결과를 받아와야 해요!
                // Thread를 이용해서 network 연결 처리를 해야 해요!
                // Thread에 입력 keyword값과 handler객체가 인자로 넘어가야 해요!
                BookSearchRunnable runnable =
                        new BookSearchRunnable(handler,
                                customEt.getText().toString());
                Thread t = new Thread(runnable);
                t.start();
            }
        });
    }
}