package vn.vnpay.mongoez.mongo;

import com.mongodb.client.MongoClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class MongoPool {

    private GenericObjectPool<MongoClient> pool;

    public static class SingletonHolder {
        public static final MongoPool instance = new MongoPool();
    }

    public static MongoPool getInstance() {
        return SingletonHolder.instance;
    }

    public synchronized void start() {
        try {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(100);
            config.setMinIdle(10);
            config.setMaxIdle(20);
            config.setBlockWhenExhausted(true);
            config.setMaxWaitMillis(60000);
            pool = new GenericObjectPool<>(new MongoPoolFactory(), config);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public MongoPool() {
    }

    public synchronized MongoClient borrowMongo() {
        if (pool == null) {
            start();
        }
        try {
            MongoClient mongoClient = pool.borrowObject();
            System.out.println("NumActive : " + pool.getNumActive());
            System.out.println("NumIdle: " + pool.getNumIdle());
            return mongoClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void returnMongo(MongoClient mongoClient) {
        if (mongoClient != null) {
            pool.returnObject(mongoClient);
        }
    }

    public synchronized void close() {
        if (pool != null) {
            pool.close();
        }
    }


}
