package eu.cxn.mema;

import eu.cxn.mema.json.Oma;
import eu.cxn.mema.skelet.IEntity;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

import static eu.cxn.mema.xlo.Xlo.info;

/**
 *
 * @author kubasek
 */
public class Entity implements IEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Entity.class);
    

    private ObjectId guid;

    private String clazz;

    private Class<?> cls;

    public Entity() {

    }

    public static Entity of( String json ) {
        return (Entity)IEntity.read(json);
    }


//    public Map serialize() {
//        /* pokud nebyl nikdy pouzity, je potreba inicializovat id */
//        info( "SERIALIZE: " + clazz() + " [ "+guid()+" ]");
//
//        /* pak uz standartni process */
//        Map<String, Object> res = new LinkedHashMap<>();
//        res.put("_id", guid);
//        res.put("clazz", clazz());
//
//        Oma.write(this);
//
//        return res;
//    }
//
//    @Override
//    public <T extends IEntity> T deserialize(Map m) {
//
//
//        return null;
//    }

    @Override
    public String guid() {
        if (guid == null) {
            guid = ObjectId.get();
        }
        return guid.toString();
    }

    @Override
    public Class<?> clazz() {
        try {
            if (cls == null) {
                cls = getClass();
            }
            return cls;
        } catch (Exception ex) {
            LOG.error( "Cant get class: ", ex);
        }

        return null;
    }

}
