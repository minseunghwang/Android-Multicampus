package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class KAKAOMAPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaomap);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@맵그리기가즈아@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        MapView map = new MapView(this);
        ViewGroup group = (ViewGroup)findViewById(R.id.mapll);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.501390, 127.039647);    // 인자가 두개 들어가요 위도와,경도 // 포인트 위치객체 생성
        map.setMapCenterPoint(mapPoint, true);                                  // 내가정한 위치가 가운데지점
        group.addView(map);

    }
}
