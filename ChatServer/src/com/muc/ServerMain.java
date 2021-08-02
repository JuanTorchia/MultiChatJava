package com.muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        int port = 8818;
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accept connection from " + clientSocket);
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write("Hello World\n".getBytes());
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
