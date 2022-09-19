package week2;

import org.apache.commons.io.FileUtils;
import org.json.CDL;

import java.io.File;
import org.json.JSONArray;

public class JsonToCsv {
    public static void jsonToCSV(JSONArray response, String path) {
        try {
            System.out.println("Path : " + path);
            File file = new File(path);
            String csvString = CDL.toString(response);
            FileUtils.writeStringToFile(file, csvString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
