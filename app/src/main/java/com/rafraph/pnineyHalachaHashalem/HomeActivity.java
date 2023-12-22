package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class HomeActivity extends AppCompatActivity {

    static SharedPreferences mPrefs;
    public Context context;
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor shPrefEditor;
    public Dialog newVersionDialog, languageDialog;
    public int StartInLastLocation = 1;
    public boolean newVersion = false;
    public int BlackBackground=0, SleepScreen=1, MyLanguage = -1;
    private static final int HEBREW	 = 0;
    private static final int ENGLISH = 1;
    private static final int RUSSIAN = 2;
    private static final int SPANISH = 3;
    private static final int FRENCH = 4;
    public Dialog acronymsDialog;
    public EditText TextToDecode;
    String acronymsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
        MyLanguage = mPrefs.getInt("MyLanguage", -1);
        StartInLastLocation = mPrefs.getInt("StartInLastLocation", 1);
        if(StartInLastLocation == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && newVersion == false)/*check if book and chapter are 0 so this is the first time the user open the application so don't go to the last location*/
        {
            goToLastLocation();
        }

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showPopupMenuSettings(findViewById(R.id.action_config));
            }
        });

        findViewById(R.id.books).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.MainActivity");
            }
        });
        findViewById(R.id.tvBooks).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.MainActivity");
            }
        });

        findViewById(R.id.dailyLearn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.dailyLearn");
            }
        });
        findViewById(R.id.tvDailyLearn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.dailyLearn");
            }
        });

        findViewById(R.id.bookmarks).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.BookmarkActivity");
            }
        });
        findViewById(R.id.tvBookmarks).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.BookmarkActivity");
            }
        });

        findViewById(R.id.searchAll).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.SearchActivity");
            }
        });
        findViewById(R.id.tvSearchAll).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.SearchActivity");
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Settings");
            }
        });
        findViewById(R.id.tvSettings).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Settings");
            }
        });

        findViewById(R.id.lastLocation).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goToLastLocation();
            }
        });
        findViewById(R.id.tvLastLocation).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goToLastLocation();
            }
        });

        findViewById(R.id.askTheRav).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.AskTheRav");
            }
        });
        findViewById(R.id.tvAskTheRav).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.AskTheRav");
            }
        });

        findViewById(R.id.quiz).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Quiz");
            }
        });
        findViewById(R.id.tvQuiz).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Quiz");
            }
        });

        findViewById(R.id.donation).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Donation");
            }
        });
        findViewById(R.id.tvDonation).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Donation");
            }
        });

        findViewById(R.id.shop).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Shop");
            }
        });
        findViewById(R.id.tvShop).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openActivity("com.rafraph.pnineyHalachaHashalem.Shop");
            }
        });

        /* Choose language*/
        if(MyLanguage == -1)
        {
            languageDialog(context);
        }

        /*display the new features of this version*/
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        String version;
        try
        {
            version = packageManager.getPackageInfo(packageName, 0).versionName;

            if(mPrefs.getString("Version", "").equals(version) == false)
            {
                newVersion = true;
                shPrefEditor.putString("Version", version);
                shPrefEditor.commit();
                newVersionDialog = new Dialog(context);
                newVersionDialog.setContentView(R.layout.new_version);
                newVersionDialog.setTitle("גרסה " + version);

                Button dialogButtonExit = (Button) newVersionDialog.findViewById(R.id.dialogButtonExit);
                // if button is clicked
                dialogButtonExit.setOnClickListener(new View.OnClickListener()
                {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v)
                    {
                        newVersionDialog.dismiss();
                    }
                });
                newVersionDialog.show();
				//fix bookmarks
				//fixBookmarks(GIYUR);//open this comment only if you added new book that is not the last in the book IDs
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }//onCreate

	public void fixBookmarks(int newBook){
		//nasty way to fix bookmarks when new book added and some books IDs incremented
		String Bookmarks = mPrefs.getString("Bookmarks", "");
		int book;
		int i, index = 1/*to skip the first comma*/, index_end=0;
		if(Bookmarks == null || Bookmarks.length()<2)
			return;
		index = Bookmarks.indexOf(",", index) + 1;
		index_end = Bookmarks.indexOf(",", index);
		book = Integer.parseInt(Bookmarks.substring(index, index_end));
		if(book >= newBook)
		{
			book++;
			Bookmarks = Bookmarks.substring(0, index) + Integer.toString(book) + Bookmarks.substring(index_end);
		}
		for (i = 0; i<5; i++)/*skip to the book of the right bookmark*/
			index = Bookmarks.indexOf(",", index) + 1;

		while(index>0) {
			index_end = Bookmarks.indexOf(",", index);
			book = Integer.parseInt(Bookmarks.substring(index, index_end));
			if(book >= newBook)
			{
				book++;
				Bookmarks = Bookmarks.substring(0, index) + Integer.toString(book) + Bookmarks.substring(index_end);
			}
			for (i = 0; i<5; i++)/*skip to the book of the right bookmark*/ {
				index = Bookmarks.indexOf(",", index) + 1;
				if(index==0) break;
			}
		}
		shPrefEditor.putString("Bookmarks", Bookmarks);
		shPrefEditor.commit();
	}

    public void openActivity(String name){
        try
        {
            Class ourClass = Class.forName(name);
            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
            startActivity(ourIntent);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    void goToLastLocation()
    {
        try
        {
            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
            int[] book_chapter = new int[2];
            book_chapter[0] = 0xFFFF;
            book_chapter[1] = 0xFFFF;
            ourIntent.putExtra("book_chapter", book_chapter);
            startActivity(ourIntent);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    void languageDialog(Context context)
    {
        languageDialog = new Dialog(context);
        languageDialog.setContentView(R.layout.language);

        Button ButtonSetLanguage = (Button) languageDialog.findViewById(R.id.dialogButtonOK);
        final RadioButton radioHebrew = (RadioButton) languageDialog.findViewById(R.id.radioHebrew);
        final RadioButton radioEnglish = (RadioButton) languageDialog.findViewById(R.id.radioEnglish);
        final RadioButton radioRussian = (RadioButton) languageDialog.findViewById(R.id.radioRussian);
        final RadioButton radioSpanish = (RadioButton) languageDialog.findViewById(R.id.radioSpanish);
        final RadioButton radioFrench = (RadioButton) languageDialog.findViewById(R.id.radioFrench);

        if(MyLanguage == -1)
        {
            MyLanguage = HEBREW; /*default value*/
            shPrefEditor.putInt("MyLanguage", MyLanguage);
            shPrefEditor.commit();
        }
        else
        {
            if(MyLanguage == HEBREW)
                radioHebrew.setChecked(true);
            else if(MyLanguage == ENGLISH)
                radioEnglish.setChecked(true);
            else if(MyLanguage == RUSSIAN)
                radioRussian.setChecked(true);
            else if(MyLanguage == SPANISH)
                radioSpanish.setChecked(true);
            else if(MyLanguage == FRENCH)
                radioFrench.setChecked(true);
        }

        // if button is clicked
        ButtonSetLanguage.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                if(radioHebrew.isChecked())
                {
                    MyLanguage = HEBREW;
                }
                else if(radioEnglish.isChecked())
                {
                    MyLanguage = ENGLISH;
                }
                else if(radioRussian.isChecked())
                {
                    MyLanguage = RUSSIAN;
                }
                else if(radioSpanish.isChecked())
                {
                    MyLanguage = SPANISH;
                }
                else if(radioFrench.isChecked())
                {
                    MyLanguage = FRENCH;
                }

                shPrefEditor.putInt("MyLanguage", MyLanguage);
                shPrefEditor.commit();

                languageDialog.dismiss();
            }
        });

        languageDialog.show();
    }

    private void showPopupMenuSettings(View v)
    {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
        //  popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());

        String configHeaders[] = new String[8];
        if(MyLanguage == ENGLISH) {
            configHeaders[0] = "Settings";
            configHeaders[1] = "About";
            configHeaders[2] = "Feedback";
            configHeaders[3] = "Explanation of search results";
            configHeaders[4] = "Acronyms";
            configHeaders[5] = "Approbations";
            configHeaders[6] = "Language / שפה";
        }
        else if(MyLanguage == RUSSIAN) {
            configHeaders[0] = "Настройки";
            configHeaders[1] = "Около";
            configHeaders[2] = "Обратная связь";
            configHeaders[3] = "Объяснение результатов поиска";
            configHeaders[4] = "Абревиатуры";
            configHeaders[5] = "Апробации";
            configHeaders[6] = "ЯЗЫК / שפה";
        }
        else if(MyLanguage == SPANISH) {
            configHeaders[0] = "Ajustes";
            configHeaders[1] = "Acerca de";
            configHeaders[2] = "Comentarios";
            configHeaders[3] = "Explicacion del resultado de la busqueda";
            configHeaders[4] = "Acronimos";
            configHeaders[5] = "Aprovaciones";
            configHeaders[6] = "Idioma / שפה";
        }
        else if(MyLanguage == FRENCH) {
            configHeaders[0] = "Definitions";
            configHeaders[1] = "A Propos de…";
            configHeaders[2] = "Commentaires";
            configHeaders[3] = "Explication de la recherche";
            configHeaders[4] = "Acronymes";
            configHeaders[5] = "Approbations";
            configHeaders[6] = "Langue / שפה";
        }
        else {/*this is the default*/
            configHeaders[0] = "הגדרות";
            configHeaders[1] = "אודות";
            configHeaders[2] = "משוב";
            configHeaders[3] = "הסבר על החיפוש";
            configHeaders[4] = "ראשי תיבות";
            configHeaders[5] = "הסכמות";
            //booksDownload configHeaders[6] = "ספרים להורדה";
            configHeaders[6/*booksDownload 7*/] = "Language / שפה";
        }

        popupMenu.getMenu().add(0,0,0,configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
        popupMenu.getMenu().add(0,1,1,configHeaders[1]);
        popupMenu.getMenu().add(0,2,2,configHeaders[2]);
        popupMenu.getMenu().add(0,3,3,configHeaders[3]);
        popupMenu.getMenu().add(0,4,4,configHeaders[4]);
        popupMenu.getMenu().add(0,5,5,configHeaders[5]);
        popupMenu.getMenu().add(0,6,6,configHeaders[6]);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case 0:/*settings*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Settings");
                            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
                            startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }

                        break;
                    case 1:/*about*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
                            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
                            startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }

                        break;
                    case 2:/*Feedback*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
                            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
                            startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 3:/*Explanation for Search*/
                        try
                        {
                            Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
                            Intent ourIntent = new Intent(HomeActivity.this, ourClass);
                            startActivity(ourIntent);
                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 4:/*acronyms*/
                        acronymsDecode();
                        break;
                    case 5:/*hascamot*/
                        hascamotDialog();
                        break;
                    case 6:/*language*/
                        languageDialog(context);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

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

    @SuppressLint("SetJavaScriptEnabled")
    void hascamotDialog()
    {
        final Context context = this;
        final Dialog dialog = new Dialog(context);
        int fontSize;
        WebView webviewHascmot;
        WebSettings webSettingsHascamot;

        dialog.setContentView(R.layout.note);

        dialog.setTitle(" הסכמות ");

        webviewHascmot = (WebView) dialog.findViewById(R.id.webViewNote1);
        webSettingsHascamot = webviewHascmot.getSettings();
        webSettingsHascamot.setJavaScriptEnabled(true);
        webSettingsHascamot.setDefaultTextEncodingName("utf-8");
        webviewHascmot.requestFocusFromTouch();

        fontSize = mPrefs.getInt("fontSize", 20);
        webSettingsHascamot.setDefaultFontSize(fontSize);
        int backgroundColor = mPrefs.getInt("BlackBackground", 0);
        webviewHascmot.setBackgroundColor(backgroundColor);
        if(backgroundColor == 0)
            webviewHascmot.loadUrl("javascript:document.body.style.color=\"black\";");
        else
            webviewHascmot.loadUrl("javascript:document.body.style.color=\"white\";");
        webviewHascmot.loadUrl("file:///android_asset/hascamot.html");
        dialog.show();
    }


}
