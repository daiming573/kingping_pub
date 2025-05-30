package com.common.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisTemplateService {

    /**
     * 设置键前缀
     *
     * @param var1
     */
    String setKeyPrefix(String var1);

    <T> T get(String key);

    <T> boolean set(String key, T value);

    <T> boolean set(String key, T value, long time);

    /**
     * 删除多个key
     *
     * @param key
     * @return
     */
    boolean remove(String... key);

    /**
     * 根据前缀批量删除，例如"SERVICES*"会删除所有以keyPrefix:SERVICES开头的key
     *
     * @param key
     * @return
     */
    boolean batchDelete(String key);

    /**
     * 为键设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    boolean expire(String key, long time);

    /**
     * 根据key获取过期时间
     *
     * @param key
     * @return
     */
    long getExpire(String key);

    /**
     * 获取key对应value的类型
     *
     * @param key
     * @return
     */
    String getType(String key);

    /**
     * List相关操作
     */
    <T> List<T> getList(String key);

    <T> boolean setList(String key, List<T> value);

    <T> boolean setList(String key, List<T> value, long time);

    <T> boolean setListItem(String key, T... value);

    Long getListSize(String key);

    /**
     * Set相关操作
     */
    <T> Set<T> getSet(String key);

    <T> boolean setSet(String key, Set<T> value);

    <T> boolean setSet(String key, Set<T> value, long time);

    boolean setSetItem(String key, Object... value);

    <T> boolean removeInSet(String key, T... value);

    <T> boolean existInSet(String key, T value);

    Long getSetSize(String key);

    /**
     * Map相关操作
     */
    <K, V> Map<K, V> getMap(String key);

    <K, V> boolean setMap(String key, Map<K, V> value);

    <K, V> boolean setMap(String key, Map<K, V> value, long time);

    <K, V> V getMapItem(String key, K mapKey);

    <K, V> boolean setMapItem(String key, K mapKey, V value);

    <K> boolean removeInMap(String key, K... mapKey);

    <K> boolean existsInMap(String key, K mapKey);

    Long getMapSize(String key);

    boolean hasKey(String key);

    @Deprecated
    void del(String... keys);

    @Deprecated
    void deleteByPrex(String prex);

    long incr(String key, long delta);

    long decr(String key, long delta);

    @Deprecated
    Object hget(String key, String item);

    @Deprecated
    Map<Object, Object> hmget(String key);

    @Deprecated
    boolean hmset(String key, Map<String, Object> map);

    @Deprecated
    boolean hmset(String key, Map<String, Object> map, long time);

    @Deprecated
    boolean hset(String key, String item, Object value);

    @Deprecated
    boolean hset(String key, String item, Object value, long time);

    @Deprecated
    void hdel(String key, Object... item);

    boolean hHasKey(String key, String item);

    double hincr(String key, String item, double by);

    double hdecr(String key, String item, double by);

    Set<Object> sGet(String key);

    boolean sHasKey(String key, Object value);

    long sSet(String key, Object... values);

    long sSetAndTime(String key, long time, Object... values);

    long sGetSetSize(String key);

    long setRemove(String key, Object... values);

    List<Object> lGet(String key, long start, long end);

    long lGetListSize(String key);

    Object lGetIndex(String key, long index);

    boolean lSet(String key, Object value);

    boolean lSet(String key, Object value, long time);

    boolean lSetList(String key, List<Object> value);

    boolean lSetList(String key, List<Object> value, long time);

    boolean lUpdateByIndex(String key, long index, Object value);

    long lRemove(String key, long count, Object value);

    Object lLeftPop(String key);

}
