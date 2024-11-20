package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;


public class jsonReader {

    public static String getTestData(String key) throws IOException, ParseException {
        String testdataValue;
         testdataValue = (String) getJsonData().get(key);//key is the input
        return testdataValue;

    }

    public static JSONObject getJsonData() throws IOException, ParseException {

        //pass the path of the testdata.json file
        File filename = new File("resources//TestData/testdata.json");
        //convert json file into string
        String json = FileUtils.readFileToString(filename, "UTF-8");
        //parse the string into object
        //JSONParser parser = new JSONParser();
        //Object obj  = parser.parse(json);
        Object obj = new JSONParser().parse(json);
        //give jsonobject o that I can return it to the function everytime it get called
        JSONObject jsonObject ;

         jsonObject= (JSONObject) obj;
         return jsonObject;

    }

    public static JSONArray getJsonArray(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) throws IOException, ParseException {
        JSONArray languages = getJsonArray(key);
        return languages.get(index);
    }
}
