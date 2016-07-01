package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.*;

import java.util.Collection;

/**
 *
 * @author kubasek
 */
public class Node extends Entity implements INode {

    @JsonProperty
    String name;

    @JsonProperty
    String type;

    public Node() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public INodeType getType() {
        return null;
    }

    @Override
    public Collection<IFeature> features() {
        return null;
    }

    @Override
    public Collection<INodeTag> tags() {
        return null;
    }

    @Override
    public Collection<ILink> links() {
        return null;
    }

    @Override
    public Collection<IData> datas() {
        return null;
    }

}
