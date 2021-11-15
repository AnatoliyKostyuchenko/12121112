package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static sun.jvm.hotspot.runtime.PerfMemory.start;

//обработчик клиента
public class ClientHandler {
    private MyServer server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;

    public ClientHandler(MyServer server, Socket socket)  {

        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() ->{
                try{
                  authorization();
                  readMessage();
                }catch(IOException ex){
                    ex.printStackTrace();
                }finally{
                  closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика");
        }

    }

    private void authorization() throws IOException {
        while(true){
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
        while(true){
            String messageFromClient = in.readUTF();
            System.out.println("Cообщение от" + nickname + " :" +messageFromClient);
            if (messageFromClient.equals(Constants.END_COMMAND)){
                break;
            }
            server.broadcastMessage(messageFromClient);//распрострнаитель сообщений
        }
    }
    private void closeConnection(){
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
}
