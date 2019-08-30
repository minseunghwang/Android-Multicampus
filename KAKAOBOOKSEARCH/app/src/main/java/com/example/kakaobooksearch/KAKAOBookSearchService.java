package com.example.kakaobooksearch;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class KAKAOBookSearchService extends Service {

    // inner class형태로 Thread를 생성하기 위한 Runnable interface를 구현한 class정의.
    // Activity로부터 넘겨받은 context를 쉽게 이용하기 위해 Service 내에 inner class로 정의
    private class BookSearchRunnalbe implements Runnable{
        private String keyword;

        BookSearchRunnalbe(String keyword){
            this.keyword = keyword;
        }

        @Override
        public void run() {
            // 전달된 keyword를 이용해서 network 처리(API 호출)
            String url = "https://dapi.kakao.com/v3/search/book?target=title&query=" + keyword;
            String myKey = "69f24df20bb1cdee9267e8bd27ecc425";
            try {
                URL urlobj = new URL(url);
                HttpURLConnection con = (HttpURLConnection)urlobj.openConnection();
                // request 방식을 지정
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization","KakaoAK " + myKey);     // 인증키값
                // 정상적으로 설정을 하면 API호출이 성공하고 결과를 받아 올 수 있어요!
                // 연결통로(stream)를 통해서 결과를 문자열로 얻어내요!
                // 기본적인 stream을 BufferdReader형태로 생성
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                // 왜 버퍼드 리더를쓰는가 ? 얘까 가장 사용하기 편해서그래요
                // 연결통로 InputStreamREader도 별로안좋아 그래서 더 좋게좋은걸로 사용하려고
                String line = null;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                // 데이터를 다 읽어들였으니 통로(stream)을 닫아요!
                br.close();
                Log.i("KAKAOBOOKLog", sb.toString());
                // 데이터가 JSON형태로 정상적으로 출력되면 외부 API 호출 성공!!
                // Jackson library를 이용해서 JSON데이터를
                // { documents : [] }
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});

                Object obj = map.get("documents");
                String resultJsonData = mapper.writeValueAsString(obj);
                Log.i("KAKAOBOOKLog", resultJsonData);
                // 결과적으로 우리가 얻은 데이터의 형태는
                // [ {책1권의 데이터}, {책1권의 데이터}, {책1권의 데이터}, {책1권의 데이터}, ...]
                // 책 1권의 데이터를 객체화 => KAKAOBookVO class를 이용
                // 책 여러권의 데이터는 ArrayList로 표현.
                // 책 1권의 데이터는 key와 value의 쌍으로 표현되고 있어요
                ArrayList<KAKAOBookVO> myObject =
                        mapper.readValue(resultJsonData, new TypeReference<ArrayList<KAKAOBookVO>>(){});

                // 정상적으로 객체화 되었는지 확인
                for(KAKAOBookVO book : myObject){
                    book.UrlToByteArray();
                    Log.i("KAKAOBOOKLog", book.getTitle());
                }

                // 정상적으로 객체화가 되었으면 intent에 해당 데이터를 붙여서
                // Activity에게 전달해야 해요!!
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                // 만약 Activity가 메모리에 존재하면 새로 생성하지 않고 기존 Activity를 이용
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // 전달할 데이터를 intent에 붙여요!!
                // parcelable interface를 구현한 객체를 붙이기 위해서
                // method를 다른 method로 교체
                i.putParcelableArrayListExtra("resultData", myObject);

                // Activity에게 데이터를 전달
                startActivity(i);

            } catch (Exception e){
                Log.i("KAKAOBOOKLog", e.toString());
            }
        }
    }


    public KAKAOBookSearchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // 서비스 객체가 만들어지는 시점에 1번 호출
        // 사용할 resource를 준비하는 과정
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // onCreate() 후에 자동적으로 호출되며
        // startService()에 의해서 호출되요!
        // 실제 로직처리는 onStartCommand에서 진행
        Log.i("KAKAOBOOKLog", "onStartCommand 호출됬어요!!");
        // 전달된 키워드를 이용해서 외부 네트워크 접속을 위한 Thread를 하나 생성해야 해요!

        String keyword = intent.getExtras().getString("searchKeyword");
        // Thread를 만들기 위한 Runnable 객체부터 생성@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        BookSearchRunnalbe runnalbe = new BookSearchRunnalbe(keyword);
        Thread t = new Thread(runnalbe);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 서비스 객체가 메모리상에서 삭제될 때 1번 호출
        // 사용한 resource를 정리하는 과정
        super.onDestroy();
    }
}
