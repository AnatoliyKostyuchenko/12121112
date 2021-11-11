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

        public static void main(String[] args) throws InterruptedException {
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
                                public synchronized void run() {   while (true)

                                {
                                        String message = null;
                                        try {
                                                message = dataInputStream.readUTF();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
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
                        });t1.start();
                         t1.join();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}