package week2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterServiceImplTest {

	ConverterServiceImpl converterService;

	@BeforeEach
	void setUp() {
		converterService = new ConverterServiceImpl();
	}

	@Test
	void converToHTMLTest() throws IOException {
		converterService.convertToHtml("./././Test_Input/testinput.csv", "./././Test_Input/testinput.html");
		File Actual = new File("./././Test_Input/testinput.html");
		File Expected = new File("./././Test_Output/testinput.html");
		boolean isEqual = FileUtils.contentEquals(Actual, Expected);
		assertEquals(true, isEqual);
	}

	@Test
	void converToHTMLWithWrongInputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToHtml("./././Test_Input/input.csv", "./././Test_Input/testinput.html");
		});
	}
	
	@Test
	void convertToHTMLWithWrongOutputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToXls("./././Test_Input/input.csv", "./././Test_Input/testinput.xls");
		});
	}


	void converToXLSTest() throws IOException {
		converterService.convertToXls("./././Test_Input/testinput.csv", "./././Test_Input/testinput.xls");
		File Actual = new File("./././Test_Input/testinput.xls");
		File Expected = new File("./././Test_Output/testinput.xls");
		boolean isEqual = FileUtils.contentEquals(Actual, Expected);
		assertEquals(true, isEqual);
	}
	
	@Test
	void convertToXlsWithWrongOutputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToXls("./././Test_Input/input.csv", "./././Test_Input/testinput.html");
		});
	}
	
	@Test
	void convertToXLSWithWrongInputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToXls("./././Test_Input/input.csv", "./././Test_Input/testinput.xls");
		});
	}

	@Test
	void converToXMLTest() throws IOException {
		converterService.convertToXML("./././Test_Input/testinput.csv", "./././Test_Input/testinput.xml");
		File Actual = new File("./././Test_Input/testinput.xml");
		File Expected = new File("./././Test_Output/testinput.xml");
		boolean isEqual = FileUtils.contentEquals(Actual, Expected);
		assertEquals(true, isEqual);
	}
	
	@Test
	void convertToXMLWithWrongOutputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToXML("./././Test_Input/input.csv", "./././Test_Input/testinput.html");
		});
	}
	
	@Test
	void convertToXMLWithWrongInputPathTest() {
		assertThrows(IOException.class, () -> {
			converterService.convertToXML("./././Test_Input/input.csv", "./././Test_Input/testinput.xml");
		});
	}
	
	@Test
	void convertToPDFWithWrongInputPathTest() {
		assertThrows(IOException.class, () -> {
			converterService.convertToPDF("./././Test_Input/input.csv", "./././Test_Input/testinput.pdf");
		});
	}

	@Test
	void convertToPDFWithWrongOutputPathTest() {
		assertThrows(FileNotFoundException.class, () -> {
			converterService.convertToPDF("./././Test_Input/input.csv", "./././Test_Input/testinput.html");
		});
	}
	
	@Test
	void converToCSVTest() throws IOException {
		JSONArray response = getJson();
		converterService.convertJsonToCSV(response, "./././Test_Input/testinputcsv.csv");
		File Actual = new File("./././Test_Input/testinputcsv.csv");
		File Expected = new File("./././Test_Output/testinputcsv.csv");
		boolean isEqual = FileUtils.contentEquals(Actual, Expected);
		assertEquals(true, isEqual);
	}
	
	@Test
	void converToCSVWithNullJsonTest() throws IOException {
		JSONArray response = null;
		assertThrows(NullPointerException.class, () -> {
			converterService.convertJsonToCSV(response, "./././Test_Input/testinputcsv.csv");
		});
	}
	
	@Test
	void converToCSVWithWrongExtensionTest() throws IOException {
		JSONArray response = null;
		assertThrows(NullPointerException.class, () -> {
			converterService.convertJsonToCSV(response, "./././Test_Input/testinputwrong.html");
		});
	}
	
	
	private JSONArray getJson() {
		JSONArray response = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("name", "John Doe");
		json.put("score", 98);
		json.put("major", "Agricultural studies");
		json.put("institution", "Harvard University");
		JSONObject json1 = new JSONObject();
		json1.put("name", "Sam Adams");
		json1.put("score", 87);
		json1.put("major", "Biology");
		json1.put("institution", "Dartmouth College");
		JSONObject json2 = new JSONObject();
		json2.put("name", "James Isner");
		json2.put("score", 100);
		json2.put("major", "Computer Science");
		json2.put("institution", "MIT");
		JSONObject json3 = new JSONObject();
		json3.put("name", "George Orwell");
		json3.put("score", 99);
		json3.put("major", "English Literature");
		json3.put("institution", "Eton College");
		JSONObject json4 = new JSONObject();
		json4.put("name", "Patrick Chang");
		json4.put("score", 93);
		json4.put("major", "Computer Science");
		json4.put("institution", "National University of Singapore");
		JSONObject json5 = new JSONObject();
		json5.put("name", "Ajay Babu");
		json5.put("score", 92);
		json5.put("major", "Computer Science");
		json5.put("institution", "National University of Singapore");
		response.put(json);
		response.put(json1);
		response.put(json2);
		response.put(json3);
		response.put(json4);
		response.put(json5);
		return response;
	}

}
