package com.nphilip.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nphilip.models.ProjectItem;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

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
        ArrayList<ProjectItem> existingProjectItems;
        if (loadItemsFromJsonFile() != null) {
            existingProjectItems = loadItemsFromJsonFile();
        } else {
            existingProjectItems = new ArrayList<>();
        }
        existingProjectItems.add(projectItem);
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(gson.toJson(existingProjectItems));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ProjectItem> loadItemsFromJsonFile() {
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
        ArrayList<ProjectItem> projectItems = loadItemsFromJsonFile();
        ArrayList<ProjectItem> updatedItems = new ArrayList<>();

        for (ProjectItem projectItem : projectItems) {
            if (!Objects.equals(projectItem.getSubtitle(), item.getSubtitle())) {
                updatedItems.add(projectItem);
            }
        }

        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(gson.toJson(updatedItems));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadJSONStringFromJSONFile() {
        StringBuilder fileContent = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(JSON_FILE_PATH);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }
}
