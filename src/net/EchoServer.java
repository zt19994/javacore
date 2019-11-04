package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 响应服务器
 *
 * @author zt1994 2019/11/4 21:20
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        // 建立服务器socket
        try (ServerSocket s = new ServerSocket(8189)) {
            // 等待客户端连接
            try (Socket incoming = s.accept()){
                InputStream inputStream = incoming.getInputStream();
                OutputStream outputStream = incoming.getOutputStream();

                try (Scanner in = new Scanner(inputStream, "UTF-8")){
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                    out.println("Hello! Enter BYE to exit.");

                    // 响应客服端输入
                    boolean done = false;
                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                        if (line.trim().equals("BYE")) {
                            done = true;
                        }
                    }
                }
            }

        }
    }


}
