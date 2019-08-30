package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

class MySum implements Runnable{
    private TextView tv;

    MySum(TextView tv){     // 클래스간의 연결 구조와 상관없이 프로그램을 유연하게 끌고 갈 수 있는 장점!
        this.tv = tv;
    }

    @Override       // override 해줘야지 추상 클래스가 안되겟죠?
    public void run() {
        // Thread가 실행이 되면 수행되는 코드를 여기에 작성
        long sum = 0;
        for(long i=0; i<1000000000L; i++){
            sum += i;
        }
        String a = String.valueOf(sum);
        Log.i("min",a);


        tv.setText("총합은 :" + sum);          // 이런식으로 화면을 바꾸는 코드는 Activity 내에서만 할 수 있어요!
    }

}

public class ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        final TextView tv = (TextView) findViewById(R.id.countTv);
        Button countBtn = (Button)findViewById(R.id.countBtn);
        Button toastBtn = (Button)findViewById(R.id.toastBtn);

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Thread를 파생시켜야 해요!!
                MySum mySum = new MySum(tv);          // Runnable interface를 구현한 객체

                Thread t = new Thread(mySum);       // 위의 runnalbㄷ한 녀석을 스레드 만드는데 인자로 박아서 // Thread 생성

                t.start();                          // non-blocking method  // method가 다 수행되지 않아도 다음으로 넘어감
                                                    // 원래는 이 줄 코드가 다 실행되야지 다음으로 넘어가는데 start()는 얘 수행과 상관없이
                                                    // 실행시켜놓고 완료되든안되든 다음줄로 넘어간다
                                                    // 새로운 실행흐름을 만들어 낼 수 있어요!

            }
        });

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ANRActivity.this,"Toast가 실행되요!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
