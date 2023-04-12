package com.rafraph.pnineyHalachaHashalem;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{


	private Context _context;
	public int BlackBackground=0;
	static SharedPreferences mPrefs;
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	static int textColor = Color.BLACK;
	public LinearLayout listGroupLayout;
	public LinearLayout listChildLayout;
	public ImageView ListHeaderIconPlay;
	public ImageView ListHeaderIconPlay2;
	String fileName, fileNameOnly, lastFileName = null,bookName="";
	int lastChap,bookId;
	Elements headers;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
	HashMap<String, List<String>> listChildData) 
	{
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) 
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) 
	{
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) 
	{
	
		final String childText = (String) getChild(groupPosition, childPosition);
		
		if (convertView == null) 
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}
	
		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		listChildLayout = (LinearLayout) convertView.findViewById(R.id.LLlistChild);
		
		txtListChild.setText(childText);
		if(textColor==Color.BLACK)
		{
			listChildLayout.setBackgroundColor(Color.WHITE);
			txtListChild.setTextColor(Color.BLACK);
		}
		else
		{
			listChildLayout.setBackgroundColor(Color.BLACK);
			txtListChild.setTextColor(Color.WHITE);
		}
		
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) 
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}
	
	@Override
	public Object getGroup(int groupPosition) 
	{
		return this._listDataHeader.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() 
	{
		return this._listDataHeader.size();
	}
	
	@Override
	public long getGroupId(int groupPosition) 
	{
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		listGroupLayout = (LinearLayout) convertView.findViewById(R.id.LLlistGroup);
		ListHeaderIconPlay = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay);
		ListHeaderIconPlay2 = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay2);

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		ImageView ListHeaderIconPlay = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay);
		int a = lblListHeader.getId();
		if(headerTitle.equals("ברכות") || headerTitle.equals("מועדים")      ||
		   headerTitle.equals("זמנים") || headerTitle.equals("סוכות")       ||
		   headerTitle.equals("שבת")   || headerTitle.equals("פסח")         ||
		   headerTitle.equals("תפילה") || headerTitle.equals("ימים נוראים") ||
		   headerTitle.equals("כשרות א - הצומח והחי") || headerTitle.equals("כשרות ב - המזון והמטבח") ||
		   headerTitle.equals("שמחת הבית וברכתו") || headerTitle.equals("טהרת המשפחה") ||
			headerTitle.equals("שביעית ויובל") || headerTitle.equals("העם והארץ")||headerTitle.equals("משפחה"))
		{
			ListHeaderIconPlay.setVisibility(View.VISIBLE);
			ListHeaderIconPlay2.setVisibility(View.VISIBLE);
		}
		else
		{
			ListHeaderIconPlay.setVisibility(View.GONE);
			ListHeaderIconPlay2.setVisibility(View.GONE);
		}
		ListHeaderIconPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{


					switch (headerTitle){
						case "ברכות":
							bookName="brachot";
							lastChap=18;
							bookId=0;
							break;
						case "מועדים":
							bookName="moadim";
							lastChap=13;
							bookId=9;
							break;
						case "זמנים":
							bookName="zmanim";
							lastChap=17;
							bookId=2;
							break;
						case "סוכות":
							bookName="sucot";
							lastChap=8;
							bookId=11;
							break;
						case "שבת":
							bookName="shabat";
							lastChap=30;
							bookId=14;
							break;
						case "פסח":
							bookName="pesach";
							lastChap=16;
							bookId=12;
							break;
						case "תפילה":
							bookName="tefila";
							lastChap=26;
							bookId=16;
							break;
						case "כשרות א - הצומח והחי":
							bookName="kashrut";
							lastChap=19;
							bookId=5;
							break;
						case "כשרות ב - המזון והמטבח":
							bookName="kashrut";
							lastChap=19;
							bookId=6;
							break;
						case "שמחת הבית וברכתו":
							bookName="simchat";
							lastChap=10;
							bookId=15;
							break;
						case "טהרת המשפחה":
							bookName="taharat";
							lastChap=10;
							bookId=3;
							break;
						case "שביעית ויובל":
							bookName="shviit";
							lastChap=11;
							bookId=13;
							break;
						case "העם והארץ":
							bookName="haamvehaarez";
							lastChap=11;
							bookId=1;
							break;
						case "משפחה":
							bookName="mishpacha";
							lastChap=10;
							bookId=10;
							break;
						case "ימים נוראים":
							bookName="yamim";
							lastChap=10;
							bookId=4;
							break;
						default:
							bookName="yamim";
							lastChap=10;
							bookId=4;
							break;

					}

					Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.myAudio");
					Intent ourIntent = new Intent(_context, ourClass);
					ourIntent.putExtra("audio_id", 1);
					ourIntent.putExtra("book_id", bookId);
					ourIntent.putExtra("chapter_id", 1);
					if (bookId!=6)
					ourIntent.putExtra("webLink", "file:///android_asset/"+bookName+"_1.html");
					else
					ourIntent.putExtra("webLink", "file:///android_asset/"+bookName+"_20.html");
						//ourIntent.putExtra("webLink", localFile.getPath());
					ourIntent.putExtra("hearAndRead", true
					);
					ourIntent.putExtra("fontSize", 15);
					//findAllHeaders(ourIntent);
					findAllHeaders(ourIntent);
					_context.startActivity(ourIntent);



				}
				catch (ClassNotFoundException | FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		});
		ListHeaderIconPlay2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{


					switch (headerTitle){
						case "ברכות":
							bookName="brachot";
							lastChap=18;
							bookId=0;
							break;
						case "מועדים":
							bookName="moadim";
							lastChap=13;
							bookId=9;
							break;
						case "זמנים":
							bookName="zmanim";
							lastChap=17;
							bookId=2;
							break;
						case "סוכות":
							bookName="sucot";
							lastChap=8;
							bookId=11;
							break;
						case "שבת":
							bookName="shabat";
							lastChap=30;
							bookId=14;
							break;
						case "פסח":
							bookName="pesach";
							lastChap=16;
							bookId=12;
							break;
						case "תפילה":
							bookName="tefila";
							lastChap=26;
							bookId=16;
							break;
						case "כשרות א - הצומח והחי":
							bookName="kashrut";
							lastChap=19;
							bookId=5;
							break;
						case "כשרות ב - המזון והמטבח":
							bookName="kashrut";
							lastChap=19;
							bookId=6;
							break;
						case "שמחת הבית וברכתו":
							bookName="simchat";
							lastChap=10;
							bookId=15;
							break;
						case "טהרת המשפחה":
							bookName="taharat";
							lastChap=10;
							bookId=3;
							break;
						case "שביעית ויובל":
							bookName="shviit";
							lastChap=11;
							bookId=13;
							break;
						case "העם והארץ":
							bookName="haamvehaarez";
							lastChap=11;
							bookId=1;
							break;
						case "ימים נוראים":
							bookName="yamim";
							lastChap=10;
							bookId=4;
							break;
						default:
							bookName="yamim";
							lastChap=10;
							bookId=4;
							break;

					}

					Class ourClass = Class.forName("com.rafraph.pnineyHalachaHashalem.myAudio");
					Intent ourIntent = new Intent(_context, ourClass);
					ourIntent.putExtra("audio_id", 1);
					ourIntent.putExtra("book_id", bookId);
					ourIntent.putExtra("chapter_id", 1);
					if (bookId!=6)
						ourIntent.putExtra("webLink", "file:///android_asset/"+bookName+"_1.html");
					else
						ourIntent.putExtra("webLink", "file:///android_asset/"+bookName+"_20.html");
					//ourIntent.putExtra("webLink", localFile.getPath());
					ourIntent.putExtra("hearAndRead", false);
					ourIntent.putExtra("fontSize", 15);
					//findAllHeaders(ourIntent);
					findAllHeaders(ourIntent);
					_context.startActivity(ourIntent);



				}
				catch (ClassNotFoundException | FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		});
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		if(textColor==Color.BLACK)
		{
			listGroupLayout.setBackgroundColor(Color.WHITE);
			lblListHeader.setTextColor(Color.BLACK);
		}
		else
		{
			listGroupLayout.setBackgroundColor(Color.BLACK);
			lblListHeader.setTextColor(Color.WHITE);
		}
		listGroupLayout.setGravity(Gravity.RIGHT);
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() 
	{
		return false;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) 
	{
		return true;
	}
	public void setTextColor(int c) 
	{
		textColor=c;
	}

	private String getClearUrl()
	{
		String ClearUrl;
		//ClearUrl = webview.getUrl();
		//ClearUrl = ClearUrl.substring(0, ClearUrl.indexOf(".html")+5);
		return "";
	}

	private void findAllHeaders(Intent ourIntent) throws FileNotFoundException {
		String prefix, a;
		int j;
		ArrayList<String> sections = new ArrayList<String>();
		ArrayList<String> sections2 = new ArrayList<String>();


		for(int i=1; i<=lastChap; i++) {
			sections = new ArrayList<String>();
			if(bookId!=6)
				fileName = bookName+"_"+i+".html";
			else
				fileName = bookName+"_"+(i+19)+".html";

			prefix = "file:///android_asset/";
			//fileNameOnly = fileName.substring(prefix.length());
			//fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("_")+1);
			try {
				InputStream is;

				is = _context.getAssets().open(fileName);

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
					for(int k = 0; k < headers.size(); k++) {
						if(headers.get(k).text().length()>1)
							if(headers.get(k).text().charAt(1)=='.'||headers.get(k).text().charAt(2)=='.'||headers.get(k).text().charAt(2)==')')
								NewHead.add(headers.get(k));
					}
					headers=NewHead;
				}
				for(j = 0; j < headers.size(); j++) {
					sections.add(headers.get(j).text());
				}

				String name;
				if(bookId==6)
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

}
