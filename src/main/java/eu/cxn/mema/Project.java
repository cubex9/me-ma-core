package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.IProject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kubasek
 */
public class Project extends Entity implements IProject {

    @JsonProperty
    @JsonView(Views.Db.class)
    private String name;

    @JsonProperty
    @JsonView(Views.Db.class)
    private List<IEntity> entities;

    /* hashed index of entities, by id */
    private Map<String, IEntity> index;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public IEntity get(String id) {
        return index(id);
    }

    /**
     * faster get
     *
     * @param id
     * @return
     */
    private IEntity index(String id) {
        if (index == null) {
            index = entities.stream().collect(Collectors.toMap(IEntity::id, e -> e));
        }

        return index.get(id);
    }
}
