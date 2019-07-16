package cn.mlick.aopservicedemo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static cn.mlick.aopservicedemo.IPUtils.getWlanIp;

/**
 * @author lixiangxin
 * @date 2019/7/15 16:07
 **/

public class Main {
  public static void main(String[] args)
      throws Exception {
//    InetAddress addr = InetAddress.getLocalHost();
//    System.out.println("Local HostAddress:" + addr.getHostAddress());
//    String hostname = addr.getHostName();
//    System.out.println("Local host name: " + hostname);
//
//    System.out.println("本机IP:" + getIpAddress());
//    System.out.println("本机内网IP:" + getIntranetIp());


    // 获得本机的所有网络接口
    System.out.println(getWlanIp());

  }



  public static String getIpAddress() {
    try {
      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
      InetAddress ip = null;
      while (allNetInterfaces.hasMoreElements()) {
        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
          continue;
        } else {
          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
          while (addresses.hasMoreElements()) {
            ip = addresses.nextElement();
            if (ip != null && ip instanceof Inet4Address) {
              return ip.getHostAddress();
            }
          }
        }
      }
    } catch (Exception e) {
      System.err.println("IP地址获取失败" + e.toString());
    }
    return "";
  }

  /**
   * 获取内网ip
   */
  public static String getIntranetIp() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      return null;
    }
  }
}


//---------------------
//  作者：nianbingsihan
//  来源：CSDN
//  原文：https://blog.csdn.net/nianbingsihan/article/details/80265029
//  版权声明：本文为博主原创文章，转载请附上博文链接！
