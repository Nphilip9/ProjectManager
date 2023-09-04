package com.nphilip;

import com.nphilip.UI.AddNewProject;
import com.nphilip.server.Server;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UIThread uiThread = new UIThread();
        uiThread.run();

        ServerThread serverThread = new ServerThread();
        serverThread.run();
    }
}

class UIThread implements Runnable {
    @Override
    public void run() {
        AddNewProject.addNewProject();
    }
}

class ServerThread implements Runnable {
    @Override
    public void run() {
        Server server = new Server();
        server.startServer();
    }
}