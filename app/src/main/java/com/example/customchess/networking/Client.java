package com.example.customchess.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    private Socket socket;
    private String serverAddress;
    private int    port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread inputThread;
    private Thread outputThread;


    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public boolean start() {
        connect();
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket.isConnected();
    }

    public void stop() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String packet) {
        try {
//            while (true) {
                outputStream.writeUTF(packet);
                outputStream.flush();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        try {
            inputStream = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            socket = new Socket(serverAddress, port);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
