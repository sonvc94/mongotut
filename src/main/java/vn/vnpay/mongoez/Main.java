package vn.vnpay.mongoez;

import com.mongodb.client.MongoClient;
import vn.vnpay.mongoez.mongo.MongoPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(19);
        for (int i = 0; i < 20; i++) {
            final int Ifinal = i;
            executorService.execute(() -> {
                MongoClient mongoClient = MongoPool.getInstance().borrowMongo();
                System.out.println(mongoClient.getDatabase("mongotut").getCollection("Department").countDocuments());
                MongoPool.getInstance().returnMongo(mongoClient);
            });
        }
        MongoPool.getInstance().borrowMongo().close();
    }
}
