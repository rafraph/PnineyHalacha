package com.rafraph.pnineyHalachaHashalem;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;

/*jsoup*/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressLint("SetJavaScriptEnabled")
public class TextMain extends AppCompatActivity implements View.OnClickListener//, OnGestureListener
{
    WebView webview;
	public static int[] book_chapter = new int[2];
	boolean cameFromSearch = false, firstTime = true, ChangeChapter = false;
	String searchPosition = null, sectionsForToast = null;
	ImageButton bAutoScroll, bParagraphs, /*bFullScreen,*/ bNext_sec, bPrevious_sec, bNext_page, bPrevious_page, bFindNext, bFindPrevious;
	LinearLayout llMainLayout;
	String stHeadersArr;
	Elements headers;
    String fileName, fileNameOnly, lastFileName = null;
	String[][] chaptersFiles = new String[Util.BOOKS_NUMBER][31];
	private LinearLayout lnrOptions, lnrFindOptions;
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	int scrollY = 0;
	public int BlackBackground=0, SleepScreen=1/*, cbFullScreen=1, cbAssistButtons=1*/;
	boolean bookmark = false;
	Document doc = null;
	static MenuInflater inflater;
	static public Toolbar textMainToolbar;
	public String query, title;
	public String note_id;
	public String audio_id;
	public Resources resources;
//	static byte fullScreenFlag = 0;
	public static byte rotate = 0; 
	public String noteStr = "0";
    public int MyLanguage;

	/*for bookmarks*/
	public List<String> bookmarks_array_names = new ArrayList<String>();
	public EditText result;
	public Spinner spinner1, spinnerAutoScroll;
	public EditText BookmarkName, TextToSearch, TextToDecode;
	public Dialog bookmarkDialog, innerSearchDialog, acronymsDialog, autoScrollDialog;
	String[][] chaptersNames = new String[Util.BOOKS_NUMBER][31];
	String innerSearchText, acronymsText;

	//	static int odd=1;
	public int API;
	static public boolean jumpToSectionFlag = false;

	public int fontSize;
	public String strBookmark, Bookmarks;
	public Util util;
	public static TextView titleTv;
	public static Context context;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("JavascriptInterface")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		loadActivity();
	}//onCreate

	private void loadActivity() 
	{
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
//		cbAssistButtons = mPrefs.getInt("cbAssistButtons", 1);
        MyLanguage = mPrefs.getInt("MyLanguage", 0);

//		if(cbAssistButtons==0)
//			setContentView(R.layout.text_main);
//		else
		setContentView(R.layout.text_main_down);


		textMainToolbar = (Toolbar) findViewById(R.id.textMainToolbar);
		setSupportActionBar(textMainToolbar);
		// Enable the back arrow
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// Set a click listener for the toolbar
		textMainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Handle the back arrow click event
				onBackPressed();
			}
		});

		util = new Util(this);
		firstTime = true;
		book_chapter[0] = -1;
		book_chapter[1] = -1;
		int fromBookmarks = 0;
		lnrOptions = (LinearLayout) findViewById(R.id.lnrOptions);
		lnrFindOptions = (LinearLayout) findViewById(R.id.lnrFindOptions);
		context = this;
		webview = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = webview.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		API = android.os.Build.VERSION.SDK_INT;
		if(API < 19)
			webSettings.setBuiltInZoomControls(true);

		resources = getResources();

		webview.requestFocusFromTouch();

		webview.setWebViewClient(new MyWebViewClient());

		bAutoScroll    = (ImageButton) findViewById(R.id.ibAutoScrool);
		bParagraphs    = (ImageButton) findViewById(R.id.ibChapters);
//		bFullScreen    = (ImageButton) findViewById(R.id.ibFullScreen);
		bNext_sec      = (ImageButton) findViewById(R.id.ibNext);
		bPrevious_sec  = (ImageButton) findViewById(R.id.ibPrevious);
		bNext_page     = (ImageButton) findViewById(R.id.ibNextPage);
		bPrevious_page = (ImageButton) findViewById(R.id.ibPreviousPage);
		llMainLayout   = (LinearLayout) findViewById(R.id.llMainLayout);
		lnrOptions     = (LinearLayout) findViewById(R.id.lnrOptions);
		bFindNext      = (ImageButton) findViewById(R.id.ibFindNext);
		bFindPrevious  = (ImageButton) findViewById(R.id.ibFindPrevious);

		bAutoScroll.setOnClickListener(this);
		bParagraphs.setOnClickListener(this);
