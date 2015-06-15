package com.example.bluetooth.le;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class StartActivity extends Activity 
{
	SharedPreferences settingsActivity;
	Button b_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_start);
		
		settingsActivity = getSharedPreferences("Warmie",0);
		String travel = settingsActivity.getString("TRAVEL","");
		Log.d("SharedPreferences ",  travel);
		
		
		String weight = settingsActivity.getString("WEIGHT","");
		String height = settingsActivity.getString("HEIGHT","");
		Log.d("SharedPreferences  ",  weight);
		Log.d("SharedPreferences  ",  height);
		
		String name = settingsActivity.getString("NAME","");
		String email = settingsActivity.getString("EMAIL","");
		String pwd = settingsActivity.getString("PASSWORD","");
		String birth = settingsActivity.getString("BIRTHDAY","");
		Log.d("SharedPreferences  ",  name);
		Log.d("SharedPreferences  ",  email);
		Log.d("SharedPreferences  ",  pwd);
		Log.d("SharedPreferences  ",  birth);	
		
		String peroid = settingsActivity.getString("PERIOD", "");
		String last = settingsActivity.getString("LAST", "");
		String when = settingsActivity.getString("WHEN", "");
		String regular = settingsActivity.getString("RUGULAR", "");
		Log.d("SharedPreferences  ",  peroid);
		Log.d("SharedPreferences  ",  last);
		Log.d("SharedPreferences  ",  when);
		Log.d("SharedPreferences  ",  regular);	
		
		
		b_start = (Button) findViewById(R.id.button_start);
		b_start.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	Intent i = new Intent();
                i.setClass(StartActivity.this, RecordsActivity.class);
                StartActivity.this.startActivity(i);                 
            }
        });	
		
		
	}

	
}
