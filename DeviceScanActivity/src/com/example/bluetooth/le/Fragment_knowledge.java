package com.example.bluetooth.le;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.os.Build;

public class Fragment_knowledge extends Fragment 
{

	ListView CompleteListView;

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_knowledge, container, false);
		setHasOptionsMenu(true);
		return v;	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		WebView wv = (WebView) this.getView().findViewById(R.id.webView_knowledge);
		wv.loadUrl("https://medium.com/@Frances_Huang");
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// TODO Add your menu entries here
		menu.setGroupVisible(R.id.records_menu_group, false);
	}
	
}
