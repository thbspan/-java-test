package com.test.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));) {

            Scanner scan = new Scanner(System.in);

            String message = scan.nextLine();
            while (!"bye".equals(message)) {
                writer.write(message + '\n');
                writer.flush();
                System.out.println(reader.readLine());
                message = scan.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
