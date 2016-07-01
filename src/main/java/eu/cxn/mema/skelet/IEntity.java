package eu.cxn.mema.skelet;

import eu.cxn.mema.json.Oma;
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
            IEntity ie = (IEntity)Oma.read(data, IEntity.class);
            return (T)Oma.read(data, ie.clazz());
    }

    
    static <T extends IEntity> String write( T o ) {
        return Oma.write(o);
    }
}
