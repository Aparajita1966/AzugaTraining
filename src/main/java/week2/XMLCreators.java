package week2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

public class XMLCreators {
    public static void main(String[] args) {

        // Vars Initialization
        String csvFile = "/Users/nidhi/Documents/test/data.csv";
        String xmlFile = "/Users/nidhi/Documents/test/data.xml";
        String elementName = "element";
        String csvSplit = ",";

        try {
            ArrayList<String[]> elements;
            elements = CSVtoArrayList(csvFile, csvSplit);
            Document xmlDoc;
            xmlDoc = new XMLDoc().docBuilder(elements, elementName);
            XMLTransformer.transformDocToFile(xmlDoc, xmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File wasn't found, error: " + e);
        } catch (TransformerException e) {
            System.out.println("Transformer error: " + e);
        } catch (ParserConfigurationException e) {
            System.out.println("Configuration error: " + e);
        }
    }

    public static ArrayList<String[]> CSVtoArrayList(String csvFile, String csvSplit) throws IOException {
        ArrayList<String[]> elements = new ArrayList<String[]>();
        BufferedReader csvReader = null;
        String line;

        try {
            csvReader = new BufferedReader(new FileReader(csvFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while ((line = csvReader.readLine()) != null) {
            String[] nodes = line.split(csvSplit);
            elements.add(nodes);
        }

        return elements;
    }
}

