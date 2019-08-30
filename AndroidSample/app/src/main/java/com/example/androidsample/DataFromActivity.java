package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DataFromActivity extends AppCompatActivity {

    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from);

        final ArrayList<String> list = new ArrayList<String>();
        list.add("수박");
        list.add("바나나");
        list.add("딸기");
        list.add("메론");

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        // adapter가 필요~!
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),           // 첫번째 : ApplicationContext
                android.R.layout.simple_spinner_dropdown_item, list);              // 두번째 사항은 : 어떤형태로 표현할지(sytle)
                                                                                    // adapter를 만들기 위한 데이터
        spinner.setAdapter(adapter);
        // spinner에서 item을 선택하는 이벤트 처리가 필요!
        // ApplicationContext - Context - Activity
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { // 세번째꺼가 index번호(바나나하면 여기에 1 들어가서 콜백)
                selectedItem = (String)list.get(i);
                Log.i("selectedTest","선택된 과일 : " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button sendDataBtn = (Button)findViewById(R.id.sendMsgBtn);
        sendDataBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 클릭되면 현재 선택한 과일이름을 이전 Activity로 전달하고
                // 현재 Activity는 종료
                Intent resultIntent = new Intent();
                resultIntent.putExtra("DATA", selectedItem);
                setResult(5000, resultIntent);

//                finish(); // 이렇게해도되고 ?
                DataFromActivity.this.finish();
            }
        });



    }
}
