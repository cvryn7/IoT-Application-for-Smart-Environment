package edu.rit.csci759.mobile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity
 * 
 * @author vaibhav, karan and dler
 *
 */
public class MainActivity extends Activity implements OnItemSelectedListener{
	Button btn_send_request;
	Button btn_suggest;
	Button notificationBtn;
	static EditText et_server_url;
	static EditText et_requst_method;
	TextView tv_response;
	TextView tv_response_1;
	TextView tv_response_2;
	private static final String[] jsonMethodNames = {"getTemp", "getLight"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Start Temperature Service
		//new TemperatureNotificationService().execute();
		
		// Get Views
		tv_response = (TextView) findViewById(R.id.tv_response);
		tv_response_1 = (TextView) findViewById(R.id.tv_response_1);
		tv_response_2 = (TextView) findViewById(R.id.tv_response_2);
		
		btn_suggest = (Button) findViewById(R.id.change_fuzzy_logic);
		
		notificationBtn = (Button) findViewById(R.id.btn_extra);
		
		// Get Server Temperature and Light
		new SendJSONRequest().execute();
        
		OnClickListener buttonListener = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	
		    	//Toast.makeText(getApplicationContext(), R.string.Toast, Toast.LENGTH_SHORT).show();		    	
		    	Intent intent = new Intent(MainActivity.this, RulesActivity.class);
		    	// Display List of Rules
		    	startActivity(intent); 
		    	
		    }
		};
		
		
		btn_suggest.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(MainActivity.this, SuggestRuleActivity.class);
						// Suggest Rules (EXTRA FUNCTIONALITY)
				    	startActivity(intent);
						
					}
				}
				
		);
		
		btn_send_request = (Button) findViewById(R.id.btn_sendRequest);
		btn_send_request.setOnClickListener(buttonListener);
		
		notificationBtn.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
						// Notifications
				    	startActivity(intent);
						
					}
				}
				
		);
		
	}
	
	
	public void startService(View view){
		startService(new Intent(getBaseContext(), TemperatureService.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	class TemperatureNotificationService extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			String myIPAddress = "";
			
			Context context = getApplicationContext();
			
			Intent i = new Intent(context, TemperatureService.class);
			context.startService(i);
			
			try {
				myIPAddress = InetAddress.getLocalHost().getHostAddress().toString();
				myIPAddress += ":" + "6363";
				System.out.println("IPADDRESS " + myIPAddress);
			} catch (UnknownHostException e) {				
				e.printStackTrace();
			}
			
			String response = JSONHandler.sendIPJSONRequest("clientIp", myIPAddress);
			
			context.stopService(i);
			
			return null;
		}
		
	}
	
	
	class SendJSONRequest extends AsyncTask<Void, String, String> {
		String response_txt;
		
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			String request_method = "";
			String response_temp = "";
			String response_light = "";
			String server_time = "";
			int light = 0;
			Time time = new Time();
			
			// GET temp and light from Server
			response_temp = JSONHandler.testJSONRequest("10.10.10.112:8080", "getTemp");
			response_light = JSONHandler.testJSONRequest("10.10.10.112:8080", "getLight");
			
			time.setToNow();
			
			// Set time
			server_time = "" + time.hour + ":" + time.minute + ":" + time.second;
						
			light = Integer.parseInt(response_light);
			
			if(light >=0 && light <= 25){
				response_light = "DIM";
			}else if(light > 25 && light <= 75){
				response_light = "BRIGHT";
			}else{
				response_light = "DARK";
			}
			
			// Form a response to set in View
			response_txt = response_temp + " " + response_light + " " + server_time;
			
			return response_txt;
		}
		
		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(String result) {
	    	
	    	 String[] output = result.split(" ");	    	 
	    	 tv_response.setText(Html.fromHtml(output[0] + "<sup>o</sup>C"));
	    	 tv_response_1.setText(output[1]);
	    	 tv_response_2.setText(output[2]);
	    	 
	     }
		
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Do when getTemp and getLight are selected, set method name to it
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}
