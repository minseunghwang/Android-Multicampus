package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastTestActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        Button registBtn = (Button)findViewById(R.id.registerBtn);
        registBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Broadcast Receiver를 생성.
                // 먼저 Broadcast receiver가 어떤 Broadcast를 청취할 수 있는지를 나타내는 intent filter를 생성
                IntentFilter filter = new IntentFilter();    // manifest.xml에 있는 테그야!
                filter.addAction("MY_BROADCAST");
                // 안드로이드 시스템에서 나오는 여러가지 정해져있는 broadcast를 catch할 수 있어요!

                // 안드로이드의 3번째 component인 broadcast reveiver를 생성
                receiver = new BroadcastReceiver() {    // 신호 잡는 놈이야
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // broadcast를 잡았을 때 처리해야 할 코드 작성.
//                        Toast.makeText(context,"신호 캐치!!", Toast.LENGTH_SHORT).show();
                        // Notificatino을 사용해 보아요!

                        // NotificationManager객체를 획득.
                        NotificationManager nManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                        // channelID와 channelName, Notification중요도 설정
                        String channelId = "MY_CHANNEL_ID";
                        String channelName = "MY_CHANNEL_NAME";
                        int important = NotificationManager.IMPORTANCE_HIGH;    // 중요한 notification이야~

                        // Oreo버전 이상에서는 channel 객체를 생성해서 설정해야 해요.
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel = new NotificationChannel(channelId, channelName, important);
                            nManager.createNotificationChannel(channel);
                        }

                        // Notificatino을 생성
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

                        // Intent를 하나 생성 => 나중에 notification을 클랙했을 때
                        // 화면에 Activity를 보여주기 위한 용도
                        Intent nIntent = new Intent(context, BroadcastTestActivity.class);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        int requestID = 10;

                        // PendingIntent는 intent를 가지고 있는 Intent
                        // Intent의 수행을 지연시키는 역할을 수행
                        PendingIntent pIntent = PendingIntent.getActivity(context, requestID,nIntent,PendingIntent.FLAG_CANCEL_CURRENT);

                        // Notificatino 설정부분
                        builder.setContentTitle("제목부분이에요!!")
                                .setContentText("여기는 내용이 나와요!!")
                                .setAutoCancel(true)            // 터치했을때 사라지도록 처리
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setSmallIcon(android.R.drawable.btn_star)  // 별모양의 아이콘 표시
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.blackjean))
                                .setContentIntent(pIntent);

                        nManager.notify(0,builder.build());

                    }
                };
                // 등록 작업(리시버가 등록되어야지 신호를 잡을 수 있어요!!)
                registerReceiver(receiver, filter);      // 해당 리씨버와 필터가 결합이되서 등록이 됩니다. 이떄부터 브로드캐스트 신호를 잡을 수 있다
            }
        });

        Button unregisterBtn = (Button)findViewById(R.id.unregisterBtn);
        unregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되면 receiver의 등록을 해제해줘요!!
                unregisterReceiver(receiver);
            }
        });

        Button sendSignalBtn = (Button)findViewById(R.id.sendSignalBtn);
        sendSignalBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("MY_BROADCAST");
                sendBroadcast(i);
            }
        });
    }
}
