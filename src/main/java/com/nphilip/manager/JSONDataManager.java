package com.nphilip.manager;

import com.google.gson.Gson;
import com.nphilip.models.ProjectItem;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONDataManager {

    private static final String JSON_FILE_PATH= "C:/users/phili/items.json";

    private final Gson gson = new Gson();

    public void appendItemToJsonFile(ProjectItem projectItem) {
        String jsonData = gson.toJson(projectItem);
        System.out.println(jsonData);
    }

    public ProjectItem loadDataFromJsonFile() {
        try {
            return gson.fromJson(new FileReader(JSON_FILE_PATH), ProjectItem.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
