import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {

    private static final String CONFIG_FILE_NAME = "config.txt";
    private static final String WORLD_LIST_FILE_NAME = "worldcities.csv";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        List<String> list = readDataFromFileToList(WORLD_LIST_FILE_NAME);

        writeDataFromListToFile(CONFIG_FILE_NAME, list);

        Map<String, List<String>> map =  readDataFromFileToMap(CONFIG_FILE_NAME);

//        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
//        for (Map.Entry<String, List<String>> entry : entries) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }





        try {

            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=9a3194d55e70fc631f78dd9ac4187ee5");
            //URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=9a3194d55e70fc631f78dd9ac4187ee5");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                System.out.println(informationString);

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);

                System.out.println(array.get(0));

                JSONObject jsonObject = (JSONObject) array.get(0);
                System.out.println();
                System.out.println(jsonObject.get("main"));

            }
        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("Wczytano dane z pliku: " + fileName);

            scanner.close();

        } catch (FileNotFoundException e) {
            System.err.println("Nie odnaleziono pliku o podanej nazwie: " + fileName);
        }

        return map;
    }

    private static void writeDataFromListToFile(String fileName, List<String> list) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter writer = new BufferedWriter(fileWriter))
        {

            for (int i = 1; i < list.size(); i++) {
                writer.write(list.get(i));
                writer.newLine();
            }

            System.out.println("Zapisano dane w pliku: " + fileName);

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
            System.out.println("Wczytano dane z pliku: " + fileName);

            scanner.close();

        } catch (FileNotFoundException e) {
            System.err.println("Nie odnaleziono pliku o podanej nazwie: " + fileName);
        }
        return list;

    }

}



