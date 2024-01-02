package com.rafraph.pnineyHalachaHashalem;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class About extends AppCompatActivity
{
	public Util util;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		// for toolbar
		Toolbar generalToolbar = (Toolbar) findViewById(R.id.generalToolbar);
		setSupportActionBar(generalToolbar);
		// Display icon in the toolbar
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setLogo(R.drawable.toolbar_header);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		// Enable the home button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		util = new Util(this);

		/*version*/
		String myVersionName = "not available"; // initialize String

		Context context = getApplicationContext(); // or activity.getApplicationContext()
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();

		try 
		{
		    myVersionName = "גירסה: " + packageManager.getPackageInfo(packageName, 0).versionName;
		} 
		catch (PackageManager.NameNotFoundException e) 
		{
		    e.printStackTrace();
		}
		TextView tvVersion = (TextView) findViewById(R.id.textViewVersion);
		tvVersion.setText(myVersionName);
	}
	// for toolbar
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_actionbar, (android.view.Menu) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_config:
				util.showPopupMenuSettings(findViewById(R.id.action_config), About.this);
				break;
			case android.R.id.home:
				onBackPressed();
				break;
			default:
				break;
		}
		return true;
	}
}
