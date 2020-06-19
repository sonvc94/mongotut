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
            System.out.println("start");
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(100);
            config.setMinIdle(30);
            config.setMaxIdle(50);
            config.setBlockWhenExhausted(true);
            config.setMaxWaitMillis(6000);
            pool = new GenericObjectPool<>(new MongoPoolFactory(), config);
            System.out.println("idle: " + pool.getNumIdle());
        } catch (Throwable e){
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
            return mongoClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnMongo(MongoClient mongoClient) {
        if (mongoClient != null) {
            pool.returnObject(mongoClient);
        }
    }


}
