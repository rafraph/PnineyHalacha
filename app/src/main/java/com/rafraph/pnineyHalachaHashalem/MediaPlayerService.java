package com.rafraph.pnineyHalachaHashalem;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import android.media.session.MediaSessionManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener {

    /*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  31  32  33 34  35  36  37  38  39  40  41  42  42  44  45  46  47  48*/
    public int[] lastChapter = {18, 11, 17, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 26, 30 ,0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 26};

    private MediaPlayer mediaPlayer;
    private String mediaUrl;
    private int resumePosition;
    private AudioManager audioManager;
    private boolean ongoingCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;

    public static final String ACTION_PLAY = "com.valdioveliu.valdio.audioplayer.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.valdioveliu.valdio.audioplayer.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.valdioveliu.valdio.audioplayer.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.valdioveliu.valdio.audioplayer.ACTION_NEXT";
    public static final String ACTION_STOP = "com.valdioveliu.valdio.audioplayer.ACTION_STOP";

    //MediaSession
    private MediaSessionManager mediaSessionManager;
    private MediaSessionCompat mediaSession;
    private MediaControllerCompat.TransportControls transportControls;

    //AudioPlayer notification ID
    private static final int NOTIFICATION_ID = 101;

    // Binder given to clients
    private final IBinder iBinder = new LocalBinder();

    private int book;
    private int chapter;
    private int section;
    ArrayList<String> sections;
    private int book_audio_id;

    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 10000, backwardTime = 10000;
    private Handler durationHandler = new Handler();
    Intent serviceIntent;
    boolean wasPlaying = false;
    public static final String PREFS_NAME = "MyPrefsFile";
    static SharedPreferences mPrefs;
    SharedPreferences.Editor shPrefEditor;
    public static final String Broadcast_SERVICE_SKIP_NEXT = "com.rafraph.ph_beta.ServiceSkipNext";
    public   static float speed=1f;
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
    private static final int F_TFILA	= 48;


    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    public int getSection()
    {
        return section;
    }
    public void setSection(int sec){this.section=sec;}
    public int getChapter()
    {
        return chapter;
    }
    public int getBook()
    {
        return book;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // Perform one-time setup procedures
        float defSpeed=getSharedPreferences("MyPrefsFile",0).getFloat("audioSpeed",1);


        // Manage incoming phone calls during playback.
        // Pause MediaPlayer on incoming call,
        // Resume on hangup.
        callStateListener();
        //ACTION_AUDIO_BECOMING_NOISY -- change in audio outputs -- BroadcastReceiver
        registerBecomingNoisyReceiver();
        registerAllBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
            mediaPlayer = null;//without this you will get crash when pressing back button that stop the audio service
        }
        removeAudioFocus();
        //Disable the PhoneStateListener
        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }

        removeNotification();

        //unregister BroadcastReceivers
        unregisterReceiver(becomingNoisyReceiver);
    }

    //The system calls this method when an activity, requests the service be started
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            serviceIntent = intent;
            book = intent.getExtras().getInt("book_id");
            chapter = intent.getExtras().getInt("chapter_id");
            section = intent.getExtras().getInt("audio_id");
            convert_book_id();

            sections = new ArrayList<String>();
            sections = serviceIntent.getExtras().getStringArrayList("sections_"+chapter);
            if (book_audio_id!=F_TFILA) {
               // File fileF = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/audio/10-02-03.mp3");
               // if (fileF.exists()) {
                   // mediaUrl =Environment.getExternalStorageDirectory().toString() + "/DCIM/pnineyHalacha/audio/10-02-02.mp3";

                  //  }
               // else
                    mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/%02d-%02d-%02d.mp3", book_audio_id, chapter, section);
            }
            else
                mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/ru/ru-%02d-%02d-%02d.mp3" ,2, chapter, section);
        } catch (NullPointerException e) {
            stopSelf();
        }

        //Request audio focus
        if (requestAudioFocus() == false) {
            //Could not gain focus
            stopSelf();
        }

        if (mediaUrl != null && mediaUrl != "")
            initMediaPlayer();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //Invoked indicating buffering status of
        //a media resource being streamed over the network.
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //Invoked when playback of a media source has completed.
        if(timeElapsed > 40000 ) {//this strange condition is to prevent strange bug. If next button pressed and right after that skip_5_seconds pressed it cause to invoke onCompletion. So this condition prevent it
            Intent broadcastIntent = new Intent(Broadcast_SERVICE_SKIP_NEXT);
            sendBroadcast(broadcastIntent);
        }
    }

    //Handle errors
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //Invoked when there has been an error during an asynchronous operation
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        //Invoked to communicate some info.
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
        finalTime = mp.getDuration(); // gets the song length in milliseconds from URL
        timeElapsed = mp.getCurrentPosition();
        durationHandler.postDelayed(sendTimeElapsed, 100);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        //Invoked indicating the completion of a seek operation.
    }

    @Override
    public void onAudioFocusChange(int focusState) {
        //Invoked when the audio focus of the system is updated.
        switch (focusState) {
         /*   case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mediaPlayer == null) initMediaPlayer();
                else if (!mediaPlayer.isPlaying()) mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;*/
        }
    }

    private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this);
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private <playbackParams> void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        //Set up MediaPlayer event listeners
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        //Reset so that the MediaPlayer is not pointing to another data source
        //mediaPlayer.reset();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaUrl location
            mediaPlayer.setDataSource(mediaUrl);
            mediaPlayer.getCurrentPosition();
            PlaybackParams playbackParams;
            IntentFilter intentFilter = new IntentFilter(myAudio.Broadcast_Speed);

            playbackParams = mediaPlayer.getPlaybackParams().setSpeed(speed);
            mediaPlayer.setPlaybackParams(playbackParams);

            //get option to change the speed not in 3.2
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {

            mediaPlayer.start();


        }
    }

    private void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            wasPlaying = true;
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }
    }

    private void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }

    //Handle incoming phone calls
    private void callStateListener() {
        // Get the telephony manager
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Starting listening for PhoneState changes
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    //if at least one call exists or the phone is ringing
                    //pause the MediaPlayer
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            ongoingCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle. Start playing.
                        if (mediaPlayer != null) {
                            if (ongoingCall) {
                                ongoingCall = false;
                                if(wasPlaying == true)//resume to play only if was playing before the call
                                {
                                    resumeMedia();
                                    durationHandler.postDelayed(sendTimeElapsed, 100);
                                }
                            }
                        }
                        break;
                }
            }
        };
        // Register the listener with the telephony manager
        // Listen for changes to the device call state.
