package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {


    private int a = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Button imgBtn = (Button)findViewById(R.id.imgBtn);

            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView iv = (ImageView) findViewById(R.id.iv);
                    iv.setImageResource(R.drawable.blackjeantotal);      // 내가가지고있는 이미지 resource를 이용해서 이미지를 세팅하겟다
                }
            });
    }
}
