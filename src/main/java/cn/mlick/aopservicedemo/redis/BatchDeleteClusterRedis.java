package cn.mlick.aopservicedemo.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lixiangxin
 * @date 2019/4/2 11:03
 */
public class BatchDeleteClusterRedis {

  Set<HostAndPort> jedisClusterNodes = new HashSet<>();
  JedisCluster jc;

  public void init() {
    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7000));
    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7001));
    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7002));

    jc = new JedisCluster(jedisClusterNodes, 3000, 2);
  }

  private void del(int p) {
    del(p, "apim:*");
  }

  private void del(int p, String key) {
    Jedis jedis = new Jedis("10.27.21.243", p);
    Set<String> set = jedis.keys(key);

    System.out.println(set.size());
    for (String s : set) {
      System.out.println(s);
      long r = jc.del(s);
      System.out.println(r);
    }
  }

  public void deleteAllApim() {
    new Thread(() -> del(7000)).start();
    new Thread(() -> del(7001)).start();
    new Thread(() -> del(7002)).start();
  }

  public static void run() {
    BatchDeleteClusterRedis b = new BatchDeleteClusterRedis();
    b.init();
    b.deleteAllApim();
  }

  public static void main(String[] args) {
    run();
  }
}
