package cn.mlick.aopservicedemo.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author lixiangxin
 * @date 2019/4/2 11:03
 */
public class BatchDeleteRedis {

  //  private static String host = LionUtil.getInstance().getValue("apim.redis.host");
  //  private static int port = LionUtil.getInstance().getIntValue("apim.redis.port");

  private static String host = "10.27.20.113";
  private static int port = 6380;

  Jedis jedis;

  public void init() {
    jedis = new Jedis(host, port, 1500);
  }

  private void del() {
    del("apim:*");
  }

  private void del(String key) {
    Set<String> set = jedis.keys(key);

    System.out.println(set.size());

    for (String s : set) {
      System.out.println(s);
      long r = 0;
      try {
        r = jedis.del(s);
      } catch (Exception ignored) {
      }
      System.out.println(r);
    }
  }

  public static void run() {
    BatchDeleteRedis b = new BatchDeleteRedis();
    b.init();
    b.del();
  }

  public static void main(String[] args) {

    BatchDeleteRedis b = new BatchDeleteRedis();
    b.init();
    b.del();
    //    b.del("apim:token:refresh:*");
  }
}
