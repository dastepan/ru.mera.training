package org.pizza.service.utilities;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class JsonUtil {


    JsonUtil(){
    }

    public JSONArray readFile(String path) {
        JSONParser parser=new JSONParser();
        JSONObject jsonObject=null;
        try {
            File file = ResourceUtils.getFile(this.getClass().getResource("/"+path));
            FileReader reader = new FileReader(file);
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("FileNotFoundException");
        }
        catch (ParseException e) {
            e.printStackTrace();
            System.out.print("ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("IOException");
        }
        return (JSONArray) jsonObject.get("array");
    }
}
