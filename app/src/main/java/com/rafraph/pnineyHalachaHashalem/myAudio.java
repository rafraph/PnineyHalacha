package com.rafraph.pnineyHalachaHashalem;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    /*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  30*/
    public int[] lastChapter = {18, 10, 17, 10, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 26, 30};

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

    public TextView duration, bufferingPercent;
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
    public static final String Broadcast_FORWARD_10 = "com.rafraph.pnineyHalachaHashalem.Forward10";
    public static final String Broadcast_BACKWARD_10 = "com.rafraph.pnineyHalachaHashalem.Backward10";
    public static final String Broadcast_OnTouch = "com.rafraph.pnineyHalachaHashalem.OnTouch";
    public static final String Broadcast_SPEED_2_0 = "com.rafraph.pnineyHalachaHashalem.Speed2_0";
    public static final String Broadcast_SPEED_1_8 = "com.rafraph.pnineyHalachaHashalem.Speed1_8";
    public static final String Broadcast_SPEED_1_5 = "com.rafraph.pnineyHalachaHashalem.Speed1_5";
    public static final String Broadcast_SPEED_1_2 = "com.rafraph.pnineyHalachaHashalem.Speed1_2";
    public static final String Broadcast_SPEED_1_0 = "com.rafraph.pnineyHalachaHashalem.Speed1_0";
    public static final String Broadcast_SPEED_0_8 = "com.rafraph.pnineyHalachaHashalem.Speed0_8";
    ImageButton red, blue;

    private PopupWindow audioSpeedPopupWindow = null;
    LayoutInflater mInflater;
    Button bAudioSpeed;
    SharedPreferences.Editor shPrefEditor;
    static SharedPreferences mPrefs;
    public static final String PREFS_NAME = "MyPrefsFile";
    String audioSpeedPref;

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
        if(book == KASHRUT_B)//KASHRUT_B is starting from chapter 20
            chapter += 19;
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

        bAudioSpeed = (Button)findViewById(R.id.audio_speed);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
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
            audioSpeedPref = mPrefs.getString("audioSpeed", "1.0");
            changeSpeed(audioSpeedPref);
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

    public void open_speeds(View view) {
        try {
            mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.audio_speeds, null);

            final TextView speed08text = (TextView) layout.findViewById(R.id.speed0_8);
            final TextView speed10text = (TextView) layout.findViewById(R.id.speed1_0);
            final TextView speed12text = (TextView) layout.findViewById(R.id.speed1_2);
            final TextView speed15text = (TextView) layout.findViewById(R.id.speed1_5);
            final TextView speed18text = (TextView) layout.findViewById(R.id.speed1_8);
            final TextView speed20text = (TextView) layout.findViewById(R.id.speed2_0);
            speed08text.setOnClickListener(this::onClickChangeSpeed);
            speed10text.setOnClickListener(this::onClickChangeSpeed);
            speed12text.setOnClickListener(this::onClickChangeSpeed);
            speed15text.setOnClickListener(this::onClickChangeSpeed);
            speed18text.setOnClickListener(this::onClickChangeSpeed);
            speed20text.setOnClickListener(this::onClickChangeSpeed);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            this.audioSpeedPopupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,true);
            Drawable background = getResources().getDrawable(android.R.drawable.checkbox_off_background);
            this.audioSpeedPopupWindow.setBackgroundDrawable(background);
            this.audioSpeedPopupWindow.showAsDropDown(bAudioSpeed, 0, -1000);

            if(audioSpeedPref.equals("2.0"))
                speed20text.setText("2.0x ✓");
            else if(audioSpeedPref.equals("1.8"))
                speed18text.setText("1.8x ✓");
            else if(audioSpeedPref.equals("1.5"))
                speed15text.setText("1.5x ✓");
            else if(audioSpeedPref.equals("1.2"))
                speed12text.setText("1.2x ✓");
            else if(audioSpeedPref.equals("1.0"))
                speed10text.setText("1.0x ✓");
            else if(audioSpeedPref.equals("0.8"))
                speed08text.setText("0.8x ✓");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onClickChangeSpeed(View v) {
            switch (v.getId()) {
                case R.id.speed2_0:
                    changeSpeed("2.0");
                    break;
                case R.id.speed1_8:
                    changeSpeed("1.8");
                    break;
                case R.id.speed1_5:
                    changeSpeed("1.5");
                    break;
                case R.id.speed1_2:
                    changeSpeed("1.2");
                    break;
                case R.id.speed1_0:
                    changeSpeed("1.0");
                    break;
                case R.id.speed0_8:
                    changeSpeed("0.8");
                    break;
            }
        this.audioSpeedPopupWindow.dismiss();
    }

    void changeSpeed(String audioSpeed) {
        Intent broadcastIntent = new Intent(Broadcast_SPEED_1_0);
        switch (audioSpeed) {
            case "2.0":
                broadcastIntent = new Intent(Broadcast_SPEED_2_0);
                bAudioSpeed.setText("2.0x");
                audioSpeedPref = "2.0";
                break;
            case "1.8":
                broadcastIntent = new Intent(Broadcast_SPEED_1_8);
                bAudioSpeed.setText("1.8x");
                audioSpeedPref = "1.8";
                break;
            case "1.5":
                broadcastIntent = new Intent(Broadcast_SPEED_1_5);
                bAudioSpeed.setText("1.5x");
                audioSpeedPref = "1.5";
                break;
            case "1.2":
                broadcastIntent = new Intent(Broadcast_SPEED_1_2);
                bAudioSpeed.setText("1.2x");
                audioSpeedPref = "1.2";
                break;
            case "1.0":
                broadcastIntent = new Intent(Broadcast_SPEED_1_0);
                bAudioSpeed.setText("1.0x");
                audioSpeedPref = "1.0";
                break;
            case "0.8":
                broadcastIntent = new Intent(Broadcast_SPEED_0_8);
                bAudioSpeed.setText("0.8x");
                audioSpeedPref = "0.8";
                break;
        }
        shPrefEditor.putString("audioSpeed", audioSpeedPref);
        sendBroadcast(broadcastIntent);
        shPrefEditor.commit();
    }
}
