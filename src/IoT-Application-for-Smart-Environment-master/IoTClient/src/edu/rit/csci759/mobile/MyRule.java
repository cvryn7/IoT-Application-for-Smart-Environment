package edu.rit.csci759.mobile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rule Data Set
 * @author vaibhav, karan and dler
 *
 */

public class MyRule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private int ruleID;
	private String temperature;
	private String light;
	private String time;
	private String operator;
	private String blind;
	private String completeRule;
	private ArrayList<String> wholeRuleList;
	
	public MyRule(){
			}
	
	public int getRuleID() {
		return ruleID;
	}
	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBlind() {
		return blind;
	}

	public void setBlind(String blind) {
		this.blind = blind;
	}

	public String getCompleteRule() {
		return completeRule;
	}

	public void setCompleteRule(String completeRule) {
		this.completeRule = completeRule;
	}

	public ArrayList<String> getWholeRuleList() {
		return wholeRuleList;
	}

	public void setWholeRuleList(ArrayList<String> wholeRuleList) {
		this.wholeRuleList = wholeRuleList;
	}
	
	
	
}
