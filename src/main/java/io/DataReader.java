package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataReader {

    private static Scanner scanner = new Scanner(System.in);

    public Map<String, List<String>> readDataFromFileToMap(String fileName) {
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

    public List<String> readDataFromFileToList(String fileName) {
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
