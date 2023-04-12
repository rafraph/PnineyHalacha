package com.rafraph.pnineyHalachaHashalem;





import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity
{
	private HashMap<String,Integer> numbook=new HashMap<>();
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
	private static final int F_TFILA         =52;
	private static final int F_MOADIM        =53;
	private static final int F_SUCOT         =54;
	private static final int F_ZMANIM        =55;
	private static final int F_SIMCHAT       =56;
	private static final int F_PESACH        =57;
	private static final int F_SHABBAT       =58;
	private static final int F_YAMMIM        =59;
	private static final int F_TFILAT_NASHIM =60;
	private static final int BOOKS_NUMBER	= 61;
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
	public  static  int pop=0;
	public static int nowExp=-1;
	private SeekBar seekbar;
	private static int heS=0,heE=23,enS=24,enE=31,esS=32,esE=40,ruS=41,ruE=51,frS=52,frE=60;
	private TextView cb;
	public static Bundle extras;
	private static  boolean  hebDisplay = true;
	private List<String> books = new ArrayList<String>();
	public ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	LinearLayout LinearLayoutListGroup;
	List<String> listDataHeader,listDisplay;
	HashMap<String, List<String>> listDataChild;
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	public int BlackBackground=0, SleepScreen=1, MyLanguage = -1;
	public MenuInflater inflater;
	public ActionBar actionBar;
	public Menu abMenu=null;
	public EditText TextToDecode;
	public Dialog acronymsDialog, newVersionDialog, simchatDialog, languageDialog, booksDownloadDialog;
	String acronymsText;
	public int StartInLastLocation = 1;
	public boolean newVersion = false;
	public Context context;
	public boolean changeL=false;
	public String tochen="";
	public String HneedPr="זקוקה הרשאה",Hmassage="הרשאה זו נצרכת בשביל להוריד  את הספרים",Hconfirm="אשר",Hcancel="סרב";
	public String EnSureDel="books Deleted",EnmassageDel="this book will deleted",EnconfirmDel="confirm",EncancelDel="cancel";
	private int STORAGE_PREMISSION_CODE=1;
	public boolean HomePage;
	//private StorageReference storageRef;
	//private FirebaseStorage storage;
	//private FirebaseAuth mAuth;
	@Override
	public void onBackPressed() {
		//if(nowExp==-1) {  // change to this for only close the inner list and not go to the home page
		if(true) {
			try {
				Class ourClass = null;
				Intent ourIntent;
				ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
				ourIntent = new Intent(MainActivity.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			expListView.collapseGroup(nowExp);
			shPrefEditor.putInt("expList",-1);
			expListView.setSelection(0);
			nowExp=-1;
		}
	}
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		if (mPrefs.getInt("BlackBackground", 0)==0)
//			Toast.makeText(getApplicationContext(), "אין חיבור אינטרנט", Toast.LENGTH_LONG).show();
			context = this;
			mPrefs = getSharedPreferences(PREFS_NAME, 0);
			shPrefEditor = mPrefs.edit();
			BlackBackground = mPrefs.getInt("BlackBackground", 0);
			StartInLastLocation = mPrefs.getInt("StartInLastLocation", 1);
			MyLanguage = mPrefs.getInt("MyLanguage", -1);


			//storage = FirebaseStorage.getInstance();
			// Create a storage reference from our app
			//StorageReference storageRef = storage.getReference();

			//mAuth = FirebaseAuth.getInstance();

			actionBar = getSupportActionBar();
			// get the listview
			expListView = (ExpandableListView) findViewById(R.id.lvExp);
			expListView.setGroupIndicator(null);

			// preparing list data
			prepareListData();
			if (hebDisplay) {
				for (int i = heS; i <= heE; i++)
					listDisplay.add(listDataHeader.get(i));
			}
			ImageView toMain = (ImageView) findViewById(R.id.to_main);
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
						ourIntent = new Intent(MainActivity.this, ourClass);
						startActivity(ourIntent);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
			ImageView menu = (ImageView) findViewById(R.id.menu);

			menu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ContextThemeWrapper ctw = new ContextThemeWrapper(MainActivity.this, R.style.CustomPopupTheme);
					PopupMenu popupMenu = new PopupMenu(ctw, v);
					//popupMenu.

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
										ourIntent = new Intent(MainActivity.this, ourClass);
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
									ourIntent = new Intent(MainActivity.this, ourClass);
									ourIntent.putExtra("homePage", true);
									startActivity(ourIntent);
									break;

								case 1:/*to books*/
									try {
										ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
									ourIntent = new Intent(MainActivity.this, ourClass);
									ourIntent.putExtra("homePage", false);
									startActivity(ourIntent);
									break;

								case 2:/*pninaYomit*/
									try {
										ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
									ourIntent = new Intent(MainActivity.this, ourClass);
									startActivity(ourIntent);
									break;

								case 3:/*search in all books*/
									try {
										ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
									ourIntent = new Intent(MainActivity.this, ourClass);
									startActivity(ourIntent);

									break;

								case 4:/*acronyms*/
									acronymsDecode();
									break;

								case 5:/*feedback*/
									try {
										ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
										ourIntent = new Intent(MainActivity.this, ourClass);
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
										ourIntent = new Intent(MainActivity.this, ourClass);
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
										ourIntent = new Intent(MainActivity.this, ourClass);
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

			if (mPrefs.getBoolean("en_exists", false))
				for (int i = enS; i <= enE; i++)
					listDisplay.add(listDataHeader.get(i));
			if (mPrefs.getBoolean("es_exists", false))
				for (int i = esS; i <= esE; i++)
					listDisplay.add(listDataHeader.get(i));
			if (mPrefs.getBoolean("r_exists", false))
				for (int i = ruS; i <= ruE; i++)
					listDisplay.add(listDataHeader.get(i));
			if (mPrefs.getBoolean("f_exists", false))
				for (int i = frS; i <= frE; i++)
					listDisplay.add(listDataHeader.get(i));
			listAdapter = new ExpandableListAdapter(this, listDisplay, listDataChild);


			// setting list adapter
			expListView.setAdapter(listAdapter);
			expListView.setSelected(true);

			//.ex


			if (BlackBackground == 1) {
				//actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,247,236) ));
				//inflater.inflate(R.menu.tochen_actionbar_black, menu);
				//actionBar.setTitle(Html.fromHtml("<font color=\"white\">" + tochen + "</font>"));
				listAdapter.setTextColor(Color.WHITE);
				//to set the list text color
				expListView.setAdapter(listAdapter);
				expListView.setBackgroundColor(Color.BLACK);
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
				menu = (ImageView) findViewById(R.id.menu);
				menu.setImageResource(R.drawable.ic_action_congif_b);
				main.setBackgroundColor(Color.rgb(120, 1, 1));//to set the list text color
			} else {
				//.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,247,236) ));
				//inflater.inflate(R.menu.tochen_actionbar, menu);
				//actionBar.setTitle(Html.fromHtml("<font color=\"black\">" + tochen + "</font>"));
				listAdapter.setTextColor(Color.BLACK);//to set the list text color
				expListView.setAdapter(listAdapter);//to set the list text color
			}
			extras = getIntent().getExtras();
			int pos = extras.getInt("exp", -1);
			if (pos != -1) {
				expListView.expandGroup(pos);
				nowExp = pos;
				expListView.setSelection(pos);

			}
			expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
					if (nowExp == -1)
						nowExp = groupPosition;
					else {
						if (nowExp == groupPosition) {
							expListView.collapseGroup(nowExp);
							nowExp = -1;
						} else {
							expListView.collapseGroup(nowExp);
							nowExp = groupPosition;
							expListView.expandGroup(groupPosition);
						}
						//expListView.setScrollY(400);
						return true;
					}

					return false;
				}
			});
			// Listview on child click listener
			expListView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
											int groupPosition, int childPosition, long id) {
					// TODO Auto-generated method stub
					pop = 0;


					//Toast.makeText(getApplicationContext(), listDataHeader.indexOf(listDisplay.get(groupPosition)) + " : "
							//+ listDataChild.get(listDataHeader.get(listDataHeader.indexOf(listDisplay.get(groupPosition)))).get(childPosition), Toast.LENGTH_SHORT).show();
					try {
						//this if is for the pnina hayomit.(not ready)

						Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
						Intent ourIntent = new Intent(MainActivity.this, ourClass);

						int[] book_chapter = new int[2];
						book_chapter[0] = listDataHeader.indexOf(listDisplay.get(groupPosition));
						book_chapter[1] = childPosition;
						ourIntent.putExtra("book_chapter", book_chapter);
						//ourIntent.putExtra("readonly", true);
						startActivity(ourIntent);

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					return false;
				}
			});

			/* Choose language*/
			if (MyLanguage == -1) {
				languageDialog(context, 0);
			}
			extras = getIntent().getExtras();
			HomePage = extras.getBoolean("homePage", false);
			if (HomePage)
				languageDialog(context, 1);
			/*display the new features of this version*/
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			String version;
			try {
				version = packageManager.getPackageInfo(packageName, 0).versionName;

				if (mPrefs.getString("Version", "").equals("5.1.14") == false) {
					newVersion = true;
					shPrefEditor.putString("Version", version);
					shPrefEditor.commit();
					newVersionDialog = new Dialog(context);
					newVersionDialog.setContentView(R.layout.new_version);
					newVersionDialog.setTitle("גרסה " + version);

					Button dialogButtonExit = (Button) newVersionDialog.findViewById(R.id.dialogButtonExit);
					// if button is clicked
					dialogButtonExit.setOnClickListener(new OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							newVersionDialog.dismiss();
						}
					});
					newVersionDialog.show();
				}
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
			if (changeL && StartInLastLocation == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && newVersion == false)/*check if book and chapter are 0 so this is the first time the user open the application so don't go to the last location*/ {
				changeL = false;
				goToLastLocation();
			}
		//onCreate

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		abMenu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater = getMenuInflater();




		return true;
	}//onCreateOptionsMenu

	protected void onResume()
	{
		// The activity has become visible (it is now "resumed").
		super.onResume();
		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		supportInvalidateOptionsMenu();//This will dump the current menu and call your activity's onCreateOptionsMenu/onPrepareOptionsMenu methods again to rebuild it.
	}//onResume

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub

		return true;
		//return super.onOptionsItemSelected(item);
	}

	private void showPopupMenuSettings(View v)
	{
		PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
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

		//booksDownload popupMenu.getMenu().add(0,7,7,configHeaders[7]);



		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				switch (item.getItemId())
				{
					case 0:/*settings*/
						languageDialog(context,1);//1 is  for users that not new
						break;
					case 1:/*about*/
						try
						{
							Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.About");
							Intent ourIntent = new Intent(MainActivity.this, ourClass);
							shPrefEditor.putString("where", "MainActivity");
							shPrefEditor.commit();
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
							Intent ourIntent = new Intent(MainActivity.this, ourClass);
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
							Intent ourIntent = new Intent(MainActivity.this, ourClass);
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
						languageDialog(context,1);
						break;


					default:
						break;
				}
				return true;
			}
		});

		popupMenu.show();
	}

	/*Preparing the list data*/
	private void prepareListData()
	{

		listDataHeader = new ArrayList<String>();
		listDisplay = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
			listDataHeader.add("ברכות");
			listDataHeader.add("העם והארץ");
			listDataHeader.add("זמנים");
			listDataHeader.add("טהרת המשפחה");
			listDataHeader.add("ימים נוראים");
			listDataHeader.add("כשרות א - הצומח והחי");
			listDataHeader.add("כשרות ב - המזון והמטבח");
			listDataHeader.add("ליקוטים א");
			listDataHeader.add("ליקוטים ב");
			listDataHeader.add("מועדים");
			listDataHeader.add("משפחה");
			listDataHeader.add("סוכות");
			listDataHeader.add("פסח");
			listDataHeader.add("שביעית ויובל");
			listDataHeader.add("שבת");
			listDataHeader.add("שמחת הבית וברכתו");
			listDataHeader.add("תפילה");
			listDataHeader.add("תפילת נשים");
			listDataHeader.add("הרחבות ברכות");
			listDataHeader.add("הרחבות ימים נוראים");
			listDataHeader.add("הרחבות מועדים");
			listDataHeader.add("הרחבות סוכות");
			listDataHeader.add("הרחבות שבת");
			listDataHeader.add("הרחבות שמחת הבית וברכתו");
			listDataHeader.add("Tefila (en)");
			listDataHeader.add("Pesaĥ (en)");
			listDataHeader.add("Z’manim (en)");
			listDataHeader.add("Laws of Women’s Prayer (en)");
			listDataHeader.add("Laws of Shabbat (en)");
			listDataHeader.add("yammim noraiim (en)");
			listDataHeader.add("moadim (en)");
			listDataHeader.add("simchat habait (en)");
			listDataHeader.add("Shabbat (Es)");
			listDataHeader.add("brachot (Es)");
			listDataHeader.add("moadim (Es)");
			listDataHeader.add("Yamim noraiim (Es)");
			listDataHeader.add("peasch (Es)");
			listDataHeader.add("simchat habait (Es)");
			listDataHeader.add("tfila (Es)");
			listDataHeader.add("tfilat nashim (Es)");
			listDataHeader.add("zmanim(Es)");
			listDataHeader.add("haam vehaarez(ru)");
			listDataHeader.add("shabaat(ru)");
			listDataHeader.add("Yammim Noraiim(ru)");
			listDataHeader.add("sucot(ru)");
			listDataHeader.add("simchat habait(ru)");
			listDataHeader.add("mishpacha(ru)");
			listDataHeader.add("pesach(ru)");
			listDataHeader.add("moadim(ru)");
			listDataHeader.add("tfilat_nashim(ru)");
			listDataHeader.add("tfila(ru)");
			listDataHeader.add("zmanim(ru)");
			listDataHeader.add("La prière d’Israël (fr)");
			listDataHeader.add("moadim(fr)");
			listDataHeader.add("sucot(fr)");
			listDataHeader.add("zmanim(fr)");
			listDataHeader.add("simchat habait(fr)");
			listDataHeader.add("pesach(fr)");
			listDataHeader.add("shabbat(fr)");
			listDataHeader.add("yammim(fr)");
			listDataHeader.add("tfilat nashim(fr)");

		;


		// Adding child data
		List<String> brachot = new ArrayList<String>();
		brachot.add("תוכן מפורט, מבוא, מפתח");
		brachot.add("א - פתיחה");
		brachot.add("ב - נטילת ידיים לסעודה");
		brachot.add("ג - ברכת המוציא");
		brachot.add("ד - ברכת המזון");
		brachot.add("ה - זימון");
		brachot.add("ו - חמשת מיני דגן");
		brachot.add("ז - ברכת היין");
		brachot.add("ח - ברכת הפירות ושהכל");
		brachot.add("ט - כללי ברכה ראשונה");
		brachot.add("י - ברכה אחרונה");
		brachot.add("יא - עיקר וטפל");
		brachot.add("יב - כללי ברכות");
		brachot.add("יג - דרך ארץ");
		brachot.add("יד - ברכת הריח");
		brachot.add("טו - ברכות הראייה");
		brachot.add("טז - ברכת הגומל");
		brachot.add("יז - ברכות ההודאה והשמחה");
		brachot.add("יח - תפילת הדרך");

		List<String> haam = new ArrayList<String>();
		haam.add("תוכן מפורט, מבוא");
		haam.add("א - מעלת הארץ");
		haam.add("ב - קודש וחול ביישוב הארץ");
		haam.add("ג - מצוות יישוב הארץ");
		haam.add("ד - מהלכות צבא ומלחמה");
		haam.add("ה - שמירת הארץ");
		haam.add("ו - מהלכות מדינה");
		haam.add("ז - ערבות הדדית");
		haam.add("ח - עבודה עברית");
		haam.add("ט - זכר למקדש");
		haam.add("י - הלכות גרים");
		haam.add("יא - נספח: תשובות מאת הרב גורן ומרבנים נוספים");

		List<String> zmanim = new ArrayList<String>();
		zmanim.add("תוכן מפורט, מבוא");
		zmanim.add("א - ראש חודש");
		zmanim.add("ב - הלכות ספירת העומר");
		zmanim.add("ג - מנהגי אבילות בספירת העומר");
		zmanim.add("ד - יום העצמאות");
		zmanim.add("ה - לג בעומר");
		zmanim.add("ו - ארבעת צומות החורבן");
		zmanim.add("ז - דיני הצומות הקלים");
		zmanim.add("ח - מנהגי שלושת השבועות");
		zmanim.add("ט - ערב תשעה באב");
		zmanim.add("י - הלכות תשעה באב");
		zmanim.add("יא - ימי החנוכה");
		zmanim.add("יב - הדלקת נרות חנוכה");
		zmanim.add("יג - דיני המקום והזמן");
		zmanim.add("יד - חודש אדר");
		zmanim.add("טו - פורים ומקרא מגילה");
		zmanim.add("טז - מצוות השמחה והחסד");
		zmanim.add("יז - דיני פרזים ומוקפים");

		List<String> taharat = new ArrayList<String>();
		taharat.add("תוכן מפורט, מבוא");
		taharat.add("א - טהרת המשפחה");
		taharat.add("ב - דם וכתם");
		taharat.add("ג - איסורי הרחקה");
		taharat.add("ד - שבעה נקיים");
		taharat.add("ה - טבילת טהרה");
		taharat.add("ו - פרישה ווסתות");
		taharat.add("ז - שאלת חכם ובדיקה רפואית");
		taharat.add("ח - כלה");
		taharat.add("ט - יולדת");
		taharat.add("י - מקוואות");

		List<String> yamim = new ArrayList<String>();
		yamim.add("תוכן מפורט, מבוא");
		yamim.add("א - הדין השכר והעונש");
		yamim.add("ב - סליחות ותפילות");
		yamim.add("ג - ראש השנה");
		yamim.add("ד - מצוות השופר");
		yamim.add("ה - עשרת ימי תשובה");
		yamim.add("ו - יום הכיפורים");
		yamim.add("ז - הלכות יום הכיפורים");
		yamim.add("ח - דיני התענית");
		yamim.add("ט - שאר עינויים");
		yamim.add("י - עבודת יום הכיפורים");

		List<String> kashrut_a = new ArrayList<String>();
		kashrut_a.add("תוכן מפורט, פתח דבר");
		kashrut_a.add("א - חדש");
		kashrut_a.add("ב - ערלה ורבעי");
		kashrut_a.add("ג - כלאי בהמה ואילן");
		kashrut_a.add("ד - כלאי זרעים");
		kashrut_a.add("ה - כלאי הכרם");
		kashrut_a.add("ו - מתנות עניים");
		kashrut_a.add("ז - תרומות ומעשרות");
		kashrut_a.add("ח - החייב והפטור");
		kashrut_a.add("ט - כללי המצווה");
		kashrut_a.add("י - סדר ההפרשה למעשה");
		kashrut_a.add("יא - חלה");
		kashrut_a.add("יב - מצוות התלויות בארץ");
		kashrut_a.add("יג - עצי פרי ובל תשחית");
		kashrut_a.add("יד - אכילת בשר");
		kashrut_a.add("טו - צער בעלי חיים");
		kashrut_a.add("טז - שילוח הקן");
		kashrut_a.add("יז - כשרות בעלי חיים");
		kashrut_a.add("יח - הלכות שחיטה");
		kashrut_a.add("יט - מתנות כהונה מהחי");

		List<String> kashrut_b = new ArrayList<String>();
		kashrut_b.add("תוכן מפורט, פתח דבר, מפתח");
		kashrut_b.add("כ - טריפות");
		kashrut_b.add("כא - חֵלֶב וגיד הנשה וניקור");
		kashrut_b.add("כב - דם והכשרת הבשר");
		kashrut_b.add("כג - שרצים");
		kashrut_b.add("כד - מזון מהחי");
		kashrut_b.add("כה - בשר בחלב");
		kashrut_b.add("כו - דיני ההפסקה");
		kashrut_b.add("כז - הגזירות על מאכלי גויים");
		kashrut_b.add("כח - פת ובישולי גויים");
		kashrut_b.add("כט - יין ומשקאות גויים");
		kashrut_b.add("ל - חלב ומוצריו");
		kashrut_b.add("לא - טבילת כלים");
		kashrut_b.add("לב - כללי הכשרת כלים");
		kashrut_b.add("לג - הכשרת כלים ומטבח");
		kashrut_b.add("לד - דיני תערובות");
		kashrut_b.add("לה - סוגי בליעות");
		kashrut_b.add("לו - סכנות");
		kashrut_b.add("לז - תעשיית המזון");
		kashrut_b.add("לח - נאמנות והשגחה");

		List<String> likutimA = new ArrayList<String>();
		likutimA.add("תוכן מפורט, מבוא");
		likutimA.add("א - הלכות תלמוד תורה");
		likutimA.add("ב - החינוך לתורה");
		likutimA.add("ג - קיום התורה והחינוך");
		likutimA.add("ד - הלכות ספר תורה");
		likutimA.add("ה - מהלכות קריאת התורה");
		likutimA.add("ו - כבוד ספר תורה ושמות קדושים");
		likutimA.add("ז - הלכות בית כנסת");
		likutimA.add("ח - כיפה");
		likutimA.add("ט - מהלכות ציצית");
		likutimA.add("י - מהלכות תפילין");
		likutimA.add("יא - מהלכות מזוזה");
		likutimA.add("יב - הלכות כהנים");
		likutimA.add("יג - שעטנז");

		List<String> likutimB = new ArrayList<String>();
		likutimB.add("תוכן מפורט, מבוא");
		likutimB.add("א - בין אדם לחברו");
		likutimB.add("ב - הלכות אמירת אמת");
		likutimB.add("ג - הלכות גניבת דעת");
		likutimB.add("ד - הלכות גניבה");
		likutimB.add("ה - מצוות הלוואה");
		likutimB.add("ו - הלכות צדקה");
		likutimB.add("ז - הכנסת אורחים");
		likutimB.add("ח - הלכות רוצח ומתאבד");
		likutimB.add("ט - הלכות שמירת הנפש");
		likutimB.add("י - נהיגה זהירה ותפילת הדרך");
		likutimB.add("יא - הלכות הצלת נפשות");
		likutimB.add("יב - הלכות ניתוחי מתים");
		likutimB.add("יג - השתלת אברים");
		likutimB.add("יד - הלכות הנוטה למות");
		likutimB.add("טו - ליקוטים");
		likutimB.add("טז - חברה ושליחות");

		List<String> mishpacha = new ArrayList<String>();
		mishpacha.add("תוכן מפורט, מבוא");
		mishpacha.add("א - כיבוד הורים");
		mishpacha.add("ב - מצוות הנישואין");
		mishpacha.add("ג - שידוכים");
		mishpacha.add("ד - קידושין וכתובה");
		mishpacha.add("ה - החתונה ומנהגיה");
		mishpacha.add("ו - איסורי עריות");
		mishpacha.add("ז - מהלכות צניעות");
		mishpacha.add("ח - ברית מילה");
		mishpacha.add("ט - פדיון הבן");
		mishpacha.add("י - אבלות");

		List<String> moadim = new ArrayList<String>();
		moadim.add("תוכן מפורט, מבוא, מפתח");
		moadim.add("א - פתיחה");
		moadim.add("ב - דיני עשה ביום טוב");
		moadim.add("ג - כללי המלאכות");
		moadim.add("ד - מלאכות המאכלים");
		moadim.add("ה - הבערה כיבוי וחשמל");
		moadim.add("ו - הוצאה ומוקצה");
		moadim.add("ז - מדיני יום טוב");
		moadim.add("ח - עירוב תבשילין");
		moadim.add("ט - יום טוב שני של גלויות");
		moadim.add("י - מצוות חול המועד");
		moadim.add("יא - מלאכת חול המועד");
		moadim.add("יב - היתרי עבודה במועד");
		moadim.add("יג - חג שבועות");

		List<String> sucot = new ArrayList<String>();
		sucot.add("תוכן מפורט, מבוא, מפתח");
		sucot.add("א - חג הסוכות");
		sucot.add("ב - הלכות סוכה");
		sucot.add("ג - ישיבה בסוכה");
		sucot.add("ד - ארבעת המינים");
		sucot.add("ה - נטילת לולב");
		sucot.add("ו - הושענא רבה");
		sucot.add("ז - שמיני עצרת");
		sucot.add("ח - הקהל");

		List<String> pesach = new ArrayList<String>();
		pesach.add("תוכן מפורט, מבוא, מפתח");
		pesach.add("א - משמעות החג");
		pesach.add("ב - כללי איסור חמץ");
		pesach.add("ג - מצוות השבתת חמץ");
		pesach.add("ד - בדיקת חמץ");
		pesach.add("ה - ביטול חמץ וביעורו");
		pesach.add("ו - מכירת חמץ");
		pesach.add("ז - תערובת חמץ");
		pesach.add("ח - מהלכות כשרות לפסח");
		pesach.add("ט - מנהג איסור קטניות");
		pesach.add("י - כללי הגעלת כלים");
		pesach.add("יא - הכשרת המטבח לפסח");
		pesach.add("יב - הלכות מצה");
		pesach.add("יג - הלכות ערב פסח ומנהגיו");
		pesach.add("יד - ערב פסח שחל בשבת");
		pesach.add("טו - ההגדה");
		pesach.add("טז - ליל הסדר");

		List<String> shviit = new ArrayList<String>();
		shviit.add("תוכן מפורט, מבוא, מפתח");
		shviit.add("א - מצוות השביעית");
		shviit.add("ב - מצוות השביתה");
		shviit.add("ג - השמטת הפירות");
		shviit.add("ד - פירות השביעית");
		shviit.add("ה - הזמן המקום והאדם");
		shviit.add("ו - שמיטת כספים");
		shviit.add("ז - היתר המכירה");
		shviit.add("ח - אוצר בית דין");
		shviit.add("ט - קניית פירות בשביעית");
		shviit.add("י - מצוות היובל");
		shviit.add("יא - חזון השביעית");

		List<String> shabat = new ArrayList<String>();
		shabat.add("תוכן מפורט, מבוא, מפתח");
		shabat.add("א - פתיחה");
		shabat.add("ב - הכנות לשבת");
		shabat.add("ג - זמני השבת");
		shabat.add("ד - הדלקת נרות שבת");
		shabat.add("ה - תורה ותפילה בשבת");
		shabat.add("ו - הלכות קידוש");
		shabat.add("ז - סעודות השבת ומלווה מלכה");
		shabat.add("ח - הבדלה ומוצאי שבת");
		shabat.add("ט - כללי המלאכות");
		shabat.add("י - בישול");
		shabat.add("יא - בורר");
		shabat.add("יב - הכנת מאכלים");
		shabat.add("יג - מלאכות הבגד");
		shabat.add("יד - הטיפול בגוף");
		shabat.add("טו - בונה סותר בבית וכלים");
		shabat.add("טז - מבעיר ומכבה");
		shabat.add("יז - חשמל ומכשיריו");
		shabat.add("יח - כותב מוחק וצובע");
		shabat.add("יט - מלאכות שבצומח");
		shabat.add("כ - בעלי חיים");
		shabat.add("כא - הלכות הוצאה");
		shabat.add("כב - צביון השבת");
		shabat.add("כג - מוקצה");
		shabat.add("כד - דיני קטן");
		shabat.add("כה - מלאכת גוי");
		shabat.add("כו - מעשה שבת ולפני עיוור");
		shabat.add("כז - פיקוח נפש וחולה");
		shabat.add("כח - חולה שאינו מסוכן");
		shabat.add("כט - עירובין");
		shabat.add("ל - תחומי שבת");

		List<String> simchat = new ArrayList<String>();
		simchat.add("תוכן מפורט, מבוא, מפתח");
		simchat.add("א - מצוות עונה");
		simchat.add("ב - הלכות עונה");
		simchat.add("ג - קדושה וכוונה");
		simchat.add("ד - שמירת הברית");
		simchat.add("ה - פרו ורבו");
		simchat.add("ו - קשיים ועקרות");
		simchat.add("ז - סריס והשחתה");
		simchat.add("ח - נחמת חשוכי ילדים");
		simchat.add("ט - הפסקת הריון");
		simchat.add("י - האיש והאשה");

		List<String> tefila = new ArrayList<String>();
		tefila.add("תוכן מפורט, מבוא, מפתח");
		tefila.add("א - יסודות הלכות תפילה");
		tefila.add("ב - המניין");
		tefila.add("ג - מקום התפילה");
		tefila.add("ד - החזן וקדיש של אבלים");
		tefila.add("ה - הכנות לתפילה");
		tefila.add("ו - הנוסחים ומנהגי העדות");
		tefila.add("ז - השכמת הבוקר");
		tefila.add("ח - נטילת ידיים שחרית");
		tefila.add("ט - ברכות השחר");
		tefila.add("י - ברכת התורה");
		tefila.add("יא - זמן ק\"ש ותפילת שחרית");
		tefila.add("יב - לקראת תפילת שחרית");
		tefila.add("יג - סדר קרבנות");
		tefila.add("יד - פסוקי דזמרה");
		tefila.add("טו - קריאת שמע");
		tefila.add("טז - ברכות קריאת שמע");
		tefila.add("יז - תפילת עמידה");
		tefila.add("יח - טעויות הזכרות ושכחה");
		tefila.add("יט - חזרת הש\"ץ");
		tefila.add("כ - ברכת כהנים");
		tefila.add("כא - נפילת אפיים ותחנונים");
		tefila.add("כב - מדיני קריאת התורה");
		tefila.add("כג - סיום שחרית ודיני קדיש");
		tefila.add("כד - תפילת מנחה");
		tefila.add("כה - תפילת מעריב");
		tefila.add("כו - קריאת שמע על המיטה");

		List<String> tefilatNashim = new ArrayList<String>();
		tefilatNashim.add("תוכן מפורט, מבוא, מפתח");
		tefilatNashim.add("א - יסודות הלכות תפילה");
		tefilatNashim.add("ב - מצוות תפילה לנשים");
		tefilatNashim.add("ג - טעמי מצוות הנשים");
		tefilatNashim.add("ד - השכמת הבוקר");
		tefilatNashim.add("ה - נטילת ידיים שחרית");
		tefilatNashim.add("ו - ברכות השחר");
		tefilatNashim.add("ז - ברכות התורה");
		tefilatNashim.add("ח - תפילת שחרית והדינים שלפניה");
		tefilatNashim.add("ט - הכנת הגוף");
		tefilatNashim.add("י - הכנת הנפש והלבוש");
		tefilatNashim.add("יא - מקום התפילה");
		tefilatNashim.add("יב - תפילת עמידה");
		tefilatNashim.add("יג - הזכרת גשמים ובקשתם");
		tefilatNashim.add("יד - כבוד התפילה");
		tefilatNashim.add("טו - קרבנות ופסוקי דזמרה");
		tefilatNashim.add("טז - קריאת שמע וברכותיה");
		tefilatNashim.add("יז - התפילות שלאחר עמידה");
		tefilatNashim.add("יח - מנחה וערבית");
		tefilatNashim.add("יט - קריאת שמע על המיטה");
		tefilatNashim.add("כ - מהלכות התפילה במניין");
		tefilatNashim.add("כא - מהלכות בית הכנסת");
		tefilatNashim.add("כב - תפילה וקידוש בשבת");
		tefilatNashim.add("כג - מהלכות חגים ומועדים");
		tefilatNashim.add("כד - נוסחי התפלה ומנהגי העדות");

		List<String> harchavot_moadim = new ArrayList<String>();
		harchavot_moadim.add("תוכן העניינים, פתח דבר, סוגיות מורחבות");
		harchavot_moadim.add("א - פתיחה");
		harchavot_moadim.add("ב - דיני עשה ביום טוב");
		harchavot_moadim.add("ג - כללי המלאכות");
		harchavot_moadim.add("ד - מלאכות המאכלים");
		harchavot_moadim.add("ה - הבערה כיבוי וחשמל");
		harchavot_moadim.add("ו - הוצאה ומוקצה");
		harchavot_moadim.add("ז - מדיני יום טוב");
		harchavot_moadim.add("ח - עירוב תבשילין");
		harchavot_moadim.add("י - מצוות חול המועד");
		harchavot_moadim.add("יא - מלאכת חול המועד");
		harchavot_moadim.add("יב - היתרי עבודה במועד");
		harchavot_moadim.add("יג - חג השבועות");

		List<String> harchavot_sucot = new ArrayList<String>();
		harchavot_sucot.add("תוכן העניינים, פתח דבר, סוגיות מורחבות");
		harchavot_sucot.add("א -חג הסוכות");
		harchavot_sucot.add("ב - הלכות סוכה");
		harchavot_sucot.add("ג - ישיבה בסוכה");
		harchavot_sucot.add("ד - ארבעת המינים");
		harchavot_sucot.add("ה - נטילת לולב");
		harchavot_sucot.add("ו - הושענא רבה");
		harchavot_sucot.add("ז - שמיני עצרת");
		harchavot_sucot.add("ח - הקהל");

		List<String> harchavot_shabat = new ArrayList<String>();
		harchavot_shabat.add("תוכן העניינים, פתח דבר, סוגיות מורחבות");
		harchavot_shabat.add("א - פתיחה");
		harchavot_shabat.add("ב - הכנות לשבת");
		harchavot_shabat.add("ג - זמני השבת");
		harchavot_shabat.add("ד - הדלקת נרות שבת");
		harchavot_shabat.add("ה - תורה ותפילה בשבת");
		harchavot_shabat.add("ו - הלכות קידוש");
		harchavot_shabat.add("ז - סעודות השבת ומלווה מלכה");
		harchavot_shabat.add("ח - הבדלה ומוצאי שבת");
		harchavot_shabat.add("ט - כללי המלאכות");
		harchavot_shabat.add("י - בישול");
		harchavot_shabat.add("יא - בורר");
		harchavot_shabat.add("יב - הכנת מאכלים");
		harchavot_shabat.add("יג - מלאכות הבגד");
		harchavot_shabat.add("יד - הטיפול בגוף");
		harchavot_shabat.add("טו - בונה סותר בבית וכלים");
		harchavot_shabat.add("טז - מבעיר ומכבה");
		harchavot_shabat.add("יז - חשמל ומכשיריו");
		harchavot_shabat.add("יח - כותב מוחק וצובע");
		harchavot_shabat.add("יט - מלאכות שבצומח");
		harchavot_shabat.add("כ - בעלי חיים");
		harchavot_shabat.add("כא - הלכות הוצאה");
		harchavot_shabat.add("כב - צביון השבת");
		harchavot_shabat.add("כג - מוקצה");
		harchavot_shabat.add("כד - דיני קטן");
		harchavot_shabat.add("כה - מלאכת גוי");
		harchavot_shabat.add("כו - מעשה שבת ולפני עיוור");
		harchavot_shabat.add("כז - פיקוח נפש וחולה");
		harchavot_shabat.add("כח - חולה שאינו מסוכן");
		harchavot_shabat.add("כט - עירובין");
		harchavot_shabat.add("ל - תחומי שבת");

		List<String> harchavot_simchat = new ArrayList<String>();
		harchavot_simchat.add("תוכן מפורט, פתח דבר");
		harchavot_simchat.add("א - מצוות עונה");
		harchavot_simchat.add("ב - הלכות עונה");
		harchavot_simchat.add("ג - קדושה וכוונה");
		harchavot_simchat.add("ד - שמירת הברית");
		harchavot_simchat.add("ה - פרו ורבו");
		harchavot_simchat.add("ו - קשיים ועקרות");
		harchavot_simchat.add("ז - סריס והשחתה");
		harchavot_simchat.add("ח - נחמת חשוכי ילדים");
		harchavot_simchat.add("ט - הפסקת הריון");
		harchavot_simchat.add("י - האיש והאשה");

		List<String> harchavot_brachot = new ArrayList<String>();
		harchavot_brachot.add("פתח דבר");
		harchavot_brachot.add("א - פתיחה");
		harchavot_brachot.add("ב - נטילת ידיים לסעודה");
		harchavot_brachot.add("ג - ברכת המוציא");
		harchavot_brachot.add("ד - ברכת המזון");
		harchavot_brachot.add("ה - זימון");
		harchavot_brachot.add("ו - חמשת מיני דגן");
		harchavot_brachot.add("ז - ברכת היין");
		harchavot_brachot.add("ח - ברכת הפירות ושהכל");
		harchavot_brachot.add("ט - כללי ברכה ראשונה");
		harchavot_brachot.add("י - ברכה אחרונה");
		harchavot_brachot.add("יא - עיקר וטפל");
		harchavot_brachot.add("יב - כללי ברכות");
		harchavot_brachot.add("יג - דרך ארץ");
		harchavot_brachot.add("יד - ברכת הריח");
		harchavot_brachot.add("טו - ברכות הראייה");
		harchavot_brachot.add("טז - ברכת הגומל");
		harchavot_brachot.add("יז - ברכות ההודאה והשמחה");

		List<String> harchavot_yamim = new ArrayList<String>();
		harchavot_yamim.add("תוכן מפורט, מבוא");
		harchavot_yamim.add("א - הדין השכר והעונש");
		harchavot_yamim.add("ב - סליחות ותפילות");
		harchavot_yamim.add("ג - ראש השנה");
		harchavot_yamim.add("ד - מצוות השופר");
		harchavot_yamim.add("ה - עשרת ימי תשובה");
		harchavot_yamim.add("ו - יום הכיפורים");
		harchavot_yamim.add("ז - הלכות יום הכיפורים");
		harchavot_yamim.add("ח - דיני התענית");
		harchavot_yamim.add("ט - שאר עינויים");
		harchavot_yamim.add("י - עבודת יום הכיפורים");

		List<String> E_tefila = new ArrayList<String>();
		E_tefila.add("Contents, Introduction, Glossary and Index");
		E_tefila.add("1 - Fundamentals of the Laws of Prayer");
		E_tefila.add("2 - Minyan");
		E_tefila.add("3 - The Place of Prayer");
		E_tefila.add("4 - The Chazan and the Mourner's Kaddish");
		E_tefila.add("5 - Preparations for Prayer");
		E_tefila.add("6 - Nusach: Wording of Prayer");
		E_tefila.add("7 - Waking Up in the Morning");
		E_tefila.add("8 - Washing One’s Hands in the Morning");
		E_tefila.add("9 - Birkot HaShachar – The Morning Blessings");
		E_tefila.add("10 - Birkot HaTorah – The Blessings on the Torah");
		E_tefila.add("11 - The Times of Keriat Shema and Shacharit");
		E_tefila.add("12 - Before the Shacharit Prayer");
		E_tefila.add("13 - Korbanot – The Passages of the Sacrificial Offerings");
		E_tefila.add("14 - Pesukei d’Zimrah");
		E_tefila.add("15 - Keriat Shema");
		E_tefila.add("16 - Birkot Keriat Shema");
		E_tefila.add("17 - The Amidah");
		E_tefila.add("18 - Errors, Additions, and Omissions in the Amidah");
		E_tefila.add("19 - The Chazan’s Repetition of the Amidah");
		E_tefila.add("20 - Birkat Kohanim – The Priestly Blessing");
		E_tefila.add("21 - Nefillat Apayim and the Prayers of Supplication");
		E_tefila.add("22 - Several Laws of Torah Reading");
		E_tefila.add("23 - The Conclusion of Shacharit and the Laws of Kaddish");
		E_tefila.add("24 - The Minchah Prayer");
		E_tefila.add("25 - The Ma’ariv Prayer");
		E_tefila.add("26 - The Bedtime Shema");

		List<String> E_pesach = new ArrayList<String>();
		E_pesach.add("Contents, Introduction, Glossary and Index");
		E_pesach.add("1 - The Meaning of the Holiday");
		E_pesach.add("2 - General Rules of the Prohibition against Ĥametz");
		E_pesach.add("3 - The Mitzva of Getting Rid of Ĥametz");
		E_pesach.add("4 - Bedikat Ĥametz – the Search for Ĥametz");
		E_pesach.add("5 - Bitul and Bi’ur Ĥametz");
		E_pesach.add("6 - Mekhirat Ĥametz – the Sale of Ĥametz");
		E_pesach.add("7 - Ĥametz Mixtures");
		E_pesach.add("8 - Pesaĥ Kashrut");
		E_pesach.add("9 - Kitniyot");
		E_pesach.add("10 - The Principles of Hagalat Kelim");
		E_pesach.add("11 - Koshering the Kitchen");
		E_pesach.add("12 - The Laws of Matza");
		E_pesach.add("13 - The Laws and Customs of Erev Pesaĥ");
		E_pesach.add("14 - When Erev Pesaĥ Falls on Shabbat");
		E_pesach.add("15 - The Hagada");
		E_pesach.add("16 - Seder Night");

		List<String> E_zmanim = new ArrayList<String>();
		E_zmanim.add("Contents");
		E_zmanim.add("1 - Rosh Chodesh");
		E_zmanim.add("2 - The Laws of Counting the Omer");
		E_zmanim.add("3 - Customs of Mourning During the Omer Period");
		E_zmanim.add("4 - Yom HaAtzma’ut, Yom Yerushalayim, and Yom HaZikaron");
		E_zmanim.add("5 - Lag B’Omer");
		E_zmanim.add("6 - The Four Fasts Commemorating the Churban");
		E_zmanim.add("7 - The Laws of the Minor Fasts");
		E_zmanim.add("8 - The Customs of the Three Weeks");
		E_zmanim.add("9 - The Eve of Tish’a B’Av");
		E_zmanim.add("10 - The Laws of Tish’a B’Av");
		E_zmanim.add("11 - Chanukah");
		E_zmanim.add("12 - Lighting the Chanukah Candles");
		E_zmanim.add("13 - Unavailable");
		E_zmanim.add("14 - The Month of Adar");
		E_zmanim.add("15 - Purim and the Reading of the Megillah");

		List<String> E_Women_Prayer = new ArrayList<String>();
		E_Women_Prayer.add("Contents, Abbreviations, Preface, Glossary and Index");
		E_Women_Prayer.add("1 - Fundamentals of the Laws of Prayer");
		E_Women_Prayer.add("2 - The Mitzva of Prayer for Women");
		E_Women_Prayer.add("3 - The Reasons behind Women's Mitzvot");
		E_Women_Prayer.add("4 - Waking Up in the Morning");
		E_Women_Prayer.add("5 - Netilat Yadayim in the Morning");
		E_Women_Prayer.add("6 - Birkhot Ha-shaĥar – The Morning Blessings");
		E_Women_Prayer.add("7 - Birkhot Ha-Torah – The Blessings on the Torah");
		E_Women_Prayer.add("8 - The Shaĥarit Prayer and the Laws Prior to its Recitation");
		E_Women_Prayer.add("9 - Preparing the Body for Prayer");
		E_Women_Prayer.add("10 - Mental Preparation and Proper Attire");
		E_Women_Prayer.add("11 - The Place of Prayer");
		E_Women_Prayer.add("12 - The Amida");
		E_Women_Prayer.add("13 - Additions, Errors, and Omissions in the Amida");
		E_Women_Prayer.add("14 - Respect for Prayer");
		E_Women_Prayer.add("15 - Korbanot and Pesukei De-zimra");
		E_Women_Prayer.add("16 - Keri’at Shema and its Berakhot");
		E_Women_Prayer.add("17 - The Prayers after the Amida");
		E_Women_Prayer.add("18 - Minĥa and Ma’ariv");
		E_Women_Prayer.add("19 - The Bedtime Shema");
		E_Women_Prayer.add("20 - Praying with a Minyan");
		E_Women_Prayer.add("21 - Some Laws Concerning the Synagogue, Tzitzit, and Tefilin");
		E_Women_Prayer.add("22 - Shabbat Prayer and Kiddush");
		E_Women_Prayer.add("23 - Some Laws Concerning the Holidays and Festivals");
		E_Women_Prayer.add("24 - Prayer Rites (Nusaĥ)  and Customs of Different Communities");

		List<String> E_Shabat = new ArrayList<String>();
		E_Shabat.add("Contents, Introduction, Glossary and Index");
		E_Shabat.add("1 - Introduction");
		E_Shabat.add("2 - Preparing for Shabbat");
		E_Shabat.add("3 - Shabbat Times");
		E_Shabat.add("4 - Lighting Shabbat Candles");
		E_Shabat.add("5 - Torah Study and Prayer on Shabbat");
		E_Shabat.add("6 - Laws of Kiddush");
		E_Shabat.add("7 - Shabbat Meals and Melaveh Malka");
		E_Shabat.add("8 - Havdala and Saturday Night");
		E_Shabat.add("9 - The Principles of the Melakhot");
		E_Shabat.add("10 - Bishul (Cooking)");
		E_Shabat.add("11 - Borer (Separating)");
		E_Shabat.add("12 - Food Preparation");
		E_Shabat.add("13 - Melakhot Pertaining to Clothing");
		E_Shabat.add("14 - Personal Grooming");
		E_Shabat.add("15 - Boneh and Soter ");
		E_Shabat.add("16 - Mav’ir and Mekhabeh");
		E_Shabat.add("17 - Electricity and Electrical Appliances");
		E_Shabat.add("18 - Kotev, Moĥek, and Tzove’a");
		E_Shabat.add("19 - Agricultural Melakhot (Ĥoresh, Zore’a, Kotzer, and Me’amer)");
		E_Shabat.add("20 - Animals");
		E_Shabat.add("21 - Hotza’ah");
		E_Shabat.add("22 - The Spirit of Shabbat");
		E_Shabat.add("23 - Muktzeh");
		E_Shabat.add("24 - Children");
		E_Shabat.add("25 - Melakha Performed by a Non-Jew");
		E_Shabat.add("26 - Ma’aseh Shabbat and Lifnei Iver");
		E_Shabat.add("27 - Sick People and Saving Lives");
		E_Shabat.add("28 - Illness That Is Not Life-Threatening");
		E_Shabat.add("29 - Eruvin");
		E_Shabat.add("30 - Teĥum Shabbat ");
		List<String> e_yammim = new ArrayList<String>();
		e_yammim.add("Contents, Introduction");
		e_yammim.add("1 - Judgment, Reward, and Punishment");
		e_yammim.add("2 - Seliḥot and Prayers");
		e_yammim.add("3 - Rosh Ha-shana");
		e_yammim.add("4 - The Mitzva of Shofar");
		e_yammim.add("5 - The Ten Days of Repentance");
		e_yammim.add("6 - Yom Kippur");
		e_yammim.add("7 - Laws of Yom Kippur");
		e_yammim.add("8 - The Laws of the Fast");
		e_yammim.add("9 - The Other Deprivations");
		e_yammim.add("10 - The Yom Kippur Avoda");
		List<String> e_moadim = new ArrayList<String>();
		e_moadim.add("Contents, Introduction");
		e_moadim.add("1 - Introduction");
		e_moadim.add("2 - Positive Yom Tov Obligations");
		e_moadim.add("3 - The Principles of the Melakhot");
		e_moadim.add("4 - Melakhot Pertaining to Food");
		e_moadim.add("5 - Mav’ir, Mekhabeh, and Electricity");
		e_moadim.add("6 - Hotza’ah and Muktzeh");
		e_moadim.add("7 - Various Laws of Yom Tov");
		e_moadim.add("8 - Eruv Tavshilin");
		e_moadim.add("9 - Yom Tov Sheni");
		e_moadim.add("10 - The Mitzvot of Ḥol Ha-mo’ed");
		e_moadim.add("11 - Melakha on Ḥol Ha-mo’ed");
		e_moadim.add("12 - When Work Is Permitted on Ḥol Ha-mo’ed");
		e_moadim.add("13 - Shavu’ot");
		List<String> e_simchat = new ArrayList<String>();
		e_simchat.add("Contents, Introduction");
		e_simchat.add("1 - The Mitzva of Marital Sexual Relations");
		e_simchat.add("2 - The Laws of Ona");
		e_simchat.add("3 - Sanctity and Intention");
		e_simchat.add("4 - Safeguarding the Covenant of Circumcision");
		e_simchat.add("5 - Procreation");
		e_simchat.add("6 - Complications and Infertility");
		e_simchat.add("7 - Castration and Sterilization");
		e_simchat.add("8 – Consolation for the Childless");
		e_simchat.add("9 - Terminating Pregnancy");


		List<String> F_tefila = new ArrayList<String>();
		F_tefila.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		F_tefila.add("1 - Fondements des lois de la prière");
		F_tefila.add("2 - Le minyan");
		F_tefila.add("3 - Le lieu de la prière");
		F_tefila.add("4 - L’officiant et le Qaddich des endeuillés");
		F_tefila.add("5 - Préparatifs de la prière");
		F_tefila.add("6 - Rituels et coutumes des communautés");
		F_tefila.add("7 - Le lever matinal");
		F_tefila.add("8 - Nétilat yadaïm, l’ablution des mains");
		F_tefila.add("9 - Birkot hacha’har, les bénédictions du matin");
		F_tefila.add("10 - Birkot Hatorah, les bénédictions de la Torah");
		F_tefila.add("11 - Horaires de la lecture du Chéma et de la prière du matin");
		F_tefila.add("12 - A l’approche de la prière de Cha’harit");
		F_tefila.add("13 - Séder qorbanot, le rappel des sacrifices");
		F_tefila.add("14 - Les Versets de louange (Pessouqé dezimra)");
		F_tefila.add("15 - Lecture du Chéma Israël");
		F_tefila.add("16 - Les bénédictions du Chéma (Birkot qriat Chéma) ");
		F_tefila.add("17 - La ‘Amida");
		F_tefila.add("18 - Erreurs et oublis dans la récitation de la ‘Amida");
		F_tefila.add("19 - Répétition de l’officiant (‘hazara)");
		F_tefila.add("20 - La bénédiction sacerdotale (Birkat Cohanim)");
		F_tefila.add("21 - Nefilat apayima et les supplications (Ta’hanounim)");
		F_tefila.add("22 - Résumé des lois de lecture de la Torah (Qriat Hatorah)");
		F_tefila.add("23 - Conclusion de l’office du matin et règles du Qaddich");
		F_tefila.add("24 - L’office de Min’ha");
		F_tefila.add("25 - L’office d’Arvit");
		F_tefila.add("26 - Prière du coucher");

		List<String> f_moadim = new ArrayList<String>();
		f_moadim.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_moadim.add("Chapitre 1 : Introduction");
		f_moadim.add("Chapitre 2 : Mitsvot positives des jours de fête");
		f_moadim.add("Chapitre 3 : Généralités sur les travaux interdits");
		f_moadim.add("Chapitre 4 : Travaux relatifs aux aliments");
		f_moadim.add("Chapitre 5 : Allumage, extinction et électricité");
		f_moadim.add("Chapitre 6 : Port d’objets (hotsaa) et mouqtsé");
		f_moadim.add("Chapitre 7 : Quelques-unes des lois de Yom tov");
		f_moadim.add("Chapitre 8 : Érouv tavchilin");
		f_moadim.add("Chapitre 9 : Second jour de Yom tov en diaspora");
		f_moadim.add("Chapitre 10 : La mitsva de ‘Hol hamo’ed");
		f_moadim.add("Chapitre 11 : Le travail exécuté à ‘Hol hamo’ed");
		f_moadim.add("Chapitre 12 : Cas d’autorisation du travail à ‘Hol hamo’ed");
		f_moadim.add("Chapitre 13 : Chavou’ot");

		List<String> f_sucot = new ArrayList<String>();
		f_sucot.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_sucot.add("Chapitre 1 : La fête de Soukot");
		f_sucot.add("Chapitre 2 : Lois de la souka");
		f_sucot.add("Chapitre 3 : Résider sous la souka");
		f_sucot.add("Chapitre 4 : Les quatre espèces (arba’a minim) ");
		f_sucot.add("Chapitre 5 : La mitsva d’agiter le loulav (nétilat loulav)");
		f_sucot.add("Chapitre 6 : Hocha’na rabba");
		f_sucot.add("Chapitre 7 : Chemini ‘atséret ");
		f_sucot.add("Chapitre 8 : Le Haqhel");

		List<String> f_zmanim = new ArrayList<String>();
		f_zmanim.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_zmanim.add("Chapitre 1 : Roch ‘hodech (la néoménie)");
		f_zmanim.add("Chapitre 2 : Lois du compte de l’omer");
		f_zmanim.add("Chapitre 3 : Usages de deuil pendant l’omer");
		f_zmanim.add("Chapitre 4 : Yom Ha’atsmaout ");
		f_zmanim.add("Chapitre 5 : Lag ba’omer");
		f_zmanim.add("Chapitre 6 : Les quatre jeûnes de deuil");
		f_zmanim.add("Chapitre 7 : Règles des jeûnes légers");
		f_zmanim.add("Chapitre 8 : Coutumes des trois semaines");
		f_zmanim.add("Chapitre 9 : Veille du 9 av");
		f_zmanim.add("Chapitre 10 : Lois du 9 av");
		f_zmanim.add("Chapitre 11 : ‘Hanouka");
		f_zmanim.add("Chapitre 12 : L’allumage des veilleuses de ‘Hanouka");
		f_zmanim.add("Chapitre 13 : Le lieu et le temps de l’allumage");
		f_zmanim.add("Chapitre 14 : Le mois d’adar");
		f_zmanim.add("Chapitre 15 : Pourim et la lecture de la Méguila");
		f_zmanim.add("Chapitre 16 : Mitsvot de la joie et de la bienfaisance");
		f_zmanim.add("Chapitre 17 : Villes ouvertes et villes fortifiées");

		List<String> f_simchat = new ArrayList<String>();
		f_simchat.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_simchat.add("Chapitre 1 : Le devoir conjugal (mitsvat ‘ona)");
		f_simchat.add("Chapitre 2 : Règles de l’union conjugale");
		f_simchat.add("Chapitre 3 : Sainteté et intention");
		f_simchat.add("Chapitre 4 : Préservation de l’alliance ");
		f_simchat.add("Chapitre 5 : Croissez et multiplier");
		f_simchat.add("Chapitre 6 : Difficultés et stérilité");
		f_simchat.add("Chapitre 7 : Castration et mutilation");
		f_simchat.add("Chapitre 8 : Consolation de ceux qui n’ont pas d’enfants");
		f_simchat.add("Chapitre 9 : Interruption de grossesse");
		f_simchat.add("Chapitre 10 : L’homme et la femme");

		List<String> f_pesach = new ArrayList<String>();
		f_pesach.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_pesach.add("Chapitre 1 : Signification de la fête");
		f_pesach.add("Chapitre 2 : L’interdit du ‘hamets");
		f_pesach.add("Chapitre 3 : La mitsva d’éliminer le ‘hamets");
		f_pesach.add("Chapitre 4 : Recherche du ‘hamets ");
		f_pesach.add("Chapitre 5 : Annulation et destruction du ‘hamets");
		f_pesach.add("Chapitre 6 : Vente du ‘hamets");
		f_pesach.add("Chapitre 7 : Le mélange de ‘hamets");
		f_pesach.add("Chapitre 8 : Quelques règles de cacheroute à Pessa’h");
		f_pesach.add("Chapitre 9 : Les kitniot (légumineuses)");
		f_pesach.add("Chapitre 10 : Cachérisation des ustensiles");
		f_pesach.add("Chapitre 11 : Cachérisation de la cuisine en vue de Pessa’h");
		f_pesach.add("Chapitre 12 : Lois de la matsa");
		f_pesach.add("Chapitre 13 : Veille de Pessa’h");
		f_pesach.add("Chapitre 14 : Veille de Pessa’h ayant lieu le Chabbat");
		f_pesach.add("Chapitre 15 : La Haggada");
		f_pesach.add("Chapitre 16 : La soirée du séder");

		List<String> f_shabbat = new ArrayList<String>();
		f_shabbat.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_shabbat.add("Chapitre 1 : Ouverture");
		f_shabbat.add("Chapitre 2 : Préparatifs du Chabbat");
		f_shabbat.add("Chapitre 3 : Horaires du Chabbat");
		f_shabbat.add("Chapitre 4 : Allumage des veilleuses (hadlaqat nérot)");
		f_shabbat.add("Chapitre 5 : Etude de la Torah et prière, le Chabbat");
		f_shabbat.add("Chapitre 6 : Règles du Qidouch");
		f_shabbat.add("Chapitre 7 : Repas de Chabbat et mélavé malka");
		f_shabbat.add("Chapitre 8 : Havdala et issue de Chabbat");
		f_shabbat.add("Chapitre 9 : Généralités sur les travaux interdits (mélakhot)");
		f_shabbat.add("Chapitre 10 : Cuisson (bichoul)");
		f_shabbat.add("Chapitre 11 : Tri (borer)");
		f_shabbat.add("Chapitre 12 : Préparation de la nourriture");
		f_shabbat.add("Chapitre 13 : Travaux relatifs au vêtement");
		f_shabbat.add("Chapitre 14 : Soins corporels");
		f_shabbat.add("Chapitre 15 : Construire, détruire, un immeuble ou un objet");
		f_shabbat.add("Chapitre 16 : Allumer, éteindre un feu");
		f_shabbat.add("Chapitre 17 : Electricité et appareils électriques");
		f_shabbat.add("Chapitre 18 : Ecrire, effacer et teindre");
		f_shabbat.add("Chapitre 19 : Travaux liés aux végétaux");
		f_shabbat.add("Chapitre 20 : Animaux");
		f_shabbat.add("Chapitre 21 : Transfert et port d’objets (hotsaa)");
		f_shabbat.add("Chapitre 22 : Le caractère du Chabbat");
		f_shabbat.add("Chapitre 23 : Le mouqtsé");
		f_shabbat.add("Chapitre 24 : Règles applicables au mineur");
		f_shabbat.add("Chapitre 25 : Travaux exécutés par un non-Juif");
		f_shabbat.add("Chapitre 26 : Travail exécuté pendant Chabbat et interdit d’induire son prochain à la faute");
		f_shabbat.add("Chapitre 27 : Préservation de la vie humaine, cas du malade");
		f_shabbat.add("Chapitre 28 : Malades dont l’état n’est pas dangereux");
		f_shabbat.add("Chapitre 29 : L’érouv : jonction des domaines");
		f_shabbat.add("Chapitre 30 : Zones d’habitation sabbatique (te’houmin)");

		List<String> f_yammim = new ArrayList<String>();
		f_yammim.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		f_yammim.add("Chapitre 1 : Jugement, récompense et punition");
		f_yammim.add("Chapitre 2 : Les Seli’hot et autres prières");
		f_yammim.add("Chapitre 3 : Roch hachana");
		f_yammim.add("Chapitre 4 :La mitsva du chofar");
		f_yammim.add("Chapitre 5 : Les dix jours de pénitence");
		f_yammim.add("Chapitre 6 : Le jour de Kipour");
		f_yammim.add("Chapitre 7 : Lois du jour de Kipour");
		f_yammim.add("Chapitre 8 :Lois du jeûne de Kipour");
		f_yammim.add("Chapitre 9 : Autres abstentions de Kipour");
		f_yammim.add("Chapitre 10 : Le service de Yom Kipour à l’époque du sanctuaire");

		List<String> F_tfilat_nashim = new ArrayList<String>();
		F_tfilat_nashim.add("Table des matières, Préfaces, Avant-propos, Note du traducteur and Index ");
		F_tfilat_nashim.add("1 - Bases des lois de la prière");
		F_tfilat_nashim.add("2 - Les femmes et la mitsva de la prière");
		F_tfilat_nashim.add("3 - Le lieu de la prière");
		F_tfilat_nashim.add("4 - Le lever matinal");
		F_tfilat_nashim.add("5 - Nétilat yadaïm , l’ablution matinale des mains");
		F_tfilat_nashim.add("6 - Birkot hacha’har , les bénédictions du matin");
		F_tfilat_nashim.add("7 - Birkot ha-Torah, les bénédictions de la Torah");
		F_tfilat_nashim.add("8 - Office de Cha’harit et règles qui le précèdent");
		F_tfilat_nashim.add("9 - Préparation corporelle");
		F_tfilat_nashim.add("10 - Préparation spirituelle et vestimentaire");
		F_tfilat_nashim.add("11 - Lieu de la prière");
		F_tfilat_nashim.add("12 - La ‘Amida");
		F_tfilat_nashim.add("13 - Erreurs et oublis dans la récitation de la ‘Amida");
		F_tfilat_nashim.add("14 -  L’Honneur dû à la prière");
		F_tfilat_nashim.add("15 - Qorbanot et Pessouqé dezimra");
		F_tfilat_nashim.add("16 -Le Chéma et ses bénédictions");
		F_tfilat_nashim.add("17 - Prières qui suivent la ‘Amida de Cha’harit");
		F_tfilat_nashim.add("18 -  Min’ha et Arvit");
		F_tfilat_nashim.add("19 -  Prière du coucher Qriat Chéma ‘al hamita");
		F_tfilat_nashim.add("20 -  Quelques règles relatives à la prière en minyan");
		F_tfilat_nashim.add("21 - La synagogue, les tsitsit et les téphilines");
		F_tfilat_nashim.add("22 - Prière et Qidouch de Chabbat");
		F_tfilat_nashim.add("23 - Quelques règles relatives aux fêtes");
		F_tfilat_nashim.add("24 - Rituels et coutumes des communautés");
		List<String> S_shabat = new ArrayList<String>();
		S_shabat.add("Índice Detallado, Prólogo");
		S_shabat.add("1 - Introducción");
		S_shabat.add("2 - Los preparativos previos al Shabat");
		S_shabat.add("3 - Los horarios de Shabat");
		S_shabat.add("4 - El encendido de las velas de Shabat");
		S_shabat.add("5 - Torá y Tefilá (rezos) en Shabat");
		S_shabat.add("6 - Leyes referentes al Kidush");
		S_shabat.add("7 - Las comidas de Shabat y el “Melavé Malká”");
		S_shabat.add("8 - Havdalá y la conclusión del Shabat");
		S_shabat.add("9 - Las reglas generales de la realización de labores (“Melajot”).");
		S_shabat.add("10 - Cocinar");
		S_shabat.add("11 - Seleccionar (“Borer”)");
		S_shabat.add("12 - La preparación de alimentos");
		S_shabat.add("13 - Labores necesarias para la confección de vestimenta");
		S_shabat.add("14 - El cuidado corporal");
		S_shabat.add("15 - \"Construir\" (\"Boné\"), \"demoler\" (\"Soter\") y \"el toque final\" (\"Maké Bepatish\")");
		S_shabat.add("16 - Encender y apagar un fuego");
		S_shabat.add("17 - La electricidad y los artefactos eléctricos");
		S_shabat.add("18 - Escribir (\"Kotev\"), borrar (\"Mojek\") y pintar o colorear (\"Tzovea\"). En este capítulo se explicarán también las siguientes labores: desollar (\"Mafshit\"), curtir (\"Meabed\"), alisar (\"Memajek\") y trazar una línea para efectuar un corte (\"Mesartet\")");
		S_shabat.add("19 - Labores relativas al reino vegetal. En este capítulo se explicarán las siguientes labores: \"Arar\" (\"Joresh\"), \"Sembrar\" (\"Zorea\"), \"Cosechar\" (\"Kotzer\") y \"Apilar\" (\"Me´amer\")");
		S_shabat.add("20 - Animales");
		S_shabat.add("21 - Transportar\" (\"Hotzaá\")");
		S_shabat.add("22 - El carácter del Shabat");
		S_shabat.add("23 - \"Muktzé\"");
		S_shabat.add("24 - Leyes referentes a los niños pequeños (\"Dinei katán\")");
		S_shabat.add("25 - Labores realizadas por un gentil");
		S_shabat.add("26 - Labores realizadas en Shabat y la prohibición de facilitar la transgresión del prójimo (\"lifnei iver\" N. de t. \"no pondrás obstáculo delante del ciego\")");
		S_shabat.add("27 - Casos de peligro inminente de vida (\"pikuaj nefesh\") y reglas referentes a personas enfermas");
		S_shabat.add("28 - Persona enferma que no corre riesgo de vida");
		S_shabat.add("29 - Eruvín");
		S_shabat.add("30 - Las \"áreas (\"tjumim\") del Shabat\"");

		List<String> s_brachot = new ArrayList<String>();
		s_brachot.add("Índice Detallado, Prólogo");
		s_brachot.add("1 - Introducción");
		s_brachot.add("2 - Lavado o ablución de manos previo a la comida");
		s_brachot.add("3 - La bendición de Hamotzí");
		s_brachot.add("4 - Birkat Hamazón (La bendición posterior a la comida)");
		s_brachot.add("5 - La convocatoria para bendecir: el Zimún");
		s_brachot.add("6 - Las cinco especies de cereales.");
		s_brachot.add("7 - La bendición por el vino");
		s_brachot.add("8 - La bendición por los frutos y Shehakol");
		s_brachot.add("9 - Reglas relativas a la bendición anterior a la ingestión (Berajá Rishoná).");
		s_brachot.add("10 - Bendición final (Berajá Ajaroná).");
		s_brachot.add("11 - Principal y secundario (Ykar Vetafel).");
		s_brachot.add("12 - Reglas referentes a las bendiciones");
		s_brachot.add("13 - Aspectos éticos y buenos modales (Derej Eretz).");
		s_brachot.add("14 - Bendición por el aroma");
		s_brachot.add("15 - Bendiciones por la visión de situaciones extraordinarias y conmovedoras");
		s_brachot.add("16 - Bendición de agradecimiento por una salvación (Birkat Hagomel)");
		s_brachot.add("17 - Bendiciones de agradecimiento y por una alegría");
		s_brachot.add("18 - Tefilat Haderej (La plegaria del viajero)");

		List<String> s_moadim = new ArrayList<String>();
		s_moadim.add("Índice Detallado, Prólogo");
		s_moadim.add("1 - Introducción");
		s_moadim.add("2 - Reglas de preceptos positivos en las festividades");
		s_moadim.add("3 - Reglas relativas a las labores");
		s_moadim.add("4 - Las labores de los alimentos");
		s_moadim.add("5 - Encender fuego, apagarlo y la electricidad");
		s_moadim.add("6 - Transportar y Muktzé");
		s_moadim.add("7 - Algunas leyes de Yom Tov");
		s_moadim.add("8 - Eruv Tavshilín");
		s_moadim.add("9 - Segundo día festivo de las diásporas");
		s_moadim.add("10 - Preceptos de Jol HaMo’ed");
		s_moadim.add("11 - La realización de labores durante Jol HaMo’ed");
		s_moadim.add("12 - Permisos para trabajar en Jol HaMo’ed");
		s_moadim.add("13 - Shavu’ot");

		List<String> s_yamim = new ArrayList<String>();
		s_yamim.add("Índice Detallado, Prólogo");
		s_yamim.add("1 – El Juicio Divino, la recompensa y el castigo.");
		s_yamim.add("2 - Selijot, las súplicas y las plegarias");
		s_yamim.add("3 - Rosh HaShaná");
		s_yamim.add("4 -El precepto del Shofar");
		s_yamim.add("5 - Los Diez días del Retorno (teshuvá)");
		s_yamim.add("6 - Yom Kipur");
		s_yamim.add("7 - Reglas (halajot) de Yom Kipur");
		s_yamim.add("8 - Las leyes del ayuno");
		s_yamim.add("9 -  Las otras aflicciones");
		s_yamim.add("10 -  La «Avodá» –la labor sagrada- en el Templo en Yom Kipur");

		List<String> s_pesach = new ArrayList<String>();
		s_pesach.add("Índice Detallado, Prólogo");
		s_pesach.add("1 - El significado de la fiesta");
		s_pesach.add("2 - Las reglas referentes a la prohibición de jametz");
		s_pesach.add("3 - El precepto de cesar el jametz – “hashbatat jametz”");
		s_pesach.add("4 - La revisación del jametz");
		s_pesach.add("5 - La anulación del jametz y su eliminación");
		s_pesach.add("6 - Venta de Jametz");
		s_pesach.add("7 - Mezclas que contienen jametz");
		s_pesach.add("8 - Leyes referentes a la kashrut en Pesaj");
		s_pesach.add("9 - La costumbre de abstenerse de comer legumbres («kitniot»)");
		s_pesach.add("10 - Reglas referentes a la kasherización de utensilios para Pesaj mediante inmersión en agua hirviendo («Hag´alát Kelím»)");
		s_pesach.add("11 - Kasherización de la cocina para Pesaj");
		s_pesach.add("12 - Leyes referentes a la matzá");
		s_pesach.add("13 - Reglas referentes a la víspera de Pesaj y sus costumbres");
		s_pesach.add("14 - Víspera de Pesaj que cae en Shabat");
		s_pesach.add("15 - La Hagadá");
		s_pesach.add("16 - La noche del Seder");

		List<String> s_simchat = new ArrayList<String>();
		s_simchat.add("Índice Detallado, Prólogo");
		s_simchat.add("1 - El precepto de satisfacer sexualmente a la esposa (Oná)");
		s_simchat.add("2 - Reglas referentes al cumplimiento del precepto de Oná");
		s_simchat.add("3 - Santidad e intención (kavaná)");
		s_simchat.add("4 - El cuidado del pacto");
		s_simchat.add("5 - «Creced y multiplicaos» («Prú Urbú»)");
		s_simchat.add("6 - Esterilidad y otras dificultades");
		s_simchat.add("7 -  El caso del eunuco y otro tipo de daños");
		s_simchat.add("8 - Un consuelo para quienes carecen de descendencia");
		s_simchat.add("9 - La interrupción del embarazo");
		s_simchat.add("10 - El hombre y la mujer");

		List<String> s_tfila = new ArrayList<String>();
		s_tfila.add("Índice Detallado, Prólogo");
		s_tfila.add("1 - Las bases de las leyes referentes a la plegaria (Tefilá).");
		s_tfila.add("2 - El «Minián»");
		s_tfila.add("3 - El lugar del rezo.");
		s_tfila.add("4 - El oficiante (jazán) y el «Kadish» de los que están de duelo.");
		s_tfila.add("5 - Los preparativos previos al rezo.");
		s_tfila.add("6 - Las diferentes versiones del rezo y las costumbres de las diversas comunidades.");
		s_tfila.add("7 - Al despertar por la mañana");
		s_tfila.add("8 - Lavado (ablución) de manos matinal.");
		s_tfila.add("9 - Las bendiciones matinales («Birkot Hashajar»)");
		s_tfila.add("10 -  La bendición por la Torá");
		s_tfila.add("11 -  Los horarios de recitado del Shemá y el rezo matinal (Shajarit)");
		s_tfila.add("12 - Antes de Shajarit");
		s_tfila.add("13 - Las ofrendas («Korbanot»)");
		s_tfila.add("14 - Los cánticos de alabanza (Pesukei dezimrá).");
		s_tfila.add("15 - El recitado del Shemá.");
		s_tfila.add("16 -  Las bendiciones del recitado del «Shemá»");
		s_tfila.add("17 - La «Amidá» (el rezo de pie y en silencio).");
		s_tfila.add("18 - Errores, olvidos y menciones");
		s_tfila.add("19 - La repetición de la Amidá por parte del oficiante.");
		s_tfila.add("20 -  La bendición sacerdotal (Birkat Cohanim).");
		s_tfila.add("21 - La inclinación sobre el rostro («Nefilat Apáim») y las súplicas («Tajanunim»).");
		s_tfila.add("22 - La lectura de la Torá.");
		s_tfila.add("23 - La finalización del servicio de Shajarit y las leyes del Kadish.");
		s_tfila.add("24 - El servicio de Minjá.");
		s_tfila.add("25 -  El servicio de Arvit.");
		s_tfila.add("26 -  El recitado del «Shemá» al acostarse");

		List<String> s_tfilat_nashim = new ArrayList<String>();
		s_tfilat_nashim.add("Índice Detallado, Prólogo");
		s_tfilat_nashim.add("1 - Las bases de las leyes referentes a la plegaria (Tefilá)");
		s_tfilat_nashim.add("2 -El precepto de rezar en las mujeres.");
		s_tfilat_nashim.add("3 - El significado de los preceptos de las mujeres.");
		s_tfilat_nashim.add("4 - Al despertar por la mañana.");
		s_tfilat_nashim.add("5 - Lavado (ablución) de manos matinal.");
		s_tfilat_nashim.add("6 - Las bendiciones matinales («Birkot Hashajar»)");
		s_tfilat_nashim.add("7 - Las Bendiciones por la Torá.");
		s_tfilat_nashim.add("8 - El rezo de Shajarit y  reglas previo a su recitado.");
		s_tfilat_nashim.add("9 - La preparación corporal previa al rezo.");
		s_tfilat_nashim.add("10 - Los preparativos emocionales y de indumentaria previa al rezo.");
		s_tfilat_nashim.add("11 - El sitio del rezo.");
		s_tfilat_nashim.add("12 - La «Amidá» (el rezo de pie y en silencio).");
		s_tfilat_nashim.add("13 - Errores, olvidos y menciones.");
		s_tfilat_nashim.add("14 - El respeto por la Tefilá.");
		s_tfilat_nashim.add("15 - Ofrendas y cánticos de alabanza.");
		s_tfilat_nashim.add("16 -  El recitado del «Shemá» y sus bendiciones");
		s_tfilat_nashim.add("17 - Los pasajes posteriores a la Amidá");
		s_tfilat_nashim.add("18 - Minjá y Arvit.");
		s_tfilat_nashim.add("19 - El recitado del «Shemá» previo a dormir.");
		s_tfilat_nashim.add("20 -  Leyes referentes al rezo con «Minián»");
		s_tfilat_nashim.add("21 - Beit Kneset, Tzitzit y Tefilín.");
		s_tfilat_nashim.add("22 - Rezo y Kidush en Shabat");
		s_tfilat_nashim.add("23 - Leyes referentes a las fiestas y días festivos.");
		s_tfilat_nashim.add("24 - Las diferentes versiones del rezo y las costumbres de las diversas comunidades.");

		List<String> s_zmanim = new ArrayList<String>();
		s_zmanim.add("Índice Detallado, Prólogo");
		s_zmanim.add("1 - Rosh Jodesh (Novilunio)");
		s_zmanim.add("2 - Las reglas del Conteo del “Omer”.");
		s_zmanim.add("3 - Costumbres luctuosas durante el período de la Cuenta del “Omer”.");
		s_zmanim.add("4 - Yom Haatzmaut, Yom Yerushalaim y los días de recordación.");
		s_zmanim.add("5 - Lag Ba´Omer.");
		s_zmanim.add("6 - Los cuatro ayunos por la destrucción del Templo.");
		s_zmanim.add("7 - Leyes referentes a los ayunos menores");
		s_zmanim.add("8 - Las costumbres de las «Tres Semanas»");
		s_zmanim.add("9 - Víspera del 9 de Av.");
		s_zmanim.add("10 - Leyes referentes al 9 de Av.");
		s_zmanim.add("11 -  Januca");
		s_zmanim.add("12 - El encendido de las velas de Januca.");
		s_zmanim.add("13 - Leyes referentes a la ubicación de las velas y el horario de su encendido.");
		s_zmanim.add("14 - El mes de Adar");
		s_zmanim.add("15 - Purim y la lectura de la Meguilá (rollo del libro de Esther");
		s_zmanim.add("16 - La alegría y la generosidad como preceptos");
		s_zmanim.add("17 - Purim en ciudades con y sin murallas");

		List<String> r_haam = new ArrayList<String>();
		r_haam.add("Содержание");
		r_haam.add("Глава 1. Величие Земли Израиля");
		r_haam.add("Глава 2. Святое и будничное в заселении Земли Израиля");
		r_haam.add("Глава 3. Заповедь заселения Земли Израиля");
		r_haam.add("Глава 4. Законы, связанные с воинской службой и ведением войны");
		r_haam.add("Глава 5. Сохранение Земли Израиля");
		r_haam.add("Глава 6. Некоторые законы, связанные с еврейским государством");
		r_haam.add("Глава 7. Взаимная ответственность евреев друг за друга");
		r_haam.add("Глава 8. Еврейский труд");
		r_haam.add("Глава 9. Память о Храме");
		r_haam.add("Глава 10. Законы, связанные с переходом в иудаизм и приобщением к еврейскому народу");

		List<String> r_shabat = new ArrayList<String>();
		r_shabat.add("Содержание");
		r_shabat.add("Глава 1. Введение ");
		r_shabat.add("Глава 2. Приготовления к субботе");
		r_shabat.add("Глава 3. Время наступления субботы");
		r_shabat.add("Глава 4. Зажигание субботних свечей");
		r_shabat.add("Глава 5. Изучение Торы и молитва в субботу");
		r_shabat.add("Глава 6. Законы кидуша");
		r_shabat.add("Глава 7. Субботние трапезы и «проводы субботы» (мелаве малка)");
		r_shabat.add("Глава 8. Ѓавдала и исход субботы");
		r_shabat.add("Глава 9. Работы, запрещенные в субботу (мелахот): общие поло-жения");
		r_shabat.add("Глава 10. Приготовление пищи в субботу");
		r_shabat.add("Глава 11. Запрет отбора в субботу (борер)");
		r_shabat.add("Глава 12. Приготовление различных блюд в субботу");
		r_shabat.add("Глава 13. Работы (мелахот), связанные с одеждой");
		r_shabat.add("Глава 14. Уход за телом");
		r_shabat.add("Глава 15. Запреты строительства (боне) и разрушения постро енного (сотер) применительно к дому и предметам домашнего обихода");
		r_shabat.add("Глава 16. Зажигание и тушение огня");
		r_shabat.add("Глава 17. Электричество и электроприборы");
		r_shabat.add("Глава 18. Запреты письма (котев), стирания написанного (мохек) и окрашивания (цовеа)");
		r_shabat.add("Глава 19. Работы (мелахот), связанные с растениями");
		r_shabat.add("Глава 20. Работы (мелахот), связанные с животными");
		r_shabat.add("Глава 21. Законы перенесения предметов из одного владения в другое");
		r_shabat.add("Глава 22. Субботняя атмосфера");
		r_shabat.add("Глава 23. Запрет мукце");
		r_shabat.add("Глава 24. Законы, связанные с детьми");
		r_shabat.add("Глава 25. Работы (мелахот), выполняемые неевреем");
		r_shabat.add("Глава 26. Работа (мелаха), выполненная в субботу, и запрет «пред слепым не клади преткновения»");
		r_shabat.add("Глава 27. Спасение жизни (пикуах нефеш); законы в отношении больного");
		r_shabat.add("Глава 28. Законы в отношении человека, больного неопасной бо- лезнью");
		r_shabat.add("Глава 29. Законы эрува");
		r_shabat.add("Глава 30. Субботний предел");

		List<String> r_yammim = new ArrayList<String>();
		r_yammim.add("Содержание");
		r_yammim.add("Глава 1  Высший суд, награда и наказание  ");
		r_yammim.add("Глава 2  Слихот (молитвы, призывающие к раскаянию) ");
		r_yammim.add("Глава 3  Рош ѓа-Шана   ");
		r_yammim.add("Глава 4  Заповедь трубления в шофар");
		r_yammim.add("Глава 5  Десять дней покаяния");
		r_yammim.add("Глава 6  Йом Кипур");
		r_yammim.add("Глава 7  Законы Йом Кипура  ");
		r_yammim.add("Глава 8  Законы поста в Йом Кипур");
		r_yammim.add("Глава 9  Остальные запреты Йом Кипура");
		r_yammim.add("Глава 10  Храмовое служение в Йом Кипур");

		List<String> r_sucot = new ArrayList<String>();
		r_sucot.add("Содержание");
		r_sucot.add("Глава 1  Праздник Суккот");
		r_sucot.add("Глава 2  Законы сукки");
		r_sucot.add("Глава 3  Заповедь жить в сукке    ");
		r_sucot.add("Глава 4  Четыре вида растений (арбаат ѓа-миним)");
		r_sucot.add("Глава 5  Заповедь лулава (нетилат лулав");
		r_sucot.add("Глава 6  Ѓошана раба – седьмой день праздника Суккот");
		r_sucot.add("Глава 7  Шмини Ацерет");
		r_sucot.add("Глава 8  Ѓакѓель");

		List<String> r_simchat = new ArrayList<String>();
		r_simchat.add("Содержание");
		r_simchat.add("Глава 1. Заповедь супружеской близости (мицват она)");
		r_simchat.add("Глава 2. Законы исполнения заповеди супружеской близости");
		r_simchat.add("Глава 3. Святость и душевный настрой (кавана) при исполнении заповеди супружеской близости");
		r_simchat.add("Глава 4. Соблюдение Священного завета");
		r_simchat.add("Глава 5. Заповедь «плодитесь и размножайтесь» ");
		r_simchat.add("Глава 6. Различные трудности и бесплодие");
		r_simchat.add("Глава 7. Проблемы мужского бесплодия: увечье,кастрация и стерилизация");
		r_simchat.add("Глава 8. Слова поддержки и утешения бездетным парам");
		r_simchat.add("Глава 9. Прерывание беременности ");
		r_simchat.add("Глава 10. Мужчина и женщина");

		List<String> r_mispacha = new ArrayList<String>();
		r_mispacha.add("Содержание");
		r_mispacha.add("Глава 1. Заповедь почтения к родителям ");
		r_mispacha.add("Глава 2. Заповедь вступления в брак");
		r_mispacha.add("Глава 3. Сватовство");
		r_mispacha.add("Глава 4. Кидушин и ктуба");
		r_mispacha.add("Глава 5. Свадебные законы и обычаи");
		r_mispacha.add("Глава 6. Запрещенные интимные связи");
		r_mispacha.add("Глава 7. Законы скромности");
		r_mispacha.add("Глава 8. Заповедь обрезания (Брит мила)");
		r_mispacha.add("Глава 9. Выкуп первенцев");
		r_mispacha.add("Глава 10. Законы траура");


		List<String> r_pesach = new ArrayList<String>();
		r_pesach.add("Содержание");
		r_pesach.add("Глава 1. Смысл праздника Песах");
		r_pesach.add("Глава 2. Правила, связанные с запретом на квасное (хамец)");
		r_pesach.add("Глава 3. Заповедь устранения квасного (биур хамец)");
		r_pesach.add("Глава 4. Проверка владений еврея на наличие квасного (бдикат хамец)");
		r_pesach.add("Глава 5. Аннулирование квасного (битуль хамец) и его уничтожение");
		r_pesach.add("Глава 6. Продажа квасного");
		r_pesach.add("Глава 7. Смесь квасного с пищей, кошерной на Песах (тааровет хамец)");
		r_pesach.add("Глава 8. Некоторые законы кашрута на Песах");
		r_pesach.add("Глава 9. Обычай запрета китнийот в Песах");
		r_pesach.add("Глава 10. Общие правила кошерования посуды к Песаху");
		r_pesach.add("Глава 11. Кошерование кухни к Песаху");
		r_pesach.add("Глава 12. Законы, связанные с мацой");
		r_pesach.add("Глава 13. Законы и обычаи кануна Песаха");
		r_pesach.add("Глава 14. Если канун Песаха выпадает на субботу");
		r_pesach.add("Глава 15. Пасхальная Агада");
		r_pesach.add("Глава 16. Пасхальный Седер");

		List<String> r_moadim = new ArrayList<String>();
		r_moadim.add("Содержание");
		r_moadim.add("Глава 1. Вступление");
		r_moadim.add("Глава 2. Заповеди-предписания (мицвот асэ),действующие в йом тов");
		r_moadim.add("Глава 3. Запрет выполнения созидательной работы (мелаха) в йом тов");
		r_moadim.add("Глава 4. Виды созидательной работы (мелаха),связанной с приготовлением пищи");
		r_moadim.add("Глава 5. Зажигание и тушение огня; использование электричества в йом тов");
		r_moadim.add("Глава 6. Перенесение предметов из одного владения в другое; закон мукце");
		r_moadim.add("ГГлава 7. Некоторые законы йом това");
		r_moadim.add("Глава 8. Эрув тавшилин");
		r_moadim.add("Глава 9. Второй йом тов за пределами Земли Израиля");
		r_moadim.add("Глава 10. Заповеди холь ѓа-моэда");
		r_moadim.add("Глава 11. Созидательная работа (мелаха) в холь ѓа-моэд");
		r_moadim.add("Глава 12. Какая работа разрешена в холь ѓа-моэд");
		r_moadim.add("Глава 13 Шавуот");

		List<String> r_tfilat_nashim = new ArrayList<String>();
		r_tfilat_nashim.add("Содержание");
		r_tfilat_nashim.add("Глава 1  Основы законов молитвы ");
		r_tfilat_nashim.add("Глава 2  Заповедь молитвы для женщин");
		r_tfilat_nashim.add("Глава 3  Смысл заповедей, обязательных для женщин");
		r_tfilat_nashim.add("Глава 4  Утреннее пробуждение");
		r_tfilat_nashim.add("Глава 5  Утреннее омовение рук");
		r_tfilat_nashim.add("Глава 6  Утренние благословения (Биркот ѓа-шахар) ");
		r_tfilat_nashim.add("Глава 7  Благословения Торы (Биркот ѓа-Тора) ");
		r_tfilat_nashim.add("Глава 8  Утренняя молитвенная служба (шахарит) и законы, связанные с подготовкой к ней");
		r_tfilat_nashim.add("Глава 9  Телесная подготовка к молитве ");
		r_tfilat_nashim.add("Глава 10  Подготовка души к молитве; одежда, подобающая молитве  ");
		r_tfilat_nashim.add("Глава 11  Место молитвы ");
		r_tfilat_nashim.add("Глава 12  Молитва амида ");
		r_tfilat_nashim.add("Глава 13  Ошибки при чтении молитвы амида ");
		r_tfilat_nashim.add("Глава 14  Почтительное отношение к молитве ");
		r_tfilat_nashim.add("Глава 15  Отрывки о жертвоприношениях (Корбанот) и «Хвалебные гимны» (Псукей де-зимра) ");
		r_tfilat_nashim.add("Глава 16  Шма Исраэль и сопутствующие благословения ");
		r_tfilat_nashim.add("Глава 17  Молитвы, читаемые после амиды");
		r_tfilat_nashim.add("Глава 18  Послеполуденная и вечерняя молитвы (минха и арвит) ");
		r_tfilat_nashim.add("Глава 19  Чтение Шма Исраэль перед отходом ко сну ");
		r_tfilat_nashim.add("Глава 20  Отдельные законы молитвы в миньяне ");
		r_tfilat_nashim.add("Глава 21  Отдельные законы, связанные с синагогой ");
		r_tfilat_nashim.add("Глава 22  Молитва и кидуш в субботу");
		r_tfilat_nashim.add("Глава 23  Отдельные законы праздников и памятных дней");
		r_tfilat_nashim.add("Глава 24  Молитвенные каноны и обычаи разных общин ");

		List<String> r_tfila = new ArrayList<String>();
		r_tfila.add("Содержание");
		r_tfila.add("Глава 1 Основы законов молитвы ");
		r_tfila.add("Глава 2 Миньян");
		r_tfila.add("Глава 3 Место молитвы");
		r_tfila.add("Глава 4 Кантор и кадиш скорбящих");
		r_tfila.add("Глава 5 Подготовка к молитве");
		r_tfila.add("Глава 6 Молитвенные каноны и обычаи разных общин ");
		r_tfila.add("Глава 7 Утреннее пробуждение");
		r_tfila.add("Глава 8 Утреннее омовение рук");
		r_tfila.add("Глава 9 Утренние благословения (Биркот ѓа-шахар)");
		r_tfila.add("Глава 10 Благословения Торы (Биркот ѓа-Тора)");
		r_tfila.add("Глава 11 Время чтения Шма Исраэль и утренней молитвы ( шахарит)");
		r_tfila.add("Глава 12 Подготовка к утренней молитве");
		r_tfila.add("Глава 13 Порядок жертвоприношений (Корбанот)");
		r_tfila.add("Глава 14 Хвалебные гимны (Псукей де-зимра)");
		r_tfila.add("Глава 15 Шма Исраэль ");
		r_tfila.add("Глава 16 Благословения, сопровождающие чтение Шма Исраэль");
		r_tfila.add("Глава 17 Молитва амида");
		r_tfila.add("Глава 18 Ошибки при чтении молитвы амида");
		r_tfila.add("Глава 19 Повторение кантором молитвы амида");
		r_tfila.add("Глава 20 Благословение народа коѓенами (Биркат коѓаним)");
		r_tfila.add("Глава 21 Таханун и Нефилат апаим");
		r_tfila.add("Глава 22 Некоторые законы чтения Торы");
		r_tfila.add("Глава 23 Окончание утренней молитвы и законы кадиша");
		r_tfila.add("Глава 24 Минха - послеполуденная молитва ");
		r_tfila.add("Глава 25 Арвит - вечерняя молитва ");
		r_tfila.add("Глава 26 Чтение Шма Исраэль перед отходом ко сну");

		List<String> r_zmanim = new ArrayList<String>();
		r_zmanim.add("Содержание");
		r_zmanim.add("Глава 1. Новомесячье (рош ходеш)");
		r_zmanim.add("Глава 2. Законы отсчета омера (сфират ѓа-омер)");
		r_zmanim.add("Глава 3. Траурные обычаи в период отсчета омера");
		r_zmanim.add("Глава 4. День независимости Израиля, День освобождения Иерусалима и дни памяти");
		r_zmanim.add("Глава 5. Лаг ба-омер");
		r_zmanim.add("Глава 6. Четыре поста в память о разрушении Храма");
		r_zmanim.add("Глава 7. Законы малых постов");
		r_zmanim.add("Глава 8. Обычаи трех траурных недель бейн ѓа-мецарим");
		r_zmanim.add("Глава 9. Канун поста 9 ава");
		r_zmanim.add("Глава 10. Законы поста 9 ава");
		r_zmanim.add("Глава 11. Дни Хануки");
		r_zmanim.add("Глава 12. Зажигание ханукальных свечей");
		r_zmanim.add("Глава 13. Законы места и времени");
		r_zmanim.add("Глава 14. Месяц адар");
		r_zmanim.add("Глава 15. Пурим и чтение Свитка Эстер");
		r_zmanim.add("Глава 16. Заповеди радости и милосердия");
		r_zmanim.add("Глава 17. Законы городов, обнесенных и не обнесенных стеной");


			listDataChild.put(listDataHeader.get(BRACHOT), brachot); // Header, Child data
			listDataChild.put(listDataHeader.get(HAAMVEHAAREZ), haam);
			listDataChild.put(listDataHeader.get(ZMANIM), zmanim);
			listDataChild.put(listDataHeader.get(TAHARAT), taharat);
			listDataChild.put(listDataHeader.get(YAMIM), yamim);
			listDataChild.put(listDataHeader.get(KASHRUT_A), kashrut_a);
			listDataChild.put(listDataHeader.get(KASHRUT_B), kashrut_b);
			listDataChild.put(listDataHeader.get(LIKUTIM_A), likutimA);
			listDataChild.put(listDataHeader.get(LIKUTIM_B), likutimB);
			listDataChild.put(listDataHeader.get(MOADIM), moadim);
			listDataChild.put(listDataHeader.get(MISHPACHA), mishpacha);
			listDataChild.put(listDataHeader.get(SUCOT), sucot);
			listDataChild.put(listDataHeader.get(PESACH), pesach);
			listDataChild.put(listDataHeader.get(SHVIIT), shviit);
			listDataChild.put(listDataHeader.get(SHABAT), shabat);
			listDataChild.put(listDataHeader.get(SIMCHAT), simchat);
			listDataChild.put(listDataHeader.get(TEFILA), tefila);
			listDataChild.put(listDataHeader.get(TEFILAT_NASHIM), tefilatNashim);
			listDataChild.put(listDataHeader.get(HAR_BRACHOT), harchavot_brachot);
			listDataChild.put(listDataHeader.get(HAR_YAMIM), harchavot_yamim);
			listDataChild.put(listDataHeader.get(HAR_MOADIM), harchavot_moadim);
			listDataChild.put(listDataHeader.get(HAR_SUCOT), harchavot_sucot);
			listDataChild.put(listDataHeader.get(HAR_SHABAT), harchavot_shabat);
			listDataChild.put(listDataHeader.get(HAR_SIMCHAT), harchavot_simchat);
			listDataChild.put(listDataHeader.get(E_TEFILA), E_tefila);
			listDataChild.put(listDataHeader.get(E_PESACH), E_pesach);
			listDataChild.put(listDataHeader.get(E_ZMANIM), E_zmanim);
			listDataChild.put(listDataHeader.get(E_WOMEN_PRAYER), E_Women_Prayer);
			listDataChild.put(listDataHeader.get(E_SHABAT), E_Shabat);
			listDataChild.put(listDataHeader.get(E_YAMMIM), e_yammim);
			listDataChild.put(listDataHeader.get(E_MOADIM), e_moadim);
			listDataChild.put(listDataHeader.get(E_SIMCHAT), e_simchat);
			listDataChild.put(listDataHeader.get(F_TFILA), F_tefila);
			listDataChild.put(listDataHeader.get(F_MOADIM), f_moadim);
			listDataChild.put(listDataHeader.get(F_SUCOT), f_sucot);
			listDataChild.put(listDataHeader.get(F_ZMANIM), f_zmanim);
			listDataChild.put(listDataHeader.get(F_SIMCHAT), f_simchat);
			listDataChild.put(listDataHeader.get(F_PESACH), f_pesach);
			listDataChild.put(listDataHeader.get(F_SHABBAT), f_shabbat);
			listDataChild.put(listDataHeader.get(F_YAMMIM), f_yammim);
			listDataChild.put(listDataHeader.get(F_TFILAT_NASHIM), F_tfilat_nashim);
			listDataChild.put(listDataHeader.get(S_SHABAT), S_shabat);
			listDataChild.put(listDataHeader.get(S_BRACHOT), s_brachot);
			listDataChild.put(listDataHeader.get(S_MOADIM), s_moadim);
			listDataChild.put(listDataHeader.get(S_YAMIM), s_yamim);
			listDataChild.put(listDataHeader.get(S_PESACH), s_pesach);
			listDataChild.put(listDataHeader.get(S_SIMCHAT), s_simchat);
			listDataChild.put(listDataHeader.get(S_TFILA), s_tfila);
			listDataChild.put(listDataHeader.get(S_TFILAT_NASHIM), s_tfilat_nashim);
			listDataChild.put(listDataHeader.get(S_ZMANIM), s_zmanim);
			listDataChild.put(listDataHeader.get(R_HAAM), r_haam);
			listDataChild.put(listDataHeader.get(R_SHABBAT), r_shabat);
			listDataChild.put(listDataHeader.get(R_YAMMIM), r_yammim);
			listDataChild.put(listDataHeader.get(R_SUCOT), r_sucot);
			listDataChild.put(listDataHeader.get(R_SIMCHAT), r_simchat);
			listDataChild.put(listDataHeader.get(R_MISHPHACHA), r_mispacha);
			listDataChild.put(listDataHeader.get(R_PESACH), r_pesach);
			listDataChild.put(listDataHeader.get(R_MOADIM), r_moadim);
			listDataChild.put(listDataHeader.get(R_TEFILAT_NASHIM), r_tfilat_nashim);
			listDataChild.put(listDataHeader.get(R_TFILA), r_tfila);
			listDataChild.put(listDataHeader.get(R_ZMANIM), r_zmanim);

	}//prepareListData

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
		dialogButtonExit.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				acronymsDialog.dismiss();
			}
		});

		dialogButtonDecode.setOnClickListener(new OnClickListener()
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
		//	if(API < 19)
		//	webSettingsNote.setBuiltInZoomControls(true);

		fontSize = mPrefs.getInt("fontSize", 20);
		webSettingsHascamot.setDefaultFontSize(fontSize);
		int backgroundColor = mPrefs.getInt("BlackBackground", 0);
		webviewHascmot.setBackgroundColor(backgroundColor);
		if(backgroundColor==1)
			webviewHascmot.loadUrl("file:///android_asset/hascamot_white.html");
		else
			webviewHascmot.loadUrl("file:///android_asset/hascamot.html");
		dialog.show();
	}




