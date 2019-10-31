package net;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Socket 连接测试
 *
 * @author zt1994 2019/10/31 21:29
 */
public class SocketTest {

    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
             Scanner scanner = new Scanner(s.getInputStream(), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        }
    }
}
