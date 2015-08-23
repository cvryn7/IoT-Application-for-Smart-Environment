package edu.rit.csci759.mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class JSONHandler {

	public static String testJSONRequest(String server_URL_text, String method){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		Log.d("Debug serverURL", server_URL_text);
		
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);

		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return response.getResult().toString();
	}
	
	
	public static String addRuleJSONRequest(String method, String reqString){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		//Log.d("Debug serverURL", server_URL_text);
	
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RULE", reqString);
		
		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);
		request.setNamedParams(map);
				
		//request.("RULE", reqString);
		
		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return response.getResult().toString();
	}
	
	
	
	
	public static String getRuleJSONRequest(String method){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		//Log.d("Debug serverURL", server_URL_text);
		
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);

		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return  response.getResult().toString();
	}
	
	
	

	public static String editRuleJSONRequest(String method, int rule_id, String ruleList){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		//Log.d("Debug serverURL", server_URL_text);
		
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RULE", ruleList);
		map.put("RULE_ID", ""+rule_id);
		
		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);
		request.setNamedParams(map);
		
		
		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return  response.getResult().toString();
	}
	
	
	
	public static String deleteRuleJSONRequest(String method, int rule_id){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		//Log.d("Debug serverURL", server_URL_text);
		
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("RULE_ID", ""+rule_id);
		
		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);
		request.setNamedParams(map);
		
		
		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return  response.getResult().toString();
	}
	
	
	
	
	public static String sendIPJSONRequest(String method, String ipAddressAndPort){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		//Log.d("Debug serverURL", server_URL_text);
		
		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://10.10.10.112:8080");

		} catch (MalformedURLException e) {
		// handle exception...
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);


		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("IP_PORT", ipAddressAndPort);
		
		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);
		request.setNamedParams(map);
		
		
		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());
		
	
		return  response.getResult().toString();
	}
	
	
	
}
