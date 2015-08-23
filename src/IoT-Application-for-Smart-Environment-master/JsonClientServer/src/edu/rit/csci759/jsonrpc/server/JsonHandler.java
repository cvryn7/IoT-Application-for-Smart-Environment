package edu.rit.csci759.jsonrpc.server;


/**
 * Demonstration of the JSON-RPC 2.0 Server framework usage. The request
 * handlers are implemented as static nested classes for convenience, but in 
 * real life applications may be defined as regular classes within their old 
 * source files.
 *
 * @author Vladimir Dzhuvinov
 * @version 2011-03-05
 */ 

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;

public class JsonHandler {

	/* Class RuleHandler
	 * Author : Dler, Vaibhav and Karan
	 * Description: Contains member methods to modify
	 * 				Rules in the fcl file.
	 */

	public static class RuleHandler implements RequestHandler {

		static TipperClass tipperClass=null;
		
		//Constructor to RuleHandler
		public RuleHandler(){
			if(tipperClass==null){
				tipperClass= new TipperClass();
			}
		}

		// Reports the method names of the handled requests
		public String[] handledRequests() {
			
			//All the method which client can request for.
			return new String[]{"addRule", "getRules" ,"editRule","deleteRule"};
		}


		// Processes the requests
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {

			
			String hostname="unknown";
	
			try {
				hostname=InetAddress.getLocalHost().getHostName();			
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			//Add new Rule to the RuleBlock 
			if (req.getMethod().equals("addRule")) {
				ArrayList<String> ruleParam = new ArrayList<String>();
				Map<String, Object> map = req.getNamedParams();
				
				//Getting new Rule from client in String from map.
				String ruleStr=(String)map.get("RULE");
				
				String[] reqParams = ruleStr.split(":");
				ruleParam.add(reqParams[0]);
				ruleParam.add(reqParams[1]);
				ruleParam.add(reqParams[2]);
				ruleParam.add(reqParams[3]);
				
				//Adding the Rule by invoking addRule method in object of TipperClass
				String response=tipperClass.addRule(ruleParam);

				return new JSONRPC2Response(response, req.getID());

			}
			//Sending List of Rules on Client Request
			else if (req.getMethod().equals("getRules")) {
				//System.out.println(req.toString());
				String response=tipperClass.getRules();
				//System.out.println("getRules: "+response);
				return new JSONRPC2Response(response, req.getID());
			}
			//Edit existing Rule
			else if (req.getMethod().equals("editRule")) {

				ArrayList<String> ruleParam = new ArrayList<String>();
				Map<String, Object> map = req.getNamedParams();
				String ruleStr=(String)map.get("RULE");
				int ruleId = Integer.parseInt((String)map.get("RULE_ID"));
				
				String[] reqParams = ruleStr.split(":");
				 
				ruleParam.add(reqParams[0]);
				ruleParam.add(reqParams[1]);
				ruleParam.add(reqParams[2]);
				ruleParam.add(reqParams[3]);
				
				String response=tipperClass.editRule(ruleId,ruleParam);
				
				return new JSONRPC2Response(response, req.getID());
			}
			//Delete existing Rule.
			else if (req.getMethod().equals("deleteRule")) {
				
				Map<String, Object> map = req.getNamedParams();
				
				//Delete rule with specific ruleId sent by client
				int ruleId = Integer.parseInt((String)map.get("RULE_ID"));

				String response=tipperClass.deleteRule(ruleId);

				return new JSONRPC2Response(response, req.getID());
				
			}
			else {

				// Method name not supported

				return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
			}
		}


	}

	/* Class TempLightHandler
	 * Author : Dler, Vaibhav and Karan
	 * Description: Contains member methods to get
	 * 				current Temperature and ambient
	 * 				Recorded by RaspberryPi.
	 */
	
	public static class TempLightHandler implements RequestHandler {
		
		public TempLightHandler(){
			 
		 }
		 
	     // Reports the method names of the handled requests
		public String[] handledRequests() {
			
			//List of Member methods of this class
		    return new String[]{"getLight", "getTemp"};
		}
		
		
		
		// Processes the requests
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
		
			
			String hostname="unknown";
			try {
				hostname=InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			//Method to handle request demanding current ambient reading
		    if (req.getMethod().equals("getLight")) {
		    
			String light=  Integer.toString(LedBlindSimulator.read_ambient_light_intensity());
			
			return new JSONRPC2Response(light, req.getID());

	         }
		    //Method to handle request demanding current Temperature reading
	         else if (req.getMethod().equals("getTemp")) {
	        	
	        	 int tempOriginal=(int)(LedBlindSimulator.read_temperature() / 10.24);
		       
			String temp = Integer.toString(tempOriginal);
			
			return new JSONRPC2Response(temp, req.getID());
	         }
		    else {
		    
		        // Method name not supported
			
			return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
	         }
	     }
	}
}