private void initializeSeekBar()
{

			seekbar.setOnSeekBarChangeListener(
			new SeekBar.OnSeekBarChangeListener() {
				int userSelectedPosition = 0;
				boolean mUserIsSeeking = false;

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					cb.setTextSize(progress);
					shPrefEditor.putInt("fontSize",progress);
					shPrefEditor.commit();

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			});
}
	void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				deleteRecursive(child);

		fileOrDirectory.delete();
	}
	void languageDialog(Context context,int firstLang) {
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		String version;
		try
		{
			version = packageManager.getPackageInfo(packageName, 0).versionName;

			if(mPrefs.getString("Version", "").equals("5.1.14") == false)
			{
				firstLang=0;
			}
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		if(firstLang==0) {
			languageDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			languageDialog.setContentView(R.layout.language);

			TextView welcome =languageDialog.findViewById(R.id.chooseTochen);
			TextView pickBooks =languageDialog.findViewById(R.id.pickBooks);
			switch (Locale.getDefault().getLanguage()) {
				case "en":
					MyLanguage = ENGLISH;
					//welcome.setText("Welcome to the \"Peninei Halakha\" App");
					pickBooks.setText("Select the desired book language to download to your device:");
					HneedPr="Need permission";
					Hmassage="This permission is required to download the books";
					Hconfirm="Confirmation";
					Hcancel="cancelation";

					break;
				case "es":
					MyLanguage = SPANISH;
					//welcome.setText("Bienvenido a la aplicación Pninei Halaja");
					pickBooks.setText("Seleccione el idioma del libro deseado para descargarlo en su dispositivo:");
					HneedPr="Necesito permiso";
					Hmassage="Este permiso es necesario para descargar los libros.";
					Hconfirm="Confirmación";
					Hcancel="cancelación";
					EnSureDel="books Deleted";
					break;
				case "ru":
					MyLanguage = RUSSIAN;
					//welcome.setText("Добро пожаловать в приложение \"Жемчужины Галахи\"");
					pickBooks.setText("Выберите язык книг для загрузки на Ваше устройство:");
					HneedPr="Требуется разрешение";
					Hmassage="Разрешить загрузку книг";
					Hconfirm="Разрешение";
					Hcancel="Отмена";
					break;
				case "fr":
					MyLanguage = FRENCH;
					//welcome.setText("Welcome to the \"Peninei Halakha\" App");
					pickBooks.setText("Select the desired book language to download to your device:");
					HneedPr="Nécessite une autorisation";
					Hmassage="Cette autorisation est nécessaire pour le téléchargement des livres";
					Hconfirm="Confirmation ";
					Hcancel="Annulation";
					break;
				default:
					MyLanguage = HEBREW;
					break;
			}
			shPrefEditor.putInt("MyLanguage", MyLanguage);
			shPrefEditor.commit();

		}
		else
		{
			languageDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			languageDialog.setContentView(R.layout.activity_settings);
			TextView langOf=(TextView) languageDialog.findViewById(R.id.langof);
			TextView chooseTochen=(TextView) languageDialog.findViewById(R.id.chooseTochen);
			TextView chooseSize=(TextView) languageDialog.findViewById(R.id.chooseSize);
			TextView textSizeExm=(TextView) languageDialog.findViewById(R.id.textSizeExm);
			TextView chooseBlack=(TextView) languageDialog.findViewById(R.id.blackScreen);
			TextView chooseLastLoc=(TextView) languageDialog.findViewById(R.id.lastLocation);

			//LinearLayout main=(LinearLayout) findViewById(R.id.lnrOption2);
			//	LinearLayout main3=(LinearLayout) findViewById(R.id.lnrOption3);
			//	LinearLayout main4=(LinearLayout) findViewById(R.id.lnrOption7);
			//	LinearLayout main5=(LinearLayout) findViewById(R.id.lnrOption8);
			RelativeLayout main=(RelativeLayout) languageDialog.findViewById(R.id.layout_root);
			LinearLayout main2=(LinearLayout) languageDialog.findViewById(R.id.main);
			LinearLayout partA=(LinearLayout) languageDialog.findViewById(R.id.lnrOptions);
			LinearLayout partB=(LinearLayout) languageDialog.findViewById(R.id.lnrOption2);
			LinearLayout partC=(LinearLayout) languageDialog.findViewById(R.id.lnrOption5);
			LinearLayout partD=(LinearLayout) languageDialog.findViewById(R.id.lnrOption6);
			LinearLayout partE=(LinearLayout) languageDialog.findViewById(R.id.lnrOption7);
			LinearLayout partF=(LinearLayout) languageDialog.findViewById(R.id.lnrOption8);
			TextView screenOn=(TextView) languageDialog.findViewById(R.id.screenOn);
			TextView brightScreen=(TextView) languageDialog.findViewById(R.id.brightScreen);
			if (mPrefs.getInt("BlackBackground", 0)==1)
			{
				langOf.setTextColor(Color.WHITE);
				chooseBlack.setTextColor(Color.WHITE);
				chooseTochen.setTextColor(Color.WHITE);
				chooseSize.setTextColor(Color.WHITE);
				textSizeExm.setTextColor(Color.WHITE);
				chooseLastLoc.setTextColor(Color.WHITE);
				screenOn.setTextColor(Color.WHITE);
				brightScreen.setTextColor(Color.WHITE);
				main.setBackgroundColor(Color.BLACK);
				main2.setBackgroundColor(Color.BLACK);
				partA.setBackgroundColor(Color.BLACK);
				partB.setBackgroundColor(Color.BLACK);
				partC.setBackgroundColor(Color.BLACK);
				partD.setBackgroundColor(Color.BLACK);
				partE.setBackgroundColor(Color.BLACK);
				partF.setBackgroundColor(Color.BLACK);

				//main3.setBackgroundColor(Color.BLACK);
				//main4.setBackgroundColor(Color.BLACK);
				//main5.setBackgroundColor(Color.BLACK);

			}
		}



		//Button ButtonSetLanguage = (Button) languageDialog.findViewById(R.id.dialogButtonOK);
		//ImageView h_imv=(ImageView) languageDialog.findViewById(R.id.im_h);
		ImageView h_imv_down = (ImageView) languageDialog.findViewById(R.id.too_py);
		ImageView dialog_x = (ImageView) languageDialog.findViewById(R.id.dialog_x);
		//ImageView dialog_x2 = (ImageView) languageDialog.findViewById(R.id.dialog_x2);
		//ImageView r_imv=(ImageView) languageDialog.findViewById(R.id.im_r);
		ImageView r_imv_down = (ImageView) languageDialog.findViewById(R.id.my_marks);
		//ImageView es_imv=(ImageView) languageDialog.findViewById(R.id.im_es);
		ImageView es_imv_down = (ImageView) languageDialog.findViewById(R.id.im_es_down);
		//ImageView en_imv=(ImageView) languageDialog.findViewById(R.id.im_en);
		ImageView en_imv_down = (ImageView) languageDialog.findViewById(R.id.settings);
		//ImageView f_imv=(ImageView) languageDialog.findViewById(R.id.im_f);
		ImageView f_imv_down = (ImageView) languageDialog.findViewById(R.id.im_f_down);
		if(firstLang==1)
		{
			ImageView h_imv=(ImageView) languageDialog.findViewById(R.id.im_h);
			ImageView r_imv=(ImageView) languageDialog.findViewById(R.id.im_r);
			ImageView es_imv=(ImageView) languageDialog.findViewById(R.id.im_es);
			ImageView en_imv=(ImageView) languageDialog.findViewById(R.id.im_en);
			ImageView f_imv=(ImageView) languageDialog.findViewById(R.id.im_f);
			ImageView im_black_screen=(ImageView) languageDialog.findViewById(R.id.im_black_screen);
			ImageView im_white_screen=(ImageView) languageDialog.findViewById(R.id.im_white_screen);
			ImageView im_last_loc=(ImageView) languageDialog.findViewById(R.id.im_last_location);
			ImageView im_screenOn=(ImageView) languageDialog.findViewById(R.id.im_so);
			if (mPrefs.getInt("BlackBackground", 0) == 1)
			{
				im_black_screen.setImageResource(R.drawable.check_fill);
				im_white_screen.setImageResource(R.drawable.check);
				im_black_screen.setTag("3");
				im_white_screen.setTag("4");
			}
			else
			{
				im_black_screen.setImageResource(R.drawable.check);
				im_white_screen.setImageResource(R.drawable.check_fill);
				im_black_screen.setTag("4");
				im_white_screen.setTag("3");
			}
			if (mPrefs.getInt("StartInLastLocation", 1) == 1)
			{
				im_last_loc.setImageResource(R.drawable.checkbox_fill);
				im_last_loc.setTag("3");
			}
			else
			{
				im_last_loc.setImageResource(R.drawable.checkbox);
				im_last_loc.setTag("4");
			}

			if (mPrefs.getInt("ScreenOn", 1) == 1)
			{
				im_screenOn.setImageResource(R.drawable.checkbox_fill);
				im_screenOn.setTag("3");
			}
			else
			{
				im_screenOn.setImageResource(R.drawable.checkbox);
				im_screenOn.setTag("4");
			}

			seekbar = (SeekBar) languageDialog.findViewById(R.id.seekBar6);
			seekbar.setMax(77);
			seekbar.setProgress(mPrefs.getInt("fontSize",20));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				seekbar.setMin(15);
			}
			cb 		= (TextView) languageDialog.findViewById(R.id.textSizeExm);
			cb.setTextSize(mPrefs.getInt("fontSize",20)*2);
			initializeSeekBar();
			TextView langOf=languageDialog.findViewById(R.id.langof);
			TextView chooseTochenLang=languageDialog.findViewById(R.id.chooseTochen);
			TextView chooseSize=languageDialog.findViewById(R.id.chooseSize);
			TextView exm=languageDialog.findViewById(R.id.textSizeExm);
			TextView blackScreen=languageDialog.findViewById(R.id.blackScreen);
			TextView brightScreen=languageDialog.findViewById(R.id.brightScreen);
			TextView screenOn=languageDialog.findViewById(R.id.screenOn);
			TextView lastLoc=languageDialog.findViewById(R.id.lastLocation);
			if (MyLanguage == HEBREW)
				h_imv.setImageResource(R.drawable.h_b_2);
			else if (MyLanguage == RUSSIAN)
			{
				r_imv.setImageResource(R.drawable.r_b_2);
				langOf.setText("Язык приложения");
				chooseTochenLang.setText("Выбор языка книг");
                chooseTochenLang.setPadding(0,0,500,0);
				chooseSize.setText("Выбрать размер текста");
				exm.setText("Жемчужины Галахи");
				screenOn.setText("Темная тема отмена");
				blackScreen.setText("темный");
				brightScreen.setText("яркий   :Режим отображения");
				lastLoc.setText("При загрузке приложения возвращаться на последнее место чтения");
			}
			else if (MyLanguage == SPANISH)
			{
				es_imv.setImageResource(R.drawable.es_b_2);
				langOf.setText("El idioma de la aplicación");
				chooseTochenLang.setText("Selección del contenido de los idiomas en los libros.");
				chooseSize.setText("Establecer tamaño de texto");
				exm.setText("Pninei Halaja");
				screenOn.setText("cancelar el sueño del monitor");
				blackScreen.setText("oscura");
				brightScreen.setText("brillante   :modo de visualización");
				lastLoc.setText("Al abrir la aplicación, salta a la última ubicación");
			}
			else if (MyLanguage == FRENCH)
			{
				f_imv.setImageResource(R.drawable.f_b_2);
				langOf.setText("Language de l'appplication");
				chooseTochenLang.setText("Selection du contenu des langues des livres");
				chooseSize.setText("Définir la taille du texte");
				exm.setText("Pninei Halakha");
				screenOn.setText("Annuler mode veille");
				blackScreen.setText("foncée");
				brightScreen.setText("brillante   :mode d'affichage");
				lastLoc.setText("À l'ouverture de l'application, passez directement au dernier emplacement visité");

			}
			else if (MyLanguage == ENGLISH)
			{
				en_imv.setImageResource(R.drawable.en_b_2);
				langOf.setText("language of the application");
				chooseTochenLang.setText("Selecting the content of the languages in the books");
				chooseSize.setText("Set text size");
				exm.setText("Pninei Halakha");
				blackScreen.setText("On");
				screenOn.setText("Cancel screen timeout");
				brightScreen.setText("Off        :Dark mode");
				lastLoc.setText("When opening the app, skip to the last location");
			}
			shPrefEditor.putInt("MyLanguage", MyLanguage);
			shPrefEditor.commit();
			im_black_screen.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {


					if (im_black_screen.getTag().equals("4")) {
						im_black_screen.setImageResource(R.drawable.check_fill);
						im_white_screen.setImageResource(R.drawable.check);
						im_black_screen.setTag("3");
						im_white_screen.setTag("4");
						shPrefEditor.putInt("BlackBackground", 1);
						TextView chooseBlack=(TextView) languageDialog.findViewById(R.id.blackScreen);
						TextView chooseTochen=(TextView) languageDialog.findViewById(R.id.chooseTochen);
						TextView textSizeExm=(TextView) languageDialog.findViewById(R.id.textSizeExm);
						TextView chooseLastLoc=(TextView) languageDialog.findViewById(R.id.lastLocation);
						TextView screenOn=(TextView) languageDialog.findViewById(R.id.screenOn);
						TextView brightScreen=(TextView) languageDialog.findViewById(R.id.brightScreen);
						RelativeLayout main=(RelativeLayout) languageDialog.findViewById(R.id.layout_root);
						LinearLayout main2=(LinearLayout) languageDialog.findViewById(R.id.main);
						LinearLayout partA=(LinearLayout) languageDialog.findViewById(R.id.lnrOptions);
						LinearLayout partB=(LinearLayout) languageDialog.findViewById(R.id.lnrOption2);
						LinearLayout partC=(LinearLayout) languageDialog.findViewById(R.id.lnrOption5);
						LinearLayout partD=(LinearLayout) languageDialog.findViewById(R.id.lnrOption6);
						LinearLayout partE=(LinearLayout) languageDialog.findViewById(R.id.lnrOption7);
						LinearLayout partF=(LinearLayout) languageDialog.findViewById(R.id.lnrOption8);
							langOf.setTextColor(Color.WHITE);
							chooseBlack.setTextColor(Color.WHITE);
							chooseTochen.setTextColor(Color.WHITE);
							chooseSize.setTextColor(Color.WHITE);
							textSizeExm.setTextColor(Color.WHITE);
							chooseLastLoc.setTextColor(Color.WHITE);
							screenOn.setTextColor(Color.WHITE);
							brightScreen.setTextColor(Color.WHITE);
							main.setBackgroundColor(Color.BLACK);
							main2.setBackgroundColor(Color.BLACK);
							partA.setBackgroundColor(Color.BLACK);
							partB.setBackgroundColor(Color.BLACK);
							partC.setBackgroundColor(Color.BLACK);
							partD.setBackgroundColor(Color.BLACK);
							partE.setBackgroundColor(Color.BLACK);
							partF.setBackgroundColor(Color.BLACK);


					}
				}
			});
			im_white_screen.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {


					if (im_white_screen.getTag().equals("4")) {
						im_black_screen.setImageResource(R.drawable.check);
						im_white_screen.setImageResource(R.drawable.check_fill);
						im_black_screen.setTag("4");
						im_white_screen.setTag("3");
						shPrefEditor.putInt("BlackBackground", 0);
						TextView chooseBlack=(TextView) languageDialog.findViewById(R.id.blackScreen);
						TextView chooseTochen=(TextView) languageDialog.findViewById(R.id.chooseTochen);
						TextView textSizeExm=(TextView) languageDialog.findViewById(R.id.textSizeExm);
						TextView chooseLastLoc=(TextView) languageDialog.findViewById(R.id.lastLocation);
						TextView screenOn=(TextView) languageDialog.findViewById(R.id.screenOn);
						TextView brightScreen=(TextView) languageDialog.findViewById(R.id.brightScreen);
						RelativeLayout main=(RelativeLayout) languageDialog.findViewById(R.id.layout_root);
						LinearLayout main2=(LinearLayout) languageDialog.findViewById(R.id.main);
						LinearLayout partA=(LinearLayout) languageDialog.findViewById(R.id.lnrOptions);
						LinearLayout partB=(LinearLayout) languageDialog.findViewById(R.id.lnrOption2);
						LinearLayout partC=(LinearLayout) languageDialog.findViewById(R.id.lnrOption5);
						LinearLayout partD=(LinearLayout) languageDialog.findViewById(R.id.lnrOption6);
						LinearLayout partE=(LinearLayout) languageDialog.findViewById(R.id.lnrOption7);
						LinearLayout partF=(LinearLayout) languageDialog.findViewById(R.id.lnrOption8);
						langOf.setTextColor(Color.BLACK);
						chooseBlack.setTextColor(Color.BLACK);
						chooseTochen.setTextColor(Color.BLACK);
						chooseSize.setTextColor(Color.BLACK);
						screenOn.setTextColor(Color.BLACK);
						brightScreen.setTextColor(Color.BLACK);
						chooseLastLoc.setTextColor(Color.BLACK);
						main.setBackgroundColor(Color.WHITE);
						main2.setBackgroundColor(Color.WHITE);
						partA.setBackgroundColor(Color.WHITE);
						partB.setBackgroundColor(Color.WHITE);
						partC.setBackgroundColor(Color.WHITE);
						partD.setBackgroundColor(Color.WHITE);
						partE.setBackgroundColor(Color.WHITE);
						partF.setBackgroundColor(Color.WHITE);
					}
				}
			});
			im_last_loc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {


					if (im_last_loc.getTag().equals("4")) {
						im_last_loc.setImageResource(R.drawable.checkbox_fill);
						im_last_loc.setTag("3");
						shPrefEditor.putInt("StartInLastLocation", 1);

					} else {
						im_last_loc.setImageResource(R.drawable.checkbox);
						im_last_loc.setTag("4");
						shPrefEditor.putInt("StartInLastLocation", 0);
					}

				}
			});

			im_screenOn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {


					if (im_screenOn.getTag().equals("4")) {
						im_screenOn.setImageResource(R.drawable.checkbox_fill);
						im_screenOn.setTag("3");
						shPrefEditor.putInt("ScreenOn", 1);

					} else {
						im_screenOn.setImageResource(R.drawable.checkbox);
						im_screenOn.setTag("4");
						shPrefEditor.putInt("ScreenOn", 0);
					}

				}
			});
			h_imv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLanguage = HEBREW;
				h_imv.setImageResource(R.drawable.h_b_2);
				r_imv.setImageResource(R.drawable.r_b_1);
				es_imv.setImageResource(R.drawable.es_b_1);
				en_imv.setImageResource(R.drawable.en_b_1);
				f_imv.setImageResource(R.drawable.f_b_1);
				langOf.setText("שפת האפליקציה");
				chooseTochenLang.setText("בחירת תוכן השפות בספרים");
				chooseSize.setText("קביעת גודל טקסט");
				exm.setText("פניני הלכה");
				blackScreen.setText("חשוך");
				brightScreen.setText("בחירת מצב תצוגה:   בהיר");
				lastLoc.setText("בפתיחת האפליקציה דלג למיקום אחרון");
				screenOn.setText("ביטול החשכת מסך");
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
			}
		});
		r_imv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLanguage = RUSSIAN;
				h_imv.setImageResource(R.drawable.h_b_1);
				r_imv.setImageResource(R.drawable.r_b_2);
				es_imv.setImageResource(R.drawable.es_b_1);
				en_imv.setImageResource(R.drawable.en_b_1);
				f_imv.setImageResource(R.drawable.f_b_1);
				langOf.setText("Язык приложения");
				chooseTochenLang.setText("Выбор языка книг");
				chooseSize.setText("Выбрать размер текста");
				exm.setText("Жемчужины Галахи");
				blackScreen.setText("темный");
				brightScreen.setText("яркий   :Режим отображения");
				screenOn.setText("Темная тема отмена");
				lastLoc.setText("При загрузке приложения возвращаться на последнее место чтения");
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();

			}
		});
		es_imv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLanguage = SPANISH;
				h_imv.setImageResource(R.drawable.h_b_1);
				r_imv.setImageResource(R.drawable.r_b_1);
				es_imv.setImageResource(R.drawable.es_b_2);
				en_imv.setImageResource(R.drawable.en_b_1);
				f_imv.setImageResource(R.drawable.f_b_1);
				langOf.setText("El idioma de la aplicación");
				chooseTochenLang.setText("Selección del contenido de los idiomas en los libros.");
				chooseSize.setText("Establecer tamaño de texto");
				exm.setText("Pninei Halaja");
				blackScreen.setText("oscura");
				brightScreen.setText("brillante   :modo de visualización");
				screenOn.setText("cancelar el sueño del monitor");
				lastLoc.setText("Al abrir la aplicación, salta a la última ubicación");
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
			}
		});
		en_imv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLanguage = ENGLISH;
				h_imv.setImageResource(R.drawable.h_b_1);
				r_imv.setImageResource(R.drawable.r_b_1);
				es_imv.setImageResource(R.drawable.es_b_1);
				en_imv.setImageResource(R.drawable.en_b_2);
				f_imv.setImageResource(R.drawable.f_b_1);
				langOf.setText("language of the application");
				chooseTochenLang.setText("Selecting the content of the languages in the books");
				chooseSize.setText("Set text size");
				exm.setText("Pninei Halakha");
				blackScreen.setText("On");
				screenOn.setText("Cancel screen timeout");
					brightScreen.setText("Off        :Dark mode");
				lastLoc.setText("When opening the app, skip to the last location");
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
			}
		});
		f_imv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyLanguage = FRENCH;;
				h_imv.setImageResource(R.drawable.h_b_1);
				r_imv.setImageResource(R.drawable.r_b_1);
				es_imv.setImageResource(R.drawable.es_b_1);
				en_imv.setImageResource(R.drawable.en_b_1);
				f_imv.setImageResource(R.drawable.f_b_2);
				langOf.setText("Language de l'appplication");
				chooseTochenLang.setText("Selection du contenu des langues des livres");
				chooseSize.setText("Définir la taille du texte");
				exm.setText("Pninei Halakha");
				blackScreen.setText("foncée");
				screenOn.setText("Annuler mode veille");
				brightScreen.setText("brillante   :mode d'affichage");
				lastLoc.setText("À l'ouverture de l'application, passez directement au dernier emplacement visité");
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
			}
		});

		}
		if (!hebDisplay)
			h_imv_down.setImageResource(R.drawable.h_b_3);
		else
			h_imv_down.setImageResource(R.drawable.h_b_4);

		//final CheckBox chkFr=(CheckBox) languageDialog.findViewById(R.id.checkBoxFR);
		//final CheckBox chkEs=(CheckBox) languageDialog.findViewById(R.id.checkBoxES);
		if(MyLanguage==-1) {
			switch (Locale.getDefault().getLanguage()) {
				case "en":
					MyLanguage = ENGLISH;
					break;
				case "es":
					MyLanguage = SPANISH;
					break;
				case "ru":
					MyLanguage = RUSSIAN;
				case "fr":
					MyLanguage = FRENCH;
					break;
				default:
					MyLanguage = HEBREW;
					break;
			}
			shPrefEditor.putInt("MyLanguage", MyLanguage);
			shPrefEditor.commit();
		}
		//else {



		refresh(1000);
		if (mPrefs.getBoolean("f_exists",false))
				f_imv_down.setImageResource(R.drawable.f_b_4);


		if (mPrefs.getBoolean("es_exists",false))
			es_imv_down.setImageResource(R.drawable.es_b_4);

		if (mPrefs.getBoolean("en_exists",false))
			en_imv_down.setImageResource(R.drawable.en_b_4);

		if (mPrefs.getBoolean("r_exists",false))
			r_imv_down.setImageResource(R.drawable.r_b_4);

		dialog_x.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop = 0;
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
				changeL = true;

				//mPrefs.getString("where","HomePage");
				Class ourClass = null;
				try {
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem."+mPrefs.getString("where","HomePage"));
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				shPrefEditor.putString("where", "HomePage");
				shPrefEditor.commit();
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				int[] book_chapter = new int[2];
				book_chapter[0] = 0xFFFF;
				book_chapter[1] = 0xFFFF;
				ourIntent.putExtra("book_chapter", mPrefs.getInt("h&rChapId",1));
				ourIntent.putExtra("audio_id", mPrefs.getInt("h&rAI",1));
				ourIntent.putExtra("MyLanguage", MyLanguage);
				ourIntent.putExtra("book_id", mPrefs.getInt("h&rBookId",1));
				ourIntent.putExtra("chapter_id", mPrefs.getInt("h&rChapId",1));
				ourIntent.putExtra("webLink", mPrefs.getString("h&rWebLink",""));
				//ourIntent.putExtra("webLink", localFile.getPath());
				ourIntent.putExtra("scroolY", mPrefs.getInt("h&rScrool",0));
				ourIntent.putExtra("hearAndRead", mPrefs.getBoolean("hearAndRead",true));
				ourIntent.putExtra("book_chapter",extras.getIntArray("book_chapter") );
				ourIntent.putExtra("sections_" + 1,extras.getStringArrayList("sections_" + 1));
				ourIntent.putExtra("sections_" + 2,extras.getStringArrayList("sections_" + 2));
				ourIntent.putExtra("sections_" + 3,extras.getStringArrayList("sections_" + 3));
				ourIntent.putExtra("sections_" + 4,extras.getStringArrayList("sections_" + 4));
				ourIntent.putExtra("sections_" + 5,extras.getStringArrayList("sections_" + 5));
				ourIntent.putExtra("sections_" + 6,extras.getStringArrayList("sections_" + 6));
				ourIntent.putExtra("sections_" + 7,extras.getStringArrayList("sections_" + 7));
				ourIntent.putExtra("sections_" + 8,extras.getStringArrayList("sections_" + 8));
				ourIntent.putExtra("sections_" + 9,extras.getStringArrayList("sections_" + 9));
				ourIntent.putExtra("sections_" + 10,extras.getStringArrayList("sections_" + 10));
				ourIntent.putExtra("sections_" + 11,extras.getStringArrayList("sections_" + 11));
				ourIntent.putExtra("sections_" + 12,extras.getStringArrayList("sections_" + 12));
				ourIntent.putExtra("sections_" + 13,extras.getStringArrayList("sections_" + 13));
				ourIntent.putExtra("sections_" + 14,extras.getStringArrayList("sections_" + 14));
				ourIntent.putExtra("sections_" + 15,extras.getStringArrayList("sections_" + 15));
				ourIntent.putExtra("sections_" + 16,extras.getStringArrayList("sections_" + 16));
				ourIntent.putExtra("sections_" + 17,extras.getStringArrayList("sections_" + 17));
				ourIntent.putExtra("sections_" + 18,extras.getStringArrayList("sections_" + 18));
				ourIntent.putExtra("sections_" + 19,extras.getStringArrayList("sections_" + 19));
				ourIntent.putExtra("sections_" + 20,extras.getStringArrayList("sections_" + 20));
				ourIntent.putExtra("sections_" + 21,extras.getStringArrayList("sections_" + 21));
				ourIntent.putExtra("sections_" + 22,extras.getStringArrayList("sections_" + 22));
				ourIntent.putExtra("sections_" + 23,extras.getStringArrayList("sections_" + 23));
				ourIntent.putExtra("sections_" + 24,extras.getStringArrayList("sections_" + 24));
				ourIntent.putExtra("sections_" + 25,extras.getStringArrayList("sections_" + 25));
				ourIntent.putExtra("sections_" + 26,extras.getStringArrayList("sections_" + 26));
				ourIntent.putExtra("sections_" + 27,extras.getStringArrayList("sections_" + 27));
				ourIntent.putExtra("sections_" + 28,extras.getStringArrayList("sections_" + 28));
				ourIntent.putExtra("sections_" + 29,extras.getStringArrayList("sections_" + 29));
				ourIntent.putExtra("sections_" + 30,extras.getStringArrayList("sections_" + 30));
				ourIntent.putExtra("sections_" + 31,extras.getStringArrayList("sections_" + 31));
				ourIntent.putExtra("sections_" + 32,extras.getStringArrayList("sections_" + 32));
				ourIntent.putExtra("sections_" + 33,extras.getStringArrayList("sections_" + 33));
				ourIntent.putExtra("sections_" + 34,extras.getStringArrayList("sections_" + 34));
				ourIntent.putExtra("sections_" + 35,extras.getStringArrayList("sections_" + 35));
				ourIntent.putExtra("sections_" + 36,extras.getStringArrayList("sections_" + 36));
				ourIntent.putExtra("sections_" + 37,extras.getStringArrayList("sections_" + 37));
				ourIntent.putExtra("sections_" + 38,extras.getStringArrayList("sections_" + 38));
				ourIntent.putExtra("sections_" + 39,extras.getStringArrayList("sections_" + 39));
				ourIntent.putExtra("sections_" + 40,extras.getStringArrayList("sections_" + 40));
				startActivity(ourIntent);
			}
		});
		if(firstLang==0) {

		}
		h_imv_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


				if (h_imv_down.getTag().equals("4")) {
					h_imv_down.setImageResource(R.drawable.h_b_3);
					shPrefEditor.putBoolean("h_exists", false);
					h_imv_down.setTag("3");
					hebDisplay = false;
				} else {
					h_imv_down.setImageResource(R.drawable.h_b_4);
					shPrefEditor.putBoolean("h_exists", true);
					h_imv_down.setTag("4");
					hebDisplay = true;
				}

			}
		});

		es_imv_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

					if (!mPrefs.getBoolean("es_exists",false)) {
						shPrefEditor.putBoolean("es_exists", true);
						shPrefEditor.commit();
						es_imv_down.setImageResource(R.drawable.es_b_4);

					} else {
						shPrefEditor.putBoolean("es_exists", false);
						shPrefEditor.commit();
						es_imv_down.setImageResource(R.drawable.es_b_3);//es_imv_down.setImageResource(R.drawable.es_b_3);
					}


			}
		});
		r_imv_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!mPrefs.getBoolean("r_exists",false)) {
					shPrefEditor.putBoolean("r_exists", true);
					shPrefEditor.commit();
					r_imv_down.setImageResource(R.drawable.r_b_4);

				} else {
					shPrefEditor.putBoolean("r_exists", false);
					shPrefEditor.commit();
					r_imv_down.setImageResource(R.drawable.r_b_3);//es_imv_down.setImageResource(R.drawable.es_b_3);
				}
			}
		});
		f_imv_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mPrefs.getBoolean("f_exists",false)) {
					shPrefEditor.putBoolean("f_exists", true);
					shPrefEditor.commit();
					f_imv_down.setImageResource(R.drawable.f_b_4);

				} else {
					shPrefEditor.putBoolean("f_exists", false);
					shPrefEditor.commit();
					f_imv_down.setImageResource(R.drawable.f_b_3);//es_imv_down.setImageResource(R.drawable.es_b_3);
				}
			}
		});
		en_imv_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mPrefs.getBoolean("en_exists",false)) {
					shPrefEditor.putBoolean("en_exists", true);
					shPrefEditor.commit();
					en_imv_down.setImageResource(R.drawable.en_b_4);

				} else {
					shPrefEditor.putBoolean("en_exists", false);
					shPrefEditor.commit();
					en_imv_down.setImageResource(R.drawable.en_b_3);//es_imv_down.setImageResource(R.drawable.es_b_3);
				}
			}

		});
		languageDialog.dismiss();


		// if button is clicked
		languageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				pop = 0;
				shPrefEditor.putInt("MyLanguage", MyLanguage);
				shPrefEditor.commit();
				changeL = true;

				//mPrefs.getString("where","HomePage");
				Class ourClass = null;
				try {
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem."+mPrefs.getString("where","HomePage"));
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				shPrefEditor.putString("where", "HomePage");
				shPrefEditor.commit();
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				int[] book_chapter = new int[2];
				book_chapter[0] = 0xFFFF;
				book_chapter[1] = 0xFFFF;
				ourIntent.putExtra("book_chapter", mPrefs.getInt("h&rChapId",1));
				ourIntent.putExtra("audio_id", mPrefs.getInt("h&rAI",1));
				ourIntent.putExtra("MyLanguage", MyLanguage);
				ourIntent.putExtra("book_id", mPrefs.getInt("h&rBookId",1));
				ourIntent.putExtra("chapter_id", mPrefs.getInt("h&rChapId",1));
				ourIntent.putExtra("webLink", mPrefs.getString("h&rWebLink",""));
				//ourIntent.putExtra("webLink", localFile.getPath());
				ourIntent.putExtra("scroolY", mPrefs.getInt("h&rScrool",0));
				ourIntent.putExtra("hearAndRead", mPrefs.getBoolean("hearAndRead",true));
				ourIntent.putExtra("book_chapter",extras.getIntArray("book_chapter") );
				ourIntent.putExtra("sections_" + 1,extras.getStringArrayList("sections_" + 1));
				ourIntent.putExtra("sections_" + 2,extras.getStringArrayList("sections_" + 2));
				ourIntent.putExtra("sections_" + 3,extras.getStringArrayList("sections_" + 3));
				ourIntent.putExtra("sections_" + 4,extras.getStringArrayList("sections_" + 4));
				ourIntent.putExtra("sections_" + 5,extras.getStringArrayList("sections_" + 5));
				ourIntent.putExtra("sections_" + 6,extras.getStringArrayList("sections_" + 6));
				ourIntent.putExtra("sections_" + 7,extras.getStringArrayList("sections_" + 7));
				ourIntent.putExtra("sections_" + 8,extras.getStringArrayList("sections_" + 8));
				ourIntent.putExtra("sections_" + 9,extras.getStringArrayList("sections_" + 9));
				ourIntent.putExtra("sections_" + 10,extras.getStringArrayList("sections_" + 10));
				ourIntent.putExtra("sections_" + 11,extras.getStringArrayList("sections_" + 11));
				ourIntent.putExtra("sections_" + 12,extras.getStringArrayList("sections_" + 12));
				ourIntent.putExtra("sections_" + 13,extras.getStringArrayList("sections_" + 13));
				ourIntent.putExtra("sections_" + 14,extras.getStringArrayList("sections_" + 14));
				ourIntent.putExtra("sections_" + 15,extras.getStringArrayList("sections_" + 15));
				ourIntent.putExtra("sections_" + 16,extras.getStringArrayList("sections_" + 16));
				ourIntent.putExtra("sections_" + 17,extras.getStringArrayList("sections_" + 17));
				ourIntent.putExtra("sections_" + 18,extras.getStringArrayList("sections_" + 18));
				ourIntent.putExtra("sections_" + 19,extras.getStringArrayList("sections_" + 19));
				ourIntent.putExtra("sections_" + 20,extras.getStringArrayList("sections_" + 20));
				ourIntent.putExtra("sections_" + 21,extras.getStringArrayList("sections_" + 21));
				ourIntent.putExtra("sections_" + 22,extras.getStringArrayList("sections_" + 22));
				ourIntent.putExtra("sections_" + 23,extras.getStringArrayList("sections_" + 23));
				ourIntent.putExtra("sections_" + 24,extras.getStringArrayList("sections_" + 24));
				ourIntent.putExtra("sections_" + 25,extras.getStringArrayList("sections_" + 25));
				ourIntent.putExtra("sections_" + 26,extras.getStringArrayList("sections_" + 26));
				ourIntent.putExtra("sections_" + 27,extras.getStringArrayList("sections_" + 27));
				ourIntent.putExtra("sections_" + 28,extras.getStringArrayList("sections_" + 28));
				ourIntent.putExtra("sections_" + 29,extras.getStringArrayList("sections_" + 29));
				ourIntent.putExtra("sections_" + 30,extras.getStringArrayList("sections_" + 30));
				ourIntent.putExtra("sections_" + 31,extras.getStringArrayList("sections_" + 31));
				ourIntent.putExtra("sections_" + 32,extras.getStringArrayList("sections_" + 32));
				ourIntent.putExtra("sections_" + 33,extras.getStringArrayList("sections_" + 33));
				ourIntent.putExtra("sections_" + 34,extras.getStringArrayList("sections_" + 34));
				ourIntent.putExtra("sections_" + 35,extras.getStringArrayList("sections_" + 35));
				ourIntent.putExtra("sections_" + 36,extras.getStringArrayList("sections_" + 36));
				ourIntent.putExtra("sections_" + 37,extras.getStringArrayList("sections_" + 37));
				ourIntent.putExtra("sections_" + 38,extras.getStringArrayList("sections_" + 38));
				ourIntent.putExtra("sections_" + 39,extras.getStringArrayList("sections_" + 39));
				ourIntent.putExtra("sections_" + 40,extras.getStringArrayList("sections_" + 40));
				startActivity(ourIntent);



			}
		});
		languageDialog.show();
	}


	void goToLastLocation()
	{
		try
		{
			Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
			Intent ourIntent = new Intent(MainActivity.this, ourClass);
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
	private void refresh(int millisec) {
		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				//speed = sppedArray[spinner.getSelectedItemPosition()];
				content();
			}
		};
		handler.postDelayed(runnable, millisec);
	}
	public void content()
	{
			ImageView r_imv_down = (ImageView) languageDialog.findViewById(R.id.my_marks);
			//ImageView es_imv=(ImageView) languageDialog.findViewById(R.id.im_es);
			ImageView es_imv_down = (ImageView) languageDialog.findViewById(R.id.im_es_down);
			//ImageView en_imv=(ImageView) languageDialog.findViewById(R.id.im_en);
			ImageView en_imv_down = (ImageView) languageDialog.findViewById(R.id.settings);
			//ImageView f_imv=(ImageView) languageDialog.findViewById(R.id.im_f);
			ImageView f_imv_down = (ImageView) languageDialog.findViewById(R.id.im_f_down);
			if (mPrefs.getBoolean("r_exists",false))
				r_imv_down.setImageResource(R.drawable.r_b_4);
			if (mPrefs.getBoolean("en_exists",false))
				en_imv_down.setImageResource(R.drawable.en_b_4);
			if (mPrefs.getBoolean("es_exists",false))
				es_imv_down.setImageResource(R.drawable.es_b_4);
			if (mPrefs.getBoolean("f_exists",false))
				f_imv_down.setImageResource(R.drawable.f_b_4);

	}


	static final String LOG_TAG = "MyFtpTest";



}
