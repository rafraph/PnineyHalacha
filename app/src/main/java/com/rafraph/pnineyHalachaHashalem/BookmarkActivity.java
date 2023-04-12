package com.rafraph.pnineyHalachaHashalem;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class BookmarkActivity extends Activity 
{
	static SharedPreferences mPrefs;
//	SharedPreferences.Editor shPrefEditor;
	public ListView bookmarksListView = null;
	public List<String> listBookmarksNames = new ArrayList<String>();
	public String Bookmarks;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
	public static int[] book_chapter = new int[2];
	ArrayAdapter  adapter;
	SharedPreferences.Editor shPrefEditor;
	Button buttonDeleteAll;
	public Dialog acronymsDialog;
	public EditText TextToDecode;
	String acronymsText;
	@Override
	public void onBackPressed() {
		try
		{
			Class ourClass = null;
			Intent ourIntent;
			ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
			ourIntent = new Intent(BookmarkActivity.this, ourClass);
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		bookmarksListView = (ListView) findViewById(R.id.Bookmarkslist);
		buttonDeleteAll = (Button) findViewById(R.id.buttonDeleteAll);
	//	shPrefEditor = mPrefs.edit();
		
		TextView textView = new TextView(this);

		textView.setTextColor(Color.BLACK);
		textView.setTextSize(30);
		bookmarksListView.addHeaderView(textView);
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		int MyLanguage = mPrefs.getInt("MyLanguage", 0);
		if(MyLanguage==HEBREW)
			textView.setText("סימניות");
		if(MyLanguage==FRENCH)
			textView.setText("Signets");
		if(MyLanguage==ENGLISH)
			textView.setText("Bookmarks");
		if(MyLanguage==RUSSIAN)
			textView.setText("Закладки");
		if(MyLanguage==SPANISH)
			textView.setText("Marcadores");

		Bookmarks = mPrefs.getString("Bookmarks", "");
		ImageView menu= (ImageView) findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContextThemeWrapper ctw = new ContextThemeWrapper(BookmarkActivity.this, R.style.CustomPopupTheme);
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
									ourIntent = new Intent(BookmarkActivity.this, ourClass);
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
								ourIntent = new Intent(BookmarkActivity.this, ourClass);
								ourIntent.putExtra("homePage", true);
								shPrefEditor.putString("where", "BookmarkActivity");
								shPrefEditor.commit();
								startActivity(ourIntent);
								break;

							case 1:/*to books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(BookmarkActivity.this, ourClass);
								ourIntent.putExtra("homePage", false);

								startActivity(ourIntent);
								break;

							case 2:/*pninaYomit*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(BookmarkActivity.this, ourClass);
								startActivity(ourIntent);
								break;

							case 3:/*search in all books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(BookmarkActivity.this, ourClass);
								startActivity(ourIntent);

								break;

							case 4:/*acronyms*/
								acronymsDecode();
								break;

							case 5:/*feedback*/
								try
								{
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
									ourIntent = new Intent(BookmarkActivity.this, ourClass);
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
									ourIntent = new Intent(BookmarkActivity.this, ourClass);
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
									ourIntent = new Intent(BookmarkActivity.this, ourClass);
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
		LinearLayout main=(LinearLayout) findViewById(R.id.main);
		LinearLayout main2=(LinearLayout) findViewById(R.id.lnrOption3);
		if (mPrefs.getInt("BlackBackground", 0)==1)
		{
;			ImageView toMain= (ImageView) findViewById(R.id.to_main);
			//main.setBackgroundColor(Color.BLACK);
			main.setBackgroundColor(Color.BLACK);
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
			menu= (ImageView) findViewById(R.id.menu);
			menu.setImageResource(R.drawable.ic_action_congif_b);
			main.setBackgroundColor(Color.rgb(120,1,1));
			//main2.setBackgroundColor(Color.BLACK);
			//main3.setBackgroundColor(Color.BLACK);
			//main4.setBackgroundColor(Color.BLACK);
			//main5.setBackgroundColor(Color.BLACK);


		}
		else
		{
			ImageView toMain= (ImageView) findViewById(R.id.to_main);
			//main.setBackgroundColor(Color.BLACK);

			if(MyLanguage==ENGLISH)
				toMain.setImageResource(R.drawable.to_main_e);
			if(MyLanguage==RUSSIAN)
				toMain.setImageResource(R.drawable.to_main_r);
			if(MyLanguage==SPANISH)
				toMain.setImageResource(R.drawable.to_main_s);
			if(MyLanguage==FRENCH)
				toMain.setImageResource(R.drawable.to_main_f);
			if(MyLanguage==HEBREW)
				toMain.setImageResource(R.drawable.to_main);
		}
		ImageView toMain= (ImageView) findViewById(R.id.to_main);
		toMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{
					Class ourClass = null;
					Intent ourIntent;
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
					ourIntent = new Intent(BookmarkActivity.this, ourClass);
					startActivity(ourIntent);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		});
		fillBookmarksNames();
		showBookmarksList();

		final Context context = this;
		
		bookmarksListView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					try {
						Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
						Intent ourIntent = new Intent(BookmarkActivity.this, ourClass);
						ourIntent.putExtra("hearAndRead", true);
						int i, index = 1/*to skip the first comma*/, index_end = 0;
						int bookmarkScrollY, fontSize;

						for (i = 0; i < ((position - 1) * 5) + 1; i++)/*skip to the book of the right bookmark*/
							index = Bookmarks.indexOf(",", index) + 1;

						/*book*/
						index_end = Bookmarks.indexOf(",", index);
						book_chapter[0] = Integer.parseInt(Bookmarks.substring(index, index_end));

						/*chapter*/
						index = index_end + 1;
						index_end = Bookmarks.indexOf(",", index);
						book_chapter[1] = Integer.parseInt(Bookmarks.substring(index, index_end));
						ourIntent.putExtra("book_chapter", book_chapter);

						/*scroll*/
						index = index_end + 1;
						index_end = Bookmarks.indexOf(",", index);
						bookmarkScrollY = Integer.parseInt(Bookmarks.substring(index, index_end));
						ourIntent.putExtra("bookmarkScrollY", bookmarkScrollY);

						/*font size*/
						index = index_end + 1;
						index_end = Bookmarks.indexOf(",", index);
						if (index_end == -1)/*last bookmark*/
							index_end = Bookmarks.length();
						fontSize = Integer.parseInt(Bookmarks.substring(index, index_end));
						shPrefEditor.putInt("fontSize", fontSize);
						shPrefEditor.commit();

						ourIntent.putExtra("fromBookmarks", 1);
						startActivity(ourIntent);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		bookmarksListView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener ()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
			{
				bookmarksListView.setOnItemClickListener(new OnItemClickListener()
				{
					public void onItemClick(AdapterView<?> a, View v, int position, long id)
					{
						//bookmarksListView.removeHeaderView(v);
						AlertDialog.Builder adb=new AlertDialog.Builder(context);
						adb.setTitle("Delete?");
						adb.setMessage("Are you sure you want to delete " + position);
						final int positionToRemove = position-1;
						adb.setNegativeButton("Cancel", null);
						adb.setPositiveButton("Ok", new AlertDialog.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								int index = 0, index_end = 0;
								//listBookmarksNames.(positionToRemove);
								adapter.notifyDataSetChanged();
								/*remove the bookmark detailes from Bookmark variable*/
								for(int i=0;i<(positionToRemove*5)+1;i++)/*skip to the book of the right bookmark*/
									index = Bookmarks.indexOf("," , index) + 1;
								index_end = index;
								if(index != 0)
									index--;//in order to delete the comma "," (except in case we want to delete the first item)
								for(int i=0;i<5;i++)/*find the end index of this bookmark*/
									index_end = Bookmarks.indexOf("," , index_end) + 1;
								if(index_end == 0)
									index_end = Bookmarks.length();
								else
									index_end--;//We don't want to delete the last comma
								String strToDelete = Bookmarks.substring(index, index_end);
								Bookmarks = Bookmarks.substring(0, index) + Bookmarks.substring(index_end, Bookmarks.length());
								String strBookmark = Bookmarks;
								shPrefEditor.putString("Bookmarks", Bookmarks);
								shPrefEditor.commit();
								try
								{
									Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.BookmarkActivity");
									Intent ourIntent = new Intent(BookmarkActivity.this, ourClass);
									startActivity(ourIntent);
								}
								catch (ClassNotFoundException e)
								{
									e.printStackTrace();
								}
							}});
						adb.show();
					}
				});
				/*Listener for the "delete all button"*/
				buttonDeleteAll.setOnClickListener(new OnClickListener()
				{
					@SuppressLint("NewApi")
					@Override
					public void onClick(View v)
					{
						AlertDialog.Builder adb=new AlertDialog.Builder(context);
						adb.setTitle("Delete?");
						adb.setMessage("Are you sure you want to delete all?");
						adb.setNegativeButton("Cancel", null);
						adb.setPositiveButton("Ok", new AlertDialog.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								Bookmarks = "";
								shPrefEditor.putString("Bookmarks", Bookmarks);
								shPrefEditor.commit();
							}});
						adb.show();
					}

				});
				return false;
			}
		});

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
	private void fillBookmarksNames()
	{
		int i, index = 0, index_end=0;
		String strBookmark = Bookmarks;
		while((index = Bookmarks.indexOf("," , index)) != -1)
		{
			index++;
			index_end = Bookmarks.indexOf("," , index);
			//listBookmarksNames.add(Bookmarks.substring(index, index_end));
			TextView textView = new TextView(getBaseContext());
			//String sourceString = "<b>" + "[" + chapterCounter + "] " + chaptersNames[i][j] + "</b> " + sections;
			//String sourceString = "<b >"+ chaptersNames[i][j].split("-")[1] + "</b>("+ chaptersNames[i][j].split("-")[0]+","+ sections+")";
			textView.setText(Bookmarks.substring(index, index_end));
			//textView.setText("shilo");
			//textView.setText(" (" + sections+ ")");/*only one item in the list per chapter*/
			if (mPrefs.getInt("BlackBackground", 0)==1)
			{
				textView.setTextColor(Color.WHITE);
				bookmarksListView.setBackgroundColor(Color.BLACK);
			}
			else
				textView.setTextColor(Color.BLACK);
			textView.setTextSize(24);
			bookmarksListView.addFooterView(textView);
			for(i=0;i<4;i++)/*skip all other fields*/
				index = Bookmarks.indexOf("," , index) + 1;
		}
	}
	
	public void showBookmarksList()
	{
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listBookmarksNames);
		bookmarksListView.setAdapter(adapter);
	}	

}
