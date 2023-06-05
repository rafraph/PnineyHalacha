package com.rafraph.pnineyHalachaHashalem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
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
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;

/*jsoup*/




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressLint("SetJavaScriptEnabled")
public class textMain extends AppCompatActivity implements View.OnClickListener//, OnGestureListener
{
	// Create a reference from an HTTPS URL
	// Note that in the URL, characters are URL escaped!
	//StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");
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
	private static final int HAR_BRACHOT    = 18;
	private static final int HAR_YAMIM      = 19;
	private static final int HAR_MOADIM     = 20;
	private static final int HAR_SUCOT      = 21;
	private static final int HAR_SHABAT     = 22;
	private static final int HAR_SIMCHAT    = 23;
	private static final int BOOKS_HEB_NUMBER=24;
	private static final int E_TEFILA       = 24;
	private static final int E_PESACH       = 25;
	private static final int E_ZMANIM       = 26;
	private static final int E_WOMEN_PRAYER = 27;
	private static final int E_SHABAT       = 28;
	private static final int E_YAMMIM 	    = 29;
	private static final int E_MOADIM   	= 30;
	private static final int E_SIMCHAT     	= 31;
	private static final int S_SHABAT       = 32;
	private static final int S_BRACHOT       =33;
	private static final int S_MOADIM        =34;
	private static final int S_YAMIM         =35;
	private static final int S_PESACH        =36;
	private static final int S_SIMCHAT       =37;
	private static final int S_TFILA         =38;
	private static final int S_TFILAT_NASHIM =39;
	private static final int S_ZMANIM        =40;
	private static final int R_HAAM         = 41;
	private static final int R_SHABBAT      = 42;
	private static final int R_YAMMIM       = 43;
	private static final int R_SUCOT        = 44;
	private static final int R_SIMCHAT      = 45;
	private static final int R_MISHPHACHA   = 46;
	private static final int R_PESACH       = 47;
	private static final int R_MOADIM       = 48;
	private static final int R_TEFILAT_NASHIM=49;
	private static final int R_TFILA         =50;
	private static final int R_ZMANIM        =51;
	private static final int F_TEFILA         =52;
	private static final int F_MOADIM        =53;
	private static final int F_SUCOT         =54;
	private static final int F_ZMANIM        =55;
	private static final int F_SIMCHAT       =56;
	private static final int F_PESACH        =57;
	private static final int F_SHABBAT       =58;
	private static final int F_YAMMIM        =59;
	private static final int F_TFILAT_NASHIM =60;
	private static final int BOOKS_NUMBER	= 61;

	public static int scrollSpeed=-2;

	/*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29   30  31  32  33  34  35  36  37  38  39  40  41  42  43  44 45  46  47  48  49  50  51  52  53  54 55  56  57  58  59  60*/
	public int[] lastChapter = {18, 11, 17, 10, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10, 26, 16, 15, 24, 30, 10 , 13,  9, 30, 18, 13, 10, 16, 10, 26, 24, 17, 10, 30, 10, 8, 10, 10, 16, 13, 24, 26, 17, 26, 13, 8 ,17, 10, 16, 30, 10, 24  };
	public int[] haveAudio={BRACHOT,HAAMVEHAAREZ,ZMANIM,TAHARAT,YAMIM,KASHRUT_A,KASHRUT_B,MOADIM,SUCOT,PESACH,SHVIIT,SIMCHAT,SHABAT,TEFILA,MISHPACHA};
	public Dialog dialogModes;
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
	public boolean isNotHeb=true;
	public static  ImageView scroll;
	public static Bundle extras;
	WebView webview;
	public static int[] book_chapter = new int[2];
	boolean cameFromSearch = false, firstTime = true, ChangeChapter = false,gi=false;
	String searchPosition = null, sectionsForToast = null;
	ImageButton bParagraphs, bSwitchModes, bNext_sec, bPrevious_sec, bNext_page, bPrevious_page, bFindNext, bFindPrevious;
	LinearLayout llMainLayout;
	String stHeadersArr;
	Elements headers;
	String fileName, fileNameOnly, lastFileName = null;
	String[][] chaptersFiles = new String[BOOKS_NUMBER][31];
	private LinearLayout lnrOptions, lnrFindOptions;
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	int scrollY = 0;
	public int BlackBackground=0, SleepScreen=1, cbFullScreen=1, cbAssistButtons=1;
	boolean bookmark = false;
	Document doc = null;
	static MenuInflater inflater;
	static public ActionBar textActionBar;
	public String query, title;
	public String note_id;
	public String audio_id;
	public Resources resources;
	static byte fullScreenFlag = 0;
	public static byte rotate = 0;
	public String noteStr = "0";
	public int MyLanguage;

	/*for bookmarks*/
	public List<String> bookmarks_array_names = new ArrayList<String>();
	public EditText result;
	public Spinner spinner1, spinnerAutoScroll;
	public EditText BookmarkName, TextToSearch, TextToDecode;
	public Dialog bookmarkDialog, innerSearchDialog, acronymsDialog, autoScrollDialog;
	String[][] chaptersNames = new String[BOOKS_NUMBER][31];
	String innerSearchText, acronymsText;

