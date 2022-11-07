package week2.service;

import static week2.XMLCreators.CSVtoArrayList;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import au.com.bytecode.opencsv.CSVReader;
import week2.XMLDoc;
import week2.XMLTransformer;

public class ConverterServiceImpl implements ConverterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterServiceImpl.class);

	@Override
	public void convertToPDF(String inputPath, String outputPath) throws FileNotFoundException {

		LOGGER.info("Pdf Path : " + outputPath);
		/* Step -1 : Read input CSV file in Java */
		CSVReader reader = new CSVReader(new FileReader(inputPath));
		try {
			Document my_pdf_data = new Document();
			Rectangle rc = new Rectangle(2250f, 2500f);
			my_pdf_data.setPageSize(rc);
			PdfWriter.getInstance(my_pdf_data, new FileOutputStream(outputPath));
			my_pdf_data.open();
			List<String[]> list = reader.readAll();
			int size = list.get(0).length;
			PdfPTable my_first_table = new PdfPTable(size);
			PdfPCell table_cell;
			for (String[] strings : list) {
				for (String string : strings) {
					table_cell = new PdfPCell(new Phrase(string));
					my_first_table.addCell(table_cell);
				}
			}
			/* Step -4: Attach table to PDF and close the document */
			my_pdf_data.add(my_first_table);
			my_pdf_data.close();
			LOGGER.info("PDF file generated");
		} catch (DocumentException | IOException e) {
			LOGGER.error("Exception occurs while conversion to PDF and exception is :" + e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void convertToXls(String inputPath, String outputPath) throws FileNotFoundException, IOException {
		LOGGER.info("XLS Path : " + outputPath);
		ArrayList<ArrayList<String>> arList = new ArrayList<>();
		String thisLine;
		FileInputStream fis = new FileInputStream(inputPath);
		DataInputStream myInput = new DataInputStream(fis);
		while ((thisLine = myInput.readLine()) != null) {
			ArrayList<String> al = new ArrayList<>();
			String[] strAr = thisLine.split(",");
			Collections.addAll(al, strAr);
			arList.add(al);
		}
		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList arData = (ArrayList) arList.get(k);
				HSSFRow row = sheet.createRow(k);
				for (int p = 0; p < arData.size(); p++) {
					HSSFCell cell = row.createCell((short) p);
					String data = arData.get(p).toString();
					if (data.startsWith("=")) {
						cell.setCellType(CellType.STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(CellType.STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(CellType.NUMERIC);
						cell.setCellValue(data);
					}
				}
			}
			FileOutputStream fileOut = new FileOutputStream(outputPath);
			hwb.write(fileOut);
			fileOut.close();
			LOGGER.info("XLS file generated");
		} catch (Exception ex) {
			LOGGER.error("Error occurs during XLS conversion" + ex);
		} // main method ends

	}

	@Override
	public void convertToHtml(String inputPath, String outputPath) throws FileNotFoundException, IOException {
		LOGGER.info("HTML Path : " + outputPath);
		// read lines of csv to a string array list
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(inputPath));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			lines.add(currentLine);
		}

		for (int i = 0; i < lines.size(); i++) {
			lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
			lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
		}
		// embrace <table> and </table>
		lines.set(0, "<table border>" + lines.get(0));
		lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");
		// output result
		try (FileWriter writer = new FileWriter(outputPath)) {
			for (String line : lines) {
				writer.write(line + "\n");
			}
		} catch (IOException e) {
			LOGGER.error("Error occurs during XLS conversion" + e);
		}
		LOGGER.info("HTML file generated");
	}

	@Override
	public void convertToXML(String inputPath, String outputPath) throws IOException {
		LOGGER.info("XML Path : " + outputPath);
		String elementName = "element";
		String csvSplit = ",";

		try {
			ArrayList<String[]> elements;
			elements = CSVtoArrayList(inputPath, csvSplit);
			org.w3c.dom.Document xmlDoc;
			xmlDoc = new XMLDoc().docBuilder(elements, elementName);
			XMLTransformer.transformDocToFile(xmlDoc, outputPath);
			LOGGER.info("XML file generated");
		} catch (TransformerException e) {
			LOGGER.info("Transformer error: " + e);
		} catch (ParserConfigurationException e) {
			LOGGER.info("Configuration error: " + e);
		}
	}

	@Override
	public void convertJsonToCSV(JSONArray response, String path) throws IOException {
			JSONArray array = flatData(response);
			LOGGER.info("Csv Path : " + path);
			File file = new File(path);
			String csvString = CDL.toString(array);
			FileUtils.writeStringToFile(file, csvString);
			LOGGER.info("CSV file generated");
	}

	private static JSONArray flatData(JSONArray docs) {
		for (int i = 0; i < docs.length(); i++) {
			JSONObject jsonObj = new JSONObject(docs.get(i).toString());
			String flattenedJson = JsonFlattener.flatten(jsonObj.toString());
			JSONObject formattedJsonObj = new JSONObject(flattenedJson);
			docs.put(i, formattedJsonObj);
		}
		return docs;
	}

}
