package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public static String sendGET(String url)  {
        StringBuffer response = null;
        try {
        response = new StringBuffer();
            System.out.println("Calling URL " + url);
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection conection = null;
        conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            System.out.println("JSON String Result " + response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response != null ? response.toString() : null;
    }
}
