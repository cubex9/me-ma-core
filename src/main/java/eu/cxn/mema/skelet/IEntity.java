package eu.cxn.mema.skelet;

import eu.cxn.mema.json.Oma;
import eu.cxn.mema.Entity;
import java.util.Map;

/**
 *
 * @author kubasek
 */
public interface IEntity {

    String id();

    Class<?> clazz();

    INet net();

    IEntity setNet(INet net);

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    static <T extends IEntity> T read(String data) {
        Entity ie = (Entity)Oma.read(data, Entity.class);
        return (T)Oma.read(data, ie.clazz());
    }

    /**
     *
     * @param <T>
     * @return
     */
    default <T extends IEntity> String write() {
        return Oma.write(this);
    }

    /**
     *
     * @return
     */
    default Map toMap() {
        return null;
    }
}
