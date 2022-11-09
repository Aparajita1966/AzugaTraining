package week2.controller;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.utility.Convertor;
import week2.utility.HttpClient;
import week2.utility.Utility;

import java.io.IOException;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */
public class BasketBallApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketBallApiController.class);
    private static final String BASE_URL = "https://www.balldontlie.io/api/v1";
    private static final String PLAYERS_URL = "/players";
    private static final String TEAMS_URL = "/teams";
    private static final String GAMES_URL = "/games?page=1&per_page=100&";


    public static void basketBallApi(boolean sendIndividualZipFile) throws JSONException {
        LOGGER.info("Calling BasketBall API");
        //getPlayersData(sendIndividualZipFile);
        //getPlayerData(sendIndividualZipFile, 1);
        //getTeamsData(sendIndividualZipFile);
        //getGamesData(sendIndividualZipFile,"", "", true);
    }

    public static String getGamesData(boolean sendIndividualZipFile,String urlAppend, String range, Boolean flag) throws JSONException, IOException {
        String response = HttpClient.sendGET(BASE_URL + GAMES_URL + urlAppend);
        if (null != response) {
            if (flag) {
                JSONArray docs = Utility.getAllData(response, "data");
                Convertor.convertFile(false, StringUtils.isEmpty(range) ? "/games/games" : "/games/" + range, docs, true, true);
            }
        }
        return response;
    }

    public static void getPlayersData(boolean sendIndividualZipFile) throws JSONException, IOException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL);
        LOGGER.debug("Response for getPlayersData method : " + response);
        if (null != response) {
            JSONArray docs = Utility.getAllData(response, "data");
            Convertor.convertFile(false, "/players/players", docs, true, false);
        }
    }

    public static void getTeamsData(boolean sendIndividualZipFile) throws JSONException, IOException {
        String response = HttpClient.sendGET(BASE_URL + TEAMS_URL);
        if (null != response) {
            JSONArray docs = Utility.getAllData(response, "data");
            Convertor.convertFile(false, "/teams/teams", docs, true, true);
        }
    }

    public static void getPlayerData(boolean sendIndividualZipFile, Integer id) throws JSONException, IOException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL + "/" + id);
        if (null != response) {
            JSONArray docs = Utility.getUniqueData(response);
            Convertor.convertFile(false, "/player/player", docs, true, false);
        }
    }
}
