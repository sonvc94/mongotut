package vn.vnpay.mongoez;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import vn.vnpay.mongoez.mongo.MongoPool;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        for (int i = 0; i < 1; i++) {
//            executorService.execute(() -> {
//                MongoClient mongoClient = MongoPool.getInstance().borrowMongo();
//                Document doc = new Document("name", "MongoDB")
//                        .append("type", "database")
//                        .append("author", "sonvc")
//                        .append("count", 1)
//                        .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
//                        .append("info", new Document("x", 203).append("y", 102));
//                MongoCollection<Document> collection = mongoClient.getDatabase("mongotut").getCollection("document_test");
//
//                collection.insertOne(doc);
//                MongoPool.getInstance().returnMongo(mongoClient);
//            });
//        }
        MongoClient mongoClient = MongoPool.getInstance().borrowMongo();
        MongoCollection<Document> collection = mongoClient.getDatabase("mongotut").getCollection("document_test");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("author", "sonvc");
        FindIterable findIterable = collection.find(whereQuery);
        Iterator iterator = findIterable.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("CLOSE..........................");
            MongoPool.getInstance().close();
        } ));
    }
}
