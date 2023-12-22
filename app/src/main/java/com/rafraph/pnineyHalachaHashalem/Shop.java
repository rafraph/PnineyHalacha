package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        WebView wvDailyLearn = findViewById(R.id.wv);
        wvDailyLearn.loadUrl("https://shop.yhb.org.il/");
    }
}