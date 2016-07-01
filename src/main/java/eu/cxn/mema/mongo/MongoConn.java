/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.mongo;

import com.mongodb.*;
import com.mongodb.util.*;
import com.sun.xml.internal.ws.util.StringUtils;
import eu.cxn.mema.skelet.IEntity;
import eu.cxn.mema.xlo.Xlo;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MongoDB connector, spis zapamatovani nekterych postupu pri pripojovani a praci s mongem
 *
 * <pre>
 * {@literal MongoConn mog = MongoConn.connect( "localhost", "test",
 * new MongoAuth( "useName", "passws" )
 * );
 *
 * MongoColl coll = mog.db.getCollection( "collectionName" );
 *
 * }
 * </pre>
 *
 *
 * @author kubasek
 */
public class MongoConn {

    /**
     * databazicka
     */
    public DB db;

    /**
     * exit status remember this.. ;-)
     */
    public MongoEnu.ReturnCodes exitStatus = MongoEnu.ReturnCodes.OK;
    protected String exitMessage = null;

    public MongoConn() {
    }

    public MongoEnu.ReturnCodes lastResult() {
        return exitStatus;
    }

    public String lastErrorMessage() {
        return exitMessage;
    }

    /**
     * vytvori spojeni na colleci s urcitym typem, tim se dost zjednodusi vsechny zapisy
     *
     * @param <T>
     * @param cls
     * @param collection
     * @return
     */
    public <T extends IEntity> IMongoCollection<T> createMoCoColle(Class<T> cls, String collection) {
        if (collection != null) {
            return new MongoCollection(cls, this, collection);
        }
        return null;
    }

    /**
     * vytvori spojeni na colleci s urcitym typem, tim se dost zjednodusi vsechny zapisy
     *
     * @param <T>
     * @param cls
     * @param collection
     * @param losa
     * @return
     */
    public <T extends IEntity> IMongoCollection<T> createMoCoColle(Class<T> cls, String collection, boolean losa) {
        IMongoCollection<T> mococolle = new MongoCollection(cls, this, collection);
        return mococolle;
    }

    /**
     * odhlaseni
     */
    public synchronized void close() {
        if (db != null) {
            db.getMongo().close();
        }
    }

    public synchronized boolean isUp() {
        if (db != null) {
            if (exitStatus == MongoEnu.ReturnCodes.OK && db.getMongo().getConnector().isOpen()) {
                return true;
            }
        }

        return false;

    }

    /**
     * host je nutny dycky db jen kdyz ji chceme rovnou otevrit to same pro autentificator
     *
     * @param host
     * @param d
     * @param auth
     * @return
     */
    public static MongoConn connect(String host, String d, MongoAuth auth) {
        MongoConn mog = new MongoConn();
        mog.exitStatus = MongoEnu.ReturnCodes.ERROR;
        try {
            // connection try
            mog.db = Mongo.connect(new DBAddress(host, d));
            // authentisation onli if
            if (mog.db != null && auth != null) {
                mog.db.authenticate(auth.getUser(), auth.getPasswd().toCharArray());
            }

            mog.exitStatus = MongoEnu.ReturnCodes.OK;
        } catch (UnknownHostException | IllegalStateException e) {
            // check and write errors
            if (e instanceof UnknownHostException) {
                mog.exitStatus = MongoEnu.ReturnCodes.EXCEPTION_UNKNOWN_HOST;
            }
            if (e instanceof IllegalStateException) {
                mog.exitStatus = MongoEnu.ReturnCodes.EXCEPTION_ILLEGALSTATE;
            }
            Xlo.err("MongoConn.connect: " + mog.exitStatus.name() + " -> " + e.getMessage());
            mog.exitMessage = e.getMessage();
        }
        return mog;
    }

    /**
     * list databazi ya ?
     */
    public List<String> databaseList() {
        if (db != null) {
            return db.getMongo().getDatabaseNames();
        } else {
            Xlo.err("Can't get list databases, no connect to mongoDb");
            return null;
        }
    }

