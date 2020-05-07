package cn.mlick.aopservicedemo.redis;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author lixiangxin
 * @date 2019/6/6 16:56
 */
public interface RedisStrategy {

    String catalog = "apim";

    default byte[] getKey(String key) {
        return (catalog + ":" + key).getBytes();
    }

    default String getKey(String cg, String key) {
        return catalog + ":" + cg + ":" + key;
    }

    default <T> byte[] parseToBytes(T s) {
        return JSON.toJSONString(s).getBytes(Charset.forName("UTF-8"));
    }

    public <T> Boolean setAddSorted(String key, double dvalue, T svalue);

    public Boolean setHValue(String key, Long id, String value);

    /**
     * https://redis.io/commands/set 设置key为保持字符串value。 如果key已经保存了一个值，则无论其类型如何都会被覆盖。
     * 在成功的SET操作中丢弃与密钥相关联的任何先前时间。
     */
    public <T> Boolean putCacheSet(String key, T obj);

    public Long getAutoIncrement(String key);

    public Set<String> getAllElementsOfSet(String key);

    public Long pushToSet(String key, String element);

    public String getCache(String key);

    public boolean existInSet(String key, String element);

    /**
     * 通过分数删除对应set中的值
     *
     * @param key
     * @param dvalue
     * @return
     */
    public Boolean removeValueByScore(String key, double dvalue);

    /**
     * 精确删除key
     *
     * @param key
     */
    public long deleteCache(String key);

    boolean delHValue(String redisApiOrg, Long id);
}
