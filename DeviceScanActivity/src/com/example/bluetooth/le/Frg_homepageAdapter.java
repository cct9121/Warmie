package com.example.bluetooth.le;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Frg_homepageAdapter extends BaseAdapter 
{

    private Activity Context;  
    private ArrayList<String> List1 = new ArrayList<String>(); 
    private ArrayList<String> List2 = new ArrayList<String>(); 
    private ArrayList<String> List3 = new ArrayList<String>(); 
    
    private LayoutInflater LayoutInflater = null; 
    
    public Frg_homepageAdapter(Activity context, ArrayList<String> list1, ArrayList<String> list2, ArrayList<String> list3) 
    {  
         Context = context;  
         List1 = list1; 
         List2 = list2; 
         List3 = list3; 
         LayoutInflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        
    } 
    
    @Override  
    public int getCount() 
    {
         return List1.size();         
    }
    
    @Override  
    public Object getItem(int pos) 
    {  
         return List1.get(pos);  
    }
    
    @Override  
    public long getItemId(int position) 
    {  
         return position;  
    } 
    
    @Override  
    public View getView(int position, View convertView, ViewGroup parent)
    {
         View v = convertView;  
         //Log.d("position", String.valueOf(position));
         recordsListViewHolder viewHolder;  
         if (convertView == null) 
         {  
              LayoutInflater li = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
              v = li.inflate(R.layout.cell_record, null);  
              viewHolder = new recordsListViewHolder(v);  
              v.setTag(viewHolder);  
         } 
         else 
         {  
              viewHolder = (recordsListViewHolder) v.getTag();  
         }  
         viewHolder.Item1.setText(List1.get(position)); 
         viewHolder.Item2.setText(List2.get(position));
         viewHolder.Item3.setText(List3.get(position));
        
         return v;  
    } 	
    
}



class recordsListViewHolder //put data into listview's cell
{
	public TextView Item1;
    public TextView Item2;
    public TextView Item3;

    public recordsListViewHolder(View base) 
    {  
    	Item1 = (TextView) base.findViewById(R.id.records_date);  
    	Item2 = (TextView) base.findViewById(R.id.records_time);
    	Item3 = (TextView) base.findViewById(R.id.records_temp);
        
    } 
} 



