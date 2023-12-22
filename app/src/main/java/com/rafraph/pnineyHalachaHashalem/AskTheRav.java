package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AskTheRav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_the_rav);

        WebView wvDailyLearn = findViewById(R.id.askTheRav);
        wvDailyLearn.loadUrl("https://yhb.org.il/ask-the-rabbi2/");
    }
}