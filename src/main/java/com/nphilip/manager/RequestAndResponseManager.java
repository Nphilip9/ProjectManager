package com.nphilip.manager;

import com.nphilip.server.Server;

public class RequestAndResponseManager {
    public void sendNewItemToClient(String jsonData) {
        jsonData = "New Item" + jsonData;
        Server.broadcastMessage(jsonData);
    }
}
