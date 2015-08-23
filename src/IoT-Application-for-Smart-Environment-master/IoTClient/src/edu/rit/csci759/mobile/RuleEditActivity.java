package edu.rit.csci759.mobile;

import java.util.ArrayList;

import edu.rit.csci759.mobile.RuleAddActivity.BlindItemSelectListner;
import edu.rit.csci759.mobile.RuleAddActivity.LightItemSelectListner;
import edu.rit.csci759.mobile.RuleAddActivity.OpeartorItemSelectListner;
import edu.rit.csci759.mobile.RuleAddActivity.TempItemSelectListner;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * User can edit / delete rules
 * @author vaibhav, karan and dler
 *
 */

public class RuleEditActivity extends Activity {

	static private Spinner temperatureSpinner;
	static private Spinner operatorSpinner;
	static private Spinner lightSpinner;
	static private Spinner blindSpinner;
	private Button editButton;
	private Button deleteButton;
	private TextView currentTemp;
	private TextView currentOperator;
	private TextView currentLight;
	private TextView currentBlind;
	static private String temperature = "";
	static private String operator = "";
	static private String light = "";
	static private String blind = "";
	private String finalRule;
	static private int ruleID;
	ArrayList<String> stringToSend = new ArrayList<String>();
	MyRule ruleObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule_edit);
		Intent ruleIntent = getIntent();
		
		ruleObj = (MyRule) ruleIntent.getSerializableExtra("RULE");
		
		// Get current values
		
		temperature = ruleObj.getTemperature(); 
		
		operator = ruleObj.getOperator();
		
		light = ruleObj.getLight();
		
		blind = ruleObj.getBlind();
		
		finalRule = ruleObj.getCompleteRule();
		
		System.out.println("TEMP RULE " + temperature);
		System.out.println("Light Rule " + light);
		System.out.println("blind rule " + blind);
		
		ruleID = ruleObj.getRuleID();
		
		// Set current values to View
		
		editButton = (Button) findViewById(R.id.rule_edit_button);
		
		deleteButton = (Button) findViewById(R.id.rule_delete_button);		
		
		currentTemp = (TextView) findViewById(R.id.ui_label_current_temp);
		
		currentTemp.setText(temperature);
		
		currentOperator = (TextView) findViewById(R.id.ui_label_current_boolean);
		
		currentOperator.setText(operator);
		
		currentLight = (TextView) findViewById(R.id.ui_label_current_light);
		
		currentLight.setText(light);
		
		currentBlind = (TextView) findViewById(R.id.ui_label_current_blind);
		
		currentBlind.setText(blind);
		
		temperatureSpinner = (Spinner) findViewById(R.id.spinner_temp_edit);
		
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("");
		temp.add("hot");
		temp.add("warm");
		temp.add("cold");
		temp.add("comfort");
		temp.add("freezing");
		
		ArrayAdapter<String> tempAdatAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item
				, temp);
		
		tempAdatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		temperatureSpinner.setAdapter(tempAdatAdapter);
		
        operatorSpinner = (Spinner) findViewById(R.id.spinner_boolean_edit);
		
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("");
		operatorList.add("and");
		operatorList.add("or");
		
		ArrayAdapter<String> opeartorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , operatorList);
		
		opeartorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		operatorSpinner.setAdapter(opeartorAdapter);
		
		lightSpinner = (Spinner) findViewById(R.id.spinner_light_edit);
		
		ArrayList<String> lightList = new ArrayList<String>();
		lightList.add("");
		lightList.add("dim");
		lightList.add("bright");
		lightList.add("dark");
		
		ArrayAdapter<String> lightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lightList);
		lightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		lightSpinner.setAdapter(lightAdapter);
		
		blindSpinner = (Spinner) findViewById(R.id.spinner_blind_edit);
		
		ArrayList<String> blindList = new ArrayList<String>();
		blindList.add("");
		blindList.add("open");
		blindList.add("half");
		blindList.add("close");
		
		
		ArrayAdapter<String> blindAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, blindList);
		blindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		blindSpinner.setAdapter(blindAdapter);
		
		
		
		temperatureSpinner.setOnItemSelectedListener(new TempItemSelectEditListner());
		
		operatorSpinner.setOnItemSelectedListener(new OpeartorItemSelectEditListner());
		
		lightSpinner.setOnItemSelectedListener(new LightItemSelectEditListner());
		
		blindSpinner.setOnItemSelectedListener(new BlindItemSelectEditListner());
		
		/*
		 * To edit a rule
		 */
		
		editButton.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(!temperature.equals("")){
							
							System.out.println("Temp " + temperature);
							
							stringToSend.add(temperature);
						}else{
							System.out.println("Temp " + temperature);
							
							if(currentTemp.getText().toString().equals("")){
								stringToSend.add("null");
							}else{
								stringToSend.add(currentTemp.getText().toString());
							}
							
							
						}
						

						if(!light.equals("")){
							System.out.println("light " + light);
							
							stringToSend.add(light);
						}else{
							System.out.println("light " + light);
							
							if(currentLight.getText().toString().equals("")){
								stringToSend.add("null");
							}else{
								stringToSend.add(currentLight.getText().toString());
							}
							
							
						}
						
						
						if(!operator.equals("")){
							System.out.println("Ope " + operator);
							
							stringToSend.add(operator);
						}else{
							System.out.println("Ope " + operator);
							
							if(currentOperator.getText().toString().equals("")){
								stringToSend.add("null");
							}else{
								stringToSend.add(currentOperator.getText().toString());
							}
							
						}
						
						
						if(!blind.equals("")){
							System.out.println("bli " +blind);
							stringToSend.add(blind);
						}else{
							System.out.println("bli " +blind);
							if(currentBlind.getText().toString().equals("")){
								stringToSend.add("null");
							}else{
								stringToSend.add(currentBlind.getText().toString());
							}
							
							
						}
						
						
						new SendEditJSONRequest().execute();
						
					}
				}
				
		);		
		
		/*
		 * To delete a rule
		 */
		
		deleteButton.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						new SendDeleteJSONRequest().execute();
						
					}
				}
				
		);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rule_edit, menu);
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
	
	
	class TempItemSelectEditListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			temperature = parent.getItemAtPosition(position).toString();
			currentTemp.setText(temperature);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class OpeartorItemSelectEditListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			 operator = parent.getItemAtPosition(position).toString();
			 currentOperator.setText(operator);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	class LightItemSelectEditListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			light= parent.getItemAtPosition(position).toString();
			currentLight.setText(light);			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class BlindItemSelectEditListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			blind = parent.getItemAtPosition(position).toString();
			currentBlind.setText(blind);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	class loadEditUI extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			
			return null;
		}
		
		
		
	}
	
	
	class SendEditJSONRequest extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			StringBuilder newRule = new StringBuilder();
			String response;
						
			
			String toEditRuleSend = stringToSend.get(0) + ":" 
					+ stringToSend.get(1) + ":"
					+ stringToSend.get(2) + ":" +
					stringToSend.get(3);
			
			//Log.v("toEditTTTTTT", toEditRuleSend);
			
			//System.out.println("toEditTTTTTT " + toEditRuleSend);
			
			response = JSONHandler.editRuleJSONRequest("editRule",ruleID, toEditRuleSend);
			
			//finish(); // To finish activity
			return null;
		}
		
	}
	
	
	class SendDeleteJSONRequest extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			// TODO : to send deletion request
			
			String response = "";
			
			response = JSONHandler.deleteRuleJSONRequest("deleteRule",ruleObj.getRuleID());
		
			finish(); // To finish activity
			return null;
		}
		
		
		
	}
	
}
