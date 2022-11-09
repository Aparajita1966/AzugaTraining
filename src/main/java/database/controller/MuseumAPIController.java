package database.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.service.MuseumServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.wnameless.json.unflattener.JsonUnflattener;

import database.service.MuseumService;
import week2.constant.GeneralConstant;
import week2.controller.MuseumApi;

public class MuseumAPIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumAPIController.class);

    public static void main(String[] args) {
        //addMuseumUsingCSVFile();
        addMuseum(25);
        //getMuseumObjectById(1);
        //updateMuseumObject(1);
        //deleteMuseumObject(437133);
        //getAllMuseumObject(null);
        //getAllMuseumObject("objects");
        //getAllMuseumObject("constituents");
        //getAllMuseumObject("measurements");
        //getAllMuseumObject("tags");
    }

    public static void addMuseum(int objectId) {
        MuseumService museumService = new MuseumServiceImpl();
        try {
            JSONObject jsonObject = MuseumApi.getMuseumObjectByIdData(objectId);
            if (!jsonObject.isEmpty()) {
                museumService.addMuseum(jsonObject);
            } else {
                LOGGER.info("JSON Object is empty for ObjectID: " + objectId);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    public static void getMuseumObjectById(int objectId) {
        MuseumService museumService = new MuseumServiceImpl();
        try {
            JSONObject json = museumService.getMuseum(objectId);

            LOGGER.info("JSON Object for: " + objectId + " and Json Data: " + json);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    public static void getAllMuseumObject(String tableName) {
        MuseumService museumService = new MuseumServiceImpl();
        try {
            List<JSONObject> list = museumService.getMuseumAll(tableName);
            LOGGER.info("All Museum Objects: " + list);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    public static void deleteMuseumObject(int objectId) {

        MuseumService museumService = new MuseumServiceImpl();
        try {
            LOGGER.info("Deleting Data for objectId : " + objectId);
            museumService.deleteMuseumObject(objectId);
            LOGGER.info("Data deleted for objectId : " + objectId);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    public static void updateMuseumObject(int objectId) {
        MuseumService museumService = new MuseumServiceImpl();
        try {
            JSONObject json = createJsonObj(objectId);
            LOGGER.info("Going to update json: " + json + " for object Id : " + objectId);
            museumService.updateMuseumObject(json);
            LOGGER.info("Object ID: " + objectId + " Updated");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    private static JSONObject createJsonObj(int objectId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("objectID", objectId);
        jsonObject.put("isHighlight", true);
        jsonObject.put("accessionNumber", 2903.2);
        jsonObject.put("accessionYear", 2000);
        return jsonObject;
    }

    public static void addMuseumUsingCSVFile() {
        MuseumService museumService = new MuseumServiceImpl();
        try {
            List<JSONObject> jsonList = csvToJson();
            if (!jsonList.isEmpty()) {
                for (JSONObject object : jsonList) {
                    museumService.addMuseum(object);
                }
            } else {
                LOGGER.info("CSV file is empty");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
    }

    public static List<JSONObject> csvToJson() {
        List<JSONObject> jsonList = new ArrayList<>();
        String filePath = "." + File.separator + "." + File.separator + "." + File.separator + "OutputFiles"
                + File.separator;
        File input = new File(filePath + "/objects/objects" + GeneralConstant.CSV);
        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
                    .readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();
            for (Map<?, ?> map : list) {
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<?, ?> pair : map.entrySet()) {
                    jsonObject.put(pair.getKey().toString(), pair.getValue());
                }
                String nestedJson = JsonUnflattener.unflatten(String.valueOf(jsonObject));
                JSONObject new1 = new JSONObject(nestedJson);
                jsonList.add(new1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}
