package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        final ScrollView sv = (ScrollView)findViewById(R.id.suna);

        final TextView tv = (TextView)findViewById(R.id.tv);
        final TextView uId = (TextView)findViewById(R.id.userId);
        final EditText et = (EditText)findViewById(R.id.et);
        final Button sendBtn = (Button)findViewById(R.id.sendBtn);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());    // 스크롤 만드는 코드~

        sendBtn.setOnClickListener(new View.OnClickListener() {         // onCreate와 사용되는 시점이 다르다 ㅇㅇ그래서 에러뜨는구만
            @Override
            public void onClick(View view) {
                tv.append(uId.getText() + ">>" + et.getText() + "\n");       // 내 아이디가져오고 , 입력된거 가져와서 띄울 textview에다가 append해주면되요
                // 스크롤을 해야하는지 판단해서
//                tv.scrollTo(0,100);
                sv.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
