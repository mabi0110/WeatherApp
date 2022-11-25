import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final String WORLD_LIST_FILE_NAME = "worldcities.csv";
    private static final String JSON_FILE_NAME = "weather.json";

    private static final Double KELWIN_TO_CEL = 273.15;
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        String CONFIG_FILE_NAME = "config.txt";
        String key = "ac5bf29d05cc68c773cc34b1868465b6";
        String city = "London";
        String lat = "";
        String lon = "";
        //String exclude = "hourly";
        String exclude = "daily";

        List<String> list = readDataFromFileToList(WORLD_LIST_FILE_NAME);

        writeDataFromListToFile(CONFIG_FILE_NAME, list);

        Map<String, List<String>> map = readDataFromFileToMap(CONFIG_FILE_NAME);

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            if (entry.getKey().equals(city)) {
                lat = entry.getValue().get(0);
                lon = entry.getValue().get(1);
            }
        }

        String stringUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat
                + "&lon=" + lon
                + "&exclude=" + exclude
                + ",current,minutely,alerts&appid=" + key;


        String weatherJson = getJsonResponse(stringUrl);

        HourlyWeather hourlyWeather = new Gson().fromJson(weatherJson, HourlyWeather.class);

        HourlyWeather.Hourly hour;
        HourlyWeather.Hourly.Rain rain;
        for (int i = 0; i < hourlyWeather.hourly.size(); i++) {
            hour = hourlyWeather.hourly.get(i);

            Timestamp stamp = new Timestamp(hour.dt * 1000);
            Date date = new Date(stamp.getTime());
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formattedDate = f.format(date);

            rain = hour.rain;

            if (rain == null){
                System.out.printf("%19s, %-6.2f unknown,\n", formattedDate, (hour.temp - KELWIN_TO_CEL));
            } else  {
                System.out.printf("%19s, %-6.2f %6f,\n", formattedDate, (hour.temp - KELWIN_TO_CEL), rain.h);
            }
        }



//        Weather weather = new Gson().fromJson(weatherJson, Weather.class);

//        Weather.Daily day;
//        for (int i = 0; i < weather.daily.size(); i++) {
//            day = weather.daily.get(i);
//
//            Timestamp stamp = new Timestamp(day.dt * 1000);
//            Date date = new Date(stamp.getTime());
//            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = f.format(date);
//
//            double avgTemp = (day.temp.morn + day.temp.day + day.temp.eve + day.temp.night) / 4;
//
//            System.out.printf("%10s, %6.2f, %6.2f, %6.2f, %6.2f\n",
//                    formattedDate, (day.temp.min - KELWIN_TO_CEL), (day.temp.max - KELWIN_TO_CEL), (avgTemp - KELWIN_TO_CEL), day.pop * 100);

//        }

    }

    private static String getJsonResponse(String stringUrl) {
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
                scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(informationString));
                writeJsonObjectToFile(JSON_FILE_NAME, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(informationString);
    }

    private static void writeJsonObjectToFile(String fileName, JSONObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(jsonObject.toJSONString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Błąd odczytu/zapisu danych z/do pliku: " + fileName);
        }
    }

    private static Map<String, List<String>> readDataFromFileToMap(String fileName) {
        Map<String, List<String>> map = new HashMap<>();
        File file = new File(fileName);
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (!map.containsKey(data[0])) {
                    map.put(data[0], List.of(data[1], data[2]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nie odnaleziono pliku o podanej nazwie: " + fileName);
        }
        return map;
    }

    private static void writeDataFromListToFile(String fileName, List<String> list) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (int i = 1; i < list.size(); i++) {
                writer.write(list.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu/zapisu danych z/do pliku: " + fileName);
        }
    }

    private static List<String> readDataFromFileToList(String fileName) {
        List<String> list = new ArrayList<>();
        File worldListFile = new File(fileName);
        try {
            scanner = new Scanner(worldListFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String s = data[0] + "," + data[1] + "," + data[2];
                list.add(s);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nie odnaleziono pliku o podanej nazwie: " + fileName);
        }
        return list;
    }
}