    /**
     * vynalezeni objektu z db pomoci Json query
     *
     * @param collection
     * @param query
     * @return
     */
    public synchronized List<DBObject> find(String collection, String query) {
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

    /**
     * hleda query v kolekci a vysledek konvertuje na objekty Mo*
     *
     * nejde to tam sice zadat do definice ale T by mel implementovat nebo extendovat E, jinak
     * konverze pri vkladani do kolekce bude padat a to az za behu...
     *
     * funkce tedy vytvari z nalezenych DBObjektu, objekty Mo** a muze vracet Elementy napriklad:
     * List<Box> boxi = find( MoBox.class, "nejakakolekce",
     *
     *
     *
     * @param <T>
     * @param <E>
     * @param c
     * @param collection
     * @param query
     * @param fields
     * @param result
     * @return
     */
    public synchronized <T extends IEntity, E> List<E> find(Class<T> c, String collection, String query, String fields, List<E> result) {
        DBCollection coll = getCollection(collection);
        List<E> out = result == null ? new ArrayList<E>() : result;

        if (coll != null) {
            DBCursor cur;
            if (fields == null) {
                cur = query == null ? coll.find() : coll.find((BasicDBObject) JSON.parse(query));
            } else {
                cur = coll.find((BasicDBObject) JSON.parse(query == null ? "{}" : query), (BasicDBObject) JSON.parse(fields));
            }

            /**
             * nikdy neni null, muze bejt prazdnej..
             */
            while (cur.hasNext()) {
                //out.add((E) IEntity.of(cur.next().toMap()));
            }

        }

        return (List<E>) out;
    }

//    /**
//     * najde podle jmena elementu a listu jeho hodnot seznam vsech objektu v collekci
//     *
//     * @param <T>
//     * @param <E>
//     * @param c
//     * @param collection
//     * @param name
//     * @param inListQuery
//     * @param fields
//     * @param result
//     * @return
//     */
//    public <T extends IEntity, E> List<E> find(Class<T> c, String collection, String name, Linq<String> inListQuery, String fields, List<E> result) {
//        return find(c, collection, QueryBuildHelper.qbInList(name, inListQuery), fields, result);
//    }

    /*
     * najde jednoho :-)
     */
    public DBObject findOne(String collection, String query) {
        DBCollection coll = getCollection(collection);
        if (coll != null && query != null) {
            return coll.findOne((BasicDBObject) JSON.parse(query));
        }
        return null;
    }

    /**
     * spusteni nejakeho commandu nad kolekci, pouziva hlavne fullText search nad kolekci musi byt
     * vytvoreny index: db.collection.ensureIndex( { field: "text" } )
     *
     * @param <T>
     * @param <E>
     * @param c
     * @param collection
     * @param wordsPhrase
     * @param fields
     * @param result
     * @return
     */
    public synchronized <T extends IEntity, E> List<E> textSearch(Class<T> c, String collection, String wordsPhrase, String fields, List<E> result) {
        /**
         * getCollection funguje zaroven jako tester.. + vysledny list nikdy neni null, muze tak
         * maximalne mit 0 prvku
         */
        DBCollection coll = getCollection(collection);
        List<E> out = result == null ? new ArrayList<E>() : result;
        if (coll != null) {

            /**
             * nejdriv jestli vubec neco nebo to rachne ?
             */
            CommandResult cr = db.command(textSearchQueryObject(collection, wordsPhrase, fields));
            if (cr.ok()) {
                /**
                 * posleme list s vysledkama, structura je ponekud vnorena: { "queryDebugString"
                 * : "tfrautenberk||||||", "language" : "english", "results" : [ { "score" :
                 * 0.555, "obj" : { tady teprva el.documento }} ], "stats" : { "nscanned" : 0,
                 * "nscannedObjects" : 0, "n" : 0, "nfound" : 0, "timeMicros" : 7568 }, "ok" : 1
                 */
                for (DBObject o : (List<DBObject>) cr.get("results")) {
                    //out.add((E) IEntity.of(o.toMap()));
                }
            }

        }
        return out;
    }

    /**
     * najde jednoho a konvertuje z DBObject do MoElement's a vraci ts Elements
     *
     * @param <T>
     * @param <E>
     * @param c
     * @param collection
     * @param query
     * @param fields
     * @return
     */
    public synchronized <T extends IEntity, E> E findOne(Class<T> c, String collection, String query, String fields) {
        DBCollection coll = getCollection(collection);
        if (coll != null && query != null) {


            /* kdyz nic nenajde, tak smolik  */
            DBObject dbo;
            if (fields == null) {
                dbo = coll.findOne((BasicDBObject) JSON.parse(query));
            } else {
                dbo = coll.findOne((BasicDBObject) JSON.parse(query), (BasicDBObject) JSON.parse(fields));
            }

            /**
             * jestli jo..
             */
            if (dbo != null) {
                /* zkonvertuj ho do Mo** */
                //return (E) IEntity.of(dbo.toMap());
            }

        }

        return null;
    }

    /**
     * ge
     *
     * @param query
     * @param collection database object serialized into JSON
     * @return
     */
    public List<String> old_find(String collection, String query) {
        List<String> out = new ArrayList();

        DBCollection coll = getCollection(collection);
        if (coll != null && query != null) {
            DBCursor cur = coll.find((BasicDBObject) JSON.parse(query));
            while (cur.hasNext()) {
                out.add(JSON.serialize(cur.next()));
            }
        } else {
            out.add(exitStatus.toString());
        }

        return out;
    }

    /**
     * vraci JSONized objekt nalezeny pomoci oid
     *
     * @param collection
     * @param oid
     * @return
     */
    public synchronized String getById(String collection, String oid) {
        DBCollection coll = getCollection(collection);
        if (coll != null && oid != null) {
            DBObject o = coll.findOne("{ '_id.oid' : '" + oid + "' }");
            if (o != null) {
                return JSON.serialize(o);
            }
        }
        return null;
    }

    /**
     * vraci collection, je osetrena i existence db/connection
     *
     * @param collection
     * @return null kdz neexistuje db nebo collection
     */
    public DBCollection getCollection(String collection) {
        return getCollection(collection, 0);
    }

    /**
     * vraci collection, je osetrena i existence db/connection
     *
     * @param collection
     * @return null kdz neexistuje db nebo collection
     */
    public DBCollection getCollection(String collection, int trycounter) {
        try {
            if (db == null) {
                exitStatus = MongoEnu.ReturnCodes.ERROR_DATABASE_NOT_EXIST;
                return null;
            } else if (db.collectionExists(collection)) {
                exitStatus = MongoEnu.ReturnCodes.OK;
                return db.getCollection(collection);
            }
        } catch (Exception e) {
            if (trycounter == 0) {
                Xlo.war("Mongo Connection error, try repeat");
                return getCollection(collection, 1);
            }
            Xlo.err("Mongo Connection error", e);
            exitStatus = MongoEnu.ReturnCodes.ERROR_CONNECTION;
            return null;
        }
        exitStatus = MongoEnu.ReturnCodes.ERROR_COLLECTION_NOT_EXIST;
        return null;
    }

    /**
     * zkontroluje jestli kolekce uz neexistuje a pripadne ji vytvori
     *
     * @param collection
     * @return
     */
    public DBCollection createCollection(String collection) {
        DBCollection coll = getCollection(collection);
        if (db != null && coll == null) {
            coll = db.createCollection(collection, null);
        }

        return coll;
    }

    /**
     * insert object into collection
     *
     * @param collection
     * @param o
     * @return
     */
    public synchronized boolean insert(String collection, DBObject o) {
        DBCollection coll = createCollection(collection);
        if (coll != null && o != null) {
            WriteResult wr = coll.insert(o);

            // vetsi nez 0..
            return wr.getN() > 0;
        }
        return false;
    }

    /**
     * vlozi i s vrapperkem (zavola record )
     *
     * @param collection
     * @param e
     * @return
     */
    public boolean insert(String collection, IEntity e) {
        DBObject o = new BasicDBObject( e.toMap());
        return insert(collection, o);
    }

    /**
     * save object
     *
     * @param collection
     * @param o
     * @return
     */
    public synchronized boolean save(String collection, DBObject o) {
        DBCollection coll = createCollection(collection);
        if (coll != null && o != null) {
            WriteResult wr = coll.save(o);
            return wr.getN() > 0;
        }
        return false;
    }

    /**
     * save element
     *
     * @param collection
     * @param e
     * @return
     */
    public boolean save(String collection, IEntity e) {
        DBObject o = new BasicDBObject( e.toMap());
        return save(collection, (DBObject) o);
    }

    /**
     * remove object
     *
     * @param collection
     * @param o
     * @return
     */
    public synchronized boolean remove(String collection, DBObject o) {
        DBCollection coll = createCollection(collection);
        if (coll != null && o != null) {
            WriteResult wr = coll.remove(o, WriteConcern.ACKNOWLEDGED);
            if (wr.getN() > 0) {
//                if( o.isPartialObject()) {
//                    coll.remove( "{ '_id.oid' : '" + oid + "' }" );
//                }
                return true;
            }

            Xlo.err("Mongo can't remove object: " + o.toString() + " -> " + wr.getError());
        }
        return false;
    }

    /**
     * remove collection from database
     *
     * @param collection
     * @return
     */
    public boolean removeCollection(String collection) {
        DBCollection coll = getCollection(collection);
        if (coll != null) {
            WriteResult wr = coll.remove(new BasicDBObject());
            return wr.getN() > 0;
        }
        return false;
    }

    /**
     * Agregate by the string
     */
    public Iterable<DBObject> aggregate(String collection, String matchQuery, String groupQuery) {
        /**
         * jen zjisti co je prazdne a naplni to prazdnym objektama, ty funguji jako 'vsechno'
         */
//        return aggregate(collection,
//                StringUtils(matchQuery) ? new BasicDBObject() : (DBObject) JSON.parse(matchQuery),
//                new BasicDBObject(),
//                StringUtils.isNullOrEmpty(groupQuery) ? new BasicDBObject() : (DBObject) JSON.parse(groupQuery));
        return null;
    }

    /**
     * zavola aggregacni routineee, low level..
     */
    public Iterable<DBObject> aggregate(String collection, DBObject match, DBObject project, DBObject group) {
        DBCollection coll = getCollection(collection);
        if (coll != null) {
            AggregationOutput ao = coll.aggregate(match, project, group);
            if (ao.getCommandResult().ok()) {
                return ao.results();
            }
        }

        return null;
    }

    /**
     * diiconstruct ya
     */
    public void dispose() {
        close();
    }

    /**
     * vraci retezec dotazu na quid objektu
     *
     * @param g
     * @return
     */
    public static String guidQuery(String g) {
        return "{ '_id' : { $oid : '" + g + "' }}";
    }

    /**
     * retezec dotazu na guid's objektu
     *
     * @param g
     * @return
     */
//    public static String guidInQuery(Iterable<String> g) {
//        return "{ '_id' : { $in : [ " + Strings.join(",", g, "{ $oid : '%s' }") + " ] }}";
//    }
//
//    public static String guidInQuery(Collection<String> g) {
//        return "{ '_id' : { $in : [ " + Strings.join(",", g, "{ $oid : '%s' }") + " ] }}";
//    }

    /**
     * regular expression for box identity found
     *
     * @param role
     * @param boxIdentity
     * @return
     */
//    public static String roleRexQuery(Box.Role role, String boxIdentity) {
//        if (boxIdentity.length() > 20) {
//            return "{'_id' : { $oid : '" + boxIdentity + "' }, 'role' : '" + role.name() + "' }";
//        } else {
//            return "{ 'role' : '" + role.name() + "', $or : [ { 'ip': { $regex : '" + boxIdentity + ".*' }}, { 'hostname.name': { $regex : '" + boxIdentity + ".*' }} ] }";
//        }
//    }

    /**
     * query pro text search
     *
     * @param collection
     * @param wordsPhrase
     * @param fields
     * @return
     */
    public static DBObject textSearchQueryObject(String collection, String wordsPhrase, String fields) {
        /**
         * je potreba vytvorit spetzialni json objektik pro dotaz
         */
        DBObject dbo = new BasicDBObject();
        dbo.put("text", collection);
        dbo.put("search", wordsPhrase);
        dbo.put("sort", (DBObject) JSON.parse("{ '_id' : -1 }"));
        /**
         * jestli se nema vypisovat kompletni dokument, je potreba: { "_id" : 1 } napriklad
         */
//        if (!Strings.isNullOrEmpty(fields)) {
//            dbo.put("project", (DBObject) JSON.parse(fields));
//        }

        return dbo;
    }
}
