package TestRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import java.net.HttpURLConnection;
import org.json.simple.JSONObject;

public class BaseTest {
	
	public Properties appData;
	InputStream appDataInput;
	
	public HttpURLConnection conn;
	public String testUrl;
	
	public JSONObject response;
	
	public BaseTest(){
		
		appData = new Properties();
		appDataInput = null;
		
		appData = new Properties();
		appDataInput = null;
		
		try{
			appDataInput = new FileInputStream("src/test/resources/properties/application_data.properties");	
			appData.load(appDataInput);
			
			testUrl = appData.getProperty("Api_BaseURL")+appData.getProperty("Api_version")+"/";
		}catch (Exception e){
			System.out.println(e);}
		


	}
}
