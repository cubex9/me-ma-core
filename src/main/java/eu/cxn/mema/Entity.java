package eu.cxn.mema;


import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.INet;
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

    @Override
    public String id() {
        if (id == null) {
            id = ObjectId.get().toString();
        }
        return id;
    }
}
