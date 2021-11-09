package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoKlient {
    public final String SERVER_ADDRESS = "localhost";
    public final int SERVER_PORT = 8089;
    public Socket socket;
    public BufferedReader br;
    public DataInputStream ois;
    public DataOutputStream oos;


    public EchoKlient() throws IOException {
        try {
            connection();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
        public

        void connection() throws IOException {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            br = new BufferedReader(new InputStreamReader(System.in));
            ois = new DataInputStream(socket.getInputStream());
            oos = new DataOutputStream(socket.getOutputStream());
            Thread t2= new Thread (new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    sendMessage();
                    String messageFromServer = ois.readUTF();
                    if (messageFromServer.equals("end")) break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }).start();
        }

    public void potoki(){
        try {
            oos.close();
        } catch (Exception ex) {

        }
        try {
            ois.close();
        } catch (Exception ex) {

        }
        try {
            socket.close();
        } catch (Exception ex) {

        }
    }

    public void sendMessage() throws IOException, InterruptedException {
        String clientCommand = br.readLine();
        oos.writeUTF(clientCommand);
        oos.flush();
        System.out.println("Client send message " + clientCommand + " to server.");

    }

    public  void main(String[] args) throws IOException {
        new EchoKlient();
    }
}

