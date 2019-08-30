package com.example.myresolverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        Button btn = (Button) findViewById(R.id.selectBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ContentProvider에 접근하기 위한 ContentResolver 객체를 얻어온다
                ContentResolver cr = getContentResolver();

                // 'content://' + 어떤 ContentProvider + '/' + 어떤 데이터베이스에 접근할 것인지
                Uri uri = Uri.parse("content://com.test.data/Member");

                // 해당 uri에 있는 Content Provider의 query()라는 Method가 호출
                Cursor c = cr.query(uri, null, null, null, null);

                String result = "";
                // == rs.next()
                while (c.moveToNext()) {
                    // 한 명의 정보를 한 줄로 연결
                    result += c.getString(0);
                    result += ",";
                    result += c.getInt(1);
                    result += "\n";
                }

                //이렇게 데이터를 다 얻어오면, 해당 결과를 TextView에 출력
                tv.setText(result);
            }
        });
    }
}
