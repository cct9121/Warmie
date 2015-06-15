/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluetooth.le;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.PendingIntent;
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
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
//public class DeviceScanActivity extends ListActivity {
public class DeviceScanActivity extends Activity 
{
	private final static String TAG = "ScanControlActivity";
//	private final static String DEFAULTDEVICENAME = "xb64";
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private String mDeviceAddress;
    //private TextView StatusText;
    String StatusText = "aaaaa";
    
    private boolean mScanning = false;
    private boolean BLEConnected = false;
    private BluetoothLeService mBluetoothLeService;
    private boolean setTimeout = false;
    private String adName;	//device advertisement Name
    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    
    SharedPreferences settingsActivity;
    static final String pcelcius = "CELCIUS";

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	Log.d("view","DeviceScan");
    	
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle(R.string.title_devices);
        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) 
        {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) 
        {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
      //set main Layout
      //setContentView(R.layout.main);     

    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.main, menu);
       
        /*
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        */
        //return true;
    }

    
/*    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                //   StopMainService();
                finish();
                // scanLeDevice(true);
                break;
            case R.id.menu_stop:
            	if (mBluetoothLeService != null)
            		mBluetoothLeService.disconnect();
                // scanLeDevice(false);
                break;
        }
        return true;
    }
*/
    
    
    @Override
    public void onStart() 
    {
    	super.onStart();
    	Log.i(TAG, "++ ON START ++");
        //StatusText = (TextView) findViewById(R.id.textView1);
        Log.d("cycle", "onStart");
    }

    @Override
    protected void onResume() 
    {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.

        if (!mBluetoothAdapter.isEnabled()) 
        {
    	   Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        else
        {
        	registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        	//StartbgService();
        	
        	scanLeDevice(true);
        }
        
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
//        setListAdapter(mLeDeviceListAdapter);        
        //scanLeDevice(true);
        Log.d("cycle", "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) 
        {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("cycle", "onActivityResult");
    }

    @Override
    protected void onPause() 
    {
        super.onPause();
        
        unregisterReceiver(mGattUpdateReceiver);
   //     if(mBluetoothLeService != null)
   //     	unbindService(mServiceConnection);
        mLeDeviceListAdapter.clear();
        Log.d("cycle", "onPause");
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        Log.d("cycle", "onDestroy");
    }
/*    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
       // final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
      //  if (device == null) return;
        
    }
*/


    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter 
    {
    	
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() 
        {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
            Log.d("cycle", "LeDeviceListAdapter");
        }

        public void addDevice(BluetoothDevice device) 
        {
            if(!mLeDevices.contains(device)) 
            {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) 
        {
            return mLeDevices.get(position);
        }

        public void clear() 
        {
            mLeDevices.clear();
        }

        @Override
        public int getCount() 
        {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) 
        {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) 
        {
            return i;
        }

        
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) 
        {
        	
            ViewHolder viewHolder;
            /*
            // General ListView optimization code.
            if (view == null) 
            {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } 
            else 
            {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());
            
            return view;
			*/

            return null;
            
        }
        
    }


    static class ViewHolder 
    {
        TextView deviceName;
        TextView deviceAddress;
    }
    
    
    private static IntentFilter makeGattUpdateIntentFilter() 
    {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        
        return intentFilter;
    }
    
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() 
    {
        @Override
        public void onReceive(Context context, Intent intent) 
        {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) 
            {
            	
            	addStatus("XenonBLE Connected:"+adName);
            	
            	Log.i(TAG, "ACTION_GATT_CONNECTED");
            	BLEConnected = true;
            	
            	
            	//getActionBar().setTitle("Connected to "+adName);
            //	acquireWakeLock();  
            //	cancelTimeoutDis();
            //	timeoutDisconnect();
            
            } 
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) 
            {
            	addStatus("XenonBLE Disconnected");
            	BLEConnected = false;
            	unbindService(mServiceConnection);
            	cancelTimeoutDis();
            	scanLeDevice(true);
            	
            } 
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) 
            {
            	
           // 	int status = intent.getIntExtra("DisCov_status", -1);            	
          //  	addStatus("XenonBLE Discovered Status:"+status);
            	
            	Log.i(TAG, "adName:"+adName);
            	Log.i(TAG, "adName.charAt(0):"+adName.charAt(0));
        
            	if(adName.charAt(0) == 'X')
            	{
            		if( mBluetoothLeService.SetTime() == true)
            		{
            			//must delay for module to setup
            			try {
                			Thread.sleep((int)1000);
                		} 
            			catch (InterruptedException e) {
                			e.printStackTrace(); 
                		}
            			mBluetoothLeService.disconnect();
            		}	
            	}
            	else
            	{
            		//if(adName.equals(DEFAULTDEVICENAME))
            		{
            			//first Set on receiving notification to ture
            			addStatus("setXenonNotificationOn:" + mBluetoothLeService.SetXenonNotificationOn());
            		}
            		/*
            		else
            		{
            			mBluetoothLeService.disconnect();
            		}
            		*/
            	}

            } 
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) 
            {

            	/*
            	byte[] data;
    			data = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);   
    			if(data.length > 0)
    			{
    				addStatus("Get Data:" + bytesToHex(data));
    			}
            	*/
            	
            	String data;
    			data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
    			Log.d("get data !!!", "data is: " + data);
    			

    			
    			if(data.length() > 0)
    			{   				
    				//addStatus("Get Data:" + data); 
    				settingsActivity = getSharedPreferences("Warmie",0);
            		SharedPreferences.Editor editor = settingsActivity.edit();
            		
            		editor.putString(pcelcius, data);
                    editor.commit();
                    
                    Intent toFrg = new Intent();
        			toFrg.setClass( DeviceScanActivity.this , RecordsActivity.class);
        	        startActivity(toFrg); 
        	        finish();
    			}    			       	   	
            }
           
        }
    };
