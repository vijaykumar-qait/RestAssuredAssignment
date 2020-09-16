package com.assignment.utilities;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Restutilities {

	public static String ENDPOINT;
	
	public static Map<String, String> getHeaders() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("Content-Type", "application/json");
		map.put("Accept", "application/json");
		return map;
	}
	
}