package eu.cxn.mema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import eu.cxn.mema.json.Views;
import eu.cxn.mema.skeleton.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kubasek
 */
public class Node extends Entity implements INode, IEntity {

    @JsonProperty
    @JsonView(Views.Db.class)
    private String name;

    @JsonProperty
    @JsonView(Views.Db.class)
    private String type;

    @JsonProperty
    @JsonView(Views.Db.class)
    private List<String> tags;

    public Node() {
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public INodeType type() {
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
}
