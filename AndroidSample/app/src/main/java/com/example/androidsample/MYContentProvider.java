package com.example.androidsample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MYContentProvider extends ContentProvider {

    private SQLiteDatabase db;

    public MYContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        MysqliteHelper helper = new MysqliteHelper(getContext());
        db = helper.getWritableDatabase();
        Log.i("Database","CP의 onCreate()호출!!");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Log.i("DatabaseExam", "query()가 호출되었어요!!");
        // 인자로 들어오는 값을 이용하여 사용할 SQL을 구성해야 해요!!
        String sql = "SELECT * FROM member";
        return db.rawQuery(sql, null);  // 데이터를 뒤져서 결과를 나온 커서객체를 다른 앱에 넘겨준다
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