/*    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(String data) {
    	Log.i(TAG, "Data length:"+data.length());
        char[] hexChars = new char[data.length() * 2];
        for ( int j = 0; j < data.length(); j++ ) {
            int v = data.charAt(j) & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
*/    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) 
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) 
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() 
    {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) 
        {
        	Log.e(TAG, "onServiceConnected");
        	mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) 
            {
                Log.e(TAG, "Unable to initialize Bluetooth");
            }
            // Automatically connects to the device upon successful start-up initialization.
            Log.i(TAG, "mBluetoothLeService.connect");
            mBluetoothLeService.connect(mDeviceAddress);        	
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) 
        {
            mBluetoothLeService = null;
        }
    };
    
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() 
    {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) 
        {
        	//auto connection
            if (device == null) return;
            Log.d(TAG, "device name:"+device.getName());
            
            
            //parse ScanRecord data
            List<AdRecord> records = AdRecord.parseScanRecord(scanRecord);
            if (records.size() == 0) 
            {
                Log.i(TAG, "Scan Record Empty");
            } 
            else 
            {
                Log.i(TAG, "Scan Record: " + TextUtils.join(",", records));
            }

            adName = null;     
            //get advertise Name
            for(AdRecord packet : records) 
            {
            	Log.i(TAG,"packet.getType():"+packet.getType());
                //Find the device name record
                if (packet.getType() == AdRecord.TYPE_NAME) 
                {
                	adName = AdRecord.getName(packet);
                	Log.i(TAG, "advertise Name:"+adName);
                }
                else if (packet.getType() == AdRecord.TYPE_UUID16) 
                {
                	Log.i(TAG,"advertise Service:"+
                	Integer.toHexString(AdRecord.getServiceDataUuid(packet)));
                }
            } 
            
           // if(adName.equals(DEFAULTDEVICENAME)|| adName.equals("Xb40"))
            if( (adName.charAt(0) == 'X' || adName.charAt(0) == 'x') && (adName.charAt(1) == 'b') )
            {
            	scanLeDevice(false);
            	mDeviceAddress = device.getAddress();                   
            	Intent gattServiceIntent = new Intent(DeviceScanActivity.this, BluetoothLeService.class);
            	Log.i(TAG,"Bind Service:"+ bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE));                
	        }
        }
    };
    
    private void scanLeDevice(final boolean enable) 
    {
    	if (enable) 
    	{
          	
    		if(!mScanning)
    		{
    			Log.i(TAG, "Start BLE Scan");
    			
    			android.os.SystemClock.sleep(3000);
    			Log.i(TAG, "after delay 3 sec");
    			
    			addStatus("Scanning device:");
    			mScanning = true;
    			mBluetoothAdapter.startLeScan(mLeScanCallback);
    		}
        } 
    	else 
    	{
        	Log.i(TAG, "Stop BLE Scan");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
    
    private void timeoutDisconnect()
    {
    	if(!setTimeout)
    	{
    		Log.i(TAG, "set timeoutDisconnect");
    		mHandler.postDelayed(DisconnectTask,10000);
    		setTimeout = true;
    	}
    	
    }
    
    private void cancelTimeoutDis()
    {
    	if(setTimeout)
    	{
    		Log.i(TAG, "realse timeoutDisconnect");
    		mHandler.removeCallbacks(DisconnectTask);
    		setTimeout = false;
    	}
    }
    
    private Runnable DisconnectTask = new Runnable()
    {
    	public void run()
    	{
    		Log.e(TAG,"in DisconnectTask");   
    		if(BLEConnected)
    		{   
    			//	releaseWakeLock();    	
    			mBluetoothLeService.disconnect();	
    			setTimeout = false;
 //   			scanLeDevice(true);
    		}
    	}
    };
    
    private void addStatus(String str)
    {
    	Log.d("addStatus", "str："+str);
    	//if(StatusText.length()> 1000){StatusText.setText("");}
    	if(StatusText.length() > 1000)
    		StatusText="";
    	
    	Time mTime = new Time();
		mTime.setToNow();
    	//StatusText.append("\t\n"+mTime.format("%H:%M:%S") +" "+ str);
		//StatusText = "\t\n"+mTime.format("%H:%M:%S") +" "+ str; 
		//Log.d("addStatus", "StatusText："+StatusText);
    }
    
    
         
}