package com.example.bluetooth.le;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class ChooseActivity extends Activity 
{
	Button b1,b2,b3,arror_button;
	static String travel = "TRAVEL";
	
	SharedPreferences settingsActivity;
    static final String ptravel = "TRAVEL";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_choose);
		
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		arror_button = (Button) findViewById(R.id.arrow_button);
		
		arror_button.getBackground().setAlpha(0);
		arror_button.setEnabled(false);
		
        b1.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	b1.getBackground().setAlpha(255);
            	arror_button.getBackground().setAlpha(255);
            	b2.getBackground().setAlpha(100);
            	b3.getBackground().setAlpha(100);
            	
            	travel = "1";
            	arror_button.setEnabled(true);
                
            }
        });
		
        b2.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	b2.getBackground().setAlpha(255);
            	arror_button.getBackground().setAlpha(255);
            	b1.getBackground().setAlpha(100);
            	b3.getBackground().setAlpha(100);
                
            	travel = "2";
            	arror_button.setEnabled(true);                
            }
        });
        
        b3.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	b3.getBackground().setAlpha(255);
            	arror_button.getBackground().setAlpha(255);
            	b1.getBackground().setAlpha(100);
            	b2.getBackground().setAlpha(100);
                
            	travel = "3";
            	arror_button.setEnabled(true);                
            }
        });
        
        arror_button.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
            	settingsActivity = getSharedPreferences("Warmie",0);
        		SharedPreferences.Editor editor = settingsActivity.edit();       		     		
        		
        		editor.putString(ptravel, travel);
                editor.commit();
            	
                // TODO Auto-generated method stub
            	Intent i = new Intent();
                i.setClass(ChooseActivity.this, BMIActivity.class);
                ChooseActivity.this.startActivity(i);                 
            }
        });
		
		
	}
}
