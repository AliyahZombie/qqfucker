package com.zombie.qqfucker;

import android.app.Activity;
import android.os.Bundle;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;

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
        // == Get one poem
        //getActionBar().setSubtitle("");
    }


}
