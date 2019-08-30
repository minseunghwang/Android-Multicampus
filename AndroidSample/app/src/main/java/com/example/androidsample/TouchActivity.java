package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class TouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, R.string.toastMsg, Toast.LENGTH_SHORT).show();        // 잠깐 나타났따 사라지는 문자를 만들수잇습니다( ?, 출력할메세지, 얼마나오래) // .show()앞 까지가 특정문자열 만드는 과정
        return super.onTouchEvent(event);
    }
}
