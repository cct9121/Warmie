package com.example.bluetooth.le;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecordsActivity extends Activity 
{
	private Menu menu;
	private String inBedMenuTitle = "Set to 'In bed'";
    private String outOfBedMenuTitle = "Set to 'Out of bed'";
    private boolean inBed = false;
	
	private DrawerLayout layDrawer;
    private ListView lstDrawer;

    private ActionBarDrawerToggle drawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    Fragment start_fragment;
    
    boolean nodata = true;
    
    SharedPreferences settingsActivity_celcius;	
	static final String pcelcius = "Celcius" ;
	final Context context = this;
	NumberPicker celcius_np;
	int celcius_npvalue = 0;
	String pastrecordslist = "", addrecord = "";  
	String celcius = "";
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_records);
		
		initActionBar();
        initDrawer();
        initDrawerList();
		
        if (savedInstanceState == null) //define firstshowfragment
        	selectItem(0);
	
	}
	
	private void initActionBar()
	{
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);        
        
    }
	
	private void initDrawer()
	{
        setContentView(R.layout.drawer);

        layDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        lstDrawer = (ListView) findViewById(R.id.left_drawer);

        layDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mTitle = mDrawerTitle = getTitle();
        drawerToggle = new ActionBarDrawerToggle(this, layDrawer,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) 
        {

            @Override
            public void onDrawerClosed(View view) 
            {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }
            @Override
            public void onDrawerOpened(View drawerView) 
            {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        drawerToggle.syncState();

        layDrawer.setDrawerListener(drawerToggle);
    }

	private void initDrawerList()
	{
        String[] drawer_menu = this.getResources().getStringArray(R.array.drawer_menu);
        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawer_menu);
        
        List<HashMap<String,String>> lstData = new ArrayList<HashMap<String,String>>();
        /*
        for (int i = 0; i < 4; i++) 
        {
        	HashMap<String, String> mapValue = new HashMap<String, String>();
        	mapValue.put("icon", Integer.toString(R.drawable.ic_launcher));
        	mapValue.put("title", drawer_menu[i]);
        	lstData.add(mapValue);
        }
        */
        HashMap<String, String> mapValue1 = new HashMap<String, String>();
    	mapValue1.put("icon", Integer.toString(R.drawable.drawer_frontpage));
    	mapValue1.put("title", drawer_menu[0]);
    	lstData.add(mapValue1);
    	
    	HashMap<String, String> mapValue2 = new HashMap<String, String>();
    	mapValue2.put("icon", Integer.toString(R.drawable.drawer_knowledge));
    	mapValue2.put("title", drawer_menu[1]);
    	lstData.add(mapValue2);
    	
    	HashMap<String, String> mapValue3 = new HashMap<String, String>();
    	mapValue3.put("icon", Integer.toString(R.drawable.drawer_contact));
    	mapValue3.put("title", drawer_menu[2]);
    	lstData.add(mapValue3);
    	
    	HashMap<String, String> mapValue4 = new HashMap<String, String>();
    	mapValue4.put("icon", Integer.toString(R.drawable.drawer_howtouse));
    	mapValue4.put("title", drawer_menu[3]);
    	lstData.add(mapValue4);
               
        SimpleAdapter adapter = new SimpleAdapter(this, lstData, R.layout.drawer_list_item, new String[]{"icon", "title"}, new int[]{R.id.imgIcon, R.id.txtItem});
        lstDrawer.setAdapter(adapter);
        
        lstDrawer.setOnItemClickListener(new DrawerItemClickListener());
    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener 
	{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
            selectItem(position);
        }
    }
	
	private void selectItem(int position) 
	{
	    Fragment fragment = null;

	    switch (position) 
	    {
		    case 0:
		    	setTitle( getString(R.string.title_activity_records) );
		        fragment = new Fragment_homepage();
		        break;
		    
		    case 1:
		    	setTitle( getString(R.string.warmie_knowledge) );
		        fragment = new Fragment_knowledge();
		        Log.d("hide_menu_add","hide_menu_add");
		        break;
		        
		    case 2:
		    	setTitle( getString(R.string.contact_warmie) );
		    	fragment = new Fragment_contactWarmie();
		        break;
		        
		    case 3:		  
		    	setTitle( getString(R.string.how_to_use_warmie) );
		    	fragment = new Fragment_howtouse();
		        break;
   
		    default:
		    	fragment = new Fragment_homepage();
		        return;
	    }

	    FragmentManager fragmentManager = getFragmentManager();
	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


	    lstDrawer.setItemChecked(position, true);
	    layDrawer.closeDrawer(lstDrawer);
	}

	@Override
	public void setTitle(CharSequence title) 
	{
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.records, menu);
	    
		/*
		MenuItem item = menu.findItem(R.id.menu_add);
        item.setVisible(false);
        this.invalidateOptionsMenu();
        */
		
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{

	    //home
	    if (drawerToggle.onOptionsItemSelected(item)) 
	    {
	        return true;
	    }
	    	    
	    if (item.getItemId() == R.id.menu_add) 
        {
	    	//show dialog to choose tempurature
	    	ShowTempMsgDialog();
        }

	    return super.onOptionsItemSelected(item);
	}
	
	
	
	private void ShowTempMsgDialog() 
	{		
		// custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_weight);

        celcius_np = (NumberPicker) dialog.findViewById(R.id.weight_numberPicker);  
        celcius_np.setMaxValue(50);  
        celcius_np.setMinValue(20);  
        celcius_np.setValue(37); 
		       
        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.weight_button_ok);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {	
            	celcius_npvalue = celcius_np.getValue();
            	//Log.d("celcius_npvalue:", Integer.toString(celcius_npvalue) );           	          	
            	 
            	//get add value
            	Calendar c = Calendar.getInstance();
            	Time mTime = new Time();
        		mTime.setToNow();
            	addrecord = sdf.format( c.getTime() ) +"/"+ mTime.format("%H:%M:%S") +"/"+ Integer.toString(celcius_npvalue) + "C/" ;           	
            	Log.d("celciuslist:", addrecord );
            	
            	//passing value
                celcius= Integer.toString(celcius_npvalue) ;
            	Fragment_homepage.getcelcius(celcius);
            	
            	//save value       	
            	settingsActivity_celcius = getSharedPreferences("Warmie",0);
            	pastrecordslist = settingsActivity_celcius.getString("Celcius","");          	            	
        		SharedPreferences.Editor editor = settingsActivity_celcius.edit();  		      		
        		editor.putString( pcelcius, pastrecordslist + addrecord);
                editor.commit();          	
                         	               
                dialog.dismiss();
            }
        });

        dialog.show();
        
        
    }

	
}
