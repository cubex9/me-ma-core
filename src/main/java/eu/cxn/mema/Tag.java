package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.IEntity;
import eu.cxn.mema.skeleton.ITag;

/**
 * Created by kubasek on 7/1/2016.
 */
public class Tag extends Entity implements ITag, IEntity {

    @JsonProperty
    @JsonView(Views.Db.class)
    String name;

    public Tag() {

    }

    @Override
    public String name() {
        return name;
    }
}
