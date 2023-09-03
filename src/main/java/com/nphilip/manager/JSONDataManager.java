package com.nphilip.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.models.ProjectItem;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JSONDataManager {

    private static final String JSON_FILE_PATH = "C:/users/phili/items.json";

    private final Gson gson = new Gson();

    public JSONDataManager() {
        if (!new File(JSON_FILE_PATH).exists()) {
            try {
                new File(JSON_FILE_PATH).createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void appendItemToJsonFile(ProjectItem projectItem) {
        ArrayList<ProjectItem> existingProjectItems = loadDataFromJsonFile();
        existingProjectItems.add(projectItem);
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(gson.toJson(existingProjectItems));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProjectItem> loadDataFromJsonFile() {
        ArrayList<ProjectItem> projectItems = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(JSON_FILE_PATH);
            Type listType = new TypeToken<ArrayList<ProjectItem>>() {}.getType();
            projectItems = gson.fromJson(fileReader, listType);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return projectItems;
    }

    public void deleteItemFromJSONFile(ProjectItem item) {

    }
}
