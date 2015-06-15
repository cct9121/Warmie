package com.example.bluetooth.le;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class ImageActivity extends Activity 
{
	
	Button startregister;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_imageslideshow);
		 
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		ImageAdapter adapter = new ImageAdapter(this);
		viewPager.setAdapter(adapter);
		

        startregister = (Button) findViewById(R.id.register_button);
        startregister.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
            	
                Intent i = new Intent();
                i.setClass(ImageActivity.this, ChooseActivity.class);
                ImageActivity.this.startActivity(i);                
            }
        });
		
		
	}
 
}