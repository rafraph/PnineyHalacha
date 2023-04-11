package com.rafraph.pnineyHalachaHashalem;

import android.annotation.SuppressLint;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class pninaYomit extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    static SharedPreferences mPrefs;
    SharedPreferences.Editor shPrefEditor;
    WebView webview;
    private static final int HEBREW	 = 0;
    private static final int ENGLISH = 1;
    private static final int RUSSIAN = 2;
    private static final int SPANISH = 3;
    private static final int FRENCH = 4;
    private NumberPicker etH,etM;
    public static AlarmManager alarm=null;
    public static PendingIntent penNotu=null;
    private String[] pickerValsH,pickerValsM;
    public boolean notPress=false;
    public static int scrool=0;
    public static String set="",noteSet="",setRemind="",remindCancel="";
    public Dialog acronymsDialog;
    public EditText TextToDecode;
    String acronymsText;
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

        TextToDecode = (EditText) acronymsDialog.findViewById(R.id.editTextAcronyms );

        // if button is clicked
        dialogButtonExit.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                acronymsDialog.dismiss();
            }
        });

        dialogButtonDecode.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                acronymsText = "\r\n" + /*"י\"א" */TextToDecode.getText().toString() + " - ";
                acronymsText = acronymsText.replace("\"", "");
                acronymsText = acronymsText.replace("'", "");
                InputStream is;
                String r="לא נמצאו תוצאות";
                int index=0, index_end=0, first=1;
                try
                {
                    is = getAssets().open("acronyms.txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String strText  = new String(buffer);

                    while (strText.indexOf(acronymsText, index_end) != -1)
                    {
                        index = strText.indexOf(acronymsText, index);
                        index = strText.indexOf("-", index+1) + 2;
                        index_end = strText.indexOf("\r\n", index);
                        if(first==1)
                        {
                            r = strText.substring (index, index_end);
                            first=0;
                        }
                        else
                            r += ", " + strText.substring (index, index_end);
                    }
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                decodedText.setText(TextToDecode.getText().toString() + " - " + r);

            }
        });
        acronymsDialog.show();
    }
    @Override
    public void onBackPressed() {
        try
        {
            Class ourClass = null;
            Intent ourIntent;
            ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
            ourIntent = new Intent(pninaYomit.this, ourClass);
            startActivity(ourIntent);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnina_yomit);
       // webview.clearView();


        webview = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webview.requestFocusFromTouch();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webview.setWebChromeClient(new WebChromeClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
        LinearLayout main=(LinearLayout) findViewById(R.id.lnrOption3);
        int MyLanguage = mPrefs.getInt("MyLanguage", 0);
        if (mPrefs.getInt("BlackBackground", 0)==1)
        {
            ImageView toMain= (ImageView) findViewById(R.id.to_main);
            main.setBackgroundColor(Color.BLACK);
            webview.setBackgroundColor(Color.BLACK);
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
            main=(LinearLayout) findViewById(R.id.lnrOption3);
            ImageView menu= (ImageView) findViewById(R.id.menu);
            menu.setImageResource(R.drawable.ic_action_congif_b);
            main.setBackgroundColor(Color.rgb(120,1,1));

        }

        switch (MyLanguage)
        {
            case ENGLISH:
                webview.loadUrl("https://ph.yhb.org.il/pninayomit-en/#page-data");


                set="Set";
                noteSet="The reminder was set up successfully";
                setRemind="Set a reminder";
                remindCancel="Reminder canceled";
                break;
            case RUSSIAN:
                webview.loadUrl("https://ph.yhb.org.il/pninayomit-ru/#page-data");
                set="Постоянно";
                noteSet="Напоминание установлено";
                setRemind="напоминание";
                remindCancel="Удалить напоминание";
                break;
            case FRENCH:
                webview.loadUrl("https://ph.yhb.org.il/pninayomit-fr/#page-data");
                set="Valider ";
                noteSet="Le rappel a été correctement programmé";
                setRemind="Programmer un rappel ";
                remindCancel="Le rappel a été correctement annulé";
                break;
            case SPANISH:
                webview.loadUrl("https://ph.yhb.org.il/pninayomit-es/#page-data");
                set="arreglar";
                noteSet="El recordatorio se configuró correctamente.";
                setRemind="Establecer un recordatorio";
                remindCancel="Recordatorio cancelado";
                break;
            case HEBREW:
                webview.loadUrl("https://ph.yhb.org.il/pninayomit/#page-data");
                set="קבע";
                noteSet="קבע תזכורת";
                setRemind="תזכורת הוגדרה";
                remindCancel="תזכורת בוטלה";
                break;
        }


        scrool=webview.getScrollY();
        ImageView toMain= (ImageView) findViewById(R.id.to_main);
        if(MyLanguage==ENGLISH)
            toMain.setImageResource(R.drawable.to_main_e);
        if(MyLanguage==RUSSIAN)
            toMain.setImageResource(R.drawable.to_main_r);
        if(MyLanguage==SPANISH)
            toMain.setImageResource(R.drawable.to_main_s);
        if(MyLanguage==FRENCH)
            toMain.setImageResource(R.drawable.to_main_f);
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Class ourClass = null;
                    Intent ourIntent;
                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
                    ourIntent = new Intent(pninaYomit.this, ourClass);
                    startActivity(ourIntent);
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });
        ImageView menu= (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextThemeWrapper ctw = new ContextThemeWrapper(pninaYomit.this, R.style.CustomPopupTheme);
                PopupMenu popupMenu = new PopupMenu(ctw, v);
                //popupMenu.

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
                                    ourIntent = new Intent(pninaYomit.this, ourClass);
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
                                ourIntent = new Intent(pninaYomit.this, ourClass);
                                ourIntent.putExtra("homePage", true);
                                startActivity(ourIntent);
                                break;

                            case 1:/*to books*/
                                try {
                                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                ourIntent = new Intent(pninaYomit.this, ourClass);
                                ourIntent.putExtra("homePage", false);
                                startActivity(ourIntent);
                                break;

                            case 2:/*pninaYomit*/
                                try {
                                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                ourIntent = new Intent(pninaYomit.this, ourClass);
                                startActivity(ourIntent);
                                break;

                            case 3:/*search in all books*/
                                try {
                                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                ourIntent = new Intent(pninaYomit.this, ourClass);
                                startActivity(ourIntent);

                                break;

                            case 4:/*acronyms*/
                                acronymsDecode();
                                break;

                            case 5:/*feedback*/
                                try
                                {
                                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
                                    ourIntent = new Intent(pninaYomit.this, ourClass);
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
                                startActivity(intent);
                                break;

                            case 7:/*ask the rav*/
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://yhb.org.il/שאל-את-הרב-2/"));
                                startActivity(intent);
                                break;
                            case 8:/*about pninei*/
                                try
                                {
                                    ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About_p");
                                    ourIntent = new Intent(pninaYomit.this, ourClass);
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
                                    ourIntent = new Intent(pninaYomit.this, ourClass);
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
        createNotiChannel();
        refresh(10);
        TextView img=(TextView) findViewById(R.id.set_note);
        TextView imgF=(TextView) findViewById(R.id.set_note_f);
        TextView imgEn=(TextView) findViewById(R.id.set_note_en);
        TextView imgEs=(TextView) findViewById(R.id.set_note_es);
        TextView imgR=(TextView) findViewById(R.id.set_note_r);
        if(MyLanguage==ENGLISH) {
            img.setVisibility(View.GONE);
            imgEn.setVisibility(View.VISIBLE);
        }
        if(MyLanguage==RUSSIAN) {
            img.setVisibility(View.GONE);
            imgR.setVisibility(View.VISIBLE);
        }
        if(MyLanguage==FRENCH) {
            img.setVisibility(View.GONE);
            imgF.setVisibility(View.VISIBLE);

        }

        if(MyLanguage==SPANISH) {
            img.setVisibility(View.GONE);
            imgEs.setVisibility(View.VISIBLE);
        }
        ImageView deleteR=(ImageView) findViewById(R.id.deleteReminder);
        etH=(NumberPicker) findViewById(R.id.etH);
        etM=(NumberPicker) findViewById(R.id.etM);
        ImageButton Sep=(ImageButton) findViewById(R.id.sep);
        etH.setVisibility(View.INVISIBLE);
        Sep.setVisibility(View.INVISIBLE);
        etM.setVisibility(View.INVISIBLE);

        LinearLayout l=(LinearLayout) findViewById(R.id.lnrOptions);
        //String[] values = {"Public", "Shared", "Private"};
        etH.setMaxValue(23);
        etH.setMinValue(0);
        //etH.def
        etH.setWrapSelectorWheel(true);
        etM.setMaxValue(59);
        etM.setMinValue(0);
        //etH.def
        etM.setWrapSelectorWheel(true);
        //etH.setDisplayedValues(values);
        pickerValsM = new String[] {"00", "01", "02", "03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
        pickerValsH = new String[] {"00", "01", "02", "03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

        etH.setDisplayedValues(pickerValsH);
        etM.setDisplayedValues(pickerValsM);
        etH.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = etH.getValue();
                Log.d("picker value", pickerValsH[valuePicker1] + "");

            }
        });
        etM.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = etM.getValue();
                Log.d("picker value", pickerValsM[valuePicker1] + "");
            }
        });
        l.setBackgroundColor(Color.parseColor("#fefef2"));
        if(mPrefs.getInt("PYnote" , -1)!=-1) {
            img.setPadding(50,0,0,0);
            if(mPrefs.getInt("PYnote" , -1)<10&&mPrefs.getInt("PYnote2" , -1)<10)
                img.setText("0"+mPrefs.getInt("PYnote" , -1)+" : 0"+mPrefs.getInt("PYnote2" , -1));
            if(mPrefs.getInt("PYnote" , -1)<10&&mPrefs.getInt("PYnote2" , -1)>=10)
                img.setText("0"+mPrefs.getInt("PYnote" , -1)+" : "+mPrefs.getInt("PYnote2" , -1));
            if(mPrefs.getInt("PYnote" , -1)>=10&&mPrefs.getInt("PYnote2" , -1)<10)
                img.setText(mPrefs.getInt("PYnote" , -1)+" : 0"+mPrefs.getInt("PYnote2" , -1));
            if(mPrefs.getInt("PYnote" , -1)>=10&&mPrefs.getInt("PYnote2" , -1)>=10)
                img.setText(mPrefs.getInt("PYnote" , -1)+" : "+mPrefs.getInt("PYnote2" , -1));
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notPress) {
                   // img.setBackgroundResource(R.drawable.h_set_remainder);
                    img.setPadding(0,0,430,0);
                    img.setText(set);
                    l.setBackgroundColor(Color.parseColor("#970606"));
                    etH.setVisibility(View.VISIBLE);
                    Sep.setVisibility(View.VISIBLE);
                    etM.setVisibility(View.VISIBLE);
                    shPrefEditor.putInt("PYnote",0 );
                    shPrefEditor.putInt("PYnote2",0);
                    shPrefEditor.commit();
                    notPress=true;
                }
                else
                {
                    int hour=0,mins=0;
                    try {
                         hour = ((NumberPicker) findViewById(R.id.etH)).getValue();
                         mins = ((NumberPicker) findViewById(R.id.etM)).getValue();
                    Calendar calnder = Calendar.getInstance();
                    calnder.set(Calendar.HOUR_OF_DAY, hour);
                    calnder.set(Calendar.MINUTE, mins);

                    Intent notiReciv = new Intent(getApplicationContext(), Notification_reciver.class);
                    penNotu = PendingIntent.getBroadcast(getApplicationContext(), 100, notiReciv, PendingIntent.FLAG_IMMUTABLE);
                    alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calnder.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penNotu);
                    Toast.makeText(getApplicationContext(), setRemind, Toast.LENGTH_SHORT).show();
                        shPrefEditor.putInt("PYnote",hour );
                        shPrefEditor.putInt("PYnote2",mins);
                        shPrefEditor.commit();
                    String setH="",setM="";
                    if(((NumberPicker) findViewById(R.id.etH)).getValue()<10)
                        setH+="0";
                    if(((NumberPicker) findViewById(R.id.etM)).getValue()<10)
                        setM+="0";
                    img.setText(setH+((NumberPicker) findViewById(R.id.etH)).getValue()+" : "+setM+((NumberPicker) findViewById(R.id.etM)).getValue());


                    etH.setVisibility(View.INVISIBLE);
                    Sep.setVisibility(View.INVISIBLE);
                    img.setPadding(50,0,0,0);
                    etM.setVisibility(View.INVISIBLE);
                    l.setBackgroundColor(Color.parseColor("#fefef2"));
                    //img.setBackgroundResource(R.drawable.h_set_remainder);
                    notPress=false;
//
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "נא למלא את השעה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notPress) {
                    // img.setBackgroundResource(R.drawable.h_set_remainder);
                    imgF.setPadding(50,0,0,0);
                    imgF.setText(set);
                    l.setBackgroundColor(Color.parseColor("#970606"));
                    etH.setVisibility(View.VISIBLE);
                    Sep.setVisibility(View.VISIBLE);
                    etM.setVisibility(View.VISIBLE);
                    shPrefEditor.putInt("PYnote",0 );
                    shPrefEditor.putInt("PYnote2",0);
                    shPrefEditor.commit();
                    notPress=true;
                }
                else
                {
                    int hour=0,mins=0;
                    try {
                        hour = ((NumberPicker) findViewById(R.id.etH)).getValue();
                        mins = ((NumberPicker) findViewById(R.id.etM)).getValue();
                        Calendar calnder = Calendar.getInstance();
                        calnder.set(Calendar.HOUR_OF_DAY, hour);
                        calnder.set(Calendar.MINUTE, mins);

                        Intent notiReciv = new Intent(getApplicationContext(), Notification_reciver.class);
                        penNotu = PendingIntent.getBroadcast(getApplicationContext(), 100, notiReciv, PendingIntent.FLAG_IMMUTABLE);
                        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calnder.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penNotu);
                        Toast.makeText(getApplicationContext(), setRemind, Toast.LENGTH_SHORT).show();
                        shPrefEditor.putInt("PYnote",hour );
                        shPrefEditor.putInt("PYnote2",mins);
                        shPrefEditor.commit();
                        String setH="",setM="";
                        if(((NumberPicker) findViewById(R.id.etH)).getValue()<10)
                            setH+="0";
                        if(((NumberPicker) findViewById(R.id.etM)).getValue()<10)
                            setM+="0";
                        imgF.setText(setH+((NumberPicker) findViewById(R.id.etH)).getValue()+" : "+setM+((NumberPicker) findViewById(R.id.etM)).getValue());


                        etH.setVisibility(View.INVISIBLE);
                        Sep.setVisibility(View.INVISIBLE);
                        img.setPadding(50,0,0,0);
                        imgF.setPadding(50,0,0,0);
                        etM.setVisibility(View.INVISIBLE);
                        l.setBackgroundColor(Color.parseColor("#fefef2"));
                        //img.setBackgroundResource(R.drawable.h_set_remainder);
                        notPress=false;
//
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "נא למלא את השעה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notPress) {
                    // img.setBackgroundResource(R.drawable.h_set_remainder);
                    imgEn.setPadding(50,0,0,0);
                    imgEn.setText(set);
                    l.setBackgroundColor(Color.parseColor("#970606"));
                    etH.setVisibility(View.VISIBLE);
                    Sep.setVisibility(View.VISIBLE);
                    etM.setVisibility(View.VISIBLE);
                    shPrefEditor.putInt("PYnote",0 );
                    shPrefEditor.putInt("PYnote2",0);
                    shPrefEditor.commit();
                    notPress=true;
                }
                else
                {
                    int hour=0,mins=0;
                    try {
                        hour = ((NumberPicker) findViewById(R.id.etH)).getValue();
                        mins = ((NumberPicker) findViewById(R.id.etM)).getValue();
                        Calendar calnder = Calendar.getInstance();
                        calnder.set(Calendar.HOUR_OF_DAY, hour);
                        calnder.set(Calendar.MINUTE, mins);

                        Intent notiReciv = new Intent(getApplicationContext(), Notification_reciver.class);
                        penNotu = PendingIntent.getBroadcast(getApplicationContext(), 100, notiReciv, PendingIntent.FLAG_IMMUTABLE);
                        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calnder.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penNotu);
                        Toast.makeText(getApplicationContext(), setRemind, Toast.LENGTH_SHORT).show();
                        shPrefEditor.putInt("PYnote",hour );
                        shPrefEditor.putInt("PYnote2",mins);
                        shPrefEditor.commit();
                        String setH="",setM="";
                        if(((NumberPicker) findViewById(R.id.etH)).getValue()<10)
                            setH+="0";
                        if(((NumberPicker) findViewById(R.id.etM)).getValue()<10)
                            setM+="0";
                        imgEn.setText(setH+((NumberPicker) findViewById(R.id.etH)).getValue()+" : "+setM+((NumberPicker) findViewById(R.id.etM)).getValue());


                        etH.setVisibility(View.INVISIBLE);
                        Sep.setVisibility(View.INVISIBLE);
                        imgEn.setPadding(50,0,0,0);
                        etM.setVisibility(View.INVISIBLE);
                        l.setBackgroundColor(Color.parseColor("#fefef2"));
                        //img.setBackgroundResource(R.drawable.h_set_remainder);
                        notPress=false;
//
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "נא למלא את השעה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgEs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notPress) {
                    // img.setBackgroundResource(R.drawable.h_set_remainder);
                    imgEs.setPadding(50,0,0,0);
                    imgEs.setText(set);
                    l.setBackgroundColor(Color.parseColor("#970606"));
                    etH.setVisibility(View.VISIBLE);
                    Sep.setVisibility(View.VISIBLE);
                    etM.setVisibility(View.VISIBLE);
                    shPrefEditor.putInt("PYnote",0 );
                    shPrefEditor.putInt("PYnote2",0);
                    shPrefEditor.commit();
                    notPress=true;
                }
                else
                {
                    int hour=0,mins=0;
                    try {
                        hour = ((NumberPicker) findViewById(R.id.etH)).getValue();
                        mins = ((NumberPicker) findViewById(R.id.etM)).getValue();
                        Calendar calnder = Calendar.getInstance();
                        calnder.set(Calendar.HOUR_OF_DAY, hour);
                        calnder.set(Calendar.MINUTE, mins);

                        Intent notiReciv = new Intent(getApplicationContext(), Notification_reciver.class);
                        penNotu = PendingIntent.getBroadcast(getApplicationContext(), 100, notiReciv, PendingIntent.FLAG_IMMUTABLE);
                        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calnder.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penNotu);
                        Toast.makeText(getApplicationContext(), setRemind, Toast.LENGTH_SHORT).show();
                        shPrefEditor.putInt("PYnote",hour );
                        shPrefEditor.putInt("PYnote2",mins);
                        shPrefEditor.commit();
                        String setH="",setM="";
                        if(((NumberPicker) findViewById(R.id.etH)).getValue()<10)
                            setH+="0";
                        if(((NumberPicker) findViewById(R.id.etM)).getValue()<10)
                            setM+="0";
                        imgEs.setText(setH+((NumberPicker) findViewById(R.id.etH)).getValue()+" : "+setM+((NumberPicker) findViewById(R.id.etM)).getValue());


                        etH.setVisibility(View.INVISIBLE);
                        Sep.setVisibility(View.INVISIBLE);
                        //img.setPadding(50,0,0,0);
                        imgEs.setPadding(50,0,0,0);
                        etM.setVisibility(View.INVISIBLE);
                        l.setBackgroundColor(Color.parseColor("#fefef2"));
                        //img.setBackgroundResource(R.drawable.h_set_remainder);
                        notPress=false;
//
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "נא למלא את השעה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notPress) {
                    // img.setBackgroundResource(R.drawable.h_set_remainder);
                    //imgR.setPadding(50,0,0,0);
                    imgR.setText(set);
                    l.setBackgroundColor(Color.parseColor("#970606"));
                    etH.setVisibility(View.VISIBLE);
                    Sep.setVisibility(View.VISIBLE);
                    etM.setVisibility(View.VISIBLE);
                    shPrefEditor.putInt("PYnote",0 );
                    shPrefEditor.putInt("PYnote2",0);
                    shPrefEditor.commit();
                    notPress=true;
                }
                else
                {
                    int hour=0,mins=0;
                    try {
                        hour = ((NumberPicker) findViewById(R.id.etH)).getValue();
                        mins = ((NumberPicker) findViewById(R.id.etM)).getValue();
                        Calendar calnder = Calendar.getInstance();
                        calnder.set(Calendar.HOUR_OF_DAY, hour);
                        calnder.set(Calendar.MINUTE, mins);

                        Intent notiReciv = new Intent(getApplicationContext(), Notification_reciver.class);
                        penNotu = PendingIntent.getBroadcast(getApplicationContext(), 100, notiReciv, PendingIntent.FLAG_IMMUTABLE);

                        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calnder.getTimeInMillis(), AlarmManager.INTERVAL_DAY, penNotu);
                        Toast.makeText(getApplicationContext(), setRemind, Toast.LENGTH_SHORT).show();
                        shPrefEditor.putInt("PYnote",hour );
                        shPrefEditor.putInt("PYnote2",mins);
                        shPrefEditor.commit();
                        String setH="",setM="";
                        if(((NumberPicker) findViewById(R.id.etH)).getValue()<10)
                            setH+="0";
                        if(((NumberPicker) findViewById(R.id.etM)).getValue()<10)
                            setM+="0";
                        imgR.setText(setH+((NumberPicker) findViewById(R.id.etH)).getValue()+" : "+setM+((NumberPicker) findViewById(R.id.etM)).getValue());


                        etH.setVisibility(View.INVISIBLE);
                        Sep.setVisibility(View.INVISIBLE);
                        //imgR.setPadding(50,0,0,0);
                        etM.setVisibility(View.INVISIBLE);
                        l.setBackgroundColor(Color.parseColor("#fefef2"));
                        //img.setBackgroundResource(R.drawable.h_set_remainder);
                        notPress=false;
//
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "נא למלא את השעה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        deleteR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.cancel(penNotu);
                Toast.makeText(getApplicationContext(), remindCancel, Toast.LENGTH_SHORT).show();
                etH.setVisibility(View.INVISIBLE);
                Sep.setVisibility(View.INVISIBLE);
                img.setPadding(0,0,320,0);
                img.setPadding(50,0,0,0);

                shPrefEditor.putInt("PYnote",-1 );
                shPrefEditor.commit();
                img.setText(noteSet);
                etM.setVisibility(View.INVISIBLE);
                l.setBackgroundColor(Color.parseColor("#fefef2"));
                //img.setBackgroundResource(R.drawable.h_set_remainder);
                notPress=false;
            }
        });
        findViewById(R.id.to_main).setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Class ourClass = null;
               try {
                   ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
               Intent ourIntent = new Intent(pninaYomit.this, ourClass);
               startActivity(ourIntent);
            }
        });
    }

    private void createNotiChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="hi";
            String des="hiAgain";
            int imp= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("notifyLemubit",name,imp);
            channel.setDescription(des);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void refresh(int millisec) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, millisec);
    }
    public void content()
    {

        refresh(10);
    }


}