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
            connection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connection() {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream ois = new DataInputStream(socket.getInputStream());
        DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
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
    }

    public static potoki() {
        try {
            DataOutputStream.close();
        } catch (Exception ex) {

        }
        try {
            DataInputStream.close();
        } catch (Exception ex) {

        }
        try {
            socket.close();
        } catch (Exception ex) {

        }
        try {
            dataOutputStream.writeUTF(textField.getText());
            textField.grabFocus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static sendMessage {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream ois = new DataInputStream(socket.getInputStream());
        DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
        String clientCommand;
        oos.writeUTF(clientCommand);
        oos.flush();
        System.out.println("Client send message " + clientCommand + " to server.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoKlient::new);
    }
}
}
