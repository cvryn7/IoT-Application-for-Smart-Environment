package edu.rit.csci759.mobile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class TemperatureService extends Service{


	ServerSocket serverSocket = null;
	Socket socket = null;
	Scanner input = null;
	String notificationString;
	int count;
	int time = 0;
	final static String MY_ACTION = "MY_ACTION";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		
		Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_SHORT).show();
		
		time = intent.getIntExtra("Time", 2000);
		
		Mythread mt = new Mythread();
		mt.start();
		
		return START_STICKY;
	}
	
	
	class Mythread extends Thread{
		
		String response = "";	
		
		public void run(){
			
			while(true){
				
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				response = JSONHandler.testJSONRequest("10.10.10.112:8080", "getTemp");
				
				Intent in = new Intent();
				Bundle b = new Bundle();
				
				in.setAction(MY_ACTION);
				
				b.putString("RES", response);
				in.putExtras(b);
				sendBroadcast(in);
				
			}
			
		}
		
		
	}
	
	
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();;
	}

}
