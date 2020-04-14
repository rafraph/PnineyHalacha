package com.rafraph.pnineyHalachaHashalem;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.BroadcastReceiver;


public class myAudio extends Activity
{
    /*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29*/
    public int[] lastChapter = {17, 11, 17, 10, 19, 19, 11, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 26, 30};

    private static final int BRACHOT      	= 0;
    private static final int HAAMVEHAAREZ 	= 1;
    private static final int ZMANIM    		= 2;
    private static final int YAMIM    		= 3;
    private static final int KASHRUT_A 		= 4;
    private static final int KASHRUT_B 		= 5;
    private static final int LIKUTIM_A 		= 6;
    private static final int LIKUTIM_B 		= 7;
    private static final int MOADIM    		= 8;
    private static final int MISHPACHA   	= 9;
    private static final int SUCOT			= 10;
    private static final int PESACH			= 11;
    private static final int SHVIIT			= 12;
    private static final int SHABAT			= 13;
    private static final int SIMCHAT		= 14;
    private static final int TEFILA			= 15;
    private static final int TEFILAT_NASHIM	= 16;


    public TextView duration, bufferingPercent;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 5000, backwardTime = 5000;
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

    private final Handler handler = new Handler();
    private TextView header_text;
    private String header;
    public ListView listview;
    public String book_name;
    Bundle extras;
    private MediaPlayerService playerService;
    boolean serviceBound = false;
    boolean firstCall;
    private Intent playerIntent;
    boolean clickOnItemFromList = false;

    public static final String Broadcast_START = "com.rafraph.pnineyHalachaHashalem.StartPlay";
    public static final String Broadcast_PLAY_PAUSE = "com.rafraph.pnineyHalachaHashalem.PlayPause";
    public static final String Broadcast_SKIP_NEXT = "com.rafraph.pnineyHalachaHashalem.SkipNext";
    public static final String Broadcast_SKIP_PREVIOUS = "com.rafraph.pnineyHalachaHashalem.SkipPrevious";
    public static final String Broadcast_SKIP_TO_SPECIFIC_SECTION = "com.rafraph.pnineyHalachaHashalem.SkipToSpecificSection";
    public static final String Broadcast_FORWARD_5 = "com.rafraph.pnineyHalachaHashalem.Forward5";
    public static final String Broadcast_BACKWARD_5 = "com.rafraph.pnineyHalachaHashalem.Backward5";
    public static final String Broadcast_OnTouch = "com.rafraph.pnineyHalachaHashalem.OnTouch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        firstCall = true;
        registerAllBroadcast();
        initializeViews();
        header_text = (TextView) findViewById(R.id.header_text);
        extras = getIntent().getExtras();
        sections = new ArrayList<String>();
        book = extras.getInt("book_id");
        chapter = extras.getInt("chapter_id");
        section = extras.getInt("audio_id");
        sections = extras.getStringArrayList("sections_"+chapter);

        book_name = get_book_name_by_id();
        header = book_name + " " + convert_character_to_id(chapter) ;
        header_text.setText(header);
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
    }

    private void sendSectionIdAndPlay(int selectedSection)
    {
        playing = 2;
        Intent broadcastIntent = new Intent(Broadcast_SKIP_TO_SPECIFIC_SECTION);
        broadcastIntent.putExtra("audio_id", selectedSection);
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
            duration.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
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
//            case HAAMVEHAAREZ:
//                return "העם והארץ";
            case ZMANIM:
                return "זמנים";
            case YAMIM:
                return "ימים נוראים";
            case KASHRUT_A:
                return "כשרות א";
//            case KASHRUT_B:
//                return "כשרות ב";
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
//            case SHVIIT:
//                return "שביעית";
            case SHABAT:
                return "שבת";
//            case SIMCHAT:
//                return "שמחת הבית וברכתו";
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
        //bufferingPercent = (TextView) findViewById(R.id.fileBuffering);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
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
    }

    public void restartPage()
    {
        header = book_name + " " +   convert_character_to_id(chapter) ;
        header_text.setText(header);
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
    }

    public void forward_5_sec(View view) {
        Intent broadcastIntent = new Intent(Broadcast_FORWARD_5);
        sendBroadcast(broadcastIntent);
    }

    public void rewind_5_sec(View view) {
        Intent broadcastIntent = new Intent(Broadcast_BACKWARD_5);
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
                String name = "sections_"+i;
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
}
