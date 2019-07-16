package cn.mlick.aopservicedemo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author lixiangxin
 * @date 2019/7/16 16:09
 **/
public class IPUtils {

  public static String getWlanIp() {
    Enumeration<NetworkInterface> nifs;
    try {
      nifs = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e) {
      return "127.0.0.1";
    }
    while (nifs.hasMoreElements()) {
      NetworkInterface nif = nifs.nextElement();

      // 获得与该网络接口绑定的 IP 地址，一般只有一个
      Enumeration<InetAddress> addresses = nif.getInetAddresses();
      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();

        // 只关心 IPv4 地址
        if (addr instanceof Inet4Address && nif.getName().contains("wlan")) {
          System.out.println("网卡接口名称：" + nif.getName());
          System.out.println("网卡接口地址：" + addr.getHostAddress());
          return addr.getHostAddress();
        }
      }
    }
    return "127.0.0.1";
  }
}
