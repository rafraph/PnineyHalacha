package com.rafraph.pnineyHalachaHashalem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;

import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;


public class SearchHelp extends Activity {

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
	private static final int BOOKS_NUMBER	= 24;
	public static int totalCount=0;
	public static int totalCount2=0;
	public int MyLanguage;
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
	public static int LangWrite;
	public static  String[][] fBook={{"f_moadim","f_peasach","f_shabbat","f_simchat","f_sucot","f_tefila","f_tfilat_nashim","f_yammim","f_zmanim"},{"13","16","30","10","8","26","24","10","17"}};
	public static  String[][] rBook={{"r_moadim","r_pesach","r_shabbat","r_simchat","r_sucot","r_tfila","r_tfilat_nashim","r_yammim","r_zmanim","r_haamvehaarez","r_misphacha"},{"13","16","30","10","8","26","24","10","17","10","10"}};
	public static  String[][] enBook={{"e_moadim","e_pesach","e_shabbat","e_simchat","e_tefila","e_w_prayer","e_yammim","r_zmanim"},{"13","16","30","10","26","24","10","15"}};
	public static  String[][] esBook={{"s_moadim","s_pesach","s_shabbat","s_simchat","s_tfila","s_tfilat_nashim","s_yammim","s_zmanim","s_brachot"},{"13","16","30","10","26","24","10","17","18"}};

	public static boolean onceLast=false;
	public String[] querys=new String[2000000];
	public static Stack<File> stcakFiles=new Stack<>();
	public static String pleaseWait="please wait";

	/*							0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23*/
	public int[] lastChapter = {18, 11, 17, 10, 10, 19, 19, 13, 16, 13, 10, 8, 16, 11, 30, 10, 26, 24, 17, 10, 12, 8, 30, 10};

	String[][] chaptersFiles = new String[BOOKS_NUMBER][31];
	String[][] chaptersNames = new String[BOOKS_NUMBER][31];
	public List<String> listBookLocation = new ArrayList<String>();
	public List<String> listStrAnchor = new ArrayList<String>();
	public List<String> listStrName = new ArrayList<String>();
	public ListView searchListView = null;
	public ListView searchListView2 = null;
	public String query;

	public TextView bookFound;
	public String queryToSearch;
	public String sectionsForToast = null;
	public int i = 0;
	boolean searchFound=false;
	public String hebCharacter = "אבגדהוזחטיכלמנסעפצקרשתםןץףך -'\"";
	public boolean validQuery = false;
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
	//public static List<HashMap<String,List<String>>> lst1=new List<HashMap<String,List<String>>>();

