package com.muc;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
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
        this.outputStream  = clientSocket.getOutputStream();

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

    public String getLogin() {
        return login;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String passwords = tokens[2];

            if ((login.equals("guest") && passwords.equals("guest")) || (login.equals("juan") && passwords.equals("juan"))  ) {
                String msg = "Ok Login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in successfully:  " + login);


                List<ServerWorker> workerList = server.getWorkerList();
                // send current user all others online logins

                for(ServerWorker worker : workerList) {
                        if (worker.getLogin() != null) {
                            if (!login.equals(worker.getLogin())) {
                                String msg2 = "online " + worker.getLogin() + "\n";
                                send(msg2);
                            }
                        }
                    }


                // send other online users current user´s status
                String onlineMsg = "online " + login + "\n";
                for(ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                    worker.send(onlineMsg);
                    }
                }


            } else {
                String msg = "Error Login\n";
                outputStream.write(msg.getBytes());

            }
        }
    }

    private void send(String msg) throws IOException {
        if (login != null){
            outputStream.write(msg.getBytes());
        }
    }
}
