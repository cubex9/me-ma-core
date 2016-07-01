package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.IEntity;
import eu.cxn.mema.skelet.INet;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kubasek
 */
public class Net extends Entity implements INet {

    @JsonProperty
    private String name;

    private Map<String,IEntity> entities;

    public Net() {
    }

    public Net( String name ) {
        this.name = name;
    }

    public static INet of( String name, String... entities ) {
        Net n = new Net(name);
        n.entities = new HashMap<>();
        for( String e : entities ) {
            IEntity ent = Entity.of( e );
            n.entities.put( ent.id(), ent );
        }

        return n;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IEntity get( String id ) {
        return entities.get(id);
    }
}
