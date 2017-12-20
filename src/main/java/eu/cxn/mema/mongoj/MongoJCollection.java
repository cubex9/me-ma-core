package eu.cxn.mema.mongoj;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    /**
     * get the query of collection
     *
     * @return
     */
    private DBCursor<T> query() {
        return coll().find();
    }

    /**
     * one result by Id -> quid
     *
     * @param id
     * @return
     */
    public T findById(String id) {
        return coll().findOneById(id);
    }

    /**
     * nefiltrovana kompletni kolekce
     */
    public Stream<T> stream() {
        return stream(q -> q);

    }

    /**
     * get stream of results, by the query
     * <p>
     * <pre>
     *     Stream<T> result = stream( q -> q.is("published", true).in("tags", "mongodb", "java", "jackson"));
     * </pre>
     *
     * @param query
     * @return
     */
    public Stream<T> stream(Function<DBCursor<T>, DBCursor<T>> query) {

        /* make the cursor from query */
        DBCursor<T> cursor = query.apply(query());

        /* need spliterator */
        Spliterator<T> s = new Spliterator<T>() {

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (cursor.hasNext()) {
                    action.accept(cursor.next());
                    return true;
                }

                return false;
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return 0;
            }

            @Override
            public int characteristics() {
                return 0;
            }
        };

        /* return stream */
        return StreamSupport.stream(s, false);
    }

    /**
     * vlozeni objektu do kolekce
     *
     * @param t
     * @return
     */
    public T insert(T t) {
        WriteResult<T, String> result = coll().insert(t);

        return result.getSavedObject();
    }

    /**
     * ulozeni existujiciho objektu do kolekce
     */
    public T merge(T t) {
        WriteResult<T, String> result = coll().save(t);

        return result.getSavedObject();
    }


}
