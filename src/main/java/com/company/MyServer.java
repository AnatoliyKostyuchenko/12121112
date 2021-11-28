package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    public DataInputStream in;
    private AuthService authService;
    private List<ClientHandler> clients;


    public AuthService getAuthService() {
        return authService;
    }

    public MyServer()  {
        try(ServerSocket server = new ServerSocket(Constants.SERVER_PORT)) {
            authService = new Base();
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
    public synchronized void broadcastMessage(String message) {
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
    public void broadcastMessageToOneClient(String message, String nickname){
       for(ClientHandler client: clients){
           if(  client.getNickname().equals(nickname)){
               client.sendMessage(message);
           }

       }
    }
    public synchronized String printActiveClients(){
 StringBuilder sb = new StringBuilder(Constants.ACTIVE_COMMAND).append(" ");
for(ClientHandler clientHandler: clients){
    sb.append(clientHandler.getNickname()).append(" ");
}
        return sb.toString();
    }


}
