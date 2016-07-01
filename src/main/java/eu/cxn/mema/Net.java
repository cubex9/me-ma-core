package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.INet;
import eu.cxn.mema.skelet.ILink;
import eu.cxn.mema.skelet.INode;
import eu.cxn.mema.skelet.ITag;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author kubasek
 */
public class Net extends Entity implements INet {

    @JsonProperty
    private String name;

    @JsonProperty
    private Map<String, Node> nodes;

    @JsonProperty
    private Map<String, Link> links;

    @JsonProperty
    private Map<String, Tag> tags;

    @Override
    public Collection<? extends INode> nodes() {
        return nodes.values();
    }

    @Override
    public Collection<? extends ILink> links() {
        return links.values();
    }

    @Override
    public Collection<? extends ITag> tags() {
        return tags.values();
    }
}
