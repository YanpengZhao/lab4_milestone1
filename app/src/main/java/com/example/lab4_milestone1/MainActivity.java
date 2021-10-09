package com.example.lab4_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Button startButton;
    private static final String TAG="MainActivity";
    private volatile boolean stopThread=false;
    public TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton=findViewById(R.id.button2);
        text=findViewById(R.id.text);
    }
    public void mockFileDownloader(){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });

        for (int downloadProgress=0;downloadProgress<=100;downloadProgress=downloadProgress+10){
            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("");
                    }
                });
                return;
            }
            int finalDownloadProgress;
            finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Downloading:"+ finalDownloadProgress+"%");
                }
            });
            Log.d(TAG,"Download Progress"+downloadProgress+"%");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                startButton.setText("Start");
            }
        });
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                text.setText("");
            }
        });
    }
    public void startDownload(View view){
        stopThread=false;
        ExampleRunnable runnable =new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void stopDownload(View view){
        stopThread=true;
    }
    class ExampleRunnable implements Runnable{


        @Override
        public void run() {
            mockFileDownloader();
        }

    }
}