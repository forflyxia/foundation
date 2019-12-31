package cn.tcc.foundation.cache.data.client;

import cn.tcc.foundation.cache.CacheInitializer;
import cn.tcc.foundation.cache.redis.client.AccessLimitClient;
import cn.tcc.foundation.cache.redis.client.RedisAutoRefreshClient;
import cn.tcc.foundation.cache.redis.client.RedisClient;
import cn.tcc.foundation.core.convert.ListTo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RedisClientTest {
    @Test
    public void getFromCache() throws Exception {

        RedisClient<Integer, ProductInfoVo> client = new RedisClient<Integer, ProductInfoVo>("key1", "ProductInfoVo");
        List<Integer> ids = Arrays.asList(1,2,3);
        Map<Integer, ProductInfoVo> map = client.getFromCache(ids, (p)->{ return new ProductInfoService().getProductInfoMap(p); });
    }

    public static void main(String[] args) throws Exception {

        System.setProperty("spring.profiles.active", "dev");

        List<Integer> ids = Arrays.asList(1,2,3);
        String[] array = ListTo.toStringArray(ids);

        CacheInitializer initializer = new CacheInitializer();
        initializer.onStartup();

        RedisClient<Integer, ProductInfoVo> redisClient = new RedisClient<Integer, ProductInfoVo>("key1", "ProductInfoVo");
        RedisClient<Integer, List<ProductInfoVo>> redisListClient = new RedisClient<Integer, List<ProductInfoVo>>("key1", "ProductInfoVoList");
        RedisAutoRefreshClient<Integer, ProductInfoVo> autoRefreshClient = new RedisAutoRefreshClient<Integer, ProductInfoVo>("key1", "ProductInfoVo", 10);

        Map<Integer, List<ProductInfoVo>> listMap = redisListClient.getFromCache(ids, (p)->{ return new ProductInfoService().getProductInfoListMap(p); }, 10*1000);

        redisListClient.removeFromCache(ids);

//        AccessLimitClient accessLimitClient = new AccessLimitClient("key1", 10, 10);
//        accessLimitClient.smoothLimit("test");
//
//        accessLimitClient.smoothLimit("test");

//        Map<Integer, ProductInfoVo> map = client.getFromCache(ids, (p)->{ return new ProductInfoService().getProductInfoMap(p); });
//        System.out.println(MapTo.toString(map));
//
//        Map<Integer, ProductInfoVo> map1 = client.getFromCache(ids, (p)->{ return new ProductInfoService().getProductInfoMap(p); });
//        System.out.println(MapTo.toString(map));
    }
}