package net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 多线程响应服务器
 * <p>
 * 多线程监听8189并返回响应
 *
 * @author zt1994 2019/11/14 21:38
 */
public class ThreadedEchoServer {

    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8189)) {
            int i = 1;

            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable handler = new ThreadedEchoHandler(incoming);
                Thread thread = new Thread(handler);
                thread.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 多线程响应处理器
     */
    static class ThreadedEchoHandler implements Runnable {

        private Socket incoming;

        public ThreadedEchoHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try (InputStream inputStream = incoming.getInputStream();
                 OutputStream outputStream = incoming.getOutputStream()) {
                Scanner in = new Scanner(inputStream, "UTF-8");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                out.println("Hello! Enter BYE to exit");

                // 客户端响应输出
                boolean done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println("Echo:" + line);
                    if (line.trim().equals("BYE")) {
                        done = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
