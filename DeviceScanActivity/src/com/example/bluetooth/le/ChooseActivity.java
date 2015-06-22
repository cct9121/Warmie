package com.example.bluetooth.le;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
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
		
		b1 = (Button) findViewById(R.id.choose_button1);		
		Spannable span1 = new SpannableString( b1.getText() );
	    span1.setSpan(new RelativeSizeSpan(0.70f), 5, b1.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    b1.setText(span1);
		
		b2 = (Button) findViewById(R.id.choose_button2);
		Spannable span2 = new SpannableString( b2.getText() );
	    span2.setSpan(new RelativeSizeSpan(0.70f), 2, b2.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    b2.setText(span2);
	    
		b3 = (Button) findViewById(R.id.choose_button3);
		Spannable span3 = new SpannableString( b3.getText() );
	    span3.setSpan(new RelativeSizeSpan(0.70f), 4, b3.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    b3.setText(span3);
		
		arror_button = (Button) findViewById(R.id.choose_arrow_button);
		
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
