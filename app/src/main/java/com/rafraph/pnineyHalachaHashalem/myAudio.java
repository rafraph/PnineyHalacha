package com.rafraph.pnineyHalachaHashalem;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;

import android.support.v4.content.LocalBroadcastManager;
import android.text.Layout;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.BroadcastReceiver;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class myAudio extends Activity implements AdapterView.OnItemSelectedListener {
    private static  int API ;
    static  float nimi=8;
    /*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  30*/
    public int[] lastChapter = {18, 11, 17, 10, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 26, 30};

    private static final int BRACHOT      	= 0;
    private static final int HAAMVEHAAREZ 	= 1;
    private static final int ZMANIM    		= 2;
    private static final int TAHARAT   		= 3;
    private static final int YAMIM    		= 4;
    private static final int KASHRUT_A 		= 5;
    private static final int KASHRUT_B 		= 6;
    private static final int LIKUTIM_A 		= 7;
    private static final int LIKUTIM_B 		= 8;
    private static final int MOADIM    		= 9;
    private static final int MISHPACHA   	= 10;
    private static final int SUCOT			= 11;
    private static final int PESACH			= 12;
    private static final int SHVIIT			= 13;
    private static final int SHABAT			= 14;
    private static final int SIMCHAT		= 15;
    private static final int TEFILA			= 16;
    private static final int TEFILAT_NASHIM	= 17;
    float speed;
    String fileName, fileNameOnly, lastFileName = null;
    public TextView duration,duration2, bufferingPercent;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 10000, backwardTime = 10000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    private int mediaFileLengthInMilliseconds;
    private char playing = 0;//0-not playing 1-playing
    ImageButton buttonPlayPause;
    ImageButton buttonPrevious;
    ImageButton buttonNext;
    private int book;
    private int chapter;
    private int section;
    ArrayList<String> sections;
    private String url;
    View view;
    Elements headers;
    Document doc = null;
    private Spinner spinner;
    private final Handler handler = new Handler();

    private String header;
    public ListView listview;
    public String book_name;
    public TextView txtI;
    Bundle extras;
    private MediaPlayerService playerService;
    boolean serviceBound = false;
    boolean firstCall;
    private Intent playerIntent;
    boolean clickOnItemFromList = false;
    public Bundle b=null;
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
    public  static Context context;
    public Boolean hearAndRead;
    void ParseTheDoc()
    {
        String prefix;
        InputStream is;
        int size;
        byte[] buffer;
        String input;

        fileName = getClearUrl();
        if ((fileName.equals(lastFileName) == false))
        {
            lastFileName = fileName;
            prefix = "file:///android_asset/";
            fileNameOnly = fileName.substring(prefix.length());
            try
            {
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
    private String getClearUrl()
    {
        String ClearUrl;
        ClearUrl = webview.getUrl();
        ClearUrl = ClearUrl.substring(0, ClearUrl.indexOf(".html")+5);
        return ClearUrl;
    }
    public void  setNoteId(String id)
    {
        note_id=id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        b=savedInstanceState;
        int count=0;
        super.onCreate(savedInstanceState);
        //PRAPRE TO READ AND LISTEN VIEW
        setContentView(R.layout.text_main_audio);
        context = this;
        extras = getIntent().getExtras();
        hearAndRead=extras.getBoolean("hearAndRead");
        //to swich to read and hear mode change here and 269+- to if(true)
        if (hearAndRead) {

            webview=(WebView)  findViewById(R.id.webView1);
            webSettings=webview.getSettings();
            webSettings.setMinimumFontSize(20);
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            API = android.os.Build.VERSION.SDK_INT;
            if(API < 19)
                webSettings.setBuiltInZoomControls(true);

            //resources = getResources();
            webview.requestFocusFromTouch();
            webview.getSettings().setAllowFileAccess(true);
            webview.loadUrl(getIntent().getStringExtra("webLink"));
            System.out.println(getClearUrl());

            //webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
            final Runnable runnableNote = new Runnable() {
                public void run() {
                    String note, content = null;
                    int intNoteId;
                    final Dialog dialog = new Dialog(context);
                    WebView webviewNote;
                    WebSettings webSettingsNote;
                    dialog.setContentView(R.layout.note);
                    intNoteId = Integer.parseInt(note_id)-1000;
                    note_id = Integer.toString(intNoteId);
                    dialog.setTitle("        הערה "+note_id);
                    webviewNote = (WebView) dialog.findViewById(R.id.webViewNote1);
                    webSettingsNote = webviewNote.getSettings();
                    webSettingsNote.setDefaultTextEncodingName("utf-8");
                    webviewNote.requestFocusFromTouch();
                    if(API < 19)
                        webSettingsNote.setBuiltInZoomControls(true);

                    content =  "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
                            "<html><head>"+
                            "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                            "<head>";
                    content += "<body>";//White background
                    ParseTheDoc();
                    headers = doc.select("div#ftn"+1);
                    note = headers.get(0).text();
                    //note = note.substring(6);//in order to remove the prefix of the note. something like [1]
                    content += "<p dir=\"RTL\">" + note + "</p> </body></html>";
                    webviewNote.loadData(content, "text/html; charset=utf-8", "UTF-8");
                    webSettingsNote.setDefaultFontSize(18);
                    dialog.show();

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                    {
                        @Override
                        public void onCancel(DialogInterface dialog)
                        {
                            //do whatever you want the back key to do
                            dialog.dismiss();

                        }
                    });
                }
            };
            final Runnable runnableAudio = new Runnable()
            {
                public void run()
                {
                    sendSectionIdAndPlay(Integer.parseInt(audio_id));
                }
            };
            webview.addJavascriptInterface(new Object()
            {
                @JavascriptInterface
                public <var> void performClick(String id)
                {
                    setNoteId(id);
                    runOnUiThread(runnableNote);


                }
            }, "ok");
            webview.addJavascriptInterface(new Object()
            {
                @JavascriptInterface
                public void performClick(String id)
                {

                    setAudioId(id);
                    runOnUiThread(runnableAudio);

                    playing=0;
                    playPause(view);


                }
            }, "audio");


        }
        else
            setContentView(R.layout.activity_audio);
        firstCall = true;
        registerAllBroadcast();
        initializeViews();
        txtI = (TextView) findViewById(R.id.txt1);
        extras = getIntent().getExtras();
        sections = new ArrayList<String>();
        book = extras.getInt("book_id");
        chapter = extras.getInt("chapter_id");
        if(hearAndRead) {
            int scroolYto = extras.getInt("scroolY");
            webview.scrollTo(0,scroolYto-90);
            int fontSize = extras.getInt("fontSize");
            webSettings.setMinimumFontSize(fontSize);
;
            //l1.setAlpha(0);

        }

        if(book == KASHRUT_B)//KASHRUT_B is starting from chapter 20
            chapter += 19;
        section = extras.getInt("audio_id");

        sections = extras.getStringArrayList("sections_"+chapter);

        book_name = get_book_name_by_id();
        txtI.setText(book_name + " " +   convert_character_to_id(chapter)+", "+convert_character_to_id(section));






        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < sections.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", sections.get(i));
            aList.add(hm);
        }

        String[] from = { "listview_title"};
        int[] to = {R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.audio_list, from, to);
        listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setSelected(true);
                listview.setSelection(position);
                if (clickOnItemFromList == true)
                    sendSectionIdAndPlay(position+1);
                playPause(view);
                clickOnItemFromList = true;//change it back to true. In cases that it is not came directly from click om list item, it will be changed to false in this cases
            }
        });
        buttonNext = (ImageButton)findViewById(R.id.media_next);
        buttonPrevious = (ImageButton)findViewById(R.id.media_prev);


        initializeSeekBar();
        while(playing == 1){
            try {
                wait(1000);
                Intent broadcastIntent = new Intent(Broadcast_FORWARD_10);
                sendBroadcast(broadcastIntent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        spinner=findViewById(R.id.myspinner);
        spinner.setSelected(true);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.speed_audio_array, android.R.layout.simple_list_item_1 );
        adapter.setDropDownViewResource((android.R.layout.simple_list_item_1));
        spinner.setAdapter(adapter);
        spinner.setSelection(2);

        spinner.setOnItemSelectedListener( this);
        Intent broadcastIntent = new Intent(Broadcast_SKIP_NEXT);
        sendBroadcast(broadcastIntent);
        broadcastIntent = new Intent(Broadcast_Speed);
        int choice=spinner.getSelectedItemPosition();
        if(choice==0)
            speed=2f;
        if(choice==1)
            speed=1.5f;
        if(choice==2)
            speed=1f;
        if(choice==3)
            speed=0.75f;
        broadcastIntent.putExtra("speed",speed);
        if (playing==0)
            broadcastIntent.putExtra("play",0);
        sendBroadcast(broadcastIntent);

    }
    public void  setAudioId(String id)
    {
        audio_id=id;
    }
    private void sendSectionIdAndPlay(int selectedSection)
    {
        playing = 2;
        Intent broadcastIntent = new Intent(Broadcast_SKIP_TO_SPECIFIC_SECTION);
        broadcastIntent.putExtra("audio_id", selectedSection);
        txtI.setText(book_name + " " +   convert_character_to_id(chapter)+", "+convert_character_to_id(selectedSection));
        section=selectedSection;
        sendBroadcast(broadcastIntent);
    }

    private void initializeSeekBar() {
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
                        broadcastIntent.putExtra("seekbarProgress", userSelectedPosition);
                        sendBroadcast(broadcastIntent);
                    }
                });
    }


    private void registerAllBroadcast() {
        //register after getting audio focus
        playerService.speed=speed;//prapre to set speed
        IntentFilter intentFilter = new IntentFilter(MediaPlayerService.Broadcast_SERVICE_SKIP_NEXT);
        registerReceiver(BRskipNext, intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                timeElapsedUpdates, new IntentFilter("timeElapsedUpdates"));
        LocalBroadcastManager.getInstance(this).registerReceiver(
                chapterUpdate, new IntentFilter("chapterUpdate"));
    }


    private BroadcastReceiver BRskipNext = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            skip_to_next(view);
        }
    };

    private BroadcastReceiver timeElapsedUpdates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            timeElapsed = intent.getDoubleExtra("timeElapsed", 0.0);
            finalTime = intent.getDoubleExtra("finalTime", 0.0);
            seekbar.setMax((int)finalTime);
            //set seekbar progress
            seekbar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            double time = timeElapsed;

            duration.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            duration2.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) time), TimeUnit.MILLISECONDS.toSeconds((long) time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))));
        }
    };

    private BroadcastReceiver chapterUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int oldChapter;
            oldChapter = chapter;
            chapter = intent.getIntExtra("chapter", 0);
            if(chapter != oldChapter) {
                restartPage();
            }
            section = intent.getIntExtra("section", 0);
            clickOnItemFromList = false;
            listview.performItemClick(listview.getAdapter().getView(section-1, null, null), section-1, section-1);
        }
    };

    protected void onStart ()
    {
        super.onStart();
        if (firstCall == true) {
            clickOnItemFromList = false;
            listview.performItemClick(listview.getAdapter().getView(section - 1, null, null), section - 1, section - 1);
        }
        firstCall = false;
        playAudioService();
    }

    private String get_book_name_by_id()
    {
        switch (book)
        {
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
//            case MISHPACHA:
//                return "משפחה";
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
//            case TEFILAT_NASHIM:
//                return "תפילת נשים";
        }
        return "לא נמצא";
    }

    protected void onPause()
    {
        super.onPause();
    }

    public void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BRskipNext);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timeElapsedUpdates);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chapterUpdate);

        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            stopService(playerIntent);
        }
    }

    public void initializeViews(){
        buttonPlayPause = (ImageButton)findViewById(R.id.media_play_pause);
        duration = (TextView) findViewById(R.id.audioDuration);
        duration2 = (TextView) findViewById(R.id.audioDuration2);
        //bufferingPercent = (TextView) findViewById(R.id.fileBuffering);
        seekbar = (SeekBar) findViewById(R.id.seekBar6);
        playerIntent = new Intent(this, MediaPlayerService.class);
    }

    //playing = 0-pause, 1-play, 2-skip
    public void playPause(View view) {

        Intent broadcastIntent;
        if(playing == 0)//if pause change button icon to play
        {
            playing = 1;
            buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
            broadcastIntent = new Intent(Broadcast_PLAY_PAUSE);
            sendBroadcast(broadcastIntent);
        }
        else if (playing == 1)//if play change button icon to pause
        {
            playing = 0;
            buttonPlayPause.setImageResource(R.drawable.baseline_play_circle_outline_white_48);
            broadcastIntent = new Intent(Broadcast_PLAY_PAUSE);
            sendBroadcast(broadcastIntent);
        }
        else if (playing == 2)//if skip next change button icon to pause
        {
            buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
            playing = 1;
        }
    }

    public void skip_to_next(View view) {
        playing = 2;
        Intent broadcastIntent = new Intent(Broadcast_SKIP_NEXT);
        sendBroadcast(broadcastIntent);
        broadcastIntent = new Intent(Broadcast_Speed);
        int choice=spinner.getSelectedItemPosition();
        if(choice==0)
            speed=2f;
        if(choice==1)
            speed=1.5f;
        if(choice==2)
            speed=1f;
        if(choice==3)
            speed=0.75f;
        broadcastIntent.putExtra("speed",speed);
        if (playing==0)
            broadcastIntent.putExtra("play",0);
        sendBroadcast(broadcastIntent);
        txtI.setText(book_name + " " +   convert_character_to_id(chapter)+", "+convert_character_to_id(section+1));

    }

    public void restartPage()
    {
        header = book_name + " " +   convert_character_to_id(chapter)+", א";
        txtI.setText(header);
        // TODO: fill the list of sections of the new chapter
        sections = extras.getStringArrayList("sections_"+chapter);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < sections.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", sections.get(i));
            aList.add(hm);
        }

        String[] from = { "listview_title"};
        int[] to = { R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.audio_list, from, to);
        listview.setAdapter(simpleAdapter);
    }

    public void skip_to_previous(View view) {
        playing = 2;
        buttonPlayPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
        Intent broadcastIntent = new Intent(Broadcast_SKIP_PREVIOUS);
        sendBroadcast(broadcastIntent);
        broadcastIntent = new Intent(Broadcast_Speed);
        int choice=spinner.getSelectedItemPosition();
        if(choice==0)
            speed=2f;
        if(choice==1)
            speed=1.5f;
        if(choice==2)
            speed=1f;
        if(choice==3)
            speed=0.75f;
        broadcastIntent.putExtra("speed",speed);
        broadcastIntent.putExtra("play",0);
        sendBroadcast(broadcastIntent);
        txtI.setText(book_name + " " +   convert_character_to_id(chapter)+", "+convert_character_to_id(section-1));
        if(section+1486<'א')
            txtI.setText(book_name + " " +   convert_character_to_id(chapter)+", א");
    }

    public void forward_10_sec(View view) {
        Intent broadcastIntent = new Intent(Broadcast_FORWARD_10);
        sendBroadcast(broadcastIntent);
    }



    public void rewind_10_sec(View view) {
        Intent broadcastIntent = new Intent(Broadcast_BACKWARD_10);
        sendBroadcast(broadcastIntent);
    }

    //indicate how much percent already buffered (downloaded)
