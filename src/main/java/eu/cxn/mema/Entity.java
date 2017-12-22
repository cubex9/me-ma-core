package eu.cxn.mema;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Oma;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.IProject;
import org.mongojack.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author kubasek
 */
public class Entity implements IEntity {
    private static final Logger LOG = LoggerFactory.getLogger(Entity.class);

    private IProject project;

    @ObjectId
    @JsonProperty("_id")
    @JsonView(Views.Db.class)
    private String id;

    @ObjectId
    @JsonProperty()
    @JsonView(Views.Db.class)
    private String projectId;


    public Entity() {
    }

    @Override
    public IProject project() {
        return project;
    }

    @Override
    public IEntity project(IProject project) {
        this.project = project;

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
