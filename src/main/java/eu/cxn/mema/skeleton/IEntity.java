package eu.cxn.mema.skeleton;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eu.cxn.mema.*;
import eu.cxn.mema.json.Oma;
import eu.cxn.mema.json.Views;

import java.util.Map;

/**
 *
 * @author kubasek
 */

@JsonSubTypes({
        @JsonSubTypes.Type(value = Entity.class, name = "entity"),
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Node.class, name = "node"),
        @JsonSubTypes.Type(value = Link.class, name = "link"),
        @JsonSubTypes.Type(value = Tag.class, name = "tag"),
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "clazz")
public interface IEntity {

    String id();

    IProject project();

    IEntity project(IProject net);

    IEntity id(String id);

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    static <T extends IEntity> T read(String data) {
        Entity ie = (Entity) Oma.read(data, IEntity.class);
        return (T) Oma.read(data, ie.getClass());
    }

    /**
     *
     * @param <T>
     * @return
     */
    default <T extends IEntity> String write() {
        return Oma.write(Views.Db.class, this);
    }

    /**
     *
     * @return
     */
    default Map toMap() {
        return null;
    }
}
