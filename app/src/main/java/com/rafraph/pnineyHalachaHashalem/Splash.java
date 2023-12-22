package com.rafraph.pnineyHalachaHashalem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openHomeActivity = new Intent("com.rafraph.pnineyHalachaHashalem.HomeActivity");
					startActivity(openHomeActivity);
				}
			}
		};
		timer.start();
	}
	
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
