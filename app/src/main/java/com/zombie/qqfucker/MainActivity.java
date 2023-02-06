package com.zombie.qqfucker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import java.util.Random;

public class MainActivity extends Activity {

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    

    Handler handle = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity.this.getActionBar().setSubtitle("6");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        
        //线程
        new Thread(){
            public void run(){
                Random r = new Random();
                r.ints(1,2).sum();
            }
        }.run();
        
        
        // == Get one poem
        //getActionBar().setSubtitle("");
    }


}
