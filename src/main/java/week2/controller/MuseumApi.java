package week2.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.utility.Convertor;
import week2.utility.HttpClient;
import week2.utility.Utility;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class MuseumApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumApi.class);

    private static final String BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final String DEPARTMENTS_URL = "/departments";
    private static final String OBJECT_URL = "/objects";


    public static void getMuseumDepartmentData() throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + DEPARTMENTS_URL);
        if (null != response) {
            response = response.replace(", ", "; ");
            JSONArray docs = Utility.getAllData(response, "departments");
            Convertor.convertFile(false,"/departments/departments", docs, true, true);
        }
    }

    public static void getMuseumObjects() throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + OBJECT_URL);
        if (null != response) {
            JSONArray objectIds = Utility.getAllData(response, "objectIDs");
            JSONArray docs = new JSONArray();
            for (int i = 41; i < 51; i++) {
                JSONObject resp = getMuseumObjectByIdData(Integer.valueOf(objectIds.get(i).toString()));
                docs.put(resp);
            }
            Convertor.convertFile(false, "/objects/objects", docs, true, true);
        }
    }

    public static JSONObject getMuseumObjectByIdData(Integer id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String response = HttpClient.sendGET(BASE_URL + OBJECT_URL + "/" + id);
        if (null != response) {
            response = response.replace(", ", "; ");
            jsonObject = new JSONObject(response);
        }
        return jsonObject;
    }
}
