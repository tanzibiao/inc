package com.inc.admin.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.util.ClassUtils;

/**
 * @author tzb
 * @desc 缓存工具类
 * @date 2021-06-12 23:19:09
 */
@Slf4j
public class EhCacheUtil {

    private static final String PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"spring-ehcache.xml";
    private static final CacheManager manager = CacheManager.create(PATH);
    public static final String BIZ_CACHE = "bizCache";

    /**
     * 获取Cache类
     * @param cacheName
     * @return
     */
    public Cache getCache(String cacheName) {
        return manager.getCache(cacheName);
    }

    /**
     * 添加缓存数据
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        try {
            Cache cache = manager.getCache(BIZ_CACHE);
            Element element = new Element(key, value);
            cache.put(element);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加缓存失败：{}",e.getMessage());
        }
    }

    /**
     * 获取缓存数据
     * @param key
     * @return
     */
    public static Object get(String key) {
        try {
            Cache cache = manager.getCache(BIZ_CACHE);
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取缓存数据失败：{}",e.getMessage());
            return null;
        }
    }

    /**
     * 删除缓存数据
     * @param key
     */
    public static void delete(String key) {
        try {
            Cache cache = manager.getCache(BIZ_CACHE);
            cache.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除缓存数据失败：{}",e.getMessage());
        }
    }
}
