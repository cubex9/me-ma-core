package eu.cxn.mema;


import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.IEntity;
import eu.cxn.mema.skelet.INet;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author kubasek
 */
public class Entity implements IEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Entity.class);

    private INet net;

    @JsonProperty
    private String guid;

    @JsonProperty
    private String clazz;

    private Class<?> cls;

    public Entity() {
    }

    @Override
    public INet net() {
        return net;
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
            guid = ObjectId.get().toString();
        }
        return guid;
    }

    @Override
    public Class<?> clazz() {
        try {
            if( clazz == null ) {
                if( cls == null ) {
                    cls = getClass();
                }
                clazz = cls.getName();
            } else {
                if( cls == null ) {
                    cls = Class.forName("eu.cxn.mema." + clazz );
                }
            }
            return cls;
        } catch (Exception ex) {
            LOG.error( "Cant get class: ", ex);
        }

        return null;
    }

}
