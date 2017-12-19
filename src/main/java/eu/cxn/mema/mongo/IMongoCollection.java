/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.mongo;

import com.mongodb.DBObject;
import eu.cxn.mema.skeleton.IEntity;
import java.util.List;

/**
 *
 * @author kubasek
 * @param <T>
 */
public interface IMongoCollection<T extends IEntity> {

    /**
     * nastaveni putIn objektu, kazdy metoda putIn se bude volat nad kazdym objektem ktery probehne
     * ctenim z databaze
     *
     * @param putIn
     */
    //void putIn(IMongoPutIn putIn);

    /**
     * find the object by query ya chci yield!!
     *
     * @param <E>
     * @param query
     * @return
     */
    <E> List<E> find(String query);

    /**
     * bajecny fulltext
     *
     * @param <E>
     * @param query
     * @param fields
     * @return
     */
    <E> List<E> textSearch(String query, String fields);

    /**
     * najde jeden kousek podle guid
     *
     * @param quid
     * @return
     */
    DBObject findGuid(String quid);

    /**
     * vytvori query
     *
     * @param g
     * @return
     */
    String guidQuery(String g);

    /**
     * najde jeden kousek
     *
     * @param <E>
     * @param query
     * @return
     */
    <E> E findOne(String query);

    /**
     * ugh !!
     *
     * @param <E>
     * @param cls
     * @param collection
     * @return
     */
    <E extends IEntity> IMongoCollection<E> createMoCoColle(Class<E> cls, String collection);

    /**
     * ugh !!
     *
     * @param <E>
     * @param cls
     * @param collection
     * @param losa : on Load/Save enable/disable
     * @return
     */
    <E extends IEntity> IMongoCollection<E> createMoCoColle(Class<E> cls, String collection, boolean losa);

    /**
     * vlozit
     *
     * @param o
     * @return
     */
    boolean insert(T o);

    /**
     * jelikos casto poskytujeme spojeni pres collectionalni spojeni, hodi se mit ho takhle venku
     * pristupny, a odvozovat od neji jiny collectionalni spojenicko ya ?
     *
     * @return
     */
    MongoConn mongoConn();

    /**
     * vrati kollekci pro kterou fachci tento stroj
     *
     * @return
     */
    String collection();

    /**
     * smazat
     *
     * @param o
     * @return
     */
    boolean remove(T o);

    /**
     * prepsat..
     *
     * @param o
     * @return
     */
    boolean save(T o);

    /**
     * filtr na zobrazovane fields, je globalni pro vsechny findy, vymaze se zadanim null
     *
     * @param fields
     */
    IMongoCollection<T> fields(String fields);
}
