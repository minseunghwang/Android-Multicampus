package com.example.kakaobooksearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.keywordEditText);
        Button searchBtn = (Button) this.findViewById(R.id.searchBtn);   // 앞에 this가 생략할수도있는데 여기 activity에서 찾는거
        // anonymous inner class를 이용한 Event처리 (Android의 전형적인 event처리)
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.
                Intent i = new Intent();
                // 명시적 Intent를 사용
                ComponentName cname = new ComponentName("com.example.kakaobooksearch",
                        "com.example.kakaobooksearch.KAKAOBookSearchService");
                i.setComponent(cname);
                i.putExtra("searchKeyword", editText.getText().toString());
                startService(i);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("KAKAOBOOKLog", "데이터가 정상적으로 Activity에 도달!!");
        // intent에서 데이터 추출해서 ListView에 출력하는 작업을 진행
        ArrayList<KAKAOBookVO> data = intent.getExtras().getParcelableArrayList("resultData");

        // 만약 그림까지 포함하려면 추가적인 작업이 더 필요
        // ListView에 도서 제목만 먼저 출력

        ListView lv = (ListView) this.findViewById(R.id.bookListView);

        CustomListViewAdapter adapter = new CustomListViewAdapter();
        lv.setAdapter(adapter);

        for (KAKAOBookVO vo : data) {
            adapter.addItem(vo);
//            Log.i("KAKAOBOOKLog",adapter.toString());
        }
        // 성공 시 CustomListView를 이용해서 임지, 제목, 저자, 가격 등의 데이터를 출력
    }
}