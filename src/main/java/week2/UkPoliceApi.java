package week2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UkPoliceApi {

    private static final String FORCES_URL = "https://data.police.uk/api/forces";
    private static final String NEIGHBOUR_URL = "https://data.police.uk/api/leicestershire/neighbourhoods";

    public static void main(String[] args) throws IOException, JSONException {
        getForces();
        getNeighbour();
    }

    private static void getForces() throws IOException, JSONException {
        String response = HttpClient.sendGET(FORCES_URL);
        JSONArray jsonObject = new JSONArray(response);
        JsonToCsv.jsonToCSV(jsonObject, "/Users/aparajita/Documents/data.csv");
    }

    private static void getNeighbour() throws IOException, JSONException {
        String response = HttpClient.sendGET(NEIGHBOUR_URL);
        JSONArray jsonObject = new JSONArray(response);
        JsonToCsv.jsonToCSV(jsonObject, "/Users/aparajita/Documents/neighbour.csv");
    }

//    private static void getSpecificForces() throws IOException, JSONException {
//        String response = sendGET(SPECIFIC_FORCES_URL);
//        JSONObject json = new JSONObject(response);
//        JSONArray jsonarr = json.getJSONArray("engagement_methods");
//
//        jsonToCSV(jsonarr.toString(), "/Users/aparajita/Documents/data1.csv");
//    }



//	public static void convertToJsonFile(String response) {
//		String path = "/Users/aparajita/Documents/response.json";
//		try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
//			out.write(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


}
