package eu.cxn.mema;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Oma;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.INet;
import org.mongojack.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author kubasek
 */
public class Entity implements IEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Entity.class);

    private INet net;

    @ObjectId
    @JsonProperty("_id")
    @JsonView(Views.Db.class)
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
        return id;
    }

    @Override
    public Entity id(String id) {
        this.id = id;

        return this;
    }

    @Override
    public String toString() {
        return Oma.write(Views.Db.class, this);
    }
}
