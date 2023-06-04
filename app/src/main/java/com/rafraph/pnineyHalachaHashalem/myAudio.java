package com.rafraph.pnineyHalachaHashalem;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class myAudio extends Activity implements AdapterView.OnItemSelectedListener {
    private static int API;

    /*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48*/
    public int[] lastChapter = {18, 11, 17, 10, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 26, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10};
    String[][] chaptersNames = new String[BOOKS_NUMBER][32];
    public  static int rotateState;

    private static final int BRACHOT = 0;
    private static final int HAAMVEHAAREZ = 1;
    private static final int ZMANIM = 2;
    private static final int TAHARAT = 3;
    private static final int YAMIM = 4;
    private static final int KASHRUT_A = 5;
    private static final int KASHRUT_B = 6;
    private static final int LIKUTIM_A = 7;
    private static final int LIKUTIM_B = 8;
    private static final int MOADIM = 9;
    private static final int MISHPACHA = 10;
    private static final int SUCOT = 11;
    private static final int PESACH = 12;
    private static final int SHVIIT = 13;
    private static final int SHABAT = 14;
    private static final int SIMCHAT = 15;
    private static final int TEFILA = 16;
    private static final int TEFILAT_NASHIM = 17;
    private static final int HAR_BRACHOT = 18;
    private static final int HAR_YAMIM = 19;
    private static final int HAR_MOADIM = 20;
    private static final int HAR_SUCOT = 21;
    private static final int HAR_SHABAT = 22;
    private static final int HAR_SIMCHAT = 23;
    private static final int BOOKS_HEB_NUMBER = 24;
    private static final int E_TEFILA = 24;
    private static final int E_PESACH = 25;
    private static final int E_ZMANIM = 26;
    private static final int E_WOMEN_PRAYER = 27;
    private static final int E_SHABAT = 28;
    private static final int F_TEFILA = 29;
    private static final int S_SHABAT = 30;
    private static final int R_TFILA = 48;
    private static final int BOOKS_NUMBER = 49;
    private static final int HEBREW = 0;
    private static final int ENGLISH = 1;
    private static final int RUSSIAN = 2;
    private static final int SPANISH = 3;
    private static final int FRENCH = 4;
    public boolean firstChap = true;
    float speed;
    public static boolean restart=false;
    public static Intent broadcastIntent;
    public static String[] ToAudio={"שמע:","audio:","","",""};
    String fileName, fileNameOnly, lastFileName = null;
    public TextView duration, duration2;
    private double timeElapsed = 0, finalTime = 0;
    private SeekBar seekbar;
    private char playing = 0;//0-not playing 1-playing
    ImageButton buttonPlayPause;
    ImageButton buttonPrevious;
    ImageButton buttonNext;
    private int book;
    private int chapter;
    private int section;
    public int lastSec;
    ArrayList<String> sections;
    View view;
    public int refreshCount=0;
    Elements headers;
    Document doc = null;
    private Spinner spinner;
    private String header;
    public ListView listview;
    public String book_name;
    public TextView playerInfo;
    Bundle extras;
    public TextView[] textViewArray =new TextView[50];
    private MediaPlayerService  playerService;
    boolean serviceBound = false;
    boolean firstCall;
    private Intent playerIntent;
    boolean clickOnItemFromList = false;
    public String note_id;
    public String audio_id;
    public static final String Broadcast_START = "com.rafraph.pnineyHalachaHashalem.StartPlay";
    public static final String Broadcast_PLAY_PAUSE = "com.rafraph.pnineyHalachaHashalem.PlayPause";
    public static final String Broadcast_SKIP_NEXT = "com.rafraph.pnineyHalachaHashalem.SkipNext";
    public static final String Broadcast_SKIP_PREVIOUS = "com.rafraph.pnineyHalachaHashalem.SkipPrevious";
    public static final String Broadcast_SKIP_TO_SPECIFIC_SECTION = "com.rafraph.pnineyHalachaHashalem.SkipToSpecificSection";
    public static final String Broadcast_FORWARD_10 = "com.rafraph.pnineyHalachaHashalem.Forward10";
    public static final String Broadcast_BACKWARD_10 = "com.rafraph.pnineyHalachaHashalem.Backward10";
    public static final String Broadcast_OnTouch = "com.rafraph.pnineyHalachaHashalem.OnTouch";
    public static final String Broadcast_Speed = "com.rafraph.pnineyHalachaHashalem.Speed";
    public static WebView webview;
    public static WebSettings webSettings;
    public static Context context;
    public Boolean hearAndRead;
    public static final String PREFS_NAME = "MyPrefsFile";
    static SharedPreferences mPrefs;
    SharedPreferences.Editor shPrefEditor;
    public int BlackBackground = 0;
    public float[] sppedArray = {2f,1.75f, 1.5f,1.25f, 1f, 0.75f};
    public String innerSearchText, webLink;
    public Dialog innerSearchDialog, bookmarkDialog, acronymsDialog, autoScrollDialog;
    public int MyLanguage;
    public EditText TextToSearch, BookmarkName, TextToDecode;
    public Spinner spinnerAddMark, spinnerAutoScroll;
    public String strBookmark, Bookmarks;
    public int fontSize = 20;
    public LayoutInflater inf;
    public View infView;
    public RelativeLayout rl;
    public  LinearLayout ll;
    public String acronymsText;
    public static  ImageView scroll;
    public Date time = new Date();
    public int lastScrool = 0;
    public int lastChap=1;
    public boolean team=false;
    public int scrollPos=0;
    public Boolean finishLoad=false;
    public static String head;

    void ParseTheDoc()
    {
        String prefix;
        InputStream is;
        int size;
        byte[] buffer;
        String input;

        fileName = getClearUrl();
        if ((fileName.equals(lastFileName) == false)) {
            lastFileName = fileName;
            prefix = "file:///android_asset/";
            fileNameOnly = fileName.substring(prefix.length());
            try {
                is = getAssets().open(fileNameOnly);
                size = is.available();
                buffer = new byte[size];
                is.read(buffer);
                is.close();
                input = new String(buffer);
                doc = Jsoup.parse(input);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String getClearUrl() {
        String ClearUrl;
        ClearUrl = webview.getUrl();
        ClearUrl = ClearUrl.substring(0, ClearUrl.indexOf(".html") + 5);
        return ClearUrl;
    }

    public void setNoteId(String id) {
        note_id = id;
    }
    @Override
    public void onBackPressed() {
        if(playing==0)
            playPause(view);
        playPause(view);
        //playPause(view);
        extras = getIntent().getExtras();

        boolean fromText = extras.getBoolean("cameFromText");
        if(fromText){
        try {
            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
            Intent ourIntent = new Intent(myAudio.this, ourClass);
            int[] book_chapter = new int[2];
            book_chapter[0] = 0xFFFF;
            book_chapter[1] = 0xFFFF;
            ourIntent.putExtra("book_chapter", book_chapter);
            finish();
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
        else
        {
            Class ourClass = null;
            try {
                ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent ourIntent = new Intent(myAudio.this, ourClass);

            ourIntent.putExtra("homePage", false);
            ourIntent.putExtra("newV", true);
            finish();
            startActivity(ourIntent);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (hearAndRead)
            if (ev.getY() > 350 && ev.getY() < 1800 && lastScrool != webview.getScrollY()) {
                infView.setVisibility(View.INVISIBLE);
                webview.setY(0);
                //lastScrool = webview.getScrollY();
            }
        return super.dispatchTouchEvent(ev);
    }

    public void isConnected() throws ClassNotFoundException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null)
        {
            if(!networkInfo.isConnected())
            {
                Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.ExpandableListAdapter");
                Intent ourIntent = new Intent(myAudio.this, ourClass);
                ourIntent.putExtra("connect", 0);
                finish();
                startActivity(ourIntent);
            }
        }
        else
        {
            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.ExpandableListAdapter");
            Intent ourIntent = new Intent(myAudio.this, ourClass);
            ourIntent.putExtra("connect", 0);
            finish();
            startActivity(ourIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //rotateState=getResources().getConfiguration().orientation;
        extras = getIntent().getExtras();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
        rotateState=mPrefs.getInt("rotate",-1);
        MyLanguage = mPrefs.getInt("MyLanguage", 0);
        fontSize =mPrefs.getInt("fontSize",20);

        //if u wont rotate modecheck if(rotateState==getResources().getConfiguration().orientation)
        if (true)
        {
            shPrefEditor.putInt("rotate", getResources().getConfiguration().orientation);
            hearAndRead = extras.getBoolean("hearAndRead");
            //PREPARE TO READ AND LISTEN VIEW
            if (hearAndRead)
                setContentView(R.layout.text_main_audio);
            else
            {
                setContentView(R.layout.activity_audio);
                ImageView toMain2 = (ImageView) findViewById(R.id.to_main);
                if(MyLanguage==ENGLISH)
                    toMain2.setImageResource(R.drawable.to_main_e);
                else if(MyLanguage==RUSSIAN)
                    toMain2.setImageResource(R.drawable.to_main_r);
                else if(MyLanguage==SPANISH)
                    toMain2.setImageResource(R.drawable.to_main_s);
                else if(MyLanguage==FRENCH)
                    toMain2.setImageResource(R.drawable.to_main_f);

                toMain2.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if(playing==0)
                            playPause(view);
                        playPause(view);
                        playPause(v);

                        Class ourClass = null;
                        try
                        {
                            ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        Intent ourIntent = new Intent(myAudio.this, ourClass);
                        ourIntent.putExtra("goLast", true);

                        finish();
                        startActivity(ourIntent);
                    }
                });
            }
            mPrefs = getSharedPreferences(PREFS_NAME, 0);
            shPrefEditor = mPrefs.edit();
            ImageView menu= (ImageView) findViewById(R.id.menu);
            menu.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    ContextThemeWrapper ctw = new ContextThemeWrapper(myAudio.this, R.style.CustomPopupTheme);
                    PopupMenu popupMenu = new PopupMenu(ctw, v);

                    if(MyLanguage == ENGLISH) {
                        popupMenu.getMenu().add(0,-1,0,"Homepage");
                        popupMenu.getMenu().add(0,0,0,"Settings");
                        popupMenu.getMenu().add(0,1,0,"Books");
                        popupMenu.getMenu().add(0,2,0,"Daily Study");
                        popupMenu.getMenu().add(0,3,0,"Search");
                        popupMenu.getMenu().add(0,5,0,"Contact Us");
                        popupMenu.getMenu().add(0,6,0,"Purchasing books");
                        popupMenu.getMenu().add(0,7,0,"Ask the Rabbi");
                        //booksDownload configHeaders[6] = "ספרים להורדה";
                        popupMenu.getMenu().add(0,8,0,"About the series");
                        popupMenu.getMenu().add(0,9,0,"About");
                    }
                    else if(MyLanguage == RUSSIAN) {
                        popupMenu.getMenu().add(0,-1,0,"домашняя страница");
                        popupMenu.getMenu().add(0,0,0,"Настройки");
                        popupMenu.getMenu().add(0,1,0,"Книги");
                        popupMenu.getMenu().add(0,2,0,"Ежедневное изучение");
                        popupMenu.getMenu().add(0,3,0,"Поиск");
                        popupMenu.getMenu().add(0,5,0,"Отзыв");
                        popupMenu.getMenu().add(0,6,0,"Список книг");
                        popupMenu.getMenu().add(0,7,0,"Спросить равина");
                        //booksDownload configHeaders[6] = "ספרים להורדה";
                        popupMenu.getMenu().add(0,8,0,"О серии книг");
                        popupMenu.getMenu().add(0,9,0,"О приложении");
                    }
                    else if(MyLanguage == SPANISH) {
                        popupMenu.getMenu().add(0,-1,0,"Página principal");
                        popupMenu.getMenu().add(0,0,0,"Definiciones");
                        popupMenu.getMenu().add(0,1,0,"Libros");
                        popupMenu.getMenu().add(0,2,0,"Estudio diario");
                        popupMenu.getMenu().add(0,3,0,"Búsqueda");
                        popupMenu.getMenu().add(0,5,0,"Retroalimentación");
                        popupMenu.getMenu().add(0,6,0,"Compra de libros");
                        popupMenu.getMenu().add(0,7,0,"Pregúntale al rabino");
                        //booksDownload configHeaders[6] = "ספרים להורדה";
                        popupMenu.getMenu().add(0,8,0,"En la serie");
                        popupMenu.getMenu().add(0,9,0,"Sobre");
                    }
                    else if(MyLanguage == FRENCH) {
                        popupMenu.getMenu().add(0,-1,0,"Page d'accueil");
                        popupMenu.getMenu().add(0,0,0,"Réglages");
                        popupMenu.getMenu().add(0,1,0,"Livres");
                        popupMenu.getMenu().add(0,2,0,"étude quotidienne");
                        popupMenu.getMenu().add(0,3,0,"Recherche");
                        popupMenu.getMenu().add(0,5,0,"Contact Us");
                        popupMenu.getMenu().add(0,6,0,"Achat de livres");
                        popupMenu.getMenu().add(0,7,0,"Demander au rav");
                        //booksDownload configHeaders[6] = "ספרים להורדה";
                        popupMenu.getMenu().add(0,8,0,"Sur la collection");
                        popupMenu.getMenu().add(0,9,0,"À propos");
                    }
                    else {/*this is the default*/
                        popupMenu.getMenu().add(0,-1,0,"דף הבית");
                        popupMenu.getMenu().add(0,0,0,"הגדרות");
                        popupMenu.getMenu().add(0,1,0,"ספרים");
                        popupMenu.getMenu().add(0,2,0,"הלימוד היומי");
                        popupMenu.getMenu().add(0,3,0,"חיפוש");
                        popupMenu.getMenu().add(0,4,0,"ראשי תיבות");
                        popupMenu.getMenu().add(0,5,0,"משוב");
                        popupMenu.getMenu().add(0,6,0,"רכישת ספרים");
                        popupMenu.getMenu().add(0,7,0,"שאל את הרב");
                        //booksDownload configHeaders[6] = "ספרים להורדה";
                        popupMenu.getMenu().add(0,8,0,"על הסדרה");
                        popupMenu.getMenu().add(0,9,0,"אודות");
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                    {

                        @Override
                        public boolean onMenuItemClick(MenuItem item)
                        {
                            if(playing==0)
                                playPause(view);
                            playPause(view);
                            Class ourClass = null;
                            Intent ourIntent;
                            Intent intent;
                            switch (item.getItemId())
                            {
                                case -1:/*Home page*/

                                    try
                                    {
                                        ourClass = null;

                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
                                        ourIntent = new Intent(myAudio.this, ourClass);
                                        finish();
                                        startActivity(ourIntent);
                                    }
                                    catch (ClassNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    break;

                                case 0:/*settings*/

                                    try {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    ourIntent = new Intent(myAudio.this, ourClass);
                                    ourIntent.putExtra("homePage", true);
                                    shPrefEditor.putString("where", "myAudio");
                                    shPrefEditor.putString("h&rWebLink", webLink);
                                    shPrefEditor.putBoolean("hearAndRead", hearAndRead);
                                    if(hearAndRead)
                                        shPrefEditor.putInt("h&rScrool", webview.getScrollY());
                                    shPrefEditor.putInt("h&rAI", section);
                                    ourIntent.putExtra("sections_" + 1,extras.getStringArrayList("sections_" + 1));
                                    ourIntent.putExtra("sections_" + 2,extras.getStringArrayList("sections_" + 2));
                                    ourIntent.putExtra("sections_" + 3,extras.getStringArrayList("sections_" + 3));
                                    ourIntent.putExtra("sections_" + 4,extras.getStringArrayList("sections_" + 4));
                                    ourIntent.putExtra("sections_" + 5,extras.getStringArrayList("sections_" + 5));
                                    ourIntent.putExtra("sections_" + 6,extras.getStringArrayList("sections_" + 6));
                                    ourIntent.putExtra("sections_" + 7,extras.getStringArrayList("sections_" + 7));
                                    ourIntent.putExtra("sections_" + 8,extras.getStringArrayList("sections_" + 8));
                                    ourIntent.putExtra("sections_" + 9,extras.getStringArrayList("sections_" + 9));
                                    ourIntent.putExtra("sections_" + 10,extras.getStringArrayList("sections_" + 10));
                                    ourIntent.putExtra("sections_" + 11,extras.getStringArrayList("sections_" + 11));
                                    ourIntent.putExtra("sections_" + 12,extras.getStringArrayList("sections_" + 12));
                                    ourIntent.putExtra("sections_" + 13,extras.getStringArrayList("sections_" + 13));
                                    ourIntent.putExtra("sections_" + 14,extras.getStringArrayList("sections_" + 14));
                                    ourIntent.putExtra("sections_" + 15,extras.getStringArrayList("sections_" + 15));
                                    ourIntent.putExtra("sections_" + 16,extras.getStringArrayList("sections_" + 16));
                                    ourIntent.putExtra("sections_" + 17,extras.getStringArrayList("sections_" + 17));
                                    ourIntent.putExtra("sections_" + 18,extras.getStringArrayList("sections_" + 18));
                                    ourIntent.putExtra("sections_" + 19,extras.getStringArrayList("sections_" + 19));
                                    ourIntent.putExtra("sections_" + 20,extras.getStringArrayList("sections_" + 20));
                                    ourIntent.putExtra("sections_" + 21,extras.getStringArrayList("sections_" + 21));
                                    ourIntent.putExtra("sections_" + 22,extras.getStringArrayList("sections_" + 22));
                                    ourIntent.putExtra("sections_" + 23,extras.getStringArrayList("sections_" + 23));
                                    ourIntent.putExtra("sections_" + 24,extras.getStringArrayList("sections_" + 24));
                                    ourIntent.putExtra("sections_" + 25,extras.getStringArrayList("sections_" + 25));
                                    ourIntent.putExtra("sections_" + 26,extras.getStringArrayList("sections_" + 26));
                                    ourIntent.putExtra("sections_" + 27,extras.getStringArrayList("sections_" + 27));
                                    ourIntent.putExtra("sections_" + 28,extras.getStringArrayList("sections_" + 28));
                                    ourIntent.putExtra("sections_" + 29,extras.getStringArrayList("sections_" + 29));
                                    ourIntent.putExtra("sections_" + 30,extras.getStringArrayList("sections_" + 30));
                                    ourIntent.putExtra("sections_" + 31,extras.getStringArrayList("sections_" + 31));
                                    ourIntent.putExtra("sections_" + 32,extras.getStringArrayList("sections_" + 32));
                                    ourIntent.putExtra("sections_" + 33,extras.getStringArrayList("sections_" + 33));
                                    ourIntent.putExtra("sections_" + 34,extras.getStringArrayList("sections_" + 34));
                                    ourIntent.putExtra("sections_" + 35,extras.getStringArrayList("sections_" + 35));
                                    ourIntent.putExtra("sections_" + 36,extras.getStringArrayList("sections_" + 36));
                                    ourIntent.putExtra("sections_" + 37,extras.getStringArrayList("sections_" + 37));
                                    ourIntent.putExtra("sections_" + 38,extras.getStringArrayList("sections_" + 38));
                                    ourIntent.putExtra("sections_" + 39,extras.getStringArrayList("sections_" + 39));
                                    ourIntent.putExtra("sections_" + 40,extras.getStringArrayList("sections_" + 40));
                                    shPrefEditor.putInt("h&rChapId", chapter);
                                    shPrefEditor.putInt("h&rBookId", book);
                                    shPrefEditor.commit();
                                    finish();
                                    startActivity(ourIntent);
                                    break;

                                case 1:/*to books*/
                                    try {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    ourIntent = new Intent(myAudio.this, ourClass);
                                    ourIntent.putExtra("homePage", false);
                                    finish();
                                    startActivity(ourIntent);
                                    break;

                                case 2:/*pninaYomit*/
                                    try {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    ourIntent = new Intent(myAudio.this, ourClass);
                                    finish();
                                    startActivity(ourIntent);
                                    break;

                                case 3:/*search in all books*/
                                    try {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    ourIntent = new Intent(myAudio.this, ourClass);
                                    finish();
                                    startActivity(ourIntent);

                                    break;

                                case 4:/*acronyms*/
                                    acronymsDecode();
                                    break;

                                case 5:/*feedback*/
                                    try
                                    {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
                                        ourIntent = new Intent(myAudio.this, ourClass);
                                        finish();
                                        startActivity(ourIntent);
                                    }
                                    catch (ClassNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 6:/*buy books*/
                                    intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://shop.yhb.org.il/"));

                                    if(MyLanguage==FRENCH)
                                        intent.setData(Uri.parse("https://shop.yhb.org.il/fr/"));
                                    if(MyLanguage==RUSSIAN)
                                        intent.setData(Uri.parse("https://shop.yhb.org.il/ru/"));
                                    if(MyLanguage==SPANISH)
                                        intent.setData(Uri.parse("https://shop.yhb.org.il/es/"));
                                    if(MyLanguage==ENGLISH)
                                        intent.setData(Uri.parse("https://shop.yhb.org.il/en/"));
                                    finish();
                                    startActivity(intent);
                                    break;

                                case 7:/*ask the rav*/
                                    intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://yhb.org.il/שאל-את-הרב-2/"));
                                    finish();
                                    startActivity(intent);
                                    break;
                                case 8:/*about pninei*/
                                    try
                                    {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About_p");
                                        ourIntent = new Intent(myAudio.this, ourClass);
                                        finish();
                                        startActivity(ourIntent);
                                    }
                                    catch (ClassNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    //case 8:/*hascamot*/
                                    //   hascamotDialog();
                                      break;
                                case 9:/*about*/
                                    try
                                    {
                                        ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
                                        ourIntent = new Intent(myAudio.this, ourClass);
                                        finish();
                                        startActivity(ourIntent);
                                    }
                                    catch (ClassNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    break;


                                default:
                                    break;
                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                }
            });

            if (hearAndRead)
            {
                inf = getLayoutInflater();
                infView = inf.inflate(R.layout.tochen_actionbar_lay, null);
                rl = (RelativeLayout) findViewById(R.id.content);
                infView.setVisibility(View.VISIBLE);
                rl.addView(infView);
                scroll=findViewById(R.id.auto_scrool2);
                scroll.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        scroll.setImageResource(R.drawable.fill_auto_scroll);
                        showPopupAutoScrollSettings(findViewById(R.id.auto_scrool2));
                    }
                });
            }
            context = this;
            ll = (LinearLayout) findViewById(R.id.hi);
            //ll.setVisibility(View.INVISIBLE);
            //to swich to read and hear mode change here and 269+- to if(true)
            if (hearAndRead) {

                webview = (WebView) findViewById(R.id.webView1);
                webSettings = webview.getSettings();
                webSettings.setMinimumFontSize(20);
                webSettings.setDefaultTextEncodingName("utf-8");
                webSettings.setJavaScriptEnabled(true);

                webSettings.setSupportZoom(true);
                API = android.os.Build.VERSION.SDK_INT;
                if (API < 19)
                    webSettings.setBuiltInZoomControls(true);
                webview.requestFocusFromTouch();
                webview.getSettings().setAllowFileAccess(true);
                finishLoad=false;
                webLink = getIntent().getStringExtra("webLink");
                getIntent().putExtra("WebLink",webLink);
                webview.loadUrl(webLink);
                webview.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        finishLoad=true;
                    }
                });

                webview.setY(80);
                BlackBackground = mPrefs.getInt("BlackBackground", 0);
                if (BlackBackground == 1) {
                    webview.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            view.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
                        }
                    });
                    webview.setBackgroundColor(Color.BLACK);
                    infView.setBackgroundColor(Color.BLACK);
                }

                final Runnable runnableNote = new Runnable() {
                    public void run() {
                        String note, content = null;
                        int intNoteId;
                        final Dialog dialog = new Dialog(context);
                        WebView webviewNote;
                        WebSettings webSettingsNote;
                        dialog.setContentView(R.layout.note);
                        intNoteId = Integer.parseInt(note_id) - 1000;
                        note_id = Integer.toString(intNoteId);
                        dialog.setTitle("        הערה " + note_id);
                        webviewNote = (WebView) dialog.findViewById(R.id.webViewNote1);
                        webSettingsNote = webviewNote.getSettings();
                        webSettingsNote.setDefaultTextEncodingName("utf-8");
                        webviewNote.requestFocusFromTouch();
                        if (API < 19)
                            webSettingsNote.setBuiltInZoomControls(true);

                        content = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                                "<html><head>" +
                                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />" +
                                "<head>";
                        if (mPrefs.getInt("BlackBackground", 0) == 0)
                            content += "<body>";//White background
                        else
                            content += "<body style=\"background-color:black;color:white\">";//Black background

                        ParseTheDoc();
                        headers = doc.select("div#ftn" + note_id);
                        note = headers.get(0).text();
                        content += "<p dir=\"RTL\">" + note + "</p> </body></html>";
                        webviewNote.loadData(content, "text/html; charset=utf-8", "UTF-8");
                        webSettingsNote.setDefaultFontSize(18);
                        dialog.show();

                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //do whatever you want the back key to do
                                dialog.dismiss();

                            }
                        });
                    }
                };
                final Runnable runnableAudio = new Runnable() {
                    public void run() {
                        sendSectionIdAndPlay(Integer.parseInt(audio_id));
                    }
                };
                webview.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public <var> void performClick(String id) {
                        setNoteId(id);
                        runOnUiThread(runnableNote);
                    }
                }, "ok");
                webview.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void performClick(String id) {
                        setAudioId(id);
                        runOnUiThread(runnableAudio);
                        playing = 0;
                        playPause(view);
                    }
                }, "audio");
                createActionBar();
//                ImageView scroll=findViewById(R.id.auto_scrool);
//                scroll.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        autoScrollSpeedDialog();
//                    }
//                });
            }

            firstCall = true;
            spinner = findViewById(R.id.myspinner);
            spinner.setSelected(true);
            final List<String> states= Arrays.asList("x2","x1.75","x1.5","x1.25","x1","x0.75");
             ArrayAdapter simpleadapter = new ArrayAdapter(this, R.layout.spinner_des, states);

            simpleadapter.setDropDownViewResource((android.R.layout.simple_list_item_1));
            spinner.setAdapter(simpleadapter);
            float tempSpeed=mPrefs.getFloat("audioSpeed",1f);
            if (tempSpeed==0.75f)
                spinner.setSelection(5);
            if (tempSpeed==1f)
                spinner.setSelection(4);
            if (tempSpeed==1.25f)
                spinner.setSelection(3);
            if (tempSpeed==1.5f)
                spinner.setSelection(2);
            if (tempSpeed==1.75f)
                spinner.setSelection(1);
            if (tempSpeed==2f)
                spinner.setSelection(0);
            spinner.setOnItemSelectedListener(this);
            registerAllBroadcast();
            initializeViews();
            playerInfo = (TextView) findViewById(R.id.playerInfo);
            extras = getIntent().getExtras();
            sections = new ArrayList<String>();
            book = extras.getInt("book_id");
            chapter = extras.getInt("chapter_id");
            if (hearAndRead)
            {
                scrollPos = getIntent().getIntExtra("scroolY", 0);
                fontSize = mPrefs.getInt("fontSize",20);
                webSettings.setMinimumFontSize(fontSize);
            }

            if (book == KASHRUT_B)//KASHRUT_B is starting from chapter 20
                chapter += 19;
            section = extras.getInt("audio_id");
            sections = extras.getStringArrayList("sections_" + chapter);
            book_name = get_book_name_by_id();
            playerInfo.setText(book_name + " " + convert_character_to_id(chapter) + ", " + convert_character_to_id(section));
            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
            List<TextView> txtvList = new ArrayList<TextView>();
            TextView textView = new TextView(getBaseContext());
            for (int i = 0; i < sections.size(); i++)
            {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("listview_title", sections.get(i));
                textView = new TextView(getBaseContext());
                textView.setText("   " + sections.get(i) + "\n");
                if (mPrefs.getInt("BlackBackground", 0) == 1)
                    textView.setTextColor(Color.WHITE);
                else
                    textView.setTextColor(Color.BLACK);
                textView.setTextSize(24);
                if ((textView.getText().charAt(2) == 'א') && i != 0)
                    break;
                txtvList.add(textView);
            }
            lastSec=txtvList.size();

            String[] from = {"listview_title"};
            int[] to = {R.id.listview_item_title};
            ArrayAdapter adapter;
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aList);
            listview = (ListView) findViewById(R.id.list_view);
            listview.setAdapter(adapter);

            if (mPrefs.getInt("BlackBackground", 0) == 1)
                listview.setBackgroundColor(Color.BLACK);
            else
                listview.setBackgroundColor(Color.WHITE);
            for (int i=0; i<txtvList.size(); i++)
            {
                listview.addHeaderView(txtvList.get(i));
                textViewArray[i]=txtvList.get(i);
            }
            for(int i = 0; i< textViewArray.length; i++)
            {
                if(textViewArray[i]!=null)
                {
                    if (mPrefs.getInt("BlackBackground", 0) == 1)
                    {
                        textViewArray[i].setBackgroundColor(Color.BLACK);
                        textViewArray[i].setTextColor(Color.WHITE);
                    }
                    else
                    {
                        textViewArray[i].setBackgroundColor(Color.WHITE);
                        textViewArray[i].setTextColor(Color.BLACK);
                    }
                }
            }
            textViewArray[section-1].setBackgroundColor(Color.rgb(151, 6, 6));
            textViewArray[section-1].setTextColor(Color.WHITE);
            listview.setCacheColorHint(Color.WHITE);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    listview.setSelected(true);
                    listview.setSelection(position);
                    listview.setSelection(position);

                    if (clickOnItemFromList == true) {
                        sendSectionIdAndPlay(position + 1);
                        for (int i = 0; i < txtvList.size(); i++) {
                            if (i == position) {
                                txtvList.get(position).setBackgroundColor(Color.rgb(151, 6, 6));
                                txtvList.get(position).setTextColor(Color.WHITE);
                            } else if (mPrefs.getInt("BlackBackground", 0) == 1) {
                                txtvList.get(i).setBackgroundColor(Color.BLACK);
                                txtvList.get(i).setTextColor(Color.WHITE);
                            } else {
                                txtvList.get(i).setBackgroundColor(Color.WHITE);
                                txtvList.get(i).setTextColor(Color.BLACK);
                            }
                        }
                        listview = (ListView) findViewById(R.id.list_view);
                        listview.setAdapter(adapter);
                        listview.setBackgroundColor(Color.WHITE);
                        listview.setSelection(position);

                        playing = 1;
                        buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);

                        //for (int i=0;i<txtvList.size();i++)
                        //  listview.addHeaderView(txtvList.get(i));
                        broadcastIntent = new Intent(Broadcast_Speed);
                        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");

                        sendBroadcast(broadcastIntent);
                        broadcastIntent = new Intent(Broadcast_Speed);
                        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
                        int choice = spinner.getSelectedItemPosition();
                        speed = sppedArray[choice];
                        shPrefEditor.putFloat("audioSpeed", speed);
                        shPrefEditor.commit();
                    }

                    //playPause(view);
                    clickOnItemFromList = true;//change it back to true. In cases that it is not came directly from click om list item, it will be changed to false in this cases
                }
            });
            buttonNext = (ImageButton) findViewById(R.id.media_next);
            buttonPrevious = (ImageButton) findViewById(R.id.media_prev);
            head = ((TextView) findViewById(R.id.playerInfo)).getText().toString();
            initializeSeekBar();
            while (playing == 1) {
                try {
                    wait(1000);
                    broadcastIntent = new Intent(Broadcast_FORWARD_10);
                    broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
                    //broadcastIntent.putExtra("section", section);
                    sendBroadcast(broadcastIntent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Intent broadcastIntent = new Intent(Broadcast_SKIP_NEXT);
            broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
            sendBroadcast(broadcastIntent);
            broadcastIntent = new Intent(Broadcast_Speed);
            broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
            int choice = spinner.getSelectedItemPosition();
            speed = sppedArray[choice];
            shPrefEditor.putFloat("audioSpeed", speed);
            shPrefEditor.commit();
            if (playing == 0)
                broadcastIntent.putExtra("play", 0);
            sendBroadcast(broadcastIntent);
            playing = 1;
            buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
            refresh(2000);
        }

        if(false) //downaudio
        {
            final ProgressDialog downloadWait = ProgressDialog.show(myAudio.this, "", "מוריד ספרים אנא המתן");
            new Thread() {
                public void run() {
                    try {
                        myAudio.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    downloadBooks2(book,chapter,section, "audio");
                                } catch (InterruptedException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {

                    }
                    downloadWait.dismiss();
                }
            }.start();
        }
    }

    public  void downloadBooks2(int downBook,int downChap,int downSec ,String folder) throws InterruptedException, IOException {
        if(downSec>1) {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/"+ folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec-1)).substring(0, 2) + ".mp3");
            if(!file.exists()) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://cdn1.yhb.org.il/mp3/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec - 1)).substring(0, 2) + ".mp3"));
                request.setDescription("please  wait");
                //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, "pnineyHalacha/" + folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec - 1)).substring(0, 2) + ".mp3");
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                shPrefEditor.putString("Bookmark1",mPrefs.getString("Bookmark2",""));
                shPrefEditor.putString("Bookmark2",mPrefs.getString("Bookmark3",""));
                shPrefEditor.putString("Bookmark3",mPrefs.getString("Bookmark4",""));
                shPrefEditor.putString("Bookmark4",mPrefs.getString("Bookmark5",""));
                shPrefEditor.putString("Bookmark5",("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec - 1)).substring(0, 2) + ".mp3");
                shPrefEditor.commit();
            }
        }
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/"+ folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec)).substring(0, 2) + ".mp3");
        if(!file.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://cdn1.yhb.org.il/mp3/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec)).substring(0, 2) + ".mp3"));
            request.setDescription("please  wait");
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, "pnineyHalacha/" + folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec)).substring(0, 2) + ".mp3");
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            shPrefEditor.putString("Bookmark1",mPrefs.getString("Bookmark2",""));
            shPrefEditor.putString("Bookmark2",mPrefs.getString("Bookmark3",""));
            shPrefEditor.putString("Bookmark3",mPrefs.getString("Bookmark4",""));
            shPrefEditor.putString("Bookmark4",mPrefs.getString("Bookmark5",""));
            shPrefEditor.putString("Bookmark5",("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec )).substring(0, 2) + ".mp3");
            shPrefEditor.commit();
        }

        if(downSec<lastSec) {
             file = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/"+ folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec+1)).substring(0, 2) + ".mp3");
            if(!file.exists()) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://cdn1.yhb.org.il/mp3/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec + 1)).substring(0, 2) + ".mp3"));
                request.setDescription("please  wait");
                //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, "pnineyHalacha/" + folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec + 1)).substring(0, 2) + ".mp3");
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                shPrefEditor.putString("Bookmark1",mPrefs.getString("Bookmark2",""));
                shPrefEditor.putString("Bookmark2",mPrefs.getString("Bookmark3",""));
                shPrefEditor.putString("Bookmark3",mPrefs.getString("Bookmark4",""));
                shPrefEditor.putString("Bookmark4",mPrefs.getString("Bookmark5",""));
                shPrefEditor.putString("Bookmark5",("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec +1)).substring(0, 2) + ".mp3");
                shPrefEditor.commit();
            }
        }
        if(downSec<lastSec-1) {
             file = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/"+ folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec+2)).substring(0, 2) + ".mp3");
            if(!file.exists()) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://cdn1.yhb.org.il/mp3/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec + 2)).substring(0, 2) + ".mp3"));
                request.setDescription("please  wait");
                //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, "pnineyHalacha/" + folder + "/" + ("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec + 2)).substring(0, 2) + ".mp3");
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                shPrefEditor.putString("Bookmark1",mPrefs.getString("Bookmark2",""));
                shPrefEditor.putString("Bookmark2",mPrefs.getString("Bookmark3",""));
                shPrefEditor.putString("Bookmark3",mPrefs.getString("Bookmark4",""));
                shPrefEditor.putString("Bookmark4",mPrefs.getString("Bookmark5",""));
                shPrefEditor.putString("Bookmark5",("0" + (downBook)).substring(0, 2) + "-" + ("0" + (downChap)).substring(0, 2) + "-" + ("0" + (downSec +2)).substring(0, 2) + ".mp3");
                shPrefEditor.commit();
            }
        }
        file=new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/"+ folder);
        File[] list = file.listFiles();
        int count = 0;
        if(list!=null)
        for (File f: list){
            String name = f.getName();
            if(!name.equals(mPrefs.getString("Bookmark1",""))&&!name.equals(mPrefs.getString("Bookmark2",""))&&!name.equals(mPrefs.getString("Bookmark3",""))&&!name.equals(mPrefs.getString("Bookmark4",""))&&!name.equals(mPrefs.getString("Bookmark5","")))
                file.delete();
        }

    }

    private void refresh(int millisec) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                speed = sppedArray[spinner.getSelectedItemPosition()];
                content();
            }
        };
        handler.postDelayed(runnable, millisec);
    }

    public void content()
    {
        if (hearAndRead) {
              if(lastChap!=chapter&&finishLoad){
                lastScrool=webview.getScrollY();
                webview.loadUrl(webLink.substring(0, webLink.lastIndexOf('_')) + "_" + (chapter) + ".html");
                webview.setWebViewClient(new WebViewClient(){

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        webview.setY(100);
                        webview.scrollTo(0,scrollPos);
                        scrollPos=0;
                        super.onPageFinished(view, url);
                    }
                });
                lastChap=chapter;
              }
            if (webview.getScrollY()==0)
                webview.setY(100);

            infView.setVisibility(View.VISIBLE);
            if (mPrefs.getInt("BlackBackground", 0) == 1) {
                webview.loadUrl(
                        "javascript:document.body.style.setProperty(\"color\", \"white\");"
                );
                webview.setBackgroundColor(Color.BLACK);
                infView.setBackgroundColor(Color.BLACK);
            } else {
                webview.loadUrl(
                        "javascript:document.body.style.setProperty(\"color\", \"black\");"
                );
                webview.setBackgroundColor(Color.WHITE);
                infView.setBackgroundColor(Color.WHITE);
            }
           refreshCount++;
            refresh(2000);
        }
    }

    public void createActionBar()
    {
        scrollSpeed=-1;
        infView.setVisibility(View.VISIBLE);
        ImageView searchBtn = infView.findViewById(R.id.page_search);

        final ImageButton searchBtnDown = infView.findViewById(R.id.ibFindNext);
        final ImageButton searchBtnUp = infView.findViewById(R.id.ibFindPrevious);
        final ImageView addBookMark = infView.findViewById(R.id.make_mark);
        final ImageView toMain = infView.findViewById(R.id.to_main);
        final ImageView menu = infView.findViewById(R.id.menu);
        final LinearLayout main = infView.findViewById(R.id.lnrOption3);
        if(MyLanguage==ENGLISH)
            toMain.setImageResource(R.drawable.to_main_e);
        if(MyLanguage==RUSSIAN)
            toMain.setImageResource(R.drawable.to_main_r);
        if(MyLanguage==SPANISH)
            toMain.setImageResource(R.drawable.to_main_s);
        if(MyLanguage==FRENCH)
            toMain.setImageResource(R.drawable.to_main_f);
        if(BlackBackground==1)
        {
            main.setBackgroundColor(Color.rgb(120,1,1));
            if(MyLanguage==ENGLISH)
                toMain.setImageResource(R.drawable.to_main_b_e);
            if(MyLanguage==RUSSIAN)
                toMain.setImageResource(R.drawable.to_main_b_r);
            if(MyLanguage==SPANISH)
                toMain.setImageResource(R.drawable.to_main_b_s);
            if(MyLanguage==FRENCH)
                toMain.setImageResource(R.drawable.to_main_b_f);
            if(MyLanguage==HEBREW)
                toMain.setImageResource(R.drawable.to_main_b);
            if(hearAndRead)
            {
                final ImageView pageSearch = infView.findViewById(R.id.page_search);
                final ImageView makeMark = infView.findViewById(R.id.make_mark);
                pageSearch.setImageResource(R.drawable.page_search_b);
                makeMark.setImageResource(R.drawable.make_bookmark_b);
            }
            menu.setImageResource(R.drawable.ic_action_congif_b);
        }
        //searchBtn.setVisibility(View.GONE);
        //addBookMark.setVisibility(View.GONE);
        //config.setVisibility(View.GONE);
        //scrollBtn.setVisibility(View.GONE);
        searchBtnDown.setVisibility(View.GONE);
        searchBtnUp.setVisibility(View.GONE);

        toMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(playing==0)
                    playPause(view);
                playPause(view);
                playPause(v);
                Class ourClass = null;
                if (serviceBound) {
                    unbindService(serviceConnection);
                    //service is active
                    stopService(playerIntent);
                    playerService.onDestroy();
                    //int a=0/0;
                    //serviceBound=false;
                }
                try {
                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent ourIntent = new Intent(myAudio.this, ourClass);
                finish();
                //ourIntent.putExtra("goLast", true);
                startActivity(ourIntent);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                searchBtnDown.setVisibility(View.VISIBLE);
                searchBtnUp.setVisibility(View.VISIBLE);
                innerSearch();
            }
        });
        searchBtnDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webview.findNext(true);
            }
        });
        searchBtnUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webview.findNext(false);
            }
        });
        addBookMark.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                bookmarkDialog = new Dialog(context);
                if (MyLanguage == ENGLISH)
                    bookmarkDialog.setContentView(R.layout.add_bookmark_english);
                else if (MyLanguage == RUSSIAN)
                    bookmarkDialog.setContentView(R.layout.add_bookmark_russian);
                else if (MyLanguage == SPANISH)
                    bookmarkDialog.setContentView(R.layout.add_bookmark_spanish);
                else if (MyLanguage == FRENCH)
                    bookmarkDialog.setContentView(R.layout.add_bookmark_french);
                else
                    bookmarkDialog.setContentView(R.layout.add_bookmark);
                bookmarkDialog.setTitle("הוסף סימניה");

                Button dialogButton = (Button) bookmarkDialog.findViewById(R.id.dialogButtonOK);
                spinnerAddMark = (Spinner) bookmarkDialog.findViewById(R.id.spinner1);
                BookmarkName = (EditText) bookmarkDialog.findViewById(R.id.editTextBookmarkName);

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String extra="";
                        int index = 0, index_end = 0;
                        String bookmarkText = BookmarkName.getText().toString();
                        bookmarkText.replaceAll(",", "-");/*if the user insert comma, replace it with "-"*/
                        /*		      bookmark name			book					chapter						scroll
                        						fontSize*/
                        if (MyLanguage == ENGLISH)
                            extra=ToAudio[1];

                        else if (MyLanguage == RUSSIAN)
                            extra=ToAudio[2];

                        else if (MyLanguage == SPANISH)
                            extra = ToAudio[3];
                        else if (MyLanguage == FRENCH)
                            extra = ToAudio[4];
                        else
                            extra = ToAudio[0];

                        strBookmark =extra+ bookmarkText + "," + book + "," + chapter + "," + webview.getScrollY() + "," + (int) (fontSize)/*(webview.getScale()*100)*/;

                        Bookmarks = mPrefs.getString("Bookmarks", "");
                        if ((index = Bookmarks.indexOf(bookmarkText)) != -1)/*if there is already bookmark with the same name override it*/ {
                            index_end = index;
                            for (int i = 0; i < 5; i++) {
                                if (Bookmarks.indexOf(",", index_end + 1) != -1)
                                    index_end = Bookmarks.indexOf(",", index_end + 1);
                                else/*in case that this is the last bookmark*/
                                    index_end = Bookmarks.length();
                            }
                            Bookmarks = Bookmarks.substring(0, index) + strBookmark + Bookmarks.substring(index_end, Bookmarks.length());
                            if (MyLanguage == ENGLISH) {
                                Toast.makeText(getApplicationContext(), "Existing bookmark updated", Toast.LENGTH_SHORT).show();
                            }
                            else if (MyLanguage == RUSSIAN) {
                                Toast.makeText(getApplicationContext(), "Текущая закладка обновлена", Toast.LENGTH_SHORT).show();
                            }
                            else if (MyLanguage == SPANISH) {
                                Toast.makeText(getApplicationContext(), "Marcador existente actualizado", Toast.LENGTH_SHORT).show();
                            }
                            else if (MyLanguage == FRENCH) {
                                Toast.makeText(getApplicationContext(), "Le signet existant est mis à jour", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "הסימניה הקיימת עודכנה", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            {
                            Bookmarks += "," + strBookmark;
                            if (MyLanguage == ENGLISH) {
                                Toast.makeText(getApplicationContext(), "New bookmark created", Toast.LENGTH_SHORT).show();
                                extra = ToAudio[1];
                            }
                            else if (MyLanguage == RUSSIAN) {
                                Toast.makeText(getApplicationContext(), "Создана новая закладка", Toast.LENGTH_SHORT).show();
                                extra = ToAudio[2];
                            }
                            else if (MyLanguage == SPANISH) {
                                Toast.makeText(getApplicationContext(), "Nuevo marcador creado", Toast.LENGTH_SHORT).show();
                                extra = ToAudio[3];
                            }
                            else if (MyLanguage == FRENCH) {
                                Toast.makeText(getApplicationContext(), "Nouveau signet créé", Toast.LENGTH_SHORT).show();
                                extra = ToAudio[4];
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "סימניה חדשה נוצרה", Toast.LENGTH_SHORT).show();
                            }
                            }
                        shPrefEditor.putString("Bookmarks", Bookmarks);
                        shPrefEditor.commit();
                        bookmarkDialog.dismiss();
                    }
                });

                fillChaptersNames();
                BookmarkName.setText(chaptersNames[book][chapter]);

                addItemsOnSpinner();

                spinnerAddMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    boolean first = true;

                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                    {
                        if (first == false)
                            BookmarkName.setText(parent.getItemAtPosition(pos).toString());
                        first = false;
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                        // do nothing
                    }
                });
                bookmarkDialog.show();
            }
        });
    }

    public int scrollSpeed = -1;
    private Handler mHandler = new Handler();

    public Runnable mScrollDown = new Runnable()
    {
        public void run() {
            if (scrollSpeed == -1) // in case that "stop" pressed
            {
                webview.scrollBy(0, 0);
            } else {
                webview.scrollBy(0, 1);
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, 200 / scrollSpeed);
            }
        }
    };

    private void showPopupAutoScrollSettings(View v)
    {
        android.support.v7.view.ContextThemeWrapper ctw = new ContextThemeWrapper(myAudio.this, R.style.CustomPopupTheme3);
        PopupMenu popupMenu = new PopupMenu(ctw, v);

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                scroll.setImageResource(R.drawable.auto_scrool);

            }
        });
        String configHeaders[] = new String[7];

        configHeaders[0] = "0";
        configHeaders[1] = "1";
        configHeaders[2] = "2";
        configHeaders[3] = "3";
        configHeaders[4] = "4";
        configHeaders[5] = "5";
        configHeaders[6] = "6";


        popupMenu.getMenu().add(0, 0, 0, configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
        popupMenu.getMenu().add(0, 1, 1, configHeaders[1]);
        popupMenu.getMenu().add(0, 2, 2, configHeaders[2]);
        popupMenu.getMenu().add(0, 3, 3, configHeaders[3]);
        popupMenu.getMenu().add(0, 4, 4, configHeaders[4]);
        popupMenu.getMenu().add(0, 5, 5, configHeaders[5]);
        popupMenu.getMenu().add(0, 6, 6, configHeaders[6]);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                WebSettings webSettings = webview.getSettings();
                webSettings.setMinimumFontSize(mPrefs.getInt("fontSize",20));
                switch (item.getItemId()) {
                    case 0:
                        scrollSpeed = -1;

                        break;
                    case 1:
                        scrollSpeed = 2;
                        runOnUiThread(mScrollDown);
                        break;
                    case 2:
                        scrollSpeed = 4;
                        runOnUiThread(mScrollDown);
                        break;
                    case 3:
                        scrollSpeed = 6;
                        runOnUiThread(mScrollDown);
                        break;
                    case 4:
                        scrollSpeed = 8;
                        runOnUiThread(mScrollDown);
                        break;
                    case 5:
                        scrollSpeed = 10;
                        runOnUiThread(mScrollDown);
                    case 6:
                        scrollSpeed = 12;
                        runOnUiThread(mScrollDown);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    protected void onStop() {
        scrollSpeed=-1;
        super.onStop();
    }

    void autoScrollSpeedDialog() {
        final Context context = this;

        // custom dialog
        autoScrollDialog = new Dialog(context);
        autoScrollDialog.setContentView(R.layout.auto_scroll);
        autoScrollDialog.setTitle("מהירות גלילה אוטומטית");
        Button dialogButton = (Button) autoScrollDialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                autoScrollDialog.dismiss();
            }
        });
        spinnerAutoScroll = (Spinner) autoScrollDialog.findViewById(R.id.spinner_auto_scroll);
        scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
        spinnerAutoScroll.setSelection((scrollSpeed / 2) - 1);
        spinnerAutoScroll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            boolean first = true;

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                scrollSpeed = (pos + 1) * 2;
                shPrefEditor.putInt("scrollSpeed", scrollSpeed);
                shPrefEditor.commit();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // do nothing
            }
        });
        autoScrollDialog.show();
    }

    private void showPopupMenuSettings(View v)
    {
        PopupMenu popupMenu = new PopupMenu(myAudio.this, v);

        String configHeaders[] = new String[7];
        if (MyLanguage == ENGLISH) {
            configHeaders[0] = "Settings";
            configHeaders[1] = "About";
            configHeaders[2] = "Feedback";
            configHeaders[3] = "Explanation of search results";
            configHeaders[4] = "Acronyms";
            configHeaders[5] = "Zoom in";
            configHeaders[6] = "Zoom out";
        } else if (MyLanguage == RUSSIAN) {
            configHeaders[0] = "Настройки";
            configHeaders[1] = "Около";
            configHeaders[2] = "Обратная связь";
            configHeaders[3] = "Объяснение результатов поиска";
            configHeaders[4] = "Абревиатуры";
            configHeaders[5] = "Увеличить шрифт";
            configHeaders[6] = "Уменьшить шрифт";
        } else if (MyLanguage == SPANISH) {
            configHeaders[0] = "Ajustes";
            configHeaders[1] = "Acerca de";
            configHeaders[2] = "Comentarios";
            configHeaders[3] = "Explicacion del resultado de la busqueda";
            configHeaders[4] = "Acronimos";
            configHeaders[5] = "Aumentar enfoque";
            configHeaders[6] = "Disminuir enfoque";
        } else if (MyLanguage == FRENCH) {
            configHeaders[0] = "Definitions";
            configHeaders[1] = "A Propos de…";
            configHeaders[2] = "Commentaires";
            configHeaders[3] = "Explication de la recherche";
            configHeaders[4] = "Acronymes";
            configHeaders[5] = "Zoom avant";
            configHeaders[6] = "Zoom arrière";
        } else {/*this is the default*/
            configHeaders[0] = "הגדרות";
            configHeaders[1] = "אודות";
            configHeaders[2] = "משוב";
            configHeaders[3] = "הסבר על החיפוש";
            configHeaders[4] = "ראשי תיבות";
            configHeaders[5] = "הגדל טקסט";
            configHeaders[6] = "הקטן טקסט";
        }

        popupMenu.getMenu().add(0, 0, 0, configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
        popupMenu.getMenu().add(0, 1, 1, configHeaders[1]);
        popupMenu.getMenu().add(0, 2, 2, configHeaders[2]);
        popupMenu.getMenu().add(0, 3, 3, configHeaders[3]);
        popupMenu.getMenu().add(0, 4, 4, configHeaders[4]);
        if (API >= 19) {
            popupMenu.getMenu().add(0, 5, 5, configHeaders[5]);
            popupMenu.getMenu().add(0, 6, 6, configHeaders[6]);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                WebSettings webSettings = webview.getSettings();
                fontSize = mPrefs.getInt("fontSize",20);;
                Class ourClass = null;
                Intent ourIntent;
                Intent intent;
                switch (item.getItemId()) {
                    case 0:/*settings*/

                        try {
                            ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        ourIntent = new Intent(myAudio.this, ourClass);
                        ourIntent.putExtra("homePage", true);
                        shPrefEditor.putString("where", "myAudio");
                        shPrefEditor.putString("h&rWebLink", webLink);
                        shPrefEditor.putBoolean("hearAndRead", hearAndRead);
                        if(hearAndRead)
                            shPrefEditor.putInt("h&rScrool", webview.getScrollY());
                        shPrefEditor.putInt("h&rAI", section);

                        ourIntent.putExtra("sections_" + 1,extras.getStringArrayList("sections_" + 1));
                        ourIntent.putExtra("sections_" + 2,extras.getStringArrayList("sections_" + 2));
                        ourIntent.putExtra("sections_" + 3,extras.getStringArrayList("sections_" + 3));
                        ourIntent.putExtra("sections_" + 4,extras.getStringArrayList("sections_" + 4));
                        ourIntent.putExtra("sections_" + 5,extras.getStringArrayList("sections_" + 5));
                        ourIntent.putExtra("sections_" + 6,extras.getStringArrayList("sections_" + 6));
                        ourIntent.putExtra("sections_" + 7,extras.getStringArrayList("sections_" + 7));
                        ourIntent.putExtra("sections_" + 8,extras.getStringArrayList("sections_" + 8));
                        ourIntent.putExtra("sections_" + 9,extras.getStringArrayList("sections_" + 9));
                        ourIntent.putExtra("sections_" + 10,extras.getStringArrayList("sections_" + 10));
                        ourIntent.putExtra("sections_" + 11,extras.getStringArrayList("sections_" + 11));
                        ourIntent.putExtra("sections_" + 12,extras.getStringArrayList("sections_" + 12));
                        ourIntent.putExtra("sections_" + 13,extras.getStringArrayList("sections_" + 13));
                        ourIntent.putExtra("sections_" + 14,extras.getStringArrayList("sections_" + 14));
                        ourIntent.putExtra("sections_" + 15,extras.getStringArrayList("sections_" + 15));
                        ourIntent.putExtra("sections_" + 16,extras.getStringArrayList("sections_" + 16));
                        ourIntent.putExtra("sections_" + 17,extras.getStringArrayList("sections_" + 17));
                        ourIntent.putExtra("sections_" + 18,extras.getStringArrayList("sections_" + 18));
                        ourIntent.putExtra("sections_" + 19,extras.getStringArrayList("sections_" + 19));
                        ourIntent.putExtra("sections_" + 20,extras.getStringArrayList("sections_" + 20));
                        ourIntent.putExtra("sections_" + 21,extras.getStringArrayList("sections_" + 21));
                        ourIntent.putExtra("sections_" + 22,extras.getStringArrayList("sections_" + 22));
                        ourIntent.putExtra("sections_" + 23,extras.getStringArrayList("sections_" + 23));
                        ourIntent.putExtra("sections_" + 24,extras.getStringArrayList("sections_" + 24));
                        ourIntent.putExtra("sections_" + 25,extras.getStringArrayList("sections_" + 25));
                        ourIntent.putExtra("sections_" + 26,extras.getStringArrayList("sections_" + 26));
                        ourIntent.putExtra("sections_" + 27,extras.getStringArrayList("sections_" + 27));
                        ourIntent.putExtra("sections_" + 28,extras.getStringArrayList("sections_" + 28));
                        ourIntent.putExtra("sections_" + 29,extras.getStringArrayList("sections_" + 29));
                        ourIntent.putExtra("sections_" + 30,extras.getStringArrayList("sections_" + 30));
                        ourIntent.putExtra("sections_" + 31,extras.getStringArrayList("sections_" + 31));
                        ourIntent.putExtra("sections_" + 32,extras.getStringArrayList("sections_" + 32));
                        ourIntent.putExtra("sections_" + 33,extras.getStringArrayList("sections_" + 33));
                        ourIntent.putExtra("sections_" + 34,extras.getStringArrayList("sections_" + 34));
                        ourIntent.putExtra("sections_" + 35,extras.getStringArrayList("sections_" + 35));
                        ourIntent.putExtra("sections_" + 36,extras.getStringArrayList("sections_" + 36));
                        ourIntent.putExtra("sections_" + 37,extras.getStringArrayList("sections_" + 37));
                        ourIntent.putExtra("sections_" + 38,extras.getStringArrayList("sections_" + 38));
                        ourIntent.putExtra("sections_" + 39,extras.getStringArrayList("sections_" + 39));
                        ourIntent.putExtra("sections_" + 40,extras.getStringArrayList("sections_" + 40));
                        shPrefEditor.putInt("h&rChapId", chapter);
                        shPrefEditor.putInt("h&rBookId", book);
                        shPrefEditor.commit();
                        finish();
                        startActivity(ourIntent);
                        break;
                    case 1:/*about*/
                        try {
                             ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
                             ourIntent = new Intent(myAudio.this, ourClass);
                            finish();
                            startActivity(ourIntent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 2:/*Feedback*/
                        try {
                             ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
                             ourIntent = new Intent(myAudio.this, ourClass);
                            finish();
                            startActivity(ourIntent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:/*Explanation for Search*/
                        try {
                             ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
                             ourIntent = new Intent(myAudio.this, ourClass);
                            finish();
                            startActivity(ourIntent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:/*acronyms*/
                        acronymsDecode();
                        break;
                    case 5:/*increase text*/
                        if (fontSize <= 47) {
                            fontSize += 3;
                            webSettings.setDefaultFontSize(fontSize);
                            shPrefEditor.putInt("fontSize", fontSize);
                            shPrefEditor.commit();
                            switch (MyLanguage) {
                                case ENGLISH:
                                    Toast.makeText(getApplicationContext(), "Font size - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case RUSSIAN:
                                    Toast.makeText(getApplicationContext(), "Размер шрифта - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case SPANISH:
                                    Toast.makeText(getApplicationContext(), "Tamaño de fuente - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case FRENCH:
                                    Toast.makeText(getApplicationContext(), "Taille de police - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "גודל גופן - " + fontSize, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            switch (MyLanguage) {
                                case ENGLISH:
                                    Toast.makeText(getApplicationContext(), "Maximum font size - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case RUSSIAN:
                                    Toast.makeText(getApplicationContext(), "Максимальный размер шрифта - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case SPANISH:
                                    Toast.makeText(getApplicationContext(), "Tamaño máximo de la fuente - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case FRENCH:
                                    Toast.makeText(getApplicationContext(), "Taille maximale de la police - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case 6:/*decrease text*/
                        if (fontSize >= 10)
                        {
                            fontSize -= 3;
                            webSettings.setDefaultFontSize(fontSize);
                            shPrefEditor.putInt("fontSize", fontSize);
                            shPrefEditor.commit();
                            switch (MyLanguage) {
                                case ENGLISH:
                                    Toast.makeText(getApplicationContext(), "Font size - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case RUSSIAN:
                                    Toast.makeText(getApplicationContext(), "Размер шрифта - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case SPANISH:
                                    Toast.makeText(getApplicationContext(), "Tamaño de fuente - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case FRENCH:
                                    Toast.makeText(getApplicationContext(), "Taille de police - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "גודל גופן - " + fontSize, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            switch (MyLanguage) {
                                case ENGLISH:
                                    Toast.makeText(getApplicationContext(), "Minimum font size - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case RUSSIAN:
                                    Toast.makeText(getApplicationContext(), "Минимальный размер шрифта - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case SPANISH:
                                    Toast.makeText(getApplicationContext(), "Tamaño mínimo de fuente - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                case FRENCH:
                                    Toast.makeText(getApplicationContext(), "Taille de police minimale - " + fontSize, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "גודל גופן מינימלי", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }//showPopupMenuSettings

    void acronymsDecode()
    {
        final Context context = this;
        // custom dialog
        acronymsDialog = new Dialog(context);
        acronymsDialog.setContentView(R.layout.acronyms);
        acronymsDialog.setTitle("פענוח ראשי תיבות");
        Button dialogButtonExit = (Button) acronymsDialog.findViewById(R.id.dialogButtonExit);
        Button dialogButtonDecode = (Button) acronymsDialog.findViewById(R.id.dialogButtonDecode);
        final TextView decodedText = (TextView) acronymsDialog.findViewById(R.id.textViewDecodedText);
        //final byte[] buffer;
        //final int size;
        TextToDecode = (EditText) acronymsDialog.findViewById(R.id.editTextAcronyms);
        // if button is clicked
        dialogButtonExit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                acronymsDialog.dismiss();
            }
        });

        dialogButtonDecode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                acronymsText = "\r\n" + /*"י\"א" */TextToDecode.getText().toString() + " - ";
                acronymsText = acronymsText.replace("\"", "");
                acronymsText = acronymsText.replace("'", "");
                InputStream is;
                String r = "לא נמצאו תוצאות";
                int index = 0, index_end = 0, first = 1;
                try {
                    is = getAssets().open("acronyms.txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String strText = new String(buffer);

                    while (strText.indexOf(acronymsText, index_end) != -1) {
                        index = strText.indexOf(acronymsText, index);
                        index = strText.indexOf("-", index + 1) + 2;
                        index_end = strText.indexOf("\r\n", index);
                        if (first == 1) {
                            r = strText.substring(index, index_end);
                            first = 0;
                        } else
                            r += ", " + strText.substring(index, index_end);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                decodedText.setText(TextToDecode.getText().toString() + " - " + r);

            }
        });
        acronymsDialog.show();
    }

    public void addItemsOnSpinner()
    {
        List<String> list = new ArrayList<String>();
        int i, index = 0, index_end = 0;

        Bookmarks = mPrefs.getString("Bookmarks", "");
        list.add("");/*this is for the first item that need to be hidden in order to have the ability to choose the first item*/

        while ((index = Bookmarks.indexOf(",", index)) != -1) {
            index++;
            index_end = Bookmarks.indexOf(",", index);
            list.add(Bookmarks.substring(index, index_end));
            for (i = 0; i < 4; i++)/*skip all other fields*/
                index = Bookmarks.indexOf(",", index) + 1;
        }

        int hidingItemIndex = 0;
        CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, list, hidingItemIndex);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddMark.setAdapter(dataAdapter);
    }

    void innerSearch()
    {
        final Context context = this;

        // custom dialog
        innerSearchDialog = new Dialog(context);
        innerSearchDialog.setContentView(R.layout.inner_search);
        Window window = innerSearchDialog.getWindow();
        window.setGravity(Gravity.TOP);
        ImageView dialogButton = innerSearchDialog.findViewById(R.id.goSearch);
        ImageView clearBtn = innerSearchDialog.findViewById(R.id.clear);
        TextToSearch = (EditText) innerSearchDialog.findViewById(R.id.title);
        TextToSearch.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.

                                innerSearchText = TextToSearch.getText().toString();

                                innerSearchDialog.dismiss();
                                if(API < 16)
                                {
                                    int a=webview.findAll(/*"כל"*/innerSearchText);
                                    /*to highlight the searched text*/
                                    try
                                    {
                                        Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
                                        m.invoke(webview, true);
                                    }
                                    catch (Throwable ignored){}
                                }
                                else
                                {
                                    webview.findAllAsync(/*"כל"*/innerSearchText);
                                }

                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
        // if button is clicked
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                innerSearchText = TextToSearch.getText().toString();

                innerSearchDialog.dismiss();
                //lnrFindOptions.setVisibility(View.VISIBLE);

                if (API < 16) {
                    int a = webview.findAll(/*"כל"*/innerSearchText);
                    /*to highlight the searched text*/
                    try {
                        Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
                        m.invoke(webview, true);
                    } catch (Throwable ignored) {
                    }
                } else {
                    webview.findAllAsync(/*"כל"*/innerSearchText);
                }
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                TextToSearch.setText("");
            }
        });
        innerSearchDialog.show();
    }

    private void fillChaptersNames()
    {
        /*BRACHOT*/
        chaptersNames[BRACHOT][1] = "ברכות: א - פתיחה";
        chaptersNames[BRACHOT][2] = "ברכות: ב - נטילת ידיים לסעודה";
        chaptersNames[BRACHOT][3] = "ברכות: ג - ברכת המוציא";
        chaptersNames[BRACHOT][4] = "ברכות: ד - ברכת המזון";
        chaptersNames[BRACHOT][5] = "ברכות: ה - זימון";
        chaptersNames[BRACHOT][6] = "ברכות: ו - חמשת מיני דגן";
        chaptersNames[BRACHOT][7] = "ברכות: ז - ברכת היין";
        chaptersNames[BRACHOT][8] = "ברכות: ח - ברכת הפירות ושהכל";
        chaptersNames[BRACHOT][9] = "ברכות: ט - כללי ברכה ראשונה";
        chaptersNames[BRACHOT][10] = "ברכות: י - ברכה אחרונה";
        chaptersNames[BRACHOT][11] = "ברכות: יא - עיקר וטפל";
        chaptersNames[BRACHOT][12] = "ברכות: יב - כללי ברכות";
        chaptersNames[BRACHOT][13] = "ברכות: יג - דרך ארץ";
        chaptersNames[BRACHOT][14] = "ברכות: יד - ברכת הריח";
        chaptersNames[BRACHOT][15] = "ברכות: טו - ברכות הראייה";
        chaptersNames[BRACHOT][16] = "ברכות: טז - ברכת הגומל";
        chaptersNames[BRACHOT][17] = "ברכות: יז - ברכות ההודאה והשמחה";
        chaptersNames[BRACHOT][18] = "ברכות: יח - תפילת הדרך";
        /*HAAMVEHAAREZ*/
        chaptersNames[HAAMVEHAAREZ][1] = "העם והארץ: א - מעלת הארץ";
        chaptersNames[HAAMVEHAAREZ][2] = "העם והארץ: ב - קודש וחול ביישוב הארץ";
        chaptersNames[HAAMVEHAAREZ][3] = "העם והארץ: ג - מצוות יישוב הארץ";
        chaptersNames[HAAMVEHAAREZ][4] = "העם והארץ: ד - מהלכות צבא ומלחמה";
        chaptersNames[HAAMVEHAAREZ][5] = "העם והארץ: ה - שמירת הארץ";
        chaptersNames[HAAMVEHAAREZ][6] = "העם והארץ: ו - מהלכות מדינה";
        chaptersNames[HAAMVEHAAREZ][7] = "העם והארץ: ז - ערבות הדדית";
        chaptersNames[HAAMVEHAAREZ][8] = "העם והארץ: ח - עבודה עברית";
        chaptersNames[HAAMVEHAAREZ][9] = "העם והארץ: ט - זכר למקדש";
        chaptersNames[HAAMVEHAAREZ][10] = "העם והארץ: י - הלכות גרים";
        chaptersNames[HAAMVEHAAREZ][11] = "העם והארץ: יא - נספח: תשובות מאת הרב גורן ומרבנים נוספים";
        /*ZMANIM*/
        chaptersNames[ZMANIM][1] = "זמנים: א - ראש חודש";
        chaptersNames[ZMANIM][2] = "זמנים: ב - הלכות ספירת העומר";
        chaptersNames[ZMANIM][3] = "זמנים: ג - מנהגי אבילות בספירת העומר";
        chaptersNames[ZMANIM][4] = "זמנים: ד - יום העצמאות";
        chaptersNames[ZMANIM][5] = "זמנים: ה - לג בעומר";
        chaptersNames[ZMANIM][6] = "זמנים: ו - ארבעת צומות החורבן";
        chaptersNames[ZMANIM][7] = "זמנים: ז - דיני הצומות הקלים";
        chaptersNames[ZMANIM][8] = "זמנים: ח - מנהגי שלושת השבועות";
        chaptersNames[ZMANIM][9] = "זמנים: ט - ערב תשעה באב";
        chaptersNames[ZMANIM][10] = "זמנים: י - הלכות תשעה באב";
        chaptersNames[ZMANIM][11] = "זמנים: יא - ימי החנוכה";
        chaptersNames[ZMANIM][12] = "זמנים: יב - הדלקת נרות חנוכה";
        chaptersNames[ZMANIM][13] = "זמנים: יג - דיני המקום והזמן";
        chaptersNames[ZMANIM][14] = "זמנים: יד - חודש אדר";
        chaptersNames[ZMANIM][15] = "זמנים: טו - פורים ומקרא מגילה";
        chaptersNames[ZMANIM][16] = "זמנים: טז - מצוות השמחה והחסד";
        chaptersNames[ZMANIM][17] = "זמנים: יז - דיני פרזים ומוקפים";
        /*TAHARAT*/
        chaptersNames[TAHARAT][1] = "טהרת המשפחה: א - טהרת המשפחה";
        chaptersNames[TAHARAT][2] = "טהרת המשפחה: ב - דם וכתם";
        chaptersNames[TAHARAT][3] = "טהרת המשפחה: ג - איסורי הרחקה";
        chaptersNames[TAHARAT][4] = "טהרת המשפחה: ד - שבעה נקיים";
        chaptersNames[TAHARAT][5] = "טהרת המשפחה: ה - טבילת טהרה";
        chaptersNames[TAHARAT][6] = "טהרת המשפחה: ו - פרישה ווסתות";
        chaptersNames[TAHARAT][7] = "טהרת המשפחה: ז - שאלת חכם ובדיקה רפואית";
        chaptersNames[TAHARAT][8] = "טהרת המשפחה: ח - כלה";
        chaptersNames[TAHARAT][9] = "טהרת המשפחה: ט - יולדת";
        chaptersNames[TAHARAT][10] = "טהרת המשפחה: י - מקוואות";
        /*YAMIM*/
        chaptersNames[YAMIM][1] = "ימים נוראים: א - הדין השכר והעונש";
        chaptersNames[YAMIM][2] = "ימים נוראים: ב - סליחות ותפילות";
        chaptersNames[YAMIM][3] = "ימים נוראים: ג - ראש השנה";
        chaptersNames[YAMIM][4] = "ימים נוראים: ד - מצוות השופר";
        chaptersNames[YAMIM][5] = "ימים נוראים: ה - עשרת ימי תשובה";
        chaptersNames[YAMIM][6] = "ימים נוראים: ו - יום הכיפורים";
        chaptersNames[YAMIM][7] = "ימים נוראים: ז - הלכות יום הכיפורים";
        chaptersNames[YAMIM][8] = "ימים נוראים: ח - דיני התענית";
        chaptersNames[YAMIM][9] = "ימים נוראים: ט - שאר עינויים";
        chaptersNames[YAMIM][10] = "ימים נוראים: י - עבודת יום הכיפורים";
        /*KASHRUT_A*/
        chaptersNames[KASHRUT_A][1] = "כשרות א: א - חדש";
        chaptersNames[KASHRUT_A][2] = "כשרות א: ב - ערלה ורבעי";
        chaptersNames[KASHRUT_A][3] = "כשרות א: ג - כלאי בהמה ואילן";
        chaptersNames[KASHRUT_A][4] = "כשרות א: ד - כלאי זרעים";
        chaptersNames[KASHRUT_A][5] = "כשרות א: ה - כלאי הכרם";
        chaptersNames[KASHRUT_A][6] = "כשרות א: ו - מתנות עניים";
        chaptersNames[KASHRUT_A][7] = "כשרות א: ז - תרומות ומעשרות";
        chaptersNames[KASHRUT_A][8] = "כשרות א: ח - החייב והפטור";
        chaptersNames[KASHRUT_A][9] = "כשרות א: ט - כללי המצווה";
        chaptersNames[KASHRUT_A][10] = "כשרות א: י - סדר ההפרשה למעשה";
        chaptersNames[KASHRUT_A][11] = "כשרות א: יא - חלה";
        chaptersNames[KASHRUT_A][12] = "כשרות א: יב - מצוות התלויות בארץ";
        chaptersNames[KASHRUT_A][13] = "כשרות א: יג - עצי פרי ובל תשחית";
        chaptersNames[KASHRUT_A][14] = "כשרות א: יד - אכילת בשר";
        chaptersNames[KASHRUT_A][15] = "כשרות א: טו - צער בעלי חיים";
        chaptersNames[KASHRUT_A][16] = "כשרות א: טז - שילוח הקן";
        chaptersNames[KASHRUT_A][17] = "כשרות א: יז - כשרות בעלי חיים";
        chaptersNames[KASHRUT_A][18] = "כשרות א: יח - הלכות שחיטה";
        chaptersNames[KASHRUT_A][19] = "כשרות א: יט - מתנות כהונה מהחי";
        /*KASHRUT_B*/
        chaptersNames[KASHRUT_B][1] = "כשרות ב: כ - טריפות";
        chaptersNames[KASHRUT_B][2] = "כשרות ב: כא - חֵלֶב וגיד הנשה וניקור";
        chaptersNames[KASHRUT_B][3] = "כשרות ב: כב - דם והכשרת הבשר";
        chaptersNames[KASHRUT_B][4] = "כשרות ב: כג - שרצים";
        chaptersNames[KASHRUT_B][5] = "כשרות ב: כד - מזון מהחי";
        chaptersNames[KASHRUT_B][6] = "כשרות ב: כה - בשר בחלב";
        chaptersNames[KASHRUT_B][7] = "כשרות ב: כו - דיני ההפסקה";
        chaptersNames[KASHRUT_B][8] = "כשרות ב: כז - הגזירות על מאכלי גויים";
        chaptersNames[KASHRUT_B][9] = "כשרות ב: כח - פת ובישולי גויים";
        chaptersNames[KASHRUT_B][10] = "כשרות ב: כט - יין ומשקאות גויים";
        chaptersNames[KASHRUT_B][11] = "כשרות ב: ל - חלב ומוצריו";
        chaptersNames[KASHRUT_B][12] = "כשרות ב: לא - טבילת כלים";
        chaptersNames[KASHRUT_B][13] = "כשרות ב: לב - כללי הכשרת כלים";
        chaptersNames[KASHRUT_B][14] = "כשרות ב: לג - הכשרת כלים ומטבח";
        chaptersNames[KASHRUT_B][15] = "כשרות ב: לד - דיני תערובות";
        chaptersNames[KASHRUT_B][16] = "כשרות ב: לה - סוגי בליעות";
        chaptersNames[KASHRUT_B][17] = "כשרות ב: לו - סכנות";
        chaptersNames[KASHRUT_B][18] = "כשרות ב: לז - תעשיית המזון";
        chaptersNames[KASHRUT_B][19] = "כשרות ב: לח - נאמנות והשגחה";
        /*LIKUTIM_A*/
        chaptersNames[LIKUTIM_A][1] = "ליקוטים א: א - הלכות תלמוד תורה";
        chaptersNames[LIKUTIM_A][2] = "ליקוטים א: ב - החינוך לתורה";
        chaptersNames[LIKUTIM_A][3] = "ליקוטים א: ג - קיום התורה והחינוך";
        chaptersNames[LIKUTIM_A][4] = "ליקוטים א: ד - הלכות ספר תורה";
        chaptersNames[LIKUTIM_A][5] = "ליקוטים א: ה - מהלכות קריאת התורה";
        chaptersNames[LIKUTIM_A][6] = "ליקוטים א: ו - כבוד ספר תורה ושמות קדושים";
        chaptersNames[LIKUTIM_A][7] = "ליקוטים א: ז - הלכות בית כנסת";
        chaptersNames[LIKUTIM_A][8] = "ליקוטים א: ח - כיפה";
        chaptersNames[LIKUTIM_A][9] = "ליקוטים א: ט - מהלכות ציצית";
        chaptersNames[LIKUTIM_A][10] = "ליקוטים א: י - מהלכות תפילין";
        chaptersNames[LIKUTIM_A][11] = "ליקוטים א: יא - מהלכות מזוזה";
        chaptersNames[LIKUTIM_A][12] = "ליקוטים א: יב - הלכות כהנים";
        chaptersNames[LIKUTIM_A][13] = "ליקוטים א: יג - שעטנז";
        /*LIKUTIM_B*/
        chaptersNames[LIKUTIM_B][1] = "ליקוטים ב: א - בין אדם לחברו";
        chaptersNames[LIKUTIM_B][2] = "ליקוטים ב: ב - הלכות אמירת אמת";
        chaptersNames[LIKUTIM_B][3] = "ליקוטים ב: ג - הלכות גניבת דעת";
        chaptersNames[LIKUTIM_B][4] = "ליקוטים ב: ד - הלכות גניבה";
        chaptersNames[LIKUTIM_B][5] = "ליקוטים ב: ה - מצוות הלוואה";
        chaptersNames[LIKUTIM_B][6] = "ליקוטים ב: ו - מהלכות צדקה";
        chaptersNames[LIKUTIM_B][7] = "ליקוטים ב: ז - הכנסת אורחים";
        chaptersNames[LIKUTIM_B][8] = "ליקוטים ב: ח - הלכות רוצח ומתאבד";
        chaptersNames[LIKUTIM_B][9] = "ליקוטים ב: ט - הלכות שמירת הנפש";
        chaptersNames[LIKUTIM_B][10] = "ליקוטים ב: י - נהיגה זהירה ותפילת הדרך";
        chaptersNames[LIKUTIM_B][11] = "ליקוטים ב: יא - הלכות הצלת נפשות";
        chaptersNames[LIKUTIM_B][12] = "ליקוטים ב: יב - הלכות ניתוחי מתים";
        chaptersNames[LIKUTIM_B][13] = "ליקוטים ב: יג - השתלת אברים";
        chaptersNames[LIKUTIM_B][14] = "ליקוטים ב: יד - הלכות הנוטה למות";
        chaptersNames[LIKUTIM_B][15] = "ליקוטים ב: טו - ליקוטים";
        chaptersNames[LIKUTIM_B][16] = "ליקוטים ב: טז - חברה ושליחות";
        /*MISHPACHA*/
        chaptersNames[MISHPACHA][1] = "משפחה: א - כיבוד הורים";
        chaptersNames[MISHPACHA][2] = "משפחה: ב - מצוות הנישואין";
        chaptersNames[MISHPACHA][3] = "משפחה: ג - שידוכים";
        chaptersNames[MISHPACHA][4] = "משפחה: ד - קידושין וכתובה";
        chaptersNames[MISHPACHA][5] = "משפחה: ה - החתונה ומנהגיה";
        chaptersNames[MISHPACHA][6] = "משפחה: ו - איסורי עריות";
        chaptersNames[MISHPACHA][7] = "משפחה: ז - מהלכות צניעות";
        chaptersNames[MISHPACHA][8] = "משפחה: ח - ברית מילה";
        chaptersNames[MISHPACHA][9] = "משפחה: ט - פדיון הבן";
        chaptersNames[MISHPACHA][10] = "משפחה: י - אבלות";
        /*MOADIM*/
        chaptersNames[MOADIM][1] = "מועדים: א - פתיחה";
        chaptersNames[MOADIM][2] = "מועדים: ב - דיני עשה ביום טוב";
        chaptersNames[MOADIM][3] = "מועדים: ג - כללי המלאכות";
        chaptersNames[MOADIM][4] = "מועדים: ד - מלאכות המאכלים";
        chaptersNames[MOADIM][5] = "מועדים: ה - הבערה כיבוי וחשמל";
        chaptersNames[MOADIM][6] = "מועדים: ו - הוצאה ומוקצה";
        chaptersNames[MOADIM][7] = "מועדים: ז - מדיני יום טוב";
        chaptersNames[MOADIM][8] = "מועדים: ח - עירוב תבשילין";
        chaptersNames[MOADIM][9] = "מועדים: ט - יום טוב שני של גלויות";
        chaptersNames[MOADIM][10] = "מועדים: י - מצוות חול המועד";
        chaptersNames[MOADIM][11] = "מועדים: יא - מלאכת חול המועד";
        chaptersNames[MOADIM][12] = "מועדים: יב - היתרי עבודה במועד";
        chaptersNames[MOADIM][13] = "מועדים: יג - חג שבועות";
        /*SUCOT*/
        chaptersNames[SUCOT][1] = "סוכות: א - חג הסוכות";
        chaptersNames[SUCOT][2] = "סוכות: ב - הלכות סוכה";
        chaptersNames[SUCOT][3] = "סוכות: ג - ישיבה בסוכה";
        chaptersNames[SUCOT][4] = "סוכות: ד - ארבעת המינים";
        chaptersNames[SUCOT][5] = "סוכות: ה - נטילת לולב";
        chaptersNames[SUCOT][6] = "סוכות: ו - הושענא רבה";
        chaptersNames[SUCOT][7] = "סוכות: ז - שמיני עצרת";
        chaptersNames[SUCOT][8] = "סוכות: ח - הקהל";
        /*PESACH*/
        chaptersNames[PESACH][1] = "פסח: א - משמעות החג";
        chaptersNames[PESACH][2] = "פסח: ב - כללי איסור חמץ";
        chaptersNames[PESACH][3] = "פסח: ג - מצוות השבתת חמץ";
        chaptersNames[PESACH][4] = "פסח: ד - בדיקת חמץ";
        chaptersNames[PESACH][5] = "פסח: ה - ביטול חמץ וביעורו";
        chaptersNames[PESACH][6] = "פסח: ו - מכירת חמץ";
        chaptersNames[PESACH][7] = "פסח: ז - תערובת חמץ";
        chaptersNames[PESACH][8] = "פסח: ח - מהלכות כשרות לפסח";
        chaptersNames[PESACH][9] = "פסח: ט - מנהג איסור קטניות";
        chaptersNames[PESACH][10] = "פסח: י - כללי הגעלת כלים";
        chaptersNames[PESACH][11] = "פסח: יא - הכשרת המטבח לפסח";
        chaptersNames[PESACH][12] = "פסח: יב - הלכות מצה";
        chaptersNames[PESACH][13] = "פסח: יג - הלכות ערב פסח ומנהגיו";
        chaptersNames[PESACH][14] = "פסח: יד - ערב פסח שחל בשבת";
        chaptersNames[PESACH][15] = "פסח: טו - ההגדה";
        chaptersNames[PESACH][16] = "פסח: טז - ליל הסדר";
        /*SHVIIT*/
        chaptersNames[SHVIIT][1] = "שביעית: א - מצוות השביעית";
        chaptersNames[SHVIIT][2] = "שביעית: ב - מצוות השביתה";
        chaptersNames[SHVIIT][3] = "שביעית: ג - השמטת הפירות";
        chaptersNames[SHVIIT][4] = "שביעית: ד - פירות השביעית";
        chaptersNames[SHVIIT][5] = "שביעית: ה - הזמן המקום והאדם";
        chaptersNames[SHVIIT][6] = "שביעית: ו - שמיטת כספים";
        chaptersNames[SHVIIT][7] = "שביעית: ז - היתר המכירה";
        chaptersNames[SHVIIT][8] = "שביעית: ח - אוצר בית דין";
        chaptersNames[SHVIIT][9] = "שביעית: ט - קניית פירות בשביעית";
        chaptersNames[SHVIIT][10] = "שביעית: י - מצוות היובל";
        chaptersNames[SHVIIT][11] = "שביעית: יא - חזון השביעית";
        /*SHABAT*/
        chaptersNames[SHABAT][1] = "שבת: א - פתיחה";
        chaptersNames[SHABAT][2] = "שבת: ב - הכנות לשבת";
        chaptersNames[SHABAT][3] = "שבת: ג - זמני השבת";
        chaptersNames[SHABAT][4] = "שבת: ד - הדלקת נרות שבת";
        chaptersNames[SHABAT][5] = "שבת: ה - תורה ותפילה בשבת";
        chaptersNames[SHABAT][6] = "שבת: ו - הלכות קידוש";
        chaptersNames[SHABAT][7] = "שבת: ז - סעודות השבת ומלווה מלכה";
        chaptersNames[SHABAT][8] = "שבת: ח - הבדלה ומוצאי שבת";
        chaptersNames[SHABAT][9] = "שבת: ט - כללי המלאכות";
        chaptersNames[SHABAT][10] = "שבת: י - בישול";
        chaptersNames[SHABAT][11] = "שבת: יא - בורר";
        chaptersNames[SHABAT][12] = "שבת: יב - הכנת מאכלים";
        chaptersNames[SHABAT][13] = "שבת: יג - מלאכות הבגד";
        chaptersNames[SHABAT][14] = "שבת: יד - הטיפול בגוף";
        chaptersNames[SHABAT][15] = "שבת: טו - בונה סותר בבית וכלים";
        chaptersNames[SHABAT][16] = "שבת: טז - מבעיר ומכבה";
        chaptersNames[SHABAT][17] = "שבת: יז - חשמל ומכשיריו";
        chaptersNames[SHABAT][18] = "שבת: יח - כותב מוחק וצובע";
        chaptersNames[SHABAT][19] = "שבת: יט - מלאכות שבצומח";
        chaptersNames[SHABAT][20] = "שבת: כ - בעלי חיים";
        chaptersNames[SHABAT][21] = "שבת: כא - הלכות הוצאה";
        chaptersNames[SHABAT][22] = "שבת: כב - צביון השבת";
        chaptersNames[SHABAT][23] = "שבת: כג - מוקצה";
        chaptersNames[SHABAT][24] = "שבת: כד - דיני קטן";
        chaptersNames[SHABAT][25] = "שבת: כה - מלאכת גוי";
        chaptersNames[SHABAT][26] = "שבת: כו - מעשה שבת ולפני עיוור";
        chaptersNames[SHABAT][27] = "שבת: כז - פיקוח נפש וחולה";
        chaptersNames[SHABAT][28] = "שבת: כח - חולה שאינו מסוכן";
        chaptersNames[SHABAT][29] = "שבת: כט - עירובין";
        chaptersNames[SHABAT][30] = "שבת: ל - תחומי שבת";
        /*SIMCHAT*/
        chaptersNames[SIMCHAT][1] = "שמחת הבית וברכתו: א - מצוות עונה";
        chaptersNames[SIMCHAT][2] = "שמחת הבית וברכתו: ב - הלכות עונה";
        chaptersNames[SIMCHAT][3] = "שמחת הבית וברכתו: ג - קדושה וכוונה";
        chaptersNames[SIMCHAT][4] = "שמחת הבית וברכתו: ד - שמירת הברית";
        chaptersNames[SIMCHAT][5] = "שמחת הבית וברכתו: ה - פרו ורבו";
        chaptersNames[SIMCHAT][6] = "שמחת הבית וברכתו: ו - קשיים ועקרות";
        chaptersNames[SIMCHAT][7] = "שמחת הבית וברכתו: ז - סריס והשחתה";
        chaptersNames[SIMCHAT][8] = "שמחת הבית וברכתו: ח - נחמת חשוכי ילדים";
        chaptersNames[SIMCHAT][9] = "שמחת הבית וברכתו: ט - הפסקת הריון";
        chaptersNames[SIMCHAT][10] = "שמחת הבית וברכתו: י - האיש והאשה";
        /*TEFILA*/
        chaptersNames[TEFILA][1] = "תפילה: א - יסודות הלכות תפילה";
        chaptersNames[TEFILA][2] = "תפילה: ב - המניין";
        chaptersNames[TEFILA][3] = "תפילה: ג - מקום התפילה";
        chaptersNames[TEFILA][4] = "תפילה: ד - החזן וקדיש של אבלים";
        chaptersNames[TEFILA][5] = "תפילה: ה - הכנות לתפילה";
        chaptersNames[TEFILA][6] = "תפילה: ו - הנוסחים ומנהגי העדות";
        chaptersNames[TEFILA][7] = "תפילה: ז - השכמת הבוקר";
        chaptersNames[TEFILA][8] = "תפילה: ח - נטילת ידיים שחרית";
        chaptersNames[TEFILA][9] = "תפילה: ט - ברכות השחר";
        chaptersNames[TEFILA][10] = "תפילה: י - ברכת התורה";
        chaptersNames[TEFILA][11] = "תפילה: יא - זמן ק\"ש ותפילת שחרית";
        chaptersNames[TEFILA][12] = "תפילה: יב - לקראת תפילת שחרית";
        chaptersNames[TEFILA][13] = "תפילה: יג - סדר קרבנות";
        chaptersNames[TEFILA][14] = "תפילה: יד - פסוקי דזמרה";
        chaptersNames[TEFILA][15] = "תפילה: טו - קריאת שמע";
        chaptersNames[TEFILA][16] = "תפילה: טז - ברכות קריאת שמע";
        chaptersNames[TEFILA][17] = "תפילה: יז - תפילת עמידה";
        chaptersNames[TEFILA][18] = "תפילה: יח - טעויות הזכרות ושכחה";
        chaptersNames[TEFILA][19] = "תפילה: יט - חזרת הש\"ץ";
        chaptersNames[TEFILA][20] = "תפילה: כ - ברכת כהנים";
        chaptersNames[TEFILA][21] = "תפילה: כא - נפילת אפיים ותחנונים";
        chaptersNames[TEFILA][22] = "תפילה: כב - מדיני קריאת התורה";
        chaptersNames[TEFILA][23] = "תפילה: כג - סיום שחרית ודיני קדיש";
        chaptersNames[TEFILA][24] = "תפילה: כד - תפילת מנחה";
        chaptersNames[TEFILA][25] = "תפילה: כה - תפילת מעריב";
        chaptersNames[TEFILA][26] = "תפילה: כו - קריאת שמע על המיטה";
        /*TEFILAT_NASHIM*/
        chaptersNames[TEFILAT_NASHIM][1] = "תפילת נשים: א - יסודות הלכות תפילה";
        chaptersNames[TEFILAT_NASHIM][2] = "תפילת נשים: ב - מצוות תפילה לנשים";
        chaptersNames[TEFILAT_NASHIM][3] = "תפילת נשים: ג - טעמי מצוות הנשים";
        chaptersNames[TEFILAT_NASHIM][4] = "תפילת נשים: ד - השכמת הבוקר";
        chaptersNames[TEFILAT_NASHIM][5] = "תפילת נשים: ה - נטילת ידיים שחרית";
        chaptersNames[TEFILAT_NASHIM][6] = "תפילת נשים: ו - ברכות השחר";
        chaptersNames[TEFILAT_NASHIM][7] = "תפילת נשים: ז - ברכות התורה";
        chaptersNames[TEFILAT_NASHIM][8] = "תפילת נשים: ח - תפילת שחרית והדינים שלפניה";
        chaptersNames[TEFILAT_NASHIM][9] = "תפילת נשים: ט - הכנת הגוף";
        chaptersNames[TEFILAT_NASHIM][10] = "תפילת נשים: י - הכנת הנפש והלבוש";
        chaptersNames[TEFILAT_NASHIM][11] = "תפילת נשים: יא - מקום התפילה";
        chaptersNames[TEFILAT_NASHIM][12] = "תפילת נשים: יב - תפילת עמידה";
        chaptersNames[TEFILAT_NASHIM][13] = "תפילת נשים: יג - הזכרת גשמים ובקשתם";
        chaptersNames[TEFILAT_NASHIM][14] = "תפילת נשים: יד - כבוד התפילה";
        chaptersNames[TEFILAT_NASHIM][15] = "תפילת נשים: טו - קרבנות ופסוקי דזמרה";
        chaptersNames[TEFILAT_NASHIM][16] = "תפילת נשים: טז - קריאת שמע וברכותיה";
        chaptersNames[TEFILAT_NASHIM][17] = "תפילת נשים: יז - התפילות שלאחר עמידה";
        chaptersNames[TEFILAT_NASHIM][18] = "תפילת נשים: יח - מנחה וערכית";
        chaptersNames[TEFILAT_NASHIM][19] = "תפילת נשים: יט - קריאת שמע על המיטה";
        chaptersNames[TEFILAT_NASHIM][20] = "תפילת נשים: כ - מהלכות התפילה במניין";
        chaptersNames[TEFILAT_NASHIM][21] = "תפילת נשים: כא - מהלכות בית הכנסת";
        chaptersNames[TEFILAT_NASHIM][22] = "תפילת נשים: כב - תפילה וקידוש בשבת";
        chaptersNames[TEFILAT_NASHIM][23] = "תפילת נשים: כג - מהלכות חגים ומועדים";
        chaptersNames[TEFILAT_NASHIM][24] = "תפילת נשים: כד - נוסחי התפלה ומנהגי העדות";
        /*HAR_MOADIM*/
        chaptersNames[HAR_MOADIM][1] = "הר' מועדים: א - פתיחה";
        chaptersNames[HAR_MOADIM][2] = "הר' מועדים: ב - דיני עשה ביום טוב";
        chaptersNames[HAR_MOADIM][3] = "הר' מועדים: ג - כללי המלאכות";
        chaptersNames[HAR_MOADIM][4] = "הר' מועדים: ד - מלאכות המאכלים";
        chaptersNames[HAR_MOADIM][5] = "הר' מועדים: ה - הבערה כיבוי וחשמל";
        chaptersNames[HAR_MOADIM][6] = "הר' מועדים: ו - הוצאה ומוקצה";
        chaptersNames[HAR_MOADIM][7] = "הר' מועדים: ז - מדיני יום טוב";
        chaptersNames[HAR_MOADIM][8] = "הר' מועדים: ח - עירוב תבשילין";
        chaptersNames[HAR_MOADIM][9] = "הר' מועדים: ט - יום טוב שני של גלויות";
        chaptersNames[HAR_MOADIM][10] = "הר' מועדים: י - מצוות חול המועד";
        chaptersNames[HAR_MOADIM][11] = "הר' מועדים: יא - מלאכת חול המועד";
        chaptersNames[HAR_MOADIM][12] = "הר' מועדים: יב - היתרי עבודה במועד";
        /*HAR_SUCOT*/
        chaptersNames[HAR_SUCOT][1] = "א -חג הסוכות";
        chaptersNames[HAR_SUCOT][2] = "ב - הלכות סוכה";
        chaptersNames[HAR_SUCOT][3] = "ג - ישיבה בסוכה";
        chaptersNames[HAR_SUCOT][4] = "ד - ארבעת המינים";
        chaptersNames[HAR_SUCOT][5] = "ה - נטילת לולב";
        chaptersNames[HAR_SUCOT][6] = "ו - הושענא רבה";
        chaptersNames[HAR_SUCOT][7] = "ז - שמיני עצרת";
        chaptersNames[HAR_SUCOT][8] = "ח - הקהל";
        /*HAR_SHABAT*/
        chaptersNames[HAR_SHABAT][1] = "הר' שבת: א - פתיחה";
        chaptersNames[HAR_SHABAT][2] = "הר' שבת: ב - הכנות לשבת";
        chaptersNames[HAR_SHABAT][3] = "הר' שבת: ג - זמני השבת";
        chaptersNames[HAR_SHABAT][4] = "הר' שבת: ד - הדלקת נרות שבת";
        chaptersNames[HAR_SHABAT][5] = "הר' שבת: ה - תורה ותפילה בשבת";
        chaptersNames[HAR_SHABAT][6] = "הר' שבת: ו - הלכות קידוש";
        chaptersNames[HAR_SHABAT][7] = "הר' שבת: ז - סעודות השבת ומלווה מלכה";
        chaptersNames[HAR_SHABAT][8] = "הר' שבת: ח - הבדלה ומוצאי שבת";
        chaptersNames[HAR_SHABAT][9] = "הר' שבת: ט - כללי המלאכות";
        chaptersNames[HAR_SHABAT][10] = "הר' שבת: י - בישול";
        chaptersNames[HAR_SHABAT][11] = "הר' שבת: יא - בורר";
        chaptersNames[HAR_SHABAT][12] = "הר' שבת: יב - הכנת מאכלים";
        chaptersNames[HAR_SHABAT][13] = "הר' שבת: יג - מלאכות הבגד";
        chaptersNames[HAR_SHABAT][14] = "הר' שבת: יד - הטיפול בגוף";
        chaptersNames[HAR_SHABAT][15] = "הר' שבת: טו - בונה סותר בבית וכלים";
        chaptersNames[HAR_SHABAT][16] = "הר' שבת: טז - מבעיר ומכבה";
        chaptersNames[HAR_SHABAT][17] = "הר' שבת: יז - חשמל ומכשיריו";
        chaptersNames[HAR_SHABAT][18] = "הר' שבת: יח - כותב מוחק וצובע";
        chaptersNames[HAR_SHABAT][19] = "הר' שבת: יט - מלאכות שבצומח";
        chaptersNames[HAR_SHABAT][20] = "הר' שבת: כ - בעלי חיים";
        chaptersNames[HAR_SHABAT][21] = "הר' שבת: כא - הלכות הוצאה";
        chaptersNames[HAR_SHABAT][22] = "הר' שבת: כב - צביון השבת";
        chaptersNames[HAR_SHABAT][23] = "הר' שבת: כג - מוקצה";
        chaptersNames[HAR_SHABAT][24] = "הר' שבת: כד - דיני קטן";
        chaptersNames[HAR_SHABAT][25] = "הר' שבת: כה - מלאכת גוי";
        chaptersNames[HAR_SHABAT][26] = "הר' שבת: כו - מעשה שבת ולפני עיוור";
        chaptersNames[HAR_SHABAT][27] = "הר' שבת: כז - פיקוח נפש וחולה";
        chaptersNames[HAR_SHABAT][28] = "הר' שבת: כח - חולה שאינו מסוכן";
        chaptersNames[HAR_SHABAT][29] = "הר' שבת: כט - עירובין";
        chaptersNames[HAR_SHABAT][30] = "הר' שבת: ל - תחומי שבת";
        /*HAR_SIMCHAT*/
        chaptersNames[HAR_SIMCHAT][1] = "הר' שמחת: א - מצוות עונה";
        chaptersNames[HAR_SIMCHAT][2] = "הר' שמחת: ב - הלכות עונה";
        chaptersNames[HAR_SIMCHAT][3] = "הר' שמחת: ג - קדושה וכוונה";
        chaptersNames[HAR_SIMCHAT][4] = "הר' שמחת: ד - שמירת הברית";
        chaptersNames[HAR_SIMCHAT][5] = "הר' שמחת: ה - פרו ורבו";
        chaptersNames[HAR_SIMCHAT][6] = "הר' שמחת: ו - קשיים ועקרות";
        chaptersNames[HAR_SIMCHAT][7] = "הר' שמחת: ז - סריס והשחתה";
        chaptersNames[HAR_SIMCHAT][8] = "הר' שמחת: ח - נחמת חשוכי ילדים";
        chaptersNames[HAR_SIMCHAT][9] = "הר' שמחת: ט - הפסקת הריון";
        chaptersNames[HAR_SIMCHAT][10] = "הר' שמחת: י - האיש והאשה";
        /*HAR_BRACHOT*/
        chaptersNames[HAR_BRACHOT][1] = "הר' ברכות: א - פתיחה";
        chaptersNames[HAR_BRACHOT][2] = "הר' ברכות: ב - נטילת ידיים לסעודה";
        chaptersNames[HAR_BRACHOT][3] = "הר' ברכות: ג - ברכת המוציא";
        chaptersNames[HAR_BRACHOT][4] = "הר' ברכות: ד - ברכת המזון";
        chaptersNames[HAR_BRACHOT][5] = "הר' ברכות: ה - זימון";
        chaptersNames[HAR_BRACHOT][6] = "הר' ברכות: ו - חמשת מיני דגן";
        chaptersNames[HAR_BRACHOT][7] = "הר' ברכות: ז - ברכת היין";
        chaptersNames[HAR_BRACHOT][8] = "הר' ברכות: ח - ברכת הפירות ושהכל";
        chaptersNames[HAR_BRACHOT][9] = "הר' ברכות: ט - כללי ברכה ראשונה";
        chaptersNames[HAR_BRACHOT][10] = "הר' ברכות: י - ברכה אחרונה";
        chaptersNames[HAR_BRACHOT][11] = "הר' ברכות: יא - עיקר וטפל";
        chaptersNames[HAR_BRACHOT][12] = "הר' ברכות: יב - כללי ברכות";
        chaptersNames[HAR_BRACHOT][13] = "הר' ברכות: יג - דרך ארץ";
        chaptersNames[HAR_BRACHOT][14] = "הר' ברכות: יד - ברכת הריח";
        chaptersNames[HAR_BRACHOT][15] = "הר' ברכות: טו - ברכות הראייה";
        chaptersNames[HAR_BRACHOT][16] = "הר' ברכות: טז - ברכת הגומל";
        chaptersNames[HAR_BRACHOT][17] = "הר' ברכות: יז - ברכות ההודאה והשמחה";
        /*HAR_YAMIM*/
        chaptersNames[HAR_YAMIM][1] = "הר' ימים נוראים: א - הדין השכר והעונש";
        chaptersNames[HAR_YAMIM][2] = "הר' ימים נוראים: ב - סליחות ותפילות";
        chaptersNames[HAR_YAMIM][3] = "הר' ימים נוראים: ג - ראש השנה";
        chaptersNames[HAR_YAMIM][4] = "הר' ימים נוראים: ד - מצוות השופר";
        chaptersNames[HAR_YAMIM][5] = "הר' ימים נוראים: ה - עשרת ימי תשובה";
        chaptersNames[HAR_YAMIM][6] = "הר' ימים נוראים: ו - יום הכיפורים";
        chaptersNames[HAR_YAMIM][7] = "הר' ימים נוראים: ז - הלכות יום הכיפורים";
        chaptersNames[HAR_YAMIM][8] = "הר' ימים נוראים: ח - דיני התענית";
        chaptersNames[HAR_YAMIM][9] = "הר' ימים נוראים: ט - שאר עינויים";
        chaptersNames[HAR_YAMIM][10] = "הר' ימים נוראים: י - עבודת יום הכיפורים";

        chaptersNames[R_TFILA][1]="Глава 1 Основы законов молитвы ";
        chaptersNames[R_TFILA][2]="Глава 2 Миньян";
        chaptersNames[R_TFILA][3]="Глава 3 Место молитвы";
        chaptersNames[R_TFILA][4]="Глава 4 Кантор и кадиш скорбящих";
        chaptersNames[R_TFILA][5]="Глава 5 Подготовка к молитве";
        chaptersNames[R_TFILA][6]="Глава 6 Молитвенные каноны и обычаи разных общин ";
        chaptersNames[R_TFILA][7]="Глава 7 Утреннее пробуждение";
        chaptersNames[R_TFILA][8]="Глава 8 Утреннее омовение рук";
        chaptersNames[R_TFILA][9]="Глава 9 Утренние благословения (Биркот ѓа-шахар)";
        chaptersNames[R_TFILA][10]="Глава 10 Благословения Торы (Биркот ѓа-Тора)";
        chaptersNames[R_TFILA][11]="Глава 11 Время чтения Шма Исраэль и утренней молитвы ( шахарит)";
        chaptersNames[R_TFILA][12]="Глава 12 Подготовка к утренней молитве";
        chaptersNames[R_TFILA][13]="Глава 13 Порядок жертвоприношений (Корбанот)";
        chaptersNames[R_TFILA][14]="Глава 14 Хвалебные гимны (Псукей де-зимра)";
        chaptersNames[R_TFILA][15]="Глава 15 Шма Исраэль ";
        chaptersNames[R_TFILA][16]="Глава 16 Благословения, сопровождающие чтение Шма Исраэль";
        chaptersNames[R_TFILA][17]="Глава 17 Молитва амида";
        chaptersNames[R_TFILA][18]="Глава 18 Ошибки при чтении молитвы амида";
        chaptersNames[R_TFILA][19]="Глава 19 Повторение кантором молитвы амида";
        chaptersNames[R_TFILA][20]="Глава 20 Благословение народа коѓенами (Биркат коѓаним)";
        chaptersNames[R_TFILA][21]="Глава 21 Таханун и Нефилат апаим";
        chaptersNames[R_TFILA][22]="Глава 22 Некоторые законы чтения Торы";
        chaptersNames[R_TFILA][23]="Глава 23 Окончание утренней молитвы и законы кадиша";
        chaptersNames[R_TFILA][24]="Глава 24 Минха - послеполуденная молитва ";
        chaptersNames[R_TFILA][25]="Глава 25 Арвит - вечерняя молитва ";
        chaptersNames[R_TFILA][26]="Глава 26 Чтение Шма Исраэль перед отходом ко сну";

    }

    public void setAudioId(String id) {
        audio_id = id;
    }

    private void sendSectionIdAndPlay(int selectedSection)
    {
        playing = 2;
        Intent broadcastIntent = new Intent(Broadcast_SKIP_TO_SPECIFIC_SECTION);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        broadcastIntent.putExtra("audio_id", selectedSection);
        int choice = spinner.getSelectedItemPosition();
        speed = sppedArray[choice];

        shPrefEditor.putFloat("audioSpeed", speed);
        shPrefEditor.commit();
        //shilo
        playerInfo.setText(book_name + " " + convert_character_to_id(chapter) + ", " + convert_character_to_id(selectedSection));
        section = selectedSection;
        sendBroadcast(broadcastIntent);
    }

    private void initializeSeekBar()
    {
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;
                    boolean mUserIsSeeking = false;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        Intent broadcastIntent = new Intent(Broadcast_OnTouch);
                        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
                        broadcastIntent.putExtra("seekbarProgress", userSelectedPosition);
                        sendBroadcast(broadcastIntent);
                    }
                });
    }

    private void registerAllBroadcast()
    {
        //register after getting audio focus
        int choice = spinner.getSelectedItemPosition();
        speed = sppedArray[choice];
        playerService.speed = speed;//prapre to set speed
        IntentFilter intentFilter = new IntentFilter(MediaPlayerService.Broadcast_SERVICE_SKIP_NEXT);
        registerReceiver(BRskipNext, intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                timeElapsedUpdates, new IntentFilter("timeElapsedUpdates"));
        LocalBroadcastManager.getInstance(this).registerReceiver(
                chapterUpdate, new IntentFilter("chapterUpdate"));
    }

    private BroadcastReceiver BRskipNext = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            skip_to_next(view);
        }
    };

    private BroadcastReceiver timeElapsedUpdates = new BroadcastReceiver()
    {
        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onReceive(Context context, Intent intent)
        {
            timeElapsed = intent.getDoubleExtra("timeElapsed", 0.0);
            finalTime = intent.getDoubleExtra("finalTime", 0.0);
            seekbar.setMax((int) finalTime);
            //set seekbar progress
            seekbar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            duration.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            duration2.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed), TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed))));
        }
    };

    private BroadcastReceiver chapterUpdate = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int oldChapter;
            oldChapter = chapter;
            chapter = intent.getIntExtra("chapter", 0);
            restartPage();
            for (int i = 0; i < 50; i++) {
                if (textViewArray[i] != null) {
                    if (mPrefs.getInt("BlackBackground", 0) == 1) {
                        textViewArray[i].setBackgroundColor(Color.BLACK);
                        textViewArray[i].setTextColor(Color.WHITE);
                    } else {
                        textViewArray[i].setBackgroundColor(Color.WHITE);
                        textViewArray[i].setTextColor(Color.BLACK);
                    }
                }
            }
//            if (chapter != oldChapter) {
//               v1[0].setBackgroundColor(Color.rgb(151, 6, 6));
//               v1[0].setTextColor(Color.WHITE);
//            }
            int a=0;
            if(team)
                a=1;
            if (textViewArray[playerService.getSection()] != null) {
                textViewArray[playerService.getSection() - 1].setBackgroundColor(Color.rgb(151, 6, 6));
                textViewArray[playerService.getSection() - 1].setTextColor(Color.WHITE);
                playerInfo.setText(book_name + " " + convert_character_to_id(playerService.getChapter()) + ", " + convert_character_to_id(playerService.getSection()));
            }
            clickOnItemFromList = false;
            //listview.performItemClick(listview.getAdapter().getView(section - 1, null, null), section - 1, section - 1);
        }
    };

    protected void onStart()
    {
        super.onStart();
        clickOnItemFromList = false;
        listview.performItemClick(listview.getAdapter().getView(section - 1, null, null), section - 1, section - 1);
        playAudioService();
    }

    private String get_book_name_by_id()
    {
        switch (book) {
            case BRACHOT:
                return "ברכות";
            case HAAMVEHAAREZ:
                return "העם והארץ";
            case ZMANIM:
                return "זמנים";
            case TAHARAT:
                return "טהרת המשפחה";
            case YAMIM:
                return "ימים נוראים";
            case KASHRUT_A:
                return "כשרות א";
            case KASHRUT_B:
                return "כשרות ב";
//            case LIKUTIM_A:
//                return "ליקוטים א";
//            case LIKUTIM_B:
//                return "ליקוטים ב";
            case MOADIM:
                return "מועדים";
            case MISHPACHA:
               return "משפחה";
            case SUCOT:
                return "סוכות";
            case PESACH:
                return "פסח";
            case SHVIIT:
                return "שביעית ויובל";
            case SHABAT:
                return "שבת";
            case SIMCHAT:
                return "שמחת הבית וברכתו";
            case TEFILA:
                return "תפילה";
            case R_TFILA:
                return "tfila(r)";
//            case TEFILAT_NASHIM:
//                return "תפילת נשים";
        }
        return "לא נמצא";
    }

    public void onDestroy()
    {
        super.onDestroy();
        //for not crash on rotate change if to true and change the plappusae icon
        if(true) {
            //LocalBroadcastManager.getInstance(this).unregisterReceiver(BRskipNext);
            //LocalBroadcastManager.getInstance(this).unregisterReceiver(timeElapsedUpdates);
            //LocalBroadcastManager.getInstance(this).unregisterReceiver(chapterUpdate);
            if (serviceBound) {
                unbindService(serviceConnection);
                //service is active
                stopService(playerIntent);
                //int a=0/0;
                //serviceBound=false;
            }
            //serviceBound=false;
        }
    }

    public void initializeViews()
    {
        buttonPlayPause = (ImageButton) findViewById(R.id.media_play_pause);
        duration = (TextView) findViewById(R.id.audioDuration);
        duration2 = (TextView) findViewById(R.id.audioDuration2);
        //bufferingPercent = (TextView) findViewById(R.id.fileBuffering);
        seekbar = (SeekBar) findViewById(R.id.seekBar6);
        playerIntent = new Intent(this, MediaPlayerService.class);
    }

    //playing = 0-pause, 1-play, 2-skip
    public void playPause(View view)
    {
        Intent broadcastIntent;
        if (playing == 0)//if pause change button icon to play
        {
            playing = 1;
            buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);

            broadcastIntent = new Intent(Broadcast_PLAY_PAUSE);
            broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
            sendBroadcast(broadcastIntent);
        } else if (playing == 1)//if play change button icon to pause
        {
            playing = 0;
            buttonPlayPause.setImageResource(R.drawable.baseline_play_circle_outline_white_48);
            broadcastIntent = new Intent(Broadcast_PLAY_PAUSE);
            broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
            sendBroadcast(broadcastIntent);
        } else if (playing == 2)//if skip next change button icon to pause
        {
            buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
            playing = 1;
        }
    }

    public void skip_to_next(View view)
    {
        playing = 2;
        Intent broadcastIntent = new Intent(Broadcast_SKIP_NEXT);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        sendBroadcast(broadcastIntent);
        broadcastIntent = new Intent(Broadcast_Speed);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        int choice = spinner.getSelectedItemPosition();
        speed = sppedArray[choice];
        shPrefEditor.putFloat("audioSpeed", speed);
        shPrefEditor.commit();

        if (playing == 0)
            broadcastIntent.putExtra("play", 0);
        sendBroadcast(broadcastIntent);
        section=playerService.getSection();
        /*if(section>3){
            listview.setSelected(true);
            listview.setSelection(section);
        }*/
    }

    public void restartPage()
    {
        List<TextView> txtvList = new ArrayList<TextView>();
        TextView textView = new TextView(getBaseContext());
        header = book_name + convert_character_to_id(chapter) + ", א";
        playerInfo.setText(header);
        // TODO: fill the list of sections of the new chapter
        sections = extras.getStringArrayList("sections_" + playerService.getChapter());
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        boolean first=true;
        ArrayAdapter adapter;
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aList);

        for (int m = 0; m < textViewArray.length; m++) {
             listview.removeHeaderView(textViewArray[m]);
        }

        for (int m = listview.getFooterViewsCount()-1; m >= 0; m--)
            listview.removeFooterView(listview.getChildAt(0));
        textViewArray =new TextView[50];
        //while (listview.getHeaderViewsCount()>0)
           // listview.removeHeaderView(listview.getChildAt(0));
        for (int i = 0; i < sections.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", sections.get(i));
            //aList.add(hm);
            textView = new TextView(getBaseContext());
            //String sourceString = "<b>" + "[" + chapterCounter + "] " + chaptersNames[i][j] + "</b> " + sections;
            //String sourceString = "<b >"+ chaptersNames[i][j].split("-")[1] + "</b>("+ chaptersNames[i][j].split("-")[0]+","+ sections+")";
            textView.setText("  " + sections.get(i) + "\n");
            //textView.setText("shilo");
            //textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
            if (mPrefs.getInt("BlackBackground", 0) == 1) {
                textView.setTextColor(Color.WHITE);
                ImageView toMain= (ImageView) findViewById(R.id.to_main);
                if(MyLanguage==ENGLISH)
                    toMain.setImageResource(R.drawable.to_main_b_e);
                if(MyLanguage==RUSSIAN)
                    toMain.setImageResource(R.drawable.to_main_b_r);
                if(MyLanguage==SPANISH)
                    toMain.setImageResource(R.drawable.to_main_b_s);
                if(MyLanguage==FRENCH)
                    toMain.setImageResource(R.drawable.to_main_b_f);
                if(MyLanguage==HEBREW)
                    toMain.setImageResource(R.drawable.to_main_b);
                LinearLayout main=(LinearLayout) findViewById(R.id.lnrOption3);
                ImageView menu= (ImageView) findViewById(R.id.menu);
                menu.setImageResource(R.drawable.ic_action_congif_b);
                main.setBackgroundColor(Color.rgb(120,1,1));
                // listview.setBackgroundColor(Color.BLACK);
            } else
                textView.setTextColor(Color.BLACK);
            textView.setTextSize(24);
            if ((textView.getText().charAt(2) != 'א')&&first)
                continue;
            if ((textView.getText().charAt(2) == 'א')&&!first)
                break;
            if ((textView.getText().charAt(2) == 'א')&&first)
                first=false;

            txtvList.add(textView);
        }

        lastSec=txtvList.size();

        String[] from = {"listview_title"};
        int[] to = {R.id.listview_item_title};

        listview.setAdapter(adapter);

        if (mPrefs.getInt("BlackBackground", 0) == 1)
            listview.setBackgroundColor(Color.BLACK);
        else
            listview.setBackgroundColor(Color.WHITE);
        txtvList.get(0).setBackgroundColor(Color.rgb(120,1,1));
        for (int i = 0; i <txtvList.size(); i++) {
            listview.addHeaderView(txtvList.get(i));
            textViewArray[i]=txtvList.get(i);
        }

        listview.setCacheColorHint(Color.WHITE);
        //txtvList.get(0).setBackgroundColor(Color.BLUE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setSelected(true);
                listview.setSelection(position);
                if (clickOnItemFromList == true) {
                    sendSectionIdAndPlay(position + 1);
                    for (int i = 0; i < txtvList.size(); i++)
                        if (i == position) {
                            txtvList.get(position).setBackgroundColor(Color.rgb(151, 6, 6));
                            txtvList.get(position).setTextColor(Color.WHITE);
                        }
                        else if (mPrefs.getInt("BlackBackground", 0) == 1) {
                            txtvList.get(i).setBackgroundColor(Color.BLACK);
                            txtvList.get(i).setTextColor(Color.WHITE);
                        }
                        else {
                            txtvList.get(i).setBackgroundColor(Color.WHITE);
                            txtvList.get(i).setTextColor(Color.BLACK);
                        }
                    ArrayAdapter adapter;
                    adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, aList);
                    listview = (ListView) findViewById(R.id.list_view);
                    listview.setAdapter(adapter);
                    listview.setBackgroundColor(Color.WHITE);
                    listview.setSelection(position);
                    playing = 1;
                    buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
                    //for (int i=0;i<txtvList.size();i++)
                    //  listview.addHeaderView(txtvList.get(i));
                }

                //playPause(view);
                clickOnItemFromList = true;//change it back to true. In cases that it is not came directly from click om list item, it will be changed to false in this cases
            }
        });
    }

    public void skip_to_previous(View view)
    {
        playing = 2;
        buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
        Intent broadcastIntent = new Intent(Broadcast_SKIP_PREVIOUS);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        sendBroadcast(broadcastIntent);
        broadcastIntent = new Intent(Broadcast_Speed);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        int choice = spinner.getSelectedItemPosition();
        speed = sppedArray[choice];
        shPrefEditor.putFloat("audioSpeed", speed);
        shPrefEditor.commit();
        broadcastIntent.putExtra("play", 0);
        sendBroadcast(broadcastIntent);
        section=playerService.getSection();
        listview.setSelected(true);
        listview.setSelection(section - 2);
        //playerInfo.setText(book_name + " " + convert_character_to_id(chapter) + ", " + convert_character_to_id(section ));
        //check if the user return from brachot it give him back to brachot א,א
        if (section + 1486 < 'א')
            playerInfo.setText(book_name + " " + convert_character_to_id(chapter) + ", א");
    }

    public void forward_10_sec(View view)
    {
        Intent broadcastIntent = new Intent(Broadcast_FORWARD_10);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        sendBroadcast(broadcastIntent);
    }

    public void rewind_10_sec(View view)
    {
        Intent broadcastIntent = new Intent(Broadcast_BACKWARD_10);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        sendBroadcast(broadcastIntent);
    }

    //indicate how much percent already buffered (downloaded)
/*    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        bufferingPercent.setText(String.format("טוען %d", percent));
    }*/

    public String convert_character_to_id(int Id)
    {
        if(book==R_TFILA)
            return String.valueOf(Id);
        switch (Id) {
            case 1:
                return "א";
            case 2:
                return "ב";
            case 3:
                return "ג";
            case 4:
                return "ד";
            case 5:
                return "ה";
            case 6:
                return "ו";
            case 7:
                return "ז";
            case 8:
                return "ח";
            case 9:
                return "ט";
            case 10:
                return "י";
            case 11:
                return "יא";
            case 12:
                return "יב";
            case 13:
                return "יג";
            case 14:
                return "יד";
            case 15:
                return "טו";
            case 16:
                return "טז";
            case 17:
                return "יז";
            case 18:
                return "יח";
            case 19:
                return "יט";
            case 20:
                return "כ";
            case 21:
                return "כא";
            case 22:
                return "כב";
            case 23:
                return "כג";
            case 24:
                return "כד";
            case 25:
                return "כה";
            case 26:
                return "כו";
            case 27:
                return "כז";
            case 28:
                return "כח";
            case 29:
                return "כט";
            case 30:
                return "ל";
            case 31:
                return "לא";
            case 32:
                return "לב";
            case 33:
                return "לג";
            case 34:
                return "לד";
            case 35:
                return "לה";
            case 36:
                return "לו";
            case 37:
                return "לז";
            case 38:
                return "לח";
            case 39:
                return "לט";
            case 40:
                return "מ";
            default:
                return "תת";
        }
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            playerService = binder.getService();
            serviceBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            serviceBound = false;
        }
    };

    private void playAudioService()
    {
        //Check is service is active
        if (!serviceBound) {
            playerIntent = new Intent(this, MediaPlayerService.class);
            playerIntent.putExtra("book_id", book);
            playerIntent.putExtra("chapter_id", chapter);
            playerIntent.putExtra("audio_id", section);

            for (int i = 1; i <= lastChapter[book]; i++)
            {
                String name;
                if (book == KASHRUT_B)
                    name = "sections_" + (i + 19);
                else
                    name = "sections_" + i;
                sections = extras.getStringArrayList(name);
                // Creating a new local copy of the current list.
                ArrayList<String> newList = new ArrayList<>(sections);
                playerIntent.putStringArrayListExtra(name, newList);
            }

            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        int choice = spinner.getSelectedItemPosition();
        broadcastIntent = new Intent(Broadcast_Speed);
        broadcastIntent.setPackage("com.rafraph.pnineyHalachaHashalem");
        shPrefEditor.putFloat("audioSpeed", sppedArray[choice]);
        shPrefEditor.commit();
        if (playing == 0) {
            shPrefEditor.putFloat("audioSpeed", speed);
            shPrefEditor.commit();
        }
        sendBroadcast(broadcastIntent);
    }

    // onNothingSelected must be here since this class implements AdapterView.OnItemSelectedListener
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}