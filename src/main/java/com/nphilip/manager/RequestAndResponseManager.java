package com.nphilip.manager;

import com.google.gson.Gson;
import com.nphilip.models.ProjectItem;
import com.nphilip.models.RequestType;
import com.nphilip.server.Server;

import java.io.*;

public class RequestAndResponseManager {

    Server server = new Server();

    public void handleRequests(String request) {
        if (request.startsWith(RequestType.GET_JSON_DATA.toString())) {

        } else if (request.startsWith(RequestType.NEW_ITEM_CREATION.toString())) {

        } else if (request.startsWith(RequestType.ITEM_DELETION.toString())) {

        }
    }

    public void createRequest(RequestType requestType) {
        String request = requestType.toString();
        if (server.isClientConnected()) {
            Server.broadcastMessage(request);
        } else {
            handleRequestOnClientOffline(request);
        }
    }

    public void createRequest(RequestType requestType, ProjectItem item) {
        String request = requestType.toString() + new Gson().toJson(item);
        if (server.isClientConnected()) {
            Server.broadcastMessage(request);
        } else {
            handleRequestOnClientOffline(request);
        }
    }

    public void createRequest(String request) {
        if (server.isClientConnected()) {
            Server.broadcastMessage(request);
        } else {
            handleRequestOnClientOffline(request);
        }
    }

    private void handleRequestOnClientOffline(String request) {
        try {
            if (new File("C:/Users/phili/item_request.txt").exists()) {
                FileWriter fileWriter = new FileWriter("C:/Users/phili/item_request.txt", true);
                fileWriter.append(request).append("\n");
                fileWriter.close();
            } else {
                new File("C:/Users/phili/item_request.txt").createNewFile();
                FileWriter fileWriter = new FileWriter("C:/Users/phili/item_request.txt");
                fileWriter.write(request + "\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkForMessageQueue() {
        if (new File("C:/Users/phili/item_request.txt").exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream("C:/Users/phili/item_request.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(RequestType.GET_JSON_DATA.toString())) {
                        createRequest(RequestType.GET_JSON_DATA);
                    } else {
                        createRequest(line);
                    }
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new File("C:/Users/phili/item_request.txt").delete();
        }
    }
}
