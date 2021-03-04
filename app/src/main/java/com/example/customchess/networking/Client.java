package com.example.customchess.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    private final String serverAddress;
    private final int    port;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


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
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
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

    public void send(ChessNetMovementPacket packet) {
        try {
            outputStream.writeObject(packet);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChessNetPacket receive() {
        ChessNetPacket responsePacket = null;
        try {
            responsePacket = (ChessNetPacket) inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | ClassCastException e) {
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
