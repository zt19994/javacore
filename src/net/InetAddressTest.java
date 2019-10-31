package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * net 地址测试
 *
 * @author zt1994 2019/10/31 21:38
 */
public class InetAddressTest {

    public static void main(String[] args) throws UnknownHostException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress address : addresses) {
                System.out.println(address);
            }
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
    }
}
