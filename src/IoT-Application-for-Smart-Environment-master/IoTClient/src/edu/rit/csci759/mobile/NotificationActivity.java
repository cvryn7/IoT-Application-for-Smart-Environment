package edu.rit.csci759.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NotificationActivity extends Activity {

	EditText notificationTime;
	int timeNotification = 2000;
	Button addTimeNotBtn;
	Button seeNotBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		notificationTime = (EditText) findViewById(R.id.ui_notifications_time);
		addTimeNotBtn = (Button) findViewById(R.id.add_notification_rule_button);
		seeNotBtn = (Button) findViewById(R.id.see_notifications_button);
		
		addTimeNotBtn.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						timeNotification = Integer.parseInt(notificationTime.getText().toString());
						
					}
					
				}
				
		);
		
		
		
		seeNotBtn.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent seeIntent = new Intent(NotificationActivity.this,SeeNotificationsActivity.class);
						seeIntent.putExtra("TIME", timeNotification);
						startActivity(seeIntent);
						
					}
				}
				
		);
		
		
		
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
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
