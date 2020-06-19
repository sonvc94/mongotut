package vn.vnpay.mongoez.mongo;

import com.mongodb.client.MongoClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class MongoPoolFactory extends BasePooledObjectFactory<MongoClient> {

    @Override
    public MongoClient create() throws Exception {
        System.out.println("Create on factory");
        MongoClient mongoClient =  MongoManager.getInstance().getMongoClient();
        return mongoClient;
    }

    @Override
    public PooledObject<MongoClient> wrap(MongoClient t) {
        return new DefaultPooledObject<MongoClient>(t);
    }


    @Override
    public void destroyObject(PooledObject<MongoClient> p) throws Exception {
        System.out.println("close in factory");
        p.getObject().close();
    }
}
