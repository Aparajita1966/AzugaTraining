package week2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class BasketBallApi {

    public static final String CSV = ".csv";
    private static final String BASE_URL = "https://www.balldontlie.io/api/v1";
    private static final String PLAYERS_URL = "/players";
    private static final String PATH = "/Users/aparajita/Documents/";


    public static void main (String[] args) throws JSONException {
        getPlayersData();
        getPlayerData(1);
    }

    public static void getPlayersData() throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL);
        if( null != response){
            getAllData(response, "players");
        }
    }

    public static void getPlayerData(Integer id) throws JSONException {
        String response = HttpClient.sendGET(BASE_URL + PLAYERS_URL + "/" +id);
        if( null != response){
            getUniqueData(response, "player.csv");
        }
    }

    private static void getUniqueData(String response, String path) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = new JSONArray();
        array.put(jsonObject);
        JsonToCsv.jsonToCSV(array, PATH + path);
    }

    private static void getAllData(String response, String path) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = jsonObject.getJSONArray("data");
        JsonToCsv.jsonToCSV(docs, PATH + path + CSV);
        Convertor.convertToPDF(PATH + path +CSV, PATH + path + ".pdf");
        Convertor.converToXls(PATH + path +CSV, PATH + path + ".xls");
        Convertor.convertToHtml(PATH + path +CSV, PATH + path + ".html");
    }
}
