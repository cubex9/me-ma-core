package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.ILink;
import eu.cxn.mema.skelet.ILinkTag;
import eu.cxn.mema.skelet.ILinkType;
import eu.cxn.mema.skelet.INode;

import java.util.Collection;

/**
 * Created by kubasek on 7/1/2016.
 */
public class Link extends Entity implements ILink {


    @JsonProperty
    private Node off;

    @JsonProperty
    private Node target;

    @JsonProperty
    private LinkType type;

    @JsonProperty
    private Map<String,Tag> tags;

    @JsonProperty
    private double weight;

    @Override
    public INode getOff() {
        return off;
    }

    @Override
    public INode getTarget() {
        return target;
    }

    @Override
    public ILinkType getType() {
        return type;
    }

    @Override
    public Collection<ILinkTag> tags() {
        return null;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
