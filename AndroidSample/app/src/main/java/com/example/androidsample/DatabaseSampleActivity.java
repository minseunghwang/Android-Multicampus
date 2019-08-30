package com.example.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DatabaseSampleActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_sample);

        Button createDbBtn = (Button)findViewById(R.id.createDbBtn);
        createDbBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 클릭하면 Database를 생성하고 Table을 만들어요!
                // Sqlite Database를 사용하기 쉽도록 도와주는 Helper class가 제공
                // 이 Helper class를 직접 이용하는게 아니라 이놈을 상속받아서
                // 클래스를 작성한 후 사용자 정의 클래스의 객체를 이용
                // Helper class를 작성하러 갑시다
                MysqliteHelper helper = new MysqliteHelper(DatabaseSampleActivity.this);
                // helper를 통해서 Database에 대한 Handle을 얻어올 수 있어요
                db = helper.getWritableDatabase();
            }
        });
        Button selectDbBtn = (Button)findViewById(R.id.selectDbBtn);
        selectDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Database handle을 이용해서 Database처리를 할 수 있어요!
                // rawQuery() : select계열의 SQL문을 실행할 떄 사용돼요!!
                // Cursor의 역할은 JDBC의 resultSet의 역할을 수행
                Cursor c = db.rawQuery("SELECT * FROM member", null);
                String result = "";
                while(c.moveToNext()) {
                    result += c.getString(0);
                    result += ",";
                    result += c.getInt(1);
                    result += "\n";
                }
                TextView tv = (TextView)findViewById(R.id.selectTv);
                tv.setText(result);
            }
        });
    }
}
