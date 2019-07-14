package com.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;

import groovy.json.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseTest {

	public static final String PROPERTIES_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/EmployeeProperties.properties";
	public static final String JSON_PATH = System.getProperty("user.dir") + "/src/test/resources/";

	public static Properties getProperties() {
		try {
			System.out.println(System.getProperty("user.dir"));
			InputStream input = new FileInputStream(PROPERTIES_PATH);
			Properties prop = new Properties();
			prop.load(input);
			return prop;
		} catch (Exception e) {
			return null;
		}
	}

	public static String readFile(String filename) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Object parseJson(String jsonString) throws ParseException {
		JSONParser parse = new JSONParser();
		return parse.parse(jsonString);
	}

	public HttpResponse<String> createEmployee()
			throws UnirestException, FileNotFoundException, IOException, ParseException {
		String json = readFile(JSON_PATH + "CreateEmployee.json");
		HttpResponse<String> jsonResponse = Unirest.post(getProperties().getProperty("baseUrl") + "/employees")
				.header("Content-Type", "application/json").header("Accept", "*/*").body(json).asString();
		return jsonResponse;
	}

	public HttpResponse<String> updateEmployee() throws ParseException, UnirestException {
		String json = readFile(JSON_PATH + "UpdateEmployee.json");
		HttpResponse<String> jsonResponse =Unirest.put(getProperties().getProperty("baseUrl") + "/employees/2")
		  .header("Content-Type", "application/json")
		  .header("Accept", "*/*").body(json).asString();
		return jsonResponse;
	}
	
	public HttpResponse<String> getEmployee() throws ParseException, UnirestException {
		
		HttpResponse<String>  jsonResponse= Unirest.get(getProperties().getProperty("baseUrl") + "/employees/2")
				  .header("Content-Type", "application/json")
				  .header("User-Agent", "PostmanRuntime/7.15.0")
				  .header("Accept", "*/*").asString();
               return jsonResponse;
		}
	
	public HttpResponse<String> deleteEmployee() throws ParseException, UnirestException {
		String json = readFile(JSON_PATH + "UpdateEmployee.json");
		HttpResponse<String> jsonResponse =Unirest.delete(getProperties().getProperty("baseUrl") + "/employees/2")
		  .header("Content-Type", "application/json")
		  .header("Accept", "*/*").body(json).asString();
		return jsonResponse;
	}
	
	

}
