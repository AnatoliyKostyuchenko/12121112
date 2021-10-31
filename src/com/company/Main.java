package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main{

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(EchoKlient::new);
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8089)){
          System.out.println("Ожидается сообщение");
          socket = serverSocket.accept();
          System.out.println("Сообщение произошло");
            DataInputStream dataInputStream = new DataInputStream((socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while(true){
                String message = dataInputStream.readUTF();
                if(message.equals("end")){
                    dataOutputStream.writeUTF(message);
                    break;
                }
                dataOutputStream.writeUTF(message);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
