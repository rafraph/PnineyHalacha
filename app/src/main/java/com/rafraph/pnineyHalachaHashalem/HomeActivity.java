package com.rafraph.pnineyHalachaHashalem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class HomeActivity extends AppCompatActivity {

    static SharedPreferences mPrefs;
    public Context context;
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor shPrefEditor;
    public Dialog newVersionDialog;
    public int StartInLastLocation = 1;
    public boolean newVersion = false;
    public int BlackBackground=0, SleepScreen=1, MyLanguage = -1;
    public Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // for toolbar
        Toolbar generalToolbar = (Toolbar) findViewById(R.id.generalToolbar);
        setSupportActionBar(generalToolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.toolbar_header);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        util = new Util();
        context = this;
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        shPrefEditor = mPrefs.edit();
        MyLanguage = mPrefs.getInt("MyLanguage", -1);
        StartInLastLocation = mPrefs.getInt("StartInLastLocation", 1);
        if(StartInLastLocation == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && newVersion == false)/*check if book and chapter are 0 so this is the first time the user open the application so don't go to the last location*/
        {
            goToLastLocation();
        }

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
            util.languageDialog(context);
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

    // for toolbar
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.config_actionbar, (android.view.Menu) menu);
        return true;
    }

    // for toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_config:
                util.showPopupMenuSettings(findViewById(R.id.action_config), HomeActivity.this);
                break;
            default:
                break;
        }
        return true;
    }

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
}
