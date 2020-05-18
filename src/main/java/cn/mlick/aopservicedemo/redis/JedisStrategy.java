package cn.mlick.aopservicedemo.redis;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

/**
 * @author lixiangxin
 * @date 2019/6/6 16:54
 */
@Slf4j
public class JedisStrategy implements RedisStrategy {

  private Jedis jedis;

  public JedisStrategy(String host, int port) {
    jedis = new Jedis(host, port);
  }

  @Override
  public <T> Boolean setAddSorted(String key, double dvalue, T svalue) {
    if (StringUtils.isEmpty(key) || Double.isNaN(dvalue)) {
      return false;
    }

    final byte[] bkey = getKey(key);
    final byte[] bvalue = parseToBytes(svalue);
    Boolean r =
        Optional.ofNullable(jedis.zadd(bkey, dvalue, bvalue))
            .map(
                s -> {
                  log.debug("redis zadd ->" + s);
                  return s == 1;
                })
            .orElse(false);

    log.debug("redis setAddSorted->" + r);
    return r;
  }

  @Override
  public Boolean setHValue(String key, Long id, String value) {

    final byte[] field = parseToBytes(id);
    final byte[] fieldValue = parseToBytes(value);
    boolean r =
        Optional.ofNullable(jedis.hset(getKey(key), field, fieldValue))
            .map(
                s -> {
                  log.debug("redis hset->" + s);
                  return true;
                })
            .orElse(false);

    log.debug("redis setHValue->" + r);
    return r;
  }

  /**
   * https://redis.io/commands/set 设置key为保持字符串value。 如果key已经保存了一个值，则无论其类型如何都会被覆盖。
   * 在成功的SET操作中丢弃与密钥相关联的任何先前时间。
   */
  @Override
  public <T> Boolean putCacheSet(String key, T obj) {
    if (obj == null) {
      return false;
    }

    final byte[] bkey = getKey(key);
    final byte[] bvalue = parseToBytes(obj);

    Boolean r =
        Optional.ofNullable(jedis.set(bkey, bvalue))
            .map(
                s -> {
                  log.debug("redis set->" + s);
                  return "ok".equalsIgnoreCase(s);
                })
            .orElse(false);

    log.debug("redis putCacheSet->" + r);
    return r;
  }

  @Override
  public Long getAutoIncrement(String key) {
    return jedis.incr(getKey("autoIncrement", key));
  }

  @Override
  public Set<String> getAllElementsOfSet(String key) {
    Set<String> elements = Sets.newHashSet();
    Set<byte[]> elementBytes =
        Optional.ofNullable(jedis.smembers(getKey(key))).orElse(Sets.newHashSet());
    for (byte[] elementByte : elementBytes) {
      elements.add(new String(elementByte));
    }
    return elements;
  }

  @Override
  public Long pushToSet(String key, String element) {

    final byte[] value = parseToBytes(element);

    return Optional.ofNullable(jedis.sadd(getKey(key), value)).orElse(0L);
  }

  @Override
  public String getCache(String key) {
    final byte[] bkey = getKey(key);
    byte[] bytes = jedis.get(bkey);
    return Optional.ofNullable(bytes)
        .map(s -> new String(bytes, StandardCharsets.UTF_8))
        .orElse("");
  }

  @Override
  public boolean existInSet(String key, String element) {

    final byte[] value = parseToBytes(element);

    return Optional.ofNullable(jedis.sismember(getKey(key), value)).orElse(false);
  }

  /**
   * 通过分数删除对应set中的值
   *
   * @param key
   * @param dvalue
   * @return
   */
  @Override
  public Boolean removeValueByScore(String key, double dvalue) {
    if (StringUtils.isEmpty(key) || Double.isNaN(dvalue)) {
      return false;
    }

    final byte[] bkey = getKey(key);
    Long result = Optional.ofNullable(jedis.zremrangeByScore(bkey, dvalue, dvalue)).orElse(0L);

    return result > 0;
  }

  /**
   * 精确删除key
   *
   * @param key
   */
  @Override
  public long deleteCache(String key) {
    final byte[] bkey = getKey(key);
    return Optional.ofNullable(jedis.del(bkey)).orElse(0L);
  }

  @Override
  public boolean delHValue(String key, Long id) {

    final byte[] field = parseToBytes(id);
    Long result = Optional.ofNullable(jedis.hdel(getKey(key), field)).orElse(0L);
    return Optional.of(result).map(r -> r > 0).orElse(false);
  }
}
