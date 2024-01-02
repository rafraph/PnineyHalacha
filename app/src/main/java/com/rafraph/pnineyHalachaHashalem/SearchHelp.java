package com.rafraph.pnineyHalachaHashalem;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SearchHelp extends AppCompatActivity {
	public Util util;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_help);
		// for toolbar
		Toolbar generalToolbar = (Toolbar) findViewById(R.id.generalToolbar);
		setSupportActionBar(generalToolbar);
		// Display icon in the toolbar
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);//to remove the right corner icon/title
		getSupportActionBar().setLogo(R.drawable.toolbar_header);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		// Enable the home button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		util = new Util(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				break;
			default:
				break;
		}
		return true;
	}
}
