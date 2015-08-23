package edu.rit.csci759.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Rules Activity - Displays All Rules
 * Rules can be added, deleted and edited
 * 
 * @author vaibhav, karan and dler
 *
 */

public class RulesActivity extends Activity {

	Button addRule;
	ArrayList<MyRule> rulesList = new ArrayList<MyRule>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		
		// To populate list
		new SendRuleJSONRequest().execute();
		
		ListView rulesListView = (ListView) findViewById(R.id.fuzzyRules);
		
		/*
		 * To edit / delete a rule
		 */
		rulesListView.setOnItemClickListener(
				  
				  new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {
						
						// Get particular rule
						String rule = String.valueOf(parent.getItemAtPosition(position));
						
						//Toast.makeText(getApplicationContext(), rule, Toast.LENGTH_SHORT).show();
						
						Intent editRuleIntent = new Intent(RulesActivity.this, RuleEditActivity.class);
						System.out.println("LIGHTT " + rulesList.get(position).getLight());
						editRuleIntent.putExtra("RULE", rulesList.get(position));
						startActivity(editRuleIntent);
						
					}
					  
				  } 
				  
			);
		
		addRule = (Button) findViewById(R.id.addRuleButton);
		
		/*
		 * To add a new rule
		 */
		addRule.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent addNewRuleIntent = new Intent(RulesActivity.this, RuleAddActivity.class);
						startActivity(addNewRuleIntent);
						
					}
				});
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.rules, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		
		switch(id){
		
			case R.id.action_refresh_rules:
		        rulesList = new ArrayList<MyRule>();
				new SendRuleJSONRequest().execute();
				break;
			
			case R.id.action_settings:
				break;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	class SendRuleJSONRequest extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			String response = "";  // Response from Pi
						
			response = JSONHandler.getRuleJSONRequest("getRules");
			
			Log.d("RULEs RES", response);			
			
			String[] allRules = response.split("\n");
			String[] rule = null;  // For each single rule to be split into many words
			
			// Get all rules from Pi
			for(int i = 0; i < allRules.length; i++){
				allRules[i] = allRules[i].replace("(", ""); // Replace unwanted characters
				allRules[i] = allRules[i].replace(")", "");
				rule = allRules[i].split(" ");
				
				// Rule object contains different variables
				// like temperature, ambient, blind, operator
			    MyRule ruleObj = new MyRule();
			    
			    // Initialize Rule object
			    
			    ruleObj.setTemperature("");
			    ruleObj.setOperator("");
			    ruleObj.setLight("");
			    ruleObj.setBlind("");
			    
				if(rule.length == 8){	
				
					if(rule[1].equals("temperature")){
						ruleObj.setTemperature(rule[3]);
						ruleObj.setOperator("");
						ruleObj.setLight("");						
					}else{
						ruleObj.setLight(rule[3]);
						ruleObj.setOperator("");
						ruleObj.setTemperature("");
					}
					
					ruleObj.setBlind(rule[7]);					
				
				}else if(rule.length == 12){
					
					ruleObj.setTemperature(rule[3]);
					
					ruleObj.setOperator(rule[4]);
					
					ruleObj.setLight(rule[7]);
					
					ruleObj.setBlind(rule[11]);
					
				}
				
				ruleObj.setRuleID(i + 1); // To set ID of rule from 1
				ruleObj.setCompleteRule(allRules[i]);
				rulesList.add(ruleObj);
				
				System.out.println("RULE LIST " + rulesList.get(i));
			
			}
			
			
			return response;
		}
		
		  protected void onPostExecute(String result) {
			  
			  Log.d("RULES", result);
			  
			  // Get all rules
			  String[] res = result.split("\n");
			  
			  // Initialize List Adapter
			  ListAdapter listAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, res);			  
			  ListView rulesListView = (ListView) findViewById(R.id.fuzzyRules);		
			  rulesListView.setAdapter(listAdapter);
			  
		  }
		
	}
	
}