//		bFullScreen.setOnClickListener(this);
		bNext_sec.setOnClickListener(this);
		bPrevious_sec.setOnClickListener(this);
		bNext_page.setOnClickListener(this);
		bPrevious_page.setOnClickListener(this);
		bFindNext.setOnClickListener(this);
		bFindPrevious.setOnClickListener(this);

		jumpToSectionFlag = false;

		final Runnable runnableNote = new Runnable()
		{
			public void run()
			{
				// your code here
				String note, content = null;
				int intNoteId;
				final Dialog dialog = new Dialog(context);
				WebView webviewNote;
				WebSettings webSettingsNote;
				BlackBackground = mPrefs.getInt("BlackBackground", 0);
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
				if(BlackBackground == 0)
					content += "<body>";//White background
				else if(BlackBackground == 1)
					content += "<body style=\"background-color:black;color:white\">";//Black background
				ParseTheDoc();
				headers = doc.select("div#ftn"+note_id);
				note = headers.get(0).text();
				if (book_chapter[0] < Util.BOOKS_HEB_NUMBER)/*if this is a hebrew book*/
				{
					content += "<p dir=\"RTL\">" + note + "</p> </body></html>";
				}
				else
				{
					content += "<p dir=\"LTR\">" + note + "</p> </body></html>";
				}

				webviewNote.loadData(content, "text/html; charset=utf-8", "UTF-8");
				webSettingsNote.setDefaultFontSize(fontSize);
				dialog.show();
				
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
				{
				    @Override
				    public void onCancel(DialogInterface dialog) 
				    {
				        //do whatever you want the back key to do
				    	dialog.dismiss();
				    	scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
				    }
				});
			}
		};

		final Runnable runnableAudio = new Runnable()
		{
			public void run()
			{
				// your code here
				try
				{
					Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MyAudio");
					Intent ourIntent = new Intent(TextMain.this, ourClass);

                    ourIntent.putExtra("audio_id", Integer.parseInt(audio_id));
                    ourIntent.putExtra("book_id", book_chapter[0]);
                    ourIntent.putExtra("chapter_id", book_chapter[1]);
					findAllHeaders(ourIntent);
					startActivity(ourIntent);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		};

		webview.addJavascriptInterface(new Object()
		{
			@JavascriptInterface
			public void performClick(String id)
			{
			    scrollSpeed = 0;
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
            }
        }, "audio");

		fillChaptersFiles();


		BlackBackground = mPrefs.getInt("BlackBackground", 0);
//		cbFullScreen = mPrefs.getInt("cbFullScreen", 1);
		
//		inflater = getMenuInflater();

		Bundle extras = getIntent().getExtras();
		if (extras != null) 
		{
			cameFromSearch = extras.getBoolean("cameFromSearch",false);
			searchPosition = extras.getString("searchPosition");
			if(extras.getIntArray("book_chapter") != null)
				book_chapter = extras.getIntArray("book_chapter");
			sectionsForToast = extras.getString("sectionsForToast");

			if(cameFromSearch == true)
			{
				query = extras.getString("query");
				findBookAndChapter();
				webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
				scrollY = 0;
				lnrFindOptions.setVisibility(View.VISIBLE);
			}
			else
			{
				lnrFindOptions.setVisibility(View.GONE);
				book_chapter = extras.getIntArray("book_chapter");
				fromBookmarks = extras.getInt("fromBookmarks");
				if(fromBookmarks == 1)/*came from bookmarks*/
				{
					webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
					scrollY = extras.getInt("bookmarkScrollY");
				}
				else if(book_chapter != null)
				{
					if(book_chapter[0] == 0xFFFF || book_chapter[1] == 0xFFFF)/*go to the last location*/
					{
						bookmark = true;
						book_chapter[0] = mPrefs.getInt("book", 0);
						book_chapter[1] = mPrefs.getInt("chapter", 0);
						webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
						scrollY = mPrefs.getInt("scrollY", 0);
					}
					else/*the regular choice of chapter*/
					{
						bookmark = false;
						scrollY = 0;
						webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
					}
				}
			}
		}
		fontSize = mPrefs.getInt("fontSize", 20);
		if(fontSize > 50)
			fontSize = 20;
		webSettings.setDefaultFontSize(fontSize);

		if(book_chapter[1] == util.lastChapter[book_chapter[0]])
			bNext_sec.setEnabled(false);
		else if(book_chapter[1] == 0)
			bPrevious_sec.setEnabled(false);

		webview.setWebChromeClient(new WebChromeClient() 
		{
			@Override
			public void onProgressChanged(WebView view, int progress) 
			{
				if ( view.getProgress()==100)
				{
					if(jumpToSectionFlag == false)
						jumpToY( scrollY );
				}
			}
		});

		final WebView wv = new WebView(this);
		wv.post(new Runnable() {
			@Override
			public void run() {
				wv.loadUrl(fileName);
			}
		});
	}
	
	public void  setNoteId(String id)
	{
		note_id=id;
	}

	public void  setAudioId(String id)
	{
		audio_id=id;
	}


	private void jumpToY ( int yLocation )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{ 
				if(scrollY != 0)
					webview.scrollTo(0, scrollY);
			}
		}, 400);/*how much time to delay*/
	}

	private void finddelay (final String query  )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{ 
				int a =webview.findAll(query);
				try
				{
					Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
					m.invoke(webview, true);
				}
				catch (Throwable ignored){}
			}
		}, 400);/*how much time to delay*/
	}
	
	private void WhiteTextAfterDelay (  )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{ 
				webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";} myFunction(); ");
				webview.findAll(query);
				try
				{
					Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
					m.invoke(webview, true);
				}
				catch (Throwable ignored){}
			}
		}, 400);/*how much time to delay*/
	}
	
	public void ParseTheDoc()
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

	protected void onStart() 
	{
		super.onStart();
		// The activity is about to become visible.
		titleTv = textMainToolbar.findViewById(R.id.title);
		if(book_chapter[1]==0)
			title = convertBookIdToName(book_chapter[0]);
		else
			title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);
		titleTv.setText(title);

		ImageView actionSearchIv = textMainToolbar.findViewById(R.id.action_search);
		actionSearchIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				innerSearch();
			}
		});
		ImageView addBookmarkIv = textMainToolbar.findViewById(R.id.add_bookmark);
		addBookmarkIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addBookmark();
			}
		});
		ImageView actionConfigIv = textMainToolbar.findViewById(R.id.action_config);
		actionConfigIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopupMenuSettings(findViewById(R.id.action_config));
			}
		});
	}//onStart

	protected void onResume() 
	{
		super.onResume();
		// The activity has become visible (it is now "resumed").

		supportInvalidateOptionsMenu();
		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		SleepScreen = mPrefs.getInt("SleepScreen", 1);

		if(SleepScreen == 0)
		{
			webview.setKeepScreenOn (false);
		}
		else if(SleepScreen == 1)
		{
			webview.setKeepScreenOn (true);
		}
		
//		if(cbAssistButtons != mPrefs.getInt("cbAssistButtons", 1))
//		{
//			loadActivity();
//		}
	}//onResume

	protected void onPause()
	{
		super.onPause();

		scrollY = webview.getScrollY();
		shPrefEditor.putInt("book", book_chapter[0]);
		shPrefEditor.putInt("chapter", book_chapter[1]);
		shPrefEditor.putInt("scrollY", scrollY);
		shPrefEditor.putInt("fontSize", fontSize);

		shPrefEditor.commit();
	}//onPaused

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		BlackBackground = mPrefs.getInt("BlackBackground", 0);

		if(book_chapter[1]==0)
			title = convertBookIdToName(book_chapter[0]);
		else
			title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]); 

		if(BlackBackground == 1)
		{
            //webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";var y = document.getElementsByClassName(\"left\"); y[0].style.display = 'none';} myFunction(); ");
			webview.setBackgroundColor(0xFFFFFF);//black
			llMainLayout.setBackgroundColor(Color.BLACK);
			if(cameFromSearch == true)
			{
				bFindNext.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down_black));
				bFindPrevious.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up_black));
			}

		} else {
			webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
			webview.setBackgroundColor(0x000000);//white
			llMainLayout.setBackgroundColor(Color.WHITE);
			if(cameFromSearch == true)
			{
				bFindNext.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down));
				bFindPrevious.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up));
			}
		}
		return true;
	}//onCreateOptionsMenu

	public void onBackPressed() 
	{
//		if(fullScreenFlag == 1)
//		{
//			fullScreenFlag = 0;
//			getSupportActionBar().show();
//			lnrOptions.setVisibility(View.VISIBLE);
//		}
//		else
//		{
			super.onBackPressed();
//		}
	}

	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
		{
			rotate=2;
		} 
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			rotate=1;
		}
	}
	
	 @Override
	 public boolean dispatchKeyEvent(KeyEvent event) 
	 {
		 int keyCode = event.getKeyCode();
		 int keyAction = event.getAction();
		 switch (keyCode) 
		 {
		 case KeyEvent.KEYCODE_VOLUME_UP:
			 if(keyAction == KeyEvent.ACTION_UP)
			 {
				 webview.pageUp(false);
			 }
			 return true;
		 case KeyEvent.KEYCODE_VOLUME_DOWN:
			 if(keyAction == KeyEvent.ACTION_UP)
			 {
				 webview.pageDown(false);
			 }
			 return true;
		 default:
			 return super.dispatchKeyEvent(event);
		 }
	 }

	 int scrollSpeed=1;
	 private Handler mHandler=new Handler();
	 public Runnable mScrollDown = new Runnable()
	 {
		 public void run()
		 {
			 if(scrollSpeed == 0) // in case of note opened
			 {
				 mHandler.postDelayed(this, 200);
			 }
			 else if(scrollSpeed == -1) // in case that "stop" pressed
			 {
				 webview.scrollBy(0, 0);
			 }
			 else
			 {
				 webview.scrollBy(0, 1);
				 mHandler.postDelayed(this, 200/scrollSpeed);
			 }
		 }
	 };
	 	 
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view) 
	{
		String currentChapter;
		switch(view.getId())
		{
		case R.id.ibAutoScrool:
			showAutoScrollMenu(view);
			break;
		case R.id.ibChapters:
			findHeaders();
			showPopupMenu(view);
			break;

//		case R.id.ibFullScreen:
//			cbFullScreen = mPrefs.getInt("cbFullScreen", 1);
//			if(cbFullScreen == 0)
//				lnrOptions.setVisibility(View.GONE);
//			getSupportActionBar().hide();
//			Toast.makeText(getApplicationContext(), "לחץ על כפתור 'חזור' כדי לצאת ממסך מלא", Toast.LENGTH_LONG).show();
//			fullScreenFlag = 1;
//			break;
			
		case R.id.ibNext:
			cameFromSearch = false;
			scrollY = 0;/*In order to jump to the beginning of the chapter*/
			currentChapter = getClearUrl();
			getTheArrayLocation(currentChapter);
			book_chapter[1] += 1;
			webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
			title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);
			titleTv.setText(title);
			if(book_chapter[1] == util.lastChapter[book_chapter[0]])
				bNext_sec.setEnabled(false);
			else
				bPrevious_sec.setEnabled(true);
			ChangeChapter = true;

			shPrefEditor.putInt("fontSize", fontSize);/*in order to keep the fontSize when moving to next chapter*/

			break;
			
		case R.id.ibPrevious:
			cameFromSearch = false;
			scrollY = 0;/*In order to jump to the beginning of the chapter*/
			currentChapter = getClearUrl();
			getTheArrayLocation(currentChapter);
			book_chapter[1] -= 1;
			webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
			if(book_chapter[1] == 0)
				title = convertBookIdToName(book_chapter[0]);
			else
				title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);
			titleTv.setText(title);
			if(book_chapter[1] == 0)
				bPrevious_sec.setEnabled(false);
			else
				bNext_sec.setEnabled(true);
			ChangeChapter = true;

			shPrefEditor.putInt("fontSize", fontSize);/*in order to keep the fontSize when moving to next chapter*/
			break;
		
		case R.id.ibNextPage:
			webview.pageDown(false);
			break;
		
		case R.id.ibPreviousPage:
			webview.pageUp(false);
			break;
		
		case R.id.ibFindNext:
			webview.findNext(true);
			break;
		
		case R.id.ibFindPrevious:
			webview.findNext(false);
			break;
		}

	}//onClick

	public void addItemsOnSpinner() 
	{		 
		List<String> list = new ArrayList<String>();
		int i, index = 0, index_end=0;

		Bookmarks = mPrefs.getString("Bookmarks", "");
		list.add("");/*this is for the first item that need to be hidden in order to have the ability to choose the first item*/

		while((index = Bookmarks.indexOf("," , index)) != -1)
		{
			index++;
			index_end = Bookmarks.indexOf("," , index);
			list.add(Bookmarks.substring(index, index_end));
			for(i=0;i<4;i++)/*skip all other fields*/
				index = Bookmarks.indexOf("," , index) + 1;
		}

		int hidingItemIndex = 0;
		CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, list, hidingItemIndex);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);
	}

	private void showPopupMenu(View v)
	{
		PopupMenu popupMenu = new PopupMenu(TextMain.this, v);

		//popupMenu.
		for(int i = 0; i < headers.size(); i++)//fill the menu list
		{
			popupMenu.getMenu().add(0,i,i,headers.get(i).text());
		}

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				int id = item.getItemId()+1;
				String s=fileName+ "#" + id;
//				String s2=fileName+ "#" + (id+1);
//				webview.loadUrl(s2);/*Workaround to fix the bug of jumping to same anchor twice*/
				webview.loadUrl(s);
				jumpToSectionFlag = true;
				return true;
			}
		});

		popupMenu.show();
	}

	private void findHeaders()
	{
		String prefix;
		fileName = getClearUrl();
		prefix = "file:///android_asset/";
		fileNameOnly = fileName.substring(prefix.length());
		try 
		{
			InputStream is = getAssets().open(fileNameOnly);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String input = new String(buffer);

			Document doc = Jsoup.parse(input);
			headers = doc.select("h2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTheArrayLocation(String Chapter)
	{
		int perek, seif;
		for (perek = 0; perek < chaptersFiles.length; perek++)
		{
			for (seif = 0; seif < chaptersFiles[perek].length; seif++)
			{
				if(Chapter.equals(chaptersFiles[perek][seif]) == true)
				{
					book_chapter[0] = perek;
					book_chapter[1] = seif;
					return;
				}
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

    private void findAllHeaders(Intent ourIntent)
    {
        String prefix, a;
		int j;
		ArrayList<String> sections = new ArrayList<String>();
		ArrayList<String> sections2 = new ArrayList<String>();
        fileName = getClearUrl();
        prefix = "file:///android_asset/";
        fileNameOnly = fileName.substring(prefix.length());
		fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("_")+1);

        for(int i=1; i<=util.lastChapter[book_chapter[0]]; i++) {
            try {
                InputStream is;
                if (book_chapter[0] == Util.KASHRUT_B)
                    is = getAssets().open(fileNameOnly+(i+19)+".html");
                else
                    is = getAssets().open(fileNameOnly+i+".html");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String input = new String(buffer);

                Document doc = Jsoup.parse(input);
                headers = doc.select("h2");

				sections.clear();
				for(j = 0; j < headers.size(); j++)
					sections.add(headers.get(j).text());

				String name;
				if (book_chapter[0] == Util.KASHRUT_B)
					name = "sections_"+(i+19);
				else
					name = "sections_"+i;

				// Creating a new local copy of the current list.
				ArrayList<String> newList = new ArrayList<>(sections);

				ourIntent.putStringArrayListExtra(name, newList);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

	private void showAutoScrollMenu(View v)
	{
		PopupMenu popupMenu = new PopupMenu(TextMain.this, v);

		List<String> autoScrollText = util.getTextArray(Util.TextArrayEnum.AUTO_SCROLL_MENU);
		popupMenu.getMenu().add(0,0,0,autoScrollText.get(0));
		popupMenu.getMenu().add(0,1,1,autoScrollText.get(1));
		popupMenu.getMenu().add(0,2,2,autoScrollText.get(2));
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				int id = item.getItemId();
				if(id == 0){
					scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
					runOnUiThread(mScrollDown);
				}
				else if (id == 1){
					scrollSpeed = -1;
				}
				else if (id == 2){
					autoScrollSpeedDialog();
				}
				return true;
			}
		});

		popupMenu.show();
	}

	private void fillChaptersFiles()/*list of all assets*/
	{
		/*BRACHOT*/
		chaptersFiles[Util.BRACHOT][0] = "file:///android_asset/brachot_tochen.html";
		chaptersFiles[Util.BRACHOT][1] = "file:///android_asset/brachot_1.html";
		chaptersFiles[Util.BRACHOT][2] = "file:///android_asset/brachot_2.html";
		chaptersFiles[Util.BRACHOT][3] = "file:///android_asset/brachot_3.html";
		chaptersFiles[Util.BRACHOT][4] = "file:///android_asset/brachot_4.html";
		chaptersFiles[Util.BRACHOT][5] = "file:///android_asset/brachot_5.html";
		chaptersFiles[Util.BRACHOT][6] = "file:///android_asset/brachot_6.html";
		chaptersFiles[Util.BRACHOT][7] = "file:///android_asset/brachot_7.html";
		chaptersFiles[Util.BRACHOT][8] = "file:///android_asset/brachot_8.html";
		chaptersFiles[Util.BRACHOT][9] = "file:///android_asset/brachot_9.html";
		chaptersFiles[Util.BRACHOT][10] = "file:///android_asset/brachot_10.html";
		chaptersFiles[Util.BRACHOT][11] = "file:///android_asset/brachot_11.html";
		chaptersFiles[Util.BRACHOT][12] = "file:///android_asset/brachot_12.html";
		chaptersFiles[Util.BRACHOT][13] = "file:///android_asset/brachot_13.html";
		chaptersFiles[Util.BRACHOT][14] = "file:///android_asset/brachot_14.html";
		chaptersFiles[Util.BRACHOT][15] = "file:///android_asset/brachot_15.html";
		chaptersFiles[Util.BRACHOT][16] = "file:///android_asset/brachot_16.html";
		chaptersFiles[Util.BRACHOT][17] = "file:///android_asset/brachot_17.html";
		chaptersFiles[Util.BRACHOT][18] = "file:///android_asset/brachot_18.html";
		/*GIYUR*/
		chaptersFiles[Util.GIYUR][0] = "file:///android_asset/giyur_tochen.html";
		chaptersFiles[Util.GIYUR][1] = "file:///android_asset/giyur_1.html";
		chaptersFiles[Util.GIYUR][2] = "file:///android_asset/giyur_2.html";
		chaptersFiles[Util.GIYUR][3] = "file:///android_asset/giyur_3.html";
		chaptersFiles[Util.GIYUR][4] = "file:///android_asset/giyur_4.html";
		chaptersFiles[Util.GIYUR][5] = "file:///android_asset/giyur_5.html";
		chaptersFiles[Util.GIYUR][6] = "file:///android_asset/giyur_6.html";
		chaptersFiles[Util.GIYUR][7] = "file:///android_asset/giyur_7.html";
		chaptersFiles[Util.GIYUR][8] = "file:///android_asset/giyur_8.html";
		chaptersFiles[Util.GIYUR][9] = "file:///android_asset/giyur_9.html";
		/*HAAMVEHAAREZ*/
		chaptersFiles[Util.HAAMVEHAAREZ][0] = "file:///android_asset/haamvehaarez_tochen.html";
		chaptersFiles[Util.HAAMVEHAAREZ][1] = "file:///android_asset/haamvehaarez_1.html";
		chaptersFiles[Util.HAAMVEHAAREZ][2] = "file:///android_asset/haamvehaarez_2.html";
		chaptersFiles[Util.HAAMVEHAAREZ][3] = "file:///android_asset/haamvehaarez_3.html";
		chaptersFiles[Util.HAAMVEHAAREZ][4] = "file:///android_asset/haamvehaarez_4.html";
		chaptersFiles[Util.HAAMVEHAAREZ][5] = "file:///android_asset/haamvehaarez_5.html";
		chaptersFiles[Util.HAAMVEHAAREZ][6] = "file:///android_asset/haamvehaarez_6.html";
		chaptersFiles[Util.HAAMVEHAAREZ][7] = "file:///android_asset/haamvehaarez_7.html";
		chaptersFiles[Util.HAAMVEHAAREZ][8] = "file:///android_asset/haamvehaarez_8.html";
		chaptersFiles[Util.HAAMVEHAAREZ][9] = "file:///android_asset/haamvehaarez_9.html";
		chaptersFiles[Util.HAAMVEHAAREZ][10] = "file:///android_asset/haamvehaarez_10.html";
		/*ZMANIM*/
		chaptersFiles[Util.ZMANIM][0] = "file:///android_asset/zmanim_tochen.html";
		chaptersFiles[Util.ZMANIM][1] = "file:///android_asset/zmanim_1.html";
		chaptersFiles[Util.ZMANIM][2] = "file:///android_asset/zmanim_2.html";
		chaptersFiles[Util.ZMANIM][3] = "file:///android_asset/zmanim_3.html";
		chaptersFiles[Util.ZMANIM][4] = "file:///android_asset/zmanim_4.html";
		chaptersFiles[Util.ZMANIM][5] = "file:///android_asset/zmanim_5.html";
		chaptersFiles[Util.ZMANIM][6] = "file:///android_asset/zmanim_6.html";
		chaptersFiles[Util.ZMANIM][7] = "file:///android_asset/zmanim_7.html";
		chaptersFiles[Util.ZMANIM][8] = "file:///android_asset/zmanim_8.html";
		chaptersFiles[Util.ZMANIM][9] = "file:///android_asset/zmanim_9.html";
		chaptersFiles[Util.ZMANIM][10] = "file:///android_asset/zmanim_10.html";
		chaptersFiles[Util.ZMANIM][11] = "file:///android_asset/zmanim_11.html";
		chaptersFiles[Util.ZMANIM][12] = "file:///android_asset/zmanim_12.html";
		chaptersFiles[Util.ZMANIM][13] = "file:///android_asset/zmanim_13.html";
		chaptersFiles[Util.ZMANIM][14] = "file:///android_asset/zmanim_14.html";
		chaptersFiles[Util.ZMANIM][15] = "file:///android_asset/zmanim_15.html";
		chaptersFiles[Util.ZMANIM][16] = "file:///android_asset/zmanim_16.html";
		chaptersFiles[Util.ZMANIM][17] = "file:///android_asset/zmanim_17.html";
		/*TAHARAT*/
		chaptersFiles[Util.TAHARAT][0] = "file:///android_asset/taharat_tochen.html";
		chaptersFiles[Util.TAHARAT][1] = "file:///android_asset/taharat_1.html";
		chaptersFiles[Util.TAHARAT][2] = "file:///android_asset/taharat_2.html";
		chaptersFiles[Util.TAHARAT][3] = "file:///android_asset/taharat_3.html";
		chaptersFiles[Util.TAHARAT][4] = "file:///android_asset/taharat_4.html";
		chaptersFiles[Util.TAHARAT][5] = "file:///android_asset/taharat_5.html";
		chaptersFiles[Util.TAHARAT][6] = "file:///android_asset/taharat_6.html";
		chaptersFiles[Util.TAHARAT][7] = "file:///android_asset/taharat_7.html";
		chaptersFiles[Util.TAHARAT][8] = "file:///android_asset/taharat_8.html";
		chaptersFiles[Util.TAHARAT][9] = "file:///android_asset/taharat_9.html";
		chaptersFiles[Util.TAHARAT][10] = "file:///android_asset/taharat_10.html";
		/*YAMIM*/
		chaptersFiles[Util.YAMIM][0] = "file:///android_asset/yamim_tochen.html";
		chaptersFiles[Util.YAMIM][1] = "file:///android_asset/yamim_1.html";
		chaptersFiles[Util.YAMIM][2] = "file:///android_asset/yamim_2.html";
		chaptersFiles[Util.YAMIM][3] = "file:///android_asset/yamim_3.html";
		chaptersFiles[Util.YAMIM][4] = "file:///android_asset/yamim_4.html";
		chaptersFiles[Util.YAMIM][5] = "file:///android_asset/yamim_5.html";
		chaptersFiles[Util.YAMIM][6] = "file:///android_asset/yamim_6.html";
		chaptersFiles[Util.YAMIM][7] = "file:///android_asset/yamim_7.html";
		chaptersFiles[Util.YAMIM][8] = "file:///android_asset/yamim_8.html";
		chaptersFiles[Util.YAMIM][9] = "file:///android_asset/yamim_9.html";
		chaptersFiles[Util.YAMIM][10] = "file:///android_asset/yamim_10.html";
		/*KASHRUT_A*/
		chaptersFiles[Util.KASHRUT_A][0] = "file:///android_asset/kashrut_a_tochen.html";
		chaptersFiles[Util.KASHRUT_A][1] = "file:///android_asset/kashrut_1.html";
		chaptersFiles[Util.KASHRUT_A][2] = "file:///android_asset/kashrut_2.html";
		chaptersFiles[Util.KASHRUT_A][3] = "file:///android_asset/kashrut_3.html";
		chaptersFiles[Util.KASHRUT_A][4] = "file:///android_asset/kashrut_4.html";
		chaptersFiles[Util.KASHRUT_A][5] = "file:///android_asset/kashrut_5.html";
		chaptersFiles[Util.KASHRUT_A][6] = "file:///android_asset/kashrut_6.html";
		chaptersFiles[Util.KASHRUT_A][7] = "file:///android_asset/kashrut_7.html";
		chaptersFiles[Util.KASHRUT_A][8] = "file:///android_asset/kashrut_8.html";
		chaptersFiles[Util.KASHRUT_A][9] = "file:///android_asset/kashrut_9.html";
		chaptersFiles[Util.KASHRUT_A][10] = "file:///android_asset/kashrut_10.html";
		chaptersFiles[Util.KASHRUT_A][11] = "file:///android_asset/kashrut_11.html";
		chaptersFiles[Util.KASHRUT_A][12] = "file:///android_asset/kashrut_12.html";
		chaptersFiles[Util.KASHRUT_A][13] = "file:///android_asset/kashrut_13.html";
		chaptersFiles[Util.KASHRUT_A][14] = "file:///android_asset/kashrut_14.html";
		chaptersFiles[Util.KASHRUT_A][15] = "file:///android_asset/kashrut_15.html";
		chaptersFiles[Util.KASHRUT_A][16] = "file:///android_asset/kashrut_16.html";
		chaptersFiles[Util.KASHRUT_A][17] = "file:///android_asset/kashrut_17.html";
		chaptersFiles[Util.KASHRUT_A][18] = "file:///android_asset/kashrut_18.html";
		chaptersFiles[Util.KASHRUT_A][19] = "file:///android_asset/kashrut_19.html";
		/*KASHRUT_B*/
		chaptersFiles[Util.KASHRUT_B][0] = "file:///android_asset/kashrut_b_tochen.html";
		chaptersFiles[Util.KASHRUT_B][1] = "file:///android_asset/kashrut_20.html";
		chaptersFiles[Util.KASHRUT_B][2] = "file:///android_asset/kashrut_21.html";
		chaptersFiles[Util.KASHRUT_B][3] = "file:///android_asset/kashrut_22.html";
		chaptersFiles[Util.KASHRUT_B][4] = "file:///android_asset/kashrut_23.html";
		chaptersFiles[Util.KASHRUT_B][5] = "file:///android_asset/kashrut_24.html";
		chaptersFiles[Util.KASHRUT_B][6] = "file:///android_asset/kashrut_25.html";
		chaptersFiles[Util.KASHRUT_B][7] = "file:///android_asset/kashrut_26.html";
		chaptersFiles[Util.KASHRUT_B][8] = "file:///android_asset/kashrut_27.html";
		chaptersFiles[Util.KASHRUT_B][9] = "file:///android_asset/kashrut_28.html";
		chaptersFiles[Util.KASHRUT_B][10] = "file:///android_asset/kashrut_29.html";
		chaptersFiles[Util.KASHRUT_B][11] = "file:///android_asset/kashrut_30.html";
		chaptersFiles[Util.KASHRUT_B][12] = "file:///android_asset/kashrut_31.html";
		chaptersFiles[Util.KASHRUT_B][13] = "file:///android_asset/kashrut_32.html";
		chaptersFiles[Util.KASHRUT_B][14] = "file:///android_asset/kashrut_33.html";
		chaptersFiles[Util.KASHRUT_B][15] = "file:///android_asset/kashrut_34.html";
		chaptersFiles[Util.KASHRUT_B][16] = "file:///android_asset/kashrut_35.html";
		chaptersFiles[Util.KASHRUT_B][17] = "file:///android_asset/kashrut_36.html";
		chaptersFiles[Util.KASHRUT_B][18] = "file:///android_asset/kashrut_37.html";
		chaptersFiles[Util.KASHRUT_B][19] = "file:///android_asset/kashrut_38.html";
		/*LIKUTIM_A*/
		chaptersFiles[Util.LIKUTIM_A][0] = "file:///android_asset/likutim_a_tochen.html";
		chaptersFiles[Util.LIKUTIM_A][1] = "file:///android_asset/likutim_a_1.html";
		chaptersFiles[Util.LIKUTIM_A][2] = "file:///android_asset/likutim_a_2.html";
		chaptersFiles[Util.LIKUTIM_A][3] = "file:///android_asset/likutim_a_3.html";
		chaptersFiles[Util.LIKUTIM_A][4] = "file:///android_asset/likutim_a_4.html";
		chaptersFiles[Util.LIKUTIM_A][5] = "file:///android_asset/likutim_a_5.html";
		chaptersFiles[Util.LIKUTIM_A][6] = "file:///android_asset/likutim_a_6.html";
		chaptersFiles[Util.LIKUTIM_A][7] = "file:///android_asset/likutim_a_7.html";
		chaptersFiles[Util.LIKUTIM_A][8] = "file:///android_asset/likutim_a_8.html";
		chaptersFiles[Util.LIKUTIM_A][9] = "file:///android_asset/likutim_a_9.html";
		chaptersFiles[Util.LIKUTIM_A][10] = "file:///android_asset/likutim_a_10.html";
		chaptersFiles[Util.LIKUTIM_A][11] = "file:///android_asset/likutim_a_11.html";
        chaptersFiles[Util.LIKUTIM_A][12] = "file:///android_asset/likutim_a_12.html";
        chaptersFiles[Util.LIKUTIM_A][13] = "file:///android_asset/likutim_a_13.html";
		/*LIKUTIM_B*/
		chaptersFiles[Util.LIKUTIM_B][0] = "file:///android_asset/likutim_b_tochen.html";
		chaptersFiles[Util.LIKUTIM_B][1] = "file:///android_asset/likutim_b_1.html";
		chaptersFiles[Util.LIKUTIM_B][2] = "file:///android_asset/likutim_b_2.html";
		chaptersFiles[Util.LIKUTIM_B][3] = "file:///android_asset/likutim_b_3.html";
		chaptersFiles[Util.LIKUTIM_B][4] = "file:///android_asset/likutim_b_4.html";
		chaptersFiles[Util.LIKUTIM_B][5] = "file:///android_asset/likutim_b_5.html";
		chaptersFiles[Util.LIKUTIM_B][6] = "file:///android_asset/likutim_b_6.html";
		chaptersFiles[Util.LIKUTIM_B][7] = "file:///android_asset/likutim_b_7.html";
		chaptersFiles[Util.LIKUTIM_B][8] = "file:///android_asset/likutim_b_8.html";
		chaptersFiles[Util.LIKUTIM_B][9] = "file:///android_asset/likutim_b_9.html";
		chaptersFiles[Util.LIKUTIM_B][10] = "file:///android_asset/likutim_b_10.html";
		chaptersFiles[Util.LIKUTIM_B][11] = "file:///android_asset/likutim_b_11.html";
		chaptersFiles[Util.LIKUTIM_B][12] = "file:///android_asset/likutim_b_12.html";
		chaptersFiles[Util.LIKUTIM_B][13] = "file:///android_asset/likutim_b_13.html";
		chaptersFiles[Util.LIKUTIM_B][14] = "file:///android_asset/likutim_b_14.html";
		chaptersFiles[Util.LIKUTIM_B][15] = "file:///android_asset/likutim_b_15.html";
		chaptersFiles[Util.LIKUTIM_B][16] = "file:///android_asset/likutim_b_16.html";
		/*MISHPACHA*/
		chaptersFiles[Util.MISHPACHA][0] = "file:///android_asset/mishpacha_tochen.html";
		chaptersFiles[Util.MISHPACHA][1] = "file:///android_asset/mishpacha_1.html";
		chaptersFiles[Util.MISHPACHA][2] = "file:///android_asset/mishpacha_2.html";
		chaptersFiles[Util.MISHPACHA][3] = "file:///android_asset/mishpacha_3.html";
		chaptersFiles[Util.MISHPACHA][4] = "file:///android_asset/mishpacha_4.html";
		chaptersFiles[Util.MISHPACHA][5] = "file:///android_asset/mishpacha_5.html";
		chaptersFiles[Util.MISHPACHA][6] = "file:///android_asset/mishpacha_6.html";
		chaptersFiles[Util.MISHPACHA][7] = "file:///android_asset/mishpacha_7.html";
		chaptersFiles[Util.MISHPACHA][8] = "file:///android_asset/mishpacha_8.html";
		chaptersFiles[Util.MISHPACHA][9] = "file:///android_asset/mishpacha_9.html";
		chaptersFiles[Util.MISHPACHA][10] = "file:///android_asset/mishpacha_10.html";
		/*MOADIM*/
		chaptersFiles[Util.MOADIM][0] = "file:///android_asset/moadim_tochen.html";
		chaptersFiles[Util.MOADIM][1] = "file:///android_asset/moadim_1.html";
		chaptersFiles[Util.MOADIM][2] = "file:///android_asset/moadim_2.html";
		chaptersFiles[Util.MOADIM][3] = "file:///android_asset/moadim_3.html";
		chaptersFiles[Util.MOADIM][4] = "file:///android_asset/moadim_4.html";
		chaptersFiles[Util.MOADIM][5] = "file:///android_asset/moadim_5.html";
		chaptersFiles[Util.MOADIM][6] = "file:///android_asset/moadim_6.html";
		chaptersFiles[Util.MOADIM][7] = "file:///android_asset/moadim_7.html";
		chaptersFiles[Util.MOADIM][8] = "file:///android_asset/moadim_8.html";
		chaptersFiles[Util.MOADIM][9] = "file:///android_asset/moadim_9.html";
		chaptersFiles[Util.MOADIM][10] = "file:///android_asset/moadim_10.html";
		chaptersFiles[Util.MOADIM][11] = "file:///android_asset/moadim_11.html";
		chaptersFiles[Util.MOADIM][12] = "file:///android_asset/moadim_12.html";
		chaptersFiles[Util.MOADIM][13] = "file:///android_asset/moadim_13.html";
		/*SUCOT*/
		chaptersFiles[Util.SUCOT][0] = "file:///android_asset/sucot_tochen.html";
		chaptersFiles[Util.SUCOT][1] = "file:///android_asset/sucot_1.html";
		chaptersFiles[Util.SUCOT][2] = "file:///android_asset/sucot_2.html";
		chaptersFiles[Util.SUCOT][3] = "file:///android_asset/sucot_3.html";
		chaptersFiles[Util.SUCOT][4] = "file:///android_asset/sucot_4.html";
		chaptersFiles[Util.SUCOT][5] = "file:///android_asset/sucot_5.html";
		chaptersFiles[Util.SUCOT][6] = "file:///android_asset/sucot_6.html";
		chaptersFiles[Util.SUCOT][7] = "file:///android_asset/sucot_7.html";
		chaptersFiles[Util.SUCOT][8] = "file:///android_asset/sucot_8.html";
		/*PESACH*/
		chaptersFiles[Util.PESACH][0] = "file:///android_asset/pesach_tochen.html";
		chaptersFiles[Util.PESACH][1] = "file:///android_asset/pesach_1.html";
		chaptersFiles[Util.PESACH][2] = "file:///android_asset/pesach_2.html";
		chaptersFiles[Util.PESACH][3] = "file:///android_asset/pesach_3.html";
		chaptersFiles[Util.PESACH][4] = "file:///android_asset/pesach_4.html";
		chaptersFiles[Util.PESACH][5] = "file:///android_asset/pesach_5.html";
		chaptersFiles[Util.PESACH][6] = "file:///android_asset/pesach_6.html";
		chaptersFiles[Util.PESACH][7] = "file:///android_asset/pesach_7.html";
		chaptersFiles[Util.PESACH][8] = "file:///android_asset/pesach_8.html";
		chaptersFiles[Util.PESACH][9] = "file:///android_asset/pesach_9.html";
		chaptersFiles[Util.PESACH][10] = "file:///android_asset/pesach_10.html";
		chaptersFiles[Util.PESACH][11] = "file:///android_asset/pesach_11.html";
		chaptersFiles[Util.PESACH][12] = "file:///android_asset/pesach_12.html";
		chaptersFiles[Util.PESACH][13] = "file:///android_asset/pesach_13.html";
		chaptersFiles[Util.PESACH][14] = "file:///android_asset/pesach_14.html";
		chaptersFiles[Util.PESACH][15] = "file:///android_asset/pesach_15.html";
		chaptersFiles[Util.PESACH][16] = "file:///android_asset/pesach_16.html";
		/*SHVIIT*/
		chaptersFiles[Util.SHVIIT][0] = "file:///android_asset/shviit_tochen.html";
		chaptersFiles[Util.SHVIIT][1] = "file:///android_asset/shviit_1.html";
		chaptersFiles[Util.SHVIIT][2] = "file:///android_asset/shviit_2.html";
		chaptersFiles[Util.SHVIIT][3] = "file:///android_asset/shviit_3.html";
		chaptersFiles[Util.SHVIIT][4] = "file:///android_asset/shviit_4.html";
		chaptersFiles[Util.SHVIIT][5] = "file:///android_asset/shviit_5.html";
		chaptersFiles[Util.SHVIIT][6] = "file:///android_asset/shviit_6.html";
		chaptersFiles[Util.SHVIIT][7] = "file:///android_asset/shviit_7.html";
		chaptersFiles[Util.SHVIIT][8] = "file:///android_asset/shviit_8.html";
		chaptersFiles[Util.SHVIIT][9] = "file:///android_asset/shviit_9.html";
		chaptersFiles[Util.SHVIIT][10] = "file:///android_asset/shviit_10.html";
		chaptersFiles[Util.SHVIIT][11] = "file:///android_asset/shviit_11.html";
		/*SHABAT*/
		chaptersFiles[Util.SHABAT][0] = "file:///android_asset/shabat_tochen.html";
		chaptersFiles[Util.SHABAT][1] = "file:///android_asset/shabat_1.html";
		chaptersFiles[Util.SHABAT][2] = "file:///android_asset/shabat_2.html";
		chaptersFiles[Util.SHABAT][3] = "file:///android_asset/shabat_3.html";
		chaptersFiles[Util.SHABAT][4] = "file:///android_asset/shabat_4.html";
		chaptersFiles[Util.SHABAT][5] = "file:///android_asset/shabat_5.html";
		chaptersFiles[Util.SHABAT][6] = "file:///android_asset/shabat_6.html";
		chaptersFiles[Util.SHABAT][7] = "file:///android_asset/shabat_7.html";
		chaptersFiles[Util.SHABAT][8] = "file:///android_asset/shabat_8.html";
		chaptersFiles[Util.SHABAT][9] = "file:///android_asset/shabat_9.html";
		chaptersFiles[Util.SHABAT][10] = "file:///android_asset/shabat_10.html";
		chaptersFiles[Util.SHABAT][11] = "file:///android_asset/shabat_11.html";
		chaptersFiles[Util.SHABAT][12] = "file:///android_asset/shabat_12.html";
		chaptersFiles[Util.SHABAT][13] = "file:///android_asset/shabat_13.html";
		chaptersFiles[Util.SHABAT][14] = "file:///android_asset/shabat_14.html";
		chaptersFiles[Util.SHABAT][15] = "file:///android_asset/shabat_15.html";
		chaptersFiles[Util.SHABAT][16] = "file:///android_asset/shabat_16.html";
		chaptersFiles[Util.SHABAT][17] = "file:///android_asset/shabat_17.html";
		chaptersFiles[Util.SHABAT][18] = "file:///android_asset/shabat_18.html";
		chaptersFiles[Util.SHABAT][19] = "file:///android_asset/shabat_19.html";
		chaptersFiles[Util.SHABAT][20] = "file:///android_asset/shabat_20.html";
		chaptersFiles[Util.SHABAT][21] = "file:///android_asset/shabat_21.html";
		chaptersFiles[Util.SHABAT][22] = "file:///android_asset/shabat_22.html";
		chaptersFiles[Util.SHABAT][23] = "file:///android_asset/shabat_23.html";
		chaptersFiles[Util.SHABAT][24] = "file:///android_asset/shabat_24.html";
		chaptersFiles[Util.SHABAT][25] = "file:///android_asset/shabat_25.html";
		chaptersFiles[Util.SHABAT][26] = "file:///android_asset/shabat_26.html";
		chaptersFiles[Util.SHABAT][27] = "file:///android_asset/shabat_27.html";
		chaptersFiles[Util.SHABAT][28] = "file:///android_asset/shabat_28.html";
		chaptersFiles[Util.SHABAT][29] = "file:///android_asset/shabat_29.html";
		chaptersFiles[Util.SHABAT][30] = "file:///android_asset/shabat_30.html";
		/*SIMCHAT*/
		chaptersFiles[Util.SIMCHAT][0] = "file:///android_asset/simchat_tochen.html";
		chaptersFiles[Util.SIMCHAT][1] = "file:///android_asset/simchat_1.html";
		chaptersFiles[Util.SIMCHAT][2] = "file:///android_asset/simchat_2.html";
		chaptersFiles[Util.SIMCHAT][3] = "file:///android_asset/simchat_3.html";
		chaptersFiles[Util.SIMCHAT][4] = "file:///android_asset/simchat_4.html";
		chaptersFiles[Util.SIMCHAT][5] = "file:///android_asset/simchat_5.html";
		chaptersFiles[Util.SIMCHAT][6] = "file:///android_asset/simchat_6.html";
		chaptersFiles[Util.SIMCHAT][7] = "file:///android_asset/simchat_7.html";
		chaptersFiles[Util.SIMCHAT][8] = "file:///android_asset/simchat_8.html";
		chaptersFiles[Util.SIMCHAT][9] = "file:///android_asset/simchat_9.html";
		chaptersFiles[Util.SIMCHAT][10] = "file:///android_asset/simchat_10.html";

		/*TEFILA*/
		chaptersFiles[Util.TEFILA][0] = "file:///android_asset/tefila_tochen.html";
		chaptersFiles[Util.TEFILA][1] = "file:///android_asset/tefila_1.html";
		chaptersFiles[Util.TEFILA][2] = "file:///android_asset/tefila_2.html";
		chaptersFiles[Util.TEFILA][3] = "file:///android_asset/tefila_3.html";
		chaptersFiles[Util.TEFILA][4] = "file:///android_asset/tefila_4.html";
		chaptersFiles[Util.TEFILA][5] = "file:///android_asset/tefila_5.html";
		chaptersFiles[Util.TEFILA][6] = "file:///android_asset/tefila_6.html";
		chaptersFiles[Util.TEFILA][7] = "file:///android_asset/tefila_7.html";
		chaptersFiles[Util.TEFILA][8] = "file:///android_asset/tefila_8.html";
		chaptersFiles[Util.TEFILA][9] = "file:///android_asset/tefila_9.html";
		chaptersFiles[Util.TEFILA][10] = "file:///android_asset/tefila_10.html";
		chaptersFiles[Util.TEFILA][11] = "file:///android_asset/tefila_11.html";
		chaptersFiles[Util.TEFILA][12] = "file:///android_asset/tefila_12.html";
		chaptersFiles[Util.TEFILA][13] = "file:///android_asset/tefila_13.html";
		chaptersFiles[Util.TEFILA][14] = "file:///android_asset/tefila_14.html";
		chaptersFiles[Util.TEFILA][15] = "file:///android_asset/tefila_15.html";
		chaptersFiles[Util.TEFILA][16] = "file:///android_asset/tefila_16.html";
		chaptersFiles[Util.TEFILA][17] = "file:///android_asset/tefila_17.html";
		chaptersFiles[Util.TEFILA][18] = "file:///android_asset/tefila_18.html";
		chaptersFiles[Util.TEFILA][19] = "file:///android_asset/tefila_19.html";
		chaptersFiles[Util.TEFILA][20] = "file:///android_asset/tefila_20.html";
		chaptersFiles[Util.TEFILA][21] = "file:///android_asset/tefila_21.html";
		chaptersFiles[Util.TEFILA][22] = "file:///android_asset/tefila_22.html";
		chaptersFiles[Util.TEFILA][23] = "file:///android_asset/tefila_23.html";
		chaptersFiles[Util.TEFILA][24] = "file:///android_asset/tefila_24.html";
		chaptersFiles[Util.TEFILA][25] = "file:///android_asset/tefila_25.html";
		chaptersFiles[Util.TEFILA][26] = "file:///android_asset/tefila_26.html";
		/*TEFILAT_NASHIM*/
		chaptersFiles[Util.TEFILAT_NASHIM][0] = "file:///android_asset/tefilat_nashim_tochen.html";
		chaptersFiles[Util.TEFILAT_NASHIM][1] = "file:///android_asset/tefilat_nashim_1.html";
		chaptersFiles[Util.TEFILAT_NASHIM][2] = "file:///android_asset/tefilat_nashim_2.html";
		chaptersFiles[Util.TEFILAT_NASHIM][3] = "file:///android_asset/tefilat_nashim_3.html";
		chaptersFiles[Util.TEFILAT_NASHIM][4] = "file:///android_asset/tefilat_nashim_4.html";
		chaptersFiles[Util.TEFILAT_NASHIM][5] = "file:///android_asset/tefilat_nashim_5.html";
		chaptersFiles[Util.TEFILAT_NASHIM][6] = "file:///android_asset/tefilat_nashim_6.html";
		chaptersFiles[Util.TEFILAT_NASHIM][7] = "file:///android_asset/tefilat_nashim_7.html";
		chaptersFiles[Util.TEFILAT_NASHIM][8] = "file:///android_asset/tefilat_nashim_8.html";
		chaptersFiles[Util.TEFILAT_NASHIM][9] = "file:///android_asset/tefilat_nashim_9.html";
		chaptersFiles[Util.TEFILAT_NASHIM][10] = "file:///android_asset/tefilat_nashim_10.html";
		chaptersFiles[Util.TEFILAT_NASHIM][11] = "file:///android_asset/tefilat_nashim_11.html";
		chaptersFiles[Util.TEFILAT_NASHIM][12] = "file:///android_asset/tefilat_nashim_12.html";
		chaptersFiles[Util.TEFILAT_NASHIM][13] = "file:///android_asset/tefilat_nashim_13.html";
		chaptersFiles[Util.TEFILAT_NASHIM][14] = "file:///android_asset/tefilat_nashim_14.html";
		chaptersFiles[Util.TEFILAT_NASHIM][15] = "file:///android_asset/tefilat_nashim_15.html";
		chaptersFiles[Util.TEFILAT_NASHIM][16] = "file:///android_asset/tefilat_nashim_16.html";
		chaptersFiles[Util.TEFILAT_NASHIM][17] = "file:///android_asset/tefilat_nashim_17.html";
		chaptersFiles[Util.TEFILAT_NASHIM][18] = "file:///android_asset/tefilat_nashim_18.html";
		chaptersFiles[Util.TEFILAT_NASHIM][19] = "file:///android_asset/tefilat_nashim_19.html";
		chaptersFiles[Util.TEFILAT_NASHIM][20] = "file:///android_asset/tefilat_nashim_20.html";
		chaptersFiles[Util.TEFILAT_NASHIM][21] = "file:///android_asset/tefilat_nashim_21.html";
		chaptersFiles[Util.TEFILAT_NASHIM][22] = "file:///android_asset/tefilat_nashim_22.html";
		chaptersFiles[Util.TEFILAT_NASHIM][23] = "file:///android_asset/tefilat_nashim_23.html";
		chaptersFiles[Util.TEFILAT_NASHIM][24] = "file:///android_asset/tefilat_nashim_24.html";
		/*HAR_BRACHOT*/
		chaptersFiles[Util.HAR_BRACHOT][0] = "file:///android_asset/har_brachot_tochen.html";
		chaptersFiles[Util.HAR_BRACHOT][1] = "file:///android_asset/har_brachot_1.html";
		chaptersFiles[Util.HAR_BRACHOT][2] = "file:///android_asset/har_brachot_2.html";
		chaptersFiles[Util.HAR_BRACHOT][3] = "file:///android_asset/har_brachot_3.html";
		chaptersFiles[Util.HAR_BRACHOT][4] = "file:///android_asset/har_brachot_4.html";
		chaptersFiles[Util.HAR_BRACHOT][5] = "file:///android_asset/har_brachot_5.html";
		chaptersFiles[Util.HAR_BRACHOT][6] = "file:///android_asset/har_brachot_6.html";
		chaptersFiles[Util.HAR_BRACHOT][7] = "file:///android_asset/har_brachot_7.html";
		chaptersFiles[Util.HAR_BRACHOT][8] = "file:///android_asset/har_brachot_8.html";
		chaptersFiles[Util.HAR_BRACHOT][9] = "file:///android_asset/har_brachot_9.html";
		chaptersFiles[Util.HAR_BRACHOT][10] = "file:///android_asset/har_brachot_10.html";
		chaptersFiles[Util.HAR_BRACHOT][11] = "file:///android_asset/har_brachot_11.html";
		chaptersFiles[Util.HAR_BRACHOT][12] = "file:///android_asset/har_brachot_12.html";
		chaptersFiles[Util.HAR_BRACHOT][13] = "file:///android_asset/har_brachot_13.html";
		chaptersFiles[Util.HAR_BRACHOT][14] = "file:///android_asset/har_brachot_14.html";
		chaptersFiles[Util.HAR_BRACHOT][15] = "file:///android_asset/har_brachot_15.html";
		chaptersFiles[Util.HAR_BRACHOT][16] = "file:///android_asset/har_brachot_16.html";
		chaptersFiles[Util.HAR_BRACHOT][17] = "file:///android_asset/har_brachot_17.html";
		/*HAR_YAMIM*/
		chaptersFiles[Util.HAR_YAMIM][0] = "file:///android_asset/har_yamim_tochen.html";
		chaptersFiles[Util.HAR_YAMIM][1] = "file:///android_asset/har_yamim_1.html";
		chaptersFiles[Util.HAR_YAMIM][2] = "file:///android_asset/har_yamim_2.html";
		chaptersFiles[Util.HAR_YAMIM][3] = "file:///android_asset/har_yamim_3.html";
		chaptersFiles[Util.HAR_YAMIM][4] = "file:///android_asset/har_yamim_4.html";
		chaptersFiles[Util.HAR_YAMIM][5] = "file:///android_asset/har_yamim_5.html";
		chaptersFiles[Util.HAR_YAMIM][6] = "file:///android_asset/har_yamim_6.html";
		chaptersFiles[Util.HAR_YAMIM][7] = "file:///android_asset/har_yamim_7.html";
		chaptersFiles[Util.HAR_YAMIM][8] = "file:///android_asset/har_yamim_8.html";
		chaptersFiles[Util.HAR_YAMIM][9] = "file:///android_asset/har_yamim_9.html";
		chaptersFiles[Util.HAR_YAMIM][10] = "file:///android_asset/har_yamim_10.html";
		/*HAR_MOADIM*/
		chaptersFiles[Util.HAR_MOADIM][0] = "file:///android_asset/har_moadim_tochen.html";
		chaptersFiles[Util.HAR_MOADIM][1] = "file:///android_asset/har_moadim_1.html";
		chaptersFiles[Util.HAR_MOADIM][2] = "file:///android_asset/har_moadim_2.html";
		chaptersFiles[Util.HAR_MOADIM][3] = "file:///android_asset/har_moadim_3.html";
		chaptersFiles[Util.HAR_MOADIM][4] = "file:///android_asset/har_moadim_4.html";
		chaptersFiles[Util.HAR_MOADIM][5] = "file:///android_asset/har_moadim_5.html";
		chaptersFiles[Util.HAR_MOADIM][6] = "file:///android_asset/har_moadim_6.html";
		chaptersFiles[Util.HAR_MOADIM][7] = "file:///android_asset/har_moadim_7.html";
		chaptersFiles[Util.HAR_MOADIM][8] = "file:///android_asset/har_moadim_8.html";
		//chaptersFiles[Util.HAR_MOADIM][9] = "file:///android_asset/har_moadim_9.html"; //currently there is no chapter 9
		chaptersFiles[Util.HAR_MOADIM][9] = "file:///android_asset/har_moadim_10.html";
		chaptersFiles[Util.HAR_MOADIM][10] = "file:///android_asset/har_moadim_11.html";
		chaptersFiles[Util.HAR_MOADIM][11] = "file:///android_asset/har_moadim_12.html";
		chaptersFiles[Util.HAR_MOADIM][12] = "file:///android_asset/har_moadim_13.html";
		/*HAR_SUCOT*/
		chaptersFiles[Util.HAR_SUCOT][0] = "file:///android_asset/sucot_tochen.html";
		chaptersFiles[Util.HAR_SUCOT][1] = "file:///android_asset/har_sucot_1.html";
		chaptersFiles[Util.HAR_SUCOT][2] = "file:///android_asset/har_sucot_2.html";
		chaptersFiles[Util.HAR_SUCOT][3] = "file:///android_asset/har_sucot_3.html";
		chaptersFiles[Util.HAR_SUCOT][4] = "file:///android_asset/har_sucot_4.html";
		chaptersFiles[Util.HAR_SUCOT][5] = "file:///android_asset/har_sucot_5.html";
		chaptersFiles[Util.HAR_SUCOT][6] = "file:///android_asset/har_sucot_6.html";
		chaptersFiles[Util.HAR_SUCOT][7] = "file:///android_asset/har_sucot_7.html";
		chaptersFiles[Util.HAR_SUCOT][8] = "file:///android_asset/har_sucot_8.html";
		/*HAR_SHABAT*/
		chaptersFiles[Util.HAR_SHABAT][0] = "file:///android_asset/har_shabat_tochen.html";
		chaptersFiles[Util.HAR_SHABAT][1] = "file:///android_asset/har_shabat_1.html";
		chaptersFiles[Util.HAR_SHABAT][2] = "file:///android_asset/har_shabat_2.html";
		chaptersFiles[Util.HAR_SHABAT][3] = "file:///android_asset/har_shabat_3.html";
		chaptersFiles[Util.HAR_SHABAT][4] = "file:///android_asset/har_shabat_4.html";
		chaptersFiles[Util.HAR_SHABAT][5] = "file:///android_asset/har_shabat_5.html";
		chaptersFiles[Util.HAR_SHABAT][6] = "file:///android_asset/har_shabat_6.html";
		chaptersFiles[Util.HAR_SHABAT][7] = "file:///android_asset/har_shabat_7.html";
		chaptersFiles[Util.HAR_SHABAT][8] = "file:///android_asset/har_shabat_8.html";
		chaptersFiles[Util.HAR_SHABAT][9] = "file:///android_asset/har_shabat_9.html";
		chaptersFiles[Util.HAR_SHABAT][10] = "file:///android_asset/har_shabat_10.html";
		chaptersFiles[Util.HAR_SHABAT][11] = "file:///android_asset/har_shabat_11.html";
		chaptersFiles[Util.HAR_SHABAT][12] = "file:///android_asset/har_shabat_12.html";
		chaptersFiles[Util.HAR_SHABAT][13] = "file:///android_asset/har_shabat_13.html";
		chaptersFiles[Util.HAR_SHABAT][14] = "file:///android_asset/har_shabat_14.html";
		chaptersFiles[Util.HAR_SHABAT][15] = "file:///android_asset/har_shabat_15.html";
		chaptersFiles[Util.HAR_SHABAT][16] = "file:///android_asset/har_shabat_16.html";
		chaptersFiles[Util.HAR_SHABAT][17] = "file:///android_asset/har_shabat_17.html";
		chaptersFiles[Util.HAR_SHABAT][18] = "file:///android_asset/har_shabat_18.html";
		chaptersFiles[Util.HAR_SHABAT][19] = "file:///android_asset/har_shabat_19.html";
		chaptersFiles[Util.HAR_SHABAT][20] = "file:///android_asset/har_shabat_20.html";
		chaptersFiles[Util.HAR_SHABAT][21] = "file:///android_asset/har_shabat_21.html";
		chaptersFiles[Util.HAR_SHABAT][22] = "file:///android_asset/har_shabat_22.html";
		chaptersFiles[Util.HAR_SHABAT][23] = "file:///android_asset/har_shabat_23.html";
		chaptersFiles[Util.HAR_SHABAT][24] = "file:///android_asset/har_shabat_24.html";
		chaptersFiles[Util.HAR_SHABAT][25] = "file:///android_asset/har_shabat_25.html";
		chaptersFiles[Util.HAR_SHABAT][26] = "file:///android_asset/har_shabat_26.html";
		chaptersFiles[Util.HAR_SHABAT][27] = "file:///android_asset/har_shabat_27.html";
		chaptersFiles[Util.HAR_SHABAT][28] = "file:///android_asset/har_shabat_28.html";
		chaptersFiles[Util.HAR_SHABAT][29] = "file:///android_asset/har_shabat_29.html";
		chaptersFiles[Util.HAR_SHABAT][30] = "file:///android_asset/har_shabat_30.html";
		/*HAR_SIMCHAT*/
		chaptersFiles[Util.HAR_SIMCHAT][0] = "file:///android_asset/har_simchat_tochen.html";
		chaptersFiles[Util.HAR_SIMCHAT][1] = "file:///android_asset/har_simchat_1.html";
		chaptersFiles[Util.HAR_SIMCHAT][2] = "file:///android_asset/har_simchat_2.html";
		chaptersFiles[Util.HAR_SIMCHAT][3] = "file:///android_asset/har_simchat_3.html";
		chaptersFiles[Util.HAR_SIMCHAT][4] = "file:///android_asset/har_simchat_4.html";
		chaptersFiles[Util.HAR_SIMCHAT][5] = "file:///android_asset/har_simchat_5.html";
		chaptersFiles[Util.HAR_SIMCHAT][6] = "file:///android_asset/har_simchat_6.html";
		chaptersFiles[Util.HAR_SIMCHAT][7] = "file:///android_asset/har_simchat_7.html";
		chaptersFiles[Util.HAR_SIMCHAT][8] = "file:///android_asset/har_simchat_8.html";
		chaptersFiles[Util.HAR_SIMCHAT][9] = "file:///android_asset/har_simchat_9.html";
		chaptersFiles[Util.HAR_SIMCHAT][10] = "file:///android_asset/har_simchat_10.html";
		/*E_TEFILA*/
		chaptersFiles[Util.E_TEFILA][0] = "file:///android_asset/E_tefila_tochen.html";
		chaptersFiles[Util.E_TEFILA][1] = "file:///android_asset/E_tefila_1.html";
		chaptersFiles[Util.E_TEFILA][2] = "file:///android_asset/E_tefila_2.html";
		chaptersFiles[Util.E_TEFILA][3] = "file:///android_asset/E_tefila_3.html";
		chaptersFiles[Util.E_TEFILA][4] = "file:///android_asset/E_tefila_4.html";
		chaptersFiles[Util.E_TEFILA][5] = "file:///android_asset/E_tefila_5.html";
		chaptersFiles[Util.E_TEFILA][6] = "file:///android_asset/E_tefila_6.html";
		chaptersFiles[Util.E_TEFILA][7] = "file:///android_asset/E_tefila_7.html";
		chaptersFiles[Util.E_TEFILA][8] = "file:///android_asset/E_tefila_8.html";
		chaptersFiles[Util.E_TEFILA][9] = "file:///android_asset/E_tefila_9.html";
		chaptersFiles[Util.E_TEFILA][10] = "file:///android_asset/E_tefila_10.html";
		chaptersFiles[Util.E_TEFILA][11] = "file:///android_asset/E_tefila_11.html";
		chaptersFiles[Util.E_TEFILA][12] = "file:///android_asset/E_tefila_12.html";
		chaptersFiles[Util.E_TEFILA][13] = "file:///android_asset/E_tefila_13.html";
		chaptersFiles[Util.E_TEFILA][14] = "file:///android_asset/E_tefila_14.html";
		chaptersFiles[Util.E_TEFILA][15] = "file:///android_asset/E_tefila_15.html";
		chaptersFiles[Util.E_TEFILA][16] = "file:///android_asset/E_tefila_16.html";
		chaptersFiles[Util.E_TEFILA][17] = "file:///android_asset/E_tefila_17.html";
		chaptersFiles[Util.E_TEFILA][18] = "file:///android_asset/E_tefila_18.html";
		chaptersFiles[Util.E_TEFILA][19] = "file:///android_asset/E_tefila_19.html";
		chaptersFiles[Util.E_TEFILA][20] = "file:///android_asset/E_tefila_20.html";
		chaptersFiles[Util.E_TEFILA][21] = "file:///android_asset/E_tefila_21.html";
		chaptersFiles[Util.E_TEFILA][22] = "file:///android_asset/E_tefila_22.html";
		chaptersFiles[Util.E_TEFILA][23] = "file:///android_asset/E_tefila_23.html";
		chaptersFiles[Util.E_TEFILA][24] = "file:///android_asset/E_tefila_24.html";
		chaptersFiles[Util.E_TEFILA][25] = "file:///android_asset/E_tefila_25.html";
		chaptersFiles[Util.E_TEFILA][26] = "file:///android_asset/E_tefila_26.html";
		/*E_PESACH*/		
		chaptersFiles[Util.E_PESACH][0] = "file:///android_asset/E_pesach_tochen.html";
		chaptersFiles[Util.E_PESACH][1] = "file:///android_asset/E_pesach_1.html";
		chaptersFiles[Util.E_PESACH][2] = "file:///android_asset/E_pesach_2.html";
		chaptersFiles[Util.E_PESACH][3] = "file:///android_asset/E_pesach_3.html";
		chaptersFiles[Util.E_PESACH][4] = "file:///android_asset/E_pesach_4.html";
		chaptersFiles[Util.E_PESACH][5] = "file:///android_asset/E_pesach_5.html";
		chaptersFiles[Util.E_PESACH][6] = "file:///android_asset/E_pesach_6.html";
		chaptersFiles[Util.E_PESACH][7] = "file:///android_asset/E_pesach_7.html";
		chaptersFiles[Util.E_PESACH][8] = "file:///android_asset/E_pesach_8.html";
		chaptersFiles[Util.E_PESACH][9] = "file:///android_asset/E_pesach_9.html";
		chaptersFiles[Util.E_PESACH][10] = "file:///android_asset/E_pesach_10.html";
		chaptersFiles[Util.E_PESACH][11] = "file:///android_asset/E_pesach_11.html";
		chaptersFiles[Util.E_PESACH][12] = "file:///android_asset/E_pesach_12.html";
		chaptersFiles[Util.E_PESACH][13] = "file:///android_asset/E_pesach_13.html";
		chaptersFiles[Util.E_PESACH][14] = "file:///android_asset/E_pesach_14.html";
		chaptersFiles[Util.E_PESACH][15] = "file:///android_asset/E_pesach_15.html";
		chaptersFiles[Util.E_PESACH][16] = "file:///android_asset/E_pesach_16.html";
		/*E_ZMANIM*/
		chaptersFiles[Util.E_ZMANIM][0] = "file:///android_asset/E_zmanim_tochen.html";
		chaptersFiles[Util.E_ZMANIM][1] = "file:///android_asset/E_zmanim_1.html";
		chaptersFiles[Util.E_ZMANIM][2] = "file:///android_asset/E_zmanim_2.html";
		chaptersFiles[Util.E_ZMANIM][3] = "file:///android_asset/E_zmanim_3.html";
		chaptersFiles[Util.E_ZMANIM][4] = "file:///android_asset/E_zmanim_4.html";
		chaptersFiles[Util.E_ZMANIM][5] = "file:///android_asset/E_zmanim_5.html";
		chaptersFiles[Util.E_ZMANIM][6] = "file:///android_asset/E_zmanim_6.html";
		chaptersFiles[Util.E_ZMANIM][7] = "file:///android_asset/E_zmanim_7.html";
		chaptersFiles[Util.E_ZMANIM][8] = "file:///android_asset/E_zmanim_8.html";
		chaptersFiles[Util.E_ZMANIM][9] = "file:///android_asset/E_zmanim_9.html";
		chaptersFiles[Util.E_ZMANIM][10] = "file:///android_asset/E_zmanim_10.html";
		chaptersFiles[Util.E_ZMANIM][11] = "file:///android_asset/E_zmanim_11.html";
		chaptersFiles[Util.E_ZMANIM][12] = "file:///android_asset/E_zmanim_12.html";
		chaptersFiles[Util.E_ZMANIM][13] = "file:///android_asset/E_zmanim_13.html";
		chaptersFiles[Util.E_ZMANIM][14] = "file:///android_asset/E_zmanim_14.html";
		chaptersFiles[Util.E_ZMANIM][15] = "file:///android_asset/E_zmanim_15.html";
		/*E_WOMEN_PRAYER*/
		chaptersFiles[Util.E_WOMEN_PRAYER][0] = "file:///android_asset/e_w_prayer_tochen.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][1] = "file:///android_asset/e_w_prayer_1.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][2] = "file:///android_asset/e_w_prayer_2.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][3] = "file:///android_asset/e_w_prayer_3.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][4] = "file:///android_asset/e_w_prayer_4.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][5] = "file:///android_asset/e_w_prayer_5.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][6] = "file:///android_asset/e_w_prayer_6.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][7] = "file:///android_asset/e_w_prayer_7.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][8] = "file:///android_asset/e_w_prayer_8.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][9] = "file:///android_asset/e_w_prayer_9.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][10] = "file:///android_asset/e_w_prayer_10.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][11] = "file:///android_asset/e_w_prayer_11.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][12] = "file:///android_asset/e_w_prayer_12.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][13] = "file:///android_asset/e_w_prayer_13.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][14] = "file:///android_asset/e_w_prayer_14.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][15] = "file:///android_asset/e_w_prayer_15.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][16] = "file:///android_asset/e_w_prayer_16.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][17] = "file:///android_asset/e_w_prayer_17.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][18] = "file:///android_asset/e_w_prayer_18.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][19] = "file:///android_asset/e_w_prayer_19.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][20] = "file:///android_asset/e_w_prayer_20.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][21] = "file:///android_asset/e_w_prayer_21.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][22] = "file:///android_asset/e_w_prayer_22.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][23] = "file:///android_asset/e_w_prayer_23.html";
		chaptersFiles[Util.E_WOMEN_PRAYER][24] = "file:///android_asset/e_w_prayer_24.html";
		/*E_SHABAT*/
		chaptersFiles[Util.E_SHABAT][0] = "file:///android_asset/e_shabbat_tochen.html";
		chaptersFiles[Util.E_SHABAT][1] = "file:///android_asset/e_shabbat_1.html";
		chaptersFiles[Util.E_SHABAT][2] = "file:///android_asset/e_shabbat_2.html";
		chaptersFiles[Util.E_SHABAT][3] = "file:///android_asset/e_shabbat_3.html";
		chaptersFiles[Util.E_SHABAT][4] = "file:///android_asset/e_shabbat_4.html";
		chaptersFiles[Util.E_SHABAT][5] = "file:///android_asset/e_shabbat_5.html";
		chaptersFiles[Util.E_SHABAT][6] = "file:///android_asset/e_shabbat_6.html";
		chaptersFiles[Util.E_SHABAT][7] = "file:///android_asset/e_shabbat_7.html";
		chaptersFiles[Util.E_SHABAT][8] = "file:///android_asset/e_shabbat_8.html";
		chaptersFiles[Util.E_SHABAT][9] = "file:///android_asset/e_shabbat_9.html";
		chaptersFiles[Util.E_SHABAT][10] = "file:///android_asset/e_shabbat_10.html";
		chaptersFiles[Util.E_SHABAT][11] = "file:///android_asset/e_shabbat_11.html";
		chaptersFiles[Util.E_SHABAT][12] = "file:///android_asset/e_shabbat_12.html";
		chaptersFiles[Util.E_SHABAT][13] = "file:///android_asset/e_shabbat_13.html";
		chaptersFiles[Util.E_SHABAT][14] = "file:///android_asset/e_shabbat_14.html";
		chaptersFiles[Util.E_SHABAT][15] = "file:///android_asset/e_shabbat_15.html";
		chaptersFiles[Util.E_SHABAT][16] = "file:///android_asset/e_shabbat_16.html";
		chaptersFiles[Util.E_SHABAT][17] = "file:///android_asset/e_shabbat_17.html";
		chaptersFiles[Util.E_SHABAT][18] = "file:///android_asset/e_shabbat_18.html";
		chaptersFiles[Util.E_SHABAT][19] = "file:///android_asset/e_shabbat_19.html";
		chaptersFiles[Util.E_SHABAT][20] = "file:///android_asset/e_shabbat_20.html";
		chaptersFiles[Util.E_SHABAT][21] = "file:///android_asset/e_shabbat_21.html";
		chaptersFiles[Util.E_SHABAT][22] = "file:///android_asset/e_shabbat_22.html";
		chaptersFiles[Util.E_SHABAT][23] = "file:///android_asset/e_shabbat_23.html";
		chaptersFiles[Util.E_SHABAT][24] = "file:///android_asset/e_shabbat_24.html";
		chaptersFiles[Util.E_SHABAT][25] = "file:///android_asset/e_shabbat_25.html";
		chaptersFiles[Util.E_SHABAT][26] = "file:///android_asset/e_shabbat_26.html";
		chaptersFiles[Util.E_SHABAT][27] = "file:///android_asset/e_shabbat_27.html";
		chaptersFiles[Util.E_SHABAT][28] = "file:///android_asset/e_shabbat_28.html";
		chaptersFiles[Util.E_SHABAT][29] = "file:///android_asset/e_shabbat_29.html";
		chaptersFiles[Util.E_SHABAT][30] = "file:///android_asset/e_shabbat_30.html";
		/*F_TEFILA*/
		chaptersFiles[Util.F_TEFILA][0] = "file:///android_asset/F_tefila_tochen.html";
		chaptersFiles[Util.F_TEFILA][1] = "file:///android_asset/F_tefila_1.html";
		chaptersFiles[Util.F_TEFILA][2] = "file:///android_asset/F_tefila_2.html";
		chaptersFiles[Util.F_TEFILA][3] = "file:///android_asset/F_tefila_3.html";
		chaptersFiles[Util.F_TEFILA][4] = "file:///android_asset/F_tefila_4.html";
		chaptersFiles[Util.F_TEFILA][5] = "file:///android_asset/F_tefila_5.html";
		chaptersFiles[Util.F_TEFILA][6] = "file:///android_asset/F_tefila_6.html";
		chaptersFiles[Util.F_TEFILA][7] = "file:///android_asset/F_tefila_7.html";
		chaptersFiles[Util.F_TEFILA][8] = "file:///android_asset/F_tefila_8.html";
		chaptersFiles[Util.F_TEFILA][9] = "file:///android_asset/F_tefila_9.html";
		chaptersFiles[Util.F_TEFILA][10] = "file:///android_asset/F_tefila_10.html";
		chaptersFiles[Util.F_TEFILA][11] = "file:///android_asset/F_tefila_11.html";
		chaptersFiles[Util.F_TEFILA][12] = "file:///android_asset/F_tefila_12.html";
		chaptersFiles[Util.F_TEFILA][13] = "file:///android_asset/F_tefila_13.html";
		chaptersFiles[Util.F_TEFILA][14] = "file:///android_asset/F_tefila_14.html";
		chaptersFiles[Util.F_TEFILA][15] = "file:///android_asset/F_tefila_15.html";
		chaptersFiles[Util.F_TEFILA][16] = "file:///android_asset/F_tefila_16.html";
		chaptersFiles[Util.F_TEFILA][17] = "file:///android_asset/F_tefila_17.html";
		chaptersFiles[Util.F_TEFILA][18] = "file:///android_asset/F_tefila_18.html";
		chaptersFiles[Util.F_TEFILA][19] = "file:///android_asset/F_tefila_19.html";
		chaptersFiles[Util.F_TEFILA][20] = "file:///android_asset/F_tefila_20.html";
		chaptersFiles[Util.F_TEFILA][21] = "file:///android_asset/F_tefila_21.html";
		chaptersFiles[Util.F_TEFILA][22] = "file:///android_asset/F_tefila_22.html";
		chaptersFiles[Util.F_TEFILA][23] = "file:///android_asset/F_tefila_23.html";
		chaptersFiles[Util.F_TEFILA][24] = "file:///android_asset/F_tefila_24.html";
		chaptersFiles[Util.F_TEFILA][25] = "file:///android_asset/F_tefila_25.html";
		chaptersFiles[Util.F_TEFILA][26] = "file:///android_asset/F_tefila_26.html";

		/*S_SHABAT*/
		chaptersFiles[Util.S_SHABAT][0] = "file:///android_asset/s_shabat_tochen.html";
		chaptersFiles[Util.S_SHABAT][1] = "file:///android_asset/s_shabat_1.html";
		chaptersFiles[Util.S_SHABAT][2] = "file:///android_asset/s_shabat_2.html";
		chaptersFiles[Util.S_SHABAT][3] = "file:///android_asset/s_shabat_3.html";
		chaptersFiles[Util.S_SHABAT][4] = "file:///android_asset/s_shabat_4.html";
		chaptersFiles[Util.S_SHABAT][5] = "file:///android_asset/s_shabat_5.html";
		chaptersFiles[Util.S_SHABAT][6] = "file:///android_asset/s_shabat_6.html";
		chaptersFiles[Util.S_SHABAT][7] = "file:///android_asset/s_shabat_7.html";
		chaptersFiles[Util.S_SHABAT][8] = "file:///android_asset/s_shabat_8.html";
		chaptersFiles[Util.S_SHABAT][9] = "file:///android_asset/s_shabat_9.html";
		chaptersFiles[Util.S_SHABAT][10] = "file:///android_asset/s_shabat_10.html";
		chaptersFiles[Util.S_SHABAT][11] = "file:///android_asset/s_shabat_11.html";
		chaptersFiles[Util.S_SHABAT][12] = "file:///android_asset/s_shabat_12.html";
		chaptersFiles[Util.S_SHABAT][13] = "file:///android_asset/s_shabat_13.html";
		chaptersFiles[Util.S_SHABAT][14] = "file:///android_asset/s_shabat_14.html";
		chaptersFiles[Util.S_SHABAT][15] = "file:///android_asset/s_shabat_15.html";
		chaptersFiles[Util.S_SHABAT][16] = "file:///android_asset/s_shabat_16.html";
		chaptersFiles[Util.S_SHABAT][17] = "file:///android_asset/s_shabat_17.html";
		chaptersFiles[Util.S_SHABAT][18] = "file:///android_asset/s_shabat_18.html";
		chaptersFiles[Util.S_SHABAT][19] = "file:///android_asset/s_shabat_19.html";
		chaptersFiles[Util.S_SHABAT][20] = "file:///android_asset/s_shabat_20.html";
		chaptersFiles[Util.S_SHABAT][21] = "file:///android_asset/s_shabat_21.html";
		chaptersFiles[Util.S_SHABAT][22] = "file:///android_asset/s_shabat_22.html";
		chaptersFiles[Util.S_SHABAT][23] = "file:///android_asset/s_shabat_23.html";
		chaptersFiles[Util.S_SHABAT][24] = "file:///android_asset/s_shabat_24.html";
		chaptersFiles[Util.S_SHABAT][25] = "file:///android_asset/s_shabat_25.html";
		chaptersFiles[Util.S_SHABAT][26] = "file:///android_asset/s_shabat_26.html";
		chaptersFiles[Util.S_SHABAT][27] = "file:///android_asset/s_shabat_27.html";
		chaptersFiles[Util.S_SHABAT][28] = "file:///android_asset/s_shabat_28.html";
		chaptersFiles[Util.S_SHABAT][29] = "file:///android_asset/s_shabat_29.html";
		chaptersFiles[Util.S_SHABAT][30] = "file:///android_asset/s_shabat_30.html";
	}


	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	private void showPopupMenuSettings(View v)
	{
		PopupMenu popupMenu = new PopupMenu(TextMain.this, v);

        String configHeaders[] = new String[7];
        if(MyLanguage == Util.ENGLISH) {
            configHeaders[0] = "Settings";
            configHeaders[1] = "About";
            configHeaders[2] = "Feedback";
            configHeaders[3] = "Explanation of search results";
            configHeaders[4] = "Acronyms";
            configHeaders[5] = "Zoom in";
            configHeaders[6] = "Zoom out";
        }
        else if(MyLanguage == Util.RUSSIAN) {
            configHeaders[0] = "Настройки";
            configHeaders[1] = "Около";
            configHeaders[2] = "Обратная связь";
            configHeaders[3] = "Объяснение результатов поиска";
            configHeaders[4] = "Абревиатуры";
            configHeaders[5] = "Увеличить шрифт";
            configHeaders[6] = "Уменьшить шрифт";
        }
        else if(MyLanguage == Util.SPANISH) {
            configHeaders[0] = "Ajustes";
            configHeaders[1] = "Acerca de";
            configHeaders[2] = "Comentarios";
            configHeaders[3] = "Explicacion del resultado de la busqueda";
            configHeaders[4] = "Acronimos";
            configHeaders[5] = "Aumentar enfoque";
            configHeaders[6] = "Disminuir enfoque";
        }
        else if(MyLanguage == Util.FRENCH) {
            configHeaders[0] = "Definitions";
            configHeaders[1] = "A Propos de…";
            configHeaders[2] = "Commentaires";
            configHeaders[3] = "Explication de la recherche";
            configHeaders[4] = "Acronymes";
            configHeaders[5] = "Zoom avant";
            configHeaders[6] = "Zoom arrière";
        }
        else {/*this is the default*/
            configHeaders[0] = "הגדרות";
            configHeaders[1] = "אודות";
            configHeaders[2] = "משוב";
            configHeaders[3] = "הסבר על החיפוש";
            configHeaders[4] = "ראשי תיבות";
            configHeaders[5] = "הגדל טקסט";
            configHeaders[6] = "הקטן טקסט";
        }

		popupMenu.getMenu().add(0,0,0,configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
		popupMenu.getMenu().add(0,1,1,configHeaders[1]);
		popupMenu.getMenu().add(0,2,2,configHeaders[2]);
		popupMenu.getMenu().add(0,3,3,configHeaders[3]);
		popupMenu.getMenu().add(0,4,4,configHeaders[4]);
		if(API >= 19)
		{
			popupMenu.getMenu().add(0,5,5,configHeaders[5]);
			popupMenu.getMenu().add(0,6,6,configHeaders[6]);
		}

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				WebSettings webSettings = webview.getSettings();
				fontSize = webSettings.getDefaultFontSize();
				switch (item.getItemId())
				{
				case 0:/*settings*/
					try
					{
						Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Settings");
						Intent ourIntent = new Intent(TextMain.this, ourClass);
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
						Intent ourIntent = new Intent(TextMain.this, ourClass);
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
						Intent ourIntent = new Intent(TextMain.this, ourClass);
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
						Intent ourIntent = new Intent(TextMain.this, ourClass);
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
				case 5:/*increase text*/
					if(fontSize <= 47) {
						fontSize += 3;
						webSettings.setDefaultFontSize(fontSize);
						shPrefEditor.putInt("fontSize", fontSize);
						shPrefEditor.commit();
						switch (MyLanguage){
							case Util.ENGLISH:
								Toast.makeText(getApplicationContext(),	"Font size - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.RUSSIAN:
								Toast.makeText(getApplicationContext(),	"Размер шрифта - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.SPANISH:
								Toast.makeText(getApplicationContext(),	"Tamaño de fuente - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.FRENCH:
								Toast.makeText(getApplicationContext(),	"Taille de police - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							default:
								Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
						}
					}
					else{
						switch (MyLanguage){
							case Util.ENGLISH:
								Toast.makeText(getApplicationContext(),	"Maximum font size - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.RUSSIAN:
								Toast.makeText(getApplicationContext(),	"Максимальный размер шрифта - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.SPANISH:
								Toast.makeText(getApplicationContext(),	"Tamaño máximo de la fuente - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							case Util.FRENCH:
								Toast.makeText(getApplicationContext(),	"Taille maximale de la police - "+fontSize, Toast.LENGTH_SHORT).show();
								break;
							default:
								Toast.makeText(getApplicationContext(),	"גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
						}
					}
					break;
				case 6:/*decrease text*/
				if(fontSize >= 10 ) {
					fontSize -= 3;
					webSettings.setDefaultFontSize(fontSize);
					shPrefEditor.putInt("fontSize", fontSize);
					shPrefEditor.commit();
					switch (MyLanguage){
						case Util.ENGLISH:
							Toast.makeText(getApplicationContext(),	"Font size - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.RUSSIAN:
							Toast.makeText(getApplicationContext(),	"Размер шрифта - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.SPANISH:
							Toast.makeText(getApplicationContext(),	"Tamaño de fuente - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.FRENCH:
							Toast.makeText(getApplicationContext(),	"Taille de police - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						default:
							Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
					}
				}
				else{
					switch (MyLanguage){
						case Util.ENGLISH:
							Toast.makeText(getApplicationContext(),	"Minimum font size - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.RUSSIAN:
							Toast.makeText(getApplicationContext(),	"Минимальный размер шрифта - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.SPANISH:
							Toast.makeText(getApplicationContext(),	"Tamaño mínimo de fuente - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						case Util.FRENCH:
							Toast.makeText(getApplicationContext(),	"Taille de police minimale - "+fontSize, Toast.LENGTH_SHORT).show();
							break;
						default:
							Toast.makeText(getApplicationContext(),	"גודל גופן מינימלי", Toast.LENGTH_SHORT).show();
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

	private class MyWebViewClient extends WebViewClient 
	{
		@SuppressLint("NewApi")
		@Override
		public void onPageFinished(WebView view, String url) 
		{
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			String strToastSearch;
			WebSettings webSettings = webview.getSettings();

			if(firstTime == true || ChangeChapter == true)
			{
				firstTime = false;

				webSettings.setDefaultFontSize(fontSize);

				if(cameFromSearch == true)
				{
					webview.loadUrl(searchPosition);
					if(noteStr != "0")/*if all the results are in notes*/
					{
						query = "הערה " + noteStr;
						strToastSearch = "תוצאות החיפוש נמצאות בהערות: " + sectionsForToast;
					}
					else
					{
						strToastSearch = "תוצאות החיפוש נמצאות גם בהערות: " + sectionsForToast;
					}

					if(API < 16) 
					{
						if(BlackBackground == 1)
							WhiteTextAfterDelay();
						else
							finddelay(query);
					} else {
						webview.findAllAsync(query);
					}

					if (sectionsForToast.compareTo("") != 0)
						Toast.makeText(getApplicationContext(), strToastSearch, Toast.LENGTH_LONG).show();
				}
				if(BlackBackground == 1)
				{
                    //webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";var y = document.getElementsByClassName(\"left_white\"); y[0].style.display = 'none';} myFunction(); ");
					webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";} myFunction(); ");
					llMainLayout.setBackgroundColor(Color.BLACK);
					webview.setBackgroundColor(0xFFFFFF);//black
				//	textActionBar.setTitle(Html.fromHtml("<font color=\"white\">" + title + "</font>"));
				}
				else
				{
					//webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";var y = document.getElementsByClassName(\"left_black\"); y[0].style.display = 'none';} myFunction(); ");
					webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
					llMainLayout.setBackgroundColor(Color.WHITE);
					webview.setBackgroundColor(0x000000);//white
				//	textActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));
				}
				invalidateOptionsMenu();
			}
		}
	}

	public void findBookAndChapter()
	{
		String bookAndChapter;

		int length= searchPosition.lastIndexOf("#"); 
		if(length == -1)/*it means that all the results are in notes*/
		{
			length = searchPosition.lastIndexOf(":");
			noteStr = searchPosition.substring(length+1,searchPosition.length());
			searchPosition = searchPosition.substring(0, length);
			bookAndChapter = searchPosition;
		}
		else
		{
			length= searchPosition.lastIndexOf("#");
			bookAndChapter = searchPosition.substring(0, length);
		}

		book_chapter = new int[2];
		for (int i=0; i<=Util.BOOKS_HEB_NUMBER; i++)
			for (int j=1; j<=util.lastChapter[i]; j++)
				if(bookAndChapter.equals(chaptersFiles[i][j]))
				{
					book_chapter[0] = i;
					book_chapter[1] = j;
					return;
				}
	}

	public String convertAnchorIdToSection(int Id)
	{
		if(book_chapter[0] == Util.KASHRUT_B && Id != 0)//for KASHRUT_B start from chapter 20
			Id = Id + 19;
		switch (Id)
		{
		case 0:
			return "פתיחה";
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

	public String convertBookIdToName(int bookId)
	{
		switch (bookId)
		{
		case Util.BRACHOT:
			return "ברכות";
		case Util.GIYUR:
			return "גיור";
		case Util.HAAMVEHAAREZ:
			return "העם והא'";
		case Util.ZMANIM:
			return "זמנים";
		case Util.TAHARAT:
			return "טהרת המש'";
		case Util.YAMIM:
			return "ימים נוראים";
		case Util.KASHRUT_A:
			return "כשרות א";
		case Util.KASHRUT_B:
			return "כשרות ב";
		case Util.LIKUTIM_A:
			return "ליקוטים א";
		case Util.LIKUTIM_B:
			return "ליקוטים ב";
		case Util.MISHPACHA:
			return "משפחה";
		case Util.MOADIM:
			return "מועדים";
		case Util.SUCOT:
			return "סוכות";
		case Util.PESACH:
			return "פסח";
		case Util.SHVIIT:
			return "שביעית";
		case Util.SHABAT:
			return "שבת";
		case Util.SIMCHAT:
			return "שמחת הבית";
		case Util.TEFILA:
			return "תפילה";
		case Util.TEFILAT_NASHIM:
			return "תפילת נש'";
		case Util.HAR_MOADIM:
			return "הר' מועדים";
		case Util.HAR_SUCOT:
			return "הר' סוכות";
		case Util.HAR_SHABAT:
			return "הר' שבת";
		case Util.HAR_SIMCHAT:
			return "הר' שמחת הבית";
		case Util.HAR_YAMIM:
			return "הר' ימים נוראים";
		case Util.HAR_BRACHOT:
			return "הר' ברכות";
		case Util.E_TEFILA:
			return "Tefila";
		case Util.E_PESACH:
			return "Pesach";
		case Util.E_ZMANIM:
			return "Zmanim";
		case Util.E_WOMEN_PRAYER:
			return "Women’s Prayer";
		case Util.E_SHABAT:
			return "Shabbat";
		case Util.F_TEFILA:
			return "La prière d’Israël";
		case Util.S_SHABAT:
			return "Shabbat (Español)";
		default:
			return "לא ידוע";
		}
	}

	private void fillChaptersNames()
	{
		/*BRACHOT*/
		chaptersNames[Util.BRACHOT][0] = "ברכות: תוכן";
		chaptersNames[Util.BRACHOT][1] = "ברכות: א - פתיחה";
		chaptersNames[Util.BRACHOT][2] = "ברכות: ב - נטילת ידיים לסעודה";
		chaptersNames[Util.BRACHOT][3] = "ברכות: ג - ברכת המוציא";
		chaptersNames[Util.BRACHOT][4] = "ברכות: ד - ברכת המזון";
		chaptersNames[Util.BRACHOT][5] = "ברכות: ה - זימון";
		chaptersNames[Util.BRACHOT][6] = "ברכות: ו - חמשת מיני דגן";
		chaptersNames[Util.BRACHOT][7] = "ברכות: ז - ברכת היין";
		chaptersNames[Util.BRACHOT][8] = "ברכות: ח - ברכת הפירות ושהכל";
		chaptersNames[Util.BRACHOT][9] = "ברכות: ט - כללי ברכה ראשונה";
		chaptersNames[Util.BRACHOT][10] = "ברכות: י - ברכה אחרונה";
		chaptersNames[Util.BRACHOT][11] = "ברכות: יא - עיקר וטפל";
		chaptersNames[Util.BRACHOT][12] = "ברכות: יב - כללי ברכות";
		chaptersNames[Util.BRACHOT][13] = "ברכות: יג - דרך ארץ";
		chaptersNames[Util.BRACHOT][14] = "ברכות: יד - ברכת הריח";
		chaptersNames[Util.BRACHOT][15] = "ברכות: טו - ברכות הראייה";
		chaptersNames[Util.BRACHOT][16] = "ברכות: טז - ברכת הגומל";
		chaptersNames[Util.BRACHOT][17] = "ברכות: יז - ברכות ההודאה והשמחה";
		chaptersNames[Util.BRACHOT][18] = "ברכות: יח - תפילת הדרך";
		/*GIYUR*/
		chaptersNames[Util.GIYUR][0] = "גיור: תוכן";
		chaptersNames[Util.GIYUR][1] = "גיור: א - הגיור";
		chaptersNames[Util.GIYUR][2] = "גיור: ב - גרי הצדק";
		chaptersNames[Util.GIYUR][3] = "גיור: ג - הגיורים המורכבים";
		chaptersNames[Util.GIYUR][4] = "גיור: ד - בית הדין";
		chaptersNames[Util.GIYUR][5] = "גיור: ה - הגיור למעשה";
		chaptersNames[Util.GIYUR][6] = "גיור: ו - הגיור בשעת הדחק";
		chaptersNames[Util.GIYUR][7] = "גיור: ז - גיור קטנים";
		chaptersNames[Util.GIYUR][8] = "גיור: ח - דיני משפחה";
		chaptersNames[Util.GIYUR][9] = "גיור: ט - מעמד הגר והלכותיו";
		/*HAAMVEHAAREZ*/
		chaptersNames[Util.HAAMVEHAAREZ][0] = "העם והארץ: תוכן";
		chaptersNames[Util.HAAMVEHAAREZ][1] = "העם והארץ: א - מעלת הארץ";
		chaptersNames[Util.HAAMVEHAAREZ][2] = "העם והארץ: ב - קודש וחול ביישוב הארץ";
		chaptersNames[Util.HAAMVEHAAREZ][3] = "העם והארץ: ג - מצוות יישוב הארץ";
		chaptersNames[Util.HAAMVEHAAREZ][4] = "העם והארץ: ד - מהלכות צבא ומלחמה";
		chaptersNames[Util.HAAMVEHAAREZ][5] = "העם והארץ: ה - שמירת הארץ";
		chaptersNames[Util.HAAMVEHAAREZ][6] = "העם והארץ: ו - מהלכות מדינה";
		chaptersNames[Util.HAAMVEHAAREZ][7] = "העם והארץ: ז - ערבות הדדית";
		chaptersNames[Util.HAAMVEHAAREZ][8] = "העם והארץ: ח - עבודה עברית";
		chaptersNames[Util.HAAMVEHAAREZ][9] = "העם והארץ: ט - זכר למקדש";
		chaptersNames[Util.HAAMVEHAAREZ][10] = "העם והארץ: יא - נספח: תשובות מאת הרב גורן ומרבנים נוספים";
		/*ZMANIM*/
		chaptersNames[Util.ZMANIM][0] = "זמנים: תוכן";
		chaptersNames[Util.ZMANIM][1] = "זמנים: א - ראש חודש";
		chaptersNames[Util.ZMANIM][2] = "זמנים: ב - הלכות ספירת העומר";
		chaptersNames[Util.ZMANIM][3] = "זמנים: ג - מנהגי אבילות בספירת העומר";
		chaptersNames[Util.ZMANIM][4] = "זמנים: ד - יום העצמאות";
		chaptersNames[Util.ZMANIM][5] = "זמנים: ה - לג בעומר";
		chaptersNames[Util.ZMANIM][6] = "זמנים: ו - ארבעת צומות החורבן";
		chaptersNames[Util.ZMANIM][7] = "זמנים: ז - דיני הצומות הקלים";
		chaptersNames[Util.ZMANIM][8] = "זמנים: ח - מנהגי שלושת השבועות";
		chaptersNames[Util.ZMANIM][9] = "זמנים: ט - ערב תשעה באב";
		chaptersNames[Util.ZMANIM][10] = "זמנים: י - הלכות תשעה באב";
		chaptersNames[Util.ZMANIM][11] = "זמנים: יא - ימי החנוכה";
		chaptersNames[Util.ZMANIM][12] = "זמנים: יב - הדלקת נרות חנוכה";
		chaptersNames[Util.ZMANIM][13] = "זמנים: יג - דיני המקום והזמן";
		chaptersNames[Util.ZMANIM][14] = "זמנים: יד - חודש אדר";
		chaptersNames[Util.ZMANIM][15] = "זמנים: טו - פורים ומקרא מגילה";
		chaptersNames[Util.ZMANIM][16] = "זמנים: טז - מצוות השמחה והחסד";
		chaptersNames[Util.ZMANIM][17] = "זמנים: יז - דיני פרזים ומוקפים";
		/*TAHARAT*/
		chaptersNames[Util.TAHARAT][0] = "טהרת המשפחה: תוכן";
		chaptersNames[Util.TAHARAT][1] = "טהרת המשפחה: א - טהרת המשפחה";
		chaptersNames[Util.TAHARAT][2] = "טהרת המשפחה: ב - דם וכתם";
		chaptersNames[Util.TAHARAT][3] = "טהרת המשפחה: ג - איסורי הרחקה";
		chaptersNames[Util.TAHARAT][4] = "טהרת המשפחה: ד - שבעה נקיים";
		chaptersNames[Util.TAHARAT][5] = "טהרת המשפחה: ה - טבילת טהרה";
		chaptersNames[Util.TAHARAT][6] = "טהרת המשפחה: ו - פרישה ווסתות";
		chaptersNames[Util.TAHARAT][7] = "טהרת המשפחה: ז - שאלת חכם ובדיקה רפואית";
		chaptersNames[Util.TAHARAT][8] = "טהרת המשפחה: ח - כלה";
		chaptersNames[Util.TAHARAT][9] = "טהרת המשפחה: ט - יולדת";
		chaptersNames[Util.TAHARAT][10] = "טהרת המשפחה: י - מקוואות";
		/*YAMIM*/
		chaptersNames[Util.YAMIM][0] = "ימים נוראים: תוכן";
		chaptersNames[Util.YAMIM][1] = "ימים נוראים: א - הדין השכר והעונש";
		chaptersNames[Util.YAMIM][2] = "ימים נוראים: ב - סליחות ותפילות";
		chaptersNames[Util.YAMIM][3] = "ימים נוראים: ג - ראש השנה";
		chaptersNames[Util.YAMIM][4] = "ימים נוראים: ד - מצוות השופר";
		chaptersNames[Util.YAMIM][5] = "ימים נוראים: ה - עשרת ימי תשובה";
		chaptersNames[Util.YAMIM][6] = "ימים נוראים: ו - יום הכיפורים";
		chaptersNames[Util.YAMIM][7] = "ימים נוראים: ז - הלכות יום הכיפורים";
		chaptersNames[Util.YAMIM][8] = "ימים נוראים: ח - דיני התענית";
		chaptersNames[Util.YAMIM][9] = "ימים נוראים: ט - שאר עינויים";
		chaptersNames[Util.YAMIM][10] = "ימים נוראים: י - עבודת יום הכיפורים";
		/*KASHRUT_A*/
		chaptersNames[Util.KASHRUT_A][0] = "כשרות א: תוכן";
		chaptersNames[Util.KASHRUT_A][1] = "כשרות א: א - חדש";
		chaptersNames[Util.KASHRUT_A][2] = "כשרות א: ב - ערלה ורבעי";
		chaptersNames[Util.KASHRUT_A][3] = "כשרות א: ג - כלאי בהמה ואילן";
		chaptersNames[Util.KASHRUT_A][4] = "כשרות א: ד - כלאי זרעים";
		chaptersNames[Util.KASHRUT_A][5] = "כשרות א: ה - כלאי הכרם";
		chaptersNames[Util.KASHRUT_A][6] = "כשרות א: ו - מתנות עניים";
		chaptersNames[Util.KASHRUT_A][7] = "כשרות א: ז - תרומות ומעשרות";
		chaptersNames[Util.KASHRUT_A][8] = "כשרות א: ח - החייב והפטור";
		chaptersNames[Util.KASHRUT_A][9] = "כשרות א: ט - כללי המצווה";
		chaptersNames[Util.KASHRUT_A][10] ="כשרות א: י - סדר ההפרשה למעשה";
		chaptersNames[Util.KASHRUT_A][11] ="כשרות א: יא - חלה";
		chaptersNames[Util.KASHRUT_A][12] ="כשרות א: יב - מצוות התלויות בארץ";
		chaptersNames[Util.KASHRUT_A][13] ="כשרות א: יג - עצי פרי ובל תשחית";
		chaptersNames[Util.KASHRUT_A][14] ="כשרות א: יד - אכילת בשר";
		chaptersNames[Util.KASHRUT_A][15] ="כשרות א: טו - צער בעלי חיים";
		chaptersNames[Util.KASHRUT_A][16] ="כשרות א: טז - שילוח הקן";
		chaptersNames[Util.KASHRUT_A][17] ="כשרות א: יז - כשרות בעלי חיים";
		chaptersNames[Util.KASHRUT_A][18] ="כשרות א: יח - הלכות שחיטה";
		chaptersNames[Util.KASHRUT_A][19] ="כשרות א: יט - מתנות כהונה מהחי";
		/*KASHRUT_B*/
		chaptersNames[Util.KASHRUT_B][0] = "כשרות ב: תוכן";
		chaptersNames[Util.KASHRUT_B][1] = "כשרות ב: כ - טריפות";
		chaptersNames[Util.KASHRUT_B][2] = "כשרות ב: כא - חֵלֶב וגיד הנשה וניקור";
		chaptersNames[Util.KASHRUT_B][3] = "כשרות ב: כב - דם והכשרת הבשר";
		chaptersNames[Util.KASHRUT_B][4] = "כשרות ב: כג - שרצים";
		chaptersNames[Util.KASHRUT_B][5] = "כשרות ב: כד - מזון מהחי";
		chaptersNames[Util.KASHRUT_B][6] = "כשרות ב: כה - בשר בחלב";
		chaptersNames[Util.KASHRUT_B][7] = "כשרות ב: כו - דיני ההפסקה";
		chaptersNames[Util.KASHRUT_B][8] = "כשרות ב: כז - הגזירות על מאכלי גויים";
		chaptersNames[Util.KASHRUT_B][9] = "כשרות ב: כח - פת ובישולי גויים";
		chaptersNames[Util.KASHRUT_B][10] ="כשרות ב: כט - יין ומשקאות גויים";
		chaptersNames[Util.KASHRUT_B][11] ="כשרות ב: ל - חלב ומוצריו";
		chaptersNames[Util.KASHRUT_B][12] ="כשרות ב: לא - טבילת כלים";
		chaptersNames[Util.KASHRUT_B][13] ="כשרות ב: לב - כללי הכשרת כלים";
		chaptersNames[Util.KASHRUT_B][14] ="כשרות ב: לג - הכשרת כלים ומטבח";
		chaptersNames[Util.KASHRUT_B][15] ="כשרות ב: לד - דיני תערובות";
		chaptersNames[Util.KASHRUT_B][16] ="כשרות ב: לה - סוגי בליעות";
		chaptersNames[Util.KASHRUT_B][17] ="כשרות ב: לו - סכנות";
		chaptersNames[Util.KASHRUT_B][18] ="כשרות ב: לז - תעשיית המזון";
		chaptersNames[Util.KASHRUT_B][19] ="כשרות ב: לח - נאמנות והשגחה";
		/*LIKUTIM_A*/
		chaptersNames[Util.LIKUTIM_A][0] = "ליקוטים א: תוכן";
		chaptersNames[Util.LIKUTIM_A][1] = "ליקוטים א: א - הלכות תלמוד תורה";
		chaptersNames[Util.LIKUTIM_A][2] = "ליקוטים א: ב - החינוך לתורה";
        chaptersNames[Util.LIKUTIM_A][3] = "ליקוטים א: ג - קיום התורה והחינוך";
		chaptersNames[Util.LIKUTIM_A][4] = "ליקוטים א: ד - הלכות ספר תורה";
		chaptersNames[Util.LIKUTIM_A][5] = "ליקוטים א: ה - מהלכות קריאת התורה";
		chaptersNames[Util.LIKUTIM_A][6] = "ליקוטים א: ו - כבוד ספר תורה ושמות קדושים";
		chaptersNames[Util.LIKUTIM_A][7] = "ליקוטים א: ז - הלכות בית כנסת";
		chaptersNames[Util.LIKUTIM_A][8] = "ליקוטים א: ח - כיפה";
		chaptersNames[Util.LIKUTIM_A][9] = "ליקוטים א: ט - מהלכות ציצית";
		chaptersNames[Util.LIKUTIM_A][10] = "ליקוטים א: י - מהלכות תפילין";
		chaptersNames[Util.LIKUTIM_A][11] = "ליקוטים א: יא - מהלכות מזוזה";
		chaptersNames[Util.LIKUTIM_A][12] = "ליקוטים א: יב - הלכות כהנים";
        chaptersNames[Util.LIKUTIM_A][13] = "ליקוטים א: יג - שעטנז";
		/*LIKUTIM_B*/
		chaptersNames[Util.LIKUTIM_B][0] = "ליקוטים ב: תוכן";
		chaptersNames[Util.LIKUTIM_B][1] = "ליקוטים ב: א - בין אדם לחברו";
		chaptersNames[Util.LIKUTIM_B][2] = "ליקוטים ב: ב - הלכות אמירת אמת";
		chaptersNames[Util.LIKUTIM_B][3] = "ליקוטים ב: ג - הלכות גניבת דעת";
		chaptersNames[Util.LIKUTIM_B][4] = "ליקוטים ב: ד - הלכות גניבה";
		chaptersNames[Util.LIKUTIM_B][5] = "ליקוטים ב: ה - מצוות הלוואה";
		chaptersNames[Util.LIKUTIM_B][6] = "ליקוטים ב: ו - מהלכות צדקה";
		chaptersNames[Util.LIKUTIM_B][7] = "ליקוטים ב: ז - הכנסת אורחים";
		chaptersNames[Util.LIKUTIM_B][8] = "ליקוטים ב: ח - הלכות רוצח ומתאבד";
		chaptersNames[Util.LIKUTIM_B][9] = "ליקוטים ב: ט - הלכות שמירת הנפש";
		chaptersNames[Util.LIKUTIM_B][10] = "ליקוטים ב: י - נהיגה זהירה ותפילת הדרך";
		chaptersNames[Util.LIKUTIM_B][11] = "ליקוטים ב: יא - הלכות הצלת נפשות";
		chaptersNames[Util.LIKUTIM_B][12] = "ליקוטים ב: יב - הלכות ניתוחי מתים";
		chaptersNames[Util.LIKUTIM_B][13] = "ליקוטים ב: יג - השתלת אברים";
		chaptersNames[Util.LIKUTIM_B][14] = "ליקוטים ב: יד - הלכות הנוטה למות";
		chaptersNames[Util.LIKUTIM_B][15] = "ליקוטים ב: טו - ליקוטים";
		chaptersNames[Util.LIKUTIM_B][16] = "ליקוטים ב: טז - חברה ושליחות";
		/*MISHPACHA*/
		chaptersNames[Util.MISHPACHA][0] = "משפחה: תוכן";
		chaptersNames[Util.MISHPACHA][1] = "משפחה: א - כיבוד הורים";
		chaptersNames[Util.MISHPACHA][2] = "משפחה: ב - מצוות הנישואין";
		chaptersNames[Util.MISHPACHA][3] = "משפחה: ג - שידוכים";
		chaptersNames[Util.MISHPACHA][4] = "משפחה: ד - קידושין וכתובה";
		chaptersNames[Util.MISHPACHA][5] = "משפחה: ה - החתונה ומנהגיה";
		chaptersNames[Util.MISHPACHA][6] = "משפחה: ו - איסורי עריות";
		chaptersNames[Util.MISHPACHA][7] = "משפחה: ז - מהלכות צניעות";
		chaptersNames[Util.MISHPACHA][8] = "משפחה: ח - ברית מילה";
		chaptersNames[Util.MISHPACHA][9] = "משפחה: ט - פדיון הבן";
		chaptersNames[Util.MISHPACHA][10] ="משפחה: י - אבלות";
		/*MOADIM*/
		chaptersNames[Util.MOADIM][0] = "מועדים: תוכן";
		chaptersNames[Util.MOADIM][1] = "מועדים: א - פתיחה";
		chaptersNames[Util.MOADIM][2] = "מועדים: ב - דיני עשה ביום טוב";
		chaptersNames[Util.MOADIM][3] = "מועדים: ג - כללי המלאכות";
		chaptersNames[Util.MOADIM][4] = "מועדים: ד - מלאכות המאכלים";
		chaptersNames[Util.MOADIM][5] = "מועדים: ה - הבערה כיבוי וחשמל";
		chaptersNames[Util.MOADIM][6] = "מועדים: ו - הוצאה ומוקצה";
		chaptersNames[Util.MOADIM][7] = "מועדים: ז - מדיני יום טוב";
		chaptersNames[Util.MOADIM][8] = "מועדים: ח - עירוב תבשילין";
		chaptersNames[Util.MOADIM][9] = "מועדים: ט - יום טוב שני של גלויות";
		chaptersNames[Util.MOADIM][10] = "מועדים: י - מצוות חול המועד";
		chaptersNames[Util.MOADIM][11] = "מועדים: יא - מלאכת חול המועד";
		chaptersNames[Util.MOADIM][12] = "מועדים: יב - היתרי עבודה במועד";
		chaptersNames[Util.MOADIM][13] = "מועדים: יג - חג שבועות";
		/*SUCOT*/
		chaptersNames[Util.SUCOT][0] = "סוכות: תוכן";
		chaptersNames[Util.SUCOT][1] = "סוכות: א - חג הסוכות";
		chaptersNames[Util.SUCOT][2] = "סוכות: ב - הלכות סוכה";
		chaptersNames[Util.SUCOT][3] = "סוכות: ג - ישיבה בסוכה";
		chaptersNames[Util.SUCOT][4] = "סוכות: ד - ארבעת המינים";
		chaptersNames[Util.SUCOT][5] = "סוכות: ה - נטילת לולב";
		chaptersNames[Util.SUCOT][6] = "סוכות: ו - הושענא רבה";
		chaptersNames[Util.SUCOT][7] = "סוכות: ז - שמיני עצרת";
		chaptersNames[Util.SUCOT][8] = "סוכות: ח - הקהל";
		/*PESACH*/
		chaptersNames[Util.PESACH][0] = "פסח: תוכן";
		chaptersNames[Util.PESACH][1] = "פסח: א - משמעות החג";
		chaptersNames[Util.PESACH][2] = "פסח: ב - כללי איסור חמץ";
		chaptersNames[Util.PESACH][3] = "פסח: ג - מצוות השבתת חמץ";
		chaptersNames[Util.PESACH][4] = "פסח: ד - בדיקת חמץ";
		chaptersNames[Util.PESACH][5] = "פסח: ה - ביטול חמץ וביעורו";
		chaptersNames[Util.PESACH][6] = "פסח: ו - מכירת חמץ";
		chaptersNames[Util.PESACH][7] = "פסח: ז - תערובת חמץ";
		chaptersNames[Util.PESACH][8] = "פסח: ח - מהלכות כשרות לפסח";
		chaptersNames[Util.PESACH][9] = "פסח: ט - מנהג איסור קטניות";
		chaptersNames[Util.PESACH][10] = "פסח: י - כללי הגעלת כלים";
		chaptersNames[Util.PESACH][11] = "פסח: יא - הכשרת המטבח לפסח";
		chaptersNames[Util.PESACH][12] = "פסח: יב - הלכות מצה";
		chaptersNames[Util.PESACH][13] = "פסח: יג - הלכות ערב פסח ומנהגיו";
		chaptersNames[Util.PESACH][14] = "פסח: יד - ערב פסח שחל בשבת";
		chaptersNames[Util.PESACH][15] = "פסח: טו - ההגדה";
		chaptersNames[Util.PESACH][16] = "פסח: טז - ליל הסדר";
		/*SHVIIT*/
		chaptersNames[Util.SHVIIT][0] = "שביעית: תוכן";
		chaptersNames[Util.SHVIIT][1] = "שביעית: א - מצוות השביעית";
		chaptersNames[Util.SHVIIT][2] = "שביעית: ב - מצוות השביתה";
		chaptersNames[Util.SHVIIT][3] = "שביעית: ג - השמטת הפירות";
		chaptersNames[Util.SHVIIT][4] = "שביעית: ד - פירות השביעית";
		chaptersNames[Util.SHVIIT][5] = "שביעית: ה - הזמן המקום והאדם";
		chaptersNames[Util.SHVIIT][6] = "שביעית: ו - שמיטת כספים";
		chaptersNames[Util.SHVIIT][7] = "שביעית: ז - היתר המכירה";
		chaptersNames[Util.SHVIIT][8] = "שביעית: ח - אוצר בית דין";
		chaptersNames[Util.SHVIIT][9] = "שביעית: ט - קניית פירות בשביעית";
		chaptersNames[Util.SHVIIT][10] = "שביעית: י - מצוות היובל";
		chaptersNames[Util.SHVIIT][11] = "שביעית: יא - חזון השביעית";		
		/*SHABAT*/
		chaptersNames[Util.SHABAT][0] = "שבת: תוכן";
		chaptersNames[Util.SHABAT][1] = "שבת: א - פתיחה";
		chaptersNames[Util.SHABAT][2] = "שבת: ב - הכנות לשבת";
		chaptersNames[Util.SHABAT][3] = "שבת: ג - זמני השבת";
		chaptersNames[Util.SHABAT][4] = "שבת: ד - הדלקת נרות שבת";
		chaptersNames[Util.SHABAT][5] = "שבת: ה - תורה ותפילה בשבת";
		chaptersNames[Util.SHABAT][6] = "שבת: ו - הלכות קידוש";
		chaptersNames[Util.SHABAT][7] = "שבת: ז - סעודות השבת ומלווה מלכה";
		chaptersNames[Util.SHABAT][8] = "שבת: ח - הבדלה ומוצאי שבת";
		chaptersNames[Util.SHABAT][9] = "שבת: ט - כללי המלאכות";
		chaptersNames[Util.SHABAT][10] = "שבת: י - בישול";
		chaptersNames[Util.SHABAT][11] = "שבת: יא - בורר";
		chaptersNames[Util.SHABAT][12] = "שבת: יב - הכנת מאכלים";
		chaptersNames[Util.SHABAT][13] = "שבת: יג - מלאכות הבגד";
		chaptersNames[Util.SHABAT][14] = "שבת: יד - הטיפול בגוף";
		chaptersNames[Util.SHABAT][15] = "שבת: טו - בונה סותר בבית וכלים";
		chaptersNames[Util.SHABAT][16] = "שבת: טז - מבעיר ומכבה";
		chaptersNames[Util.SHABAT][17] = "שבת: יז - חשמל ומכשיריו";
		chaptersNames[Util.SHABAT][18] = "שבת: יח - כותב מוחק וצובע";
		chaptersNames[Util.SHABAT][19] = "שבת: יט - מלאכות שבצומח";
		chaptersNames[Util.SHABAT][20] = "שבת: כ - בעלי חיים";
		chaptersNames[Util.SHABAT][21] = "שבת: כא - הלכות הוצאה";
		chaptersNames[Util.SHABAT][22] = "שבת: כב - צביון השבת";
		chaptersNames[Util.SHABAT][23] = "שבת: כג - מוקצה";
		chaptersNames[Util.SHABAT][24] = "שבת: כד - דיני קטן";
		chaptersNames[Util.SHABAT][25] = "שבת: כה - מלאכת גוי";
		chaptersNames[Util.SHABAT][26] = "שבת: כו - מעשה שבת ולפני עיוור";
		chaptersNames[Util.SHABAT][27] = "שבת: כז - פיקוח נפש וחולה";
		chaptersNames[Util.SHABAT][28] = "שבת: כח - חולה שאינו מסוכן";
		chaptersNames[Util.SHABAT][29] = "שבת: כט - עירובין";
		chaptersNames[Util.SHABAT][30] = "שבת: ל - תחומי שבת";
		/*SIMCHAT*/
		chaptersNames[Util.SIMCHAT][0] = "שמחת הבית וברכתו: תוכן";
		chaptersNames[Util.SIMCHAT][1] = "שמחת הבית וברכתו: א - מצוות עונה";
		chaptersNames[Util.SIMCHAT][2] = "שמחת הבית וברכתו: ב - הלכות עונה";
		chaptersNames[Util.SIMCHAT][3] = "שמחת הבית וברכתו: ג - קדושה וכוונה";
		chaptersNames[Util.SIMCHAT][4] = "שמחת הבית וברכתו: ד - שמירת הברית";
		chaptersNames[Util.SIMCHAT][5] = "שמחת הבית וברכתו: ה - פרו ורבו";
		chaptersNames[Util.SIMCHAT][6] = "שמחת הבית וברכתו: ו - קשיים ועקרות";
		chaptersNames[Util.SIMCHAT][7] = "שמחת הבית וברכתו: ז - סריס והשחתה";
		chaptersNames[Util.SIMCHAT][8] = "שמחת הבית וברכתו: ח - נחמת חשוכי ילדים";
		chaptersNames[Util.SIMCHAT][9] = "שמחת הבית וברכתו: ט - הפסקת הריון";
		chaptersNames[Util.SIMCHAT][10] = "שמחת הבית וברכתו: י - האיש והאשה";
		/*TEFILA*/
		chaptersNames[Util.TEFILA][0] = "תפילה: תוכן";
		chaptersNames[Util.TEFILA][1] = "תפילה: א - יסודות הלכות תפילה";
		chaptersNames[Util.TEFILA][2] = "תפילה: ב - המניין";
		chaptersNames[Util.TEFILA][3] = "תפילה: ג - מקום התפילה";
		chaptersNames[Util.TEFILA][4] = "תפילה: ד - החזן וקדיש של אבלים";
		chaptersNames[Util.TEFILA][5] = "תפילה: ה - הכנות לתפילה";
		chaptersNames[Util.TEFILA][6] = "תפילה: ו - הנוסחים ומנהגי העדות";
		chaptersNames[Util.TEFILA][7] = "תפילה: ז - השכמת הבוקר";
		chaptersNames[Util.TEFILA][8] = "תפילה: ח - נטילת ידיים שחרית";
		chaptersNames[Util.TEFILA][9]  = "תפילה: ט - ברכות השחר";
		chaptersNames[Util.TEFILA][10] = "תפילה: י - ברכת התורה";
		chaptersNames[Util.TEFILA][11] = "תפילה: יא - זמן ק\"ש ותפילת שחרית";
		chaptersNames[Util.TEFILA][12] = "תפילה: יב - לקראת תפילת שחרית";
		chaptersNames[Util.TEFILA][13] = "תפילה: יג - סדר קרבנות";
		chaptersNames[Util.TEFILA][14] = "תפילה: יד - פסוקי דזמרה";
		chaptersNames[Util.TEFILA][15] = "תפילה: טו - קריאת שמע";
		chaptersNames[Util.TEFILA][16] = "תפילה: טז - ברכות קריאת שמע";
		chaptersNames[Util.TEFILA][17] = "תפילה: יז - תפילת עמידה";
		chaptersNames[Util.TEFILA][18] = "תפילה: יח - טעויות הזכרות ושכחה";
		chaptersNames[Util.TEFILA][19] = "תפילה: יט - חזרת הש\"ץ";
		chaptersNames[Util.TEFILA][20] = "תפילה: כ - ברכת כהנים";
		chaptersNames[Util.TEFILA][21] = "תפילה: כא - נפילת אפיים ותחנונים";
		chaptersNames[Util.TEFILA][22] = "תפילה: כב - מדיני קריאת התורה";
		chaptersNames[Util.TEFILA][23] = "תפילה: כג - סיום שחרית ודיני קדיש";
		chaptersNames[Util.TEFILA][24] = "תפילה: כד - תפילת מנחה";
		chaptersNames[Util.TEFILA][25] = "תפילה: כה - תפילת מעריב";
		chaptersNames[Util.TEFILA][26] = "תפילה: כו - קריאת שמע על המיטה"; 
		/*TEFILAT_NASHIM*/
		chaptersNames[Util.TEFILAT_NASHIM][0] = "תפילת נשים: תוכן";
		chaptersNames[Util.TEFILAT_NASHIM][1] = "תפילת נשים: א - יסודות הלכות תפילה";
		chaptersNames[Util.TEFILAT_NASHIM][2] = "תפילת נשים: ב - מצוות תפילה לנשים";
		chaptersNames[Util.TEFILAT_NASHIM][3] = "תפילת נשים: ג - טעמי מצוות הנשים";
		chaptersNames[Util.TEFILAT_NASHIM][4] = "תפילת נשים: ד - השכמת הבוקר";
		chaptersNames[Util.TEFILAT_NASHIM][5] = "תפילת נשים: ה - נטילת ידיים שחרית";
		chaptersNames[Util.TEFILAT_NASHIM][6] = "תפילת נשים: ו - ברכות השחר";
		chaptersNames[Util.TEFILAT_NASHIM][7] = "תפילת נשים: ז - ברכות התורה";
		chaptersNames[Util.TEFILAT_NASHIM][8] = "תפילת נשים: ח - תפילת שחרית והדינים שלפניה";
		chaptersNames[Util.TEFILAT_NASHIM][9]  = "תפילת נשים: ט - הכנת הגוף";
		chaptersNames[Util.TEFILAT_NASHIM][10] = "תפילת נשים: י - הכנת הנפש והלבוש";
		chaptersNames[Util.TEFILAT_NASHIM][11] = "תפילת נשים: יא - מקום התפילה";
		chaptersNames[Util.TEFILAT_NASHIM][12] = "תפילת נשים: יב - תפילת עמידה";
		chaptersNames[Util.TEFILAT_NASHIM][13] = "תפילת נשים: יג - הזכרת גשמים ובקשתם";
		chaptersNames[Util.TEFILAT_NASHIM][14] = "תפילת נשים: יד - כבוד התפילה";
		chaptersNames[Util.TEFILAT_NASHIM][15] = "תפילת נשים: טו - קרבנות ופסוקי דזמרה";
		chaptersNames[Util.TEFILAT_NASHIM][16] = "תפילת נשים: טז - קריאת שמע וברכותיה";
		chaptersNames[Util.TEFILAT_NASHIM][17] = "תפילת נשים: יז - התפילות שלאחר עמידה";
		chaptersNames[Util.TEFILAT_NASHIM][18] = "תפילת נשים: יח - מנחה וערכית";
		chaptersNames[Util.TEFILAT_NASHIM][19] = "תפילת נשים: יט - קריאת שמע על המיטה";
		chaptersNames[Util.TEFILAT_NASHIM][20] = "תפילת נשים: כ - מהלכות התפילה במניין";
		chaptersNames[Util.TEFILAT_NASHIM][21] = "תפילת נשים: כא - מהלכות בית הכנסת";
		chaptersNames[Util.TEFILAT_NASHIM][22] = "תפילת נשים: כב - תפילה וקידוש בשבת";
		chaptersNames[Util.TEFILAT_NASHIM][23] = "תפילת נשים: כג - מהלכות חגים ומועדים";
		chaptersNames[Util.TEFILAT_NASHIM][24] = "תפילת נשים: כד - נוסחי התפלה ומנהגי העדות";
		/*HAR_MOADIM*/
		chaptersNames[Util.HAR_MOADIM][0]  = "הר' מועדים: תוכן";
		chaptersNames[Util.HAR_MOADIM][1]  = "הר' מועדים: א - פתיחה";
		chaptersNames[Util.HAR_MOADIM][2]  = "הר' מועדים: ב - דיני עשה ביום טוב";
		chaptersNames[Util.HAR_MOADIM][3]  = "הר' מועדים: ג - כללי המלאכות";
		chaptersNames[Util.HAR_MOADIM][4]  = "הר' מועדים: ד - מלאכות המאכלים";
		chaptersNames[Util.HAR_MOADIM][5]  = "הר' מועדים: ה - הבערה כיבוי וחשמל";
		chaptersNames[Util.HAR_MOADIM][6]  = "הר' מועדים: ו - הוצאה ומוקצה";
		chaptersNames[Util.HAR_MOADIM][7]  = "הר' מועדים: ז - מדיני יום טוב";
		chaptersNames[Util.HAR_MOADIM][8]  = "הר' מועדים: ח - עירוב תבשילין";
		chaptersNames[Util.HAR_MOADIM][9]  = "הר' מועדים: ט - יום טוב שני של גלויות";
		chaptersNames[Util.HAR_MOADIM][10] = "הר' מועדים: י - מצוות חול המועד";
		chaptersNames[Util.HAR_MOADIM][11] = "הר' מועדים: יא - מלאכת חול המועד";
		chaptersNames[Util.HAR_MOADIM][12] = "הר' מועדים: יב - היתרי עבודה במועד";
		/*HAR_SUCOT*/
		chaptersNames[Util.HAR_SUCOT][0]  = "הר' סוכות: תוכן";
		chaptersNames[Util.HAR_SUCOT][1]  = "הר' סוכות: א - חג הסוכות";
		chaptersNames[Util.HAR_SUCOT][2]  = "הר' סוכות: ב - הלכות סוכה";
		chaptersNames[Util.HAR_SUCOT][3]  = "הר' סוכות: ג - ישיבה בסוכה";
		chaptersNames[Util.HAR_SUCOT][4]  = "הר' סוכות: ד - ארבעת המינים";
		chaptersNames[Util.HAR_SUCOT][5]  = "הר' סוכות: ה - נטילת לולב";
		chaptersNames[Util.HAR_SUCOT][6]  = "הר' סוכות: ו - הושענא רבה";
		chaptersNames[Util.HAR_SUCOT][7]  = "הר' סוכות: ז - שמיני עצרת";
		chaptersNames[Util.HAR_SUCOT][8]  = "הר' סוכות: ח - הקהל";
		/*HAR_SHABAT*/
		chaptersNames[Util.HAR_SHABAT][0]  = "הר' שבת: תוכן";
		chaptersNames[Util.HAR_SHABAT][1]  = "הר' שבת: א - פתיחה";
		chaptersNames[Util.HAR_SHABAT][2]  = "הר' שבת: ב - הכנות לשבת";
		chaptersNames[Util.HAR_SHABAT][3]  = "הר' שבת: ג - זמני השבת";
		chaptersNames[Util.HAR_SHABAT][4]  = "הר' שבת: ד - הדלקת נרות שבת";
		chaptersNames[Util.HAR_SHABAT][5]  = "הר' שבת: ה - תורה ותפילה בשבת";
		chaptersNames[Util.HAR_SHABAT][6]  = "הר' שבת: ו - הלכות קידוש";
		chaptersNames[Util.HAR_SHABAT][7]  = "הר' שבת: ז - סעודות השבת ומלווה מלכה";
		chaptersNames[Util.HAR_SHABAT][8]  = "הר' שבת: ח - הבדלה ומוצאי שבת";
		chaptersNames[Util.HAR_SHABAT][9]  = "הר' שבת: ט - כללי המלאכות";
		chaptersNames[Util.HAR_SHABAT][10] = "הר' שבת: י - בישול";
		chaptersNames[Util.HAR_SHABAT][11] = "הר' שבת: יא - בורר";
		chaptersNames[Util.HAR_SHABAT][12] = "הר' שבת: יב - הכנת מאכלים";
		chaptersNames[Util.HAR_SHABAT][13] = "הר' שבת: יג - מלאכות הבגד";
		chaptersNames[Util.HAR_SHABAT][14] = "הר' שבת: יד - הטיפול בגוף";
		chaptersNames[Util.HAR_SHABAT][15] = "הר' שבת: טו - בונה סותר בבית וכלים";
		chaptersNames[Util.HAR_SHABAT][16] = "הר' שבת: טז - מבעיר ומכבה";
		chaptersNames[Util.HAR_SHABAT][17] = "הר' שבת: יז - חשמל ומכשיריו";
		chaptersNames[Util.HAR_SHABAT][18] = "הר' שבת: יח - כותב מוחק וצובע";
		chaptersNames[Util.HAR_SHABAT][19] = "הר' שבת: יט - מלאכות שבצומח";
		chaptersNames[Util.HAR_SHABAT][20] = "הר' שבת: כ - בעלי חיים";
		chaptersNames[Util.HAR_SHABAT][21] = "הר' שבת: כא - הלכות הוצאה";
		chaptersNames[Util.HAR_SHABAT][22] = "הר' שבת: כב - צביון השבת";
		chaptersNames[Util.HAR_SHABAT][23] = "הר' שבת: כג - מוקצה";
		chaptersNames[Util.HAR_SHABAT][24] = "הר' שבת: כד - דיני קטן";
		chaptersNames[Util.HAR_SHABAT][25] = "הר' שבת: כה - מלאכת גוי";
		chaptersNames[Util.HAR_SHABAT][26] = "הר' שבת: כו - מעשה שבת ולפני עיוור";
		chaptersNames[Util.HAR_SHABAT][27] = "הר' שבת: כז - פיקוח נפש וחולה";
		chaptersNames[Util.HAR_SHABAT][28] = "הר' שבת: כח - חולה שאינו מסוכן";
		chaptersNames[Util.HAR_SHABAT][29] = "הר' שבת: כט - עירובין";
		chaptersNames[Util.HAR_SHABAT][30] = "הר' שבת: ל - תחומי שבת";
		/*HAR_SIMCHAT*/
		chaptersNames[Util.HAR_SIMCHAT][0]  = "הר' שמחת: תוכן";
		chaptersNames[Util.HAR_SIMCHAT][1]  = "הר' שמחת: א - מצוות עונה";
		chaptersNames[Util.HAR_SIMCHAT][2]  = "הר' שמחת: ב - הלכות עונה";
		chaptersNames[Util.HAR_SIMCHAT][3]  = "הר' שמחת: ג - קדושה וכוונה";
		chaptersNames[Util.HAR_SIMCHAT][4]  = "הר' שמחת: ד - שמירת הברית";
		chaptersNames[Util.HAR_SIMCHAT][5]  = "הר' שמחת: ה - פרו ורבו";
		chaptersNames[Util.HAR_SIMCHAT][6]  = "הר' שמחת: ו - קשיים ועקרות";
		chaptersNames[Util.HAR_SIMCHAT][7]  = "הר' שמחת: ז - סריס והשחתה";
		chaptersNames[Util.HAR_SIMCHAT][8]  = "הר' שמחת: ח - נחמת חשוכי ילדים";
		chaptersNames[Util.HAR_SIMCHAT][9]  = "הר' שמחת: ט - הפסקת הריון";
		chaptersNames[Util.HAR_SIMCHAT][10] = "הר' שמחת: י - האיש והאשה";
		/*HAR_BRACHOT*/
		chaptersNames[Util.HAR_BRACHOT][0]  = "הר' ברכות: תוכן";
		chaptersNames[Util.HAR_BRACHOT][1]  = "הר' ברכות: א - פתיחה";
		chaptersNames[Util.HAR_BRACHOT][2]  = "הר' ברכות: ב - נטילת ידיים לסעודה";
		chaptersNames[Util.HAR_BRACHOT][3]  = "הר' ברכות: ג - ברכת המוציא";
		chaptersNames[Util.HAR_BRACHOT][4]  = "הר' ברכות: ד - ברכת המזון";
		chaptersNames[Util.HAR_BRACHOT][5]  = "הר' ברכות: ה - זימון";
		chaptersNames[Util.HAR_BRACHOT][6]  = "הר' ברכות: ו - חמשת מיני דגן";
		chaptersNames[Util.HAR_BRACHOT][7]  = "הר' ברכות: ז - ברכת היין";
		chaptersNames[Util.HAR_BRACHOT][8]  = "הר' ברכות: ח - ברכת הפירות ושהכל";
		chaptersNames[Util.HAR_BRACHOT][9]  = "הר' ברכות: ט - כללי ברכה ראשונה";
		chaptersNames[Util.HAR_BRACHOT][10] = "הר' ברכות: י - ברכה אחרונה";
		chaptersNames[Util.HAR_BRACHOT][11] = "הר' ברכות: יא - עיקר וטפל";
		chaptersNames[Util.HAR_BRACHOT][12] = "הר' ברכות: יב - כללי ברכות";
		chaptersNames[Util.HAR_BRACHOT][13] = "הר' ברכות: יג - דרך ארץ";
		chaptersNames[Util.HAR_BRACHOT][14] = "הר' ברכות: יד - ברכת הריח";
		chaptersNames[Util.HAR_BRACHOT][15] = "הר' ברכות: טו - ברכות הראייה";
		chaptersNames[Util.HAR_BRACHOT][16] = "הר' ברכות: טז - ברכת הגומל";
		chaptersNames[Util.HAR_BRACHOT][17] = "הר' ברכות: יז - ברכות ההודאה והשמחה";
		/*HAR_YAMIM*/
		chaptersNames[Util.HAR_YAMIM][0] = "הר' ימים נוראים: תוכן";
		chaptersNames[Util.HAR_YAMIM][1] = "הר' ימים נוראים: א - הדין השכר והעונש";
		chaptersNames[Util.HAR_YAMIM][2] = "הר' ימים נוראים: ב - סליחות ותפילות";
		chaptersNames[Util.HAR_YAMIM][3] = "הר' ימים נוראים: ג - ראש השנה";
		chaptersNames[Util.HAR_YAMIM][4] = "הר' ימים נוראים: ד - מצוות השופר";
		chaptersNames[Util.HAR_YAMIM][5] = "הר' ימים נוראים: ה - עשרת ימי תשובה";
		chaptersNames[Util.HAR_YAMIM][6] = "הר' ימים נוראים: ו - יום הכיפורים";
		chaptersNames[Util.HAR_YAMIM][7] = "הר' ימים נוראים: ז - הלכות יום הכיפורים";
		chaptersNames[Util.HAR_YAMIM][8] = "הר' ימים נוראים: ח - דיני התענית";
		chaptersNames[Util.HAR_YAMIM][9] = "הר' ימים נוראים: ט - שאר עינויים";
		chaptersNames[Util.HAR_YAMIM][10] = "הר' ימים נוראים: י - עבודת יום הכיפורים";
	}

	void innerSearch()
	{
		// custom dialog
		innerSearchDialog = new Dialog(context);
		innerSearchDialog.setContentView(R.layout.inner_search);
		innerSearchDialog.setTitle("חיפוש בפרק הנוכחי");

		Button dialogButton = (Button) innerSearchDialog.findViewById(R.id.dialogButtonOK);
		TextToSearch = (EditText) innerSearchDialog.findViewById(R.id.editTextTextToSearch );

		// if button is clicked
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) 
			{
				innerSearchText = TextToSearch.getText().toString();

				innerSearchDialog.dismiss();
				lnrFindOptions.setVisibility(View.VISIBLE);
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
			}
		});
		innerSearchDialog.show();
	}

	void addBookmark(){
		bookmarkDialog = new Dialog(context);
		if(MyLanguage == Util.ENGLISH)
			bookmarkDialog.setContentView(R.layout.add_bookmark_english);
		else if(MyLanguage == Util.RUSSIAN)
			bookmarkDialog.setContentView(R.layout.add_bookmark_russian);
		else if(MyLanguage == Util.SPANISH)
			bookmarkDialog.setContentView(R.layout.add_bookmark_spanish);
		else if(MyLanguage == Util.FRENCH)
			bookmarkDialog.setContentView(R.layout.add_bookmark_french);
		else
			bookmarkDialog.setContentView(R.layout.add_bookmark);
		bookmarkDialog.setTitle("הוסף סימניה");

		Button dialogButton = (Button) bookmarkDialog.findViewById(R.id.dialogButtonOK);
		spinner1 = (Spinner) bookmarkDialog.findViewById(R.id.spinner1);
		BookmarkName = (EditText) bookmarkDialog.findViewById(R.id.editTextBookmarkName);

		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int index = 0, index_end = 0;
				String bookmarkText = BookmarkName.getText().toString();
				bookmarkText.replaceAll(",", "-");/*if the user insert comma, replace it with "-"*/
				/*		      bookmark name			book					chapter						scroll							fontSize*/
				strBookmark = bookmarkText + "," + book_chapter[0] + "," + book_chapter[1] + "," + webview.getScrollY() + "," + (int) (fontSize)/*(webview.getScale()*100)*/;

				Bookmarks = mPrefs.getString("Bookmarks", "");
				if((index = Bookmarks.indexOf(bookmarkText))!=-1)/*if there is already bookmark with the same name override it*/
				{
					index_end = index;
					for(int i=0; i<5; i++)
					{
						if(Bookmarks.indexOf(",", index_end+1) != -1)
							index_end = Bookmarks.indexOf(",", index_end + 1);
						else/*in case that this is the last bookmark*/
							index_end = Bookmarks.length();
					}
					Bookmarks = Bookmarks.substring(0, index) + strBookmark + Bookmarks.substring(index_end, Bookmarks.length());
					if(MyLanguage == Util.ENGLISH)
						Toast.makeText(getApplicationContext(),	"Existing bookmark updated", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.RUSSIAN)
						Toast.makeText(getApplicationContext(),	"Текущая закладка обновлена", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.SPANISH)
						Toast.makeText(getApplicationContext(),	"Marcador existente actualizado", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.FRENCH)
						Toast.makeText(getApplicationContext(),	"Le signet existant est mis à jour", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(),	"הסימניה הקיימת עודכנה", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Bookmarks += "," + strBookmark;
					if(MyLanguage == Util.ENGLISH)
						Toast.makeText(getApplicationContext(),	"New bookmark created", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.RUSSIAN)
						Toast.makeText(getApplicationContext(),	"Создана новая закладка", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.SPANISH)
						Toast.makeText(getApplicationContext(),	"Nuevo marcador creado", Toast.LENGTH_SHORT).show();
					else if(MyLanguage == Util.FRENCH)
						Toast.makeText(getApplicationContext(),	"Nouveau signet créé", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(),	"סימניה חדשה נוצרה", Toast.LENGTH_SHORT).show();
				}
				shPrefEditor.putString("Bookmarks", Bookmarks);
				shPrefEditor.commit();
				bookmarkDialog.dismiss();
			}
		});

		fillChaptersNames();
		BookmarkName.setText(chaptersNames[book_chapter[0]][book_chapter[1]]);

		addItemsOnSpinner();

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			boolean first=true;
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
			{
				if (first==false)
					BookmarkName.setText(parent.getItemAtPosition(pos).toString());
				first = false;
			}

			public void onNothingSelected(AdapterView<?> arg0)
			{
				// do nothing
			}
		});

		bookmarkDialog.show();
	}

	void acronymsDecode()
	{
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
		dialogButtonExit.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				acronymsDialog.dismiss();
			}
		});
		
		dialogButtonDecode.setOnClickListener(new OnClickListener() {
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
	
	void autoScrollSpeedDialog()
	{
		final Context context = this;

		// custom dialog
		autoScrollDialog = new Dialog(context);
		autoScrollDialog.setContentView(R.layout.auto_scroll);
		TextView tvAutoScrollHeader = autoScrollDialog.findViewById(R.id.tvAutoScrollHeader);
		List<String> header = util.getTextArray(Util.TextArrayEnum.AUTO_SCROLL_SET_SPEED_DIALOG_HEADER);
		tvAutoScrollHeader.setText(header.get(0));
		Button dialogButton = (Button) autoScrollDialog.findViewById(R.id.dialogButtonOK);
		
		// if button is clicked
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) 
			{
				autoScrollDialog.dismiss();
			}
		});
		spinnerAutoScroll = (Spinner) autoScrollDialog.findViewById(R.id.spinner_auto_scroll);
		scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
		spinnerAutoScroll.setSelection((scrollSpeed / 2) - 1);
		spinnerAutoScroll.setOnItemSelectedListener(new OnItemSelectedListener() {
			boolean first = true;

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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
}
