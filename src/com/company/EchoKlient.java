package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoKlient extends JFrame {
    public final String SERVER_ADDRESS = "localhost";
    public final int SERVER_PORT = 8089;
    public JTextField textField;
    public JTextArea textArea;
    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;

    public EchoKlient() {

        try {
            EchoKlient
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        dataInputStream ois = new DataInputStream(socket.getInputStream());
        dataOutputStream oos= new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String messageFromServer = dataInputStream.readUTF();
                        if (messageFromServer.equals("end")) {
                            break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        try{
            dataOutputStream.close();
        }catch(Exception ex){

        }
        try{
            dataInputStream.close();
        }catch(Exception ex){

        }
        try{
            socket.close();
        }catch(Exception ex){

        }
        try{
            dataOutputStream.writeUTF(textField.getText());
            textField.grabFocus();
        }catch (Exception ex){
            ex.printStackTrace();
        }

String clientCommand;
    oos.writeUTF(clientCommand);
    oos.flush();
    System.out.println("Client send message " + clientCommand + " to server.");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoKlient::new);
    }
}