	public int API;
	static public boolean jumpToSectionFlag = false;
	public File localFile;
	public static void downLocal(Context context,String fileName)
	{
		File f1=new File(context.getFilesDir() +"/"+fileName);
		String a ="https://ph.yhb.org.il/wp-content/themes/s/"+fileName;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		URL url = null;
		String data="";
		try {
			url = new URL(a);

			URLConnection conn = url.openConnection();
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			inputLine = br.readLine();
			while(inputLine!=null)
			{
				data+=inputLine;
				inputLine = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			FileWriter writer = new FileWriter(f1);
			writer.append(data);
			writer.flush();
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("JavascriptInterface")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Context context = this;
		setContentView(R.layout.text_main_down);
		loadActivity();
		if (BlackBackground == 1) {
			webview.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView view, String url) {
					view.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
				}
			});
			webview.setBackgroundColor(Color.BLACK);//black
			//infView.setBackgroundColor(Color.BLACK);
		}
	}//onCreate

	@Override
	protected void onDestroy() {
		if (webview != null)
			webview.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		try {
			if (lnrFindOptions.getTag().equals("vis")) {
				lnrFindOptions.setVisibility(View.GONE);
				lnrOptions.setVisibility(View.VISIBLE);
				lnrFindOptions.setTag("from search");
//				webview.findAllAsync("sdafsdhgfsdagfhgdszgf");
			}
			if (lnrFindOptions.getTag().equals("from search")) {
				Class ourClass = null;
				Intent ourIntent = null;
				try {
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
					//book_chapter[0]

					shPrefEditor.putInt("expList", book_chapter[0]);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ourIntent = new Intent(textMain.this, ourClass);
				startActivity(ourIntent);
			} else {
				Class ourClass = null;
				Intent ourIntent = null;
				try {
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
					//book_chapter[0]
					shPrefEditor.putInt("expList", book_chapter[0]);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ourIntent = new Intent(textMain.this, ourClass);
				ourIntent.putExtra("homePage", false);
				int reduce=0;
				if(book_chapter[0]<=24)
					reduce=0;
				if(book_chapter[0]>24&&book_chapter[0]<=31)
				{
					if(!mPrefs.getBoolean("h_exists",true))
						reduce-=24;
				}
				if(book_chapter[0]>31&&book_chapter[0]<=40)
				{
					if(!mPrefs.getBoolean("h_exists",true))
						reduce-=24;
					if(!mPrefs.getBoolean("en_exists",true))
						reduce-=8;
				}
				if(book_chapter[0]>40&&book_chapter[0]<=51)
				{
					if(!mPrefs.getBoolean("h_exists",true))
						reduce-=24;
					if(!mPrefs.getBoolean("en_exists",true))
						reduce-=8;
					if(!mPrefs.getBoolean("es_exists",true))
						reduce-=9;
				}
				if(book_chapter[0]>51)
				{
					if(!mPrefs.getBoolean("h_exists",true))
						reduce-=24;
					if(!mPrefs.getBoolean("en_exists",true))
						reduce-=8;
					if(!mPrefs.getBoolean("es_exists",true))
						reduce-=9;
					if(!mPrefs.getBoolean("r_exists",true))
						reduce-=11;
				}

				ourIntent.putExtra("exp", book_chapter[0]+reduce);
				startActivity(ourIntent);
			}
		}
		catch (Exception exp)
		{
			Toast.makeText(getApplicationContext(),	"t:"+exp.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	public void loadWebview(String path, WebView webview)
	{
		//final ProgressDialog downloadWait = ProgressDialog.show(textMain.this, "", "please wait");
		new Thread() {
			public void run() {
				try {
					textMain.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							File file = new File(path);
							if(file.exists())
								webview.loadUrl(path);
							else
							{
								downLocal(getBaseContext(),path.split("/")[path.split("/").length-1]);
								webview.loadUrl(path);
								webview.loadUrl(path);
								webview.setWebViewClient(new WebViewClient() {
									@SuppressLint("SuspiciousIndentation")
									public void onPageFinished(WebView view, String url) {
										if (BlackBackground==1)
											webview.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
									}
								});
							}
						}
					});
				} catch (Exception e) {

				}
				//downloadWait.dismiss();
			}
		}.start();
	}

//	private static void unzip(String zipFile, String location,String specificFile) {
//		try {
//			File f = new File(location);
//			if (!f.isDirectory()) {
//				f.mkdirs();
//			}
//			ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
//			try {
//				ZipEntry ze = null;
//
//				while ((ze = zin.getNextEntry()) != null) {
//					String path = location + File.separator + ze.getName();
//
//					if(ze.getName().equals(specificFile)){
//						if (ze.isDirectory()) {
//							File unzipFile = new File(path);
//							if (!unzipFile.isDirectory()) {
//								unzipFile.mkdirs();
//							}
//						} else {
//							FileOutputStream fout = new FileOutputStream(path, false);
//
//							try {
//								for (int c = zin.read(); c != -1; c = zin.read()) {
//									fout.write(c);
//								}
//								zin.closeEntry();
//							} finally {
//								fout.close();
//							}
//						}
//					}
//				}
//			} finally {
//				zin.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private void showPopupAutoScroolSettings(View v)
	{
		android.support.v7.view.ContextThemeWrapper ctw = new ContextThemeWrapper(textMain.this, R.style.CustomPopupTheme2);
		PopupMenu popupMenu = new PopupMenu(ctw, v);
		popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
			@Override
			public void onDismiss(PopupMenu menu) {
				scroll.setImageResource(R.drawable.auto_scrool);
			}
		});
		String configHeaders[] = new String[12];

		configHeaders[0] = "                                     ";
		configHeaders[1] = "                                    0";
		configHeaders[2] = "                                    1";
		configHeaders[3] = "                                    2";
		configHeaders[4] = "                                    3";
		configHeaders[5] = "                                    4";
		configHeaders[6] = "                                    5";
		configHeaders[7] = "                                    6";
		configHeaders[8] = "                                    7";
		configHeaders[9] = "                                    8";
		configHeaders[10] = "                                    9";
		configHeaders[11] = "                                    10";

		popupMenu.getMenu().add(0, 0, 0, configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
		popupMenu.getMenu().add(0, 1, 1, configHeaders[1]);
		popupMenu.getMenu().add(0, 2, 2, configHeaders[2]);
		popupMenu.getMenu().add(0, 3, 3, configHeaders[3]);
		popupMenu.getMenu().add(0, 4, 4, configHeaders[4]);
		popupMenu.getMenu().add(0, 5, 5, configHeaders[5]);
		popupMenu.getMenu().add(0, 6, 6, configHeaders[6]);
		popupMenu.getMenu().add(0, 7, 7, configHeaders[7]);
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				WebSettings webSettings = webview.getSettings();
				webSettings.setMinimumFontSize(mPrefs.getInt("fontSize",20));
				switch (item.getItemId()) {
					case 1:
						scrollSpeed = -1;
						break;
					case 2:
						scrollSpeed = 2;
						runOnUiThread(mScrollDown);
						break;
					case 3:
						scrollSpeed = 4;
						runOnUiThread(mScrollDown);
						break;
					case 4:
						scrollSpeed = 6;
						runOnUiThread(mScrollDown);
						break;
					case 5:
						scrollSpeed = 8;
						runOnUiThread(mScrollDown);
						break;
					case 6:
						scrollSpeed = 10;
						runOnUiThread(mScrollDown);
						break;
					case 7:
						scrollSpeed = 12;
						runOnUiThread(mScrollDown);
						break;
					case 8:
						scrollSpeed = 7;
						runOnUiThread(mScrollDown);
						break;
					case 9:
						scrollSpeed = 16;
						runOnUiThread(mScrollDown);
						break;
					case 10:
						scrollSpeed = 18;
						runOnUiThread(mScrollDown);
						break;
					case 11:
						scrollSpeed = 20;
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

	private void loadActivity() {
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();

		MyLanguage = mPrefs.getInt("MyLanguage", 0);
		firstTime = true;
		book_chapter[0] = -1;
		book_chapter[1] = -1;
		fillChaptersNames();
		int fromBookmarks = 0;
		lnrOptions = (LinearLayout) findViewById(R.id.lnrOptions);
		lnrFindOptions = (LinearLayout) findViewById(R.id.lnrFindOptions);
		ImageView menu = (ImageView) findViewById(R.id.menu);
		menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContextThemeWrapper ctw = new ContextThemeWrapper(textMain.this, R.style.CustomPopupTheme);
				PopupMenu popupMenu = new PopupMenu(ctw, v);
				if (MyLanguage == ENGLISH) {
					popupMenu.getMenu().add(0, -1, 0, "Homepage");
					popupMenu.getMenu().add(0, 0, 0, "Settings");
					popupMenu.getMenu().add(0, 1, 0, "Books");
					popupMenu.getMenu().add(0, 2, 0, "Daily Study");
					popupMenu.getMenu().add(0, 3, 0, "Search");
					popupMenu.getMenu().add(0, 5, 0, "Contact Us");
					popupMenu.getMenu().add(0, 6, 0, "Purchasing books");
					popupMenu.getMenu().add(0, 7, 0, "Ask the Rabbi");
					//booksDownload configHeaders[6] = "ספרים להורדה";
					popupMenu.getMenu().add(0, 8, 0, "About the series");
					popupMenu.getMenu().add(0, 9, 0, "About");
				} else if (MyLanguage == RUSSIAN) {
					popupMenu.getMenu().add(0, -1, 0, "домашняя страница");
					popupMenu.getMenu().add(0, 0, 0, "Настройки");
					popupMenu.getMenu().add(0, 1, 0, "Книги");
					popupMenu.getMenu().add(0, 2, 0, "Ежедневное изучение");
					popupMenu.getMenu().add(0, 3, 0, "Поиск");
					popupMenu.getMenu().add(0, 5, 0, "Отзыв");
					popupMenu.getMenu().add(0, 6, 0, "Список книг");
					popupMenu.getMenu().add(0, 7, 0, "Спросить равина");
					//booksDownload configHeaders[6] = "ספרים להורדה";
					popupMenu.getMenu().add(0, 8, 0, "О серии книг");
					popupMenu.getMenu().add(0, 9, 0, "О приложении");
				} else if (MyLanguage == SPANISH) {
					popupMenu.getMenu().add(0, -1, 0, "Página principal");
					popupMenu.getMenu().add(0, 0, 0, "Definiciones");
					popupMenu.getMenu().add(0, 1, 0, "Libros");
					popupMenu.getMenu().add(0, 2, 0, "Estudio diario");
					popupMenu.getMenu().add(0, 3, 0, "Búsqueda");
					popupMenu.getMenu().add(0, 5, 0, "Retroalimentación");
					popupMenu.getMenu().add(0, 6, 0, "Compra de libros");
					popupMenu.getMenu().add(0, 7, 0, "Pregúntale al rabino");
					//booksDownload configHeaders[6] = "ספרים להורדה";
					popupMenu.getMenu().add(0, 8, 0, "En la serie");
					popupMenu.getMenu().add(0, 9, 0, "Sobre");
				} else if (MyLanguage == FRENCH) {
					popupMenu.getMenu().add(0, -1, 0, "Page d'accueil");
					popupMenu.getMenu().add(0, 0, 0, "Réglages");
					popupMenu.getMenu().add(0, 1, 0, "Livres");
					popupMenu.getMenu().add(0, 2, 0, "étude quotidienne");
					popupMenu.getMenu().add(0, 3, 0, "Recherche");
					popupMenu.getMenu().add(0, 5, 0, "Contact Us");
					popupMenu.getMenu().add(0, 6, 0, "Achat de livres");
					popupMenu.getMenu().add(0, 7, 0, "Demander au rav");
					//booksDownload configHeaders[6] = "ספרים להורדה";
					popupMenu.getMenu().add(0, 8, 0, "Sur la collection");
					popupMenu.getMenu().add(0, 9, 0, "À propos");
				} else {/*this is the default*/
					popupMenu.getMenu().add(0, -1, 0, "דף הבית");
					popupMenu.getMenu().add(0, 0, 0, "הגדרות");
					popupMenu.getMenu().add(0, 1, 0, "ספרים");
					popupMenu.getMenu().add(0, 2, 0, "הלימוד היומי");
					popupMenu.getMenu().add(0, 3, 0, "חיפוש");
					popupMenu.getMenu().add(0, 4, 0, "ראשי תיבות");
					popupMenu.getMenu().add(0, 5, 0, "משוב");
					popupMenu.getMenu().add(0, 6, 0, "רכישת ספרים");
					popupMenu.getMenu().add(0, 7, 0, "שאל את הרב");
					//booksDownload configHeaders[6] = "ספרים להורדה";
					popupMenu.getMenu().add(0, 8, 0, "על הסדרה");
					popupMenu.getMenu().add(0, 9, 0, "אודות");
				}
				popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Class ourClass = null;
						Intent ourIntent;
						Intent intent;
						switch (item.getItemId()) {
							case -1:/*Home page*/
								try {
									ourClass = null;
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
									ourIntent = new Intent(textMain.this, ourClass);
									startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								break;
							case 0:/*settings*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(textMain.this, ourClass);
								ourIntent.putExtra("homePage", true);
								shPrefEditor.putString("where", "textMain");
								ourIntent.putExtra("book_chapter", extras.getIntArray("book_chapter"));
								shPrefEditor.commit();
								startActivity(ourIntent);
								break;
							case 1:/*to books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(textMain.this, ourClass);
								ourIntent.putExtra("homePage", false);
								startActivity(ourIntent);
								break;
							case 2:/*pninaYomit*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(textMain.this, ourClass);
								startActivity(ourIntent);
								break;
							case 3:/*search in all books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(textMain.this, ourClass);
								startActivity(ourIntent);
								break;
							case 4:/*acronyms*/
								acronymsDecode();
								break;
							case 5:/*feedback*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
									ourIntent = new Intent(textMain.this, ourClass);
									startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								break;
							case 6:/*buy books*/
								intent = new Intent(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://shop.yhb.org.il/"));

								if (MyLanguage == FRENCH)
									intent.setData(Uri.parse("https://shop.yhb.org.il/fr/"));
								if (MyLanguage == RUSSIAN)
									intent.setData(Uri.parse("https://shop.yhb.org.il/ru/"));
								if (MyLanguage == SPANISH)
									intent.setData(Uri.parse("https://shop.yhb.org.il/es/"));
								if (MyLanguage == ENGLISH)
									intent.setData(Uri.parse("https://shop.yhb.org.il/en/"));
								startActivity(intent);
								break;
							case 7:/*ask the rav*/
								intent = new Intent(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://yhb.org.il/שאל-את-הרב-2/"));
								startActivity(intent);
								break;
							case 8:/*about pninei*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About_p");
									ourIntent = new Intent(textMain.this, ourClass);
									startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								//case 8:/*hascamot*/
								//   hascamotDialog();
								break;
							case 9:/*about*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
									ourIntent = new Intent(textMain.this, ourClass);
									startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
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
		final Context context = this;
		ImageView searchPage = findViewById(R.id.page_search);
		searchPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				innerSearch();
			}
		});
		scroll = findViewById(R.id.auto_scrool);
		scroll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scroll.setImageResource(R.drawable.fill_auto_scroll);
				showPopupAutoScroolSettings(findViewById(R.id.auto_scrool));
			}
		});
		ImageView addMark = findViewById(R.id.make_mark);
		addMark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
				spinner1 = (Spinner) bookmarkDialog.findViewById(R.id.spinner1);
				BookmarkName = (EditText) bookmarkDialog.findViewById(R.id.editTextBookmarkName);

				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (BookmarkName.getText().toString().equals(""))
							Toast.makeText(getApplicationContext(), "cant be empty", Toast.LENGTH_SHORT).show();
						else {
							int index = 0, index_end = 0;
							String bookmarkText = BookmarkName.getText().toString();
							bookmarkText.replaceAll(",", "-");/*if the user insert comma, replace it with "-"*/
							/*		      bookmark name			book					chapter						scroll							fontSize*/
							strBookmark = bookmarkText + "," + book_chapter[0] + "," + book_chapter[1] + "," + webview.getScrollY() + "," + mPrefs.getInt("fontSize", 20)/*(webview.getScale()*100)*/;

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
								if (MyLanguage == ENGLISH)
									Toast.makeText(getApplicationContext(), "Existing bookmark updated", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == RUSSIAN)
									Toast.makeText(getApplicationContext(), "Текущая закладка обновлена", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == SPANISH)
									Toast.makeText(getApplicationContext(), "Marcador existente actualizado", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == FRENCH)
									Toast.makeText(getApplicationContext(), "Le signet existant est mis à jour", Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(getApplicationContext(), "הסימניה הקיימת עודכנה", Toast.LENGTH_SHORT).show();
							} else {
								Bookmarks += "," + strBookmark;
								if (MyLanguage == ENGLISH)
									Toast.makeText(getApplicationContext(), "New bookmark created", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == RUSSIAN)
									Toast.makeText(getApplicationContext(), "Создана новая закладка", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == SPANISH)
									Toast.makeText(getApplicationContext(), "Nuevo marcador creado", Toast.LENGTH_SHORT).show();
								else if (MyLanguage == FRENCH)
									Toast.makeText(getApplicationContext(), "Nouveau signet créé", Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(getApplicationContext(), "סימניה חדשה נוצרה", Toast.LENGTH_SHORT).show();
							}
							shPrefEditor.putString("Bookmarks", Bookmarks);
							shPrefEditor.commit();
							bookmarkDialog.dismiss();
						}
					}
				});

				BookmarkName.setText(chaptersNames[book_chapter[0]][book_chapter[1]]);
				addItemsOnSpinner();

				spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
					boolean first = true;

					public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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

		webview = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = webview.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		API = Build.VERSION.SDK_INT;
		if (API < 19)
			webSettings.setBuiltInZoomControls(true);

		resources = getResources();
		webview.requestFocusFromTouch();
		webview.getSettings().setAllowFileAccess(true);
		webview.setWebViewClient(new MyWebViewClient());
		bParagraphs = (ImageButton) findViewById(R.id.to_main);
		bSwitchModes = (ImageButton) findViewById(R.id.set_note);
		bNext_sec = (ImageButton) findViewById(R.id.ibNext);
		bPrevious_sec = (ImageButton) findViewById(R.id.ibPrevious);
		bNext_page = (ImageButton) findViewById(R.id.ibNextPage);
		bPrevious_page = (ImageButton) findViewById(R.id.ibPreviousPage);
		llMainLayout = (LinearLayout) findViewById(R.id.tooBooks);
		lnrOptions = (LinearLayout) findViewById(R.id.lnrOptions);
		bFindNext = (ImageButton) findViewById(R.id.ibFindNext);
		bFindPrevious = (ImageButton) findViewById(R.id.ibFindPrevious);
		ImageView toMain = (ImageView) findViewById(R.id.too_main);

		if (MyLanguage == ENGLISH)
			toMain.setImageResource(R.drawable.to_main_e);
		if (MyLanguage == RUSSIAN)
			toMain.setImageResource(R.drawable.to_main_r);
		if (MyLanguage == SPANISH)
			toMain.setImageResource(R.drawable.to_main_s);
		if (MyLanguage == FRENCH)
			toMain.setImageResource(R.drawable.to_main_f);
		toMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Class ourClass = null;
					Intent ourIntent;
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
					ourIntent = new Intent(textMain.this, ourClass);
					ourIntent.putExtra("goLast", false);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		bParagraphs.setOnClickListener(this);
		bSwitchModes.setOnClickListener(this);
		bNext_sec.setOnClickListener(this);
		bPrevious_sec.setOnClickListener(this);
		bNext_page.setOnClickListener(this);
		bPrevious_page.setOnClickListener(this);
		bFindNext.setOnClickListener(this);
		bFindPrevious.setOnClickListener(this);

		jumpToSectionFlag = false;

		final Runnable runnableNote = new Runnable() {
			public void run() {
				String note, content = null;
				int intNoteId;
				final Dialog dialog = new Dialog(context);
				WebView webviewNote;
				WebSettings webSettingsNote;
				BlackBackground = mPrefs.getInt("BlackBackground", 0);
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
				if (BlackBackground == 0)
					content += "<body>";//White background
				else if (BlackBackground == 1) {
					content += "<body style=\"background-color:black;color:white\">";//Black background
				}
				ParseTheDoc();
				headers = doc.select("div#ftn" + note_id);
				note = headers.get(0).text();
				if (book_chapter[0] < BOOKS_HEB_NUMBER)/*if this is a hebrew book*/ {
					note = note.substring(6);//in order to remove the prefix of the note. something like [1]
					content += "<p dir=\"RTL\">" + note + "</p> </body></html>";
				} else {
					note = note.substring(3);//in order to remove the prefix of the note. something like [1]
					content += "<p dir=\"LTR\">" + note + "</p> </body></html>";
				}

				webviewNote.loadData(content, "text/html; charset=utf-8", "UTF-8");
				webSettingsNote.setDefaultFontSize(mPrefs.getInt("fontSize", 20));
				dialog.show();

				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						//do whatever you want the back key to do
						dialog.dismiss();
						//scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
					}
				});
			}
		};

		final Runnable runnableAudio = new Runnable() {
			public void run() {
				try {
					if ((isConnected())) {
						Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.myAudio");
						Intent ourIntent = new Intent(textMain.this, ourClass);

						ourIntent.putExtra("audio_id", Integer.parseInt(audio_id));
						ourIntent.putExtra("cameFromText", true);
						ourIntent.putExtra("MyLanguage", MyLanguage);
						ourIntent.putExtra("book_id", book_chapter[0]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("webLink", chaptersFiles[book_chapter[0]][book_chapter[1]]);
						//ourIntent.putExtra("webLink", localFile.getFilesDir());
						ourIntent.putExtra("scroolY", webview.getScrollY());
						ourIntent.putExtra("hearAndRead", false);

						findAllHeaders(ourIntent);
						startActivity(ourIntent);
					} else
						Toast.makeText(getApplicationContext(), "אין חיבור אינטרנט", Toast.LENGTH_LONG).show();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		};

		webview.addJavascriptInterface(new Object() {
			@JavascriptInterface
			public void performClick(String id) {
				//scrollSpeed = 0;
				setNoteId(id);
				runOnUiThread(runnableNote);
			}
		}, "ok");

		webview.addJavascriptInterface(new Object() {
			@JavascriptInterface
			public void performClick(String id) {
				setAudioId(id);
				runOnUiThread(runnableAudio);
			}
		}, "audio");

		fillChaptersFiles();


		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		if (BlackBackground == 1) {
			toMain = (ImageView) findViewById(R.id.too_main);
			ImageView makeMark = (ImageView) findViewById(R.id.make_mark);
			ImageView pageSearch = (ImageView) findViewById(R.id.page_search);
			if (MyLanguage == ENGLISH)
				toMain.setImageResource(R.drawable.to_main_b_e);
			if (MyLanguage == RUSSIAN)
				toMain.setImageResource(R.drawable.to_main_b_r);
			if (MyLanguage == SPANISH)
				toMain.setImageResource(R.drawable.to_main_b_s);
			if (MyLanguage == FRENCH)
				toMain.setImageResource(R.drawable.to_main_b_f);
			if (MyLanguage == HEBREW)
				toMain.setImageResource(R.drawable.to_main_b);
			LinearLayout main = (LinearLayout) findViewById(R.id.lnrOption3);
			ImageView menu2 = (ImageView) findViewById(R.id.menu);
			TextView bookName = (TextView) findViewById(R.id.bookname);
			bookName.setTextColor(Color.WHITE);
			menu2.setImageResource(R.drawable.ic_action_congif_b);
			makeMark.setImageResource(R.drawable.make_bookmark_b);
			searchPage.setImageResource(R.drawable.page_search_b);
			main.setBackgroundColor(Color.rgb(120, 1, 1));
		}

		inflater = getMenuInflater();
		textActionBar = getSupportActionBar();
		extras = getIntent().getExtras();
		if (extras != null) {
			cameFromSearch = extras.getBoolean("cameFromSearch", false);
			searchPosition = extras.getString("searchPosition");
			if (extras.getIntArray("book_chapter") != null)
				book_chapter = extras.getIntArray("book_chapter");
			sectionsForToast = extras.getString("sectionsForToast");
			if (cameFromSearch == true) {
				TextView nameBook = findViewById(R.id.bookname);

				query = extras.getString("query");
				findBookAndChapter();
				nameBook.setText(chaptersNames[book_chapter[0]][book_chapter[1]]);

				if (isNotHeb)
					loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]], webview);

				scrollY = 0;
				lnrFindOptions.setVisibility(View.VISIBLE);
				lnrOptions.setVisibility(View.INVISIBLE);
				lnrFindOptions.setTag("vis");
				webview.findAllAsync(" " + query + " ");
				webview.setFindListener(new WebView.FindListener() {

					@Override
					public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
						if (numberOfMatches == 0) {
							webview.findAllAsync(" " + query + ",");
							webview.setFindListener(new WebView.FindListener() {

								@Override
								public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
									if (numberOfMatches == 0) {
										webview.findAllAsync("," + query + " ");
										webview.setFindListener(new WebView.FindListener() {

											@Override
											public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
												if (numberOfMatches == 0) {
													webview.findAllAsync(query);
													webview.setFindListener(new WebView.FindListener() {

														@Override
														public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
															if (numberOfMatches == 0) {
																webview.findAllAsync(" " + query + "ם");
																webview.setFindListener(new WebView.FindListener() {

																	@Override
																	public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
																		if (numberOfMatches == 0) {
																			webview.findAllAsync(" " + query + "הם");
																			webview.setFindListener(new WebView.FindListener() {

																				@Override
																				public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
																					if (numberOfMatches == 0) {
																						webview.findAllAsync(" " + query + "יהם");
																					}
																				}
																			});
																		}
																	}
																});
															}
														}
													});
												}
											}
										});
									}
								}
							});
						}

					}
				});

			} else {
				lnrFindOptions.setVisibility(View.GONE);
				lnrFindOptions.setTag("gone");
				book_chapter = extras.getIntArray("book_chapter");
				TextView nameBook = findViewById(R.id.bookname);
				if (convertBookIdToName(book_chapter[0]).equals("לא ידוע"))
					nameBook.setText(mPrefs.getString("book_name", "לא ידוע"));
				else {
					shPrefEditor.putString("book_name", convertBookIdToName(book_chapter[0]) + " , " + convertAnchorIdToSection(book_chapter[1]));
					if (book_chapter[0] < 19)
						nameBook.setText(convertBookIdToName(book_chapter[0]) + " , " + convertAnchorIdToSection(book_chapter[1]));
					else
						nameBook.setText(convertBookIdToName(book_chapter[0]) + " , " + book_chapter[1]);
				}

				fromBookmarks = extras.getInt("fromBookmarks");
				if (fromBookmarks == 1)/*came from bookmarks*/ {
					//webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
					loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]], webview);
					scrollY = extras.getInt("bookmarkScrollY");
				} else if (book_chapter != null) {
					if (book_chapter[0] == 0xFFFF || book_chapter[1] == 0xFFFF)/*go to the last location*/ {
						bookmark = true;
						if (!extras.getBoolean("forgenLS", false)) {
							book_chapter[0] = mPrefs.getInt("book", 0);
							book_chapter[1] = mPrefs.getInt("chapter", 0);
							//webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
							loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]], webview);
							scrollY = mPrefs.getInt("scrollY", 0);
						} else {
							book_chapter[0] = mPrefs.getInt("book", 0);
							book_chapter[1] = mPrefs.getInt("chapter", 0);
							webview.getSettings().setDomStorageEnabled(true);

							String dag = extras.getString("searchPosition", "false");
							dag = dag.split(":")[0];
							webview.loadUrl(dag);
							webview.setWebViewClient(new WebViewClient() {

								public void onPageFinished(WebView view, String url) {
									webview.findAllAsync("and");
								}
							});
						}
					} /*else {
						book_chapter[0] = mPrefs.getInt("book", 0);
						book_chapter[1] = mPrefs.getInt("chapter", 0);
						webview.getSettings().setDomStorageEnabled(true);

						String dag = extras.getString("searchPosition", "false");
						dag = dag.split(":")[0];
						//loadWebview(dag, webview);
						webview.loadUrl(dag);
						webview.setWebViewClient(new WebViewClient() {

							public void onPageFinished(WebView view, String url) {
								// do your stuff here
								webview.findAllAsync("and");
							}
						});
					}*/
					else/*the regular choice of chapter*/ {
						bookmark = false;
						scrollY = 0;
						//webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
						loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]], webview);
					}
				}
			}
		}

		webSettings.setMinimumFontSize(mPrefs.getInt("fontSize",20));

		if(book_chapter[1] == lastChapter[book_chapter[0]])
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
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.setWebViewClient(new WebViewClient());
		//webview.setWebChromeClient(new WebChromeClient());
		//webSettings.setJavaScriptEnabled(true);
		//webSettings.setPluginState(WebSettings.PluginState.ON);
		//webview.loadUrl("https://ph.yhb.org.il/pninayomit/");
		//webview.scrollTo(0,680);
		//webSettings.setDomStorageEnabled(true);
		int a;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			if(!IntStream.of(haveAudio).anyMatch(x -> x ==book_chapter[0]))
				bSwitchModes.setVisibility(View.GONE);
		}
	}

	public void  setNoteId(String id)
	{
		note_id=id;
	}

	public void  setAudioId(String id)
	{
		audio_id=id;
	}

	boolean isConnected(){
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if(networkInfo!=null){
			if(networkInfo.isConnected())
				return true;
			else
				return false;
		}else
			return false;
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

	private Context getContext() {
		return null;
	}

	public void ParseTheDoc()
	{
		String prefix;
		InputStream is;
		int size;
		byte[] buffer;
		String input;

		fileName = getClearUrl();
		String[] splitString = fileName.split("_");

		if ((fileName.equals(lastFileName) == false))
		{
			lastFileName = fileName;
			try
			{
				splitString[0]=splitString[0].split("file://")[1];
				if((splitString[0].equals(getBaseContext().getFilesDir()+"/s"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/r"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/e"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/f")))
				{
					prefix="file:///data/user/0/com.rafraph.pnineyHalachaHashalem/files/";
					//fileNameOnly = fileName.substring(prefix.length());
					File file = new File(getBaseContext().getFilesDir() +"/"+fileName.substring(prefix.length()));

					is = new FileInputStream(file);
				}
				else{
					prefix = "file:///android_asset/";
					fileNameOnly = fileName.substring(prefix.length());
					is = getAssets().open(fileNameOnly);
				}
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

	}//onStart

	protected void onResume()
	{
		super.onResume();
		// The activity has become visible (it is now "resumed").
		supportInvalidateOptionsMenu();
		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		SleepScreen = mPrefs.getInt("ScreenOn", 1);

		if(SleepScreen == 0)
		{
			webview.setKeepScreenOn (false);
		}
		else if(SleepScreen == 1)
		{
			webview.setKeepScreenOn (true);
		}

		if(cbAssistButtons != mPrefs.getInt("cbAssistButtons", 1))
		{
			loadActivity();
		}
	}//onResume

	protected void onPause()
	{
		super.onPause();
		scrollY = webview.getScrollY();
		shPrefEditor.putInt("book", book_chapter[0]);
		shPrefEditor.putInt("chapter", book_chapter[1]);
		shPrefEditor.putInt("scrollY", scrollY);
		shPrefEditor.commit();
	}//onPaused

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		BlackBackground = mPrefs.getInt("BlackBackground", 0);

		if(book_chapter[1]==0)
			title = convertBookIdToName(book_chapter[0]);
		else
			title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);

		if(BlackBackground == 1)
		{
			textActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

			webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";var y = document.getElementsByClassName(\"left\"); y[0].style.display = 'none';} myFunction(); ");
			webview.setBackgroundColor(0xFFFFFF);//black
			llMainLayout.setBackgroundColor(Color.BLACK);
			webview.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView view, String url) {
					webview.loadUrl(
							"javascript:document.body.style.setProperty(\"color\", \"white\");"
					);
				}
			});
			textActionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + title + "</font>"));
			bParagraphs.setImageDrawable(resources.getDrawable(R.drawable.ic_action_view_as_list));
			bSwitchModes.setImageDrawable(resources.getDrawable(R.drawable.ic_action_switches_modes));
			bNext_sec.setImageDrawable(resources.getDrawable(R.drawable.ic_action_next_item));
			bPrevious_sec.setImageDrawable(resources.getDrawable(R.drawable.ic_action_previous_item));
			bNext_page.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down));
			bPrevious_page.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up));
			if(cameFromSearch == true)
			{
				bFindNext.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down_black));
				bFindPrevious.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up_black));
			}
			ImageView toMain= (ImageView) findViewById(R.id.too_main);
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
			ImageView menu2= (ImageView) findViewById(R.id.menu);
			menu2.setImageResource(R.drawable.ic_action_congif_b);
			main.setBackgroundColor(Color.rgb(120,1,1));

		} else {

			textActionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
			webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
			webview.setBackgroundColor(0x000000);//white
			llMainLayout.setBackgroundColor(Color.WHITE);
			textActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));
			bParagraphs.setImageDrawable(resources.getDrawable(R.drawable.ic_action_view_as_list));
			bSwitchModes.setImageDrawable(resources.getDrawable(R.drawable.ic_action_switches_modes));

			bNext_sec.setImageDrawable(resources.getDrawable(R.drawable.ic_action_next_item));
			bPrevious_sec.setImageDrawable(resources.getDrawable(R.drawable.ic_action_previous_item));
			bNext_page.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down));
			bPrevious_page.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up));
			if(cameFromSearch == true)
			{
				bFindNext.setImageDrawable(resources.getDrawable(R.drawable.ic_action_down));
				bFindPrevious.setImageDrawable(resources.getDrawable(R.drawable.ic_action_up));
			}
		}
		return true;
	}//onCreateOptionsMenu

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

	private Handler mHandler=new Handler();
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

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view)
	{
		String currentChapter;
		// TODO Auto-generated method stub

		switch(view.getId())
		{
			case R.id.to_main:
				findHeaders();
				showPopupMenu(view);
				break;

			case R.id.set_note:
				boolean enterForIf=false;
				for (int i: haveAudio)
					if(i==book_chapter[0]){
						enterForIf=true;
						final Context context = this;
						Class ourClass = null;
						try {
							ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.myAudio");
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
						Intent ourIntent = new Intent(textMain.this,ourClass);
						ourIntent.putExtra("cameFromText", true);
						ourIntent.putExtra("audio_id", Integer.parseInt("1"));
						ourIntent.putExtra("book_id", book_chapter[0]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("webLink", chaptersFiles[book_chapter[0]][book_chapter[1]]);
						ourIntent.putExtra("hearAndRead", true);
						ourIntent.putExtra("MyLanguage", MyLanguage);
						ourIntent.putExtra("scroolY", webview.getScrollY());

						findAllHeaders(ourIntent);
						startActivity(ourIntent);
					}
				if(!enterForIf)
					Toast.makeText(getApplicationContext(),	"לספר זה אין שמע", Toast.LENGTH_SHORT).show();
				break;

			case R.id.ibNext:
				cameFromSearch = false;
				scrollY = 0;/*In order to jump to the beginning of the chapter*/
				currentChapter = getClearUrl();
				getTheArrayLocation(currentChapter);
				book_chapter[1] += 1;
				TextView bookName= (TextView) findViewById(R.id.bookname);
				//webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
				loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]],webview);
				if(book_chapter[1] == 0) {
					title = convertBookIdToName(book_chapter[0]);
					bookName.setText(title);
				}
				else {
					if(book_chapter[0]<19)
						title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);
					else
						title = convertBookIdToName(book_chapter[0]) + ": " + book_chapter[1];
					bookName.setText(title);
				}
				if(book_chapter[1] == lastChapter[book_chapter[0]])
					bNext_sec.setEnabled(false);
				else
					bPrevious_sec.setEnabled(true);
				ChangeChapter = true;
				/*in order to keep the fontSize when moving to next chapter*/
				break;

			case R.id.ibPrevious:
				cameFromSearch = false;
				scrollY = 0;/*In order to jump to the beginning of the chapter*/
				currentChapter = getClearUrl();
				getTheArrayLocation(currentChapter);
				bookName= (TextView) findViewById(R.id.bookname);
				book_chapter[1] -= 1;
				//webview.loadUrl(chaptersFiles[book_chapter[0]][book_chapter[1]]);
				loadWebview(chaptersFiles[book_chapter[0]][book_chapter[1]],webview);
				if(book_chapter[1] == 0) {
					title = convertBookIdToName(book_chapter[0]);
					bookName.setText(title);
				}
				else {
					if(book_chapter[0]<19)
						title = convertBookIdToName(book_chapter[0]) + ": " + convertAnchorIdToSection(book_chapter[1]);
					else
						title = convertBookIdToName(book_chapter[0]) + ": " + book_chapter[1];
					bookName.setText(title);
				}
				if(book_chapter[1] == 0)
					bPrevious_sec.setEnabled(false);
				else
					bNext_sec.setEnabled(true);
				ChangeChapter = true;


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

	public String strBookmark, Bookmarks;
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		return true;
		//return super.onOptionsItemSelected(item);
	}

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
		PopupMenu popupMenu = new PopupMenu(textMain.this, v);
		for(int i = 0; i < headers.size(); i++)//fill the menu list
		{
			popupMenu.getMenu().add(0,i,i,headers.get(i).text());
		}

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				if(MyLanguage!=HEBREW&& MyLanguage!=ENGLISH) {
					webview.findAllAsync(item.toString().trim());
					webview.clearMatches();
				}
				else {
					int id = item.getItemId() + 1;
					String s = fileName + "#" + id;
					String s2 = fileName + "#" + (id + 1);
					webview.loadUrl(s2);/*Workaround to fix the bug of jumping to same anchor twice*/
					webview.loadUrl(s);
					jumpToSectionFlag = true;
					return true;
				}
				return true;
			}
		});

		popupMenu.show();
	}

	private void findHeaders()
	{
		String prefix;

		fileName = getClearUrl();
		String[] splitString = fileName.split("_");

		try
		{
			File file=null;
			InputStream is=null;
			splitString[0]=splitString[0].split("file://")[1];
			if((splitString[0].equals(getBaseContext().getFilesDir()+"/s"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/r"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/e"))||(splitString[0].equals(getBaseContext().getFilesDir()+"/f")))
			{
				prefix="file:///data/user/0/com.rafraph.pnineyHalachaHashalem/files/";
				//fileNameOnly = fileName.substring(prefix.length());
				file = new File(getBaseContext().getFilesDir() +"/"+fileName.substring(prefix.length()));
				is = new FileInputStream(file);
			}
			else{
				prefix = "file:///android_asset/";
				fileNameOnly = fileName.substring(prefix.length());
				is = getAssets().open(fileNameOnly);
			}
			//InputStream is = getAssets().open(fileNameOnly);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String input = new String(buffer);

			Document doc = Jsoup.parse(input);
			headers = doc.select("h2");
			if(headers.size()==0){
				headers = doc.select("p");

				Elements NewHead = new Elements();
				for(int j = 0; j < headers.size(); j++) {
					if(headers.get(j).text().length()>1)
						if(headers.get(j).text().charAt(1)=='.'||headers.get(j).text().charAt(2)=='.'||((headers.get(j).text().charAt(1)==')'||headers.get(j).text().charAt(2)==')')))
							NewHead.add(headers.get(j));
				}
				headers=NewHead;
			}
		} catch (IOException e) {
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
		String prefix;
		int j;
		ArrayList<String> sections = new ArrayList<String>();
		ArrayList<String> sections2 = new ArrayList<String>();
		fileName = getClearUrl();
		prefix = "file:///android_asset/";
		fileNameOnly = fileName.substring(prefix.length());
		fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("_")+1);

		for(int i=1; i<=lastChapter[book_chapter[0]]; i++) {
			try {
				InputStream is;
				if (book_chapter[0] == KASHRUT_B)
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
				if (book_chapter[0] == KASHRUT_B)
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

	private void fillChaptersFiles()/*list of all assets*/
	{
		/*BRACHOT*/
		chaptersFiles[BRACHOT][0] = "file:///android_asset/brachot_tochen.html";
		chaptersFiles[BRACHOT][1] = "file:///android_asset/brachot_1.html";
		chaptersFiles[BRACHOT][2] = "file:///android_asset/brachot_2.html";
		chaptersFiles[BRACHOT][3] = "file:///android_asset/brachot_3.html";
		chaptersFiles[BRACHOT][4] = "file:///android_asset/brachot_4.html";
		chaptersFiles[BRACHOT][5] = "file:///android_asset/brachot_5.html";
		chaptersFiles[BRACHOT][6] = "file:///android_asset/brachot_6.html";
		chaptersFiles[BRACHOT][7] = "file:///android_asset/brachot_7.html";
		chaptersFiles[BRACHOT][8] = "file:///android_asset/brachot_8.html";
		chaptersFiles[BRACHOT][9] = "file:///android_asset/brachot_9.html";
		chaptersFiles[BRACHOT][10] = "file:///android_asset/brachot_10.html";
		chaptersFiles[BRACHOT][11] = "file:///android_asset/brachot_11.html";
		chaptersFiles[BRACHOT][12] = "file:///android_asset/brachot_12.html";
		chaptersFiles[BRACHOT][13] = "file:///android_asset/brachot_13.html";
		chaptersFiles[BRACHOT][14] = "file:///android_asset/brachot_14.html";
		chaptersFiles[BRACHOT][15] = "file:///android_asset/brachot_15.html";
		chaptersFiles[BRACHOT][16] = "file:///android_asset/brachot_16.html";
		chaptersFiles[BRACHOT][17] = "file:///android_asset/brachot_17.html";
		chaptersFiles[BRACHOT][18] = "file:///android_asset/brachot_18.html";
		/*HAAMVEHAAREZ*/
		chaptersFiles[HAAMVEHAAREZ][0] = "file:///android_asset/haamvehaarez_tochen.html";
		chaptersFiles[HAAMVEHAAREZ][1] = "file:///android_asset/haamvehaarez_1.html";
		chaptersFiles[HAAMVEHAAREZ][2] = "file:///android_asset/haamvehaarez_2.html";
		chaptersFiles[HAAMVEHAAREZ][3] = "file:///android_asset/haamvehaarez_3.html";
		chaptersFiles[HAAMVEHAAREZ][4] = "file:///android_asset/haamvehaarez_4.html";
		chaptersFiles[HAAMVEHAAREZ][5] = "file:///android_asset/haamvehaarez_5.html";
		chaptersFiles[HAAMVEHAAREZ][6] = "file:///android_asset/haamvehaarez_6.html";
		chaptersFiles[HAAMVEHAAREZ][7] = "file:///android_asset/haamvehaarez_7.html";
		chaptersFiles[HAAMVEHAAREZ][8] = "file:///android_asset/haamvehaarez_8.html";
		chaptersFiles[HAAMVEHAAREZ][9] = "file:///android_asset/haamvehaarez_9.html";
		chaptersFiles[HAAMVEHAAREZ][10] = "file:///android_asset/haamvehaarez_10.html";
		chaptersFiles[HAAMVEHAAREZ][11] = "file:///android_asset/haamvehaarez_11.html";
		/*ZMANIM*/
		chaptersFiles[ZMANIM][0] = "file:///android_asset/zmanim_tochen.html";
		chaptersFiles[ZMANIM][1] = "file:///android_asset/zmanim_1.html";
		chaptersFiles[ZMANIM][2] = "file:///android_asset/zmanim_2.html";
		chaptersFiles[ZMANIM][3] = "file:///android_asset/zmanim_3.html";
		chaptersFiles[ZMANIM][4] = "file:///android_asset/zmanim_4.html";
		chaptersFiles[ZMANIM][5] = "file:///android_asset/zmanim_5.html";
		chaptersFiles[ZMANIM][6] = "file:///android_asset/zmanim_6.html";
		chaptersFiles[ZMANIM][7] = "file:///android_asset/zmanim_7.html";
		chaptersFiles[ZMANIM][8] = "file:///android_asset/zmanim_8.html";
		chaptersFiles[ZMANIM][9] = "file:///android_asset/zmanim_9.html";
		chaptersFiles[ZMANIM][10] = "file:///android_asset/zmanim_10.html";
		chaptersFiles[ZMANIM][11] = "file:///android_asset/zmanim_11.html";
		chaptersFiles[ZMANIM][12] = "file:///android_asset/zmanim_12.html";
		chaptersFiles[ZMANIM][13] = "file:///android_asset/zmanim_13.html";
		chaptersFiles[ZMANIM][14] = "file:///android_asset/zmanim_14.html";
		chaptersFiles[ZMANIM][15] = "file:///android_asset/zmanim_15.html";
		chaptersFiles[ZMANIM][16] = "file:///android_asset/zmanim_16.html";
		chaptersFiles[ZMANIM][17] = "file:///android_asset/zmanim_17.html";
		/*TAHARAT*/
		chaptersFiles[TAHARAT][0] = "file:///android_asset/taharat_tochen.html";
		chaptersFiles[TAHARAT][1] = "file:///android_asset/taharat_1.html";
		chaptersFiles[TAHARAT][2] = "file:///android_asset/taharat_2.html";
		chaptersFiles[TAHARAT][3] = "file:///android_asset/taharat_3.html";
		chaptersFiles[TAHARAT][4] = "file:///android_asset/taharat_4.html";
		chaptersFiles[TAHARAT][5] = "file:///android_asset/taharat_5.html";
		chaptersFiles[TAHARAT][6] = "file:///android_asset/taharat_6.html";
		chaptersFiles[TAHARAT][7] = "file:///android_asset/taharat_7.html";
		chaptersFiles[TAHARAT][8] = "file:///android_asset/taharat_8.html";
		chaptersFiles[TAHARAT][9] = "file:///android_asset/taharat_9.html";
		chaptersFiles[TAHARAT][10] = "file:///android_asset/taharat_10.html";
		/*YAMIM*/
		chaptersFiles[YAMIM][0] = "file:///android_asset/yamim_tochen.html";
		chaptersFiles[YAMIM][1] = "file:///android_asset/yamim_1.html";
		chaptersFiles[YAMIM][2] = "file:///android_asset/yamim_2.html";
		chaptersFiles[YAMIM][3] = "file:///android_asset/yamim_3.html";
		chaptersFiles[YAMIM][4] = "file:///android_asset/yamim_4.html";
		chaptersFiles[YAMIM][5] = "file:///android_asset/yamim_5.html";
		chaptersFiles[YAMIM][6] = "file:///android_asset/yamim_6.html";
		chaptersFiles[YAMIM][7] = "file:///android_asset/yamim_7.html";
		chaptersFiles[YAMIM][8] = "file:///android_asset/yamim_8.html";
		chaptersFiles[YAMIM][9] = "file:///android_asset/yamim_9.html";
		chaptersFiles[YAMIM][10] = "file:///android_asset/yamim_10.html";
		/*KASHRUT_A*/
		chaptersFiles[KASHRUT_A][0] = "file:///android_asset/kashrut_a_tochen.html";
		chaptersFiles[KASHRUT_A][1] = "file:///android_asset/kashrut_1.html";
		chaptersFiles[KASHRUT_A][2] = "file:///android_asset/kashrut_2.html";
		chaptersFiles[KASHRUT_A][3] = "file:///android_asset/kashrut_3.html";
		chaptersFiles[KASHRUT_A][4] = "file:///android_asset/kashrut_4.html";
		chaptersFiles[KASHRUT_A][5] = "file:///android_asset/kashrut_5.html";
		chaptersFiles[KASHRUT_A][6] = "file:///android_asset/kashrut_6.html";
		chaptersFiles[KASHRUT_A][7] = "file:///android_asset/kashrut_7.html";
		chaptersFiles[KASHRUT_A][8] = "file:///android_asset/kashrut_8.html";
		chaptersFiles[KASHRUT_A][9] = "file:///android_asset/kashrut_9.html";
		chaptersFiles[KASHRUT_A][10] = "file:///android_asset/kashrut_10.html";
		chaptersFiles[KASHRUT_A][11] = "file:///android_asset/kashrut_11.html";
		chaptersFiles[KASHRUT_A][12] = "file:///android_asset/kashrut_12.html";
		chaptersFiles[KASHRUT_A][13] = "file:///android_asset/kashrut_13.html";
		chaptersFiles[KASHRUT_A][14] = "file:///android_asset/kashrut_14.html";
		chaptersFiles[KASHRUT_A][15] = "file:///android_asset/kashrut_15.html";
		chaptersFiles[KASHRUT_A][16] = "file:///android_asset/kashrut_16.html";
		chaptersFiles[KASHRUT_A][17] = "file:///android_asset/kashrut_17.html";
		chaptersFiles[KASHRUT_A][18] = "file:///android_asset/kashrut_18.html";
		chaptersFiles[KASHRUT_A][19] = "file:///android_asset/kashrut_19.html";
		/*KASHRUT_B*/
		chaptersFiles[KASHRUT_B][0] = "file:///android_asset/kashrut_b_tochen.html";
		chaptersFiles[KASHRUT_B][1] = "file:///android_asset/kashrut_20.html";
		chaptersFiles[KASHRUT_B][2] = "file:///android_asset/kashrut_21.html";
		chaptersFiles[KASHRUT_B][3] = "file:///android_asset/kashrut_22.html";
		chaptersFiles[KASHRUT_B][4] = "file:///android_asset/kashrut_23.html";
		chaptersFiles[KASHRUT_B][5] = "file:///android_asset/kashrut_24.html";
		chaptersFiles[KASHRUT_B][6] = "file:///android_asset/kashrut_25.html";
		chaptersFiles[KASHRUT_B][7] = "file:///android_asset/kashrut_26.html";
		chaptersFiles[KASHRUT_B][8] = "file:///android_asset/kashrut_27.html";
		chaptersFiles[KASHRUT_B][9] = "file:///android_asset/kashrut_28.html";
		chaptersFiles[KASHRUT_B][10] = "file:///android_asset/kashrut_29.html";
		chaptersFiles[KASHRUT_B][11] = "file:///android_asset/kashrut_30.html";
		chaptersFiles[KASHRUT_B][12] = "file:///android_asset/kashrut_31.html";
		chaptersFiles[KASHRUT_B][13] = "file:///android_asset/kashrut_32.html";
		chaptersFiles[KASHRUT_B][14] = "file:///android_asset/kashrut_33.html";
		chaptersFiles[KASHRUT_B][15] = "file:///android_asset/kashrut_34.html";
		chaptersFiles[KASHRUT_B][16] = "file:///android_asset/kashrut_35.html";
		chaptersFiles[KASHRUT_B][17] = "file:///android_asset/kashrut_36.html";
		chaptersFiles[KASHRUT_B][18] = "file:///android_asset/kashrut_37.html";
		chaptersFiles[KASHRUT_B][19] = "file:///android_asset/kashrut_38.html";
		/*LIKUTIM_A*/
		chaptersFiles[LIKUTIM_A][0] = "file:///android_asset/likutim_a_tochen.html";
		chaptersFiles[LIKUTIM_A][1] = "file:///android_asset/likutim_a_1.html";
		chaptersFiles[LIKUTIM_A][2] = "file:///android_asset/likutim_a_2.html";
		chaptersFiles[LIKUTIM_A][3] = "file:///android_asset/likutim_a_3.html";
		chaptersFiles[LIKUTIM_A][4] = "file:///android_asset/likutim_a_4.html";
		chaptersFiles[LIKUTIM_A][5] = "file:///android_asset/likutim_a_5.html";
		chaptersFiles[LIKUTIM_A][6] = "file:///android_asset/likutim_a_6.html";
		chaptersFiles[LIKUTIM_A][7] = "file:///android_asset/likutim_a_7.html";
		chaptersFiles[LIKUTIM_A][8] = "file:///android_asset/likutim_a_8.html";
		chaptersFiles[LIKUTIM_A][9] = "file:///android_asset/likutim_a_9.html";
		chaptersFiles[LIKUTIM_A][10] = "file:///android_asset/likutim_a_10.html";
		chaptersFiles[LIKUTIM_A][11] = "file:///android_asset/likutim_a_11.html";
		chaptersFiles[LIKUTIM_A][12] = "file:///android_asset/likutim_a_12.html";
		chaptersFiles[LIKUTIM_A][13] = "file:///android_asset/likutim_a_13.html";
		/*LIKUTIM_B*/
		chaptersFiles[LIKUTIM_B][0] = "file:///android_asset/likutim_b_tochen.html";
		chaptersFiles[LIKUTIM_B][1] = "file:///android_asset/likutim_b_1.html";
		chaptersFiles[LIKUTIM_B][2] = "file:///android_asset/likutim_b_2.html";
		chaptersFiles[LIKUTIM_B][3] = "file:///android_asset/likutim_b_3.html";
		chaptersFiles[LIKUTIM_B][4] = "file:///android_asset/likutim_b_4.html";
		chaptersFiles[LIKUTIM_B][5] = "file:///android_asset/likutim_b_5.html";
		chaptersFiles[LIKUTIM_B][6] = "file:///android_asset/likutim_b_6.html";
		chaptersFiles[LIKUTIM_B][7] = "file:///android_asset/likutim_b_7.html";
		chaptersFiles[LIKUTIM_B][8] = "file:///android_asset/likutim_b_8.html";
		chaptersFiles[LIKUTIM_B][9] = "file:///android_asset/likutim_b_9.html";
		chaptersFiles[LIKUTIM_B][10] = "file:///android_asset/likutim_b_10.html";
		chaptersFiles[LIKUTIM_B][11] = "file:///android_asset/likutim_b_11.html";
		chaptersFiles[LIKUTIM_B][12] = "file:///android_asset/likutim_b_12.html";
		chaptersFiles[LIKUTIM_B][13] = "file:///android_asset/likutim_b_13.html";
		chaptersFiles[LIKUTIM_B][14] = "file:///android_asset/likutim_b_14.html";
		chaptersFiles[LIKUTIM_B][15] = "file:///android_asset/likutim_b_15.html";
		chaptersFiles[LIKUTIM_B][16] = "file:///android_asset/likutim_b_16.html";
		/*MISHPACHA*/
		chaptersFiles[MISHPACHA][0] = "file:///android_asset/mishpacha_tochen.html";
		chaptersFiles[MISHPACHA][1] = "file:///android_asset/mishpacha_1.html";
		chaptersFiles[MISHPACHA][2] = "file:///android_asset/mishpacha_2.html";
		chaptersFiles[MISHPACHA][3] = "file:///android_asset/mishpacha_3.html";
		chaptersFiles[MISHPACHA][4] = "file:///android_asset/mishpacha_4.html";
		chaptersFiles[MISHPACHA][5] = "file:///android_asset/mishpacha_5.html";
		chaptersFiles[MISHPACHA][6] = "file:///android_asset/mishpacha_6.html";
		chaptersFiles[MISHPACHA][7] = "file:///android_asset/mishpacha_7.html";
		chaptersFiles[MISHPACHA][8] = "file:///android_asset/mishpacha_8.html";
		chaptersFiles[MISHPACHA][9] = "file:///android_asset/mishpacha_9.html";
		chaptersFiles[MISHPACHA][10] = "file:///android_asset/mishpacha_10.html";
		/*MOADIM*/
		chaptersFiles[MOADIM][0] = "file:///android_asset/moadim_tochen.html";
		chaptersFiles[MOADIM][1] = "file:///android_asset/moadim_1.html";
		chaptersFiles[MOADIM][2] = "file:///android_asset/moadim_2.html";
		chaptersFiles[MOADIM][3] = "file:///android_asset/moadim_3.html";
		chaptersFiles[MOADIM][4] = "file:///android_asset/moadim_4.html";
		chaptersFiles[MOADIM][5] = "file:///android_asset/moadim_5.html";
		chaptersFiles[MOADIM][6] = "file:///android_asset/moadim_6.html";
		chaptersFiles[MOADIM][7] = "file:///android_asset/moadim_7.html";
		chaptersFiles[MOADIM][8] = "file:///android_asset/moadim_8.html";
		chaptersFiles[MOADIM][9] = "file:///android_asset/moadim_9.html";
		chaptersFiles[MOADIM][10] = "file:///android_asset/moadim_10.html";
		chaptersFiles[MOADIM][11] = "file:///android_asset/moadim_11.html";
		chaptersFiles[MOADIM][12] = "file:///android_asset/moadim_12.html";
		chaptersFiles[MOADIM][13] = "file:///android_asset/moadim_13.html";
		/*SUCOT*/
		chaptersFiles[SUCOT][0] = "file:///android_asset/sucot_tochen.html";
		chaptersFiles[SUCOT][1] = "file:///android_asset/sucot_1.html";
		chaptersFiles[SUCOT][2] = "file:///android_asset/sucot_2.html";
		chaptersFiles[SUCOT][3] = "file:///android_asset/sucot_3.html";
		chaptersFiles[SUCOT][4] = "file:///android_asset/sucot_4.html";
		chaptersFiles[SUCOT][5] = "file:///android_asset/sucot_5.html";
		chaptersFiles[SUCOT][6] = "file:///android_asset/sucot_6.html";
		chaptersFiles[SUCOT][7] = "file:///android_asset/sucot_7.html";
		chaptersFiles[SUCOT][8] = "file:///android_asset/sucot_8.html";
		/*PESACH*/
		chaptersFiles[PESACH][0] = "file:///android_asset/pesach_tochen.html";
		chaptersFiles[PESACH][1] = "file:///android_asset/pesach_1.html";
		chaptersFiles[PESACH][2] = "file:///android_asset/pesach_2.html";
		chaptersFiles[PESACH][3] = "file:///android_asset/pesach_3.html";
		chaptersFiles[PESACH][4] = "file:///android_asset/pesach_4.html";
		chaptersFiles[PESACH][5] = "file:///android_asset/pesach_5.html";
		chaptersFiles[PESACH][6] = "file:///android_asset/pesach_6.html";
		chaptersFiles[PESACH][7] = "file:///android_asset/pesach_7.html";
		chaptersFiles[PESACH][8] = "file:///android_asset/pesach_8.html";
		chaptersFiles[PESACH][9] = "file:///android_asset/pesach_9.html";
		chaptersFiles[PESACH][10] = "file:///android_asset/pesach_10.html";
		chaptersFiles[PESACH][11] = "file:///android_asset/pesach_11.html";
		chaptersFiles[PESACH][12] = "file:///android_asset/pesach_12.html";
		chaptersFiles[PESACH][13] = "file:///android_asset/pesach_13.html";
		chaptersFiles[PESACH][14] = "file:///android_asset/pesach_14.html";
		chaptersFiles[PESACH][15] = "file:///android_asset/pesach_15.html";
		chaptersFiles[PESACH][16] = "file:///android_asset/pesach_16.html";
		/*SHVIIT*/
		chaptersFiles[SHVIIT][0] = "file:///android_asset/shviit_tochen.html";
		chaptersFiles[SHVIIT][1] = "file:///android_asset/shviit_1.html";
		chaptersFiles[SHVIIT][2] = "file:///android_asset/shviit_2.html";
		chaptersFiles[SHVIIT][3] = "file:///android_asset/shviit_3.html";
		chaptersFiles[SHVIIT][4] = "file:///android_asset/shviit_4.html";
		chaptersFiles[SHVIIT][5] = "file:///android_asset/shviit_5.html";
		chaptersFiles[SHVIIT][6] = "file:///android_asset/shviit_6.html";
		chaptersFiles[SHVIIT][7] = "file:///android_asset/shviit_7.html";
		chaptersFiles[SHVIIT][8] = "file:///android_asset/shviit_8.html";
		chaptersFiles[SHVIIT][9] = "file:///android_asset/shviit_9.html";
		chaptersFiles[SHVIIT][10] = "file:///android_asset/shviit_10.html";
		chaptersFiles[SHVIIT][11] = "file:///android_asset/shviit_11.html";
		/*SHABAT*/
		chaptersFiles[SHABAT][0] = "file:///android_asset/shabat_tochen.html";
		chaptersFiles[SHABAT][1] = "file:///android_asset/shabat_1.html";
		chaptersFiles[SHABAT][2] = "file:///android_asset/shabat_2.html";
		chaptersFiles[SHABAT][3] = "file:///android_asset/shabat_3.html";
		chaptersFiles[SHABAT][4] = "file:///android_asset/shabat_4.html";
		chaptersFiles[SHABAT][5] = "file:///android_asset/shabat_5.html";
		chaptersFiles[SHABAT][6] = "file:///android_asset/shabat_6.html";
		chaptersFiles[SHABAT][7] = "file:///android_asset/shabat_7.html";
		chaptersFiles[SHABAT][8] = "file:///android_asset/shabat_8.html";
		chaptersFiles[SHABAT][9] = "file:///android_asset/shabat_9.html";
		chaptersFiles[SHABAT][10] = "file:///android_asset/shabat_10.html";
		chaptersFiles[SHABAT][11] = "file:///android_asset/shabat_11.html";
		chaptersFiles[SHABAT][12] = "file:///android_asset/shabat_12.html";
		chaptersFiles[SHABAT][13] = "file:///android_asset/shabat_13.html";
		chaptersFiles[SHABAT][14] = "file:///android_asset/shabat_14.html";
		chaptersFiles[SHABAT][15] = "file:///android_asset/shabat_15.html";
		chaptersFiles[SHABAT][16] = "file:///android_asset/shabat_16.html";
		chaptersFiles[SHABAT][17] = "file:///android_asset/shabat_17.html";
		chaptersFiles[SHABAT][18] = "file:///android_asset/shabat_18.html";
		chaptersFiles[SHABAT][19] = "file:///android_asset/shabat_19.html";
		chaptersFiles[SHABAT][20] = "file:///android_asset/shabat_20.html";
		chaptersFiles[SHABAT][21] = "file:///android_asset/shabat_21.html";
		chaptersFiles[SHABAT][22] = "file:///android_asset/shabat_22.html";
		chaptersFiles[SHABAT][23] = "file:///android_asset/shabat_23.html";
		chaptersFiles[SHABAT][24] = "file:///android_asset/shabat_24.html";
		chaptersFiles[SHABAT][25] = "file:///android_asset/shabat_25.html";
		chaptersFiles[SHABAT][26] = "file:///android_asset/shabat_26.html";
		chaptersFiles[SHABAT][27] = "file:///android_asset/shabat_27.html";
		chaptersFiles[SHABAT][28] = "file:///android_asset/shabat_28.html";
		chaptersFiles[SHABAT][29] = "file:///android_asset/shabat_29.html";
		chaptersFiles[SHABAT][30] = "file:///android_asset/shabat_30.html";

		/*SIMCHAT*/
		chaptersFiles[SIMCHAT][0] = "file:///android_asset/simchat_tochen.html";
		chaptersFiles[SIMCHAT][1] = "file:///android_asset/simchat_1.html";
		chaptersFiles[SIMCHAT][2] = "file:///android_asset/simchat_2.html";
		chaptersFiles[SIMCHAT][3] = "file:///android_asset/simchat_3.html";
		chaptersFiles[SIMCHAT][4] = "file:///android_asset/simchat_4.html";
		chaptersFiles[SIMCHAT][5] = "file:///android_asset/simchat_5.html";
		chaptersFiles[SIMCHAT][6] = "file:///android_asset/simchat_6.html";
		chaptersFiles[SIMCHAT][7] = "file:///android_asset/simchat_7.html";
		chaptersFiles[SIMCHAT][8] = "file:///android_asset/simchat_8.html";
		chaptersFiles[SIMCHAT][9] = "file:///android_asset/simchat_9.html";
		chaptersFiles[SIMCHAT][10] = "file:///android_asset/simchat_10.html";

		/*TEFILA*/
		chaptersFiles[TEFILA][0] = "file:///android_asset/tefila_tochen.html";
		chaptersFiles[TEFILA][1] = "file:///android_asset/tefila_1.html";
		chaptersFiles[TEFILA][2] = "file:///android_asset/tefila_2.html";
		chaptersFiles[TEFILA][3] = "file:///android_asset/tefila_3.html";
		chaptersFiles[TEFILA][4] = "file:///android_asset/tefila_4.html";
		chaptersFiles[TEFILA][5] = "file:///android_asset/tefila_5.html";
		chaptersFiles[TEFILA][6] = "file:///android_asset/tefila_6.html";
		chaptersFiles[TEFILA][7] = "file:///android_asset/tefila_7.html";
		chaptersFiles[TEFILA][8] = "file:///android_asset/tefila_8.html";
		chaptersFiles[TEFILA][9] = "file:///android_asset/tefila_9.html";
		chaptersFiles[TEFILA][10] = "file:///android_asset/tefila_10.html";
		chaptersFiles[TEFILA][11] = "file:///android_asset/tefila_11.html";
		chaptersFiles[TEFILA][12] = "file:///android_asset/tefila_12.html";
		chaptersFiles[TEFILA][13] = "file:///android_asset/tefila_13.html";
		chaptersFiles[TEFILA][14] = "file:///android_asset/tefila_14.html";
		chaptersFiles[TEFILA][15] = "file:///android_asset/tefila_15.html";
		chaptersFiles[TEFILA][16] = "file:///android_asset/tefila_16.html";
		chaptersFiles[TEFILA][17] = "file:///android_asset/tefila_17.html";
		chaptersFiles[TEFILA][18] = "file:///android_asset/tefila_18.html";
		chaptersFiles[TEFILA][19] = "file:///android_asset/tefila_19.html";
		chaptersFiles[TEFILA][20] = "file:///android_asset/tefila_20.html";
		chaptersFiles[TEFILA][21] = "file:///android_asset/tefila_21.html";
		chaptersFiles[TEFILA][22] = "file:///android_asset/tefila_22.html";
		chaptersFiles[TEFILA][23] = "file:///android_asset/tefila_23.html";
		chaptersFiles[TEFILA][24] = "file:///android_asset/tefila_24.html";
		chaptersFiles[TEFILA][25] = "file:///android_asset/tefila_25.html";
		chaptersFiles[TEFILA][26] = "file:///android_asset/tefila_26.html";
		/*TEFILAT_NASHIM*/
		chaptersFiles[TEFILAT_NASHIM][0] = "file:///android_asset/tefilat_nashim_tochen.html";
		chaptersFiles[TEFILAT_NASHIM][1] = "file:///android_asset/tefilat_nashim_1.html";
		chaptersFiles[TEFILAT_NASHIM][2] = "file:///android_asset/tefilat_nashim_2.html";
		chaptersFiles[TEFILAT_NASHIM][3] = "file:///android_asset/tefilat_nashim_3.html";
		chaptersFiles[TEFILAT_NASHIM][4] = "file:///android_asset/tefilat_nashim_4.html";
		chaptersFiles[TEFILAT_NASHIM][5] = "file:///android_asset/tefilat_nashim_5.html";
		chaptersFiles[TEFILAT_NASHIM][6] = "file:///android_asset/tefilat_nashim_6.html";
		chaptersFiles[TEFILAT_NASHIM][7] = "file:///android_asset/tefilat_nashim_7.html";
		chaptersFiles[TEFILAT_NASHIM][8] = "file:///android_asset/tefilat_nashim_8.html";
		chaptersFiles[TEFILAT_NASHIM][9] = "file:///android_asset/tefilat_nashim_9.html";
		chaptersFiles[TEFILAT_NASHIM][10] = "file:///android_asset/tefilat_nashim_10.html";
		chaptersFiles[TEFILAT_NASHIM][11] = "file:///android_asset/tefilat_nashim_11.html";
		chaptersFiles[TEFILAT_NASHIM][12] = "file:///android_asset/tefilat_nashim_12.html";
		chaptersFiles[TEFILAT_NASHIM][13] = "file:///android_asset/tefilat_nashim_13.html";
		chaptersFiles[TEFILAT_NASHIM][14] = "file:///android_asset/tefilat_nashim_14.html";
		chaptersFiles[TEFILAT_NASHIM][15] = "file:///android_asset/tefilat_nashim_15.html";
		chaptersFiles[TEFILAT_NASHIM][16] = "file:///android_asset/tefilat_nashim_16.html";
		chaptersFiles[TEFILAT_NASHIM][17] = "file:///android_asset/tefilat_nashim_17.html";
		chaptersFiles[TEFILAT_NASHIM][18] = "file:///android_asset/tefilat_nashim_18.html";
		chaptersFiles[TEFILAT_NASHIM][19] = "file:///android_asset/tefilat_nashim_19.html";
		chaptersFiles[TEFILAT_NASHIM][20] = "file:///android_asset/tefilat_nashim_20.html";
		chaptersFiles[TEFILAT_NASHIM][21] = "file:///android_asset/tefilat_nashim_21.html";
		chaptersFiles[TEFILAT_NASHIM][22] = "file:///android_asset/tefilat_nashim_22.html";
		chaptersFiles[TEFILAT_NASHIM][23] = "file:///android_asset/tefilat_nashim_23.html";
		chaptersFiles[TEFILAT_NASHIM][24] = "file:///android_asset/tefilat_nashim_24.html";
		/*HAR_BRACHOT*/
		chaptersFiles[HAR_BRACHOT][0] = "file:///android_asset/har_brachot_tochen.html";
		chaptersFiles[HAR_BRACHOT][1] = "file:///android_asset/har_brachot_1.html";
		chaptersFiles[HAR_BRACHOT][2] = "file:///android_asset/har_brachot_2.html";
		chaptersFiles[HAR_BRACHOT][3] = "file:///android_asset/har_brachot_3.html";
		chaptersFiles[HAR_BRACHOT][4] = "file:///android_asset/har_brachot_4.html";
		chaptersFiles[HAR_BRACHOT][5] = "file:///android_asset/har_brachot_5.html";
		chaptersFiles[HAR_BRACHOT][6] = "file:///android_asset/har_brachot_6.html";
		chaptersFiles[HAR_BRACHOT][7] = "file:///android_asset/har_brachot_7.html";
		chaptersFiles[HAR_BRACHOT][8] = "file:///android_asset/har_brachot_8.html";
		chaptersFiles[HAR_BRACHOT][9] = "file:///android_asset/har_brachot_9.html";
		chaptersFiles[HAR_BRACHOT][10] = "file:///android_asset/har_brachot_10.html";
		chaptersFiles[HAR_BRACHOT][11] = "file:///android_asset/har_brachot_11.html";
		chaptersFiles[HAR_BRACHOT][12] = "file:///android_asset/har_brachot_12.html";
		chaptersFiles[HAR_BRACHOT][13] = "file:///android_asset/har_brachot_13.html";
		chaptersFiles[HAR_BRACHOT][14] = "file:///android_asset/har_brachot_14.html";
		chaptersFiles[HAR_BRACHOT][15] = "file:///android_asset/har_brachot_15.html";
		chaptersFiles[HAR_BRACHOT][16] = "file:///android_asset/har_brachot_16.html";
		chaptersFiles[HAR_BRACHOT][17] = "file:///android_asset/har_brachot_17.html";
		/*HAR_YAMIM*/
		chaptersFiles[HAR_YAMIM][0] = "file:///android_asset/har_yamim_tochen.html";
		chaptersFiles[HAR_YAMIM][1] = "file:///android_asset/har_yamim_1.html";
		chaptersFiles[HAR_YAMIM][2] = "file:///android_asset/har_yamim_2.html";
		chaptersFiles[HAR_YAMIM][3] = "file:///android_asset/har_yamim_3.html";
		chaptersFiles[HAR_YAMIM][4] = "file:///android_asset/har_yamim_4.html";
		chaptersFiles[HAR_YAMIM][5] = "file:///android_asset/har_yamim_5.html";
		chaptersFiles[HAR_YAMIM][6] = "file:///android_asset/har_yamim_6.html";
		chaptersFiles[HAR_YAMIM][7] = "file:///android_asset/har_yamim_7.html";
		chaptersFiles[HAR_YAMIM][8] = "file:///android_asset/har_yamim_8.html";
		chaptersFiles[HAR_YAMIM][9] = "file:///android_asset/har_yamim_9.html";
		chaptersFiles[HAR_YAMIM][10] = "file:///android_asset/har_yamim_10.html";
		/*HAR_MOADIM*/
		chaptersFiles[HAR_MOADIM][0] = "file:///android_asset/har_moadim_tochen.html";
		chaptersFiles[HAR_MOADIM][1] = "file:///android_asset/har_moadim_1.html";
		chaptersFiles[HAR_MOADIM][2] = "file:///android_asset/har_moadim_2.html";
		chaptersFiles[HAR_MOADIM][3] = "file:///android_asset/har_moadim_3.html";
		chaptersFiles[HAR_MOADIM][4] = "file:///android_asset/har_moadim_4.html";
		chaptersFiles[HAR_MOADIM][5] = "file:///android_asset/har_moadim_5.html";
		chaptersFiles[HAR_MOADIM][6] = "file:///android_asset/har_moadim_6.html";
		chaptersFiles[HAR_MOADIM][7] = "file:///android_asset/har_moadim_7.html";
		chaptersFiles[HAR_MOADIM][8] = "file:///android_asset/har_moadim_8.html";
		//chaptersFiles[HAR_MOADIM][9] = "file:///android_asset/har_moadim_9.html"; //currently there is no chapter 9
		chaptersFiles[HAR_MOADIM][9] = "file:///android_asset/har_moadim_10.html";
		chaptersFiles[HAR_MOADIM][10] = "file:///android_asset/har_moadim_11.html";
		chaptersFiles[HAR_MOADIM][11] = "file:///android_asset/har_moadim_12.html";
		chaptersFiles[HAR_MOADIM][12] = "file:///android_asset/har_moadim_13.html";
		/*HAR_SUCOT*/
		chaptersFiles[HAR_SUCOT][0] = "file:///android_asset/sucot_tochen.html";
		chaptersFiles[HAR_SUCOT][1] = "file:///android_asset/har_sucot_1.html";
		chaptersFiles[HAR_SUCOT][2] = "file:///android_asset/har_sucot_2.html";
		chaptersFiles[HAR_SUCOT][3] = "file:///android_asset/har_sucot_3.html";
		chaptersFiles[HAR_SUCOT][4] = "file:///android_asset/har_sucot_4.html";
		chaptersFiles[HAR_SUCOT][5] = "file:///android_asset/har_sucot_5.html";
		chaptersFiles[HAR_SUCOT][6] = "file:///android_asset/har_sucot_6.html";
		chaptersFiles[HAR_SUCOT][7] = "file:///android_asset/har_sucot_7.html";
		chaptersFiles[HAR_SUCOT][8] = "file:///android_asset/har_sucot_8.html";
		/*HAR_SHABAT*/
		chaptersFiles[HAR_SHABAT][0] = "file:///android_asset/har_shabat_tochen.html";
		chaptersFiles[HAR_SHABAT][1] = "file:///android_asset/har_shabat_1.html";
		chaptersFiles[HAR_SHABAT][2] = "file:///android_asset/har_shabat_2.html";
		chaptersFiles[HAR_SHABAT][3] = "file:///android_asset/har_shabat_3.html";
		chaptersFiles[HAR_SHABAT][4] = "file:///android_asset/har_shabat_4.html";
		chaptersFiles[HAR_SHABAT][5] = "file:///android_asset/har_shabat_5.html";
		chaptersFiles[HAR_SHABAT][6] = "file:///android_asset/har_shabat_6.html";
		chaptersFiles[HAR_SHABAT][7] = "file:///android_asset/har_shabat_7.html";
		chaptersFiles[HAR_SHABAT][8] = "file:///android_asset/har_shabat_8.html";
		chaptersFiles[HAR_SHABAT][9] = "file:///android_asset/har_shabat_9.html";
		chaptersFiles[HAR_SHABAT][10] = "file:///android_asset/har_shabat_10.html";
		chaptersFiles[HAR_SHABAT][11] = "file:///android_asset/har_shabat_11.html";
		chaptersFiles[HAR_SHABAT][12] = "file:///android_asset/har_shabat_12.html";
		chaptersFiles[HAR_SHABAT][13] = "file:///android_asset/har_shabat_13.html";
		chaptersFiles[HAR_SHABAT][14] = "file:///android_asset/har_shabat_14.html";
		chaptersFiles[HAR_SHABAT][15] = "file:///android_asset/har_shabat_15.html";
		chaptersFiles[HAR_SHABAT][16] = "file:///android_asset/har_shabat_16.html";
		chaptersFiles[HAR_SHABAT][17] = "file:///android_asset/har_shabat_17.html";
		chaptersFiles[HAR_SHABAT][18] = "file:///android_asset/har_shabat_18.html";
		chaptersFiles[HAR_SHABAT][19] = "file:///android_asset/har_shabat_19.html";
		chaptersFiles[HAR_SHABAT][20] = "file:///android_asset/har_shabat_20.html";
		chaptersFiles[HAR_SHABAT][21] = "file:///android_asset/har_shabat_21.html";
		chaptersFiles[HAR_SHABAT][22] = "file:///android_asset/har_shabat_22.html";
		chaptersFiles[HAR_SHABAT][23] = "file:///android_asset/har_shabat_23.html";
		chaptersFiles[HAR_SHABAT][24] = "file:///android_asset/har_shabat_24.html";
		chaptersFiles[HAR_SHABAT][25] = "file:///android_asset/har_shabat_25.html";
		chaptersFiles[HAR_SHABAT][26] = "file:///android_asset/har_shabat_26.html";
		chaptersFiles[HAR_SHABAT][27] = "file:///android_asset/har_shabat_27.html";
		chaptersFiles[HAR_SHABAT][28] = "file:///android_asset/har_shabat_28.html";
		chaptersFiles[HAR_SHABAT][29] = "file:///android_asset/har_shabat_29.html";
		chaptersFiles[HAR_SHABAT][30] = "file:///android_asset/har_shabat_30.html";
		/*HAR_SIMCHAT*/
		chaptersFiles[HAR_SIMCHAT][0] = "file:///android_asset/har_simchat_tochen.html";
		chaptersFiles[HAR_SIMCHAT][1] = "file:///android_asset/har_simchat_1.html";
		chaptersFiles[HAR_SIMCHAT][2] = "file:///android_asset/har_simchat_2.html";
		chaptersFiles[HAR_SIMCHAT][3] = "file:///android_asset/har_simchat_3.html";
		chaptersFiles[HAR_SIMCHAT][4] = "file:///android_asset/har_simchat_4.html";
		chaptersFiles[HAR_SIMCHAT][5] = "file:///android_asset/har_simchat_5.html";
		chaptersFiles[HAR_SIMCHAT][6] = "file:///android_asset/har_simchat_6.html";
		chaptersFiles[HAR_SIMCHAT][7] = "file:///android_asset/har_simchat_7.html";
		chaptersFiles[HAR_SIMCHAT][8] = "file:///android_asset/har_simchat_8.html";
		chaptersFiles[HAR_SIMCHAT][9] = "file:///android_asset/har_simchat_9.html";
		chaptersFiles[HAR_SIMCHAT][10] = "file:///android_asset/har_simchat_10.html";

		/*E_TEFILA*/
		chaptersFiles[E_TEFILA][0] = getBaseContext().getFilesDir() +"/e_tefila_tochen.html";
		chaptersFiles[E_TEFILA][1] = ""+getBaseContext().getFilesDir() + "/e_tefila_1.html";
		chaptersFiles[E_TEFILA][2] = ""+getBaseContext().getFilesDir() + "/e_tefila_2.html";
		chaptersFiles[E_TEFILA][3] = ""+getBaseContext().getFilesDir() + "/e_tefila_3.html";
		chaptersFiles[E_TEFILA][4] = ""+getBaseContext().getFilesDir() + "/e_tefila_4.html";
		chaptersFiles[E_TEFILA][5] = ""+getBaseContext().getFilesDir() + "/e_tefila_5.html";
		chaptersFiles[E_TEFILA][6] = ""+getBaseContext().getFilesDir() + "/e_tefila_6.html";
		chaptersFiles[E_TEFILA][7] = ""+getBaseContext().getFilesDir() + "/e_tefila_7.html";
		chaptersFiles[E_TEFILA][8] = ""+getBaseContext().getFilesDir() + "/e_tefila_8.html";
		chaptersFiles[E_TEFILA][9] = ""+getBaseContext().getFilesDir() + "/e_tefila_9.html";
		chaptersFiles[E_TEFILA][10] = ""+getBaseContext().getFilesDir() + "/e_tefila_10.html";
		chaptersFiles[E_TEFILA][11] = ""+getBaseContext().getFilesDir() + "/e_tefila_11.html";
		chaptersFiles[E_TEFILA][12] = ""+getBaseContext().getFilesDir() + "/e_tefila_12.html";
		chaptersFiles[E_TEFILA][13] = ""+getBaseContext().getFilesDir() + "/e_tefila_13.html";
		chaptersFiles[E_TEFILA][14] = ""+getBaseContext().getFilesDir() + "/e_tefila_14.html";
		chaptersFiles[E_TEFILA][15] = ""+getBaseContext().getFilesDir() + "/e_tefila_15.html";
		chaptersFiles[E_TEFILA][16] = ""+getBaseContext().getFilesDir() + "/e_tefila_16.html";
		chaptersFiles[E_TEFILA][17] = ""+getBaseContext().getFilesDir() + "/e_tefila_17.html";
		chaptersFiles[E_TEFILA][18] = ""+getBaseContext().getFilesDir() + "/e_tefila_18.html";
		chaptersFiles[E_TEFILA][19] = ""+getBaseContext().getFilesDir() + "/e_tefila_19.html";
		chaptersFiles[E_TEFILA][20] = ""+getBaseContext().getFilesDir() + "/e_tefila_20.html";
		chaptersFiles[E_TEFILA][21] = ""+getBaseContext().getFilesDir() + "/e_tefila_21.html";
		chaptersFiles[E_TEFILA][22] = ""+getBaseContext().getFilesDir() + "/e_tefila_22.html";
		chaptersFiles[E_TEFILA][23] = ""+getBaseContext().getFilesDir() + "/e_tefila_23.html";
		chaptersFiles[E_TEFILA][24] = ""+getBaseContext().getFilesDir() + "/e_tefila_24.html";
		chaptersFiles[E_TEFILA][25] = ""+getBaseContext().getFilesDir() + "/e_tefila_25.html";
		chaptersFiles[E_TEFILA][26] = ""+getBaseContext().getFilesDir() + "/e_tefila_26.html";
		/*E_PESACH*/
		chaptersFiles[E_PESACH][0] = ""+getBaseContext().getFilesDir() + "/e_pesach_tochen.html";
		chaptersFiles[E_PESACH][1] = ""+getBaseContext().getFilesDir() + "/e_pesach_1.html";
		chaptersFiles[E_PESACH][2] = ""+getBaseContext().getFilesDir() + "/e_pesach_2.html";
		chaptersFiles[E_PESACH][3] = ""+getBaseContext().getFilesDir() + "/e_pesach_3.html";
		chaptersFiles[E_PESACH][4] = ""+getBaseContext().getFilesDir() + "/e_pesach_4.html";
		chaptersFiles[E_PESACH][5] = ""+getBaseContext().getFilesDir() + "/e_pesach_5.html";
		chaptersFiles[E_PESACH][6] = ""+getBaseContext().getFilesDir() + "/e_pesach_6.html";
		chaptersFiles[E_PESACH][7] = ""+getBaseContext().getFilesDir() + "/e_pesach_7.html";
		chaptersFiles[E_PESACH][8] = ""+getBaseContext().getFilesDir() + "/e_pesach_8.html";
		chaptersFiles[E_PESACH][9] = ""+getBaseContext().getFilesDir() + "/e_pesach_9.html";
		chaptersFiles[E_PESACH][10] = ""+getBaseContext().getFilesDir() + "/e_pesach_10.html";
		chaptersFiles[E_PESACH][11] = ""+getBaseContext().getFilesDir() + "/e_pesach_11.html";
		chaptersFiles[E_PESACH][12] = ""+getBaseContext().getFilesDir() + "/e_pesach_12.html";
		chaptersFiles[E_PESACH][13] = ""+getBaseContext().getFilesDir() + "/e_pesach_13.html";
		chaptersFiles[E_PESACH][14] = ""+getBaseContext().getFilesDir() + "/e_pesach_14.html";
		chaptersFiles[E_PESACH][15] = ""+getBaseContext().getFilesDir() + "/e_pesach_15.html";
		chaptersFiles[E_PESACH][16] = ""+getBaseContext().getFilesDir() + "/e_pesach_16.html";
		/*E_ZMANIM*/
		chaptersFiles[E_ZMANIM][0] = ""+getBaseContext().getFilesDir() + "/e_zmanim_tochen.html";
		chaptersFiles[E_ZMANIM][1] = ""+getBaseContext().getFilesDir() + "/e_zmanim_1.html";
		chaptersFiles[E_ZMANIM][2] = ""+getBaseContext().getFilesDir() + "/e_zmanim_2.html";
		chaptersFiles[E_ZMANIM][3] = ""+getBaseContext().getFilesDir() + "/e_zmanim_3.html";
		chaptersFiles[E_ZMANIM][4] = ""+getBaseContext().getFilesDir() + "/e_zmanim_4.html";
		chaptersFiles[E_ZMANIM][5] = ""+getBaseContext().getFilesDir() + "/e_zmanim_5.html";
		chaptersFiles[E_ZMANIM][6] = ""+getBaseContext().getFilesDir() + "/e_zmanim_6.html";
		chaptersFiles[E_ZMANIM][7] = ""+getBaseContext().getFilesDir() + "/e_zmanim_7.html";
		chaptersFiles[E_ZMANIM][8] = ""+getBaseContext().getFilesDir() + "/e_zmanim_8.html";
		chaptersFiles[E_ZMANIM][9] = ""+getBaseContext().getFilesDir() + "/e_zmanim_9.html";
		chaptersFiles[E_ZMANIM][10] = ""+getBaseContext().getFilesDir() + "/e_zmanim_10.html";
		chaptersFiles[E_ZMANIM][11] = ""+getBaseContext().getFilesDir() + "/e_zmanim_11.html";
		chaptersFiles[E_ZMANIM][12] = ""+getBaseContext().getFilesDir() + "/e_zmanim_12.html";
		chaptersFiles[E_ZMANIM][13] = ""+getBaseContext().getFilesDir() + "/e_zmanim_13.html";
		chaptersFiles[E_ZMANIM][14] = ""+getBaseContext().getFilesDir() + "/e_zmanim_14.html";
		chaptersFiles[E_ZMANIM][15] = ""+getBaseContext().getFilesDir() + "/e_zmanim_15.html";
		/*E_WOMEN_PRAYER*/
		chaptersFiles[E_WOMEN_PRAYER][0] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_tochen.html";
		chaptersFiles[E_WOMEN_PRAYER][1] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_1.html";
		chaptersFiles[E_WOMEN_PRAYER][2] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_2.html";
		chaptersFiles[E_WOMEN_PRAYER][3] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_3.html";
		chaptersFiles[E_WOMEN_PRAYER][4] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_4.html";
		chaptersFiles[E_WOMEN_PRAYER][5] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_5.html";
		chaptersFiles[E_WOMEN_PRAYER][6] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_6.html";
		chaptersFiles[E_WOMEN_PRAYER][7] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_7.html";
		chaptersFiles[E_WOMEN_PRAYER][8] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_8.html";
		chaptersFiles[E_WOMEN_PRAYER][9] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_9.html";
		chaptersFiles[E_WOMEN_PRAYER][10] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_10.html";
		chaptersFiles[E_WOMEN_PRAYER][11] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_11.html";
		chaptersFiles[E_WOMEN_PRAYER][12] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_12.html";
		chaptersFiles[E_WOMEN_PRAYER][13] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_13.html";
		chaptersFiles[E_WOMEN_PRAYER][14] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_14.html";
		chaptersFiles[E_WOMEN_PRAYER][15] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_15.html";
		chaptersFiles[E_WOMEN_PRAYER][16] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_16.html";
		chaptersFiles[E_WOMEN_PRAYER][17] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_17.html";
		chaptersFiles[E_WOMEN_PRAYER][18] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_18.html";
		chaptersFiles[E_WOMEN_PRAYER][19] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_19.html";
		chaptersFiles[E_WOMEN_PRAYER][20] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_20.html";
		chaptersFiles[E_WOMEN_PRAYER][21] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_21.html";
		chaptersFiles[E_WOMEN_PRAYER][22] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_22.html";
		chaptersFiles[E_WOMEN_PRAYER][23] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_23.html";
		chaptersFiles[E_WOMEN_PRAYER][24] = ""+getBaseContext().getFilesDir() + "/e_w_prayer_24.html";
		/*E_SHABAT*/
		chaptersFiles[E_SHABAT][0] = ""+getBaseContext().getFilesDir() + "/e_shabbat_tochen.html";
		chaptersFiles[E_SHABAT][1] = ""+getBaseContext().getFilesDir() + "/e_shabbat_1.html";
		chaptersFiles[E_SHABAT][2] = ""+getBaseContext().getFilesDir() + "/e_shabbat_2.html";
		chaptersFiles[E_SHABAT][3] = ""+getBaseContext().getFilesDir() + "/e_shabbat_3.html";
		chaptersFiles[E_SHABAT][4] = ""+getBaseContext().getFilesDir() + "/e_shabbat_4.html";
		chaptersFiles[E_SHABAT][5] = ""+getBaseContext().getFilesDir() + "/e_shabbat_5.html";
		chaptersFiles[E_SHABAT][6] = ""+getBaseContext().getFilesDir() + "/e_shabbat_6.html";
		chaptersFiles[E_SHABAT][7] = ""+getBaseContext().getFilesDir() + "/e_shabbat_7.html";
		chaptersFiles[E_SHABAT][8] = ""+getBaseContext().getFilesDir() + "/e_shabbat_8.html";
		chaptersFiles[E_SHABAT][9] = ""+getBaseContext().getFilesDir() + "/e_shabbat_9.html";
		chaptersFiles[E_SHABAT][10] = ""+getBaseContext().getFilesDir() + "/e_shabbat_10.html";
		chaptersFiles[E_SHABAT][11] = ""+getBaseContext().getFilesDir() + "/e_shabbat_11.html";
		chaptersFiles[E_SHABAT][12] = ""+getBaseContext().getFilesDir() + "/e_shabbat_12.html";
		chaptersFiles[E_SHABAT][13] = ""+getBaseContext().getFilesDir() + "/e_shabbat_13.html";
		chaptersFiles[E_SHABAT][14] = ""+getBaseContext().getFilesDir() + "/e_shabbat_14.html";
		chaptersFiles[E_SHABAT][15] = ""+getBaseContext().getFilesDir() + "/e_shabbat_15.html";
		chaptersFiles[E_SHABAT][16] = ""+getBaseContext().getFilesDir() + "/e_shabbat_16.html";
		chaptersFiles[E_SHABAT][17] = ""+getBaseContext().getFilesDir() + "/e_shabbat_17.html";
		chaptersFiles[E_SHABAT][18] = ""+getBaseContext().getFilesDir() + "/e_shabbat_18.html";
		chaptersFiles[E_SHABAT][19] = ""+getBaseContext().getFilesDir() + "/e_shabbat_19.html";
		chaptersFiles[E_SHABAT][20] = ""+getBaseContext().getFilesDir() + "/e_shabbat_20.html";
		chaptersFiles[E_SHABAT][21] = ""+getBaseContext().getFilesDir() + "/e_shabbat_21.html";
		chaptersFiles[E_SHABAT][22] = ""+getBaseContext().getFilesDir() + "/e_shabbat_22.html";
		chaptersFiles[E_SHABAT][23] = ""+getBaseContext().getFilesDir() + "/e_shabbat_23.html";
		chaptersFiles[E_SHABAT][24] = ""+getBaseContext().getFilesDir() + "/e_shabbat_24.html";
		chaptersFiles[E_SHABAT][25] = ""+getBaseContext().getFilesDir() + "/e_shabbat_25.html";
		chaptersFiles[E_SHABAT][26] = ""+getBaseContext().getFilesDir() + "/e_shabbat_26.html";
		chaptersFiles[E_SHABAT][27] = ""+getBaseContext().getFilesDir() + "/e_shabbat_27.html";
		chaptersFiles[E_SHABAT][28] = ""+getBaseContext().getFilesDir() + "/e_shabbat_28.html";
		chaptersFiles[E_SHABAT][29] = ""+getBaseContext().getFilesDir() + "/e_shabbat_29.html";
		chaptersFiles[E_SHABAT][30] = ""+getBaseContext().getFilesDir() + "/e_shabbat_30.html";
		/*E_yammim*/
		chaptersFiles[E_YAMMIM][0] = ""+getBaseContext().getFilesDir() + "/e_yammim_tochen.html";
		chaptersFiles[E_YAMMIM][1] = ""+getBaseContext().getFilesDir() + "/e_yammim_1.html";
		chaptersFiles[E_YAMMIM][2] = ""+getBaseContext().getFilesDir() + "/e_yammim_2.html";
		chaptersFiles[E_YAMMIM][3] = ""+getBaseContext().getFilesDir() + "/e_yammim_3.html";
		chaptersFiles[E_YAMMIM][4] = ""+getBaseContext().getFilesDir() + "/e_yammim_4.html";
		chaptersFiles[E_YAMMIM][5] = ""+getBaseContext().getFilesDir() + "/e_yammim_5.html";
		chaptersFiles[E_YAMMIM][6] = ""+getBaseContext().getFilesDir() + "/e_yammim_6.html";
		chaptersFiles[E_YAMMIM][7] = ""+getBaseContext().getFilesDir() + "/e_yammim_7.html";
		chaptersFiles[E_YAMMIM][8] = ""+getBaseContext().getFilesDir() + "/e_yammim_8.html";
		chaptersFiles[E_YAMMIM][9] = ""+getBaseContext().getFilesDir() + "/e_yammim_9.html";
		chaptersFiles[E_YAMMIM][10] = ""+getBaseContext().getFilesDir() + "/e_yammim_10.html";
		/*E_moadim*/
		chaptersFiles[E_MOADIM][0] = ""+getBaseContext().getFilesDir() + "/e_moadim_tochen.html";
		chaptersFiles[E_MOADIM][1] = ""+getBaseContext().getFilesDir() + "/e_moadim_1.html";
		chaptersFiles[E_MOADIM][2] = ""+getBaseContext().getFilesDir() + "/e_moadim_2.html";
		chaptersFiles[E_MOADIM][3] = ""+getBaseContext().getFilesDir() + "/e_moadim_3.html";
		chaptersFiles[E_MOADIM][4] = ""+getBaseContext().getFilesDir() + "/e_moadim_4.html";
		chaptersFiles[E_MOADIM][5] = ""+getBaseContext().getFilesDir() + "/e_moadim_5.html";
		chaptersFiles[E_MOADIM][6] = ""+getBaseContext().getFilesDir() + "/e_moadim_6.html";
		chaptersFiles[E_MOADIM][7] = ""+getBaseContext().getFilesDir() + "/e_moadim_7.html";
		chaptersFiles[E_MOADIM][8] = ""+getBaseContext().getFilesDir() + "/e_moadim_8.html";
		chaptersFiles[E_MOADIM][9] = ""+getBaseContext().getFilesDir() + "/e_moadim_9.html";
		chaptersFiles[E_MOADIM][10] = ""+getBaseContext().getFilesDir() + "/e_moadim_10.html";
		chaptersFiles[E_MOADIM][11] = ""+getBaseContext().getFilesDir() + "/e_moadim_11.html";
		chaptersFiles[E_MOADIM][12] = ""+getBaseContext().getFilesDir() + "/e_moadim_12.html";
		chaptersFiles[E_MOADIM][13] = ""+getBaseContext().getFilesDir() + "/e_moadim_13.html";
		/*E_simchat*/
		chaptersFiles[E_SIMCHAT][0] = ""+getBaseContext().getFilesDir() + "/e_simchat_tochen.html";
		chaptersFiles[E_SIMCHAT][1] = ""+getBaseContext().getFilesDir() + "/e_simchat_1.html";
		chaptersFiles[E_SIMCHAT][2] = ""+getBaseContext().getFilesDir() + "/e_simchat_2.html";
		chaptersFiles[E_SIMCHAT][3] = ""+getBaseContext().getFilesDir() + "/e_simchat_3.html";
		chaptersFiles[E_SIMCHAT][4] = ""+getBaseContext().getFilesDir() + "/e_simchat_4.html";
		chaptersFiles[E_SIMCHAT][5] = ""+getBaseContext().getFilesDir() + "/e_simchat_5.html";
		chaptersFiles[E_SIMCHAT][6] = ""+getBaseContext().getFilesDir() + "/e_simchat_6.html";
		chaptersFiles[E_SIMCHAT][7] = ""+getBaseContext().getFilesDir() + "/e_simchat_7.html";
		chaptersFiles[E_SIMCHAT][8] = ""+getBaseContext().getFilesDir() + "/e_simchat_8.html";
		chaptersFiles[E_SIMCHAT][9] = ""+getBaseContext().getFilesDir() + "/e_simchat_9.html";
		/*F_TEFILA*/
		chaptersFiles[F_TEFILA][0] = ""+getBaseContext().getFilesDir() + "/f_tefila_tochen.html";
		chaptersFiles[F_TEFILA][1] = ""+getBaseContext().getFilesDir() + "/f_tefila_1.html";
		chaptersFiles[F_TEFILA][2] = ""+getBaseContext().getFilesDir() + "/f_tefila_2.html";
		chaptersFiles[F_TEFILA][3] = ""+getBaseContext().getFilesDir() + "/f_tefila_3.html";
		chaptersFiles[F_TEFILA][4] = ""+getBaseContext().getFilesDir() + "/f_tefila_4.html";
		chaptersFiles[F_TEFILA][5] = ""+getBaseContext().getFilesDir() + "/f_tefila_5.html";
		chaptersFiles[F_TEFILA][6] = ""+getBaseContext().getFilesDir() + "/f_tefila_6.html";
		chaptersFiles[F_TEFILA][7] = ""+getBaseContext().getFilesDir() + "/f_tefila_7.html";
		chaptersFiles[F_TEFILA][8] = ""+getBaseContext().getFilesDir() + "/f_tefila_8.html";
		chaptersFiles[F_TEFILA][9] = ""+getBaseContext().getFilesDir() + "/f_tefila_9.html";
		chaptersFiles[F_TEFILA][10] = ""+getBaseContext().getFilesDir() + "/f_tefila_10.html";
		chaptersFiles[F_TEFILA][11] = ""+getBaseContext().getFilesDir() + "/f_tefila_11.html";
		chaptersFiles[F_TEFILA][12] = ""+getBaseContext().getFilesDir() + "/f_tefila_12.html";
		chaptersFiles[F_TEFILA][13] = ""+getBaseContext().getFilesDir() + "/f_tefila_13.html";
		chaptersFiles[F_TEFILA][14] = ""+getBaseContext().getFilesDir() + "/f_tefila_14.html";
		chaptersFiles[F_TEFILA][15] = ""+getBaseContext().getFilesDir() + "/f_tefila_15.html";
		chaptersFiles[F_TEFILA][16] = ""+getBaseContext().getFilesDir() + "/f_tefila_16.html";
		chaptersFiles[F_TEFILA][17] = ""+getBaseContext().getFilesDir() + "/f_tefila_17.html";
		chaptersFiles[F_TEFILA][18] = ""+getBaseContext().getFilesDir() + "/f_tefila_18.html";
		chaptersFiles[F_TEFILA][19] = ""+getBaseContext().getFilesDir() + "/f_tefila_19.html";
		chaptersFiles[F_TEFILA][20] = ""+getBaseContext().getFilesDir() + "/f_tefila_20.html";
		chaptersFiles[F_TEFILA][21] = ""+getBaseContext().getFilesDir() + "/f_tefila_21.html";
		chaptersFiles[F_TEFILA][22] = ""+getBaseContext().getFilesDir() + "/f_tefila_22.html";
		chaptersFiles[F_TEFILA][23] = ""+getBaseContext().getFilesDir() + "/f_tefila_23.html";
		chaptersFiles[F_TEFILA][24] = ""+getBaseContext().getFilesDir() + "/f_tefila_24.html";
		chaptersFiles[F_TEFILA][25] = ""+getBaseContext().getFilesDir() + "/f_tefila_25.html";
		chaptersFiles[F_TEFILA][26] = ""+getBaseContext().getFilesDir() + "/f_tefila_26.html";
		/*F_Moadim*/
		chaptersFiles[F_MOADIM][0] = ""+getBaseContext().getFilesDir() + "/f_moadim_tochen.html";
		chaptersFiles[F_MOADIM][1] = ""+getBaseContext().getFilesDir() + "/f_moadim_1.html";
		chaptersFiles[F_MOADIM][2] = ""+getBaseContext().getFilesDir() + "/f_moadim_2.html";
		chaptersFiles[F_MOADIM][3] = ""+getBaseContext().getFilesDir() + "/f_moadim_3.html";
		chaptersFiles[F_MOADIM][4] = ""+getBaseContext().getFilesDir() + "/f_moadim_4.html";
		chaptersFiles[F_MOADIM][5] = ""+getBaseContext().getFilesDir() + "/f_moadim_5.html";
		chaptersFiles[F_MOADIM][6] = ""+getBaseContext().getFilesDir() + "/f_moadim_6.html";
		chaptersFiles[F_MOADIM][7] = ""+getBaseContext().getFilesDir() + "/f_moadim_7.html";
		chaptersFiles[F_MOADIM][8] = ""+getBaseContext().getFilesDir() + "/f_moadim_8.html";
		chaptersFiles[F_MOADIM][9] = ""+getBaseContext().getFilesDir() + "/f_moadim_9.html";
		chaptersFiles[F_MOADIM][10] = ""+getBaseContext().getFilesDir() + "/f_moadim_10.html";
		chaptersFiles[F_MOADIM][11] = ""+getBaseContext().getFilesDir() + "/f_moadim_11.html";
		chaptersFiles[F_MOADIM][12] = ""+getBaseContext().getFilesDir() + "/f_moadim_12.html";
		chaptersFiles[F_MOADIM][13] = ""+getBaseContext().getFilesDir() + "/f_moadim_13.html";
		/*F_SUCOT*/
		chaptersFiles[F_SUCOT][0] = ""+getBaseContext().getFilesDir() + "/f_sucot_tochen.html";
		chaptersFiles[F_SUCOT][1] = ""+getBaseContext().getFilesDir() + "/f_sucot_1.html";
		chaptersFiles[F_SUCOT][2] = ""+getBaseContext().getFilesDir() + "/f_sucot_2.html";
		chaptersFiles[F_SUCOT][3] = ""+getBaseContext().getFilesDir() + "/f_sucot_3.html";
		chaptersFiles[F_SUCOT][4] = ""+getBaseContext().getFilesDir() + "/f_sucot_4.html";
		chaptersFiles[F_SUCOT][5] = ""+getBaseContext().getFilesDir() + "/f_sucot_5.html";
		chaptersFiles[F_SUCOT][6] = ""+getBaseContext().getFilesDir() + "/f_sucot_6.html";
		chaptersFiles[F_SUCOT][7] = ""+getBaseContext().getFilesDir() + "/f_sucot_7.html";
		chaptersFiles[F_SUCOT][8] = ""+getBaseContext().getFilesDir() + "/f_sucot_8.html";
		chaptersFiles[F_SUCOT][9] = ""+getBaseContext().getFilesDir() + "/f_sucot_9.html";
		chaptersFiles[F_SUCOT][10] = ""+getBaseContext().getFilesDir() + "/f_sucot_10.html";
		chaptersFiles[F_SUCOT][11] = ""+getBaseContext().getFilesDir() + "/f_sucot_11.html";
		chaptersFiles[F_SUCOT][12] = ""+getBaseContext().getFilesDir() + "/f_sucot_12.html";
		chaptersFiles[F_SUCOT][13] = ""+getBaseContext().getFilesDir() + "/f_sucot_13.html";
		/*F_ZMANIM*/
		chaptersFiles[F_ZMANIM][0] = ""+getBaseContext().getFilesDir() + "/f_zmanim_tochen.html";
		chaptersFiles[F_ZMANIM][1] = ""+getBaseContext().getFilesDir() + "/f_zmanim_1.html";
		chaptersFiles[F_ZMANIM][2] = ""+getBaseContext().getFilesDir() + "/f_zmanim_2.html";
		chaptersFiles[F_ZMANIM][3] = ""+getBaseContext().getFilesDir() + "/f_zmanim_3.html";
		chaptersFiles[F_ZMANIM][4] = ""+getBaseContext().getFilesDir() + "/f_zmanim_4.html";
		chaptersFiles[F_ZMANIM][5] = ""+getBaseContext().getFilesDir() + "/f_zmanim_5.html";
		chaptersFiles[F_ZMANIM][6] = ""+getBaseContext().getFilesDir() + "/f_zmanim_6.html";
		chaptersFiles[F_ZMANIM][7] = ""+getBaseContext().getFilesDir() + "/f_zmanim_7.html";
		chaptersFiles[F_ZMANIM][8] = ""+getBaseContext().getFilesDir() + "/f_zmanim_8.html";
		chaptersFiles[F_ZMANIM][9] = ""+getBaseContext().getFilesDir() + "/f_zmanim_9.html";
		chaptersFiles[F_ZMANIM][10] = ""+getBaseContext().getFilesDir() + "/f_zmanim_10.html";
		chaptersFiles[F_ZMANIM][11] = ""+getBaseContext().getFilesDir() + "/f_zmanim_11.html";
		chaptersFiles[F_ZMANIM][12] = ""+getBaseContext().getFilesDir() + "/f_zmanim_12.html";
		chaptersFiles[F_ZMANIM][13] = ""+getBaseContext().getFilesDir() + "/f_zmanim_13.html";
		chaptersFiles[F_ZMANIM][14] = ""+getBaseContext().getFilesDir() + "/f_zmanim_14.html";
		chaptersFiles[F_ZMANIM][15] = ""+getBaseContext().getFilesDir() + "/f_zmanim_15.html";
		chaptersFiles[F_ZMANIM][16] = ""+getBaseContext().getFilesDir() + "/f_zmanim_16.html";
		chaptersFiles[F_ZMANIM][17] = ""+getBaseContext().getFilesDir() + "/f_zmanim_17.html";
		/*F_SIMCHAT*/
		chaptersFiles[F_SIMCHAT][0] = ""+getBaseContext().getFilesDir() + "/f_simchat_tochen.html";
		chaptersFiles[F_SIMCHAT][1] = ""+getBaseContext().getFilesDir() + "/f_simchat_1.html";
		chaptersFiles[F_SIMCHAT][2] = ""+getBaseContext().getFilesDir() + "/f_simchat_2.html";
		chaptersFiles[F_SIMCHAT][3] = ""+getBaseContext().getFilesDir() + "/f_simchat_3.html";
		chaptersFiles[F_SIMCHAT][4] = ""+getBaseContext().getFilesDir() + "/f_simchat_4.html";
		chaptersFiles[F_SIMCHAT][5] = ""+getBaseContext().getFilesDir() + "/f_simchat_5.html";
		chaptersFiles[F_SIMCHAT][6] = ""+getBaseContext().getFilesDir() + "/f_simchat_6.html";
		chaptersFiles[F_SIMCHAT][7] = ""+getBaseContext().getFilesDir() + "/f_simchat_7.html";
		chaptersFiles[F_SIMCHAT][8] = ""+getBaseContext().getFilesDir() + "/f_simchat_8.html";
		chaptersFiles[F_SIMCHAT][9] = ""+getBaseContext().getFilesDir() + "/f_simchat_9.html";
		chaptersFiles[F_SIMCHAT][10] = ""+getBaseContext().getFilesDir() + "/f_simchat_10.html";
		/*F_ZMANIM*/
		chaptersFiles[F_PESACH][0] = ""+getBaseContext().getFilesDir() + "/f_pesach_tochen.html";
		chaptersFiles[F_PESACH][1] = ""+getBaseContext().getFilesDir() + "/f_pesach_1.html";
		chaptersFiles[F_PESACH][2] = ""+getBaseContext().getFilesDir() + "/f_pesach_2.html";
		chaptersFiles[F_PESACH][3] = ""+getBaseContext().getFilesDir() + "/f_pesach_3.html";
		chaptersFiles[F_PESACH][4] = ""+getBaseContext().getFilesDir() + "/f_pesach_4.html";
		chaptersFiles[F_PESACH][5] = ""+getBaseContext().getFilesDir() + "/f_pesach_5.html";
		chaptersFiles[F_PESACH][6] = ""+getBaseContext().getFilesDir() + "/f_pesach_6.html";
		chaptersFiles[F_PESACH][7] = ""+getBaseContext().getFilesDir() + "/f_pesach_7.html";
		chaptersFiles[F_PESACH][8] = ""+getBaseContext().getFilesDir() + "/f_pesach_8.html";
		chaptersFiles[F_PESACH][9] = ""+getBaseContext().getFilesDir() + "/f_pesach_9.html";
		chaptersFiles[F_PESACH][10] = ""+getBaseContext().getFilesDir() + "/f_pesach_10.html";
		chaptersFiles[F_PESACH][11] = ""+getBaseContext().getFilesDir() + "/f_pesach_11.html";
		chaptersFiles[F_PESACH][12] = ""+getBaseContext().getFilesDir() + "/f_pesach_12.html";
		chaptersFiles[F_PESACH][13] = ""+getBaseContext().getFilesDir() + "/f_pesach_13.html";
		chaptersFiles[F_PESACH][14] = ""+getBaseContext().getFilesDir() + "/f_pesach_14.html";
		chaptersFiles[F_PESACH][15] = ""+getBaseContext().getFilesDir() + "/f_pesach_15.html";
		chaptersFiles[F_PESACH][16] = ""+getBaseContext().getFilesDir() + "/f_pesach_16.html";
		/*F_TFILAT_NASHIM*/
		chaptersFiles[F_TFILAT_NASHIM][0] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_tochen.html";
		chaptersFiles[F_TFILAT_NASHIM][1] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_1.html";
		chaptersFiles[F_TFILAT_NASHIM][2] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_2.html";
		chaptersFiles[F_TFILAT_NASHIM][3] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_3.html";
		chaptersFiles[F_TFILAT_NASHIM][4] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_4.html";
		chaptersFiles[F_TFILAT_NASHIM][5] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_5.html";
		chaptersFiles[F_TFILAT_NASHIM][6] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_6.html";
		chaptersFiles[F_TFILAT_NASHIM][7] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_7.html";
		chaptersFiles[F_TFILAT_NASHIM][8] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_8.html";
		chaptersFiles[F_TFILAT_NASHIM][9] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_9.html";
		chaptersFiles[F_TFILAT_NASHIM][10] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_10.html";
		chaptersFiles[F_TFILAT_NASHIM][11] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_11.html";
		chaptersFiles[F_TFILAT_NASHIM][12] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_12.html";
		chaptersFiles[F_TFILAT_NASHIM][13] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_13.html";
		chaptersFiles[F_TFILAT_NASHIM][14] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_14.html";
		chaptersFiles[F_TFILAT_NASHIM][15] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_15.html";
		chaptersFiles[F_TFILAT_NASHIM][16] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_16.html";
		chaptersFiles[F_TFILAT_NASHIM][17] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_17.html";
		chaptersFiles[F_TFILAT_NASHIM][18] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_18.html";
		chaptersFiles[F_TFILAT_NASHIM][19] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_19.html";
		chaptersFiles[F_TFILAT_NASHIM][20] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_20.html";
		chaptersFiles[F_TFILAT_NASHIM][21] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_21.html";
		chaptersFiles[F_TFILAT_NASHIM][22] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_22.html";
		chaptersFiles[F_TFILAT_NASHIM][23] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_23.html";
		chaptersFiles[F_TFILAT_NASHIM][24] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_24.html";
		chaptersFiles[F_TFILAT_NASHIM][25] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_25.html";
		chaptersFiles[F_TFILAT_NASHIM][26] = ""+getBaseContext().getFilesDir() + "/f_tfilat_nashim_26.html";
		/*F_SHABAT*/
		chaptersFiles[F_SHABBAT][0] = ""+getBaseContext().getFilesDir() + "/f_shabbat_tochen.html";
		chaptersFiles[F_SHABBAT][1] = ""+getBaseContext().getFilesDir() + "/f_shabbat_1.html";
		chaptersFiles[F_SHABBAT][2] = ""+getBaseContext().getFilesDir() + "/f_shabbat_2.html";
		chaptersFiles[F_SHABBAT][3] = ""+getBaseContext().getFilesDir() + "/f_shabbat_3.html";
		chaptersFiles[F_SHABBAT][4] = ""+getBaseContext().getFilesDir() + "/f_shabbat_4.html";
		chaptersFiles[F_SHABBAT][5] = ""+getBaseContext().getFilesDir() + "/f_shabbat_5.html";
		chaptersFiles[F_SHABBAT][6] = ""+getBaseContext().getFilesDir() + "/f_shabbat_6.html";
		chaptersFiles[F_SHABBAT][7] = ""+getBaseContext().getFilesDir() + "/f_shabbat_7.html";
		chaptersFiles[F_SHABBAT][8] = ""+getBaseContext().getFilesDir() + "/f_shabbat_8.html";
		chaptersFiles[F_SHABBAT][9] = ""+getBaseContext().getFilesDir() + "/f_shabbat_9.html";
		chaptersFiles[F_SHABBAT][10] = ""+getBaseContext().getFilesDir() + "/f_shabbat_10.html";
		chaptersFiles[F_SHABBAT][11] = ""+getBaseContext().getFilesDir() + "/f_shabbat_11.html";
		chaptersFiles[F_SHABBAT][12] = ""+getBaseContext().getFilesDir() + "/f_shabbat_12.html";
		chaptersFiles[R_SHABBAT][13] = ""+getBaseContext().getFilesDir() + "/f_shabbat_13.html";
		chaptersFiles[F_SHABBAT][14] = ""+getBaseContext().getFilesDir() + "/f_shabbat_14.html";
		chaptersFiles[F_SHABBAT][15] = ""+getBaseContext().getFilesDir() + "/f_shabbat_15.html";
		chaptersFiles[F_SHABBAT][16] = ""+getBaseContext().getFilesDir() + "/f_shabbat_16.html";
		chaptersFiles[F_SHABBAT][17] = ""+getBaseContext().getFilesDir() + "/f_shabbat_17.html";
		chaptersFiles[F_SHABBAT][18] = ""+getBaseContext().getFilesDir() + "/f_shabbat_18.html";
		chaptersFiles[F_SHABBAT][19] = ""+getBaseContext().getFilesDir() + "/f_shabbat_19.html";
		chaptersFiles[F_SHABBAT][20] = ""+getBaseContext().getFilesDir() + "/f_shabbat_20.html";
		chaptersFiles[F_SHABBAT][21] = ""+getBaseContext().getFilesDir() + "/f_shabbat_21.html";
		chaptersFiles[F_SHABBAT][22] = ""+getBaseContext().getFilesDir() + "/f_shabbat_22.html";
		chaptersFiles[F_SHABBAT][23] = ""+getBaseContext().getFilesDir() + "/f_shabbat_23.html";
		chaptersFiles[F_SHABBAT][24] = ""+getBaseContext().getFilesDir() + "/f_shabbat_24.html";
		chaptersFiles[F_SHABBAT][25] = ""+getBaseContext().getFilesDir() + "/f_shabbat_25.html";
		chaptersFiles[F_SHABBAT][26] = ""+getBaseContext().getFilesDir() + "/f_shabbat_26.html";
		chaptersFiles[F_SHABBAT][27] = ""+getBaseContext().getFilesDir() + "/f_shabbat_27.html";
		chaptersFiles[F_SHABBAT][28] = ""+getBaseContext().getFilesDir() + "/f_shabbat_28.html";
		chaptersFiles[F_SHABBAT][29] = ""+getBaseContext().getFilesDir() + "/f_shabbat_29.html";
		chaptersFiles[F_SHABBAT][30] = ""+getBaseContext().getFilesDir() + "/f_shabbat_30.html";

		/*F_YAMIM*/
		chaptersFiles[F_YAMMIM][0] = ""+getBaseContext().getFilesDir() + "/f_yammim_tochen.html";
		chaptersFiles[F_YAMMIM][1] = ""+getBaseContext().getFilesDir() + "/f_yammim_1.html";
		chaptersFiles[F_YAMMIM][2] = ""+getBaseContext().getFilesDir() + "/f_yammim_2.html";
		chaptersFiles[F_YAMMIM][3] = ""+getBaseContext().getFilesDir() + "/f_yammim_3.html";
		chaptersFiles[F_YAMMIM][4] = ""+getBaseContext().getFilesDir() + "/f_yammim_4.html";
		chaptersFiles[F_YAMMIM][5] = ""+getBaseContext().getFilesDir() + "/f_yammim_5.html";
		chaptersFiles[F_YAMMIM][6] = ""+getBaseContext().getFilesDir() + "/f_yammim_6.html";
		chaptersFiles[F_YAMMIM][7] = ""+getBaseContext().getFilesDir() + "/f_yammim_7.html";
		chaptersFiles[F_YAMMIM][8] = ""+getBaseContext().getFilesDir() + "/f_yammim_8.html";
		chaptersFiles[F_YAMMIM][9] = ""+getBaseContext().getFilesDir() + "/f_yammim_9.html";
		chaptersFiles[F_YAMMIM][10]= ""+getBaseContext().getFilesDir() + "/f_yammim_10.html";

		/*S_SHABAT*/
		chaptersFiles[S_SHABAT][0] = ""+getBaseContext().getFilesDir() + "/s_shabbat_tochen.html";
		chaptersFiles[S_SHABAT][1] = ""+getBaseContext().getFilesDir() + "/s_shabbat_1.html";
		chaptersFiles[S_SHABAT][2] = ""+getBaseContext().getFilesDir() + "/s_shabbat_2.html";
		chaptersFiles[S_SHABAT][3] = ""+getBaseContext().getFilesDir() + "/s_shabbat_3.html";
		chaptersFiles[S_SHABAT][4] = ""+getBaseContext().getFilesDir() + "/s_shabbat_4.html";
		chaptersFiles[S_SHABAT][5] = ""+getBaseContext().getFilesDir() + "/s_shabbat_5.html";
		chaptersFiles[S_SHABAT][6] = ""+getBaseContext().getFilesDir() + "/s_shabbat_6.html";
		chaptersFiles[S_SHABAT][7] = ""+getBaseContext().getFilesDir() + "/s_shabbat_7.html";
		chaptersFiles[S_SHABAT][8] = ""+getBaseContext().getFilesDir() + "/s_shabbat_8.html";
		chaptersFiles[S_SHABAT][9] = ""+getBaseContext().getFilesDir() + "/s_shabbat_9.html";
		chaptersFiles[S_SHABAT][10] = ""+getBaseContext().getFilesDir() + "/s_shabbat_10.html";
		chaptersFiles[S_SHABAT][11] = ""+getBaseContext().getFilesDir() + "/s_shabbat_11.html";
		chaptersFiles[S_SHABAT][12] = ""+getBaseContext().getFilesDir() + "/s_shabbat_12.html";
		chaptersFiles[S_SHABAT][13] = ""+getBaseContext().getFilesDir() + "/s_shabbat_13.html";
		chaptersFiles[S_SHABAT][14] = ""+getBaseContext().getFilesDir() + "/s_shabbat_14.html";
		chaptersFiles[S_SHABAT][15] = ""+getBaseContext().getFilesDir() + "/s_shabbat_15.html";
		chaptersFiles[S_SHABAT][16] = ""+getBaseContext().getFilesDir() + "/s_shabbat_16.html";
		chaptersFiles[S_SHABAT][17] = ""+getBaseContext().getFilesDir() + "/s_shabbat_17.html";
		chaptersFiles[S_SHABAT][18] = ""+getBaseContext().getFilesDir() + "/s_shabbat_18.html";
		chaptersFiles[S_SHABAT][19] = ""+getBaseContext().getFilesDir() + "/s_shabbat_19.html";
		chaptersFiles[S_SHABAT][20] = ""+getBaseContext().getFilesDir() + "/s_shabbat_20.html";
		chaptersFiles[S_SHABAT][21] = ""+getBaseContext().getFilesDir() + "/s_shabbat_21.html";
		chaptersFiles[S_SHABAT][22] = ""+getBaseContext().getFilesDir() + "/s_shabbat_22.html";
		chaptersFiles[S_SHABAT][23] = ""+getBaseContext().getFilesDir() + "/s_shabbat_23.html";
		chaptersFiles[S_SHABAT][24] = ""+getBaseContext().getFilesDir() + "/s_shabbat_24.html";
		chaptersFiles[S_SHABAT][25] = ""+getBaseContext().getFilesDir() + "/s_shabbat_25.html";
		chaptersFiles[S_SHABAT][26] = ""+getBaseContext().getFilesDir() + "/s_shabbat_26.html";
		chaptersFiles[S_SHABAT][27] = ""+getBaseContext().getFilesDir() + "/s_shabbat_27.html";
		chaptersFiles[S_SHABAT][28] = ""+getBaseContext().getFilesDir() + "/s_shabbat_28.html";
		chaptersFiles[S_SHABAT][29] = ""+getBaseContext().getFilesDir() + "/s_shabbat_29.html";
		chaptersFiles[S_SHABAT][30] = ""+getBaseContext().getFilesDir() + "/s_shabbat_30.html";

		/*S_BRACHOT*/
		chaptersFiles[S_BRACHOT][0] = ""+getBaseContext().getFilesDir() + "/s_brachot_tochen.html";
		chaptersFiles[S_BRACHOT][1] = ""+getBaseContext().getFilesDir() + "/s_brachot_1.html";
		chaptersFiles[S_BRACHOT][2] = ""+getBaseContext().getFilesDir() + "/s_brachot_2.html";
		chaptersFiles[S_BRACHOT][3] = ""+getBaseContext().getFilesDir() + "/s_brachot_3.html";
		chaptersFiles[S_BRACHOT][4] = ""+getBaseContext().getFilesDir() + "/s_brachot_4.html";
		chaptersFiles[S_BRACHOT][5] = ""+getBaseContext().getFilesDir() + "/s_brachot_5.html";
		chaptersFiles[S_BRACHOT][6] = ""+getBaseContext().getFilesDir() + "/s_brachot_6.html";
		chaptersFiles[S_BRACHOT][7] = ""+getBaseContext().getFilesDir() + "/s_brachot_7.html";
		chaptersFiles[S_BRACHOT][8] = ""+getBaseContext().getFilesDir() + "/s_brachot_8.html";
		chaptersFiles[S_BRACHOT][9] = ""+getBaseContext().getFilesDir() + "/s_brachot_9.html";
		chaptersFiles[S_BRACHOT][10] = ""+getBaseContext().getFilesDir() + "/s_brachot_10.html";
		chaptersFiles[S_BRACHOT][11] = ""+getBaseContext().getFilesDir() + "/s_brachot_11.html";
		chaptersFiles[S_BRACHOT][12] = ""+getBaseContext().getFilesDir() + "/s_brachot_12.html";
		chaptersFiles[S_BRACHOT][13] = ""+getBaseContext().getFilesDir() + "/s_brachot_13.html";
		chaptersFiles[S_BRACHOT][14] = ""+getBaseContext().getFilesDir() + "/s_brachot_14.html";
		chaptersFiles[S_BRACHOT][15] = ""+getBaseContext().getFilesDir() + "/s_brachot_15.html";
		chaptersFiles[S_BRACHOT][16] = ""+getBaseContext().getFilesDir() + "/s_brachot_16.html";
		chaptersFiles[S_BRACHOT][17] = ""+getBaseContext().getFilesDir() + "/s_brachot_17.html";
		chaptersFiles[S_BRACHOT][18] = ""+getBaseContext().getFilesDir() + "/s_brachot_18.html";

		/*S_MOADIM*/
		chaptersFiles[S_MOADIM][0] = ""+getBaseContext().getFilesDir() + "/s_moadim_tochen.html";
		chaptersFiles[S_MOADIM][1] = ""+getBaseContext().getFilesDir() + "/s_moadim_1.html";
		chaptersFiles[S_MOADIM][2] = ""+getBaseContext().getFilesDir() + "/s_moadim_2.html";
		chaptersFiles[S_MOADIM][3] = ""+getBaseContext().getFilesDir() + "/s_moadim_3.html";
		chaptersFiles[S_MOADIM][4] = ""+getBaseContext().getFilesDir() + "/s_moadim_4.html";
		chaptersFiles[S_MOADIM][5] = ""+getBaseContext().getFilesDir() + "/s_moadim_5.html";
		chaptersFiles[S_MOADIM][6] = ""+getBaseContext().getFilesDir() + "/s_moadim_6.html";
		chaptersFiles[S_MOADIM][7] = ""+getBaseContext().getFilesDir() + "/s_moadim_7.html";
		chaptersFiles[S_MOADIM][8] = ""+getBaseContext().getFilesDir() + "/s_moadim_8.html";
		chaptersFiles[S_MOADIM][9] = ""+getBaseContext().getFilesDir() + "/s_moadim_9.html";
		chaptersFiles[S_MOADIM][10] = ""+getBaseContext().getFilesDir() + "/s_moadim_10.html";
		chaptersFiles[S_MOADIM][11] = ""+getBaseContext().getFilesDir() + "/s_moadim_11.html";
		chaptersFiles[S_MOADIM][12] = ""+getBaseContext().getFilesDir() + "/s_moadim_12.html";
		chaptersFiles[S_MOADIM][13] = ""+getBaseContext().getFilesDir() + "/s_moadim_13.html";
		/*S_YAMIM*/
		chaptersFiles[S_YAMIM][0] = ""+getBaseContext().getFilesDir() + "/s_yamim_tochen.html";
		chaptersFiles[S_YAMIM][1] = ""+getBaseContext().getFilesDir() + "/s_yamim_1.html";
		chaptersFiles[S_YAMIM][2] = ""+getBaseContext().getFilesDir() + "/s_yamim_2.html";
		chaptersFiles[S_YAMIM][3] = ""+getBaseContext().getFilesDir() + "/s_yamim_3.html";
		chaptersFiles[S_YAMIM][4] = ""+getBaseContext().getFilesDir() + "/s_yamim_4.html";
		chaptersFiles[S_YAMIM][5] = ""+getBaseContext().getFilesDir() + "/s_yamim_5.html";
		chaptersFiles[S_YAMIM][6] = ""+getBaseContext().getFilesDir() + "/s_yamim_6.html";
		chaptersFiles[S_YAMIM][7] = ""+getBaseContext().getFilesDir() + "/s_yamim_7.html";
		chaptersFiles[S_YAMIM][8] = ""+getBaseContext().getFilesDir() + "/s_yamim_8.html";
		chaptersFiles[S_YAMIM][9] = ""+getBaseContext().getFilesDir() + "/s_yamim_9.html";
		chaptersFiles[S_YAMIM][10] = ""+getBaseContext().getFilesDir() + "/s_yamim_10.html";
		/*S_PESACH*/
		chaptersFiles[S_PESACH][0] = ""+getBaseContext().getFilesDir() + "/s_pesach_tochen.html";
		chaptersFiles[S_PESACH][1] = ""+getBaseContext().getFilesDir() + "/s_pesach_1.html";
		chaptersFiles[S_PESACH][2] = ""+getBaseContext().getFilesDir() + "/s_pesach_2.html";
		chaptersFiles[S_PESACH][3] = ""+getBaseContext().getFilesDir() + "/s_pesach_3.html";
		chaptersFiles[S_PESACH][4] = ""+getBaseContext().getFilesDir() + "/s_pesach_4.html";
		chaptersFiles[S_PESACH][5] = ""+getBaseContext().getFilesDir() + "/s_pesach_5.html";
		chaptersFiles[S_PESACH][6] = ""+getBaseContext().getFilesDir() + "/s_pesach_6.html";
		chaptersFiles[S_PESACH][7] = ""+getBaseContext().getFilesDir() + "/s_pesach_7.html";
		chaptersFiles[S_PESACH][8] = ""+getBaseContext().getFilesDir() + "/s_pesach_8.html";
		chaptersFiles[S_PESACH][9] = ""+getBaseContext().getFilesDir() + "/s_pesach_9.html";
		chaptersFiles[S_PESACH][10] = ""+getBaseContext().getFilesDir() + "/s_pesach_10.html";
		chaptersFiles[S_PESACH][11] = ""+getBaseContext().getFilesDir() + "/s_pesach_11.html";
		chaptersFiles[S_PESACH][11] = "file:///android_asset/s_pesach_11.html";
		chaptersFiles[S_PESACH][12] = ""+getBaseContext().getFilesDir() + "/s_pesach_12.html";
		chaptersFiles[S_PESACH][13] = ""+getBaseContext().getFilesDir() + "/s_pesach_13.html";
		chaptersFiles[S_PESACH][14] = ""+getBaseContext().getFilesDir() + "/s_pesach_14.html";
		chaptersFiles[S_PESACH][15] = ""+getBaseContext().getFilesDir() + "/s_pesach_15.html";
		chaptersFiles[S_PESACH][16] = ""+getBaseContext().getFilesDir() + "/s_pesach_16.html";
		/*S_simchat*/
		chaptersFiles[S_SIMCHAT][0] = ""+getBaseContext().getFilesDir() + "/s_simchat_tochen.html";
		chaptersFiles[S_SIMCHAT][1] = ""+getBaseContext().getFilesDir() + "/s_simchat_1.html";
		chaptersFiles[S_SIMCHAT][2] = ""+getBaseContext().getFilesDir() + "/s_simchat_2.html";
		chaptersFiles[S_SIMCHAT][3] = ""+getBaseContext().getFilesDir() + "/s_simchat_3.html";
		chaptersFiles[S_SIMCHAT][4] = ""+getBaseContext().getFilesDir() + "/s_simchat_4.html";
		chaptersFiles[S_SIMCHAT][5] = ""+getBaseContext().getFilesDir() + "/s_simchat_5.html";
		chaptersFiles[S_SIMCHAT][6] = ""+getBaseContext().getFilesDir() + "/s_simchat_6.html";
		chaptersFiles[S_SIMCHAT][7] = ""+getBaseContext().getFilesDir() + "/s_simchat_7.html";
		chaptersFiles[S_SIMCHAT][8] = ""+getBaseContext().getFilesDir() + "/s_simchat_8.html";
		chaptersFiles[S_SIMCHAT][9] = ""+getBaseContext().getFilesDir() + "/s_simchat_9.html";
		chaptersFiles[S_SIMCHAT][10] = ""+getBaseContext().getFilesDir() + "/s_simchat_10.html";
		/*S_TFILA*/
		chaptersFiles[S_TFILA][0] = ""+getBaseContext().getFilesDir() + "/s_tfila_tochen.html";
		chaptersFiles[S_TFILA][1] = ""+getBaseContext().getFilesDir() + "/s_tfila_1.html";
		chaptersFiles[S_TFILA][2] = ""+getBaseContext().getFilesDir() + "/s_tfila_2.html";
		chaptersFiles[S_TFILA][3] = ""+getBaseContext().getFilesDir() + "/s_tfila_3.html";
		chaptersFiles[S_TFILA][4] = ""+getBaseContext().getFilesDir() + "/s_tfila_4.html";
		chaptersFiles[S_TFILA][5] = ""+getBaseContext().getFilesDir() + "/s_tfila_5.html";
		chaptersFiles[S_TFILA][6] = ""+getBaseContext().getFilesDir() + "/s_tfila_6.html";
		chaptersFiles[S_TFILA][7] = ""+getBaseContext().getFilesDir() + "/s_tfila_7.html";
		chaptersFiles[S_TFILA][8] = ""+getBaseContext().getFilesDir() + "/s_tfila_8.html";
		chaptersFiles[S_TFILA][9] = ""+getBaseContext().getFilesDir() + "/s_tfila_9.html";
		chaptersFiles[S_TFILA][10] = ""+getBaseContext().getFilesDir() + "/s_tfila_10.html";
		chaptersFiles[S_TFILA][11] = ""+getBaseContext().getFilesDir() + "/s_tfila_11.html";
		chaptersFiles[S_TFILA][12] = ""+getBaseContext().getFilesDir() + "/s_tfila_12.html";
		chaptersFiles[S_TFILA][13] = ""+getBaseContext().getFilesDir() + "/s_tfila_13.html";
		chaptersFiles[S_TFILA][14] = ""+getBaseContext().getFilesDir() + "/s_tfila_14.html";
		chaptersFiles[S_TFILA][15] = ""+getBaseContext().getFilesDir() + "/s_tfila_15.html";
		chaptersFiles[S_TFILA][16] = ""+getBaseContext().getFilesDir() + "/s_tfila_16.html";
		chaptersFiles[S_TFILA][17] = ""+getBaseContext().getFilesDir() + "/s_tfila_17.html";
		chaptersFiles[S_TFILA][18] = ""+getBaseContext().getFilesDir() + "/s_tfila_18.html";
		chaptersFiles[S_TFILA][19] = ""+getBaseContext().getFilesDir() + "/s_tfila_19.html";
		chaptersFiles[S_TFILA][20] = ""+getBaseContext().getFilesDir() + "/s_tfila_20.html";
		chaptersFiles[S_TFILA][21] = ""+getBaseContext().getFilesDir() + "/s_tfila_21.html";
		chaptersFiles[S_TFILA][22] = ""+getBaseContext().getFilesDir() + "/s_tfila_22.html";
		chaptersFiles[S_TFILA][23] = ""+getBaseContext().getFilesDir() + "/s_tfila_23.html";
		chaptersFiles[S_TFILA][24] = ""+getBaseContext().getFilesDir() + "/s_tfila_24.html";
		chaptersFiles[S_TFILA][25] = ""+getBaseContext().getFilesDir() + "/s_tfila_25.html";
		chaptersFiles[S_TFILA][26] = ""+getBaseContext().getFilesDir() + "/s_tfila_26.html";
		/*S_TFILAT_NASHIM*/
		chaptersFiles[S_TFILAT_NASHIM][0] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_tochen.html";
		chaptersFiles[S_TFILAT_NASHIM][1] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_1.html";
		chaptersFiles[S_TFILAT_NASHIM][2] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_2.html";
		chaptersFiles[S_TFILAT_NASHIM][3] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_3.html";
		chaptersFiles[S_TFILAT_NASHIM][4] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_4.html";
		chaptersFiles[S_TFILAT_NASHIM][5] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_5.html";
		chaptersFiles[S_TFILAT_NASHIM][6] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_6.html";
		chaptersFiles[S_TFILAT_NASHIM][7] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_7.html";
		chaptersFiles[S_TFILAT_NASHIM][8] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_8.html";
		chaptersFiles[S_TFILAT_NASHIM][9] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_9.html";
		chaptersFiles[S_TFILAT_NASHIM][10] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_10.html";
		chaptersFiles[S_TFILAT_NASHIM][11] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_11.html";
		chaptersFiles[S_TFILAT_NASHIM][12] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_12.html";
		chaptersFiles[S_TFILAT_NASHIM][13] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_13.html";
		chaptersFiles[S_TFILAT_NASHIM][14] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_14.html";
		chaptersFiles[S_TFILAT_NASHIM][15] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_15.html";
		chaptersFiles[S_TFILAT_NASHIM][16] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_16.html";
		chaptersFiles[S_TFILAT_NASHIM][17] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_17.html";
		chaptersFiles[S_TFILAT_NASHIM][18] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_18.html";
		chaptersFiles[S_TFILAT_NASHIM][19] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_19.html";
		chaptersFiles[S_TFILAT_NASHIM][20] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_20.html";
		chaptersFiles[S_TFILAT_NASHIM][21] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_21.html";
		chaptersFiles[S_TFILAT_NASHIM][22] = ""+getBaseContext().getFilesDir() + "/s_tfilat_nashim_22.html";
		chaptersFiles[S_TFILAT_NASHIM][23] = ""+getBaseContext().getFilesDir() + "/s_tfila_t_nashim23.html";
		chaptersFiles[S_TFILAT_NASHIM][24] = ""+getBaseContext().getFilesDir() + "/s_tfila_t_nashim24.html";
		/*S_ZMANIM*/
		chaptersFiles[S_ZMANIM][0] = ""+getBaseContext().getFilesDir() + "/s_zmanim_tochen.html";
		chaptersFiles[S_ZMANIM][1] = ""+getBaseContext().getFilesDir() + "/s_zmanim_1.html";
		chaptersFiles[S_ZMANIM][2] = ""+getBaseContext().getFilesDir() + "/s_zmanim_2.html";
		chaptersFiles[S_ZMANIM][3] = ""+getBaseContext().getFilesDir() + "/s_zmanim_3.html";
		chaptersFiles[S_ZMANIM][4] = ""+getBaseContext().getFilesDir() + "/s_zmanim_4.html";
		chaptersFiles[S_ZMANIM][5] = ""+getBaseContext().getFilesDir() + "/s_zmanim_5.html";
		chaptersFiles[S_ZMANIM][6] = ""+getBaseContext().getFilesDir() + "/s_zmanim_6.html";
		chaptersFiles[S_ZMANIM][7] = ""+getBaseContext().getFilesDir() + "/s_zmanim_7.html";
		chaptersFiles[S_ZMANIM][8] = ""+getBaseContext().getFilesDir() + "/s_zmanim_8.html";
		chaptersFiles[S_ZMANIM][9] = ""+getBaseContext().getFilesDir() + "/s_zmanim_9.html";
		chaptersFiles[S_ZMANIM][10] = ""+getBaseContext().getFilesDir() + "/s_zmanim_10.html";
		chaptersFiles[S_ZMANIM][11] = ""+getBaseContext().getFilesDir() + "/s_zmanim_11.html";
		chaptersFiles[S_ZMANIM][12] = ""+getBaseContext().getFilesDir() + "/s_zmanim_12.html";
		chaptersFiles[S_ZMANIM][13] = ""+getBaseContext().getFilesDir() + "/s_zmanim_13.html";
		chaptersFiles[S_ZMANIM][14] = ""+getBaseContext().getFilesDir() + "/s_zmanim_14.html";
		chaptersFiles[S_ZMANIM][15] = ""+getBaseContext().getFilesDir() + "/s_zmanim_15.html";
		chaptersFiles[S_ZMANIM][16] = ""+getBaseContext().getFilesDir() + "/s_zmanim_16.html";
		chaptersFiles[S_ZMANIM][17] = ""+getBaseContext().getFilesDir() + "/s_zmanim_17.html";

		/*r_HAAM*/
		chaptersFiles[R_HAAM][0] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_tochen.html";
		chaptersFiles[R_HAAM][1] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_1.html";
		chaptersFiles[R_HAAM][2] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_2.html";
		chaptersFiles[R_HAAM][3] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_3.html";
		chaptersFiles[R_HAAM][4] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_4.html";
		chaptersFiles[R_HAAM][5] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_5.html";
		chaptersFiles[R_HAAM][6] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_6.html";
		chaptersFiles[R_HAAM][7] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_7.html";
		chaptersFiles[R_HAAM][8] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_8.html";
		chaptersFiles[R_HAAM][9] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_9.html";
		chaptersFiles[R_HAAM][10] = ""+getBaseContext().getFilesDir() + "/r_haamvehaarez_10.html";

		/*R_SHABAT*/
		chaptersFiles[R_SHABBAT][0] = ""+getBaseContext().getFilesDir() + "/r_shabbat_tochen.html";
		chaptersFiles[R_SHABBAT][1] = ""+getBaseContext().getFilesDir() + "/r_shabbat_1.html";
		chaptersFiles[R_SHABBAT][2] = ""+getBaseContext().getFilesDir() + "/r_shabbat_2.html";
		chaptersFiles[R_SHABBAT][3] = ""+getBaseContext().getFilesDir() + "/r_shabbat_3.html";
		chaptersFiles[R_SHABBAT][4] = ""+getBaseContext().getFilesDir() + "/r_shabbat_4.html";
		chaptersFiles[R_SHABBAT][5] = ""+getBaseContext().getFilesDir() + "/r_shabbat_5.html";
		chaptersFiles[R_SHABBAT][6] = ""+getBaseContext().getFilesDir() + "/r_shabbat_6.html";
		chaptersFiles[R_SHABBAT][7] = ""+getBaseContext().getFilesDir() + "/r_shabbat_7.html";
		chaptersFiles[R_SHABBAT][8] = ""+getBaseContext().getFilesDir() + "/r_shabbat_8.html";
		chaptersFiles[R_SHABBAT][9] = ""+getBaseContext().getFilesDir() + "/r_shabbat_9.html";
		chaptersFiles[R_SHABBAT][10] = ""+getBaseContext().getFilesDir() + "/r_shabbat_10.html";
		chaptersFiles[R_SHABBAT][11] = ""+getBaseContext().getFilesDir() + "/r_shabbat_11.html";
		chaptersFiles[R_SHABBAT][12] = ""+getBaseContext().getFilesDir() + "/r_shabbat_12.html";
		chaptersFiles[R_SHABBAT][13] = ""+getBaseContext().getFilesDir() + "/r_shabbat_13.html";
		chaptersFiles[R_SHABBAT][14] = ""+getBaseContext().getFilesDir() + "/r_shabbat_14.html";
		chaptersFiles[R_SHABBAT][15] = ""+getBaseContext().getFilesDir() + "/r_shabbat_15.html";
		chaptersFiles[R_SHABBAT][16] = ""+getBaseContext().getFilesDir() + "/r_shabbat_16.html";
		chaptersFiles[R_SHABBAT][17] = ""+getBaseContext().getFilesDir() + "/r_shabbat_17.html";
		chaptersFiles[R_SHABBAT][18] = ""+getBaseContext().getFilesDir() + "/r_shabbat_18.html";
		chaptersFiles[R_SHABBAT][19] = ""+getBaseContext().getFilesDir() + "/r_shabbat_19.html";
		chaptersFiles[R_SHABBAT][20] = ""+getBaseContext().getFilesDir() + "/r_shabbat_20.html";
		chaptersFiles[R_SHABBAT][21] = ""+getBaseContext().getFilesDir() + "/r_shabbat_21.html";
		chaptersFiles[R_SHABBAT][22] = ""+getBaseContext().getFilesDir() + "/r_shabbat_22.html";
		chaptersFiles[R_SHABBAT][23] = ""+getBaseContext().getFilesDir() + "/r_shabbat_23.html";
		chaptersFiles[R_SHABBAT][24] = ""+getBaseContext().getFilesDir() + "/r_shabbat_24.html";
		chaptersFiles[R_SHABBAT][25] = ""+getBaseContext().getFilesDir() + "/r_shabbat_25.html";
		chaptersFiles[R_SHABBAT][26] = ""+getBaseContext().getFilesDir() + "/r_shabbat_26.html";
		chaptersFiles[R_SHABBAT][27] = ""+getBaseContext().getFilesDir() + "/r_shabbat_27.html";
		chaptersFiles[R_SHABBAT][28] = ""+getBaseContext().getFilesDir() + "/r_shabbat_28.html";
		chaptersFiles[R_SHABBAT][29] = ""+getBaseContext().getFilesDir() + "/r_shabbat_29.html";
		chaptersFiles[R_SHABBAT][30] = ""+getBaseContext().getFilesDir() + "/r_shabbat_30.html";

		/*r_YAMIM*/
		chaptersFiles[R_YAMMIM][0] = ""+getBaseContext().getFilesDir() + "/r_yammim_tochen.html";
		chaptersFiles[R_YAMMIM][1] = ""+getBaseContext().getFilesDir() + "/r_yammim_1.html";
		chaptersFiles[R_YAMMIM][2] = ""+getBaseContext().getFilesDir() + "/r_yammim_2.html";
		chaptersFiles[R_YAMMIM][3] = ""+getBaseContext().getFilesDir() + "/r_yammim_3.html";
		chaptersFiles[R_YAMMIM][4] = ""+getBaseContext().getFilesDir() + "/r_yammim_4.html";
		chaptersFiles[R_YAMMIM][5] = ""+getBaseContext().getFilesDir() + "/r_yammim_5.html";
		chaptersFiles[R_YAMMIM][6] = ""+getBaseContext().getFilesDir() + "/r_yammim_6.html";
		chaptersFiles[R_YAMMIM][7] = ""+getBaseContext().getFilesDir() + "/r_yammim_7.html";
		chaptersFiles[R_YAMMIM][8] = ""+getBaseContext().getFilesDir() + "/r_yammim_8.html";
		chaptersFiles[R_YAMMIM][9] = ""+getBaseContext().getFilesDir() + "/r_yammim_9.html";
		chaptersFiles[R_YAMMIM][10]= ""+getBaseContext().getFilesDir() + "/r_yammim_10.html";

		/*r_SUCOT*/
		chaptersFiles[R_SUCOT][0] = ""+getBaseContext().getFilesDir() + "/r_sucot_tochen.html";
		chaptersFiles[R_SUCOT][1] = ""+getBaseContext().getFilesDir() + "/r_sucot_1.html";
		chaptersFiles[R_SUCOT][2] = ""+getBaseContext().getFilesDir() + "/r_sucot_2.html";
		chaptersFiles[R_SUCOT][3] = ""+getBaseContext().getFilesDir() + "/r_sucot_3.html";
		chaptersFiles[R_SUCOT][4] = ""+getBaseContext().getFilesDir() + "/r_sucot_4.html";
		chaptersFiles[R_SUCOT][5] = ""+getBaseContext().getFilesDir() + "/r_sucot_5.html";
		chaptersFiles[R_SUCOT][6] = ""+getBaseContext().getFilesDir() + "/r_sucot_6.html";
		chaptersFiles[R_SUCOT][7] = ""+getBaseContext().getFilesDir() + "/r_sucot_7.html";
		chaptersFiles[R_SUCOT][8] = ""+getBaseContext().getFilesDir() + "/r_sucot_8.html";

		/*r_SIMCHAT*/
		chaptersFiles[R_SIMCHAT][0] = ""+getBaseContext().getFilesDir() + "/r_simchat_tochen.html";
		chaptersFiles[R_SIMCHAT][1] = ""+getBaseContext().getFilesDir() + "/r_simchat_1.html";
		chaptersFiles[R_SIMCHAT][2] = ""+getBaseContext().getFilesDir() + "/r_simchat_2.html";
		chaptersFiles[R_SIMCHAT][3] = ""+getBaseContext().getFilesDir() + "/r_simchat_3.html";
		chaptersFiles[R_SIMCHAT][4] = ""+getBaseContext().getFilesDir() + "/r_simchat_4.html";
		chaptersFiles[R_SIMCHAT][5] = ""+getBaseContext().getFilesDir() + "/r_simchat_5.html";
		chaptersFiles[R_SIMCHAT][6] = ""+getBaseContext().getFilesDir() + "/r_simchat_6.html";
		chaptersFiles[R_SIMCHAT][7] = ""+getBaseContext().getFilesDir() + "/r_simchat_7.html";
		chaptersFiles[R_SIMCHAT][8] = ""+getBaseContext().getFilesDir() + "/r_simchat_8.html";
		chaptersFiles[R_SIMCHAT][9] = ""+getBaseContext().getFilesDir() + "/r_simchat_9.html";
		chaptersFiles[R_SIMCHAT][10] =""+getBaseContext().getFilesDir() + "/r_simchat_10.html";

		/*r_MISHPACHA*/
		chaptersFiles[R_MISHPHACHA][0] = ""+getBaseContext().getFilesDir() + "/r_misphacha_tochen.html";
		chaptersFiles[R_MISHPHACHA][1] = ""+getBaseContext().getFilesDir() + "/r_misphacha_1.html";
		chaptersFiles[R_MISHPHACHA][2] = ""+getBaseContext().getFilesDir() + "/r_misphacha_2.html";
		chaptersFiles[R_MISHPHACHA][3] = ""+getBaseContext().getFilesDir() + "/r_misphacha_3.html";
		chaptersFiles[R_MISHPHACHA][4] = ""+getBaseContext().getFilesDir() + "/r_misphacha_4.html";
		chaptersFiles[R_MISHPHACHA][5] = ""+getBaseContext().getFilesDir() + "/r_misphacha_5.html";
		chaptersFiles[R_MISHPHACHA][6] = ""+getBaseContext().getFilesDir() + "/r_misphacha_6.html";
		chaptersFiles[R_MISHPHACHA][7] = ""+getBaseContext().getFilesDir() + "/r_misphacha_7.html";
		chaptersFiles[R_MISHPHACHA][8] = ""+getBaseContext().getFilesDir() + "/r_misphacha_8.html";
		chaptersFiles[R_MISHPHACHA][9] = ""+getBaseContext().getFilesDir() + "/r_misphacha_9.html";
		chaptersFiles[R_MISHPHACHA][10] =""+getBaseContext().getFilesDir() + "/r_misphacha_10.html";

		/*R_pesach*/
		chaptersFiles[R_PESACH][0] = ""+getBaseContext().getFilesDir() + "/r_pesach_tochen.html";
		chaptersFiles[R_PESACH][1] = ""+getBaseContext().getFilesDir() + "/r_pesach_1.html";
		chaptersFiles[R_PESACH][2] = ""+getBaseContext().getFilesDir() + "/r_pesach_2.html";
		chaptersFiles[R_PESACH][3] = ""+getBaseContext().getFilesDir() + "/r_pesach_3.html";
		chaptersFiles[R_PESACH][4] = ""+getBaseContext().getFilesDir() + "/r_pesach.html";
		chaptersFiles[R_PESACH][5] = ""+getBaseContext().getFilesDir() + "/r_pesach_5.html";
		chaptersFiles[R_PESACH][6] = ""+getBaseContext().getFilesDir() + "/r_pesach_6.html";
		chaptersFiles[R_PESACH][7] = ""+getBaseContext().getFilesDir() + "/r_pesach_7.html";
		chaptersFiles[R_PESACH][8] = ""+getBaseContext().getFilesDir() + "/r_pesach_8.html";
		chaptersFiles[R_PESACH][9] = ""+getBaseContext().getFilesDir() + "/r_pesach_9.html";
		chaptersFiles[R_PESACH][10] = ""+getBaseContext().getFilesDir() + "/r_pesach_10.html";
		chaptersFiles[R_PESACH][11] = ""+getBaseContext().getFilesDir() + "/r_pesach_11.html";
		chaptersFiles[R_PESACH][12] = ""+getBaseContext().getFilesDir() + "/r_pesach_12.html";
		chaptersFiles[R_PESACH][13] = ""+getBaseContext().getFilesDir() + "/r_pesach_13.html";
		chaptersFiles[R_PESACH][14] = ""+getBaseContext().getFilesDir() + "/r_pesach_14.html";
		chaptersFiles[R_PESACH][15] = ""+getBaseContext().getFilesDir() + "/r_pesach_15.html";
		chaptersFiles[R_PESACH][16] = ""+getBaseContext().getFilesDir() + "/r_pesach_16.html";
		chaptersFiles[R_PESACH][17] = ""+getBaseContext().getFilesDir() + "/r_pesach_17.html";

		/*R_moadim*/
		chaptersFiles[R_MOADIM][0] = ""+getBaseContext().getFilesDir() + "/r_moadim_tochen.html";
		chaptersFiles[R_MOADIM][1] = ""+getBaseContext().getFilesDir() + "/r_moadim_1.html";
		chaptersFiles[R_MOADIM][2] = ""+getBaseContext().getFilesDir() + "/r_moadim_2.html";
		chaptersFiles[R_MOADIM][3] = ""+getBaseContext().getFilesDir() + "/r_moadim_3.html";
		chaptersFiles[R_MOADIM][4] = ""+getBaseContext().getFilesDir() + "/r_moadim_4.html";
		chaptersFiles[R_MOADIM][5] = ""+getBaseContext().getFilesDir() + "/r_moadim_5.html";
		chaptersFiles[R_MOADIM][6] = ""+getBaseContext().getFilesDir() + "/r_moadim_6.html";
		chaptersFiles[R_MOADIM][7] = ""+getBaseContext().getFilesDir() + "/r_moadim_7.html";
		chaptersFiles[R_MOADIM][8] = ""+getBaseContext().getFilesDir() + "/r_moadim_8.html";
		chaptersFiles[R_MOADIM][9] = ""+getBaseContext().getFilesDir() + "/r_moadim_9.html";
		chaptersFiles[R_MOADIM][9] = ""+getBaseContext().getFilesDir() + "/r_moadim_9.html";
		chaptersFiles[R_MOADIM][10] = ""+getBaseContext().getFilesDir() + "/r_moadim_10.html";
		chaptersFiles[R_MOADIM][11] = ""+getBaseContext().getFilesDir() + "/r_moadim_11.html";
		chaptersFiles[R_MOADIM][12] = ""+getBaseContext().getFilesDir() + "/r_moadim_12.html";
		chaptersFiles[R_MOADIM][13] = ""+getBaseContext().getFilesDir() + "/r_moadim_13.html";

		/*R_tfilat_nashim*/
		chaptersFiles[R_TEFILAT_NASHIM][0] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_tochen.html";
		chaptersFiles[R_TEFILAT_NASHIM][1] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_1.html";
		chaptersFiles[R_TEFILAT_NASHIM][2] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_2.html";
		chaptersFiles[R_TEFILAT_NASHIM][3] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_3.html";
		chaptersFiles[R_TEFILAT_NASHIM][4] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_4.html";
		chaptersFiles[R_TEFILAT_NASHIM][5] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_5.html";
		chaptersFiles[R_TEFILAT_NASHIM][6] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_6.html";
		chaptersFiles[R_TEFILAT_NASHIM][7] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_7.html";
		chaptersFiles[R_TEFILAT_NASHIM][8] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_8.html";
		chaptersFiles[R_TEFILAT_NASHIM][9] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_9.html";
		chaptersFiles[R_TEFILAT_NASHIM][10] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_10.html";
		chaptersFiles[R_TEFILAT_NASHIM][11] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_11.html";
		chaptersFiles[R_TEFILAT_NASHIM][12] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_12.html";
		chaptersFiles[R_TEFILAT_NASHIM][13] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_13.html";
		chaptersFiles[R_TEFILAT_NASHIM][14] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_14.html";
		chaptersFiles[R_TEFILAT_NASHIM][15] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_15.html";
		chaptersFiles[R_TEFILAT_NASHIM][16] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_16.html";
		chaptersFiles[R_TEFILAT_NASHIM][17] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_17.html";
		chaptersFiles[R_TEFILAT_NASHIM][18] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_18.html";
		chaptersFiles[R_TEFILAT_NASHIM][19] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_19.html";
		chaptersFiles[R_TEFILAT_NASHIM][20] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_20.html";
		chaptersFiles[R_TEFILAT_NASHIM][21] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_21.html";
		chaptersFiles[R_TEFILAT_NASHIM][22] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_22.html";
		chaptersFiles[R_TEFILAT_NASHIM][23] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_23.html";
		chaptersFiles[R_TEFILAT_NASHIM][24] = ""+getBaseContext().getFilesDir() + "/r_tfilat_nashim_24.html";

		/*R_tfila*/
		chaptersFiles[R_TFILA][0] = ""+getBaseContext().getFilesDir() + "/r_tfila_tochen.html";
		chaptersFiles[R_TFILA][1] = ""+getBaseContext().getFilesDir() + "/r_tfila_1.html";
		chaptersFiles[R_TFILA][2] = ""+getBaseContext().getFilesDir() + "/r_tfila_2.html";
		chaptersFiles[R_TFILA][3] = ""+getBaseContext().getFilesDir() + "/r_tfila_3.html";
		chaptersFiles[R_TFILA][4] = ""+getBaseContext().getFilesDir() + "/r_tfila_4.html";
		chaptersFiles[R_TFILA][5] = ""+getBaseContext().getFilesDir() + "/r_tfila_5.html";
		chaptersFiles[R_TFILA][6] = ""+getBaseContext().getFilesDir() + "/r_tfila_6.html";
		chaptersFiles[R_TFILA][7] = ""+getBaseContext().getFilesDir() + "/r_tfila_7.html";
		chaptersFiles[R_TFILA][8] = ""+getBaseContext().getFilesDir() + "/r_tfila_8.html";
		chaptersFiles[R_TFILA][9] = ""+getBaseContext().getFilesDir() + "/r_tfila_9.html";
		chaptersFiles[R_TFILA][10] = ""+getBaseContext().getFilesDir() + "/r_tfila_10.html";
		chaptersFiles[R_TFILA][11] = ""+getBaseContext().getFilesDir() + "/r_tfila_11.html";
		chaptersFiles[R_TFILA][12] = ""+getBaseContext().getFilesDir() + "/r_tfila_12.html";
		chaptersFiles[R_TFILA][13] = ""+getBaseContext().getFilesDir() + "/r_tfila_13.html";
		chaptersFiles[R_TFILA][14] = ""+getBaseContext().getFilesDir() + "/r_tfila_14.html";
		chaptersFiles[R_TFILA][15] = ""+getBaseContext().getFilesDir() + "/r_tfila_15.html";
		chaptersFiles[R_TFILA][16] = ""+getBaseContext().getFilesDir() + "/r_tfila_16.html";
		chaptersFiles[R_TFILA][17] = ""+getBaseContext().getFilesDir() + "/r_tfila_17.html";
		chaptersFiles[R_TFILA][18] = ""+getBaseContext().getFilesDir() + "/r_tfila_18.html";
		chaptersFiles[R_TFILA][19] = ""+getBaseContext().getFilesDir() + "/r_tfila_19.html";
		chaptersFiles[R_TFILA][20] = ""+getBaseContext().getFilesDir() + "/r_tfila_20.html";
		chaptersFiles[R_TFILA][21] = ""+getBaseContext().getFilesDir() + "/r_tfila_21.html";
		chaptersFiles[R_TFILA][22] = ""+getBaseContext().getFilesDir() + "/r_tfila_22.html";
		chaptersFiles[R_TFILA][23] = ""+getBaseContext().getFilesDir() + "/r_tfila_23.html";
		chaptersFiles[R_TFILA][24] = ""+getBaseContext().getFilesDir() + "/r_tfila_24.html";
		chaptersFiles[R_TFILA][25] = ""+getBaseContext().getFilesDir() + "/r_tfila_25.html";
		chaptersFiles[R_TFILA][26] = ""+getBaseContext().getFilesDir() + "/r_tfila_26.html";

		/*R_zmanim*/
		chaptersFiles[R_ZMANIM][0] = ""+getBaseContext().getFilesDir() + "/r_zmanim_tochen.html";
		chaptersFiles[R_ZMANIM][1] = ""+getBaseContext().getFilesDir() + "/r_zmanim_1.html";
		chaptersFiles[R_ZMANIM][2] = ""+getBaseContext().getFilesDir() + "/r_zmanim_2.html";
		chaptersFiles[R_ZMANIM][3] = ""+getBaseContext().getFilesDir() + "/r_zmanim_3.html";
		chaptersFiles[R_ZMANIM][4] = ""+getBaseContext().getFilesDir() + "/r_zmanim_4.html";
		chaptersFiles[R_ZMANIM][5] = ""+getBaseContext().getFilesDir() + "/r_zmanim_5.html";
		chaptersFiles[R_ZMANIM][6] = ""+getBaseContext().getFilesDir() + "/r_zmanim_6.html";
		chaptersFiles[R_ZMANIM][7] = ""+getBaseContext().getFilesDir() + "/r_zmanim_7.html";
		chaptersFiles[R_ZMANIM][8] = ""+getBaseContext().getFilesDir() + "/r_zmanim_8.html";
		chaptersFiles[R_ZMANIM][9] = ""+getBaseContext().getFilesDir() + "/r_zmanim_9.html";
		chaptersFiles[R_ZMANIM][10] = ""+getBaseContext().getFilesDir() + "/r_zmanim_10.html";
		chaptersFiles[R_ZMANIM][11] = ""+getBaseContext().getFilesDir() + "/r_zmanim_11.html";
		chaptersFiles[R_ZMANIM][12] = ""+getBaseContext().getFilesDir() + "/r_zmanim_12.html";
		chaptersFiles[R_ZMANIM][13] = ""+getBaseContext().getFilesDir() + "/r_zmanim_13.html";
		chaptersFiles[R_ZMANIM][14] = ""+getBaseContext().getFilesDir() + "/r_zmanim_14.html";
		chaptersFiles[R_ZMANIM][15] = ""+getBaseContext().getFilesDir() + "/r_zmanim_15.html";
		chaptersFiles[R_ZMANIM][16] = ""+getBaseContext().getFilesDir() + "/r_zmanim_16.html";
		chaptersFiles[R_ZMANIM][17] = ""+getBaseContext().getFilesDir() + "/r_zmanim_17.html";
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
		PopupMenu popupMenu = new PopupMenu(textMain.this, v);

		String configHeaders[] = new String[7];
		if(MyLanguage == ENGLISH) {
			configHeaders[0] = "Settings";
			configHeaders[1] = "About";
			configHeaders[2] = "Feedback";
			configHeaders[3] = "Explanation of search results";
			configHeaders[4] = "Acronyms";
			configHeaders[5] = "Zoom in";
			configHeaders[6] = "Zoom out";
		}
		else if(MyLanguage == RUSSIAN) {
			configHeaders[0] = "Настройки";
			configHeaders[1] = "Около";
			configHeaders[2] = "Обратная связь";
			configHeaders[3] = "Объяснение результатов поиска";
			configHeaders[4] = "Абревиатуры";
			configHeaders[5] = "Увеличить шрифт";
			configHeaders[6] = "Уменьшить шрифт";
		}
		else if(MyLanguage == SPANISH) {
			configHeaders[0] = "Ajustes";
			configHeaders[1] = "Acerca de";
			configHeaders[2] = "Comentarios";
			configHeaders[3] = "Explicacion del resultado de la busqueda";
			configHeaders[4] = "Acronimos";
			configHeaders[5] = "Aumentar enfoque";
			configHeaders[6] = "Disminuir enfoque";
		}
		else if(MyLanguage == FRENCH) {
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
				webSettings.setMinimumFontSize(mPrefs.getInt("fontSize",20));
				switch (item.getItemId())
				{
					case 0:/*settings*/
						try
						{
							Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Settings");
							Intent ourIntent = new Intent(textMain.this, ourClass);
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
							Intent ourIntent = new Intent(textMain.this, ourClass);
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
							Intent ourIntent = new Intent(textMain.this, ourClass);
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
							Intent ourIntent = new Intent(textMain.this, ourClass);
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

						break;
					case 6:/*decrease text*/

						break;
					default:
						break;
				}
				return true;
			}
		});

		popupMenu.show();
	}//showPopupMenuSettings

	class MyWebViewClient extends WebViewClient
	{
		@SuppressLint("NewApi")
		@Override
		public void onPageFinished(WebView view, String url)
		{
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			String strToastSearch;
			WebSettings webSettings = webview.getSettings();
			webSettings.setMinimumFontSize(mPrefs.getInt("fontSize",20));

			if(firstTime == true || ChangeChapter == true)
			{
				firstTime = false;
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
					webview.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
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
		if(searchPosition.charAt(19)=='0'||searchPosition.charAt(18)=='0')
		{
			loadWebview((searchPosition.split(":")[0]),webview);
			isNotHeb=false;
		}
		int length = searchPosition.lastIndexOf("#");
		if (length == -1)/*it means that all the results are in notes*/ {

			length = searchPosition.lastIndexOf(":");
			noteStr = searchPosition.substring(length + 1, searchPosition.length());
			searchPosition = searchPosition.substring(0, length);

			bookAndChapter = searchPosition;
		} else {
			length = searchPosition.lastIndexOf("#");
			bookAndChapter = searchPosition.substring(0, length);
		}

		book_chapter = new int[2];
		for (int i = 0; i <= BOOKS_HEB_NUMBER; i++)
			for (int j = 1; j <= lastChapter[i]; j++)
				if (bookAndChapter.equals(chaptersFiles[i][j])) {
					book_chapter[0] = i;
					book_chapter[1] = j;
					return;
				}
	}

	public String convertAnchorIdToSection(int Id)
	{
		if (MyLanguage!=HEBREW)
			return String.valueOf(Id);
		if(book_chapter[0] == KASHRUT_B && Id != 0)//for KASHRUT_B start from chapter 20
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
			case BRACHOT:
				return "ברכות";
			case HAAMVEHAAREZ:
				return "העם והא'";
			case ZMANIM:
				return "זמנים";
			case TAHARAT:
				return "טהרת המש'";
			case YAMIM:
				return "ימים נוראים";
			case KASHRUT_A:
				return "כשרות א";
			case KASHRUT_B:
				return "כשרות ב";
			case LIKUTIM_A:
				return "ליקוטים א";
			case LIKUTIM_B:
				return "ליקוטים ב";
			case MISHPACHA:
				return "משפחה";
			case MOADIM:
				return "מועדים";
			case SUCOT:
				return "סוכות";
			case PESACH:
				return "פסח";
			case SHVIIT:
				return "שביעית";
			case SHABAT:
				return "שבת";
			case SIMCHAT:
				return "שמחת הבית";
			case TEFILA:
				return "תפילה";
			case TEFILAT_NASHIM:
				return "תפילת נש'";
			case HAR_MOADIM:
				return "הר' מועדים";
			case HAR_SUCOT:
				return "הר' סוכות";
			case HAR_SHABAT:
				return "הר' שבת";
			case HAR_SIMCHAT:
				return "הר' שמחת הבית";
			case HAR_YAMIM:
				return "הר' ימים נוראים";
			case HAR_BRACHOT:
				return "הר' ברכות";
			case E_TEFILA:
				return "Tefila(en)";
			case E_PESACH:
				return "Pesach(en)";
			case E_ZMANIM:
				return "Zmanim(en)";
			case E_WOMEN_PRAYER:
				return "Women’s Prayer(en)";
			case E_SHABAT:
				return "Shabbat(en)";
			case E_YAMMIM:
				return "yammim(en)";
			case E_MOADIM:
				return "moadim(en)";
			case E_SIMCHAT:
				return "simchat habait(en)";
			case F_TEFILA:
				return "La prière d’Israël(fr)";
			case F_MOADIM:
				return "moadim(fr)";
			case F_SUCOT:
				return "sucot(fr)";
			case F_ZMANIM:
				return "zmanim(fr)";
			case F_SIMCHAT:
				return "simchat habait(fr)";
			case F_PESACH:
				return "pesach(fr)";
			case F_SHABBAT:
				return "shabbat(fr)";
			case F_TFILAT_NASHIM:
				return "tfilat nashim(fr)";
			case F_YAMMIM:
				return "yammim noraiim(fr)";
			case S_SHABAT:
				return "Shabbat (Es)";
			case S_BRACHOT:
				return "Brachot (Es)";
			case S_MOADIM:
				return "Moadim (Es)";
			case S_YAMIM:
				return "Yamim noriim(Es)";
			case S_PESACH:
				return "Pesach (Es)";
			case S_SIMCHAT:
				return "Simchat habit (Es)";
			case S_TFILA:
				return "Tfila (Es)";
			case S_TFILAT_NASHIM:
				return "Tfilat nashim (Es)";
			case S_ZMANIM:
				return "Zmanim (Es)";
			case R_HAAM:
				return "haam vehaarez(ru)";
			case R_SHABBAT:
				return "shabbat(ru)";
			case R_YAMMIM:
				return "yamim noriim(ru)";
			case R_SUCOT:
				return "sucot(ru)";
			case R_SIMCHAT:
				return "simchat habiit(ru)";
			case R_MISHPHACHA:
				return "mishpacha(ru)";
			case R_PESACH:
				return "pesach(ru)";
			case R_MOADIM:
				return "moadim(ru)";
			case R_TEFILAT_NASHIM:
				return "tfilat nashim(ru)";
			case R_TFILA:
				return "tfila(ru)";
			case R_ZMANIM:
				return "zmanim(ru)";
			default:
				return "לא ידוע";
		}
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
		chaptersNames[KASHRUT_A][10] ="כשרות א: י - סדר ההפרשה למעשה";
		chaptersNames[KASHRUT_A][11] ="כשרות א: יא - חלה";
		chaptersNames[KASHRUT_A][12] ="כשרות א: יב - מצוות התלויות בארץ";
		chaptersNames[KASHRUT_A][13] ="כשרות א: יג - עצי פרי ובל תשחית";
		chaptersNames[KASHRUT_A][14] ="כשרות א: יד - אכילת בשר";
		chaptersNames[KASHRUT_A][15] ="כשרות א: טו - צער בעלי חיים";
		chaptersNames[KASHRUT_A][16] ="כשרות א: טז - שילוח הקן";
		chaptersNames[KASHRUT_A][17] ="כשרות א: יז - כשרות בעלי חיים";
		chaptersNames[KASHRUT_A][18] ="כשרות א: יח - הלכות שחיטה";
		chaptersNames[KASHRUT_A][19] ="כשרות א: יט - מתנות כהונה מהחי";
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
		chaptersNames[KASHRUT_B][10] ="כשרות ב: כט - יין ומשקאות גויים";
		chaptersNames[KASHRUT_B][11] ="כשרות ב: ל - חלב ומוצריו";
		chaptersNames[KASHRUT_B][12] ="כשרות ב: לא - טבילת כלים";
		chaptersNames[KASHRUT_B][13] ="כשרות ב: לב - כללי הכשרת כלים";
		chaptersNames[KASHRUT_B][14] ="כשרות ב: לג - הכשרת כלים ומטבח";
		chaptersNames[KASHRUT_B][15] ="כשרות ב: לד - דיני תערובות";
		chaptersNames[KASHRUT_B][16] ="כשרות ב: לה - סוגי בליעות";
		chaptersNames[KASHRUT_B][17] ="כשרות ב: לו - סכנות";
		chaptersNames[KASHRUT_B][18] ="כשרות ב: לז - תעשיית המזון";
		chaptersNames[KASHRUT_B][19] ="כשרות ב: לח - נאמנות והשגחה";
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
		chaptersNames[MISHPACHA][10] ="משפחה: י - אבלות";
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
		chaptersNames[TEFILA][9]  = "תפילה: ט - ברכות השחר";
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
		chaptersNames[TEFILAT_NASHIM][9]  = "תפילת נשים: ט - הכנת הגוף";
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
		chaptersNames[HAR_MOADIM][1]  = "הר' מועדים: א - פתיחה";
		chaptersNames[HAR_MOADIM][2]  = "הר' מועדים: ב - דיני עשה ביום טוב";
		chaptersNames[HAR_MOADIM][3]  = "הר' מועדים: ג - כללי המלאכות";
		chaptersNames[HAR_MOADIM][4]  = "הר' מועדים: ד - מלאכות המאכלים";
		chaptersNames[HAR_MOADIM][5]  = "הר' מועדים: ה - הבערה כיבוי וחשמל";
		chaptersNames[HAR_MOADIM][6]  = "הר' מועדים: ו - הוצאה ומוקצה";
		chaptersNames[HAR_MOADIM][7]  = "הר' מועדים: ז - מדיני יום טוב";
		chaptersNames[HAR_MOADIM][8]  = "הר' מועדים: ח - עירוב תבשילין";
		chaptersNames[HAR_MOADIM][9]  = "הר' מועדים: ט - יום טוב שני של גלויות";
		chaptersNames[HAR_MOADIM][10] = "הר' מועדים: י - מצוות חול המועד";
		chaptersNames[HAR_MOADIM][11] = "הר' מועדים: יא - מלאכת חול המועד";
		chaptersNames[HAR_MOADIM][12] = "הר' מועדים: יב - היתרי עבודה במועד";
		/*HAR_SUCOT*/
		chaptersNames[HAR_SUCOT][1]  = "א -חג הסוכות";
		chaptersNames[HAR_SUCOT][2]  = "ב - הלכות סוכה";
		chaptersNames[HAR_SUCOT][3]  = "ג - ישיבה בסוכה";
		chaptersNames[HAR_SUCOT][4]  = "ד - ארבעת המינים";
		chaptersNames[HAR_SUCOT][5]  = "ה - נטילת לולב";
		chaptersNames[HAR_SUCOT][6]  = "ו - הושענא רבה";
		chaptersNames[HAR_SUCOT][7]  = "ז - שמיני עצרת";
		chaptersNames[HAR_SUCOT][8]  = "ח - הקהל";
		/*HAR_SHABAT*/
		chaptersNames[HAR_SHABAT][1]  = "הר' שבת: א - פתיחה";
		chaptersNames[HAR_SHABAT][2]  = "הר' שבת: ב - הכנות לשבת";
		chaptersNames[HAR_SHABAT][3]  = "הר' שבת: ג - זמני השבת";
		chaptersNames[HAR_SHABAT][4]  = "הר' שבת: ד - הדלקת נרות שבת";
		chaptersNames[HAR_SHABAT][5]  = "הר' שבת: ה - תורה ותפילה בשבת";
		chaptersNames[HAR_SHABAT][6]  = "הר' שבת: ו - הלכות קידוש";
		chaptersNames[HAR_SHABAT][7]  = "הר' שבת: ז - סעודות השבת ומלווה מלכה";
		chaptersNames[HAR_SHABAT][8]  = "הר' שבת: ח - הבדלה ומוצאי שבת";
		chaptersNames[HAR_SHABAT][9]  = "הר' שבת: ט - כללי המלאכות";
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
		chaptersNames[HAR_SIMCHAT][1]  = "הר' שמחת: א - מצוות עונה";
		chaptersNames[HAR_SIMCHAT][2]  = "הר' שמחת: ב - הלכות עונה";
		chaptersNames[HAR_SIMCHAT][3]  = "הר' שמחת: ג - קדושה וכוונה";
		chaptersNames[HAR_SIMCHAT][4]  = "הר' שמחת: ד - שמירת הברית";
		chaptersNames[HAR_SIMCHAT][5]  = "הר' שמחת: ה - פרו ורבו";
		chaptersNames[HAR_SIMCHAT][6]  = "הר' שמחת: ו - קשיים ועקרות";
		chaptersNames[HAR_SIMCHAT][7]  = "הר' שמחת: ז - סריס והשחתה";
		chaptersNames[HAR_SIMCHAT][8]  = "הר' שמחת: ח - נחמת חשוכי ילדים";
		chaptersNames[HAR_SIMCHAT][9]  = "הר' שמחת: ט - הפסקת הריון";
		chaptersNames[HAR_SIMCHAT][10] = "הר' שמחת: י - האיש והאשה";
		/*HAR_BRACHOT*/
		chaptersNames[HAR_BRACHOT][1]  = "הר' ברכות: א - פתיחה";
		chaptersNames[HAR_BRACHOT][2]  = "הר' ברכות: ב - נטילת ידיים לסעודה";
		chaptersNames[HAR_BRACHOT][3]  = "הר' ברכות: ג - ברכת המוציא";
		chaptersNames[HAR_BRACHOT][4]  = "הר' ברכות: ד - ברכת המזון";
		chaptersNames[HAR_BRACHOT][5]  = "הר' ברכות: ה - זימון";
		chaptersNames[HAR_BRACHOT][6]  = "הר' ברכות: ו - חמשת מיני דגן";
		chaptersNames[HAR_BRACHOT][7]  = "הר' ברכות: ז - ברכת היין";
		chaptersNames[HAR_BRACHOT][8]  = "הר' ברכות: ח - ברכת הפירות ושהכל";
		chaptersNames[HAR_BRACHOT][9]  = "הר' ברכות: ט - כללי ברכה ראשונה";
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

		chaptersNames[R_HAAM][1]="Глава 1. Величие Земли Израиля";
		chaptersNames[R_HAAM][2]="Глава 2. Святое и будничное в заселении Земли Израиля";
		chaptersNames[R_HAAM][3]="Глава 3. Заповедь заселения Земли Израиля";
		chaptersNames[R_HAAM][4]="Глава 4. Законы, связанные с воинской службой и ведением войны";
		chaptersNames[R_HAAM][5]="Глава 5. Сохранение Земли Израиля";
		chaptersNames[R_HAAM][6]="Глава 6. Некоторые законы, связанные с еврейским государством";
		chaptersNames[R_HAAM][7]="Глава 7. Взаимная ответственность евреев друг за друга";
		chaptersNames[R_HAAM][8]="Глава 8. Еврейский труд";
		chaptersNames[R_HAAM][9]="Глава 9. Память о Храме";
		chaptersNames[R_HAAM][10]="Глава 10. Законы, связанные с переходом в иудаизм и приобщением к еврейскому народу";


		chaptersNames[R_SHABBAT][1]="Глава 1. Введение ";
		chaptersNames[R_SHABBAT][2]="Глава 2. Приготовления к субботе";
		chaptersNames[R_SHABBAT][3]="Глава 3. Время наступления субботы";
		chaptersNames[R_SHABBAT][4]="Глава 4. Зажигание субботних свечей";
		chaptersNames[R_SHABBAT][5]="Глава 5. Изучение Торы и молитва в субботу";
		chaptersNames[R_SHABBAT][6]="Глава 6. Законы кидуша";
		chaptersNames[R_SHABBAT][7]="Глава 7. Субботние трапезы и «проводы субботы» (мелаве малка)";
		chaptersNames[R_SHABBAT][8]="Глава 8. Ѓавдала и исход субботы";
		chaptersNames[R_SHABBAT][9]="Глава 9. Работы, запрещенные в субботу (мелахот): общие поло-жения";
		chaptersNames[R_SHABBAT][10]="Глава 10. Приготовление пищи в субботу";
		chaptersNames[R_SHABBAT][11]="Глава 11. Запрет отбора в субботу (борер)";
		chaptersNames[R_SHABBAT][12]="Глава 12. Приготовление различных блюд в субботу";
		chaptersNames[R_SHABBAT][13]="Глава 13. Работы (мелахот), связанные с одеждой";
		chaptersNames[R_SHABBAT][14]="Глава 14. Уход за телом";
		chaptersNames[R_SHABBAT][15]="Глава 15. Запреты строительства (боне) и разрушения постро енного (сотер) применительно к дому и предметам домашнего обихода";
		chaptersNames[R_SHABBAT][16]="Глава 16. Зажигание и тушение огня";
		chaptersNames[R_SHABBAT][17]="Глава 17. Электричество и электроприборы";
		chaptersNames[R_SHABBAT][18]="Глава 18. Запреты письма (котев), стирания написанного (мохек) и окрашивания (цовеа)";
		chaptersNames[R_SHABBAT][19]="Глава 19. Работы (мелахот), связанные с растениями";
		chaptersNames[R_SHABBAT][20]="Глава 20. Работы (мелахот), связанные с животными";
		chaptersNames[R_SHABBAT][21]="Глава 21. Законы перенесения предметов из одного владения в другое";
		chaptersNames[R_SHABBAT][22]="Глава 22. Субботняя атмосфера";
		chaptersNames[R_SHABBAT][23]="Глава 23. Запрет мукце";
		chaptersNames[R_SHABBAT][24]="Глава 24. Законы, связанные с детьми";
		chaptersNames[R_SHABBAT][25]="Глава 25. Работы (мелахот), выполняемые неевреем";
		chaptersNames[R_SHABBAT][26]="Глава 26. Работа (мелаха), выполненная в субботу, и запрет «пред слепым не клади преткновения»";
		chaptersNames[R_SHABBAT][27]="Глава 27. Спасение жизни (пикуах нефеш); законы в отношении больного";
		chaptersNames[R_SHABBAT][28]="Глава 28. Законы в отношении человека, больного неопасной бо- лезнью";
		chaptersNames[R_SHABBAT][29]="Глава 29. Законы эрува";
		chaptersNames[R_SHABBAT][30]="Глава 30. Субботний предел";


		chaptersNames[R_YAMMIM][1]="Глава 1  Высший суд, награда и наказание  ";
		chaptersNames[R_YAMMIM][2]="Глава 2  Слихот (молитвы, призывающие к раскаянию) ";
		chaptersNames[R_YAMMIM][3]="Глава 3  Рош ѓа-Шана   ";
		chaptersNames[R_YAMMIM][4]="Глава 4  Заповедь трубления в шофар";
		chaptersNames[R_YAMMIM][5]="Глава 5  Десять дней покаяния";
		chaptersNames[R_YAMMIM][6]="Глава 6  Йом Кипур";
		chaptersNames[R_YAMMIM][7]="Глава 7  Законы Йом Кипура  ";
		chaptersNames[R_YAMMIM][8]="Глава 8  Законы поста в Йом Кипур";
		chaptersNames[R_YAMMIM][9]="Глава 9  Остальные запреты Йом Кипура";
		chaptersNames[R_YAMMIM][10]="Глава 10  Храмовое служение в Йом Кипур";

		chaptersNames[R_SUCOT][1]="Глава 1  Праздник Суккот";
		chaptersNames[R_SUCOT][2]="Глава 2  Законы сукки";
		chaptersNames[R_SUCOT][3]="Глава 3  Заповедь жить в сукке    ";
		chaptersNames[R_SUCOT][4]="Глава 4  Четыре вида растений (арбаат ѓа-миним)";
		chaptersNames[R_SUCOT][5]="Глава 5  Заповедь лулава (нетилат лулав";
		chaptersNames[R_SUCOT][6]="Глава 6  Ѓошана раба – седьмой день праздника Суккот";
		chaptersNames[R_SUCOT][7]="Глава 7  Шмини Ацерет";
		chaptersNames[R_SUCOT][8]="Глава 8  Ѓакѓель";

		chaptersNames[R_SIMCHAT][1]="Глава 1. Заповедь супружеской близости (мицват она)";
		chaptersNames[R_SIMCHAT][1]="Глава 2. Законы исполнения заповеди супружеской близости";
		chaptersNames[R_SIMCHAT][1]="Глава 3. Святость и душевный настрой (кавана) при исполнении заповеди супружеской близости";
		chaptersNames[R_SIMCHAT][1]="Глава 4. Соблюдение Священного завета";
		chaptersNames[R_SIMCHAT][1]="Глава 5. Заповедь «плодитесь и размножайтесь» ";
		chaptersNames[R_SIMCHAT][1]="Глава 6. Различные трудности и бесплодие";
		chaptersNames[R_SIMCHAT][1]="Глава 7. Проблемы мужского бесплодия: увечье,кастрация и стерилизация";
		chaptersNames[R_SIMCHAT][1]="Глава 8. Слова поддержки и утешения бездетным парам";
		chaptersNames[R_SIMCHAT][1]="Глава 9. Прерывание беременности ";
		chaptersNames[R_SIMCHAT][1]="Глава 10. Мужчина и женщина";

		chaptersNames[R_MISHPHACHA][1]="Глава 1. Заповедь почтения к родителям ";
		chaptersNames[R_MISHPHACHA][2]="Глава 2. Заповедь вступления в брак";
		chaptersNames[R_MISHPHACHA][3]="Глава 3. Сватовство";
		chaptersNames[R_MISHPHACHA][4]="Глава 4. Кидушин и ктуба";
		chaptersNames[R_MISHPHACHA][5]="Глава 5. Свадебные законы и обычаи";
		chaptersNames[R_MISHPHACHA][6]="Глава 6. Запрещенные интимные связи";
		chaptersNames[R_MISHPHACHA][7]="Глава 7. Законы скромности";
		chaptersNames[R_MISHPHACHA][8]="Глава 8. Заповедь обрезания (Брит мила)";
		chaptersNames[R_MISHPHACHA][9]="Глава 9. Выкуп первенцев";
		chaptersNames[R_MISHPHACHA][10]="Глава 10. Законы траура";

		chaptersNames[R_PESACH][1]="Глава 1. Смысл праздника Песах";
		chaptersNames[R_PESACH][2]="Глава 2. Правила, связанные с запретом на квасное (хамец)";
		chaptersNames[R_PESACH][3]="Глава 3. Заповедь устранения квасного (биур хамец)";
		chaptersNames[R_PESACH][4]="Глава 4. Проверка владений еврея на наличие квасного (бдикат хамец)";
		chaptersNames[R_PESACH][5]="Глава 5. Аннулирование квасного (битуль хамец) и его уничтожение";
		chaptersNames[R_PESACH][6]="Глава 6. Продажа квасного";
		chaptersNames[R_PESACH][7]="Глава 7. Смесь квасного с пищей, кошерной на Песах (тааровет хамец)";
		chaptersNames[R_PESACH][8]="Глава 8. Некоторые законы кашрута на Песах.";
		chaptersNames[R_PESACH][9]="Глава 9. Обычай запрета китнийот в Песах";
		chaptersNames[R_PESACH][10]="Глава 10. Общие правила кошерования посуды к Песаху";
		chaptersNames[R_PESACH][11]="Глава 11. Кошерование кухни к Песаху";
		chaptersNames[R_PESACH][12]="Глава 12. Законы, связанные с мацой";
		chaptersNames[R_PESACH][13]="Глава 13. Законы и обычаи кануна Песаха";
		chaptersNames[R_PESACH][14]="Глава 14. Если канун Песаха выпадает на субботу";
		chaptersNames[R_PESACH][15]="Глава 15. Пасхальная Агада";
		chaptersNames[R_PESACH][16]="Глава 16. Пасхальный Седер";

		chaptersNames[R_MOADIM][1]="Глава 1. Вступление";
		chaptersNames[R_MOADIM][2]="Глава 2. Заповеди-предписания (мицвот асэ),действующие в йом тов";
		chaptersNames[R_MOADIM][3]="Глава 3. Запрет выполнения созидательной работы (мелаха) в йом тов";
		chaptersNames[R_MOADIM][4]="Глава 4. Виды созидательной работы (мелаха),связанной с приготовлением пищи";
		chaptersNames[R_MOADIM][5]="Глава 5. Зажигание и тушение огня; использование электричества в йом тов";
		chaptersNames[R_MOADIM][6]="Глава 6. Перенесение предметов из одного владения в другое; закон мукце";
		chaptersNames[R_MOADIM][7]="ГГлава 7. Некоторые законы йом това";
		chaptersNames[R_MOADIM][8]="Глава 8. Эрув тавшилин";
		chaptersNames[R_MOADIM][9]="Глава 9. Второй йом тов за пределами Земли Израиля";
		chaptersNames[R_MOADIM][10]="Глава 10. Заповеди холь ѓа-моэда";
		chaptersNames[R_MOADIM][11]="Глава 11. Созидательная работа (мелаха) в холь ѓа-моэд";
		chaptersNames[R_MOADIM][12]="Глава 12. Какая работа разрешена в холь ѓа-моэд";
		chaptersNames[R_MOADIM][13]="Глава 13 Шавуот";

		chaptersNames[R_TEFILAT_NASHIM][1]="Глава 1  Основы законов молитвы ";
		chaptersNames[R_TEFILAT_NASHIM][2]="Глава 2  Заповедь молитвы для женщин";
		chaptersNames[R_TEFILAT_NASHIM][3]="Глава 3  Смысл заповедей, обязательных для женщин";
		chaptersNames[R_TEFILAT_NASHIM][4]="Глава 4  Утреннее пробуждение";
		chaptersNames[R_TEFILAT_NASHIM][5]="Глава 5  Утреннее омовение рук";
		chaptersNames[R_TEFILAT_NASHIM][6]="ГГлава 6  Утренние благословения (Биркот ѓа-шахар) ";
		chaptersNames[R_TEFILAT_NASHIM][7]="Глава 7  Благословения Торы (Биркот ѓа-Тора) ";
		chaptersNames[R_TEFILAT_NASHIM][8]="Глава 8  Утренняя молитвенная служба (шахарит) и законы, связанные с подготовкой к ней";
		chaptersNames[R_TEFILAT_NASHIM][9]="Глава 9  Телесная подготовка к молитве ";
		chaptersNames[R_TEFILAT_NASHIM][10]="Глава 10  Подготовка души к молитве; одежда, подобающая молитве  ";
		chaptersNames[R_TEFILAT_NASHIM][11]="Глава 11  Место молитвы ";
		chaptersNames[R_TEFILAT_NASHIM][12]="Глава 12  Молитва амида ";
		chaptersNames[R_TEFILAT_NASHIM][13]="Глава 13  Ошибки при чтении молитвы амида ";
		chaptersNames[R_TEFILAT_NASHIM][14]="Глава 14  Почтительное отношение к молитве ";
		chaptersNames[R_TEFILAT_NASHIM][15]="Глава 15  Отрывки о жертвоприношениях (Корбанот) и «Хвалебные гимны» (Псукей де-зимра) ";
		chaptersNames[R_TEFILAT_NASHIM][16]="Глава 16  Шма Исраэль и сопутствующие благословения ";
		chaptersNames[R_TEFILAT_NASHIM][17]="Глава 17  Молитвы, читаемые после амиды";
		chaptersNames[R_TEFILAT_NASHIM][18]="Глава 18  Послеполуденная и вечерняя молитвы (минха и арвит) ";
		chaptersNames[R_TEFILAT_NASHIM][19]="Глава 19  Чтение Шма Исраэль перед отходом ко сну ";
		chaptersNames[R_TEFILAT_NASHIM][20]="Глава 20  Отдельные законы молитвы в миньяне ";
		chaptersNames[R_TEFILAT_NASHIM][21]="Глава 21  Отдельные законы, связанные с синагогой ";
		chaptersNames[R_TEFILAT_NASHIM][22]="Глава 22  Молитва и кидуш в субботу";
		chaptersNames[R_TEFILAT_NASHIM][23]="Глава 23  Отдельные законы праздников и памятных дней";
		chaptersNames[R_TEFILAT_NASHIM][24]="Глава 24  Молитвенные каноны и обычаи разных общин ";

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

		chaptersNames[R_ZMANIM][1]="Глава 1. Новомесячье (рош ходеш)";
		chaptersNames[R_ZMANIM][2]="Глава 2. Законы отсчета омера (сфират ѓа-омер)";
		chaptersNames[R_ZMANIM][3]="Глава 3. Траурные обычаи в период отсчета омера";
		chaptersNames[R_ZMANIM][4]="Глава 4. День независимости Израиля, День освобождения Иерусалима и дни памяти";
		chaptersNames[R_ZMANIM][5]="Глава 5. Лаг ба-омер";
		chaptersNames[R_ZMANIM][6]="Глава 6. Четыре поста в память о разрушении Храма";
		chaptersNames[R_ZMANIM][7]="Глава 7. Законы малых постов";
		chaptersNames[R_ZMANIM][8]="Глава 8. Обычаи трех траурных недель бейн ѓа-мецарим";
		chaptersNames[R_ZMANIM][9]="Глава 9. Канун поста 9 ава";
		chaptersNames[R_ZMANIM][10]="Глава 10. Законы поста 9 ава";
		chaptersNames[R_ZMANIM][11]="Глава 11. Дни Хануки";
		chaptersNames[R_ZMANIM][12]="Глава 12. Зажигание ханукальных свечей";
		chaptersNames[R_ZMANIM][13]="Глава 13. Законы места и времени";
		chaptersNames[R_ZMANIM][14]="Глава 14. Месяц адар";
		chaptersNames[R_ZMANIM][15]="Глава 15. Пурим и чтение Свитка Эстер";
		chaptersNames[R_ZMANIM][16]="Глава 16. Заповеди радости и милосердия";
		chaptersNames[R_ZMANIM][17]="Глава 17. Законы городов, обнесенных и не обнесенных стеной";
	}

	void innerSearch()
	{
		final Context context = this;
		// custom dialog
		innerSearchDialog = new Dialog(context);
		innerSearchDialog.setContentView(R.layout.inner_search);
		innerSearchDialog.setTitle("חיפוש בפרק הנוכחי");
		Window window = innerSearchDialog.getWindow();
		window.setGravity(Gravity.TOP);
		ImageView dialogButton =  innerSearchDialog.findViewById(R.id.goSearch);
		TextToSearch = (EditText) innerSearchDialog.findViewById(R.id.title );
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
								lnrFindOptions.setVisibility(View.VISIBLE);
								lnrFindOptions.setTag("vis");
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
		ImageView clearBtn = innerSearchDialog.findViewById(R.id.clear);

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
				lnrFindOptions.setTag("vis");
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
		clearBtn.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				TextToSearch.setText("");
			}
		});
		innerSearchDialog.show();
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
		autoScrollDialog.setTitle("מהירות גלילה אוטומטית");

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
		//scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
		//spinnerAutoScroll.setSelection((scrollSpeed / 2) - 1);
		spinnerAutoScroll.setOnItemSelectedListener(new OnItemSelectedListener() {
			boolean first = true;

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				//scrollSpeed = (pos + 1) * 2;
				//shPrefEditor.putInt("scrollSpeed", scrollSpeed);
				shPrefEditor.commit();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		autoScrollDialog.show();

	}
}