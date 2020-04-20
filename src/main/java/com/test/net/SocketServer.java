package com.test.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));

            String message;
            do {
                message = reader.readLine();
                writer.write("Response:" + message + '\n');
                writer.flush();
            } while (!"bye".equals(message));

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
