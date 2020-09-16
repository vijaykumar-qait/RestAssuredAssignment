package com.Test;

import com.assignment.statuses.HelperClass;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.assignment.constants.EndPoints;
import com.assignment.constants.Path;
import com.assignment.utilities.Restutilities;
import com.assignment.utilities.YamlReader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * This is the USER API class for getting adding, updating and fetching user details
 */

public class Test_UserAPI {
	public RequestSpecification requestSpecification;
	public YamlReader reader = new YamlReader("UserTestData.yaml");
	HelperClass helperClass = new HelperClass();
	Queue<Map<String, String>> queue = new LinkedList<Map<String, String>>();
	
	
	@Test
	public void Test_CreateUser() {
		int rowNum = 1;
		try {
			requestSpecification = RestAssured.given();
			requestSpecification.headers(Restutilities.getHeaders());
			JSONObject json = new JSONObject(reader.getValue("row"+rowNum));
			
			Response response = requestSpecification.body("["+json.toString()+"]").when().post(Path.BASE_URI+EndPoints.CREATE_MUlTIPLE_USER).then().log().
					all().extract().response();
			helperClass.VerifyStatusCode(response, 200);

			JSONObject responseJson = new JSONObject(response.asString());
			helperClass.VerifyCreateUser(responseJson);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test(dependsOnMethods = "Test_CreateUser")
	public void Test_UpdateUserDetails() {
		int rowNum = 1;
		try {
			requestSpecification = RestAssured.given();
			requestSpecification.headers(Restutilities.getHeaders());
			Map<String, String> map = reader.getValue("row"+rowNum);

			String userName = map.get("username");
			map.put("username", "new"+map.get("username"));
			queue.add(map);
			JSONObject json = new JSONObject(map);

			Response response = requestSpecification.body(json.toString()).when().put(Path.BASE_URI+EndPoints.USER_DETAiLs+userName).then().log().
					all().extract().response();

			helperClass.VerifyStatusCode(response, 200);
			JSONObject responseJson = new JSONObject(response.asString());
			
			System.out.println("responseJson: " + responseJson.toString());
			helperClass.VerifyUpdateUser(responseJson, map);

		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test(dependsOnMethods = "Test_UpdateUserDetails")
	public void Test_GetUserDetails() {
		try {
			requestSpecification = RestAssured.given();
			requestSpecification.headers(Restutilities.getHeaders());
			Map<String, String> map = queue.poll();

			String userName = map.get("username");

			Response response = requestSpecification.when().get(Path.BASE_URI+EndPoints.USER_DETAiLs+userName).then().log().
					all().extract().response();

			helperClass.VerifyStatusCode(response, 200);
			JSONObject responseJson = new JSONObject(response.asString());

			System.out.println("responseJson: " + responseJson.toString());
			helperClass.VerifyGetUser(responseJson, map);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
}