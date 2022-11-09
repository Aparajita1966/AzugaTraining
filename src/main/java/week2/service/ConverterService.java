package week2.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONArray;

public interface ConverterService {

    void convertToPDF(String inputPath, String outputPath) throws FileNotFoundException;

    void convertToXls(String inputPath, String outputPath) throws FileNotFoundException, IOException;

    void convertToHtml(String inputPath, String outputPath) throws FileNotFoundException, IOException;

    void convertToXML(String inputPath, String outputPath) throws IOException;

    void convertJsonToCSV(JSONArray response, String path) throws IOException;

}
