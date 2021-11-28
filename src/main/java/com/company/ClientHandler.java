package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;


public class ClientHandler {
    private MyServer server;
    private Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    private String nickname;

    public ClientHandler(MyServer server, Socket socket)  {

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.server = server;
            this.socket = socket;

            new Thread(() ->{
                try{
                  authorization();
                  readMessage();
                }catch(IOException ex){
                    ex.printStackTrace();
                }finally{
                    try {
                        closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика");
        }

    }


    private void authorization() throws IOException {
        while(true){
            long start = System.currentTimeMillis();
            String str = in.readUTF();
            if(str.startsWith(Constants.AUTH_COMMAND)){
                String[] tokens = str.split("\\s+");
                String nickname = server.getAuthService().authorization(tokens[1], tokens[2]);
                //дописать проверку наличия ника в чате
              nickname=nickname;
                if(nickname != null){
                  sendMessage(Constants.AUTH_OK_COMMAND + " " + nickname);
                  server.broadcastMessage(nickname + "Вошёл в чат");
                  server.subscribe(this);//одписаться на обновления
                }else{
                    sendMessage("Неверные логин/пароль");
                }
            }
            long timeWork = System.currentTimeMillis() - start;
            if(timeWork>120000){
                System.out.println("Время ожидания прошло...");
                break;
            }
        }
    }
    public void sendMessage(String message){
        try {
            out.writeUTF(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void readMessage() throws IOException {

            String messageFromClient = in.readUTF();
            System.out.println("Cообщение от" + nickname + " :" + messageFromClient);
            if (messageFromClient.startsWith(Constants.ACTIVE_COMMAND)) {
                server.printActiveClients();
            } else {
                if (messageFromClient.equals(Constants.END_COMMAND)) {
                    break;
                }
                if (messageFromClient.equals(Constants.AUTH_ONE_COMMAND)) {
                    server.broadcastMessageToOneClient(messageFromClient, nickname);
                }
                server.broadcastMessage(messageFromClient);//распрострнаитель сообщений
            }
        }
    }
    private void closeConnection() throws IOException {
        server.unsubscribe(this);
        server.broadcastMessage(nickname + "вышел из чата");
        try{
            in.close();
        }catch(IOException ex){

        }
        try{
           out.close();
        }catch(IOException ex){

        }
        try{
            socket.close();
        }catch (IOException ex){

        }
    }

    public String getNickname() {
        return nickname;
    }

}
