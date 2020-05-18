package cn.mlick.aopservicedemo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * redis缓存
 *
 * @author mlick
 * @modifydata 2018年8月16日10:22:40
 * <p>可以查看官方文档: https://redis.io/commands/set
 */
@Slf4j
public class RedisUtils {

//  private static String host = LionUtil.getInstance().getValue("apim.redis.host");
//
//  private static int port = LionUtil.getInstance().getIntValue("apim.redis.port");
//
//  private static int timeout = LionUtil.getInstance().getIntValue("apim.redis.timeout");
//
//  /** 多个以逗号,隔开 */
//  private static String nodes = LionUtil.getInstance().getValue("apim.redis.nodes");

  private static volatile RedisStrategy redisStrategy;

  public static RedisStrategy getInstance(String host, int port, int timeout, String nodes) {
    if (redisStrategy == null) {
      synchronized (RedisUtils.class) {
        if (redisStrategy == null) {
          if (!StringUtils.isEmpty(nodes)) {
            redisStrategy = new JedisClusterStrategy(host, port, nodes, timeout);
          } else {
            redisStrategy = new JedisStrategy(host, port);
          }
        }
      }
    }
    return redisStrategy;
  }

}
