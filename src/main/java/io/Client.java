package io;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Client {

    public String getJsonResponse(String stringUrl) {
        StringBuilder informationString = null;
        try {
            URL url = new URL(stringUrl);
            System.out.println("Url: " + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(informationString);
    }
}
