package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private AuthService authService;
    private List<ClientHandler> clients;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer()  {
        try(ServerSocket server = new ServerSocket(Constants.SERVER_PORT)) {
            authService = new BaseAuthService();
                    authService.start();

                    clients = new ArrayList<>();
                    while(true) {
                        System.out.println("Сервер ожидает подключение");
                       Socket socket = server.accept(); //блокировка сервера
                        System.out.println("Подключение произошло");
                        new ClientHandler(this,socket);
                    }
        } catch (IOException ex) {
            System.out.println("Ошибка сервера");
            ex.printStackTrace();
        }finally{
            if(authService != null){
             authService.stop();
            }
    }
    }
    public synchronized void broadcastMessage(String message){
        for(ClientHandler client: clients){
            client.sendMessage(message);
        }
    }
    public synchronized void subscribe(ClientHandler client){
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client){
        clients.remove(client);
    }
}
