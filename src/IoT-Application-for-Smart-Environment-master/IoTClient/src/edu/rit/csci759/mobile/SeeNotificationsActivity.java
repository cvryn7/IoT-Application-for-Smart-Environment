package edu.rit.csci759.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Set all notifications
 * @author vaibhav, karan and dler
 *
 */
public class SeeNotificationsActivity extends Activity {

	ArrayList<String> notificationList = new ArrayList<String>();
	int time = 20000;
	Bundle b;
	static final String MY_ACTION = "MY_ACTION";
	ArrayAdapter<String> listAdapter;
	ListView rulesListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_notifications);
		
		// Get time interval of notification from user
		Intent intent = getIntent();
		time = intent.getIntExtra("TIME", 20000);
		
		// Populate notifications
		
		// Get notifications
		//new NotifJSONRequest().execute();
		
		
		listAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, notificationList);		
        rulesListView = (ListView) findViewById(R.id.NotificationsList);
        rulesListView.setAdapter(listAdapter);
        
		
		MyRec rec = new MyRec();
		
		IntentFilter inF = new IntentFilter();
		
		inF.addAction(MY_ACTION);
		
		registerReceiver(rec, inF);
		
		Intent i = new Intent(SeeNotificationsActivity.this, TemperatureService.class);
		i.putExtra("Time", time);
		startService(i);
		
		
		
		
		
	}

	
	class NotifJSONRequest extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			String response = "";
			

			Context context = getApplicationContext();
			
			Intent i = new Intent(SeeNotificationsActivity.this, TemperatureService.class);
			i.putExtra("Time", time);
			startService(i);
			
			
			response = "temperature is " + response;
			notificationList.add(response);
			System.out.println("RESPONSE " + response);
				
				
				        
	        //
		
			return null;
			
		}
		
	}
	
	
	class MyRec extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			b = intent.getExtras();
			
			String res = b.getString("RES");
			
			notificationList.add(res);
			
			listAdapter.notifyDataSetChanged();
			
			System.out.println("RESSS " + res);
			
			Toast.makeText(SeeNotificationsActivity.this, res, Toast.LENGTH_SHORT).show();
			
			
		}
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_notifications, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
