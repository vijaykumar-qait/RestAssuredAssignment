package com.assignment.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class YamlReader {
	private static YamlReader singleInstance = null;
    private Yaml yaml = null;
    private FileInputStream inputStream = null;
    private Map<String, Object> map = null;

    public YamlReader(String path){
        yaml = new Yaml();
        System.out.println(System.getProperty("user.dir"));
        String filePath = System.getProperty("user.dir") + "/Parameters/" + path;
        System.out.println("filePath: " + filePath);
        try {
            inputStream = new FileInputStream(filePath);
            
        }
        catch (IOException e){
            e.printStackTrace();
        }
        map = yaml.load(inputStream);
    }

    public JSONObject getJsonValue(String key){
        return new JSONObject(map.get(key));
    }

    public Map<String, String> getValue(String key){
        Map<String, Object>data = (Map<String, Object>)map.get(key);
        Map<String, String> dataMap = new HashMap<String, String>();
        for(Map.Entry<String, Object> entry : data.entrySet()) {
        	dataMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        System.out.print(dataMap.toString());
        return dataMap;
    }

}