/*    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        bufferingPercent.setText(String.format("טוען %d", percent));
    }*/

    public String convert_character_to_id(int Id)
    {
        switch (Id)
        {
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
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            playerService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void playAudioService() {
        //Check is service is active
        if (!serviceBound) {
            playerIntent = new Intent(this, MediaPlayerService.class);
            playerIntent.putExtra("book_id", book);
            playerIntent.putExtra("chapter_id", chapter);
            playerIntent.putExtra("audio_id", section);

            for(int i=1; i<=lastChapter[book]; i++) {
                String name;
                if (book == KASHRUT_B)
                    name = "sections_"+(i+19);
                else
                    name = "sections_"+i;
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice =adapterView.getItemAtPosition(i).toString();
        float sp=1f;
        if (choice.equals("x2"))
            sp=2f;
        if (choice.equals("x1.5"))
            sp=1.5f;
        if (choice.equals("x0.75"))
            sp=0.75f;
        if (choice.equals("x1"))
            sp=1f;
        Intent broadcastIntent = new Intent(Broadcast_Speed);
        speed=sp;
        broadcastIntent.putExtra("speed",sp);
        if (playing==0)
            broadcastIntent.putExtra("play",0);
        sendBroadcast(broadcastIntent);
        //if (playing==0)
        //  playPause(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}