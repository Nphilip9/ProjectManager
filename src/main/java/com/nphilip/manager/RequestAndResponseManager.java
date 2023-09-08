package com.nphilip.manager;

import com.nphilip.server.Server;

import java.io.*;

public class RequestAndResponseManager {
    public void sendNewItemToClient(String jsonData) {
        jsonData = "New Item" + jsonData;
        Server.broadcastMessage(jsonData);
    }

    public void handleResponse(String response) {
        sendNewItemToClient(new JSONDataManager().loadJSONStringFromJSONFile());
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
                    // Send requests all one by one
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
