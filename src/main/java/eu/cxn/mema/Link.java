package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.ILink;
import eu.cxn.mema.skelet.ILinkType;
import eu.cxn.mema.skelet.INode;
import eu.cxn.mema.skelet.ITag;

import java.util.Collection;
import java.util.List;

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
    public INode getOff() {
        return (INode)net().get(off);
    }

    @Override
    public INode getTarget() {
        return (INode)net().get(target);
    }

    @Override
    public ILinkType getType() {
        return null;
    }

    @Override
    public Collection<ITag> tags() {
        return null;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
