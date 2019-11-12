package com.stav.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.stav.miaosha.redis.KeyPrefix;
import com.stav.miaosha.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    RedisConfig redisConfig;

    /**
     *@des 获取当个对象
     *@author stav stavyan@qq.com
     *@blog https://stavtop.club
     *@date 2019/11/11 21:59:56
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }
    /**
     *@des 设置当个对象
     *@author stav stavyan@qq.com
     *@blog https://stavtop.club
     *@date 2019/11/11 22:00:15
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }
    /**
     *@des 判断对象是否存在
     *@author stav stavyan@qq.com
     *@blog https://stavtop.club
     *@date 2019/11/11 22:00:15
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }
    /**
     *@des 增加值
     *@author stav stavyan@qq.com
     *@blog https://stavtop.club
     *@date 2019/11/11 22:01:00
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }
     /**
     *@des 减少值
     *@author stav stavyan@qq.com
     *@blog https://stavtop.club
     *@date 2019/11/11 22:01:00
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clz = value.getClass();
        if (clz == int.class || clz == Integer.class) {
            return "" + value;
        } else if (clz == String.class) {
            return (String) value;
        } else if (clz == long.class || clz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String str, Class<T> clz) {
        if (str == null || str.length() <= 0 || clz == null) {
            return null;
        }
        if (clz == int.class || clz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clz == String.class) {
            return (T) str;
        } else if (clz == long.class || clz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
