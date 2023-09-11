package com.nphilip.server;

import com.nphilip.manager.JSONDataManager;
import com.nphilip.manager.RequestAndResponseManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 1080;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new RequestAndResponseManager().checkForMessageQueue();

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public boolean isClientConnected() {
        return clients.size() > 0;
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    new RequestAndResponseManager().handleRequests(clientMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
                clients.remove(this);
                System.out.println("Client disconnected: " + socket.getInetAddress());
            }
        }

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }

        public void close() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
