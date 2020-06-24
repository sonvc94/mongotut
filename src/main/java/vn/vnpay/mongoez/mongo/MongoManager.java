package vn.vnpay.mongoez.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoManager {

    private static class SingletonHolder {
        private static final MongoManager instance = new MongoManager();
    }

    public static MongoManager getInstance() {
        return SingletonHolder.instance;
    }

    private MongoManager() {
    }

    public MongoClient createNewConnection() {
        ConnectionString connString = new ConnectionString(
                "mongodb://mongotut:mongotut@35.247.136.81:27017/mongotut"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

}
