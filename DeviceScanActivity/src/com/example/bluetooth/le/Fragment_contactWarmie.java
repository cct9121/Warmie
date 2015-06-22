package com.example.bluetooth.le;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class Fragment_contactWarmie extends Fragment 
{
	boolean sentmail = false;

	ListView CompleteListView;

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_contactwarmie, container, false);	
		setHasOptionsMenu(true);
		return v;	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		Log.i("Send email", "");

		String[] TO = {"warmie@gmail.com!"};
		//String[] CC = {"mcmohd@gmail.com"};
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.setType("text/plain");


		emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		//emailIntent.putExtra(Intent.EXTRA_CC, CC);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Warmie");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here.");

		try 
		{
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			sentmail = true;
			//finish();
			Log.i("Finished sending email...", "");	
		} 
		catch (android.content.ActivityNotFoundException ex) 
		{
			//Toast.makeText(Fragment_contactWarmie.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
	 
		}
					

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// TODO Add your menu entries here
		menu.setGroupVisible(R.id.records_menu_group, false);
	}
	
	
	
		
	private void returntorecordspage()
	{
		Intent i = new Intent();
        i.setClass(getActivity(), RecordsActivity.class);
        Fragment_contactWarmie.this.startActivity(i); 
		
	}
			
	
}
