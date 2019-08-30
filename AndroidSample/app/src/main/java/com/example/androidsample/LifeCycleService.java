package com.example.androidsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LifeCycleService extends Service {
    public LifeCycleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 3가지 Callback method를 사용
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 사용할 resource 준비작업
        Log.i("ServiceExam", "onCreate() 호출!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 하는 로직처리
        Log.i("ServiceExam", "onStartCommand() 호출!!");
//        return super.onStartCommand(intent, flags, startId);
//        return Service.START_NOT_STICKY;    // 강제종료되도 자동으로 재시작 하지마!!
          if(intent == null) {               // 정상적인 case는 intent에 activity가 넘겨준게 넘어와야함! 근데이게 null 이면 강제로 재시작된 case
              // 강제종료되서 서비스가 재시작된 경우
              Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

              // flags 값 주의!
              // Cleartop, singletop -> 생성되어 있는 Activity가 없는 경우 Activity 생성함
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              // 생성되어 있는 Activity인지 확인
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
              // 화면이 없는 객체가 화면이 있는 객체로 값을 전달하려면 TASK가 필요함
              //       Service    ->    Activity
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              resultIntent.putExtra("ServiceToActivityData", "새로운 Activity가 생성 되었어요!!");

              startActivity(resultIntent);
          } else{
              // Activity가 보내준 데이터를 서비스가 받아요!
              String msg = intent.getExtras().getString("ActivityToServiceData");
              Log.i("ServiceExam", "Activity가 보내준 메시지 : " + msg);
              // 로직처리가 진행!! => Activity에게 전달해야 하는 최종 결과 데이터
              Intent resultIntent = new Intent(getApplicationContext(),
                      MainActivity.class);
              // flags 값 주의!
              // Cleartop, singletop -> 생성되어 있는 Activity가 없는 경우 Activity 생성함
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              // 생성되어 있는 Activity인지 확인
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
              // 화면이 없는 객체가 화면이 있는 객체로 값을 전달하려면 TASK가 필요함
              //       Service    ->    Activity
              resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              resultIntent.putExtra("ServiceToActivityData", "결과데이터전달이에요!!");

              startActivity(resultIntent);
          }

          return Service.START_STICKY;    // 강제종료되면 자동적으로 재시작.
    }

    @Override
    public void onDestroy() {
        // 리소스 해제 작업
        Log.i("ServiceExam", "onDestroy() 호출!!");
        super.onDestroy();
    }
}
