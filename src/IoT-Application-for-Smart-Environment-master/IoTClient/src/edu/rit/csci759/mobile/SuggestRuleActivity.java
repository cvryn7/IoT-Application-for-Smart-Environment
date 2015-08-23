package edu.rit.csci759.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Suggests new fuzzy rule to user depending on temperature outside.
 * EXTRA Functionality 
 * 
 * @author vaibhav , karan and dler
 *
 */

public class SuggestRuleActivity extends Activity {

	private EditText zipcodeView;
	private Button suggestRuleButton;
	private Button addSuggestedButton;
	private String zipcodeString;
	private TextView suggestedTempView;
	private TextView suggestedRuleView;
	private String lightValue;
	String toAddRuleSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_rule);
		
		zipcodeView = (EditText) findViewById(R.id.ui_zipcode);
		suggestRuleButton = (Button) findViewById(R.id.ui_btn_suggest_rule);
		addSuggestedButton = (Button) findViewById(R.id.ui_btn_add_suggested_rule);
		
		suggestedTempView = (TextView) findViewById(R.id.outside_temperature);
		suggestedRuleView = (TextView) findViewById(R.id.suggested_rule);
		
		
		suggestRuleButton.setOnClickListener(
				
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// Take zipcode of user
						zipcodeString = zipcodeView.getText().toString();
						//Toast.makeText(getApplicationContext(), zipcodeString, Toast.LENGTH_SHORT).show();
						new Weather().execute();
						
					}
				}
				
		);
		
		
		addSuggestedButton.setOnClickListener(
			new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					new AddSuggested().execute();
					
				}
			}
		);
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest_rule, menu);
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
	
	
	class AddSuggested extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String response = JSONHandler.addRuleJSONRequest("addRule", toAddRuleSend);
			
			return null;
		}
		
		
		
	}

	
	class Weather extends AsyncTask<Void, String, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			String responseLight = "";
			String temperature = "";
			
			
			   HttpURLConnection urlConnection = null;
	           BufferedReader reader = null;
	           zipcodeString = zipcodeView.getText().toString();

	           // Will contain the raw JSON response as a string.
	            String forecastJsonStr = null;
	            StringBuffer buffer = new StringBuffer();
	            JSONObject jObject = null;
	            
	            try {
	            	// Construct the URL for the OpenWeatherMap query
	            	// Possible parameters are avaiable at OWM's forecast API page, at
	            	// http://openweathermap.org/API#forecast
	                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+zipcodeString+"&mode=json&units=metric");

	                // Create the request to OpenWeatherMap, and open the connection
	                urlConnection = (HttpURLConnection) url.openConnection();
	                urlConnection.setRequestMethod("GET");
	                urlConnection.connect();

	                // Read the input stream into a String
	                InputStream inputStream = urlConnection.getInputStream();
	                
	                if (inputStream == null) {

	                    return null;
	                }
	                reader = new BufferedReader(new InputStreamReader(inputStream));

	                String line;
	                while ((line = reader.readLine()) != null) {
	                    buffer.append(line + "\n");
	                }

	                if (buffer.length() == 0) {
	                    return null;
	                }
	                forecastJsonStr = buffer.toString();

	            } catch (Exception e) {
	                Log.e("PlaceholderFragment", "Error ", e);
	                System.out.println(e.getMessage());
	                return null;
	            } finally{
	                if (urlConnection != null) {
	                    urlConnection.disconnect();
	                }
	                if (reader != null) {
	                    try {
	                        reader.close();
	                    } catch (final Exception e) {
	                        Log.e("PlaceholderFragment", "Error closing stream", e);
	                    }
	                }
	            }
			
			
	         try {
				jObject = new JSONObject(buffer.toString());
				System.out.println("JOBJ " + jObject);				
				JSONArray list = jObject.getJSONArray("list");
				System.out.println("LIST " + list);
				
				JSONObject li = list.getJSONObject(0);
				
				JSONObject temp = li.getJSONObject("temp");
				
				System.out.println("TEMP " + temp);
				
				temperature = temp.getString("day");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}  
	         
	        System.out.println("Inside " + buffer.toString());
	        System.out.println("TEMP " + temperature);
			return temperature;
			
		}
		
		protected void onPostExecute(String resString){			
					
			suggestedTempView.setText(Html.fromHtml(resString + "<sup>o</sup>C"));
			
			double temp = Double.parseDouble(resString);
			ArrayList<String> suggestRule = new ArrayList<String>();
			String suggTemp = "";
			
			// Suggest Rule depending upon outside temperature
			
			if(temp > -50 && temp <= 0){
				suggTemp = "freezing";
				suggestRule.add(suggTemp);
				suggestRule.add("null");
				suggestRule.add("null");
				suggestRule.add("open");
			}else if(temp > 0 && temp <= 10) {
				suggTemp = "cold";
				suggestRule.add(resString);
				suggestRule.add("null");
				suggestRule.add("null");
				suggestRule.add("open");
			}else if(temp > 10 && temp <= 20) {
				suggTemp = "comfort";
				suggestRule.add(resString);
				suggestRule.add("null");
				suggestRule.add("null");
				suggestRule.add("open");
			}else if(temp > 20 && temp <= 30) {
				suggTemp = "warm";
				suggestRule.add(resString);
				suggestRule.add("null");
				suggestRule.add("null");
				suggestRule.add("close");
			}else if(temp > 30 && temp <= 60) {
				suggTemp = "hot";
				suggestRule.add(resString);
				suggestRule.add("null");
				suggestRule.add("null");
				suggestRule.add("close");
			}			
			
			toAddRuleSend = suggTemp + ":" 
					+ suggestRule.get(1) + ":"
					+ suggestRule.get(2) + ":" +
					suggestRule.get(3);
			
			suggestedRuleView.setText("IF temperature IS " + suggTemp +
									   " THEN blind IS " + suggestRule.get(3));
			
		}
		
	}
	
}
