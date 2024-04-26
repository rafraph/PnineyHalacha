package com.rafraph.pnineyHalachaHashalem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class SearchableActivity extends Activity 
{
	public Util util;
	String[][] chaptersFiles = new String[Util.BOOKS_NUMBER][31];
	String[][] chaptersNames = new String[Util.BOOKS_NUMBER][31];
	public List<String> listBookLocation = new ArrayList<String>();
	public List<String> listStrAnchor = new ArrayList<String>();
	public ListView searchListView = null;
	public String query;
	public static final String PREFS_NAME = "MyPrefsFile";
	public String sectionsForToast = null;
	public int i = 0;
	public String hebCharacter = "אבגדהוזחטיכלמנסעפצקרשתםןץףך -'\"";
	public boolean validQuery = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		final SharedPreferences mPrefs;
		mPrefs = getSharedPreferences(PREFS_NAME, 0);

		util = new Util(this);
		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
		{
			query = intent.getStringExtra(SearchManager.QUERY);
//			query = "ו";// for test of the search
			for (i=0; i<query.length(); i++)
			{
				validQuery = hebCharacter.contains(query.substring(i, i+1));
				if(validQuery == false)
				{
					break;
				}
			}

			if(validQuery == true)		
			{
				searchListView = (ListView) findViewById(R.id.list);
				fillChaptersFiles();
				fillChaptersNames();
				doMySearch();
				showResults();

				searchListView.setOnItemClickListener(new OnItemClickListener() 
				{
					boolean cameFromSearch = false;
					String searchPosition = null;
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						try
						{
							Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.TextMain");
							Intent ourIntent = new Intent(SearchableActivity.this, ourClass);

							searchPosition = listStrAnchor.get(position-1);
							cameFromSearch = true;

							ourIntent.putExtra("cameFromSearch", cameFromSearch);
							ourIntent.putExtra("searchPosition", searchPosition);
							ourIntent.putExtra("query", query);
							sectionsForToast = listBookLocation.get(position - 1);
							if (sectionsForToast.indexOf("הערות:") != -1) {
								sectionsForToast = sectionsForToast.substring(sectionsForToast.indexOf("הערות: ") + 7, sectionsForToast.indexOf(")"));
							} else {
								sectionsForToast = "";
							}
							ourIntent.putExtra("sectionsForToast", sectionsForToast);

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
				final Context context = this;
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
						SearchableActivity.this.finish();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.searchable, menu);
		return true;
	}

	public boolean doMySearch()
	{
		InputStream is;
		int size, i, j, index, index_anchor_start, index_anchor_end, anchorId=0, lastanchorId=0, globalCounter=0, chapterCounter=0, noteIndex = 0;
		byte[] buffer;
		String strText = null, strAnchor=null, section=null, sections=null; 
		String prefixAnchor="<a name=" ;

		for(i=0; i<Util.BOOKS_NUMBER; i++)
		{
			for(j=1; j<=util.lastChapter[i]; j++)//starts from 1 since I don't need to search in "tochen" files
			{
				try 
				{
					chapterCounter = 0;
					sections="";
					is = getAssets().open(chaptersFiles[i][j]);
					size = is.available(); 
					buffer = new byte[size];
					is.read(buffer);
					is.close();
					strText  = new String(buffer);

					index = 0;
					index_anchor_start = 0;
					index_anchor_end = 0;
					noteIndex = strText.indexOf("<div style=\"display:none;\">", 0);
					while(index != (-1))
					{
//						System.out.println("book="+i+" chapter="+j+" chapterCounter="+chapterCounter);/*for test - if need to check crash with searching*/
//						if(i==2 && j==12 && chapterCounter==1455)// for test of the search
//							i=2;
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
							chapterCounter++;
							lastanchorId = anchorId;
						}
					}
					if(chapterCounter > 0)
					{
						listBookLocation.add("["+chapterCounter+"] "+chaptersNames[i][j]+ " (" + sections+ ")");/*only one item in the list per chapter*/
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		}
		TextView textView = new TextView(this);
		textView.setText(query + ": נמצאו "+globalCounter+" תוצאות");
		textView.setTextSize(30);
		searchListView.addHeaderView(textView);

		return true;
	}

	public void showResults()
	{
		ArrayAdapter  adapter;
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listBookLocation);
		searchListView.setAdapter(adapter);
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
		chaptersFiles[Util.BRACHOT][0] = "brachot_tochen.html";
		chaptersFiles[Util.BRACHOT][1] = "brachot_1.html";
		chaptersFiles[Util.BRACHOT][2] = "brachot_2.html";
		chaptersFiles[Util.BRACHOT][3] = "brachot_3.html";
		chaptersFiles[Util.BRACHOT][4] = "brachot_4.html";
		chaptersFiles[Util.BRACHOT][5] = "brachot_5.html";
		chaptersFiles[Util.BRACHOT][6] = "brachot_6.html";
		chaptersFiles[Util.BRACHOT][7] = "brachot_7.html";
		chaptersFiles[Util.BRACHOT][8] = "brachot_8.html";
		chaptersFiles[Util.BRACHOT][9] = "brachot_9.html";
		chaptersFiles[Util.BRACHOT][10] = "brachot_10.html";
		chaptersFiles[Util.BRACHOT][11] = "brachot_11.html";
		chaptersFiles[Util.BRACHOT][12] = "brachot_12.html";
		chaptersFiles[Util.BRACHOT][13] = "brachot_13.html";
		chaptersFiles[Util.BRACHOT][14] = "brachot_14.html";
		chaptersFiles[Util.BRACHOT][15] = "brachot_15.html";
		chaptersFiles[Util.BRACHOT][16] = "brachot_16.html";
		chaptersFiles[Util.BRACHOT][17] = "brachot_17.html";
        chaptersFiles[Util.BRACHOT][18] = "brachot_18.html";
		/*GIYUR*/
		chaptersFiles[Util.GIYUR][0] = "giyur_tochen.html";
		chaptersFiles[Util.GIYUR][1] = "giyur_1.html";
		chaptersFiles[Util.GIYUR][2] = "giyur_2.html";
		chaptersFiles[Util.GIYUR][3] = "giyur_3.html";
		chaptersFiles[Util.GIYUR][4] = "giyur_4.html";
		chaptersFiles[Util.GIYUR][5] = "giyur_5.html";
		chaptersFiles[Util.GIYUR][6] = "giyur_6.html";
		chaptersFiles[Util.GIYUR][7] = "giyur_7.html";
		chaptersFiles[Util.GIYUR][8] = "giyur_8.html";
		chaptersFiles[Util.GIYUR][9] = "giyur_9.html";
		/*HAAMVEHAAREZ*/
		chaptersFiles[Util.HAAMVEHAAREZ][0] = "haamvehaarez_tochen.html";
		chaptersFiles[Util.HAAMVEHAAREZ][1] = "haamvehaarez_1.html";
		chaptersFiles[Util.HAAMVEHAAREZ][2] = "haamvehaarez_2.html";
		chaptersFiles[Util.HAAMVEHAAREZ][3] = "haamvehaarez_3.html";
		chaptersFiles[Util.HAAMVEHAAREZ][4] = "haamvehaarez_4.html";
		chaptersFiles[Util.HAAMVEHAAREZ][5] = "haamvehaarez_5.html";
		chaptersFiles[Util.HAAMVEHAAREZ][6] = "haamvehaarez_6.html";
		chaptersFiles[Util.HAAMVEHAAREZ][7] = "haamvehaarez_7.html";
		chaptersFiles[Util.HAAMVEHAAREZ][8] = "haamvehaarez_8.html";
		chaptersFiles[Util.HAAMVEHAAREZ][9] = "haamvehaarez_9.html";
		chaptersFiles[Util.HAAMVEHAAREZ][10] = "haamvehaarez_10.html";
		/*ZMANIM*/
		chaptersFiles[Util.ZMANIM][0] = "zmanim_tochen.html";
		chaptersFiles[Util.ZMANIM][1] = "zmanim_1.html";
		chaptersFiles[Util.ZMANIM][2] = "zmanim_2.html";
		chaptersFiles[Util.ZMANIM][3] = "zmanim_3.html";
		chaptersFiles[Util.ZMANIM][4] = "zmanim_4.html";
		chaptersFiles[Util.ZMANIM][5] = "zmanim_5.html";
		chaptersFiles[Util.ZMANIM][6] = "zmanim_6.html";
		chaptersFiles[Util.ZMANIM][7] = "zmanim_7.html";
		chaptersFiles[Util.ZMANIM][8] = "zmanim_8.html";
		chaptersFiles[Util.ZMANIM][9] = "zmanim_9.html";
		chaptersFiles[Util.ZMANIM][10] = "zmanim_10.html";
		chaptersFiles[Util.ZMANIM][11] = "zmanim_11.html";
		chaptersFiles[Util.ZMANIM][12] = "zmanim_12.html";
		chaptersFiles[Util.ZMANIM][13] = "zmanim_13.html";
		chaptersFiles[Util.ZMANIM][14] = "zmanim_14.html";
		chaptersFiles[Util.ZMANIM][15] = "zmanim_15.html";
		chaptersFiles[Util.ZMANIM][16] = "zmanim_16.html";
		chaptersFiles[Util.ZMANIM][17] = "zmanim_17.html";
		/*TAHARAT*/
		chaptersFiles[Util.TAHARAT][0] = "taharat_tochen.html";
		chaptersFiles[Util.TAHARAT][1] = "taharat_1.html";
		chaptersFiles[Util.TAHARAT][2] = "taharat_2.html";
		chaptersFiles[Util.TAHARAT][3] = "taharat_3.html";
		chaptersFiles[Util.TAHARAT][4] = "taharat_4.html";
		chaptersFiles[Util.TAHARAT][5] = "taharat_5.html";
		chaptersFiles[Util.TAHARAT][6] = "taharat_6.html";
		chaptersFiles[Util.TAHARAT][7] = "taharat_7.html";
		chaptersFiles[Util.TAHARAT][8] = "taharat_8.html";
		chaptersFiles[Util.TAHARAT][9] = "taharat_9.html";
		chaptersFiles[Util.TAHARAT][10] = "taharat_10.html";
		/*YAMIM*/
		chaptersFiles[Util.YAMIM][0] = "yamim_tochen.html";
		chaptersFiles[Util.YAMIM][1] = "yamim_1.html";
		chaptersFiles[Util.YAMIM][2] = "yamim_2.html";
		chaptersFiles[Util.YAMIM][3] = "yamim_3.html";
		chaptersFiles[Util.YAMIM][4] = "yamim_4.html";
		chaptersFiles[Util.YAMIM][5] = "yamim_5.html";
		chaptersFiles[Util.YAMIM][6] = "yamim_6.html";
		chaptersFiles[Util.YAMIM][7] = "yamim_7.html";
		chaptersFiles[Util.YAMIM][8] = "yamim_8.html";
		chaptersFiles[Util.YAMIM][9] = "yamim_9.html";
		chaptersFiles[Util.YAMIM][10] = "yamim_10.html";
		/*KASHRUT_A*/
		chaptersFiles[Util.KASHRUT_A][0] = "kashrut_a_tochen.html";
		chaptersFiles[Util.KASHRUT_A][1] = "kashrut_1.html";
		chaptersFiles[Util.KASHRUT_A][2] = "kashrut_2.html";
		chaptersFiles[Util.KASHRUT_A][3] = "kashrut_3.html";
		chaptersFiles[Util.KASHRUT_A][4] = "kashrut_4.html";
		chaptersFiles[Util.KASHRUT_A][5] = "kashrut_5.html";
		chaptersFiles[Util.KASHRUT_A][6] = "kashrut_6.html";
		chaptersFiles[Util.KASHRUT_A][7] = "kashrut_7.html";
		chaptersFiles[Util.KASHRUT_A][8] = "kashrut_8.html";
		chaptersFiles[Util.KASHRUT_A][9] = "kashrut_9.html";
		chaptersFiles[Util.KASHRUT_A][10] = "kashrut_10.html";
		chaptersFiles[Util.KASHRUT_A][11] = "kashrut_11.html";
		chaptersFiles[Util.KASHRUT_A][12] = "kashrut_12.html";
		chaptersFiles[Util.KASHRUT_A][13] = "kashrut_13.html";
		chaptersFiles[Util.KASHRUT_A][14] = "kashrut_14.html";
		chaptersFiles[Util.KASHRUT_A][15] = "kashrut_15.html";
		chaptersFiles[Util.KASHRUT_A][16] = "kashrut_16.html";
		chaptersFiles[Util.KASHRUT_A][17] = "kashrut_17.html";
		chaptersFiles[Util.KASHRUT_A][18] = "kashrut_18.html";
		chaptersFiles[Util.KASHRUT_A][19] = "kashrut_19.html";
        /*KASHRUT_B*/
        chaptersFiles[Util.KASHRUT_B][0] = "kashrut_b_tochen.html";
        chaptersFiles[Util.KASHRUT_B][1] = "kashrut_20.html";
        chaptersFiles[Util.KASHRUT_B][2] = "kashrut_21.html";
        chaptersFiles[Util.KASHRUT_B][3] = "kashrut_22.html";
        chaptersFiles[Util.KASHRUT_B][4] = "kashrut_23.html";
        chaptersFiles[Util.KASHRUT_B][5] = "kashrut_24.html";
        chaptersFiles[Util.KASHRUT_B][6] = "kashrut_25.html";
        chaptersFiles[Util.KASHRUT_B][7] = "kashrut_26.html";
        chaptersFiles[Util.KASHRUT_B][8] = "kashrut_27.html";
        chaptersFiles[Util.KASHRUT_B][9] = "kashrut_28.html";
        chaptersFiles[Util.KASHRUT_B][10] = "kashrut_29.html";
        chaptersFiles[Util.KASHRUT_B][11] = "kashrut_30.html";
        chaptersFiles[Util.KASHRUT_B][12] = "kashrut_31.html";
        chaptersFiles[Util.KASHRUT_B][13] = "kashrut_32.html";
        chaptersFiles[Util.KASHRUT_B][14] = "kashrut_33.html";
        chaptersFiles[Util.KASHRUT_B][15] = "kashrut_34.html";
        chaptersFiles[Util.KASHRUT_B][16] = "kashrut_35.html";
        chaptersFiles[Util.KASHRUT_B][17] = "kashrut_36.html";
        chaptersFiles[Util.KASHRUT_B][18] = "kashrut_37.html";
        chaptersFiles[Util.KASHRUT_B][19] = "kashrut_38.html";
		/*LIKUTIM_A*/
		chaptersFiles[Util.LIKUTIM_A][0] = "likutim_a_tochen.html";
		chaptersFiles[Util.LIKUTIM_A][1] = "likutim_a_1.html";
		chaptersFiles[Util.LIKUTIM_A][2] = "likutim_a_2.html";
		chaptersFiles[Util.LIKUTIM_A][3] = "likutim_a_3.html";
		chaptersFiles[Util.LIKUTIM_A][4] = "likutim_a_4.html";
		chaptersFiles[Util.LIKUTIM_A][5] = "likutim_a_5.html";
		chaptersFiles[Util.LIKUTIM_A][6] = "likutim_a_6.html";
		chaptersFiles[Util.LIKUTIM_A][7] = "likutim_a_7.html";
		chaptersFiles[Util.LIKUTIM_A][8] = "likutim_a_8.html";
		chaptersFiles[Util.LIKUTIM_A][9] = "likutim_a_9.html";
		chaptersFiles[Util.LIKUTIM_A][10] = "likutim_a_10.html";
		chaptersFiles[Util.LIKUTIM_A][11] = "likutim_a_11.html";
		chaptersFiles[Util.LIKUTIM_A][12] = "likutim_a_12.html";
		chaptersFiles[Util.LIKUTIM_A][13] = "likutim_a_13.html";
		/*LIKUTIM_B*/
		chaptersFiles[Util.LIKUTIM_B][0] = "likutim_b_tochen.html";
		chaptersFiles[Util.LIKUTIM_B][1] = "likutim_b_1.html";
		chaptersFiles[Util.LIKUTIM_B][2] = "likutim_b_2.html";
		chaptersFiles[Util.LIKUTIM_B][3] = "likutim_b_3.html";
		chaptersFiles[Util.LIKUTIM_B][4] = "likutim_b_4.html";
		chaptersFiles[Util.LIKUTIM_B][5] = "likutim_b_5.html";
		chaptersFiles[Util.LIKUTIM_B][6] = "likutim_b_6.html";
		chaptersFiles[Util.LIKUTIM_B][7] = "likutim_b_7.html";
		chaptersFiles[Util.LIKUTIM_B][8] = "likutim_b_8.html";
		chaptersFiles[Util.LIKUTIM_B][9] = "likutim_b_9.html";
		chaptersFiles[Util.LIKUTIM_B][10] = "likutim_b_10.html";
		chaptersFiles[Util.LIKUTIM_B][11] = "likutim_b_11.html";
		chaptersFiles[Util.LIKUTIM_B][12] = "likutim_b_12.html";
		chaptersFiles[Util.LIKUTIM_B][13] = "likutim_b_13.html";
		chaptersFiles[Util.LIKUTIM_B][14] = "likutim_b_14.html";
		chaptersFiles[Util.LIKUTIM_B][15] = "likutim_b_15.html";
		chaptersFiles[Util.LIKUTIM_B][16] = "likutim_b_16.html";
		chaptersFiles[Util.LIKUTIM_B][17] = "likutim_b_17.html";
		/*MOADIM*/
		chaptersFiles[Util.MOADIM][0] = "moadim_tochen.html";
		chaptersFiles[Util.MOADIM][1] = "moadim_1.html";
		chaptersFiles[Util.MOADIM][2] = "moadim_2.html";
		chaptersFiles[Util.MOADIM][3] = "moadim_3.html";
		chaptersFiles[Util.MOADIM][4] = "moadim_4.html";
		chaptersFiles[Util.MOADIM][5] = "moadim_5.html";
		chaptersFiles[Util.MOADIM][6] = "moadim_6.html";
		chaptersFiles[Util.MOADIM][7] = "moadim_7.html";
		chaptersFiles[Util.MOADIM][8] = "moadim_8.html";
		chaptersFiles[Util.MOADIM][9] = "moadim_9.html";
		chaptersFiles[Util.MOADIM][10] = "moadim_10.html";
		chaptersFiles[Util.MOADIM][11] = "moadim_11.html";
		chaptersFiles[Util.MOADIM][12] = "moadim_12.html";
		chaptersFiles[Util.MOADIM][13] = "moadim_13.html";
		/*MISHPACHA*/
		chaptersFiles[Util.MISHPACHA][0] = "mishpacha_tochen.html";
		chaptersFiles[Util.MISHPACHA][1] = "mishpacha_1.html";
		chaptersFiles[Util.MISHPACHA][2] = "mishpacha_2.html";
		chaptersFiles[Util.MISHPACHA][3] = "mishpacha_3.html";
		chaptersFiles[Util.MISHPACHA][4] = "mishpacha_4.html";
		chaptersFiles[Util.MISHPACHA][5] = "mishpacha_5.html";
		chaptersFiles[Util.MISHPACHA][6] = "mishpacha_6.html";
		chaptersFiles[Util.MISHPACHA][7] = "mishpacha_7.html";
		chaptersFiles[Util.MISHPACHA][8] = "mishpacha_8.html";
		chaptersFiles[Util.MISHPACHA][9] = "mishpacha_9.html";
		chaptersFiles[Util.MISHPACHA][10] = "mishpacha_10.html";
		/*SUCOT*/
		chaptersFiles[Util.SUCOT][0] = "sucot_tochen.html";
		chaptersFiles[Util.SUCOT][1] = "sucot_1.html";
		chaptersFiles[Util.SUCOT][2] = "sucot_2.html";
		chaptersFiles[Util.SUCOT][3] = "sucot_3.html";
		chaptersFiles[Util.SUCOT][4] = "sucot_4.html";
		chaptersFiles[Util.SUCOT][5] = "sucot_5.html";
		chaptersFiles[Util.SUCOT][6] = "sucot_6.html";
		chaptersFiles[Util.SUCOT][7] = "sucot_7.html";
		chaptersFiles[Util.SUCOT][8] = "sucot_8.html";
		/*PESACH*/
		chaptersFiles[Util.PESACH][0] = "pesach_tochen.html";
		chaptersFiles[Util.PESACH][1] = "pesach_1.html";
		chaptersFiles[Util.PESACH][2] = "pesach_2.html";
		chaptersFiles[Util.PESACH][3] = "pesach_3.html";
		chaptersFiles[Util.PESACH][4] = "pesach_4.html";
		chaptersFiles[Util.PESACH][5] = "pesach_5.html";
		chaptersFiles[Util.PESACH][6] = "pesach_6.html";
		chaptersFiles[Util.PESACH][7] = "pesach_7.html";
		chaptersFiles[Util.PESACH][8] = "pesach_8.html";
		chaptersFiles[Util.PESACH][9] = "pesach_9.html";
		chaptersFiles[Util.PESACH][10] = "pesach_10.html";
		chaptersFiles[Util.PESACH][11] = "pesach_11.html";
		chaptersFiles[Util.PESACH][12] = "pesach_12.html";
		chaptersFiles[Util.PESACH][13] = "pesach_13.html";
		chaptersFiles[Util.PESACH][14] = "pesach_14.html";
		chaptersFiles[Util.PESACH][15] = "pesach_15.html";
		chaptersFiles[Util.PESACH][16] = "pesach_16.html";
		/*SHVIIT*/
		chaptersFiles[Util.SHVIIT][0] = "shviit_tochen.html";
		chaptersFiles[Util.SHVIIT][1] = "shviit_1.html";
		chaptersFiles[Util.SHVIIT][2] = "shviit_2.html";
		chaptersFiles[Util.SHVIIT][3] = "shviit_3.html";
		chaptersFiles[Util.SHVIIT][4] = "shviit_4.html";
		chaptersFiles[Util.SHVIIT][5] = "shviit_5.html";
		chaptersFiles[Util.SHVIIT][6] = "shviit_6.html";
		chaptersFiles[Util.SHVIIT][7] = "shviit_7.html";
		chaptersFiles[Util.SHVIIT][8] = "shviit_8.html";
		chaptersFiles[Util.SHVIIT][9] = "shviit_9.html";
		chaptersFiles[Util.SHVIIT][10] = "shviit_10.html";
		chaptersFiles[Util.SHVIIT][11] = "shviit_11.html";
		/*SHABAT*/
		chaptersFiles[Util.SHABAT][0] = "shabat_tochen.html";
		chaptersFiles[Util.SHABAT][1] = "shabat_1.html";
		chaptersFiles[Util.SHABAT][2] = "shabat_2.html";
		chaptersFiles[Util.SHABAT][3] = "shabat_3.html";
		chaptersFiles[Util.SHABAT][4] = "shabat_4.html";
		chaptersFiles[Util.SHABAT][5] = "shabat_5.html";
		chaptersFiles[Util.SHABAT][6] = "shabat_6.html";
		chaptersFiles[Util.SHABAT][7] = "shabat_7.html";
		chaptersFiles[Util.SHABAT][8] = "shabat_8.html";
		chaptersFiles[Util.SHABAT][9] = "shabat_9.html";
		chaptersFiles[Util.SHABAT][10] = "shabat_10.html";
		chaptersFiles[Util.SHABAT][11] = "shabat_11.html";
		chaptersFiles[Util.SHABAT][12] = "shabat_12.html";
		chaptersFiles[Util.SHABAT][13] = "shabat_13.html";
		chaptersFiles[Util.SHABAT][14] = "shabat_14.html";
		chaptersFiles[Util.SHABAT][15] = "shabat_15.html";
		chaptersFiles[Util.SHABAT][16] = "shabat_16.html";
		chaptersFiles[Util.SHABAT][17] = "shabat_17.html";
		chaptersFiles[Util.SHABAT][18] = "shabat_18.html";
		chaptersFiles[Util.SHABAT][19] = "shabat_19.html";
		chaptersFiles[Util.SHABAT][20] = "shabat_20.html";
		chaptersFiles[Util.SHABAT][21] = "shabat_21.html";
		chaptersFiles[Util.SHABAT][22] = "shabat_22.html";
		chaptersFiles[Util.SHABAT][23] = "shabat_23.html";
		chaptersFiles[Util.SHABAT][24] = "shabat_24.html";
		chaptersFiles[Util.SHABAT][25] = "shabat_25.html";
		chaptersFiles[Util.SHABAT][26] = "shabat_26.html";
		chaptersFiles[Util.SHABAT][27] = "shabat_27.html";
		chaptersFiles[Util.SHABAT][28] = "shabat_28.html";
		chaptersFiles[Util.SHABAT][29] = "shabat_29.html";
		chaptersFiles[Util.SHABAT][30] = "shabat_30.html";
		/*SIMCHAT*/
		chaptersFiles[Util.SIMCHAT][0] = "simchat_tochen.html";
		chaptersFiles[Util.SIMCHAT][1] = "simchat_1.html";
		chaptersFiles[Util.SIMCHAT][2] = "simchat_2.html";
		chaptersFiles[Util.SIMCHAT][3] = "simchat_3.html";
		chaptersFiles[Util.SIMCHAT][4] = "simchat_4.html";
		chaptersFiles[Util.SIMCHAT][5] = "simchat_5.html";
		chaptersFiles[Util.SIMCHAT][6] = "simchat_6.html";
		chaptersFiles[Util.SIMCHAT][7] = "simchat_7.html";
		chaptersFiles[Util.SIMCHAT][8] = "simchat_8.html";
		chaptersFiles[Util.SIMCHAT][9] = "simchat_9.html";
		chaptersFiles[Util.SIMCHAT][10] = "simchat_10.html";
		/*TEFILA*/
		chaptersFiles[Util.TEFILA][0] = "tefila_tochen.html";
		chaptersFiles[Util.TEFILA][1] = "tefila_1.html";
		chaptersFiles[Util.TEFILA][2] = "tefila_2.html";
		chaptersFiles[Util.TEFILA][3] = "tefila_3.html";
		chaptersFiles[Util.TEFILA][4] = "tefila_4.html";
		chaptersFiles[Util.TEFILA][5] = "tefila_5.html";
		chaptersFiles[Util.TEFILA][6] = "tefila_6.html";
		chaptersFiles[Util.TEFILA][7] = "tefila_7.html";
		chaptersFiles[Util.TEFILA][8] = "tefila_8.html";
		chaptersFiles[Util.TEFILA][9] = "tefila_9.html";
		chaptersFiles[Util.TEFILA][10] = "tefila_10.html";
		chaptersFiles[Util.TEFILA][11] = "tefila_11.html";
		chaptersFiles[Util.TEFILA][12] = "tefila_12.html";
		chaptersFiles[Util.TEFILA][13] = "tefila_13.html";
		chaptersFiles[Util.TEFILA][14] = "tefila_14.html";
		chaptersFiles[Util.TEFILA][15] = "tefila_15.html";
		chaptersFiles[Util.TEFILA][16] = "tefila_16.html";
		chaptersFiles[Util.TEFILA][17] = "tefila_17.html";
		chaptersFiles[Util.TEFILA][18] = "tefila_18.html";
		chaptersFiles[Util.TEFILA][19] = "tefila_19.html";
		chaptersFiles[Util.TEFILA][20] = "tefila_20.html";
		chaptersFiles[Util.TEFILA][21] = "tefila_21.html";
		chaptersFiles[Util.TEFILA][22] = "tefila_22.html";
		chaptersFiles[Util.TEFILA][23] = "tefila_23.html";
		chaptersFiles[Util.TEFILA][24] = "tefila_24.html";
		chaptersFiles[Util.TEFILA][25] = "tefila_25.html";
		chaptersFiles[Util.TEFILA][26] = "tefila_26.html";
		/*TEFILAT_NASHIM*/
		chaptersFiles[Util.TEFILAT_NASHIM][0] = "tefilat_nashim_tochen.html";
		chaptersFiles[Util.TEFILAT_NASHIM][1] = "tefilat_nashim_1.html";
		chaptersFiles[Util.TEFILAT_NASHIM][2] = "tefilat_nashim_2.html";
		chaptersFiles[Util.TEFILAT_NASHIM][3] = "tefilat_nashim_3.html";
		chaptersFiles[Util.TEFILAT_NASHIM][4] = "tefilat_nashim_4.html";
		chaptersFiles[Util.TEFILAT_NASHIM][5] = "tefilat_nashim_5.html";
		chaptersFiles[Util.TEFILAT_NASHIM][6] = "tefilat_nashim_6.html";
		chaptersFiles[Util.TEFILAT_NASHIM][7] = "tefilat_nashim_7.html";
		chaptersFiles[Util.TEFILAT_NASHIM][8] = "tefilat_nashim_8.html";
		chaptersFiles[Util.TEFILAT_NASHIM][9] = "tefilat_nashim_9.html";
		chaptersFiles[Util.TEFILAT_NASHIM][10] = "tefilat_nashim_10.html";
		chaptersFiles[Util.TEFILAT_NASHIM][11] = "tefilat_nashim_11.html";
		chaptersFiles[Util.TEFILAT_NASHIM][12] = "tefilat_nashim_12.html";
		chaptersFiles[Util.TEFILAT_NASHIM][13] = "tefilat_nashim_13.html";
		chaptersFiles[Util.TEFILAT_NASHIM][14] = "tefilat_nashim_14.html";
		chaptersFiles[Util.TEFILAT_NASHIM][15] = "tefilat_nashim_15.html";
		chaptersFiles[Util.TEFILAT_NASHIM][16] = "tefilat_nashim_16.html";
		chaptersFiles[Util.TEFILAT_NASHIM][17] = "tefilat_nashim_17.html";
		chaptersFiles[Util.TEFILAT_NASHIM][18] = "tefilat_nashim_18.html";
		chaptersFiles[Util.TEFILAT_NASHIM][19] = "tefilat_nashim_19.html";
		chaptersFiles[Util.TEFILAT_NASHIM][20] = "tefilat_nashim_20.html";
		chaptersFiles[Util.TEFILAT_NASHIM][21] = "tefilat_nashim_21.html";
		chaptersFiles[Util.TEFILAT_NASHIM][22] = "tefilat_nashim_22.html";
		chaptersFiles[Util.TEFILAT_NASHIM][23] = "tefilat_nashim_23.html";
		chaptersFiles[Util.TEFILAT_NASHIM][24] = "tefilat_nashim_24.html";
		/*HAR_BRACHOT*/
		chaptersFiles[Util.HAR_BRACHOT][0] = "har_brachot_tochen.html";
		chaptersFiles[Util.HAR_BRACHOT][1] = "har_brachot_1.html";
		chaptersFiles[Util.HAR_BRACHOT][2] = "har_brachot_2.html";
		chaptersFiles[Util.HAR_BRACHOT][3] = "har_brachot_3.html";
		chaptersFiles[Util.HAR_BRACHOT][4] = "har_brachot_4.html";
		chaptersFiles[Util.HAR_BRACHOT][5] = "har_brachot_5.html";
		chaptersFiles[Util.HAR_BRACHOT][6] = "har_brachot_6.html";
		chaptersFiles[Util.HAR_BRACHOT][7] = "har_brachot_7.html";
		chaptersFiles[Util.HAR_BRACHOT][8] = "har_brachot_8.html";
		chaptersFiles[Util.HAR_BRACHOT][9] = "har_brachot_9.html";
		chaptersFiles[Util.HAR_BRACHOT][10] = "har_brachot_10.html";
		chaptersFiles[Util.HAR_BRACHOT][11] = "har_brachot_11.html";
		chaptersFiles[Util.HAR_BRACHOT][12] = "har_brachot_12.html";
		chaptersFiles[Util.HAR_BRACHOT][13] = "har_brachot_13.html";
		chaptersFiles[Util.HAR_BRACHOT][14] = "har_brachot_14.html";
		chaptersFiles[Util.HAR_BRACHOT][15] = "har_brachot_15.html";
		chaptersFiles[Util.HAR_BRACHOT][16] = "har_brachot_16.html";
		chaptersFiles[Util.HAR_BRACHOT][17] = "har_brachot_17.html";
		/*HAR_YAMIM*/
		chaptersFiles[Util.HAR_YAMIM][0] = "har_yamim_tochen.html";
		chaptersFiles[Util.HAR_YAMIM][1] = "har_yamim_1.html";
		chaptersFiles[Util.HAR_YAMIM][2] = "har_yamim_2.html";
		chaptersFiles[Util.HAR_YAMIM][3] = "har_yamim_3.html";
		chaptersFiles[Util.HAR_YAMIM][4] = "har_yamim_4.html";
		chaptersFiles[Util.HAR_YAMIM][5] = "har_yamim_5.html";
		chaptersFiles[Util.HAR_YAMIM][6] = "har_yamim_6.html";
		chaptersFiles[Util.HAR_YAMIM][7] = "har_yamim_7.html";
		chaptersFiles[Util.HAR_YAMIM][8] = "har_yamim_8.html";
		chaptersFiles[Util.HAR_YAMIM][9] = "har_yamim_9.html";
		chaptersFiles[Util.HAR_YAMIM][10] = "har_yamim_10.html";
		/*HAR_MOADIM*/
		chaptersFiles[Util.HAR_MOADIM][0] = "har_moadim_tochen.html";
		chaptersFiles[Util.HAR_MOADIM][1] = "har_moadim_1.html";
		chaptersFiles[Util.HAR_MOADIM][2] = "har_moadim_2.html";
		chaptersFiles[Util.HAR_MOADIM][3] = "har_moadim_3.html";
		chaptersFiles[Util.HAR_MOADIM][4] = "har_moadim_4.html";
		chaptersFiles[Util.HAR_MOADIM][5] = "har_moadim_5.html";
		chaptersFiles[Util.HAR_MOADIM][6] = "har_moadim_6.html";
		chaptersFiles[Util.HAR_MOADIM][7] = "har_moadim_7.html";
		chaptersFiles[Util.HAR_MOADIM][8] = "har_moadim_8.html";
		//chaptersFiles[Util.HAR_MOADIM][9] = "har_moadim_9.html"; //currently there is no chapter 9
		chaptersFiles[Util.HAR_MOADIM][9] = "har_moadim_10.html";
		chaptersFiles[Util.HAR_MOADIM][10] = "har_moadim_11.html";
		chaptersFiles[Util.HAR_MOADIM][11] = "har_moadim_12.html";
		chaptersFiles[Util.HAR_MOADIM][12] = "har_moadim_13.html";
		/*HAR_SUCOT*/
		chaptersFiles[Util.HAR_SUCOT][0] = "sucot_tochen.html";
		chaptersFiles[Util.HAR_SUCOT][1] = "har_sucot_1.html";
		chaptersFiles[Util.HAR_SUCOT][2] = "har_sucot_2.html";
		chaptersFiles[Util.HAR_SUCOT][3] = "har_sucot_3.html";
		chaptersFiles[Util.HAR_SUCOT][4] = "har_sucot_4.html";
		chaptersFiles[Util.HAR_SUCOT][5] = "har_sucot_5.html";
		chaptersFiles[Util.HAR_SUCOT][6] = "har_sucot_6.html";
		chaptersFiles[Util.HAR_SUCOT][7] = "har_sucot_7.html";
		chaptersFiles[Util.HAR_SUCOT][8] = "har_sucot_8.html";
		/*HAR_SHABAT*/
		chaptersFiles[Util.HAR_SHABAT][0] = "har_shabat_tochen.html";
		chaptersFiles[Util.HAR_SHABAT][1] = "har_shabat_1.html";
		chaptersFiles[Util.HAR_SHABAT][2] = "har_shabat_2.html";
		chaptersFiles[Util.HAR_SHABAT][3] = "har_shabat_3.html";
		chaptersFiles[Util.HAR_SHABAT][4] = "har_shabat_4.html";
		chaptersFiles[Util.HAR_SHABAT][5] = "har_shabat_5.html";
		chaptersFiles[Util.HAR_SHABAT][6] = "har_shabat_6.html";
		chaptersFiles[Util.HAR_SHABAT][7] = "har_shabat_7.html";
		chaptersFiles[Util.HAR_SHABAT][8] = "har_shabat_8.html";
		chaptersFiles[Util.HAR_SHABAT][9] = "har_shabat_9.html";
		chaptersFiles[Util.HAR_SHABAT][10] = "har_shabat_10.html";
		chaptersFiles[Util.HAR_SHABAT][11] = "har_shabat_11.html";
		chaptersFiles[Util.HAR_SHABAT][12] = "har_shabat_12.html";
		chaptersFiles[Util.HAR_SHABAT][13] = "har_shabat_13.html";
		chaptersFiles[Util.HAR_SHABAT][14] = "har_shabat_14.html";
		chaptersFiles[Util.HAR_SHABAT][15] = "har_shabat_15.html";
		chaptersFiles[Util.HAR_SHABAT][16] = "har_shabat_16.html";
		chaptersFiles[Util.HAR_SHABAT][17] = "har_shabat_17.html";
		chaptersFiles[Util.HAR_SHABAT][18] = "har_shabat_18.html";
		chaptersFiles[Util.HAR_SHABAT][19] = "har_shabat_19.html";
		chaptersFiles[Util.HAR_SHABAT][20] = "har_shabat_20.html";
		chaptersFiles[Util.HAR_SHABAT][21] = "har_shabat_21.html";
		chaptersFiles[Util.HAR_SHABAT][22] = "har_shabat_22.html";
		chaptersFiles[Util.HAR_SHABAT][23] = "har_shabat_23.html";
		chaptersFiles[Util.HAR_SHABAT][24] = "har_shabat_24.html";
		chaptersFiles[Util.HAR_SHABAT][25] = "har_shabat_25.html";
		chaptersFiles[Util.HAR_SHABAT][26] = "har_shabat_26.html";
		chaptersFiles[Util.HAR_SHABAT][27] = "har_shabat_27.html";
		chaptersFiles[Util.HAR_SHABAT][28] = "har_shabat_28.html";
		chaptersFiles[Util.HAR_SHABAT][29] = "har_shabat_29.html";
		chaptersFiles[Util.HAR_SHABAT][30] = "har_shabat_30.html";
		/*HAR_SIMCHAT*/
		chaptersFiles[Util.HAR_SIMCHAT][0] = "har_simchat_tochen.html";
		chaptersFiles[Util.HAR_SIMCHAT][1] = "har_simchat_1.html";
		chaptersFiles[Util.HAR_SIMCHAT][2] = "har_simchat_2.html";
		chaptersFiles[Util.HAR_SIMCHAT][3] = "har_simchat_3.html";
		chaptersFiles[Util.HAR_SIMCHAT][4] = "har_simchat_4.html";
		chaptersFiles[Util.HAR_SIMCHAT][5] = "har_simchat_5.html";
		chaptersFiles[Util.HAR_SIMCHAT][6] = "har_simchat_6.html";
		chaptersFiles[Util.HAR_SIMCHAT][7] = "har_simchat_7.html";
		chaptersFiles[Util.HAR_SIMCHAT][8] = "har_simchat_8.html";
		chaptersFiles[Util.HAR_SIMCHAT][9] = "har_simchat_9.html";
		chaptersFiles[Util.HAR_SIMCHAT][10] = "har_simchat_10.html";
	}

	private void fillChaptersNames()
	{
		/*BRACHOT*/
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
		chaptersNames[Util.LIKUTIM_B][12] = "ליקוטים ב: יב - הפסקת הריון";
		chaptersNames[Util.LIKUTIM_B][13] = "ליקוטים ב: יג - הלכות ניתוחי מתים";
		chaptersNames[Util.LIKUTIM_B][14] = "ליקוטים ב: יד - השתלת אברים";
		chaptersNames[Util.LIKUTIM_B][15] = "ליקוטים ב: טו - הלכות הנוטה למות";
		chaptersNames[Util.LIKUTIM_B][16] = "ליקוטים ב: טז - ליקוטים";
		chaptersNames[Util.LIKUTIM_B][17] = "ליקוטים ב: יז - חברה ושליחות";
		/*MOADIM*/
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
		/*MISHPACHA*/
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
		/*SUCOT*/
		chaptersNames[Util.SUCOT][1] = "סוכות: א - חג הסוכות";
		chaptersNames[Util.SUCOT][2] = "סוכות: ב - הלכות סוכה";
		chaptersNames[Util.SUCOT][3] = "סוכות: ג - ישיבה בסוכה";
		chaptersNames[Util.SUCOT][4] = "סוכות: ד - ארבעת המינים";
		chaptersNames[Util.SUCOT][5] = "סוכות: ה - נטילת לולב";
		chaptersNames[Util.SUCOT][6] = "סוכות: ו - הושענא רבה";
		chaptersNames[Util.SUCOT][7] = "סוכות: ז - שמיני עצרת";
		chaptersNames[Util.SUCOT][8] = "סוכות: ח - הקהל";
		/*PESACH*/
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
		chaptersNames[Util.HAR_SUCOT][1]  = "הר' סוכות: א -חג הסוכות";
		chaptersNames[Util.HAR_SUCOT][2]  = "הר' סוכות: ב - הלכות סוכה";
		chaptersNames[Util.HAR_SUCOT][3]  = "הר' סוכות: ג - ישיבה בסוכה";
		chaptersNames[Util.HAR_SUCOT][4]  = "הר' סוכות: ד - ארבעת המינים";
		chaptersNames[Util.HAR_SUCOT][5]  = "הר' סוכות: ה - נטילת לולב";
		chaptersNames[Util.HAR_SUCOT][6]  = "הר' סוכות: ו - הושענא רבה";
		chaptersNames[Util.HAR_SUCOT][7]  = "הר' סוכות: ז - שמיני עצרת";
		chaptersNames[Util.HAR_SUCOT][8]  = "הר' סוכות: ח - הקהל";
		/*HAR_SHABAT*/
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

}
