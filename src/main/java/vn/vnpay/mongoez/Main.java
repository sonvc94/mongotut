package vn.vnpay.mongoez;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import vn.vnpay.mongoez.mongo.MongoPool;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        String thread = args[0];

        ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(thread));
        for (int i = 0; i < 200_000; i++) {
            executorService.execute(() -> {
                MongoClient mongoClient = MongoPool.getInstance().borrowMongo();
                Document doc = new Document("name", "MongoDB")
                        .append("type", "database")
                        .append("count", 1)
                        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                        .append("info", new Document("x", 203).append("y", 102));
                MongoCollection<Document> collection = mongoClient.getDatabase("mongotut").getCollection("document_test");
                collection.insertOne(doc);
                MongoPool.getInstance().returnMongo(mongoClient);
            });
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("CLOSE..........................");
            MongoPool.getInstance().close();
        } ));
    }

}
