package com.rafraph.pnineyHalachaHashalem;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		ImageView ListHeaderIconPlay = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay);
		int a = lblListHeader.getId();
		if(headerTitle.equals("ברכות") || headerTitle.equals("מועדים")      ||
		   headerTitle.equals("זמנים") || headerTitle.equals("סוכות")       ||
		   headerTitle.equals("שבת")   || headerTitle.equals("פסח")         ||
		   headerTitle.equals("תפילה") || headerTitle.equals("ימים נוראים") ||
		   headerTitle.equals("כשרות א - הצומח והחי") || headerTitle.equals("כשרות ב - המזון והמטבח") ||
		   headerTitle.equals("שמחת הבית וברכתו") || headerTitle.equals("טהרת המשפחה") ||
		   headerTitle.equals("שביעית ויובל") || headerTitle.equals("העם והארץ") ||
		   headerTitle.equals("משפחה"))
		{
			ListHeaderIconPlay.setVisibility(View.VISIBLE);
		}
		else
		{
			ListHeaderIconPlay.setVisibility(View.GONE);
		}
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
	
}
