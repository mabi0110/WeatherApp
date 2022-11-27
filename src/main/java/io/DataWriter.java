package io;

import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {

    public void writeDataFromListToFile(String fileName, List<String> list) {
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

    public void writeJsonObjectToFile(String fileName, JSONObject jsonObject) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(jsonObject.toJSONString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Błąd odczytu/zapisu danych z/do pliku: " + fileName);
        }
    }
}
