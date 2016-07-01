/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import eu.cxn.mema.skelet.IEntity;
import eu.cxn.mema.xlo.Xlo;
import java.util.ArrayList;
import java.util.List;

/**
 * kollekce dokumentu, v jedne databazove kolekci nemusi byt jeden typ zde pro zjednoduseni nacitani
 * objektu...
 *
 * @author kubasek
 * @param <T>
 */
public class MongoCollection<T extends IEntity> implements IMongoCollection<T> {

    // aby nezapomel ;-)
    private MongoConn conn;
    // jmenicko
    private String collection;
    // triiidicka
    private Class<T> cls;

    /**
     * events methodes
     */
    private IMongoPutIn putIn;

    /**
     * with connection parameter
     *
     * @param cls
     * @param conn
     * @param collection
     */
    public MongoCollection(Class<T> cls, MongoConn conn, String collection) {

        this.cls = cls;
        this.conn = conn;
        this.collection = collection;
    }

    @Override
    public void putIn(IMongoPutIn putIn) {
        this.putIn = putIn;
    }

    /**
     * jelikos casto poskytujeme spojeni pres collectionalni spojeni, hodi se mit ho takhle venku
     * pristupny, a odvozovat od neji jiny collectionalni spojenicko ya ?
     *
     * @return
     */
    @Override
    public MongoConn mongoConn() {
        return conn;
    }

    /**
     * nejake ty zpetne infomationen
     */
    @Override
    public String collection() {
        return collection;
    }

    /**
     * ugh !!
     *
     * @param <E>
     * @param cls
     * @param collection
     * @return
     */
    @Override
    public <E extends IEntity> IMongoCollection<E> createMoCoColle(Class<E> cls, String collection) {
        return new MongoCollection(cls, conn, collection);
    }

    /**
     * ugh !!
     *
     * @param <E>
     * @param cls
     * @param collection
     * @return
     */
    @Override
    public <E extends IEntity> IMongoCollection<E> createMoCoColle(Class<E> cls, String collection, boolean losa) {
        IMongoCollection<E> mocolle = createMoCoColle(cls, collection);
        if (losa) {
            mocolle.losaOn();
        }
        return mocolle;
    }

    /**
     * find the object by query ya chci yield!!
     *
     * @param <E>
     * @param query
     * @return
     */
    @Override
    public <E> List<E> find(String query) {
        List<E> res = conn.find(cls, collection, query, fields, new ArrayList<E>());

        if (putIn != null) {
            res = putIn.on(IMongoPutIn.On.LOAD, res);
        }

        fields = null;
        return res;
    }

    /**
     * bajecny text search aka fulltext mainfroind db.reviews.ensureIndex( { columnname : "text" } )
     *
     * @param <E>
     * @param query
     * @param fields
     * @return
     */
    @Override
    public <E> List<E> textSearch(String query, String fields) {
        List<E> res = conn.textSearch(cls, collection, query, fields, new ArrayList<E>());

        if (putIn != null) {
            res = putIn.on(IMongoPutIn.On.LOAD, res);
        }

        fields = null;
        return res;
    }

    /**
     * najde jeden kousek
     *
     * @param <E>
     * @param query
     * @return
     */
    @Override
    public <E> E findOne(String query) {
        E e = conn.findOne(cls, collection, query, fields);

        if (putIn != null && e instanceof MoPro) {
            e = (E) putIn.on(IMongoPutIn.On.LOAD, (MoPro) e);
        }

        fields = null;
        return e;
    }

    /**
     * najde jeden kousek podle guid
     *
     * @param quid
     * @return
     */
    @Override
    public DBObject findGuid(String quid) {
        DBCollection coll = conn.getCollection(collection);
        if (coll != null) {

            try {
                /* kdyz nic nenajde, tak smolik  */
                DBObject dbo = coll.findOne((BasicDBObject) JSON.parse(guidQuery(quid)));
                if (dbo != null) {
                    return dbo;
                }
            } catch (Exception e) {
                Xlo.err("MongoConnCollection.findGuid() : " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    public String guidQuery(String g) {
        return "{ '_id' : { $oid : '" + g + "'}}";
    }

    public static String gQ(String g) {
        return "{ '_id' : { $oid : '" + g + "'}}";
    }

    /**
     * vlozit
     *
     * @param o
     * @return
     */
    @Override
    public boolean insert(T o) {
        if (o instanceof Mobject) {
            o.setMoCo(conn);
            if (losa) {
                ((Mobject) o).onSave();
            }
        }
        /**
         * Putinator
         */
        if (putIn != null && o instanceof IEntity) {
            o = (T) putIn.on(IMongoPutIn.On.INSERT, (MoPro) o);
        }

        return conn.insert(collection, (DBObject) o);
    }

    /**
     * smazat
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(T o) {
        if (putIn != null && o instanceof IEntity) {
            o = (T) putIn.on(IMongoPutIn.On.REMOVE, (MoPro) o);
        }

        return conn.remove(collection, (Mobject) o);
    }

    /**
     * prepsat..
     *
     * @param o
     * @return
     */
    @Override
    public boolean save(T o) {
        if (o instanceof Mobject) {
            o.setMoCo(conn);
            if (losa) {
                ((Mobject) o).onSave();
            }
        }
        if (putIn != null && o instanceof IEntity) {
            o = (T) putIn.on(IMongoPutIn.On.SAVE, (MoPro) o);
        }

        return conn.save(collection, (DBObject) o);
    }

    private String fields = null;

    /**
     * filtr na zobrazovane fields, je globalni pro vsechny findy, vymaze se po jednom pouziti,
     * klasicky mococolle.fields( { neco : 1 }).find( ... );
     */
    @Override
    public IMongoCollection<T> fields(String fields) {
        this.fields = fields;
        return this;
    }

    /**
     * query:
     *
     * > db.taskTest.aggregate( [ { $match: {}}, { $group : { _id : "$name", count : { $sum : 1 } ,
     * average : { $avg : "$consumption" }, total : { $sum : "$consumption" }} }, { $sort : {
     * average : -1 }} ])
     *
     * vysledek:
     *
     * > {
     * "result" : [ { "_id" : "/js/tools/methode.js", "count" : 1, "average" : 26166, "total" :
     * 26166 }, .... { "_id" : "/js/scripts/hlidatko.js", "count" : 531, "average" :
     * 35.54425612052731, "total" : 18874 } ], "ok" : 1 }
     *
     * @param matchQuery
     * @param groupQuery
     * @return
     */
    public Iterable<DBObject> aggregation(String matchQuery, String groupQuery) {
        return conn.aggregate(collection, matchQuery, groupQuery);
    }
}
