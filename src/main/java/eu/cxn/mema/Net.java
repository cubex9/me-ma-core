package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Oma;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.INet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kubasek
 */
public class Net extends Entity implements INet {

    @JsonProperty
    @JsonView(Views.Db.class)
    private String name;

    @JsonProperty
    @JsonView(Views.Db.class)
    private Map<String, IEntity> entities;

    public Net() {
    }

    public Net(String name) {
        this.name = name;
    }

    public static INet of(String name, String... entities) {
        Net n = new Net(name);
        n.entities = new HashMap<>();
        for (String e : entities) {
            IEntity ent = Entity.of(e);
            ent.setNet(n);
            n.entities.put(ent.id(), ent);
        }

        return n;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IEntity get(String id) {
        return entities.get(id);
    }

    @Override
    public String toString() {
        return entities.values().stream().collect(Collectors.groupingBy(IEntity::clazz))
            .entrySet().stream()
            .map(e -> e.getKey().getSimpleName() + ":\n\t"
                + e.getValue().stream().map(v -> v.toString()).collect(Collectors.joining(",\n\t"))
            )
            .collect(Collectors.joining("\n"));
    }

    @Override
    public String toJson() {
        return Oma.write(this);
    }
}
