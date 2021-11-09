package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

        public static final int SERVER_PORT = 8089;

        public static void main(String[] args) {
                Socket socket = null;
                try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                        System.out.println("Ожидается сообщение");
                        socket = serverSocket.accept();
                        System.out.println("Соединение произошло!!!!");
                        DataInputStream dataInputStream = new DataInputStream((socket.getInputStream()));
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        Thread t1 = new Thread(new Runnable() {
                                @Override
                                public void run() {   while (true)

                                {
                                        String message = dataInputStream.readUTF();
                                        if (message.equals("end")) {
                                                break;
                                        }
                                        System.out.println("Klient:" + message);
                                        String messageFromServer = null;
                                        try {
                                                messageFromServer = br.readLine();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                        try {
                                                dataOutputStream.writeUTF(messageFromServer);
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                        System.out.println("Server: " + messageFromServer);
                                }

                                }
                        }).start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}