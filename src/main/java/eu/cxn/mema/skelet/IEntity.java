package eu.cxn.mema.skelet;

import eu.cxn.mema.json.Oma;
import eu.cxn.mema.Entity;
import static eu.cxn.mema.xlo.Xlo.err;
import java.util.Map;

/**
 *
 * @author kubasek
 */
public interface IEntity {

    String guid();

    Class<?> clazz();

    static <T extends IEntity> T read(String data) {
        Entity ie = (Entity)Oma.read(data, Entity.class);
        return (T)Oma.read(data, ie.clazz());
    }


    default <T extends IEntity> String write() {
        return Oma.write(this);
    }

    default Map toMap() {
        return null;
    }
}
