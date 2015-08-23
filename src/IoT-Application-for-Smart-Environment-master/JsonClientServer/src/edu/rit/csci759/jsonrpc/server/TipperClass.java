package edu.rit.csci759.jsonrpc.server;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleExpression;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
//import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleExpression;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodAndMin;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodOrMax;


/* Class Tipperclass
 * Author : Dler, Vaibhav and Karan
 * Description: Contains methods to 
 * 				modify and reading
 * 				fuzzy logic file.
 */
public class TipperClass {
	
	public static  FunctionBlock fb;
	public static  RuleBlock rb;
	public static FIS fis;
	final static  String filename;
	
	//defining variables to add and access these variable terms.
	Variable temperatureVar= fb.getVariable("temperature");
	Variable ambientVar= fb.getVariable("ambient");
	Variable blind= fb.getVariable("blind");
	
	//Initializing loading .fcl file 
	//FunctionBlock(fb) and RuleBlock(rb). 
	static{
		filename = "FuzzyLogic/tipper.fcl";
		fis = FIS.load(filename, true);
		
		fb= fis.getFunctionBlock(null);
		rb=fb.getFuzzyRuleBlock("No1");
		
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}
		
	}
	
	//constructor to TipperClass
	public TipperClass(){
		//Everything initialized in static block
	}
	
	//Method to evaluate current Blind state from .fcl file
	//using current temperature and ambient using FunctionBlock(fb) 
	public String setVaribales(double temp, double light ){
		double supportDegree=0;
		String blindPosition="";
		
		fb.setVariable("temperature", temp);
        fb.setVariable("ambient",light);
		
        fb.evaluate();

        //int i=0;
        
        //Calculating DegreeOfSupport of each Rule based on current 
        //Blind value.
        for( Rule r : rb.getRules()){
        //	System.out.println("rule evaluation ------ " + i++ + "  " + r.getDegreeOfSupport() );
        	//Selecting Rule with maximum Degree of Support.
        	if( r.getDegreeOfSupport() >= supportDegree ){
        		supportDegree = r.getDegreeOfSupport();
        		blindPosition = r.getConsequents().element().getTermName();
        	}
        }
        return blindPosition;
	}
	
	//Method to addRule to RuleBlock rb
	public  String addRule(ArrayList<String> param){

		//getting all current rules
		List<Rule> rules= rb.getRules();

		int ruleListSize=rules.size();

		//make a new rule
		Rule newRule= new Rule(Integer.toString(ruleListSize+1),rb);
		
		String temp= param.get(0);
		String light= param.get(1);
		String operator= param.get(2);
		String blindcondition= param.get(3);

		RuleExpression ruleExp;
		
		//Checking if Rule is having both Temperature and Ambient
		if (!temp.equals("null") && !light.equals("null")){

			RuleTerm term1 = new RuleTerm(temperatureVar, temp, false);
			RuleTerm term2 = new RuleTerm(ambientVar, light, false);
			if(operator.equals("and")){
				ruleExp=new RuleExpression(term1,term2, RuleConnectionMethodAndMin.get());
			}else if (operator.equals("or")){
				ruleExp=new RuleExpression(term1,term2,RuleConnectionMethodOrMax.get());
			}else{
				ruleExp=null;
			}
			
			newRule.setAntecedents(ruleExp);
			newRule.addConsequent(blind, blindcondition, false);

		}else if (!temp.equals("null") && light.equals("null")){

			RuleTerm term1 = new RuleTerm(temperatureVar, temp, false);
			ruleExp=new RuleExpression();
			ruleExp.add(term1);
			
			newRule.addConsequent(blind, blindcondition, false);
			newRule.setAntecedents(ruleExp);

		}else if (temp.equals("null") && !light.equals("null")){
			RuleTerm term1 = new RuleTerm(ambientVar, light, false);
			
			ruleExp=new RuleExpression();
			ruleExp.add(term1);
			
			newRule.addConsequent(blind, blindcondition, false);
			newRule.setAntecedents(ruleExp);

		}//returning -1 if Rule do not get added
		else {
			return "-1";
		}
		
		//Add Rule to RuleBlock(rb) if not already Present.
		if(!rules.contains(newRule)){
			rb.add(newRule);
			
		}  // rule already exist
		else{
			return "-2";
		}
		
		//Rule Successfully added
		return "1";
	}
	
	//Return List of Rules in RuleBlock(rb)
	public String getRules(){	
		StringBuilder rulesStr= new StringBuilder();
		for (Rule elements : rb.getRules()){
			String tmpStr=elements.toString().substring(8, elements.toString().length()-14);
			rulesStr.append(tmpStr + "\n");
		}
		//System.out.println(rulesStr.toString());
		
		//Sending Rules as one string separated by delimiter "\n"
		return rulesStr.toString();
	}

	//Method to edit existing Rule in the RuleBlock(rb)
	public String editRule(int ruleNum, ArrayList<String> param){
		
		String temp= param.get(0);
		String light= param.get(1);
		String operator= param.get(2);
		String blindcondition= param.get(3);
		
		RuleExpression ruleExp;
		Variable blind= fb.getVariable("blind");
		
		List<Rule> rules= rb.getRules();
		//Remove the rule to edit and then add
		//new rule edited rule in its place.
		rules.remove(ruleNum-1);

		//make a new rule
		Rule newRule= new Rule(Integer.toString(ruleNum),rb);

		//Checking if edited Rule has both Temperature and Ambient
		if (!temp.equals("null") && !light.equals("null")){

			RuleTerm term1 = new RuleTerm(temperatureVar, temp, false);
			RuleTerm term2 = new RuleTerm(ambientVar, light, false);
			if(operator.equals("and")){
				ruleExp=new RuleExpression(term1,term2,RuleConnectionMethodAndMin.get());
			}else{
				ruleExp=new RuleExpression(term1,term2,RuleConnectionMethodOrMax.get());
			}
			newRule.addConsequent(blind, blindcondition, false);
			newRule.setAntecedents(ruleExp);



		}else if (!temp.equals("null") && light.equals("null")){

			RuleTerm term1 = new RuleTerm(temperatureVar, temp, false);
			newRule.addConsequent(blind, blindcondition, false);
			ruleExp=new RuleExpression();
			ruleExp.add(term1);
			newRule.setAntecedents(ruleExp);


		}else if (temp.equals("null") && !light.equals("null")){
			RuleTerm term1 = new RuleTerm(ambientVar, light, false);
			newRule.addConsequent(blind, blindcondition, false);
			ruleExp=new RuleExpression();
			ruleExp.add(term1);
			newRule.setAntecedents(ruleExp);
		}//Return -1 if Rule cannot be edited.
		else {
			return "-1";
		}

		rules.add(ruleNum-1, newRule);
		rb.setRules(rules);
		return "1";
	}
	
	//Method to Delete existing Rule RuleBlock(rb)

	public String deleteRule(int ruleNum){
		
		//Deleting Rule with Specific ruleNumber sent by client
		List<Rule> rules= rb.getRules();
		if(ruleNum >rules.size() || ruleNum <= 0){
			return "-1";
		}
		
		rules.remove(ruleNum-1);
		
		for(int i=ruleNum-1; i < rules.size() ; i++){
			rules.get(i).setName(Integer.toString(i+1));
		}
		
		rb.setRules(rules);
		
		return "1";
	}	

}

