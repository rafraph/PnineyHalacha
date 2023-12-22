package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Donation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        WebView wvDailyLearn = findViewById(R.id.wv);
        wvDailyLearn.loadUrl("https://yhb.org.il/support-us/");
    }
}