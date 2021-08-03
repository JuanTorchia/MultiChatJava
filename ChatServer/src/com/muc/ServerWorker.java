package com.muc;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private String login = null;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(line);
            String cmd = tokens[0];
            if ("quit".equalsIgnoreCase(cmd)) {
                break;
            } else if ("login".equalsIgnoreCase(cmd)) {
                handleLogin(outputStream, tokens);
                
            } else {
                String msg = "Unknown " + cmd + "\n";
                outputStream.write(msg.getBytes());
            }
        }

        clientSocket.close();
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String passwords = tokens[2];

            if (login.equals("guest") && passwords.equals("guest")) {
                String msg = "Ok Login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in successfully:  " + login);
            } else {
                String msg = "Error Login\n";
                outputStream.write(msg.getBytes());

            }
        }
    }
}
