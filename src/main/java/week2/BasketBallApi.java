package week2;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasketBallApi {

    public static final String CSV = ".csv";
    private static final String BASE_URL = "https://www.balldontlie.io/api/v1";
    private static final String PLAYERS_URL = "/players";
    private static final String TEAMS_URL = "/teams";
    private static final String PATH = "/Users/aparajita/Documents";
    private static final String GAMES_URL = "/games?page=1&per_page=100&";


    public static void main(String[] args) throws JSONException {
        //getPlayersData();
        //getPlayerData(1);
       // getTeamsData();
        getGamesData("","", true);
    }

    public static String getGamesData(String urlAppend, String range, Boolean flag) throws JSONException {
        String response =  HttpClient.sendGET(BASE_URL + GAMES_URL + urlAppend);
        if (null != response) {
            if(flag){
                JSONArray docs = getAllData(response);
                convertGames(docs);
                convertFile(StringUtils.isEmpty(range) ? "/games/games" : "/games/" + range, docs);
            }
        }
        return response;
    }

    public static void getPlayersData() throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL);
        if (null != response) {
            JSONArray docs = getAllData(response);
            convertTeam(docs);
            convertFile("/players/players", docs);
        }
    }

    public static void getTeamsData() throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + TEAMS_URL);
        if (null != response) {
            JSONArray docs = getAllData(response);
            convertFile("/teams/teams", docs);
        }
    }

    public static void getPlayerData(Integer id) throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL + "/" + id);
        if (null != response) {
            JSONArray docs = getUniqueData(response);
            convertTeam(docs);
            convertFile("/player/player", docs);
        }
    }

    private static JSONArray getUniqueData(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = new JSONArray();
        docs.put(jsonObject);
        return docs;
    }

    private static JSONArray getAllData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = jsonObject.getJSONArray("data");
        return docs;
    }

    private static void convertTeam(JSONArray docs) {
        for (int i = 0; i < docs.length(); i++){
            JSONObject jsonObj = new JSONObject(docs.get(i).toString());
            JSONObject teamObj = jsonObj.getJSONObject("team");
            if(null != teamObj){
                String teamStr = teamObj.toString().replaceAll(",",";");
                jsonObj.put("team",teamStr);
                docs.put(i, jsonObj);
            }
        }
    }

    private static void convertGames(JSONArray docs) {
        for (int i = 0; i < docs.length(); i++){
            JSONObject jsonObj = new JSONObject(docs.get(i).toString());
            JSONObject teamObj = jsonObj.getJSONObject("visitor_team");
            JSONObject homeTeamObj = jsonObj.getJSONObject("home_team");
            if(null != teamObj){
                String teamStr = teamObj.toString().replaceAll(",",";");
                jsonObj.put("visitor_team",teamStr);
                docs.put(i, jsonObj);
            }
            if(null != homeTeamObj){
                String teamStr = homeTeamObj.toString().replaceAll(",",";");
                jsonObj.put("home_team",teamStr);
                docs.put(i, jsonObj);
            }
        }
    }

    private static void convertFile(String path, JSONArray docs) {
        JsonToCsv.jsonToCSV(docs, PATH + path + CSV);
        Convertor.convertToPDF(PATH + path + CSV, PATH + path + ".pdf");
        Convertor.convertToXls(PATH + path + CSV, PATH + path + ".xls");
        Convertor.convertToHtml(PATH + path + CSV, PATH + path + ".html");
        Convertor.convertToXML(PATH + path + CSV, PATH + path + ".xml");
    }
}
