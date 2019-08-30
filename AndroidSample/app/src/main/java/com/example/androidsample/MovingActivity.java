package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MovingActivity extends AppCompatActivity {

    Adapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving);

        viewPager = (ViewPager)findViewById(R.id.view);     // 초기화, 아까만든 design뷰를 그대로 가져오고
        adapter = new Adapter(this);    // 초기화,
        viewPager.setAdapter(adapter);      // 어뎁터 설정
    }
}
