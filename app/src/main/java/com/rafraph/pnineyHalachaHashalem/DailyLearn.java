package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class DailyLearn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_learn);

        WebView wvDailyLearn = findViewById(R.id.wv);
        wvDailyLearn.loadUrl("https://ph.yhb.org.il/pninayomit/");

    }
}