package com.employee;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EmployeeManagementTest extends BaseTest {

	@Test(priority = 0)
	public void verifyEmployeeCreation() throws UnirestException, FileNotFoundException, IOException, ParseException {
		HttpResponse<String> response = createEmployee();
		Assert.assertEquals(response.getStatus(), 200);
	
	}

	@Test(priority = 1)
	public void updateEmployeeAndValidate() throws ParseException, UnirestException {
		HttpResponse<String> response = updateEmployee();
		Assert.assertEquals(response.getStatus(), 200);
		System.out.println(response.getBody());
		HttpResponse<String> response2 = getEmployee();
		System.out.println(response2.getBody());
		Assert.assertEquals(response.getStatus(), 200);
		
	//Assert.assertTrue(response.getBody().contains("swaraj_updated"));
		
	}

	@Test(priority = 2)
	public void deleteEmployeeAndValidate() throws ParseException, UnirestException {
		HttpResponse<String> response = deleteEmployee();
		Assert.assertEquals(response.getStatus(), 200);
		System.out.println(response.getBody());
		 Assert.assertFalse(response.getBody().contains("swaraj_updated"));
		 HttpResponse<String> response2 = getEmployee();
		System.out.println(response2.getBody());
			
		
		
		
	}
}
