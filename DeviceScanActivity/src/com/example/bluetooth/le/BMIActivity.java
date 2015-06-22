package com.example.bluetooth.le;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class BMIActivity extends Activity 
{
	final Context context = this;
	int weight_npvalue = 0, height_npvalue = 0;
	static boolean weightpressed = false, heightpressed = false;
	static double height = 0, weight = 0, bmi;
	String msg = "msg";
	Button b_weight,b_height, b_arrow;
	TextView showbmi,bmimsg;
	NumberPicker weight_np, height_np;
	
	SharedPreferences settingsActivity;
    static final String pheight = "HEIGHT";
    static final String pweight = "WEIGHT";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);		
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();		
		setContentView(R.layout.activity_bmi);
		
		b_weight = (Button) findViewById(R.id.weight);		
		b_weight.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
				weightpressed=true;
            	weight = ShowWeightMsgDialog(height, heightpressed ) ;            	
            }
        });		
				
		b_height = (Button) findViewById(R.id.height);		
		b_height.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
				heightpressed=true;
            	height = ShowHeightMsgDialog( weight, weightpressed ) ;
            }
        });
		
			
		b_arrow = (Button) findViewById(R.id.arrow_button1);
		b_arrow.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
				if( check_bmidata() )
				{
					settingsActivity = getSharedPreferences("Warmie",0);
	        		SharedPreferences.Editor editor = settingsActivity.edit();
	        		
	        		b_weight = (Button) findViewById(R.id.weight);
	        		b_height = (Button) findViewById(R.id.height);       		
	        		
	        		editor.putString(pweight, b_weight.getText().toString());
	        		editor.putString(pheight, b_height.getText().toString());
	                editor.commit();
					
					
	                // TODO Auto-generated method stub
	            	Intent i = new Intent();
	                i.setClass(BMIActivity.this, PeriodActivity.class);
	                BMIActivity.this.startActivity(i);
				}
				                 
            }
        });	
		
		b_arrow.setAlpha(0);
		b_arrow.setEnabled(false);
				
		bmimsg = (TextView) findViewById(R.id.bmimsg_textview);
		bmimsg.setAlpha(0);
												
	}
	
	private boolean check_bmidata()
	{
		boolean correctdata = true;		
		b_weight = (Button) findViewById(R.id.weight);
		b_height = (Button) findViewById(R.id.height);	
		
		//if null
		if( b_weight.getText().equals( findViewById(R.string.weight_btn_text) ) )
		{
			correctdata = false;
			Toast.makeText( BMIActivity.this, "您忘了輸入體重喔！", Toast.LENGTH_SHORT) .show();	
		}
		else if( b_height.getText().equals( findViewById(R.string.height_btn_text) ) )
		{
			correctdata = false;
			Toast.makeText( BMIActivity.this, "您忘了輸入身高喔！", Toast.LENGTH_SHORT) .show();	
		}
		
		return correctdata;		
	}


	private int ShowWeightMsgDialog( final double np_height, final boolean flag_h ) 
	{	
		
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_weight);

        weight_np = (NumberPicker) dialog.findViewById(R.id.weight_numberPicker);  
        weight_np.setMaxValue(200);  
        weight_np.setMinValue(1);  
        weight_np.setValue(50); 
		       
        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.weight_button_ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	b_weight = (Button) findViewById(R.id.weight);	
            	weight_npvalue = weight_np.getValue();
            	b_weight.setText(weight_npvalue +" kg"); 
            	
            	//Log.d("weight", "weight! "+weight);
            	
            	if( weightpressed && heightpressed )        		
            		ShowMsg( height_npvalue, weight_npvalue ); 
                
                dialog.dismiss();
            }
        });

        dialog.show();
        return weight_npvalue;

    }
	
	private int ShowHeightMsgDialog( final double np_weight, final boolean flag_w ) 
	{
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_height);

        height_np = (NumberPicker) dialog.findViewById(R.id.height_numberPicker);  
        height_np.setMaxValue(200);  
        height_np.setMinValue(1);  
        height_np.setValue(150); 
		       
        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.height_button_ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	b_height = (Button) findViewById(R.id.height);
            	height_npvalue = height_np.getValue();
            	b_height.setText(height_npvalue +" cm");
            	
            	//Log.d("height", "height     "+height);
 
            	if( weightpressed && heightpressed )        		
            		ShowMsg( height_npvalue, weight_npvalue ); 
            	
                dialog.dismiss(); 
            }
        });

        dialog.show();
        return height_npvalue;
    }
	
	private void ShowMsg(double height, double weight)
	{
		
		double height_m = height/100; 
		bmi = weight/(height_m*height_m);
		
		Log.d("BMI", "weight     "+weight);
		Log.d("BMI", "height     "+height);
		Log.d("BMI", "bmi     "+bmi);
		
		String msg = "hello~";
		
		if( bmi < 18)
			msg = getString(R.string.bmi1);
		else if ( bmi>18 && bmi<24 )
			msg = getString(R.string.bmi2);
		else
			msg = getString(R.string.bmi3);

		showbmi = (TextView) findViewById(R.id.textView2);
		DecimalFormat nf = new DecimalFormat("0.0");
		showbmi.setText( getString(R.string.bmi_is) +nf.format(bmi)+ getString(R.string.dot) );	
		
		bmimsg = (TextView) findViewById(R.id.bmimsg_textview);		
		bmimsg.setText(msg);
		bmimsg.setAlpha(255);
		
		b_arrow = (Button) findViewById(R.id.arrow_button1);
		b_arrow.setAlpha(255);
		b_arrow.setEnabled(true);		
				
	}
}
