package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.ILink;
import eu.cxn.mema.skelet.ILinkType;
import eu.cxn.mema.skelet.INode;
import eu.cxn.mema.skelet.ITag;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kubasek on 7/1/2016.
 */
public class Link extends Entity implements ILink {

    @JsonProperty
    private String off;

    @JsonProperty
    private String target;

    @JsonProperty
    private String type;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
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
