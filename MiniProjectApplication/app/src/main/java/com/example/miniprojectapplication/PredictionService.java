package com.example.miniprojectapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PredictionService extends Service {
    String predictionms = "";

    public PredictionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // 서비스에서 사용할 resource 준비작업
        Log.i("ServiceExam", "onCreate() 호출!!");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceExam", "onStartCommand() 호출!!");
        if (intent == null) {               // 정상적인 case는 intent에 activity가 넘겨준게 넘어와야함! 근데이게 null 이면 강제로 재시작된 case
            // 강제종료되서 서비스가 재시작된 경우
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

            // flags 값 주의!
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(resultIntent);
        } else {
            // Activity가 보내준 데이터를 서비스가 받아요!
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            final String msg = intent.getExtras().getString("Realpath");

            try {
                Thread wThread = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                    public void run() {
                        try {
                            String url = "http://70.12.115.61:9090/study/test.do";
                            HttpFileUpload(url, "", msg);
                            Log.i("Path1", msg);
                        } catch (Exception e) {
                            Log.i("Predict", "이상이상");
                        }
                    }
                };
                wThread.start();

                try {
                    wThread.join();
                } catch (Exception e) {
                    Log.i("Predict", "이상이상22");
                }

                resultIntent.putExtra("panswer", predictionms);
            } catch (Exception e) {
                Log.i("Path1", e.toString());
            }
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

    public void HttpFileUpload(String urlString, String params, String fileName) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        try {
            File sourceFile = new File(fileName);
            Log.i("ms이건 그냥 파일위치겟고", fileName);
            Log.i("ms이건 파일화 한거", "" + sourceFile);
            DataOutputStream dos;
            if (!sourceFile.isFile()) {
                Log.i("uploadFile", "Source File not exist :" + fileName);
            } else {
                Log.i("Predict", "connection준비");
                FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                URL connectUrl = new URL(urlString);
                // open connection
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                // write data
                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                Log.i("ms", "" + bytesRead);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

                Log.i("ms", "쩝이");
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                mFileInputStream.close();
                dos.flush(); // finish upload...

                Log.i("ms", "아아아ㅏㅇ " + conn.getResponseCode());


                if (conn.getResponseCode() == 200) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }

                    Log.i("Predict", stringBuffer.toString());
                    predictionms = stringBuffer.toString();

                } else {
                    Log.i("Predict", "안들어갓슈");
                }
                mFileInputStream.close();
                dos.close();

                Log.i("Predict", "HTTPTPTPTP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
