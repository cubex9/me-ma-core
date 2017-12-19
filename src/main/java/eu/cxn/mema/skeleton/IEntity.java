package eu.cxn.mema.skeleton;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eu.cxn.mema.*;
import eu.cxn.mema.json.Oma;
import eu.cxn.mema.mongoj.MongoJAuth;

import java.util.Map;

/**
 *
 * @author kubasek
 */

@JsonSubTypes({
        @JsonSubTypes.Type(value = Entity.class, name = "entity"),
        @JsonSubTypes.Type(value = Net.class, name = "class"),
        @JsonSubTypes.Type(value = Node.class, name = "node"),
        @JsonSubTypes.Type(value = Link.class, name = "link"),
        @JsonSubTypes.Type(value = Tag.class, name = "tag"),

        // authentications
        @JsonSubTypes.Type(value = MongoJAuth.class, name = "mongo-auth")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "clazz")
public interface IEntity {

    String id();

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
