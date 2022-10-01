package week2.controller;

import org.json.JSONArray;
import week2.utility.Convertor;
import week2.utility.HttpClient;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class UkPoliceApi {

    private static final String FORCES_URL = "https://data.police.uk/api/forces";
    private static final String NEIGHBOUR_URL = "https://data.police.uk/api/leicestershire/neighbourhoods";

    private static void getForces() {
        String response = HttpClient.sendGET(FORCES_URL);
        if (null != response) {
            JSONArray jsonObject = new JSONArray(response);
            Convertor.convertFile(false,"/forces/forces", jsonObject, true, true);
        }
    }

    private static void getNeighbour() {
        String response = HttpClient.sendGET(NEIGHBOUR_URL);
        if (null != response) {
            JSONArray jsonObject = new JSONArray(response);
            Convertor.convertFile(false, "/neighbour/neighbour", jsonObject, true, true);
        }
    }
}
