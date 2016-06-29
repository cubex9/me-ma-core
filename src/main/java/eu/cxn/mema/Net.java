package eu.cxn.mema;

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
public class Net implements INet {

    private Map<String, INode> nodes;

    private Map<String, ILink> links;

    private Map<String, ITag> tags;

    @Override
    public Collection<INode> nodes() {
        return nodes.values();
    }

    @Override
    public Collection<ILink> links() {
        return links.values();
    }

    @Override
    public Collection<ITag> tags() {
        return tags.values();
    }
}
