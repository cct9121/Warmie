package com.example.bluetooth.le;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class Fragment_homepage extends Fragment 
{
	/* Can get data from DeviceScanAvtivity using SharedPreferences,
	 * but can't show right view.
	 * */
	boolean nodata = true;
	private static Frg_homepageAdapter ListAdapter;
	private static ArrayList<String> dateList = new ArrayList<String>();
	private static ArrayList<String> timeList = new ArrayList<String>();
	private static ArrayList<String> tempList = new ArrayList<String>();
	
	ListView CompleteListView;
	
	SharedPreferences settingsActivity_celcius;	
	String records = "";

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_homepage, container, false);	
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) 
	{
		super.onActivityCreated(savedInstanceState);
		
		CompleteListView =(ListView)this.getView().findViewById(R.id.records_listView1);
		
		dateList.clear();
		timeList.clear();
		tempList.clear();
		
		dateList.add( getString(R.string.records_date) );
		timeList.add( getString(R.string.records_time) );
		tempList.add( getString(R.string.records_c) );
				
		settingsActivity_celcius = this.getActivity().getSharedPreferences("Warmie",0);
		records = settingsActivity_celcius.getString("Celcius","");
		Log.d("frg_homepage settingsActivity_celcius",  records );
		
		if( records.length() != 0 )
		{
			String[] splitString = records.split("/");
			
			for(int i=0; i<splitString.length; i++)
			{
				if( i%3 == 0 )
				{
					//Log.d("i%3 == 0", splitString[i]);
					dateList.add( splitString[i] );
				}				
				else if( i%3 == 1 )
				{
					//Log.d("i%3 == 1", splitString[i]);
					timeList.add( splitString[i] );
				}				
				else if( i%3 == 2 )
				{
					//Log.d("i%3 == 2", splitString[i]);
					tempList.add( splitString[i] );
				}				
				else
					Log.d("listview", "listview wrong");
			}			
		}	
				
		ListAdapter = new Frg_homepageAdapter(getActivity(), dateList, timeList, tempList);//put data into mListAdapter		
		CompleteListView.setAdapter(ListAdapter);  //fCompleteListView is the layout of listview, show content of fCompleteListView		 
		//ListAdapter.notifyDataSetChanged();  // refresh listview's arraylist
		
	}
	
	public static void getcelcius(String celcius)
	{				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();
    	Time mTime = new Time();
		mTime.setToNow();
		
		dateList.add( sdf.format( c.getTime() ) );
		timeList.add( mTime.format("%H:%M:%S") );
		tempList.add(celcius+"C");	
		ListAdapter.notifyDataSetChanged();
	}
	

}
