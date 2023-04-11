package com.rafraph.pnineyHalachaHashalem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;

public class About_p extends Activity
{
	Button link;
	public int MyLanguage;
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
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

	public static void setMargins (View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}
	@Override
	public void onBackPressed() {
		try
		{
			Class ourClass = null;
			Intent ourIntent;
			ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
			ourIntent = new Intent(About_p.this, ourClass);
			startActivity(ourIntent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	@SuppressLint("ResourceType")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_pninei);
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		MyLanguage = mPrefs.getInt("MyLanguage", -1);
		ImageView toMain= (ImageView) findViewById(R.id.to_main);
		if(MyLanguage==ENGLISH)
			toMain.setImageResource(R.drawable.to_main_e);
		if(MyLanguage==RUSSIAN)
			toMain.setImageResource(R.drawable.to_main_r);
		if(MyLanguage==SPANISH)
			toMain.setImageResource(R.drawable.to_main_s);
		if(MyLanguage==FRENCH)
			toMain.setImageResource(R.drawable.to_main_f);

		TextView txt2=(TextView) findViewById(R.id.headr);

		TextView txt4=(TextView) findViewById(R.id.textView4);
		TextView aboutRav=(TextView) findViewById(R.id.about_rav);
		TextView aboutPn=(TextView) findViewById(R.id.about_pn);
		ImageView rav=(ImageView) findViewById(R.id.rav);
		ImageView books=(ImageView) findViewById(R.id.books);
		//Glide.with(this).load(R.drawable.abb).into(lili);
		switch (MyLanguage)
		{
			case ENGLISH:
				txt2.setText("'Peninei Halakha', the work of Rabbi Eliezer Melamed Shlita, presents the tradition of halachic rulings in a fresh Israeli spirit. The books are written in clear and up-to-date language, which illuminates the halakhot with their spiritual “flavor”, and refer to the customs of the various sectors. The halakhot are explained from the rules to the details, and with an emphasis on current issues which have recently emerged.");
				txt4.setText("Rosh Yeshivat Har Bracha and Rabbi of the community, author of the “Peninei Halakha” series, and author of the weekly column “Revivim” in the “Besheva” newspaper");
				rav.setImageResource(R.drawable.rav_ltr);
				//aboutRav.setPadding(0,0,300,0);
				aboutRav.setText("About the Author");
				books.setImageResource(R.drawable.pn_books_e);
				aboutPn.setPadding(0,0,310,0);
				aboutPn.setText("About the series");
				break;
			case RUSSIAN:
				txt2.setText("Книги «Жемчужины Ѓалахи», рава Элиэзера Меламеда, представляют ѓалахические постановления. Книги написаны ясным и простым языком; в них раскрывается духовный смысл заповедей Торы и рассматриваются обычаи различных общин. Ѓалаха объясняется на основе универсальных принципов и рассматривается во всех деталях и нюансах. В книгах делается особый акцент на актуальные вопросы нашего времени.");
				txt4.setText("Рав Элиэзер Меламед – раввин поселения и ешивы Ѓар-Браха. Один из ведущих ѓалахических авторитетов в религиозном сионизме и атор еженедельной колонки \"Ревивим\". Рав Меламед – выпускник ешивы «Мерказ ѓа-рав» и ученик рава Цви-Йеѓуды Кука. Автор серии «Жемчужины Ѓалахи» и книги «Еврейская традиция»");
				rav.setImageResource(R.drawable.rav_ltr);
				aboutRav.setPadding(0,0,430,0);
				aboutRav.setText("Об авторе");
				books.setImageResource(R.drawable.pn_books_r);
				aboutPn.setPadding(0,0,480,0);
				aboutPn.setText("О серии");
				break;
			case FRENCH:
				txt2.setText("''Les Perles de la Loi'', du rav Eliezer Melamed Shlita, présente la traditionnelle jurisprudence halakhique, sous un regard sioniste rafraîchissant. " +
						"L'oeuvre est écrite dans une syntaxe volontairement moderne et claire, détaillant pour chaque loi son fondement spirituel, et mettant en exergue les coutumes des différents courants. " +
						"Les lois sont enseignées depuis la règle générale jusqu'aux conséquences pratiques, y compris celles qui ont attrait à l'actualité la plus récente.");
				txt4.setText("Le rav Eliezer Melamed est le Rosh Yeshiva de l'institut talmudique Har Berakha et rav du Yishouv. Il est un décisionnaire halakhique dominant du courant sioniste religieux et jouit d'une tribune hebdomadaire dans les journaux ''Revivim'' et ''Besheva''. Le rav Melamed est diplômé de la Yeshiva Merkaz Harav, et a eu loisir de faire partie des élèves les plus proches du Rav Tsvi Yehouda Kook. Parmi ses nombreux écrits : ''Les Perles de la Loi'', ou ''La Tradition Juive''.");
				rav.setImageResource(R.drawable.rav_ltr);
				aboutRav.setPadding(0,0,230,0);
				aboutRav.setText("À propos de l'auteur");
				books.setImageResource(R.drawable.pn_books_f);
				aboutPn.setPadding(0,0,230,0);
				aboutPn.setText("À propos de l'oeuvre");

				break;
			case SPANISH:
				txt2.setText("'Pnimei halajá', la obra del rabino Eliezer Melamed Shalita, presenta la tradición de los decretos halájicos con el espíritu fresco de la tierra de Israel. Los libros están escritos en un lenguaje claro y actual, que despierta  con su espiritualidad el sabor por el estudió sabor." +
						"Y hace hincapié sobre las diversas costumbres Las halajot se explican desde las reglas hasta los detalles y con énfasis en temas de actualidad que han sido actualizados recientemente.");

				txt4.setText("Rabino Eliezer Melamed, director de la ieshivá Har Beraja y rabino del ishub. Uno de los principales exegetas de Halajá en el sionismo religioso y columnista 'Ravivim' del diario 'Bessheva'." +
						"El rabino es graduado de la Merkaz Rabbi Yeshiva y estudiante del rab tzvi yehuda Cohen kuk z\"l entre sus libros se encuentran: 'Penini Halajá' y el libro 'La Tradición Judía'.");
				rav.setImageResource(R.drawable.rav_ltr);
				aboutRav.setPadding(0,0,350,0);
				aboutRav.setText("Sobre el Autor");
				books.setImageResource(R.drawable.pn_books_s);
				aboutPn.setPadding(0,0,260,0);
				aboutPn.setText("sobre la colección");
				break;
			case HEBREW:
				setMargins(rav,0,50,0,0);
				setMargins(aboutRav,0,50,0,0);
				break;
		}
		//LinearLayout main=(LinearLayout) findViewById(R.id.lnrOption2);
	//	LinearLayout main3=(LinearLayout) findViewById(R.id.lnrOption3);
	//	LinearLayout main4=(LinearLayout) findViewById(R.id.lnrOption7);
	//	LinearLayout main5=(LinearLayout) findViewById(R.id.lnrOption8);
		RelativeLayout main2=(RelativeLayout) findViewById(R.id.layout_root);
		if (mPrefs.getInt("BlackBackground", 0)==1)
		{
			txt2.setTextColor(Color.WHITE);

			txt4.setTextColor(Color.WHITE);
//			if(MyLanguage==ENGLISH)
//				toMain.setImageResource(R.drawable.to_main_b_e);
//			if(MyLanguage==RUSSIAN)
//				toMain.setImageResource(R.drawable.to_main_b_r);
//			if(MyLanguage==SPANISH)
//				toMain.setImageResource(R.drawable.to_main_b_s);
//			if(MyLanguage==FRENCH)
//				toMain.setImageResource(R.drawable.to_main_b_f);
//			if(MyLanguage==HEBREW)
//				toMain.setImageResource(R.drawable.to_main_b);
			//main.setBackgroundColor(Color.BLACK);
			main2.setBackgroundColor(Color.BLACK);
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
			ImageView menu= (ImageView) findViewById(R.id.menu);
			menu.setImageResource(R.drawable.ic_action_congif_b);
			main.setBackgroundColor(Color.rgb(120,1,1));
			//main3.setBackgroundColor(Color.BLACK);
			//main4.setBackgroundColor(Color.BLACK);
			//main5.setBackgroundColor(Color.BLACK);


		}
		toMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{
					Class ourClass = null;
					Intent ourIntent;
					ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
					ourIntent = new Intent(About_p.this, ourClass);
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
				ContextThemeWrapper ctw = new ContextThemeWrapper(About_p.this, R.style.CustomPopupTheme);
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
									ourIntent = new Intent(About_p.this, ourClass);
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
								ourIntent = new Intent(About_p.this, ourClass);
								ourIntent.putExtra("homePage", true);
								shPrefEditor.putString("where", "About_p");
								shPrefEditor.commit();
								startActivity(ourIntent);
								break;

							case 1:/*to books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.MainActivity");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(About_p.this, ourClass);
								ourIntent.putExtra("homePage", false);
								startActivity(ourIntent);
								break;

							case 2:/*pninaYomit*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.pninaYomit");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(About_p.this, ourClass);
								startActivity(ourIntent);
								break;

							case 3:/*search in all books*/
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.SearchHelp");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								ourIntent = new Intent(About_p.this, ourClass);
								startActivity(ourIntent);

								break;

							case 4:/*acronyms*/
								acronymsDecode();
								break;

							case 5:/*feedback*/
								try
								{
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.Feedback");
									ourIntent = new Intent(About_p.this, ourClass);
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
									ourIntent = new Intent(About_p.this, ourClass);
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
									ourIntent = new Intent(About_p.this, ourClass);
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
	}


}
