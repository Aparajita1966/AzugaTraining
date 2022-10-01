package week2.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.controller.BasketBallApiController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utility {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    public static JSONArray getUniqueData(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = new JSONArray();
        docs.put(jsonObject);
        LOGGER.info("Converted JSON : " + docs);
        return docs;
    }

    public static JSONArray getAllData(String response, String key) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = jsonObject.getJSONArray(key);
        LOGGER.info("Converted JSON : " + docs);
        return docs;
    }

    public static class UnzipFolder {

        private static final Logger LOGGER = LoggerFactory.getLogger(BasketBallApiController.class);
        public static void unZipFolder(String zipFile, String outputFolder) {
            UnzipFolder unZip = new UnzipFolder();
            unZip.unZip(zipFile, outputFolder);
        }

        public void unZip(String zipFile, String outputFolder) {
            byte[] buffer = new byte[1024];
            try {
                // create output directory is not exists
                File folder = new File(outputFolder);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                // get the zip file content
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
                // get the zipped file list entry
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    String fileName = ze.getName();
                    File newFile = new File(outputFolder + File.separator + fileName);
                    System.out.println("file unzip : " + newFile.getAbsoluteFile());
                    // create all non exists folders
                    // else you will hit FileNotFoundException for compressed folder
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    ze = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
                System.out.println("Done");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
