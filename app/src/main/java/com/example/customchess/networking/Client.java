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


    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public boolean start() {
        connect();
        if (socket == null) return false;
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
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
            outputStream.writeUTF(packet);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        String responsePacket = "";
        try {
            responsePacket = inputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsePacket;
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
