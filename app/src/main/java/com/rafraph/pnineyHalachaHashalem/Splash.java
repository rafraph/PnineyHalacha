 package com.rafraph.pnineyHalachaHashalem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Locale;


public class Splash extends Activity{
	static SharedPreferences mPrefs;
	public boolean newVersion = false;
	public int MyLanguage;
	public int stop;
	public Dialog  newVersionDialog;
	public Context context;
	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;
	SharedPreferences.Editor shPrefEditor;
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		MyLanguage = mPrefs.getInt("MyLanguage", -1);
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
					break;
				case "fr":
					MyLanguage = FRENCH;
					break;
				default:
					MyLanguage = HEBREW;
					break;
			}

		}
		shPrefEditor.putInt("MyLanguage", MyLanguage);
		shPrefEditor.commit();
		Bundle extras = getIntent().getExtras();
		// if its not first virsion and have last place
		if (mPrefs.getInt("StartInLastLocation", 1) == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && (mPrefs.getString("Version", "").equals("5.1.14") == true)) {

			try {
				Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
				Intent ourIntent = new Intent(Splash.this, ourClass);
				int[] book_chapter = new int[2];
				book_chapter[0] = 0xFFFF;
				book_chapter[1] = 0xFFFF;
				ourIntent.putExtra("book_chapter", book_chapter);
				ourIntent.putExtra("fromSplash", true);

				setContentView(R.layout.splash);
				Thread timer2 = new Thread() {
					public void run() {
						try {
							setContentView(R.layout.splash);
							sleep(800);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							startActivity(ourIntent);
						}
					}
				};
				timer2.start();


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		// if its not first virsion and not last place
		if (mPrefs.getString("Version", "").equals("5.1.14") == true) {

			try {
				Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.textMain");
				Intent ourIntent = new Intent(Splash.this, ourClass);
				int[] book_chapter = new int[2];
				book_chapter[0] = 0xFFFF;
				book_chapter[1] = 0xFFFF;
				ourIntent.putExtra("book_chapter", book_chapter);
				ourIntent.putExtra("fromSplash", true);

				setContentView(R.layout.splash);
				Thread timer2 = new Thread() {
					public void run() {
						try {
							sleep(800);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							stop = 2;
							if (!(mPrefs.getInt("StartInLastLocation", 1) == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && (mPrefs.getString("Version", "").equals("5.1.14") == true))) {
								Class ourClass = null;
								try {
									ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								Intent ourIntent = new Intent(Splash.this, ourClass);
								ourIntent.putExtra("goLast", true);
								startActivity(ourIntent);
							}
						}
					}
				};
				timer2.start();


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		else {
			stop = 1;
			setContentView(R.layout.splash);
			ImageView lili = (ImageView) findViewById(R.id.lili);
			Glide.with(this).load(R.drawable.first_gif).into(lili);
			LinearLayout lili3 = (LinearLayout) findViewById(R.id.hi);
			Glide.with(this).load(R.drawable.first_gif).into(lili);
			lili3.setBackground(lili.getDrawable());

			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			String version;
			try {
				version = packageManager.getPackageInfo(packageName, 0).versionName;
				setContentView(R.layout.splash);
				ImageView lili2 = (ImageView) findViewById(R.id.lili);
				switch (MyLanguage) {
					case ENGLISH:
						Glide.with(this).load(R.drawable.gif_en).into(lili2);
						break;
					case RUSSIAN:
						Glide.with(this).load(R.drawable.gif_ru).into(lili2);
						break;
					case SPANISH:
						Glide.with(this).load(R.drawable.gif_es).into(lili2);
						break;
					case FRENCH:
						Glide.with(this).load(R.drawable.gif_fr).into(lili2);
						break;
					default:
						Glide.with(this).load(R.drawable.first_gif).into(lili2);
						break;
				}


				lili3 = (LinearLayout) findViewById(R.id.hi);
				switch (MyLanguage) {
					case ENGLISH:
						Glide.with(this).load(R.drawable.gif_en).into(lili);
						break;
					case RUSSIAN:
						Glide.with(this).load(R.drawable.gif_ru).into(lili);
						break;
					case SPANISH:
						Glide.with(this).load(R.drawable.gif_es).into(lili);
						break;
					case FRENCH:
						Glide.with(this).load(R.drawable.gif_fr).into(lili);
						break;
					default:
						Glide.with(this).load(R.drawable.first_gif).into(lili);
						break;
				}
				lili3.setBackground(lili.getDrawable());


			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
			Thread timer2 = new Thread() {
				public void run() {
					try {
						sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						stop = 2;
						if (!(mPrefs.getInt("StartInLastLocation", 1) == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && (mPrefs.getString("Version", "").equals("5.1.14") == true))) {
							Class ourClass = null;
							try {
								ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.HomePage");
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							Intent ourIntent = new Intent(Splash.this, ourClass);
							ourIntent.putExtra("goLast", true);
							startActivity(ourIntent);
						}
					}
				}
			};
			timer2.start();
		}
	}

	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
