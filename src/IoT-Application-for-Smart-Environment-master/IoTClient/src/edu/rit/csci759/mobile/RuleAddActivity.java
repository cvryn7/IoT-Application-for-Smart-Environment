package edu.rit.csci759.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

/**
 * Rule additions
 * @author vaibhav, karan and dler
 *
 */
public class RuleAddActivity extends Activity {

	private Spinner spinner_temp; 
	private Spinner spinner_boolean;
	private Spinner spinner_light ;
	private Spinner spinner_blind;
	private Button addNewRule;
	private String temperature;
	private String operator;
	private String light;
	private String blind;
	private String finalRule;
	private StringBuilder newRule = new StringBuilder();
	ArrayList<String> stringToSend = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule_add);
		
		spinner_temp = (Spinner) findViewById(R.id.spinner_temp);
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
		
		spinner_temp.setAdapter(tempAdatAdapter);
		
		spinner_boolean = (Spinner) findViewById(R.id.spinner_boolean);
		
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("");
		operatorList.add("and");
		operatorList.add("or");
		
		ArrayAdapter<String> opeartorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , operatorList);
		
		opeartorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner_boolean.setAdapter(opeartorAdapter);
		
		spinner_light = (Spinner) findViewById(R.id.spinner_light);
		
		ArrayList<String> lightList = new ArrayList<String>();
		lightList.add("");
		lightList.add("dim");
		lightList.add("bright");
		lightList.add("dark");
		
		ArrayAdapter<String> lightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lightList);
		lightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner_light.setAdapter(lightAdapter);
		
		spinner_blind = (Spinner) findViewById(R.id.spinner_blind);
		
		ArrayList<String> blindList = new ArrayList<String>();
		
		blindList.add("");
		blindList.add("open");
		blindList.add("half");
		blindList.add("close");
		
		
		ArrayAdapter<String> blindAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, blindList);
		blindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner_blind.setAdapter(blindAdapter);
		
		temperature = spinner_temp.getItemAtPosition(0).toString();
		operator = spinner_boolean.getItemAtPosition(0).toString();
		light = spinner_light.getItemAtPosition(0).toString();
		blind = spinner_blind.getItemAtPosition(0).toString();
		
		
		spinner_temp.setOnItemSelectedListener(new TempItemSelectListner());
		
		spinner_boolean.setOnItemSelectedListener(new OpeartorItemSelectListner());
		
		spinner_light.setOnItemSelectedListener(new LightItemSelectListner());
		
		spinner_blind.setOnItemSelectedListener(new BlindItemSelectListner());
		
		addNewRule = (Button) findViewById(R.id.new_rule_add_button);		
		
		addNewRule.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {						
						
						stringToSend = new ArrayList<String>();
						
						if(!temperature.equals("")){
							newRule.append(" temperature IS " + temperature);
							stringToSend.add(temperature);
						}else{
							stringToSend.add("null");
						}
						
						if(!light.equals("")){
							newRule.append(" ambient IS " + light);
							stringToSend.add(light);
						}else{
							stringToSend.add("null");
						}
						
						
						if(!operator.equals("")){
							newRule.append(" " + operator);
							stringToSend.add(operator);
						}else{
							stringToSend.add("null");
						}						
						
						
						if(!blind.equals("")){							
							stringToSend.add(blind);
						}else{
							stringToSend.add("null");
						}
						
						
						if(!(newRule.toString().equals("") || newRule.toString().equals(" " + operator.toUpperCase()))){
							finalRule = "IF" + newRule.toString() + " THEN blind IS " + blind + ";";
							newRule = new StringBuilder();
							Toast.makeText(getApplicationContext(), finalRule, Toast.LENGTH_SHORT).show();
							new AddNewRuleJSONRequest().execute();							
						}else{
							newRule = new StringBuilder();
							Toast.makeText(getApplicationContext()," Try Again", Toast.LENGTH_SHORT).show();
						}		
												
					}
					
				}
				
	    );
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rule_add, menu);
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
	
	class TempItemSelectListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			temperature = parent.getItemAtPosition(position).toString();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class OpeartorItemSelectListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			operator = parent.getItemAtPosition(position).toString();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	class LightItemSelectListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			light = parent.getItemAtPosition(position).toString();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class BlindItemSelectListner implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			blind = parent.getItemAtPosition(position).toString();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class AddNewRuleJSONRequest extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			int error_code = 0;
			String response = "";
			
			
			MyRule ruleToSend = new MyRule();
			
			ruleToSend.setTemperature(stringToSend.get(0));
			ruleToSend.setLight(stringToSend.get(1));
			ruleToSend.setOperator(stringToSend.get(2));
			ruleToSend.setBlind(stringToSend.get(3));
			
			String toAddRuleSend = stringToSend.get(0) + ":" 
					+ stringToSend.get(1) + ":"
					+ stringToSend.get(2) + ":" +
					stringToSend.get(3);
			
			response = JSONHandler.addRuleJSONRequest("addRule", toAddRuleSend);
			
			
			
			return "" + response;
		}
		
		 protected void onPostExecute(String result) {
	    	
			// Toast.makeText(getApplicationContext(),"Code of add " + result, Toast.LENGTH_SHORT).show();
	    	
	     }
		
	}
	
}
