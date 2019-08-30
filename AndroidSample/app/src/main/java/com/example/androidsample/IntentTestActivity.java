package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        Button implicitBtn = (Button)findViewById(R.id.implicitBtn);

        implicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이클릭되면 새로운 Activity를 호출!!!
                // 그러나 이번엔 묵시적으로 엑티비티를 호출할 겁니다
                Intent i = new Intent();
                i.setAction("MY_ACTION");   // user define
                //Intent에 Action은 하나밖에 못ㅎㅐ 그래서 set
                i.addCategory("MY_CATEGORY");
                //Intent에 Category는 여러개 가능해. 그래서 add 야
                i.putExtra("DATA", "소리없는 아우성!!");
                startActivity(i);
            }
        });

        Button dialBtn = (Button)findViewById(R.id.dialBtn);

        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전화걸기 Activity 호출!!
                // 묵시적 인텐트를 이용해야지만 안드로이드 자체에있는 기능을 이용할 수 있습니다@@@@
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:01039871010"));        // 어디에 전화를 걸건지
                startActivity(i);
            }
        });
//  @@@@@@@@@@@@@@@@@@@@@@@@묵시적 인텐트를 이용해서 다른앱들과의 연동을 가능케 한다@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Button browserBtn = (Button)findViewById(R.id.browserBtn);

        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // browser를 이용해서 특정 URL로 접속
                Uri uri = Uri.parse("http://www.naver.com");
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });

        Button mapBtn = (Button)findViewById(R.id.mapBtn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 지도를 표현할 activity를 실행
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.androidsample",
                        "com.example.androidsample.KAKAOMAPActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button callBtn = (Button)findViewById(R.id.callBtn);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 사용자의 안드로이드 버젼이 6버젼 보다 작은가 ?
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // 추가적인 보안해제가 필요
                    // 현재 앱에 대해 사용자 권한 중 전화걸기 기능이 설정되어 있는가(되어있으면 바로 쓰면되고, 아니면 뭔설정해줘야되고)
                    int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if(result == PackageManager.PERMISSION_DENIED){     // 전화걸기 기능이 설정이 안되있으면..!
                        // 전화걸기 기능에 대한 보안이 설정 안되있어요!!
                        // 한번이라도 전화걸기에 대한 권한 설정을 거부한 적이 있는가 ?
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){       // 그동안 한번이라도 권한 설정 거부한적잇는가 확인
                            // 거부한적이 있어요! 다시 dialog를 띄워서 물어봐야 해요
                            AlertDialog.Builder builder = new AlertDialog.Builder(IntentTestActivity.this);     // 컨택스트 객체로 넣어줘라...
                            builder.setTitle("권한필요!!");
                            builder.setMessage("전화걸기 기능이 필요해요. 수락할까요?");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {   // "예" 버튼을 누르면 얘가 호출이된다
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                }
                            });
                            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } else{

                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    } else{
                        // 전화걸기 기능에 대한 보안이 설정되어 있어요!!
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:01039871010"));
                        startActivity(i);
                    }
                } else{
                    // 이전 안드로이드 버젼이기 때문에 간단한 설정으로 바로 실행
                    // 직접 calling하는 activity 호출
                    // 직접 calling하는 activity 호출
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:01039871010"));        // 어디에 전화를 걸건지
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {   // 권한에 대해 허가를 햇느냐 안햇느냐가 grantResults에 들어가있어!!
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:01039871010"));        // 어디에 전화를 걸건지
                startActivity(i);
            }
        }
    }
}
