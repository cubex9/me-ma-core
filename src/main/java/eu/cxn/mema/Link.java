package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kubasek on 7/1/2016.
 */
public class Link extends Entity implements ILink, IEntity {

    @JsonProperty
    @JsonView(Views.Db.class)
    private String off;

    @JsonProperty
    @JsonView(Views.Db.class)
    private String target;

    @JsonProperty
    @JsonView(Views.Db.class)
    private String type;

    @JsonProperty
    @JsonView(Views.Db.class)
    private List<String> tags;

    @JsonProperty
    @JsonView(Views.Db.class)
    private double weight;

    public Link() {
    }

    @Override
    @JsonIgnore
    public INode getOff() {
        return (INode)net().get(off);
    }

    @Override
    @JsonIgnore
    public INode getTarget() {
        return (INode)net().get(target);
    }

    @Override
    @JsonIgnore
    public ILinkType getType() {
        return null;
    }

    @Override
    public Collection<ITag> tags() {
        return tags.stream().map( t -> (ITag)net().get(t)).collect(Collectors.toList());
    }


    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "[" + getOff() + " -> " + getTarget() + "] ("+id()+")";
    }
}
