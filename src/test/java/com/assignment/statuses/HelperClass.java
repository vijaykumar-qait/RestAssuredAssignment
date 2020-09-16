package com.assignment.statuses;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.Map;

public class HelperClass {

    public void VerifyStatusCode(Response response, int statusCode){
        Assert.assertTrue(response.getStatusCode() == statusCode);
    }

    public void VerifyCreateUser(JSONObject responseJson){
        Assert.assertTrue(String.valueOf(responseJson.get("code")).equals("200"));
        Assert.assertTrue(responseJson.getString("message").equals("ok"));
        Assert.assertTrue(responseJson.getString("type").equals("unknown"));

    }

    public void VerifyGetUser(JSONObject responseJson, Map<String, String> map){
        Assert.assertTrue(String.valueOf(responseJson.get("id")).equals(map.get("id")));
        Assert.assertTrue(String.valueOf(responseJson.get("username")).equals(map.get("username")));
        Assert.assertTrue(String.valueOf(responseJson.get("firstName")).equals(map.get("firstName")));
        Assert.assertTrue(String.valueOf(responseJson.get("lastName")).equals(map.get("lastName")));
        Assert.assertTrue(String.valueOf(responseJson.get("email")).equals(map.get("email")));
        Assert.assertTrue(String.valueOf(responseJson.get("password")).equals(map.get("password")));
        Assert.assertTrue(String.valueOf(responseJson.get("phone")).equals(map.get("phone")));
        Assert.assertTrue(String.valueOf(responseJson.get("userStatus")).equals(map.get("userStatus")));

    }

    public void VerifyUpdateUser(JSONObject responseJson, Map<String, String> map){
        Assert.assertTrue(String.valueOf(responseJson.get("code")).equals("200"));
        Assert.assertTrue(responseJson.getString("message").equals(map.get("id")));
        Assert.assertTrue(responseJson.getString("type").equals("unknown"));
    }
}