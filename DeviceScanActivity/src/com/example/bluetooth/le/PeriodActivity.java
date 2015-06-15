package com.example.bluetooth.le;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class PeriodActivity extends Activity 
{
	final Context context = this;
	final String myFormat = "MM/dd/yyyy";
	static boolean p1 = false, p2 = false, p3 = false, p4 = false ;
	
	String when = "//";
	String regular = "";
	
	int period = 0, last = 0;
	int perioid_npvalue = 0, last_npvalue = 0;
	NumberPicker perioid_np, last_np;
	
	int dp_month = 0, dp_day = 0, dp_year = 0;
	Calendar c;
	DatePickerDialog dpdialog;
	
	SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
	
	Button b_arrow, b_period, b_last, b_when,b_regular;
	TextView thankmsg;
	
	SharedPreferences settingsActivity;
    static final String peroidtime = "PERIOD";
    static final String lasttime = "LAST";
    static final String whentime = "WHEN";
    static final String isregular = "RUGULAR";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_period);
		
		initialboolean();
		

		b_period = (Button) findViewById(R.id.button_period);	
		b_period.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
				p1 = true;
				show_arrowbutton();
            	period = ShowPeriodMsgDialog(31, 1, 28);
            }
        });
		
		

		b_last = (Button) findViewById(R.id.button_last);		
		b_last.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
				p2 = true;
				show_arrowbutton();
            	last = ShowLastMsgDialog(31, 1, 5) ;
            }
        });
		
		
		b_when = (Button) findViewById(R.id.button_when);		
		b_when.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {         	
                // TODO Auto-generated method stub
				p3 = true;
				show_arrowbutton();
				//dpdialog = new DatePickerDialog(context, datepicker, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            	ShowDateDialog() ;
            	
            }
        });
		

		b_regular = (Button) findViewById(R.id.button_regular);		
		b_regular.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
				p4 = true;
				show_arrowbutton();
            	regular = ShowRegularDialog() ;
            }
        });
		
		
		//next page
		b_arrow = (Button) findViewById(R.id.arrow_button2);
		b_arrow.setOnClickListener(new Button.OnClickListener()
        {
			@Override
            public void onClick(View v) 
            {
				if( check_perioddata() )
				{
					settingsActivity = getSharedPreferences("Warmie",0);
	        		SharedPreferences.Editor editor = settingsActivity.edit();
	        		
	        		b_period = (Button) findViewById(R.id.button_period);
	        		b_last = (Button) findViewById(R.id.button_last);
	        		b_when = (Button) findViewById(R.id.button_when);
	        		b_regular = (Button) findViewById(R.id.button_regular);	
	        		
	        		editor.putString(peroidtime, b_period.getText().toString());
	        		editor.putString(lasttime, b_last.getText().toString());
	                editor.putString(whentime, b_when.getText().toString());
	                editor.putString(isregular, b_regular.getText().toString());
	                editor.commit();
					
	                // TODO Auto-generated method stub
	            	Intent i = new Intent();
	                i.setClass(PeriodActivity.this, NameActivity.class);
	                PeriodActivity.this.startActivity(i); 
				}       		                
            }
        });
		
		b_arrow.setAlpha(0);
		b_arrow.setEnabled(false);
		
		
				
	}
	
	private void initialboolean()
	{
		p1 = false;
		p2 = false;
		p3 = false;
		p4 = false ;
	}
	
	private void show_arrowbutton()
	{
		//Log.d("show_arrowbutton", "p1     "+p1);
		//Log.d("show_arrowbutton", "p2     "+p2);
		//Log.d("show_arrowbutton", "p3     "+p3);
		//Log.d("show_arrowbutton", "p4     "+p4);
		
		if( p1 && p2 && p3 && p4)
		{
			b_arrow = (Button) findViewById(R.id.arrow_button2);
			b_arrow.setAlpha(255);
			b_arrow.setEnabled(true);
			
			thankmsg = (TextView) findViewById(R.id.textView6);
			thankmsg.setText(  getString(R.string.period_thankmsg) );
			
		}		
	}
	
	private boolean check_perioddata()
	{
		boolean correctdata = true;		
		b_period = (Button) findViewById(R.id.button_period);
		b_last = (Button) findViewById(R.id.button_last);
		b_when = (Button) findViewById(R.id.button_when);
		b_regular = (Button) findViewById(R.id.button_regular);	
		
		int periodlong = Integer.parseInt( b_period.getText().subSequence(0, b_period.getText().length()-1).toString() );
		int lastlong = Integer.parseInt( b_last.getText().subSequence(0, b_last.getText().length()-1).toString() );
		
		//if null
		if( b_period.getText().equals("- -天") )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您忘了輸入平均月經週期喔！", Toast.LENGTH_SHORT) .show();	
		}
		else if( b_last.getText().equals("- -天") )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您忘了輸入輸入月經經期喔！", Toast.LENGTH_SHORT) .show();	
		}			
		else if( b_when.getText().equals("月/日/年") )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您忘了輸入輸入上一次月經時間喔！", Toast.LENGTH_SHORT) .show();
		}
		else if( b_regular.getText().equals("- -") )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您忘了輸入選擇月經規律喔！", Toast.LENGTH_SHORT) .show();
		}
		
		//b_period must bigger than b_last		
		else if(  periodlong < lastlong )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您輸入的月經經期比平均月經週期大喔！", Toast.LENGTH_SHORT) .show();
		}
		else if(  periodlong == lastlong )
		{
			correctdata = false;
			Toast.makeText( PeriodActivity.this, "您輸入的月經經期與平均月經週期一樣大喔！", Toast.LENGTH_SHORT) .show();
		}
		
		return correctdata;		
	}
		
	private int ShowPeriodMsgDialog(int max, int min, int inital) 
	{		
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_periodday);

        perioid_np = (NumberPicker) dialog.findViewById(R.id.periodday_numberPicker);  
        perioid_np.setMaxValue(max);  
        perioid_np.setMinValue(min);  
        perioid_np.setValue(inital); 
		       
        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.periodday_button_ok);
        
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            { 
            	perioid_npvalue = perioid_np.getValue();
            	b_period = (Button) findViewById(R.id.button_period);	
            	b_period.setText(perioid_npvalue+ getString(R.string.space_day));
                dialog.dismiss();                
            }
        });

        dialog.show();
               
        return perioid_npvalue;
    }
	
	private int ShowLastMsgDialog(int max, int min, int inital) 
	{		
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_lastday);

        last_np = (NumberPicker) dialog.findViewById(R.id.lastday_numberPicker);  
        last_np.setMaxValue(max);  
        last_np.setMinValue(min);  
        last_np.setValue(inital); 
		       
        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.lastday_button_ok);
        
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            { 
            	last_npvalue = last_np.getValue();
            	b_last = (Button) findViewById(R.id.button_last);	
            	b_last.setText(last_npvalue+ getString(R.string.space_day));
                dialog.dismiss();                
            }
        });

        dialog.show();
               
        return last_npvalue;
    }
	
	private void ShowDateDialog() 
	{  
		c = Calendar.getInstance();
		//c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
		/*
        dpdialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() 
    	{
            @Override
            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) 
            {
            	c.set(year, monthOfYear, dayOfMonth);           	
            	b_when = (Button) findViewById(R.id.button_when);
    		    b_when.setText(  sdf.format( c.getTime())  );	 
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        */
        
		DatePickerDialog.OnDateSetListener dpdialoglistener = new DatePickerDialog.OnDateSetListener() 
    	{
            @Override
            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) 
            {
            	c.set(year, monthOfYear, dayOfMonth);           	
            	b_when = (Button) findViewById(R.id.button_when);
    		    b_when.setText(  sdf.format( c.getTime())  );	 
            }
        };
		dpdialog = new DatePickerDialog(this, dpdialoglistener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));		
		//limit future date
		dpdialog.getDatePicker().setMaxDate(c.getTimeInMillis() );
        
        dpdialog.show();
                
    }
	
	private String ShowRegularDialog() 
	{		
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_regular);

        // set the custom dialog components - text, image and button
        final Button regular1 = (Button) dialog.findViewById(R.id.button_r1);
        // if button is clicked, close the custom dialog
        regular1.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {   
            	regular = regular1.getText().toString();
            	b_regular.setText(regular);
                dialog.dismiss();                
            }
        });
        
        final Button regular2 = (Button) dialog.findViewById(R.id.button_r2);
        // if button is clicked, close the custom dialog
        regular2.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {   
            	regular = regular2.getText().toString();
            	b_regular.setText(regular);
                dialog.dismiss();                
            }
        });
        
        final Button regular3 = (Button) dialog.findViewById(R.id.button_r3);
        // if button is clicked, close the custom dialog
        regular3.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {   
            	regular = regular3.getText().toString();
            	b_regular.setText(regular);
                dialog.dismiss();                
            }
        });
        
        final Button regular4 = (Button) dialog.findViewById(R.id.button_r4);
        // if button is clicked, close the custom dialog
        regular4.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {   
            	regular = regular4.getText().toString();
            	b_regular.setText(regular);
                dialog.dismiss();                
            }
        });

        dialog.show();

        return regular;
    }
	

	
}
