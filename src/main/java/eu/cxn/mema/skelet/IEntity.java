package eu.cxn.mema.skelet;

import com.fasterxml.jackson.databind.ObjectMapper;
import static eu.cxn.mema.xlo.Xlo.err;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author kubasek
 */
public interface IEntity {

    long id();

    String clazz();
    
    ObjectMapper om();
    
    default <T extends IEntity> T read(String data) {
        try {
            return read((Map)om().readValue(data, Map.class));
        } catch ( IOException ioe ) {
            err("Can't read data: " + data, ioe);
        }
        return null;
    }
    
    default <T extends IEntity> T read( Map m) {
         try {
            T e = (T) Class.forName((String) m.get("clazz")).newInstance();
            return e.deserialize(m);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            err("Can't inicialize IEntity: " + m.get("clazz"), ex);
        }
        return null;       
    }
    
    default String write() {
        try {
            return om().writeValueAsString(this);
        } catch( Exception e ) {
            err("Can't write",e);
        }
        return null;
    }
}
