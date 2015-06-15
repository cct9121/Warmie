package com.example.bluetooth.le;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameActivity extends Activity 
{
	EditText name, email, pwd, birth;
	Button b_register;
	
	SharedPreferences settingsActivity;
    static final String pname = "NAME";
    static final String pemail = "EMAIL";
    static final String ppwd = "PASSWORD";
    static final String pbirth = "BIRTHDAY";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_name);
		
		edittextlisteners();		
				
		
		b_register = (Button) findViewById(R.id.nameb_register_button);		
		b_register.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) 
            {
            	if( correct_namedata() )
            	{
            		settingsActivity = getSharedPreferences("Warmie",0);
            		SharedPreferences.Editor editor = settingsActivity.edit();
            		
            		name = (EditText)findViewById(R.id.editText_name);
            		email = (EditText)findViewById(R.id.editText_email);
            		pwd = (EditText)findViewById(R.id.editText_pwd);
            		birth = (EditText)findViewById(R.id.editText_birth);       		
            		
            		editor.putString(pname, name.getText().toString());
            		editor.putString(pemail, email.getText().toString());
                    editor.putString(ppwd, pwd.getText().toString());
                    editor.putString(pbirth, birth.getText().toString());
                    editor.commit();           	
                	
                	// TODO Auto-generated method stub
                	Intent i = new Intent();
                    i.setClass(NameActivity.this, StartActivity.class);
                    NameActivity.this.startActivity(i);
            	}
        		 
            }
        });						
	}
	
	public boolean correct_namedata()
	{
		boolean correct_edittext = true;
		
		name = (EditText)findViewById(R.id.editText_name);
		email = (EditText)findViewById(R.id.editText_email);
		pwd = (EditText)findViewById(R.id.editText_pwd);
		birth = (EditText)findViewById(R.id.editText_birth);
		
		String emailaddress = email.getText().toString().trim();
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		//check name
		if(name.getText().length() == 0)
		{
			correct_edittext = false;
    		Toast.makeText(getApplicationContext(),"請輸入的名字喔！",Toast.LENGTH_SHORT).show();
		}
		
		//check email
		else if ( email.getText().length() == 0)
		{
			correct_edittext = false;
			Toast.makeText(getApplicationContext(),"請輸入信箱謝謝！",Toast.LENGTH_SHORT).show();
		}			
		else if ( !emailaddress.matches(emailPattern) )
		{
			correct_edittext = false;
    		Toast.makeText(getApplicationContext(),"請再檢查一下輸入的信箱喔！",Toast.LENGTH_SHORT).show();
		}
		
		//check pwd
		else if(pwd.getText().length() == 0)
		{
			correct_edittext = false;
    		Toast.makeText(getApplicationContext(),"請輸入的密碼喔！",Toast.LENGTH_SHORT).show();
		}
    	else if( pwd.getText().length() <8 )
    	{
			correct_edittext = false;
    		Toast.makeText(getApplicationContext(),"密碼至少要8位喔！",Toast.LENGTH_SHORT).show();
    	}
		
		//check birth
    	else if(birth.getText().length() == 0)
		{
			correct_edittext = false;
    		Toast.makeText(getApplicationContext(),"請輸入生日喔！",Toast.LENGTH_SHORT).show();
		}
		
		return correct_edittext;
	}
	
	

	public void edittextlisteners()
	{		
		name = (EditText)findViewById(R.id.editText_name);		
		name.addTextChangedListener( new TextWatcher() 
		{
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {    
            	//Log.d("name onTextChanged",s.toString());           	
            	
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {
            	//Log.d("name beforeTextChanged",s.toString());
            	//Log.d("edittext_name",  getResources().getString(R.string.edittext_name) );
            	//name.clearComposingText();
            }

            @Override
            public void afterTextChanged(Editable s) 
            {
            	name.setError(null);
            	//Log.d("name afterTextChanged",s.toString());
            }
		});
		
		email = (EditText)findViewById(R.id.editText_email);
		email.addTextChangedListener( new TextWatcher() 
		{
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {                       

            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {

            }

            @Override
            public void afterTextChanged(Editable s) 
            {
            	email.setError(null);
            }
		});
		
		pwd = (EditText)findViewById(R.id.editText_pwd);
		//pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
		pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		pwd.addTextChangedListener( new TextWatcher() 
		{
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {
            	
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {

            }

            @Override
            public void afterTextChanged(Editable s) 
            {
            	pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            	pwd.setError(null);            	
            }
		});

		
		birth = (EditText)findViewById(R.id.editText_birth);
		birth.addTextChangedListener( new TextWatcher() 
		{
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {                       

            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) 
            {

            }

            @Override
            public void afterTextChanged(Editable s) 
            {
            	birth.setError(null);
            }
		});
        
	}
	
}


