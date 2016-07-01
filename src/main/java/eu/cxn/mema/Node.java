package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cxn.mema.skelet.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kubasek
 */
public class Node extends Entity implements INode {

    @JsonProperty
    private String name;

    @JsonProperty
    private String type;

    @JsonProperty
    private List<String> tags;

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
    public Collection<ITag> tags() {
        return tags == null ? Arrays.asList() : tags.stream().map(t -> (ITag)net().get(t)).collect(Collectors.toList());
    }

    @Override
    public Collection<ILink> links() {
        return null;
    }

    @Override
    public Collection<IData> datas() {
        return null;
    }

    @Override
    public INode put( IEntity e ) {
        if( e instanceof ITag ) {
            tags.add(e.id());
        }

        return this;
    }

    @Override
    public String toString() {
        Collection<ITag> tags = tags();

        return name + "("+id()+")"
            + ( tags.isEmpty() ? "" : " #" + tags.stream().map( t -> t.getName()).collect(Collectors.joining(", #")));
    }
}