	@Override
	public void onBackPressed() {
		try
		{
			Class ourClass = null;
			Intent ourIntent;
			ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
			ourIntent = new Intent(SearchHelp.this, ourClass);
			startActivity(ourIntent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		MyLanguage = mPrefs.getInt("MyLanguage", -1);
		TextView searchNow2=findViewById(R.id.search_now);
		TextView lastSearch2=findViewById(R.id.last_search);
		//TextView head=findViewById(R.id.headr);
		EditText title=findViewById(R.id.title);
		title.setOnEditorActionListener(
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
								TextView txt1 = (TextView) findViewById(R.id.textView7);
								TextView txt2 = (TextView) findViewById(R.id.textView8);
								TextView txt3 = (TextView) findViewById(R.id.textView9);
								TextView txt4 = (TextView) findViewById(R.id.textView10);
								TextView txt5 = (TextView) findViewById(R.id.textView11);
								txt1.setVisibility(View.GONE);
								txt2.setVisibility(View.GONE);
								txt3.setVisibility(View.GONE);
								txt4.setVisibility(View.GONE);
								txt5.setVisibility(View.GONE);
								goSearch();

								return true; // consume.
							}
						}
						return false; // pass on to other listeners.
					}
				}
		);
		if(MyLanguage==ENGLISH){
			searchNow2.setText("Search");
			lastSearch2.setText("Last search");
			//head.setText("Search in \"Peninei Halakha\"");
			title.setHint("Search text");

		}
		if(MyLanguage==RUSSIAN)
		{
			searchNow2.setText("Поиск");
			lastSearch2.setText("Последний поиск");
			//head.setText("Поиск по книгам \"Жемчужины Галахи\"");
			title.setHint("Текст");
		}
		if(MyLanguage==SPANISH)
		{
			searchNow2.setText("Búsqueda");
			lastSearch2.setText("última búsqueda");
			//head.setText("Búsqueda de Pninei Halaja");
			title.setHint("Buscar texto");
		}
		if(MyLanguage==FRENCH)
		{
			searchNow2.setText("Recherche");
			lastSearch2.setText("Dernière recherche ");
			//head.setText("Recherche sur PNINÉ HALAKHA'");
			title.setHint("Mot clef ");
		}
		if(MyLanguage==HEBREW)
			pleaseWait="אנא המתן";
		fillChaptersFiles();
		fillChaptersNames();
		LinearLayout main=(LinearLayout) findViewById(R.id.lnrOptions);
		LinearLayout main2=(LinearLayout) findViewById(R.id.main);
		if (mPrefs.getInt("BlackBackground", 0)==1)
		{
			//main.setBackgroundColor(Color.BLACK);
			main.setBackgroundColor(Color.BLACK);
			main2.setBackgroundColor(Color.BLACK);
			//main3.setBackgroundColor(Color.BLACK);
			//main4.setBackgroundColor(Color.BLACK);
			//main5.setBackgroundColor(Color.BLACK);


		}
		//makeKeys();
		ImageView tooHome= (ImageView) findViewById(R.id.to_main);

		ImageView goSearch= (ImageView) findViewById(R.id.goSearch);
		ImageView getVoice= (ImageView) findViewById(R.id.getVoice);
		TextView searchNow= (TextView) findViewById(R.id.search_now);

		bookFound= (TextView) findViewById(R.id.no_found);
		EditText searchText= (EditText) findViewById(R.id.title);
		TextView lastSearch= (TextView) findViewById(R.id.last_search);
		ImageView toMain= (ImageView) findViewById(R.id.to_main);

		if(MyLanguage==ENGLISH)
			toMain.setImageResource(R.drawable.to_main_e);
		if(MyLanguage==RUSSIAN)
			toMain.setImageResource(R.drawable.to_main_r);
		if(MyLanguage==SPANISH)
			toMain.setImageResource(R.drawable.to_main_s);
		if(MyLanguage==FRENCH)
			toMain.setImageResource(R.drawable.to_main_f);
		searchListView2 = (ListView) findViewById(R.id.list2);
		searchListView2.setVisibility(View.GONE);
		getVoice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				speak();
			}
		});
		toMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{
					Class ourClass = null;
					Intent ourIntent;
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
					ourIntent = new Intent(SearchHelp.this, ourClass);
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
				ContextThemeWrapper ctw = new ContextThemeWrapper(SearchHelp.this, R.style.CustomPopupTheme);
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
									ourIntent = new Intent(SearchHelp.this, ourClass);
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
								ourIntent = new Intent(SearchHelp.this, ourClass);
								ourIntent.putExtra("homePage", true);
								shPrefEditor.putString("where", "SearchHelp");
								shPrefEditor.commit();
								startActivity(ourIntent);
								break;

							case 1:/*to books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(SearchHelp.this, ourClass);
								ourIntent.putExtra("homePage", false);
								startActivity(ourIntent);
								break;

							case 2:/*pninaYomit*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(SearchHelp.this, ourClass);
								startActivity(ourIntent);
								break;

							case 3:/*search in all books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(SearchHelp.this, ourClass);
								startActivity(ourIntent);

								break;

							case 4:/*acronyms*/
								acronymsDecode();
								break;

							case 5:/*feedback*/
								try
								{
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
									ourIntent = new Intent(SearchHelp.this, ourClass);
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
								startActivity(intent);;
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
									ourIntent = new Intent(SearchHelp.this, ourClass);
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
									ourIntent = new Intent(SearchHelp.this, ourClass);
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

		searchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)  {
				//if(searchText.getText().toString().contains("\'")) {
				if (searchText.getText().toString().length() == 1)
					if (hebCharacter.contains(searchText.getText().toString()))
						LangWrite = HEBREW;
					else if ("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя".contains((searchText.getText().toString())))
						LangWrite = RUSSIAN;
					else if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains((searchText.getText().toString())))
						LangWrite = ENGLISH;

				if (searchText.getText().toString().length() > 1 && LangWrite == 0) {
					Trie root = new Trie();
					// Input keys (use only 'a' through 'z' and lower cas
					String keys[] = {"אבוקדו", "אבטיח", "אבקת כביסה", "אגוזים", "אגסים", "אוזני המן", "אוכל בחוץ", "אוכל ואינו מברך", "אוכל", "מזיק", "מקולקל", "אורז", "אורח", "אטריות", "אינפוזיה", "איסור", "אכילה גסה", "אכילת פרס", "אילם", "אמה", "אמן", "אמן", "אננס", "אפונה", "אפטרשייב", "אפרסמון", "אפרסק", "אצבעות", "ארץ ישראל", "אשכוליות", "אשר יצר", "אתרוג", "בוטנים", "בוטנים אמריקאים", "בוסר", "בורא מיני בשמים", "בורא נפשות", "בורא פרי האדמה", "בורא פרי העץ", "בורגול", "בורקס", "בושם סינטטי", "בייגלך ", "ביסלי", "ביסקוויטים", "ביצה", "ביצים", "בית הכסא", "בל תשחית", "בל תשקצו", "בלינצ'ס", "במבה", "בננה", "בצל", "בקבוק", "ברוך הוא וברוך שמו", "ברוש לימוני", "ברז", "בריאות ותזונה", "בריה", "ברכה", "ברכה אחרונה", "ברכה לבטלה", "ברכה ראשונה", "פרטי דינים", "אופן אמירת הברכה", "כללים", "סדר", "קדימה", "עד כמה ברכה פוטרת", "ברכה שאינה צריכה", "ברכות", "כללים", "ברכות ההודאה והשמחה", "שהחיינו והטוב והמטיב", "בית חדש", "הטוב והמטיב", "לידה", "שהחיינו", "שהחיינוחבר", "שהחיינופירות חדשים", "שהחיינובגד חדש", "ברכות הראייה", "פרטי דינים", "בריות יפות", "מלכים", "מקום שנעשו ניסים לאדם", "מקום שנעשו ניסים לישראל", "קברי ישראל", "קשת בענן", "ברכות הריח", "בורא מיני בשמים", "בורא עצי בשמים ועשבי בשמים", "בורא שמן ערב", "הנותן ריח טוב בפירות", "פרטי דינים", "ברכת האילנות", "ברכת הגומל", "פרטי דינים", "הולכי מדבריות", "היוצא מבית האסורים", "חולה שנתרפא", "ברכת הגשמים", "ברכת החמה", "ברכת המזון", "כללים ונוסח", "פרטי דינים", "ברקים", "בשר", "גבינה", "גביע גלידה", "גבעולים", "גבעות", "גדול", "גידולי מים", "גודגדן", "גוי", "גויאבה", "ג'חנון", "גייזר", "גינה", "ג'לי", "גלידה", "גליליות", "גניבה", "גריסים", "גרנולה", "גרעינים", "גשמים", "דאודורנט", "דבק", "דבר שטיבולו במשקה", "דבר תורה", "דברים הבאים מחמת הסעודה", "דבש תמרים", "דגן", "דגנים", "דובדבן ", "דוחן", "דיבורים בסעודה", "דיין האמת", "דלק", "דרך ארץ", "דייסה", "הבדלה", "הגבהת הידיים", "הדס", "הולכי דרכים", "הוצאה ידי חובה", "הטוב והמטיב", "היסח הדעת", "הכנסת אורחים", "הכרת הטוב", "הליכה", "המוציא", "הנאה", "העלאת ניצוצות", "הפסק", "הר געש", "הרהור", "הרים", "השמנת יתר", "וופל", "וורמוט", "זונדה", "זימון", "נשים בזימון, האם חייבות לזמן", "זית", "חבוש", "חביב", "חביתיות", "חולה שתרפא", "חומוס", "חומץ", "חטא אדם הראשון", "חיטה", "חיילים", "חילוני", "חכם הרזים", "חכמים", "חלב", "חלה מתוקה", "חלווה", "חמין", "חמר מדינה", "חנות בשמים", "חצילים", "חציצה", "חרש", "חרש אילם", "טבעת", "טורט", "טחינה", "טיסה", "טלויזיה", "טלפון", "טעות", "טעימה", "טשולנט", "ידיים עסקניות", "יום טוב", "יין", "פרטי דינים", "ברכת הטוב והמטיב", "ים", "יסמין", "יעלה ויבוא", "יערה", "יציאה", "יציאת מצרים", "יקינתון", "ישן", "כבד שמיעה", "כובנה", "כוכב", "כוס", "כוס של ברכה", "כופתאות", "כח גברא", "כלי", "כף היד", "כרוב", "כרוכית", "לאטקעס", "לבבות דקל", "לבוש", "לביבות", "לבן", "לדר", "לוט", "לחם", "לחמניות מתוקות", "ליצ'י", "ליקוי מאורות", "מאה ברכות", "מאכל מאוס או מזיק", "מאפה ממולא", "מאפים מטוגנים", "מארח", "מברך באיסור", "מדבר", "מוסקה", "מזונות", "מחיה המתים", "מחית פירות", "מי גילוח", "מי חלב", "מיל", "מים", "מים אחרונים", "מי פירות", "מיץ פירות", "מיץ ענבים", "מכשיר שמיעה ", "מלוואח", "מלון", "מלח", "מלך", "מלכות בית דוד", "מלפפונים", "ממתקים", "מנגו", "מנדרינה", "מסטיק", "מעין שלוש", "מפל", "מצה", "מצה בריי", "מצווה מדרבנן", "מצות מטוגנות", "מציב גבול אלמנה", "מציות", "מצייה ומה שעליה", "מרציפן", "מרק", "מרק פירות (קומפוט", "משכורת ", "משלשל", "משמש", "משנה הבריות", "משקאות", "משתלה ", "מתן תורה", "נבטים", "נגיעה", "במקום מכוסה", "נהר", "נוסח הברכות", "נח", "נטילת ידיים", "ניגוב הידיים", "נימוסים בסעודה", "ניסים", "נצרך לנקביו", "נקניק", "נרקיס", "נשים", "סבון ריחני", "סברס", "סדר קדימה ", "סויה", "סוכר", "סוכריה", "סופגניות", "סופה", "סעודה", "סעודה שאינה מספקת לבעליה", "ספק", "ספק ברכות להקל", "עבודה זרה", "עגבניות", "עדשים", "עוגה", "עוגיות", "עושה מעשה בראשית", "עושה צרכיו", "עיקר וטפל", "על הניסים", "עלים", "עם הארץ", "ענבים", "עציץ", "ערוה", "עשיית צרכים", "פג'ויה", "פודינג", "פומלה", "פומלית", "פופקורן", "פטריות", "פיגם", "פיל", "פיצה", "פירה", "פירות", "לעניין שהחיינו", "פלאפל", "פלפל חריף", "פלפל ממולא", "פפאיה", "פצע", "פרי אדמה", "פריכיות", "פרינגלס", "פשטידה", "פת הבאה בכיסנים", "פתיתים", "צבר", "צואה", "צום", "ציפורן", "צ'יפס", "קבוקים", "קביעות סעודה", "קברים", "קובה", "קוגל", "קווקר", "קומפוט", "קונדיטון", "קונפיטורה", "קוף", "קוקוס", "קורא", "קורנפלקס", "קטורת ביתית", "קטטר", "קטן", "קטניות", "קיווי", "קינוחים", "קליפה", "קלמנטינה", "קמח", "קמצן", "קנה וושט", "קנה סוכר", "קניידלאך", "קסטה", "קפה", "קציצה", "קרוטונים", "קרטיב", "קרם ידיים", "קרמבו", "קרמבולה", "קרקר", "קשיו", "קשת", "ראייה ", "ראש חודש", "רביעית", "רדיו", "רוזמרין", "ריבה", "ריח", "רימון", "ריסוק", "רמקול", "רסק עגבניות", "רעידת אדמה", "רעם", "רפואה", "רצה", "שבעת המינים", "שהחיינו", "שהכל", "שומשום", "שוקולד", "שזיף", "שטרודל", "שיבא", "שיבולת שועל", "שיירי מאכלים", "שיירי פירורי לחם", "שיכור", "שינה", "שינוי מקום", "שיעורים", "שכוחו וגבורתו מלא עולם", "שככה לו בעולמו", "שכרות", "שלווה", "שלם", "שמן זית", "שניים שאוכלים", "שניצל ", "שסק", "שעורה ", "שקדי מרק", "שקדים", "שרביטי קטניות", "שתוי", "שתייה", "תאנה", "תבלינים", "תה", "תוך כדי דיבור", "תוכי", "תורה", "תות עץ", "תות שדה", "תירס", "תמרים", "תערובת", "תפוזים ", "תפוחים", "תפילה", "תרופה", "תרסיס", "תתרן", "אדום", "אודם", "אוטובוס", "אוכל", "אולטרסאונד", "אוצר השקה", "אוצר זריעה", "איסורי הרחקה", "איפור", "אכילה משותפת", "אלכוהול", "אמבטיה", "אסלה", "אָפור", "אפידורל", "ארבעים סאה", "אשבורן", "בגד צבעוני", "בדיקה", "בדיקה פנימית", "בדיקות רפואיות", "בדיקת מי שפיר", "בדיקת משטח ", "בדיקת פתיחה", "בור מים", "בורדו", "בחירת רופא", "בית הסתרים", "ביתוק בתולים", "בלנית", "בעילת מצווה", "ברד", "ברית מילה", "ברכה", "בתולה", "בתולים", "גבס", "גוון", "גידולים", "", "גיל המעבר", "גלד פצע", "גלולות הורמונליות", "גניקולוגיה", "גרידה", "גרידת פיפל", "גריס", "דבר שמקבל טומאה", "דיאפרגמה", "דיכאון אחר לידה", "דלקת אוזניים", "דם", "דם בתולים", "דם חימוד", "דם טוהר", "דם קושי", "הדרכת כלות", "הווייתו בטומאה", "הווייתו בידי אדם", "הזרעה", "החזרת עוּברים", "הטוב והמטיב", "היסטרוסקופיה", "היריון", "הכנה לטבילה", "הנקה", "הסתכלות", "הפלה", "הפסק טהרה", "הפרדת המיטות", "הפרדת קרומים ", "הפריה חוץ גופית", "הר הבית", "הרגשה", "הרחקות", "השקה", "התאמת דיאפרגמה", "התקן תוך רחמי", "התקשטות", "וסת", "ורוד", "זבה", "זחילה", "זיבת דבר לח", "זירוז לידה", "זריעה", "חוּם", "חופת נידה", "חורבן בית המקדש", "חזקת טהרה", "חזרה בתשובה", "חטא אדם הראשון", "חיבוק", "חיילים", "חמישה דמים טמאים", "חפיפה", "חציצה", "חתונה", "טבילה", "טבעת", "טבעת נרתיקית", "טהרה", "טומאה", "טחורים", "טיפול רפואי", "טיפולי פוריות", "טישו", "טמפון", "טנקולום", "יבלת", "יולדת", "יוצא לדרך", "יחסי אישות", "ייחוד", "יין", "ים", "ירוק", "ירידת מים", "ישיבה", "כותונת", "כחול", "כינים", "כלה", "כתום", "כתם", "לבן", "לבנים", "לידה", "לידה שקטה", "לייזר", "לק ", "מבוגרת", "מגבון לח", "מגע", "מדבקה למניעת היריון", "מדריכת כלות", "מוך דחוק", "מורת הלכה", "מחזור", "מחזיר גרושתו", "מחלה", "מי ברז", "מי שפיר", "מייק אפ", "מים", "מים מותפלים", "מים שאובים", "מכונית", "מנהג בנות ישראל ", "", "מניעת היריון", "מניקה", "מסולקת דמים", "מסירת חפצים מיד ליד", "מעוברת", "מעיין", "מצוות עונה", "מצעים", "מקווה", "משחה", "משקאות אלכוהוליים", "נגיעה", "נהר", "נזם", "נחל אכזב", "ניגוב", "נידה", "נייר טואלט", "ניתוח קיסרי", "נֵפֶל", "נשיקה", "סגול", "סדין", "סטריפינג ", "סיכה", "סירוק", "סלק", "ספקולום", "סרך ביתה", "", "סתימה בשן", "עגילים", "עדי בדיקה", "עדשות מגע", "עוּבר", "עונת אור זרוע", "עונת הווסת", "עור יבש", "עור מתקלף", "עשה לך רב", "פֶּד", "פולטת שכבת זרע", "פלסטר", "פצע", "פקק הרחם", "פרישה סמוך לווסת", "פתיחת הארון", "פתיחת הרחם", "פתיחת מקור ", "צבע", "צהוב", "צוואר הרחם", "צום", "צורת הטבילה", "צחצוח שיניים", "צילום רחם", "צינור", "ציפורניים", "צמות", "צמר גפן", "צניעות", "קווטר", "קופת חולים", "קוץ", "קידוש", "קציצת ציפורניים", "קרח", "קרם", "קשרים בשיער", "ראסטות", "רבי זירא", "רבנית", "רחם", "רחצה", "רפואת נשים ", "שאיבת ביציות", "שאלת חכם", "שבעה נקיים", "שבת", "שהחיינו", "שומרת יום כנגד יום", "שחור", "שיחה", "שינה", "שינוי צבע המראה", "שיניים תותבות", "שיער", "שירה", "שירותים", "שלג", "שפופרת הנוד", "שקיעה", "תוספת שיער", "תחבושת", "תחבושת הגיינית", "תחתונים", "תחתונית", "תכשיטים", "תפילה", "תפרים", "תקנות רבי", "תקנת עזרא", "תשובה", "אבינו מלכנו", "אבן משכית", "אגוזים", "איפור", "אכילה ", "אכילה ביום הכיפורים", "אלול", "אנגינה", "ארון קודש", "אשמנו בגדנו", "בגדים לבנים", "בגדים נאים", "בין אדם לחברו", "בעל תוקע ", "ברוך שם כבוד מלכותו", "בריאת העולם", "ברכת הלבנה", "ברכת כהנים", "ברכת שהחיינו", "בשמים ", "(להרחה)", "בשר", "גילוח", "גלולות", "דאודורנט", "דג", "דוחה יתושים", "דין", "הבדלה", "הדלקת נרות", "היריון", "המלך המשפט", "המלך הקדוש", "הנקה", "השתחוויה", "התרת נדרים", "ווידוי", "חולה שאין בו סכנה", "חולה שיש בו סכנה", "חזן", "חסד", "חשבון נפש", "טבילה", "יובל ", "יולדת ", "יום הדין", "יום הזיכרון", "יום הכיפורים", "חולה", "שיש בו סכנה", "יולדת", "נטילת ידיים", "נעילת הסנדל", "קבלת קדושת היום", "תענית", "יום טוב שני", "יום תרועה", "יין", "ייסורים", "יעלה ויבוא", "(בברכת המזון)", "כאב", "כדורים ", "(תרופות)", "כדי אכילת פרס", "כהן גדול", "כותבת הגסה", "כל נדרי", "כפכפים", "כפרה", "כפרות", "כריעה", "לימוד תורה", "לך אלי תשוקתי", "לשנה טובה תכתב ותחתם", "מדרסים", "מוצאי יום הכיפורים", "מחלה ", "מיגרנה", "מים אחרונים", "מינקת", "מכשיר שמיעה", "מלוא פיו", "מלכויות זכרונות ושופרות", "מלקות ", "מניין", "מניין השטרות", "מניין השנים", "מעוברת", "מעין שלוש", "מעשרות", "נדבות", "נטילת ידיים", "נישואין", "נעילה", "נעילת הסנדל", "נעלי בית", "נעליים", "נפילת אפיים", "נר נשמה", "נשיאת כפיים", "נשים", "סיכה", "סימנים", "(מאכלי ראש השנה)", "סכרת", "סליחות", "סנדלים", "סעודה", "עבודת יום הכיפורים", "קודש הקודשים", "עיצומו של יום מכפר, ", "על חטא שחטאנו", "ערב יום הכיפורים", "אכילה ושתייה", "ערב ראש השנה", "ערלה", "עשרת ימי תשובה", " תשובה ", "צדקה", "צום", "צניעות", "קטורת", "קטן", "קידוש", "קידוש לבנה", "קיטל", "קפאין", "קרוקס", "ראש השנה", "יומיים", "סעודות", "תשליך", "ראש השנה שחל בשבת", "תקיעת שופר ", "ראש כבש או דג", "רופא", "שאלת רופא ביום הכיפורים", "רימון", "שברים", "שבת ", "שבת שובה", "שבת תשובה", "שהחיינו", "שופר ", "שטיפת הפה", "שטרות", "שידוכים", "שינה", "שמיטה", "שעיר לעזאזל", "שפעת", "שתייה", "שתל קוכליארי", "תאריכים", "תספורת", "תעניות וסיגופים", "תענית", "תפוח בדבש", "תפילה", "תפילה זכה", "תפילות יום הכיפורים", "תפילות ראש השנה", "תפילת עמידה בקול רם ", "(בראש השנה וביום הכיפורים)", "תקיעה", "תקיעה בשופר", "תקיעה גדולה", "תקיעת שופר", "יסוד", "הפסק", "טעה בתקיעות", "תרועה", "תרופה", "תשובה", "עשרת ימי תשובה ", "תשליך", "תשמיש המיטה", "אַיָּל", "אַיָּל אדום", "אַסְנָה", "אבטיח (גרעין)", "אבן", "אבקת חלב", "אבר מן החי", "אגוזים", "אגס (עץ)", "אגרוף", "אדים (זיעה)", "אדם", "אוגרת", "אווז בית", "אוכל", "אוכל מתחת למיטה", "אוכמניות", "אולם אירועים", "אונות הריאה", "אורז", "אורח", "אותו ואת בנו", "אטריות", "אילם", "אילת", "איסור הנאה", "איסור חדש", "איסורים מבטלים זה את זה", "אכילת בשר", "אליה", "אלכוהול", "אלמנת כהן", "אמונים כלוריד", "אמייל", "אמירה לגוי", "אניסקיס", "אספרגוס", "אפייה", "אפרוח", "אפרוחים", "אפרסק (עץ)", "אצות ים", "אקרית", "ארונות", "ארטישוק", "ארנבת", "ארץ העמים", "ארץ ישראל", "אשכולית", "אשלגן כלוריד", "אתאיסטים", "אתרוג", "בגדים", "בדיקת מזון", "בהמה", "בהמה דקה", "בוסר", "בורא פרי העץ / האדמה", "ביגלה (גדול)", "ביטול איסור", "ביטול ברוב", "ביטול בשישים", "בייגלך", "ביכורים", "ביעור מעשרות", "ביצים", "בירה", "בישול", "בישול גויים", "בישול נכרים", "בית הכוסות", "בית מלון", "בכור בהמה", "בל תשחית", "בל תשקצו", "בלילה נוזלית", "בלילה עבה", "בלינצ'ס", "בליעה", "בלנדר", "בן יומו", "בן נח", "בן פקועה", "בננה", "בעלי חיים", "בצבוץ", "בצל", "בצל ירוק", "בצל קלוף", "בקבוקי זכוכית", "בקלאווה", "בר (פאב)", "ברבור מצוי", "ברווז בית", "ברוקולי", "בריאות", "בריה", "ברירה", "ברית", "ברכות", "ברכות הנהנין", "בשר", "בשר בחלב", "בשר ודג", "בשר וחלב", "בשר מלאכותי (מתורבת)", "בשרי", "בת כהן", "בתי עסק", "ג'חנון", "ג'ירפה", "ג'לטין", "גבולות הארץ", "גבינה", "גבינה לבנה", "גבינה קשה", "גבינת גויים", "גבעול", "גג", "גדיש", "גדר חיה", "גוי", "גויאבה", "גוש", "גוש אדמה", "גוש קטיף", "גזע", "גיד הַנָּשֶׁה", "גידולי מים (הידרופוניקה)", "גינה ציבוריות", "גינות נוי", "גלאט", "גלוטן", "גליצרין (גליצרול)", "גמל", "גמר מלאכה", "גן חיות", "גפן", "גר", "גר תושב", "גרושת כהן", "גרים", "גרעין (פרי/ירק)", "גרעינים", "גת", "דבר חשוב", "דבר שאינו מתכוון", "דבר שיש לו מתירים", "דבש", "דג", "דג וחלב", "דג חרב", "דג מלוח", "דגים", "דגן", "דגני בוקר", "דוחן", "דחיית סיפוקים", "דיאטה", "דיג", "דלעת (צמח)", "דם", "דמאי", "דניאל", "דרוזים", "דרוסה", "דרור (ציפור)", "דרסה (שחיטה)", "דשא", "הַבְרָכָה", "הבאת שליש", "הגעלה", "הגרמה", "הדברה", "הדחה", "הדחה (כלים)", "הדרים", "הורים", "הזרעה מלאכותית", "היאבקות", "הידרופוניקה", "הינדואיסטים", "הכלאה", "הכשרת הבשר", "הכשרת כלים", "הכשרת מטבח", "הנאה", "הנבטה", "הנדסה גנטית", "הסקה", "העיר העתיקה (ירושלים)", "הפסקה", "הפקר", "הפרדה בין בשר לחלב", "הפרשת חלה", "הר הבית", "הר ציון", "הרובע היהודי והרובע הארמני (ירושלים)", "הרחקה", "הרינג", "הרכבה", "השגחה", "התרת נדרים", "וודקה", "וופל בלגי", "וידוי מעשרות", "ויטמינים", "ויסקי", "זה וזה גורם", "זחל (עש, פרפר)", "זיבול", "זיעה", "זיעה (אדים)", "זיתים", "זכוכית", "זכוכית מגדלת", "זרוע לחיים וקיבה", "זריעה", "זרע", "חֵלֶב", "חָלָק", "חבוש", "חביתית (פנקייק)", "חגבים", "חד פעמי", "חדקנית", "חדש", "חוזר וניעור", "חולדה", "חולה", "חומוס", "חומץ", "חומרי טעם", "חומרי ריח", "חומש", "חוץ לארץ", "חותמת כשרות", "חזיר", "חזקת בשרי או חלבי", "חטיפים", "חיה", "חיות מחמד", "חיטה", "חיידקים", "חילוני", "חינוך", "חיסונים", "חישוב שנות ערלה", "חלב", "חלב אישה", "חלב אם", "חלב ישראל", "חלב נוכרים", "חלבי", "חלדה", "חלה", "חלוץ פקקים", "חליבה", "חליטה", "חמאה", "חמאת גויים", "חמור, פריקת משא", "חממה", "חמץ", "חמשת מיני דגן", "חנות", "חנטה", "חסה", "חסרה", "חצובה", "חציל", "חצר", "חריף", "חרס", "חרסינה (פורצלן)", "חרקים", "חרש", "חתול", "חתונה", "חתיכה הראויה להתכבד בה", "חתיכה נעשית נבלה", "טבילת כלים", "טבל", "טבע", "טהרה", "טוחן תבלינים", "טומאה", "טומאת ארץ העמים", "טוסטר", "טחול", "טיפול בעזרת בעלי חיים", "טל", "טעם הנפלט מהכלים", "טעם כעיקר", "טרפה", "יבש ביבש", "יונים", "ייחור", "יין", "יין גויים", "יין מבושל", "יין נסך", "יעקב אבינו", "ירושלים", "ירקות", "ירקות 'גוש קטיף'", "ירקות עלים", "יש ברירה", "ישיבה על אוכל", "כַּנָּה", "כבד", "כבולעו כך פולטו", "כבוש במבושל", "כבישה (מאכל)", "כבש", "כדי נטילה", "כדי קליפה", "כהן", "כובאנה", "כוהל", "כוס מעל לחם", "כוסברה", "כוסברה", "כוסות", "כוסמין", "כוסמת", "כיור", "כיסא תינוק", "כיסוי הדם", "כיסנים (קרפלך)", "כיריים", "כלאי אילן", "כלאי בהמה", "כלאי הכרם", "כלאי זרעים", "כלאיים", "כלב", "כלי אבן", "כלי אחסון", "כלי אמייל", "כלי ארקופל", "כלי הגשה", "כלי זכוכית", "כלי חרס", "כלי חרסינה (פורצלן)", "כלי חשמל", "כלי מתכת", "כלי עץ", "כלי פורצלן", "כלי פלסטיק", "כלי קרמיקה", "כלי ראשון", "כלי שלישי", "כלי שני", "כלים", "כלים חד פעמיים", "כמהין", "כמון", "כנימת המגן", "כנימת עלה", "כעס", "כף", "כרוב", "כרובית", "כרם", "כרמל", "כרס (בהמה)", "כרתי", "כשרות", "לֶבֶּן", "לֶקֶט", "לא תחסום", "לב", "לדרמן", "לוי", "לח בלח", "לחוח", "לחם", "ליבון", "לימון", "ליקר", "לכּה", "מְתַחְלֵבִים", "מאכל בהמה", "מאכל חריף", "מאכלות אסורים", "מאכלי גויים", "מאכלים חיים", "מאכלים מעובדים", "מאפה", "מארזי מאכלים", "מגבירי טעם", "מגרדת (פומפייה)", "מגש", "מדיח", "מדינת ישראל", "מהדרין", "מונעי התגיישות", "מוסלמים", "מועדון", "מופלטה", "מוצרי חלב", "מוקף", "מורסן", "מזון מלכות", "מחבת", "מחבת טפלון", "מחובר", "מחזור המעשרות", "מחלל שבת", "מטבח", "מטבע בפה", "מטפל זר", "מי חלב", "מי ביצים", "מי פירות", "מייצבים", "מים", "מיעוט המצוי", "מיעוך ומשמוש", "מיץ (פרי)", "מיץ ענבים", "מיקסר", "מיקרואורגניזמים", "מיקרוגל", "מיקרוסקופ", "מכירי לוויה", "מכסה סיר", "מכר עני", "מלוואח", "מלוח כרותח", "מלח", "מלחייה", "מלחמת כלבים", "מלחמת שוורים", "מלחמת תרנגולים", "מליחה", "מליחה (מאכל)", "מלכודת עכברים", "ממתקים", "מנגל", "מנדרינה", "מנהרן", "מסורת", "מסורתי", "מסיבה", "מסננת (תה)", "מסעדה", "מספוא", "מעיים", "מעלה גרה", "מעמיד", "מעשר בהמה", "מעשר כספים", "מעשר עני", "מעשר ראשון", "מעשר שני", "מעשרות", "מפעל", "מפצח אגוזים", "מפריס פרסה", "מצוות התלויות בארץ", "מצנם", "מקווה", "מקום ציבורי", "מקלות מלוחים", "מקרר", "מראית עין", "מרגרינה", "מרוץ סוסים", "מרפסת", "מש (יחידת צפיפות לנפה)", "משאבי טבע", "משמרי לחות", "משמש", "משפחה", "משקאות אלכוהוליים", "משקה מגולה", "משתלה", "מתארח", "מתכת", "מתנות כהונה", "מתנות כהונה מן החי", "מתנות עניים", "נאמנות", "נבטים", "נבלה", "נוצרים", "נותן טעם לפגם", "נח", "נחש", "נטולה", "נטע רבעי", "ניסויים בבעלי חיים", "ניפוי קמח", "ניקור", "ניתוחים בבעלי חיים", "נכרי", "נענע", "נפה", "נפולה", "נצרים (סורים)", "נקובה", "נר", "נשים", "סובין", "סוכר", "סופגניות", "סוריה", "סורים (נצרים)", "סושי", "סימני טהרה", "סיעודי", "סיר", "סיר פלא", "סירוס", "סירכות", "סכין", "סכין שחיטה", "סכנה", "סלטים", "סלמון", "סלק", "סמדר", "סנפירים", "סעודת מצווה", "ספק", "ספר תורה", "ספרי קודש", "סתם יינם", "עוגה", "עולי בבל ועולי מצרים", "עולי מצרים, גבול", "עוללות", "עומר", "עונת המעשרות", "עופות", "עור", "עז", "עין טובה, בינונית ורעה", "עיסה נוזלית", "עיסה שנועדה לחלוקה", "עיקור (סירוס)", "עיקור (שחיטה)", "עיר דוד", "עירוי", "עישון (מאכל)", "עכבר", "עלייה לרגל", "עמילן", "ענבים", "עני", "עץ", "עץ מורכב", "עץ סרק", "עץ פרי", "עץ, עצי נוי", "עצי פרי", "עציץ", "עצמות", "עקרב", "ערבה, דרום הערבה", "ערום", "עריס", "ערל", "ערלה", "ערק", "עשרת ימי תשובה", "פֶּרֶד / פִּרְדָּה", "פֶּרֶט", "פאב (בר)", "פאה", "פדיון מעשר שני", "פדיון רבעי", "פולן (דבורים)", "פועל", "פורס ביצים", "פורצלן", "פותחן קופסאות", "פחזניות", "פטל", "פטר חמור", "פטרוזיליה", "פטריות", "פיצוחים", "פירורי לחם", "פירורים", "פירות", "פירות הדר", "פירות יבשים", "פלסטיק", "פלפל (ירק)", "פלפל שחור", "פנים חדשות", "פנקייק (חביתית)", "פסוקה", "פסטור", "פסי בידוד", "פסיפלורה", "פפאיה", "פרה", "פרווה", "פרוטה", "פרופוליס", "פרח", "פרחים", "פרט", "פריכיות אורז", "פריקת משא", "פת", "פת גויים", "פת נכרים", "פת פלטר (נחתום)", "פתיתים", "צבי", "צביעה", "צבעי מאכל", "צדקה", "צונן", "צחצוח שיניים", "ציד", "צימוקים", "ציצית", "צלחת", "צלייה", "צלף", "צמחונות", "צמחי בר", "צמחי מרפא", "צמחי נוי", "צמחי ריח", "צמת הגידים", "צנצנת", "צער בעלי חיים", "קָלִי", "קביעה למעשרות", "קדושה ראשונה", "קדושה שנייה", "קובה", "קווקר (דייסה)", "קוטג'", "קולפן", "קומביין", "קומקום", "קופסאות קפה/שימורים", "קטן", "קטניות", "קטשופ", "קינוח והדחה", "קינמון", "קיסמי שיניים", "קישוא", "קליפה (פרי)", "קליפות תפוז", "קמא קמא בטל", "קמח", "קמח לבן", "קמח מלא", "קנקן", "קערה", "קפה", "קציר", "קרבן העומר", "קרבן שתי הלחם", "קרדית הקמח", "קרועה", "קריעה על מת", "קרמיקה", "קרפלך (כיסנים)", "קרפצ'יו", "קרקר", "קרש חיתוך", "קשיו (אגוז)", "קשיש", "קשקשים", "ראש השנה", "ראשית הגז", "רהיטים", "רוכב", "רחם (בהמה)", "ריאות", "ריח", "רימה (זבוב)", "רימון", "רשת צלייה (מנגל)", "שאריות אוכל", "שבורה", "שביעית", "שבירת כוס בחופה", "שבת", "שהייה", "שוחט", "שוטה", "שולחן", "שולחן מלכים", "שום", "שום קלוף", "שומן רווי", "שומשום", "שוסע שסע", "שוקולד", "שור", "שזיף (עץ)", "שחיטה", "שיבולת שועל", "שיבלות שועל", "שיח רב שנתי", "שיירי מאכלים", "שיכור", "שיכר", "שילוח הקן", "שיפוד", "שיפון", "שיפוץ (בבית)", "שיש (מטבח)", "שכחה", "שלאק", "שלוש שנים", "שליו", "שליח", "שליש", "שליש גידול", "שמונה עשרה גזירות", "שמיטה", "שמיר", "שמירם", "שמן גויים", "שמן זית", "שנה", "שעועית", "שעורה", "שעטנז", "שעת התקופה", "שפן", "שפתון", "שקדי מרק", "שרצים", "שתי הלחם", "שתיל", "שתילה", "תאנה", "תבואה", "תבלינים", "תבנית אפייה", "תבשיל דגן", "תה", "תולעים", "תוספי מזון", "תוספי תזונה", "תור מצוי", "תות שדה", "תחום עולי בבל ועולי מצרים", "תחליף מלח", "תירס", "תמר", "תנור", "תעודת כשרות", "תערובות", "תעשיית המזון", "תפוז", "תפוז (קליפה)", "תפוח אדמה", "תרד", "תרומה גדולה", "תרומות ומעשרות", "תרומת מעשר", "תרופות", "תריפס", "תרנגול", "תרנגול הודו", "תרפיה בעזרת בעלי חיים", "תתאה גבר", "אבילות", "אוטובוס", "אוכל", "אוכל נפש", "אוכל קפוא", "אורח", "אוריגמי", "אזעקה", "איטריות", "אילת", "אינטרקום", "איפור", "אירוסין", "אלוהי נשמה", "אלמנה", "אמבטיה", "אמירה ליהודי", "אמירה לנוכרי", "אסרו חג", "אפיה", "אריזות אוכל", "אש", "אשפה", "אשר יצר", "אתר אינטרנט", "בגדים", "בהמה", "בונה", "בורר", "ביוב", "ביכורים", "ביסקוויט", "ביצה", "בישול", "בית", "בית דין", "בית כנסת", "בית מלון", "בית ספר", "בן הארץ", "בני ישיבה", "בנק", "ברז", "ברית מילה", "ברכות השחר", "ברכות התורה", "ברכת המזון", "בשר", "גבינה", "גדול העיר", "גוזז", "גוי", "גז", "גזיזת ציפורניים", "גיהוץ", "גילוח", "גירושין", "גלאי תנועה", "גלידה", "גרביים", "גרים", "גרמא", "דבר האבד", "יסוד", "דבש", "דגים", "דואר", "דוד חשמל", "דוד שמש", "דוכן", "דיכה", "דיסקים", "דם", "דרשת הרב", "דש", "הבדלה", "הבערה", "הגברת אש", "הדלקת נרות", "הדפסה", "הואיל ויבואו אורחים", "הוצאה", "הזכרת נשמות", "הכנה מקודש לחול", "הכשרת כלים", "הלוואה", "הלוויה", "הלל", "המעביר שינה", "הספד", "הסרת שיער", "העברת אש", "הפרשת חלה", "הקבלת פני רבו ברגל", "הקדשה", "השקיית צמחים", "ותודיענו", "ותערב", "זבל", "זרוע, לחיים וקיבה", "חג הקציר", "חגז", "חג, חגים", "חול המועד", "ברית מילה", "ספר תורה", "תיקון", "קניות", "חולה", "חולה במקצת גופו", "חולה מסוכן", "חולה שאין בו סכנה", "חומוס", "חופשה", "חוץ לארץ", "חושך", "חילוני", "חימום", "חימום בית", "חלב", "חלה", "חלון", "חלות", "חליבה", "חליצה", "חנות", "חפיפת שיער", "חקלאות", "חקלאות", "חרדל", "חרש", "חשבונות", "חשמל", "חתונה", "טבילה", "טבילת כלים", "טוחן", "טיול", "טלטול", "טכנאי", "טלית", "טלפון", "יום טוב", "יסוד", "כשחל בשבת", "שמחה", "בן הארץ", "יום הביכורים", "יום כיפור", "ייבום", "יין", "ימי חופשה", "יעלה ויבוא", "ירקות", "כביסה", "כותב", "כיבוי", "כיסוי דם", "כיריים", "כלים", "כעס", "כף מחוררת", "כתיבה", "כתישה", "לולב", "לחם", "ליל הסדר", "לימוד תורה", "יום טוב", "לימודים", "לימון", "לישב בסוכה", "לישה", "מאוורר", "מאכל ממולא", "מאכלי חלב / דבש", "מאפייה", "מבצע", "מגבות", "מגרדת", "מגילות", "מדורה", "מדידה", "מדיח כלים", "מוחק", "מונית", "מוסף", "מוצץ", "מוקצה", "מזגן", "מזוזה", "מחסן", "מחשב", "מיחוש", "מים", "מים חמים", "מיקסר", "מכולת", "מכונית", "מכשיר שמיעה", "מכשירי אוכל נפש", "מכתב", "מלאכה", "מלאכת הדיוט", "ממרח", "מניעת רווח", "מסחר", "מסמכים", "מסננת", "מעבר דירה", "מעלית", "מעסיק", "מעקה", "מפתחות", "מפשיט", "מפת שולחן", "מצה", "מקלחת", "מקפיא", "מקרר", "מראית עין", "מרק", "משחק", "משחקים", "משקפי שמש", "משקפיים", "משרת", "מת", "מתוך שהותרה", "מתן תורה", "מתנות כהונה­", "נגינה", "נולד", "נורה", "נטילת ידיים", "ניפוי קמח", "ניקוי הבית", "ניתוח", "נוכרי", "נעליים", "נפט", "נר", "נר נשמה", "נשים", "סבון ", "סדינים", "סדנת יצירה", "סוודר", "סוכה", "סחיטת פירות", "סיגריה", "סירוק", "סכין", "סלט", "סעודה שלישית", "ספירת העומר", "ספרים", "עבודה", "עגלה", "עוגה", "עירוב חצירות", "עירוב תבשילין", "עישון", "עלייה לרגל", "עני", "עפר", "עץ", "עצמאי", "עצמות", "עצרת", "ערבות", "עשרת הדיברות", "פאה נוכרית", "פדיון הבן", "פועלים", "פיקוח נפש", "פירה", "פירות", "פירות מבושלים", "פסח", "צד", "צורך מצווה", "צורכי רבים", "ציור", "ציצית", "צנרת", "צער", "צרעת", "קבורה", "קהלת", "קולפן", "קוסמטיקאית", "קוצר", "קורע", "קטורת ביתית", "קטיף", "קטן", "קידוש", "קליעת שיער", "קלנועית", "קמח", "קניות", "קרבן חגיגה", "קרבנות", "קריאת התורה", "קריאת מגילות", "קריעה על המת", "ראייה", "רב", "רגל, רגלים", "רדיאטור", "רהיטים", "רווקה", "רופא", "רופא מומחה", "רופא שיניים", "רות", "רחצה", "ריסוס", "ריקוד", "רכב הסעות", "רכבת", "רמקול", "רסק", "רפואה", "רצפה", "שבועות", "יסוד", "שביעי של פסח", "שביתת בהמה", "שבע ברכות", "שהחיינו", "שואב אבק", "שוחט", "שולחן", "שופר", "שחיטה", "שחייה", "שטיחים", "שטיפת כלים", "שידוכין", "שינוי", "שיעור נהיגה", "שיער", "שיפוד", "שיר השירים", "שירה", "שירותים", "שכיר", "שכיר", "שליחות", "שמחת תורה", "שמיני עצרת", "שמרטף", "שעון", "שעון שבת", "שתי הלחם", "תאורה", "תאורת בניין", "תאורת רחוב", "תבלינים", " תופר", "תחומין", "תחתונים", "תינוק", "תיקון ליל שבועות", "תכשיטים", "תלמיד חכם", "תלמידי ישיבה", "תנור", "תספורת", "תענית", "תעשיית מזון", "תפילין", "תרומות ומעשרות", "תרופות", "תרמוסטט", "אבל", "אונן", "אושפיזין", "אזיקון", "אכילה", "ארבעת המינים", "יסוד", "פסולי ארבעת המינים", "ארוסין", "אשפה", "אתרוג", "אתרוג מורכב", "בד", "ביצה", "בניית סוכה", "בר מצווה", "ברזנט", "ברית מילה", "ברכת לישב בסוכה", "בשר", "גבינה", "גגון", "גוי", "גידולי קרקע", "גשם", "דברי חול", "דגים", "דופן עקומה", "דירת ארעי", "דפנות", "הדס", "הדס שוטה", "הדר", "הושענא רבה", "הזכרת גשמים", "החלפת חיתול", "הידור מצווה", "הלל", "הנותן ריח טוב בפירות", "הקבלת פני רבו", "הקהל", "הקפות", "הושענא רבה", "שמיני עצרת", "הקפות שניות", "זכר להקהל", "זכר למקדש", "חיבוט ערבה", "חבל", "חג האסיף", "חג הביכורים", "חוטי חשמל וכביסה", "חוטים", "חולה", "חול המועד", "חוץ לארץ", "חייל", "חמשת מיני דגן", "חסר", "חרקים", "חתן", "חתן בראשית", "חתן מענה", "חתן תורה", "טומאה", "טיול", "טפח", "יין", "ירק", "ישיבה בסוכה", "שינה", "כילה", "כל הנערים", "כלים", "לבוד", "לולב", "לולב קנרי", "לחם", "לילה ראשון", "לימוד תורה", "לימון", "לישב בסוכה", "מוקצה", "מזוזה", "מזונות", "מחצלת", "מיטה", "מיטת קומתיים", "מין אחר", "מסמר", "מעמיד", "מצוות", "עונה", "מצטער", "שינה", "מקלות", "משקאות", "חריפים", "מתנמנם", "נטילת", "לולב", "ברכה", "סדר הניענוע", "נייר", "ניסוך", "המים", "נישואין", "נסיעת", "עסקים", "נסרים", "נשים", "נשתנה צורתו", "סוכה", "בנייה", "יסוד", "מידות", "סוכה לנצח", "סוכה שאולה", "סוכות", "סיום מסכת", "סכך", "סכך לנצח", "ספינה", "עגלה", "עוסק", "במצווה", "עוקץ", "על", "נטילת", "לולב", "עלייה", "לרגל", "ענני", "הכבוד", "עץ", "ערב", "סוכות", "ערבה", "פיטם", "פינוי", "כלים", "ופסולת", "פירוק", "סוכה", "פירות", "פסולי", "היום", "הראשון", "פסולי", "סכך", "פרגולה", "פרי", "פשתן", "צדקה", "צורת", "הפתח", "צמר גפן", "צפצפה", "קהלת", "קוישקלאך", "קורא", "קטן", "קישוטים", "סוכה", "קרטון", "קרשים", "רוח", "רכב", "שבע", "ברכות", "שבעים", "אומות", "שבת", "שהחיינו", "שטיפת", "כלים", "שינה", "שיעור ארבעת המינים", "שמחה", "שמחת בית השואבה", "שמחת תורה", "שמיני עצרת", "יסוד", "תאורה", "תיומת", "תעשה ולא מן העשוי", "תפילה", "תפילת הגשם", "תקרה", "אבוקדו", "אבות מלאכה", "אבטיח", "אבן", "אבני פלא", "אבק", "אגוזים", "אגרטל ", "אדים ", "אודם", "אוהל ", "אווזים ", "אוזניים ", "אוטובוס ", "אוכל ", "מתוך פסולת", "אולר ", "אופניים", "אור ", "אורג", "אורח ", "אזעקה ", "אחות ", "אטב ", "משרדי", "איטר ", "אילן ", "אינו ", "מתכוון", "אינטרקום", "איפור ", "אירוסין ", "איתורית ", "אכילה ", "אלבום ", "אמבולנס ", "אמה", "אמירה ", "לגוי", "אף ", "אפיה ", "אפרוחים ", "אקווריום ", "ארבעה ", "מינים", "ארגז ", "חול", "ארון ", "ארון ", "קודש", "אריזה ", "אריס ", "אש ", "אשכולית ", "אתר ", "אינטרנט", "אתרוג ", "בגד ", "בד ", "בדיקת ", "דם", "בהמה ", "בובת ", "שלג", "בוטנים ", "בוילר ", "בולים ", "בונה ", "וסותר", "בוץ ", "בור ", "בורג ", "בורר ", "בושם", "בחורי ", "ישיבה", "בטון ", "ביטול ", "כלי מהיכנו", "ביטחון ", "שוטף", "ביליארד", "בין ", "השמשות", "ביסקויט ", "ביצים ", "בירה ", "בישול ", "בישול ", "בישולי ", "גויים", "בית ", "בית ", "דין", "בית ", "חולים", "בית ", "מלון", "בית ", "סאתיים", "בכדי ", "שיעשו", "בלון ", "במה ", "מדליקין", "בן ", "דורסאי", "בנזין ", "בניה ", "בננה ", "בנק ", "בסיס ", "לדבר האסור", "בעלי ", "חיים", "בקבוק ", "בר ", "ברד ", "ברז ", "ברית ", "מילה", "ברכת ", "המזון", "בשמים ", "בשר ", " בשר ", "גבינה ", "גביע ", "גביע ", "גבעולים ", "גג ", "גגון ", "גודש ", "גוזז", "גוי ", "גולות ", "גורל ", "גוש ", "גחלת ", "גילוח ", "גירושין", "ג'ל ", "גלאי ", "מתכות", "גלגיליות ", "גלד ", "גלולות ", "ג'לי ", "גלעין ", "גפרור ", "גר ", "גר ", "תושב", "גרביון ", "גרון ", "גריפה ", "וקטימה", "גרמא", "גרנולה ", "גרעינים ", "גרעינים ", "גרף ", "של רעי", "גשם ", "דבר ", "שאינו מתכוון", "דבש ", "דגים ", "דוד ", "דוד ", "חשמלי", "דוד ", "שמש", "דולה ", "דיבור ", "דיסקית ", "דיקור ", "דירה ", "דלת ", "דמקה", "דרך ", "אכילה", "דרך ", "בין עירונית", "דרמטול ", "דרשת ", "השבת", "דש ", "דשא ", "הבדלה ", "הבלעה ", "הבלעת ", "ריבועים", "הברגה ", "הדבקה ", "הדלקת ", "נרות", "הוצאה ", "החזרה ", "הטלת ", "מים", "הטמנה ", "היסח ", "הדעת", "הכנה ", "משבת לחול", "הכנות ", "לשבת", "הלוואה ", "הליכה", "הנבטה ", "הנחה ", "הנקה ", "הערמה ", "הפטרה", "הפלגה ", "הפסקת ", "חשמל", "הפרשה ", "מאיסור", "הקבלת ", "פני הרב", "הרהור ", "הרכבה ", "השאלה ", "השבת ", "אבידה", "השהייה ", "השוואת ", "גומות", "השכרה ", "השמעת ", "קול", "השקיה ", "השרייה ", "התירו ", "סופם משום תחילתם", "התעמלות", "וודקה ", "וופל ", "ויטמינם ", "ויכולו ", "וילון ", "זבובים ", "זורה ", "זורע ", "זיבול ", "זירוז ", "לידה", "זיתים ", "זכור ", "זמירה ", "זמני ", "השבת", "זקן ", "זריקה ", "זרעים ", "חבילה ", "חבית ", "חבל ", "חברת ", "החשמל", "חגורה ", "חדשות ", "חובל ", "חובש ", "חוט ", "חוט ", "להט", "חול ", "חולה ", "חולה ", " חולה ", "חולץ ", "פקקים", "חום ", "שהיד סולדת בו", "חומוס ", "חומר ", "ניקוי", "חורש ", "חותמת ", "חיות ", "מחמד", "חיטוי ", "חיי ", "שעה", "חייל ", "חיישן ", "חילול ", "שבת", "חילוני ", "חימום ", "חיתוי ", "בגחלים", "חלב ", "חלב ", "אם", "חלון ", "חלות ", "חליבה", "חליצה ", "חמאה ", "חמאת ", "חמוצים ", "חמין ", "חממה ", "חמר ", "מדינה", "חפירה ", "חפצים ", "חצילים ", "חצץ ", "חצר ", "חרק ", "חרש ", "חשבונות ", "חשמל ", "טאטוא ", "טבילה ", "טבל ", "טבק ", "טווה", "טוחן ", "טונה ", "טחינה ", "טיח ", "טיט ", "טיטול ", "טייפ ", "טיסה ", "טיפולי ", "לחיצה", "טיפוס ", "טיפות ", "אף", "טיפות ", "עיניים", "טישיו ", "טכנאי ", "טלויזיה ", "טלויזיה ", "במעגל סגור", "טלטול ", " טלטול ", "טלית ", "טלפון ", "טלק ", "טניס", "טס ", "מתכת", "טפח", "יבלות ", "ידית ", "יהרג ", "ואל יעבור", "יוד ", "יולדת ", "יום ", "טוב", "יום ", "כפור", "ייבום ", "ייבוש ", "ייחוד ", "יין ", "יישוב ", "הארץ", "יישוב ", "הארץ", "יישובים ", "ים ", "יריעה ", "ירקות ", "יתושים ", "כבד ", "כבוד ", "כבוד ", "כבושים ", "כביש ", "כדור ", "כדור ", "שלג", "כדורי ", "שינה", "כדורים ", "כובע ", "כוס ", "כורסא ", "כותב ", "כחול", "כיבוס ", "כיווץ ", "כיור", "כילה ", "כינים ", "כיס ", "כיפה ", "כיריים ", "כל צרכו", "כלב ", "כלב ", "כלבת ", "כלוב ", "כלי ", " כלי ", " כלי ", "כלי ", "כלי ", "ראשון, שני ושלישי", "כלי ", "שמלאכתו לאיסור", "כלי ", "שמלאכתו להיתר", "כלי ", "שמלאכתו להיתר ולאיסור", "כללי ", "המלאכות", "כסא ", " כסא ", "כסף ", "כף ", "כפפות ", "כפתור ", "כר ", "כרטיס ", "אשראי", "כרטיסיה ", "כרית ", "אוויר", "כרמלית ", "כתב ", "כתיבה ", "כתם ", "כתף ", "לֶבֶּן ", "לאלתר ", "לגו ", "לול ", "לולאות ", "לחם ", "לחם ", "לחצניות ", "ליבון ", "לימוד ", "תורה", "לימונדה ", "לכה", "לכה ", "דודי", "לפני ", "עיוור", "לש ", "מאוורר ", "מאכל ", "מאכל ", "בן דורסאי", "מבושל ", "כל צרכו", "מבחן ", "מבעיר ", "מברג ", "מברשת ", "צביעה", "מברשת ", "שיניים", "מברשת ", "שיער", "מגב ", "מגבונים ", "לחים", "מגבית ", "מגבעת ", "מגבת ", "מגיס ", "מגירות ", "מגנט ", "מגרדת ", "מד", "חום", "מד ", "טמפרטורה", "מדורה ", "מדידה ", "מדיח ", "כלים", "מדפסת ", "מהדק ", "מוחק ", "מונופול ", "מוסך ", "מוצאי ", "שבת", "מוצץ ", "מוצק ", "מוקצה ", "מזגן ", "מזיד ", "מזלג ", "מזרן ", "אוויר", "מחדד ", "מחוסר ", "צידה", "מחזי ", "כמבשל", "מחט ", "מחיאת ", "כפיים", "מחיצה ", "מחיצה ", "המתרת", "מחלבה ", "מחמר", "מחרוזת", "מחשב ", "מחתך", "מטאטא ", "מטפחת ", "מטפל ", "רפואי", "מטריה ", "מטרנה ", "מי ", "בושם", "מי ", "מלח", "מי ", "פה", "מיונז ", "מיחוש ", "מיחזי ", "כבמבשל", "מיחם ", "מיטה", "מיטת ", "מייק אפ", "מים ", "מיסך", "מיץ ", "מיץ ", "מיץ ", "מיקסר ", "מיקר ", "מיקרוגל ", "מכבה ", "מכבי ", "אש", "מכה ", "מכה ", "מכה ", "בפטיש", "מכונה ", "מכונת ", "מכונת ", "מכסה", "מכשיר ", "מכשיר חשמלי", " מכשיר ", "מלא ", "לוגמיו", "מלאכה ", "מלאכה ", "שאינה צריכה לגופה", "מלאכות ", "שבת", "מלאכת ", "מחשבת", "מלבן", "מלווה ", "מלון ", "מלח ", "מלחיה ", "מלחמה ", "מלט ", "מליחה ", "מלילה ", "מלכודת ", "מלצרות ", "ממחטה ", "ממחק", "ממרח ", "מנה ", "חמה", "מנורה ", "מנחה ", "קטנה", "מניות ", "מנעול ", "מספרים", "מנפץ", "מסחר ", "מסמכים ", "מסמר ", "מסננת ", "מסעדה ", "מספריים ", "מסרק", "מעבד", "מעיל ", "מעין ", "שבע", "מעלית ", "שבת", "מעמר", "מעשה ", "שבת", "מפות ", "ניילון", "מפיות ", "מפרק ", "מפשיט", "מפתח ", "מצטער ", "מצלמה ", "מקום ", "פטור", "מקח ", "וממכר", "מקל ", "הליכה", "מקלף ", "מקלקל", "מקפיא ", "מקרר ", "מראית ", "עין", "מרזב ", "מרפסת ", "מרק ", "מרקד ", "משולש ", "משחה ", "משחק ", "משחת ", "נעליים", "משחת ", "שיניים", "משטרה ", "משמרות ", "משקאות ", "משקה ", "מדינה", "משקים ", "משקפי ", "שמש", "משקפיים ", "משרטט", "מתיר", "מתלה ", "ואקום", "מתנה ", "מתעסק ", "נגן ", "נדנדה ", "נהג ", "אמבולנס", "נהר ", "נוזל ", "נוכרי ", "נוצות ", "נורה ", "נזלת ", "נחש ", "נטילת ", "ידיים", "נטילת ", "נשמה", "ניגוב ", "ניגון", "ניילון ", "נייר ", " נייר ", "ניכוש ", "ניצוצות ", "נישואין ", "ניתוח ", "נכה ", "נמל ", "תעופה", "נמלים ", "נעליים ", "נפט ", "נר ", "נרות ", "שבת", "נשים ", "נשק ", "נתיבי ", "איילון", "סבון ", "סביבון ", "סוכה ", "סוכר ", "סולם ", "סותר ", "סחורה ", "סחיטה ", "סטנדר ", "סיגריות ", "סידור ", "הבית", "סיכה", " סיכת ", "סימניה ", "סיקול ", "סיר ", "סירוק ", "סכו", "סכין ", "סכנה ", "סכנת ", "איבר", "סלט ", "סלסולים ", "סמרטוט ", "סעודה ", "שלישית", "סעודה ", "שנייה", "סעודות ", "שבת", "ספה ", "ספוג ", "ספסל ", "ספק ", "ספר ", "ספר ", "טלפונים", "ספר ", "תורה", "ספריי ", "ספרים ", "סקוטש ", "סקוטש ", "סקטים ", "עבד ", "עגלת ", "תינוק", "עדשה ", "עדשות ", "עובדין ", "דחול", "עובר ", "עוגה ", "עונג ", "שבת", "עוף ", "מבושל", "עור ", "עושה ", "שני בתי נירין", "עט ", "עיוור ", "עיניים ", "עיסה ", "עיסוי ", "עיר ", "עירוב ", "חצירות", "עירוב ", "תחומין", "עירוי ", "עיתון ", "עלוני ", "שבת", "עליות", "לתורה", "עמודי ", "חשמל וטלפון", "עמודים ", "עניבה ", "ענף ", "עפרון ", "עץ ", "עציץ ", "עצמות ", "עקרב ", "ערב ", "שבת", "ערבוב ", "עריסה ", "ערק ", "עשבים ", "פאה ", "נוכרית", "פאזל ", "פג ", "פדיון ", "בכור", "פודינג ", "פודרה", "פוליאסטר ", "פומפה ", "פומפיה ", "פוצע", "פח ", "אשפה", "פחיות ", "פטיש ", "פיאות ", "פיזיותרפיה", "פימו ", "פיצוחים ", "פיקוח ", "נפש", "פירה ", "פירות ", "פלטה ", "פלטה ", "פלסטיק ", "פלסטלינה ", "פלסטר", "פלפל ", "חריף", "פנימיה ", "פסולת ", "מתוך אוכל", "פסוקי ", "דזמרה", "פסיק ", "רישא", "פעמון ", "פצע ", "פקס ", "פקק ", "פרווה ", "פרח ", "פרי ", "פרסומות ", "פרפר ", "פרצות ", "פת ", "פתילה ", "צאת ", "הכוכבים", "צביון ", "השבת", "צביעה ", "צבת ", "צדקה ", "צובע", "צורך ", "גופו", "צורך ", "מצוה", "צורך ", "מקומו", "צורכי ", "מצווה", "צורת ", "הפתח", "צחצוח ", "שיניים", "צידה ", "צינור ", "ציפורן ", "ציפית ", "צל ", "צלחות ", "צלייה ", "צמות", "צמחים ", "ריחניים", "צמר ", "צמר ", "גפן", "צנים ", "צעצוע ", "צרכי ", "קבלן ", "קבלת ", "שבת", "קדירה ", "קוביה ", "קוגל ", "קול ", "קולה ", "קולן ", "קולפן ", "קולר ", "קוּלר ", "קומפרסים ", "קוסקוס ", "קופסאות ", "שימורים", "קוץ ", "קוצר ", "קורע", "קורקינט ", "קושר ומתיר", "קטן ", "קטניות ", "קידוח ", "קידוש ", "קיסם ", "קיפול ", "קלי ", "הבישול", "קליפה ", "קלנועית", "קלף ", "קמח ", "מצה", "קניות ", "לשבת", "קפה ", "קפיץ ", "קפיצה ", "קצפת ", "קראק ", "פוט", "קרח ", "קריאה ", "קריאת ", "התורה", "קרם ", "קרפף", "קשקשים ", "קשר ", "ראש ", "חדש", "רביעית ", "רדיאטור ", "רדיו ", "רהיטים ", "רווק ", "רוטב ", "רוכסן ", "רופא ", "רוק ", "רחצה ", "ריבוע ", "ריבית ", "ריצה", "ריקוד", "רכבת ", "רכוש ", "רכיבה ", "רמז ", "רמקול ", "רסן ", "רעש", "רפואה ", "רצה ", "והחליצנו", "רצועה ", "רצפה ", "רשות ", "היחיד", "רשות ", "הרבים", "שבות ", "שבות ", "דשבות", "שביתת ", "בעלי חיים", "שביתת ", "כלים", "שבע ", "ברכות", "שוגג ", "שוחט ", "שוכר ", "שולחן ", "שופר ", "שוקולד ", "שורשים ", "שותפות ", "שחייה", "שחיקת ", "סממנים", "שחמט", "שטיפת ", "כלים", "שטיפת ", "רצפה", "שטף ", "דם", "שטרות ", "שיבולים ", "שימון ", "שימורים ", "שינה ", "שינוי ", "שיניים ", "שיער ", "שפשוף ", "שירותים ", "שיתוף ", "מבואות", "שכונות ", "שכיר ", "שכר ", "שבת", "שלג ", "שלום ", "בית", "שלט ", "רחוק", "שמאלי ", "שמחת ", "תורה", "שמירה ", "שמן ", "שמשיה ", "שניים ", "מקרא ואחד תרגום", "שניים ", "שעשו מלאכה", "שעון ", "שעון ", "שעון ", "שבת", "שעורים ", "שער ", "שפות ", "שפתון", "שקדים ", "שקיות ", "שקילה ", "שקיעה ", "שקע ", "שרוך ", "שריפה ", "שריקה", "שרירים ", "שרשרת ", "חרוזים", "שש בש", "תאורה ", "תבלין ", "תבן ", "תה ", "תווית ", "תולדות ", "תולדות ", "חמה", "תומכת ", "לידה", "תוספת ", "שבת", "תופר", "תחבושת ", "תחום ", "שבת", "תחומין ", "תיבה ", "תיון ", "תינוק ", "תיפוף ", "תיק תק", "תכשיטים ", "תל ", "המתלקט", "תלייה ", "תלת אופן", "תמונה ", "תנור אפיה ", "תנור חימום ", "תנור ", "חשמלי", "תנור ", "נפט / גז", "תספורת ", "תסרוקת", "תעודת ", "זהות", "תענית ", "תערובת ", "תפילה ", "תפילת ", "תפילין ", "תפירה ", "תקע ", "תרומות ", "ומעשרות", "תרופה ", "תריס ", "תרמוס ", "תרמוסטט ", "תתרן ", "אביונה", "אבל", "אדמת", "אהבה", "אוטיסט", "אוננות", "אונס", "אורגזמה", "אורח", "איבר זכרות", "איידס", "אילונית", "אימוץ", "אין אונות", "איסורי עריות", "איש ואשה", "אמצעי מניעה", "קונדום", "אשכים", "אשת", "איש", "בגידה", "בדיקות לגילוי מומים בעובר", "בדיקת זרע", "ביאה", "ביטול מצוות עונה", "בית אפל", "בני", "נח", "בן עזאי", "בני תשע מידות", "ברית", "גוי", "גויה", "גיל הנישואין", "גירוי", "גירושין", "גלולות למניעת היריון", "גלולת היום שאחרי", "גלות וגאולה", "גר", "דגדגן", "דיאפרגמה", "דילול עוברים", "דניאל ", "דרך ארץ", "הומוסקסואליות", "הוצאת זרע לבטלה", "היריון", "הנאה", "הנקה", "הפלה", "שלושה חודשים", "הפסקת היריון", "הפריית", "מבחנה", "הקרנה", "הרהור", "יסוד", "הרכבה על כתפיים", "התקן תוך רחמי", "ואהבת לרעך כמוך", "ויאגרה", "זוגיות", "זירוז לידה", "זמני עונה", "זרייה בחוץ", "זרע", "חזקיהו", "חטא אדם הראשון", "חיבוק", "חנניה, מישאל ועזריה", "חרם דרבנו גרשום", "חרש אילם", "חשוכי ילדים", "חתונה", "טבילה", "טהרה", "טומאה", "טיי זקס", "טיפולי פוריות", "יום טוב", "יום כיפור", "יוצא לדרך", "יחסי אישות", "ייחוד", "ילדים", "יעוץ", "כובעון", "כימותרפיה", "כפייה על המצוות", "כרות שפכה", "כריתת אשכים", "כריתת ערמונית", "כריתת רחם", "ליטוף", "ליל טבילה", "ליל יום טוב", "ליל ראש חדש", "ליל שבת", "לימוד תורה", "לסביות", "מום", "מזוזה", "מחילה", "מחלה", "מחלות גנטיות", "מחלת נפש", "מחמאות ", "ממזר", "מניעת היריון", "בין לידות", "מעמד האיש", "מעמד האשה", "מצוות עונה", "מקלחת", "מקרה לילה", "מראות הצובאות", "משכב זכור", "לעניין החובה להתחתן", "נדה", "נחמת חשוכי ילדים", "נטיות הפוכות", "נטילת ידיים", "ניאוף", "נישואין", "גיל הנישואין", "סריס", "נישוק", "נרות קוטלי זרע", "נשים", "עונה", "נשים המסוללות", "לסביות", "לעניין החובה להתחתן", "סירוס", "ספר", "תורה", "ספרי קודש", "סרטן", "סריס", "עובר", "עונה", "אשה", "יסוד", "פרו ורבו", "שיא השמחה", "עקרות", "עשר שנים", "פגם הברית", "פיגור שכלי", "פמיניזם", "פנויה", "פצוע דכא", "פרו ורבו", "אחריות", "גוי", "גיל הנישואין", "יסוד", "ממזר", " מניעת היריון", "פרישות ", "צדקה", "צומות הקלים", "צניעות", "קוטל זרע", "קישוי", "קצף קוטל זרע", "קרי", " יסוד", "קשירת חצוצרות", "ראש חדש", "רווקה", "רחצה", "רכיבה ללא אוכף", "שאלת רב", "שבת", "שוטה", "שום", "שיא השמחה", "שינה על הגב", "שכבת זרע", "שפחה", "תאווה", "תביעה בלב", "תכנון המשפחה", "תכשיטים", "תסמונת דאון", "תקנת עזרא", "תרומת זרע", "תשוקה", "עונה", "תשמיש", "תשע מידות", "תשעה באב", "תשעה קבין", "CMV", "אבלות", "אהבת עולם", "אכילה", "אמן", "ארבע אמות של המתפלל", "ארבעת העולמות", "אשה", "אשרי", "בגדים", "בחירה חופשית", "בית כנסת", "בן מאומץ", "בעל קורא", "בקשות אישיות בתפילה", "ברוך שם כבוד מלכותו", "ברך עלינו", "ברכו", "ברכות התפילה", "גילוח:", "דברים שבקדושה", "דילוג", "הבדלים בין נוסח ספרד לאשכנז", "הגיית המילים בעדות השונות", "הכנה נפשית לתפילה", "הכנות לתפילה", "המפיל", "הנץ החמה", "הפסקה", "הקדמת", "הרהור", "הרהור בדברי תורה", "השכמת הבוקר", "השלמת תפילה", "השמעה לאוזניים", "התנועעות בתפילה", "ובא לציון", "וידוי", "ותיקין", "ותן ברכה", "ותן טל ומטר", "זכירת יציאת מצרים", "זמני הבוקר וחשבונם", "זמנים", "חגורה", "חולה", "חזן", "חזנות", "חזרה קצרה", "חילוני", "חנוכה", "חציצה", "חצי קדיש", "טירדה בתפילה", "טעות", "טפח", "ידיים", "יהא שמיה רבא", "יהי רצון שאחר התפילה", "ילדים", "יעלה ויבוא", "יציאה", "יציאת מצרים", "ירושלים", "כהנים", "כוונה", "כיפה", "כריעות בתפילה", "לא תתגודדו", "לבישת הבגדים בבוקר", "לבוש הראוי לתפילה", "למנצח (יענך ה')", "מאכלים", "מבטא", "מגן אברהם", "מודה אני", "מודים דרבנן", "ממהר", "מנהגי עדות", "מנחה", "מניין", "מעבר כנגד המתפלל", "מעריב", "מקווה", "מקום הראוי לתפילה", "מקום התפילה", "מקום מטונף", "משיב הרוח", "'משיכיר'", "נוסח התפילה", "נטילת ידים בבוקר", "נטילת ידים לפני התפילה", "נעליים", "נפילת אפיים", "נצרך לנקביו", "נקיות", "נשק:", "סמיכת גאולה לתפילה", "סעודה גדולה", "סעודה רגילה", "ספק", "ספר תורה", "עיוור", "עלות השחר", "עלינו לשבח", "עמוד השחר", "ערווה", "פוחח", "פיטום הקטורת", "פלג המנחה", "פסוקי דזימרה", "פסיעות לפני ואחרי התפילה", "פרשיות שמע", "פרשת הקטורת", "פרשת התמיד", "צואה", "צניעות", "צרכי מצווה", "צרכי עצמו", "קדושה", "קדושה דסידרא", "קדושת יוצר", "קדיש", "קטורת", "קידה", "קרבן התמיד", "קרבנות", "קריאת התורה", "קריאת שמע", "קריאת שמע של ערבית", "קריאת שמע שעל המיטה", "רחצה", "רוח רעה", "רצפת אבנים", "שאילת גשמים", "שחרית", "שיכור ושתוי", "שיער מגולה", "שיר של יום", "שירת אישה", "שכח להתפלל", "שלום", "שלום רב", "שליח ציבור", "שינת קבע", "שעון מעורר", "שעות זמניות", "שפת התפילה", "תחנון", "תספורת", "תפילה", "תפילת אבלים", "תפילת ותיקין", "תפילת נדבה", "תפילת עמידה", "תפילת שחרית", "תפילת תשלומין", "תפילין"};


					int i;
					for (i = 0; i < keys.length; i++)
						if (!keys[i].equals("") && keys[i].charAt(keys[i].length() - 1) != ' ')
							root.insert(keys[i]);
					if (searchListView != null) {
						for (int m = searchListView.getHeaderViewsCount(); m >= 0; m--)
							searchListView.removeHeaderView(searchListView.getChildAt(m));
						for (int m = searchListView.getFooterViewsCount(); m >= 0; m--)
							searchListView.removeFooterView(searchListView.getChildAt(m));
					}
					TextView txt1 = (TextView) findViewById(R.id.textView7);
					TextView txt2 = (TextView) findViewById(R.id.textView8);
					TextView txt3 = (TextView) findViewById(R.id.textView9);
					TextView txt4 = (TextView) findViewById(R.id.textView10);
					TextView txt5 = (TextView) findViewById(R.id.textView11);
					txt1.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String temp = txt1.getText().toString();
							if (temp.charAt(0) == ' ')
								temp = temp.substring(1);
							if (temp.charAt(temp.length() - 1) == ' ')
								temp = temp.substring(0, temp.length() - 1);

							searchText.setText(temp.split("\\(")[0]);
							txt1.setVisibility(View.GONE);
							txt2.setVisibility(View.GONE);
							txt3.setVisibility(View.GONE);
							txt4.setVisibility(View.GONE);
							txt5.setVisibility(View.GONE);
							goSearch();
						}
					});
					txt2.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String temp = txt2.getText().toString();
							if (temp.charAt(0) == ' ')
								temp = temp.substring(1);
							if (temp.charAt(temp.length() - 1) == ' ')
								temp = temp.substring(0, temp.length() - 1);

							searchText.setText(temp.split("\\(")[0]);
							txt1.setVisibility(View.GONE);
							txt2.setVisibility(View.GONE);
							txt3.setVisibility(View.GONE);
							txt4.setVisibility(View.GONE);
							txt5.setVisibility(View.GONE);
							goSearch();
						}
					});
					txt3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String temp = txt3.getText().toString();
							if (temp.charAt(0) == ' ')
								temp = temp.substring(1);
							if (temp.charAt(temp.length() - 1) == ' ')
								temp = temp.substring(0, temp.length() - 1);

							searchText.setText(temp);
							txt1.setVisibility(View.GONE);
							txt2.setVisibility(View.GONE);
							txt3.setVisibility(View.GONE);
							txt4.setVisibility(View.GONE);
							txt5.setVisibility(View.GONE);
							goSearch();
						}
					});
					txt4.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String temp = txt4.getText().toString();
							if (temp.charAt(0) == ' ')
								temp = temp.substring(1);
							if (temp.charAt(temp.length() - 1) == ' ')
								temp = temp.substring(0, temp.length() - 1);

							searchText.setText(temp);
							txt1.setVisibility(View.GONE);
							txt2.setVisibility(View.GONE);
							txt3.setVisibility(View.GONE);
							txt4.setVisibility(View.GONE);
							txt5.setVisibility(View.GONE);
							goSearch();
						}
					});
					txt5.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String temp = txt5.getText().toString();
							if (temp.charAt(0) == ' ')
								temp = temp.substring(1);
							if (temp.charAt(temp.length() - 1) == ' ')
								temp = temp.substring(0, temp.length() - 1);

							searchText.setText(temp);
							;
							txt1.setVisibility(View.GONE);
							txt2.setVisibility(View.GONE);
							txt3.setVisibility(View.GONE);
							txt4.setVisibility(View.GONE);
							txt5.setVisibility(View.GONE);
							goSearch();
						}
					});
					txt1.setText("");
					txt1.setVisibility(View.GONE);
					txt2.setText("");
					txt2.setVisibility(View.GONE);
					txt3.setText("");
					txt3.setVisibility(View.GONE);
					txt4.setText("");
					txt4.setVisibility(View.GONE);
					txt5.setText("");
					txt5.setVisibility(View.GONE);


					// Search for different keys
					List<String> lst = new ArrayList<String>();
					int k = 1;
					lst = root.complite(searchText.getText().toString());
					while (!lst.isEmpty())
						if (root.search(lst.get(0))) {
							if (k == 1) {
								txt1.setVisibility(View.VISIBLE);
								txt1.setText(lst.remove(0));
							}
							if (k == 2) {
								txt2.setVisibility(View.VISIBLE);
								txt2.setText(lst.remove(0));
							}
							if (k == 3) {
								txt3.setVisibility(View.VISIBLE);
								txt3.setText(lst.remove(0));
							}
							if (k == 4) {
								txt4.setVisibility(View.VISIBLE);
								txt4.setText(lst.remove(0));
							}
							if (k == 5) {
								txt5.setVisibility(View.VISIBLE);
								txt5.setText(lst.remove(0));
							}
							if (k > 5)
								lst.remove(0);
							k++;

						} else
							lst.remove(0);

				}


			}
			@Override
			public void afterTextChanged(Editable s) {

			}

		});
		searchNow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				searchNow.setBackgroundResource(R.drawable.rec_r_half_full);
				searchNow.setTextColor(Color.WHITE);
				lastSearch.setBackgroundResource(R.drawable.rec_r_half2_empty);
				searchText.setVisibility(View.VISIBLE);
				goSearch.setVisibility(View.VISIBLE);
				getVoice.setVisibility(View.VISIBLE);
				lastSearch.setTextColor(Color.rgb(151, 6, 6));
				searchFound=false;
				bookFound.setVisibility(View.GONE);
				searchListView2.setVisibility(View.GONE);
				if(searchListView!=null)
					searchListView.setVisibility(View.VISIBLE);
			}
		});
		lastSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchFound=false;
				bookFound.setVisibility(View.GONE);
				searchText.setVisibility(View.GONE);
				goSearch.setVisibility(View.GONE);
				getVoice.setVisibility(View.GONE);
				lastSearch.setBackgroundResource(R.drawable.rec_r_half2_full);
				lastSearch.setTextColor(Color.WHITE);
				searchNow.setBackgroundResource(R.drawable.rec_r_half_empty);
				searchNow.setTextColor(Color.rgb(151, 6, 6));
				if(searchListView!=null)
					searchListView.setVisibility(View.GONE);
				searchListView2.setVisibility(View.VISIBLE);

				mPrefs = getSharedPreferences(PREFS_NAME, 0);
				shPrefEditor = mPrefs.edit();
				//BlackBackground = mPrefs.getInt("BlackBackground", 0);
				//searchListView2.addHeaderView(mPrefs.getString("BlackBackground", ""));
				if(!onceLast) {
					onceLast=true;
					for (int k = 0; k < 10; k++) {
						TextView textView = new TextView(getBaseContext());
						//String sourceString = "<b>" + "[" + chapterCounter + "] " + chaptersNames[i][j] + "</b> " + sections;
						//String sourceString = "<b >"+ chaptersNames[i][j].split("-")[1] + "</b>("+ chaptersNames[i][j].split("-")[0]+","+ sections+")";
						textView.setText(mPrefs.getString("s" + k, ""));
						//textView.setText("shilo");
						//textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
						if (mPrefs.getInt("BlackBackground", 0) == 1) {
							textView.setTextColor(Color.WHITE);
							ImageView toMain = (ImageView) findViewById(R.id.to_main);
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
							ImageView menu = (ImageView) findViewById(R.id.menu);
							menu.setImageResource(R.drawable.ic_action_congif_b);
							main.setBackgroundColor(Color.rgb(120, 1, 1));
						} else
							textView.setTextColor(Color.BLACK);
						textView.setTextSize(24);
						searchListView2.addFooterView(textView);
						showResults();
					}
				}
				searchListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						searchNow.setBackgroundResource(R.drawable.rec_r_half_full);
						searchNow.setTextColor(Color.WHITE);
						lastSearch.setBackgroundResource(R.drawable.rec_r_half2_empty);
						searchText.setVisibility(View.VISIBLE);
						goSearch.setVisibility(View.VISIBLE);
						getVoice.setVisibility(View.VISIBLE);
						lastSearch.setTextColor(Color.rgb(151, 6, 6));
						searchFound=false;
						bookFound.setVisibility(View.GONE);
						searchListView2.setVisibility(View.GONE);
						if(searchListView!=null)
							searchListView.setVisibility(View.VISIBLE);
						searchText.setText(((TextView)view).getText().toString());
						final ProgressDialog downloadWait = ProgressDialog.show(SearchHelp.this, "", "please wait");
						new Thread() {
							public void run() {
								try {
									SearchHelp.this.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											// TODO Auto-generated method stub
											onceLast=false;
											goSearch();
										}
									});
								} catch (Exception e) {

								}
								downloadWait.dismiss();
							}
						}.start();

						//ourIntent.putExtra("sectionsForToast", sectionsForToast);

					}
				});

			}
		});
		goSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView txt1 = (TextView) findViewById(R.id.textView7);
				TextView txt2 = (TextView) findViewById(R.id.textView8);
				TextView txt3 = (TextView) findViewById(R.id.textView9);
				TextView txt4 = (TextView) findViewById(R.id.textView10);
				TextView txt5 = (TextView) findViewById(R.id.textView11);
				txt1.setVisibility(View.GONE);
				txt2.setVisibility(View.GONE);
				txt3.setVisibility(View.GONE);
				txt4.setVisibility(View.GONE);
				txt5.setVisibility(View.GONE);
				if(title.length()>0)
					goSearch();
			}
		});

		tooHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Class ourClass = null;
				try {
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				Intent ourIntent = new Intent(SearchHelp.this, ourClass);
				startActivity(ourIntent);
			}
		});
	}

	public boolean doMySearch(int num) throws InterruptedException {
		String LangSearch="EnglishBooks";
		if(true) {
			File fileCheck = new File(getBaseContext().getFilesDir() + "/en.txt");
			if (fileCheck.exists()) {
				System.out.println("test");
				for (int j = 0; j < enBook[0].length; j++) {

					for (int i = 1; i <= Integer.parseInt(enBook[1][j]); i++) {
						File file = new File(getBaseContext().getFilesDir() + "/" + enBook[0][j] + "_" + i + ".html");
						stcakFiles.add(file);
					}
				}
			}
			File fileCheckEs = new File(getBaseContext().getFilesDir() + "/es.txt");
			if (fileCheckEs.exists()) {

				for (int j = 0; j < esBook[0].length; j++) {
					for (int i = 1; i <= Integer.parseInt(esBook[1][j]); i++) {
						File file = new File(getBaseContext().getFilesDir() + "/" + esBook[0][j] + "_" + i + ".html");
						stcakFiles.add(file);
					}
				}
			}
			File fileCheckF = new File(getBaseContext().getFilesDir() + "/f.txt");
			if (fileCheckF.exists()) {

				for (int j = 0; j < fBook[0].length; j++) {
					for (int i = 1; i <= Integer.parseInt(fBook[1][j]); i++) {
						File file = new File(getBaseContext().getFilesDir() + "/" + fBook[0][j] + "_" + i + ".html");
						stcakFiles.add(file);
					}
				}
			}
			File fileCheckR = new File(getBaseContext().getFilesDir() + "/r.txt");
			if (fileCheckR.exists()) {

				for (int j = 0; j < rBook[0].length; j++) {
					for (int i = 1; i <= Integer.parseInt(rBook[1][j]); i++) {
						File file = new File(getBaseContext().getFilesDir() + "/" + rBook[0][j] + "_" + i + ".html");
						stcakFiles.add(file);
					}
				}
			}
		}

		System.out.println("hihiohihih");
		InputStream is;
		String str="";
		int size, i, j, index, index_anchor_start, index_anchor_end, anchorId=0, lastanchorId=0, globalCounter=0, chapterCounter=0, noteIndex = 0;
		byte[] buffer;
		String strText = null, strAnchor=null, section=null, sections=null;
		String prefixAnchor="<a name=" ;
		if(totalCount<num)
			for(i=0; i<BOOKS_NUMBER&&totalCount<num; i++)
			{
				for(j=1; j<=lastChapter[i]&&totalCount<num; j++)//starts from 1 since I don't need to search in "tochen" files
				{
					try
					{
						chapterCounter = 0;
						sections="";
						//if(LangWrite==HEBREW||stcakFiles.isEmpty())
						//	is = getAssets().open(chaptersFiles[i][j]);
						//else
//						if(!stcakFiles.isEmpty()) {
//							is = new FileInputStream(stcakFiles.pop());
//							System.out.println("hi wait");
//						}
//
						is = getAssets().open(chaptersFiles[i][j]);
						size = is.available();
						String s;
						buffer = new byte[size];
						if(!stcakFiles.isEmpty()) {
							System.out.println("shilo76236362372382378923237");

							String fi =stcakFiles.peek().getName();
							System.out.println(fi);
							FileReader fr = new FileReader(stcakFiles.pop());  //Creation of File Reader object
							BufferedReader br = new BufferedReader(fr);
							String first;
							first= br.readLine();
							System.out.println(first);
							System.out.println(br.readLine());

							boolean once=true;
							String subject="";
							if(first!=null) {
								first = first.replace("<em>", "");
								first = first.replace("</em>", "");
								subject=first.split("-")[1].split("</h1>")[0];

								if (first.contains(query))
								{
									TextView textView = new TextView(this);
									String sourceString = subject+ "(" + fi.substring(0, fi.length() - 5) + ")";

									textView.setText(Html.fromHtml(sourceString)+"\n");
									//textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
									if (mPrefs.getInt("BlackBackground", 0)==1)
										textView.setTextColor(Color.WHITE);
									else
										textView.setTextColor(Color.BLACK);
									textView.setTextSize(24);
									searchListView.addHeaderView(textView);
									listStrAnchor.add(getBaseContext().getFilesDir()+"/"+fi+":1");
									//querys[totalCount-1]=query;
									listStrName.add("(" + chaptersNames[i][j].split("-")[0] + "," + sections + ")");
									searchFound=true;
								}
							}

							while ((s = br.readLine()) != null)   //Reading Content from the file
							{
								System.out.println("99999999999999999999999999999999999999");
								if (s.contains(query)&&once&&first.contains("<strong>")) {
									System.out.println(s);
									once=false;
									str+=(first + "(" + fi.substring(0, fi.length() - 5) + ")" + ":shilo");
									listStrAnchor.add(getApplicationContext().getFilesDir()+"/"+fi+":1");
									TextView textView = new TextView(this);
									//String sourceString = "<b>" + "[" + chapterCounter + "] " + chaptersNames[i][j] + "</b> " + sections;
									String sourceString = first + "(" + fi.substring(0, fi.length() - 5) + ")";
									textView.setText(Html.fromHtml(sourceString)+"\n");
									System.out.println(sourceString);
									//textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
									if (mPrefs.getInt("BlackBackground", 0)==1)
										textView.setTextColor(Color.WHITE);
									else
										textView.setTextColor(Color.BLACK);
									textView.setTextSize(24);
									searchListView.addHeaderView(textView);
									//querys[totalCount-1]=query;
									listStrName.add("(" + chaptersNames[i][j].split("-")[0] + "," + sections + ")");
									searchFound=true;
								}
							}

						}
						is.read(buffer);
						is.close();
						strText  = new String(buffer);
						try {

							strText = strText.substring(0, strText.indexOf("<div style=\"display:none;\">", 0));
						}
						catch (Exception ex)
						{
							continue;
						}
						index = 0;
						index_anchor_start = 0;
						index_anchor_end = 0;
						noteIndex = strText.indexOf("<div style=\"display:none;\">", 0);
						while(index != (-1)&&totalCount<num)
						{


							index = strText.indexOf(query, index+1);

							if(index != (-1))
							{
								if((noteIndex != -1) && (noteIndex < index))/*find in note*/
								{
									index_anchor_end = strText.lastIndexOf("</a>", index);
									index_anchor_end = strText.lastIndexOf("]", index_anchor_end);
									index_anchor_start = strText.lastIndexOf("[", index_anchor_end) + 1;
									strAnchor = strText.substring (index_anchor_start, index_anchor_end);
									anchorId = Integer.parseInt(strAnchor);//convert the anchor ID from string to int
									section = strAnchor;
									if (sections.indexOf("הערות") == -1)//if this is the first find in note make lastanchorId = -1. otherwise don't do it to prevent mentioning of the same note
									{
										lastanchorId = -1;//to separate the anchor ID if the main text and the notes
										if (sections.compareTo("") == 0)
											section =  "הערות: " + strAnchor;
										else
											section =  " הערות: " + strAnchor;
									}
								}
								else
								{
									index_anchor_start = strText.lastIndexOf(prefixAnchor, index);
									index_anchor_start += prefixAnchor.length()+1;
									index_anchor_end = strText.indexOf("\"", index_anchor_start);
									strAnchor = strText.substring (index_anchor_start, index_anchor_end);
									anchorId = Integer.parseInt(strAnchor);//convert the anchor ID from string to int
									section = convertAnchorIdToSection(anchorId);
								}

								if(chapterCounter==0)/*the first is the link*/
								{
									sections += section;
									if(noteIndex < index)/*find in note*/
										listStrAnchor.add("file:///android_asset/" + chaptersFiles[i][j]+":"+strAnchor);/*if all the results are in notes so the link will be to the first note*/
									else
										listStrAnchor.add("file:///android_asset/" + chaptersFiles[i][j] + "#" + anchorId);
								}

								else if(lastanchorId != anchorId)
								{
									sections += ","+section;
								}

								globalCounter++;
								totalCount++;
								chapterCounter++;
								lastanchorId = anchorId;
							}
						}
						if(chapterCounter > 0)
						{
							if(!sections.contains("הערות")) {

								TextView textView = new TextView(this);
								//String sourceString = "<b>" + "[" + chapterCounter + "] " + chaptersNames[i][j] + "</b> " + sections;
								String sourceString = "<b >" + chaptersNames[i][j].split("-")[1] + "</b>(" + chaptersNames[i][j].split("-")[0] + "," + sections + ")";
								textView.setText(Html.fromHtml(sourceString)+"\n");
								//textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
								if (mPrefs.getInt("BlackBackground", 0)==1)
									textView.setTextColor(Color.WHITE);
								else
									textView.setTextColor(Color.BLACK);
								textView.setTextSize(24);
								searchListView.addHeaderView(textView);
								querys[totalCount-1]=query;
								listStrName.add("(" + chaptersNames[i][j].split("-")[0] + "," + sections + ")");
								searchFound=true;

							}
							else totalCount--;
							//listBookLocation.add(textView.getText().toString());
							//listBookLocation.add("["+chapterCounter+"] "+chaptersNames[i][j]+ " (" + sections+ ")");/*only one item in the list per chapter*/
							//listBookLocation.add(Html.fromHtml(sourceString).toString());/*only one item in the list per chapter*/
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//TextView textView = new TextView(this);
				//textView.setText(query + ": נמצאו "+globalCounter+" תוצאות");
				//textView.setTextSize(30);
				//searchListView.addHeaderView(textView);

				showResults();
			}
		totalCount=0;
		return true;
	}
	public void goSearch()
	{
		EditText searchText= (EditText) findViewById(R.id.title);
		searchFound=false;
		bookFound.setVisibility(View.GONE);

		if(searchListView!=null) {
			for (int i=searchListView.getHeaderViewsCount() ;i>=0;i--)
				searchListView.removeHeaderView(searchListView.getChildAt(i));
			for (int i=searchListView.getFooterViewsCount() ;i>=0;i--)
				searchListView.removeFooterView(searchListView.getChildAt(i));
			searchListView=null;
		}

		query=" "+searchText.getText().toString()+" ";
		queryToSearch=searchText.getText().toString().split("\\(")[0];
		{

			//query = "ו";// for test of the search
//					for (int i=0; i<query.length(); i++)
//					{
//						validQuery = hebCharacter.contains(query.substring(i, i+1));
//						if(validQuery == false)
//						{
//							break;
//						}
//					}

			if(true)
			{
				searchListView = (ListView) findViewById(R.id.list);
				searchListView.setVisibility(View.VISIBLE);
				final ProgressDialog downloadWait = ProgressDialog.show(SearchHelp.this, "", pleaseWait);
				new Thread() {
					public void run() {
						try {
							SearchHelp.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {

										int num=querys.length;
										int temp;
										query=queryToSearch;
										doMySearch(num);

										temp=totalCount;
										if(totalCount<num)
										{
											query=" "+ queryToSearch+",";
											doMySearch(num);
										}
										if(totalCount<num)
										{
											query="'"+ queryToSearch+"'";
											doMySearch(num);
										}
										if(totalCount<num)
										{
											query="\""+ queryToSearch+"\"";
											doMySearch(num);
										}
										if(totalCount<num)
										{
											query=","+queryToSearch+" ";
											doMySearch(num);
										}

										if(totalCount<num)
										{
											query="ה"+queryToSearch+" ";
											//doMySearch(num);
										}
										if(totalCount<num)
										{
											query="ו"+query+" ";
											//doMySearch(num);
										}
										if(totalCount<num)
										{
											query=" "+queryToSearch+"ם";
											//doMySearch(num);
										}

										if(totalCount<num)
										{
											query=" "+queryToSearch+"הם";
											//doMySearch(num);
										}

										if(totalCount<num)
										{
											query=" "+queryToSearch+"יהם";
											//doMySearch(num);
										}
										if(totalCount<num)
										{
											query=queryToSearch;
											doMySearch(num);
										}
										Toast.makeText(getApplicationContext(), ":"+searchListView.getHeaderViewsCount(), Toast.LENGTH_LONG).show();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									showResults();
								}
							});
						} catch (Exception e) {

						}
						downloadWait.dismiss();
					}
				}.start();
				downloadWait.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if(!searchFound) {
							bookFound.setVisibility(View.VISIBLE);
							searchListView.setVisibility(View.GONE);
						}

					}
				});
				//for (int i=searchListView.getHeaderViewsCount() ;i>=0;i--)
				//searchListView.removeHeaderView(searchListView.getChildAt(i));

				searchText.setText("");
				searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					boolean cameFromSearch = false;
					String searchPosition = null;
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						try
						{
							Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
							Intent ourIntent = new Intent(SearchHelp.this, ourClass);

							searchPosition = listStrAnchor.get(position);
							System.out.println("shilo555555555555555555"+searchPosition);
							String searchText=listStrName.get(position);

							sectionsForToast =listStrAnchor.get(position );
							if(searchPosition.contains("asset")) {
								ourIntent.putExtra("cameFromSearch", true);
								ourIntent.putExtra("searchPosition", searchPosition);
								ourIntent.putExtra("sectionsForToast", sectionsForToast);
								ourIntent.putExtra("query", queryToSearch);

							ourIntent.putExtra("searchText",searchText.split("\\(")[1].split(",")[0]);
							boolean thereis=false;

							for (int i = 1; i < 10; i++) {
								if (mPrefs.getString("s" + (i - 1), "").equals(queryToSearch))
									break;
								shPrefEditor.putString("s" + i, mPrefs.getString("s" + (i - 1), ""));
								shPrefEditor.putString("sp" + i, mPrefs.getString("sp" + (i - 1), ""));
								shPrefEditor.putString("st" + i, mPrefs.getString("st" + (i - 1), ""));
							}
							shPrefEditor.putString("s0", queryToSearch);
							shPrefEditor.putString("sp0", searchPosition + "");
							shPrefEditor.putString("st0", searchText.split("\\(")[1].split(",")[0]);
							shPrefEditor.commit();

							if (sectionsForToast.indexOf("הערות:") != -1) {
								sectionsForToast = sectionsForToast.substring(sectionsForToast.indexOf("הערות: ") + 7, sectionsForToast.indexOf(")"));
							} else {
								sectionsForToast = "";
							}
							ourIntent.putExtra("sectionsForToast", sectionsForToast);
							if (sectionsForToast.indexOf("הערות:") != -1) {
								sectionsForToast = sectionsForToast.substring(sectionsForToast.indexOf("הערות: ") + 7, sectionsForToast.indexOf(")"));
							} else {
								sectionsForToast = "";
							}
							ourIntent.putExtra("sectionsForToast", sectionsForToast);
							}
									else{
								int[] book_chapter = new int[2];
								book_chapter[0] = 0xFFFF;
								book_chapter[1] = 0xFFFF;
								ourIntent.putExtra("book_chapter", book_chapter);
								ourIntent.putExtra("forgenLS", true);
								ourIntent.putExtra("searchPosition", searchPosition);
								ourIntent.putExtra("query", queryToSearch);
							}

							startActivity(ourIntent);
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				});
			}
			else
			{
				final Context context = getBaseContext();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("חיפוש לא חוקי");

				// set dialog message
				alertDialogBuilder
						.setMessage("הסימן "+query.substring(i, i+1)+" אינו חוקי")
						.setCancelable(false)
						.setPositiveButton("חזור",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close current activity
								//SearchableActivity.this.finish();
							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		}

	}
	private  void speak()
	{
		Intent intd=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intd.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intd.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intd.putExtra(RecognizerIntent.EXTRA_PROMPT, "do somthing");
		try {
			startActivityForResult(intd,1000);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Toast.makeText(getApplicationContext(), "result.get(0)", Toast.LENGTH_LONG).show();
		switch (requestCode){
			case 1000:{
				if(resultCode==RESULT_OK&&null!=data)
				{
					ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					EditText voiceT=(EditText) findViewById(R.id.title);
					voiceT.setText(result.get(0));
					//Toast.makeText(getApplicationContext(), "result.get(0)", Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	public void showResults()
	{
		ArrayAdapter adapter;
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listBookLocation);
		if(searchListView!=null)
			searchListView.setAdapter(adapter);
		searchListView2.setAdapter(adapter);
	}

	public String convertAnchorIdToSection(int Id)
	{
		switch (Id)
		{
			case 98:
			case 99:
			case 100:
			case 0:
				return "כותרת";
			case 101:
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

	private void fillChaptersFiles()/*list of all assets*/
	{
		/*BRACHOT*/
		chaptersFiles[BRACHOT][0] = "brachot_tochen.html";
		chaptersFiles[BRACHOT][1] = "brachot_1.html";
		chaptersFiles[BRACHOT][2] = "brachot_2.html";
		chaptersFiles[BRACHOT][3] = "brachot_3.html";
		chaptersFiles[BRACHOT][4] = "brachot_4.html";
		chaptersFiles[BRACHOT][5] = "brachot_5.html";
		chaptersFiles[BRACHOT][6] = "brachot_6.html";
		chaptersFiles[BRACHOT][7] = "brachot_7.html";
		chaptersFiles[BRACHOT][8] = "brachot_8.html";
		chaptersFiles[BRACHOT][9] = "brachot_9.html";
		chaptersFiles[BRACHOT][10] = "brachot_10.html";
		chaptersFiles[BRACHOT][11] = "brachot_11.html";
		chaptersFiles[BRACHOT][12] = "brachot_12.html";
		chaptersFiles[BRACHOT][13] = "brachot_13.html";
		chaptersFiles[BRACHOT][14] = "brachot_14.html";
		chaptersFiles[BRACHOT][15] = "brachot_15.html";
		chaptersFiles[BRACHOT][16] = "brachot_16.html";
		chaptersFiles[BRACHOT][17] = "brachot_17.html";
		chaptersFiles[BRACHOT][18] = "brachot_18.html";
		/*HAAMVEHAAREZ*/
		chaptersFiles[HAAMVEHAAREZ][0] = "haamvehaarez_tochen.html";
		chaptersFiles[HAAMVEHAAREZ][1] = "haamvehaarez_1.html";
		chaptersFiles[HAAMVEHAAREZ][2] = "haamvehaarez_2.html";
		chaptersFiles[HAAMVEHAAREZ][3] = "haamvehaarez_3.html";
		chaptersFiles[HAAMVEHAAREZ][4] = "haamvehaarez_4.html";
		chaptersFiles[HAAMVEHAAREZ][5] = "haamvehaarez_5.html";
		chaptersFiles[HAAMVEHAAREZ][6] = "haamvehaarez_6.html";
		chaptersFiles[HAAMVEHAAREZ][7] = "haamvehaarez_7.html";
		chaptersFiles[HAAMVEHAAREZ][8] = "haamvehaarez_8.html";
		chaptersFiles[HAAMVEHAAREZ][9] = "haamvehaarez_9.html";
		chaptersFiles[HAAMVEHAAREZ][10] = "haamvehaarez_10.html";
		chaptersFiles[HAAMVEHAAREZ][11] = "haamvehaarez_11.html";
		/*ZMANIM*/
		chaptersFiles[ZMANIM][0] = "zmanim_tochen.html";
		chaptersFiles[ZMANIM][1] = "zmanim_1.html";
		chaptersFiles[ZMANIM][2] = "zmanim_2.html";
		chaptersFiles[ZMANIM][3] = "zmanim_3.html";
		chaptersFiles[ZMANIM][4] = "zmanim_4.html";
		chaptersFiles[ZMANIM][5] = "zmanim_5.html";
		chaptersFiles[ZMANIM][6] = "zmanim_6.html";
		chaptersFiles[ZMANIM][7] = "zmanim_7.html";
		chaptersFiles[ZMANIM][8] = "zmanim_8.html";
		chaptersFiles[ZMANIM][9] = "zmanim_9.html";
		chaptersFiles[ZMANIM][10] = "zmanim_10.html";
		chaptersFiles[ZMANIM][11] = "zmanim_11.html";
		chaptersFiles[ZMANIM][12] = "zmanim_12.html";
		chaptersFiles[ZMANIM][13] = "zmanim_13.html";
		chaptersFiles[ZMANIM][14] = "zmanim_14.html";
		chaptersFiles[ZMANIM][15] = "zmanim_15.html";
		chaptersFiles[ZMANIM][16] = "zmanim_16.html";
		chaptersFiles[ZMANIM][17] = "zmanim_17.html";
		/*TAHARAT*/
		chaptersFiles[TAHARAT][0] = "taharat_tochen.html";
		chaptersFiles[TAHARAT][1] = "taharat_1.html";
		chaptersFiles[TAHARAT][2] = "taharat_2.html";
		chaptersFiles[TAHARAT][3] = "taharat_3.html";
		chaptersFiles[TAHARAT][4] = "taharat_4.html";
		chaptersFiles[TAHARAT][5] = "taharat_5.html";
		chaptersFiles[TAHARAT][6] = "taharat_6.html";
		chaptersFiles[TAHARAT][7] = "taharat_7.html";
		chaptersFiles[TAHARAT][8] = "taharat_8.html";
		chaptersFiles[TAHARAT][9] = "taharat_9.html";
		chaptersFiles[TAHARAT][10] = "taharat_10.html";
		/*YAMIM*/
		chaptersFiles[YAMIM][0] = "yamim_tochen.html";
		chaptersFiles[YAMIM][1] = "yamim_1.html";
		chaptersFiles[YAMIM][2] = "yamim_2.html";
		chaptersFiles[YAMIM][3] = "yamim_3.html";
		chaptersFiles[YAMIM][4] = "yamim_4.html";
		chaptersFiles[YAMIM][5] = "yamim_5.html";
		chaptersFiles[YAMIM][6] = "yamim_6.html";
		chaptersFiles[YAMIM][7] = "yamim_7.html";
		chaptersFiles[YAMIM][8] = "yamim_8.html";
		chaptersFiles[YAMIM][9] = "yamim_9.html";
		chaptersFiles[YAMIM][10] = "yamim_10.html";
		/*KASHRUT_A*/
		chaptersFiles[KASHRUT_A][0] = "kashrut_a_tochen.html";
		chaptersFiles[KASHRUT_A][1] = "kashrut_1.html";
		chaptersFiles[KASHRUT_A][2] = "kashrut_2.html";
		chaptersFiles[KASHRUT_A][3] = "kashrut_3.html";
		chaptersFiles[KASHRUT_A][4] = "kashrut_4.html";
		chaptersFiles[KASHRUT_A][5] = "kashrut_5.html";
		chaptersFiles[KASHRUT_A][6] = "kashrut_6.html";
		chaptersFiles[KASHRUT_A][7] = "kashrut_7.html";
		chaptersFiles[KASHRUT_A][8] = "kashrut_8.html";
		chaptersFiles[KASHRUT_A][9] = "kashrut_9.html";
		chaptersFiles[KASHRUT_A][10] = "kashrut_10.html";
		chaptersFiles[KASHRUT_A][11] = "kashrut_11.html";
		chaptersFiles[KASHRUT_A][12] = "kashrut_12.html";
		chaptersFiles[KASHRUT_A][13] = "kashrut_13.html";
		chaptersFiles[KASHRUT_A][14] = "kashrut_14.html";
		chaptersFiles[KASHRUT_A][15] = "kashrut_15.html";
		chaptersFiles[KASHRUT_A][16] = "kashrut_16.html";
		chaptersFiles[KASHRUT_A][17] = "kashrut_17.html";
		chaptersFiles[KASHRUT_A][18] = "kashrut_18.html";
		chaptersFiles[KASHRUT_A][19] = "kashrut_19.html";
		/*KASHRUT_B*/
		chaptersFiles[KASHRUT_B][0] = "kashrut_b_tochen.html";
		chaptersFiles[KASHRUT_B][1] = "kashrut_20.html";
		chaptersFiles[KASHRUT_B][2] = "kashrut_21.html";
		chaptersFiles[KASHRUT_B][3] = "kashrut_22.html";
		chaptersFiles[KASHRUT_B][4] = "kashrut_23.html";
		chaptersFiles[KASHRUT_B][5] = "kashrut_24.html";
		chaptersFiles[KASHRUT_B][6] = "kashrut_25.html";
		chaptersFiles[KASHRUT_B][7] = "kashrut_26.html";
		chaptersFiles[KASHRUT_B][8] = "kashrut_27.html";
		chaptersFiles[KASHRUT_B][9] = "kashrut_28.html";
		chaptersFiles[KASHRUT_B][10] = "kashrut_29.html";
		chaptersFiles[KASHRUT_B][11] = "kashrut_30.html";
		chaptersFiles[KASHRUT_B][12] = "kashrut_31.html";
		chaptersFiles[KASHRUT_B][13] = "kashrut_32.html";
		chaptersFiles[KASHRUT_B][14] = "kashrut_33.html";
		chaptersFiles[KASHRUT_B][15] = "kashrut_34.html";
		chaptersFiles[KASHRUT_B][16] = "kashrut_35.html";
		chaptersFiles[KASHRUT_B][17] = "kashrut_36.html";
		chaptersFiles[KASHRUT_B][18] = "kashrut_37.html";
		chaptersFiles[KASHRUT_B][19] = "kashrut_38.html";
		/*LIKUTIM_A*/
		chaptersFiles[LIKUTIM_A][0] = "likutim_a_tochen.html";
		chaptersFiles[LIKUTIM_A][1] = "likutim_a_1.html";
		chaptersFiles[LIKUTIM_A][2] = "likutim_a_2.html";
		chaptersFiles[LIKUTIM_A][3] = "likutim_a_3.html";
		chaptersFiles[LIKUTIM_A][4] = "likutim_a_4.html";
		chaptersFiles[LIKUTIM_A][5] = "likutim_a_5.html";
		chaptersFiles[LIKUTIM_A][6] = "likutim_a_6.html";
		chaptersFiles[LIKUTIM_A][7] = "likutim_a_7.html";
		chaptersFiles[LIKUTIM_A][8] = "likutim_a_8.html";
		chaptersFiles[LIKUTIM_A][9] = "likutim_a_9.html";
		chaptersFiles[LIKUTIM_A][10] = "likutim_a_10.html";
		chaptersFiles[LIKUTIM_A][11] = "likutim_a_11.html";
		chaptersFiles[LIKUTIM_A][12] = "likutim_a_12.html";
		chaptersFiles[LIKUTIM_A][13] = "likutim_a_13.html";
		/*LIKUTIM_B*/
		chaptersFiles[LIKUTIM_B][0] = "likutim_b_tochen.html";
		chaptersFiles[LIKUTIM_B][1] = "likutim_b_1.html";
		chaptersFiles[LIKUTIM_B][2] = "likutim_b_2.html";
		chaptersFiles[LIKUTIM_B][3] = "likutim_b_3.html";
		chaptersFiles[LIKUTIM_B][4] = "likutim_b_4.html";
		chaptersFiles[LIKUTIM_B][5] = "likutim_b_5.html";
		chaptersFiles[LIKUTIM_B][6] = "likutim_b_6.html";
		chaptersFiles[LIKUTIM_B][7] = "likutim_b_7.html";
		chaptersFiles[LIKUTIM_B][8] = "likutim_b_8.html";
		chaptersFiles[LIKUTIM_B][9] = "likutim_b_9.html";
		chaptersFiles[LIKUTIM_B][10] = "likutim_b_10.html";
		chaptersFiles[LIKUTIM_B][11] = "likutim_b_11.html";
		chaptersFiles[LIKUTIM_B][12] = "likutim_b_12.html";
		chaptersFiles[LIKUTIM_B][13] = "likutim_b_13.html";
		chaptersFiles[LIKUTIM_B][14] = "likutim_b_14.html";
		chaptersFiles[LIKUTIM_B][15] = "likutim_b_15.html";
		chaptersFiles[LIKUTIM_B][16] = "likutim_b_16.html";
		chaptersFiles[LIKUTIM_B][17] = "likutim_b_17.html";
		/*MOADIM*/
		chaptersFiles[MOADIM][0] = "moadim_tochen.html";
		chaptersFiles[MOADIM][1] = "moadim_1.html";
		chaptersFiles[MOADIM][2] = "moadim_2.html";
		chaptersFiles[MOADIM][3] = "moadim_3.html";
		chaptersFiles[MOADIM][4] = "moadim_4.html";
		chaptersFiles[MOADIM][5] = "moadim_5.html";
		chaptersFiles[MOADIM][6] = "moadim_6.html";
		chaptersFiles[MOADIM][7] = "moadim_7.html";
		chaptersFiles[MOADIM][8] = "moadim_8.html";
		chaptersFiles[MOADIM][9] = "moadim_9.html";
		chaptersFiles[MOADIM][10] = "moadim_10.html";
		chaptersFiles[MOADIM][11] = "moadim_11.html";
		chaptersFiles[MOADIM][12] = "moadim_12.html";
		chaptersFiles[MOADIM][13] = "moadim_13.html";
		/*MISHPACHA*/
		chaptersFiles[MISHPACHA][0] = "mishpacha_tochen.html";
		chaptersFiles[MISHPACHA][1] = "mishpacha_1.html";
		chaptersFiles[MISHPACHA][2] = "mishpacha_2.html";
		chaptersFiles[MISHPACHA][3] = "mishpacha_3.html";
		chaptersFiles[MISHPACHA][4] = "mishpacha_4.html";
		chaptersFiles[MISHPACHA][5] = "mishpacha_5.html";
		chaptersFiles[MISHPACHA][6] = "mishpacha_6.html";
		chaptersFiles[MISHPACHA][7] = "mishpacha_7.html";
		chaptersFiles[MISHPACHA][8] = "mishpacha_8.html";
		chaptersFiles[MISHPACHA][9] = "mishpacha_9.html";
		chaptersFiles[MISHPACHA][10] = "mishpacha_10.html";
		/*SUCOT*/
		chaptersFiles[SUCOT][0] = "sucot_tochen.html";
		chaptersFiles[SUCOT][1] = "sucot_1.html";
		chaptersFiles[SUCOT][2] = "sucot_2.html";
		chaptersFiles[SUCOT][3] = "sucot_3.html";
		chaptersFiles[SUCOT][4] = "sucot_4.html";
		chaptersFiles[SUCOT][5] = "sucot_5.html";
		chaptersFiles[SUCOT][6] = "sucot_6.html";
		chaptersFiles[SUCOT][7] = "sucot_7.html";
		chaptersFiles[SUCOT][8] = "sucot_8.html";
		/*PESACH*/
		chaptersFiles[PESACH][0] = "pesach_tochen.html";
		chaptersFiles[PESACH][1] = "pesach_1.html";
		chaptersFiles[PESACH][2] = "pesach_2.html";
		chaptersFiles[PESACH][3] = "pesach_3.html";
		chaptersFiles[PESACH][4] = "pesach_4.html";
		chaptersFiles[PESACH][5] = "pesach_5.html";
		chaptersFiles[PESACH][6] = "pesach_6.html";
		chaptersFiles[PESACH][7] = "pesach_7.html";
		chaptersFiles[PESACH][8] = "pesach_8.html";
		chaptersFiles[PESACH][9] = "pesach_9.html";
		chaptersFiles[PESACH][10] = "pesach_10.html";
		chaptersFiles[PESACH][11] = "pesach_11.html";
		chaptersFiles[PESACH][12] = "pesach_12.html";
		chaptersFiles[PESACH][13] = "pesach_13.html";
		chaptersFiles[PESACH][14] = "pesach_14.html";
		chaptersFiles[PESACH][15] = "pesach_15.html";
		chaptersFiles[PESACH][16] = "pesach_16.html";
		/*SHVIIT*/
		chaptersFiles[SHVIIT][0] = "shviit_tochen.html";
		chaptersFiles[SHVIIT][1] = "shviit_1.html";
		chaptersFiles[SHVIIT][2] = "shviit_2.html";
		chaptersFiles[SHVIIT][3] = "shviit_3.html";
		chaptersFiles[SHVIIT][4] = "shviit_4.html";
		chaptersFiles[SHVIIT][5] = "shviit_5.html";
		chaptersFiles[SHVIIT][6] = "shviit_6.html";
		chaptersFiles[SHVIIT][7] = "shviit_7.html";
		chaptersFiles[SHVIIT][8] = "shviit_8.html";
		chaptersFiles[SHVIIT][9] = "shviit_9.html";
		chaptersFiles[SHVIIT][10] = "shviit_10.html";
		chaptersFiles[SHVIIT][11] = "shviit_11.html";
		/*SHABAT*/
		chaptersFiles[SHABAT][0] = "shabat_tochen.html";
		chaptersFiles[SHABAT][1] = "shabat_1.html";
		chaptersFiles[SHABAT][2] = "shabat_2.html";
		chaptersFiles[SHABAT][3] = "shabat_3.html";
		chaptersFiles[SHABAT][4] = "shabat_4.html";
		chaptersFiles[SHABAT][5] = "shabat_5.html";
		chaptersFiles[SHABAT][6] = "shabat_6.html";
		chaptersFiles[SHABAT][7] = "shabat_7.html";
		chaptersFiles[SHABAT][8] = "shabat_8.html";
		chaptersFiles[SHABAT][9] = "shabat_9.html";
		chaptersFiles[SHABAT][10] = "shabat_10.html";
		chaptersFiles[SHABAT][11] = "shabat_11.html";
		chaptersFiles[SHABAT][12] = "shabat_12.html";
		chaptersFiles[SHABAT][13] = "shabat_13.html";
		chaptersFiles[SHABAT][14] = "shabat_14.html";
		chaptersFiles[SHABAT][15] = "shabat_15.html";
		chaptersFiles[SHABAT][16] = "shabat_16.html";
		chaptersFiles[SHABAT][17] = "shabat_17.html";
		chaptersFiles[SHABAT][18] = "shabat_18.html";
		chaptersFiles[SHABAT][19] = "shabat_19.html";
		chaptersFiles[SHABAT][20] = "shabat_20.html";
		chaptersFiles[SHABAT][21] = "shabat_21.html";
		chaptersFiles[SHABAT][22] = "shabat_22.html";
		chaptersFiles[SHABAT][23] = "shabat_23.html";
		chaptersFiles[SHABAT][24] = "shabat_24.html";
		chaptersFiles[SHABAT][25] = "shabat_25.html";
		chaptersFiles[SHABAT][26] = "shabat_26.html";
		chaptersFiles[SHABAT][27] = "shabat_27.html";
		chaptersFiles[SHABAT][28] = "shabat_28.html";
		chaptersFiles[SHABAT][29] = "shabat_29.html";
		chaptersFiles[SHABAT][30] = "shabat_30.html";
		/*SIMCHAT*/
		chaptersFiles[SIMCHAT][0] = "simchat_tochen.html";
		chaptersFiles[SIMCHAT][1] = "simchat_1.html";
		chaptersFiles[SIMCHAT][2] = "simchat_2.html";
		chaptersFiles[SIMCHAT][3] = "simchat_3.html";
		chaptersFiles[SIMCHAT][4] = "simchat_4.html";
		chaptersFiles[SIMCHAT][5] = "simchat_5.html";
		chaptersFiles[SIMCHAT][6] = "simchat_6.html";
		chaptersFiles[SIMCHAT][7] = "simchat_7.html";
		chaptersFiles[SIMCHAT][8] = "simchat_8.html";
		chaptersFiles[SIMCHAT][9] = "simchat_9.html";
		chaptersFiles[SIMCHAT][10] = "simchat_10.html";
		/*TEFILA*/
		chaptersFiles[TEFILA][0] = "tefila_tochen.html";
		chaptersFiles[TEFILA][1] = "tefila_1.html";
		chaptersFiles[TEFILA][2] = "tefila_2.html";
		chaptersFiles[TEFILA][3] = "tefila_3.html";
		chaptersFiles[TEFILA][4] = "tefila_4.html";
		chaptersFiles[TEFILA][5] = "tefila_5.html";
		chaptersFiles[TEFILA][6] = "tefila_6.html";
		chaptersFiles[TEFILA][7] = "tefila_7.html";
		chaptersFiles[TEFILA][8] = "tefila_8.html";
		chaptersFiles[TEFILA][9] = "tefila_9.html";
		chaptersFiles[TEFILA][10] = "tefila_10.html";
		chaptersFiles[TEFILA][11] = "tefila_11.html";
		chaptersFiles[TEFILA][12] = "tefila_12.html";
		chaptersFiles[TEFILA][13] = "tefila_13.html";
		chaptersFiles[TEFILA][14] = "tefila_14.html";
		chaptersFiles[TEFILA][15] = "tefila_15.html";
		chaptersFiles[TEFILA][16] = "tefila_16.html";
		chaptersFiles[TEFILA][17] = "tefila_17.html";
		chaptersFiles[TEFILA][18] = "tefila_18.html";
		chaptersFiles[TEFILA][19] = "tefila_19.html";
		chaptersFiles[TEFILA][20] = "tefila_20.html";
		chaptersFiles[TEFILA][21] = "tefila_21.html";
		chaptersFiles[TEFILA][22] = "tefila_22.html";
		chaptersFiles[TEFILA][23] = "tefila_23.html";
		chaptersFiles[TEFILA][24] = "tefila_24.html";
		chaptersFiles[TEFILA][25] = "tefila_25.html";
		chaptersFiles[TEFILA][26] = "tefila_26.html";
		/*TEFILAT_NASHIM*/
		chaptersFiles[TEFILAT_NASHIM][0] = "tefilat_nashim_tochen.html";
		chaptersFiles[TEFILAT_NASHIM][1] = "tefilat_nashim_1.html";
		chaptersFiles[TEFILAT_NASHIM][2] = "tefilat_nashim_2.html";
		chaptersFiles[TEFILAT_NASHIM][3] = "tefilat_nashim_3.html";
		chaptersFiles[TEFILAT_NASHIM][4] = "tefilat_nashim_4.html";
		chaptersFiles[TEFILAT_NASHIM][5] = "tefilat_nashim_5.html";
		chaptersFiles[TEFILAT_NASHIM][6] = "tefilat_nashim_6.html";
		chaptersFiles[TEFILAT_NASHIM][7] = "tefilat_nashim_7.html";
		chaptersFiles[TEFILAT_NASHIM][8] = "tefilat_nashim_8.html";
		chaptersFiles[TEFILAT_NASHIM][9] = "tefilat_nashim_9.html";
		chaptersFiles[TEFILAT_NASHIM][10] = "tefilat_nashim_10.html";
		chaptersFiles[TEFILAT_NASHIM][11] = "tefilat_nashim_11.html";
		chaptersFiles[TEFILAT_NASHIM][12] = "tefilat_nashim_12.html";
		chaptersFiles[TEFILAT_NASHIM][13] = "tefilat_nashim_13.html";
		chaptersFiles[TEFILAT_NASHIM][14] = "tefilat_nashim_14.html";
		chaptersFiles[TEFILAT_NASHIM][15] = "tefilat_nashim_15.html";
		chaptersFiles[TEFILAT_NASHIM][16] = "tefilat_nashim_16.html";
		chaptersFiles[TEFILAT_NASHIM][17] = "tefilat_nashim_17.html";
		chaptersFiles[TEFILAT_NASHIM][18] = "tefilat_nashim_18.html";
		chaptersFiles[TEFILAT_NASHIM][19] = "tefilat_nashim_19.html";
		chaptersFiles[TEFILAT_NASHIM][20] = "tefilat_nashim_20.html";
		chaptersFiles[TEFILAT_NASHIM][21] = "tefilat_nashim_21.html";
		chaptersFiles[TEFILAT_NASHIM][22] = "tefilat_nashim_22.html";
		chaptersFiles[TEFILAT_NASHIM][23] = "tefilat_nashim_23.html";
		chaptersFiles[TEFILAT_NASHIM][24] = "tefilat_nashim_24.html";
		/*HAR_BRACHOT*/
		chaptersFiles[HAR_BRACHOT][0] = "har_brachot_tochen.html";
		chaptersFiles[HAR_BRACHOT][1] = "har_brachot_1.html";
		chaptersFiles[HAR_BRACHOT][2] = "har_brachot_2.html";
		chaptersFiles[HAR_BRACHOT][3] = "har_brachot_3.html";
		chaptersFiles[HAR_BRACHOT][4] = "har_brachot_4.html";
		chaptersFiles[HAR_BRACHOT][5] = "har_brachot_5.html";
		chaptersFiles[HAR_BRACHOT][6] = "har_brachot_6.html";
		chaptersFiles[HAR_BRACHOT][7] = "har_brachot_7.html";
		chaptersFiles[HAR_BRACHOT][8] = "har_brachot_8.html";
		chaptersFiles[HAR_BRACHOT][9] = "har_brachot_9.html";
		chaptersFiles[HAR_BRACHOT][10] = "har_brachot_10.html";
		chaptersFiles[HAR_BRACHOT][11] = "har_brachot_11.html";
		chaptersFiles[HAR_BRACHOT][12] = "har_brachot_12.html";
		chaptersFiles[HAR_BRACHOT][13] = "har_brachot_13.html";
		chaptersFiles[HAR_BRACHOT][14] = "har_brachot_14.html";
		chaptersFiles[HAR_BRACHOT][15] = "har_brachot_15.html";
		chaptersFiles[HAR_BRACHOT][16] = "har_brachot_16.html";
		chaptersFiles[HAR_BRACHOT][17] = "har_brachot_17.html";
		/*HAR_YAMIM*/
		chaptersFiles[HAR_YAMIM][0] = "har_yamim_tochen.html";
		chaptersFiles[HAR_YAMIM][1] = "har_yamim_1.html";
		chaptersFiles[HAR_YAMIM][2] = "har_yamim_2.html";
		chaptersFiles[HAR_YAMIM][3] = "har_yamim_3.html";
		chaptersFiles[HAR_YAMIM][4] = "har_yamim_4.html";
		chaptersFiles[HAR_YAMIM][5] = "har_yamim_5.html";
		chaptersFiles[HAR_YAMIM][6] = "har_yamim_6.html";
		chaptersFiles[HAR_YAMIM][7] = "har_yamim_7.html";
		chaptersFiles[HAR_YAMIM][8] = "har_yamim_8.html";
		chaptersFiles[HAR_YAMIM][9] = "har_yamim_9.html";
		chaptersFiles[HAR_YAMIM][10] = "har_yamim_10.html";
		/*HAR_MOADIM*/
		chaptersFiles[HAR_MOADIM][0] = "har_moadim_tochen.html";
		chaptersFiles[HAR_MOADIM][1] = "har_moadim_1.html";
		chaptersFiles[HAR_MOADIM][2] = "har_moadim_2.html";
		chaptersFiles[HAR_MOADIM][3] = "har_moadim_3.html";
		chaptersFiles[HAR_MOADIM][4] = "har_moadim_4.html";
		chaptersFiles[HAR_MOADIM][5] = "har_moadim_5.html";
		chaptersFiles[HAR_MOADIM][6] = "har_moadim_6.html";
		chaptersFiles[HAR_MOADIM][7] = "har_moadim_7.html";
		chaptersFiles[HAR_MOADIM][8] = "har_moadim_8.html";
		//chaptersFiles[HAR_MOADIM][9] = "har_moadim_9.html"; //currently there is no chapter 9
		chaptersFiles[HAR_MOADIM][9] = "har_moadim_10.html";
		chaptersFiles[HAR_MOADIM][10] = "har_moadim_11.html";
		chaptersFiles[HAR_MOADIM][11] = "har_moadim_12.html";
		chaptersFiles[HAR_MOADIM][12] = "har_moadim_13.html";
		/*HAR_SUCOT*/
		chaptersFiles[HAR_SUCOT][0] = "sucot_tochen.html";
		chaptersFiles[HAR_SUCOT][1] = "har_sucot_1.html";
		chaptersFiles[HAR_SUCOT][2] = "har_sucot_2.html";
		chaptersFiles[HAR_SUCOT][3] = "har_sucot_3.html";
		chaptersFiles[HAR_SUCOT][4] = "har_sucot_4.html";
		chaptersFiles[HAR_SUCOT][5] = "har_sucot_5.html";
		chaptersFiles[HAR_SUCOT][6] = "har_sucot_6.html";
		chaptersFiles[HAR_SUCOT][7] = "har_sucot_7.html";
		chaptersFiles[HAR_SUCOT][8] = "har_sucot_8.html";
		/*HAR_SHABAT*/
		chaptersFiles[HAR_SHABAT][0] = "har_shabat_tochen.html";
		chaptersFiles[HAR_SHABAT][1] = "har_shabat_1.html";
		chaptersFiles[HAR_SHABAT][2] = "har_shabat_2.html";
		chaptersFiles[HAR_SHABAT][3] = "har_shabat_3.html";
		chaptersFiles[HAR_SHABAT][4] = "har_shabat_4.html";
		chaptersFiles[HAR_SHABAT][5] = "har_shabat_5.html";
		chaptersFiles[HAR_SHABAT][6] = "har_shabat_6.html";
		chaptersFiles[HAR_SHABAT][7] = "har_shabat_7.html";
		chaptersFiles[HAR_SHABAT][8] = "har_shabat_8.html";
		chaptersFiles[HAR_SHABAT][9] = "har_shabat_9.html";
		chaptersFiles[HAR_SHABAT][10] = "har_shabat_10.html";
		chaptersFiles[HAR_SHABAT][11] = "har_shabat_11.html";
		chaptersFiles[HAR_SHABAT][12] = "har_shabat_12.html";
		chaptersFiles[HAR_SHABAT][13] = "har_shabat_13.html";
		chaptersFiles[HAR_SHABAT][14] = "har_shabat_14.html";
		chaptersFiles[HAR_SHABAT][15] = "har_shabat_15.html";
		chaptersFiles[HAR_SHABAT][16] = "har_shabat_16.html";
		chaptersFiles[HAR_SHABAT][17] = "har_shabat_17.html";
		chaptersFiles[HAR_SHABAT][18] = "har_shabat_18.html";
		chaptersFiles[HAR_SHABAT][19] = "har_shabat_19.html";
		chaptersFiles[HAR_SHABAT][20] = "har_shabat_20.html";
		chaptersFiles[HAR_SHABAT][21] = "har_shabat_21.html";
		chaptersFiles[HAR_SHABAT][22] = "har_shabat_22.html";
		chaptersFiles[HAR_SHABAT][23] = "har_shabat_23.html";
		chaptersFiles[HAR_SHABAT][24] = "har_shabat_24.html";
		chaptersFiles[HAR_SHABAT][25] = "har_shabat_25.html";
		chaptersFiles[HAR_SHABAT][26] = "har_shabat_26.html";
		chaptersFiles[HAR_SHABAT][27] = "har_shabat_27.html";
		chaptersFiles[HAR_SHABAT][28] = "har_shabat_28.html";
		chaptersFiles[HAR_SHABAT][29] = "har_shabat_29.html";
		chaptersFiles[HAR_SHABAT][30] = "har_shabat_30.html";
		/*HAR_SIMCHAT*/
		chaptersFiles[HAR_SIMCHAT][0] = "har_simchat_tochen.html";
		chaptersFiles[HAR_SIMCHAT][1] = "har_simchat_1.html";
		chaptersFiles[HAR_SIMCHAT][2] = "har_simchat_2.html";
		chaptersFiles[HAR_SIMCHAT][3] = "har_simchat_3.html";
		chaptersFiles[HAR_SIMCHAT][4] = "har_simchat_4.html";
		chaptersFiles[HAR_SIMCHAT][5] = "har_simchat_5.html";
		chaptersFiles[HAR_SIMCHAT][6] = "har_simchat_6.html";
		chaptersFiles[HAR_SIMCHAT][7] = "har_simchat_7.html";
		chaptersFiles[HAR_SIMCHAT][8] = "har_simchat_8.html";
		chaptersFiles[HAR_SIMCHAT][9] = "har_simchat_9.html";
		chaptersFiles[HAR_SIMCHAT][10] = "har_simchat_10.html";
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
		chaptersNames[LIKUTIM_B][12] = "ליקוטים ב: יב - הפסקת הריון";
		chaptersNames[LIKUTIM_B][13] = "ליקוטים ב: יג - הלכות ניתוחי מתים";
		chaptersNames[LIKUTIM_B][14] = "ליקוטים ב: יד - השתלת אברים";
		chaptersNames[LIKUTIM_B][15] = "ליקוטים ב: טו - הלכות הנוטה למות";
		chaptersNames[LIKUTIM_B][16] = "ליקוטים ב: טז - ליקוטים";
		chaptersNames[LIKUTIM_B][17] = "ליקוטים ב: יז - חברה ושליחות";
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
		chaptersNames[HAR_SUCOT][1]  = "הר' סוכות: א -חג הסוכות";
		chaptersNames[HAR_SUCOT][2]  = "הר' סוכות: ב - הלכות סוכה";
		chaptersNames[HAR_SUCOT][3]  = "הר' סוכות: ג - ישיבה בסוכה";
		chaptersNames[HAR_SUCOT][4]  = "הר' סוכות: ד - ארבעת המינים";
		chaptersNames[HAR_SUCOT][5]  = "הר' סוכות: ה - נטילת לולב";
		chaptersNames[HAR_SUCOT][6]  = "הר' סוכות: ו - הושענא רבה";
		chaptersNames[HAR_SUCOT][7]  = "הר' סוכות: ז - שמיני עצרת";
		chaptersNames[HAR_SUCOT][8]  = "הר' סוכות: ח - הקהל";
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
	}


}
