package com.company;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyServer {
    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    public DataInputStream in;
    private AuthService authService;
    private List<ClientHandler> clients;


    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(Constants.SERVER_PORT)) {
            authService = new Base();
            authService.start();

            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключение");
                Socket socket = server.accept(); //блокировка сервера
                System.out.println("Подключение произошло");
                new ClientHandler(this, socket);
            }
        } catch (IOException ex) {
            System.out.println("Ошибка сервера");
            ex.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public void broadcastMessage(String message) throws IOException {
        executorService.execute(() -> {
            for (ClientHandler client : clients) {
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribe(ClientHandler client) {
        executorService.execute(() -> clients.add(client));
    }

    public void unsubscribe(ClientHandler client) {

        executorService.execute(() -> clients.remove(client));
    }

    public void broadcastMessageToOneClient(String message, String nickname) throws IOException {
        executorService.execute(() -> {
            for (ClientHandler client : clients) {
                if (client.getNickname().equals(nickname)) {
                    try {
                        client.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void printActiveClients() {
        StringBuilder sb = new StringBuilder(Constants.ACTIVE_COMMAND).append(" ");
        executorService.execute(() -> {
            for (ClientHandler clientHandler : clients) {
                sb.append(clientHandler.getNickname()).append(" ");
            }
            System.out.println(sb.toString());;
        });


    }
}
