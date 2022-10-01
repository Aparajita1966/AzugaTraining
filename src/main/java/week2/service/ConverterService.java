package week2.service;

import org.json.JSONArray;

public interface ConverterService {

    void convertToPDF(String inputPath, String outputPath);

    void convertToXls(String inputPath, String outputPath);

    void convertToHtml(String inputPath, String outputPath);

    void convertToXML(String inputPath, String outputPath);

    void convertJsonToCSV(JSONArray response, String path);

}
