package cn.tcc.foundation.cache.redis.client;


import cn.tcc.foundation.cache.redis.cluster.RedisClusterConfig;
import cn.tcc.foundation.cache.redis.cluster.RedisClusterProvider;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class RedisClientProvider {

    private static final ConcurrentHashMap<String, RedissonClient> REDIS_CLIENT_MAP = new ConcurrentHashMap<>();
    private static final Timer timer = new Timer();

    static {
        timer.schedule(new RedisClientHeartBeatTask(RedisClientProvider.REDIS_CLIENT_MAP), 10 * 1000, 60 * 1000);
    }

    public static RedissonClient getClient(String clusterName) throws Exception {
        RedissonClient client = REDIS_CLIENT_MAP.get(clusterName);
        if (client == null) {
            RedisClusterConfig cluster = RedisClusterProvider.get(clusterName);
            if (cluster == null) {
                throw new Exception("redis config information not exists or not enable.");
            }
            client = createClient(clusterName, cluster);
            if (client != null) {
                REDIS_CLIENT_MAP.putIfAbsent(clusterName, client);
            }
        }
        return client;
    }

    public static boolean removeClient(String clusterName) {
        REDIS_CLIENT_MAP.remove(clusterName);
        return true;
    }

    private static RedissonClient createClient(String clusterName, RedisClusterConfig cluster) {

        Config config = null;
        if (cluster.getClusterType().equalsIgnoreCase("singleserver")) {
            config = cluster.getSingleServerConfig().getRedissionSingleServerConfig();
        }

        if (config != null) {
            if (cluster.getCodec().equalsIgnoreCase("json")) {
                config.setCodec(new JsonJacksonCodec());
            }
            config.setThreads(cluster.getThreads());
            config.setNettyThreads(cluster.getNettyThreads());
            if (cluster.getTransportMode().equalsIgnoreCase("nio")) {
                config.setTransportMode(TransportMode.NIO);
            }
            RedissonClient client = Redisson.create(config);
            return client;
        }
        return null;
    }

    static class RedisClientHeartBeatTask extends TimerTask {

        private ConcurrentHashMap<String, RedissonClient> redisClients = new ConcurrentHashMap();

        RedisClientHeartBeatTask(ConcurrentHashMap<String, RedissonClient> clients) {
            this.redisClients = clients;
        }

        @Override
        public void run() {
            if (!this.redisClients.isEmpty()) {

                for (Map.Entry<String, RedissonClient> entry : redisClients.entrySet()) {
                    boolean heartbeat = entry.getValue().getNodesGroup().pingAll();
                    if (heartbeat) {
                        String message = String.format("RedisClient %s heartbeat successfully.", entry.getKey());
                        System.out.println(message);
                    } else {
                        redisClients.remove(entry.getKey());
                        String message = String.format("RedisClient %s heartbeat failed(removed).", entry.getKey());
                        System.out.println(message);
                    }
                }
            } else {
                System.out.println("There is no redis client exists.");
            }
        }
    }
}
