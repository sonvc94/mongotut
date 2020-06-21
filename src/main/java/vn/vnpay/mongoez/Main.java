package vn.vnpay.mongoez;

import com.mongodb.client.MongoClient;
import vn.vnpay.mongoez.mongo.MongoPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 200; i++) {
            executorService.execute(() -> {
                MongoClient mongoClient = MongoPool.getInstance().borrowMongo();
                System.out.println(mongoClient.getDatabase("mongotut").getCollection("Department").countDocuments());
                MongoPool.getInstance().returnMongo(mongoClient);
            });
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> MongoPool.getInstance().close()));
    }
}
