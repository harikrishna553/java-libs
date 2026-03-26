package com.sample.app.demo1;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class InternalService {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Internal Service running on port 9090...");

        while (true) {
            Socket socket = serverSocket.accept();

            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
            );

            String responseBody = "SECRET: Database password = super-secret";

            String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseBody.length() + "\r\n" +
                "\r\n" +
                responseBody;

            out.write(httpResponse);
            out.flush();
            socket.close();
        }
    }
}