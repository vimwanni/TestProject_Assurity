package stepDefinitions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import TestRunner.BaseTest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CategoriesSteps extends BaseTest{
	
	String category_id;
	String catalog_pref;
	JSONObject service_Response;
	int responseCode;
	
	
	@Given("^Categories API is under test$")
	public void setBaseURL() throws Throwable {
		testUrl = testUrl + "Categories/";
	}

	
	@When("^category id \"([^\"]*)\" is provided$")
	public void setCategoryId(String argCategoryId) throws Throwable {
		category_id = argCategoryId;
	}

	
	@When("^catalogue parameter is \"([^\"]*)\"$")
	public void setCataloguePreference(String argCatalogPref) throws Throwable {
		catalog_pref = argCatalogPref;
	}

	
	@When("^Category details API is called$")
	public void triggerAPICall() throws Throwable {
		
		String output;
		URL url;
		JSONParser parser;
		BufferedReader br;
		
		testUrl = testUrl + category_id + "/" + "Details.json?catalogue=" + catalog_pref;
		url = new URL(testUrl);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("content-type", "application/json");
		
		responseCode = conn.getResponseCode();
		
		assertTrue("Service call was not successfull. Expected Http Code 200 instead received " + responseCode, responseCode==200);

		parser = new JSONParser();
		br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		output = br.readLine();
		service_Response = (JSONObject) parser.parse(output);
	}

	
	@Then("^API response contains Name as \"([^\"]*)\"$")
	public void verifyResponseForName(String argName) throws Throwable {
		Object response_name = service_Response.get("Name");
	    assertTrue("Response value of NAME doesnt match: \nEXPECTED		- "+argName+"\nACTUAL		- "+response_name.toString(), argName.equals(response_name));
	}

	
	@Then("^API response contains CanRelist as \"([^\"]*)\"$")
	public void verifyResponseForCanRelist(String argCanRelistVal) throws Throwable {
		Object response_CanRelist = service_Response.get("CanRelist");
	    assertTrue("Response value of CanRelist doesn't match: \nEXPECTED	- "+argCanRelistVal+"\nACTUAL		- "+response_CanRelist.toString(), argCanRelistVal.equals(service_Response.get("CanRelist").toString()));
	}
	
	
	@Then("^Promotion element with name \"([^\"]*)\" contains \"([^\"]*)\" in description$")
	public void verifyPromotionDescription(String argPromoName, String argPromoDesc) throws Throwable {
		
		JSONArray arrPromo;
		int noOfPromo;
		
	    arrPromo = (JSONArray) service_Response.get("Promotions");
	    noOfPromo = arrPromo.size();
	    
	    for (int i = 0, size = noOfPromo; i < size; i++)
	    {
	    	JSONObject objectInArray = (JSONObject) arrPromo.get(i);
	    	
	    	if (argPromoName.equals(objectInArray.get("Name"))){
	    		assertTrue("Promotion Description doesn't contains given text: \nEXPECTED	- "+argPromoDesc+"\nACTUAL		- "+objectInArray.get("Description").toString(), objectInArray.get("Description").toString().contains(argPromoDesc));
	    		break;
	    	}

	    	if (noOfPromo-1==i && !argPromoName.equals(objectInArray.get("Name")))
	    		assertTrue("Promotions element NOT found with name: "+argPromoName, false);
	    		
	    }
	}	

}
