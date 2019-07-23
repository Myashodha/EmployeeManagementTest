package com.employee;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
//import org.eclipse.jetty.http.HttpStatus;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EmployeeManagementTest extends BaseTest {

	@Test(priority = 0)
	public void verifyEmployeeCreation() throws UnirestException, FileNotFoundException, IOException, ParseException {
		HttpResponse<String> response = createEmployee();
		AssertJUnit.assertEquals(response.getStatus(), 200);
	
	}
	
	@Test(priority = 1)
	public void checkEmployeeCreationCount()  throws ParseException,UnirestException {
		HttpResponse<JsonNode> response = getEmployeeCount();
        JSONObject responseJson = new JSONObject(response);
        System.out.println(responseJson);
        JSONObject body = responseJson.getJSONObject("body");
        JSONArray empid=body.getJSONArray("array");
        int length = empid.length(); 
        int count=0;
        System.out.println(length);
        for(int i=0; i<length; i++) 
        {
            JSONObject jObj = empid.getJSONObject(i);
            System.out.println(jObj);
            count++;
        }
         System.out.println(count);
       AssertJUnit.assertEquals(count, length);
     }
	
	@Test(priority = 2)
	
	public void checkLatestEmployeeidIsnotNull() throws ParseException, UnirestException 
	{
		 HttpResponse<JsonNode> response = getEmployeeCount();
		JSONObject responseJson = new JSONObject(response);
		 System.out.println(responseJson);
		 JSONObject body = responseJson.getJSONObject("body");
	     JSONArray empid=body.getJSONArray("array");
		 System.out.println(empid);
		 int length=empid.length();
		 JSONObject Obj=empid.getJSONObject(length-1);
	     AssertJUnit.assertNotNull(Obj.get("id"));
	 }
	
	@Test(priority = 3)
	public void updateEmployeeAndValidate() throws ParseException, UnirestException {
		HttpResponse<String> response = updateEmployee();
	   
		
		AssertJUnit.assertEquals(response.getStatus(), 200);
		System.out.println(response.getBody());
		HttpResponse<String> response2 = getEmployee();
		System.out.println(response2.getBody());
		
		AssertJUnit.assertEquals(response.getStatus(), 200);
		String[] empdetails= response2.getBody().split("\"");
		String empname=empdetails[5];
		System.out.println(empname);
		AssertJUnit.assertEquals("swaraj_updated", empname);

		
	}

    @Test(priority = 4)
	public void deleteEmployeeAndValidate() throws ParseException, UnirestException {
		HttpResponse<String> response = deleteEmployee();
		AssertJUnit.assertEquals(response.getStatus(), 200);
		System.out.println(response.getBody());
		 AssertJUnit.assertFalse(response.getBody().contains("swaraj_updated"));
		 HttpResponse<String> response2 = getEmployee();
		 JSONObject responseJson = new JSONObject(response2); 
		 System.out.println(responseJson);
		 String body=responseJson.getString("body");
		 System.out.println(body);
	     AssertJUnit.assertEquals("null", body);
		
			
	}
    

    
	
	
	

	

	
	
}
