package eu.cxn.mema.mongoj;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * one collection of mongo-db
 */
public class MongoJCollection<T> {

    private MongoJConn connection;

    private String collection;

    private Class<T> type;

    private JacksonDBCollection<T, String> coll;

    public MongoJCollection(MongoJConn connection, Class<T> type, String collection) {
        this.connection = connection;
        this.collection = collection;
        this.type = type;
    }


    private JacksonDBCollection<T, String> coll() {
        if (coll == null) {
            this.coll = JacksonDBCollection.wrap(connection.getCollection(collection), type, String.class, connection.viewClass());
        }
        return coll;
    }

    public List<T> list() {
        return coll().find().toArray();
    }

    public T findById() {
        return coll().find().curr();
    }

    public DBQuery.Query query() {
        return coll().find();
    }

    /**
     * nalezeni objektu, kde query predstavuje zadany objekt
     *
     * @return
     */
    public synchronized Stream<T> find(DBQuery.Query q) {


        org.mongojack.DBCursor<T> curr = coll.find();


        new Stream.iterate(coll.find(q));

        DBCollection coll = getCollection(collection);
        List<DBObject> out = new ArrayList();

        if (coll != null && query != null) {
            DBCursor cur = query == null ? coll.find() : coll.find((BasicDBObject) JSON.parse(query));
            while (cur.hasNext()) {
                out.add(cur.next());
            }
        }

        return out;

    }
}
