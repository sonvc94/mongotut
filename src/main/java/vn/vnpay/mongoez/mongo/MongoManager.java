package vn.vnpay.mongoez.mongo;

//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoManager {

    private MongoClient mongoClient;
    private MongoDatabase database;

    private static class SingletonHolder {
        private static final MongoManager instance = new MongoManager();
    }

    public static MongoManager getInstance() {
        return SingletonHolder.instance;
    }

    private MongoManager() {
//        mongoClient = MongoClients.create("mongodb://mongotut:mongotut@35.247.136.81:27017/mongotut?sauthSource=authSource");
//        ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder()
//                .maxConnectionIdleTime(20, TimeUnit.SECONDS)
//                .minSize(10)
//                .maxSize(100)
//                .maxWaitQueueSize(10)
//                .build();
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString(""))
//                .applyToConnectionPoolSettings(connectionPoolSettings)
//                .build();
        mongoClient = new MongoClient(
                new ServerAddress("35.247.136.81", 27017),
                MongoClientOptions.builder()
                        .
                        .build());
        database = mongoClient.getDatabase("mongotut");
    }

    private void close() {
        mongoClient.close();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
