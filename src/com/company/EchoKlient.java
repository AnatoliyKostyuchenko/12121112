package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        } prepare();
    }
    private void connection() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String messageFromServer = dataInputStream.readUTF();
                        if (messageFromServer.equals("end")) {
                            break;
                        }
                            textArea.append(messageFromServer);
                        textArea.append("\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    private void closeConnection(){
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
    }
    private void sendMessage(){
        if (TextField.getText().trim().isEmpty()){
            return;
        }
        try{
            dataOutputStream.writeUTF(textField.getText());
            textField.grabFocus();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
public static void prepare(){
    Scanner console = new Scanner(System.in);
    String str = console.nextLine();
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoKlient::new);
    }
}
