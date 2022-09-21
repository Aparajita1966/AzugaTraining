package week2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.CellType;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import au.com.bytecode.opencsv.CSVReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static week2.XMLCreators.CSVtoArrayList;


public class Convertor {


        public static void convertToPDF(String inputPath, String outputPath)  {
            try {
            /* Step -1 : Read input CSV file in Java */

            String inputCSVFile = inputPath;
            CSVReader reader = null;
                reader = new CSVReader(new FileReader(inputCSVFile));
            /* Variables to loop through the CSV File
            * for every line in the file */
            /* Step-2: Initialize PDF documents - logical objects */
            Document my_pdf_data = new Document();
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream(outputPath));
            my_pdf_data.open();
            List<String[]> list = reader.readAll();
            int size = list.get(0).length;
            PdfPTable my_first_table = new PdfPTable(size);
            PdfPCell table_cell;
            for (String[] strings : list) {
                String[] nextLine = strings;
                for (int i = 0; i < nextLine.length; i++) {
                    table_cell = new PdfPCell(new Phrase(nextLine[i]));
                    my_first_table.addCell(table_cell);
                }
            }
            /* Step -4: Attach table to PDF and close the document */
            my_pdf_data.add(my_first_table);
            my_pdf_data.close();
            } catch (FileNotFoundException | DocumentException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        public static void convertToHtml(String inputPath, String outputPath) {
            // read lines of csv to a string array list
            List<String> lines = new ArrayList<String>();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    lines.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //embrace <td> and <tr> for lines and columns
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
                e.printStackTrace();
            }
        }

        public static void convertToXls(String inputPath, String outputPath) {
            try {
            ArrayList arList=null;
            ArrayList al=null;
            String fName = inputPath;
            String thisLine;
            int count=0;
            FileInputStream fis = new FileInputStream(fName);
            DataInputStream myInput = new DataInputStream(fis);
            int i=0;
            arList = new ArrayList();
            while (true)
            {
                if (!((thisLine = myInput.readLine()) != null)) break;
                al = new ArrayList();
                String strar[] = thisLine.split(",");
                for(int j=0;j<strar.length;j++)
                {
                    al.add(strar[j]);
                }
                arList.add(al);
                i++;
            }
            try
            {
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                for(int k=0;k<arList.size();k++)
                {
                    ArrayList ardata = (ArrayList)arList.get(k);
                    HSSFRow row = sheet.createRow((short) 0+k);
                    for(int p=0;p<ardata.size();p++)
                    {
                        HSSFCell cell = row.createCell((short) p);
                        String data = ardata.get(p).toString();
                        if(data.startsWith("=")){
                            cell.setCellType(CellType.STRING);
                            data=data.replaceAll("\"", "");
                            data=data.replaceAll("=", "");
                            cell.setCellValue(data);
                        }else if(data.startsWith("\"")){
                            data=data.replaceAll("\"", "");
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(data);
                        }else{
                            data=data.replaceAll("\"", "");
                            cell.setCellType(CellType.NUMERIC);
                            cell.setCellValue(data);
                        }
                    }
                }
                FileOutputStream fileOut = new FileOutputStream(outputPath);
                hwb.write(fileOut);
                fileOut.close();
                System.out.println("Your excel file has been generated");
            } catch ( Exception ex ) {
                ex.printStackTrace();
            } //main method ends
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    public static void convertToXML(String inputPath, String outputPath) {
        String elementName = "element";
        String csvSplit = ",";

        try {
            ArrayList<String[]> elements;
            elements = CSVtoArrayList(inputPath, csvSplit);
            org.w3c.dom.Document xmlDoc;
            xmlDoc = new XMLDoc().docBuilder(elements, elementName);
            XMLTransformer.transformDocToFile(xmlDoc, outputPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File wasn't found, error: " + e);
        } catch (TransformerException e) {
            System.out.println("Transformer error: " + e);
        } catch (ParserConfigurationException e) {
            System.out.println("Configuration error: " + e);
        }
    }
}


