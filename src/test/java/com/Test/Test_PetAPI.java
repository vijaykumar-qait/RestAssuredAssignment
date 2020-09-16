package com.Test;

import com.assignment.constants.EndPoints;
import com.assignment.constants.Path;
import com.assignment.statuses.HelperClass;
import com.assignment.utilities.Restutilities;
import com.assignment.utilities.YamlReader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * This is the PET API class for adding and getting PET details
 */


public class Test_PetAPI {
    public RequestSpecification requestSpecification;
    public YamlReader reader = new YamlReader("PetTestData.yaml");
    HelperClass helperClass = new HelperClass();
    Queue<Map<String, String>> queue = new LinkedList<Map<String, String>>();


    @Test
    public void Test_CreatePetAPI() {
        int rowNum = 1;
        try {
            requestSpecification = RestAssured.given();
            requestSpecification.headers(Restutilities.getHeaders());
            Map<String, String> map = reader.getValue("row"+rowNum);

            /**
             * name: doggie
             *   photoUrls: https://mydogs.com
             *   id: 1
             *   category.id: 1
             *   category.name: labra
             *   tags.id: 1
             *   tags.name: labra
             *   status: available
             */

            String jsonData = "{\n" +
                    "    \"name\": \""+map.get("name")+"\",\n" +
                    "    \"photoUrls\": [\n" +
                    "        \""+map.get("photoUrls")+"\"\n" +
                    "    ],\n" +
                    "    \"id\": \""+map.get("id")+"\",\n" +
                    "    \"category\": {\n" +
                    "        \"id\": \""+map.get("category.id")+"\",\n" +
                    "        \"name\": \""+map.get("category.name")+"\"\n" +
                    "    },\n" +
                    "    \"tags\": [\n" +
                    "        {\n" +
                    "            \"id\": \""+map.get("tags.id")+"\",\n" +
                    "            \"name\": \""+map.get("tags.name")+"\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"status\": \""+map.get("status")+"\"\n" +
                    "}";

            System.out.println();

            JSONObject json = new JSONObject(jsonData);

            Response response = requestSpecification.body(json.toString()).when().post(Path.BASE_URI+ EndPoints.AddUpdate_PET).then().log().
                    all().extract().response();
            helperClass.VerifyStatusCode(response, 200);

            JSONObject responseJson = new JSONObject(response.asString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_GetPetAPI() {
        try {
            requestSpecification = RestAssured.given();
            requestSpecification.headers(Restutilities.getHeaders());
            requestSpecification.queryParam("status", "available");

            Response response = requestSpecification.when().get(Path.BASE_URI+ EndPoints.GET_PET).then().log().
                    all().extract().response();
            helperClass.VerifyStatusCode(response, 200);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
