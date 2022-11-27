package app;

import com.google.gson.Gson;
import exception.LackOfCityException;
import io.Client;
import io.ConsolePrinter;
import io.DataReader;
import io.DataWriter;
import model.DailyWeather;
import model.HourlyWeather;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class WeatherControl {

    private static final String WORLD_LIST_FILE_NAME = "worldcities.csv";

    private static final String JSON_FILE_NAME = "weather.json";

    private static final ConsolePrinter consolePrinter = new ConsolePrinter();

    private static final DataWriter dataWriter = new DataWriter();

    private static final DataReader dataReader = new DataReader();

    private static final Client client = new Client();


    public void control(String key, String configFileName, String exclude, String city) {


        List<String> list = dataReader.readDataFromFileToList(WORLD_LIST_FILE_NAME);
        dataWriter.writeDataFromListToFile(configFileName, list);
        Map<String, List<String>> map = dataReader.readDataFromFileToMap(configFileName);


        String lat = null;
        String lon = null;
        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            if (entry.getKey().equals(city)) {
                lat = entry.getValue().get(0);
                lon = entry.getValue().get(1);
            }
            if(!map.containsKey(city)){
                throw new LackOfCityException("Nie znaleziono miasta o podanej nazwie w bazie");
            }
        }


        String stringUrl = buildUrl(key, exclude, lat, lon);

        String weatherJson = client.getJsonResponse(stringUrl);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(weatherJson);
        } catch (
                ParseException e) {
            throw new RuntimeException(e);
        }
        dataWriter.writeJsonObjectToFile(JSON_FILE_NAME, jsonObject);

        if (Objects.equals(exclude, "daily")) {
            DailyWeather dailyWeather = new Gson().fromJson(weatherJson, DailyWeather.class);
            consolePrinter.printDailyWeather(dailyWeather);
        } else if (Objects.equals(exclude, "hourly")) {
            HourlyWeather hourlyWeather = new Gson().fromJson(weatherJson, HourlyWeather.class);
            consolePrinter.printHourlyWeather(hourlyWeather);
        }

    }

    private String buildUrl(String key, String exclude, String lat, String lon) {
        if ("daily".equals(exclude)){
            exclude = "hourly";
        } else if ("hourly".equals(exclude)){
            exclude = "daily";
        }

        return "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat
                + "&lon=" + lon
                + "&exclude=" + exclude
                + ",current,minutely,alerts&appid=" + key;
    }

}