//        telephonyManager.listen(phoneStateListener,
//                PhoneStateListener.LISTEN_CALL_STATE);
    }

    //Becoming noisy
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();
            buildNotification(PlaybackStatus.PAUSED);
        }
    };

    private void registerBecomingNoisyReceiver() {
        //register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);
    }
    private void registerAllBroadcast() {
        //register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(myAudio.Broadcast_SKIP_NEXT);
        registerReceiver(BR_skipToNext, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_SKIP_PREVIOUS);
        registerReceiver(BR_skipToPrevious, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_SKIP_TO_SPECIFIC_SECTION);
        registerReceiver(BR_skipToSpecificSection, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_START);
        registerReceiver(BR_start, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_PLAY_PAUSE);
        registerReceiver(BR_playPause, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_FORWARD_10);
        registerReceiver(BR_forward_10_sec, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_BACKWARD_10);
        registerReceiver(BR_backward_10_sec, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_OnTouch);
        registerReceiver(BR_on_Touch, intentFilter);
        intentFilter = new IntentFilter(myAudio.Broadcast_Speed);
        registerReceiver(Br_speed, intentFilter);
    }

    private BroadcastReceiver BR_forward_10_sec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //check if we can go forward at forwardTime seconds before song ends
            if ((timeElapsed + forwardTime) <= finalTime) {
                timeElapsed = timeElapsed + forwardTime;
                //seek to the exact second of the track
                mediaPlayer.seekTo((int) timeElapsed);
            }
        }
    };
    private BroadcastReceiver Br_speed = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            //check if we can go forward at forwardTime seconds before song ends
            mPrefs = getSharedPreferences(PREFS_NAME, 0);
            shPrefEditor = mPrefs.edit();
            float mesdiaSpeed=intent.getFloatExtra("speed",1f);
            int play=intent.getIntExtra("play",1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//shPrefEditor.putInt("rotate", getResources().getConfiguration().orientation);
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(mPrefs.getFloat("audioSpeed",1f)));
                //else
                if (play!=1)
                    mediaPlayer.pause();
            }
        }
    };
    private BroadcastReceiver BR_backward_10_sec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //check if we can go forward at forwardTime seconds before song ends
            if ((timeElapsed - backwardTime) < 0)
                timeElapsed = 0;
            else
                timeElapsed = timeElapsed - backwardTime;
            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    };

    private BroadcastReceiver BR_on_Touch = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int seekbarProgress = intent.getExtras().getInt("seekbarProgress", 2);
            mediaPlayer.seekTo((int) seekbarProgress);
        }
    };

    private Runnable sendTimeElapsed = new Runnable() {
        public void run() {
            if(mediaPlayer != null) {
                timeElapsed = mediaPlayer.getCurrentPosition();
                Intent intent = new Intent("timeElapsedUpdates");
                intent.putExtra("timeElapsed", timeElapsed);
                intent.putExtra("finalTime", finalTime);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                if (mediaPlayer.isPlaying()) {
                    //repeat yourself that again in 300 miliseconds
                    durationHandler.postDelayed(this, 300);
                }

            }
        }
    };

    private BroadcastReceiver BR_start = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //start();
        }
    };

    private BroadcastReceiver BR_skipToNext = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                skipToNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private BroadcastReceiver BR_skipToPrevious = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            skipToPrevious();
        }
    };

    private BroadcastReceiver BR_skipToSpecificSection = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            section = intent.getExtras().getInt("audio_id");
            skipToSpecificSection();
        }
    };

    private BroadcastReceiver BR_playPause = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                resumePosition = mediaPlayer.getCurrentPosition();
            }
            else{
                mediaPlayer.start();
                durationHandler.postDelayed(sendTimeElapsed, 100);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void skipToNext() throws IOException {
        //stopMedia();
        if (mediaPlayer==null){
            initMediaPlayer();
            mediaPlayer.start();

        }
        stopMedia();
        mediaPlayer.reset();
        if (book!=48)
        //this if crash in russian(dont know whi
        if(section == sections.size())//if it the last section
        {
            if(chapter == lastChapter[book])//if it the last chapter
            {
                Toast.makeText(getApplicationContext(), "צדיק, אשריך! סיימת את הספר. חזור לתוכן הראשי ובחר את הספר הבא שלך.", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                chapter++;
                section = 1;
                sections = new ArrayList<String>();
                sections = serviceIntent.getExtras().getStringArrayList("sections_"+chapter);
            }
        }
        else
            section++;
        else
        {
            int []r_tfilae={10,10,11,8,11,9,3,8,6,7,12,10,6,6,12,7,21,10,9,11,8,9,12,7,9,3};
            if(section<r_tfilae[chapter-1])
            {
                section++;
            }
            else {
                section = 1;
                if(chapter<26)
                    chapter++;
                else
                    chapter=1;
            }
        }
        if (book_audio_id!=48)
            mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/%02d-%02d-%02d.mp3",book_audio_id, chapter, section );
        else
            mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/ru/ru-%02d-%02d-%02d.mp3" ,2, chapter, section);

        //else
           // mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/%02d-%02d-%02d.mp3", book_audio_id, chapter, section );
            //mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/ru/ru-02-%02d-%02d.mp3" , chapter, section );
        Intent intent = new Intent("chapterUpdate");
        intent.putExtra("chapter", chapter);
        intent.putExtra("section", section);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        try {
            mediaPlayer.setDataSource(mediaUrl);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }

    private void skipToPrevious() {
        stopMedia();
        mediaPlayer.reset();

        if(section == 1)
        {
            if(chapter == 1)
                Toast.makeText(getApplicationContext(), "זה הפרק הראשון בספר!", Toast.LENGTH_SHORT).show();
            else {
                chapter--;
                section = 1;
                sections = new ArrayList<String>();
                sections = serviceIntent.getExtras().getStringArrayList("sections_"+chapter);
            }
        }
        else
            section--;
        if (book_audio_id!=48)
            mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/%02d-%02d-%02d.mp3",book_audio_id, chapter, section );
        else
            mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/ru/ru-%02d-%02d-%02d.mp3" ,2, chapter, section);

        Intent intent = new Intent("chapterUpdate");
        intent.putExtra("chapter", chapter);
        intent.putExtra("section", section);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        try {
            mediaPlayer.setDataSource(mediaUrl);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }

    private void skipToSpecificSection() {
        stopMedia();
        mediaPlayer.reset();
        if(book_audio_id!=48)
        mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/%02d-%02d-%02d.mp3", book_audio_id, chapter, section );
        else
            mediaUrl = String.format("https://cdn1.yhb.org.il/mp3/ru/ru-%02d-%02d-%02d.mp3" ,2, chapter, section);

       /* Intent intent = new Intent("chapterUpdate");
        intent.putExtra("chapter", chapter);
        intent.putExtra("section", section);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
*/
        try {
            mediaPlayer.setDataSource(mediaUrl);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }

    private void buildNotification(PlaybackStatus playbackStatus) {

        int notificationAction = android.R.drawable.ic_media_pause;//needs to be initialized
        PendingIntent play_pauseAction = null;

        //Build a new notification according to the current state of the MediaPlayer
        if (playbackStatus == PlaybackStatus.PLAYING) {
            notificationAction = android.R.drawable.ic_media_pause;
            //create the pause action
            play_pauseAction = playbackAction(1);
        } else if (playbackStatus == PlaybackStatus.PAUSED) {
            notificationAction = android.R.drawable.ic_media_play;
            //create the play action
            play_pauseAction = playbackAction(0);
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.pniney_icon); //replace with your own image

        // Create a new Notification
        /*NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setShowWhen(false)
                // Set the Notification style
                .setStyle(new NotificationCompat.MediaStyle()
                        // Attach our MediaSession token
                        .setMediaSession(mediaSession.getSessionToken())
                        // Show our playback controls in the compact notification view.
                        .setShowActionsInCompactView(0, 1, 2))
                // Set the Notification color
                .setColor(getResources().getColor(android.R.color.white))
                // Set the large and small icons
                .setLargeIcon(largeIcon)
                .setSmallIcon(android.R.drawable.stat_sys_headset)
                // Set Notification content information
                .setContentText(activeAudio.getArtist())
                .setContentTitle(activeAudio.getAlbum())
                .setContentInfo(activeAudio.getTitle())
                // Add playback actions
                .addAction(android.R.drawable.ic_media_previous, "previous", playbackAction(3))
                .addAction(notificationAction, "pause", play_pauseAction)
                .addAction(android.R.drawable.ic_media_next, "next", playbackAction(2));
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, notificationBuilder.build());*/
    }

    private void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private PendingIntent playbackAction(int actionNumber) {
        Intent playbackAction = new Intent(this, MediaPlayerService.class);
        switch (actionNumber) {
            case 0:
                // Play
                playbackAction.setAction(ACTION_PLAY);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 1:
                // Pause
                playbackAction.setAction(ACTION_PAUSE);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 2:
                // Next track
                playbackAction.setAction(ACTION_NEXT);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 3:
                // Previous track
                playbackAction.setAction(ACTION_PREVIOUS);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            default:
                break;
        }
        return null;
    }

    private void handleIncomingActions(Intent playbackAction) {
        if (playbackAction == null || playbackAction.getAction() == null) return;

        String actionString = playbackAction.getAction();
        if (actionString.equalsIgnoreCase(ACTION_PLAY)) {
            transportControls.play();
        } else if (actionString.equalsIgnoreCase(ACTION_PAUSE)) {
            transportControls.pause();
        } else if (actionString.equalsIgnoreCase(ACTION_NEXT)) {
            transportControls.skipToNext();
        } else if (actionString.equalsIgnoreCase(ACTION_PREVIOUS)) {
            transportControls.skipToPrevious();
        } else if (actionString.equalsIgnoreCase(ACTION_STOP)) {
            transportControls.stop();
        }
    }

    //convert book id to book_audio_id as in the server
    private void convert_book_id ()
    {
        switch (book)
        {
            case BRACHOT:
                book_audio_id = 10;
                return;
            case HAAMVEHAAREZ:
                book_audio_id = 6;
                return;
            case ZMANIM:
                book_audio_id = 5;
                return;
            case TAHARAT:
                book_audio_id = 18;
                return;
            case YAMIM:
                book_audio_id = 15;
                return;
            case KASHRUT_A:
                book_audio_id = 17;
                return;
            case KASHRUT_B:
                book_audio_id = 17;
                return;
            case MISHPACHA:
                book_audio_id = 11;
                return;
//            case LIKUTIM_A:
//                book_audio_id = 99;
//                return;
//            case LIKUTIM_B:
//                book_audio_id = 99;
//                return;
            case MOADIM:
                book_audio_id = 12;
                return;
//            case MISHPACHA:
//                book_audio_id = 99;
//                return;
            case SUCOT:
                book_audio_id = 13;
                return;
            case PESACH:
                book_audio_id = 4;
                return;
            case SHVIIT:
                book_audio_id = 16;
                return;
            case SHABAT:
                book_audio_id = 1;
                return;
            case SIMCHAT:
                book_audio_id = 14;
                return;
            case TEFILA:
                book_audio_id = 2;
                return;
            case 48:
                book_audio_id=48;
//            case TEFILAT_NASHIM:
//                book_audio_id = 99;
//                return;

        }
    }

}