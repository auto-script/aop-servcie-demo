//package cn.mlick.aopservicedemo.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.envision.apim.constants.Constants;
//import com.envision.apim.utils.KryoSerializerUtil;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author lixiangxin
// * @date 2019/4/2 11:03
// */
//public class BatchDeleteRedisTest {
//
//  Set<HostAndPort> jedisClusterNodes = new HashSet<>();
//  JedisCluster jc;
//
//  //  @Before
//  public void init() {
//    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7000));
//    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7001));
//    jedisClusterNodes.add(new HostAndPort("10.27.21.243", 7002));
//
//    jc = new JedisCluster(jedisClusterNodes, 3000, 2);
//  }
//
//  private void del(int p) {
//    del(p, "apim:*");
//  }
//
//  private void del(int p, String key) {
//    Jedis jedis = new Jedis("10.27.21.243", p);
//    Set<String> set = jedis.keys(key);
//
//    System.out.println(set.size());
//    for (String s : set) {
//      System.out.println(s);
//      long r = jc.del(s);
//      System.out.println(r);
//    }
//  }
//
//  //  @Test
//  public void deleteAllApim() {
//    new Thread(
//            new Runnable() {
//              @Override
//              public void run() {
//                del(7000);
//              }
//            })
//        .start();
//    new Thread(
//            new Runnable() {
//              @Override
//              public void run() {
//                del(7001);
//              }
//            })
//        .start();
//
//    new Thread(
//            new Runnable() {
//              @Override
//              public void run() {
//                del(7002);
//              }
//            })
//        .start();
//  }
//
//  public void t1() {
//
//    String key = "apim:" + Constants.REDIS_API_CATALOG + 252;
//    byte[] values = jc.get(key.getBytes());
//
//    JSONObject jsonObject = KryoSerializerUtil.deserializeByClassType(values, JSONObject.class);
//
//    System.out.println(jsonObject.toJSONString());
//  }
//
//  //  @Test
//  public void t2() {
//    del(7000, "apim:token:appKey:*");
//    del(7001, "apim:token:appKey:*");
//    del(7002, "apim:token:appKey:*");
//    del(7000, "apim:token:access:*");
//    del(7001, "apim:token:access:*");
//    del(7002, "apim:token:access:*");
//  }
//}
