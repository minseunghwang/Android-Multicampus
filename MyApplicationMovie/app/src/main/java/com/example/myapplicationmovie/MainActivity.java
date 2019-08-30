package com.example.myapplicationmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.myapplicationmovie",
                        "com.example.myapplicationmovie.MovieSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }

        });

        Button cam = (Button)findViewById(R.id.cam);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.myapplicationmovie",
                        "com.example.myapplicationmovie.CamActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

    }


}
