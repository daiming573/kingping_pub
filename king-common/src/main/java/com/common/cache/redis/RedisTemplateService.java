package com.common.cache.redis;

import com.common.cache.redis.config.IRedisPropertyService;
import com.common.errorcode.DefaultErrorCode;
import org.checkerframework.checker.units.qual.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于spring和redis的redisTemplate工具类封装
 * 针对所有的map 都是以hm开头的方法
 * 针对所有的Set 都是以s开头的方法
 * 针对所有的List 都是以l开头的方法
 *
 * @author admin
 * 引入泛型
 */
@ConditionalOnExpression("${redis.enable:false}==true")
@Service("redisTemplateService")
public class RedisTemplateService implements IRedisTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(RedisTemplateService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private IRedisPropertyService redisPropertyService;

    private String keyPrefix = null;

    /**
     * 统一设置key的前缀
     *
     * @param key
     * @return
     */
    @Override
    public String setKeyPrefix(String key) {
        if (StringUtils.isEmpty(keyPrefix)) {
            if (redisPropertyService != null) {
                String prefix = redisPropertyService.getKeyPrefix();
                if (!StringUtils.isEmpty(prefix)) {
                    keyPrefix = prefix;
                }
            }
        }

        if (!StringUtils.isEmpty(keyPrefix)) {
            //如果不是以默认前缀开头的，则添加前缀
            if (!key.startsWith(keyPrefix)) {
                key = keyPrefix + ":" + key;
            }
        }

        return key;
    }


    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @Override
    public boolean expire(String key, long time) {
        key = setKeyPrefix(key);
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("expire cache error:", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Override
    public long getExpire(String key) {
        key = setKeyPrefix(key);
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    @Override
    public boolean hasKey(String key) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("check key exist error ", e);
            return false;
        }
    }


    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    @Override
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                this.delete(setKeyPrefix(keys[0]));
            } else {
                List<String> keyList = CollectionUtils.arrayToList(keys);
                List<String> newKeyList = keyList.stream().map(this::setKeyPrefix).collect(Collectors.toList());
                redisTemplate.delete(newKeyList);
            }
        }
    }

    /**
     * 根据前缀删除，如果前缀不是以组件的key前缀开头，则添加组件的key前缀
     * 传空等同于删除该组件前缀的key
     *
     * @param prex redis前缀
     * @return void
     * @author gonghao 2019/1/4 17:29
     * @since 1.0.0
     */
    @Override
    public void deleteByPrex(String prex) {
        String keyPrex = setKeyPrefix(prex);
        if (!StringUtils.isEmpty(keyPrex)) {
            keyPrex = keyPrefix + "*";
        }
        if (!StringUtils.isEmpty(keyPrex)) {
            Set keys = redisTemplate.keys(keyPrex);
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }
    }


    //============================String=============================

    /**
     * mod by fangqin 2019/2/27
     * 底层方法不希望抛出任何异常中断service中方法的执行，连接redis失败就正常返回null
     * <p>
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @Override
    public <T> T get(String key) {
        try {
            ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
            if (!StringUtils.isEmpty(key) && valueOperations != null) {
                return valueOperations.get(setKeyPrefix(key));
            }
        } catch (Exception e) {
            logger.error("get value from cache error", e);
        }
        return null;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    @Override
    public <T> boolean set(String key, T value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForValue().set(setKeyPrefix(key), value);
                return true;
            }
        } catch (Exception e) {
            logger.error("set cache error ", e);
        }
        return false;
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    @Override
    public <T> boolean set(String key, T value, long time) {
        key = setKeyPrefix(key);
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("set cache error", e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    @Override
    public long incr(String key, long delta) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    @Override
    public long decr(String key, long delta) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Object hget(String key, String item) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    @Override
    public Map<Object, Object> hmget(String key) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error(MessageFormat.format("set map cache error key={0}, map={1}", key, map.toString()), e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    @Override
    @Deprecated
    public boolean hmset(String key, Map<String, Object> map, long time) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(MessageFormat.format("set map cache error key={0}, map={1}, time={2}", key, map.toString(), time), e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    @Override
    @Deprecated
    public boolean hset(String key, String item, Object value) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error(MessageFormat.format("set item cache error key={0}, item={1}, value={2}", key, item, value), e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(String key, String item, Object value, long time) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(MessageFormat.format("set item cache error key={0}, item={1}, value={2}, time={3}", key, item, value, time), e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @Override
    public void hdel(String key, Object... item) {
        key = setKeyPrefix(key);
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    @Override
    public boolean hHasKey(String key, String item) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    @Override
    public double hincr(String key, String item, double by) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    @Override
    public double hdecr(String key, String item, double by) {
        key = setKeyPrefix(key);
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    @Override
    public Set<Object> sGet(String key) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error(MessageFormat.format("get cache to Set error key={0}", key), e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    @Override
    public boolean sHasKey(String key, Object value) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error(MessageFormat.format("check Set exist by value error key={0}, value={1}", key, value), e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public long sSet(String key, Object... values) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error(MessageFormat.format("set Set cache error key={0}, values={1}", key, values), e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        key = setKeyPrefix(key);
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            logger.error(MessageFormat.format("set Set cache error key={0}, time={1}, values={2}", key, time, values), e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    @Override
    public long sGetSetSize(String key) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Set size error key={0}", key), e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    @Override
    public long setRemove(String key, Object... values) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            logger.error("remove by value error", e);
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    @Override
    public List<Object> lGet(String key, long start, long end) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("get cache from list error", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    @Override
    public long lGetListSize(String key) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("get list size error", e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    @Override
    public Object lGetIndex(String key, long index) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("get list by index error", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public boolean lSet(String key, Object value) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error("set list cache error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
    public boolean lSet(String key, Object value, long time) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("set list cache error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public boolean lSetList(String key, List<Object> value) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error("set list cache error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
    public boolean lSetList(String key, List<Object> value, long time) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("set list cache error", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    @Override
    public boolean lUpdateByIndex(String key, long index, Object value) {
        key = setKeyPrefix(key);
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error("update list by index error", e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    @Override
    public long lRemove(String key, long count, Object value) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            logger.error("remove from list error", e);
            return 0;
        }
    }

    /**
     * 从队列移除第一个元素并返回
     * @param key 键
     * @return 返回移除的元素
     */
    @Override
    public Object lLeftPop(String key) {
        key = setKeyPrefix(key);
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            logger.error("left pop list error key", e);
        }
        return null;
    }

    /**
     * 删除多个key
     *
     * @param keys
     * @return
     */
    @Override
    public boolean remove(String... keys) {
        try {
            if (keys != null && keys.length > 0) {
                redisTemplate.delete(Arrays.stream(keys).map(this::setKeyPrefix).collect(Collectors.toList()));
            }
            return true;
        } catch (Exception e) {
            logger.error("remove from cache error", e);
        }
        return false;
    }

    /**
     * 单个删除，兼容springboot1.5.x和springboot2.x
     *
     * @param key
     * @return
     */
    private boolean delete(final String key){
        try {
            Object obj = null;
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Long success = redisConnection.del(serializer.serialize(key));
                    redisConnection.close();
                    return success;
                }
            });
            Long isSuccess = obj != null ? (Long) obj : 0;
            return isSuccess > 0;
        }catch (Exception e){
            logger.error("delete error", e);
        }
        return false;
    }

    /**
     * 根据前缀批量删除
     *
     * @param key
     * @return
     */
    @Override
    public boolean batchDelete(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                key = "*";
            }
            String keyPrex = setKeyPrefix(key);

            Set keys = redisTemplate.keys(keyPrex);
            if (!CollectionUtils.isEmpty(keys)) {
                redisTemplate.delete(keys);
            }
            return true;
        } catch (Exception e) {
            logger.error("del from cache error", e);
        }
        return false;
    }

    /**
     * 获取key对应value的类型
     *
     * @param key
     * @return
     */
    @Override
    public String getType(String key) {
        try {
            DataType dataType = redisTemplate.type(setKeyPrefix(key));
            if (dataType != null) {
                return dataType.name();
            }
        } catch (Exception e) {
            logger.error("get type from cache error", e);
        }
        return null;
    }

    /**
     * List相关操作
     *
     * @param key
     */
    @Override
    public <T> List<T> getList(String key) {
        try {
            ListOperations<String, T> listOperations = redisTemplate.opsForList();
            if (!StringUtils.isEmpty(key) && listOperations != null) {
                return listOperations.range(setKeyPrefix(key), 0, -1);
            }
        } catch (Exception e) {
            logger.error("get list from cache error", e);
        }
        return null;
    }

    @Override
    public <T> boolean setList(String key, List<T> value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                String realKey = setKeyPrefix(key);
                redisTemplate.opsForList().rightPushAll(realKey, value);
                return true;
            }
        } catch (Exception e) {
            logger.error("set list to cache error", e);
        }
        return false;
    }

    @Override
    public <T> boolean setList(String key, List<T> value, long time) {
        try {
            if (!StringUtils.isEmpty(key)) {
                String realKey = setKeyPrefix(key);
                redisTemplate.opsForList().rightPushAll(realKey, value);
                if (time > 0) {
                    redisTemplate.expire(realKey, time, TimeUnit.SECONDS);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("set list to cache error", e);
        }
        return false;
    }

    @Override
    public <T> boolean setListItem(String key, T... value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForList().rightPushAll(setKeyPrefix(key), value);
                return true;
            }
        } catch (Exception e) {
            logger.error("set list to cache error", e);
        }
        return false;
    }

    /**
     * Set相关操作
     *
     * @param key
     */
    @Override
    public <T> Set<T> getSet(String key) {
        try {
            SetOperations<String, T> setOperations = redisTemplate.opsForSet();
            if (!StringUtils.isEmpty(key) && setOperations != null) {
                return setOperations.members(setKeyPrefix(key));
            }
        } catch (Exception e) {
            logger.error("get set from cache error", e);
        }
        return null;
    }

    @Override
    public <T> boolean setSet(String key, Set<T> value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForSet().add(setKeyPrefix(key), value.toArray());
                return true;
            }
        } catch (Exception e) {
            logger.error("set Set from cache error", e);
        }
        return false;
    }

    @Override
    public <T> boolean setSet(String key, Set<T> value, long time) {
        try {
            if (!StringUtils.isEmpty(key)) {
                String realKey = setKeyPrefix(key);
                redisTemplate.opsForSet().add(realKey, value.toArray());
                if (time > 0) {
                    redisTemplate.expire(realKey, time, TimeUnit.SECONDS);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("set Set from cache error", e);
        }
        return false;
    }

    @Override
    public boolean setSetItem(String key, Object... value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForSet().add(setKeyPrefix(key), value);
                return true;
            }
        } catch (Exception e) {
            logger.error("set Set from cache error", e);
        }
        return false;
    }

    @Override
    public <T> boolean removeInSet(String key, T... value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForSet().remove(setKeyPrefix(key), value);
                return true;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("remove Set from cache error {0}, {1}", key, value), e);
        }
        return false;
    }

    @Override
    public <T> boolean existInSet(String key, T value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                return redisTemplate.opsForSet().isMember(setKeyPrefix(key), value);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("remove Set from cache error {0}, {1}", key, value), e);
        }
        return false;
    }

    /**
     * Map相关操作
     *
     * @param key
     */
    @Override
    public <K, V> Map<K, V> getMap(String key) {
        try {
            if (!StringUtils.isEmpty(key)) {
                HashOperations<String, K, V> mapOperations = redisTemplate.opsForHash();
                return mapOperations.entries(setKeyPrefix(key));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}", key), e);
        }
        return null;
    }

    @Override
    public <K, V> boolean setMap(String key, Map<K, V> value) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForHash().putAll(setKeyPrefix(key), value);
                return true;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}, value={1}", key, value), e);
        }
        return false;
    }

    @Override
    public <K, V> boolean setMap(String key, Map<K, V> value, long time) {
        try {
            if (!StringUtils.isEmpty(key)) {
                String realKey = setKeyPrefix(key);
                redisTemplate.opsForHash().putAll(realKey, value);
                if (time > 0) {
                    redisTemplate.expire(realKey, time, TimeUnit.SECONDS);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}, value={1}, time={2}", key, value, time), e);
        }
        return false;
    }

    @Override
    public <K, V> V getMapItem(String key, K mapKey) {
        try {
            if (!StringUtils.isEmpty(key)) {
                HashOperations<String, K, V> mapOperation = redisTemplate.opsForHash();
                return mapOperation.get(setKeyPrefix(key), mapKey);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0} mapKey={1}", key, mapKey), e);
        }
        return null;
    }

    @Override
    public <K, V> boolean setMapItem(String key, K mapKey, V value) {
        try {
            if (!StringUtils.isEmpty(key)) {

                redisTemplate.opsForHash().put(setKeyPrefix(key), mapKey, value);
                return true;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}, value={1}", key, value), e);
        }
        return false;
    }

    @Override
    public <K> boolean removeInMap(String key, K... mapKey) {
        try {
            if (!StringUtils.isEmpty(key)) {
                redisTemplate.opsForHash().delete(setKeyPrefix(key), mapKey);
                return true;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}, mapKey={1}", key, mapKey), e);
        }
        return false;
    }

    @Override
    public <K> boolean existsInMap(String key, K mapKey) {
        try {
            if (!StringUtils.isEmpty(key)) {
                return redisTemplate.opsForHash().hasKey(setKeyPrefix(key), mapKey);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get Map from cache error key={0}, mapKey={1}", key, mapKey), e);
        }
        return false;
    }

    @Override
    public Long getListSize(String key) {
        try {
            if (!StringUtils.isEmpty(key)) {
                return redisTemplate.opsForList().size(setKeyPrefix(key));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get list size from cache error key={0}", key), e);
        }
        return 0L;
    }

    @Override
    public Long getSetSize(String key) {
        try {
            if (!StringUtils.isEmpty(key)) {
                return redisTemplate.opsForSet().size(setKeyPrefix(key));
            }
        } catch (Exception e) {
//            logger.error(MessageFormat.format("get set size from cache error key={0}", key), e);
        }
        return 0L;
    }

    @Override
    public Long getMapSize(String key) {
        try {
            if (!StringUtils.isEmpty(key)) {
                return redisTemplate.opsForHash().size(setKeyPrefix(key));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("get map size from cache error key={0}", key), e);
        }
        return 0L;
    }
}
