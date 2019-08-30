package com.example.androidsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btn1 : Event Source
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.LinearLayoutExampleActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // 전형적인 안드로이드 이벤트처리 형식입니다.
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.ChattingActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.ImageActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.TouchActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn0 = (Button)findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.MovingActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(MainActivity.this);       // 이벤트 처리 객체를 지칭, 여기에서 this는 컨텍스트 객체가 아니다 => MainActivity.this 여기 클래스의 객체로 가져와~
                // this라고 쓸 수 없으니
                //AlertDiaLog를 생성해 보아요!
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Activity에 데이터 전달");
                dialog.setMessage("전달할 내용을 입력하세요!!");
                dialog.setView(et);
                dialog.setPositiveButton("Activity 호출", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 다른 Activity를 호출하는 code
                        Intent intent = new Intent();
                        ComponentName cname = new ComponentName("com.example.androidsample",
                                "com.example.androidsample.SecondActivity");
                        intent.setComponent(cname);
                        intent.putExtra("sendMsg", et.getText().toString());
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소 버튼을 눌렀을때 수행할 코드 작성.
                    }
                });
                dialog.show();
            }
        });

        Button btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.DataFromActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivityForResult(i,3000);      // 새로운 액티비티를 화면에 띄우는데 "그 새 엑티비티에서 데이터를 받아올 목적!!"

            }

        });
        Button btn7 = (Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample", // 어떤페이지의
                        "com.example.androidsample.ANRActivity");  //  어떤 컴포넌트
                i.setComponent(cname);
                startActivity(i);

            }

        });

        Button btn8 = (Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.NoCounterActivity");
                i.setComponent(cname);
                startActivity(i);

            }

        });

        Button btn9 = (Button)findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.CounterActivity");
                i.setComponent(cname);
                startActivity(i);

            }

        });

        Button btn10 = (Button)findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.BookSearchActivity");
                i.setComponent(cname);
                startActivity(i);

            }

        });

        Button btn11 = (Button) findViewById(R.id.btn11);   // id를 가지고 view 객체를 (우리 눈에 보이는 컴포넌트들)을 찾아올거야.
        // id 라는 필드안에 뭉쳐있고, 스태틱 필드에서 btn2을 가져옴
        // 뭔 타입인지 모르고 가져오기 때문에 앞에 다운캐스팅 해줘야함!
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            // 클래스를 따로 안만들고 내부적으로 처리 ( 익명의(어나니모스) 클래스 )
            public void onClick(View view) {            // 외부에 명시적이냐 익명 내부클래스냐의 차이. 익명 내부가 일반적
                Intent i = new Intent();                // 인터페이스라 오버라이딩 해야함
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.CustomBookSearchActivity");
                i.setComponent(cname);  // 인텐트에 정보를 실어줌
                startActivity(i);       // 그 정보를 이용해 액티비티 스타트
            }
        });
        Button btn12 = (Button)findViewById(R.id.btn12);
        btn12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 특정 클래스 이름을 이용해 엑티비티를 실행할 겁니다 - 묵시적 인텐트
                // 명시적 인텐트 (Explicit Intent)
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.IntentTestActivity");
                i.setComponent(cname);
                startActivity(i);

            }

        });

        Button serviceStartBtn = (Button)findViewById(R.id.serviceStartBtn);
        serviceStartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 서비스 객체를 실행!
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.LifeCycleService");
                i.setComponent(cname);
                i.putExtra("ActivityToServiceData", "소리없는 아우성!!!");
                // 서비스 클래스를 찾아서 객체화 시키고 실행!
                startService(i);
            }

        });

        Button serviceStopBtn = (Button)findViewById(R.id.serviceStopBtn);
        serviceStopBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 서비스 객체 실행을 종료!!
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.LifeCycleService");
                i.setComponent(cname);
                stopService(i);
            }
        });

        Button btn13 = (Button)findViewById(R.id.btn13);
        btn13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 특정 클래스 이름을 이용해 엑티비티를 실행할 겁니다 - 묵시적 인텐트
                // 명시적 인텐트 (Explicit Intent)
                Intent i = new Intent();
                // 일반적으로 시스템에서 발생하는 broadcasting은 그 종류에 따라 사용하는 Action이 정해져 있다
                i.setAction("MY_BROADCAST");
                i.putExtra("broadcastMSG", "메시지가 전파되요!!");  // 데이터(메시지가전파되요)를 broadcastMSG라는 키워드로 인텐트에 싣기
                // startActivity(i);
                // startService(i);
                sendBroadcast(i);
            }
        });

        Button btn14 = (Button)findViewById(R.id.btn14);
        btn14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("START_BROADCAST_ACTIVITY");
                startActivity(i);
            }
        });

        Button btn15 = (Button)findViewById(R.id.btn15);
        btn15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("START_DATABASE_ACTIVITY");
                startActivity(i);
            }
        });

        Button btn16 = (Button)findViewById(R.id.btn16);
        btn16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("Contact_ACTIVITY");
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == 5000){
            String result = data.getExtras().getString("DATA");
            Log.i("ms",result);
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {     // service가 보내준 intent가 여기에 박힙니다.
        super.onNewIntent(intent);
        String msg = intent.getExtras().getString("ServiceToActivityData");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
