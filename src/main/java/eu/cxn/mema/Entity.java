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
    private String id;

    @JsonProperty
    private String clazz;

    private Class<?> cls;

    public Entity() {
    }

    @Override
    public INet net() {
        return net;
    }

    @Override
    public IEntity setNet( INet net) {
        this.net = net;
        return this;
    }


    public static Entity of( String json ) {
        return (Entity)IEntity.read(json);
    }


    @Override
    public String id() {
        if (id == null) {
            id = ObjectId.get().toString();
        }
        return id;
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
